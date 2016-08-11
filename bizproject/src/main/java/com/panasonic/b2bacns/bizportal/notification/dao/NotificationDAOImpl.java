package com.panasonic.b2bacns.bizportal.notification.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.DetailsLogsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.RefrigerantSVG;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationCountVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailList;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationDetailsVO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO;
import com.panasonic.b2bacns.bizportal.notification.vo.AlarmTypeVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverViewVO;
import com.panasonic.b2bacns.bizportal.notification.vo.NotificationOverviewAlarmVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.DaoConstants;

@Service
public class NotificationDAOImpl implements NotificationDAO {

	private static final Logger logger = Logger
			.getLogger(NotificationDAOImpl.class);

	@Autowired
	private SQLDAO sqlDao;

	@Autowired
	private ACConfigDAO acConfigDAO;

	private static final String INDOORUNIT_ID = "indoorunit_id";
	private static final String OUTDOORUNIT_ID = "outdoorunit_id";
	private static final String DATE_FORMAT_NOTIFICATION = "dd/MM/yyyy HH:mm";
	private static final String MAP_ODUID = "oduid";
	private static final String MAP_USERID = "userId";
	//added by pramod
	private static final String CRITICAL = "Critical";
	private static final String NONCRITICAL = "Non-critical";
	private static final String SEVERITY_COUNT = "severitycount";
	private static final String GROUP_ID = "groupId";
	private static final String GROUP_LEVEl = "grouplevel";
	private static final String ALARM_TYPE = "alarmType";
	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String ALARMCOUNT = "alarmCount";
	private static final String ALARMSTATUS = "status";
	private static final String GROUP_NAME = "supplygroupname";
	private static final String IDU = "IDU";
	private static final String ODU = "ODU";
	private static final String CA = "CA";
	private static final String PATH_NAME = "path_name";
	private static final String COMPANY_NAME = "companyname";
	// Modified by shanf(display device_model)
	public static final StringBuilder SQL_GET_NOTIFICATION_FOR_GROUP_INDOOR = new StringBuilder(
			"select al.time as currenttime,al.id,g.name as sitegroup,t.groupname as controlgroup,al.severity,al.alarm_type,al.code as alarmcode,err.customer_description,err.maintenance_description,")
			.append("err.countermeasure_customer as countermeasurecustomer,err.countermeasure_2way as countermeasure2way,err.countermeasure_3way as countermeasure3way,al.fixed_time, al.status,")
			.append("idu.id as indoorunit_id,svg.svg_file_name as idusvgpath, idu.outdoorunit_id as oduid,svg1.svg_file_name as odusvgpath,idu.svg_max_latitude as svg_max_latitude,")
			.append("idu.svg_max_longitude as svg_max_longitude,idu.svg_min_latitude as svg_min_latitude,idu.svg_min_longitude as svg_min_longitude,idu.name ,midu.is3way_system as system,")
			.append("svg.id as svgid,svg.svg_name as svgname,svg1.id as linkedsvgid , rm.svg_id1,rm.svg_max_latitude1 as refgrnt_svg_max_latitude1,rm.svg_max_longitude1 as refgrnt_svg_max_longitude1,")
			.append("rm.svg_min_latitude1 as refgrnt_svg_min_latitude1,rm.svg_min_longitude1 as refgrnt_svg_min_longitude1, rm.svg_id2,rm.svg_max_latitude2 as refgrnt_svg_max_latitude2,rm.svg_max_longitude2 as refgrnt_svg_max_longitude2,")
			.append("rm.svg_min_latitude2 as refgrnt_svg_min_latitude2,rm.svg_min_longitude2 as refgrnt_svg_min_longitude2, rm.svg_id3,rm.svg_max_latitude3 as refgrnt_svg_max_latitude3,rm.svg_max_longitude3 as refgrnt_svg_max_longitude3,")
			.append("rm.svg_min_latitude3 as refgrnt_svg_min_latitude3,rm.svg_min_longitude3 as refgrnt_svg_min_longitude3,sts.a1 as powerstatus,sts.a2_1 as a2_1,sts.a2_2 as a2_2,sts.a28 as iseconavi,pmd.a4 as roomtemp,")
			.append("sts.a34 as energy, sts.a3 as a3,sts.a6_1 as a6_1,sts.a7_1 as a7_1,sts.a6_2 as a6_2,sts.a7_2 as a7_2,sts.a3a as a3a,sts.a3h as a3h,sts.a3c as a3c,sts.a3d as a3d,")
			.append("sts.a6a_1 as a6a_1,sts.a6h_1 as a6h_1,sts.a6c_1 as a6c_1,sts.a6d_1 as a6d_1,sts.a6f_1 as a6f_1,sts.a7a_1 as a7a_1,sts.a7h_1 as a7h_1,sts.a7c_1 as a7c_1,sts.a7d_1 as a7d_1,sts.a7f_1 as a7f_1,idu.centraladdress  as centraladdress,")
			.append("sts.a6a_2 as a6a_2,sts.a6h_2 as a6h_2,sts.a6c_2 as a6c_2,sts.a6d_2 as a6d_2,sts.a6f_2 as a6f_2,sts.a7a_2 as a7a_2,sts.a7h_2 as a7h_2,sts.a7c_2 as a7c_2,sts.a7d_2 as a7d_2,sts.a7f_2 as a7f_2,g.path_name as sitepath,c.name as companyname, idu.device_model ")
			.append("from usp_getindoorunits_supplygroupname(:id) t join notificationlog al on t.indoorunitid = al.indoorunit_id  left outer join indoorunits as idu on idu.id = al.indoorunit_id ")
			.append("left outer join  metaindoorunits as midu on idu.metaindoorunit_id=midu.id left outer join groups g on g.uniqueid = idu.siteid left outer join outdoorunits as odu ")
			.append("on idu.outdoorunit_id = odu.id left outer join errorcode_master as err on  err.code = al.code left outer join svg_master svg on svg.id = idu.svg_id ")
			.append("left outer join svg_master svg1 on svg1.id = odu.svg_id left join refrigerantmaster rm on (rm.svg_id1=idu.svg_id or rm.svg_id2=idu.svg_id or rm.svg_id3=idu.svg_id) and rm.siteid = idu.siteid ")
			.append("left outer join ct_statusinfo sts on idu.oid= sts.facl_id left outer join ct_period_measure_data pmd on idu.oid= pmd.facl_id left join companies c on c.id = g.company_id %s order by al.time desc");
	// Modified by shanf(display device_model)
	public static final StringBuilder SQL_GET_NOTIFICATION_FOR_INDOOR = new StringBuilder(
			"select al.time as currenttime,al.id,g.name as sitegroup,g1.name as controlgroup,al.severity,al.alarm_type,al.code as alarmcode,err.customer_description,err.maintenance_description,")
			.append("err.countermeasure_customer as countermeasurecustomer,err.countermeasure_2way as countermeasure2way,err.countermeasure_3way as countermeasure3way,al.fixed_time, al.status,")
			.append("idu.id as indoorunit_id,svg.svg_file_name as idusvgpath, idu.outdoorunit_id as oduid,svg1.svg_file_name as odusvgpath,idu.svg_max_latitude as svg_max_latitude,")
			.append("idu.svg_max_longitude as svg_max_longitude,idu.svg_min_latitude as svg_min_latitude,idu.svg_min_longitude as svg_min_longitude,idu.name ,midu.is3way_system as system,")
			.append("svg.id as svgid,svg.svg_name as svgname,svg1.id as linkedsvgid , rm.svg_id1,rm.svg_max_latitude1 as refgrnt_svg_max_latitude1,rm.svg_max_longitude1 as refgrnt_svg_max_longitude1,")
			.append("rm.svg_min_latitude1 as refgrnt_svg_min_latitude1,rm.svg_min_longitude1 as refgrnt_svg_min_longitude1, rm.svg_id2,rm.svg_max_latitude2 as refgrnt_svg_max_latitude2,rm.svg_max_longitude2 as refgrnt_svg_max_longitude2,")
			.append("rm.svg_min_latitude2 as refgrnt_svg_min_latitude2,rm.svg_min_longitude2 as refgrnt_svg_min_longitude2, rm.svg_id3,rm.svg_max_latitude3 as refgrnt_svg_max_latitude3,rm.svg_max_longitude3 as refgrnt_svg_max_longitude3,")
			.append("rm.svg_min_latitude3 as refgrnt_svg_min_latitude3,rm.svg_min_longitude3 as refgrnt_svg_min_longitude3,sts.a1 as powerstatus,sts.a2_1 as a2_1,sts.a2_2 as a2_2,sts.a28 as iseconavi,pmd.a4 as roomtemp,")
			.append("sts.a34 as energy, sts.a3 as a3,sts.a6_1 as a6_1,sts.a7_1 as a7_1,sts.a6_2 as a6_2,sts.a7_2 as a7_2,sts.a3a as a3a,sts.a3h as a3h,sts.a3c as a3c,sts.a3d as a3d,")
			.append("sts.a6a_1 as a6a_1,sts.a6h_1 as a6h_1,sts.a6c_1 as a6c_1,sts.a6d_1 as a6d_1,sts.a6f_1 as a6f_1,sts.a7a_1 as a7a_1,sts.a7h_1 as a7h_1,sts.a7c_1 as a7c_1,sts.a7d_1 as a7d_1,sts.a7f_1 as a7f_1,idu.centraladdress  as centraladdress,")
			.append("sts.a6a_2 as a6a_2,sts.a6h_2 as a6h_2,sts.a6c_2 as a6c_2,sts.a6d_2 as a6d_2,sts.a6f_2 as a6f_2,sts.a7a_2 as a7a_2,sts.a7h_2 as a7h_2,sts.a7c_2 as a7c_2,sts.a7d_2 as a7d_2,sts.a7f_2 as a7f_2,g.path_name as sitepath,c.name as companyname,ca.model_id as device_model  ")
			.append("from indoorunits as idu join notificationlog al on idu.id = al.indoorunit_id left outer join groups g1 on g1.id = idu.group_id ")
			//add by shanf
			.append("left outer join adapters as ca on idu.adapters_id = ca.id ")
			.append("left outer join  metaindoorunits as midu on idu.metaindoorunit_id=midu.id left outer join groups g on g.uniqueid = idu.siteid left outer join outdoorunits as odu ")
			.append("on idu.outdoorunit_id = odu.id left outer join errorcode_master as err on  err.code = al.code left outer join svg_master svg on svg.id = idu.svg_id ")
			.append("left outer join svg_master svg1 on svg1.id = odu.svg_id left join refrigerantmaster rm on (rm.svg_id1=idu.svg_id or rm.svg_id2=idu.svg_id or rm.svg_id3=idu.svg_id) and rm.siteid = idu.siteid ")
			.append("left outer join ct_statusinfo sts on idu.oid= sts.facl_id left outer join ct_period_measure_data pmd on idu.oid= pmd.facl_id left join companies c on c.id = g.company_id %s order by al.time desc");
	// Modified by shanf(display device_model)
	public static final StringBuilder SQL_GET_NOTIFICATION_FOR_GROUP_CA = new StringBuilder(
			"select distinct al.time as currenttime,al.id,g.name as sitegroup,al.severity,al.alarm_type,al.code as alarmcode,err.customer_description,err.maintenance_description,")
			.append("al.fixed_time, al.status, svg.svg_file_name as casvgpath,ca.svg_max_latitude as svg_max_latitude,ca.svg_max_longitude as svg_max_longitude,")
			.append("ca.svg_min_latitude as svg_min_latitude,ca.svg_min_longitude as svg_min_longitude, svg.id as svgid,svg.svg_name as svgname,")
			.append("ca.id as ca_id,ca.address as centraladdress,g.path_name as sitepath,c.name as companyname,ca.name,ca.model_id as device_model from notificationlog al ")
			.append("join indoorunits as idu on idu.adapters_id = al.adapterid join usp_getindoorunits_supplygroupname(:id) t on t.indoorunitid = idu.id ")
			.append("left outer join adapters as ca on idu.adapters_id = ca.id left outer join groups g on g.uniqueid = ca.siteid ")
			.append("left outer join svg_master svg on svg.id = ca.svg_id left outer join errorcode_master as err on err.code = al.code ")
			.append("left join companies c on c.id = g.company_id %s and al.indoorunit_id is null and al.outdoorunit_id is null order by al.time desc;");

