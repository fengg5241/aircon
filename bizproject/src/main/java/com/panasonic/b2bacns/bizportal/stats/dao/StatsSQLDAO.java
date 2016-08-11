/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.dao;

import java.text.ParseException;
import java.util.List;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.stats.vo.EnergyConsumptionResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.RefrigerantCircuitResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsResponseVO;

/**
 * 
 * @author akansha
 * 
 */
public interface StatsSQLDAO {

	/**
	 * This method is used to get stats data for the charts as specified in the
	 * StatsTequestVO object properties and return StatsResponseVO objects
	 * 
	 * @param vo
	 * @return
	 * @throws HibernateException
	 * @throws GenericFailureException
	 */
	public StatsResponseVO getStatsDetails(StatsRequestVO vo)
			throws HibernateException, GenericFailureException;

	/**
	 * This method is used to get data for Energy consumption chart for dash
	 * board specified in the StatsTequestVO object properties and return
	 * EnergyConsumptionResponseVO objects
	 * 
	 * @param requestVO
	 * @return
	 */
	public EnergyConsumptionResponseVO getDataForEnergyConsumptionGraph(
			StatsRequestVO requestVO);

	
	/**
	 * 
	 * @param requestVO
	 * @return
	 */
	public RefrigerantCircuitResponseVO getRefrigerantCircuitByGroupIds(
			StatsRequestVO requestVO);

	/**
	 * This method is used to get stats report for the charts as specified in
	 * the StatsRequestVO object properties and return StatsReportVO objects
	 * 
	 * @param requestVO
	 * @return
	 * @throws HibernateException
	 * @throws GenericFailureException
	 * @throws ParseException
	 */
	public List<StatsReportVO> getStatsReport(StatsRequestVO requestVO)
			throws HibernateException, GenericFailureException, ParseException;

	/**
	 * 
	 * @param requestVO
	 * @return
	 */
	public StatsRequestVO setTimeZoneForStatsByGroup(StatsRequestVO requestVO);

}
