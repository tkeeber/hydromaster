package com.sprint22.apps.android.hydration.dto;

public class WaterFactorEffectsBuilder {

	private Double weightEffect;
	private Double exerciseEffect;
	private Double climateEffect;
	private Double illnessEffect;
	private Double pregnancyEffect;
	private Double alcholUnitsEffect;
	private Double healthyDietEffect;

	public WaterFactorEffectsBuilder buildDefault() {
		this.weightEffect = 2000.0;
		this.exerciseEffect = 0.0;
		this.climateEffect = 0.0;
		this.illnessEffect = 0.0;
		this.pregnancyEffect = 0.0;
		this.alcholUnitsEffect = 0.0;
		this.healthyDietEffect = -200.0;
		return this;
	}

	public static WaterFactorEffectsBuilder newWaterFactorEffectsBuilder() {
		return new WaterFactorEffectsBuilder();
	}

	public WaterFactorEffectsBuilder withWeightEffect(Double effectWeight) {
		this.weightEffect = effectWeight;
		return this;
	}

	public WaterFactorEffectsBuilder withClimateEffect(Double climateEffect) {
		this.climateEffect = climateEffect;
		return this;
	}

	public WaterFactorEffectsBuilder withExerciseMinutesEffect(Double exerciseEffect) {
		this.exerciseEffect = exerciseEffect;
		return this;
	}

	public WaterFactorEffectsBuilder withAlcholUnitsEffect(Double alcholUnitsEffect) {
		this.alcholUnitsEffect = alcholUnitsEffect;
		return this;
	}

	public WaterFactorEffectsBuilder withHealthDietEffect(Double healthyDietEffect) {
		this.healthyDietEffect = healthyDietEffect;
		return this;
	}

	public WaterFactorEffectsBuilder withIllnessEffect(Double illnessEffect) {
		this.illnessEffect = illnessEffect;
		return this;
	}

	public WaterFactorEffectsBuilder withPregnancyEffect(Double pregnancyEffect) {
		this.pregnancyEffect = pregnancyEffect;
		return this;
	}

	public WaterFactorEffects build() {
		return new WaterFactorEffects(weightEffect, exerciseEffect, climateEffect, illnessEffect,
				pregnancyEffect, alcholUnitsEffect, healthyDietEffect);
	}
}
