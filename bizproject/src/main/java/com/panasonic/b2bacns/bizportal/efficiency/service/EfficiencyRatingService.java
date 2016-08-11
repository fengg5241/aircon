/**
 * 
 */
package com.panasonic.b2bacns.bizportal.efficiency.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRatingRequestVO;
import com.panasonic.b2bacns.bizportal.efficiency.vo.EfficiencyRatingResponseVO;

/**
 * @author Narendra.Kumar
 * 
 */
public interface EfficiencyRatingService {
	public List<EfficiencyRatingResponseVO> getEfficiencyRatingByGroupsAndLevel(
			EfficiencyRatingRequestVO efficiencyRatingRequestVO);

	public List<EfficiencyRatingResponseVO> getEfficiencyRating(
			EfficiencyRatingRequestVO efficiencyRatingRequestVO);

	public List<?> getEfficiencyDetailByGroupAndLevel(
			EfficiencyRatingRequestVO efficiencyRatingRequestVO);

	/**
	 * Generate excel report for Efficiency Rating
	 * 
	 * @param efficiencyRatingResponseVOs
	 * @return
	 */
	public String generateEfficiencyRatingExcelReport(
			List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs);

	/**
	 * Generate csv report for Efficiency Rating
	 * 
	 * @param efficiencyRatingResponseVOs
	 * @return
	 */
	public String generateEfficiencyRatingCsvReport(
			List<EfficiencyRatingResponseVO> efficiencyRatingResponseVOs);

}
