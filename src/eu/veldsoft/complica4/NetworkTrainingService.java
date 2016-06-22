package eu.veldsoft.complica4;

import eu.veldsoft.complica4.storage.MovesHistoryDatabaseHelper;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

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
		// TODO

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
