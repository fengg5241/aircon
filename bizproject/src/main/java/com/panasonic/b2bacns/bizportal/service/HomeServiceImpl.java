package com.panasonic.b2bacns.bizportal.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.home.vo.HomeCompanySiteVO;
import com.panasonic.b2bacns.bizportal.home.vo.LocationVO;
import com.panasonic.b2bacns.bizportal.home.vo.Weather;
import com.panasonic.b2bacns.bizportal.map.vo.GroupVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;


/**
 * @author fshan
 *
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
	private JdbcTemplate jdbcTemplate;
    
    private RowMapper<Weather> rowMapper = new WeatherRowMapper();
    
    private RowMapper<LocationVO> locationRowMapper = new LocationRowMapper();
    
    private RowMapper<GroupVO> homeSiteRowMapper = new HomeSiteRowMapper();
    
	@Override
	public List<Weather> getForecastInfo(long locationId, String currentTime) {
		Date currentDate = CommonUtil.stringToDate(currentTime,
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		
		Date beforeTwentyHoursDate = getDateBeforeOrAfterHours(currentDate,-20);
		String beforeTwentyHoursDateStr = CommonUtil.dateToString(beforeTwentyHoursDate,
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
		
		String sql = "SELECT * FROM weather_forecast where location_id = ? and forecast_time <= ? and forecast_time >= ? order by id";
		
		List<Weather> weatherList = jdbcTemplate.query(sql, new Object[] { locationId,new Timestamp(currentDate.getTime()),new Timestamp(beforeTwentyHoursDate.getTime()) }, rowMapper);
    	return weatherList;
	}
	
	private class WeatherRowMapper implements RowMapper<Weather> {
		public Weather mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapWeather(rs);
		}
	}
	
	private Weather mapWeather(ResultSet rs) throws SQLException {
		// get the row column data
		String temperature = rs.getString("temperature");
		String weatherStr = rs.getString("weather");
		String time = rs.getString("forecast_time");
		String weatherIcon = rs.getString("weather_icon");
		String futureWeather = rs.getString("future_weather");
		// map to the object
		Weather weather = new Weather(temperature, weatherStr,time,weatherIcon, futureWeather);
		return weather;
	}
	
    /**
     * get date before or after hours
     *
     * @param iHour
     * if get date before hours, iHour is negative number;
     * if get date after hours, iHour is positive number;
     * @see java.util.Calendar#add(int, int)
     * @return Date 
     */
    private Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.HOUR_OF_DAY, iHour);
        return cal.getTime();
    }

	@Override
	public List<LocationVO> getLocationList(Long companyId) {
		String sql = "select wl.id,wl.name from weather_location wl, company_weather_location cwl where wl.id = cwl.location_id and cwl.company_id =" + companyId;
		List<LocationVO> locationList = jdbcTemplate.query(sql, locationRowMapper);
		return locationList;
	}
	
	private class LocationRowMapper implements RowMapper<LocationVO> {
		public LocationVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapLocation(rs);
		}
	}
	
	private LocationVO mapLocation(ResultSet rs) throws SQLException {
		// get the row column data
		long id = rs.getLong("id");
		String name = rs.getString("name");
		// map to the object
		LocationVO location = new LocationVO(id, name);
		return location;
	}

	@Override
	public HomeCompanySiteVO getHomeSiteByCompanyId(Long companyId) {
		String sql = "select c.id companyId,s.svg_file_name,s.svg_name,g.id groupId,g.svg_max_latitude,g.svg_max_longitude,"
				+ "g.svg_min_latitude,g.svg_min_longitude, g.name from companies c "
				+ "left join groups g on c.id = g.company_id "
				+ "left join svg_master s on s.id = c.svg_id where c.id = " + companyId;
		List<GroupVO> siteList = jdbcTemplate.query(sql, homeSiteRowMapper);
		HomeCompanySiteVO homeSite = new HomeCompanySiteVO();
		homeSite.setCompanyId(companyId);
		homeSite.setSites(siteList);
		homeSite.setSvgFileName(siteList.get(0).getSvg());
		return homeSite;
	}
	
	private class HomeSiteRowMapper implements RowMapper<GroupVO> {
		public GroupVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return mapHomeSite(rs);
		}
	}
	
	private GroupVO mapHomeSite(ResultSet rs) throws SQLException {
		// get the row column data
		String svg = rs.getString("svg_file_name");
		String groupName = rs.getString("name");
		Double maxLat = rs.getDouble("svg_max_latitude");
		Double maxLong = rs.getDouble("svg_max_longitude");
		Double minlat = rs.getDouble("svg_min_latitude");
		Double minLong = rs.getDouble("svg_min_longitude");
		Long groupId = rs.getLong("groupId");
		
		// map to the object
		GroupVO group = new GroupVO();
		group.setSvgMaxLatitude(maxLat);
		group.setSvgMaxLongitude(maxLong);
		group.setSvgMinLatitude(minlat);
		group.setSvgMinLongitude(minLong);
		group.setSvg(svg);
		group.setName(groupName);
		group.setGroupId(groupId);
		return group;
	}

}
