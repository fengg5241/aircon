package com.panasonic.b2bacns.bizportal.stats.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StatsCalTest {

	public static void main(String[] args) {

		Calendar cal = new GregorianCalendar(Locale.GERMANY);
		cal.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		cal.set(GregorianCalendar.DATE, 1);
		cal.set(GregorianCalendar.MONTH, GregorianCalendar.MAY);
		cal.set(GregorianCalendar.YEAR, 2015);
		cal.setMinimalDaysInFirstWeek(6);
		System.out.println(cal.getTime());
		System.out.println("Week of month :" + cal.get(GregorianCalendar.WEEK_OF_MONTH));

	}

}
