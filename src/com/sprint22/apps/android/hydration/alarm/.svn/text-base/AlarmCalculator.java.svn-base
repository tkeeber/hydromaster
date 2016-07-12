package com.sprint22.apps.android.hydration.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

import com.sprint22.apps.android.hydration.configuration.AlarmPerferences;
import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.dao.WaterAlarmDao;
import com.sprint22.apps.android.hydration.dao.WaterDao;
import com.sprint22.apps.android.hydration.dto.Water;
import com.sprint22.apps.android.hydration.dto.WaterAlarmBuilder;
import com.sprint22.apps.android.hydration.utils.DateUtils;

public class AlarmCalculator {

	public void calculate(Context context) {

		SharedPreferences sharedPerferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean isAlarmToBeSet = (Boolean) AlarmPerferences.IS_ALARM_ENABLED.get(sharedPerferences);

		removeWaterReminderAlarms(context);

		WaterAlarmBuilder waterAlarmBuilder = WaterAlarmBuilder.newWaterAlarmBuilder();

		if (isAlarmToBeSet) {
			createAlarm(context, sharedPerferences, waterAlarmBuilder);
		}
	}

	private void createAlarm(Context context, SharedPreferences sharedPerferences,
			WaterAlarmBuilder waterAlarmBuilder) {
		// set an alarm
		Water water = new WaterDao(context).retrieve();

		boolean isAutoAlarm = (Boolean) AlarmPerferences.IS_AUTO_ALARM.get(sharedPerferences);
		String strEndTime = (String) AlarmPerferences.ALARM_END_TIME.get(sharedPerferences);
		String strStartTime = (String) AlarmPerferences.ALARM_START_TIME.get(sharedPerferences);

		Calendar startTime = DateUtils.getCalendarWithTimeSet(strStartTime);
		Calendar endTime = DateUtils.getCalendarWithTimeSet(strEndTime);

		if (isAutoAlarm) {
			createAutoAlarm(context, waterAlarmBuilder, water, startTime, endTime);
		} else {
			createManualAlarm(context, sharedPerferences, waterAlarmBuilder, startTime, endTime);
		}

		// save
		new WaterAlarmDao(context).save(waterAlarmBuilder.build());
	}

	private void createManualAlarm(Context context, SharedPreferences sharedPerferences,
			WaterAlarmBuilder waterAlarmBuilder, Calendar startTime, Calendar endTime) {
		String timeIntervalHoursString = sharedPerferences.getString(
				SPConstants.KEY_ALARM_INTERVAL, SPConstants.DEFAULT_ALARM_INTERVAL);
		// turn the string to an int
		int timeIntervalHours = Integer.valueOf(timeIntervalHoursString);

		ManualAlarmCalculator manualAlarmCalculator = new ManualAlarmCalculator();
		Calendar manualCalendar = manualAlarmCalculator.calculateNextAlarmTime(timeIntervalHours,
				waterAlarmBuilder, startTime, endTime);
		createNextAlarm(context, manualCalendar, waterAlarmBuilder.build().getAlarmInterval());
	}

	private void createAutoAlarm(Context context, WaterAlarmBuilder waterAlarmBuilder, Water water,
			Calendar startTime, Calendar endTime) {
		AutoAlarmCalculator autoAlarmCalculator = new AutoAlarmCalculator();
		Calendar alarmCalendar = autoAlarmCalculator.calculateNextAlarmTime(water,
				waterAlarmBuilder, startTime, endTime);
		createNextAlarm(context, alarmCalendar, waterAlarmBuilder.build().getAlarmInterval());
	}

	public String createNextAlarm(Context context, Calendar wantedAlarCal, long interval) {

		SharedPreferences sharedPerferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		// whats our end time.
		String endTime = (String) AlarmPerferences.ALARM_END_TIME.get(sharedPerferences);

		Calendar endTimeCal = DateUtils.getCalendarWithTimeSet(endTime);

		// reschedule the water alarm
		Intent i = new Intent(context, HydrationAlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// if the alarm is after the end time then schedule for next day
		if (wantedAlarCal.before(endTimeCal)) {
			alarmManager.set(AlarmManager.RTC_WAKEUP, wantedAlarCal.getTimeInMillis(), pi);
		} else {
			// schedule for next day.
			String strStartTime = (String) AlarmPerferences.ALARM_START_TIME.get(sharedPerferences);
			wantedAlarCal = DateUtils.getCalendarWithTimeSet(strStartTime);
			wantedAlarCal.add(Calendar.DAY_OF_YEAR, 1);
			alarmManager.set(AlarmManager.RTC_WAKEUP, wantedAlarCal.getTimeInMillis(), pi);

		}
		// water1 textAlarmTimeString ="Next alarm will be at + " +
		// DateFormat.format("MM/dd/yy h:mmaa", wantedAlarCal.getTime());
		// Log.d(TAG, "Alarm set + " + DateFormat.format("MM/dd/yy h:mmaa",
		// wantedAlarCal.getTime()));
		return "Next alarm will be at " + DateFormat.format("h:mmaa", wantedAlarCal.getTime());
	}

	private void removeWaterReminderAlarms(Context context) {
		// remove an existing alarms.
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, HydrationAlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		alarmManager.cancel(pi);
	}

}
