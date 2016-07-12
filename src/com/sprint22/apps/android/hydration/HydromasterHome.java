package com.sprint22.apps.android.hydration;

import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sprint22.apps.android.hydration.configuration.ConfigurationPreferences;
import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.dao.WaterDao;
import com.sprint22.apps.android.hydration.dto.Water;
import com.sprint22.apps.android.hydration.widget.iHydrationWidgetProvider;

public class HydromasterHome extends AbstractActivity {

	private static final String TAG = "hydromaster";

	private SharedPreferences mSharedPreferences = null;

	private TextView mLastDrinkTime;
	private LinearLayout mWaterLevel;
	private ImageView mWaterLevelImage;

	private TextView mAmountOfGlassesDrank;
	private TextView mAmountOfGlasses;
	private TextView mAmountDrinkInfo;
	private TextView mAmountDrankInfo;

	private TextView mWaterLevelDescription;
	private TextView mWaterTargetReached;

	private TextView mTargetWaterAmount;
	private ImageButton mPlusButton;

	Animation.AnimationListener listener;

	private double mPaddingPerGlass;

	final Runnable mUpdateWaterRequirements = new Runnable() {
		public void run() {
			setWaterRequirementsRepresentation();
		}
	};

	// private TextView mNextWaterDrinkTime = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void initialise() {

		setContentView(R.layout.main);

		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		mLastDrinkTime = (TextView) findViewById(R.id.text_last_drink_time);
		mWaterLevel = (LinearLayout) findViewById(R.id.water_level);
		mWaterLevelImage = (ImageView) findViewById(R.id.water_level_image);

		mAmountOfGlasses = (TextView) findViewById(R.id.amount_of_glasses);
		mAmountOfGlassesDrank = (TextView) findViewById(R.id.amount_of_glasses_drunk);
		mAmountDrinkInfo = (TextView) findViewById(R.id.drink_amount);
		mAmountDrankInfo = (TextView) findViewById(R.id.drank_amount);

		mTargetWaterAmount = (TextView) findViewById(R.id.text_target_water);

		mWaterLevelDescription = (TextView) findViewById(R.id.text_water_drank_level);
		mWaterTargetReached = (TextView) findViewById(R.id.text_water_target_reached);

		long lastDrinkTime = mSharedPreferences.getLong(SPConstants.KEY_LAST_DRINK_TIME,
				SPConstants.DEFAULT_LAST_DRINK_TIME);
		refreshLastDrinkTime(lastDrinkTime);

