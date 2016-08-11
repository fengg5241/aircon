/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.stats.vo.QueryParameters;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author akansha
 * 
 */
@Component
public class StatsQueryBuilder {

	private static final StringBuffer SQL_POWER_CONSUMPTION_PARAMETERS = new StringBuffer(
			"case when sum(pcm.total_power_consumption) is not null and sum(pc.total_power_consumption) is not null then  sum(pcm.total_power_consumption) + sum(pc.total_power_consumption) ")
			.append("when sum(pcm.total_power_consumption) is  null then  sum(pc.total_power_consumption)  ")
			.append(" when sum(pc.total_power_consumption) is  null  then  sum(pcm.total_power_consumption)  end  sumtotal_power_consumption, ")
			.append("(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 then ")
			.append("((COALESCE(sum(pcm.total_power_consumption),0) + COALESCE(sum(pc.total_power_consumption),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgtotalpower ")
			.append(" ,pcm.indoorunit_id as pcmindoorunit_id,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_POWER_CONSUMPTION_PARAMETERS_HOURLY = new StringBuffer(
			" sum(pc.total_power_consumption) sumtotal_power_consumption, ")
			.append("(case when COALESCE(sum(pccount),0) > 0 then (COALESCE(sum(pc.total_power_consumption),0)/COALESCE(sum(pccount),0)) else null END) as avgtotalpower  ")
			.append(" ,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_POWER_CONSUMPTION_PARAMETERS_RANGE = new StringBuffer(
			"sum(pcm.total_power_consumption) sumtotal_power_consumption, ")
			.append("(case when COALESCE(sum(pcmcount),0) > 0 then (COALESCE(sum(pcm.total_power_consumption),0)/COALESCE(sum(pcmcount),0)) else null END) as avgtotalpower  ")
			.append(" ,pcm.indoorunit_id as pcmindoorunit_id  ");

	private static final StringBuffer SQL_CAPACITY_PARAMETERS = new StringBuffer(
			"case when sum(pcm.currentcapacity) is not null and sum(pc.currentcapacity) is not null then  sum(pcm.currentcapacity) + sum(pc.currentcapacity) ")
			.append("when sum(pcm.currentcapacity) is  null then  sum(pc.currentcapacity)  ")
			.append(" when sum(pc.currentcapacity) is  null  then  sum(pcm.currentcapacity)  end  sumcurrentcapacity, ")
			.append("case when sum(pcm.totalcapacity) is not null and sum(pc.totalcapacity) is not null then  sum(pcm.totalcapacity) + sum(pc.totalcapacity) ")
			.append("when sum(pcm.totalcapacity) is  null then  sum(pc.totalcapacity)  ")
			.append(" when sum(pc.totalcapacity) is  null  then  sum(pcm.totalcapacity)  end  sumtotalcapacity, ")
			.append("(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 then ")
			.append("((COALESCE(sum(pcm.outdoortemp),0) + COALESCE(sum(pc.outdoortemp),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgoutdoortemp")
			.append(",pcm.indoorunit_id as pcmindoorunit_id,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_CAPACITY_PARAMETERS_HOURLY = new StringBuffer(
			"sum(pc.currentcapacity)  as sumcurrentcapacity,")
			.append("sum(pc.totalcapacity)  as sumtotalcapacity,")
			.append("(case when COALESCE(sum(pccount),0) > 0 then (COALESCE(sum(pc.outdoortemp),0)/COALESCE(sum(pccount),0)) else null END) as avgoutdoortemp")
			.append(",pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_CAPACITY_PARAMETERS_RANGE = new StringBuffer(
			"sum(pcm.currentcapacity) as sumcurrentcapacity,")
			.append("sum(pcm.totalcapacity)  as sumtotalcapacity,")
			.append("(case when COALESCE(sum(pcmcount),0) > 0 then (COALESCE(sum(pcm.outdoortemp),0)/COALESCE(sum(pcmcount),0)) else null END) as avgoutdoortemp  ")
			.append(" ,pcm.indoorunit_id as pcmindoorunit_id ");

	private static final StringBuffer SQL_CAPACITY_PARAMETERS_REFRIGERANTCIRCUIT = new StringBuffer(
			"(case when (COALESCE(sum(pc.ratedcapacitycount),0) + COALESCE(sum(pcm.ratedcapacitycount),0)) > 0 and ((COALESCE(sum(pc.ratedcapacity),0) + COALESCE(sum(pcm.ratedcapacity),0)) >= 0) ")
			.append(" and (sum(pc.ratedcapacity) is not null or sum(pcm.ratedcapacity) is not null) then ((COALESCE(sum(pcm.ratedcapacity),0) + COALESCE(sum(pc.ratedcapacity),0))/(COALESCE(sum(pc.ratedcapacitycount),0)+COALESCE(sum(pcm.ratedcapacitycount),0))) else null END) as avgratedcapacity, ")
			.append(" (case when (COALESCE(sum(pc.currentcapacitycount),0) + COALESCE(sum(pcm.currentcapacitycount),0)) > 0 and ((COALESCE(sum(pc.currentcapacity),0) + COALESCE(sum(pcm.currentcapacity),0)) >= 0) ")
			.append(" and (sum(pc.currentcapacity) is not null or sum(pcm.currentcapacity) is not null) then ((COALESCE(sum(pcm.currentcapacity),0) + COALESCE(sum(pc.currentcapacity),0))/(COALESCE(sum(pc.currentcapacitycount),0)+COALESCE(sum(pcm.currentcapacitycount),0))) else null END) as avgcurrentcapacity, ")
			.append(" (case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 and ((COALESCE(sum(pc.outdoortemp),0) + COALESCE(sum(pcm.outdoortemp),0)) >= 0) ")
			.append(" and (sum(pc.outdoortemp) is not null or sum(pcm.outdoortemp) is not null) then ((COALESCE(sum(pcm.outdoortemp),0) + COALESCE(sum(pc.outdoortemp),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgoutdoortemp ");

	/*
	 * 
	 * 
	 * "(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 and ((COALESCE(sum(pc.ratedcapacity),0) + COALESCE(sum(pcm.ratedcapacity),0)) >= 0)  and (sum(pc.ratedcapacity) is not null or sum(pcm.ratedcapacity) is not null) then "
	 * ) .append(
	 * "((COALESCE(sum(pcm.ratedcapacity),0) + COALESCE(sum(pc.ratedcapacity),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgratedcapacity, "
	 * ) .append(
	 * "(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 and ((COALESCE(sum(pc.currentcapacity),0) + COALESCE(sum(pcm.currentcapacity),0)) >= 0)  and (sum(pc.currentcapacity) is not null or sum(pcm.currentcapacity) is not null) then "
	 * ) .append(
	 * "((COALESCE(sum(pcm.currentcapacity),0) + COALESCE(sum(pc.currentcapacity),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgcurrentcapacity, "
	 * ) .append(
	 * "(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 and ((COALESCE(sum(pc.outdoortemp),0) + COALESCE(sum(pcm.outdoortemp),0)) >= 0)  and (sum(pc.outdoortemp) is not null or sum(pcm.outdoortemp) is not null) then "
	 * ) .append(
	 * "((COALESCE(sum(pcm.outdoortemp),0) + COALESCE(sum(pc.outdoortemp),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgoutdoortemp"
	 * );
	 */
	private static final StringBuffer SQL_CAPACITY_PARAMETERS_HOURLY_REFRIGERANTCIRCUIT = new StringBuffer(
			"(case when (COALESCE(sum(pc.ratedcapacitycount),0) ) > 0 and ((COALESCE(sum(pc.ratedcapacity),0) ) >= 0) ")
			.append(" and (sum(pc.ratedcapacity) is not null) then (( COALESCE(sum(pc.ratedcapacity),0))/(COALESCE(sum(pc.ratedcapacitycount),0))) else null END) as avgratedcapacity, ")
			.append(" (case when (COALESCE(sum(pc.currentcapacitycount),0) ) > 0 and ((COALESCE(sum(pc.currentcapacity),0) ) >= 0) ")
			.append(" and (sum(pc.currentcapacity) is not null ) then (( COALESCE(sum(pc.currentcapacity),0))/(COALESCE(sum(pc.currentcapacitycount),0))) else null END) as avgcurrentcapacity, ")
			.append(" (case when (COALESCE(sum(pccount),0) ) > 0 and ((COALESCE(sum(pc.outdoortemp),0) ) >= 0)  and (sum(pc.outdoortemp) is not null ) then (( COALESCE(sum(pc.outdoortemp),0))/(COALESCE(sum(pccount),0))) else null END) as avgoutdoortemp ");

	/*
	 * "(case when (COALESCE(sum(pccount),0) > 0) and ((COALESCE(sum(pc.ratedcapacity),0)) >= 0)  and (sum(pc.ratedcapacity) is not null ) then (COALESCE(sum(pc.ratedcapacity),0)/COALESCE(sum(pccount),0)) else null END) as avgratedcapacity, "
	 * ) .append(
	 * "(case when (COALESCE(sum(pccount),0) > 0) and ((COALESCE(sum(pc.currentcapacity),0)) >= 0)  and (sum(pc.currentcapacity) is not null ) then (COALESCE(sum(pc.currentcapacity),0)/COALESCE(sum(pccount),0)) else null END) as avgcurrentcapacity, "
	 * ) .append(
	 * "(case when (COALESCE(sum(pccount),0) > 0) and ((COALESCE(sum(pc.outdoortemp),0)) >= 0)  and (sum(pc.outdoortemp) is not null ) then (COALESCE(sum(pc.outdoortemp),0)/COALESCE(sum(pccount),0)) else null END) as avgoutdoortemp"
	 * );
	 */

	private static final StringBuffer SQL_CAPACITY_PARAMETERS_RANGE_REFRIGERANTCIRCUIT = new StringBuffer(
			"(case when (COALESCE(sum(pcm.ratedcapacitycount),0) ) > 0 and ((COALESCE(sum(pcm.ratedcapacity),0) ) >= 0) ")
			.append(" and (sum(pcm.ratedcapacity) is not null) then (( COALESCE(sum(pcm.ratedcapacity),0))/(COALESCE(sum(pcm.ratedcapacitycount),0))) else null END) as avgratedcapacity, ")
			.append(" (case when (COALESCE(sum(pcm.currentcapacitycount),0) ) > 0 and ((COALESCE(sum(pcm.currentcapacity),0) ) >= 0) ")
			.append(" and (sum(pcm.currentcapacity) is not null ) then (( COALESCE(sum(pcm.currentcapacity),0))/(COALESCE(sum(pcm.currentcapacitycount),0))) else null END) as avgcurrentcapacity, ")
			.append(" (case when (COALESCE(sum(pcmcount),0) ) > 0 and ((COALESCE(sum(pcm.outdoortemp),0) ) >= 0)  and (sum(pcm.outdoortemp) is not null ) then (( COALESCE(sum(pcm.outdoortemp),0))/(COALESCE(sum(pcmcount),0))) else null END) as avgoutdoortemp ");

