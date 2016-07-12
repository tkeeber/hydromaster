package com.sprint22.apps.android.hydration.dto;

public class WaterAlarmBuilder {

	public long nextNotificationTime;
	public long alarmInterval;
	public String alarmTimeString;
	
	public static WaterAlarmBuilder newWaterAlarmBuilder() {
		return new WaterAlarmBuilder();
	}

	public WaterAlarmBuilder withNextNotificationTime(long nextNotificationTime) {
		this.nextNotificationTime = nextNotificationTime;
		return this;
	}

	public WaterAlarmBuilder withAlarmInterval(long alarmInterval) {
		this.alarmInterval = alarmInterval;
		return this;
	}
	
	public WaterAlarmBuilder withAlarmTimeString(String alarmTimeString) {
		this.alarmTimeString = alarmTimeString;
		return this;
	}
	
	public WaterAlarm build() {
		return new WaterAlarm(this.nextNotificationTime, this.alarmInterval, this.alarmTimeString);
	}
}
