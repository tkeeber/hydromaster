package com.sprint22.apps.android.hydration.view;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffects;

public class WaterFactorEffectsRenderer {

	private WaterRenderer waterRenderer;

	private WaterFactorEffects waterFactorEffect;

	public WaterFactorEffectsRenderer(final WaterFactorEffects waterFactorEffect,
			final WaterRenderer waterRenderer) {
		this.waterFactorEffect = waterFactorEffect;
		this.waterRenderer = waterRenderer;
	}

	/**
	 * private Double weightEffect; private Double exerciseEffect; private
	 * Double climateEffect; private Double ilnnessEffect; private Double
	 * pregnancyEffect; private Double alcholUnitsEffect; private Double
	 * healthyDietEffect;
	 */
	public String renderWeightEffect() {
		return "+" + waterFactorEffect.getWeightEffect()
				+ waterRenderer.renderWaterMeasurementType();
	}

	public String renderExerciseEffect() {
		Double exerciseEffect = waterFactorEffect.getExerciseEffect();
		if (exerciseEffect == null || exerciseEffect.doubleValue() == 0.0) {
			return "-";
		}
		return "+" + exerciseEffect + waterRenderer.renderWaterMeasurementType();
	}

	public String renderClimateEffect() {
		Double climateEffect = waterFactorEffect.getClimateEffect();
		if (climateEffect == null || climateEffect.doubleValue() == 0.0) {
			return "-";
		}
		return "+" + climateEffect + waterRenderer.renderWaterMeasurementType();
	}

	public String renderAlcholUnitsEffect() {
		Double alcholEffect = waterFactorEffect.getAlcholUnitsEffect();
		if (alcholEffect == null || alcholEffect.doubleValue() == 0.0) {
			return "-";
		}
		return "+" + alcholEffect + waterRenderer.renderWaterMeasurementType();
	}

	public String renderHealthDietEffect() {
		Double healthDietEffect = waterFactorEffect.getHealthyDietEffect();
		if (healthDietEffect == null || healthDietEffect.doubleValue() == 0.0) {
			return "-";
		}
		return healthDietEffect + waterRenderer.renderWaterMeasurementType();
	}

	public String renderIllnessEffect() {
		Double illnessEffect = waterFactorEffect.getIllnessEffect();
		if (illnessEffect == null || illnessEffect.doubleValue() == 0.0) {
			return "-";
		}
		return "+" + illnessEffect + waterRenderer.renderWaterMeasurementType();
	}

	public String renderPregnancyEffect() {
		Double pregEffect = waterFactorEffect.getPregnancyEffect();
		if (pregEffect == null || pregEffect.doubleValue() == 0.0) {
			return "-";
		}
		return "+" + pregEffect + waterRenderer.renderWaterMeasurementType();
	}
}
