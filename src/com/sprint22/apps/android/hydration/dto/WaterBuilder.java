package com.sprint22.apps.android.hydration.dto;

public class WaterBuilder {

	private double requiredDailyWaterIntake;
	private double currentDailyWaterIntake;
	private double glassSize;

	private WaterMeasurementType measurementType;
	private Float calculatedDailyWaterGlasses;

	private WaterBuilder() {
	}

	public static WaterBuilder newWaterBuilder() {
		return new WaterBuilder();
	}

	public WaterBuilder buildDefault() {
		this.calculatedDailyWaterGlasses = 0.0f;
		this.requiredDailyWaterIntake = 2000.00;
		this.currentDailyWaterIntake = 0.00;
		this.glassSize = 250.0;
		this.measurementType = WaterMeasurementType.MILLILITRES;
		return this;
	}

	public WaterBuilder startingWith(Water water) {
		this.requiredDailyWaterIntake = water.getRequiredDailyWaterIntake();
		this.currentDailyWaterIntake = water.getCurrentDailyWaterIntake();
		this.glassSize = water.getGlassSize();
		this.measurementType = water.getWaterMeasurementType();
		return this;
	}

	public WaterBuilder withCurrentDailyWaterIntake(double currentDailyWaterIntake) {
		this.currentDailyWaterIntake = currentDailyWaterIntake;
		return this;
	}

	public WaterBuilder withRequiredDailyWaterIntake(double requiredDailyWaterIntake) {
		this.requiredDailyWaterIntake = requiredDailyWaterIntake;
		return this;
	}

	public WaterBuilder withWaterMeasurementType(WaterMeasurementType measurementType) {
		this.measurementType = measurementType;
		return this;
	}

	public WaterBuilder withCalculatedDailyWaterGlasses(Float calculatedDailyWaterGlasses) {
		this.calculatedDailyWaterGlasses = calculatedDailyWaterGlasses;
		return this;
	}

	public WaterBuilder withGlassSize(Double glassSize) {
		this.glassSize = glassSize;
		return this;
	}

	public Water build() {
		return new Water(this.requiredDailyWaterIntake, this.currentDailyWaterIntake,
				this.measurementType, this.glassSize, this.calculatedDailyWaterGlasses);
	}
}
