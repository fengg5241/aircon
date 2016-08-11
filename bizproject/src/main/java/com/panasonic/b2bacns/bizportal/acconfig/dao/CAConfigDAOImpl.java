package com.panasonic.b2bacns.bizportal.acconfig.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.CAConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.DetailsLogsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUListVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.RefrigerantSVG;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.persistence.Adapter;
import com.panasonic.b2bacns.bizportal.persistence.Metaindoorunit;
import com.panasonic.b2bacns.bizportal.pf.common.Constants;
import com.panasonic.b2bacns.bizportal.pf.common.PropValueAlgo;
import com.panasonic.b2bacns.bizportal.service.IndoorUnitsService;
import com.panasonic.b2bacns.bizportal.service.MetaIndoorUnitsService;
import com.panasonic.b2bacns.bizportal.topology.grouping.IndoorUnitPlatForm;
import com.panasonic.b2bacns.bizportal.topology.grouping.OutdoorUintPf;
import com.panasonic.b2bacns.bizportal.topology.grouping.TopologyGroupingService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.DaoConstants;
import com.panasonic.spf.b2bac.facility.FacilityException;
import com.panasonic.spf.b2bac.facility.api.FacilityManager;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureParam;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Contoller;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.IndoorUnit;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Meter;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.OutdoorUnit;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Property;

/**
 * This class handles all service for Cloud Adapters
 * 
 * @author jwchan
 * 
 */

@Service
public class CAConfigDAOImpl implements CAConfigDAO {

	private static final Logger logger = Logger
			.getLogger(CAConfigDAOImpl.class);

	@Autowired
	private IndoorUnitsService indoorUnitsService;

	@Autowired
	private MetaIndoorUnitsService metaIndoorUnitsService;

	@Autowired
	private TopologyGroupingService TopologyGroupingService;

	@Autowired
	private SQLDAO sqlDao;

	private static final String MAP_ODUID = "oduid";
	private static final String MAP_GROUPID = "group_id";
	private static final String MAP_IDU_SVGMAXLONGITUDE = "idu_svg_max_longitude";
	private static final String MAP_IDU_SVGMINLONGITUDE = "idu_svg_min_longitude";
	private static final String MAP_IDU_SVGMAXLATITUDE = "idu_svg_max_latitude";
	private static final String MAP_IDU_SVGMINLATITUDE = "idu_svg_min_latitude";
	private static final String MAP_IDU_CENTRAL_ADDRESS = "iducentraladdress";
	private static final String ID = "id";
	private static final String MAP_CONTROL_GROUP = "controlgroup";
	private static final String MAP_SITE_GROUP = "sitegroup";
	private static final String MAP_PARENT_CHILD = "pc";
	private static final String MAP_NAME = "name";
	private static final String MAP_POWER_STATUS = "powerstatus";
	private static final String MAP_MODE_NAME = "modename";
	private static final String MAP_ISECONAVI = "iseconavi";
	private static final String MAP_PARENT_ID = "parent_id";
	private static final String MAP_STATUS = "status";
	private static final String MAP_IDU_SVG_PATH = "idusvgpath";
	private static final String MAP_ODU_SVG_PATH = "odusvgpath";
	private static final String ROOM_TEMP = "roomtemp";

	private static final String OUTDOORUNIT_ID = "outdoorunit_id";
	private static final String ODU_NAME = "oduname";

	private static final String INDOORUNIT_ID = "iduId";
	private static final String GROUPS_ID = "groupIdlist";

	private static final String ODUPARAM_OUTDOORUNIT_ID = "outdoorid";
	private static final String ODUPARAM_ODU_TYPE = "type";
	private static final String ODUPARAM_PROPERTY_ID = "property_id";
	private static final String ODUPARAM_MEASURE_VALUE = "measure_val";
	private static final String ODUPARAM_DISPLAY_NAME = "display_name";
	private static final String ODUPARAM_MODELNAME = "modelname";
	private static final String ODUPARAM_DIMENSION = "dimension";
	private static final String ODUPARAM_UTILIZATION_RATE = "utilizationrate";
	private static final String ODUPARAM_WORKING_HOUR = "workinghour";

	private static final String ODU_ID = "id";
	private static final String ODU_PARAMS_IDTYPE = "idType";
	private static final String MAP_PROHIBITION_STATUS = "prohibitionstatus";
	private static final String MAP_PROHIBITION_MODE = "prohibitionmode";
	private static final String MAP_PROHIBITION_TEMP = "prohibitiontemp";
	private static final String MAP_PROHIBITION_SPEED = "prohibitionspeed";
	private static final String MAP_PROHIBITION_DIRECTION = "prohibitiondirection";
	private static final String MAP_PROHIBITION_ENERGY = "prohibitionenergy";
	private static final String MAP_ENERGY = "energy";
	private static final String MAP_ALARM_CODE = "alarmcode";
	private static final String MAP_A3 = "a3";
	private static final String MAP_A3A = "a3a";
	private static final String MAP_A3B = "a3b";
	private static final String MAP_A3C = "a3c";
	private static final String MAP_A3D = "a3d";
	private static final String MAP_A6 = "a6";
	private static final String MAP_A6A = "a6a";
	private static final String MAP_A6B = "a6b";
	private static final String MAP_A6C = "a6c";
	private static final String MAP_A6D = "a6d";
	private static final String MAP_A6E = "a6e";
	private static final String MAP_A7 = "a7";
	private static final String MAP_A7A = "a7a";
	private static final String MAP_A7B = "a7b";
	private static final String MAP_A7C = "a7c";
	private static final String MAP_A7D = "a7d";
	private static final String MAP_A7E = "a7e";
	private static final String MAP_TYPE = "type";

	private static final String ONLINE = "Online";
	// private static final StringBuffer SQL_GET_ODU_PARAMETERLIST2 = new
	// StringBuffer(
	// "select parameter_name from outdoorunitparameters where type ='%s' ");
	/*
	 * private static final StringBuffer SQL_GET_ODU_PARAMETERLIST2 = new
	 * StringBuffer(
	 * "select co.name, g.name, ca.address from adapters ca, companies co, groups g, outdoorunits odu, vrfparameter_statistics pa "
	 * ) .append(
	 * "where ca.company_id = co.id AND ca.defualtgroupid = g.id AND ca.id = odu.adapters_id AND odu.id = pa.outdoorunit_id "
	 * ) .append("group by ca.address, co.name, g.name ");
	 */

	private static final StringBuffer SQL_GET_ODU_PARAMETERLIST3 = new StringBuffer(
			"select co.name as companyName, g.name as groupName, ca.address as caAddress, SUM(pa.mov1pulse) as mov1pulse, SUM(pa.mov2pulse) as mov2pulse, SUM(pa.mov4pulse) as mov4pulse, g.id as groupId from adapters ca, companies co, groups g, outdoorunits odu, vrfparameter_statistics pa ")
			.append("where ca.company_id = co.id AND ca.defualtgroupid = g.id AND ca.id = odu.adapters_id AND odu.id = pa.outdoorunit_id ")
			.append("group by ca.address, co.name, g.id, g.name ");

	// Modified by ravi to display ca only linked to IDU
	// Modified by shanf to display device model
	private static final StringBuffer SQL_GET_ODU_PARAMETERLIST2 = new StringBuffer(
			"WITH RECURSIVE ctegetcafromgroups AS(")
			.append("SELECT id, name,parentid, uniqueid, '|' || uniqueid || '|' as paths,  ARRAY['' || uniqueid] as patha, 0 as levela,")
			.append("CASE WHEN 0 = 0 THEN  uniqueid ELSE null END as u from groups WHERE parentid IS NULL ")	
			.append("UNION ALL ")	
			.append("SELECT child.id, child.name, child.parentid, child.uniqueid,   paths || child.uniqueid || '|' as paths, ")
			.append("array_append(patha, '' || child.uniqueid) , levela + 1 ,CASE WHEN levela + 1 = 1 THEN child.uniqueid ELSE null END as u FROM groups child ")	
			.append("INNER JOIN ctegetcafromgroups p ON p.id=child.parentid)")	
			.append("SELECT companyName, groupName, macAddress, groupId, array_to_string(array_agg(CONCAT(pport ,\':\' , ptype , \':\', pval,\':\', pfac)), \', \')  AS pulseMeterList, acode,cid,device_model ")
			.append("FROM (")
			.append("select ca.id as cid, ca.model_id as device_model, co.name as companyName, g.name as groupName, ca.mac_address as macAddress, g.id as groupId , ")
			.append("pm.oid as pid, pm.port_number as pport, pm.meter_type as ptype, pmd.measure_val as pval, pm.multi_factor as pfac, nlog.code as acode ")
			.append("from adapters ca ")
			.append("LEFT outer JOIN pulse_meter pm on ca.id = pm.adapters_id ")
			.append("LEFT outer join period_measure_data pmd on pm.oid =  pmd.facl_id AND pmd.property_id = 'AD1' ")
			.append("LEFT outer join companies co on ca.company_id = co.id ")
			.append("LEFT outer join groups g on ca.siteid = g.uniqueid ")
			.append("LEFT outer join notificationlog nlog on ca.id = nlog.adapterid AND  ")
			.append("nlog.id = (SELECT nlog1.id FROM notificationlog nlog1 where ca.id = nlog1.adapterid AND status <> 'Fixed' AND ")
			.append("indoorunit_id IS null AND outdoorunit_id IS NULL order by time desc limit 1) ")
			.append("WHERE ca.siteid IN (select distinct unnest(patha) as uid FROM ctegetcafromgroups ")
			.append("where ARRAY[patha] && string_to_array(btrim('%s', '[]'), ', ')) and ca.id in (select * from usp_getadapterid_supplygroupid(btrim('%s', '[]'))) ")
			.append(" ) AS row ")
			.append("GROUP BY cid, companyName, groupName, macAddress,groupId, acode,device_model");

	private static final StringBuffer SQL_GET_ODU_PARAMETERLIST1 = new StringBuffer(
			"SELECT companyName, groupName, macAddress, groupId, array_to_string(array_agg(CONCAT(pport ,\':\' , ptype , \':\', pval,\':\', pfac)), \', \')  AS pulseMeterList, acode, cid ")
			.append("FROM (")
			.append("select ca.id as cid, co.name as companyName, g.name as groupName, ca.mac_address as macAddress, g.id as groupId , ")
			.append("pm.oid as pid, pm.port_number as pport, pm.meter_type as ptype, pmd.measure_val as pval, pm.multi_factor as pfac, nlog.code as acode ")
			.append("from adapters ca ")
			.append("LEFT outer join pulse_meter pm on ca.id = pm.adapters_id ")
			.append("LEFT outer join period_measure_data pmd on pm.oid =  pmd.facl_id AND pmd.property_id = 'AD1' ")
			.append("LEFT outer join companies co on ca.company_id = co.id ")
			.append("LEFT outer join groups g on ca.siteid = g.uniqueid ")
			.append("LEFT outer join notificationlog nlog on ca.id = nlog.adapterid AND  ")
			.append("nlog.id = (SELECT nlog1.id FROM notificationlog nlog1 where ca.id = nlog1.adapterid AND status <> 'Fixed' AND ")
			.append("indoorunit_id IS null AND outdoorunit_id IS NULL order by time desc limit 1) ")
			.append("WHERE ca.mac_address = '%s' ")
			.append(" ) AS row ")
			.append("GROUP BY cid, companyName, groupName, macAddress,groupId, acode");
	// Modified by shanf
	private static final StringBuffer SQL_GET_CA_PARAMETERLIST1 = new StringBuffer(
			"SELECT companyName, groupName, macAddress, groupId, array_to_string(array_agg(CONCAT(pport ,\':\' , ptype , \':\', pval,\':\', pfac)), \', \')  AS pulseMeterList, acode, cid,device_model ")
			.append("FROM (")
			.append("select ca.id as cid, ca.model_id as device_model, co.name as companyName, g.name as groupName, ca.mac_address as macAddress, g.id as groupId , ")
			.append("pm.oid as pid, pm.port_number as pport, pm.meter_type as ptype, pmd.measure_val as pval, pm.multi_factor as pfac, nlog.code as acode ")
			.append("from adapters ca ")
			.append("LEFT OUTER JOIN pulse_meter pm on ca.id = pm.adapters_id ")
			.append("LEFT outer join period_measure_data pmd on pm.oid =  pmd.facl_id AND pmd.property_id = 'AD1' ")
			.append("LEFT outer join companies co on ca.company_id = co.id ")
			.append("LEFT outer join groups g on ca.siteid = g.uniqueid ")
			.append("LEFT outer join notificationlog nlog on ca.id = nlog.adapterid AND  ")
			.append("nlog.id = (SELECT nlog1.id FROM notificationlog nlog1 where ca.id = nlog1.adapterid AND status <> 'Fixed' AND ")
			.append("indoorunit_id IS null AND outdoorunit_id IS NULL order by time desc limit 1) ")
			.append("WHERE ca.id in(:id) ")
			.append(" ) AS row ")
			.append("GROUP BY cid, companyName, groupName, macAddress,groupId, acode,device_model");

	/*
	 * .append("INNER JOIN companies co on ca.company_id = co.id ")
	 * .append("INNER JOIN groups g on ca.defualtgroupid = g.id ")
	 * .append("INNER JOIN outdoorunits odu on ca.id = odu.adapters_id ")
	 * .append
	 * ("INNER JOIN vrfparameter_statistics pa on odu.id = pa.outdoorunit_id ")
	 * .append("group by ca.address, co.name, g.name ");
	 */
	// "select parameter_name , display_name from outdoorunitparameters where type ='%s' ")
	private static final StringBuffer SQL_GET_ODU_PARAMETERLIST = new StringBuffer(
			"select idu.id as id, g1.name as controlgroup, g.name as sitegroup,  case when idu.parent_id is null then 'Parent' else 'Child' end pc,")
			.append("idu.name as name, idu.centraladdress as iducentraladdress, ")
			.append("sts.a1 as powerstatus,sts.a2 as modename,sts.a28 as iseconavi,sts.a4 as roomtemp,idu.parent_id, 'Active' as status,group_id,idu.outdoorunit_id as oduid,idu.svg_max_latitude as idu_svg_max_latitude,idu.svg_max_longitude as idu_svg_max_longitude,idu.svg_min_latitude as idu_svg_min_latitude,idu.svg_min_longitude as idu_svg_min_longitude,idu.svg_path as idusvgpath,  ")
			.append("odu.svg_path as odusvgpath, sts.a10 as prohibitionstatus, sts.a11 as prohibitionmode, sts.a12 as prohibitiontemp, sts.a13 as prohibitionspeed, sts.a14 as prohibitiondirection, sts.a15 as prohibitionenergy, sts.a34 as energy, t.code as alarmcode,sts.a3 as a3,")
			.append("sts.a6 as a6,sts.a7 as a7,sts.a3a as a3a,sts.a3b as a3b,sts.a3c as a3c,sts.a3d as a3d,sts.a6a as a6a,sts.a6b as a6b,sts.a6c as a6c,sts.a6d as a6d,sts.a6e as a6e,sts.a7a as a7a,sts.a7b as a7b,sts.a7c as a7c,sts.a7d as a7d,sts.a7e as a7e,idu.type ")
			.append("from indoorunits idu INNER JOIN adapters ca on idu.adapters_id = ca.id left outer join ct_statusinfo sts on idu.oid = sts.facl_id  ")
			.append("left join outdoorunits odu on idu.outdoorunit_id = odu.id left outer join groups g on g.uniqueid = idu.siteid left outer join groups g1 on idu.group_id = g1.id ")
			.append("left outer join (select indoorunit_id,code from (select row_number() over (partition by indoorunit_id order  by severity, time desc) row_number, indoorunit_id,code from notificationlog where lower(status) = 'new' and severity is not null order by severity, time desc) temp where row_number = 1) t on idu.id = t.indoorunit_id ")
			.append("WHERE ca.mac_address = '%s' ")
			.append("order by case when idu.parent_id is null then idu.id else idu.parent_id end, idu.id, g.name ");

