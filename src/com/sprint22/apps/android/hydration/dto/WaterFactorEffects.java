package com.sprint22.apps.android.hydration.dto;

public class WaterFactorEffects {

	private Double weightEffect;
	private Double exerciseEffect;
	private Double climateEffect;
	private Double ilnnessEffect;
	private Double pregnancyEffect;
	private Double alcholUnitsEffect;
	private Double healthyDietEffect;
	
	WaterFactorEffects(Double effectWeight, Double effectExercise,
			Double effectClimate, Double effectIlnness, Double effectPreg,
			Double effectAlcholUnits, Double effectHealthyDiet) {
		super();
		this.weightEffect = effectWeight;
		this.exerciseEffect = effectExercise;
		this.climateEffect = effectClimate;
		this.ilnnessEffect = effectIlnness;
		this.pregnancyEffect = effectPreg;
		this.alcholUnitsEffect = effectAlcholUnits;
		this.healthyDietEffect = effectHealthyDiet;
	}

	public Double getWeightEffect() {
		return weightEffect;
	}

	public Double getExerciseEffect() {
		return exerciseEffect;
	}

	public Double getClimateEffect() {
		return climateEffect;
	}

	public Double getIllnessEffect() {
		return ilnnessEffect;
	}

	public Double getPregnancyEffect() {
		return pregnancyEffect;
	}

	public Double getAlcholUnitsEffect() {
		return alcholUnitsEffect;
	}

	public Double getHealthyDietEffect() {
		return healthyDietEffect;
	}
	
}
