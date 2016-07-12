package com.sprint22.apps.android.hydration.alarm;

import java.math.BigDecimal;
import java.util.Calendar;

import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.dto.Water;
import com.sprint22.apps.android.hydration.dto.WaterAlarmBuilder;

public class AutoAlarmCalculator {

	public Calendar calculateNextAlarmTime(Water water, WaterAlarmBuilder waterAlarmBuilder,
			Calendar startTime, Calendar endTime) {

		if (startTime.after(endTime)) {
			// unable to set alarm. start time after end time - user validation
			// required
			return null;
		} else {

			float differenceMillisecs = calculateStartEndTimeDifference(startTime, endTime);
			Float dailyWaterGlasses = calculateDailyWaterGlasses(water);
			int interval = calculateAlarmTimeInterval(differenceMillisecs, dailyWaterGlasses);
			Calendar calendar = Calendar.getInstance();
			applyIntervalToCalendar(calendar, interval);

			waterAlarmBuilder.withAlarmInterval(interval);
			// waterData.textAlarmTimeString = createNextAlarm(context,
			// calendar, interval);
			return calendar;
		}
	}

	protected void applyIntervalToCalendar(Calendar calendar, int interval) {
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MILLISECOND, interval);
	}

	protected int calculateAlarmTimeInterval(float differenceMillisecs, float dailyWaterGlasses) {
		int interval = new BigDecimal(Math.round(differenceMillisecs / dailyWaterGlasses))
				.intValue();
		return interval;
	}

	protected Float calculateDailyWaterGlasses(Water water) {
		Float dailyWaterGlasses = water.getCalculatedDailyWaterGlasses();
		if (dailyWaterGlasses == null || dailyWaterGlasses == 0) {
			// Amount of water required has not been calculated yet - so set
			// it to the default.
			dailyWaterGlasses = SPConstants.DEFAULT_GLASSES_A_DAY;
		}
		return dailyWaterGlasses;
	}

	public float calculateStartEndTimeDifference(Calendar startTime, Calendar endTime) {
		float differenceMillisecs = endTime.getTimeInMillis() - startTime.getTimeInMillis();
		return differenceMillisecs;
	}
}
