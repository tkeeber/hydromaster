package com.sprint22.apps.android.hydration.dto;

public class Water {

	// value containing the daily water intake as calculated by the hydration
	// factors in whatever measurement set by measurement type.
	private Double requiredDailyWaterIntake;
	private Double currentDailyWaterIntake;
	private WaterMeasurementType waterMeasurementType;
	private Double glassSize;

	private Float calculatedDailyWaterGlasses;

	Water(Double requiredDailyWaterIntake, Double currentDailyWaterIntake,
			WaterMeasurementType measurementType, Double glassSize,
			Float calculatedDailyWaterGlasses) {
		this.requiredDailyWaterIntake = requiredDailyWaterIntake;
		this.currentDailyWaterIntake = currentDailyWaterIntake;
		this.waterMeasurementType = measurementType;
		this.glassSize = glassSize;
		this.calculatedDailyWaterGlasses = calculatedDailyWaterGlasses;
	}

	public WaterMeasurementType getWaterMeasurementType() {
		return this.waterMeasurementType;
	}

	public Double getRequiredDailyWaterIntake() {
		return requiredDailyWaterIntake;
	}

	public Double getCurrentDailyWaterIntake() {
		return currentDailyWaterIntake;
	}

	public Double getGlassSize() {
		return glassSize;
	}

	public Float getCalculatedDailyWaterGlasses() {
		return calculatedDailyWaterGlasses;
	}
}