	/*
	 * "(case when (COALESCE(sum(pcmcount),0) > 0) and ((COALESCE(sum(pcm.ratedcapacity),0)) >= 0)  and (sum(pcm.ratedcapacity) is not null ) then (COALESCE(sum(pcm.ratedcapacity),0)/COALESCE(sum(pcmcount),0)) else null END) as avgratedcapacity, "
	 * ) .append(
	 * "(case when (COALESCE(sum(pcmcount),0) > 0) and ((COALESCE(sum(pcm.currentcapacity),0)) >= 0)  and (sum(pcm.currentcapacity) is not null ) then (COALESCE(sum(pcm.currentcapacity),0)/COALESCE(sum(pcmcount),0)) else null END) as avgcurrentcapacity, "
	 * ) .append(
	 * "(case when (COALESCE(sum(pcmcount),0) > 0) and ((COALESCE(sum(pcm.outdoortemp),0)) >= 0)  and (sum(pcm.outdoortemp) is not null ) then (COALESCE(sum(pcm.outdoortemp),0)/COALESCE(sum(pcmcount),0)) else null END) as avgoutdoortemp"
	 * );
	 */

	private static final StringBuffer SQL_EFFICIENCY_PARAMETERS = new StringBuffer(
			"(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 then ")
			.append("((COALESCE(sum(pcm.setpointtemp),0) + COALESCE(sum(pc.setpointtemp),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgsetpointtemp,  ")
			.append("(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 then ")
			.append("((COALESCE(sum(pcm.roomtemp),0) + COALESCE(sum(pc.roomtemp),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgroomtemp,  ")
			.append("(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 then ")
			.append("((COALESCE(sum(pcm.outdoortemp),0) + COALESCE(sum(pc.outdoortemp),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgoutdoortemp,  ")
			.append("(case when (COALESCE(sum(pccount),0) + COALESCE(sum(pcmcount),0)) > 0 then ")
			.append("((COALESCE(sum(pcm.efficiency),0) + COALESCE(sum(pc.efficiency),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) as avgefficiency  ")
			.append(" ,pcm.indoorunit_id as pcmindoorunit_id,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_EFFICIENCY_PARAMETERS_HOURLY = new StringBuffer(
			"(case when COALESCE(sum(pccount),0) > 0 then (COALESCE(sum(pc.setpointtemp),0)/COALESCE(sum(pccount),0)) else null END) as avgsetpointtemp,  ")
			.append("(case when COALESCE(sum(pccount),0) > 0 then (COALESCE(sum(pc.roomtemp),0)/COALESCE(sum(pccount),0)) else null END) as avgroomtemp,  ")
			.append("(case when COALESCE(sum(pccount),0) > 0 then (COALESCE(sum(pc.outdoortemp),0)/COALESCE(sum(pccount),0)) else null END) as avgoutdoortemp,  ")
			.append("(case when COALESCE(sum(pccount),0) > 0 then (COALESCE(sum(pc.efficiency),0)/COALESCE(sum(pccount),0)) else null END) as avgefficiency ")
			.append(" ,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_EFFICIENCY_PARAMETERS_RANGE = new StringBuffer(
			"(case when COALESCE(sum(pcmcount),0) > 0 then (COALESCE(sum(pcm.setpointtemp),0)/COALESCE(sum(pcmcount),0)) else null END) as avgsetpointtemp,  ")
			.append("(case when COALESCE(sum(pcmcount),0) > 0 then (COALESCE(sum(pcm.roomtemp),0)/COALESCE(sum(pcmcount),0)) else null END) as avgroomtemp,  ")
			.append("(case when COALESCE(sum(pcmcount),0) > 0 then (COALESCE(sum(pcm.outdoortemp),0)/COALESCE(sum(pcmcount),0)) else null END) as avgoutdoortemp,  ")
			.append("(case when COALESCE(sum(pcmcount),0) > 0 then (COALESCE(sum(pcm.efficiency),0)/COALESCE(sum(pcmcount),0)) else null END) as avgefficiency  ")
			.append(" ,pcm.indoorunit_id as pcmindoorunit_id ");

	private static final StringBuffer SQL_EFFICIENCY_PARAMETERS_REFRIGERANTCIRCUIT = new StringBuffer(
			"(case when (COALESCE(sum(pcmcount),0) + COALESCE(sum(pccount),0)) > 0 then ((COALESCE(sum(pcm.efficiency),0) + COALESCE(sum(pc.efficiency),0))/(COALESCE(sum(pccount),0)+COALESCE(sum(pcmcount),0))) else null END) efficiency  ");

	private static final StringBuffer SQL_EFFICIENCY_PARAMETERS_HOURLY_REFRIGERANTCIRCUIT = new StringBuffer(
			"(case when COALESCE(sum(pccount),0) > 0 then (COALESCE(sum(pc.efficiency),0)/COALESCE(sum(pccount),0)) else null END) as efficiency ");

	private static final StringBuffer SQL_EFFICIENCY_PARAMETERS_RANGE_REFRIGERANTCIRCUIT = new StringBuffer(
			"(case when COALESCE(sum(pcmcount),0) > 0 then (COALESCE(sum(pcm.efficiency),0)/COALESCE(sum(pcmcount),0)) else null END) as efficiency  ");

	private static final StringBuffer SQL_DIFFTEMP_PARAMETERS = new StringBuffer(
			"(case when (COALESCE(sum(pc.countroomtemp),0) + COALESCE(sum(pcm.countroomtemp),0)) > 0 then ((COALESCE(sum(pcm.roomtemp),0) + COALESCE(sum(pc.roomtemp),0))/(COALESCE(sum(pc.countroomtemp),0)+COALESCE(sum(pcm.countroomtemp),0))) else null END) as avgroomtemp,  ")
			.append("(case when (COALESCE(sum(pc.countsetpointtemp),0) + COALESCE(sum(pcm.countsetpointtemp),0)) > 0 then ((COALESCE(sum(pcm.setpointtemp),0) + COALESCE(sum(pc.setpointtemp),0))/(COALESCE(sum(pc.countsetpointtemp),0)+COALESCE(sum(pcm.countsetpointtemp),0))) else null END) as avgsetpointtemp  ")
			.append(",pcm.indoorunit_id as pcmindoorunit_id,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_DIFFTEMP_PARAMETERS_HOURLY = new StringBuffer(
			"(case when COALESCE(sum(pc.countroomtemp),0) > 0 then (COALESCE(sum(pc.roomtemp),0)/COALESCE(sum(pc.countroomtemp),0)) else null END) as avgroomtemp,  ")
			.append("(case when COALESCE(sum(pc.countsetpointtemp),0) > 0 then (COALESCE(sum(pc.setpointtemp),0)/COALESCE(sum(pc.countsetpointtemp),0)) else null END) as avgsetpointtemp  ")
			.append(" ,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_DIFFTEMP_PARAMETERS_RANGE = new StringBuffer(
			"(case when COALESCE(sum(pcm.countroomtemp),0) > 0 then (COALESCE(sum(pcm.roomtemp),0)/COALESCE(sum(pcm.countroomtemp),0)) else null END) as avgroomtemp,  ")
			.append("(case when COALESCE(sum(pcm.countsetpointtemp),0) > 0 then (COALESCE(sum(pcm.setpointtemp),0)/COALESCE(sum(pcm.countsetpointtemp),0)) else null END) as avgsetpointtemp  ")
			.append(" ,pcm.indoorunit_id as pcmindoorunit_id ");

	private static final StringBuffer SQL_WORKHOUR_PARAMETERS = new StringBuffer(
			" case when (COALESCE(sum(pcm.highon),0) + COALESCE(sum(pc.highon),0)) > 0 then  COALESCE(sum(pcm.highon),0) + COALESCE(sum(pc.highon),0) else null  end  sumhighon, ")

			.append(" case when (COALESCE(sum(pcm.highoff),0) + COALESCE(sum(pc.highoff),0)) > 0 then  COALESCE(sum(pcm.highoff),0) + COALESCE(sum(pc.highoff),0) else null  end  sumhighoff, ")

			.append(" case when (COALESCE(sum(pcm.mediumon),0) +  COALESCE(sum(pc.mediumon),0)) > 0  then  COALESCE(sum(pcm.mediumon),0) + COALESCE(sum(pc.mediumon),0) else null  end  summediumon, ")

			.append(" case when (COALESCE(sum(pcm.mediumoff),0) + COALESCE(sum(pc.mediumoff),0)) > 0 then  COALESCE(sum(pcm.mediumoff),0) + COALESCE(sum(pc.mediumoff),0) else null  end  summediumoff, ")

			.append(" case when (COALESCE(sum(pcm.lowon),0) + COALESCE(sum(pc.lowon),0)) > 0 then  COALESCE(sum(pcm.lowon),0) + COALESCE(sum(pc.lowon),0) else null  end  sumlowon, ")

			.append(" case when (COALESCE(sum(pcm.lowoff),0) + COALESCE(sum(pc.lowoff),0)) > 0 then  COALESCE(sum(pcm.lowoff),0) + COALESCE(sum(pc.lowoff),0) else null  end  sumlowoff ")

			.append(" ,pcm.indoorunit_id as pcmindoorunit_id,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_WORKHOUR_PARAMETERS_HOURLY = new StringBuffer(
			"sum(pc.highon)  as sumhighon,sum(pc.highoff)  as sumhighoff,")
			.append("sum(pc.mediumon)  as summediumon,sum(pc.mediumoff)  as summediumoff,")
			.append("sum(pc.lowon)  as sumlowon,sum(pc.lowoff)  as sumlowoff")
			.append(" ,pc.indoorunit_id as pcindoorunit_id ");

	private static final StringBuffer SQL_WORKHOUR_PARAMETERS_RANGE = new StringBuffer(
			"sum(pcm.highon)  as sumhighon,sum(pcm.highoff)  as sumhighoff,")
			.append("sum(pcm.mediumon)  as summediumon,sum(pcm.mediumoff)  as summediumoff,")
			.append("sum(pcm.lowon)  as sumlowon,sum(pcm.lowoff)  as sumlowoff")
			.append(" ,pcm.indoorunit_id as pcmindoorunit_id ");

