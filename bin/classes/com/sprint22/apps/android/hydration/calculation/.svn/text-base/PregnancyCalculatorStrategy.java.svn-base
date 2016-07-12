package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class PregnancyCalculatorStrategy implements IHydrationFactorCalculatorStrategy{

	public static final double PREGNANCY_FACTOR = 16;
	
	@Override
	public double apply(WaterFactors model,WaterFactorEffectsBuilder waterFactorEffectsBuilder,  double requiredDailyWaterIntake) {
		waterFactorEffectsBuilder.withPregnancyEffect(PREGNANCY_FACTOR);
		return  requiredDailyWaterIntake + PREGNANCY_FACTOR;
	}

	@Override
	public boolean shouldApplyWeighting(WaterFactors model) {
		return model.isPregnant();
	}

}
