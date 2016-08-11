/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author simanchal.patra
 *
 */
@JsonAutoDetect
public class ValidateRC {

	private int powerStatus_support;
	private int temperature_support;
	private int mode_support;
	private int fanSpeed_support;
	private int windDirection_support;
	private int energySaving_support;
	private int Prohibition_MASTER;
	private Collection<String> mode_support_list; // [auto, heat, dry, cool,
													// fan],
	private Collection<Integer> temp_range_auto; // [min,max],
	private Collection<Integer> temp_range_heat; // ": [min,max],
	private Collection<Integer> temp_range_dry; // ": [min,max],
	private Collection<Integer> temp_range_cool; // ":[min,max]",
	private Collection<Integer> temp_range_fan; // ":[min,max]",
	private Collection<String> fan_speed_list; // ":[low, med, high, auto]",
	private Collection<String> windDirection_list; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	private Collection<String> flap_range_auto_heat; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	private Collection<String> flap_range_auto_cool; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	private Collection<String> flap_range_heat; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	private Collection<String> flap_range_dry; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	private Collection<String> flap_range_cool; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	private Collection<String> flap_range_fan; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	//add by shanf
	private Collection<String> flap_range_auto; // ":[F1,F2,F3,F4,F5,Swing,Stop],
	//true means can show remote control dialog as don't have null value
	private boolean validStatus =true;
	//end by shanf

	/**
	 * @return the powerStatus_support
	 */
	public int getPowerStatus_support() {
		return powerStatus_support;
	}

	/**
	 * @param powerStatus_support
	 *            the powerStatus_support to set
	 */
	public void setPowerStatus_support(int powerStatus_support) {
		this.powerStatus_support = powerStatus_support;
	}

	/**
	 * @return the temperature_support
	 */
	public int getTemperature_support() {
		return temperature_support;
	}

	/**
	 * @param temperature_support
	 *            the temperature_support to set
	 */
	public void setTemperature_support(int temperature_support) {
		this.temperature_support = temperature_support;
	}

	/**
	 * @return the mode_support
	 */
	public int getMode_support() {
		return mode_support;
	}

	/**
	 * @param mode_support
	 *            the mode_support to set
	 */
	public void setMode_support(int mode_support) {
		this.mode_support = mode_support;
	}

	/**
	 * @return the fanSpeed_support
	 */
	public int getFanSpeed_support() {
		return fanSpeed_support;
	}

	/**
	 * @param fanSpeed_support
	 *            the fanSpeed_support to set
	 */
	public void setFanSpeed_support(int fanSpeed_support) {
		this.fanSpeed_support = fanSpeed_support;
	}

	/**
	 * @return the windDirection_support
	 */
	public int getWindDirection_support() {
		return windDirection_support;
	}

	/**
	 * @param windDirection_support
	 *            the windDirection_support to set
	 */
	public void setWindDirection_support(int windDirection_support) {
		this.windDirection_support = windDirection_support;
	}

	/**
	 * @return the energySaving_support
	 */
	public int getEnergySaving_support() {
		return energySaving_support;
	}

	/**
	 * @param energySaving_support
	 *            the energySaving_support to set
	 */
	public void setEnergySaving_support(int energySaving_support) {
		this.energySaving_support = energySaving_support;
	}

	/**
	 * @return the prohibition_MASTER
	 */
	public int getProhibition_MASTER() {
		return Prohibition_MASTER;
	}

	/**
	 * @param prohibition_MASTER
	 *            the prohibition_MASTER to set
	 */
	public void setProhibition_MASTER(int prohibition_MASTER) {
		Prohibition_MASTER = prohibition_MASTER;
	}

	/**
	 * @return the mode_support_list
	 */
	public Collection<String> getMode_support_list() {
		return mode_support_list;
	}

	/**
	 * @param mode_support_list
	 *            the mode_support_list to set
	 */
	public void setMode_support_list(Collection<String> mode_support_list) {
		this.mode_support_list = mode_support_list;
	}

	/**
	 * @return the temp_range_auto
	 */
	public Collection<Integer> getTemp_range_auto() {
		return temp_range_auto;
	}

	/**
	 * @param temp_range_auto
	 *            the temp_range_auto to set
	 */
	public void setTemp_range_auto(Collection<Integer> temp_range_auto) {
		this.temp_range_auto = temp_range_auto;
	}

	/**
	 * @return the temp_range_heat
	 */
	public Collection<Integer> getTemp_range_heat() {
		return temp_range_heat;
	}

	/**
	 * @param temp_range_heat
	 *            the temp_range_heat to set
	 */
	public void setTemp_range_heat(Collection<Integer> temp_range_heat) {
		this.temp_range_heat = temp_range_heat;
	}

