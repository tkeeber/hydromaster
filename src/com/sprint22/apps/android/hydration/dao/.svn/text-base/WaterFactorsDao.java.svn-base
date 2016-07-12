package com.sprint22.apps.android.hydration.dao;

import android.content.Context;

import com.sprint22.apps.android.hydration.dto.WaterFactors;
import com.sprint22.apps.android.hydration.utils.SharedPerferencesExecutor;

public class WaterFactorsDao {

	private static final String KEY = "waterFactors";
	private SharedPerferencesExecutor<WaterFactors> sharedPerferencesExecutor;

	public WaterFactorsDao(Context context) {
		this.sharedPerferencesExecutor = new SharedPerferencesExecutor<WaterFactors>(context);
	}

	public void save(WaterFactors model) {
		sharedPerferencesExecutor.save(KEY, model);
	}
	
	public WaterFactors retrieve() {
		return sharedPerferencesExecutor.retreive(KEY, WaterFactors.class);
	}
}
