package com.sprint22.apps.android.hydration.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sprint22.apps.android.hydration.configuration.SPConstants;

/**
 * "Cleans" the shared preferences.
 * 
 * @author Tom
 *
 */
public class CleanUpAlarm extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		//Log.d("ihydration", "Starting clean up service");
		WakefulIntentService.acquireStaticLock(context);
		context.startService(new Intent(context, CleanUpService.class));
	}
	
	/**
	 * 
	 * @author Tom
	 *
	 */
	private class CleanUpService extends WakefulIntentService
	{

		public CleanUpService(String name) {
			super("CleanUpService");
		}
		
		protected void doWakefulWork(Intent intent) {
			SharedPreferences sharedPerferences =  PreferenceManager.getDefaultSharedPreferences(this);
			
			Editor editor = sharedPerferences.edit();
			editor.putLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0l );
			editor.commit();			
		}
	}
}