		Button resetButton = (Button) findViewById(R.id.reset);
		resetButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mHydrationFunctions.resetDrankWater(getApplicationContext());
				setWaterRequirementsRepresentation();
				resetWaterLevel();
			}
		});

		mPlusButton = (ImageButton) findViewById(R.id.button_plus);
		mPlusButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				plusButtonClicked();

			}
		});

		Button saveButton = (Button) findViewById(R.id.button_save);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				saveDailyWaterDetails();
			}
		});

	}

	private void plusButtonClicked() {

		mHydrationFunctions.incrementDrankWater(getApplicationContext());
		setWaterRequirementsRepresentation();
		refreshLastDrinkTime(System.currentTimeMillis());

		adjustWaterLevelImage();
	}

	private void adjustWaterLevelImage() {

		long totalWater = mSharedPreferences.getLong(SPConstants.KEY_WATER_MILLS_A_DAY,
				SPConstants.DEFAULT_WATER_A_DAY);
		long waterGlassesDrunk = mSharedPreferences
				.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0l);
		long waterDrankToday = mSharedPreferences.getLong(SPConstants.KEY_WATER_DRANK_TODAY, 0l);
		long waterGlassesToDrink = mSharedPreferences.getLong(SPConstants.KEY_WATER_GLASSES_A_DAY,
				0l);

		boolean addPaddingToImage = true;

		if (waterGlassesDrunk > 0) {
			mWaterLevelImage.setVisibility(View.VISIBLE);
		} else {
			mWaterLevelImage.setVisibility(View.INVISIBLE);
		}

		if (waterGlassesDrunk == waterGlassesToDrink) {
			// mPlusButton.setBackgroundResource(R.drawable.fill_water_glass);
			// mPlusButton.setImageDrawable(getResources().getDrawable(
			// R.drawable.fill_water_glass));
			// mWaterComplete.setVisibility(View.VISIBLE);
			mWaterTargetReached.setVisibility(View.VISIBLE);
		} else if (waterGlassesToDrink == 0) {
			// just make sure add padding to image is still true.
		} else if (waterGlassesDrunk > waterGlassesToDrink) {
			mWaterTargetReached.setVisibility(View.VISIBLE);
			addPaddingToImage = false;
		}

		int plusButtonHeight = mPlusButton.getDrawable().getMinimumHeight() - 50;

		int possiblePadding = mWaterLevel.getPaddingBottom() + (int) Math.round(mPaddingPerGlass);
		// long water = totalWater / waterDrankToday;

		// make sure we don't go above the image height
		if (possiblePadding > plusButtonHeight) {
			addPaddingToImage = false;
		}

		// always update the water drank text.
		mWaterLevelDescription.setText(mHydrationFunctions.getDrankWaterString(mSharedPreferences));

		// only add padding if the total hasn't been reached
		if (addPaddingToImage) {
			int bottomPadding = mWaterLevel.getPaddingBottom() + (int) Math.round(mPaddingPerGlass);

			determineWaterLevelImage(waterGlassesDrunk, waterGlassesToDrink);

			mWaterLevel.setPadding(0, 0, 0, bottomPadding);
			mWaterLevelDescription.setPadding(0, 0, 0, bottomPadding);
		}

		// Log.d("TAG",
		// "padding after calculation is "
		// + mWaterLevel.getPaddingBottom());

	}

	private void initWaterLevel() {
		// mSharedPreferences.get

		long totalWater = mSharedPreferences.getLong(SPConstants.KEY_WATER_MILLS_A_DAY,
				SPConstants.DEFAULT_WATER_A_DAY);
		long waterGlassesDrunk = mSharedPreferences
				.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0l);
		long waterDrankToday = mSharedPreferences.getLong(SPConstants.KEY_WATER_DRANK_TODAY, 0l);

		long waterGlassesToDrink = mSharedPreferences.getLong(SPConstants.KEY_WATER_GLASSES_A_DAY,
				0l);

		// get the total water to be drank
		// get the water already drank
		// get the amount size of the water image
		// work out how much padding should be applied to each glass drunk.
		// Work out how many 'glasses of water should be drank'
		// scale for display density
		// set bottom padding.
		// int plusButtonHeight = mPlusButton.getDrawable().getIntrinsicHeight()
		// - 0;

		if (waterGlassesDrunk > 0) {
			mWaterLevelImage.setVisibility(View.VISIBLE);
		} else {
			mWaterLevelImage.setVisibility(View.INVISIBLE);
		}

		if (waterDrankToday > 0) {
			double paddingToBeApplied = 0.0;
			if (waterGlassesDrunk >= waterGlassesToDrink) {
				mWaterTargetReached.setVisibility(View.VISIBLE);
				// long water = totalWater / waterDrankToday;
				paddingToBeApplied = mPaddingPerGlass * waterGlassesToDrink;
			} else {

				int plusButtonHeight = mPlusButton.getDrawable().getMinimumHeight() - 50;

				// long water = totalWater / waterDrankToday;
				paddingToBeApplied = mPaddingPerGlass * waterGlassesDrunk;

				// make sure we don't go above the image height
				if (paddingToBeApplied > plusButtonHeight) {
					paddingToBeApplied = plusButtonHeight;
				}
			}

			determineWaterLevelImage(waterGlassesDrunk, waterGlassesToDrink);

			// Log.d("TAG", "On resume bottom padding applied is "
			// + paddingToBeApplied);

			mWaterLevel.setPadding(0, 0, 0, (int) paddingToBeApplied);
			mWaterLevelDescription.setPadding(0, 0, 0, (int) paddingToBeApplied);
			mWaterLevelDescription.setText(mHydrationFunctions
					.getDrankWaterString(mSharedPreferences));

		} else {
			resetWaterLevel();
		}

	}

	private void determineWaterLevelImage(long waterGlassesDrunk, long glassToDrink) {

		if (waterGlassesDrunk > 15) {
			waterGlassesDrunk = glassToDrink;
		}

		switch ((int) waterGlassesDrunk) {
		case 0:
			return;
		case 1: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_1));
			return;
		}
		case 2: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_2));
			return;
		}
		case 3: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_3));
			return;
		}
		case 4: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_4));
			return;
		}
		case 5: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_5));
			return;
		}
		case 6: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_6));
			return;
		}
		case 7: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_7));
			return;
		}
		case 8: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		case 9: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		case 10: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		case 11: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		case 12: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		case 13: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		case 14: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		case 15: {
			mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_8));
			return;
		}
		}

	}

	private void resetWaterLevel() {
		mWaterLevel.setPadding(0, 0, 0, 0);
		mWaterLevelImage.setImageDrawable(getResources().getDrawable(R.drawable.water_level_1));

		mWaterTargetReached.setVisibility(View.INVISIBLE);
		mWaterLevelDescription.setPadding(0, 0, 0, 0);
		mWaterLevelDescription.setText("0 ml");
		mWaterLevelImage.setVisibility(View.INVISIBLE);
	}

	/**
	 * 
	 * @return double
	 */
	private double calPaddingPerGlass() {
		Water water = new WaterDao(this.getApplicationContext()).retrieve();

		double waterGlassesToDrink = water.getCalculatedDailyWaterGlasses();

		int plusButtonHeight = mPlusButton.getDrawable().getIntrinsicHeight() - 50;
		final float scale = getResources().getDisplayMetrics().density;

		double paddingToBeApplied = 0;
		if (waterGlassesToDrink == 0) {
			paddingToBeApplied = plusButtonHeight;
		} else {
			paddingToBeApplied = plusButtonHeight / waterGlassesToDrink;
			;
		}

		// return (int) (paddingToBeApplied * scale + 0.5f);
		return (double) (paddingToBeApplied);

	}

	private void refreshLastDrinkTime(long lastDrinkTime) {

		if (lastDrinkTime == 0) {
			mLastDrinkTime.setText("No last drink time - Time to have a glass of water!");
		} else {
			Calendar currentTime = Calendar.getInstance();

			long difference = currentTime.getTimeInMillis() - lastDrinkTime;

			int seconds = (int) (difference / 1000) % 60;
			int minutes = (int) ((difference / (1000 * 60)) % 60);
			int hours = (int) ((difference / (1000 * 60 * 60)) % 24);

			// Log.d(TAG, "difference is " + minutes + " minutes");
			// Log.d(TAG, "difference is " + minutes / 60 + " hours");
			if (hours >= 1) {
				// we have hours
				mLastDrinkTime.setText("Last drink was " + hours + " hours and " + minutes
						+ " minutes ago");
			} else if (minutes > 1) {
				// we have minutes - but less then one hour
				mLastDrinkTime.setText("Last drink was " + minutes + " mins ago");
			} else if (minutes < 0 && seconds > 30) {
				mLastDrinkTime.setText("Last drink was less than a minute ago");
			} else if (seconds < 30) {
				mLastDrinkTime.setText("Last drink was a few moments ago");
			} else {
				mLastDrinkTime.setText("Time for a glass of water!");
			}
		}
	}

	private void saveDailyWaterDetails() {

		mHydrationFunctions.saveDailyWaterDetails(this, mSharedPreferences);

		Toast.makeText(this, "Daily water total saved", 2000).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mSharedPreferences == null) {
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		}

		mPaddingPerGlass = calPaddingPerGlass();

		long lastDrinkTime = mSharedPreferences.getLong(SPConstants.KEY_LAST_DRINK_TIME,
				SPConstants.DEFAULT_LAST_DRINK_TIME);
		refreshLastDrinkTime(lastDrinkTime);

		mTargetWaterAmount.setText(mHydrationFunctions.getTotalWaterString(mSharedPreferences)
				.toString());

		initWaterLevel();

		mUpdateWaterRequirements.run();
		// setWaterRequirementsRepresentation();
	}

	private void setWaterRequirementsRepresentation() {

		// update the widget - if any.
		try {
			AppWidgetManager gm = AppWidgetManager.getInstance(this);
			iHydrationWidgetProvider.updateAppWidget(this, gm, 0);
		} catch (Exception e) {
			// Log.d(TAG, "exception", e);
		}

		long glassesDrankDaily = mSharedPreferences.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0);
		float glassesADay = mSharedPreferences.getFloat(SPConstants.KEY_WATER_GLASSES_A_DAY,
				SPConstants.DEFAULT_GLASSES_A_DAY);
		String totalWater = mHydrationFunctions.getTotalWaterString(mSharedPreferences).toString();
		String waterDrank = mHydrationFunctions.getDrankWaterString(mSharedPreferences);

		mAmountOfGlasses.setText("" + glassesADay);
		mAmountOfGlassesDrank.setText(glassesDrankDaily + "");
		mAmountDrinkInfo.setText("(" + totalWater + ")");
		mAmountDrankInfo.setText("(" + waterDrank + ")");
	}

	protected void startConfigurationActivity() {
		Intent i = new Intent(this, ConfigurationPreferences.class);
		startActivity(i);
	}

	@Override
	public int getHelpLayout() {
		return R.layout.help_hydrate;
	}
}
