package com.sprint22.apps.android.hydration.utils;

import java.util.Calendar;
import java.util.StringTokenizer;

public class DateUtils {

	public static Calendar getCalendarWithTimeSet(String time) {

		if (isValidTimeString(time) == false) {
			return null;
		}

		StringTokenizer tokeniser = new StringTokenizer(time, ":");

		int hours, minutes;
		try {
			hours = Integer.parseInt(tokeniser.nextToken());
			minutes = Integer.parseInt(tokeniser.nextToken());
		} catch (NumberFormatException e) {
			return null;
		}
		return convertToCalendar(hours, minutes);
	}

	public static Calendar convertToCalendar(int hours, int minutes) {
		if (isValidTime(hours, minutes) == false) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, hours);
			calendar.set(Calendar.MINUTE, minutes);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar;
		}
	}

	public static boolean isValidTimeString(String time) {
		if (time == null || time.length() == 0) {
			return false;
		}
		if (time.contains(":") == false) {
			return false;
		}
		return true;
	}

	public static boolean isValidTime(int hours, int minutes) {
		return isValidHourComponent(hours) && isValidMinuteComponent(minutes);
	}

	public static boolean isValidHourComponent(int hour) {
		return (hour >= 0) && (hour <= 24);
	}

	public static boolean isValidMinuteComponent(int minutes) {
		return (minutes >= 0) && (minutes <= 60);
	}
}
