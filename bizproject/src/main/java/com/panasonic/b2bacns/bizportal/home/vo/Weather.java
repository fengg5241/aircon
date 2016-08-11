package com.panasonic.b2bacns.bizportal.home.vo;

import java.io.Serializable;

public class Weather implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4033776598769563916L;

	private String temp;
	
	private String weather;
	
	private String time;
	
	private String weatherIcon;
	
	private String future_weather;

	public Weather(String temp, String weather, String time, String weatherIcon, String future_weather) {
		super();
		this.temp = temp;
		this.weather = weather;
		this.time = time;
		this.weatherIcon = weatherIcon;
		this.future_weather = future_weather;
	}
	
	/**
	 * @return the temp
	 */
	public String getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(String temp) {
		this.temp = temp;
	}

	/**
	 * @return the weather
	 */
	public String getWeather() {
		return weather;
	}

	/**
	 * @param weather the weather to set
	 */
	public void setWeather(String weather) {
		this.weather = weather;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the weatherIcon
	 */
	public String getWeatherIcon() {
		return weatherIcon;
	}

	/**
	 * @param weatherIcon the weatherIcon to set
	 */
	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	/**
	 * @return the future_weather
	 */
	public String getFuture_weather() {
		return future_weather;
	}

	/**
	 * @param future_weather the future_weather to set
	 */
	public void setFuture_weather(String future_weather) {
		this.future_weather = future_weather;
	}
}
