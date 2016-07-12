/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sprint22.apps.android.hydration.configuration;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.TimePickerPreference;

import com.sprint22.apps.android.hydration.HydrationFunctions;
import com.sprint22.apps.android.hydration.R;
import com.sprint22.apps.android.hydration.alarm.AlarmCalculator;

public class ConfigurationPreferences extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	private static final String TAG = "hyrdation";

	private Preference mWeightUnitPreference;
	private Preference mWaterUnitsMeasurement;
	private Preference mWaterAlarmToggle;
	private TimePickerPreference mWaterAlarmStartTime;
	private TimePickerPreference mWaterAlarmEndTime;
	private ListPreference mWaterAlarmManualInterval;
	private Preference mWaterAlarmAutoToggle;
	private Preference mWaterAlarmVibrateToggle;
	private Preference mWaterAlarmSoundToggle;

	private String mWaterAlarmStartTimeSummary;
	private String mWaterAlarmEndTimeSummary;
	private String mWaterAlarmManualIntervalSummary;

	private HydrationFunctions mHydrationFunctions;

	final Handler mHandler = new Handler();

	private String mOldWaterUnit = SPConstants.DEFAULT_WATER_MEASUREMENT;

	boolean changesMade = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHydrationFunctions = new HydrationFunctions();

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		PreferenceManager root = getPreferenceManager();
		mWeightUnitPreference = root.findPreference(SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE);
		mWaterUnitsMeasurement = root.findPreference(SPConstants.KEY_WATER_MEASUREMENT_TYPE);
		mWaterAlarmToggle = root.findPreference(SPConstants.KEY_WATER_ALARM_TOGGLE);
		mWaterAlarmStartTime = (TimePickerPreference) root
				.findPreference(SPConstants.KEY_WATER_ALARM_START_TIME);
		mWaterAlarmEndTime = (TimePickerPreference) root
				.findPreference(SPConstants.KEY_WATER_ALARM_END_TIME);
		mWaterAlarmAutoToggle = root.findPreference(SPConstants.KEY_WATER_ALARM_AUTO_TOGGLE);
		mWaterAlarmManualInterval = (ListPreference) root
				.findPreference(SPConstants.KEY_WATER_ALARM_MANUAL_TIME);
		mWaterAlarmToggle = root.findPreference(SPConstants.KEY_WATER_ALARM_TOGGLE);
		mWaterAlarmVibrateToggle = root.findPreference(SPConstants.KEY_VIBRATE_ALARM_TOGGLE);
		mWaterAlarmSoundToggle = root.findPreference(SPConstants.KEY_SOUND_ALARM_TOGGLE);

		Resources resources = getResources();
		mWaterAlarmEndTimeSummary = resources.getString(R.string.summary_alarm_end_time) + " ";
		mWaterAlarmStartTimeSummary = resources.getString(R.string.summary_alarm_start_time) + " ";
		mWaterAlarmManualIntervalSummary = resources
				.getString(R.string.summary_alarm_manual_interval_time) + " ";

		// mWaterAlarmManualInterval.setEnabled(!(root.getSharedPreferences().getBoolean(SPConstants.KEY_WATER_ALARM_AUTO_TOGGLE,
		// SPConstants.DEFAULT_ALARM_WATER_AUTO_TOGGLE)));
		//
		// boolean isAlarmEnabled =
		// root.getSharedPreferences().getBoolean(SPConstants.KEY_WATER_ALARM_TOGGLE,
		// SPConstants.DEFAULT_ALARM_WATER_TOGGLE);
		//
		// mWaterAlarmManualInterval.setEnabled(isAlarmEnabled);
		// mWaterAlarmAutoToggle.setEnabled(isAlarmEnabled);
		// mWaterAlarmEndTime.setEnabled(isAlarmEnabled);
		// mWaterAlarmStartTime.setEnabled(isAlarmEnabled);

		mWaterUnitsMeasurement.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				String waterUnit = preference.getSharedPreferences().getString(
						SPConstants.KEY_WATER_MEASUREMENT_TYPE,
						SPConstants.DEFAULT_WATER_MEASUREMENT);
				mOldWaterUnit = waterUnit;
				return false;
			}
		});

		OnPreferenceChangeListener preferenceChangeListner = new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {

				boolean isAuto = (Boolean) newValue;
				mWaterAlarmManualInterval.setEnabled(!isAuto);
				return true;
			}
		};

		mWaterAlarmAutoToggle.setOnPreferenceChangeListener(preferenceChangeListner);

		OnPreferenceChangeListener alarmEnabledChangeListner = new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				applyAlarmOptionsStates((Boolean) newValue,
						(Boolean) AlarmPerferences.IS_AUTO_ALARM.get(mWaterAlarmAutoToggle
								.getSharedPreferences()));
				return true;
			}
		};

		mWaterAlarmToggle.setOnPreferenceChangeListener(alarmEnabledChangeListner);
	}

	@Override
	protected void onResume() {
		super.onResume();

		changesMade = false;

		// String wieghtUnits =
		// sharedPreferences.getString(SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE,
		// SPConstants.DEFAULT_WEIGHT_MEASUREMENT);

		applyPerferenceStates();

		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	private void applyPerferenceStates() {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		mWeightUnitPreference.setSummary(sharedPreferences.getString(
				SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE, SPConstants.DEFAULT_WEIGHT_MEASUREMENT));
		mWaterUnitsMeasurement.setSummary(sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT));

		mWaterAlarmStartTime.setSummary(mWaterAlarmStartTimeSummary
				+ mWaterAlarmStartTime.getText());
		mWaterAlarmEndTime.setSummary(mWaterAlarmEndTimeSummary + mWaterAlarmEndTime.getText());
		mWaterAlarmManualInterval.setSummary(mWaterAlarmManualIntervalSummary
				+ mWaterAlarmManualInterval.getEntry());

		boolean isAlarmEnabled = sharedPreferences.getBoolean(SPConstants.KEY_WATER_ALARM_TOGGLE,
				SPConstants.DEFAULT_ALARM_WATER_TOGGLE);
		AlarmPerferences.IS_AUTO_ALARM.get(sharedPreferences);
		applyAlarmOptionsStates(isAlarmEnabled,
				(Boolean) AlarmPerferences.IS_AUTO_ALARM.get(sharedPreferences));
	}

	private void applyAlarmOptionsStates(boolean isAlarmEnabled, boolean isManualAlarm) {

		// here we need to check that the auto alarm does not get re-enabled if
		// the alarm is on.
		if (isAlarmEnabled) {
			mWaterAlarmManualInterval.setEnabled(!isManualAlarm);
		} else {
			mWaterAlarmManualInterval.setEnabled(isAlarmEnabled);
		}
		mWaterAlarmAutoToggle.setEnabled(isAlarmEnabled);
		mWaterAlarmEndTime.setEnabled(isAlarmEnabled);
		mWaterAlarmStartTime.setEnabled(isAlarmEnabled);
		mWaterAlarmSoundToggle.setEnabled(isAlarmEnabled);
		mWaterAlarmVibrateToggle.setEnabled(isAlarmEnabled);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// So currently we recalutor the water stuff here as we might have made
		// changes to the
		// water measurements.
		// Need to look at this as this code is rubbish at the moment.
		if (changesMade) {
			// mHydrationFunctions.performWaterCalculations(ConfigurationPreferences.this);
		}

		// Recreate the alarm in case alarm times have changed
		// Only do this if they have.
		new AlarmCalculator().calculate(this.getApplicationContext());

		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
				this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

		if (key.equals(SPConstants.KEY_WATER_ALARM_START_TIME)) {
			mWaterAlarmStartTime.setSummary(mWaterAlarmStartTimeSummary
					+ mWaterAlarmStartTime.getText());
		} else if (key.equals(SPConstants.KEY_WATER_ALARM_END_TIME)) {
			mWaterAlarmEndTime.setSummary(mWaterAlarmEndTimeSummary + mWaterAlarmEndTime.getText());
		} else if (key.equals(SPConstants.KEY_WATER_ALARM_MANUAL_TIME)) {
			mWaterAlarmManualInterval.setSummary(mWaterAlarmManualIntervalSummary
					+ mWaterAlarmManualInterval.getEntry());// sharedPreferences.getString(SharedPreferenceHelper.KEY_WATER_ALARM_MANUAL_TIME,
															// SharedPreferenceHelper.DEFAULT_WATER_ALARM_MANUAL_INTERVAL));
		} else if (key.equals(SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE)) {
			mWeightUnitPreference.setSummary(sharedPreferences
					.getString(SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE,
							SPConstants.DEFAULT_WEIGHT_MEASUREMENT));
		} else if (key.equals(SPConstants.KEY_WATER_MEASUREMENT_TYPE)) {
			mWaterUnitsMeasurement.setSummary(sharedPreferences.getString(
					SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT));
		}
		changesMade = true;
	}

	private void updateWaterMeasurements() {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String waterMeasurement = sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);

		// need to change all the water measurements to the new water type
		if (mOldWaterUnit.equals(waterMeasurement) == false) {
			// need to update everything.

			long glassesDrankToday = sharedPreferences.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY,
					0);
			double waterDrankToday = glassesDrankToday * 250;
			String waterMeasurementUnit = sharedPreferences.getString(
					SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);
			double waterDrank = mHydrationFunctions.convertFromMlsToUserMeasurement(
					waterDrankToday, waterMeasurementUnit, sharedPreferences);

			Editor editor = sharedPreferences.edit();
			editor.putString(SPConstants.KEY_WATER_DRANK_TODAY, Math.round(waterDrank) + "");
			editor.commit();
		}

	}
}
