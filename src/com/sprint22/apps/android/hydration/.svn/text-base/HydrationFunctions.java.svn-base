package com.sprint22.apps.android.hydration;

import java.util.Calendar;
import java.util.StringTokenizer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

import com.sprint22.apps.android.hydration.alarm.HydrationAlarmReceiver;
import com.sprint22.apps.android.hydration.alarm.SaveWaterConsumedAlarm;
import com.sprint22.apps.android.hydration.configuration.AlarmPerferences;
import com.sprint22.apps.android.hydration.configuration.SPConstants;
import com.sprint22.apps.android.hydration.dao.WaterDao;
import com.sprint22.apps.android.hydration.dao.WaterFactorEffectsDao;
import com.sprint22.apps.android.hydration.dao.WaterFactorsDao;
import com.sprint22.apps.android.hydration.database.HydrationDbAdapter;
import com.sprint22.apps.android.hydration.dto.Water;
import com.sprint22.apps.android.hydration.dto.WaterBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactorEffects;
import com.sprint22.apps.android.hydration.dto.WaterFactorEffectsBuilder;
import com.sprint22.apps.android.hydration.dto.WaterFactors;
import com.sprint22.apps.android.hydration.dto.WaterFactorsBuilder;
import com.sprint22.apps.android.hydration.utils.DateUtils;

public class HydrationFunctions {

	private static final String TAG = "hydromaster";

	private static final int DAILY_GLASS_LIMIT = 15;

	/**
	 * Main method for determining the water which should be drank, setting the
	 * required preferences and setting the notifications.
	 */
	// public WaterNotifyData performWaterCalculations(Context context) {
	// WaterNotifyData waterData = null;
	// SharedPreferences sharedPreferences = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// try {
	//
	// // determine the next time water should be drank.
	// // waterData = determineDailyWaterRequirements(context,
	// // sharedPreferences);
	//
	// // save the determined values so we don't have to recalculate all
	// // the time
	// saveWaterData(waterData, sharedPreferences);
	//
	// setSaveAlarm(context);
	//
	// // set the next notification alarm.
	// // setNotificationAlarm(context,
	// // waterData.getNextNotificationTime());
	// } catch (Exception e) {
	// // Log.d(TAG, "Exception ", e);
	// // if there's a exception then just set the water to be the default
	// waterData = new WaterNotifyData();
	// waterData.dailyWaterGlasses = SPConstants.DEFAULT_GLASSES_A_DAY;
	// waterData.waterMillilitersDaily = SPConstants.DEFAULT_WATER_A_DAY;
	// // waterData.effectWeight = "2500";
	// saveWaterData(waterData, sharedPreferences);
	// }
	// return waterData;
	// }

	// public WaterNotifyData createWaterNotifyDataFromPrefs(SharedPreferences
	// sharedPreferences) {
	//
	// WaterNotifyData data = new WaterNotifyData();
	//
	// data.dailyWaterGlasses =
	// sharedPreferences.getLong(SPConstants.KEY_WATER_GLASSES_A_DAY,
	// SPConstants.DEFAULT_GLASSES_A_DAY);
	// data.waterMillilitersDaily =
	// sharedPreferences.getLong(SPConstants.KEY_WATER_MILLS_A_DAY,
	// SPConstants.DEFAULT_WATER_A_DAY);
	//
	// data.alarmInterval =
	// sharedPreferences.getLong(SPConstants.KEY_ALARM_INTERVAL, 0);
	// data.textAlarmTimeString =
	// sharedPreferences.getString(SPConstants.KEY_ALARM_TEXT, "");
	// return data;
	// }

	@Deprecated
	public long convertWaterMillsToGlasses(double waterInMills, SharedPreferences sharedPreferences) {
		String waterMeasurementUnit = sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);

