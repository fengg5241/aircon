package com.panasonic.b2bacns.bizportal.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Utility class for Time Zone specific queries
 * 
 * @author shobhit.singh
 * @author simanchal.patra
 * 
 */
public class TimeZoneUtil {

	/**
	 * 
	 * @param caTimeZoneStr
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String convertDateToTimeZone(String caTimeZoneStr,
			String dateTime) throws ParseException {

		TimeZone caTimeZone = TimeZone.getTimeZone(caTimeZoneStr);

		SimpleDateFormat formatter = new SimpleDateFormat(
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date actionPerformedDate = formatter.parse(dateTime);

		// To TimeZone America/New_York
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		sdfAmerica.setTimeZone(caTimeZone);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(actionPerformedDate);
		calendar.setTimeZone(caTimeZone);

		// Do not delete
		// Wrong! It will print the date with system timezone
		// System.out.println("Date : " + calendar.getTime());
		// Correct
		// System.out.println("Date + Formatter : "
		// + sdfAmerica.format(calendar.getTime()));

		return sdfAmerica.format(calendar.getTime());

	}

	public static String convertDateToTimeZone(String caTimeZoneStr,
			Date dateTime) throws ParseException {

		TimeZone caTimeZone = TimeZone.getTimeZone(caTimeZoneStr);

		// SimpleDateFormat formatter = new SimpleDateFormat(
		// BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		// formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		// Date actionPerformedDate = formatter.parse(dateTime);

		// To TimeZone America/New_York
		SimpleDateFormat newTZoneDateFormat = new SimpleDateFormat(
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		newTZoneDateFormat.setTimeZone(caTimeZone);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateTime);
		calendar.setTimeZone(caTimeZone);

		// Do not delete
		// Wrong! It will print the date with system timezone
		// System.out.println("Date : " + calendar.getTime());
		// Correct
		// System.out.println("Date + Formatter : "
		// + sdfAmerica.format(calendar.getTime()));

		return newTZoneDateFormat.format(calendar.getTime());

	}

	public static String convertDateToTimeZone(String caTimeZoneStr,
			Date dateTime, String dateFormatStr) throws ParseException {

		TimeZone caTimeZone = TimeZone.getTimeZone(caTimeZoneStr);

		// SimpleDateFormat formatter = new SimpleDateFormat(
		// BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		// formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		// Date actionPerformedDate = formatter.parse(dateTime);

		// To TimeZone America/New_York
		SimpleDateFormat newTZoneDateFormat = new SimpleDateFormat(
				dateFormatStr);
		newTZoneDateFormat.setTimeZone(caTimeZone);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateTime);
		calendar.setTimeZone(caTimeZone);

		// Do not delete
		// Wrong! It will print the date with system timezone
		// System.out.println("Date : " + calendar.getTime());
		// Correct
		// System.out.println("Date + Formatter : "
		// + sdfAmerica.format(calendar.getTime()));

		return newTZoneDateFormat.format(calendar.getTime());

	}

	/**
	 * Return provided date for requested timezone
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @param timezoneID
	 * @return
	 */
	public static String getDateTimeInRequestedTimezone(String dateStr,
			String dateFormat, String timezoneID) {

		Date date = CommonUtil.stringToDate(dateStr, dateFormat);
		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Set the formatter to use a different timezone
		formatter.setTimeZone(TimeZone.getTimeZone(timezoneID));
		dateStr = formatter.format(date);

		return dateStr;
	}

}
