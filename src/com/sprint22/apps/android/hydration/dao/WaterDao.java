package com.sprint22.apps.android.hydration.dao;

import android.content.Context;

import com.sprint22.apps.android.hydration.dto.Water;
import com.sprint22.apps.android.hydration.dto.WaterFactors;
import com.sprint22.apps.android.hydration.utils.SharedPerferencesExecutor;

public class WaterDao {

	private static final String KEY = "water";
	private SharedPerferencesExecutor<Water> sharedPerferencesExecutor;

	public WaterDao(Context context) {
		this.sharedPerferencesExecutor = new SharedPerferencesExecutor<Water>(context);
	}

	public void save(Water model) {
		sharedPerferencesExecutor.save(KEY, model);
	}
	
	public Water retrieve() {
		return sharedPerferencesExecutor.retreive(KEY, Water.class);
	}
}
