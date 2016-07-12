package com.sprint22.apps.android.hydration.dto;

/**
 * item>tropical</item> <item>dry</item> <item>moderate</item>
 * <item>continental</item> <item>highlands</item> <item>polar</item>
 * 
 * @author tomkeeber
 * 
 */
public enum ClimateType {
	DRY("Dry"), MODERATE("Moderate"), CONTINENTAL("Continental"), HIGHLANDS("Highlands"), TROPICAL(
			"Tropical"), POLAR("Polar");

	String name;

	ClimateType(String name) {
		this.name = name;
	}

	public boolean equalsString(String climate) {
		return this.name.equals(climate);
	}

	public String getName() {
		return this.name;
	}

	public static ClimateType valueOfName(String name) {
		if (name.equalsIgnoreCase(DRY.name)) {
			return DRY;
		} else if (name.equalsIgnoreCase(MODERATE.name)) {
			return MODERATE;
		} else if (name.equalsIgnoreCase(CONTINENTAL.name)) {
			return CONTINENTAL;
		} else if (name.equalsIgnoreCase(HIGHLANDS.name)) {
			return HIGHLANDS;
		} else if (name.equalsIgnoreCase(TROPICAL.name)) {
			return TROPICAL;
		} else if (name.equalsIgnoreCase(POLAR.name)) {
			return POLAR;
		} else {
			throw new IllegalArgumentException("Climate " + name + " not found.");
		}
	}
}
