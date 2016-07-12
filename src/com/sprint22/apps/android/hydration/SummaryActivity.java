package com.sprint22.apps.android.hydration;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.dao.WaterDao;
import com.sprint22.apps.android.hydration.dao.WaterFactorEffectsDao;
import com.sprint22.apps.android.hydration.dao.WaterFactorsDao;
import com.sprint22.apps.android.hydration.dto.Water;
import com.sprint22.apps.android.hydration.dto.WaterFactorEffects;
import com.sprint22.apps.android.hydration.dto.WaterFactors;
import com.sprint22.apps.android.hydration.view.WaterFactorEffectsRenderer;
import com.sprint22.apps.android.hydration.view.WaterFactorsRenderer;
import com.sprint22.apps.android.hydration.view.WaterRenderer;

public class SummaryActivity extends AbstractActivity {

	private SharedPreferences mSharedPreferences = null;

	TextView mAmountOfWaterTitle = null;

	TextView mAmountOfGlasses = null;
	TextView mAmountOfGlassesDrank = null;
	TextView mWeightFactor = null;
	TextView mExceriseFactor = null;
	TextView mClimateFactor = null;
	TextView mIllnessFactor = null;
	TextView mPregnancyFactor = null;
	TextView mAlcholUnitsFactor = null;
	TextView mHealthyDietFactor = null;

	TextView mWeightEffect = null;
	TextView mExceriseEffect = null;
	TextView mClimateEffect = null;
	TextView mIllnessEffect = null;
	TextView mPregnancyEffect = null;
	TextView mAlcholUnitsEffect = null;
	TextView mHealthyDietEffect = null;

	TextView mTotalEffect = null;

	private HydrationFunctions mHydrationFunctions = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void initialise() {
		setContentView(R.layout.summary);

		mHydrationFunctions = new HydrationFunctions();

		mAmountOfWaterTitle = (TextView) findViewById(R.id.amount_of_water_title);

		mAmountOfGlasses = (TextView) findViewById(R.id.amount_of_glasses);
		mAmountOfGlassesDrank = (TextView) findViewById(R.id.amount_of_glasses_drunk);

		mWeightFactor = (TextView) findViewById(R.id.entered_weight);
		mExceriseFactor = (TextView) findViewById(R.id.entered_excerise);
		mIllnessFactor = (TextView) findViewById(R.id.entered_illness);
		mClimateFactor = (TextView) findViewById(R.id.entered_climate);
		mPregnancyFactor = (TextView) findViewById(R.id.entered_pregnancybrestfeeding);
		mAlcholUnitsFactor = (TextView) findViewById(R.id.entered_alcohol_units);
		mHealthyDietFactor = (TextView) findViewById(R.id.entered_healthy_diet);

		mWeightEffect = (TextView) findViewById(R.id.effect_water_weight);
		mExceriseEffect = (TextView) findViewById(R.id.effect_water_exercise);
		mIllnessEffect = (TextView) findViewById(R.id.effect_water_illness);
		mClimateEffect = (TextView) findViewById(R.id.effect_water_climate);
		mPregnancyEffect = (TextView) findViewById(R.id.effect_water_pregnancyorbrestfeeding);
		mAlcholUnitsEffect = (TextView) findViewById(R.id.effect_water_alchol_utils);
		mHealthyDietEffect = (TextView) findViewById(R.id.effect_water_healthy_diet);

		mTotalEffect = (TextView) findViewById(R.id.total_water);

		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setWaterSummaryAndBreakdown();
	}

	private void setWaterSummaryAndBreakdown() {

		// the summary fields
		Water water = new WaterDao(this.getApplicationContext()).retrieve();
		WaterFactors waterFactors = new WaterFactorsDao(this.getApplicationContext()).retrieve();
		WaterFactorEffects waterFactorsEffect = new WaterFactorEffectsDao(
				this.getApplicationContext()).retrieve();

		WaterRenderer waterRenderer = new WaterRenderer(water);
		WaterFactorEffectsRenderer waterEffectRenderer = new WaterFactorEffectsRenderer(
				waterFactorsEffect, waterRenderer);
		WaterFactorsRenderer summaryRenderer = new WaterFactorsRenderer(waterFactors);

		mAmountOfWaterTitle.setText(getGlassSize() + " glasses to drink ");

		mAmountOfGlasses.setText("" + water.getCalculatedDailyWaterGlasses());
		mAmountOfGlassesDrank.setText(waterRenderer.renderCalculatedDailyWaterGlasses());

		mWeightFactor.setText(summaryRenderer.renderWeightFactor());
		mAlcholUnitsFactor.setText(summaryRenderer.renderAlcholUnitsFactor());
		mClimateFactor.setText(summaryRenderer.renderClimateFactor());
		mExceriseFactor.setText(summaryRenderer.renderExerciseFactor());
		mHealthyDietFactor.setText(summaryRenderer.renderHealthyDietFactor());
		mIllnessFactor.setText(summaryRenderer.renderIllnessFactor());
		mPregnancyFactor.setText(summaryRenderer.renderPregnancyFactor());

		// The break down water effect fields
		mWeightEffect.setText(waterEffectRenderer.renderWeightEffect());
		mExceriseEffect.setText(waterEffectRenderer.renderExerciseEffect());
		mClimateEffect.setText(waterEffectRenderer.renderClimateEffect());
		mAlcholUnitsEffect.setText(waterEffectRenderer.renderAlcholUnitsEffect());
		mHealthyDietEffect.setText(waterEffectRenderer.renderHealthDietEffect());
		mIllnessEffect.setText(waterEffectRenderer.renderIllnessEffect());
		mPregnancyEffect.setText(waterEffectRenderer.renderPregnancyEffect());

		mTotalEffect.setText(waterRenderer.renderRequiredDailyWaterIntake());
	}

	private String getGlassSize() {
		String waterMeasurementUnits = mSharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);
		String glassSize = "" + SPConstants.DEFAULT_GLASS_SIZE_MILLILITERS + "ml glasses of water";
		if (waterMeasurementUnits.equals("l")) {
			glassSize = "1/4 liter";
		} else if (waterMeasurementUnits.equals("pt")) {
			glassSize = "pints";
		} else if (waterMeasurementUnits.equals("oz")) {
			glassSize = "8.5oz";
		} else if (waterMeasurementUnits.equals("ml")) {
			glassSize = "250ml";
		}
		return glassSize;
	}

	@Override
	public int getHelpLayout() {
		return R.layout.help_summary;
	}

}
