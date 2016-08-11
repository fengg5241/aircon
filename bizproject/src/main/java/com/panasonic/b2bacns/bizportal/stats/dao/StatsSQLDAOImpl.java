/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.stats.service.DatePrcocessUtil;
import com.panasonic.b2bacns.bizportal.stats.service.StatsJsonBuilder;
import com.panasonic.b2bacns.bizportal.stats.service.StatsQueryBuilder;
import com.panasonic.b2bacns.bizportal.stats.service.StatsReportVOGenerator;
import com.panasonic.b2bacns.bizportal.stats.vo.EnergyConsumptionResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.QueryParameters;
import com.panasonic.b2bacns.bizportal.stats.vo.RefrigerantCircuitResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.RefrigerantCircuitVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsReportVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsResponseVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 * 
 */
@Repository
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class StatsSQLDAOImpl implements StatsSQLDAO {

	@Autowired
	private StatsQueryBuilder statsQueryBuilder;

	@Autowired
	private StatsJsonBuilder statsJsonBuilder;

	@Autowired
	private SQLDAO sqldao;

	@Autowired
	private StatsReportVOGenerator statsReportVOGenerator;

	private static final String SERIES_EFFICIENCY = "Efficiency";
	private static final String SERIES_SETTEMP = "Setting Temp";
	private static final String SERIES_RATED = "Rated";
	private static final String SERIES_CURRENT = "Current";
	private static final String SERIES_OUTDOORTEMP = "Outdoor Temperature";
	private static final String SERIES_ROOMTEMP = "Room Temp";
	private static final String SERIES_DIFFTEMP = "Different Temp";
	private static final String SERIES_ON = "ThermoStat On";
	private static final String SERIES_OFF = "ThermoStat Off";

	private static final StringBuffer SQL_GET_STATS_ACCUMULATED = new StringBuffer(
			"Select concat(fnouter.supplygroupname,'-',fnouter.supplygroupid) as name,%s ,g.path_name,c.name as companyname ")
			.append("from usp_getindoorunits_supplylevelgroupname(:ids,:label) fnouter  ")
			.append("join groups g on fnouter.supplygroupid  = g.id ")
			.append("left join companies c on c.id = g.company_id ")
			.append("left outer join ( ")
			.append("Select %s  ")
			.append("from usp_getindoorunits_supplylevelgroupname(:ids,:label) fn inner join indoorunits idu on idu.id = fn.indoorunitid   ")
			.append("%s  ")
			.append("%s          ")
			.append("Group by %s  ")
			.append(") T ")
			.append("On fnouter.supplygroupname = t.supplygroupname ")
			.append("and (%s) ")
			.append("group by fnouter.supplygroupname ,g.path_name,c.name,fnouter.supplygroupid order by fnouter.supplygroupname ");

	private static final StringBuffer SQL_GET_STATS_ACCUMULATED_PAST24HOURS = new StringBuffer(
			"Select concat(fnouter.supplygroupname,'-',fnouter.supplygroupid) as name,%s,min(fromtime)  fromtime ,max(totime) totime ,g.path_name,c.name as companyname ")
			.append("from usp_getindoorunits_supplylevelgroupname(:ids,:label) fnouter  ")
			.append("join groups g on fnouter.supplygroupid  = g.id ")
			.append("left join companies c on c.id = g.company_id ")
			.append("left outer join ( ")
			.append("Select %s ,case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),")
			.append(" ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime  , case when max(totime) is null then ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end  totime ")
			.append("from usp_getindoorunits_supplylevelgroupname(:ids,:label) fn join groups g on  fn.supplygroupid = g.id   left join companies c on c.id = g.company_id ")
			.append(" left outer join timezonemaster tzmo on g.timezone = tzmo.id  left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name   ")
			.append("%s  ")
			.append("%s          ")
			.append("Group by %s  ")
			.append(") T ")
			.append("On fnouter.supplygroupname = t.supplygroupname ")
			.append("and (%s) ")
			.append("group by fnouter.supplygroupname ,g.path_name,c.name,fnouter.supplygroupid order by fnouter.supplygroupname ");

	private static final StringBuffer SQL_GET_STATS_AIRCON_GROUP = new StringBuffer(
			"Select concat(idu.name,'-',fn.indoorunitid) as name,%s,g.path_name,c.name as companyname ")
			.append(" from usp_getindoorunits_supplygroupname(:ids) fn inner join indoorunits idu on idu.id = fn.indoorunitid   ")
			.append(" join groups g on  fn.groupid = g.id  ")
			.append(" left join companies c on c.id = g.company_id ")
			.append(" %s ")
			.append(" %s ")
			.append(" Group by %s fn.indoorunitid ,idu.name,g.path_name,c.name order by substring(idu.name,1,length(idu.name)-length(substring(idu.name from '\\d+')) ),cast(substring(idu.name from '\\d+') as bigint) ");

	private static final StringBuffer SQL_GET_STATS_AIRCON_GROUP_PAST24HOURS = new StringBuffer(
			"Select concat(idu.name,'-',fn.indoorunitid) as name,%s,case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),")
			.append(" ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime  , case when max(totime) is null then ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end  totime ")
			.append(" ,g.path_name,c.name as companyname ")
			.append(" from usp_getindoorunits_supplygroupname(:ids) fn inner join indoorunits idu on idu.id = fn.indoorunitid   ")
			.append(" join groups g on  fn.groupid = g.id  ")
			.append(" left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id  left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name")
			.append(" %s ")
			.append(" %s ")
			.append(" Group by %s fn.indoorunitid ,idu.name,g.path_name,c.name order by substring(idu.name,1,length(idu.name)-length(substring(idu.name from '\\d+')) ),cast(substring(idu.name from '\\d+') as bigint) ");

	private static final StringBuffer SQL_GET_STATS_AIRCON_UNIT = new StringBuffer(
			"Select  concat(idu.name,'-',idu.id) as name ,%s ,case when g.path_name is null then site.path_name else g.path_name end path_name,c.name as companyname")
			.append(" FROM %s idu ")
			.append(" left join  groups g on idu.group_id =  g.id  ")
			.append(" left outer join groups site on site.uniqueid = idu.siteid ")
			.append(" left join companies c on c.id = case when g.company_id is null then site.company_id else g.company_id end")
			.append(" %s ")
			.append(" %s ")
			.append("where idu.id in (:ids)")
			.append(" Group by %s idu.id,idu.name ,case when g.path_name is null then site.path_name else g.path_name end,c.name,idu.id order by substring(idu.name,1,length(idu.name)-length(substring(idu.name from '\\d+')) ),cast(substring(idu.name from '\\d+') as bigint)");

	private static final StringBuffer SQL_GET_STATS_AIRCON_UNIT_PAST24HOURS = new StringBuffer(
			"Select  concat(idu.name,'-',idu.id) as name ,%s,case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime  ,")
			.append(" case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end ,g.path_name,c.name as companyname")
			.append(" FROM %s idu ")
			.append(" left join  groups g on idu.siteid = g.uniqueid  ")
			.append(" left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id  left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name")
			.append(" %s ")
			.append(" %s ")
			.append("where idu.id in (:ids)")
			.append(" Group by %s idu.id,idu.name ,g.path_name,c.name,idu.id order by substring(idu.name,1,length(idu.name)-length(substring(idu.name from '\\d+')) ),cast(substring(idu.name from '\\d+') as bigint)");

	private static final StringBuffer SQL_GET_REFRIGERANTCIRCUIT_BY_GROUPIDS = new StringBuffer(
			"Select  distinct a.name,rminner.linenumber,rminner.refrigerantid,odu.refrigcircuitgroupoduid,rminner.refid from ")
			.append(" (Select distinct uniqueid from usp_groupparentchilddata(:ids) where groupcategoryid = 2) fn ")
			.append(" join refrigerantmaster rminner on fn.uniqueid = rminner.siteid ")
			.append(" left join outdoorunits odu on rminner.refid = odu.refid ")
			.append(" and odu.parentid is null ")
			.append(" Left join adapters a on a.id = rminner.adapterid  ");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_INDOORSUNIS = new StringBuffer(
			"Select %s ,g.path_name,c.name as companyname,odu.type")
			.append(" from refrigerantmaster rm ")
			.append(" left outer join outdoorunits odu on rm.refid = odu.refid and odu.parentid is null ")
			.append(" left outer  join indoorunits idu on odu.id =  idu.outdoorunit_id  ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id  ")
			.append("%s ")
			.append("%s  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by a.name,rm.linenumber,odu.refid,odu.refrigcircuitgroupoduid,g.path_name,c.name,rm.refrigerantid,odu.type order by refname");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_INDOORSUNIS_PAST24HOURS = new StringBuffer(
			"Select %s,case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end totime ")
			.append(" ,g.path_name,c.name as companyname,odu.type")
			.append(" from refrigerantmaster rm ")
			.append(" left outer join outdoorunits odu on rm.refid = odu.refid and odu.parentid is null ")
			.append(" left outer  join indoorunits idu on odu.id =  idu.outdoorunit_id  ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id  ")
			.append(" left outer join timezonemaster tzmo on g.timezone = tzmo.id left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append("%s ")
			.append("%s  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by a.name,rm.linenumber,odu.refid,odu.refrigcircuitgroupoduid,g.path_name,c.name,rm.refrigerantid,odu.type order by refname");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_BY_REFRIGERANTIDS = new StringBuffer(
			"Select %s ,g.path_name,c.name as companyname,odut.type")
			.append(" from refrigerantmaster rm ")
			.append(" left outer join (Select distinct odut.refid,odut.refrigcircuitgroupoduid,odut.type ")
			.append(" from outdoorunits odut where  odut.parentid is null) odut ")
			.append(" on rm.refid = odut.refid ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id  ")
			.append("%s ")
			.append("%s  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by a.name,rm.linenumber,odut.refid,odut.refrigcircuitgroupoduid,g.path_name,c.name,odut.type,rm.refrigerantid order by refname");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_BY_REFRIGERANTIDS_PAST24HOURS = new StringBuffer(
			"Select %s,case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end totime ")
			.append(" ,g.path_name,c.name as companyname,odut.type")
			.append(" from refrigerantmaster rm ")
			.append(" left outer join (Select distinct odut.refid,odut.refrigcircuitgroupoduid,odut.type ")
			.append(" from outdoorunits odut where odut.parentid is null) odut ")
			.append(" on rm.refid = odut.refid ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id  ")
			.append(" left outer join timezonemaster tzmo on g.timezone = tzmo.id left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append("%s ")
			.append("%s  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by a.name,rm.linenumber,odut.refid,odut.refrigcircuitgroupoduid,g.path_name,c.name,odut.type,rm.refrigerantid order by refname");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_FOR_GROUPS = new StringBuffer(
			"Select distinct concat(fn.supplygroupname,'-',fn.supplygroupid) as name,%s ,g.path_name,c.name as companyname")
			.append(" from (Select distinct supplygroupid, refrigerantid,supplygroupname from usp_getrefrigerant_suplylevelgroupname(:ids,:label)) fn ")
			.append(" Left outer join refrigerantmaster rm  on fn.refrigerantid = rm.refid ")
			.append(" Left Join groups g on  fn.supplygroupid = g.id ")
			.append(" left join companies c on c.id = g.company_id ")
			.append("%s ")
			.append("%s  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("group  by fn.supplygroupname,g.path_name,c.name,fn.supplygroupid %s order by name ");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_FOR_GROUPS_PAST24HOURS = new StringBuffer(
			"Select distinct concat(fn.supplygroupname,'-',fn.supplygroupid) as name,%s ,case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end totime ")
			.append(" ,g.path_name,c.name as companyname")
			.append(" from (Select distinct supplygroupid, refrigerantid,supplygroupname from usp_getrefrigerant_suplylevelgroupname(:ids,:label)) fn ")
			.append(" Left outer join refrigerantmaster rm  on fn.refrigerantid = rm.refid ")
			.append(" Left Join groups g on  fn.supplygroupid = g.id ")
			.append(" left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name")
			.append("%s ")
			.append("%s  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("group  by fn.supplygroupname,g.path_name,c.name,fn.supplygroupid %s order by name ");

	private static final StringBuffer SQL_GET_TIMEZONETIME_BYGROUP = new StringBuffer(
			"select Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:MI:SS TZ'))) as timestamp)) fromtime ,")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:MI:SS TZ'))) as timestamp)) totime ")
			.append(" from groups g left outer join timezonemaster tzmo on g.timezone = tzmo.id  left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append(" where g.id in (:ids) group by g.id ");

	private static final StringBuffer SQL_GET_TIMEZONETIME_BYINDOOR = new StringBuffer(
			"select Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:MI:SS TZ'))) as timestamp)) fromtime , ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:MI:SS TZ'))) as timestamp)) totime ")
			.append(" FROM indoorunits idu  left join  groups g on idu.group_id =  g.id left outer join timezonemaster tzmo on g.timezone = tzmo.id  ")
			.append(" left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name where idu.id in (:ids) group by idu.id");

	private static final StringBuffer SQL_GET_TIMEZONETIME_BYREFRIGERANT = new StringBuffer(
			"select Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:MI:SS TZ'))) as timestamp)) fromtime ,")
			.append("  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:MI:SS TZ'))) as timestamp)) totime ")
			.append(" from refrigerantmaster rm  left outer join outdoorunits odu on rm.refid = odu.refid and odu.parentid is null  ")
			.append(" left outer  join indoorunits idu on odu.id =  idu.outdoorunit_id   Left Join groups g on  rm.siteid = g.uniqueid  ")
			.append(" left outer join timezonemaster tzmo on g.timezone = tzmo.id  left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append(" where rm.refid in (:ids) group by rm.refid ");

	private static final String INDOORUNITS = "indoorunits";
	private static final String OUTDOORUNITS = "outdoorunits";
	private static final String INDOORUNIT = "indoorunit";
	private static final String OUTDOORUNIT = "outdoorunit";
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.StatsSQLDAO#getStatsDetails
	 * (com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public StatsResponseVO getStatsDetails(StatsRequestVO requestVO)
			throws HibernateException, GenericFailureException {

		System.out.println(requestVO.getPeriodStrategyMap());

		String finalSQL = BizConstants.EMPTY_STRING;
		Map<Integer, TreeMap<Integer, Object>> processMapForChronological = new HashMap<Integer, TreeMap<Integer, Object>>();
		List<String> categories = new ArrayList<String>();

		Map<String, String> groupMap = new HashMap<String, String>();

		List<Float> list = new ArrayList<Float>();

		StatsResponseVO responseVO = new StatsResponseVO();
		QueryParameters parameters = new QueryParameters();

		statsQueryBuilder.createSQLQuery(requestVO, parameters);

		switch (requestVO.getType()) {

		case BizConstants.STATISTICS_ACCUMULATED:

			finalSQL = getFinalQueryForAccumulated(requestVO, parameters);

			break;

		case BizConstants.STATISTICS_CHRONOLOGICAL:

			finalSQL = getFinalQueryForChronological(requestVO, parameters);

			processMapForChronological = DatePrcocessUtil
					.getProcessedDateMap(requestVO);

			Map<Integer, TreeMap<Integer, Object>> map = processMapForChronological;

			for (Map.Entry<Integer, TreeMap<Integer, Object>> entry : map
					.entrySet()) {

				TreeMap<Integer, Object> innerMap = entry.getValue();

				for (Map.Entry<Integer, Object> entry2 : innerMap.entrySet()) {

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_THISWEEK)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.RANGE_WEEK)) {

						Long timeInMilliSeconds = (Long) entry2.getValue();

						Calendar cal = CommonUtil
								.getCalendarFromMilliseconds(timeInMilliSeconds);
						categories.add(cal.get(Calendar.DAY_OF_MONTH) + "/"
								+ (cal.get(Calendar.MONTH) + 1) + "/"
								+ cal.get(Calendar.YEAR));

					} else if (StringUtils.equalsIgnoreCase(
							requestVO.getPeriod(), BizConstants.PERIOD_TODAY)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.RANGE_DAY)) {

						Long timeInMilliSeconds = (Long) entry2.getValue();

						Calendar cal = CommonUtil
								.getCalendarFromMilliseconds(timeInMilliSeconds);

						// Modified By Ravi
						categories
								.add((cal.get(Calendar.HOUR_OF_DAY) < 10 ? BizConstants.ZERO
										+ cal.get(Calendar.HOUR_OF_DAY)
										: cal.get(Calendar.HOUR_OF_DAY))
										+ BizConstants.CONCATE_TYPE_COLON
										+ BizConstants.ZERO
										+ BizConstants.ZERO
										+ BizConstants.EMPTY_SPACE
										+ BizConstants.DASH_STRING
										+ BizConstants.EMPTY_SPACE
										+ (cal.get(Calendar.HOUR_OF_DAY) < 9 ? BizConstants.ZERO
												+ (cal.get(Calendar.HOUR_OF_DAY) + 1)
												: (cal.get(Calendar.HOUR_OF_DAY) + 1))
										+ BizConstants.CONCATE_TYPE_COLON
										+ BizConstants.ZERO + BizConstants.ZERO);

					} else {
						categories.add(String.valueOf(entry2.getValue()));
					}
					list.add(null);

				}
			}

			responseVO.setCategories(categories);

			break;

		}

		int size = list.size();

		List<?> resultList = sqldao.executeSQLSelect(finalSQL,
				requestVO.getPeriodStrategyMap());

		boolean isCategoryForPast24Created = false;

		Calendar fromCal = null;

		Calendar toCal = null;

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();

			Iterator<?> itrForPast24Hour = resultList.iterator();

			while (itr.hasNext()) {

				Object[] rowdata = (Object[]) itr.next();

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					int fromDateIndex = 0;

					int toDateIndex = 0;

					if (StringUtils.equalsIgnoreCase(requestVO.getType(),
							BizConstants.STATISTICS_ACCUMULATED)) {

						if (StringUtils.equalsIgnoreCase(
								requestVO.getApiCallFor(),
								BizConstants.STATS_API_CALL_BY_AIRCON)
								&& StringUtils.equalsIgnoreCase(
										requestVO.getIdType(),
										BizConstants.ID_TYPE_INDOOR)) {

							fromDateIndex = rowdata.length - 4;

							toDateIndex = rowdata.length - 3;

						} else {

							fromDateIndex = rowdata.length - 5;

							toDateIndex = rowdata.length - 4;

						}

					} else {

						fromDateIndex = rowdata.length - 2;

						toDateIndex = rowdata.length - 1;

						if (rowdata[fromDateIndex] != null
								&& !isCategoryForPast24Created) {

							while (itrForPast24Hour.hasNext()) {

								Object[] rowData1 = (Object[]) itrForPast24Hour
										.next();

								if (rowData1[fromDateIndex] != null) {

									Calendar fromDateTimeTemp = CommonUtil
											.convertStringToCalendarWithDateFormat(
													rowData1[fromDateIndex]
															.toString(),
													BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

									if (fromCal == null) {

										fromCal = CommonUtil
												.convertStringToCalendarWithDateFormat(
														rowData1[fromDateIndex]
																.toString(),
														BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

									}

									fromDateTimeTemp.getTime();
									fromCal.getTime();

									if (fromCal.compareTo(fromDateTimeTemp) > 0) {

										fromCal = fromDateTimeTemp;
									}

								}

								if (rowData1[toDateIndex] != null) {

									Calendar toDateTimeTemp = CommonUtil
											.convertStringToCalendarWithDateFormat(
													rowData1[toDateIndex]
															.toString(),
													BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

									if (toCal == null) {
										toCal = CommonUtil
												.convertStringToCalendarWithDateFormat(
														rowData1[toDateIndex]
																.toString(),
														BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
									}

									toCal.getTime();
									toDateTimeTemp.getTime();

									if (toCal.compareTo(toDateTimeTemp) < 0) {

										toCal = toDateTimeTemp;
									}

								}

							}

							System.out.println("fromCal :" + fromCal.getTime());
							System.out.println("toCal :" + toCal.getTime());

							if (StringUtils.equalsIgnoreCase(
									requestVO.getType(),
									BizConstants.STATISTICS_ACCUMULATED)) {

								if (StringUtils.equalsIgnoreCase(
										requestVO.getPeriod(),
										BizConstants.PERIOD_24HOURS)) {

									requestVO
											.setStartDate(CommonUtil.getCalendarWithDateFormat(
													fromCal.getTimeInMillis(),
													BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));
									requestVO
											.setEndDate(CommonUtil.getCalendarWithDateFormat(
													toCal.getTimeInMillis(),
													BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

									isCategoryForPast24Created = true;
								}

							}

							if (StringUtils.equalsIgnoreCase(
									requestVO.getType(),
									BizConstants.STATISTICS_CHRONOLOGICAL)) {

								processMapForChronological = DatePrcocessUtil
										.getCalendarHoursForPast24Hours(
												CommonUtil
														.getCalendarWithDateFormat(
																fromCal.getTimeInMillis(),
																BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
												CommonUtil
														.getCalendarWithDateFormat(
																toCal.getTimeInMillis(),
																BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

								Map<Integer, TreeMap<Integer, Object>> map = processMapForChronological;

								for (Map.Entry<Integer, TreeMap<Integer, Object>> entry1 : map
										.entrySet()) {

									TreeMap<Integer, Object> innerMap = map
											.get(entry1.getKey());

									for (Map.Entry<Integer, Object> entry2 : innerMap
											.entrySet()) {

										Long timeInMilliSeconds = (Long) entry2
												.getValue();

									Calendar cal = CommonUtil
											.getCalendarFromMilliseconds(timeInMilliSeconds);
									// Modified By Ravi
									String monthString = BizConstants.EMPTY_STRING;
									int month = cal.get(Calendar.MONTH) + 1;
									if (month < 10) {
										StringBuilder sb = new StringBuilder();
										sb.append(BizConstants.ZERO);
										sb.append(month);
										monthString = sb.toString();
									} else {
										StringBuilder sb = new StringBuilder();
										sb.append(month);
										monthString = sb.toString();
									}

									String hourString = BizConstants.EMPTY_STRING;
									int hour = cal.get(Calendar.HOUR_OF_DAY);
									if (hour < 10) {
										StringBuilder sb = new StringBuilder();
										sb.append(BizConstants.ZERO);
										sb.append(hour);
										hourString = sb.toString();
									} else {
										StringBuilder sb = new StringBuilder();
										sb.append(hour);
										hourString = sb.toString();
									}

									String hourStringPlusOne = BizConstants.EMPTY_STRING;
									int hourPlusOne = cal
											.get(Calendar.HOUR_OF_DAY) + 1;
									if (hourPlusOne < 10) {
										StringBuilder sb = new StringBuilder();
										sb.append(BizConstants.ZERO);
										sb.append(hourPlusOne);
										hourStringPlusOne = sb.toString();
									} else {
										StringBuilder sb = new StringBuilder();
										sb.append(hourPlusOne);
										hourStringPlusOne = sb.toString();
									}

									String dayString = BizConstants.EMPTY_STRING;
									int day = cal.get(Calendar.DAY_OF_MONTH);
									if (day < 10) {
										StringBuilder sb = new StringBuilder();
										sb.append(BizConstants.ZERO);
										sb.append(day);
										dayString = sb.toString();
									} else {
										StringBuilder sb = new StringBuilder();
										sb.append(day);
										dayString = sb.toString();
									}

									categories.add(hourString
											+ BizConstants.CONCATE_TYPE_COLON
											+ BizConstants.ZERO
											+ BizConstants.ZERO
											+ BizConstants.EMPTY_SPACE
											+ BizConstants.DASH_STRING
											+ BizConstants.EMPTY_SPACE
											+ hourStringPlusOne
											+ BizConstants.CONCATE_TYPE_COLON
											+ BizConstants.ZERO
											+ BizConstants.ZERO
											+ BizConstants.EMPTY_SPACE
											+ dayString
											+ BizConstants.CONCATE_TYPE_SLASH
											+ monthString);
									// End of modification By Ravi
									list.add(null);

									}
								}

								size = list.size();

								System.out.println("categories :" + categories);

								isCategoryForPast24Created = true;

								size = categories.size();

							}
						}

					}

				}

				statsJsonBuilder.populateStatsResponseVo(rowdata, requestVO,
						responseVO, size, processMapForChronological, groupMap);

			}
		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			throw new GenericFailureException(customErrorMessage);
		}

		if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
				BizConstants.STATS_API_CALL_BY_AIRCON)
				|| (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_GROUP))) {

			if (StringUtils.equalsIgnoreCase(requestVO.getType(),
					BizConstants.STATISTICS_CHRONOLOGICAL)) {

				for (Map<String, Object> map : responseVO.getSeries()) {

					if (map.containsKey(BizConstants.KEY_JSON_RESPONSE_NAME)) {

						String name = (String) map
								.get(BizConstants.KEY_JSON_RESPONSE_NAME);

						if (name != null) {

							String splitNames[] = name.split("-");

							String parameter = BizConstants.EMPTY_STRING;

							if (StringUtils.equalsIgnoreCase(
									splitNames[(splitNames.length - 1)],
									SERIES_CURRENT)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_RATED)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_OUTDOORTEMP)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_EFFICIENCY)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_SETTEMP)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_ROOMTEMP)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_DIFFTEMP)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_ON)
									|| StringUtils
											.startsWithIgnoreCase(
													splitNames[(splitNames.length - 1)],
													SERIES_OFF)) {

								parameter = splitNames[(splitNames.length - 1)];

								name = name.substring(0, name.lastIndexOf("-"));

								map.put(BizConstants.KEY_JSON_RESPONSE_NAME,
										(StringUtils.isNotBlank(name) ? (StringUtils
												.isNotBlank(name.substring(0,
														name.lastIndexOf("-"))) ? name
												.substring(0,
														name.lastIndexOf("-"))
												: null)
												+ "-" + parameter
												: null));

							} else {

								map.put(BizConstants.KEY_JSON_RESPONSE_NAME,
										(StringUtils.isNotBlank(name) ? name
												.substring(0,
														name.lastIndexOf("-"))
												: null));

							}

						}

					}

				}

			}

			if (StringUtils.equalsIgnoreCase(requestVO.getType(),
					BizConstants.STATISTICS_ACCUMULATED)) {

				List<String> newCategory = new ArrayList<String>();

				for (String name : responseVO.getCategories()) {

					if (name != null) {

						String splitNames[] = name.split("-");

						String parameter = BizConstants.EMPTY_STRING;

						if (StringUtils.equalsIgnoreCase(
								splitNames[(splitNames.length - 1)], SERIES_ON)
								|| StringUtils.equalsIgnoreCase(
										splitNames[(splitNames.length - 1)],
										SERIES_OFF)) {

							parameter = splitNames[(splitNames.length - 1)];

							name = name.substring(0, name.lastIndexOf("-"));

							newCategory
									.add((StringUtils.isNotBlank(name) ? (StringUtils
											.isNotBlank(name.substring(0,
													name.lastIndexOf("-"))) ? name
											.substring(0, name.lastIndexOf("-"))
											: null)
											+ "-" + parameter
											: null));

						} else {

							newCategory
									.add((StringUtils.isNotBlank(name) ? (StringUtils
											.isNotBlank(name.substring(0,
													name.lastIndexOf("-"))) ? name
											.substring(0, name.lastIndexOf("-"))
											: null)
											: null));
						}

					}

				}

				responseVO.setCategories(newCategory);

			}

		}

		return responseVO;

	}

	/**
	 * This method is used to get final query for all the charts for type
	 * accumulated
	 * 
	 * @param requestVO
	 * @param parameters
	 * @return
	 */
	private String getFinalQueryForAccumulated(StatsRequestVO requestVO,
			QueryParameters parameters) {

		String finalSQL = BizConstants.EMPTY_STRING;

		String iduByGroupSql = "idu.group_id =  g.id";

		String iduBySiteIdSql = "idu.siteid =  g.uniqueid";

		if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_GROUP)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_GROUP)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.POWER_CONSUMPTION)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.DIFF_TEMPERATURE)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.WORKING_HOURS)) {

					parameters.setGroupClause(parameters.getGroupClause()
							.concat(" fn.supplygroupname "));

					parameters.setSelectInnerParams(parameters
							.getSelectInnerParams().concat(
									" , fn.supplygroupname "));

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

						finalSQL = String.format(
								SQL_GET_STATS_ACCUMULATED_PAST24HOURS
										.toString(), parameters
										.getSelectOuterParams(), parameters
										.getSelectInnerParams(), parameters
										.getJoinForPeriodTable(), parameters
										.getJoinForCoreTable(), parameters
										.getGroupClause(), parameters
										.getJoinCondition());

					} else {

						finalSQL = String.format(
								SQL_GET_STATS_ACCUMULATED.toString(),
								parameters.getSelectOuterParams(),
								parameters.getSelectInnerParams(),
								parameters.getJoinForPeriodTable(),
								parameters.getJoinForCoreTable(),
								parameters.getGroupClause(),
								parameters.getJoinCondition());
					}

				} else if (StringUtils.equalsIgnoreCase(
						requestVO.getParameter(), BizConstants.CAPACITY)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.EFFICIENCY)) {

					String efficiency_groupby = BizConstants.EMPTY_STRING;

					if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
							BizConstants.EFFICIENCY)) {

						String efficiency_Query_outer_uppersql = "Select name, avg(efficiency) efficiency, path_name,companyname from (";

						String efficiency_Query_outer_uppersql_past24hour = "Select name, avg(efficiency) efficiency,min(fromtime)  fromtime ,max(totime) totime, path_name,companyname from (";

						String efficiency_Query_outer_belowsql = ") result group  by name,path_name,companyname order by name";

						String efficiency_query = efficiency_Query_outer_uppersql
								+ SQL_GET_STATS_REFRIGERANT_FOR_GROUPS
										.toString()
								+ efficiency_Query_outer_belowsql;

						String efficiency_query_past24hour = efficiency_Query_outer_uppersql_past24hour
								+ SQL_GET_STATS_REFRIGERANT_FOR_GROUPS_PAST24HOURS
										.toString()
								+ efficiency_Query_outer_belowsql;

						if (StringUtils.isNotBlank(parameters
								.getJoinForPeriodTable())
								&& StringUtils.isNotBlank(parameters
										.getJoinForCoreTable())) {

							efficiency_groupby = ",pc.refid,pcm.refid";

						} else if (StringUtils.isNotBlank(parameters
								.getJoinForPeriodTable())) {

							efficiency_groupby = ",pcm.refid";

						} else if (StringUtils.isNotBlank(parameters
								.getJoinForCoreTable())) {

							efficiency_groupby = ",pc.refid";

						}

						if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_24HOURS)) {

							finalSQL = String.format(
									efficiency_query_past24hour,
									parameters.getSelectInnerParams(),
									parameters.getJoinForPeriodTable(),
									parameters.getJoinForCoreTable(),
									efficiency_groupby);

						} else {

							finalSQL = String.format(efficiency_query,
									parameters.getSelectInnerParams(),
									parameters.getJoinForPeriodTable(),
									parameters.getJoinForCoreTable(),
									efficiency_groupby);
						}

					} else if (StringUtils.equalsIgnoreCase(
							requestVO.getParameter(), BizConstants.CAPACITY)) {

						if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_24HOURS)) {

							finalSQL = String.format(
									SQL_GET_STATS_REFRIGERANT_FOR_GROUPS_PAST24HOURS
											.toString(), parameters
											.getSelectInnerParams(), parameters
											.getJoinForPeriodTable(),
									parameters.getJoinForCoreTable(),
									efficiency_groupby);

						} else {

							finalSQL = String.format(
									SQL_GET_STATS_REFRIGERANT_FOR_GROUPS
											.toString(), parameters
											.getSelectInnerParams(), parameters
											.getJoinForPeriodTable(),
									parameters.getJoinForCoreTable(),
									efficiency_groupby);
						}

					}

				}

				requestVO.getPeriodStrategyMap().put(BizConstants.KEY_LABEL,
						requestVO.getGrouplevel());

			} else if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_AIRCON)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					finalSQL = String.format(
							SQL_GET_STATS_AIRCON_GROUP_PAST24HOURS.toString(),
							parameters.getSelectInnerParams(),
							parameters.getJoinForPeriodTable(),
							parameters.getJoinForCoreTable(),
							parameters.getGroupClause());

				} else {

					finalSQL = String.format(
							SQL_GET_STATS_AIRCON_GROUP.toString(),
							parameters.getSelectInnerParams(),
							parameters.getJoinForPeriodTable(),
							parameters.getJoinForCoreTable(),
							parameters.getGroupClause());
				}
			}

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_INDOOR)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				finalSQL = String.format(
						SQL_GET_STATS_AIRCON_UNIT_PAST24HOURS.toString(),
						parameters.getSelectInnerParams(), INDOORUNITS,
						parameters.getJoinForPeriodTable(),
						parameters.getJoinForCoreTable(),
						parameters.getGroupClause());

				finalSQL = finalSQL.replaceAll(iduByGroupSql, iduBySiteIdSql);

			} else {

				finalSQL = String.format(SQL_GET_STATS_AIRCON_UNIT.toString(),
						parameters.getSelectInnerParams(), INDOORUNITS,
						parameters.getJoinForPeriodTable(),
						parameters.getJoinForCoreTable(),
						parameters.getGroupClause());
			}

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_OUTDOOR)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				finalSQL = String.format(
						SQL_GET_STATS_AIRCON_UNIT_PAST24HOURS.toString(),
						parameters.getSelectInnerParams(), OUTDOORUNITS,
						parameters.getJoinForPeriodTable(),
						parameters.getJoinForCoreTable(),
						parameters.getGroupClause());

			} else {

				finalSQL = String.format(SQL_GET_STATS_AIRCON_UNIT.toString(),
						parameters.getSelectInnerParams(), OUTDOORUNITS,
						parameters.getJoinForPeriodTable(),
						parameters.getJoinForCoreTable(),
						parameters.getGroupClause());
			}

			finalSQL = finalSQL.replaceAll(iduByGroupSql, iduBySiteIdSql);

			finalSQL = finalSQL.replaceAll(INDOORUNIT, OUTDOORUNIT);

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.STATISTICS_REFRIGERANT)) {

			if (StringUtils.contains(parameters.getSelectInnerParams(),
					"pcmindoorunit_id")) {

				parameters.setSelectInnerParams(parameters
						.getSelectInnerParams().replace(
								",pcm.indoorunit_id as pcmindoorunit_id",
								BizConstants.EMPTY_STRING));

			}

			if (StringUtils.contains(parameters.getSelectInnerParams(),
					"pcindoorunit_id")) {

				parameters.setSelectInnerParams(parameters
						.getSelectInnerParams().replace(
								",pc.indoorunit_id as pcindoorunit_id",
								BizConstants.EMPTY_STRING));

			}

			if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
					BizConstants.POWER_CONSUMPTION)
					|| StringUtils.equalsIgnoreCase(requestVO.getParameter(),
							BizConstants.DIFF_TEMPERATURE)) {
				// Modified by ravi
				StringBuilder refrigerantNameSQL = new StringBuilder(
						"(COALESCE(a.name,'') || '-Link' || COALESCE(rm.linenumber,'') || '-RC' || COALESCE(rm.refrigerantid,0) || '-Addr '|| COALESCE(odu.refrigcircuitgroupoduid,'')) as  refname,");

				refrigerantNameSQL.append(parameters.getSelectInnerParams());

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					finalSQL = String.format(
							SQL_GET_STATS_REFRIGERANT_INDOORSUNIS_PAST24HOURS
									.toString(), refrigerantNameSQL, parameters
									.getJoinForPeriodTable(), parameters
									.getJoinForCoreTable());

				} else {

					finalSQL = String.format(
							SQL_GET_STATS_REFRIGERANT_INDOORSUNIS.toString(),
							refrigerantNameSQL,
							parameters.getJoinForPeriodTable(),
							parameters.getJoinForCoreTable());

				}

			} else {
				// Modified by ravi
				StringBuilder refrigerantNameSQL = new StringBuilder(
						"(COALESCE(a.name,'') || '-Link' || COALESCE(rm.linenumber,'') || '-RC' || COALESCE(rm.refrigerantid,0) || '-Addr '|| COALESCE(odut.refrigcircuitgroupoduid,'')) as  refname,");

				refrigerantNameSQL.append(parameters.getSelectInnerParams());

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					finalSQL = String
							.format(SQL_GET_STATS_REFRIGERANT_BY_REFRIGERANTIDS_PAST24HOURS
									.toString(),
									refrigerantNameSQL,
									StringUtils.isNotBlank(parameters
											.getJoinForPeriodTable()) ? parameters
											.getJoinForPeriodTable().replace(
													".id", ".refrigerantid")
											: BizConstants.EMPTY_STRING,
									StringUtils.isNotBlank(parameters
											.getJoinForCoreTable()) ? (!StringUtils.equalsIgnoreCase(
											requestVO.getPeriod(),
											BizConstants.PERIOD_24HOURS) ? parameters
											.getJoinForCoreTable().replace(
													".id", ".refrigerantid")
											: parameters.getJoinForCoreTable())
											: BizConstants.EMPTY_STRING);

				} else {

					finalSQL = String
							.format(SQL_GET_STATS_REFRIGERANT_BY_REFRIGERANTIDS
									.toString(),
									refrigerantNameSQL,
									StringUtils.isNotBlank(parameters
											.getJoinForPeriodTable()) ? parameters
											.getJoinForPeriodTable().replace(
													".id", ".refrigerantid")
											: BizConstants.EMPTY_STRING,
									StringUtils.isNotBlank(parameters
											.getJoinForCoreTable()) ? (!StringUtils.equalsIgnoreCase(
											requestVO.getPeriod(),
											BizConstants.PERIOD_24HOURS) ? parameters
											.getJoinForCoreTable().replace(
													".id", ".refrigerantid")
											: parameters.getJoinForCoreTable())
											: BizConstants.EMPTY_STRING);

				}

				finalSQL = finalSQL
						.replace(INDOORUNIT + "_id", "refrigerantid");

				finalSQL = finalSQL.replace("idu.", "odut.");

			}

		}

		return finalSQL;
	}

	/**
	 * This method is used to get final query for all the charts for type
	 * chronological
	 * 
	 * @param requestVO
	 * @param parameters
	 * @return
	 */
	private String getFinalQueryForChronological(StatsRequestVO requestVO,
			QueryParameters parameters) {

		String finalSQL = "Select %s , %s , %s from (%s) T group by %s";

		String iduByGroupSql = "idu.group_id =  g.id";

		String iduBySiteIdSql = "idu.siteid = g.uniqueid";

		String minMaxForGroups = BizConstants.EMPTY_STRING;
		String orderByForPast24hours = BizConstants.EMPTY_STRING;

		if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
				BizConstants.PERIOD_24HOURS)) {
			minMaxForGroups = ",min(fromtime)  fromtime ,max(totime) totime ";
			orderByForPast24hours = " order by logtime ";
		}

		if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_GROUP)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_GROUP)
					|| StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_ENERY_CONSUMPTION)) {

				String supplyGroupParamInSelect = BizConstants.EMPTY_STRING;
				String supplyGroupIdInGroupBy = BizConstants.EMPTY_STRING;

				if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.POWER_CONSUMPTION)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.DIFF_TEMPERATURE)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.WORKING_HOURS)) {

					supplyGroupParamInSelect = StringUtils.equalsIgnoreCase(
							requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_ENERY_CONSUMPTION) ? parameters
							.getChronologicalType() + " as period "

							: " concat(supplygroupname,'-',supplygroupid) ";

					supplyGroupIdInGroupBy = ",supplygroupid";

				} else {

					supplyGroupParamInSelect = StringUtils.equalsIgnoreCase(
							requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_ENERY_CONSUMPTION) ? parameters
							.getChronologicalType() + " as period "

							: " concat(supplygroupname,'-',supplygroupid) ";

					supplyGroupIdInGroupBy = ",supplygroupid";
				}

				String supplyGroupInGroupBY = StringUtils.equalsIgnoreCase(
						requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_ENERY_CONSUMPTION) ? BizConstants.EMPTY_STRING
						: ", supplygroupname";

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_THISWEEK)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_WEEK)) {

					supplyGroupParamInSelect = StringUtils.equalsIgnoreCase(
							requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_ENERY_CONSUMPTION) ? " cast("
							+ parameters.getChronologicalType()
							+ " as date)"
							+ " as period "
							: " concat(supplygroupname,'-',supplygroupid) ";

					finalSQL = String
							.format(finalSQL,
									supplyGroupParamInSelect,
									" cast("
											+ parameters.getChronologicalType()
											+ " as date)",
									parameters
											.getSelectOuterParams()
											.concat(",path_name,companyname")
											.concat(parameters.getCastYear()
													.replace("pcm.", ""))
											.concat(minMaxForGroups),
									parameters.getJoinForPeriodTable().concat(
											parameters.getJoinForCoreTable()),
									" cast("
											+ parameters.getChronologicalType()
											+ " as date)"
											+ (StringUtils.equalsIgnoreCase(
													requestVO.getApiCallFor(),
													BizConstants.STATS_API_CALL_BY_ENERY_CONSUMPTION) ? " ,period "
													: "")
											+ supplyGroupInGroupBY
											+ parameters
													.getYearForMonthlyAndWeekly()
													.replace("pcm.", "")
													.concat(",path_name,companyname ")
													.concat(supplyGroupIdInGroupBy)
													.concat(orderByForPast24hours));

				} else if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_TODAY)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_24HOURS)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_DAY)) {

					finalSQL = String.format(
							finalSQL,
							supplyGroupParamInSelect,
							parameters.getChronologicalType(),
							parameters
									.getSelectOuterParams()
									.concat(",path_name,companyname")
									.concat(parameters.getCastYear().replace(
											"pcm.", ""))
									.concat(minMaxForGroups),
							parameters.getJoinForPeriodTable().concat(
									parameters.getJoinForCoreTable()),
							parameters.getChronologicalType()
									+ supplyGroupInGroupBY
									+ parameters.getYearForMonthlyAndWeekly()
											.replace("pcm.", "")
											.concat(",path_name,companyname ")
											.concat(supplyGroupIdInGroupBy)
											.concat(orderByForPast24hours));

				} else {

					finalSQL = String.format(
							finalSQL,
							supplyGroupParamInSelect,
							"cast (" + parameters.getChronologicalType()
									+ " as bigint)",
							parameters
									.getSelectOuterParams()
									.concat(",path_name,companyname")
									.concat(parameters.getCastYear().replace(
											"pcm.", "")),
							parameters.getJoinForPeriodTable().concat(
									parameters.getJoinForCoreTable()),
							parameters.getChronologicalType()
									+ supplyGroupInGroupBY
									+ parameters.getYearForMonthlyAndWeekly()
											.replace("pcm.", "")
											.concat(",path_name,companyname")
											.concat(supplyGroupIdInGroupBy));

				}

			} else if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_AIRCON)) {

				finalSQL = parameters.getJoinForPeriodTable().concat(
						parameters.getJoinForCoreTable().concat(
								orderByForPast24hours));

				finalSQL = finalSQL
						.replaceAll("All", BizConstants.EMPTY_STRING);
			}

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_INDOOR)) {

			finalSQL = parameters.getJoinForPeriodTable().concat(
					parameters.getJoinForCoreTable().concat(
							orderByForPast24hours));

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_OUTDOOR)) {

			finalSQL = parameters.getJoinForPeriodTable().concat(
					parameters.getJoinForCoreTable().concat(
							orderByForPast24hours));

			finalSQL = finalSQL.replaceAll(iduByGroupSql, iduBySiteIdSql);

			finalSQL = finalSQL.replaceAll(INDOORUNIT, OUTDOORUNIT);

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.STATISTICS_REFRIGERANT)) {

			String oduType = BizConstants.EMPTY_STRING;

			if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
					BizConstants.WORKING_HOURS)) {
				oduType = ",odutype";

			}

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_THISWEEK)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.RANGE_WEEK)) {

				finalSQL = String.format(
						finalSQL,
						"refname",
						" cast(" + parameters.getChronologicalType()
								+ " as date)" + oduType,
						parameters
								.getSelectOuterParams()
								.concat(",path_name,companyname ")
								.concat(parameters.getCastYear().replace(
										"pcm.", "")).concat(minMaxForGroups),
						parameters.getJoinForPeriodTable().concat(
								parameters.getJoinForCoreTable()),
						" cast("
								+ parameters.getChronologicalType()
								+ " as date)"
								+ ",refname"
								+ parameters.getYearForMonthlyAndWeekly()
										.replace("pcm.", "")
										.concat(",path_name,companyname")
										.concat(oduType)
										.concat(orderByForPast24hours));

			} else if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.RANGE_DAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_TODAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

				finalSQL = String.format(
						finalSQL,
						"refname",
						parameters.getChronologicalType() + oduType,
						parameters
								.getSelectOuterParams()
								.concat(",path_name,companyname ")
								.concat(parameters.getCastYear().replace(
										"pcm.", "")).concat(minMaxForGroups),
						parameters.getJoinForPeriodTable().concat(
								parameters.getJoinForCoreTable()),
						parameters.getChronologicalType()
								+ ",refname"
								+ parameters.getYearForMonthlyAndWeekly()
										.replace("pcm.", "")
										.concat(",path_name,companyname")
										.concat(oduType)
										.concat(orderByForPast24hours));

			} else {

				finalSQL = String.format(
						finalSQL,
						"refname",
						"cast (" + parameters.getChronologicalType()
								+ " as bigint)" + oduType,
						parameters
								.getSelectOuterParams()
								.concat(",path_name,companyname ")
								.concat(parameters.getCastYear().replace(
										"pcm.", "")),
						parameters.getJoinForPeriodTable().concat(
								parameters.getJoinForCoreTable()),
						parameters.getChronologicalType()
								+ ",refname"

								+ parameters.getYearForMonthlyAndWeekly()
										.replace("pcm.", "")
										.concat(",path_name,companyname")
										.concat(oduType));

			}

		}

		return finalSQL;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.StatsSQLDAO#
	 * getDataForEnergyConsumptionGraph
	 * (com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public EnergyConsumptionResponseVO getDataForEnergyConsumptionGraph(
			StatsRequestVO requestVO) {

		EnergyConsumptionResponseVO responseVO = new EnergyConsumptionResponseVO();
		String finalSQL = BizConstants.EMPTY_STRING;
		Map<Integer, TreeMap<Integer, Object>> processMapForChronological = null;
		List<String> categories = new ArrayList<String>();
		QueryParameters parameters = new QueryParameters();
		List<Double> list = new ArrayList<Double>();
		String StrIdList = requestVO.getPeriodStrategyMap()
				.get(BizConstants.KEY_IDS).toString();

		statsQueryBuilder.createSQLQuery(requestVO, parameters);

		finalSQL = getFinalQueryForChronological(requestVO, parameters);

		processMapForChronological = DatePrcocessUtil
				.getProcessedDateMap(requestVO);

		Map<Integer, TreeMap<Integer, Object>> map = processMapForChronological;

		for (Map.Entry<Integer, TreeMap<Integer, Object>> entry1 : map
				.entrySet()) {

			TreeMap<Integer, Object> innerMap = entry1.getValue();

			for (Map.Entry<Integer, Object> entry2 : innerMap.entrySet()) {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_THISWEEK)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_WEEK)) {

					Long timeInMilliSeconds = (long) entry2.getValue();

					Calendar cal = CommonUtil
							.getCalendarFromMilliseconds(timeInMilliSeconds);
					// Added by Seshu.
					categories.add(cal.get(Calendar.DAY_OF_MONTH) + "/"
							+ (cal.get(Calendar.MONTH) + 1) + "/"
							+ cal.get(Calendar.YEAR));

				} else {
					categories.add(String.valueOf(entry2.getValue()));
				}
				list.add(null);

			}
		}

		responseVO.setDates(categories);

		responseVO.getDates().add(responseVO.getDates().size(),
				BizConstants.STATS_ENERGY_CONSUMPTION_TODAY);

		Double[] emptyArrTotal = new Double[list.size() + 1];

		Arrays.fill(emptyArrTotal, null);

		List<Double> emptylistPCSum = Arrays.asList(emptyArrTotal);

		responseVO.setTotal_consumption(emptylistPCSum);

		Double[] emptyArrAvg = new Double[list.size()];

		Arrays.fill(emptyArrAvg, null);

		List<Double> emptylistPCAvg = Arrays.asList(emptyArrAvg);

		responseVO.setAverage_consumption(emptylistPCAvg);

		List<?> resultList = sqldao.executeSQLSelect(finalSQL,
				requestVO.getPeriodStrategyMap());

		Integer avgCount = null;
		Double avgPowerConsumption = null;

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();

			while (itr.hasNext()) {

				Object[] rowdata = (Object[]) itr.next();

				statsJsonBuilder.populateEnergyConsumptionResponseVO(rowdata,
						requestVO, responseVO, processMapForChronological);
			}

			if ((responseVO.getAverage_consumption().size() == 1 && responseVO
					.getAverage_consumption().get(0) != null)
					|| (responseVO.getAverage_consumption().size() > 1)) {

				avgCount = new Integer(0);
				avgPowerConsumption = new Double(0.0d);

				for (Double avg : responseVO.getTotal_consumption()) {
					if (avg != null) {
						avgPowerConsumption += avg;
						avgCount++;
					}
				}

			}

		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			throw new GenericFailureException(customErrorMessage);
		}

		emptyArrAvg = new Double[1];

		if (avgPowerConsumption != null && avgCount != null && avgCount != 0) {

			Double average = avgPowerConsumption / avgCount;
			Arrays.fill(emptyArrAvg, null);
			if (average != null) {

				Arrays.fill(emptyArrAvg,
						CommonUtil.getFormattedValueUpToTwoDecimal(average));
			}
		} else {

			Arrays.fill(emptyArrAvg, null);
		}

		list = Arrays.asList(emptyArrAvg);

		list.size();

		responseVO.setAverage_consumption(list);

		requestVO.getPeriodStrategyMap().clear();

		Calendar today = CommonUtil.setUserTimeZone(Calendar.getInstance(),
				requestVO.getUserTimeZone());

		requestVO.getPeriodStrategyMap().put(
				BizConstants.KEY_START_RESIDUAL_DATE + 1,
				CommonUtil.dateToString(today.getTime(),
						BizConstants.DATE_FORMAT_YYYYMMDD));

		requestVO.getPeriodStrategyMap().put(
				BizConstants.KEY_END_RESIDUAL_DATE + 1,
				CommonUtil.dateToString(today.getTime(),
						BizConstants.DATE_FORMAT_YYYYMMDD));

		requestVO.setPeriod(BizConstants.RANGE_WEEK);

		requestVO.getPeriodStrategyMap().put(BizConstants.KEY_IDS, StrIdList);

		parameters = new QueryParameters();

		finalSQL = BizConstants.EMPTY_STRING;

		statsQueryBuilder.createSQLQuery(requestVO, parameters);

		finalSQL = getFinalQueryForChronological(requestVO, parameters);

		resultList.clear();

		resultList = sqldao.executeSQLSelect(finalSQL,
				requestVO.getPeriodStrategyMap());

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();

			while (itr.hasNext()) {

				Object[] rowdata = (Object[]) itr.next();
				if (rowdata[1] != null) {
					// modifed by ravi
					responseVO
							.getTotal_consumption()
							.set(responseVO.getTotal_consumption().size() - 1,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowdata[3] == null ? 0.0
													: rowdata[3]));
				}

			}

		}

		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.StatsSQLDAO#
	 * getRefrigerantCircuitByGroupIds
	 * (com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public RefrigerantCircuitResponseVO getRefrigerantCircuitByGroupIds(
			StatsRequestVO requestVO) {

		RefrigerantCircuitResponseVO responseVO = null;

		List<?> resultList = sqldao.executeSQLSelect(
				SQL_GET_REFRIGERANTCIRCUIT_BY_GROUPIDS.toString(),
				requestVO.getPeriodStrategyMap());

		// Refrigerant Circuit format -
		// <CA Name>-<Line No>-<RefrigerantID>-Addr <S-link Address>

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();

			responseVO = new RefrigerantCircuitResponseVO();

			responseVO
					.setRefrigerantCircuits(new ArrayList<RefrigerantCircuitVO>());
			RefrigerantCircuitVO refrigerantCircuitVO = null;
			while (itr.hasNext()) {
				refrigerantCircuitVO = new RefrigerantCircuitVO();
				Object[] rowdata = (Object[]) itr.next();
				String caName = BizConstants.EMPTY_STRING;
				String lineNo = BizConstants.EMPTY_STRING;
				String refrigerantID = BizConstants.EMPTY_STRING;
				String slinkAddress = BizConstants.EMPTY_STRING;

				if (rowdata[0] != null && rowdata[1] != null) {
					if (rowdata[0] != null) {

						caName = rowdata[0].toString();
					}
					if (rowdata[1] != null) {

						lineNo = rowdata[1].toString();
					}
					if (rowdata[2] != null) {

						refrigerantID = rowdata[2].toString();
					}
					if (rowdata[3] != null) {

						slinkAddress = rowdata[3].toString();
					}
					// Modified by ravi
					refrigerantCircuitVO.setRefrigerantCircuits(caName
							+ "-Link" + lineNo + "-RC" + refrigerantID
							+ "-Addr " + slinkAddress);
					refrigerantCircuitVO.setRefId(rowdata[4] == null ? null
							: Long.parseLong(rowdata[4].toString()));
					responseVO.getRefrigerantCircuits().add(
							refrigerantCircuitVO);
				}
			}

		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			throw new GenericFailureException(customErrorMessage);

		}
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.StatsSQLDAO#getStatsReport(
	 * com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO)
	 */
	@Override
	public List<StatsReportVO> getStatsReport(StatsRequestVO requestVO)
			throws HibernateException, GenericFailureException, ParseException {

		System.out.println(requestVO.getPeriodStrategyMap());

		String finalSQL = BizConstants.EMPTY_STRING;

		QueryParameters parameters = new QueryParameters();

		statsQueryBuilder.createSQLQuery(requestVO, parameters);

		switch (requestVO.getType()) {
		case BizConstants.STATISTICS_ACCUMULATED:

			finalSQL = getFinalQueryForAccumulated(requestVO, parameters);

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_TODAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.RANGE_DAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

				Calendar endResidualCal = null;
				Calendar startResidualCal = null;

				if (requestVO.getPeriodStrategyMap().containsKey(
						BizConstants.KEY_END_RESIDUAL_DATE1)) {

					Date endDate = CommonUtil.stringToDate(
							requestVO.getPeriodStrategyMap()
									.get(BizConstants.KEY_END_RESIDUAL_DATE1)
									.toString(),
							BizConstants.DATE_FORMAT_YYYYMMDD);

					endResidualCal = Calendar.getInstance();
					endResidualCal.setTime(endDate);

					Calendar currentCal = CommonUtil
							.setUserTimeZone(Calendar.getInstance(),
									requestVO.getUserTimeZone());

					if (!StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

						if (CommonUtil
								.getCalendarWithDateFormatWithoutTime(
										endResidualCal)
								.compareTo(
										CommonUtil
												.getCalendarWithDateFormatWithoutTime(currentCal)) == 0) {

							endResidualCal.set(Calendar.HOUR,
									currentCal.get(Calendar.HOUR_OF_DAY));
							endResidualCal.set(Calendar.MINUTE,
									currentCal.get(Calendar.MINUTE));
							endResidualCal.set(Calendar.SECOND,
									currentCal.get(Calendar.SECOND));

						} else {

							endResidualCal.set(Calendar.HOUR, 23);
							endResidualCal.set(Calendar.MINUTE, 0);
							endResidualCal.set(Calendar.SECOND, 0);

						}

						requestVO
								.getPeriodStrategyMap()
								.put(BizConstants.KEY_END_RESIDUAL_DATE1,
										CommonUtil.dateToString(
												endResidualCal.getTime(),
												BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

					}

				}

				if (!StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					if (requestVO.getPeriodStrategyMap().containsKey(
							BizConstants.KEY_START_RESIDUAL_DATE1)) {

						Date startDate = CommonUtil
								.stringToDate(
										requestVO
												.getPeriodStrategyMap()
												.get(BizConstants.KEY_START_RESIDUAL_DATE1)
												.toString(),
										BizConstants.DATE_FORMAT_YYYYMMDD);

						startResidualCal = Calendar.getInstance();
						startResidualCal.setTime(startDate);

						startResidualCal.set(Calendar.HOUR, 0);
						startResidualCal.set(Calendar.MINUTE, 0);
						startResidualCal.set(Calendar.SECOND, 0);

						requestVO
								.getPeriodStrategyMap()
								.put(BizConstants.KEY_START_RESIDUAL_DATE1,
										CommonUtil.dateToString(
												startResidualCal.getTime(),
												BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

					}

				}

			}

			break;

		case BizConstants.STATISTICS_CHRONOLOGICAL:

			finalSQL = getFinalQueryForChronological(requestVO, parameters);

			break;
		}

		List<?> resultList = sqldao.executeSQLSelect(finalSQL,
				requestVO.getPeriodStrategyMap());

		List<StatsReportVO> statsReportVOList = null;

		if (resultList != null && resultList.size() > 0) {

			statsReportVOList = statsReportVOGenerator.getStatsList(resultList,
					requestVO);

		} else {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				requestVO.setStartDate(requestVO.getPeriodStrategyMap()
						.get(BizConstants.KEY_START_RESIDUAL_DATE1).toString());
				requestVO.setEndDate(requestVO.getPeriodStrategyMap()
						.get(BizConstants.KEY_END_RESIDUAL_DATE1).toString());

			}
		}

		return statsReportVOList;
	}

	@Override
	public StatsRequestVO setTimeZoneForStatsByGroup(StatsRequestVO requestVO) {

		requestVO.getPeriodStrategyMap().remove(BizConstants.KEY_LABEL);

		if (requestVO.getId() != null) {

			String[] idArr = requestVO.getId().split(BizConstants.COMMA_STRING);
			for (String id : idArr) {

				requestVO.getIdList().add(Long.valueOf(id));

			}

		}

		requestVO.getPeriodStrategyMap().put(BizConstants.KEY_IDS,
				requestVO.getIdList());

		List<?> resultList = null;

		if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_GROUP)) {

			resultList = sqldao.executeSQLSelect(
					SQL_GET_TIMEZONETIME_BYGROUP.toString(),
					requestVO.getPeriodStrategyMap());

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_INDOOR)) {

			resultList = sqldao.executeSQLSelect(
					SQL_GET_TIMEZONETIME_BYINDOOR.toString(),
					requestVO.getPeriodStrategyMap());

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_REFRIGERANT_CIRCUIT)) {

			resultList = sqldao.executeSQLSelect(
					SQL_GET_TIMEZONETIME_BYREFRIGERANT.toString(),
					requestVO.getPeriodStrategyMap());

		}

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itrForPast24Hour = resultList.iterator();

			Calendar fromCal = null;

			Calendar toCal = null;

			while (itrForPast24Hour.hasNext()) {

				Object[] rowData1 = (Object[]) itrForPast24Hour.next();

				if (rowData1[0] != null) {

					Calendar fromDateTimeTemp = CommonUtil
							.convertStringToCalendarWithDateFormat(
									rowData1[0].toString(),
									BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

					if (fromCal == null) {

						fromCal = CommonUtil
								.convertStringToCalendarWithDateFormat(
										rowData1[0].toString(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

					}

					fromDateTimeTemp.getTime();
					fromCal.getTime();

					if (fromCal.compareTo(fromDateTimeTemp) > 0) {

						fromCal = fromDateTimeTemp;
					}

				}

				if (rowData1[1] != null) {

					Calendar toDateTimeTemp = CommonUtil
							.convertStringToCalendarWithDateFormat(
									rowData1[1].toString(),
									BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

					if (toCal == null) {
						toCal = CommonUtil
								.convertStringToCalendarWithDateFormat(
										rowData1[1].toString(),
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);
					}

					toCal.getTime();
					toDateTimeTemp.getTime();

					if (toCal.compareTo(toDateTimeTemp) < 0) {

						toCal = toDateTimeTemp;
					}

				}

			}

			System.out.println("fromCal :" + fromCal.getTime());
			System.out.println("toCal :" + toCal.getTime());

			requestVO.setStartDate(CommonUtil.getCalendarWithDateFormat(
					fromCal.getTimeInMillis(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));
			requestVO.setEndDate(CommonUtil.getCalendarWithDateFormat(
					toCal.getTimeInMillis(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS));

		}

		return requestVO;
	}

}