	private static final StringBuffer SQL_WORKHOUR_PARAMETERS_REFRIGERANTCIRCUIT = new StringBuffer(
			" case when (COALESCE(sum(pcm.workinghour1),0) + COALESCE(sum(pc.workinghour1),0)) > 0 then  COALESCE(sum(pcm.workinghour1),0) + COALESCE(sum(pc.workinghour1),0) else null  end  sumworkinghour1, ")

			.append(" case when (COALESCE(sum(pcm.workinghour2),0) + COALESCE(sum(pc.workinghour2),0)) > 0 then  COALESCE(sum(pcm.workinghour2),0) + COALESCE(sum(pc.workinghour2),0) else null  end  sumworkinghour2, ")

			.append(" case when (COALESCE(sum(pcm.workinghour3),0) +  COALESCE(sum(pc.workinghour3),0)) > 0  then  COALESCE(sum(pcm.workinghour3),0) + COALESCE(sum(pc.workinghour3),0) else null  end  sumworkinghour3 ");

	private static final StringBuffer SQL_WORKHOUR_PARAMETERS_HOURLY_REFRIGERANTCIRCUIT = new StringBuffer(
			"sum(pc.workinghour1)  as sumworkinghour1,sum(pc.workinghour2)  as sumworkinghour2,")
			.append("sum(pc.workinghour3)  as sumworkinghour3");

	private static final StringBuffer SQL_WORKHOUR_PARAMETERS_RANGE_REFRIGERANTCIRCUIT = new StringBuffer(
			"sum(pcm.workinghour1)  as sumworkinghour1,sum(pcm.workinghour2)  as sumworkinghour2,")
			.append("sum(pcm.workinghour3)  as sumworkinghour3");

	private static final StringBuffer SQL_GET_STATS_CHRONOLOGICAL = new StringBuffer(
			"select %s ,sum(pcmcount) pcmcount,pcm.indoorunit_id,fn.supplygroupname,fn.supplygroupid,pcm.indoorunit_id as pcmindoorunit_id,%s ,g.path_name,c.name as companyname ")
			.append(" from usp_getindoorunits_supplygroupname(:ids) fn ")
			.append(" join groups g on  fn.supplygroupid = g.id   left join companies c on c.id = g.company_id ")
			.append(" Left Join (Select %s , indoorunit_id, %s from  %s pcm where  %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on fn.indoorunitid  = pcm.indoorunit_id ")
			.append(" Group by pcm.indoorunit_id,%s,fn.supplygroupname,g.path_name,c.name,fn.supplygroupid ");

