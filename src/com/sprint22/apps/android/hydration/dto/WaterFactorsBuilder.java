package com.sprint22.apps.android.hydration.dto;


public final class WaterFactorsBuilder {

	private GenderType gender;
	private ClimateType climate;
	private Double weight;
	private Double exerciseMinutes;
	private Double alcohol;
	private boolean isPregnant;
	private boolean hasHealtDiet;
	private boolean isIll;

	private WaterFactorsBuilder() {
	}

	public static WaterFactorsBuilder newWaterFactorsBuilder() {
		return new WaterFactorsBuilder();
	}

	public WaterFactorsBuilder buildDefault() {
		this.gender = GenderType.MALE;
		this.climate = ClimateType.MODERATE;
		this.weight = 80.0;
		this.exerciseMinutes = 0.0;
		this.alcohol = 0.0;
		this.isIll = false;
		this.isPregnant = false;
		this.hasHealtDiet = true;
		return this;
	}

	public WaterFactorsBuilder withGender(GenderType genderType) {
		this.gender = genderType;
		return this;
	}

	public WaterFactorsBuilder withAlcohol(Double alcohol) {
		this.alcohol = alcohol;
		return this;
	}

	public WaterFactorsBuilder withClimate(ClimateType climateType) {
		this.climate = climateType;
		return this;
	}

	public WaterFactorsBuilder withExerciseMinutes(Double exerciseMinutes) {
		this.exerciseMinutes = exerciseMinutes;
		return this;
	}

	public WaterFactorsBuilder withIsIll(boolean isIll) {
		this.isIll = isIll;
		return this;
	}

	public WaterFactorsBuilder withIsPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;
		return this;
	}

	public WaterFactorsBuilder withWeight(double weight) {
		this.weight = weight;
		return this;
	}

	public WaterFactorsBuilder withHealthDiet(boolean hasHealthDiet) {
		this.hasHealtDiet = hasHealthDiet;
		return this;
	}

	public WaterFactors build() {
		return new WaterFactors(this.gender, this.climate, this.weight, this.exerciseMinutes,
				this.alcohol, this.isPregnant, this.hasHealtDiet, this.isIll);
	}
}
