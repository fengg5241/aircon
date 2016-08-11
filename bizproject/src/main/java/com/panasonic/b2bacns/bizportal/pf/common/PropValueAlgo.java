/**
 * 
 */
package com.panasonic.b2bacns.bizportal.pf.common;

import org.apache.commons.lang.StringUtils;

/**
 * @author simanchal.patra
 *
 */
public class PropValueAlgo {

	public static String computeMode(String a2_1, String a2_2) {

		String mode = null;

		if (StringUtils.isNotBlank(a2_1) && StringUtils.isNotBlank(a2_2)) {

			String temp = a2_1.concat(a2_2);

			switch (temp) {
			case "-1-1":
				mode = Constants.UNDECIDED;
				break;
			case "11":
				mode = Constants.HEAT;
				break;
			case "22":
				mode = Constants.COOL;
				break;
			case "33":
				mode = Constants.FAN;
				break;
			case "44":
				mode = Constants.DRY;
				break;
			case "10":
				mode = Constants.AUTO_COOL;
				break;
			case "20":
				mode = Constants.AUTO_HEAT;
				break;
			case "30":
				mode = Constants.AUTO_UNDECIDED;
				break;

			default:
				break;
			}
		}
		return mode;
	}

	public static String computeFanSpeed(String a6x_1, String a6x_2) {

		String fs = null;

		if (StringUtils.isNotBlank(a6x_1)) {

			String temp = a6x_2 != null ? a6x_1.concat(a6x_2) : a6x_1;

			switch (temp) {
			case "0":
				fs = Constants.STOP;
				break;
			case "1":
				fs = Constants.AUTO;
				break;
				//change by shanf start
			case "201":
				fs = Constants.HIGH; // HH
				break;
			case "202":
				fs = Constants.MEDIUM; // H
				break;
			case "203":
				fs = Constants.LOW; // L
				break;
			case "204":
				//change by shanf end
				fs = Constants.VERY_LOW; // LL
				break;
			default:
				break;
			}
		}
		return fs;
	}

	public static String computeFlapPosition(String a7x_1, String a7x_2) {

		String fp = null;

		if (StringUtils.isNotBlank(a7x_1)) {

			String temp = a7x_2 != null ? a7x_1.concat(a7x_2) : a7x_1;

			switch (temp) {

			case "1":
				fp = Constants.SWING;
				break;
			case "21":
				fp = Constants.F1;
				break;
			case "22":
				fp = Constants.F2;
				break;
			case "23":
				fp = Constants.F3;
				break;
			case "24":
				fp = Constants.F4;
				break;
			case "25":
				fp = Constants.F5;
				break;
			case "0":
				fp = Constants.STOP;
				break;
			default:
				break;
			}
		}
		return fp;
	}

}
