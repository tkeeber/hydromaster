package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public interface IHydrationFactorCalculatorStrategy {

	public double apply(WaterFactors model, WaterFactorEffectsBuilder waterFactorEffectsBuilder, double requiredDailyWaterIntake);
	
	public boolean shouldApplyWeighting(WaterFactors model);
}