	/**
	 * @return the temp_range_dry
	 */
	public Collection<Integer> getTemp_range_dry() {
		return temp_range_dry;
	}

	/**
	 * @param temp_range_dry
	 *            the temp_range_dry to set
	 */
	public void setTemp_range_dry(Collection<Integer> temp_range_dry) {
		this.temp_range_dry = temp_range_dry;
	}

	/**
	 * @return the temp_range_cool
	 */
	public Collection<Integer> getTemp_range_cool() {
		return temp_range_cool;
	}

	/**
	 * @param temp_range_cool
	 *            the temp_range_cool to set
	 */
	public void setTemp_range_cool(Collection<Integer> temp_range_cool) {
		this.temp_range_cool = temp_range_cool;
	}

	/**
	 * @return the temp_range_fan
	 */
	public Collection<Integer> getTemp_range_fan() {
		return temp_range_fan;
	}

	/**
	 * @param temp_range_fan
	 *            the temp_range_fan to set
	 */
	public void setTemp_range_fan(Collection<Integer> temp_range_fan) {
		this.temp_range_fan = temp_range_fan;
	}

	/**
	 * @return the fan_speed_list
	 */
	public Collection<String> getFan_speed_list() {
		return fan_speed_list;
	}

	/**
	 * @param fan_speed_list
	 *            the fan_speed_list to set
	 */
	public void setFan_speed_list(Collection<String> fan_speed_list) {
		this.fan_speed_list = fan_speed_list;
	}

	/**
	 * @return the windDirection_list
	 */
	public Collection<String> getWindDirection_list() {
		return windDirection_list;
	}

	/**
	 * @param windDirection_list
	 *            the windDirection_list to set
	 */
	public void setWindDirection_list(Collection<String> windDirection_list) {
		this.windDirection_list = windDirection_list;
	}

	/**
	 * @return the flap_range_auto_heat
	 */
	public Collection<String> getFlap_range_auto_heat() {
		return flap_range_auto_heat;
	}

	/**
	 * @param flap_range_auto_heat
	 *            the flap_range_auto_heat to set
	 */
	public void setFlap_range_auto_heat(Collection<String> flap_range_auto_heat) {
		this.flap_range_auto_heat = flap_range_auto_heat;
	}

	/**
	 * @return the flap_range_auto_cool
	 */
	public Collection<String> getFlap_range_auto_cool() {
		return flap_range_auto_cool;
	}

	/**
	 * @param flap_range_auto_cool
	 *            the flap_range_auto_cool to set
	 */
	public void setFlap_range_auto_cool(Collection<String> flap_range_auto_cool) {
		this.flap_range_auto_cool = flap_range_auto_cool;
	}

	/**
	 * @return the flap_range_heat
	 */
	public Collection<String> getFlap_range_heat() {
		return flap_range_heat;
	}

	/**
	 * @param flap_range_heat
	 *            the flap_range_heat to set
	 */
	public void setFlap_range_heat(Collection<String> flap_range_heat) {
		this.flap_range_heat = flap_range_heat;
	}

	/**
	 * @return the flap_range_dry
	 */
	public Collection<String> getFlap_range_dry() {
		return flap_range_dry;
	}

	/**
	 * @param flap_range_dry
	 *            the flap_range_dry to set
	 */
	public void setFlap_range_dry(Collection<String> flap_range_dry) {
		this.flap_range_dry = flap_range_dry;
	}

	/**
	 * @return the flap_range_cool
	 */
	public Collection<String> getFlap_range_cool() {
		return flap_range_cool;
	}

	/**
	 * @param flap_range_cool
	 *            the flap_range_cool to set
	 */
	public void setFlap_range_cool(Collection<String> flap_range_cool) {
		this.flap_range_cool = flap_range_cool;
	}

	/**
	 * @return the flap_range_fan
	 */
	public Collection<String> getFlap_range_fan() {
		return flap_range_fan;
	}

	/**
	 * @param flap_range_fan
	 *            the flap_range_fan to set
	 */
	public void setFlap_range_fan(Collection<String> flap_range_fan) {
		this.flap_range_fan = flap_range_fan;
	}

	//add by shanf
	/**
	 * @return the flap_range_auto
	 */
	public Collection<String> getFlap_range_auto() {
		return flap_range_auto;
	}

	/**
	 * @param flap_range_auto
	 *            the flap_range_auto to set
	 */
	public void setFlap_range_auto(Collection<String> flap_range_auto) {
		this.flap_range_auto = flap_range_auto;
	}
	//add by shanf
	/**
	 * @return the validStatus
	 */
	public boolean isValidStatus() {
		return validStatus;
	}

	/**
	 * @param validStatus the validStatus to set
	 */
	public void setValidStatus(boolean validStatus) {
		this.validStatus = validStatus;
	}

}
