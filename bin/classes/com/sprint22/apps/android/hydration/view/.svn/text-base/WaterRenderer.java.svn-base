package com.sprint22.apps.android.hydration.view;

import com.sprint22.apps.android.hydration.dto.Water;

public class WaterRenderer {

	private Water water;

	public WaterRenderer(Water water) {
		this.water = water;
	}

	public String renderCalculatedDailyWaterGlasses() {
		return water.getCalculatedDailyWaterGlasses() + "";
	}

	public String renderRequiredDailyWaterIntake() {
		return water.getRequiredDailyWaterIntake() + renderWaterMeasurementType();
	}

	public String renderWaterMeasurementType() {
		return water.getWaterMeasurementType().getAbbreviation();
	}
}
