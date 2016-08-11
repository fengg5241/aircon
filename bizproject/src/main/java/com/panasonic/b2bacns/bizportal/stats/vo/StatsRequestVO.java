/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author akansha
 * 
 */
public class StatsRequestVO {

	private String id;

	private String idType;

	private String startDate;

	private String endDate;

	private String type;

	private String parameter;

	private String parameterOption;

	private String period;

	private String chartType;

	private String chart;

	private String offSet;

	private List<Long> idList = new ArrayList<Long>();

	private Map<String, Object> PeriodStrategyMap = new HashMap<String, Object>();

	private String apiCallFor;

	private int grouplevel;

	private TreeMap<Integer, TreeMap<Object, String>> categories = new TreeMap<Integer, TreeMap<Object, String>>();

	private String userTimeZone;

	private String fileType;

	private String addCustName;
	
	private String apiType ;

	/**
	 * @return the apiCallFor
	 */
	public String getApiCallFor() {
		return apiCallFor;
	}

	/**
	 * @param apiCallFor
	 *            the apiCallFor to set
	 */
	public void setApiCallFor(String apiCallFor) {
		this.apiCallFor = apiCallFor;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the parameterOption
	 */
	public String getParameterOption() {
		return parameterOption;
	}

	/**
	 * @param parameterOption
	 *            the parameterOption to set
	 */
	public void setParameterOption(String parameterOption) {
		this.parameterOption = parameterOption;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return the chartType
	 */
	public String getChartType() {
		return chartType;
	}

	/**
	 * @param chartType
	 *            the chartType to set
	 */
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	/**
	 * @return the chart
	 */
	public String getChart() {
		return chart;
	}

	/**
	 * @param chart
	 *            the chart to set
	 */
	public void setChart(String chart) {
		this.chart = chart;
	}

	/**
	 * @return the offSet
	 */
	public String getOffSet() {
		return offSet;
	}

	/**
	 * @param offSet
	 *            the offSet to set
	 */
	public void setOffSet(String offSet) {
		this.offSet = offSet;
	}

	/**
	 * @return the idList
	 */
	public List<Long> getIdList() {
		return idList;
	}

	/**
	 * @param idList
	 *            the idList to set
	 */
	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	/**
	 * @return the periodStrategyMap
	 */
	public Map<String, Object> getPeriodStrategyMap() {
		return PeriodStrategyMap;
	}

	/**
	 * @param periodStrategyMap
	 *            the periodStrategyMap to set
	 */
	public void setPeriodStrategyMap(Map<String, Object> periodStrategyMap) {
		PeriodStrategyMap = periodStrategyMap;
	}

	/**
	 * @return the categories
	 */
	public TreeMap<Integer, TreeMap<Object, String>> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(
			TreeMap<Integer, TreeMap<Object, String>> categories) {
		this.categories = categories;
	}

	/**
	 * @return the grouplevel
	 */
	public int getGrouplevel() {
		return grouplevel;
	}

	/**
	 * @param label
	 *            the grouplevel to set
	 */
	public void setGrouplevel(int grouplevel) {
		this.grouplevel = grouplevel;
	}

	/**
	 * @return the userTimeZone
	 */
	public String getUserTimeZone() {
		return userTimeZone;
	}

	/**
	 * @param userTimeZone
	 *            the userTimeZone to set
	 */
	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getAddCustName() {
		return addCustName;
	}

	public void setAddCustName(String addCustName) {
		this.addCustName = addCustName;
	}

	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}
	
	

}
