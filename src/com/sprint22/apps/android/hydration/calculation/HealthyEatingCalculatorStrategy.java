package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class HealthyEatingCalculatorStrategy implements IHydrationFactorCalculatorStrategy {

	public static final double HEALTH_DIET_FACTOR = .20;
	
	public double apply(WaterFactors model, WaterFactorEffectsBuilder waterFactorEffectsBuilder, double requiredDailyWaterIntake) {
		
//		String waterEffect = "-";
		return requiredDailyWaterIntake * HEALTH_DIET_FACTOR;
//			waterRequirement = waterRequirement *.80;
//			waterEffectDouble = convertFromMlsToUserMeasurement(waterEffectDouble,waterMeasurementUnit, sharedPreferences);
//			waterEffectDouble = Math.round(waterEffectDouble);
//			waterEffect = "-" + waterEffectDouble + waterMeasurementUnit;

//		waterData.effectHealthyDiet = waterEffect;

		//		{
//		return waterRequirement;
	}
	
	public boolean shouldApplyWeighting(WaterFactors model) {
		return model.hasHealtDiet();
	}
}