	public static final StringBuilder SQL_GET_NOTIFICATION_FOR_CA = new StringBuilder(
			"select distinct al.time as currenttime,al.id,g.name as sitegroup,al.severity,al.alarm_type,al.code as alarmcode,err.customer_description,err.maintenance_description,")
			.append("al.fixed_time, al.status, svg.svg_file_name as casvgpath,ca.svg_max_latitude as svg_max_latitude,ca.svg_max_longitude as svg_max_longitude,")
			.append("ca.svg_min_latitude as svg_min_latitude,ca.svg_min_longitude as svg_min_longitude, svg.id as svgid,svg.svg_name as svgname,")
			.append("ca.id as ca_id,ca.address as centraladdress,g.path_name as sitepath,c.name as companyname,ca.name  from notificationlog al ")
			.append("join indoorunits as idu on idu.adapters_id = al.adapterid left outer join adapters as ca on idu.adapters_id = ca.id ")
			.append("left outer join svg_master svg on svg.id = ca.svg_id left outer join groups g on g.uniqueid = ca.siteid left outer join errorcode_master as err on err.code = al.code ")
			.append("left join companies c on c.id = g.company_id %s and al.indoorunit_id is null and al.outdoorunit_id is null order by al.time desc;");
	// Modified by shanf(display device_model)
	public static final StringBuilder SQL_GET_NOTIFICATION_FOR_GROUP_OUTDOOR = new StringBuilder(
			" WITH RECURSIVE cteoutdoors AS (select distinct outdoorunit_id from usp_getindoorunits_supplygroupname(:id) f join indoorunits idu on f.indoorunitid=idu.id ")
			.append("UNION ALL SELECT si.id FROM outdoorunits As si INNER JOIN cteoutdoors AS sp ON (si.parentid = sp.outdoorunit_id)) ")
			.append("select distinct al.time as currenttime,al.id,g.name as sitegroup,al.severity,al.alarm_type,al.code as alarmcode,err.customer_description,err.maintenance_description,")
			.append("err.countermeasure_customer as countermeasurecustomer,err.countermeasure_2way as countermeasure2way,err.countermeasure_3way as countermeasure3way,")
			.append("al.fixed_time, al.status, odu.id as outdoorunit_id,svg.svg_file_name as odusvgpath,odu.svg_max_latitude as svg_max_latitude,")
			.append("odu.svg_max_longitude as svg_max_longitude,odu.svg_min_latitude as svg_min_latitude,odu.svg_min_longitude as svg_min_longitude,odu.name ,modu.is3way_system as system ")
			.append(",odu.centraladdress  as centraladdress,svg.id as svgid,svg.svg_name as svgname,g.path_name as sitepath,c.name as companyname,odu.device_model from outdoorunits odu ")
			.append("join cteoutdoors t on t.outdoorunit_id = odu.id join notificationlog al on al.outdoorunit_id = odu.id ")
			.append("left outer join  metaoutdoorunits as modu on odu.metaoutdoorunit_id=modu.id left outer join groups g on g.uniqueid = odu.siteid ")
			.append("left outer join errorcode_master as err on err.code = al.code left outer join svg_master svg on svg.id = odu.svg_id  left join companies c on c.id = g.company_id %s order by al.time desc;");

	public static final StringBuilder SQL_GET_NOTIFICATION_FOR_OUTDOOR = new StringBuilder(
			"select al.time as currenttime,al.id,g.name as sitegroup,al.severity,al.alarm_type,al.code as alarmcode,err.customer_description,err.maintenance_description,")
			.append("err.countermeasure_customer as countermeasurecustomer,err.countermeasure_2way as countermeasure2way,err.countermeasure_3way as countermeasure3way,")
			.append("al.fixed_time, al.status, odu.id as outdoorunit_id,svg.svg_file_name as odusvgpath,odu.svg_max_latitude as svg_max_latitude,")
			.append("odu.svg_max_longitude as svg_max_longitude,odu.svg_min_latitude as svg_min_latitude,odu.svg_min_longitude as svg_min_longitude,odu.name ,modu.is3way_system as system ")
			.append(",odu.centraladdress  as centraladdress,svg.id as svgid,svg.svg_name as svgname,g.path_name as sitepath,c.name as companyname from notificationlog al join outdoorunits as odu on al.outdoorunit_id = odu.id ")
			.append("left outer join  metaoutdoorunits as modu on odu.metaoutdoorunit_id=modu.id left outer join groups g on g.uniqueid = odu.siteid ")
			.append("left outer join errorcode_master as err on err.code = al.code left outer join svg_master svg on svg.id = odu.svg_id left join companies c on c.id = g.company_id %s order by al.time desc;");

	public static final StringBuilder SQL_GET_NOTIFICATION_COUNT_INDOOR = new StringBuilder(
			"select distinct  count(nl.severity) severitycount,severity from usp_getindoorunitsbyuser(:userId) fn inner join ")
			.append("(select row_number() over (partition by indoorunit_id order  by severity, time desc) row_number, ")
			.append("indoorunit_id,code,severity from notificationlog where lower(status) = 'new' and severity is not null and indoorunit_id is not null ")
			.append("order by severity, time desc) nl on fn.indoorunitid = nl.indoorunit_id where row_number = 1 group by severity");

	public static final StringBuilder SQL_GET_NOTIFICATION_COUNT_OUTDOOR = new StringBuilder(
			" WITH RECURSIVE cteoutdoors AS (select distinct outdoorunit_id from usp_getindoorunitsbyuser(:userId) f join indoorunits idu on f.indoorunitid=idu.id ")
			.append("UNION ALL SELECT si.id FROM outdoorunits As si INNER JOIN cteoutdoors AS sp ON (si.parentid = sp.outdoorunit_id)) ")
			.append("select distinct  count(nl.severity) severitycount,severity  from cteoutdoors odu ")
			.append("inner join (select row_number() over (partition by outdoorunit_id order  by severity, time desc) row_number, ")
			.append("outdoorunit_id,severity from notificationlog where lower(status) = 'new' and severity is not null and outdoorunit_id is not null ")
			.append("order by severity, time desc) nl on odu.outdoorunit_id = nl.outdoorunit_id where row_number = 1 group by nl.severity ");

	public static final StringBuilder SQL_GET_NOTIFICATION_COUNT_CA = new StringBuilder(
			"select distinct  count(nl.severity) severitycount,severity from (select distinct idu.adapters_id from usp_getindoorunitsbyuser(:userId) fn ")
			.append("inner join indoorunits idu on fn.indoorunitid = idu.id) ca inner join (select row_number() over (partition by adapterid order by severity, ")
			.append("time desc) row_number,adapterid,code,severity from notificationlog where lower(status) = 'new' and severity is not null and adapterid is not null ")
			.append("and indoorunit_id is null and outdoorunit_id is null order by severity, time desc) nl on ca.adapters_id = nl.adapterid where row_number = 1 group by severity");

	private static final StringBuffer SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID = new StringBuffer(
			"Select SUM(alarmCount)alarmCount ,status,supplygroupname from (select count(fn.supplygroupname) alarmCount,nl.status,fn.supplygroupname  ")
			.append("from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn join notificationlog nl on (nl.indoorunit_id = fn.indoorunitid ) join errorcode_master ecm 	on nl.code = ecm.code ")
			.append("where ecm.code  = :alarmType and ( (cast(nl.time as Date) between :startDate and :endDate) OR (cast(nl.fixed_time as Date) ")
			.append("between :startDate and :endDate))  Group by supplygroupname,status ")
			.append("UNION ALL ")
			.append("select count(fn.supplygroupname) alarmCount,nl.status,fn.supplygroupname from (Select distinct outdoorunitid,supplygroupname from  ")
			.append("usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn )  fn inner join notificationlog nl on ")
			.append("(nl.outdoorunit_id = fn.outdoorunitid ) join errorcode_master ecm 	on nl.code = ecm.code where ecm.code  = :alarmType and ( (cast(nl.time as Date) between :startDate and :endDate) OR ")
			.append("(cast(nl.fixed_time as Date) between :startDate and :endDate)) Group by supplygroupname,status ")
			.append("UNION ALL ")
			.append("select count(distinct nl.id) alarmCount,nl.status,fn.supplygroupname from  notificationlog nl ")
			.append("join indoorunits idu on idu.adapters_id = nl.adapterid join (Select distinct indoorunitid,supplygroupname ")
			.append("from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn) fn  on (idu.id = fn.indoorunitid ) join errorcode_master ecm on nl.code = ecm.code ")
			.append("where ecm.code  = :alarmType and  (nl.indoorunit_id is null and nl.outdoorunit_id is null) and ( (cast(nl.time as Date) between :startDate and :endDate) OR (cast(nl.fixed_time as Date) between :startDate and :endDate) ")
			.append(") Group by supplygroupname,status) temp ")
			.append("Group by supplygroupname,status");

