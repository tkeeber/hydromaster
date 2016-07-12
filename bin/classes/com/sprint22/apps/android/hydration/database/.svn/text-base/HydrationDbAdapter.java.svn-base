package com.sprint22.apps.android.hydration.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class HydrationDbAdapter {

	private static final String DATABASE_NAME = "ihydration";

	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd"); 

	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_CONFIGURATION = "configuration";

	public static final String CONFIG_VISIT_ID_COL = "_id";
	public static final String CONFIG_SHOW_START_SCREEN_COL = "show_start_screen";

	public static final String WATER_DRANK_TODAY = "water_drank_today";
	public static final String WATER_TOTAL_WATER = "total_water_intake";
	//	public static final String WATER_TODAYS_DATE = 

	//set the initial start up values. 
	//set the config screen to tbe displayed.
	private static final String CREATE_INITIAL_CONFIGURATION = "INSERT INTO " + TABLE_CONFIGURATION + " VALUES (1, 1)";

	private static final String CREATE_TABLE_CONFIGURATION ="create table " 
		+ TABLE_CONFIGURATION + "" +
		" (_id integer primary key autoincrement," +
		" show_start_screen int );";

	private static final String CREATE_TABLE_CONSUME_HISTORY ="create table " 
		+ ConsumeHistory.TABLE_CONSUME_HISTORY + "" +
		" ("+  ConsumeHistory._ID + " integer primary key autoincrement," +
		ConsumeHistory.COLUMN_TOTAL_CONSUMED_MILS + " double," +
		ConsumeHistory.COLUMN_PREDICATED_CONSUMED_MILS + " double," +
		ConsumeHistory.COLUMN_WATER_MEASUREMENT_UNIT + " text," +
		ConsumeHistory.COLUMN_GLASSES_DRANK_DAILY + " number," +
		ConsumeHistory.COLUMN_DATE + " date );";

	public static final String[] CONSUME_HISTORY_CALL_COLUMNS = {ConsumeHistory._ID, 
		ConsumeHistory.COLUMN_TOTAL_CONSUMED_MILS, 
		ConsumeHistory.COLUMN_PREDICATED_CONSUMED_MILS, 
		ConsumeHistory.COLUMN_DATE,
		ConsumeHistory.COLUMN_WATER_MEASUREMENT_UNIT, 
		ConsumeHistory.COLUMN_GLASSES_DRANK_DAILY};


	public class ConsumeHistory implements BaseColumns {

		public static final String TABLE_CONSUME_HISTORY = "consume_history";
		public static final String COLUMN_TOTAL_CONSUMED_MILS = "total_consumed_mils";
		public static final String COLUMN_PREDICATED_CONSUMED_MILS = "predicated_consumed_mils";
		public static final String COLUMN_WATER_MEASUREMENT_UNIT = "comsume_water_measurement";
		public static final String COLUMN_GLASSES_DRANK_DAILY = "glasses_consumed_daily";
		public static final String COLUMN_DATE = "consumed_date";
	}

	private Context mCtx;

	private DatabaseHelper mDbHelper;

	private SQLiteDatabase mDb;

	/**DATE_FORMATTER
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx the Context within which to work
	 */
	public HydrationDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}


	/**
	 * Open the notes database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException if the database could be neither opened or created
	 */
	public HydrationDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public long updateTodaysWaterData(long glassesDrankDaily, long waterDrankMils, long shouldHaveDrankMils, String waterMeasurementUnit)
	{    	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date date = new java.util.Date();

		ContentValues args = new ContentValues();
		args.put(ConsumeHistory.COLUMN_TOTAL_CONSUMED_MILS, waterDrankMils);
		args.put(ConsumeHistory.COLUMN_PREDICATED_CONSUMED_MILS, shouldHaveDrankMils);
		args.put(ConsumeHistory.COLUMN_DATE, dateFormat.format(date));
		args.put(ConsumeHistory.COLUMN_WATER_MEASUREMENT_UNIT, waterMeasurementUnit);
		args.put(ConsumeHistory.COLUMN_GLASSES_DRANK_DAILY, glassesDrankDaily);

		int historyId = doesConsumeHistoryExist(date);
		if (historyId > 0) {
			//update
			return mDb.update(ConsumeHistory.TABLE_CONSUME_HISTORY, args, ConsumeHistory._ID + " = " + historyId, null);
		}	  else {
			//save
			return mDb.insert(ConsumeHistory.TABLE_CONSUME_HISTORY, null, args);
		}
	}

	public int doesConsumeHistoryExist( java.util.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String strDate = dateFormat.format(date);
		Cursor c = mDb.query(ConsumeHistory.TABLE_CONSUME_HISTORY,CONSUME_HISTORY_CALL_COLUMNS, ConsumeHistory.COLUMN_DATE + " = '" + strDate + "'"  , null, null, null, ConsumeHistory.COLUMN_DATE);

		int historyId = -999;
		try {
			if (c.moveToFirst())
			{
				historyId = c.getInt(0);
			}		
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return historyId;
	}

	public Cursor fetchWaterHistory(int daysInToThePast) {

		if (daysInToThePast == 999) {
			
			//return mDb.query(ConsumeHistory.TABLE_CONSUME_HISTORY,CONSUME_HISTORY_CALL_COLUMNS , ConsumeHistory.COLUMN_DATE + "< date('now', '-" + daysInToThePast + "days ')" , null, null, null, ConsumeHistory.COLUMN_DATE);
			return mDb.query(ConsumeHistory.TABLE_CONSUME_HISTORY,CONSUME_HISTORY_CALL_COLUMNS, null  , null, null, null, ConsumeHistory.COLUMN_DATE);
		
		} else {
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			calendar.add(Calendar.DAY_OF_YEAR, -daysInToThePast);
			java.util.Date pastDate = calendar.getTime();

			String strNow = DATE_FORMATTER.format(now);
			String strPastDate = DATE_FORMATTER.format(pastDate);

			//Log.d("SQL" ,ConsumeHistory.COLUMN_DATE + " BETWEEN '" + strPastDate  + "' AND '" + strNow + "'" );

			//return mDb.query(ConsumeHistory.TABLE_CONSUME_HISTORY,CONSUME_HISTORY_CALL_COLUMNS , ConsumeHistory.COLUMN_DATE + "< date('now', '-" + daysInToThePast + "days ')" , null, null, null, ConsumeHistory.COLUMN_DATE);
			return mDb.query(ConsumeHistory.TABLE_CONSUME_HISTORY,CONSUME_HISTORY_CALL_COLUMNS, "julianday(" + ConsumeHistory.COLUMN_DATE + ")" + " BETWEEN julianday('" + strPastDate  + "') AND julianday('" + strNow + "')"  , null, null, null, ConsumeHistory.COLUMN_DATE +  " DESC");
//					SELECT field_1,field_2,date_field WHERE julianday(date_field) BETWEEN julianday('1998-01-01') and julianday('2008-01-01'); 
		}
		
		}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	public long updateConfiguration(int showStartUpScreen)
	{
		ContentValues args = new ContentValues();
		args.put(CONFIG_SHOW_START_SCREEN_COL, showStartUpScreen);
		return mDb.update(TABLE_CONFIGURATION, args, CONFIG_VISIT_ID_COL + "= 1", null);
	}

	public boolean showStartupScreen()
	{
		boolean showStartupScreen = false;

		Cursor c = getConfiguration();

		try {
			if (c.moveToFirst())
			{
				int showStartup = c.getInt(1);
				if (showStartup == 1)
				{
					showStartupScreen = true;
				}
			}		
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return showStartupScreen;
	}

	/**
	 * Returns the one and only config object
	 * 
	 * @return Cursor
	 */
	public Cursor getConfiguration()
	{
		return mDb.query(TABLE_CONFIGURATION, new String[] {CONFIG_VISIT_ID_COL, CONFIG_SHOW_START_SCREEN_COL }, 
				CONFIG_VISIT_ID_COL + "= 1", null, null, null, null);

	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(CREATE_TABLE_CONFIGURATION);
			db.execSQL(CREATE_INITIAL_CONFIGURATION);
			db.execSQL(CREATE_TABLE_CONSUME_HISTORY);         

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//			Log.w("DATABASE_UPGRADE", "Upgrading database from version " + oldVersion + " to "
//					+ newVersion + ", which will destroy all old data");

			try {
				db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONFIGURATION);
			}catch (Exception e) {
				//do nothing
			}
			try {
				db.execSQL("DROP TABLE IF EXISTS "+ ConsumeHistory.TABLE_CONSUME_HISTORY);
			} catch (Exception e) {
				//do nothing.
			}
			onCreate(db);
		}
	}
}
