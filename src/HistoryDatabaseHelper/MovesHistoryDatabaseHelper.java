package HistoryDatabaseHelper;

import eu.veldsoft.complica4.model.Example;
import eu.veldsoft.complica4.model.Util;
import android.content.ContentValues;
import android.content.Context;
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
		public static final String COLUMN_NAME_INDEX = "index";
		public static final String COLUMN_NAME_STATE = "state";
		public static final String COLUMN_NAME_TIME = "time";
	}

	/**
	 * Create table SQL patter.
	 */
	private static final String SQL_CREATE_HISTORY = "CREATE TABLE "
			+ MovesHistoryColumns.TABLE_NAME + " (" + MovesHistoryColumns._ID
			+ " INTEGER PRIMARY KEY," + MovesHistoryColumns.COLUMN_NAME_PIECE
			+ " INTEGER, " + MovesHistoryColumns.COLUMN_NAME_INDEX
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

		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MovesHistoryColumns.COLUMN_NAME_PIECE, move.piece);
		values.put(MovesHistoryColumns.COLUMN_NAME_INDEX, move.index);
		values.put(MovesHistoryColumns.COLUMN_NAME_STATE, state);
		values.put(MovesHistoryColumns.COLUMN_NAME_TIME,
				System.currentTimeMillis() / 1000L);
		db.insert(MovesHistoryColumns.TABLE_NAME, null, values);
	}

	public Example retrieveMove() {
		return retrieveMove(Util.PRNG.nextInt());
	}

	public Example retrieveMove(int position) {
		int piece = -1;
		int index = -1;
		int state[][] = { {} };

		// TODO Implement database read.

		return new Example(state, piece, index);
	}

	public void removeOldRecords() {
		// TODO Remove too old records.
	}
}