	private static final StringBuffer SQL_GET_STATS_CHRONOLOGICAL_PAST24HOUR = new StringBuffer(
			"select %s ,sum(pcmcount) pcmcount,pcm.indoorunit_id,fn.supplygroupname,fn.supplygroupid,pcm.indoorunit_id as pcmindoorunit_id,%s ,g.path_name,c.name as companyname ,")
			.append(" case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end  totime from usp_getindoorunits_supplygroupname(:ids) fn ")
			.append(" join groups g on  fn.supplygroupid = g.id   left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id  left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append(" Left Join (Select %s , indoorunit_id, %s,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime , ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime from  usp_getindoorunits_supplygroupname(:ids) fn   inner join groups g on fn.groupid =  g.id left join timezonemaster tzm on g.timezone = tzm.id ")
			.append(" left join pg_timezone_names ptz on tzm.timezone =  ptz.name left join %s pc on fn.indoorunitid = pc.indoorunit_id where  %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on fn.indoorunitid  = pcm.indoorunit_id ")
			.append(" Group by pcm.indoorunit_id,%s,fn.supplygroupname,g.path_name,c.name,fn.supplygroupid   ");

	private static final StringBuffer SQL_GET_STATS_CHRONOLOGICAL_AIRCON_UNIT = new StringBuffer(
			"select concat(idu.name,'-',idu.id) as name, %s ,%s ,g.path_name,c.name as companyname ")
			.append(" from indoorunits idu ")
			.append(" Left join groups g on  idu.siteid = g.uniqueid  ")
			.append(" left join companies c on c.id = g.company_id ")
			.append(" Left Join (Select %s , indoorunit_id, %s from  %s pcm  where %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on idu.id  = pcm.indoorunit_id ")
			.append(" where idu.id in (:ids) Group by pcm.indoorunit_id,%s,idu.name,g.path_name,c.name,idu.id");

	private static final StringBuffer SQL_GET_STATS_CHRONOLOGICAL_AIRCON_UNIT_PAST24HOUR = new StringBuffer(
			"select concat(idu.name,'-',idu.id) as name, %s ,%s ,g.path_name,c.name as companyname,")
			.append(" case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end ")
			.append(" from indoorunits idu ")
			.append(" Left join groups g on  idu.siteid = g.uniqueid  ")
			.append(" left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id  left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append(" Left Join (Select %s , indoorunit_id,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime , ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime, %s from  %s pcm inner join indoorunits idu on idu.id  = pc.indoorunit_id ")
			.append(" left join  groups g on idu.siteid = g.uniqueid left join timezonemaster tzm on g.timezone = tzm.id ")
			.append(" left join pg_timezone_names ptz on tzm.timezone =  ptz.name where %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on idu.id  = pcm.indoorunit_id ")
			.append(" where idu.id in (:ids) Group by pcm.indoorunit_id,%s,idu.name,g.path_name,c.name,idu.id");

	private static final StringBuffer SQL_GET_STATS_CHRONOLOGICAL_AIRCON_GROUP = new StringBuffer(
			"select concat(idu.name,'-',fn.indoorunitid) as name, %s ,%s ,g.path_name,c.name as companyname    ")
			.append(" from  usp_getindoorunits_supplygroupname(:ids) fn inner join indoorunits idu on idu.id = fn.indoorunitid ")
			.append(" Left join groups g on  fn.groupid = g.id  ")
			.append(" left join companies c on c.id = g.company_id   ")
			.append(" Left Join (Select %s , indoorunit_id, %s from  %s pcm  where %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on idu.id  = pcm.indoorunit_id ")
			.append(" Group by pcm.indoorunit_id,%s,idu.name,g.path_name,c.name,fn.indoorunitid ");

	private static final StringBuffer SQL_GET_STATS_CHRONOLOGICAL_AIRCON_GROUP_PAST24HOUR = new StringBuffer(
			"select concat(idu.name,'-',fn.indoorunitid) as name, %s ,%s ,g.path_name,c.name as companyname ,")
			.append(" case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end totime ")
			.append(" from  usp_getindoorunits_supplygroupname(:ids) fn inner join indoorunits idu on idu.id = fn.indoorunitid ")
			.append(" Left join groups g on  fn.groupid = g.id  ")
			.append(" left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name  ")
			.append(" Left Join (Select %s , indoorunit_id,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime , ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime, %s from usp_getindoorunits_supplygroupname(:ids) fn   inner join groups g on fn.groupid =  g.id left join timezonemaster tzm on g.timezone = tzm.id ")
			.append(" left join pg_timezone_names ptz on tzm.timezone =  ptz.name left join %s pc on fn.indoorunitid = pc.indoorunit_id  where %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on idu.id  = pcm.indoorunit_id ")
			.append(" Group by pcm.indoorunit_id,%s,idu.name,g.path_name,c.name,fn.indoorunitid ");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL = new StringBuffer(
			"Select %s ,sum(pcmcount) pcmcount,pcm.refid,pcm.refid as pcmindoorunit_id,%s,g.path_name,c.name as companyname,odut.type ")
			.append(" from refrigerantmaster rm   ")
			.append(" left outer join (Select distinct odut.refid,odut.refrigcircuitgroupoduid,odut.type  from outdoorunits odut where odut.parentid is null) odut  on rm.refid = odut.refid ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id  ")
			.append(" Left Join (Select %s , refid, %s from  %s pcm where  %s group by  pcm.refid, %s ) pcm ")
			.append(" on odut.refid  = pcm.refid ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by pcm.refid,%s , a.name,rm.linenumber,odut.refid,odut.refrigcircuitgroupoduid,g.path_name,c.name,odut.type,rm.refrigerantid ");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_PAST24HOUR = new StringBuffer(
			"Select %s ,sum(pcmcount) pcmcount,pcm.refid,pcm.refid as pcmindoorunit_id,%s,g.path_name,c.name as companyname,odut.type, ")
			.append(" case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end totime ")
			.append(" from refrigerantmaster rm   ")
			.append(" left outer join (Select distinct odut.refid,odut.refrigcircuitgroupoduid,odut.type  from outdoorunits odut where odut.parentid is null) odut  on rm.refid = odut.refid ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append(" Left Join (Select %s , pc.refid,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime , ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime, %s from  %s pcm inner join refrigerantmaster rm on rm.refid  = pc.refid ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join timezonemaster tzm on g.timezone = tzm.id left join pg_timezone_names ptz on tzm.timezone =  ptz.name  where  %s group by  pcm.refid, %s ) pcm ")
			.append(" on rm.refid  = pcm.refid ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by pcm.refid,%s , a.name,rm.linenumber,odut.refid,odut.refrigcircuitgroupoduid,g.path_name,c.name,odut.type,rm.refrigerantid ");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_INDOORUNIT = new StringBuffer(
			"Select %s ,sum(pcmcount) pcmcount,pcm.indoorunit_id,pcm.indoorunit_id as pcmindoorunit_id,%s,g.path_name,c.name as companyname ")
			.append(" from refrigerantmaster rm   ")
			.append(" left outer join outdoorunits odu on rm.refid = odu.refid and odu.parentid is null ")
			.append(" left outer  join indoorunits idu on odu.id =  idu.outdoorunit_id  ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id  ")
			.append(" Left Join (Select %s , indoorunit_id, %s from  %s pcm where  %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on idu.id  = pcm.indoorunit_id ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by pcm.indoorunit_id,%s , a.name,rm.linenumber,odu.refid,odu.refrigcircuitgroupoduid,g.path_name,c.name,rm.refrigerantid ");

	// Modified by Ravi
	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_INDOORUNIT_PAST24HOUR = new StringBuffer(
			"Select %s ,sum(pcmcount) pcmcount,pcm.indoorunit_id,pcm.indoorunit_id as pcmindoorunit_id,%s,g.path_name,c.name as companyname, ")
			.append(" case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end totime  ")
			.append(" from refrigerantmaster rm   ")
			.append(" left outer join outdoorunits odu on rm.refid = odu.refid and odu.parentid is null ")
			.append(" left outer  join indoorunits idu on odu.id =  idu.outdoorunit_id  ")
			.append(" Left Join groups g on  rm.siteid = g.uniqueid left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append(" Left Join (Select %s , indoorunit_id,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime , ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime, %s from  %s pcm inner join indoorunits idu on idu.id  = pc.indoorunit_id ")
			.append(" left join  groups g on idu.siteid =  g.uniqueid  left join timezonemaster tzm on g.timezone = tzm.id left join pg_timezone_names ptz on tzm.timezone =  ptz.name where  %s group by  pcm.indoorunit_id, %s ) pcm ")
			.append(" on idu.id  = pcm.indoorunit_id ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append("where rm.refid in (:ids) ")
			.append("group  by pcm.indoorunit_id,%s , a.name,rm.linenumber,odu.refid,odu.refrigcircuitgroupoduid,g.path_name,c.name,rm.refrigerantid ");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_GROUPS = new StringBuffer(
			"select %s ,pcm.refid,fn.supplygroupname,fn.supplygroupid, %s,g.path_name,c.name as companyname ")
			.append(" from (Select distinct supplygroupid, refid,supplygroupname from usp_getrefrigerant_suplygroupname(:ids) )  fn   ")
			.append(" left join refrigerantmaster rm  on fn.refid = rm.refid  ")
			.append(" Left Join groups g on  fn.supplygroupid = g.id ")
			.append(" left join companies c on c.id = g.company_id ")
			.append(" Left Join (Select %s , refid, %s from  %s pcm where  %s group by  pcm.refid, %s ) pcm ")
			.append(" on rm.refid  = pcm.refid  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append(" Group by pcm.refid,%s,fn.supplygroupname,g.path_name,c.name,fn.supplygroupid ");

	private static final StringBuffer SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_GROUPS_PAST24HOUR = new StringBuffer(
			"select %s ,pcm.refid,fn.supplygroupname,fn.supplygroupid, %s,g.path_name,c.name as companyname, ")
			.append(" case when min(fromtime) is null then Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else min(fromtime) end fromtime ")
			.append(" , case when max(totime) is null then  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptzo.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) else max(totime) end totime ")
			.append("  from (Select distinct supplygroupid, refid,supplygroupname from usp_getrefrigerant_suplygroupname(:ids) )  fn   ")
			.append(" left join refrigerantmaster rm  on fn.refid = rm.refid  ")
			.append(" Left Join groups g on  fn.supplygroupid = g.id ")
			.append(" left join companies c on c.id = g.company_id left outer join timezonemaster tzmo on g.timezone = tzmo.id left join pg_timezone_names ptzo on tzmo.timezone =  ptzo.name ")
			.append(" Left Join (Select %s , pc.refid, %s,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime , ")
			.append(" max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime from   usp_getrefrigerant_suplygroupname(:ids) fn ")
			.append(" inner join groups g on fn.supplygroupid =  g.id left join timezonemaster tzm on g.timezone = tzm.id ")
			.append(" left join pg_timezone_names ptz on tzm.timezone =  ptz.name ")
			.append(" left join %s pc on fn.refid = pc.refid where  %s group by  pcm.refid, %s ) pcm ")
			.append(" on rm.refid  = pcm.refid  ")
			.append(" Left join adapters a ")
			.append(" on a.id = rm.adapterid ")
			.append(" Group by pcm.refid,%s,fn.supplygroupname,g.path_name,c.name,fn.supplygroupid ");

	/**
	 * This method is used to create SQL query for charts type
	 * accumulated/chronological on the basis of different time periods.
	 * 
	 * @param requestVO
	 * @param parameters
	 */
	public void createSQLQuery(StatsRequestVO requestVO,
			QueryParameters parameters) {

		getTableAndColumnNames(requestVO, parameters);

		getWhereClause(requestVO, parameters);

		switch (requestVO.getType()) {
		case BizConstants.STATISTICS_ACCUMULATED:

			getSelectParamsForAccumulatedGraphs(requestVO, parameters);

			getJoinForPeriodAndCoreTableForAccumulated(parameters);

			break;

		case BizConstants.STATISTICS_CHRONOLOGICAL:

			if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
					BizConstants.STATISTICS_REFRIGERANT)) {

				StringBuilder refrigerantNameSQL = null;

				if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.WORKING_HOURS)) {
					// Modified by Ravi
					refrigerantNameSQL = new StringBuilder(
							"(COALESCE(a.name,'') || '-Link' || COALESCE(rm.linenumber,'') || '-RC' || COALESCE(rm.refrigerantid,0) || '-Addr '|| COALESCE(odu.refrigcircuitgroupoduid,'')) as  refname,odu.type as odutype,");
				} else {
					// Modified by Ravi
					refrigerantNameSQL = new StringBuilder(
							"(COALESCE(a.name,'') || '-Link' || COALESCE(rm.linenumber,'') || '-RC' || COALESCE(rm.refrigerantid,0) || '-Addr '|| COALESCE(odu.refrigcircuitgroupoduid,'')) as  refname,");

				}

				parameters.setSelectInnerParams(refrigerantNameSQL.toString());

			}

			getExtractParametersForChronological(requestVO, parameters);

			getSelectParamsForChronologicalGraphs(requestVO, parameters);

			getJoinForPeriodAndCoreTableForChronological(parameters, requestVO);

			break;
		}
	}

	/**
	 * This method is used to determine tables and column names as per type of
	 * charts and periods.
	 * 
	 * @param requestVO
	 * @param parameters
	 */
	private void getTableAndColumnNames(StatsRequestVO requestVO,
			QueryParameters parameters) {

		String periodTableName = BizConstants.EMPTY_STRING;
		String coreTableName = BizConstants.EMPTY_STRING;
		String columnNameSubQuery = BizConstants.EMPTY_STRING;

		switch (requestVO.getParameter()) {

		case BizConstants.POWER_CONSUMPTION:

			periodTableName = BizConstants.POWER_CUNSUMPTION_TABLE;

			coreTableName = BizConstants.POWER_CUNSUMPTION_MAIN_TABLE;

			columnNameSubQuery = " sum(total_power_consumption) total_power_consumption, count(indoorunit_id) pccount ";

			break;

		case BizConstants.CAPACITY:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
					|| StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_GROUP)) {

				periodTableName = BizConstants.REFRIGERANTSTATISTICS_TABLE;
				coreTableName = BizConstants.REFRIGERANTSTATISTICS_MAIN_TABLE;

				columnNameSubQuery = " sum(ratedcapacity) as ratedcapacity,sum(currentcapacity) as currentcapacity,sum(outdoortemp) as outdoortemp, count(outdoortemp) pccount,count((case when COALESCE(ratedcapacity,0) = 0 then null else ratedcapacity end))  ratedcapacitycount  , count((case when COALESCE(currentcapacity,0) = 0 then null else currentcapacity end)) currentcapacitycount ";

				requestVO.setParameterOption(BizConstants.EMPTY_STRING);

			} else {

				periodTableName = BizConstants.POWER_CUNSUMPTION_TABLE;

				coreTableName = BizConstants.POWER_CUNSUMPTION_MAIN_TABLE;

				/*
				 * switch (requestVO.getParameterOption()) { case
				 * BizConstants.CAPACITY_PARAM_OPTION_COOL:
				 * 
				 * columnNameSubQuery =
				 * " sum(currentcapacity_cooling) as currentcapacity, " +
				 * "sum(totalcapacity_cooling) as totalcapacity, sum(outdoortemp) as outdoortemp, count(indoorunit_id) pccount "
				 * ;
				 * 
				 * break;
				 * 
				 * case BizConstants.CAPACITY_PARAM_OPTION_HEAT:
				 * 
				 * columnNameSubQuery =
				 * " sum(currentcapacity_heating) as currentcapacity, " +
				 * "sum(totalcapacity_heating) as totalcapacity, sum(outdoortemp) as outdoortemp, count(indoorunit_id) pccount "
				 * ;
				 * 
				 * break; }
				 */
			}

			break;

		case BizConstants.EFFICIENCY:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
					|| StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_GROUP)) {

				periodTableName = BizConstants.REFRIGERANTSTATISTICS_TABLE;
				coreTableName = BizConstants.REFRIGERANTSTATISTICS_MAIN_TABLE;

				if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_GROUP)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.EFFICIENCY)) {

					columnNameSubQuery = " sum(efficiency) as efficiency, count(efficiency) pccount ";

				} else {

					columnNameSubQuery = " sum(efficiency) as efficiency, count(refid) pccount ";

				}

				requestVO.setParameterOption(BizConstants.EMPTY_STRING);

			} else {

				periodTableName = BizConstants.POWER_CUNSUMPTION_TABLE;

				coreTableName = BizConstants.POWER_CUNSUMPTION_MAIN_TABLE;

				switch (requestVO.getParameterOption()) {
				case BizConstants.EFFICIENCY_PARAM_OPTION_COP:

					columnNameSubQuery = " sum(setpointtemp)  as setpointtemp, sum(roomtemp)  as roomtemp, "
							+ "sum(outdoortemp)  as outdoortemp, sum(efficiency_cop) as efficiency, count(indoorunit_id) pccount ";

					break;
				case BizConstants.EFFICIENCY_PARAM_OPTION_EER:

					columnNameSubQuery = " sum(setpointtemp)  as setpointtemp, sum(roomtemp)  as roomtemp, "
							+ "sum(outdoortemp)  as outdoortemp, sum(efficiency_seer) as efficiency, count(indoorunit_id) pccount ";

					break;

				}

			}

			break;

		case BizConstants.DIFF_TEMPERATURE:

			periodTableName = BizConstants.POWER_CUNSUMPTION_TABLE;

			coreTableName = BizConstants.POWER_CUNSUMPTION_MAIN_TABLE;

			columnNameSubQuery = " sum(roomtemp)  as roomtemp, sum(setpointtemp)  as setpointtemp,count(roomtemp) countroomtemp  , count(setpointtemp) countsetpointtemp, "
					+ " count(indoorunit_id) pccount ";

			// sum(currentcapacity_heating) as currentcapacity_heating,
			// sum(currentcapacity_cooling) as currentcapacity_cooling,

			break;

		case BizConstants.WORKING_HOURS:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

