package com.sprint22.apps.android.hydration.calculation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sprint22.apps.android.hydration.alarm.AlarmCalculator;
import com.sprint22.apps.android.hydration.dao.WaterDao;
import com.sprint22.apps.android.hydration.dao.WaterFactorEffectsDao;
import com.sprint22.apps.android.hydration.dto.Water;
import com.sprint22.apps.android.hydration.dto.WaterBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;
import com.sprint22.apps.android.hydration.utils.CloseActivityHandler;

public class CalculateWaterTask extends AsyncTask<WaterFactors, Void, String> {

	Context context;
	ProgressDialog waitSpinner;
	CloseActivityHandler mCloseHandler;

	public static final IHydrationFactorCalculatorStrategy[] DEFAULT_HYDRATION_FACTOR_CALCULATORS = {
			new WeightCalculatorStrategy(), new ClimateCalculatorStrategy(),
			new ExerciseCalculatorStrategy(), new IllnessCalculatorStrategy(),
			new PregnancyCalculatorStrategy(), new HealthyEatingCalculatorStrategy() };
	private IHydrationFactorCalculatorStrategy[] hydrationFactorCalculators;

	public CalculateWaterTask(Context context, CloseActivityHandler closeHandler,
			IHydrationFactorCalculatorStrategy[] hydrationFactorCalculators) {
		this.context = context;
		this.mCloseHandler = closeHandler;
		this.hydrationFactorCalculators = hydrationFactorCalculators;
	}

	@Override
	protected String doInBackground(WaterFactors... models) {

		WaterFactors model = models[0];
		Water water = new WaterDao(context).retrieve();
		WaterFactorEffectsBuilder waterFactorEffectsBuilder = WaterFactorEffectsBuilder
				.newWaterFactorEffectsBuilder();
		WaterBuilder waterBuilder = WaterBuilder.newWaterBuilder();
		waterBuilder.startingWith(water);
		double requiredDailyWaterIntake = calculateDailyWaterRequired(model,
				waterFactorEffectsBuilder);
		waterBuilder.withRequiredDailyWaterIntake(requiredDailyWaterIntake);

		// WaterFactors retrievedModel = new
		// WaterFactorsDao(context).retrieve();

		new AlarmCalculator().calculate(context);

		new WaterDao(context).save(water);
		new WaterFactorEffectsDao(context).save(waterFactorEffectsBuilder.build());

		return "";
	}

	private double calculateDailyWaterRequired(WaterFactors model,
			WaterFactorEffectsBuilder waterFactorEffectsBuilder) {
		double requiredDailyWaterIntake = 0.0;
		for (IHydrationFactorCalculatorStrategy calculator : this.hydrationFactorCalculators) {
			if (calculator.shouldApplyWeighting(model)) {
				requiredDailyWaterIntake = calculator.apply(model, waterFactorEffectsBuilder,
						requiredDailyWaterIntake);
			}
		}
		return requiredDailyWaterIntake;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		waitSpinner = ProgressDialog.show(context, "Please Wait ...",
				"Calculating your water requirements ...", true);

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		waitSpinner.cancel();
		mCloseHandler.close();
	}

}