		double dbGglassesToday = 0;
		double remainder = 0;
		if (waterMeasurementUnit.equals("ml")) {
			dbGglassesToday = waterInMills / SPConstants.DEFAULT_GLASS_SIZE_MILLILITERS;
			remainder = waterInMills % SPConstants.DEFAULT_GLASS_SIZE_MILLILITERS;
		} else if (waterMeasurementUnit.equals("l")) {
			dbGglassesToday = (waterInMills * 0.001) / SPConstants.DEFAULT_LITER_GLASS_SIZE;
			remainder = (waterInMills * 0.001) % SPConstants.DEFAULT_LITER_GLASS_SIZE;

		} else if (waterMeasurementUnit.equals("pt")) {
			dbGglassesToday = (waterInMills * 0.00175) / SPConstants.DEFAULT_PINT_GLASS_SIZE;
			remainder = (waterInMills * 0.00175) % SPConstants.DEFAULT_PINT_GLASS_SIZE;

		} else if (waterMeasurementUnit.equals("oz")) {
			dbGglassesToday = (waterInMills * 0.0352) / SPConstants.DEFAULT_OUNCE_GLASS_SIZE;
			remainder = (waterInMills * 0.0352) % SPConstants.DEFAULT_OUNCE_GLASS_SIZE;
		} else {
			dbGglassesToday = waterInMills / SPConstants.DEFAULT_GLASS_SIZE_MILLILITERS;
			remainder = waterInMills % SPConstants.DEFAULT_GLASS_SIZE_MILLILITERS;
		}

		// if there is a remainder than add 1 to the glasses of water to be
		// drank.
		if (remainder > 0) {
			dbGglassesToday = dbGglassesToday + 1.0;
		}

