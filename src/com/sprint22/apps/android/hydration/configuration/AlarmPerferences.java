package com.sprint22.apps.android.hydration.configuration;

import android.content.SharedPreferences;

public enum AlarmPerferences {
	IS_ALARM_ENABLED {
		@Override
		public Object get(SharedPreferences sharedPerferences) {
			return sharedPerferences.getBoolean("alarm_toggle", false);
		}
	},
	IS_AUTO_ALARM {
		public Object get(SharedPreferences sharedPerferences) {
			return sharedPerferences.getBoolean("is_auto_alarm", false);
		}
	},
	ALARM_START_TIME {
		@Override
		public Object get(SharedPreferences sharedPerferences) {
			return sharedPerferences.getString("alarm_start_time", "08:00");
		}
	},
	ALARM_END_TIME {
		@Override
		public Object get(SharedPreferences sharedPerferences) {
			return sharedPerferences.getString("alarm_start_time", "22:00");
		}
	};

	public abstract Object get(SharedPreferences sharedPerferences);
}
