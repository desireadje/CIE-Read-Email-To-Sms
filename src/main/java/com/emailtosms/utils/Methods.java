package com.emailtosms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

public class Methods {

	// Augmenter une date jour par jour
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	// Augmenter une date jour par jour
	public static Date addMinutes(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute); // minus number would decrement the minute
		return cal.getTime();
	}

	public Date addMonth(Date date, int minute) {
		Date now = date;
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(now);
		myCal.add(Calendar.MONTH, +minute);
		now = myCal.getTime();
		System.out.println("===========> " + now);
		return now;
	}

	public static String dateNow() {

		String dateNow = null;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		dateNow = sdf.format(date);

		return dateNow;
	}

	public static String dateFormat(String format) {

		String str = null;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		str = sdf.format(date);

		return str;
	}

	public static Date stringToDate(String str) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = null;
		if (str != null) {
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	public static void getLocalDateTime() {

		// Java 8
		System.out.println(LocalDateTime.now().getYear()); // 2015
		System.out.println(LocalDateTime.now().getMonth()); // SEPTEMBER
		System.out.println(LocalDateTime.now().getDayOfMonth()); // 29
		System.out.println(LocalDateTime.now().getHour()); // 7
		System.out.println(LocalDateTime.now().getMinute()); // 36
		System.out.println(LocalDateTime.now().getSecond()); // 51
		System.out.println(LocalDateTime.now().get(ChronoField.MILLI_OF_SECOND)); // 100

	}

}
