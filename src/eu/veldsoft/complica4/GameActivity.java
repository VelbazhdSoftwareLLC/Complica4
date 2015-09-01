package eu.veldsoft.complica4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class GameActivity extends Activity {

	private ImageView pieces[][] = new ImageView[Board.COLS][Board.ROWS];

	private View.OnClickListener listeners[] = new View.OnClickListener[Board.COLS];

	private Board board = new Board();

	private void updateViews() {
		Piece values[][] = board.getPieces();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
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
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

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
			listeners[index] = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int i = -1;
					for (i = 0; i < listeners.length; i++) {
						if (this == listeners[i]) {
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
					// TODO board.isGameOver();
					board.next();
					updateViews();
				}
			};

			for (ImageView view : array) {
				view.setOnClickListener(listeners[index]);
			}

			index++;
		}

		board.reset();
		updateViews();
	}
}
