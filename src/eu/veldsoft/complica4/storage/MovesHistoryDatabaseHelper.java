package eu.veldsoft.complica4.storage;

import eu.veldsoft.complica4.model.Example;
import eu.veldsoft.complica4.model.Util;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Database helper class.
 * 
 * @author Todor Balabanov
 */
public class MovesHistoryDatabaseHelper extends SQLiteOpenHelper {
	/**
	 * History table columns description class.
	 * 
	 * @author Todor Balabanov
	 */
	public static abstract class MovesHistoryColumns implements BaseColumns {
		public static final String TABLE_NAME = "history";
		public static final String COLUMN_NAME_PIECE = "piece";
		public static final String COLUMN_NAME_COLUNM = "colunm";
		public static final String COLUMN_NAME_RANK = "rank";
		public static final String COLUMN_NAME_STATE = "state";
		public static final String COLUMN_NAME_TIME = "time";
	}

	/**
	 * Create table SQL patter.
	 */
	private static final String SQL_CREATE_HISTORY = "CREATE TABLE "
			+ MovesHistoryColumns.TABLE_NAME + " (" + MovesHistoryColumns._ID
			+ " INTEGER PRIMARY KEY," + MovesHistoryColumns.COLUMN_NAME_PIECE
			+ " INTEGER, " + MovesHistoryColumns.COLUMN_NAME_COLUNM
			+ " INTEGER, " + MovesHistoryColumns.COLUMN_NAME_RANK
			+ " INTEGER, " + MovesHistoryColumns.COLUMN_NAME_STATE + " TEXT, "
			+ MovesHistoryColumns.COLUMN_NAME_TIME + " INTEGER)";

	/**
	 * Drop database SQL pattern.
	 */
	static final String SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS "
			+ MovesHistoryColumns.TABLE_NAME;

	/**
	 * Database integer version.
	 */
	public static final int DATABASE_VERSION = 1;

	/**
	 * Database file name.
	 */
	public static final String DATABASE_NAME = "MovesHistory.db";

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            Context of database helper usage.
	 */
	public MovesHistoryDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_HISTORY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_HISTORY);
		onCreate(db);
	}

	/**
	 * Store move into database.
	 * 
	 * @param move
	 *            Move description.
	 */
	public void storeMove(Example move) {
		String state = "";
		for (int i = 0; i < move.state.length; i++) {
			for (int j = 0; j < move.state[i].length; j++) {
				state += move.state[i][j];
				if (j < move.state[i].length - 1) {
					state += " ";
				}
			}
			if (i < move.state.length - 1) {
				state += "\n";
			}
		}

		ContentValues values = new ContentValues();
		values.put(MovesHistoryColumns.COLUMN_NAME_PIECE, move.piece);
		values.put(MovesHistoryColumns.COLUMN_NAME_RANK, move.rank);
		values.put(MovesHistoryColumns.COLUMN_NAME_COLUNM, move.colunm);
		values.put(MovesHistoryColumns.COLUMN_NAME_STATE, state);
		values.put(MovesHistoryColumns.COLUMN_NAME_TIME,
				System.currentTimeMillis() / 1000L);
		getWritableDatabase().insert(MovesHistoryColumns.TABLE_NAME, null,
				values);
	}

	/**
	 * Obtain random example.
	 * 
	 * @return Object of randomly selected example.
	 */
	public Example retrieveMove() {
		return retrieveMove(Util.PRNG.nextInt(Integer.MAX_VALUE));
	}

	/**
	 * Obtain example at particular position.
	 * 
	 * @param position
	 *            Position on the table.
	 * 
	 * @return Object of selected example.
	 */
	public Example retrieveMove(int position) {
		if (position < 0) {
			throw new RuntimeException("Negative index is not possible.");
		}

		Cursor cursor = getReadableDatabase().query(
				MovesHistoryColumns.TABLE_NAME,
				new String[] { MovesHistoryColumns.COLUMN_NAME_PIECE,
						MovesHistoryColumns.COLUMN_NAME_COLUNM,
						MovesHistoryColumns.COLUMN_NAME_RANK,
						MovesHistoryColumns.COLUMN_NAME_STATE,
						MovesHistoryColumns.COLUMN_NAME_TIME }, null, null,
				null, null, MovesHistoryColumns._ID + " ASC", "1");

		/*
		 * If the database table is empty no example object can be created.
		 */
		if (cursor.getCount() == 0) {
			throw new RuntimeException("Empty database.");
		}

		/*
		 * Limit position.
		 */
		position %= cursor.getCount();

		/*
		 * Go to row on a particular position.
		 */
		cursor.moveToFirst();
		for (int p = 0; p < position; p++) {
			cursor.moveToNext();
		}

		int piece = cursor.getInt(0);
		int colunm = cursor.getInt(1);
		int rank = cursor.getInt(2);
		String text = cursor.getString(3);
		cursor.close();

		/*
		 * Parse state two dimensional array.
		 */
		int i = 0;
		String lines[] = text.split("\\r?\\n");
		int state[][] = new int[lines.length][];
		for (String line : lines) {
			String numbers[] = line.split("\\s+");
			state[i] = new int[numbers.length];
			int j = 0;
			for (String number : numbers) {
				state[i][j] = Integer.parseInt(number);
			}
			i++;
		}

		return new Example(state, piece, colunm, rank);
	}

	/**
	 * Remove useless examples.
	 */
	public void removeOldRecords() {
		// TODO Remove too old records.
	}
}
