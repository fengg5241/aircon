/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.hibernate.HibernateException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;

/**
 * @author Narendra.Kumar
 * 
 */
public interface EfficiencyRankingService {

	public String getEfficiencyRanking(StatsRequestVO requestVO)
			throws JsonProcessingException, ParseException;

	/**
	 * Generates report
	 * 
	 * @param requestVO
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 * @throws GenericFailureException
	 * @throws HibernateException
	 */
	public String getFilePath(StatsRequestVO requestVO) throws IOException,
			URISyntaxException, IllegalAccessException,
			InvocationTargetException, HibernateException,
			GenericFailureException, ParseException;

	/**
	 * 
	 * @param userID
	 * @param userTimezone
	 * @return
	 * @throws GenericFailureException
	 * @throws ParseException
	 * @throws JsonProcessingException
	 */
	public String getEfficiencyOnDashboard(Long userID, String userTimezone)
			throws GenericFailureException, ParseException,
			JsonProcessingException;
}
