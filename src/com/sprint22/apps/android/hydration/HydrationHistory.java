package com.sprint22.apps.android.hydration;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sprint22.apps.android.hydration.configuration.ConfigurationPreferences;
import com.sprint22.apps.android.hydration.database.HydrationDbAdapter;
import com.sprint22.apps.android.hydration.database.HydrationDbAdapter.ConsumeHistory;
import com.sprint22.apps.android.hydration.help.HelpActivity;
import com.sprint22.apps.android.hydration.information.InformationMain;

public class HydrationHistory extends ListActivity {

	private static final int DEFAULT_CONSUME_HISTORY_DAYS = 7;

	private HydrationDbAdapter mHydrationDbAdapter;

	private SharedPreferences mSharedPreferences = null;

	private TextView mHistoryKey;
	private Spinner mSpinnerFilter;
	private String[] mFilterOptions;
	
	protected ImageButton mHelpButton;
	protected ImageButton mAboutButton;
	protected ImageButton mCommentButton;

	static final int SELECT_FILTER = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.hydration_history);

		mHydrationDbAdapter = new HydrationDbAdapter(this);
		mHydrationDbAdapter.open();

		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		mHistoryKey = (TextView) findViewById(R.id.text_history_key);

		mSpinnerFilter = (Spinner) findViewById(R.id.button_change_view);

		mFilterOptions = getResources().getStringArray(
				R.array.entriesvalues_list_filter_options);

		mSpinnerFilter.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				filterHistory();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// turn to an int
				loadData(mHydrationDbAdapter.fetchWaterHistory(99999999));
			}

		});

		mHelpButton = (ImageButton) findViewById(R.id.image_help);
		mAboutButton = (ImageButton) findViewById(R.id.image_id);
		mCommentButton = (ImageButton) findViewById(R.id.image_comment);

		mHelpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startHelpActivity();
			}
		});
		mAboutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startAboutActivity();
			}
		});
		mCommentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startMarketComments();
			}
		});
	}

	private void startMarketComments() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri
				.parse("market://details?id=com.tek.apps.android.stadiumguide"));
		startActivity(i);

	}

	protected void startInformationActivity() {
		Intent i = new Intent(this, InformationMain.class);
		startActivity(i);
	}

	protected void startHelpActivity() {
		Intent i = new Intent(this, HelpActivity.class);
		i.putExtra("helpLayoutKey", R.layout.help_history);
		startActivity(i);
	}

	protected void startConfigurationActivity() {
		Intent i = new Intent(this, ConfigurationPreferences.class);
		startActivity(i);
	}

	protected void startAboutActivity() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	private void filterHistory() {
		String strFilterDays = mFilterOptions[(int) mSpinnerFilter
				.getSelectedItemId()];
		// turn to an int
		int filterDays = Integer.valueOf(strFilterDays);
		loadData(mHydrationDbAdapter.fetchWaterHistory(filterDays));
	}

	private void loadData(Cursor cursor) {

		startManagingCursor(cursor);
		String[] from = { ConsumeHistory.COLUMN_TOTAL_CONSUMED_MILS,
				ConsumeHistory.COLUMN_TOTAL_CONSUMED_MILS,
				ConsumeHistory.COLUMN_PREDICATED_CONSUMED_MILS,
				ConsumeHistory.COLUMN_DATE,
				ConsumeHistory.COLUMN_WATER_MEASUREMENT_UNIT };
		int[] to = { R.id.consume_amount_layout, R.id.comsumed_amount,
				R.id.predicated_daily_total, R.id.consumed_date,
				R.id.water_measurement };
		ConsumeHistoryListAdapter listAdapter = new ConsumeHistoryListAdapter(
				this, R.layout.hydration_history_row, cursor, from, to);

		setListAdapter(listAdapter);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(this);
		}

//		mWaterMeasurement = mSharedPreferences.getString(
//				SPConstants.KEY_WATER_MEASUREMENT_TYPE,
//				SPConstants.DEFAULT_WATER_MEASUREMENT);
		// mHydrationFunctions.getGlassSizeForWaterMeasurement(mWaterMeasurement);

		filterHistory();

		if (mHistoryKey != null) {
			mHistoryKey.setText("Indicates number of glasses drank");
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mHydrationDbAdapter != null) {
			mHydrationDbAdapter.close();
			mHydrationDbAdapter = null;
		}
	}

	private class ConsumeHistoryListAdapter extends SimpleCursorAdapter {

		public ConsumeHistoryListAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to);
			setViewBinder(new ConsumeHistoryViewBinder());
		}
	}

	public class ConsumeHistoryViewBinder implements ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

			if (view instanceof TableLayout) {

				TableLayout layout = (TableLayout) view;

				long glassesDrank = cursor.getLong(5);

				TableRow row = new TableRow(getApplicationContext());
				TableRow row2 = new TableRow(getApplicationContext());
				for (long i = 0; i < glassesDrank; i++) {

					ImageView consumeImage = new ImageView(
							getApplicationContext());
					consumeImage.setBackgroundResource(R.drawable.consume_icon);

					if (i < 10) {
						row.addView(consumeImage);
					} else {
						row2.addView(consumeImage);
					}
				}

				layout.addView(row);
				if (row2.getChildCount() > 0) {
					layout.addView(row2);
				}

			} else {
				return false;
			}

			return true;
		}

	}
}