	private static final StringBuffer SQL_GET_STA_FACL = new StringBuffer(
			"select sts.property_id as property_id, sts.measure_val as measure_val FROM status_info sts ")
			.append("WHERE sts.facl_id = '%s' ");

	private static final StringBuffer SQL_SET_META_BY_FACL = new StringBuffer(
			"UPDATE indoorunits SET metaindoorunit_id = %d where oid = '%s' ");

	// .append("WHERE sts.facl_id = '%s' ");

	private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_CA = new StringBuilder(
			"select * from (select idu.id as id,g1.name as controlgroup, g.name as sitegroup,case when idu.parent_id is null then 'Main' else 'Sub' end pc,case when t1.adapterid is null then 'Online' else 'Offline' end as castatus,")
			.append("idu.name as name,idu.centraladdress  as centraladdress,sts.a1 as powerstatus,sts.a2_1 as a2_1,sts.a2_2 as a2_2,sts.a28 as iseconavi,sts.a4 as roomtemp,")
			.append("idu.parent_id, 'Active' as status,group_id,idu.outdoorunit_id as oduid,idu.svg_max_latitude as svg_max_latitude,idu.svg_max_longitude as svg_max_longitude,")
			.append("idu.svg_min_latitude as svg_min_latitude,idu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as idusvgpath, svg1.svg_file_name as odusvgpath, ")
			.append("sts.a10 as prohibitionstatus, sts.a11 as prohibitionmode, sts.a12 as prohibitiontemp, sts.a13 as prohibitionspeed, sts.a14 as prohibitiondirection, ")
			.append("sts.a15 as prohibitionenergy, sts.a34 as energy, t.code as alarmcode, sts.a3 as a3,sts.a6_1 as a6_1,sts.a7_1 as a7_1,sts.a6_2 as a6_2,sts.a7_2 as a7_2,sts.a3a as a3a,sts.a3h as a3h,sts.a3c as a3c,sts.a3d as a3d,")
			.append("sts.a6a_1 as a6a_1,sts.a6h_1 as a6h_1,sts.a6c_1 as a6c_1,sts.a6d_1 as a6d_1,sts.a6f_1 as a6f_1,sts.a7a_1 as a7a_1,sts.a7h_1 as a7h_1,sts.a7c_1 as a7c_1,sts.a7d_1 as a7d_1,sts.a7f_1 as a7f_1,")
			.append("sts.a6a_2 as a6a_2,sts.a6h_2 as a6h_2,sts.a6c_2 as a6c_2,sts.a6d_2 as a6d_2,sts.a6f_2 as a6f_2,sts.a7a_2 as a7a_2,sts.a7h_2 as a7h_2,sts.a7c_2 as a7c_2,sts.a7d_2 as a7d_2,sts.a7f_2 as a7f_2,sts.a17a as a17a,idu.type, ")
			.append("idu.slinkaddress,t.severity,svg.id as svgid,svg.svg_name as svgname,svg1.id as linkedsvgid,rm.svg_id1,rm.svg_max_latitude1 as refgrnt_svg_max_latitude1,")
			.append("rm.svg_max_longitude1 as refgrnt_svg_max_longitude1,rm.svg_min_latitude1 as refgrnt_svg_min_latitude1,rm.svg_min_longitude1 as refgrnt_svg_min_longitude1, rm.svg_id2,")
			.append("rm.svg_max_latitude2 as refgrnt_svg_max_latitude2,rm.svg_max_longitude2 as refgrnt_svg_max_longitude2,rm.svg_min_latitude2 as refgrnt_svg_min_latitude2,")
			.append("rm.svg_min_longitude2 as refgrnt_svg_min_longitude2, rm.svg_id3,rm.svg_max_latitude3 as refgrnt_svg_max_latitude3,rm.svg_max_longitude3 as refgrnt_svg_max_longitude3,rm.svg_min_latitude3 as refgrnt_svg_min_latitude3,")
			.append("rm.svg_min_longitude3 as refgrnt_svg_min_longitude3,idu.rc_flag as rcflag,g.path_name as sitepath,c.name as companyname,case when parent_id is not null then idu.parent_id else idu.id end parentidsort from indoorunits idu left outer join ct_statusinfo sts on idu.oid= sts.facl_id ")
			.append("left join outdoorunits odu on idu.outdoorunit_id = odu.id left outer join groups g on g.uniqueid = idu.siteid left outer join groups g1 on g1.id = idu.group_id ")
			.append("left outer join adapters ca on idu.adapters_id = ca.id")
			.append("left outer join (select indoorunit_id,code,severity  from (select row_number() over (partition by indoorunit_id order  by severity, time desc) row_number, ")
			.append("indoorunit_id,code,severity from notificationlog where lower(status) = 'new' and indoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) t on idu.id = t.indoorunit_id ")
			.append("left join refrigerantmaster rm on (rm.svg_id1=idu.svg_id or rm.svg_id2=idu.svg_id or rm.svg_id3=idu.svg_id) and rm.siteid = idu.siteid left outer join svg_master svg on svg.id = idu.svg_id ")
			.append("left outer join svg_master svg1 on svg1.id = odu.svg_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order  by time desc) row_number, ")
			.append("adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on idu.adapters_id = t1.adapterid ")
			.append("where ca.mac_address = '%s' order by case when idu.parent_id is null then idu.id else idu.parent_id end,idu.parent_id,idu.id,g1.name, g.name) T order by parentidsort,pc,id");

	private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_ODU_CA = new StringBuilder(
			"select * from (select odu.id as oduid, odu.centraladdress as centraladdress, odu.name as name, odu.type as type,odu.svg_max_latitude as svg_max_latitude,odu.svg_max_longitude as svg_max_longitude,case when t1.adapterid is null then 'Online' ")
			.append("else 'Offline' end as castatus,odu.svg_min_latitude as svg_min_latitude,odu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as odusvgpath,case when odu.parentid is null then 'Main' else 'Sub' end pc ")
			.append(",odu.parentid as parent_id,g.name as sitegroup,odu.slinkaddress,nl.severity,svg.id as svgid,svg.svg_name as svgname,nl.code as alarmcode,g.path_name as sitepath,c.name as companyname, ")
			.append("pmd.c2,pmd.v23,pmd.g44,maint.ghp_engine_operation_hours as ghphrs,maint.ghp_time_after_oil_change as ghpoil,maint.pac_comp_operation_hours as pachrs,")
			.append("maint.vrf_comp_operation_hours_1 as vrfhrs1,maint.vrf_comp_operation_hours_2 as vrfhrs2,maint.vrf_comp_operation_hours_3 as vrfhrs3,setting.threshold_1 ,setting.threshold_2 ")
			.append(",setting.threshold_3 ,setting.threshold_4 ,setting.threshold_5 ,setting.threshold_6,case when odu.parentid is not null then odu.parentid else odu.id end parentidsort  from outdoorunits odu left outer join groups g on g.uniqueid = odu.siteid ")
			.append("left outer join (select outdoorunit_id,code,severity from (select row_number() over (partition by outdoorunit_id order  by severity, time desc) row_number, ")
			.append("outdoorunit_id,code,severity from notificationlog where lower(status) = 'new' and outdoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) ")
			.append("nl on odu.id= nl.outdoorunit_id left outer join ct_period_measure_data pmd on odu.oid=pmd.facl_id left outer join svg_master svg on svg.id = odu.svg_id ")
			.append("left outer join adapters ca on odu.adapters_id = ca.id ")
			.append("left outer join maintenance_status_data maint on odu.id=maint.outdoorunit_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order ")
			.append("by time desc) row_number, adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on odu.adapters_id = t1.adapterid ")
			.append("left join (select g.id,max(case when setting.maintenance_type_id in(1)then setting.threshold else null end) as threshold_1, ")
			.append("max(case when setting.maintenance_type_id in(2)then setting.threshold else null end) as threshold_2, ")
			.append("max(case when setting.maintenance_type_id in(3)then setting.threshold else null end) as threshold_3, ")
			.append("max(case when setting.maintenance_type_id in(4)then setting.threshold else null end) as threshold_4, ")
			.append("max(case when setting.maintenance_type_id in(5)then setting.threshold else null end) as threshold_5, ")
			.append("max(case when setting.maintenance_type_id in(6)then setting.threshold else null end) as threshold_6 ")
			.append("from maintenance_setting setting inner join groups g on g.id = setting.group_id group by g.id) setting on setting.id = g.id ")
			.append("%s order by case when odu.parentid is null then odu.id else odu.parentid end,odu.parentid,odu.id,g.name ) T order by parentidsort,pc,oduid");

	private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_ODU_CA1 = new StringBuilder(
			"select * from (select odu.id as oduid, odu.centraladdress as centraladdress, odu.name as name, odu.type as type,odu.svg_max_latitude as svg_max_latitude,odu.svg_max_longitude as svg_max_longitude,case when t1.adapterid is null then 'Online' ")
			.append("else 'Offline' end as castatus,odu.svg_min_latitude as svg_min_latitude,odu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as odusvgpath,case when odu.parentid is null then 'Main' else 'Sub' end pc ")
			.append(",odu.parentid as parent_id,g.name as sitegroup,odu.slinkaddress,nl.severity,svg.id as svgid,svg.svg_name as svgname,nl.code as alarmcode,g.path_name as sitepath,c.name as companyname, ")
			.append("pmd.c2,pmd.v23,pmd.g44,maint.ghp_engine_operation_hours as ghphrs,maint.ghp_time_after_oil_change as ghpoil,maint.pac_comp_operation_hours as pachrs,")
			.append("maint.vrf_comp_operation_hours_1 as vrfhrs1,maint.vrf_comp_operation_hours_2 as vrfhrs2,maint.vrf_comp_operation_hours_3 as vrfhrs3,setting.threshold_1 ,setting.threshold_2 ")
			.append(",setting.threshold_3 ,setting.threshold_4 ,setting.threshold_5 ,setting.threshold_6,case when odu.parentid is not null then odu.parentid else odu.id end parentidsort  from outdoorunits odu left outer join groups g on g.uniqueid = odu.siteid ")
			.append("left outer join (select outdoorunit_id,code,severity from (select row_number() over (partition by outdoorunit_id order  by severity, time desc) row_number, ")
			.append("outdoorunit_id,code,severity from notificationlog where lower(status) = 'new' and outdoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) ")
			.append("nl on odu.id= nl.outdoorunit_id left outer join ct_period_measure_data pmd on odu.oid=pmd.facl_id left outer join svg_master svg on svg.id = odu.svg_id ")
			.append("left outer join adapters ca on odu.adapters_id = ca.id ")
			.append("left outer join maintenance_status_data maint on odu.id=maint.outdoorunit_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order ")
			.append("by time desc) row_number, adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on odu.adapters_id = t1.adapterid ")
			.append("left join (select g.id,max(case when setting.maintenance_type_id in(1)then setting.threshold else null end) as threshold_1, ")
			.append("max(case when setting.maintenance_type_id in(2)then setting.threshold else null end) as threshold_2, ")
			.append("max(case when setting.maintenance_type_id in(3)then setting.threshold else null end) as threshold_3, ")
			.append("max(case when setting.maintenance_type_id in(4)then setting.threshold else null end) as threshold_4, ")
			.append("max(case when setting.maintenance_type_id in(5)then setting.threshold else null end) as threshold_5, ")
			.append("max(case when setting.maintenance_type_id in(6)then setting.threshold else null end) as threshold_6 ")
			.append("from maintenance_setting setting inner join groups g on g.id = setting.group_id group by g.id) setting on setting.id = g.id ")
			.append("where ca.mac_address ='%s' order by case when odu.parentid is null then odu.id else odu.parentid end,odu.parentid,odu.id,g.name ) T order by parentidsort,pc,oduid");

	private static final StringBuilder SQL_GET_ODU_UNITS_CA = new StringBuilder(
			" where ca.mac_address = :id ");

	private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_IDU_CA = new StringBuilder(
			"select * from (select idu.id as id,g1.name as controlgroup, g.name as sitegroup,case when idu.parent_id is null then 'Main' else 'Sub' end pc,case when t1.adapterid is null then 'Online' else 'Offline' end as castatus,")
			.append("idu.name as name,idu.centraladdress  as centraladdress,sts.a1 as powerstatus,sts.a2_1 as a2_1,sts.a2_2 as a2_2,sts.a28 as iseconavi,pmd.a4 as roomtemp,")
			.append("idu.parent_id, 'Active' as status,group_id,idu.outdoorunit_id as oduid,idu.svg_max_latitude as svg_max_latitude,idu.svg_max_longitude as svg_max_longitude,")
			.append("idu.svg_min_latitude as svg_min_latitude,idu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as idusvgpath, svg1.svg_file_name as odusvgpath, ")
			.append("sts.a10 as prohibitionstatus, sts.a11 as prohibitionmode, sts.a12 as prohibitiontemp, sts.a13 as prohibitionspeed, sts.a14 as prohibitiondirection, ")
			.append("sts.a15 as prohibitionenergy, sts.a34 as energy, t.code as alarmcode, sts.a3 as a3,sts.a6_1 as a6_1,sts.a7_1 as a7_1,sts.a6_2 as a6_2,sts.a7_2 as a7_2,sts.a3a as a3a,sts.a3h as a3h,sts.a3c as a3c,sts.a3d as a3d,")
			.append("sts.a6a_1 as a6a_1,sts.a6h_1 as a6h_1,sts.a6c_1 as a6c_1,sts.a6d_1 as a6d_1,sts.a6f_1 as a6f_1,sts.a7a_1 as a7a_1,sts.a7h_1 as a7h_1,sts.a7c_1 as a7c_1,sts.a7d_1 as a7d_1,sts.a7f_1 as a7f_1,")
			.append("sts.a6a_2 as a6a_2,sts.a6h_2 as a6h_2,sts.a6c_2 as a6c_2,sts.a6d_2 as a6d_2,sts.a6f_2 as a6f_2,sts.a7a_2 as a7a_2,sts.a7h_2 as a7h_2,sts.a7c_2 as a7c_2,sts.a7d_2 as a7d_2,sts.a7f_2 as a7f_2,sts.a17a as a17a,idu.type, ")
			.append("idu.slinkaddress,t.severity,svg.id as svgid,svg.svg_name as svgname,svg1.id as linkedsvgid,rm.svg_id1,rm.svg_max_latitude1 as refgrnt_svg_max_latitude1,")
			.append("rm.svg_max_longitude1 as refgrnt_svg_max_longitude1,rm.svg_min_latitude1 as refgrnt_svg_min_latitude1,rm.svg_min_longitude1 as refgrnt_svg_min_longitude1, rm.svg_id2,")
			.append("rm.svg_max_latitude2 as refgrnt_svg_max_latitude2,rm.svg_max_longitude2 as refgrnt_svg_max_longitude2,rm.svg_min_latitude2 as refgrnt_svg_min_latitude2,")
			.append("rm.svg_min_longitude2 as refgrnt_svg_min_longitude2, rm.svg_id3,rm.svg_max_latitude3 as refgrnt_svg_max_latitude3,rm.svg_max_longitude3 as refgrnt_svg_max_longitude3,rm.svg_min_latitude3 as refgrnt_svg_min_latitude3,")
			.append("rm.svg_min_longitude3 as refgrnt_svg_min_longitude3,idu.rc_flag as rcflag,g.path_name as sitepath,c.name as companyname,case when parent_id is not null then idu.parent_id else idu.id end parentidsort from indoorunits idu left outer join ct_statusinfo sts on idu.oid= sts.facl_id ")
			.append("left outer join ct_period_measure_data pmd on idu.oid= pmd.facl_id left join adapters ca on idu.adapters_id = ca.id ")
			.append("left join outdoorunits odu on idu.outdoorunit_id = odu.id left outer join groups g on g.uniqueid = idu.siteid left outer join groups g1 on g1.id = idu.group_id ")
			.append("left outer join (select indoorunit_id,code,severity  from (select row_number() over (partition by indoorunit_id order  by severity, time desc) row_number, ")
			.append("indoorunit_id,code,severity from notificationlog where lower(status) = 'new' and indoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) t on idu.id = t.indoorunit_id ")
			.append("left join refrigerantmaster rm on (rm.svg_id1=idu.svg_id or rm.svg_id2=idu.svg_id or rm.svg_id3=idu.svg_id) and rm.siteid = idu.siteid left outer join svg_master svg on svg.id = idu.svg_id ")
			.append("left outer join svg_master svg1 on svg1.id = odu.svg_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order  by time desc) row_number, ")
			.append("adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on idu.adapters_id = t1.adapterid ")
			.append("where ca.mac_address = :id order by case when idu.parent_id is null then idu.id else idu.parent_id end,idu.parent_id,idu.id,g1.name, g.name) T order by parentidsort,pc,id");

