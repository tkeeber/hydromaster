package com.sprint22.apps.android.hydration.calculation;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class ExerciseCalculatorStrategy implements IHydrationFactorCalculatorStrategy{

	public static final double EXERCISE_MINS_FACTOR = .1;
	
	@Override
	public double apply(WaterFactors model, WaterFactorEffectsBuilder waterFactorEffectsBuilder, double requiredDailyWaterIntake) {
		if (model.getExerciseMinutes() != null) {
			double exerciseMintesEffect = model.getExerciseMinutes() * EXERCISE_MINS_FACTOR;
			waterFactorEffectsBuilder.withExerciseMinutesEffect(exerciseMintesEffect);
			return requiredDailyWaterIntake + exerciseMintesEffect;
		}
		return requiredDailyWaterIntake;
	}

	@Override
	public boolean shouldApplyWeighting(WaterFactors model) {
		Double exerciseMinutes = model.getExerciseMinutes();
		return ((exerciseMinutes != null )
				&& (exerciseMinutes.doubleValue() != 0.0));
	}

}
