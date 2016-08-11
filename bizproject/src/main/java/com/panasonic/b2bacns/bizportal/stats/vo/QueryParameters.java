/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.vo;

/**
 * @author akansha
 * 
 */
public class QueryParameters {

	private String periodWhereSQL;
	private String coreWhereSQL;
	private String periodTableName;
	private String coreTableName;
	private String columnNameSubQuery;
	private String selectOuterParams;
	private String selectInnerParams;
	private String joinForPeriodTable;
	private String joinForCoreTable;
	private String joinCondition;
	private String groupClause;

	private String chronologicalType;
	private String extractSelectParams;
	private String yearForMonthlyAndWeekly;
	private String castYear;
	private String refrigerantName;

	/**
	 * @return the periodWhereSQL
	 */
	public String getPeriodWhereSQL() {
		return periodWhereSQL;
	}

	/**
	 * @param periodWhereSQL
	 *            the periodWhereSQL to set
	 */
	public void setPeriodWhereSQL(String periodWhereSQL) {
		this.periodWhereSQL = periodWhereSQL;
	}

	/**
	 * @return the coreWhereSQL
	 */
	public String getCoreWhereSQL() {
		return coreWhereSQL;
	}

	/**
	 * @param coreWhereSQL
	 *            the coreWhereSQL to set
	 */
	public void setCoreWhereSQL(String coreWhereSQL) {
		this.coreWhereSQL = coreWhereSQL;
	}

	/**
	 * @return the periodTableName
	 */
	public String getPeriodTableName() {
		return periodTableName;
	}

	/**
	 * @param periodTableName
	 *            the periodTableName to set
	 */
	public void setPeriodTableName(String periodTableName) {
		this.periodTableName = periodTableName;
	}

	/**
	 * @return the coreTableName
	 */
	public String getCoreTableName() {
		return coreTableName;
	}

	/**
	 * @param coreTableName
	 *            the coreTableName to set
	 */
	public void setCoreTableName(String coreTableName) {
		this.coreTableName = coreTableName;
	}

	/**
	 * @return the columnNameSubQuery
	 */
	public String getColumnNameSubQuery() {
		return columnNameSubQuery;
	}

	/**
	 * @param columnNameSubQuery
	 *            the columnNameSubQuery to set
	 */
	public void setColumnNameSubQuery(String columnNameSubQuery) {
		this.columnNameSubQuery = columnNameSubQuery;
	}

	/**
	 * @return the selectOuterParams
	 */
	public String getSelectOuterParams() {
		return selectOuterParams;
	}

	/**
	 * @param selectOuterParams
	 *            the selectOuterParams to set
	 */
	public void setSelectOuterParams(String selectOuterParams) {
		this.selectOuterParams = selectOuterParams;
	}

	/**
	 * @return the selectInnerParams
	 */
	public String getSelectInnerParams() {
		return selectInnerParams;
	}

	/**
	 * @param selectInnerParams
	 *            the selectInnerParams to set
	 */
	public void setSelectInnerParams(String selectInnerParams) {
		this.selectInnerParams = selectInnerParams;
	}

	/**
	 * @return the joinForPeriodTable
	 */
	public String getJoinForPeriodTable() {
		return joinForPeriodTable;
	}

	/**
	 * @param joinForPeriodTable
	 *            the joinForPeriodTable to set
	 */
	public void setJoinForPeriodTable(String joinForPeriodTable) {
		this.joinForPeriodTable = joinForPeriodTable;
	}

	/**
	 * @return the joinForCoreTable
	 */
	public String getJoinForCoreTable() {
		return joinForCoreTable;
	}

	/**
	 * @param joinForCoreTable
	 *            the joinForCoreTable to set
	 */
	public void setJoinForCoreTable(String joinForCoreTable) {
		this.joinForCoreTable = joinForCoreTable;
	}

	/**
	 * @return the joinCondition
	 */
	public String getJoinCondition() {
		return joinCondition;
	}

	/**
	 * @param joinCondition
	 *            the joinCondition to set
	 */
	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}

	/**
	 * @return the groupClause
	 */
	public String getGroupClause() {
		return groupClause;
	}

	/**
	 * @param groupClause
	 *            the groupClause to set
	 */
	public void setGroupClause(String groupClause) {
		this.groupClause = groupClause;
	}

	/**
	 * @return the chronologicalType
	 */
	public String getChronologicalType() {
		return chronologicalType;
	}

	/**
	 * @param chronologicalType
	 *            the chronologicalType to set
	 */
	public void setChronologicalType(String chronologicalType) {
		this.chronologicalType = chronologicalType;
	}

	/**
	 * @return the extractSelectParams
	 */
	public String getExtractSelectParams() {
		return extractSelectParams;
	}

	/**
	 * @param extractSelectParams
	 *            the extractSelectParams to set
	 */
	public void setExtractSelectParams(String extractSelectParams) {
		this.extractSelectParams = extractSelectParams;
	}

	/**
	 * @return the yearForMonthlyAndWeekly
	 */
	public String getYearForMonthlyAndWeekly() {
		return yearForMonthlyAndWeekly;
	}

	/**
	 * @param yearForMonthlyAndWeekly
	 *            the yearForMonthlyAndWeekly to set
	 */
	public void setYearForMonthlyAndWeekly(String yearForMonthlyAndWeekly) {
		this.yearForMonthlyAndWeekly = yearForMonthlyAndWeekly;
	}

	/**
	 * @return the castYear
	 */
	public String getCastYear() {
		return castYear;
	}

	/**
	 * @param castYear
	 *            the castYear to set
	 */
	public void setCastYear(String castYear) {
		this.castYear = castYear;
	}

	public String getRefrigerantName() {
		return refrigerantName;
	}

	public void setRefrigerantName(String refrigerantName) {
		this.refrigerantName = refrigerantName;
	}

}