		// set a maximum limit on the number of glasses that can be drunk.
		long glassaday = Math.round(dbGglassesToday);
		if (glassaday > DAILY_GLASS_LIMIT) {
			glassaday = DAILY_GLASS_LIMIT;
		}
		return glassaday;
	}

	public double getGlassSizeForWaterMeasurement(String waterMeasurementUnit) {
		double glassSize = SPConstants.DEFAULT_GLASS_SIZE_MILLILITERS;
		if (waterMeasurementUnit.equals("ml")) {
			// default
		} else if (waterMeasurementUnit.equals("l")) {
			glassSize = SPConstants.DEFAULT_LITER_GLASS_SIZE;
		} else if (waterMeasurementUnit.equals("pt")) {
			glassSize = SPConstants.DEFAULT_PINT_GLASS_SIZE;

		} else if (waterMeasurementUnit.equals("oz")) {
			glassSize = SPConstants.DEFAULT_OUNCE_GLASS_SIZE;
		}
		return glassSize;
	}

	public double convertFromMlsToUserMeasurement(double value, String waterMeasurementUnit,
			SharedPreferences sharedPreferences) {

		double converted = 0.0;
		if (waterMeasurementUnit.equals("l")) {
			converted = (value * 0.001);

		} else if (waterMeasurementUnit.equals("pt")) {
			converted = (value * 0.00175);

		} else if (waterMeasurementUnit.equals("oz")) {
			converted = (value * 0.0352);
		} else {
			converted = value;
		}
		return converted;
	}

	/**
	 * Determines the next time the user should be notified to drink water.
	 * 
	 * @param context
	 * @param sharedPreferences
	 * @return
	 */
	public WaterNotifyData determineDailyWaterRequirements(Context context,
			SharedPreferences sharedPreferences) {
		WaterNotifyData waterData = new WaterNotifyData();
		// String waterMeasurementUnit =
		// sharedPreferences.getString(SPConstants.KEY_WATER_MEASUREMENT_TYPE,
		// SPConstants.DEFAULT_WATER_MEASUREMENT);

		// How much water should the user be drinking a day in mills
		// double dailyWaterIntake =
		// determineDailyWaterIntakeInMililiters(waterData,
		// waterMeasurementUnit, context);

		// waterData.dailyWaterGlasses =
		// convertWaterMillsToGlasses(dailyWaterIntake, sharedPreferences);
		// get the start and end times - used to make sure we don't set alarms
		// outside these times.

		// waterData.waterMillilitersDaily =
		// Math.round(convertFromMlsToUserMeasurement(dailyWaterIntake,
		// waterMeasurementUnit, sharedPreferences));

		return waterData;
	}

	public void incrementDrankWater(Context context) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		long glassesDrankToday = sharedPreferences.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0);
		glassesDrankToday++;

		double waterDrankToday = glassesDrankToday * 250;
		String waterMeasurementUnit = sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);
		double waterDrank = convertFromMlsToUserMeasurement(waterDrankToday, waterMeasurementUnit,
				sharedPreferences);

		Editor editor = sharedPreferences.edit();
		editor.putLong(SPConstants.KEY_GLASSES_DRANK_TODAY, glassesDrankToday);
		editor.putLong(SPConstants.KEY_WATER_DRANK_TODAY, Math.round(waterDrank));
		editor.putLong(SPConstants.KEY_LAST_DRINK_TIME, System.currentTimeMillis());
		editor.commit();
	}

	public void saveDailyWaterDetails(Context context, SharedPreferences sharedPreferences) {

		long predicatedDailyWater = sharedPreferences.getLong(SPConstants.KEY_WATER_MILLS_A_DAY, 0);
		long drankDailyWater = sharedPreferences.getLong(SPConstants.KEY_WATER_DRANK_TODAY, 0l);
		long glassesDrankDaily = sharedPreferences.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0);
		String waterMeasurementUnit = sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);

		HydrationDbAdapter hrydationDbAdapter = new HydrationDbAdapter(context);
		try {
			hrydationDbAdapter.open();
			hrydationDbAdapter.updateTodaysWaterData(glassesDrankDaily, drankDailyWater,
					predicatedDailyWater, waterMeasurementUnit);
		} catch (Exception e) {
			// something went wrong!
			// Log.e("ERROR", "When saving water consumed", e);
		} finally {
			if (hrydationDbAdapter != null) {
				hrydationDbAdapter.close();
			}
		}
	}

	public void decrementDrankWater(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		long glassesDrankToday = sharedPreferences.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0);

		if (glassesDrankToday != 0) {
			glassesDrankToday--;
		}

		double waterDrankToday = glassesDrankToday * 250;
		String waterMeasurementUnit = sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);
		double waterDrank = convertFromMlsToUserMeasurement(waterDrankToday, waterMeasurementUnit,
				sharedPreferences);

		Editor editor = sharedPreferences.edit();
		editor.putLong(SPConstants.KEY_GLASSES_DRANK_TODAY, glassesDrankToday);
		editor.putLong(SPConstants.KEY_WATER_DRANK_TODAY, Math.round(waterDrank));
		editor.commit();
	}

	public void resetDrankWater(Context context) {
		SharedPreferences sharedPerferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		Editor editor = sharedPerferences.edit();
		editor.putLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0l);
		editor.putLong(SPConstants.KEY_WATER_DRANK_TODAY, 0l);
		editor.commit();
	}

	public void setUpDefaults(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		// TODO: Call a method which will calculate the water effects. instead
		// of building defaults.
		// So just build defaults for water and let the system sort out the
		// rest.

		// TODO: also calculate alarm.

		Water water = WaterBuilder.newWaterBuilder().buildDefault().build();
		new WaterDao(context).save(water);

		WaterFactors waterFactors = WaterFactorsBuilder.newWaterFactorsBuilder().buildDefault()
				.build();
		new WaterFactorsDao(context).save(waterFactors);

		WaterFactorEffects waterFactorsEffects = WaterFactorEffectsBuilder
				.newWaterFactorEffectsBuilder().buildDefault().build();
		new WaterFactorEffectsDao(context).save(waterFactorsEffects);

		setSaveAlarm(context);
	}

	// public void createWaterAlarm(Context context, Water water,
	// WaterAlarmBuilder waterAlarmBuilder) {
	//
	// SharedPreferences sharedPerferences = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// boolean alarmToBeSet =
	// sharedPerferences.getBoolean(SPConstants.KEY_WATER_ALARM_TOGGLE,
	// SPConstants.DEFAULT_ALARM_WATER_TOGGLE);
	//
	// // remove an existing alarms.
	// AlarmManager alarmManager = (AlarmManager)
	// context.getSystemService(Context.ALARM_SERVICE);
	// Intent i = new Intent(context, HydrationAlarmReceiver.class);
	// PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
	// alarmManager.cancel(pi);
	//
	// if (alarmToBeSet) {
	// // set an alarm
	//
	// // Is this an automatic alarm or a manual alarm
	// boolean isAutoAlarm = sharedPerferences.getBoolean(
	// SPConstants.KEY_WATER_ALARM_AUTO_TOGGLE,
	// SPConstants.DEFAULT_ALARM_WATER_AUTO_TOGGLE);
	//
	// String strEndTime =
	// sharedPerferences.getString(SPConstants.KEY_WATER_ALARM_END_TIME,
	// SPConstants.DEFAULT_END_TIME);
	// String strStartTime = sharedPerferences.getString(
	// SPConstants.KEY_WATER_ALARM_START_TIME, SPConstants.DEFAULT_START_TIME);
	// Calendar startTime = DateUtils.getCalendarWithTimeSet(strStartTime);
	// Calendar endTime = DateUtils.getCalendarWithTimeSet(strEndTime);
	//
	// if (isAutoAlarm) {
	// AutoAlarmCalculator autoAlarmCalculator = new AutoAlarmCalculator();
	// Calendar alarmCalendar =
	// autoAlarmCalculator.calculateNextAlarmTime(water,
	// waterAlarmBuilder, startTime, endTime);
	// createNextAlarm(context, alarmCalendar, waterAlarmBuilder.build()
	// .getAlarmInterval());
	// } else {
	// calculateNextManualAlarm(context, waterData);
	// }
	// }
	//
	// if (waterData.textAlarmTimeString == null) {
	//
	// } else {
	// SharedPreferences sharedPreferences = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// Editor editor = sharedPreferences.edit();
	// editor.putLong(SPConstants.KEY_ALARM_INTERVAL, waterData.alarmInterval);
	// editor.putString(SPConstants.KEY_ALARM_TEXT,
	// waterData.textAlarmTimeString);
	// editor.commit();
	// }
	// }

	public String createNextAlarm(Context context, Calendar wantedAlarCal, long interval) {

		SharedPreferences sharedPerferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		// whats our end time.
		String endTime = (String) AlarmPerferences.ALARM_END_TIME.get(sharedPerferences);
		Calendar endTimeCal = DateUtils.getCalendarWithTimeSet(endTime);

		// reschedule the water alarm
		Intent i = new Intent(context, HydrationAlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// if the alarm is after the end time then schedule for next day
		if (wantedAlarCal.before(endTimeCal)) {
			alarmManager.set(AlarmManager.RTC_WAKEUP, wantedAlarCal.getTimeInMillis(), pi);
		} else {
			// schedule for next day.
			String startTime = (String) AlarmPerferences.ALARM_START_TIME.get(sharedPerferences);

			wantedAlarCal = DateUtils.getCalendarWithTimeSet(startTime);
			wantedAlarCal.add(Calendar.DAY_OF_YEAR, 1);
			alarmManager.set(AlarmManager.RTC_WAKEUP, wantedAlarCal.getTimeInMillis(), pi);

		}
		// water1 textAlarmTimeString ="Next alarm will be at + " +
		// DateFormat.format("MM/dd/yy h:mmaa", wantedAlarCal.getTime());
		// Log.d(TAG, "Alarm set + " + DateFormat.format("MM/dd/yy h:mmaa",
		// wantedAlarCal.getTime()));
		return "Next alarm will be at " + DateFormat.format("h:mmaa", wantedAlarCal.getTime());
	}

	public void setSaveAlarm(Context context) {

		SharedPreferences sharedPerferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean automaticallySaved = sharedPerferences.getBoolean(SPConstants.KEY_AUTOMATIC_SAVE,
				true);
		if (automaticallySaved) {

			AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);

			// only save if set to
			String saveTime = sharedPerferences.getString(SPConstants.KEY_AUTOMATIC_SAVE_TIMES,
					SPConstants.DEFAULT_AUTO_SAVE_TIME);
			StringTokenizer tokeniser = new StringTokenizer(saveTime, ":");

			int hours = Integer.parseInt(tokeniser.nextToken());
			int minutes = Integer.parseInt(tokeniser.nextToken());

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, hours);
			calendar.set(Calendar.MINUTE, minutes);

			// reschedule the cleanup alarm
			Intent i = new Intent(context, SaveWaterConsumedAlarm.class);
			PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					(24 * 60 * 60 * 1000), pi);

			// Log.d("TAG", "Alarm schedules for " + calendar.getTime());

		} else {
			// remove the alarm.
		}

	}

	/**
	 * Determines in mls the amount of water the user should be drinking a day
	 * 
	 * @param context
	 * @return
	 * @deprecated
	 */
	// public double determineDailyWaterIntakeInMililiters(WaterNotifyData
	// waterData, String waterMeasurementUnit, Context context)
	// {
	// SharedPreferences sharedPreferences =
	// PreferenceManager.getDefaultSharedPreferences(context);
	//
	// //get the weight to be used in the calculations
	// //this weight will always be lbs.
	// final double weight = getWeight(sharedPreferences);
	//
	// //the weight of the person is the initial way of determening the water
	// they should drink.
	// // * weight * .5
	// double waterRequirement = (weight * .5);
	//
	// String weightEffect = "+" +
	// Math.round(convertFromMlsToUserMeasurement(waterRequirement *
	// 28.4,waterMeasurementUnit, sharedPreferences)) + waterMeasurementUnit;
	// waterData.effectWeight = weightEffect;
	//
	// //minutes * .1
	// waterRequirement = applyExercisePerferences(waterData, sharedPreferences,
	// waterRequirement, waterMeasurementUnit);
	// waterRequirement = applyClimatePerferences(waterData, sharedPreferences,
	// waterRequirement, waterMeasurementUnit);
	// // preg + 16
	// waterRequirement = applyPregnancyPerferences(waterData,
	// sharedPreferences, waterRequirement, waterMeasurementUnit);
	// // + 8
	// waterRequirement = applyIllnessPerferences(waterData, sharedPreferences,
	// waterRequirement, waterMeasurementUnit);
	// // alc * 8
	// waterRequirement = applyAlcoholPerferences(waterData, sharedPreferences,
	// waterRequirement, waterMeasurementUnit);
	//
	// double waterMls = waterRequirement * 28.4;
	//
	// //healthy diet deduction of 20%
	// waterMls = applyHealthyEatingPreferences(waterData, sharedPreferences,
	// waterMls, waterMeasurementUnit);
	//
	// return waterMls;
	// }

	// private double applyHealthyEatingPreferences(WaterNotifyData waterData,
	// SharedPreferences sharedPreferences, double waterRequirement, String
	// waterMeasurementUnit) {
	//
	// Boolean hasHealthyDiet =
	// sharedPreferences.getBoolean(SPConstants.KEY_HEALTHY_DIET, false);
	// String waterEffect = "-";
	// if (hasHealthyDiet)
	// {
	// double waterEffectDouble = waterRequirement * .20;
	// waterRequirement = waterRequirement *.80;
	// waterEffectDouble =
	// convertFromMlsToUserMeasurement(waterEffectDouble,waterMeasurementUnit,
	// sharedPreferences);
	// waterEffectDouble = Math.round(waterEffectDouble);
	// waterEffect = "-" + waterEffectDouble + waterMeasurementUnit;
	// }
	//
	// waterData.effectHealthyDiet = waterEffect;
	//
	// // {
	// return waterRequirement;
	// }

	// private double applyAlcoholPerferences(WaterNotifyData waterData,
	// SharedPreferences sharedPreferences, double waterRequirement, String
	// waterMeasurementUnit) {
	//
	// String enteredAlcoholUnits =
	// sharedPreferences.getString(SPConstants.KEY_ALCOHOL,
	// SPConstants.DEFAULT_EXCERCISE_MINUTES);
	//
	// String waterEffect = "-";
	// if (!enteredAlcoholUnits.equals("") && enteredAlcoholUnits.equals("0") ==
	// false)
	// {
	// int alcoholUnits = Integer.parseInt(enteredAlcoholUnits);
	// waterRequirement = waterRequirement+ alcoholUnits * 4;
	// double waterEffectDouble = convertFromMlsToUserMeasurement((alcoholUnits
	// * 4) * 28.4,waterMeasurementUnit, sharedPreferences);
	// waterEffectDouble = Math.round(waterEffectDouble);
	// waterEffect = "+" + waterEffectDouble + waterMeasurementUnit;
	// }
	//
	// waterData.effectAlcholUnits = waterEffect;
	//
	// return waterRequirement;
	// }

	// private double applyIllnessPerferences(WaterNotifyData waterData,
	// SharedPreferences sharedPreferences, double waterRequirement, String
	// waterMeasurementUnit) {
	//
	// Boolean isIll = sharedPreferences.getBoolean(SPConstants.KEY_ILLNESS,
	// false);
	//
	// String waterEffect = "-";
	// if (isIll)
	// {
	// waterRequirement = waterRequirement + 8;
	// double waterEffectDouble = convertFromMlsToUserMeasurement(8 *
	// 28.4,waterMeasurementUnit, sharedPreferences);
	// waterEffectDouble = Math.round(waterEffectDouble);
	// waterEffect = "+" + waterEffectDouble + waterMeasurementUnit;
	// }
	//
	// waterData.effectIlnness = waterEffect;
	//
	// return waterRequirement;
	// }

	/**
	 * Applies the pregnancy/breast feeding options to the water requirements
	 * 
	 * @param sharedPreferences
	 * @param waterRequirement
	 * @return
	 */
	// private double applyPregnancyPerferences(WaterNotifyData waterData,
	// SharedPreferences sharedPreferences, double waterRequirement, String
	// waterMeasurementUnit)
	// {
	// Boolean isPregnent =
	// sharedPreferences.getBoolean(SPConstants.KEY_PREGNANCY_PERFERENCE,
	// false);
	//
	// String waterEffect = "-";
	// if (isPregnent.booleanValue())
	// {
	// waterRequirement = waterRequirement + 16;
	// double waterEffectDouble = convertFromMlsToUserMeasurement(16 *
	// 28.4,waterMeasurementUnit, sharedPreferences);
	// waterEffectDouble = Math.round(waterEffectDouble);
	// waterEffect = "+" + waterEffectDouble + waterMeasurementUnit;
	// }
	//
	// waterData.effectPreg = waterEffect;
	//
	// return waterRequirement;
	// }

	// private double applyClimatePerferences(WaterNotifyData waterData,
	// SharedPreferences sharedPreferences, double waterRequirement, String
	// waterMeasurementUnit)
	// {
	// String enteredClimate =
	// sharedPreferences.getString(SPConstants.KEY_CLIMATE_PREFERENCE,
	// "moderate");
	//
	// String waterEffect = "-";
	// if (enteredClimate.equals("tropical") || enteredClimate.equals("dry") ||
	// enteredClimate.equals("polar"))
	// {
	// waterRequirement = waterRequirement + 16;
	// double waterEffectDouble = convertFromMlsToUserMeasurement(16 *
	// 28.4,waterMeasurementUnit, sharedPreferences);
	// waterEffectDouble = Math.round(waterEffectDouble);
	// waterEffect = "+" + waterEffectDouble + waterMeasurementUnit;
	// }
	//
	// waterData.effectClimate = waterEffect;
	//
	// return waterRequirement;
	// }

	/**
	 * Apply the excerise options to the water requirements
	 * 
	 * (minutes * .1)
	 * 
	 * @param sharedPreferences
	 * @param waterRequirement
	 * @return
	 */
	// private double applyExercisePerferences(WaterNotifyData waterData,
	// SharedPreferences sharedPreferences, double waterRequirement, String
	// waterMeasurementUnit)
	// {
	// String enteredExerciseMins =
	// sharedPreferences.getString(SPConstants.KEY_EXERCISE_PREFERENCE,
	// SPConstants.DEFAULT_EXCERCISE_MINUTES);
	//
	// String waterEffect = "-";
	// if (!enteredExerciseMins.equals("") && enteredExerciseMins.equals("0") ==
	// false)
	// {
	// int exerciseMins = Integer.parseInt(enteredExerciseMins);
	// waterRequirement = waterRequirement + exerciseMins * .1;
	// double waterEffectDouble = convertFromMlsToUserMeasurement((exerciseMins
	// * .1) * 28.4,waterMeasurementUnit, sharedPreferences);
	// waterEffectDouble = Math.round(waterEffectDouble);
	// waterEffect = "+" + waterEffectDouble + waterMeasurementUnit;
	// }
	//
	// waterData.effectExercise = waterEffect;
	//
	// return waterRequirement;
	// }

	public String convertFromTo(String weight, String oldWeightUnit, String newWeightUnit) {

		if (oldWeightUnit == null || oldWeightUnit.equals("")) {
			oldWeightUnit = SPConstants.DEFAULT_WEIGHT_MEASUREMENT;
		}

		// convert the current weight to a double
		double dbWeight = SPConstants.DEFAULT_MALE_WEIGHT;
		try {
			dbWeight = Double.parseDouble(weight);
		} catch (NumberFormatException e) {
			// Log.e(TAG, "", e);
		}

		double newWeight = dbWeight;
		if (oldWeightUnit.equals("st")) {
			if (newWeightUnit.equals("lb")) {
				// convert from stone to pounds
				newWeight = dbWeight * 14;
			} else if (newWeightUnit.equals("kg")) {
				// convert from stone to kiligrams
				newWeight = dbWeight * 6.35;
			}
		} else if (oldWeightUnit.equals("lb")) {
			if (newWeightUnit.equals("st")) {
				// convert from pounds to stone
				newWeight = dbWeight * 0.0714285714;
			} else if (newWeightUnit.equals("kg")) {
				// convert from pounds to kiligrams
				newWeight = dbWeight / 2.2;
			}
		} else if (oldWeightUnit.equals("kg")) {
			if (newWeightUnit.equals("lb")) {
				// convert from kiligrams to pounds
				newWeight = dbWeight * 2.2;
			} else if (newWeightUnit.equals("st")) {
				// convert from kiligrames to stones
				newWeight = dbWeight / 6.35;
			}
		}

		return roundToDecimals(newWeight, 2) + "";
	}

	public static double roundToDecimals(double d, int c) {
		int temp = (int) ((d * Math.pow(10, c)));
		return (((double) temp) / Math.pow(10, c));
	}

	// public String getHeathlyEatingString(SharedPreferences sharedPreferences)
	// {
	// boolean isHeathlyEating =
	// sharedPreferences.getBoolean(SPConstants.KEY_HEALTHY_DIET, true);
	// if (isHeathlyEating) {
	// return "Yes";
	// } else {
	// return "No";
	// }
	//
	// }
	//
	// public String getIllnessString(SharedPreferences sharedPreferences) {
	// boolean isill = sharedPreferences.getBoolean(SPConstants.KEY_ILLNESS,
	// false);
	// if (isill) {
	// return "Yes";
	// } else {
	// return "No";
	// }
	//
	// }
	//
	// public String getPregnancyString(SharedPreferences sharedPreferences) {
	// boolean isPreg =
	// sharedPreferences.getBoolean(SPConstants.KEY_PREGNANCY_PERFERENCE,
	// false);
	// if (isPreg) {
	// return "Yes";
	// } else {
	// return "No";
	// }
	//
	// }
	//
	// public String getClimateString(SharedPreferences sharedPreferences) {
	// String climate =
	// sharedPreferences.getString(SPConstants.KEY_CLIMATE_PREFERENCE, "mod");
	// if (climate.equalsIgnoreCase("moderate")) {
	// climate = "mod";
	// }
	// return climate;
	// }

	// public String getExceriseString(SharedPreferences sharedPreferences) {
	// String excerise =
	// sharedPreferences.getString(SPConstants.KEY_EXERCISE_PREFERENCE,
	// SPConstants.DEFAULT_EXCERCISE_MINUTES);
	// if (excerise.equals("0")) {
	// return "None";
	// } else {
	// return excerise;
	// }
	// }
	//
	// public String getWeightString(SharedPreferences sharedPreferences) {
	// String enteredWeight =
	// sharedPreferences.getString(SPConstants.KEY_WEIGHT_PREFERENCE, "");//
	// SharedPreferenceHelper.AVERAGE_MALE_WEIGHT);
	// String weightUnit =
	// sharedPreferences.getString(SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE,
	// SPConstants.DEFAULT_WEIGHT_MEASUREMENT);
	// return enteredWeight + weightUnit;
	// }
	//
	// public CharSequence getAlcholUnitsString(SharedPreferences
	// sharedPreferences) {
	// String enteredAlcoholUnits =
	// sharedPreferences.getString(SPConstants.KEY_ALCOHOL,
	// SPConstants.DEFAULT_EXCERCISE_MINUTES);
	//
	// if (enteredAlcoholUnits.equals("0")) {
	// return "None";
	// } else {
	// return enteredAlcoholUnits;
	// }
	// }

	/**
	 * Determines the weight of the user. If the weight is not entered then the
	 * gender is used to determine the weight of the user and the average wieght
	 * is used.
	 * 
	 * @param sharedPreferences
	 *            SharedPreferences
	 * @return double
	 */
	// public double getWeight(SharedPreferences sharedPreferences) {
	//
	// String enteredWeight =
	// sharedPreferences.getString(SPConstants.KEY_WEIGHT_PREFERENCE, "");//
	// SharedPreferenceHelper.AVERAGE_MALE_WEIGHT);
	// String selectedGender =
	// sharedPreferences.getString(SPConstants.KEY_GENDER_PREFERENCE,
	// SPConstants.DEFAULT_GENDER);
	//
	// double weight = SPConstants.DEFAULT_MALE_WEIGHT;
	// if (enteredWeight.equals("")) {
	// if (selectedGender.equals("Female")) {
	// weight = SPConstants.DEFAULT_FEMALE_WEIGHT;
	// }
	// } else {
	// // a weight has been entered. So check the weight units
	// try {
	// weight = Double.parseDouble(enteredWeight);
	// } catch (NumberFormatException e) {
	// weight = SPConstants.DEFAULT_MALE_WEIGHT;
	// }
	// /***************************** All Weights are stored in pounds
	// *******************************/
	// String weightUnit = sharedPreferences
	// .getString(SPConstants.KEY_WEIGHT_MEASUREMENT_TYPE,
	// SPConstants.DEFAULT_WEIGHT_MEASUREMENT);
	// if (weightUnit.equals("lb")) {
	// // Log.d(TAG, "Weight units is lb. Not converting");
	// } else if (weightUnit.equals("st")) {
	// // convert stone to lb. 1 stone = 14 lb
	// weight = weight * 14;
	// } else if (weightUnit.equals("kg")) {
	// // convert kg to lb. 1kg = 2.2 lb
	// weight = weight * 2.2;
	// }
	// }
	// return Math.round(weight);
	// }

	public long getTime(String time) {
		Calendar calendar = Calendar.getInstance();

		String hour = time.substring(0, 2);
		String minute = time.substring(3);

		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
		calendar.set(Calendar.MINUTE, Integer.valueOf(minute));
		return calendar.getTimeInMillis();
	}

	// public CharSequence getWeightEffectString(SharedPreferences
	// mSharedPreferences) {
	// return mSharedPreferences.getString(SPConstants.KEY_WEIGHT_WATER_EFFECT,
	// SPConstants.DEFAULT_WATER_A_DAY + "ml");
	// }
	//
	// public CharSequence getExceriseEffectString(SharedPreferences
	// mSharedPreferences) {
	// return
	// mSharedPreferences.getString(SPConstants.KEY_EXERCISE_WATER_EFFECT, "-");
	// }
	//
	// public CharSequence getClimateEffectString(SharedPreferences
	// mSharedPreferences) {
	// return mSharedPreferences.getString(SPConstants.KEY_CLIMATE_WATER_EFFECT,
	// "-");
	// }
	//
	// public CharSequence getAlcholUnitsEffectString(SharedPreferences
	// mSharedPreferences) {
	// return mSharedPreferences.getString(SPConstants.KEY_ALCOHOL_WATER_EFFECT,
	// "-");
	// }
	//
	// public CharSequence getHeathlyEatingEffectString(SharedPreferences
	// mSharedPreferences) {
	// return
	// mSharedPreferences.getString(SPConstants.KEY_HEATHY_DIET_WATER_EFFECT,
	// "-");
	// }
	//
	// public CharSequence getIllnessEffectString(SharedPreferences
	// mSharedPreferences) {
	// return mSharedPreferences.getString(SPConstants.KEY_ILLNESS_WATER_EFFECT,
	// "-");
	// }
	//
	// public CharSequence getPregnancyEffectString(SharedPreferences
	// mSharedPreferences) {
	// return mSharedPreferences.getString(SPConstants.KEY_PREG_WATER_EFFECT,
	// "-");
	// }

	public CharSequence getTotalWaterString(SharedPreferences sharedPreferences) {
		String waterMeasurementUnit = sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);
		return ""
				+ sharedPreferences.getLong(SPConstants.KEY_WATER_MILLS_A_DAY,
						SPConstants.DEFAULT_WATER_A_DAY) + waterMeasurementUnit;
	}

	public String getDrankWaterString(SharedPreferences sharedPreferences) {
		String waterMeasurementUnit = sharedPreferences.getString(
				SPConstants.KEY_WATER_MEASUREMENT_TYPE, SPConstants.DEFAULT_WATER_MEASUREMENT);
		return sharedPreferences.getLong(SPConstants.KEY_WATER_DRANK_TODAY, 0)
				+ waterMeasurementUnit;
	}

}
