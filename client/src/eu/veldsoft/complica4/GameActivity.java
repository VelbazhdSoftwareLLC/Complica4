package eu.veldsoft.complica4;

import java.util.List;

import org.encog.neural.networks.BasicNetwork;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Example;
import eu.veldsoft.complica4.model.Piece;
import eu.veldsoft.complica4.model.Util;
import eu.veldsoft.complica4.model.ia.ArtificialIntelligence;
import eu.veldsoft.complica4.model.ia.SimpleRulesArtificialIntelligence;
import eu.veldsoft.complica4.storage.MovesHistoryDatabaseHelper;

/**
 * Game screen.
 * 
 * @author Todor Balabanov
 */
public class GameActivity extends Activity {
	/**
	 * Database helper reference.
	 */
	MovesHistoryDatabaseHelper helper = null;

	/**
	 * Image references.
	 */
	private ImageView pieces[][] = new ImageView[Board.COLS][Board.ROWS];

	/**
	 * Click listeners.
	 */
	private View.OnClickListener onClick[] = new View.OnClickListener[Board.COLS];

	/**
	 * Bot operation handler.
	 */
	private final Handler botHandler = new Handler();

	/**
	 * Pool with sounds.
	 */
	private SoundPool sounds = null;

	/**
	 * Click sound identifier.
	 */
	private int clickId = -1;

	/**
	 * Finish sound identifier.
	 */
	private int finishId = -1;

	/**
	 * Board object model.
	 */
	private Board board = new Board();

	/**
	 * Array with bots.
	 */
	private ArtificialIntelligence bots[] = {};

	/**
	 * Artificial intelligence object.
	 */
	private Runnable botAction = new Runnable() {
		@Override
		public void run() {
			if (board.isGameOver() == true) {
				return;
			}

			int state[][] = board.getState();

			switch (board.getTurn() % 4) {
			case 0:
				board.addTo(bots[0].move(state, 1), Piece.PLAYER1);
				break;
			case 1:
				board.addTo(bots[1].move(state, 2), Piece.PLAYER2);
				break;
			case 2:
				board.addTo(bots[2].move(state, 3), Piece.PLAYER3);
				break;
			case 3:
				board.addTo(bots[3].move(state, 4), Piece.PLAYER4);
				break;
			}

			board.next();
			updateViews();

			if (board.getTurn() % 4 != 0) {
				botHandler.postDelayed(botAction, 500);
			}
		}
	};

