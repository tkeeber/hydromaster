package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.dto.ClimateType;
import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class ClimateCalculatorStrategy implements IHydrationFactorCalculatorStrategy{

	public static final int CLIMATE_FACTOR = 16;
	
	@Override
	public double apply(WaterFactors model,WaterFactorEffectsBuilder waterFactorEffectsBuilder, double requiredDailyWaterIntake) {
		double climateEffect = requiredDailyWaterIntake + CLIMATE_FACTOR;
		waterFactorEffectsBuilder.withClimateEffect(climateEffect);
		return climateEffect;
	}

	@Override
	public boolean shouldApplyWeighting(WaterFactors model) {
		ClimateType climate = model.getClimate();
		return (ClimateType.TROPICAL.equals(climate) 
				|| ClimateType.DRY.equals(climate) || ClimateType.POLAR.equals(climate));
	}

}
