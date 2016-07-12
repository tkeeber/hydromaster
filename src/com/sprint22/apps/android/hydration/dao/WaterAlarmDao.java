package com.sprint22.apps.android.hydration.dao;

import android.content.Context;

import com.sprint22.apps.android.hydration.dto.WaterAlarm;
import com.sprint22.apps.android.hydration.utils.SharedPerferencesExecutor;

public class WaterAlarmDao {
	
	private static final String KEY = "waterAlarm";
	private SharedPerferencesExecutor<WaterAlarm> sharedPerferencesExecutor;

	public WaterAlarmDao(Context context) {
		this.sharedPerferencesExecutor = new SharedPerferencesExecutor<WaterAlarm>(
				context);
	}

	public void save(WaterAlarm model) {
		sharedPerferencesExecutor.save(KEY, model);
	}

	public WaterAlarm retrieve() {
		return sharedPerferencesExecutor.retreive(KEY, WaterAlarm.class);
	}
}