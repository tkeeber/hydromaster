package com.sprint22.apps.android.hydration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.sprint22.apps.android.hydration.configuration.ConfigurationPreferences;
import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.help.HelpActivity;
import com.sprint22.apps.android.hydration.information.InformationMain;

public abstract class AbstractActivity extends TrackedActivity {

	protected HydrationFunctions mHydrationFunctions = null;

	protected ImageButton mHelpButton;
	protected ImageButton mAboutButton;
	protected ImageButton mCommentButton;
	
	protected GoogleAnalyticsTracker tracker;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// Hide the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.mHydrationFunctions = new HydrationFunctions();
		tracker = GoogleAnalyticsTracker.getInstance();
		initialise();
		initAbstract();
	}
	
	protected abstract void initialise();

	protected void initAbstract() {		
		mHelpButton = (ImageButton) findViewById(R.id.image_help);
		mAboutButton = (ImageButton) findViewById(R.id.image_id);
		mCommentButton = (ImageButton) findViewById(R.id.image_comment);
		
		mHelpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startHelpActivity();
			}
		});
		mAboutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startAboutActivity();
			}
		});
		mCommentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startMarketComments();
			}
		});
	}

	private void startMarketComments() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(SPConstants.MARKET_URL));
		startActivity(i);

	}

	public AbstractActivity() {
		this.mHydrationFunctions = new HydrationFunctions();
	}
	
	protected void startInformationActivity()
	{
		Intent i = new Intent(this, InformationMain.class);
		startActivity(i);
	}

	protected void startHelpActivity()
	{
		Intent i = new Intent(this, HelpActivity.class);
		i.putExtra("helpLayoutKey", getHelpLayout());
		startActivity(i);
	}
	

	protected void startConfigurationActivity()
	{
		Intent i = new Intent(this, ConfigurationPreferences.class);
		startActivity(i);
	}

	protected void startAboutActivity()
	{
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
	
	public abstract int getHelpLayout();
}
