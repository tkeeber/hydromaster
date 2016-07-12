package com.sprint22.apps.android.hydration.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.sprint22.apps.android.hydration.HydrationFunctions;
import com.sprint22.apps.android.hydration.configuration.SPConstants;

/**
 * "Cleans" the shared preferences.
 * 
 * @author Tom
 *
 */
public class SaveWaterConsumedAlarm extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		WakefulIntentService.acquireStaticLock(context);
		context.startService(new Intent(context, SaveWaterConsumedService.class));
	}
	
	/**
	 * 
	 * @author Tom
	 *
	 */
	private class SaveWaterConsumedService extends WakefulIntentService
	{

		public SaveWaterConsumedService(String name) {
			super("SaveWaterConsumedService");
		}
		
		protected void doWakefulWork(Intent intent) {
			SharedPreferences sharedPerferences =  PreferenceManager.getDefaultSharedPreferences(this);
			
			HydrationFunctions hydrationFunctions = new HydrationFunctions();
			hydrationFunctions.saveDailyWaterDetails(this, sharedPerferences);
		}
	}
}