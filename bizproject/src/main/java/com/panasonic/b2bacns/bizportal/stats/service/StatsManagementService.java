/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;

/**
 * @author akansha
 * 
 */
public interface StatsManagementService {

	/**
	 * This method is used to get details for all the charts.
	 * 
	 * @param statsRequestVO
	 * @return
	 * @throws JsonProcessingException
	 * @throws ParseException
	 * @throws GenericFailureException
	 * @throws JSONException
	 */
	public String getStatsDetails(StatsRequestVO statsRequestVO)
			throws JsonProcessingException, ParseException,
			GenericFailureException, JSONException;

	/**
	 * This method is used to determine the period strategy on the basis of type
	 * and charts and periods.
	 * 
	 * @param requestVO
	 * @return
	 * @throws ParseException 
	 * @throws GenericFailureException 
	 */
	public Map<String, Object> getPeriodStrategy(StatsRequestVO requestVO) throws GenericFailureException, ParseException;

	/**
	 * This method is used to get data for energy consumption graph
	 * 
	 * @param requestVO
	 * @return
	 * @throws JsonProcessingException
	 * @throws GenericFailureException
	 */
	public String getDataForEnergyConsumptionGraph(StatsRequestVO requestVO)
			throws JsonProcessingException, GenericFailureException;
	
	/**
	 * This method is used to get RefrigerantCircuits By GroupIds
	 * 
	 * @param requestVO
	 * @return
	 * @throws JsonProcessingException
	 * @throws GenericFailureException
	 */
	public String getRefrigerantCircuitsByGroupIds(StatsRequestVO requestVO)
			throws JsonProcessingException, GenericFailureException;

	/**
	 * This method is used to get details for Stats Reports for downloadStats
	 * APIs
	 * 
	 * @param requestVO
	 * @return
	 * @throws ParseException 
	 * @throws GenericFailureException 
	 * @throws HibernateException 
	 */
	public List<StatsReportVO> getStatsReportDetails(StatsRequestVO requestVO)
			throws HibernateException, GenericFailureException, ParseException;

	/**
	 * Generates the report and returns the path of the file
	 * 
	 * @param statsList
	 * @param requestVO
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getStatsFilePath(List<StatsReportVO> statsList,
			StatsRequestVO requestVO) throws IOException, URISyntaxException,
			IllegalAccessException, InvocationTargetException;
	
	/**
	 * This method is used to set the period strategy on the basis of type
	 * and charts and periods.
	 * @param requestVO
	 * @throws GenericFailureException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public void setPeriodStrategy(StatsRequestVO requestVO)
			throws GenericFailureException, ParseException,
			NumberFormatException;

}