			private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_ODU = new StringBuilder(
					"%s select * from (select odu.id as oduid, odu.centraladdress as centraladdress, odu.name as name, odu.type as type,odu.svg_max_latitude as svg_max_latitude,odu.svg_max_longitude as svg_max_longitude,case when t1.adapterid is null then 'Online' ")
					.append("else 'Offline' end as castatus,odu.svg_min_latitude as svg_min_latitude,odu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as odusvgpath,case when odu.parentid is null then 'Main' else 'Sub' end pc")
					.append(",odu.parentid as parent_id,g.name as sitegroup,odu.slinkaddress,nl.severity,svg.id as svgid,svg.svg_name as svgname,nl.code as alarmcode,g.path_name as sitepath,c.name as companyname,")
					.append("pmd.c2,pmd.v23,pmd.g44,maint.ghp_engine_operation_hours as ghphrs,maint.ghp_time_after_oil_change as ghpoil,maint.pac_comp_operation_hours as pachrs,")
					.append("maint.vrf_comp_operation_hours_1 as vrfhrs1,maint.vrf_comp_operation_hours_2 as vrfhrs2,maint.vrf_comp_operation_hours_3 as vrfhrs3,setting.threshold_1 ,setting.threshold_2")
					.append(",setting.threshold_3 ,setting.threshold_4 ,setting.threshold_5 ,setting.threshold_6,case when odu.parentid is not null then odu.parentid else odu.id end parentidsort,odu.compressor1,")
					.append("odu.compressor2,odu.compressor3  from outdoorunits odu %s left outer join groups g on g.uniqueid = odu.siteid ")
					.append("left outer join (select outdoorunit_id,code,severity from (select row_number() over (partition by outdoorunit_id order  by severity, time desc) row_number, ")
					.append("outdoorunit_id,code,severity from notificationlog where lower(status) = 'new' and outdoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) ")
					.append("nl on odu.id= nl.outdoorunit_id left outer join ct_period_measure_data pmd on odu.oid=pmd.facl_id left outer join svg_master svg on svg.id = odu.svg_id ")
					.append("left outer join maintenance_status_data maint on odu.id=maint.outdoorunit_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order ")
					.append(" by time desc) row_number, adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on odu.adapters_id = t1.adapterid ")
					.append("left join (select g.id,max(case when setting.maintenance_type_id in(1)then setting.threshold else null end) as threshold_1,")
					.append("max(case when setting.maintenance_type_id in(2)then setting.threshold else null end) as threshold_2,")
					.append("max(case when setting.maintenance_type_id in(3)then setting.threshold else null end) as threshold_3,")
					.append("max(case when setting.maintenance_type_id in(4)then setting.threshold else null end) as threshold_4,")
					.append("max(case when setting.maintenance_type_id in(5)then setting.threshold else null end) as threshold_5,")
					.append("max(case when setting.maintenance_type_id in(6)then setting.threshold else null end) as threshold_6 ")
					.append("from maintenance_setting setting inner join groups g on g.id = setting.group_id group by g.id) setting on setting.id = g.id ")
					.append("%s order by case when odu.parentid is null then odu.id else odu.parentid end,odu.parentid,odu.id,g.name ) T order by parentidsort,pc,oduid");

			private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_ODU_R = new StringBuilder(
					"%s select * from (select odu.id as oduid, odu.centraladdress as centraladdress, odu.name as name, odu.type as type,odu.svg_max_latitude as svg_max_latitude,odu.svg_max_longitude as svg_max_longitude,case when t1.adapterid is null then 'Online' ")
					.append("else 'Offline' end as castatus,odu.svg_min_latitude as svg_min_latitude,odu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as odusvgpath,case when odu.parentid is null then 'Main' else 'Sub' end pc")
					.append(",odu.parentid as parent_id,g.name as sitegroup,odu.slinkaddress,nl.severity,svg.id as svgid,svg.svg_name as svgname,nl.code as alarmcode,g.path_name as sitepath,c.name as companyname,")
					.append("pmd.c2,pmd.v23,pmd.g44,maint.ghp_engine_operation_hours as ghphrs,maint.ghp_time_after_oil_change as ghpoil,maint.pac_comp_operation_hours as pachrs,")
					.append("maint.vrf_comp_operation_hours_1 as vrfhrs1,maint.vrf_comp_operation_hours_2 as vrfhrs2,maint.vrf_comp_operation_hours_3 as vrfhrs3,setting.threshold_1 ,setting.threshold_2")
					.append(",setting.threshold_3 ,setting.threshold_4 ,setting.threshold_5 ,setting.threshold_6,case when odu.parentid is not null then odu.parentid else odu.id end parentidsort,odu.compressor1,")
					.append("odu.compressor2,odu.compressor3  from outdoorunits odu %s left outer join groups g on g.uniqueid = odu.siteid ")
					.append("left outer join (select outdoorunit_id,code,severity from (select row_number() over (partition by outdoorunit_id order  by severity, time desc) row_number, ")
					.append("outdoorunit_id,code,severity from notificationlog where lower(status) = 'new' and outdoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) ")
					.append("nl on odu.id= nl.outdoorunit_id left outer join ct_period_measure_data pmd on odu.oid=pmd.facl_id left outer join svg_master svg on svg.id = odu.svg_id ")
					.append("left outer join maintenance_status_data maint on odu.id=maint.outdoorunit_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order ")
					.append(" by time desc) row_number, adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on odu.adapters_id = t1.adapterid ")
					.append("left join (select g.id,max(case when setting.maintenance_type_id in(1)then setting.threshold else null end) as threshold_1,")
					.append("max(case when setting.maintenance_type_id in(2)then setting.threshold else null end) as threshold_2,")
					.append("max(case when setting.maintenance_type_id in(3)then setting.threshold else null end) as threshold_3,")
					.append("max(case when setting.maintenance_type_id in(4)then setting.threshold else null end) as threshold_4,")
					.append("max(case when setting.maintenance_type_id in(5)then setting.threshold else null end) as threshold_5,")
					.append("max(case when setting.maintenance_type_id in(6)then setting.threshold else null end) as threshold_6 ")
					.append("from maintenance_setting setting inner join groups g on g.id = setting.group_id group by g.id) setting on setting.id = g.id ")
					.append("%s order by case when odu.parentid is null then odu.id else odu.parentid end,odu.parentid,odu.id,g.name ) T order by parentidsort,pc,oduid");


			private static final StringBuilder SQL_GET_ODU_GROUPS = new StringBuilder(
					" join cteoutdoors t on t.outdoorunit_id = odu.id ");

			private static final StringBuilder SQL_GET_ODU_FN_GROUPS = new StringBuilder(
					" WITH RECURSIVE cteoutdoors AS (select distinct outdoorunit_id from indoorunits f where adapters_id = (select id from adapters where mac_address = :id) ")
					.append("UNION ALL SELECT si.id FROM outdoorunits As si INNER JOIN cteoutdoors AS sp ON (si.parentid = sp.outdoorunit_id)) ");

			private static final StringBuilder SQL_GET_ODU_UNITS = new StringBuilder(
					" where odu.id in (:id) ");

			private static final StringBuffer SQL_GET_ODU_BY_IDU_ID = new StringBuffer(
					"select odu.id as outdoorunit_id,odu.name as oduname from indoorunits idu inner join outdoorunits odu ")
					.append("on idu.outdoorunit_id  = odu.id ").append(
							"where idu.id in (:iduId)");

			private static final StringBuffer SQL_GET_ODU_BY_GROUP_ID = new StringBuffer(
					"select  odu.id as outdoorunit_id, odu.name as oduname from usp_getindoorunits_supplygroupname(:groupIdlist) fn ")
					.append("inner join indoorunits idu on idu.id = fn.indoorunitid ")
					.append("inner join  outdoorunits odu on odu.id = idu.outdoorunit_id order by outdoorunit_id");

			private static final StringBuffer SQL_GET_ODU_PARAMS_FOR_VRF = new StringBuffer(
					"Select odu.id as outdoorid,odu.type, pddata.property_id , pddata.measure_val , oduparam.display_name , modu.modelname as modelname, ")
					.append("Cast( (modu.unitdepthm ||' ,'|| modu.unitheightm || ',' || modu.unitwidthtm ) as varchar(100) ) as dimension , ")
					.append("odulog.utilizationrate as utilizationrate , odulog.workingtime1 as workinghour ")
					.append("from outdoorunits odu ")
					.append("Join period_measure_data pddata ")
					.append("on cast(odu.oid as varchar(128))= cast(pddata.facl_id as varchar(128)) ")
					.append("left outer join outdoorunitparameters oduparam on  oduparam.parameter_name = pddata.property_id ")
					.append("Left outer  join  outdoorunitslog  odulog on odulog.outdoorunit_id = odu.id ")
					.append("left outer Join  metaoutdoorunits  modu on  odu.metaoutdoorunit_id = modu.id ")
					.append("where (pddata.property_id in (%s)) and odu.id = :id and odu.type = :idType ");

			private static final StringBuffer SQL_GET_ODU_PARAMS_FOR_GHP = new StringBuffer(
					"select odu.id as outdoorid,odu.type,pddata.property_id,pddata.measure_val as measure_val,oduparam.display_name,modu.modelname as modelname, ")
					.append(" Cast( (modu.unitdepthm ||' ,'|| modu.unitheightm || ',' || modu.unitwidthtm ) as varchar(100) ) as dimension ,odulog.utilizationrate as utilizationrate , odulog.workingtime1 as workinghour ")
					.append(" from outdoorunits odu Inner Join vw_period_measure_data pddata on ")
					.append("odu.oid = pddata.facl_id left outer join outdoorunitparameters oduparam on oduparam.parameter_name = pddata.property_id        Left outer  join  outdoorunitslog  odulog on odulog.outdoorunit_id = odu.id ")
					.append("left outer Join  metaoutdoorunits  modu on  odu.metaoutdoorunit_id = modu.id where (pddata.property_id in ('%s')) and odu.id = :id and odu.type = :idType ")
					.append(" union All ")
					.append(" select odu.id as outdoorid,odu.type,sts.property_id as g1property_id,sts.measure_val as measure_valg1,oduparam.display_name,modu.modelname as modelname, ")
					.append(" Cast( (modu.unitdepthm ||' ,'|| modu.unitheightm || ',' || modu.unitwidthtm ) as varchar(100) ) as dimension ,odulog.utilizationrate as utilizationrate , odulog.workingtime1 as workinghour ")
					.append(" from outdoorunits odu Join vw_status_info  sts on sts.facl_id = odu.oid left outer join  outdoorunitparameters oduparam on  oduparam.parameter_name = sts.property_id ")
					.append("Left outer  join  outdoorunitslog  odulog on odulog.outdoorunit_id = odu.id left outer Join  metaoutdoorunits  modu on  odu.metaoutdoorunit_id = modu.id ")
					.append("where (sts.property_id in ('%s')) and  odu.id = :id and odu.type = :idType  ");

			private static final StringBuffer SQL_GET_ODU_PARAMETERLIST_R = new StringBuffer(
					"select parameter_name , display_name from outdoorunitparameters where type ='%s' ");

