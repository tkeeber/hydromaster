package com.sprint22.apps.android.hydration.alarm;

import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sprint22.apps.android.hydration.HydrationFunctions;
import com.sprint22.apps.android.hydration.HydromasterHome;
import com.sprint22.apps.android.hydration.R;
import com.sprint22.apps.android.hydration.configuration.SPConstants;

public class HydrationAlarmReceiver extends BroadcastReceiver {

	HydrationFunctions functions = new HydrationFunctions();
	private SharedPreferences mSharedPreferences;

	@Override
	public void onReceive(Context context, Intent arg1) {

		// Log.d("alarm", "Water alarm started");
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

		rescheduleAlarm(context);
		createNotification(context);

	}

	private void rescheduleAlarm(Context context) {
		new AlarmCalculator().calculate(context);
	}

	private void createNotification(Context context) {
		// Raise the notification so that user can check the details
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		int icon = R.drawable.icon;
		CharSequence tickerText = context.getResources()
				.getString(R.string.alarm_notification_text);
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);

		CharSequence contentTitle = context.getResources().getString(
				R.string.alarm_notification_title);
		CharSequence contentText = context.getResources().getString(
				R.string.alarm_notification_text);

		// The PendingIntent to launch our activity if the user selects this
		// notification
		Intent notificationIntent = new Intent(context, HydromasterHome.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

		Boolean isVibrateAlarm = mSharedPreferences.getBoolean(
				SPConstants.KEY_VIBRATE_ALARM_TOGGLE, false);
		Boolean isSoundAlarm = mSharedPreferences.getBoolean(SPConstants.KEY_SOUND_ALARM_TOGGLE,
				true);

		if (isVibrateAlarm) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		if (isSoundAlarm) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		}

		notification.defaults |= Notification.DEFAULT_LIGHTS;

		// Instead of 1234 or any other number, use below expression to have
		// unique notifications
		// Integer.parseInt(intent.getData().getSchemeSpecificPart())\\

		Random r = new Random();
		mNotificationManager.notify(r.nextInt(), notification);
	}
}
