package com.sprint22.apps.android.hydration.alarm;

import java.util.Calendar;

import android.app.AlarmManager;

import com.sprint22.apps.android.hydration.dto.WaterAlarmBuilder;

public class ManualAlarmCalculator {

	public Calendar calculateNextAlarmTime(int timeIntervalHours,
			WaterAlarmBuilder waterAlarmBuilder, Calendar startTime, Calendar endTime) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.HOUR_OF_DAY, timeIntervalHours);

		// manual alarms at the beginning of each hour.
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// Log.d(TAG, "Adding following to calendar: " + timeIntervalHours);

		long interval = AlarmManager.INTERVAL_HOUR * timeIntervalHours;
		// store the interval
		waterAlarmBuilder.withAlarmInterval(interval);

		// waterData.alarmInterval = interval;
		// waterData.textAlarmTimeString = createNextAlarm(context, calendar,
		// interval);
		return calendar;
	}
}