	@Override
	public Metaindoorunit getStatusInfoByFacl(String facl_id, String ooid,
			int adapter_id, String site_id, String company_id)
			throws HibernateException {

		String query = String.format(SQL_GET_STA_FACL.toString(), facl_id);
		// executing query
		List<?> resultSet = sqlDao.executeSQLSelect(query);

		Object[] rowData = null;
		List<Metaindoorunit> oduParamsList = null;
		Metaindoorunit metaIDUParams = null;
		metaIDUParams = new Metaindoorunit();
		try {
			if (!resultSet.isEmpty() && resultSet.size() > 0) {
				logger.debug("resultList in getStatusInfoByFacl method"
						+ resultSet.toString());
				Iterator<?> itr = resultSet.iterator();

				// oduParamsList = new ArrayList<>();

				while (itr.hasNext()) {

					rowData = (Object[]) itr.next();
					// metaIDUParams
					String property = (String) rowData[0];
					String value = (String) rowData[1];
					logger.debug("Returned PF value: '"+ value + "'");
					/*
					 * if(((String) property).contains("B15")){ Double d =
					 * Double.parseDouble(value); if(((String)
					 * property).equals("B15a_u")){
					 * metaIDUParams.setSettabletempLimitUpperAuto
					 * (d.intValue()); }else if(((String)
					 * property).equals("B15a_l")){
					 * metaIDUParams.setSettabletempLimitLowerAuto
					 * (d.intValue()); }else if(((String)
					 * property).equals("B15c_u")){
					 * metaIDUParams.setSettabletempLimitUpperCool
					 * (d.intValue()); }else if(((String)
					 * property).equals("B15c_l")){
					 * metaIDUParams.setSettabletempLimitLowerCool
					 * (d.intValue()); }else if(((String)
					 * property).equals("B15h_u")){
					 * metaIDUParams.setSettabletempLimitUpperHeat
					 * (d.intValue()); }else if(((String)
					 * property).equals("B15h_l")){
					 * metaIDUParams.setSettabletempLimitLowerHeat
					 * (d.intValue()); }else if(((String)
					 * property).equals("B15d_u")){
					 * metaIDUParams.setSettabletempLimitUpperDry(d.intValue());
					 * }else if(((String) property).equals("B15d_l")){
					 * metaIDUParams.setSettabletempLimitLowerDry(d.intValue());
					 * }
					 * 
					 * }
					 */
					if (((String) property).contains("B16")) {
						if (((String) property).equals("B16a")) {
							metaIDUParams.setSettablemodeAuto(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B16h")) {
							metaIDUParams.setSettablemodeHeat(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B16c")) {
							metaIDUParams.setSettablemodeCool(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B16d")) {
							metaIDUParams.setSettablemodeDry(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B16f")) {
							metaIDUParams.setSettablemodeFan(Boolean
									.getBoolean(value));
						}

					}

					if (((String) property).contains("B17")) {
						if (((String) property).equals("B17at")) {
							metaIDUParams.setSettablefanSpeedAuto(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B17l")) {
							metaIDUParams.setSettablefanSpeedLow(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B17mn")) {
							metaIDUParams.setSettablefanSpeedManual(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B17m")) {
							metaIDUParams.setSettablefanSpeedMedium(Boolean
									.getBoolean(value));
						} else if (((String) property).equals("B17h")) {
							metaIDUParams.setSettablefanSpeedHigh(Boolean
									.getBoolean(value));
						}

					}
					/*
					 * if(((String) property).contains("B2")){ if(((String)
					 * property).equals("B20_f")){ Double d =
					 * Double.parseDouble(value);
					 * metaIDUParams.setFixedoperationFanMode(d.intValue());
					 * }else if(((String) property).equals("B20_h")){ Double d =
					 * Double.parseDouble(value);
					 * metaIDUParams.setFixedoperationHeatMode(d.intValue());
					 * }else if(((String) property).equals("B20_c")){ Double d =
					 * Double.parseDouble(value);
					 * metaIDUParams.setFixedoperationCoolMode(d.intValue());
					 * }else if(((String) property).equals("B20_d")){ Double d =
					 * Double.parseDouble(value);
					 * metaIDUParams.setFixedoperationDryMode(d.intValue());
					 * }else if(((String) property).equals("B21")){
					 * metaIDUParams
					 * .setSettableauto(Boolean.parseBoolean(value));
					 * 
					 * }else if(((String) property).equals("B22")){
					 * metaIDUParams
					 * .setSettableEnergysaving(Boolean.parseBoolean(value));
					 * }else if(((String) property).equals("B23")){
					 * if(value!=null){
					 * metaIDUParams.setSettableflap(Boolean.getBoolean(value));
					 * }else if(value == null){
					 * metaIDUParams.setSettableflap(false); }
					 * 
					 * }else if(((String) property).equals("B24")){
					 * if(value!=null){
					 * metaIDUParams.setSettableswing(Boolean.getBoolean
					 * (value)); }else if(value == null){
					 * metaIDUParams.setSettableswing(false); } } }
					 */
					if (((String) property).equals("B21")) {
						metaIDUParams.setSettableauto(Boolean
								.parseBoolean(value));

					}
					if (((String) property).equals("C5")) {
						// metaIDUParams.setIs3waySystem(Boolean.getBoolean(value));
					}

					Adapter ca = new Adapter();
					ca.setId((long) adapter_id);

					// System.out.println(site_id + " WITH " + new
					// Integer(adapter_id).toString() +
					// ca.getCompany().getId().toString());
					List<OutdoorUintPf> jsonOdu = TopologyGroupingService
							.RetrieveTopolgyODU(site_id,
									new Integer(adapter_id).toString(),
									company_id);

					List<IndoorUnitPlatForm> jsonIdu = TopologyGroupingService
							.RetrieveTopolgyIDU(site_id,
									new Integer(adapter_id).toString(),
									company_id);

					for (IndoorUnitPlatForm idu : jsonIdu) {
						logger.debug("IDU central address "
								+ idu.getCentralAddress());
						if (facl_id.equalsIgnoreCase(idu.getFacilityId())) {
							logger.debug("IDU central address "
									+ idu.getCentralAddress());
							metaIDUParams.setCentralControlAddress(idu
									.getCentralAddress());
						}
					}

					for (OutdoorUintPf odu : jsonOdu) {

						logger.debug("ODU 3way " + odu.isWay());
						if (ooid.equalsIgnoreCase(odu.getFacilityId())) {
							logger.debug("ODU 3way " + odu.isWay());
							metaIDUParams.setIs3waySystem(odu.isWay());
						}
					}

					// logger.debug("Company Id " + ca.getCompany().getId());

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metaIDUParams;
	}

	@Override
	public long update_metaindoorunits(Metaindoorunit metaIDUParams) {
		// String sqlQuery = "";
		/*
		 * final StringBuffer SQL_INSERT_META = new StringBuffer(
		 * "INSERT INTO metaindoorunits(creationdate, settabletemp_limit_upper_auto, settabletemp_limit_lower_auto, settabletemp_limit_upper_cool, settabletemp_limit_lower_cool, "
		 * ) .append(
		 * "settabletemp_limit_upper_heat, settabletemp_limit_lower_heat, settabletemp_limit_upper_dry, settabletemp_limit_lower_dry, "
		 * ) .append(
		 * "fixedoperation_mode, settablefan_speed_auto, settablefan_speed_low, settablefan_speed_medium, settablefan_speed_high, "
		 * ) .append(
		 * "settableflap, settableswing, is3way_system, settablemode_auto, settablemode_cool, settablemode_heat, settablemode_dry, settablemode_fan) "
		 * ) .append(
		 * "VALUES(CURRENT_TIMESTAMP, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %d, %d, %d, %d, %d, "
		 * ) .append("%d, %d, %d, %d, %d, %d, %d, %d); RETURNING id;"); String
		 * sqlQuery = SQL_INSERT_META.toString(); sqlQuery =
		 * String.format(sqlQuery,
		 * metaIDUParams.getSettabletempLimitUpperAuto(),
		 * metaIDUParams.getSettabletempLimitLowerAuto(),
		 * metaIDUParams.getSettabletempLimitUpperCool(),
		 * metaIDUParams.getSettabletempLimitLowerCool(),
		 * metaIDUParams.getSettabletempLimitUpperHeat(),
		 * metaIDUParams.getSettabletempLimitLowerHeat(),
		 * metaIDUParams.getSettabletempLimitUpperDry(),
		 * metaIDUParams.getSettabletempLimitLowerDry(),
		 * metaIDUParams.getFixedoperationMode(),
		 * metaIDUParams.getSettablefanSpeedAuto(),
		 * metaIDUParams.getSettablefanSpeedLow(),
		 * metaIDUParams.getSettablefanSpeedMedium(),
		 * metaIDUParams.getSettablefanSpeedHigh(),
		 * metaIDUParams.getSettableflap(), metaIDUParams.getSettableswing(),
		 * metaIDUParams.getIs3waySystem(), metaIDUParams.getSettablemodeAuto(),
		 * metaIDUParams.getSettablemodeCool(),
		 * metaIDUParams.getSettablemodeHeat(),
		 * metaIDUParams.getSettablemodeDry(),
		 * metaIDUParams.getSettablemodeFan() );
		 */
		// SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);
		// Integer id = (Integer) getCurrentSession().save(metaIDUParams);

		// long id = metaIDUParams.getId();
		/*
		 * logger.debug("SQL query to be executed is : " + query);
		 * 
		 * try { return query.list(); } catch (HibernateException hbExp) {
		 * logger.error(String.format(
		 * "Error occured while executing query without Scalar" +
		 * " mapping - %s ", query.getQueryString()), hbExp); }
		 */

		metaIDUParams.setCreationdate(new Date());
		/* Java Reflection or BeanInfo */

		metaIndoorUnitsService.addMetaIndoorUnits(metaIDUParams);
		return metaIDUParams.getId();

	}

	@Override
	public boolean update_indoorunits_meta_by_facl(String facl_id, long id) {

		String query = String.format(SQL_SET_META_BY_FACL.toString(), id,
				facl_id);
		// executing query
		/*
		 * List<?> resultSet = sqlDao.executeSQLSelect(query);
		 * logger.debug("resultList in update_indooorunit_meta method" +
		 * resultSet.toString()); Object[] rowData = null;
		 * 
		 * if (resultSet != null && resultSet.size() > 0) {
		 * 
		 * Iterator<?> itr = resultSet.iterator();
		 * 
		 * //oduParamsList = new ArrayList<>();
		 * 
		 * while (itr.hasNext()) {
		 * 
		 * rowData = (Object[]) itr.next(); //metaIDUParams //String property =
		 * (String) rowData[0]; //String value = (String) rowData[1];
		 * 
		 * 
		 * //logger.debug((String) rowData[0]);
		 * 
		 * }//while
		 * 
		 * logger.debug("RS > 1, update successful"); }
		 */
		int rs = sqlDao.executeSQLUpdateQuery(query);

		return (rs == 1) ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUList(Long[]
	 * id)
	 */
	@Override
	public Set<CAConfigVO> getCASet(ACConfigRequest acConfigRequest)
			throws HibernateException {
		// String address = "47";
		// String sqlQuery =
		// String.format(SQL_GET_ODU_PARAMETERLIST1.toString());
		logger.info("SQL");
		// executing query
		// LinkedHashMap<String, Type> scalarMapping = scalarMappingODU();
		String sqlQuery = String.format(SQL_GET_CA_PARAMETERLIST1.toString(),
				acConfigRequest.getId());
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(DaoConstants.ID, acConfigRequest.getId());
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, parameter);

		Object[] rowData = null;
		Set<CAConfigVO> caList = null;
		CAConfigVO caVO = null;

		if (!resultList.isEmpty() && resultList.size() > 0) {
			logger.info("resultlist in getCAListMAC method"
					+ resultList.toString());
			Iterator<?> itr = resultList.iterator();

			caList = new LinkedHashSet<CAConfigVO>();
			// param.facilityIds = new ArrayList<String>();
			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();
				caVO = new CAConfigVO();
				// caVO.setOduID((BigInteger) rowData[0]);
				// caVO.setOduName((String) rowData[1]);
				caVO.setCompany_name((String) rowData[0]);
				caVO.setSite_name((String) rowData[1]);
				caVO.setAddress((String) rowData[2]);
				caVO.setGroup_id((BigInteger) rowData[3]);
				caVO.setAlarm_code((String) rowData[5]);
				//add by shanf 
				caVO.setDeviceModel((String) rowData[7]);
				String arr = (String) rowData[4];
				String[] pmList = arr.split(",");
				String[] pm;
				String[] type = new String[] { "None", "Electricity", "Gas",
						"Power Generation", "Ice", "Water" };
				int selection = 0;
				for (int i = 0; i < pmList.length; i++) {

					pm = pmList[i].split(":");
					for (int j = 0; j < pm.length; j++) {

						if (pm.length > 1) {
							selection = Integer.parseInt(pm[1]);

						}

						if (i == 0) {
							caVO.setMov1pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov1pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov1pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov1pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						} else if (i == 1) {
							caVO.setMov2pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov2pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov2pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov2pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						} else if (i == 2) {
							caVO.setMov4pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov4pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov4pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov4pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						}

					}
				}

				caList.add(caVO);
			}

			// caVO = new CAConfigVO();
			// caVO.setAddress("test");
			// caList.add(caVO);

		}

		// for (CAConfigVO ca : caList) {}

		return caList;
	}

	
	/* Current: Get CA List in AC Config
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUList(Long[]
	 * id)
	 */
	@Override
	public List<CAConfigVO> getCAList(ACConfigRequest acConfigRequest) throws HibernateException {
		// String address = "47";
		
		logger.info("SQL");
		// executing query & modified by ravi to include extra parameter
		String sqlQuery = String.format(SQL_GET_ODU_PARAMETERLIST2.toString(),
				acConfigRequest.getId(), acConfigRequest.getId());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery);

		Object[] rowData = null;
		List<CAConfigVO> caList = null;
		CAConfigVO caVO = null;

		if (!resultList.isEmpty() && resultList.size() > 0) {
			logger.info("resultlist in getCAList method"
					+ resultList.toString());
			Iterator<?> itr = resultList.iterator();

			caList = new ArrayList<>();
			// param.facilityIds = new ArrayList<String>();
			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();
				caVO = new CAConfigVO();
				// caVO.setOduID((BigInteger) rowData[0]);
				// caVO.setOduName((String) rowData[1]);
				caVO.setCompany_name((String) rowData[0]);
				caVO.setSite_name((String) rowData[1]);
				caVO.setAddress((String) rowData[2]);
				caVO.setGroup_id((BigInteger) rowData[3]);
				caVO.setId((BigInteger) rowData[6]);
				caVO.setAlarm_code((String) rowData[5]);
				//add by shanf
				caVO.setDeviceModel((String) rowData[7]);
				String arr = (String) rowData[4];
				String[] pmList = arr.split(",");
				String[] pm;
				String[] type = new String[] { "None", "Electricity", "Gas",
						"Power Generation", "Ice", "Water" };
				int selection = 0;
				for (int i = 0; i < pmList.length; i++) {

					pm = pmList[i].split(":");
					for (int j = 0; j < pm.length; j++) {

						if (pm.length > 1) {
							selection = Integer.parseInt(pm[1]);

						}

						if (i == 0) {
							caVO.setMov1pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov1pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov1pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov1pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						} else if (i == 1) {
							caVO.setMov2pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov2pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov2pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov2pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						} else if (i == 2) {
							caVO.setMov4pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov4pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov4pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov4pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						}

					}
				}

				/*
				 * caVO.setMov1pulse((BigInteger)rowData[3]);
				 * caVO.setMov2pulse((BigInteger)rowData[4]);
				 * caVO.setMov4pulse((BigInteger)rowData[5]);
				 * caVO.setMov1pulse_factor("1");
				 * caVO.setMov1pulse_id(BigInteger.valueOf(1));
				 * caVO.setMov1pulse_type("Power");
				 * caVO.setMov2pulse_factor("1");
				 * caVO.setMov2pulse_id(BigInteger.valueOf(1));
				 * caVO.setMov2pulse_type("Power");
				 * caVO.setMov4pulse_factor("1");
				 * caVO.setMov4pulse_id(BigInteger.valueOf(1));
				 * caVO.setMov4pulse_type("Power");
				 * 
				 * caVO.setAlarm_code("");
				 */

				caList.add(caVO);
			}

			// caVO = new CAConfigVO();
			// caVO.setAddress("test");
			// caList.add(caVO);

		}
		// for (CAConfigVO ca : caList) {}

		return caList;
	}

	/* Current in CA page
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUList(Long[]
	 * id)
	 */
	@Override
	public List<CAConfigVO> getCAListByMac(ACConfigRequest acConfigRequest)
			throws HibernateException {
		// String address = "47";
		// String sqlQuery =
		// String.format(SQL_GET_ODU_PARAMETERLIST1.toString());
		logger.info("SQL");
		// executing query
		// LinkedHashMap<String, Type> scalarMapping = scalarMappingODU();
		String sqlQuery = String.format(SQL_GET_ODU_PARAMETERLIST1.toString(),
				acConfigRequest.getIdType());

		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery);

		Object[] rowData = null;
		List<CAConfigVO> caList = null;
		CAConfigVO caVO = null;

		if (!resultList.isEmpty() && resultList.size() > 0) {
			logger.info("resultlist in getCAListMAC method"
					+ resultList.toString());
			Iterator<?> itr = resultList.iterator();

			caList = new ArrayList<>();
			// param.facilityIds = new ArrayList<String>();
			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();
				caVO = new CAConfigVO();
				// caVO.setOduID((BigInteger) rowData[0]);
				// caVO.setOduName((String) rowData[1]);
				caVO.setCompany_name((String) rowData[0]);
				caVO.setSite_name((String) rowData[1]);
				caVO.setAddress((String) rowData[2]);
				caVO.setGroup_id((BigInteger) rowData[3]);
				caVO.setId((BigInteger) rowData[6]);
				caVO.setAlarm_code((String) rowData[5]);
				String arr = (String) rowData[4];
				String[] pmList = arr.split(",");
				String[] pm;
				String[] type = new String[] { "None", "Electricity", "Gas",
						"Power Generation", "Ice", "Water" };
				int selection = 0;
				for (int i = 0; i < pmList.length; i++) {

					pm = pmList[i].split(":");
					for (int j = 0; j < pm.length; j++) {

						if (pm.length > 1) {
							selection = Integer.parseInt(pm[1]);

						}

						if (i == 0) {
							caVO.setMov1pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov1pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov1pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov1pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						} else if (i == 1) {
							caVO.setMov2pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov2pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov2pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov2pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						} else if (i == 2) {
							caVO.setMov4pulse_id(pm.length > 0 ? pm[0] : "-");
							caVO.setMov4pulse_type(selection > 0 ? type[selection]
									: "-");
							caVO.setMov4pulse((pm.length > 2 && !pm[2]
									.equals("")) ? pm[2] : "-");
							caVO.setMov4pulse_factor(pm.length > 3 ? pm[3]
									: "-");

						}

					}
				}

				caList.add(caVO);
			}

			// caVO = new CAConfigVO();
			// caVO.setAddress("test");
			// caList.add(caVO);

		}

		// for (CAConfigVO ca : caList) {}

		return caList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUList(Long[]
	 * id)
	 */
	@Override
	public Set<ACConfigODUVO> getODUListByMac(ACConfigRequest acConfigRequest)
			throws HibernateException {

		String sqlQuery = String.format(
				SQL_GET_ACCONFIG_DETAILS_ODU_CA1.toString(),
				acConfigRequest.getIdType());

		logger.debug("Calling the AC Config details for outdoor units");

		Set<ACConfigODUVO> acODUList = new LinkedHashSet<ACConfigODUVO>();
		ACConfigODUVO acODUObj;
		LinkedHashMap<String, Type> scalarMapping = scalarMappingODU();
		Map<String, Object> parameter = new HashMap<String, Object>();

		// String s = String.format(SQL_GET_ODU_UNITS_CA.toString(),
		// acConfigRequest.getIdType());
		// String query =
		// String.format(SQL_GET_ACCONFIG_DETAILS_ODU_CA.toString(),
		// acConfigRequest.getIdType());
		// parameter.put(DaoConstants.ID, acConfigRequest.getIdType());

		try {
			// List<?> result =
			// sqlDao.executeSQLSelect(SQL_GET_ACCONFIG_DETAILS_ODU_CA.toString());
			// sqlDao.executeSQLSelect(query, scalarMapping,parameter);
			List<?> result = sqlDao.executeSQLSelect(sqlQuery);
			Long i = 0l;
			if (!result.isEmpty() && result.size() > 0) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					acODUObj = new ACConfigODUVO();

					rowData = (Object[]) itr.next();
					acODUObj.setOduId((Long) rowData[0]);
					acODUObj.setSite(rowData[1] == null ? BizConstants.HYPHEN
							: (String) rowData[1]);
					acODUObj.setParentChild(rowData[2] == null ? BizConstants.HYPHEN
							: (String) rowData[2]);
					acODUObj.setOdu_Name(rowData[3] == null ? BizConstants.HYPHEN
							: (String) rowData[3]);
					acODUObj.setUnitAddress(rowData[4] == null ? BizConstants.HYPHEN
							: (String) rowData[4]);
					acODUObj.setParentId(rowData[5] == null ? null
							: (Long) rowData[5]);
					acODUObj.setType(rowData[6] == null ? BizConstants.HYPHEN
							: (String) rowData[6]);
					acODUObj.setMaxlatitude(rowData[7] == null ? BizConstants.NUMBER_00
							: (Double) rowData[7]);
					acODUObj.setMaxlongitude(rowData[8] == null ? BizConstants.NUMBER_00
							: (Double) rowData[8]);
					acODUObj.setMinlatitude(rowData[9] == null ? BizConstants.NUMBER_00
							: (Double) rowData[9]);
					acODUObj.setMinlongitude(rowData[10] == null ? BizConstants.NUMBER_00
							: (Double) rowData[10]);
					acODUObj.setSvg_Location(rowData[11] == null ? BizConstants.HYPHEN
							: (String) rowData[11]);
					acODUObj.setAlarmType(rowData[12] == null ? BizConstants.HYPHEN
							: ((String) rowData[12]).toUpperCase());
					acODUObj.setSLinkAddress(rowData[13] == null ? BizConstants.HYPHEN
							: (String) rowData[13]);
					acODUObj.setAlarmCode(rowData[14] == null ? BizConstants.HYPHEN
							: (String) rowData[14]);
					acODUObj.setOutdoorTemp(rowData[15] == null ? BizConstants.NUMBER_00
							: (Double) rowData[15]);
					if (rowData[6] != null) {
						switch (((String) rowData[6]).toUpperCase()) {
						case BizConstants.ODU_TYPE_VRF:

							acODUObj.setMaintenanceCountDownComp2(rowData[26] == null ? rowData[17] == null ? BizConstants.HYPHEN
									: rowData[17].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[26]) < 0 ? BizConstants.HRS0
											: rowData[26].toString().concat(
													BizConstants.HRS));
							acODUObj.setMaintenanceCountDownComp3(rowData[27] == null ? rowData[18] == null ? BizConstants.HYPHEN
									: rowData[18].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[27]) < 0 ? BizConstants.HRS0
											: rowData[27].toString().concat(
													BizConstants.HRS));
							acODUObj.setMaintenanceCountDownComp1(rowData[25] == null ? rowData[16] == null ? BizConstants.HYPHEN
									: rowData[16].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[25]) < 0 ? BizConstants.HRS0
											: rowData[25].toString().concat(
													BizConstants.HRS));
							acODUObj.setDemand(rowData[34] == null ? BizConstants.HYPHEN
									: ((String) rowData[34])
											.concat(BizConstants.PERCENT));
							break;
						case BizConstants.ODU_TYPE_PAC:
							acODUObj.setMaintenanceCountDownComp1(rowData[28] == null ? rowData[19] == null ? BizConstants.HYPHEN
									: rowData[19].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[28]) < 0 ? BizConstants.HRS0
											: rowData[28].toString().concat(
													BizConstants.HRS));
							acODUObj.setDemand(rowData[34] == null ? BizConstants.HYPHEN
									: ((String) rowData[34])
											.concat(BizConstants.PERCENT));
							break;
						case BizConstants.ODU_TYPE_GHP:
							acODUObj.setGenerationPower(rowData[22] == null ? BizConstants.HYPHEN
									: ((String) rowData[22])
											.concat(BizConstants.WH));
							acODUObj.setGhpEngineServiceCountDown(rowData[29] == null ? rowData[20] == null ? BizConstants.HYPHEN
									: rowData[20].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[29]) < 0 ? BizConstants.HRS0
											: rowData[29].toString().concat(
													BizConstants.HRS));

							acODUObj.setGhpOilCheckCountDown(rowData[30] == null ? rowData[21] == null ? BizConstants.HYPHEN
									: rowData[21].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[30]) < 0 ? BizConstants.HRS0
											: rowData[30].toString().concat(
													BizConstants.HRS));

							break;

						}
					}

					acODUObj.setSvgId(rowData[23] == null ? null
							: (Long) rowData[23]);
					acODUObj.setSvgDisplayName(rowData[24] == null ? BizConstants.HYPHEN
							: (String) rowData[24]);
					acODUObj.setSitePath(rowData[31] == null ? null
							: (String) rowData[31]);
					acODUObj.setCustomerName(rowData[32] == null ? null
							: (String) rowData[32]);

					acODUObj.setCaStatus(rowData[33] == null ? null
							: (String) rowData[33]);

					acODUList.add(acODUObj);

				}// while

			}

		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while fetching"
					+ " AC Configuration ODU Details for ID %s",
					acConfigRequest), sqlExp);
			logger.error(sqlExp);
		}
		return acODUList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO#getACConfigDetails
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public ACConfigIDUVO getACConfigDetails(ACConfigRequest acConfigRequest) {

		logger.debug("Calling the AC Config details for indoor units");

		Set<ACConfigVO> acConfigVOList = new LinkedHashSet<ACConfigVO>();
		ACConfigVO acConfigObj;
		Set<RefrigerantSVG> refrigerantSVGList = new LinkedHashSet<RefrigerantSVG>();

		LinkedHashMap<String, Type> scalarMapping = scalarMapping();

		Map<String, Object> parameter = new HashMap<String, Object>();
		String query = BizConstants.EMPTY_STRING;

		query = SQL_GET_ACCONFIG_DETAILS_CA.toString();
		parameter.put(DaoConstants.ID, acConfigRequest.getId());

		try {
			List<?> result = sqlDao.executeSQLSelect(query, scalarMapping,
					parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					acConfigObj = new ACConfigVO();

					rowData = (Object[]) itr.next();

					acConfigObj.setIduId((Long) rowData[46]);

					// Check GroupId is null
					acConfigObj
							.setLocation(rowData[54] == null ? BizConstants.HYPHEN
									: rowData[47] == null ? BizConstants.HYPHEN
											: (String) rowData[47]);
					acConfigObj
							.setSite(rowData[48] == null ? BizConstants.HYPHEN
									: (String) rowData[48]);
					acConfigObj
							.setParentChild(rowData[49] == null ? BizConstants.HYPHEN
									: (String) rowData[49]);
					acConfigObj
							.setIdu_Name(rowData[50] == null ? BizConstants.HYPHEN
									: (String) rowData[50]);
					acConfigObj
							.setUnitAddress(rowData[51] == null ? BizConstants.HYPHEN
									: (String) rowData[51]);
					acConfigObj.setParentId(rowData[52] == null ? null
							: (Long) rowData[52]);
					acConfigObj.setLinkODUid(rowData[55] == null ? null
							: (Long) rowData[55]);
					acConfigObj
							.setMaxlatitude(rowData[56] == null ? BizConstants.NUMBER_00
									: (Double) rowData[56]);
					acConfigObj
							.setMaxlongitude(rowData[57] == null ? BizConstants.NUMBER_00
									: (Double) rowData[57]);
					acConfigObj
							.setMinlatitude(rowData[58] == null ? BizConstants.NUMBER_00
									: (Double) rowData[58]);
					acConfigObj
							.setMinlongitude(rowData[59] == null ? BizConstants.NUMBER_00
									: (Double) rowData[59]);
					acConfigObj
							.setSvg_Location(rowData[60] == null ? BizConstants.HYPHEN
									: (String) rowData[60]);
					acConfigObj
							.setLinkODUSVG(rowData[61] == null ? BizConstants.HYPHEN
									: (String) rowData[61]);
					acConfigObj
							.setAlarmCode(rowData[62] == null ? BizConstants.HYPHEN
									: (String) rowData[62]);
					acConfigObj
							.setType(rowData[63] == null ? BizConstants.HYPHEN
									: (String) rowData[63]);
					acConfigObj
							.setAlarmType(rowData[64] == null ? BizConstants.HYPHEN
									: ((String) rowData[64]).toUpperCase());
					acConfigObj
							.setsLinkAddress(rowData[65] == null ? BizConstants.HYPHEN
									: (String) rowData[65]);
					acConfigObj.setSvgId(rowData[44] == null ? null
							: (Long) rowData[44]);
					acConfigObj
							.setSvgDisplayName(rowData[45] == null ? BizConstants.HYPHEN
									: (String) rowData[45]);
					acConfigObj.setProhibit(rowData[51] == null ? 0 : 1);

					acConfigObj.setRc_flag(rowData[66] == null ? null
							: (Short) rowData[66]);

					acConfigObj
							.setProhibitRCPower(rowData[38] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[38]).toString());
					acConfigObj
							.setProhibitRCMode(rowData[39] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[39]).toString());
					acConfigObj
							.setProhibitRCFanSpeed(rowData[40] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[40]).toString());
					acConfigObj
							.setProhibitRCFlapMode(rowData[41] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[41]).toString());
					acConfigObj
							.setProhibitRCTemp(rowData[42] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[42]).toString());
					acConfigObj
							.setProhibitRCEnergySaving(rowData[43] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[43]).toString());
					acConfigObj.setSitePath(rowData[80] == null ? null
							: (String) rowData[80]);
					acConfigObj.setCustomerName(rowData[81] == null ? null
							: (String) rowData[81]);
					acConfigObj.setFilterSign(rowData[82] == null ? null
							: new Integer(rowData[82].toString()));

					// RC Parameter
					DetailsLogsVO detailsObj = setRCParameter(rowData);
					acConfigObj.setPower(detailsObj.getPower());
					acConfigObj.setTemperature(detailsObj.getTemperature());
					acConfigObj.setMode(detailsObj.getMode());
					acConfigObj.setFanSpeed(detailsObj.getFanSpeed());
					acConfigObj.setFlapMode(detailsObj.getFlapMode());
					acConfigObj.setEcoNavi(detailsObj.getEcoNavi());
					acConfigObj.setRoomTemp(detailsObj.getRoomTemp());
					acConfigObj.setEnergy_saving(detailsObj.getEnergy_saving());

					// Refrigerant SVG List only for group
					if (acConfigRequest.getIdType().equals(
							BizConstants.ID_TYPE_GROUP))
						getRefrigerantSVG(refrigerantSVGList, rowData);

					acConfigObj.setCaStatus(rowData[83] == null ? null
							: (String) rowData[83]);

					// If Status is Inactive
					if (rowData[53] == null
							|| ((String) rowData[53])
									.equals(BizConstants.UNIT_STATUS_INACTIVE)) {
						acConfigObj.setPower(BizConstants.HYPHEN);
						acConfigObj.setMode(BizConstants.HYPHEN);
						acConfigObj.setTemperature(BizConstants.HYPHEN);
						acConfigObj.setEcoNavi(BizConstants.HYPHEN);
						acConfigObj.setFanSpeed(BizConstants.HYPHEN);
						acConfigObj.setFlapMode(BizConstants.HYPHEN);
						acConfigObj.setRoomTemp(BizConstants.HYPHEN);
						acConfigObj.setEnergy_saving(BizConstants.HYPHEN);
					}
					acConfigVOList.add(acConfigObj);
				}

			}

		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while fetching"
					+ " AC Configuration Details for ID %d", acConfigRequest),
					sqlExp);

		}

		ACConfigIDUVO iduVO = new ACConfigIDUVO();
		iduVO.setIduList(acConfigVOList);
		if (acConfigRequest.getIdType().equals(BizConstants.ID_TYPE_GROUP))
			iduVO.setRefrigerantList(refrigerantSVGList);
		return iduVO;
	}

	/**
	 * Set Scalar Mapping.
	 * 
	 * @return
	 */
	private LinkedHashMap<String, Type> scalarMapping() {
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

		scalarMapping.put(DaoConstants.MAP_PROHIBITION_STATUS,
				StandardBasicTypes.INTEGER); // 38
		scalarMapping.put(DaoConstants.MAP_PROHIBITION_MODE,
				StandardBasicTypes.INTEGER); // 39
		scalarMapping.put(DaoConstants.MAP_PROHIBITION_SPEED,
				StandardBasicTypes.INTEGER); // 40
		scalarMapping.put(DaoConstants.MAP_PROHIBITION_DIRECTION,
				StandardBasicTypes.INTEGER); // 41
		scalarMapping.put(DaoConstants.MAP_PROHIBITION_TEMP,
				StandardBasicTypes.INTEGER); // 42
		scalarMapping.put(DaoConstants.MAP_PROHIBITION_ENERGY,
				StandardBasicTypes.INTEGER); // 43
		scalarMapping.put(DaoConstants.MAP_SVGID, StandardBasicTypes.LONG); // 44
		scalarMapping.put(DaoConstants.MAP_SVGNAME, StandardBasicTypes.STRING); // 45
		scalarMapping.put(DaoConstants.ID, StandardBasicTypes.LONG); // 46
		scalarMapping.put(DaoConstants.MAP_CONTROL_GROUP,
				StandardBasicTypes.STRING); // 47
		scalarMapping.put(DaoConstants.MAP_SITE_GROUP,
				StandardBasicTypes.STRING); // 48
		scalarMapping.put(DaoConstants.MAP_PARENT_CHILD,
				StandardBasicTypes.STRING); // 49
		scalarMapping.put(DaoConstants.MAP_NAME, StandardBasicTypes.STRING); // 50
		scalarMapping.put(DaoConstants.MAP_CENTRAL_ADDRESS,
				StandardBasicTypes.STRING); // 51
		scalarMapping.put(DaoConstants.MAP_PARENT_ID, StandardBasicTypes.LONG); // 52
		scalarMapping.put(DaoConstants.MAP_STATUS, StandardBasicTypes.STRING); // 53
		scalarMapping.put(DaoConstants.MAP_GROUPID, StandardBasicTypes.LONG); // 54
		scalarMapping.put(DaoConstants.MAP_ODUID, StandardBasicTypes.LONG); // 55
		scalarMapping.put(DaoConstants.MAP_SVGMAXLATITUDE,
				StandardBasicTypes.DOUBLE); // 56
		scalarMapping.put(DaoConstants.MAP_SVGMAXLONGITUDE,
				StandardBasicTypes.DOUBLE); // 57
		scalarMapping.put(DaoConstants.MAP_SVGMINLATITUDE,
				StandardBasicTypes.DOUBLE); // 58
		scalarMapping.put(DaoConstants.MAP_SVGMINLONGITUDE,
				StandardBasicTypes.DOUBLE); // 59
		scalarMapping.put(DaoConstants.MAP_IDU_SVG_PATH,
				StandardBasicTypes.STRING); // 60
		scalarMapping.put(DaoConstants.MAP_ODU_SVG_PATH,
				StandardBasicTypes.STRING); // 61
		scalarMapping.put(DaoConstants.MAP_ALARM_CODE,
				StandardBasicTypes.STRING); // 62
		scalarMapping.put(DaoConstants.MAP_TYPE, StandardBasicTypes.STRING); // 63
		scalarMapping.put(DaoConstants.MAP_SEVERITY, StandardBasicTypes.STRING); // 64
		scalarMapping.put(DaoConstants.MAP_SLINKADD, StandardBasicTypes.STRING); // 65
		scalarMapping.put(DaoConstants.MAP_RCFLAG, StandardBasicTypes.SHORT); // 66
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
		scalarMapping.put(DaoConstants.MAP_SITEPATH, StandardBasicTypes.STRING); // 80
		scalarMapping.put(DaoConstants.MAP_COMPANYNAME,
				StandardBasicTypes.STRING); // 81
		scalarMapping.put(DaoConstants.MAP_A17A, StandardBasicTypes.STRING); // 82
		scalarMapping.put(DaoConstants.MAP_CASTATUS, StandardBasicTypes.STRING); // 83

		return scalarMapping;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO#getRefrigerantSVG
	 * (java.util.Set, java.lang.Object[])
	 */
	@Override
	public void getRefrigerantSVG(Set<RefrigerantSVG> refrigerantSVGList,
			Object[] rowData) {
		RefrigerantSVG refrigerantSVGObj;

		// Refrigerant
		if (rowData[0] != null) {
			refrigerantSVGObj = new RefrigerantSVG();
			refrigerantSVGObj
					.setMaxlatitude(rowData[1] == null ? BizConstants.NUMBER_00
							: (Double) rowData[1]);
			refrigerantSVGObj
					.setMaxlongitude(rowData[2] == null ? BizConstants.NUMBER_00
							: (Double) rowData[2]);
			refrigerantSVGObj
					.setMinlatitude(rowData[3] == null ? BizConstants.NUMBER_00
							: (Double) rowData[3]);
			refrigerantSVGObj
					.setMinlongitude(rowData[4] == null ? BizConstants.NUMBER_00
							: (Double) rowData[4]);
			refrigerantSVGObj.setId((Long) rowData[0]);
			refrigerantSVGObj.setLinkedSvgId(rowData[15] == null ? null
					: (Long) rowData[15]);
			refrigerantSVGList.add(refrigerantSVGObj);
		}

		if (rowData[5] != null) {
			refrigerantSVGObj = new RefrigerantSVG();
			refrigerantSVGObj
					.setMaxlatitude(rowData[6] == null ? BizConstants.NUMBER_00
							: (Double) rowData[6]);
			refrigerantSVGObj
					.setMaxlongitude(rowData[7] == null ? BizConstants.NUMBER_00
							: (Double) rowData[7]);
			refrigerantSVGObj
					.setMinlatitude(rowData[8] == null ? BizConstants.NUMBER_00
							: (Double) rowData[8]);
			refrigerantSVGObj
					.setMinlongitude(rowData[9] == null ? BizConstants.NUMBER_00
							: (Double) rowData[9]);
			refrigerantSVGObj.setId((Long) rowData[5]);
			refrigerantSVGObj.setLinkedSvgId(rowData[15] == null ? null
					: (Long) rowData[15]);
			refrigerantSVGList.add(refrigerantSVGObj);
		}

		if (rowData[10] != null) {
			refrigerantSVGObj = new RefrigerantSVG();
			refrigerantSVGObj
					.setMaxlatitude(rowData[11] == null ? BizConstants.NUMBER_00
							: (Double) rowData[11]);
			refrigerantSVGObj
					.setMaxlongitude(rowData[12] == null ? BizConstants.NUMBER_00
							: (Double) rowData[12]);
			refrigerantSVGObj
					.setMinlatitude(rowData[13] == null ? BizConstants.NUMBER_00
							: (Double) rowData[13]);
			refrigerantSVGObj
					.setMinlongitude(rowData[14] == null ? BizConstants.NUMBER_00
							: (Double) rowData[14]);
			refrigerantSVGObj.setId((Long) rowData[10]);
			refrigerantSVGObj.setLinkedSvgId(rowData[15] == null ? null
					: (Long) rowData[15]);
			refrigerantSVGList.add(refrigerantSVGObj);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO#setRCParameter
	 * (java.lang.Object[])
	 */
	@Override
	public DetailsLogsVO setRCParameter(Object[] rowData) {
		DetailsLogsVO detailsObj = new DetailsLogsVO();
		//change by shanf, IDU detail info always display no matter powerstatus is on or off
		if (rowData[16] != null ) {
			if ((Boolean) rowData[16]) {
				detailsObj.setPower(DaoConstants.PowerStatus.valueOf(1).toString());
			}else {
				detailsObj.setPower(DaoConstants.PowerStatus.valueOf(0).toString());
			}
			

			detailsObj
					.setRoomTemp(rowData[36] == null ? BizConstants.HYPHEN
							: (CommonUtil
									.getFormattedValueUpToTwoDecimal((Double) rowData[36]))
									.toString());

			if (rowData[17] == null || rowData[67] == null) {
				detailsObj.setMode(BizConstants.HYPHEN);
				detailsObj.setTemperature(BizConstants.HYPHEN);
				detailsObj.setFanSpeed(BizConstants.HYPHEN);
				detailsObj.setFlapMode(BizConstants.HYPHEN);
			} else {

				String mode = PropValueAlgo.computeMode((String) rowData[17],
						(String) rowData[67]);
				if (mode == null) {
					detailsObj.setMode(BizConstants.HYPHEN);
					detailsObj.setTemperature(BizConstants.HYPHEN);
					detailsObj.setFanSpeed(BizConstants.HYPHEN);
					detailsObj.setFlapMode(BizConstants.HYPHEN);
				} else {
					detailsObj.setMode(mode);

					switch (mode) {

					case Constants.AUTO_COOL:
					case Constants.AUTO_HEAT:
					case Constants.AUTO_UNDECIDED:
						detailsObj
								.setTemperature(rowData[21] == null ? BizConstants.HYPHEN
										: (CommonUtil
												.getFormattedValueUpToTwoDecimal((Double) rowData[21]))
												.toString());

						if (rowData[22] == null) {
							detailsObj.setFanSpeed(BizConstants.HYPHEN);
						} else {
							String fanSpeed = PropValueAlgo.computeFanSpeed(
									(String) rowData[22],
									rowData[69] == null ? null
											: (String) rowData[69]);
							detailsObj
									.setFanSpeed(fanSpeed == null ? BizConstants.HYPHEN
											: fanSpeed);
						}
						if (rowData[23] == null) {
							detailsObj.setFlapMode(BizConstants.HYPHEN);
						} else {
							String flapMode = PropValueAlgo
									.computeFlapPosition((String) rowData[23],
											rowData[75] == null ? null
													: (String) rowData[75]);
							detailsObj
									.setFlapMode(flapMode == null ? BizConstants.HYPHEN
											: flapMode);

						}

						break;
					case Constants.HEAT:
						detailsObj
								.setTemperature(rowData[24] == null ? BizConstants.HYPHEN
										: (CommonUtil
												.getFormattedValueUpToTwoDecimal((Double) rowData[24]))
												.toString());
						if (rowData[25] == null) {
							detailsObj.setFanSpeed(BizConstants.HYPHEN);
						} else {
							String fanSpeed = PropValueAlgo.computeFanSpeed(
									(String) rowData[25],
									rowData[72] == null ? null
											: (String) rowData[72]);
							detailsObj
									.setFanSpeed(fanSpeed == null ? BizConstants.HYPHEN
											: fanSpeed);
						}
						if (rowData[26] == null) {
							detailsObj.setFlapMode(BizConstants.HYPHEN);
						} else {
							String flapMode = PropValueAlgo
									.computeFlapPosition((String) rowData[26],
											rowData[78] == null ? null
													: (String) rowData[78]);
							detailsObj
									.setFlapMode(flapMode == null ? BizConstants.HYPHEN
											: flapMode);

						}

						break;
					case Constants.FAN:
						detailsObj.setTemperature(BizConstants.HYPHEN);
						if (rowData[33] == null) {
							detailsObj.setFanSpeed(BizConstants.HYPHEN);
						} else {
							String fanSpeed = PropValueAlgo.computeFanSpeed(
									(String) rowData[33],
									rowData[73] == null ? null
											: (String) rowData[73]);
							detailsObj
									.setFanSpeed(fanSpeed == null ? BizConstants.HYPHEN
											: fanSpeed);
						}
						if (rowData[34] == null) {
							detailsObj.setFlapMode(BizConstants.HYPHEN);
						} else {
							String flapMode = PropValueAlgo
									.computeFlapPosition((String) rowData[34],
											rowData[79] == null ? null
													: (String) rowData[79]);
							detailsObj
									.setFlapMode(flapMode == null ? BizConstants.HYPHEN
											: flapMode);

						}
						break;
					case Constants.COOL:
						detailsObj
								.setTemperature(rowData[27] == null ? BizConstants.HYPHEN
										: (CommonUtil
												.getFormattedValueUpToTwoDecimal((Double) rowData[27]))
												.toString());
						if (rowData[28] == null) {
							detailsObj.setFanSpeed(BizConstants.HYPHEN);
						} else {
							String fanSpeed = PropValueAlgo.computeFanSpeed(
									(String) rowData[28],
									rowData[70] == null ? null
											: (String) rowData[70]);
							detailsObj
									.setFanSpeed(fanSpeed == null ? BizConstants.HYPHEN
											: fanSpeed);
						}
						if (rowData[29] == null) {
							detailsObj.setFlapMode(BizConstants.HYPHEN);
						} else {
							String flapMode = PropValueAlgo
									.computeFlapPosition((String) rowData[29],
											rowData[76] == null ? null
													: (String) rowData[76]);
							detailsObj
									.setFlapMode(flapMode == null ? BizConstants.HYPHEN
											: flapMode);

						}

						break;
					case Constants.DRY:
						detailsObj
								.setTemperature(rowData[30] == null ? BizConstants.HYPHEN
										: (CommonUtil
												.getFormattedValueUpToTwoDecimal((Double) rowData[30]))
												.toString());
						if (rowData[31] == null) {
							detailsObj.setFanSpeed(BizConstants.HYPHEN);
						} else {
							String fanSpeed = PropValueAlgo.computeFanSpeed(
									(String) rowData[31],
									rowData[71] == null ? null
											: (String) rowData[71]);
							detailsObj
									.setFanSpeed(fanSpeed == null ? BizConstants.HYPHEN
											: fanSpeed);
						}
						if (rowData[32] == null) {
							detailsObj.setFlapMode(BizConstants.HYPHEN);
						} else {
							String flapMode = PropValueAlgo
									.computeFlapPosition((String) rowData[32],
											rowData[77] == null ? null
													: (String) rowData[77]);
							detailsObj
									.setFlapMode(flapMode == null ? BizConstants.HYPHEN
											: flapMode);

						}

						break;
					case Constants.UNDECIDED:
						detailsObj
								.setTemperature(rowData[18] == null ? BizConstants.HYPHEN
										: (CommonUtil
												.getFormattedValueUpToTwoDecimal((Double) rowData[18]))
												.toString());
						if (rowData[19] == null) {
							detailsObj.setFanSpeed(BizConstants.HYPHEN);
						} else {
							String fanSpeed = PropValueAlgo.computeFanSpeed(
									(String) rowData[19],
									rowData[68] == null ? null
											: (String) rowData[68]);
							detailsObj
									.setFanSpeed(fanSpeed == null ? BizConstants.HYPHEN
											: fanSpeed);
						}
						if (rowData[20] == null) {
							detailsObj.setFlapMode(BizConstants.HYPHEN);
						} else {
							String flapMode = PropValueAlgo
									.computeFlapPosition((String) rowData[20],
											rowData[74] == null ? null
													: (String) rowData[74]);
							detailsObj
									.setFlapMode(flapMode == null ? BizConstants.HYPHEN
											: flapMode);

						}

						break;

					}
				}
			}

			detailsObj.setEcoNavi(rowData[35] == null ? BizConstants.HYPHEN
					: DaoConstants.EcoNavi.valueOf((Integer) rowData[35])
							.toString());
			detailsObj
					.setEnergy_saving(rowData[37] == null ? BizConstants.HYPHEN
							: DaoConstants.PowerStatus.valueOf(
									(Integer) rowData[37]).toString());

		} else {
			detailsObj.setPower(DaoConstants.PowerStatus.valueOf(0).toString());
			//change by shanf, if idu off, get temperature from A3
//			detailsObj.setTemperature(BizConstants.HYPHEN);
			detailsObj.setTemperature(rowData[18] == null ? BizConstants.HYPHEN
					: (CommonUtil.getFormattedValueUpToTwoDecimal((Double) rowData[18])).toString());
			detailsObj.setMode(BizConstants.HYPHEN);
			detailsObj.setFanSpeed(BizConstants.HYPHEN);
			detailsObj.setFlapMode(BizConstants.HYPHEN);
			detailsObj.setEcoNavi(BizConstants.HYPHEN);
			detailsObj.setRoomTemp(BizConstants.HYPHEN);
			detailsObj.setEnergy_saving(BizConstants.HYPHEN);
		}

		return detailsObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO#
	 * getODUACConfigDetails
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public Set<ACConfigODUVO> getODUACConfigDetails2(
			ACConfigRequest acConfigRequest) {

		logger.debug("Calling the AC Config details for outdoor units");

		Set<ACConfigODUVO> acODUList = new LinkedHashSet<ACConfigODUVO>();
		ACConfigODUVO acODUObj;

		LinkedHashMap<String, Type> scalarMapping = scalarMappingODU();

		Map<String, Object> parameter = new HashMap<String, Object>();

		String query = String.format(
				SQL_GET_ACCONFIG_DETAILS_ODU_CA.toString(),
				SQL_GET_ODU_UNITS_CA.toString());
		parameter.put(DaoConstants.ID, acConfigRequest.getIdType());

		try {
			List<?> result = sqlDao.executeSQLSelect(query, scalarMapping,
					parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					acODUObj = new ACConfigODUVO();

					rowData = (Object[]) itr.next();
					acODUObj.setOduId((Long) rowData[0]);
					acODUObj.setSite(rowData[1] == null ? BizConstants.HYPHEN
							: (String) rowData[1]);
					acODUObj.setParentChild(rowData[2] == null ? BizConstants.HYPHEN
							: (String) rowData[2]);
					acODUObj.setOdu_Name(rowData[3] == null ? BizConstants.HYPHEN
							: (String) rowData[3]);
					acODUObj.setUnitAddress(rowData[4] == null ? BizConstants.HYPHEN
							: (String) rowData[4]);
					acODUObj.setParentId(rowData[5] == null ? null
							: (Long) rowData[5]);
					acODUObj.setType(rowData[6] == null ? BizConstants.HYPHEN
							: (String) rowData[6]);
					acODUObj.setMaxlatitude(rowData[7] == null ? BizConstants.NUMBER_00
							: (Double) rowData[7]);
					acODUObj.setMaxlongitude(rowData[8] == null ? BizConstants.NUMBER_00
							: (Double) rowData[8]);
					acODUObj.setMinlatitude(rowData[9] == null ? BizConstants.NUMBER_00
							: (Double) rowData[9]);
					acODUObj.setMinlongitude(rowData[10] == null ? BizConstants.NUMBER_00
							: (Double) rowData[10]);
					acODUObj.setSvg_Location(rowData[11] == null ? BizConstants.HYPHEN
							: (String) rowData[11]);
					acODUObj.setAlarmType(rowData[12] == null ? BizConstants.HYPHEN
							: ((String) rowData[12]).toUpperCase());
					acODUObj.setSLinkAddress(rowData[13] == null ? BizConstants.HYPHEN
							: (String) rowData[13]);
					acODUObj.setAlarmCode(rowData[14] == null ? BizConstants.HYPHEN
							: (String) rowData[14]);
					acODUObj.setOutdoorTemp(rowData[15] == null ? BizConstants.NUMBER_00
							: (Double) rowData[15]);
					if (rowData[6] != null) {
						switch (((String) rowData[6]).toUpperCase()) {
						case BizConstants.ODU_TYPE_VRF:

							acODUObj.setMaintenanceCountDownComp2(rowData[26] == null ? rowData[17] == null ? BizConstants.HYPHEN
									: rowData[17].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[26]) < 0 ? BizConstants.HRS0
											: rowData[26].toString().concat(
													BizConstants.HRS));
							acODUObj.setMaintenanceCountDownComp3(rowData[27] == null ? rowData[18] == null ? BizConstants.HYPHEN
									: rowData[18].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[27]) < 0 ? BizConstants.HRS0
											: rowData[27].toString().concat(
													BizConstants.HRS));
							acODUObj.setMaintenanceCountDownComp1(rowData[25] == null ? rowData[16] == null ? BizConstants.HYPHEN
									: rowData[16].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[25]) < 0 ? BizConstants.HRS0
											: rowData[25].toString().concat(
													BizConstants.HRS));
							acODUObj.setDemand(rowData[34] == null ? BizConstants.HYPHEN
									: ((String) rowData[34])
											.concat(BizConstants.PERCENT));
							break;
						case BizConstants.ODU_TYPE_PAC:
							acODUObj.setMaintenanceCountDownComp1(rowData[28] == null ? rowData[19] == null ? BizConstants.HYPHEN
									: rowData[19].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[28]) < 0 ? BizConstants.HRS0
											: rowData[28].toString().concat(
													BizConstants.HRS));
							acODUObj.setDemand(rowData[34] == null ? BizConstants.HYPHEN
									: ((String) rowData[34])
											.concat(BizConstants.PERCENT));
							break;
						case BizConstants.ODU_TYPE_GHP:
							acODUObj.setGenerationPower(rowData[22] == null ? BizConstants.HYPHEN
									: ((String) rowData[22])
											.concat(BizConstants.WH));
							acODUObj.setGhpEngineServiceCountDown(rowData[29] == null ? rowData[20] == null ? BizConstants.HYPHEN
									: rowData[20].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[29]) < 0 ? BizConstants.HRS0
											: rowData[29].toString().concat(
													BizConstants.HRS));

							acODUObj.setGhpOilCheckCountDown(rowData[30] == null ? rowData[21] == null ? BizConstants.HYPHEN
									: rowData[21].toString().concat(
											BizConstants.HRS)
									: ((Long) rowData[30]) < 0 ? BizConstants.HRS0
											: rowData[30].toString().concat(
													BizConstants.HRS));

							break;

						}
					}

					acODUObj.setSvgId(rowData[23] == null ? null
							: (Long) rowData[23]);
					acODUObj.setSvgDisplayName(rowData[24] == null ? BizConstants.HYPHEN
							: (String) rowData[24]);
					acODUObj.setSitePath(rowData[31] == null ? null
							: (String) rowData[31]);
					acODUObj.setCustomerName(rowData[32] == null ? null
							: (String) rowData[32]);

					acODUObj.setCaStatus(rowData[33] == null ? null
							: (String) rowData[33]);
					acODUList.add(acODUObj);
				}

			}

		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while fetching"
					+ " AC Configuration ODU Details for ID %s",
					acConfigRequest), sqlExp);
			logger.error(sqlExp);
		}
		return acODUList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO#getACConfigDetails
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public ACConfigIDUVO getACConfigDetailsCA(ACConfigRequest acConfigRequest) {

		logger.debug("Calling the AC Config details for indoor units govern by this MAC Address's CA");

		Set<ACConfigVO> acConfigVOList = new LinkedHashSet<ACConfigVO>();
		ACConfigVO acConfigObj;
		Set<RefrigerantSVG> refrigerantSVGList = new LinkedHashSet<RefrigerantSVG>();

		LinkedHashMap<String, Type> scalarMapping = scalarMapping();

		Map<String, Object> parameter = new HashMap<String, Object>();

		String query = SQL_GET_ACCONFIG_DETAILS_IDU_CA.toString();
		parameter.put(DaoConstants.ID, acConfigRequest.getIdType());

		try {
			List<?> result = sqlDao.executeSQLSelect(query, scalarMapping,
					parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					acConfigObj = new ACConfigVO();

					rowData = (Object[]) itr.next();

					acConfigObj.setIduId((Long) rowData[46]);

					// Check GroupId is null
					acConfigObj
							.setLocation(rowData[54] == null ? BizConstants.HYPHEN
									: rowData[47] == null ? BizConstants.HYPHEN
											: (String) rowData[47]);
					acConfigObj
							.setSite(rowData[48] == null ? BizConstants.HYPHEN
									: (String) rowData[48]);
					acConfigObj
							.setParentChild(rowData[49] == null ? BizConstants.HYPHEN
									: (String) rowData[49]);
					acConfigObj
							.setIdu_Name(rowData[50] == null ? BizConstants.HYPHEN
									: (String) rowData[50]);
					acConfigObj
							.setUnitAddress(rowData[51] == null ? BizConstants.HYPHEN
									: (String) rowData[51]);
					acConfigObj.setParentId(rowData[52] == null ? null
							: (Long) rowData[52]);
					acConfigObj.setLinkODUid(rowData[55] == null ? null
							: (Long) rowData[55]);
					acConfigObj
							.setMaxlatitude(rowData[56] == null ? BizConstants.NUMBER_00
									: (Double) rowData[56]);
					acConfigObj
							.setMaxlongitude(rowData[57] == null ? BizConstants.NUMBER_00
									: (Double) rowData[57]);
					acConfigObj
							.setMinlatitude(rowData[58] == null ? BizConstants.NUMBER_00
									: (Double) rowData[58]);
					acConfigObj
							.setMinlongitude(rowData[59] == null ? BizConstants.NUMBER_00
									: (Double) rowData[59]);
					acConfigObj
							.setSvg_Location(rowData[60] == null ? BizConstants.HYPHEN
									: (String) rowData[60]);
					acConfigObj
							.setLinkODUSVG(rowData[61] == null ? BizConstants.HYPHEN
									: (String) rowData[61]);
					acConfigObj
							.setAlarmCode(rowData[62] == null ? BizConstants.HYPHEN
									: (String) rowData[62]);
					acConfigObj
							.setType(rowData[63] == null ? BizConstants.HYPHEN
									: (String) rowData[63]);
					acConfigObj
							.setAlarmType(rowData[64] == null ? BizConstants.HYPHEN
									: ((String) rowData[64]).toUpperCase());
					acConfigObj
							.setsLinkAddress(rowData[65] == null ? BizConstants.HYPHEN
									: (String) rowData[65]);
					acConfigObj.setSvgId(rowData[44] == null ? null
							: (Long) rowData[44]);
					acConfigObj
							.setSvgDisplayName(rowData[45] == null ? BizConstants.HYPHEN
									: (String) rowData[45]);
					acConfigObj.setProhibit(rowData[51] == null ? 0 : 1);

					acConfigObj.setRc_flag(rowData[66] == null ? null
							: (Short) rowData[66]);

					acConfigObj
							.setProhibitRCPower(rowData[38] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[38]).toString());
					acConfigObj
							.setProhibitRCMode(rowData[39] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[39]).toString());
					acConfigObj
							.setProhibitRCFanSpeed(rowData[40] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[40]).toString());
					acConfigObj
							.setProhibitRCFlapMode(rowData[41] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[41]).toString());
					acConfigObj
							.setProhibitRCTemp(rowData[42] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[42]).toString());
					acConfigObj
							.setProhibitRCEnergySaving(rowData[43] == null ? BizConstants.HYPHEN
									: DaoConstants.Prohibited.valueOf(
											(Integer) rowData[43]).toString());
					acConfigObj.setSitePath(rowData[80] == null ? null
							: (String) rowData[80]);
					acConfigObj.setCustomerName(rowData[81] == null ? null
							: (String) rowData[81]);
					acConfigObj.setFilterSign(rowData[82] == null ? null
							: new Integer(rowData[82].toString()));

					// RC Parameter
					DetailsLogsVO detailsObj = setRCParameter(rowData);
					acConfigObj.setPower(detailsObj.getPower());
					acConfigObj.setTemperature(detailsObj.getTemperature());
					acConfigObj.setMode(detailsObj.getMode());
					acConfigObj.setFanSpeed(detailsObj.getFanSpeed());
					acConfigObj.setFlapMode(detailsObj.getFlapMode());
					acConfigObj.setEcoNavi(detailsObj.getEcoNavi());
					acConfigObj.setRoomTemp(detailsObj.getRoomTemp());
					acConfigObj.setEnergy_saving(detailsObj.getEnergy_saving());

					// Refrigerant SVG List only for group
					if (acConfigRequest.getIdType().equals(
							BizConstants.ID_TYPE_GROUP))
						getRefrigerantSVG(refrigerantSVGList, rowData);

					acConfigObj.setCaStatus(rowData[83] == null ? null
							: (String) rowData[83]);

					// If Status is Inactive
					if (rowData[53] == null
							|| ((String) rowData[53])
									.equals(BizConstants.UNIT_STATUS_INACTIVE)) {
						acConfigObj.setPower(BizConstants.HYPHEN);
						acConfigObj.setMode(BizConstants.HYPHEN);
						acConfigObj.setTemperature(BizConstants.HYPHEN);
						acConfigObj.setEcoNavi(BizConstants.HYPHEN);
						acConfigObj.setFanSpeed(BizConstants.HYPHEN);
						acConfigObj.setFlapMode(BizConstants.HYPHEN);
						acConfigObj.setRoomTemp(BizConstants.HYPHEN);
						acConfigObj.setEnergy_saving(BizConstants.HYPHEN);
					}
					acConfigVOList.add(acConfigObj);
				}

			}

		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while fetching"
					+ " AC Configuration Details for ID %s", acConfigRequest),
					sqlExp);

			logger.error(sqlExp);

		}

		ACConfigIDUVO iduVO = new ACConfigIDUVO();
		iduVO.setIduList(acConfigVOList);
		if (acConfigRequest.getIdType().equals(BizConstants.ID_TYPE_GROUP))
			iduVO.setRefrigerantList(refrigerantSVGList);
		return iduVO;
	}

	/**
	 * Set the Scalar Mapping for Outdoor Units.
	 * 
	 * @return
	 */
	private LinkedHashMap<String, Type> scalarMappingODU() {
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(DaoConstants.MAP_ODUID, StandardBasicTypes.LONG); // 0
		scalarMapping.put(DaoConstants.MAP_SITE_GROUP,
				StandardBasicTypes.STRING); // 1
		scalarMapping.put(DaoConstants.MAP_PARENT_CHILD,
				StandardBasicTypes.STRING); // 2
		scalarMapping.put(DaoConstants.MAP_NAME, StandardBasicTypes.STRING); // 3
		scalarMapping.put(DaoConstants.MAP_CENTRAL_ADDRESS,
				StandardBasicTypes.STRING); // 4
		scalarMapping.put(DaoConstants.MAP_PARENT_ID, StandardBasicTypes.LONG); // 5
		scalarMapping.put(DaoConstants.MAP_TYPE, StandardBasicTypes.STRING); // 6
		scalarMapping.put(DaoConstants.MAP_SVGMAXLATITUDE,
				StandardBasicTypes.DOUBLE); // 7
		scalarMapping.put(DaoConstants.MAP_SVGMAXLONGITUDE,
				StandardBasicTypes.DOUBLE); // 8
		scalarMapping.put(DaoConstants.MAP_SVGMINLATITUDE,
				StandardBasicTypes.DOUBLE); // 9
		scalarMapping.put(DaoConstants.MAP_SVGMINLONGITUDE,
				StandardBasicTypes.DOUBLE); // 10
		scalarMapping.put(DaoConstants.MAP_ODU_SVG_PATH,
				StandardBasicTypes.STRING); // 11
		scalarMapping.put(DaoConstants.MAP_SEVERITY, StandardBasicTypes.STRING); // 12
		scalarMapping.put(DaoConstants.MAP_SLINKADD, StandardBasicTypes.STRING); // 13
		scalarMapping.put(DaoConstants.MAP_ALARM_CODE,
				StandardBasicTypes.STRING); // 14
		scalarMapping.put(DaoConstants.MAP_C2, StandardBasicTypes.DOUBLE); // 15
																			// Outdoor
																			// Temp
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_1, StandardBasicTypes.LONG); // 16
																				// CompressorWorkingHrs_1
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_2, StandardBasicTypes.LONG); // 17
																				// CompressorWorkingHrs_2
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_3, StandardBasicTypes.LONG); // 18
																				// CompressorWorkingHrs_3
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_4, StandardBasicTypes.LONG); // 19
																				// PAC
																				// CompressorWorkingHrs
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_5, StandardBasicTypes.LONG); // 20
																				// EngineWorkingHrs
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_6, StandardBasicTypes.LONG); // 21
																				// NOT_IN_USE_EnginOilChange
		scalarMapping.put(DaoConstants.MAP_G44, StandardBasicTypes.STRING); // 22
																			// GenerationPower
		scalarMapping.put(DaoConstants.MAP_SVGID, StandardBasicTypes.LONG); // 23
		scalarMapping.put(DaoConstants.MAP_SVGNAME, StandardBasicTypes.STRING); // 24
		scalarMapping.put(DaoConstants.MAP_VRFHRS1, StandardBasicTypes.LONG); // 25
		scalarMapping.put(DaoConstants.MAP_VRFHRS2, StandardBasicTypes.LONG); // 26
		scalarMapping.put(DaoConstants.MAP_VRFHRS3, StandardBasicTypes.LONG); // 27
		scalarMapping.put(DaoConstants.MAP_PACHRS, StandardBasicTypes.LONG); // 28
		scalarMapping.put(DaoConstants.MAP_GHPHRS, StandardBasicTypes.LONG); // 29
		scalarMapping.put(DaoConstants.MAP_GHPOIL, StandardBasicTypes.LONG); // 30
		scalarMapping.put(DaoConstants.MAP_SITEPATH, StandardBasicTypes.STRING); // 31
		scalarMapping.put(DaoConstants.MAP_COMPANYNAME,
				StandardBasicTypes.STRING); // 32
		scalarMapping.put(DaoConstants.MAP_CASTATUS, StandardBasicTypes.STRING); // 33
		scalarMapping.put(DaoConstants.MAP_V23, StandardBasicTypes.STRING); // 34
		// Demand

		return scalarMapping;
	}

	public List<ODUParamsVO> getIDUList(String mac) throws HibernateException {

		String query = String.format(SQL_GET_ODU_PARAMETERLIST.toString(), mac);
		// executing query
		List<?> resultSet = sqlDao.executeSQLSelect(query);

		Object[] rowData = null;
		List<ODUParamsVO> oduParamsList = null;
		ODUParamsVO oduParams = null;

		if (!resultSet.isEmpty() && resultSet.size() > 0) {
			logger.debug("resultList in getODUParameterList method"
					+ resultSet.toString());
			Iterator<?> itr = resultSet.iterator();

			oduParamsList = new ArrayList<>();

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();

				oduParams = new ODUParamsVO();

				oduParams.setParameterName((String) rowData[0]);

				oduParams.setDisplayName((String) rowData[1]);

				oduParamsList.add(oduParams);
			}
		}
		return oduParamsList;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acconfig.dao.ACConfigDAO#
	 * getODUACConfigDetails
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public Set<ACConfigODUVO> getODUACConfigDetails(
			ACConfigRequest acConfigRequest) {

		logger.debug("Calling the AC Config details for outdoor units");

		Set<ACConfigODUVO> acODUList = new LinkedHashSet<ACConfigODUVO>();
		ACConfigODUVO acODUObj;

		LinkedHashMap<String, Type> scalarMapping = scalarMappingODU_R();

		Map<String, Object> parameter = new HashMap<String, Object>();
		String query = SQL_GET_ACCONFIG_DETAILS_ODU_R.toString();
/*
		switch (acConfigRequest.getIdType()) {
		case BizConstants.ID_TYPE_GROUP:
			query = String.format(query, SQL_GET_ODU_FN_GROUPS.toString(),
					SQL_GET_ODU_GROUPS.toString(), BizConstants.EMPTY_STRING);
			parameter.put(DaoConstants.ID, CommonUtil
					.convertCollectionToString(acConfigRequest.getId()));
			break;
		case BizConstants.ID_TYPE_OUTDOOR:
			query = String.format(query, BizConstants.EMPTY_STRING,
					BizConstants.EMPTY_STRING, SQL_GET_ODU_UNITS.toString());
			parameter.put(DaoConstants.ID, acConfigRequest.getId());
			break;

		}
*/
		query = String.format(query, SQL_GET_ODU_FN_GROUPS.toString(),
				SQL_GET_ODU_GROUPS.toString(), BizConstants.EMPTY_STRING);
		parameter.put(DaoConstants.ID, acConfigRequest.getIdType());
		try {
			List<?> result = sqlDao.executeSQLSelect(query, scalarMapping,
					parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					acODUObj = new ACConfigODUVO();

					rowData = (Object[]) itr.next();
					acODUObj.setOduId((Long) rowData[0]);
					acODUObj.setSite(rowData[1] == null ? BizConstants.HYPHEN
							: (String) rowData[1]);
					acODUObj.setParentChild(rowData[2] == null ? BizConstants.HYPHEN
							: (String) rowData[2]);
					acODUObj.setOdu_Name(rowData[3] == null ? BizConstants.HYPHEN
							: (String) rowData[3]);
					acODUObj.setUnitAddress(rowData[4] == null ? BizConstants.HYPHEN
							: (String) rowData[4]);
					acODUObj.setParentId(rowData[5] == null ? null
							: (Long) rowData[5]);
					acODUObj.setType(rowData[6] == null ? BizConstants.HYPHEN
							: (String) rowData[6]);
					acODUObj.setMaxlatitude(rowData[7] == null ? BizConstants.NUMBER_00
							: (Double) rowData[7]);
					acODUObj.setMaxlongitude(rowData[8] == null ? BizConstants.NUMBER_00
							: (Double) rowData[8]);
					acODUObj.setMinlatitude(rowData[9] == null ? BizConstants.NUMBER_00
							: (Double) rowData[9]);
					acODUObj.setMinlongitude(rowData[10] == null ? BizConstants.NUMBER_00
							: (Double) rowData[10]);
					acODUObj.setSvg_Location(rowData[11] == null ? BizConstants.HYPHEN
							: (String) rowData[11]);
					acODUObj.setAlarmType(rowData[12] == null ? BizConstants.HYPHEN
							: ((String) rowData[12]).toUpperCase());
					acODUObj.setSLinkAddress(rowData[13] == null ? BizConstants.HYPHEN
							: (String) rowData[13]);
					acODUObj.setAlarmCode(rowData[14] == null ? BizConstants.HYPHEN
							: (String) rowData[14]);
					acODUObj.setOutdoorTemp(rowData[15] == null ? BizConstants.NUMBER_00
							: (Double) rowData[15]);
					if (rowData[6] != null) {
						switch (((String) rowData[6]).toUpperCase()) {
						case BizConstants.ODU_TYPE_VRF:

							if (rowData[36] != null && (Boolean) rowData[36]) {
								acODUObj.setMaintenanceCountDownComp2(rowData[26] == null ? rowData[17] == null ? BizConstants.HYPHEN
										: rowData[17].toString().concat(
												BizConstants.HRS)
										: ((Double) rowData[26]) < 0 ? BizConstants.HRS0
												: rowData[26]
														.toString()
														.concat(BizConstants.HRS));
							} else {
								acODUObj.setMaintenanceCountDownComp2(BizConstants.HYPHEN);
							}

							if (rowData[37] != null && (Boolean) rowData[37]) {
								acODUObj.setMaintenanceCountDownComp3(rowData[27] == null ? rowData[18] == null ? BizConstants.HYPHEN
										: rowData[18].toString().concat(
												BizConstants.HRS)
										: ((Double) rowData[27]) < 0 ? BizConstants.HRS0
												: rowData[27]
														.toString()
														.concat(BizConstants.HRS));
							} else {
								acODUObj.setMaintenanceCountDownComp3(BizConstants.HYPHEN);
							}

							if (rowData[35] != null && (Boolean) rowData[35]) {
								acODUObj.setMaintenanceCountDownComp1(rowData[25] == null ? rowData[16] == null ? BizConstants.HYPHEN
										: rowData[16].toString().concat(
												BizConstants.HRS)
										: ((Double) rowData[25]) < 0 ? BizConstants.HRS0
												: rowData[25]
														.toString()
														.concat(BizConstants.HRS));
							} else {
								acODUObj.setMaintenanceCountDownComp1(BizConstants.HYPHEN);
							}
							acODUObj.setDemand(rowData[34] == null ? BizConstants.HYPHEN
									: ((String) rowData[34])
											.concat(BizConstants.PERCENT));
							break;

						case BizConstants.ODU_TYPE_PAC:
							acODUObj.setMaintenanceCountDownComp1(rowData[28] == null ? rowData[19] == null ? BizConstants.HYPHEN
									: rowData[19].toString().concat(
											BizConstants.HRS)
									: ((Double) rowData[28]) < 0 ? BizConstants.HRS0
											: rowData[28].toString().concat(
													BizConstants.HRS));
							acODUObj.setDemand(rowData[34] == null ? BizConstants.HYPHEN
									: ((String) rowData[34])
											.concat(BizConstants.PERCENT));
							break;

						case BizConstants.ODU_TYPE_GHP:
							acODUObj.setGenerationPower(rowData[22] == null ? BizConstants.HYPHEN
									: ((String) rowData[22])
											.concat(BizConstants.WH));
							acODUObj.setGhpEngineServiceCountDown(rowData[29] == null ? rowData[20] == null ? BizConstants.HYPHEN
									: rowData[20].toString().concat(
											BizConstants.HRS)
									: ((Double) rowData[29]) < 0 ? BizConstants.HRS0
											: rowData[29].toString().concat(
													BizConstants.HRS));

							acODUObj.setGhpOilCheckCountDown(rowData[30] == null ? rowData[21] == null ? BizConstants.HYPHEN
									: rowData[21].toString().concat(
											BizConstants.HRS)
									: ((Double) rowData[30]) < 0 ? BizConstants.HRS0
											: rowData[30].toString().concat(
													BizConstants.HRS));

							break;
						}
					}

					acODUObj.setSvgId(rowData[23] == null ? null
							: (Long) rowData[23]);
					acODUObj.setSvgDisplayName(rowData[24] == null ? BizConstants.HYPHEN
							: (String) rowData[24]);
					acODUObj.setSitePath(rowData[31] == null ? null
							: (String) rowData[31]);
					acODUObj.setCustomerName(rowData[32] == null ? null
							: (String) rowData[32]);

					acODUObj.setCaStatus(rowData[33] == null ? null
							: (String) rowData[33]);
					acODUList.add(acODUObj);
				}

			}

		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while fetching"
					+ " AC Configuration ODU Details for ID %d",
					acConfigRequest), sqlExp);
		}
		return acODUList;
	}
	
	/**
	 * Set the Scalar Mapping for Outdoor Units.
	 * 
	 * @return
	 */
	private LinkedHashMap<String, Type> scalarMappingODU_R() {
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(DaoConstants.MAP_ODUID, StandardBasicTypes.LONG); // 0
		scalarMapping.put(DaoConstants.MAP_SITE_GROUP,
				StandardBasicTypes.STRING); // 1
		scalarMapping.put(DaoConstants.MAP_PARENT_CHILD,
				StandardBasicTypes.STRING); // 2
		scalarMapping.put(DaoConstants.MAP_NAME, StandardBasicTypes.STRING); // 3
		scalarMapping.put(DaoConstants.MAP_CENTRAL_ADDRESS,
				StandardBasicTypes.STRING); // 4
		scalarMapping.put(DaoConstants.MAP_PARENT_ID, StandardBasicTypes.LONG); // 5
		scalarMapping.put(DaoConstants.MAP_TYPE, StandardBasicTypes.STRING); // 6
		scalarMapping.put(DaoConstants.MAP_SVGMAXLATITUDE,
				StandardBasicTypes.DOUBLE); // 7
		scalarMapping.put(DaoConstants.MAP_SVGMAXLONGITUDE,
				StandardBasicTypes.DOUBLE); // 8
		scalarMapping.put(DaoConstants.MAP_SVGMINLATITUDE,
				StandardBasicTypes.DOUBLE); // 9
		scalarMapping.put(DaoConstants.MAP_SVGMINLONGITUDE,
				StandardBasicTypes.DOUBLE); // 10
		scalarMapping.put(DaoConstants.MAP_ODU_SVG_PATH,
				StandardBasicTypes.STRING); // 11
		scalarMapping.put(DaoConstants.MAP_SEVERITY, StandardBasicTypes.STRING); // 12
		scalarMapping.put(DaoConstants.MAP_SLINKADD, StandardBasicTypes.STRING); // 13
		scalarMapping.put(DaoConstants.MAP_ALARM_CODE,
				StandardBasicTypes.STRING); // 14
		scalarMapping.put(DaoConstants.MAP_C2, StandardBasicTypes.DOUBLE); // 15
																			// Outdoor
																			// Temp
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_1, StandardBasicTypes.LONG); // 16
																				// CompressorWorkingHrs_1
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_2, StandardBasicTypes.LONG); // 17
																				// CompressorWorkingHrs_2
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_3, StandardBasicTypes.LONG); // 18
																				// CompressorWorkingHrs_3
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_4, StandardBasicTypes.LONG); // 19
																				// PAC
																				// CompressorWorkingHrs
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_5, StandardBasicTypes.LONG); // 20
																				// EngineWorkingHrs
		scalarMapping
				.put(DaoConstants.MAP_THRESHOLD_6, StandardBasicTypes.LONG); // 21
																				// NOT_IN_USE_EnginOilChange
		scalarMapping.put(DaoConstants.MAP_G44, StandardBasicTypes.STRING); // 22
																			// GenerationPower
		scalarMapping.put(DaoConstants.MAP_SVGID, StandardBasicTypes.LONG); // 23
		scalarMapping.put(DaoConstants.MAP_SVGNAME, StandardBasicTypes.STRING); // 24
		scalarMapping.put(DaoConstants.MAP_VRFHRS1, StandardBasicTypes.DOUBLE); // 25
		scalarMapping.put(DaoConstants.MAP_VRFHRS2, StandardBasicTypes.DOUBLE); // 26
		scalarMapping.put(DaoConstants.MAP_VRFHRS3, StandardBasicTypes.DOUBLE); // 27
		scalarMapping.put(DaoConstants.MAP_PACHRS, StandardBasicTypes.DOUBLE); // 28
		scalarMapping.put(DaoConstants.MAP_GHPHRS, StandardBasicTypes.DOUBLE); // 29
		scalarMapping.put(DaoConstants.MAP_GHPOIL, StandardBasicTypes.DOUBLE); // 30
		scalarMapping.put(DaoConstants.MAP_SITEPATH, StandardBasicTypes.STRING); // 31
		scalarMapping.put(DaoConstants.MAP_COMPANYNAME,
				StandardBasicTypes.STRING); // 32
		scalarMapping.put(DaoConstants.MAP_CASTATUS, StandardBasicTypes.STRING); // 33
		scalarMapping.put(DaoConstants.MAP_V23, StandardBasicTypes.STRING); // 34
		// Demand
		scalarMapping.put(DaoConstants.MAP_COMPRESSOR1,
				StandardBasicTypes.BOOLEAN); // 35
		scalarMapping.put(DaoConstants.MAP_COMPRESSOR2,
				StandardBasicTypes.BOOLEAN); // 36
		scalarMapping.put(DaoConstants.MAP_COMPRESSOR3,
				StandardBasicTypes.BOOLEAN); // 37

		return scalarMapping;
	}
	
}