				periodTableName = BizConstants.REFRIGERANTSTATISTICS_TABLE;
				coreTableName = BizConstants.REFRIGERANTSTATISTICS_MAIN_TABLE;

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					columnNameSubQuery = " sum(workinghour1)  as workinghour1,sum(workinghour2)  as workinghour2,sum(workinghour3)  as workinghour3, count(pc.refid) pccount ";

				} else {

					columnNameSubQuery = " sum(workinghour1)  as workinghour1,sum(workinghour2)  as workinghour2,sum(workinghour3)  as workinghour3, count(refid) pccount ";

				}

			} else {

				periodTableName = BizConstants.INDOORUNITSTATISTICS_TABLE;
				coreTableName = BizConstants.INDOORUNITSTATISTICS_MAIN_TABLE;

				columnNameSubQuery = " sum(othighfsthermon)  as highon,sum(othighfsthermoff)  as highoff,sum(otmediumfsthermon)  as mediumon ,"
						+ "sum(otmediumfsthermoff) as mediumoff,sum(otlowfsthermon) as lowon,sum(otlowfsthermoff) as lowoff, count(indoorunit_id) pccount ";
			}

			break;

		}

		parameters.setPeriodTableName(periodTableName);
		parameters.setCoreTableName(coreTableName);
		parameters.setColumnNameSubQuery(columnNameSubQuery);

	}

	/**
	 * This method is used to determine where clause as per type of charts and
	 * periods.
	 * 
	 * @param requestVO
	 * @param parameters
	 */
	private void getWhereClause(StatsRequestVO requestVO,
			QueryParameters parameters) {

		String whereClauseForPeriodTable = BizConstants.EMPTY_STRING;
		String whereClauseForCoreTable = BizConstants.EMPTY_STRING;

		int residualNumber = 1;
		int index = 1;

		switch (requestVO.getPeriod()) {

		case BizConstants.RANGE_3YEAR:

			parameters.setPeriodTableName(parameters.getPeriodTableName()
					.concat(BizConstants.YEARLY));

			for (Map.Entry<String, Object> entry : requestVO
					.getPeriodStrategyMap().entrySet()) {

				if (StringUtils.contains(entry.getKey(),
						BizConstants.KEY_START_RANGE1)) {

					if (!whereClauseForPeriodTable.isEmpty()) {
						whereClauseForPeriodTable += BizConstants.OR_STRING;
					}

					whereClauseForPeriodTable += " pcm.year between :startRange1 and :endRange1";

				} else if (StringUtils.contains(entry.getKey(),
						BizConstants.KEY_START_RESIDUAL_DATE)) {

					if (!whereClauseForCoreTable.isEmpty()) {
						whereClauseForCoreTable += BizConstants.OR_STRING;
					}

					whereClauseForCoreTable += "  (cast(pc.logtime as date) between to_date(:startResidualDate"
							.concat(residualNumber
									+ ",'YYYY-MM-DD') and to_date(:endResidualDate")
							.concat(residualNumber + ",'YYYY-MM-DD')) ");

					residualNumber++;
				}
			}

			break;

		case BizConstants.PERIOD_THISYEAR:
		case BizConstants.RANGE_YEAR:

			parameters.setPeriodTableName(parameters.getPeriodTableName()
					.concat(BizConstants.MONTHLY));

			for (Map.Entry<String, Object> entry : requestVO
					.getPeriodStrategyMap().entrySet()) {

				if (StringUtils.contains(entry.getKey(),
						BizConstants.KEY_START_RANGE)) {

					if (!whereClauseForPeriodTable.isEmpty()) {
						whereClauseForPeriodTable += BizConstants.OR_STRING;
					}

					whereClauseForPeriodTable += " (pcm.year = :year" + index
							+ "  and pcm.month between :startRange" + index
							+ " and :endRange" + index + ") ";
					index++;

				} else if (StringUtils.contains(entry.getKey(),
						BizConstants.KEY_START_RESIDUAL_DATE)) {

					if (!whereClauseForCoreTable.isEmpty()) {
						whereClauseForCoreTable += BizConstants.OR_STRING;
					}

					whereClauseForCoreTable += "  (cast(pc.logtime as date) between to_date(:startResidualDate"
							.concat(residualNumber
									+ ",'YYYY-MM-DD') and to_date(:endResidualDate")
							.concat(residualNumber + ",'YYYY-MM-DD')) ");

					residualNumber++;
				}

			}

			break;

		case BizConstants.PERIOD_THISMONTH:
		case BizConstants.RANGE_MONTH:

			parameters.setPeriodTableName(parameters.getPeriodTableName()
					.concat(BizConstants.WEEKLY));

			for (Map.Entry<String, Object> entry : requestVO
					.getPeriodStrategyMap().entrySet()) {

				if (entry.getKey().contains(BizConstants.KEY_START_RANGE)) {

					if (!whereClauseForPeriodTable.isEmpty()) {
						whereClauseForPeriodTable += BizConstants.OR_STRING;
					}

					whereClauseForPeriodTable += " (pcm.year = :year" + index
							+ "  and pcm.week between :startRange" + index
							+ " and :endRange" + index + ") ";
					index++;

				} else if (StringUtils.contains(entry.getKey(),
						BizConstants.KEY_START_RESIDUAL_DATE)) {

					if (!whereClauseForCoreTable.isEmpty()) {
						whereClauseForCoreTable += BizConstants.OR_STRING;
					}

					whereClauseForCoreTable += "  (cast(pc.logtime as date) between to_date(:startResidualDate"
							.concat(residualNumber
									+ ",'YYYY-MM-DD') and to_date(:endResidualDate")
							.concat(+residualNumber + ",'YYYY-MM-DD')) ");

					residualNumber++;
				}

			}

			break;

		case BizConstants.PERIOD_THISWEEK:
		case BizConstants.RANGE_WEEK:

			parameters.setPeriodTableName(parameters.getPeriodTableName()
					.concat(BizConstants.DAILY));

			for (Map.Entry<String, Object> entry : requestVO
					.getPeriodStrategyMap().entrySet()) {

				if (entry.getKey().contains(BizConstants.KEY_START_RANGE)) {

					if (!whereClauseForPeriodTable.isEmpty()) {
						whereClauseForPeriodTable += BizConstants.OR_STRING;
					}

					whereClauseForPeriodTable += " (cast(pcm.logtime as date) between to_date(:startRange"
							+ index
							+ ",'YYYY-MM-DD') and to_date(:endRange"
							+ index + ",'YYYY-MM-DD')) ";
					index++;

				} else if (StringUtils.contains(entry.getKey(),
						BizConstants.KEY_START_RESIDUAL_DATE)) {

					if (!whereClauseForCoreTable.isEmpty()) {
						whereClauseForCoreTable += BizConstants.OR_STRING;
					}

					whereClauseForCoreTable += "  (cast(pc.logtime as date) between to_date(:startResidualDate"
							+ residualNumber
							+ ",'YYYY-MM-DD') and to_date(:endResidualDate"
							+ residualNumber + ",'YYYY-MM-DD')) ";

					residualNumber++;
				}

			}

			break;

		case BizConstants.PERIOD_TODAY:
		case BizConstants.RANGE_DAY:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiType(),
					BizConstants.STATS_API_CALL_FOR_DOWNLOAD)) {

				whereClauseForCoreTable += "  (cast(pc.logtime as timestamp) between to_timestamp(:startResidualDate1"
						+ ",'YYYY-MM-DD HH24:MI:SS.MS') and to_timestamp(:endResidualDate1,'YYYY-MM-DD HH24:MI:SS.MS')) ";
			} else {

				whereClauseForCoreTable += "  (cast(pc.logtime as date) between to_date(:startResidualDate1"
						+ ",'YYYY-MM-DD') and to_date(:endResidualDate1,'YYYY-MM-DD')) ";

			}

			break;

		case BizConstants.PERIOD_24HOURS:

			whereClauseForCoreTable += "  cast(pc.logtime as timestamp)  between cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp) "
					+ "  and cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp) ";

			break;
		}

		if ((StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
				BizConstants.STATS_API_CALL_BY_GROUP) || StringUtils
				.equalsIgnoreCase(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT))
				&& (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.EFFICIENCY))) {

			whereClauseForCoreTable += " and efficiency > 0 ";
			whereClauseForPeriodTable += " and efficiency > 0 ";

		}

		parameters.setPeriodWhereSQL(whereClauseForPeriodTable);
		parameters.setCoreWhereSQL(whereClauseForCoreTable);

	}

	private void getSelectParamsForAccumulatedGraphs(StatsRequestVO requestVO,
			QueryParameters parameters) {

		String parameterSQLType = null;
		String selectOuterParams = BizConstants.EMPTY_STRING;
		String selectInnerParams = BizConstants.EMPTY_STRING;

		String joinForPeriodTable = BizConstants.EMPTY_STRING;
		String joinForCoreTable = BizConstants.EMPTY_STRING;

		if ((StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
				BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT))) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				if ((StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.POWER_CONSUMPTION) || StringUtils
						.equalsIgnoreCase(requestVO.getParameter(),
								BizConstants.DIFF_TEMPERATURE))) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					// Ravi Modified
					joinForCoreTable = " left outer join ( select %s , indoorunit_id,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),"
							+ " ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime ,  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime "
							+ " from %s pc inner join indoorunits idu on idu.id  = pc.indoorunit_id left join  groups g on idu.siteid =  g.uniqueid "
							+ " left join timezonemaster tzm on g.timezone = tzm.id left join pg_timezone_names ptz on tzm.timezone =  ptz.name where  %s group by pc.indoorunit_id ) pc on idu.id  = pc.indoorunit_id ";

				} else {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = " left outer join ( select %s , pc.refid ,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),"
							+ " ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime ,  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime "
							+ " from %s pc inner join refrigerantmaster rm on rm.refid  = pc.refid "
							+ " Left Join groups g on  rm.siteid = g.uniqueid left join timezonemaster tzm on g.timezone = tzm.id left join pg_timezone_names ptz on tzm.timezone =  ptz.name  where  %s group by pc.refid ) pc on rm.refid  = pc.refid ";

				}

			} else {

				if ((StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.POWER_CONSUMPTION) || StringUtils
						.equalsIgnoreCase(requestVO.getParameter(),
								BizConstants.DIFF_TEMPERATURE))) {

					joinForPeriodTable = " left outer join ( select %s , indoorunit_id from %s pcm where  %s	group by pcm.indoorunit_id ) pcm on idu.id  = pcm.indoorunit_id ";
					joinForCoreTable = " left outer join ( select %s , indoorunit_id from %s pc where  %s group by pc.indoorunit_id ) pc on idu.id  = pc.indoorunit_id ";

				} else {

					joinForPeriodTable = " left outer join ( select %s , refid from %s pcm where  %s	group by pcm.refid ) pcm on rm.refid  = pcm.refid ";
					joinForCoreTable = " left outer join ( select %s , refid from %s pc where  %s group by pc.refid ) pc on rm.refid  = pc.refid ";

				}

			}

		}
		if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
				BizConstants.STATS_API_CALL_BY_GROUP)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.CAPACITY)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.EFFICIENCY)) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = " left outer join ( select %s , pc.refid,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),"
							+ " ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime ,  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime "
							+ " from usp_getrefrigerant_suplylevelgroupname(:ids,:label) fn inner join groups g on fn.supplygroupid =  g.id left join timezonemaster tzm on g.timezone = tzm.id "
							+ "  left join pg_timezone_names ptz on tzm.timezone =  ptz.name  left join %s pc on fn.refrigerantid = pc.refid where  %s group by pc.refid ) pc on rm.refid  = pc.refid ";

				} else {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = " left outer join ( select %s , indoorunit_id ,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),"
							+ " ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime ,  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime "
							+ " from usp_getindoorunits_supplylevelgroupname(:ids,:label) fn inner join groups g on fn.groupid =  g.id left join timezonemaster tzm on g.timezone = tzm.id "
							+ " left join pg_timezone_names ptz on tzm.timezone =  ptz.name left join %s pc on fn.indoorunitid = pc.indoorunit_id where  %s group by pc.indoorunit_id ) pc on fn.indoorunitid  = pc.indoorunit_id ";

				}

			} else {

				if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
						BizConstants.CAPACITY)
						|| StringUtils.equalsIgnoreCase(
								requestVO.getParameter(),
								BizConstants.EFFICIENCY)) {

					joinForPeriodTable = " left outer join ( select %s , refid from %s pcm where  %s	group by pcm.refid ) pcm on rm.refid  = pcm.refid ";
					joinForCoreTable = " left outer join ( select %s , refid from %s pc where  %s group by pc.refid ) pc on rm.refid  = pc.refid ";

				} else {

					joinForPeriodTable = " left outer join ( select %s , indoorunit_id from %s pcm where  %s	group by pcm.indoorunit_id ) pcm on idu.id  = pcm.indoorunit_id ";
					joinForCoreTable = " left outer join ( select %s , indoorunit_id from %s pc where  %s group by pc.indoorunit_id ) pc on idu.id  = pc.indoorunit_id ";

				}

			}

		}

		if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
				BizConstants.STATS_API_CALL_BY_AIRCON)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
						BizConstants.ID_TYPE_INDOOR)) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = " left outer join ( select %s , indoorunit_id,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),"
							+ " ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime ,  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime "
							+ " from %s pc inner join indoorunits idu on idu.id  = pc.indoorunit_id left join  groups g on idu.group_id =  g.id "
							+ " left join timezonemaster tzm on g.timezone = tzm.id left join pg_timezone_names ptz on tzm.timezone =  ptz.name where  %s group by pc.indoorunit_id ) pc on idu.id  = pc.indoorunit_id ";
				}

				if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
						BizConstants.ID_TYPE_GROUP)) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = " left outer join ( select %s , indoorunit_id,Min(cast((Select (display_in_other_tz(cast(:startResidualDate1 as timestamp),"
							+ " ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) fromtime ,  max(cast((Select (display_in_other_tz(cast(:endResidualDate1 as timestamp),ptz.name,'YYYY-MM-DD HH24:00:00 TZ'))) as timestamp)) totime "
							+ " from  usp_getindoorunits_supplygroupname(:ids) fn   inner join groups g on fn.groupid =  g.id left join timezonemaster tzm on g.timezone = tzm.id "
							+ " left join pg_timezone_names ptz on tzm.timezone =  ptz.name left join %s pc on fn.indoorunitid = pc.indoorunit_id where  %s group by pc.indoorunit_id ) pc on idu.id  = pc.indoorunit_id ";
				}

			} else {
				joinForPeriodTable = " left outer join ( select %s , indoorunit_id from %s pcm where  %s	group by pcm.indoorunit_id ) pcm on idu.id  = pcm.indoorunit_id ";
				joinForCoreTable = " left outer join ( select %s , indoorunit_id from %s pc where  %s group by pc.indoorunit_id ) pc on idu.id  = pc.indoorunit_id ";
			}

		}

		if ((requestVO.getPeriodStrategyMap().containsKey(
				BizConstants.KEY_START_RANGE1) || requestVO
				.getPeriodStrategyMap().containsKey(BizConstants.KEY_YEAR1))
				&& requestVO.getPeriodStrategyMap().containsKey(
						BizConstants.KEY_START_RESIDUAL_DATE1)) {

			parameterSQLType = BizConstants.EMPTY_STRING;

		} else if (!(requestVO.getPeriodStrategyMap().containsKey(
				BizConstants.KEY_START_RANGE1) || requestVO
				.getPeriodStrategyMap().containsKey(BizConstants.KEY_YEAR1))
				&& requestVO.getPeriodStrategyMap().containsKey(
						BizConstants.KEY_START_RESIDUAL_DATE1)) {

			parameterSQLType = BizConstants.PARAMETER_SQL_HOURLY;

			joinForPeriodTable = BizConstants.EMPTY_STRING;

		} else if ((requestVO.getPeriodStrategyMap().containsKey(
				BizConstants.KEY_START_RANGE1) || requestVO
				.getPeriodStrategyMap().containsKey(BizConstants.KEY_YEAR1))
				&& !requestVO.getPeriodStrategyMap().containsKey(
						BizConstants.KEY_START_RESIDUAL_DATE1)) {

			parameterSQLType = BizConstants.PARAMETER_SQL_RANGE;

			joinForCoreTable = BizConstants.EMPTY_STRING;

		}

		switch (requestVO.getParameter()) {

		case BizConstants.POWER_CONSUMPTION:

			selectOuterParams = " sum(sumtotal_power_consumption) as sumtotal_power_consumption, avg(t.avgtotalpower) as avgtotalpower ";

			if (StringUtils.equalsIgnoreCase(parameterSQLType,
					BizConstants.PARAMETER_SQL_HOURLY)) {

				selectInnerParams = SQL_POWER_CONSUMPTION_PARAMETERS_HOURLY
						.toString();

			} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
					BizConstants.PARAMETER_SQL_RANGE)) {

				selectInnerParams = SQL_POWER_CONSUMPTION_PARAMETERS_RANGE
						.toString();

			} else {

				selectInnerParams = SQL_POWER_CONSUMPTION_PARAMETERS.toString();

			}

			break;

		case BizConstants.CAPACITY:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
					|| StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_GROUP)) {

				selectOuterParams = " avg(t.avgratedcapacity) as avgratedcapacity, avg(t.avgcurrentcapacity) as avgcurrentcapacity,"
						+ " avg(t.avgoutdoortemp) as avgoutdoortemp ";

				if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_HOURLY)) {

					selectInnerParams = SQL_CAPACITY_PARAMETERS_HOURLY_REFRIGERANTCIRCUIT
							.toString();

				} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_RANGE)) {

					selectInnerParams = SQL_CAPACITY_PARAMETERS_RANGE_REFRIGERANTCIRCUIT
							.toString();

				} else {

					selectInnerParams = SQL_CAPACITY_PARAMETERS_REFRIGERANTCIRCUIT
							.toString();

				}

			} else {

				selectOuterParams = " avg(t.avgcurrentcapacity) as avgcurrentcapacity, avg(t.avgtotalcapacity) as avgtotalcapacity,"
						+ " avg(t.avgoutdoortemp) as avgoutdoortemp ";

				if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_HOURLY)) {

					selectInnerParams = SQL_CAPACITY_PARAMETERS_HOURLY
							.toString();

				} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_RANGE)) {

					selectInnerParams = SQL_CAPACITY_PARAMETERS_RANGE
							.toString();

				} else {

					selectInnerParams = SQL_CAPACITY_PARAMETERS.toString();

				}
			}

			break;

		case BizConstants.EFFICIENCY:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
					|| StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_GROUP)) {

				selectOuterParams = " avg(t.avgefficiency) as avgefficiency ";

				if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_HOURLY)) {

					selectInnerParams = SQL_EFFICIENCY_PARAMETERS_HOURLY_REFRIGERANTCIRCUIT
							.toString();

				} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_RANGE)) {

					selectInnerParams = SQL_EFFICIENCY_PARAMETERS_RANGE_REFRIGERANTCIRCUIT
							.toString();

				} else {

					selectInnerParams = SQL_EFFICIENCY_PARAMETERS_REFRIGERANTCIRCUIT
							.toString();

				}

			} else {

				selectOuterParams = " avg(t.avgsetpointtemp) as avgsetpointtemp , avg(t.avgroomtemp) as avgroomtemp ,"
						+ " avg(t.avgoutdoortemp) as avgoutdoortemp , avg(t.avgefficiency) as avgefficiency ";

				if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_HOURLY)) {

					selectInnerParams = SQL_EFFICIENCY_PARAMETERS_HOURLY
							.toString();

				} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_RANGE)) {

					selectInnerParams = SQL_EFFICIENCY_PARAMETERS_RANGE
							.toString();

				} else {

					selectInnerParams = SQL_EFFICIENCY_PARAMETERS.toString();

				}
			}

			break;

		case BizConstants.DIFF_TEMPERATURE:

			selectOuterParams = " avg(t.avgroomtemp) as avgroomtemp , avg(t.avgsetpointtemp) as avgsetpointtemp , "
					+ "sum(sumcurrentcapacity_heating) as sumcurrentcapacity_heating, sum(sumcurrentcapacity_cooling) as sumcurrentcapacity_cooling ";

			if (StringUtils.equalsIgnoreCase(parameterSQLType,
					BizConstants.PARAMETER_SQL_HOURLY)) {

				selectInnerParams = SQL_DIFFTEMP_PARAMETERS_HOURLY.toString();

			} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
					BizConstants.PARAMETER_SQL_RANGE)) {

				selectInnerParams = SQL_DIFFTEMP_PARAMETERS_RANGE.toString();

			} else {
				selectInnerParams = SQL_DIFFTEMP_PARAMETERS.toString();

			}

			break;

		case BizConstants.WORKING_HOURS:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

				selectOuterParams = " sum(sumworkinghour1) as sumworkinghour1,sum(sumworkinghour2) as sumworkinghour2, sum(sumworkinghour3) as sumworkinghour3 ";

				if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_HOURLY)) {

					selectInnerParams = SQL_WORKHOUR_PARAMETERS_HOURLY_REFRIGERANTCIRCUIT
							.toString();

				} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_RANGE)) {

					selectInnerParams = SQL_WORKHOUR_PARAMETERS_RANGE_REFRIGERANTCIRCUIT
							.toString();

				} else {

					selectInnerParams = SQL_WORKHOUR_PARAMETERS_REFRIGERANTCIRCUIT
							.toString();

				}

			} else {

				selectOuterParams = " sum(sumhighon) as sumhighon,sum(sumhighoff) as sumhighoff, sum(summediumon) as summediumon,sum(summediumoff) as summediumoff, sum(sumlowon) as sumlowon,sum(sumlowoff) as sumlowoff, ";

				if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_HOURLY)) {

					selectInnerParams = SQL_WORKHOUR_PARAMETERS_HOURLY
							.toString();

				} else if (StringUtils.equalsIgnoreCase(parameterSQLType,
						BizConstants.PARAMETER_SQL_RANGE)) {

					selectInnerParams = SQL_WORKHOUR_PARAMETERS_RANGE
							.toString();

				} else {

					selectInnerParams = SQL_WORKHOUR_PARAMETERS.toString();

				}

			}

			break;

		}

		parameters.setSelectOuterParams(selectOuterParams);
		parameters.setSelectInnerParams(selectInnerParams);
		parameters.setJoinForPeriodTable(joinForPeriodTable);
		parameters.setJoinForCoreTable(joinForCoreTable);

	}

	private void getJoinForPeriodAndCoreTableForAccumulated(
			QueryParameters parameters) {

		String joinForPeriodTable = BizConstants.EMPTY_STRING;
		String joinForCoreTable = BizConstants.EMPTY_STRING;
		String joinCondition = BizConstants.EMPTY_STRING;
		String groupByClause = BizConstants.EMPTY_STRING;

		if (!StringUtils.equals(parameters.getJoinForPeriodTable(),
				BizConstants.EMPTY_STRING)) {

			joinForPeriodTable = String.format(
					parameters.getJoinForPeriodTable(),
					parameters.getColumnNameSubQuery(),
					parameters.getPeriodTableName(),
					parameters.getPeriodWhereSQL());
			joinForPeriodTable = joinForPeriodTable.replace("pccount",
					"pcmcount");

			groupByClause = groupByClause + " pcm.indoorunit_id, ";

			joinCondition = " fnouter.indoorunitid = t.pcmindoorunit_id ";

		}

		if (!StringUtils.equals(parameters.getJoinForCoreTable(),
				BizConstants.EMPTY_STRING)) {

			joinForCoreTable = String
					.format(parameters.getJoinForCoreTable(),
							parameters.getColumnNameSubQuery(),
							parameters.getCoreTableName(),
							parameters.getCoreWhereSQL());

			groupByClause = groupByClause + " pc.indoorunit_id, ";

			if (!StringUtils.equals(joinCondition, BizConstants.EMPTY_STRING)) {

				joinCondition = joinCondition
						+ " or fnouter.indoorunitid = t.pcindoorunit_id";
			} else {

				joinCondition = " fnouter.indoorunitid = t.pcindoorunit_id ";
			}

		}

		parameters.setJoinForPeriodTable(joinForPeriodTable);
		parameters.setJoinForCoreTable(joinForCoreTable);
		parameters.setJoinCondition(joinCondition);
		parameters.setGroupClause(groupByClause);

	}

	/**
	 * This method is used to determine Extract method of postgre to find out
	 * sql month/week as per type of charts and periods.
	 * 
	 * @param requestVO
	 * @param parameters
	 */
	private void getExtractParametersForChronological(StatsRequestVO requestVO,
			QueryParameters parameters) {

		String chronologicalType = BizConstants.EMPTY_STRING;
		String chronSelectQuery = BizConstants.EMPTY_STRING;
		String innerGroupByQuery = BizConstants.EMPTY_STRING;
		String yearForMonthlyAndWeekly = BizConstants.EMPTY_STRING;
		String castYear = BizConstants.EMPTY_STRING;

		switch (requestVO.getPeriod()) {

		case BizConstants.RANGE_3YEAR:

			chronologicalType = BizConstants.CHRON_TYPE_YEAR;
			chronSelectQuery = "EXTRACT(" + chronologicalType
					+ " FROM pc.logtime) as " + chronologicalType;
			innerGroupByQuery = "EXTRACT(" + chronologicalType
					+ " FROM pc.logtime) ";

			break;

		case BizConstants.PERIOD_THISYEAR:
		case BizConstants.RANGE_YEAR:

			yearForMonthlyAndWeekly = " ,cast (pcm.year as bigint)  ";

			chronologicalType = BizConstants.CHRON_TYPE_MONTH;
			chronSelectQuery = "EXTRACT("
					+ chronologicalType
					+ " FROM pc.logtime) as month ,EXTRACT(year FROM pc.logtime)  as year ";
			innerGroupByQuery = "EXTRACT(" + chronologicalType
					+ " FROM pc.logtime) ,EXTRACT(year FROM pc.logtime) ";
			castYear = " ,cast (pcm.year as bigint) ";

			break;

		case BizConstants.PERIOD_THISMONTH:
		case BizConstants.RANGE_MONTH:

			yearForMonthlyAndWeekly = " ,cast (pcm.year as bigint) ";

			chronologicalType = BizConstants.CHRON_TYPE_WEEK;
			chronSelectQuery = "EXTRACT("
					+ chronologicalType
					+ " FROM pc.logtime) as week ,EXTRACT(year FROM pc.logtime) as year ";
			innerGroupByQuery = "EXTRACT(" + chronologicalType
					+ " FROM pc.logtime) ,EXTRACT(year FROM pc.logtime)   ";
			castYear = " ,cast (pcm.year as bigint) ";

			break;

		case BizConstants.PERIOD_THISWEEK:
		case BizConstants.RANGE_WEEK:

			chronologicalType = BizConstants.CHRON_TYPE_DAY;
			chronSelectQuery = " pc.logtime as " + BizConstants.CHRON_TYPE_DAY;
			innerGroupByQuery = " pc.logtime ";

			break;

		case BizConstants.PERIOD_TODAY:
		case BizConstants.RANGE_DAY:
		case BizConstants.PERIOD_24HOURS:

			chronologicalType = BizConstants.CHRON_TYPE_DAY;

			chronSelectQuery = " pc.logtime as " + chronologicalType;
			innerGroupByQuery = " pc.logtime ";

			break;

		}

		parameters.setChronologicalType(chronologicalType);
		parameters.setExtractSelectParams(chronSelectQuery);
		parameters.setGroupClause(innerGroupByQuery);
		parameters.setYearForMonthlyAndWeekly(yearForMonthlyAndWeekly);
		parameters.setCastYear(castYear);

	}

	private void getSelectParamsForChronologicalGraphs(
			StatsRequestVO requestVO, QueryParameters parameters) {

		String joinForPeriodTable = BizConstants.EMPTY_STRING;
		String joinForCoreTable = BizConstants.EMPTY_STRING;

		if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
				BizConstants.STATS_API_CALL_BY_AIRCON)
				&& (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
						BizConstants.ID_TYPE_GROUP))) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				joinForPeriodTable = BizConstants.EMPTY_STRING;

				joinForCoreTable = SQL_GET_STATS_CHRONOLOGICAL_AIRCON_GROUP_PAST24HOUR
						.toString().replaceAll("pcm", "pc");

			} else {

				joinForPeriodTable = SQL_GET_STATS_CHRONOLOGICAL_AIRCON_GROUP
						.toString();

				joinForCoreTable = SQL_GET_STATS_CHRONOLOGICAL_AIRCON_GROUP
						.toString().replaceAll("pcm", "pc");

			}

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.ID_TYPE_INDOOR)
				|| StringUtils.equalsIgnoreCase(requestVO.getIdType(),
						BizConstants.ID_TYPE_OUTDOOR)) {

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_24HOURS)) {

				joinForPeriodTable = BizConstants.EMPTY_STRING;

				joinForCoreTable = SQL_GET_STATS_CHRONOLOGICAL_AIRCON_UNIT_PAST24HOUR
						.toString().replaceAll("pcm", "pc");

			} else {
				joinForPeriodTable = SQL_GET_STATS_CHRONOLOGICAL_AIRCON_UNIT
						.toString();

				joinForCoreTable = SQL_GET_STATS_CHRONOLOGICAL_AIRCON_UNIT
						.toString().replaceAll("pcm", "pc");
			}

		} else if (StringUtils.equalsIgnoreCase(requestVO.getIdType(),
				BizConstants.STATISTICS_REFRIGERANT)) {
			if (!StringUtils.equalsIgnoreCase(requestVO.getParameter(),
					BizConstants.POWER_CONSUMPTION)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_PAST24HOUR
							.toString().replaceAll("pcm", "pc");

				} else {

					joinForPeriodTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL
							.toString();
					joinForCoreTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL
							.toString().replaceAll("pcm", "pc");

				}
			} else {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_INDOORUNIT_PAST24HOUR
							.toString().replaceAll("pcm", "pc");

				} else {

					joinForPeriodTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_INDOORUNIT
							.toString();
					joinForCoreTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_INDOORUNIT
							.toString().replaceAll("pcm", "pc");

				}

			}

		} else {

			if (StringUtils.equalsIgnoreCase(requestVO.getParameter(),
					BizConstants.EFFICIENCY)
					|| StringUtils.equalsIgnoreCase(requestVO.getParameter(),
							BizConstants.CAPACITY)) {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_GROUPS_PAST24HOUR
							.toString().replaceAll("pcm", "pc");

				} else {

					joinForPeriodTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_GROUPS
							.toString();
					joinForCoreTable = SQL_GET_STATS_REFRIGERANT_CHRONOLOGICAL_GROUPS
							.toString().replaceAll("pcm", "pc");

				}

			} else {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_24HOURS)) {

					joinForPeriodTable = BizConstants.EMPTY_STRING;
					joinForCoreTable = SQL_GET_STATS_CHRONOLOGICAL_PAST24HOUR
							.toString().replaceAll("pcm", "pc");

				} else {

					joinForPeriodTable = SQL_GET_STATS_CHRONOLOGICAL.toString();
					joinForCoreTable = SQL_GET_STATS_CHRONOLOGICAL.toString()
							.replaceAll("pcm", "pc");
				}
			}

		}
		String selectOuterParams = BizConstants.EMPTY_STRING;
		String selectInnerParams = BizConstants.EMPTY_STRING;

		if (!(requestVO.getPeriodStrategyMap().containsKey(
				BizConstants.KEY_START_RANGE1) || requestVO
				.getPeriodStrategyMap().containsKey(BizConstants.KEY_YEAR1))
				&& requestVO.getPeriodStrategyMap().containsKey(
						BizConstants.KEY_START_RESIDUAL_DATE1)) {

			joinForPeriodTable = BizConstants.EMPTY_STRING;

		} else if ((requestVO.getPeriodStrategyMap().containsKey(
				BizConstants.KEY_START_RANGE1) || requestVO
				.getPeriodStrategyMap().containsKey(BizConstants.KEY_YEAR1))
				&& !requestVO.getPeriodStrategyMap().containsKey(
						BizConstants.KEY_START_RESIDUAL_DATE1)) {

			joinForCoreTable = BizConstants.EMPTY_STRING;

		}

		switch (requestVO.getParameter()) {

		case BizConstants.POWER_CONSUMPTION:

			selectOuterParams = " avg(avgtotalpower) avgtotalpower,SUM(total_power_consumption) total_power_consumption ";

			selectInnerParams = " (COALESCE(sum(pcm.total_power_consumption),0) /sum(pcmcount))  as avgtotalpower, SUM(pcm.total_power_consumption) as total_power_consumption ";

			break;

		case BizConstants.CAPACITY:

			if (StringUtils.equals(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
					|| StringUtils.equals(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_GROUP)) {

				selectOuterParams = " avg(ratedcapacity) as ratedcapacity, avg(currentcapacity) as currentcapacity, avg(outdoortemp) as outdoortemp ";

				selectInnerParams = " (case when sum(pcm.ratedcapacity) is not null  then (COALESCE(sum(pcm.ratedcapacity),0) /sum(ratedcapacitycount)) else null end)  as ratedcapacity,"
						+ " (case when sum(pcm.currentcapacity) is not null  then (COALESCE(sum(pcm.currentcapacity),0) /sum(currentcapacitycount)) else null end)  as currentcapacity,"
						+ " (case when sum(pcm.outdoortemp) is not null  then (COALESCE(sum(pcm.outdoortemp),0) /sum(pcmcount)) else null end)  as outdoortemp ";

			} else {

				selectOuterParams = " avg(currentcapacity) as currentcapacity, avg(totalcapacity) as totalcapacity, avg(outdoortemp) as outdoortemp ";

				selectInnerParams = " (case when sum(pcm.currentcapacity) is not null  then (COALESCE(sum(pcm.currentcapacity),0) /sum(pcmcount)) else null end)  as currentcapacity, "
						+ "(case when sum(pcm.totalcapacity) is not null  then (COALESCE(sum(pcm.totalcapacity),0) /sum(pcmcount)) else null end)  as totalcapacity, "
						+ "(case when sum(pcm.outdoortemp) is not null  then (COALESCE(sum(pcm.outdoortemp),0) /sum(pcmcount)) else null end)  as outdoortemp ";
			}

			break;

		case BizConstants.EFFICIENCY:

			if (StringUtils.equals(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
					|| StringUtils.equals(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_GROUP)) {

				selectOuterParams = " avg(efficiency) as efficiency ";

				selectInnerParams = " (COALESCE(sum(pcm.efficiency),0) /sum(pcmcount))  as efficiency ";

			} else {

				selectOuterParams = " avg(setpointtemp) as setpointtemp , avg(roomtemp) as roomtemp ,"
						+ " avg(outdoortemp) as outdoortemp , avg(efficiency) as efficiency ";

				selectInnerParams = " (COALESCE(sum(pcm.setpointtemp),0) /sum(pcmcount))  as setpointtemp, (COALESCE(SUM(pcm.roomtemp),0) /sum(pcmcount)) as roomtemp, "
						+ "(COALESCE(sum(pcm.outdoortemp),0) /sum(pcmcount))  as outdoortemp, (COALESCE(sum(pcm.efficiency),0) /sum(pcmcount))  as efficiency ";
			}

			break;

		case BizConstants.DIFF_TEMPERATURE:

			selectOuterParams = " avg(roomtemp) as roomtemp , avg(setpointtemp) as setpointtemp  ";

			selectInnerParams = " (COALESCE(sum(pcm.roomtemp),0) /sum(pcm.countroomtemp))  as roomtemp, case when sum(pcm.countsetpointtemp) > 0 then (COALESCE(SUM(pcm.setpointtemp),0) /sum(pcm.countsetpointtemp)) else null end setpointtemp ";

			break;

		case BizConstants.WORKING_HOURS:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

				selectOuterParams = " sum(workinghour1) as workinghour1,sum(workinghour2) as workinghour2, sum(workinghour3) as workinghour3 ";

				selectInnerParams = " COALESCE(sum(pcm.workinghour1),0)  as workinghour1,COALESCE(sum(pcm.workinghour2),0)  as workinghour2, COALESCE(SUM(pcm.workinghour3),0) as workinghour3 "
						+ "  ";

			} else {

				selectOuterParams = " sum(highon) as highon,sum(highoff) as highoff, sum(mediumon) as mediumon,sum(mediumoff) as mediumoff, sum(lowon) as lowon, "
						+ "sum(lowoff) as lowoff ";

				selectInnerParams = " COALESCE(sum(pcm.highon),0)  as highon,COALESCE(sum(pcm.highoff),0)  as highoff, COALESCE(SUM(pcm.mediumon),0) as mediumon, "
						+ " COALESCE(SUM(pcm.mediumoff),0) as mediumoff,COALESCE(sum(pcm.lowon),0)  as lowon,COALESCE(sum(pcm.lowoff),0)  as lowoff  ";
			}

			break;
		}

		parameters.setJoinForPeriodTable(joinForPeriodTable);
		parameters.setJoinForCoreTable(joinForCoreTable);
		parameters.setSelectOuterParams(selectOuterParams);
		if (parameters.getSelectInnerParams() != null) {
			parameters.setSelectInnerParams(parameters.getSelectInnerParams()
					+ selectInnerParams);
		} else {

			parameters.setSelectInnerParams(selectInnerParams);

		}

		if (!StringUtils.equals(requestVO.getParameter(),
				BizConstants.POWER_CONSUMPTION)
				&& StringUtils.equals(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

			parameters.setSelectInnerParams(parameters.getSelectInnerParams()
					.replace("odu.", "odut."));
			parameters.setSelectOuterParams(parameters.getSelectOuterParams()
					.replace("odu.", "odut."));

		}

	}

	/**
	 * This method is used to determine join for core or period or both as per
	 * type of charts(chronological) and periods.
	 * 
	 * @param parameters
	 * @param requestVO
	 */
	private void getJoinForPeriodAndCoreTableForChronological(
			QueryParameters parameters, StatsRequestVO requestVO) {

		String joinForPeriodTable = BizConstants.EMPTY_STRING;
		String joinForCoreTable = BizConstants.EMPTY_STRING;

		if (!StringUtils.equals(parameters.getJoinForPeriodTable(),
				BizConstants.EMPTY_STRING)) {

			String outerGroupClause = "pcm.".concat(parameters
					.getChronologicalType().concat(parameters.getCastYear()));

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_THISWEEK)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.RANGE_WEEK)) {

				joinForPeriodTable = String.format(
						parameters.getJoinForPeriodTable(),
						"cast (pcm.".concat(parameters.getChronologicalType())
								.concat(" as date)"),
						parameters.getSelectInnerParams().concat(
								parameters.getCastYear()),
						parameters.getColumnNameSubQuery().concat(
								parameters.getCastYear()),
						"cast (pcm.".concat(parameters.getChronologicalType())
								.concat(" as date)"), parameters
								.getPeriodTableName(), parameters
								.getPeriodWhereSQL(),
						"cast (".concat(outerGroupClause).concat(" as date)"),
						"cast (".concat(outerGroupClause).concat(" as date)"));

			} else if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.RANGE_DAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_TODAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

				joinForPeriodTable = String.format(
						parameters.getJoinForPeriodTable(),
						"pcm." + parameters.getChronologicalType(),
						parameters.getSelectInnerParams().concat(
								parameters.getCastYear()),
						parameters.getColumnNameSubQuery().concat(
								parameters.getCastYear()), parameters
								.getChronologicalType(), parameters
								.getPeriodTableName(), parameters
								.getPeriodWhereSQL(), outerGroupClause,
						outerGroupClause);

			} else {

				joinForPeriodTable = String.format(
						parameters.getJoinForPeriodTable(),
						"cast (pcm." + parameters.getChronologicalType()
								+ " as bigint)",
						parameters.getSelectInnerParams().concat(
								parameters.getCastYear()),
						parameters.getColumnNameSubQuery().concat(
								parameters.getCastYear()), parameters
								.getChronologicalType(), parameters
								.getPeriodTableName(), parameters
								.getPeriodWhereSQL(), outerGroupClause,
						outerGroupClause);

			}

			joinForPeriodTable = joinForPeriodTable.replace("pccount",
					"pcmcount");

		}

		if (!StringUtils.equals(parameters.getJoinForCoreTable(),
				BizConstants.EMPTY_STRING)) {

			String outerGroupClause = "pc.".concat(parameters
					.getChronologicalType().concat(parameters.getCastYear()));

			if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.PERIOD_THISWEEK)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.RANGE_WEEK)) {

				joinForCoreTable = String.format(
						parameters.getJoinForCoreTable(),
						"cast (pc.".concat(parameters.getChronologicalType())
								.concat(" as date)"),
						parameters.getSelectInnerParams().concat(
								parameters.getCastYear()),
						parameters.getColumnNameSubQuery(),
						"cast (".concat(parameters.getGroupClause()).concat(
								" as date)"),
						parameters.getCoreTableName(),
						parameters.getCoreWhereSQL(),
						"cast (".concat(parameters.getGroupClause()).concat(
								" as date)"), "cast (".concat(outerGroupClause)
								.concat(" as date)"));

			} else if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
					BizConstants.RANGE_DAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_TODAY)
					|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_24HOURS)) {

				joinForCoreTable = String.format(
						parameters.getJoinForCoreTable(),
						"pc." + parameters.getChronologicalType(),
						parameters.getSelectInnerParams().concat(
								parameters.getCastYear()), parameters
								.getColumnNameSubQuery(), parameters
								.getExtractSelectParams(), parameters
								.getCoreTableName(), parameters
								.getCoreWhereSQL(),
						parameters.getGroupClause(), outerGroupClause);

			} else {

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_THISMONTH)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_MONTH)) {

					parameters
							.setCastYear(", (case when cast (week as bigint) = 53 then cast (year as bigint) - 1 else cast (year as bigint) end) as year ");
				}

				joinForCoreTable = String.format(
						parameters.getJoinForCoreTable(),
						"cast (pc." + parameters.getChronologicalType()
								+ " as bigint)",
						parameters.getSelectInnerParams().concat(
								parameters.getCastYear()), parameters
								.getColumnNameSubQuery(), parameters
								.getExtractSelectParams(), parameters
								.getCoreTableName(), parameters
								.getCoreWhereSQL(),
						parameters.getGroupClause(), outerGroupClause);

				if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
						BizConstants.PERIOD_THISMONTH)
						|| StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.RANGE_MONTH)) {

					parameters.setCastYear(", cast (year as bigint) ");
				}

			}

			joinForCoreTable = joinForCoreTable.replaceAll("pcm", "pc");

			if (!StringUtils.equals(joinForPeriodTable,
					BizConstants.EMPTY_STRING)) {
				joinForPeriodTable = joinForPeriodTable.concat(" Union All ");
			}

		}

		parameters.setJoinForPeriodTable(joinForPeriodTable);
		parameters.setJoinForCoreTable(joinForCoreTable);

	}

}
