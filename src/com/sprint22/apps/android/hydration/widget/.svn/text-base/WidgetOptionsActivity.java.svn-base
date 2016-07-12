package com.sprint22.apps.android.hydration.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sprint22.apps.android.hydration.AbstractActivity;
import com.sprint22.apps.android.hydration.HydrationFunctions;
import com.sprint22.apps.android.hydration.R;

public class WidgetOptionsActivity extends Activity {

	private SharedPreferences mSharedPreferences = null;
	private HydrationFunctions mHydrationFunctions;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initialise();
	}
	
	protected void initialise() {
		setContentView(R.layout.widget_options);
		
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		mHydrationFunctions = new HydrationFunctions();
		
		Button closeButton = (Button) findViewById(R.id.close);		
		closeButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finishThis();
			}
		});
		
		Button resetButton = (Button) findViewById(R.id.reset);
		resetButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				mHydrationFunctions.resetDrankWater(WidgetOptionsActivity.this);
				setWaterRequirementsRepresentation();
				finishThis();
			}
		});
		
		Button plusButton  = (Button) findViewById(R.id.incrementDrankCount);
		plusButton.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				mHydrationFunctions.incrementDrankWater(getApplicationContext());
				setWaterRequirementsRepresentation();
				finishThis();
			}
		});
	}
	
	
	private void finishThis() {
		this.finish();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mSharedPreferences == null)
		{
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		}
	}


	private void setWaterRequirementsRepresentation() {
		
		ComponentName widgetComponentName = new ComponentName(getApplicationContext(), iHydrationWidgetProvider.class);
        
		//update the widget - if any.
		AppWidgetManager gm = AppWidgetManager.getInstance(this);		
		int[] appWidgetIds = gm.getAppWidgetIds(widgetComponentName);
		for (int i = 0; i < appWidgetIds.length; i++) {
			iHydrationWidgetProvider.updateAppWidget(this, gm, appWidgetIds[i]);
		}
	}
	
}
