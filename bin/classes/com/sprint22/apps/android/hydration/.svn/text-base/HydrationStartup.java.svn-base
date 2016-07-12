package com.sprint22.apps.android.hydration;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.sprint22.apps.android.hydration.alarm.CleanUpAlarm;
import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.database.HydrationDbAdapter;

public class HydrationStartup extends AbstractActivity {

	HydrationDbAdapter mHrydationDbAdapter;
	
	private SharedPreferences mSharedPreferences = null;
	
	private CheckBox mBtnShowAgain;
		
	protected ImageButton mHelpButton;
	protected ImageButton mAboutButton;
	protected ImageButton mCommentButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	protected void initialise() {
		mHrydationDbAdapter = new HydrationDbAdapter(this);
		mHrydationDbAdapter.open();

		setContentView(R.layout.startup_screen);
		
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean isFirstTimeOpen = mSharedPreferences.getBoolean(SPConstants.KEY_FIRST_TIME_OPEN_APP, true);
		if (isFirstTimeOpen) {
			//schedule the save daily alarm
			setUpDefaults();
		}
		
		Button btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				onOKClick();
			}
		});

		mBtnShowAgain = (CheckBox) findViewById(R.id.show_again_button);
		
	}
	
	private void onOKClick() {
		if (mBtnShowAgain.isChecked()) {
			updateConfiguration();	
		}			
		this.finish();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHrydationDbAdapter.close();
		mHrydationDbAdapter = null;
	}
	

	private void updateConfiguration()
	{
		mHrydationDbAdapter.updateConfiguration(0);
	}
	
	private void scheduleCleanupAlarm() {
		AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// We want the alarm to go off 30 seconds from now.
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
		cal.set(Calendar.MINUTE, 0);                 // set minute in hour
		cal.set(Calendar.SECOND, 0);                 // set second in minute
		cal.set(Calendar.MILLISECOND, 0);            // set millis in second

		//reschedule the cleanup alarm
		Intent i=new Intent(this, CleanUpAlarm.class);
		PendingIntent pi=PendingIntent.getBroadcast(this, 0, i, 0);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), (24 * 60 * 60 * 1000), pi);	

	}
	
	private void setUpDefaults()
	{
		//create a new thread to do the water calulation
		Thread t = new Thread() {
			public void run() {
				HydrationFunctions functions = new HydrationFunctions();
				functions.setUpDefaults(HydrationStartup.this);
			}
		};
		t.start();
	}

	@Override
	public int getHelpLayout() {
		return R.layout.help_startup;
	}
}
