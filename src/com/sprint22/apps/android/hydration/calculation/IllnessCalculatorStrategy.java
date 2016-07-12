package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class IllnessCalculatorStrategy implements IHydrationFactorCalculatorStrategy{

	public static final double ILLNESS_FACTOR = 8.0;
	@Override
	public double apply(WaterFactors model, WaterFactorEffectsBuilder waterFactorEffectsBuilder, double requiredDailyWaterIntake) {
		waterFactorEffectsBuilder.withIllnessEffect(Double.valueOf(ILLNESS_FACTOR));
		return requiredDailyWaterIntake + 8;
	}

	@Override
	public boolean shouldApplyWeighting(WaterFactors model) {
		return model.isIll();
	}

}