	private static final StringBuffer SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE = new StringBuffer(
			"Select SUM(alarmCount)alarmCount ,status,supplygroupname from (select count(fn.supplygroupname) alarmCount,nl.status,fn.supplygroupname  ")
			.append("from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn join notificationlog nl on (nl.indoorunit_id = fn.indoorunitid ) ")
			.append("where  ( (cast(nl.time as Date) between :startDate and :endDate) OR (cast(nl.fixed_time as Date) ")
			.append("between :startDate and :endDate))  Group by supplygroupname,status ")
			.append("UNION ALL ")
			.append("select count(fn.supplygroupname) alarmCount,nl.status,fn.supplygroupname from (Select distinct outdoorunitid,supplygroupname from  ")
			.append("usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn )  fn inner join notificationlog nl on ")
			.append("(nl.outdoorunit_id = fn.outdoorunitid ) where ( (cast(nl.time as Date) between :startDate and :endDate) OR ")
			.append("(cast(nl.fixed_time as Date) between :startDate and :endDate)) Group by supplygroupname,status ")
			.append("UNION ALL ")
			.append("select count(distinct nl.id) alarmCount,nl.status,fn.supplygroupname from  notificationlog nl ")
			.append("join indoorunits idu on idu.adapters_id = nl.adapterid join (Select distinct indoorunitid,supplygroupname ")
			.append("from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn) fn  on (idu.id = fn.indoorunitid ) ")
			.append("where  (nl.indoorunit_id is null and nl.outdoorunit_id is null) and ( (cast(nl.time as Date) between :startDate and :endDate) OR (cast(nl.fixed_time as Date) between :startDate and :endDate) ")
			.append(") Group by supplygroupname,status) temp ")
			.append("Group by supplygroupname,status");

	private static final StringBuffer SQL_GET_ALARMTYPELIST = new StringBuffer(
			"select code,maintenance_description from errorcode_master order by id");

	private static final String SQL_OCCURRED_DATE = " cast(al.time as date) between to_date('%s','dd-MM-yyyy') and to_date('%s','dd-MM-yyyy') ";
	private static final String SQL_STATUS = " lower(al.status) in (%s) ";
	private static final String SQL_SEVERITY = " lower(al.severity) in (%s) ";
	private static final String SQL_ALARM_TYPE = " lower(al.alarm_type) ='%s' ";
	private static final String SQL_FIXED_DATE = " cast(al.fixed_time as date) between to_date('%s','dd-MM-yyyy') and to_date('%s','dd-MM-yyyy') ";
	private static final String SQL_INDOOR_ID_LIST = " and idu.id in (:id) ";
	private static final String SQL_CA_ID_LIST = " and ca.id in (:id) ";
	private static final String SQL_OUTDOOR_ID_LIST = " and al.outdoorunit_id in (:id) ";
	private static final String SQL_WHERE = " where ";
	private static final String SQL_AND = " and ";

	private static final StringBuffer SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_DOWNLOAD = new StringBuffer(
			"Select SUM(alarmCount)alarmCount ,supplygroupname,path_name,companyname from  ")
			.append("(select count(fn.supplygroupname) alarmCount,fn.supplygroupname,g.path_name , c.name as companyname  ")
			.append("from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn ")
			.append("inner join groups g on g.id = fn.supplygroupid inner join companies c on c.id = g.company_id ")
			.append("join notificationlog nl on (nl.indoorunit_id = fn.indoorunitid ) join errorcode_master ecm  on nl.code = ecm.code ")
			.append("where ecm.code  = :alarmType and ( (cast(nl.time as Date) between :startDate and :endDate) OR (cast(nl.fixed_time as Date) ")
			.append("between :startDate and :endDate)) Group by supplygroupname,g.path_name ,c.name ")
			.append("UNION ALL ")
			.append("select count(fn.supplygroupname) alarmCount,fn.supplygroupname ,g.path_name as pathname , c.name as companyname  from ")
			.append("(Select distinct outdoorunitid,supplygroupname,  supplygroupid  from  usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn ) fn ")
			.append("inner join notificationlog nl on (nl.outdoorunit_id = fn.outdoorunitid ) ")
			.append("inner join groups g on g.id = fn.supplygroupid inner join companies c on ")
			.append("c.id = g.company_id  join errorcode_master ecm on nl.code = ecm.code where ecm.code  = :alarmType and ( (cast(nl.time as Date) between :startDate and :endDate) OR ")
			.append("(cast(nl.fixed_time as Date) between :startDate and :endDate)) Group by supplygroupname,g.path_name,c.name ")
			.append("UNION ALL ")
			.append("select count(distinct nl.id) alarmCount,fn.supplygroupname ,g.path_name as pathname , c.name as companyname ")
			.append("from notificationlog nl join indoorunits idu on idu.adapters_id = nl.adapterid ")
			.append("join (Select distinct indoorunitid,supplygroupname,supplygroupid from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) ) fn ")
			.append("on fn.indoorunitid = idu.id  Left  join groups g on g.id = fn.supplygroupid ")
			.append("Left  join companies c on c.id = g.company_id join errorcode_master ecm on nl.code = ecm.code where ecm.code  = :alarmType and (nl.indoorunit_id is null and nl.outdoorunit_id is null) and ")
			.append("( (cast(nl.time as Date) between :startDate and :endDate) OR ")
			.append("(cast(nl.fixed_time as Date) between :startDate and :endDate)) ")
			.append("Group by supplygroupname,g.path_name,c.name ) temp Group by supplygroupname,path_name ,companyname");

	private static final StringBuffer SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE_DOWNLOAD = new StringBuffer(
			"Select SUM(alarmCount)alarmCount ,supplygroupname,path_name,companyname from  ")
			.append("(select count(fn.supplygroupname) alarmCount,fn.supplygroupname,g.path_name , c.name as companyname  ")
			.append("from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn ")
			.append("inner join groups g on g.id = fn.supplygroupid inner join companies c on c.id = g.company_id ")
			.append("join notificationlog nl on (nl.indoorunit_id = fn.indoorunitid ) ")
			.append("where  ( (cast(nl.time as Date) between :startDate and :endDate) OR (cast(nl.fixed_time as Date) ")
			.append("between :startDate and :endDate)) Group by supplygroupname,g.path_name ,c.name ")
			.append("UNION ALL ")
			.append("select count(fn.supplygroupname) alarmCount,fn.supplygroupname ,g.path_name as pathname , c.name as companyname  from ")
			.append("(Select distinct outdoorunitid,supplygroupname,  supplygroupid  from  usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) fn ) fn ")
			.append("inner join notificationlog nl on (nl.outdoorunit_id = fn.outdoorunitid ) ")
			.append("inner join groups g on g.id = fn.supplygroupid inner join companies c on ")
			.append("c.id = g.company_id where ( (cast(nl.time as Date) between :startDate and :endDate) OR ")
			.append("(cast(nl.fixed_time as Date) between :startDate and :endDate)) Group by supplygroupname,g.path_name,c.name ")
			.append("UNION ALL ")
			.append("select count(distinct nl.id) alarmCount,fn.supplygroupname ,g.path_name as pathname , c.name as companyname ")
			.append("from notificationlog nl join indoorunits idu on idu.adapters_id = nl.adapterid ")
			.append("join (Select distinct indoorunitid,supplygroupname,supplygroupid from usp_getindooroutdoorunits_supplylevelgroupname(:groupId,:grouplevel) ) fn ")
			.append("on fn.indoorunitid = idu.id  Left  join groups g on g.id = fn.supplygroupid ")
			.append("Left  join companies c on c.id = g.company_id where (nl.indoorunit_id is null and nl.outdoorunit_id is null) and ")
			.append("( (cast(nl.time as Date) between :startDate and :endDate) OR ")
			.append("(cast(nl.fixed_time as Date) between :startDate and :endDate)) ")
			.append("Group by supplygroupname,g.path_name,c.name ) temp Group by supplygroupname,path_name ,companyname");

	private static final StringBuilder SQL_GET_NOTIFICATION_DETAIL = new StringBuilder(
			"select distinct al.time as currenttime,al.id,g.name as sitegroup,al.severity,al.alarm_type,al.code as alarmcode,err.customer_description,err.maintenance_description,al.fixed_time, ")
			.append("al.status, svg.svg_file_name as casvgpath,ca.svg_max_latitude as svg_max_latitude,ca.svg_max_longitude as svg_max_longitude,ca.svg_min_latitude as svg_min_latitude,ca.svg_min_longitude as svg_min_longitude, ")
			.append("svg.id as svgid,svg.svg_name as svgname,ca.id as ca_id,ca.address as centraladdress,g.path_name as sitepath,c.name as companyname,case when idu.name is not null then idu.name when odu.name is not null then odu.name else ca.name end devicename ")
			.append("from notificationlog al ")
			.append("left join indoorunits as idu on idu.id = al.indoorunit_id ")
			.append("left join outdoorunits as odu on al.outdoorunit_id = odu.id ")
			.append("left join adapters as ca on ca.id = (case when idu.adapters_id  is not null then idu.adapters_id when odu.adapters_id is not null then odu.adapters_id else al.adapterid end) ")
			.append("left outer join groups g on g.uniqueid = ca.siteid ")
			.append("left outer join svg_master svg on svg.id = ca.svg_id ")
			.append("left outer join errorcode_master as err on err.code = al.code and (case when al.indoorunit_id is not null then err.device_type='IDU' ")
			.append("when al.outdoorunit_id is not null then err.device_type='ODU' when al.adapterid is not null then err.device_type='CA' end) ")
			.append("left join companies c on c.id = g.company_id ")
			.append("where al.id in (:id) order by al.time desc");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.dao.NotificationDAO#
	 * getNotificationDetails
	 * (com.panasonic.b2bacns.bizportal.dashboard.vo.NotificationRequestVO)
	 */
	@Transactional
	@Override
	public NotificationDetailList getNotificationDetails(
			NotificationRequestVO request) {

		String queryIndoor;
		String queryOutdoor;
		String queryCA;
		String condition = getConditionQueryStringForNotification(request);
		Set<NotificationDetailsVO> notificationDetailsList = new LinkedHashSet<NotificationDetailsVO>();
		Set<RefrigerantSVG> refrigerantSVGList = new LinkedHashSet<RefrigerantSVG>();

		switch (request.getIdType()) {
		case BizConstants.ID_TYPE_GROUP:
			queryIndoor = String
					.format(SQL_GET_NOTIFICATION_FOR_GROUP_INDOOR.toString(),
							condition);
			queryOutdoor = String.format(
					SQL_GET_NOTIFICATION_FOR_GROUP_OUTDOOR.toString(),
					condition);
			queryCA = String.format(
					SQL_GET_NOTIFICATION_FOR_GROUP_CA.toString(), condition);
			notificationForIndoor(request, queryIndoor,
					notificationDetailsList, refrigerantSVGList);

			notificationForOutdoor(request, queryOutdoor,
					notificationDetailsList);

			notificationForCA(request, queryCA, notificationDetailsList);
			break;
		case BizConstants.ID_TYPE_INDOOR:
			queryIndoor = String.format(
					SQL_GET_NOTIFICATION_FOR_INDOOR.toString(),
					condition.concat(SQL_INDOOR_ID_LIST.toString()));

			notificationForIndoor(request, queryIndoor,
					notificationDetailsList, refrigerantSVGList);
			break;
		case BizConstants.ID_TYPE_OUTDOOR:
		case BizConstants.ID_TYPE_OUTDOOR_GHP:
		case BizConstants.ID_TYPE_OUTDOOR_VRF:
			queryOutdoor = String.format(
					SQL_GET_NOTIFICATION_FOR_OUTDOOR.toString(),
					condition.concat(SQL_OUTDOOR_ID_LIST.toString()));

			notificationForOutdoor(request, queryOutdoor,
					notificationDetailsList);

			break;
		case BizConstants.ID_TYPE_CA:
			queryOutdoor = String.format(
					SQL_GET_NOTIFICATION_FOR_CA.toString(),
					condition.concat(SQL_CA_ID_LIST.toString()));

			notificationForCA(request, queryOutdoor, notificationDetailsList);

			break;

		}
		NotificationDetailList notificationList = new NotificationDetailList();
		notificationList.setNotificationList(notificationDetailsList);
		if (request.getIdType().equals(BizConstants.ID_TYPE_GROUP))
			notificationList.setRefrigerantList(refrigerantSVGList);
		return notificationList;
	}

