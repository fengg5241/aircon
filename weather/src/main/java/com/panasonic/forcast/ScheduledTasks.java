package com.panasonic.forcast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eclipsesource.json.JsonObject;
//import com.panasonic.sourcecode.FIOHourly;
//import com.panasonic.sourcecode.ForecastIO;
import com.github.dvdme.ForecastIOLib.FIOHourly;
import com.github.dvdme.ForecastIOLib.ForecastIO;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private static final Logger logger = Logger.getLogger(ScheduledTasks.class);
    
    private RowMapper<LocationInfo> rowMapper = new LocationInfoRowMapper();
    
    @Autowired
	private JdbcTemplate jdbcTemplate;
    
//    @Scheduled(fixedRate = 5000)
//    @Scheduled(cron="0 0 * * * *")
    public void reportCurrentTime() {
    	logger.info("The time is now " + dateFormat.format(new Date()));
    	getWeatherForcast(getJdbcTemplate());
    }
    
    public void getWeatherForcast(JdbcTemplate jdbcTemplate){
    	List<LocationInfo> locationList = getLocationList(jdbcTemplate);
    	if (locationList == null ||  locationList.isEmpty() ) {
			return;
		}
    	
		ForecastIO fio = new ForecastIO("9c7ca5dbdf371c468f80e38c2cba2900"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
		fio.setExcludeURL("daily,minutely,alerts,flags");             //excluded the minutely and hourly reports from the reply
		
    	for (LocationInfo locationInfo : locationList) {

    		fio.getForecast(locationInfo.getMapLatitude().toString(), locationInfo.getMapLongitude().toString());

    		JsonObject currently = fio.getCurrently();
    		String weather = currently.get("summary").toString().replaceAll("\"", "");
    		String tempStr = currently.get("temperature").toString().replaceAll("\"", "");
    		String temp = Math.round(Double.valueOf(tempStr)) + "";
    		String icon = currently.get("icon").toString().replaceAll("\"", "");
    	    FIOHourly hourly = new FIOHourly(fio);
    	    //In case there is no hourly data available
    	    if(hourly.hours()<0)
    	    	logger.info("No hourly data.");
    	    else {
    	    	logger.info("\nHourly:\n");
    	    	logger.info("\nlocationId:" + locationInfo.getLocationId() + "~~weather:"+weather + "~~temp:" + temp);
    	    }
    	        
    	    //Print hourly data
//    	    for(int i = 0; i<hourly.hours(); i++){
//    	        String [] h = hourly.getHour(i).getFieldsArray();
//    	        System.out.println("Hour #"+(i+1));
//    	        for(int j=0; j<h.length; j++)
//    	            System.out.println(h[j]+": "+hourly.getHour(i).getByKey(h[j]));
//    	        System.out.println("\n");
//    	    }
    	    String currentTimeStr = hourly.getHour(0).getByKey("time");
    	    
    	    Timestamp currentTime = Timestamp.valueOf(currentTimeStr);
    	    //next 3 hours weather
    	    String afterThreeHoursTempStr = "";
    	    for(int i = 1; i<4; i++){
    	    	if (i != 1) {
    	    		afterThreeHoursTempStr += ",";
				}
    	    	afterThreeHoursTempStr += Math.round(Double.valueOf(hourly.getHour(i).getByKey("temperature")));
    	    }
    	    
    	    insertForecastInfo(jdbcTemplate, locationInfo.getLocationId(), weather, temp, icon, currentTime, afterThreeHoursTempStr); 
		}

    }

    private List<LocationInfo> getLocationList(JdbcTemplate jdbcTemplate){
    	// Modified by Ravi to pull weather data from first group level
    	String sql = "SELECT id, latitude, longitude FROM weather_location  "
    			+ "where latitude is not null and longitude is not null order by id desc limit 40";
    	
    	List<LocationInfo> locationList = jdbcTemplate.query(sql, rowMapper);
    	return locationList;
    }
    
	private LocationInfo mapLoginForm(ResultSet rs) throws SQLException {
		// get the row column data
		long locationId = rs.getLong("id");
		Double mapLatitude = rs.getDouble("latitude");
		Double mapLongitude = rs.getDouble("longitude");
		// map to the object
		LocationInfo loginForm = new LocationInfo(locationId,mapLatitude, mapLongitude);
		return loginForm;
	}
	
	private class LocationInfoRowMapper implements RowMapper<LocationInfo> {
		public LocationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapLoginForm(rs);
		}
	}
	
    @Transactional
	private void insertForecastInfo(JdbcTemplate jdbcTemplate, long locationId, String weather, String temp, String icon,
			Timestamp currentTime, String afterThreeHoursTempStr) {
		String insertSQL = "INSERT INTO weather_forecast(location_id, temperature, weather, forecast_time, weather_icon,future_weather)" +
	    					"VALUES ( ?,?,?,?,?,?)";
	    
	    jdbcTemplate.update(insertSQL, new PreparedStatementSetter() {  
		      @Override  
		      public void setValues(PreparedStatement pstmt) throws SQLException {  
		          pstmt.setLong(1, locationId);  
		          pstmt.setString(2, temp);  
		          pstmt.setString(3, weather);  
		          pstmt.setTimestamp(4, currentTime);  
		          pstmt.setString(5, icon);
		          pstmt.setString(6,afterThreeHoursTempStr);
		  }});
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
