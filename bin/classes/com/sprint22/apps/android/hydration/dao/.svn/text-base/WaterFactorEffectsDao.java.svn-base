package com.sprint22.apps.android.hydration.dao;

import android.content.Context;

import com.sprint22.apps.android.hydration.dto.WaterFactorEffects;
import com.sprint22.apps.android.hydration.utils.SharedPerferencesExecutor;

public class WaterFactorEffectsDao {

	private static final String KEY = "waterFactorEffects";
	private SharedPerferencesExecutor<WaterFactorEffects> sharedPerferencesExecutor;

	public WaterFactorEffectsDao(Context context) {
		this.sharedPerferencesExecutor = new SharedPerferencesExecutor<WaterFactorEffects>(context);
	}

	public void save(WaterFactorEffects model) {
		sharedPerferencesExecutor.save(KEY, model);
	}
	
	public WaterFactorEffects retrieve() {
		return sharedPerferencesExecutor.retreive(KEY, WaterFactorEffects.class);
	}
}