	/**
	 * Start animation helper funcion.
	 */
	private void startAnimation() {
		// TODO End animation listener.
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeIn.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				((ImageView) findViewById(R.id.background_logo)).setAlpha(0.1F);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		findViewById(R.id.background_logo).startAnimation(fadeIn);

		Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j].startAnimation(fadeOut);
			}
		}
	}

	/**
	 * Store session of wining training examples.
	 * 
	 * @param session
	 *            List of training examples.
	 */
	private void storeTrainingExamples(List<Example> session) {
		if (helper == null) {
			// TODO In more strict implementation exception should be risen.
			return;
		}

		for (Example example : session) {
			helper.storeMove(example);
		}
	}

	/**
	 * Update UI helper function.
	 */
	private void updateViews() {
		Piece values[][] = board.getPieces();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j].setAlpha(1F);

				switch (values[i][j]) {
				case PLAYER1:
					pieces[i][j].setImageResource(R.drawable.blue);
					break;
				case PLAYER2:
					pieces[i][j].setImageResource(R.drawable.green);
					break;
				case PLAYER3:
					pieces[i][j].setImageResource(R.drawable.orange);
					break;
				case PLAYER4:
					pieces[i][j].setImageResource(R.drawable.fuchsia);
					break;
				case EMPTY:
					pieces[i][j].setImageResource(R.drawable.white);
					break;
				}
			}
		}

		if (board.isGameOver() == true || board.hasWinner() == true) {
			/*
			 * Store winner session in SQLite database.
			 */
			storeTrainingExamples(board.getWinnerSession());

			Toast.makeText(this,
					getResources().getString(R.string.game_over_message),
					Toast.LENGTH_LONG).show();
			sounds.play(finishId, 0.99f, 0.99f, 0, 0, 1);
		}

		int winners[][] = board.winners();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				if (board.isGameOver() == true && values[i][j] == Piece.EMPTY) {
					pieces[i][j].setImageBitmap(null);
				}

				if (board.isGameOver() == true && winners[i][j] == 0) {
					pieces[i][j].setAlpha(0.4F);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		/*
		 * Wake-up alarm for network training.
		 */
		startService(new Intent(this, NetworkTrainingService.class));

		/*
		 * Database helper object.
		 */
		helper = new MovesHistoryDatabaseHelper(GameActivity.this);

		BasicNetwork net = null;
		// TODO net = loadFromServer();
		if (net == null) {
			net = Util.loadFromFile(getFilesDir() + "/" + Util.ANN_FILE_NAME);
		}
		bots = new ArtificialIntelligence[] {
				new SimpleRulesArtificialIntelligence(),
				// new NeuralNetworkArtificialIntelligence(net, Board.COLS
				// * Board.ROWS + Board.NUMBER_OF_PLAYERS, Board.COLS
				// * Board.ROWS / 2, Board.COLS, Piece.getMinId(),
				// Piece.getMaxId()),
				new SimpleRulesArtificialIntelligence(),
				new SimpleRulesArtificialIntelligence(),
				new SimpleRulesArtificialIntelligence(), };

		sounds = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		clickId = sounds.load(this, R.raw.schademans_pipe9, 1);
		finishId = sounds.load(this, R.raw.game_sound_correct, 1);

		pieces[0][0] = (ImageView) findViewById(R.id.piece00);
		pieces[0][1] = (ImageView) findViewById(R.id.piece01);
		pieces[0][2] = (ImageView) findViewById(R.id.piece02);
		pieces[0][3] = (ImageView) findViewById(R.id.piece03);
		pieces[0][4] = (ImageView) findViewById(R.id.piece04);
		pieces[0][5] = (ImageView) findViewById(R.id.piece05);
		pieces[0][6] = (ImageView) findViewById(R.id.piece06);
		pieces[1][0] = (ImageView) findViewById(R.id.piece10);
		pieces[1][1] = (ImageView) findViewById(R.id.piece11);
		pieces[1][2] = (ImageView) findViewById(R.id.piece12);
		pieces[1][3] = (ImageView) findViewById(R.id.piece13);
		pieces[1][4] = (ImageView) findViewById(R.id.piece14);
		pieces[1][5] = (ImageView) findViewById(R.id.piece15);
		pieces[1][6] = (ImageView) findViewById(R.id.piece16);
		pieces[2][0] = (ImageView) findViewById(R.id.piece20);
		pieces[2][1] = (ImageView) findViewById(R.id.piece21);
		pieces[2][2] = (ImageView) findViewById(R.id.piece22);
		pieces[2][3] = (ImageView) findViewById(R.id.piece23);
		pieces[2][4] = (ImageView) findViewById(R.id.piece24);
		pieces[2][5] = (ImageView) findViewById(R.id.piece25);
		pieces[2][6] = (ImageView) findViewById(R.id.piece26);
		pieces[3][0] = (ImageView) findViewById(R.id.piece30);
		pieces[3][1] = (ImageView) findViewById(R.id.piece31);
		pieces[3][2] = (ImageView) findViewById(R.id.piece32);
		pieces[3][3] = (ImageView) findViewById(R.id.piece33);
		pieces[3][4] = (ImageView) findViewById(R.id.piece34);
		pieces[3][5] = (ImageView) findViewById(R.id.piece35);
		pieces[3][6] = (ImageView) findViewById(R.id.piece36);
		pieces[4][0] = (ImageView) findViewById(R.id.piece40);
		pieces[4][1] = (ImageView) findViewById(R.id.piece41);
		pieces[4][2] = (ImageView) findViewById(R.id.piece42);
		pieces[4][3] = (ImageView) findViewById(R.id.piece43);
		pieces[4][4] = (ImageView) findViewById(R.id.piece44);
		pieces[4][5] = (ImageView) findViewById(R.id.piece45);
		pieces[4][6] = (ImageView) findViewById(R.id.piece46);

		int index = 0;
		for (ImageView array[] : pieces) {
			onClick[index] = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (board.isGameOver() == true) {
						return;
					}

					if (board.getTurn() % 4 != 0) {
						return;
					}

					int i = -1;
					for (i = 0; i < onClick.length; i++) {
						if (this == onClick[i]) {
							break;
						}
					}
					switch (board.getTurn() % 4) {
					case 0:
						board.addTo(i, Piece.PLAYER1);
						break;
					case 1:
						board.addTo(i, Piece.PLAYER2);
						break;
					case 2:
						board.addTo(i, Piece.PLAYER3);
						break;
					case 3:
						board.addTo(i, Piece.PLAYER4);
						break;
					}
					board.next();
					updateViews();
					sounds.play(clickId, 0.99f, 0.99f, 0, 0, 1);

					botHandler.postDelayed(botAction, 500);
				}
			};

			for (ImageView view : array) {
				view.setOnClickListener(onClick[index]);
			}

			index++;
		}

		board.reset();
		updateViews();

		startAnimation();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_option_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_game:
			board.reset();
			updateViews();
			break;
		case R.id.help:
			startActivity(new Intent(GameActivity.this, HelpActivity.class));
			break;
		case R.id.about:
			startActivity(new Intent(GameActivity.this, AboutActivity.class));
			break;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (helper != null) {
			helper.close();
			helper = null;
		}

		sounds.release();
		sounds = null;
	}
}
