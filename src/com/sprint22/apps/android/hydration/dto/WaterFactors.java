package com.sprint22.apps.android.hydration.dto;


public class WaterFactors {
	
	private GenderType gender;
	private ClimateType climate;
	private Double weight;
	private Double exerciseMinutes;
	private Double alcohol;
	private boolean isPregnant;
	private boolean hasHealtDiet;
	private boolean isIll;
	
	WaterFactors(GenderType gender, ClimateType climate, Double weight,
			Double exerciseMinutes, Double alcohol, boolean isPregnant,
			boolean hasHealtDiet, boolean isIll) {
		super();
		this.gender = gender;
		this.climate = climate;
		this.weight = weight;
		this.exerciseMinutes = exerciseMinutes;
		this.alcohol = alcohol;
		this.isPregnant = isPregnant;
		this.hasHealtDiet = hasHealtDiet;
		this.isIll = isIll;
	}
	
	public boolean isPregnant() {
		return isPregnant;
	}

	public boolean hasHealtDiet() {
		return hasHealtDiet;
	}
	
	public boolean isIll() {
		return isIll;
	}

	public GenderType getGender() {
		return gender;
	}

	public ClimateType getClimate() {
		return climate;
	}

	public Double getWeight() {
		return weight;
	}

	public Double getExerciseMinutes() {
		return exerciseMinutes;
	}

	public Double getAlcohol() {
		return alcohol;
	}

}
