package com.sprint22.apps.android.hydration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sprint22.apps.android.hydration.configuration.ConfigurationPreferences;
import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.database.HydrationDbAdapter;

public class HydromasterMenu extends AbstractActivity {

	private HydrationDbAdapter mHrydationDbAdapter;

	private static final int INFORMATION_ID = 1;
	private static final int HELP_ID = 2;
	private static final int ABOUT_ID = 3;

	private ImageButton mHydrateButton;
	private ImageButton mSummaryButton;
	private ImageButton mFactorsButton;
	private ImageButton mSettingsButton;
	private ImageButton mHistoryButton;
	private ImageButton mInfoButton;

	private SharedPreferences mSharedPreferences = null;

	private TextView mNextAlarmTime;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, INFORMATION_ID, 0, R.string.information_title);
		menu.add(0, HELP_ID, 0, R.string.help_title);
		menu.add(0, ABOUT_ID, 0, R.string.about);
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case INFORMATION_ID:
			startInformationActivity();
			return true;
		case HELP_ID:
			startHelpActivity();
			return true;
		case ABOUT_ID:
			startAboutActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void initialise() {

		setContentView(R.layout.menu);

		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		mNextAlarmTime = (TextView) findViewById(R.id.text_next_alarm_time);
		mFactorsButton = (ImageButton) findViewById(R.id.menu_factors);
		mHistoryButton = (ImageButton) findViewById(R.id.menu_history);
		mHydrateButton = (ImageButton) findViewById(R.id.menu_hydrate);
		mInfoButton = (ImageButton) findViewById(R.id.menu_info);
		mSettingsButton = (ImageButton) findViewById(R.id.menu_settings);
		mSummaryButton = (ImageButton) findViewById(R.id.menu_summary);

		mFactorsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startFactorsActivity();
			}
		});
		mHistoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startHydrationHistoryActivity();
			}
		});
		mHydrateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startHydrationActivity();
			}
		});
		mInfoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startInformationActivity();
			}
		});
		mSettingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startConfigurationActivity();
			}
		});
		mSummaryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startSummaryActivity();
			}
		});

		mHrydationDbAdapter = new HydrationDbAdapter(this);
		mHrydationDbAdapter.open();

		try {
			if (mHrydationDbAdapter.showStartupScreen()) {
				startStartupActivity();
			}
		} finally {
			mHrydationDbAdapter.close();
		}
	}

	private void startSummaryActivity() {
		Intent i = new Intent(this, SummaryActivity.class);
		startActivity(i);
	}

	private void startHydrationActivity() {
		Intent i = new Intent(this, HydromasterHome.class);
		startActivity(i);

	}

	private void startHydrationHistoryActivity() {
		Intent i = new Intent(this, HydrationHistory.class);
		startActivity(i);
	}

	private void startFactorsActivity() {
		Intent i = new Intent(this, HydrationFactors.class);
		startActivity(i);
	}

	private void startStartupActivity() {
		Intent i = new Intent(this, HydrationStartup.class);
		startActivity(i);
	}

	protected void startConfigurationActivity() {
		Intent i = new Intent(this, ConfigurationPreferences.class);
		startActivity(i);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(this);
		}
		boolean isAlarmEnabled = mSharedPreferences.getBoolean(
				SPConstants.KEY_WATER_ALARM_TOGGLE,
				SPConstants.DEFAULT_ALARM_WATER_TOGGLE);
		if (isAlarmEnabled) {
			mNextAlarmTime.setText(mSharedPreferences.getString(
					SPConstants.KEY_ALARM_TEXT, "Alarm is off"));
		} else {
			mNextAlarmTime.setText("Alarm is off");
		}

	}

	@Override
	public int getHelpLayout() {
		return R.layout.help_hydration_menu;
	}

}
