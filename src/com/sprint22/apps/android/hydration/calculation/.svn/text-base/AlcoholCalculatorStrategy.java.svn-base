package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class AlcoholCalculatorStrategy implements IHydrationFactorCalculatorStrategy{

	public static final double ALCOHOL_FACTOR = 4.0;
	
	@Override
	public double apply(WaterFactors model, WaterFactorEffectsBuilder waterFactorEffectsBuilder,  double requiredDailyWaterIntake) {
		if (model.getAlcohol() != null) {
			Double alcholUnitsEffect = model.getAlcohol() * ALCOHOL_FACTOR;
			waterFactorEffectsBuilder.withAlcholUnitsEffect(alcholUnitsEffect);
			return requiredDailyWaterIntake + alcholUnitsEffect;
		}
		return requiredDailyWaterIntake;
	}

	@Override
	public boolean shouldApplyWeighting(WaterFactors model) {
		Double alcoholeUnits = model.getAlcohol();
		return (alcoholeUnits != null)  && (alcoholeUnits.doubleValue() != 0.0);
	}

}
