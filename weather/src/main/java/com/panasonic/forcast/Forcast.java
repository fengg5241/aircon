package com.panasonic.forcast;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.github.dvdme.ForecastIOLib.FIOHourly;
import com.github.dvdme.ForecastIOLib.ForecastIO;

@Component
public class Forcast {

    public static void getWeatherForcast(JdbcTemplate jdbcTemplate){
    	System.out.println("main~~~~~~~~~~");
		ForecastIO fio = new ForecastIO("9c7ca5dbdf371c468f80e38c2cba2900"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
		fio.setExcludeURL("daily,minutely,alerts,flags");             //excluded the minutely and hourly reports from the reply
		fio.getForecast("1.357975", "-9.1500364");

		JsonObject currently = fio.getCurrently();
//		String currentTime = currently.get("time").toString();
		String weather = currently.get("summary").toString();
		String temp = currently.get("temperature").toString();
		String icon = currently.get("icon").toString();
		
	    FIOHourly hourly = new FIOHourly(fio);
	    //In case there is no hourly data available
	    if(hourly.hours()<0)
	        System.out.println("No hourly data.");
	    else
	        System.out.println("\nHourly:\n");
	    //Print hourly data
//	    for(int i = 0; i<hourly.hours(); i++){
//	        String [] h = hourly.getHour(i).getFieldsArray();
//	        System.out.println("Hour #"+(i+1));
//	        for(int j=0; j<h.length; j++)
//	            System.out.println(h[j]+": "+hourly.getHour(i).getByKey(h[j]));
//	        System.out.println("\n");
//	    }
	    long currentTime = hourly.getHour(0).timestamp();
	    
	    //next 3 hours weather
	    JsonArray jsonArray = new JsonArray();
	    
	    for(int i = 1; i<4; i++){
	    	JsonObject jsonObject = new JsonObject();
	    	jsonObject.add("time", hourly.getHour(i).getByKey("time"));
	    	jsonObject.add("temperature", hourly.getHour(i).getByKey("temperature"));
	    	
	    	jsonArray.add(jsonObject);
	    	
	        System.out.println("Hour #"+(i));
	            System.out.println("time: "+hourly.getHour(i).getByKey("time"));
	            System.out.println("temperature: "+hourly.getHour(i).getByKey("temperature"));
	        System.out.println("\n");
	    }
	    
	    String insertSQL = "INSERT INTO weather_forecast(location_id, temperature, weather, forecast_time, weather_icon,future_weather)" +
	    					"VALUES ( ?,?,?,?,?,?)";
	    
	    
	    jdbcTemplate.update(insertSQL,1, temp,weather,currentTime,icon,jsonArray.toString());
	   
    }

}