	/**
	 * Calling the notification details for indoor units.
	 * 
	 * @param request
	 * @param queryIndoor
	 * @param notificationDetailsList
	 * @param refrigerantSVGList
	 */
	private void notificationForIndoor(NotificationRequestVO request,
			String queryIndoor,
			Set<NotificationDetailsVO> notificationDetailsList,
			Set<RefrigerantSVG> refrigerantSVGList) {
		logger.debug("Calling the notification details for indoor units");
		NotificationDetailsVO notificationObj;

		LinkedHashMap<String, Type> scalarMapping = indoorScalarMapping();

		Map<String, Object> parameter = new HashMap<String, Object>();
		if (request.getIdType().equals(BizConstants.ID_TYPE_GROUP)) {
			parameter.put(DaoConstants.ID,
					CommonUtil.convertCollectionToString(request.getId()));
		} else {
			parameter.put(DaoConstants.ID, request.getId());

		}
		try {
			List<?> result = sqlDao.executeSQLSelect(queryIndoor,
					scalarMapping, parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					notificationObj = new NotificationDetailsVO();

					rowData = (Object[]) itr.next();

					// RC Parameter
					DetailsLogsVO detailsObj = acConfigDAO
							.setRCParameter(rowData);
					notificationObj.setPower(detailsObj.getPower());
					notificationObj.setTemperature(detailsObj.getTemperature());
					notificationObj.setMode(detailsObj.getMode());
					notificationObj.setFanSpeed(detailsObj.getFanSpeed());
					notificationObj.setFlapMode(detailsObj.getFlapMode());
					notificationObj.setEcoNavi(detailsObj.getEcoNavi());
					notificationObj.setRoomTemp(detailsObj.getRoomTemp());
					notificationObj.setEnergy_saving(detailsObj
							.getEnergy_saving());

					// Refrigerant SVG List only for group
					if (request.getIdType().equals(BizConstants.ID_TYPE_GROUP))
						acConfigDAO.getRefrigerantSVG(refrigerantSVGList,
								rowData);

					notificationObj
							.setAlarmOccurred(rowData[38] == null ? BizConstants.HYPHEN
									: CommonUtil.dateToString(
											(Timestamp) rowData[38],
											DATE_FORMAT_NOTIFICATION));
					notificationObj.setNotificationID((Long) rowData[39]);
					notificationObj
							.setSite(rowData[40] == null ? BizConstants.HYPHEN
									: (String) rowData[40]);
					notificationObj
							.setArea(rowData[41] == null ? BizConstants.HYPHEN
									: (String) rowData[41]);
					notificationObj
							.setSeverity(rowData[42] == null ? BizConstants.HYPHEN
									: (String) rowData[42]);
					notificationObj
							.setCategory(rowData[43] == null ? BizConstants.HYPHEN
									: (String) rowData[43]);
					notificationObj
							.setCode(rowData[44] == null ? BizConstants.HYPHEN
									: (String) rowData[44]);
					if (request.getDiscription().equals(BizConstants.CUSTOMER)) {
						notificationObj
								.setNotificationName(rowData[45] == null ? BizConstants.HYPHEN
										: (String) rowData[45]);
					} else {
						notificationObj
								.setNotificationName(rowData[46] == null ? BizConstants.HYPHEN
										: (String) rowData[46]);
					}
					if (request.getDiscription().equals(BizConstants.CUSTOMER)) {
						notificationObj
								.setCounterMeasure(rowData[47] == null ? BizConstants.HYPHEN
										: (String) rowData[47]);
					} else {
						Boolean system = rowData[61] == null ? null
								: (Boolean) rowData[61];
						if (system != null && !system) {
							notificationObj
									.setCounterMeasure(rowData[59] == null ? BizConstants.HYPHEN
											: (String) rowData[59]);
						} else if (system != null && system) {
							notificationObj
									.setCounterMeasure(rowData[60] == null ? BizConstants.HYPHEN
											: (String) rowData[60]);
						}

					}

					notificationObj
							.setAlarmFixed(rowData[48] == null ? BizConstants.HYPHEN
									: CommonUtil.dateToString(
											(Timestamp) rowData[48],
											DATE_FORMAT_NOTIFICATION));

					notificationObj
							.setAlarmStatus(rowData[49] == null ? BizConstants.HYPHEN
									: (String) rowData[49]);
					notificationObj.setIduId((Long) rowData[50]);

					notificationObj.setLinkODUid(rowData[52] == null ? null
							: (Long) rowData[52]);
					notificationObj
							.setMaxlatitude(rowData[54] == null ? BizConstants.NUMBER_00
									: (Double) rowData[54]);
					notificationObj
							.setMaxlongitude(rowData[55] == null ? BizConstants.NUMBER_00
									: (Double) rowData[55]);
					notificationObj
							.setMinlatitude(rowData[56] == null ? BizConstants.NUMBER_00
									: (Double) rowData[56]);
					notificationObj
							.setMinlongitude(rowData[57] == null ? BizConstants.NUMBER_00
									: (Double) rowData[57]);
					notificationObj
							.setSvg_Location(rowData[51] == null ? BizConstants.HYPHEN
									: (String) rowData[51]);
					notificationObj
							.setLinkODUSVG(rowData[53] == null ? BizConstants.HYPHEN
									: (String) rowData[53]);
					notificationObj
							.setDeviceName(rowData[58] == null ? BizConstants.HYPHEN
									: (String) rowData[58]);
					notificationObj.setSvgId(rowData[62] == null ? null
							: (Long) rowData[62]);
					notificationObj
							.setSvgDisplayName(rowData[63] == null ? BizConstants.HYPHEN
									: (String) rowData[63]);
					notificationObj
							.setUnitAddress(rowData[64] == null ? BizConstants.HYPHEN
									: (String) rowData[64]);
					notificationObj.setSitePath(rowData[65] == null ? null
							: (String) rowData[65]);
					if (request.getAddCustName().equalsIgnoreCase(
							BizConstants.YES))
						notificationObj
								.setCustomerName(rowData[66] == null ? null
										: (String) rowData[66]);
					else
						notificationObj.setCustomerName(BizConstants.HYPHEN);
					//add by shanf
					notificationObj.setDeviceModel(rowData[80] == null ? BizConstants.HYPHEN
							: (String) rowData[80]);
					notificationObj.setDeviceType(IDU);
					notificationDetailsList.add(notificationObj);

				}
			}
		} catch (HibernateException sqlExp) {
			logger.error("An Exception occured while fetching data from"
					+ " 'Notificationlog' for indoor for the following Exception :"
					+ sqlExp.getMessage());
		}
	}

	private LinkedHashMap<String, Type> indoorScalarMapping() {
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(DaoConstants.MAP_SVG_ID1, StandardBasicTypes.LONG); // 0
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMAXLATITUDE1,
				StandardBasicTypes.DOUBLE); // 1
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMAXLONGITUDE1,
				StandardBasicTypes.DOUBLE); // 2
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMINLATITUDE1,
				StandardBasicTypes.DOUBLE); // 3
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMINLONGITUDE1,
				StandardBasicTypes.DOUBLE); // 4
		scalarMapping.put(DaoConstants.MAP_SVG_ID2, StandardBasicTypes.LONG); // 5
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMAXLATITUDE2,
				StandardBasicTypes.DOUBLE); // 6
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMAXLONGITUDE2,
				StandardBasicTypes.DOUBLE); // 7
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMINLATITUDE2,
				StandardBasicTypes.DOUBLE); // 8
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMINLONGITUDE2,
				StandardBasicTypes.DOUBLE); // 9
		scalarMapping.put(DaoConstants.MAP_SVG_ID3, StandardBasicTypes.LONG); // 10
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMAXLATITUDE3,
				StandardBasicTypes.DOUBLE); // 11
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMAXLONGITUDE3,
				StandardBasicTypes.DOUBLE); // 12
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMINLATITUDE3,
				StandardBasicTypes.DOUBLE); // 13
		scalarMapping.put(DaoConstants.MAP_REFGRNT_SVGMINLONGITUDE3,
				StandardBasicTypes.DOUBLE); // 14
		scalarMapping
				.put(DaoConstants.MAP_LINKEDSVGID, StandardBasicTypes.LONG); // 15

		scalarMapping.put(DaoConstants.MAP_POWER_STATUS,
				StandardBasicTypes.BOOLEAN); // 16
		scalarMapping.put(DaoConstants.MAP_A2_1, StandardBasicTypes.STRING); // 17
		scalarMapping.put(DaoConstants.MAP_A3, StandardBasicTypes.DOUBLE); // 18
		scalarMapping.put(DaoConstants.MAP_A6_1, StandardBasicTypes.STRING); // 19
		scalarMapping.put(DaoConstants.MAP_A7_1, StandardBasicTypes.STRING); // 20
		scalarMapping.put(DaoConstants.MAP_A3A, StandardBasicTypes.DOUBLE); // 21
		scalarMapping.put(DaoConstants.MAP_A6A_1, StandardBasicTypes.STRING); // 22
		scalarMapping.put(DaoConstants.MAP_A7A_1, StandardBasicTypes.STRING); // 23
		scalarMapping.put(DaoConstants.MAP_A3H, StandardBasicTypes.DOUBLE); // 24
		scalarMapping.put(DaoConstants.MAP_A6H_1, StandardBasicTypes.STRING); // 25
		scalarMapping.put(DaoConstants.MAP_A7H_1, StandardBasicTypes.STRING); // 26
		scalarMapping.put(DaoConstants.MAP_A3C, StandardBasicTypes.DOUBLE); // 27
		scalarMapping.put(DaoConstants.MAP_A6C_1, StandardBasicTypes.STRING); // 28
		scalarMapping.put(DaoConstants.MAP_A7C_1, StandardBasicTypes.STRING); // 29
		scalarMapping.put(DaoConstants.MAP_A3D, StandardBasicTypes.DOUBLE); // 30
		scalarMapping.put(DaoConstants.MAP_A6D_1, StandardBasicTypes.STRING); // 31
		scalarMapping.put(DaoConstants.MAP_A7D_1, StandardBasicTypes.STRING); // 32
		scalarMapping.put(DaoConstants.MAP_A6F_1, StandardBasicTypes.STRING); // 33
		scalarMapping.put(DaoConstants.MAP_A7F_1, StandardBasicTypes.STRING); // 34
		scalarMapping.put(DaoConstants.MAP_ISECONAVI,
				StandardBasicTypes.INTEGER); // 35
		scalarMapping.put(DaoConstants.ROOM_TEMP, StandardBasicTypes.DOUBLE); // 36
		scalarMapping.put(DaoConstants.MAP_ENERGY, StandardBasicTypes.INTEGER); // 37

		scalarMapping.put(DaoConstants.CURRENT_TIME,
				StandardBasicTypes.TIMESTAMP);// 38
		scalarMapping.put(DaoConstants.ID, StandardBasicTypes.LONG);// 39
		scalarMapping.put(DaoConstants.MAP_SITE_GROUP,
				StandardBasicTypes.STRING);// 2-40
		scalarMapping.put(DaoConstants.MAP_CONTROL_GROUP,
				StandardBasicTypes.STRING);// 41
		scalarMapping.put(DaoConstants.MAP_SEVERITY, StandardBasicTypes.STRING);// 42
		scalarMapping.put(DaoConstants.MAP_ALARM_TYPE,
				StandardBasicTypes.STRING);// 43
		scalarMapping.put(DaoConstants.MAP_ALARM_CODE,
				StandardBasicTypes.STRING);// 44
		scalarMapping.put(DaoConstants.MAP_CUSTOMER_DESC,
				StandardBasicTypes.STRING);// 45
		scalarMapping.put(DaoConstants.MAP_MAINT_DESC,
				StandardBasicTypes.STRING);// 46
		scalarMapping.put(DaoConstants.MAP_COUNTER_MEASURE_CUSTOMER,
				StandardBasicTypes.STRING);// 47
		scalarMapping.put(DaoConstants.MAP_FIXEDTIME,
				StandardBasicTypes.TIMESTAMP);// 48
		scalarMapping.put(DaoConstants.MAP_STATUS, StandardBasicTypes.STRING);// 49
		scalarMapping.put(INDOORUNIT_ID, StandardBasicTypes.LONG);// 50
		scalarMapping.put(DaoConstants.MAP_IDU_SVG_PATH,
				StandardBasicTypes.STRING);// 51
		scalarMapping.put(MAP_ODUID, StandardBasicTypes.LONG);// 52
		scalarMapping.put(DaoConstants.MAP_ODU_SVG_PATH,
				StandardBasicTypes.STRING);// 53
		scalarMapping.put(DaoConstants.MAP_SVGMAXLATITUDE,
				StandardBasicTypes.DOUBLE);// 54
		scalarMapping.put(DaoConstants.MAP_SVGMAXLONGITUDE,
				StandardBasicTypes.DOUBLE);// 55
		scalarMapping.put(DaoConstants.MAP_SVGMINLATITUDE,
				StandardBasicTypes.DOUBLE);// 56
		scalarMapping.put(DaoConstants.MAP_SVGMINLONGITUDE,
				StandardBasicTypes.DOUBLE);// 57
		scalarMapping.put(DaoConstants.MAP_NAME, StandardBasicTypes.STRING);// 58
		scalarMapping.put(DaoConstants.MAP_COUNTER_MEASURE_MAINT_2WAY,
				StandardBasicTypes.STRING);// 59
		scalarMapping.put(DaoConstants.MAP_COUNTER_MEASURE_MAINT_3WAY,
				StandardBasicTypes.STRING);// 60
		scalarMapping.put(DaoConstants.MAP_SYSTEM, StandardBasicTypes.BOOLEAN);// 61
		scalarMapping.put(DaoConstants.MAP_SVGID, StandardBasicTypes.LONG); // 62
		scalarMapping.put(DaoConstants.MAP_SVGNAME, StandardBasicTypes.STRING); // 63
		scalarMapping.put(DaoConstants.MAP_CENTRAL_ADDRESS,
				StandardBasicTypes.STRING); // 64
		scalarMapping.put(DaoConstants.MAP_SITEPATH, StandardBasicTypes.STRING); // 65
		scalarMapping.put(DaoConstants.MAP_COMPANYNAME,
				StandardBasicTypes.STRING); // 66
		scalarMapping.put(DaoConstants.MAP_A2_2, StandardBasicTypes.STRING); // 67
		scalarMapping.put(DaoConstants.MAP_A6_2, StandardBasicTypes.STRING); // 68
		scalarMapping.put(DaoConstants.MAP_A6A_2, StandardBasicTypes.STRING); // 69
		scalarMapping.put(DaoConstants.MAP_A6C_2, StandardBasicTypes.STRING); // 70
		scalarMapping.put(DaoConstants.MAP_A6D_2, StandardBasicTypes.STRING); // 71
		scalarMapping.put(DaoConstants.MAP_A6H_2, StandardBasicTypes.STRING); // 72
		scalarMapping.put(DaoConstants.MAP_A6F_2, StandardBasicTypes.STRING); // 73
		scalarMapping.put(DaoConstants.MAP_A7_2, StandardBasicTypes.STRING); // 74
		scalarMapping.put(DaoConstants.MAP_A7A_2, StandardBasicTypes.STRING); // 75
		scalarMapping.put(DaoConstants.MAP_A7C_2, StandardBasicTypes.STRING); // 76
		scalarMapping.put(DaoConstants.MAP_A7D_2, StandardBasicTypes.STRING); // 77
		scalarMapping.put(DaoConstants.MAP_A7H_2, StandardBasicTypes.STRING); // 78
		scalarMapping.put(DaoConstants.MAP_A7F_2, StandardBasicTypes.STRING); // 79
		//add by shanf
		scalarMapping.put(DaoConstants.DEVICE_MODEL, StandardBasicTypes.STRING); // 80
		return scalarMapping;
	}

	/**
	 * Calling the notification details for outdoor units.
	 * 
	 * @param request
	 * @param queryOutdoor
	 * @param notificationDetailsList
	 */
	private void notificationForOutdoor(NotificationRequestVO request,
			String queryOutdoor,
			Set<NotificationDetailsVO> notificationDetailsList) {
		logger.debug("Calling the notification details for outdoor units");
		NotificationDetailsVO notificationObj;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(DaoConstants.CURRENT_TIME,
				StandardBasicTypes.TIMESTAMP); // 0
		scalarMapping.put(DaoConstants.ID, StandardBasicTypes.LONG);// 1
		scalarMapping.put(DaoConstants.MAP_SEVERITY, StandardBasicTypes.STRING);// 2
		scalarMapping.put(DaoConstants.MAP_ALARM_TYPE,
				StandardBasicTypes.STRING);// 3
		scalarMapping.put(DaoConstants.MAP_ALARM_CODE,
				StandardBasicTypes.STRING);// 4
		scalarMapping.put(DaoConstants.MAP_CUSTOMER_DESC,
				StandardBasicTypes.STRING);// 5
		scalarMapping.put(DaoConstants.MAP_MAINT_DESC,
				StandardBasicTypes.STRING);// 6
		scalarMapping.put(DaoConstants.MAP_COUNTER_MEASURE_CUSTOMER,
				StandardBasicTypes.STRING);// 7
		scalarMapping.put(DaoConstants.MAP_FIXEDTIME,
				StandardBasicTypes.TIMESTAMP);// 8
		scalarMapping.put(DaoConstants.MAP_STATUS, StandardBasicTypes.STRING);// 9
		scalarMapping.put(OUTDOORUNIT_ID, StandardBasicTypes.LONG);// 10
		scalarMapping.put(DaoConstants.MAP_ODU_SVG_PATH,
				StandardBasicTypes.STRING);// 11
		scalarMapping.put(DaoConstants.MAP_SVGMAXLATITUDE,
				StandardBasicTypes.DOUBLE);// 12
		scalarMapping.put(DaoConstants.MAP_SVGMAXLONGITUDE,
				StandardBasicTypes.DOUBLE);// 13
		scalarMapping.put(DaoConstants.MAP_SVGMINLATITUDE,
				StandardBasicTypes.DOUBLE);// 14
		scalarMapping.put(DaoConstants.MAP_SVGMINLONGITUDE,
				StandardBasicTypes.DOUBLE);// 15
		scalarMapping.put(DaoConstants.MAP_NAME, StandardBasicTypes.STRING);// 16
		scalarMapping.put(DaoConstants.MAP_COUNTER_MEASURE_MAINT_2WAY,
				StandardBasicTypes.STRING);// 17
		scalarMapping.put(DaoConstants.MAP_COUNTER_MEASURE_MAINT_3WAY,
				StandardBasicTypes.STRING);// 18
		scalarMapping.put(DaoConstants.MAP_SYSTEM, StandardBasicTypes.BOOLEAN);// 19
		scalarMapping.put(DaoConstants.MAP_SITE_GROUP,
				StandardBasicTypes.STRING);// 20
		scalarMapping.put(DaoConstants.MAP_SVGID, StandardBasicTypes.LONG); // 21
		scalarMapping.put(DaoConstants.MAP_SVGNAME, StandardBasicTypes.STRING); // 22
		scalarMapping.put(DaoConstants.MAP_CENTRAL_ADDRESS,
				StandardBasicTypes.STRING); // 23
		scalarMapping.put(DaoConstants.MAP_SITEPATH, StandardBasicTypes.STRING); // 24
		scalarMapping.put(DaoConstants.MAP_COMPANYNAME,
				StandardBasicTypes.STRING); // 25
		//add by shanf
		scalarMapping.put(DaoConstants.DEVICE_MODEL,
				StandardBasicTypes.STRING); // 26

		Map<String, Object> parameter = new HashMap<String, Object>();
		if (request.getIdType().equals(BizConstants.ID_TYPE_GROUP)) {
			parameter.put(DaoConstants.ID,
					CommonUtil.convertCollectionToString(request.getId()));
		} else {
			parameter.put(DaoConstants.ID, request.getId());

		}

		try {
			List<?> result = sqlDao.executeSQLSelect(queryOutdoor,
					scalarMapping, parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					notificationObj = new NotificationDetailsVO();

					rowData = (Object[]) itr.next();

					notificationObj
							.setAlarmOccurred(rowData[0] == null ? BizConstants.HYPHEN
									: CommonUtil.dateToString(
											(Timestamp) rowData[0],
											DATE_FORMAT_NOTIFICATION));
					notificationObj.setNotificationID((Long) rowData[1]);
					notificationObj
							.setSeverity(rowData[2] == null ? BizConstants.HYPHEN
									: (String) rowData[2]);
					notificationObj
							.setCategory(rowData[3] == null ? BizConstants.HYPHEN
									: (String) rowData[3]);
					notificationObj
							.setCode(rowData[4] == null ? BizConstants.HYPHEN
									: (String) rowData[4]);
					if (request.getDiscription().equals(BizConstants.CUSTOMER)) {
						notificationObj
								.setNotificationName(rowData[5] == null ? BizConstants.HYPHEN
										: (String) rowData[5]);
					} else {
						notificationObj
								.setNotificationName(rowData[6] == null ? BizConstants.HYPHEN
										: (String) rowData[6]);
					}
					if (request.getDiscription().equals(BizConstants.CUSTOMER)) {
						notificationObj
								.setCounterMeasure(rowData[7] == null ? BizConstants.HYPHEN
										: (String) rowData[7]);
					} else {
						Boolean system = rowData[19] == null ? null
								: (Boolean) rowData[19];
						if (system != null && !system) {
							notificationObj
									.setCounterMeasure(rowData[17] == null ? BizConstants.HYPHEN
											: (String) rowData[17]);
						} else if (system != null && system) {
							notificationObj
									.setCounterMeasure(rowData[18] == null ? BizConstants.HYPHEN
											: (String) rowData[18]);
						}

					}
					notificationObj
							.setAlarmFixed(rowData[8] == null ? BizConstants.HYPHEN
									: CommonUtil.dateToString(
											(Timestamp) rowData[8],
											DATE_FORMAT_NOTIFICATION));
					notificationObj
							.setAlarmStatus(rowData[9] == null ? BizConstants.HYPHEN
									: (String) rowData[9]);
					notificationObj.setOduId((Long) rowData[10]);

					notificationObj
							.setMaxlatitude(rowData[12] == null ? BizConstants.NUMBER_00
									: (Double) rowData[12]);
					notificationObj
							.setMaxlongitude(rowData[13] == null ? BizConstants.NUMBER_00
									: (Double) rowData[13]);
					notificationObj
							.setMinlatitude(rowData[14] == null ? BizConstants.NUMBER_00
									: (Double) rowData[14]);
					notificationObj
							.setMinlongitude(rowData[15] == null ? BizConstants.NUMBER_00
									: (Double) rowData[15]);
					notificationObj
							.setSvg_Location(rowData[11] == null ? BizConstants.HYPHEN
									: (String) rowData[11]);

					notificationObj
							.setDeviceName(rowData[16] == null ? BizConstants.HYPHEN
									: (String) rowData[16]);
					notificationObj
							.setSite(rowData[20] == null ? BizConstants.HYPHEN
									: (String) rowData[20]);
					notificationObj.setArea(BizConstants.HYPHEN);
					notificationObj.setSvgId(rowData[21] == null ? null
							: (Long) rowData[21]);
					notificationObj
							.setSvgDisplayName(rowData[22] == null ? BizConstants.HYPHEN
									: (String) rowData[22]);
					notificationObj
							.setUnitAddress(rowData[23] == null ? BizConstants.HYPHEN
									: (String) rowData[23]);
					notificationObj.setSitePath(rowData[24] == null ? null
							: (String) rowData[24]);
					if (request.getAddCustName().equalsIgnoreCase(
							BizConstants.YES))
						notificationObj
								.setCustomerName(rowData[25] == null ? null
										: (String) rowData[25]);
					else
						notificationObj.setCustomerName(BizConstants.HYPHEN);
					//add by shanf
					notificationObj
					.setDeviceModel(rowData[26] == null ? BizConstants.HYPHEN
							: (String) rowData[26]);
					notificationObj.setDeviceType(ODU);
					notificationDetailsList.add(notificationObj);

				}
			}

		} catch (HibernateException sqlExp) {
			logger.error("An Exception occured while fetching data from"
					+ " 'Notificationlog' for outdoor for the following Exception :"
					+ sqlExp.getMessage());
		}
	}

	/**
	 * Calling the notification details for Cloud Adapters units.
	 * 
	 * @param request
	 * @param queryOutdoor
	 * @param notificationDetailsList
	 */
	private void notificationForCA(NotificationRequestVO request,
			String queryOutdoor,
			Set<NotificationDetailsVO> notificationDetailsList) {
		logger.debug("Calling the notification details for  Cloud Adapters");
		NotificationDetailsVO notificationObj;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(DaoConstants.CURRENT_TIME,
				StandardBasicTypes.TIMESTAMP); // 0
		scalarMapping.put(DaoConstants.ID, StandardBasicTypes.LONG);// 1
		scalarMapping.put(DaoConstants.MAP_SEVERITY, StandardBasicTypes.STRING);// 2
		scalarMapping.put(DaoConstants.MAP_ALARM_TYPE,
				StandardBasicTypes.STRING);// 3
		scalarMapping.put(DaoConstants.MAP_ALARM_CODE,
				StandardBasicTypes.STRING);// 4
		scalarMapping.put(DaoConstants.MAP_CUSTOMER_DESC,
				StandardBasicTypes.STRING);// 5
		scalarMapping.put(DaoConstants.MAP_MAINT_DESC,
				StandardBasicTypes.STRING);// 6
		scalarMapping.put(DaoConstants.MAP_SVGID, StandardBasicTypes.LONG); // 7
		scalarMapping.put(DaoConstants.MAP_FIXEDTIME,
				StandardBasicTypes.TIMESTAMP);// 8
		scalarMapping.put(DaoConstants.MAP_STATUS, StandardBasicTypes.STRING);// 9
		scalarMapping.put(DaoConstants.MAP_CA_ID, StandardBasicTypes.LONG);// 10
		scalarMapping.put(DaoConstants.MAP_CENTRAL_ADDRESS,
				StandardBasicTypes.STRING); // 11
		scalarMapping.put(DaoConstants.MAP_SITEPATH, StandardBasicTypes.STRING); // 12
		scalarMapping.put(DaoConstants.MAP_COMPANYNAME,
				StandardBasicTypes.STRING); // 13
		scalarMapping.put(DaoConstants.MAP_SVGNAME, StandardBasicTypes.STRING); // 14
		scalarMapping.put(DaoConstants.MAP_CA_SVG_PATH,
				StandardBasicTypes.STRING);// 15
		scalarMapping.put(DaoConstants.MAP_SITE_GROUP,
				StandardBasicTypes.STRING);// 16
		scalarMapping.put(DaoConstants.MAP_NAME, StandardBasicTypes.STRING);// 17
		scalarMapping.put(DaoConstants.MAP_SVGMAXLATITUDE,
				StandardBasicTypes.DOUBLE);// 18
		scalarMapping.put(DaoConstants.MAP_SVGMAXLONGITUDE,
				StandardBasicTypes.DOUBLE);// 19
		scalarMapping.put(DaoConstants.MAP_SVGMINLATITUDE,
				StandardBasicTypes.DOUBLE);// 20
		scalarMapping.put(DaoConstants.MAP_SVGMINLONGITUDE,
				StandardBasicTypes.DOUBLE);// 21
		scalarMapping.put(DaoConstants.DEVICE_MODEL,
				StandardBasicTypes.STRING);// 22

		Map<String, Object> parameter = new HashMap<String, Object>();
		if (request.getIdType().equals(BizConstants.ID_TYPE_GROUP)) {
			parameter.put(DaoConstants.ID,
					CommonUtil.convertCollectionToString(request.getId()));
		} else {
			parameter.put(DaoConstants.ID, request.getId());

		}

		try {
			List<?> result = sqlDao.executeSQLSelect(queryOutdoor,
					scalarMapping, parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					notificationObj = new NotificationDetailsVO();

					rowData = (Object[]) itr.next();

					notificationObj
							.setAlarmOccurred(rowData[0] == null ? BizConstants.HYPHEN
									: CommonUtil.dateToString(
											(Timestamp) rowData[0],
											DATE_FORMAT_NOTIFICATION));
					notificationObj.setNotificationID((Long) rowData[1]);
					notificationObj
							.setSeverity(rowData[2] == null ? BizConstants.HYPHEN
									: (String) rowData[2]);
					notificationObj
							.setCategory(rowData[3] == null ? BizConstants.HYPHEN
									: (String) rowData[3]);
					notificationObj
							.setCode(rowData[4] == null ? BizConstants.HYPHEN
									: (String) rowData[4]);
					if (request.getDiscription().equals(BizConstants.CUSTOMER)) {
						notificationObj
								.setNotificationName(rowData[5] == null ? BizConstants.HYPHEN
										: (String) rowData[5]);
					} else {
						notificationObj
								.setNotificationName(rowData[6] == null ? BizConstants.HYPHEN
										: (String) rowData[6]);
					}
					notificationObj
							.setAlarmFixed(rowData[8] == null ? BizConstants.HYPHEN
									: CommonUtil.dateToString(
											(Timestamp) rowData[8],
											DATE_FORMAT_NOTIFICATION));
					notificationObj
							.setAlarmStatus(rowData[9] == null ? BizConstants.HYPHEN
									: (String) rowData[9]);
					notificationObj.setCaId((Long) rowData[10]);

					notificationObj
							.setUnitAddress(rowData[11] == null ? BizConstants.HYPHEN
									: (String) rowData[11]);
					notificationObj.setSitePath(rowData[12] == null ? null
							: (String) rowData[12]);
					if (request.getAddCustName().equalsIgnoreCase(
							BizConstants.YES))
						notificationObj
								.setCustomerName(rowData[13] == null ? null
										: (String) rowData[13]);
					else
						notificationObj.setCustomerName(BizConstants.HYPHEN);
					notificationObj
							.setSite(rowData[16] == null ? BizConstants.HYPHEN
									: (String) rowData[16]);
					notificationObj.setArea(BizConstants.HYPHEN);
					notificationObj
							.setDeviceName(rowData[17] == null ? BizConstants.HYPHEN
									: (String) rowData[17]);
					notificationObj
							.setMaxlatitude(rowData[18] == null ? BizConstants.NUMBER_00
									: (Double) rowData[18]);
					notificationObj
							.setMaxlongitude(rowData[19] == null ? BizConstants.NUMBER_00
									: (Double) rowData[19]);
					notificationObj
							.setMinlatitude(rowData[20] == null ? BizConstants.NUMBER_00
									: (Double) rowData[20]);
					notificationObj
							.setMinlongitude(rowData[21] == null ? BizConstants.NUMBER_00
									: (Double) rowData[21]);
					notificationObj.setSvgId(rowData[7] == null ? null
							: (Long) rowData[7]);
					notificationObj
							.setSvgDisplayName(rowData[14] == null ? BizConstants.HYPHEN
									: (String) rowData[14]);
					notificationObj
							.setSvg_Location(rowData[15] == null ? BizConstants.HYPHEN
									: (String) rowData[15]);
					//add by shanf
					notificationObj
					.setDeviceModel(rowData[22] == null ? BizConstants.HYPHEN
							: (String) rowData[22]);
					notificationObj.setDeviceType(CA);
					notificationObj.setCounterMeasure(BizConstants.HYPHEN);
					notificationDetailsList.add(notificationObj);

				}
			}

		} catch (HibernateException sqlExp) {
			logger.error("An Exception occured while fetching data from"
					+ " 'Notificationlog' for Cloud Adapters for the following Exception :"
					+ sqlExp.getMessage());
		}
	}

	/**
	 * Create dynamic condition for notification.
	 * 
	 * @param request
	 * @return
	 */
	private String getConditionQueryStringForNotification(
			NotificationRequestVO request) {
		boolean flag = false;
		StringBuilder querybuilder = new StringBuilder(SQL_WHERE);

		if (request.getAlarmOccurredStartDate() != null
				&& request.getAlarmOccurredEndDate() != null) {
			flag = true;
			querybuilder.append(String.format(SQL_OCCURRED_DATE,
					request.getAlarmOccurredStartDate(),
					request.getAlarmOccurredEndDate()));

		}
		if (request.getStatus() != null && !request.getStatus().isEmpty()) {
			if (flag)
				querybuilder.append(SQL_AND);
			else
				flag = true;
			querybuilder.append(String.format(SQL_STATUS,
					convertListToSQLString(request.getStatus())));
		}

		if (request.getSeverity() != null && !request.getSeverity().isEmpty()) {
			if (flag)
				querybuilder.append(SQL_AND);
			else
				flag = true;
			querybuilder.append(String.format(SQL_SEVERITY,
					convertListToSQLString(request.getSeverity())));
		}

		if (request.getAlarmType() != null
				&& !request.getAlarmType().isEmpty()
				&& !request.getAlarmType().trim()
						.equalsIgnoreCase(BizConstants.ALL)) {
			if (flag)
				querybuilder.append(SQL_AND);
			else
				flag = true;
			querybuilder.append(String.format(SQL_ALARM_TYPE, request
					.getAlarmType().toLowerCase()));
		}

		if (request.getAlarmFixedEndDate() != null
				&& request.getAlarmFixedStartDate() != null) {
			if (flag)
				querybuilder.append(SQL_AND);
			querybuilder.append(String.format(SQL_FIXED_DATE,
					request.getAlarmFixedStartDate(),
					request.getAlarmFixedEndDate()));
		}

		return querybuilder.toString();
	}

	/**
	 * To get the List of string into SQL String. e.g. [a,b] = 'a','b'
	 * 
	 * @param list
	 * @return
	 */
	private String convertListToSQLString(List<String> list) {
		StringBuilder value = new StringBuilder(BizConstants.EMPTY_STRING);
		for (int i = 0; i < list.size(); i++) {
			if (!value.toString().isEmpty()) {
				value.append(BizConstants.COMMA_STRING);
			}
			value.append(BizConstants.SINGLE_QUOTE_STRING)
					.append(list.get(i).toLowerCase())
					.append(BizConstants.SINGLE_QUOTE_STRING);
		}
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.dao.NotificationDAO#
	 * getNotificationCount(java.lang.Long)
	 */
	@Override
	public NotificationCountVO getNotificationCount(Long userId)
			throws HibernateException {
		logger.debug("Calling the notification Count for indoor and outdoor units");
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(SEVERITY_COUNT, StandardBasicTypes.LONG);
		scalarMapping.put(DaoConstants.MAP_SEVERITY, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(MAP_USERID, userId);
		long critical = 0l;
		long noncritical = 0l;
		NotificationCountVO notificationCount = new NotificationCountVO();
		try {
			// For indoor
			List<?> result = sqlDao.executeSQLSelect(
					SQL_GET_NOTIFICATION_COUNT_INDOOR.toString(),
					scalarMapping, parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					rowData = (Object[]) itr.next();

					if (rowData[1] != null
							&& ((String) rowData[1]).equalsIgnoreCase(CRITICAL)) {
						critical += rowData[0] == null ? 0l : (Long) rowData[0];
					}
					if (rowData[1] != null
							&& ((String) rowData[1])
									.equalsIgnoreCase(NONCRITICAL)) {
						noncritical += rowData[0] == null ? 0l
								: (Long) rowData[0];
					}
				}
			}

			// For Outdoor
			List<?> resultOutdoor = sqlDao.executeSQLSelect(
					SQL_GET_NOTIFICATION_COUNT_OUTDOOR.toString(),
					scalarMapping, parameter);

			if (!resultOutdoor.isEmpty()) {

				Iterator<?> itr = resultOutdoor.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					rowData = (Object[]) itr.next();

					if (rowData[1] != null
							&& ((String) rowData[1]).equalsIgnoreCase(CRITICAL)) {
						critical += rowData[0] == null ? 0l : (Long) rowData[0];
					}
					if (rowData[1] != null
							&& ((String) rowData[1])
									.equalsIgnoreCase(NONCRITICAL)) {
						noncritical += rowData[0] == null ? 0l
								: (Long) rowData[0];
					}
				}
			}

			// For CA
			List<?> resultCA = sqlDao.executeSQLSelect(
					SQL_GET_NOTIFICATION_COUNT_CA.toString(), scalarMapping,
					parameter);

			if (!resultCA.isEmpty()) {

				Iterator<?> itr = resultCA.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					rowData = (Object[]) itr.next();

					if (rowData[1] != null
							&& ((String) rowData[1]).equalsIgnoreCase(CRITICAL)) {
						critical += rowData[0] == null ? 0l : (Long) rowData[0];
					}
					if (rowData[1] != null
							&& ((String) rowData[1])
									.equalsIgnoreCase(NONCRITICAL)) {
						noncritical += rowData[0] == null ? 0l
								: (Long) rowData[0];
					}
				}
			}

			notificationCount.setCriticalCount(critical);
			notificationCount.setNoncriticalCount(noncritical);
		} catch (HibernateException sqlExp) {
			logger.error("An Exception occured while fetching data from"
					+ " 'Notificationlog' for Notification Count "
					+ "Exception :" + sqlExp.getMessage());
		}
		return notificationCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.dao.NotificationDAO#
	 * getNotificationOverViewDataForGroups(List<Long> groupID, String
	 * startDate, String endDate, String alarmType, String period, int
	 * grouplevel)
	 */
	@Override
	public Map<String, Object> getNotificationOverViewDataForGroups(
			List<Long> groupID, String startDate, String endDate,
			String alarmType, String period, int grouplevel, String timeZone)
			throws ParseException, HibernateException {

		StringBuilder groupId = new StringBuilder(
				CommonUtil.convertCollectionToString(groupID));

		logger.debug("list of groupid in getNotificationOverViewDataForGroups "
				+ groupID);

		String sqlQuery = null;

		// scalarmapping map
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(ALARMCOUNT, StandardBasicTypes.LONG);
		scalarMapping.put(ALARMSTATUS, StandardBasicTypes.STRING);
		scalarMapping.put(GROUP_NAME, StandardBasicTypes.STRING);

		// parameter map
		Map<String, Object> parameter = new LinkedHashMap<String, Object>();

		// when getting period in place of startDate and endDate , we create
		if (StringUtils.isNotBlank(period)) {
			Calendar cal = Calendar.getInstance();

			Date startDateWithTimeZone = null;
			Date endDateWithTimeZone = null;
			Calendar currentDateWithTimeZone = CommonUtil.setUserTimeZone(cal,
					timeZone);

			endDateWithTimeZone = currentDateWithTimeZone.getTime();

			if (period.equalsIgnoreCase(BizConstants.PERIOD_THISYEAR)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_YEAR, 1);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thisyear coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (period.equalsIgnoreCase(BizConstants.PERIOD_THISMONTH)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_MONTH, 1);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thismonth coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (period.equalsIgnoreCase(BizConstants.PERIOD_THISWEEK)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_WEEK,
						Calendar.MONDAY);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thisweek coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (period.equalsIgnoreCase(BizConstants.PERIOD_TODAY)) {

				currentDateWithTimeZone.set(GregorianCalendar.HOUR_OF_DAY, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when today coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();

			}
			if (StringUtils.isNotBlank(alarmType)) {

				sqlQuery = String.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID
						.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(ALARM_TYPE, alarmType);
				parameter.put(START_DATE, startDateWithTimeZone);
				parameter.put(END_DATE, endDateWithTimeZone);

			} else { // this condition will get executed when alarmtype is null
						// and period having value

				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE
								.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(START_DATE, startDateWithTimeZone);
				parameter.put(END_DATE, endDateWithTimeZone);

			}

		} else if ((StringUtils.isBlank(startDate))
				&& (StringUtils.isBlank(endDate))
				&& (StringUtils.isBlank((period)))) {
			Calendar cal = Calendar.getInstance();

			// start date will be current date -6 days
			cal.add(Calendar.DATE, -6);
			Calendar stDatetime = CommonUtil.setUserTimeZone(cal, timeZone);
			Date stDate = stDatetime.getTime();

			// end date current Date
			Calendar calendDate = Calendar.getInstance();
			calendDate.add(Calendar.DATE, 0);

			Calendar enDateTime = CommonUtil.setUserTimeZone(calendDate,
					timeZone);
			// Date enDate = new Date(enDateTime.getTimeInMillis());
			Date enDate = enDateTime.getTime();

			// when alarmtype is not null
			if (StringUtils.isNotBlank(alarmType)) {
				sqlQuery = String.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID
						.toString());
				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(ALARM_TYPE, alarmType);
				parameter.put(START_DATE, stDate);
				parameter.put(END_DATE, enDate);
			} else {// when alarmtype is null
				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE
								.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(START_DATE, stDate);
				parameter.put(END_DATE, enDate);
			}

		} else {// this condition will get executed when startdate and enddate
				// is coming in request and period is null in this case no need
				// of timezone

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date startDateWithOutTimeZone = null;
			Date endDateWithOutTimeZone = null;

			startDateWithOutTimeZone = sdf.parse(startDate);
			endDateWithOutTimeZone = sdf.parse(endDate);

			// alarmType is not null
			if (StringUtils.isNotBlank(alarmType)) {

				sqlQuery = String.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID
						.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(ALARM_TYPE, alarmType);
				parameter.put(START_DATE, startDateWithOutTimeZone);
				parameter.put(END_DATE, endDateWithOutTimeZone);

			} else {// alarmType is null
				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE
								.toString());
				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(START_DATE, startDateWithOutTimeZone);
				parameter.put(END_DATE, endDateWithOutTimeZone);
			}
		}

		// executing Query
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		Object[] rowData = null;

		if (resultList != null && resultList.size() > 0) {

			logger.debug("list of return by executeSQLSelect method"
					+ resultList.toString());

			Iterator<?> itr = resultList.iterator();

			Map<String, Object> finalMap = new LinkedHashMap<String, Object>();

			Map<String, List<Integer>> statusMap = new HashMap<>();

			List<NotificationOverviewAlarmVO> groupsAlarm = new ArrayList<>();

			Set<String> groupNameSet = new LinkedHashSet<>();

			List<Integer> alarmCountList = null;
			// iterating resultList
			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();

				Long alarmCount = (Long) rowData[0];
				String alarmStatus = (String) rowData[1];
				String groupName = (String) rowData[2];

				groupNameSet.add(groupName);

				NotificationOverviewAlarmVO notificationVO = new NotificationOverviewAlarmVO(
						groupName, alarmStatus, alarmCount == null ? 0
								: alarmCount.intValue());

				groupsAlarm.add(notificationVO);
			}
			// creating finalMap with key xAxis
			finalMap.put("xAxis", groupNameSet);

			for (NotificationOverviewAlarmVO notificationVO : groupsAlarm) {

				if (statusMap.containsKey(notificationVO.getAlarmStatus())) {
					alarmCountList = statusMap.get(notificationVO
							.getAlarmStatus());

					int count = 0;
					for (String group : groupNameSet) {
						if (group.equalsIgnoreCase(notificationVO
								.getGroupName())) {
							alarmCountList.remove(count);
							alarmCountList.add(count,
									notificationVO.getAlarmCount());
						}
						count++;
					}
					statusMap.put(notificationVO.getAlarmStatus(),
							alarmCountList);
				} else {

					alarmCountList = new ArrayList<Integer>();
					int count = 0;
					for (String group : groupNameSet) {
						if (!group.equalsIgnoreCase(notificationVO
								.getGroupName())) {
							alarmCountList.add(count, 0);
						} else {
							alarmCountList.add(count,
									notificationVO.getAlarmCount());
						}
						count++;
					}
					statusMap.put(notificationVO.getAlarmStatus(),
							alarmCountList);
				}
			}

			List<Map<String, Object>> listOfValues = new ArrayList<Map<String, Object>>();

			for (Map.Entry<String, List<Integer>> entry : statusMap.entrySet()) {
				Map<String, Object> listOfObjects = new LinkedHashMap<String, Object>();
				listOfObjects.put("Name", entry.getKey());
				listOfObjects.put("Data", entry.getValue());
				listOfValues.add(listOfObjects);
			}

			// creating finalMap with key yAxis
			finalMap.put("yAxis", listOfValues);
			// returning final map
			return finalMap;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.notification.dao.NotificationDAO#
	 * getAlarmTypeList()
	 */
	@Override
	public List<AlarmTypeVO> getAlarmTypeList() throws HibernateException {

		String query = String.format(SQL_GET_ALARMTYPELIST.toString());

		List<AlarmTypeVO> listAlarmType = (List<AlarmTypeVO>) sqlDao
				.executeSQLSelect(query);

		return listAlarmType;
	}

	@Override
	public List<NotificationOverViewVO> getNotificationOverViewDataForDownload(
			List<Long> groupID, String startDate, String endDate,
			String alarmType, String period, int grouplevel, String timeZone)
			throws HibernateException, ParseException {

		StringBuilder groupId = new StringBuilder(
				CommonUtil.convertCollectionToString(groupID));

		logger.debug("list of groupid in getNotificationOverViewDataForDownload "
				+ groupID);

		String sqlQuery = null;

		// scalarmapping map
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(ALARMCOUNT, StandardBasicTypes.LONG);
		scalarMapping.put(GROUP_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(PATH_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(COMPANY_NAME, StandardBasicTypes.STRING);

		// parameter map
		Map<String, Object> parameter = new LinkedHashMap<String, Object>();

		// when getting period in place of startDate and endDate , we create
		if (StringUtils.isNotBlank(period)) {
			Calendar cal = Calendar.getInstance();

			Date startDateWithTimeZone = null;
			Date endDateWithTimeZone = null;
			Calendar currentDateWithTimeZone = CommonUtil.setUserTimeZone(cal,
					timeZone);

			endDateWithTimeZone = currentDateWithTimeZone.getTime();

			if (period.equalsIgnoreCase(BizConstants.PERIOD_THISYEAR)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_YEAR, 1);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thisyear coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (period.equalsIgnoreCase(BizConstants.PERIOD_THISMONTH)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_MONTH, 1);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thismonth coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (period.equalsIgnoreCase(BizConstants.PERIOD_THISWEEK)) {
				currentDateWithTimeZone.set(GregorianCalendar.DAY_OF_WEEK,
						Calendar.MONDAY);
				currentDateWithTimeZone.set(GregorianCalendar.HOUR, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when thisweek coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();
			} else if (period.equalsIgnoreCase(BizConstants.PERIOD_TODAY)) {

				currentDateWithTimeZone.set(GregorianCalendar.HOUR_OF_DAY, 00);
				currentDateWithTimeZone.set(GregorianCalendar.MINUTE, 00);
				currentDateWithTimeZone.set(GregorianCalendar.SECOND, 00);
				// creating startdate when today coming in request
				startDateWithTimeZone = currentDateWithTimeZone.getTime();

			}
			if (StringUtils.isNotBlank(alarmType)) {

				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_DOWNLOAD
								.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(ALARM_TYPE, alarmType);
				parameter.put(START_DATE, startDateWithTimeZone);
				parameter.put(END_DATE, endDateWithTimeZone);

			} else { // this condition will get executed when alarmtype is null
						// and period having value

				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE_DOWNLOAD
								.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(START_DATE, startDateWithTimeZone);
				parameter.put(END_DATE, endDateWithTimeZone);

			}

		} else if ((StringUtils.isBlank(startDate))
				&& (StringUtils.isBlank(endDate))
				&& (StringUtils.isBlank(period))) {
			Calendar cal = Calendar.getInstance();

			// start date will be current date -6 days
			cal.add(Calendar.DATE, -6);
			Calendar stDatetime = CommonUtil.setUserTimeZone(cal, timeZone);
			Date stDate = stDatetime.getTime();

			// end date current Date
			Calendar calendDate = Calendar.getInstance();
			calendDate.add(Calendar.DATE, 0);

			Calendar enDateTime = CommonUtil.setUserTimeZone(calendDate,
					timeZone);
			// Date enDate = new Date(enDateTime.getTimeInMillis());
			Date enDate = enDateTime.getTime();

			// when alarmtype is not null
			if (StringUtils.isNotBlank(alarmType)) {
				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_DOWNLOAD
								.toString());
				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(ALARM_TYPE, alarmType);
				parameter.put(START_DATE, stDate);
				parameter.put(END_DATE, enDate);
			} else {// when alarmtype is null
				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE_DOWNLOAD
								.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(START_DATE, stDate);
				parameter.put(END_DATE, enDate);
			}

		} else {// this condition will get executed when startdate and enddate
				// is coming in request and period is null in this case no need
				// of timezone

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date startDateWithOutTimeZone = null;
			Date endDateWithOutTimeZone = null;

			startDateWithOutTimeZone = sdf.parse(startDate);
			endDateWithOutTimeZone = sdf.parse(endDate);

			// alarmType is not null
			if (StringUtils.isNotBlank(alarmType)) {

				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_DOWNLOAD
								.toString());

				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(ALARM_TYPE, alarmType);
				parameter.put(START_DATE, startDateWithOutTimeZone);
				parameter.put(END_DATE, endDateWithOutTimeZone);

			} else {// alarmType is null
				sqlQuery = String
						.format(SQL_GET_NOTIFICATION_OVERVIEW_GROUP_ID_WITHOUTALARMTYPE_DOWNLOAD
								.toString());
				parameter.put(GROUP_ID, groupId.toString());
				parameter.put(GROUP_LEVEl, grouplevel);
				parameter.put(START_DATE, startDateWithOutTimeZone);
				parameter.put(END_DATE, endDateWithOutTimeZone);
			}
		}

		// executing Query
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		Object[] rowData = null;

		List<NotificationOverViewVO> notificationOverViewList = null;

		NotificationOverViewVO notificationOverViewVO = null;

		if (resultList != null && resultList.size() > 0) {

			logger.debug("list of return by executeSQLSelect method"
					+ resultList.toString());

			Iterator<?> itr = resultList.iterator();

			notificationOverViewList = new ArrayList<NotificationOverViewVO>();
			while (itr.hasNext()) {

				notificationOverViewVO = new NotificationOverViewVO();
				rowData = (Object[]) itr.next();
				notificationOverViewVO.setAlarmcount((Long) rowData[0]);
				notificationOverViewVO.setSupplyGroupName((String) rowData[1]);
				notificationOverViewVO.setPathName((String) rowData[2]);
				notificationOverViewVO.setCompanyName((String) rowData[3]);

				notificationOverViewList.add(notificationOverViewVO);

			}

		}
		return notificationOverViewList;

	}

	@Override
	public List<NotificationDetailsVO> getNotificationDetailsDownloadData(
			List<Long> notificationIds) {

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(DaoConstants.ID, notificationIds);

		List<?> resultList = sqlDao.executeSQLSelect(
				SQL_GET_NOTIFICATION_DETAIL.toString(), parameter);

		Object[] rowData = null;

		List<NotificationDetailsVO> NotificationDetailsList = null;

		NotificationDetailsVO notificationDetailsVO = null;

		if (resultList != null && resultList.size() > 0) {

			logger.debug("list of return by executeSQLSelect method"
					+ resultList.toString());

			Iterator<?> itr = resultList.iterator();

			NotificationDetailsList = new ArrayList<NotificationDetailsVO>();
			while (itr.hasNext()) {

				notificationDetailsVO = new NotificationDetailsVO();
				rowData = (Object[]) itr.next();

				notificationDetailsVO
						.setSeverity(rowData[3] == null ? BizConstants.HYPHEN
								: (String) rowData[3]);
				notificationDetailsVO
						.setCode(rowData[5] == null ? BizConstants.HYPHEN
								: (String) rowData[5]);
				notificationDetailsVO
						.setAlarmOccurred(rowData[0] != null ? CommonUtil
								.dateToString(
										(Timestamp) rowData[0],
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD)
								: null);

				notificationDetailsVO
						.setNotificationID(((BigInteger) rowData[1])
								.longValue());
				notificationDetailsVO
						.setAlarmFixed(rowData[8] != null ? CommonUtil
								.dateToString(
										(Timestamp) rowData[8],
										BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD)
								: null);
				notificationDetailsVO
						.setAlarmStatus(rowData[9] == null ? BizConstants.HYPHEN
								: (String) rowData[9]);
				notificationDetailsVO
						.setSitePath(rowData[19] != null ? (String) rowData[19]
								: null);
				notificationDetailsVO
						.setCustomerName(rowData[20] != null ? (String) rowData[20]
								: null);
				notificationDetailsVO
						.setDeviceName(rowData[21] == null ? BizConstants.HYPHEN
								: (String) rowData[21]);

				NotificationDetailsList.add(notificationDetailsVO);

			}

		}
		return NotificationDetailsList;
	}
}
