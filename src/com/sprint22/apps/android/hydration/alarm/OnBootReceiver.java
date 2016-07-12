package com.sprint22.apps.android.hydration.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sprint22.apps.android.hydration.HydrationFunctions;
import com.sprint22.apps.android.hydration.configuration.SPConstants;

/*
 *	Listens for the phone being booted. 
 *
 *  Recreates the CleanupAlarm and the water alarm.
 */
public class OnBootReceiver extends BroadcastReceiver {

	private SharedPreferences mSharedPreferences;

	HydrationFunctions mFunctions = new HydrationFunctions();

	@Override
	public void onReceive(Context context, Intent intent) {

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);

		// reschedule the cleanup alarm
		Intent i = new Intent(context, CleanUpAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				(24 * 60 * 60 * 1000), pi);

		mFunctions.setSaveAlarm(context);

		// Log.d("alarm", "Water alarm started");
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

		boolean isAlarmOn = mSharedPreferences.getBoolean(SPConstants.KEY_WATER_ALARM_TOGGLE,
				SPConstants.DEFAULT_ALARM_WATER_TOGGLE);
		if (isAlarmOn) {
			rescheduleAlarm(context);
		}
	}

	private void rescheduleAlarm(Context context) {

		new AlarmCalculator().calculate(context);
	}
}