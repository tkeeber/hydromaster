package com.sprint22.apps.android.hydration.view;

import com.sprint22.apps.android.hydration.dto.ClimateType;
import com.sprint22.apps.android.hydration.dto.WaterFactors;

public class WaterFactorsRenderer {

	private WaterFactors waterFactors;

	public WaterFactorsRenderer(final WaterFactors waterFactors) {
		this.waterFactors = waterFactors;
	}

	public String renderPregnancyFactor() {
		boolean isPregnant = waterFactors.isPregnant();
		String displayedIsPregnant = "No";
		if (isPregnant) {
			displayedIsPregnant = "Yes";
		}
		return "(" + displayedIsPregnant + ")";
	}

	public String renderIllnessFactor() {
		boolean isIll = waterFactors.isIll();
		String displayedIsIll = "No";
		if (isIll) {
			displayedIsIll = "Yes";
		}
		return "(" + displayedIsIll + ")";
	}

	public String renderHealthyDietFactor() {
		boolean hasHealthyDiet = waterFactors.hasHealtDiet();
		String displayedHealthyDiet = "No";
		if (hasHealthyDiet) {
			displayedHealthyDiet = "Yes";
		}
		return "(" + displayedHealthyDiet + ")";
	}

	public String renderAlcholUnitsFactor() {
		Double alcholUnots = waterFactors.getAlcohol();
		String displayedAlcholUnits = "None";
		if (alcholUnots != null) {
			displayedAlcholUnits = alcholUnots + "";
		}
		return "(" + displayedAlcholUnits + ")";
	}

	public String renderClimateFactor() {
		ClimateType climate = waterFactors.getClimate();
		String displayedClimate = "0.0";
		if (climate != null) {
			displayedClimate = climate.getName();
		}
		return "(" + displayedClimate + ")";
	}

	public String renderExerciseFactor() {
		Double exercise = waterFactors.getExerciseMinutes();
		String displayedExercise = "None";
		if (exercise != null) {
			displayedExercise = exercise + "";
		}
		return "(" + displayedExercise + ")";
	}

	public String renderWeightFactor() {
		Double weight = waterFactors.getWeight();
		String displayedWeight = "0.0";
		if (weight != null) {
			displayedWeight = weight + "";
		}
		return "(" + displayedWeight + ")";
	}
}
