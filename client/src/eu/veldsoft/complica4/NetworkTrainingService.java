package eu.veldsoft.complica4;

import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.WakefulBroadcastReceiver;
import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Example;
import eu.veldsoft.complica4.model.Piece;
import eu.veldsoft.complica4.model.Util;
import eu.veldsoft.complica4.storage.MovesHistoryDatabaseHelper;

/**
 * Artificial Neural Network training class.
 * 
 * @author Todor Balabanov
 */
public class NetworkTrainingService extends Service {
	/**
	 * Reference to database helper.
	 */
	MovesHistoryDatabaseHelper helper = null;

	/**
	 * Setup alarm for service activation.
	 */
	private void setupAlarm() {
		/*
		 * Do not set if it is already there.
		 */
		if (PendingIntent.getBroadcast(this, Util.ALARM_REQUEST_CODE,
				new Intent(getApplicationContext(),
						NetworkTrainingService.class),
				PendingIntent.FLAG_NO_CREATE) != null) {
			return;
		}

		/*
		 * Parameterize weak-up interval.
		 */
		long interval = AlarmManager.INTERVAL_HALF_HOUR;
		try {
			interval = getPackageManager().getServiceInfo(
					new ComponentName(NetworkTrainingService.this,
							NetworkTrainingService.this.getClass()),
					PackageManager.GET_SERVICES | PackageManager.GET_META_DATA).metaData
					.getInt("interval", (int) AlarmManager.INTERVAL_HALF_HOUR);
		} catch (NameNotFoundException exception) {
			interval = AlarmManager.INTERVAL_HALF_HOUR;
			System.err.println(exception);
		}

		((AlarmManager) this.getSystemService(Context.ALARM_SERVICE))
				.setInexactRepeating(AlarmManager.RTC_WAKEUP, System
						.currentTimeMillis(), interval, PendingIntent
						.getBroadcast(this, Util.ALARM_REQUEST_CODE,
								new Intent(getApplicationContext(),
										NetworkTrainingReceiver.class),
								PendingIntent.FLAG_UPDATE_CURRENT));
	}

	/**
	 * Service constructor.
	 */
	public NetworkTrainingService() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		helper = new MovesHistoryDatabaseHelper(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int id) {
		/*
		 * Check alarm.
		 */
		setupAlarm();

		/*
		 * Release wake-up lock.
		 */
		if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
			WakefulBroadcastReceiver.completeWakefulIntent(intent);
		}

		/*
		 * Single training cycle.
		 */
		(new AsyncTask<Void, Void, Void>() {
			/**
			 * Network training.
			 */
			@Override
			protected Void doInBackground(Void... params) {
				/*
				 * If there is no training examples do nothing.
				 */
				if (helper == null || helper.hasMove() == false) {
					return null;
				}

				/*
				 * Load network from a file.
				 */
				BasicNetwork net = Util.loadFromFile(getFilesDir() + "/"
						+ Util.ANN_FILE_NAME);

				/*
				 * Create new network if there is no network in the file.
				 */
				if (net == null) {
					net = Util.newNetwork(Board.COLS * Board.ROWS
							+ Board.NUMBER_OF_PLAYERS, Board.COLS * Board.ROWS
							/ 2, Board.COLS);
				}

				/*
				 * Form training set.
				 */
				double min = Piece.getMinId();
				double max = Piece.getMaxId();
				double inputSet[][] = new double[Util.NUMBER_OF_SINGLE_TRAINING_EXAMPLES][net
						.getInputCount()];
				double expectedSet[][] = new double[Util.NUMBER_OF_SINGLE_TRAINING_EXAMPLES][net
						.getOutputCount()];
				for (int e = 0; e < Util.NUMBER_OF_SINGLE_TRAINING_EXAMPLES; e++) {
					Example example = helper.retrieveMove();

					/*
					 * Scale input in the range of [0.0-1.0].
					 */
					double input[] = new double[net.getInputCount()];
					for (int i = 0, k = 0; i < example.state.length; i++) {
						for (int j = 0; j < example.state[i].length; j++, k++) {
							input[k] = (example.state[i][j] - min)
									/ (max - min);
						}
					}

					/*
					 * Mark the player who is playing.
					 */
					for (int i = input.length - Board.NUMBER_OF_PLAYERS, p = 1; i < input.length; i++, p++) {
						if (example.piece == p) {
							input[i] = 1;
						} else {
							input[i] = 0;
						}
					}

					/*
					 * Mark the column to playing.
					 */
					double expected[] = new double[net.getOutputCount()];
					for (int i = 0; i < expected.length; i++) {
						if (example.colunm == i) {
							expected[i] = 1;
						} else {
							expected[i] = 0;
						}
					}

					/*
					 * For training pair.
					 */
					inputSet[e] = input;
					expectedSet[e] = expected;
				}
				MLDataSet trainingSet = new BasicMLDataSet(inputSet,
						expectedSet);

				/*
				 * Train network.
				 */
				ResilientPropagation train = new ResilientPropagation(net,
						trainingSet);
				train.iteration();
				train.finishTraining();

				/*
				 * Save network to a file.
				 */
				Util.saveToFile(net, getFilesDir() + "/" + Util.ANN_FILE_NAME);

				/*
				 * Stop service.
				 */
				NetworkTrainingService.this.stopSelf();
				return null;
			}
		}).execute();

		return START_NOT_STICKY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDestroy() {
		if (helper != null) {
			helper.close();
			helper = null;
		}
		super.onDestroy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return new Binder() {
			Service getService() {
				return NetworkTrainingService.this;
			}
		};
	}
}
