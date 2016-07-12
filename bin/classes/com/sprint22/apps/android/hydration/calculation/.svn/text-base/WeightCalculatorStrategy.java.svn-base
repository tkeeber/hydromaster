package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.dto.GenderType;
import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class WeightCalculatorStrategy implements IHydrationFactorCalculatorStrategy{

	public static final double WEIGHT_FACTOR = .5;
	
	@Override
	public double apply(WaterFactors model,WaterFactorEffectsBuilder waterFactorEffectsBuilder, double requiredDailyWaterIntake) {
		if (model.getWeight() != null) {
			double weight = Double.valueOf(model.getWeight());
			double weightEffect = weight * WEIGHT_FACTOR;
			waterFactorEffectsBuilder.withWeightEffect(weightEffect);
			return requiredDailyWaterIntake + weightEffect;
		} else {
			return applyWaterFactorForGender(model, waterFactorEffectsBuilder, requiredDailyWaterIntake);
		}
	}

	private double applyWaterFactorForGender(WaterFactors model, WaterFactorEffectsBuilder waterFactorEffectsBuilder,
			double requiredDailyWaterIntake) {
		//use the gender to determine the default weight to be used.
		GenderType gender = model.getGender();
		
		double weightEffect = 0.0;
		if (GenderType.MALE.equals(gender)) {
			weightEffect = WEIGHT_FACTOR * SPConstants.DEFAULT_MALE_WEIGHT;
		} else if (GenderType.FEMALE.equals(gender)) {
			weightEffect = (WEIGHT_FACTOR * SPConstants.DEFAULT_FEMALE_WEIGHT);
		} else {
			weightEffect = (WEIGHT_FACTOR * SPConstants.DEFAULT_MALE_WEIGHT);
		}
		
		waterFactorEffectsBuilder.withWeightEffect(weightEffect);
		return requiredDailyWaterIntake + weightEffect;
	}

	@Override
	public boolean shouldApplyWeighting(WaterFactors model) {
		//always want to have the weight applied.
		return true;
	}

}
