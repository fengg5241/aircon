package com.panasonic.b2bacns.bizportal.acconfig.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.panasonic.b2bacns.bizportal.acconfig.vo.DetailsLogsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUListVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.RefrigerantSVG;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.pf.common.Constants;
import com.panasonic.b2bacns.bizportal.pf.common.PropValueAlgo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.DaoConstants;

@Service
public class ACConfigDAOImpl implements ACConfigDAO {

	private static final Logger logger = Logger
			.getLogger(ACConfigDAOImpl.class);

	@Autowired
	private SQLDAO sqlDao;

	// Modifed by Ravi and shanf//
	private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_GROUP = new StringBuilder(
			"select * from (select fn.indoorunitid as id,fn.groupname as controlgroup, g.name as sitegroup,case when idu.parent_id is null then 'Main' else 'Sub' end pc,case when t1.adapterid is null then 'Online' else 'Offline' end as castatus,")
			.append("idu.name as name,idu.centraladdress  as centraladdress,sts.a1 as powerstatus,sts.a2_1 as a2_1,sts.a2_2 as a2_2,sts.a28 as iseconavi,pmd.a4 as roomtemp,")
			.append("idu.parent_id, 'Active' as status,group_id,idu.outdoorunit_id as oduid,idu.svg_max_latitude as svg_max_latitude,idu.svg_max_longitude as svg_max_longitude,")
			.append("idu.svg_min_latitude as svg_min_latitude,idu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as idusvgpath, svg1.svg_file_name as odusvgpath, ")
			.append("sts.a10 as prohibitionstatus, sts.a11 as prohibitionmode, sts.a12 as prohibitiontemp, sts.a13 as prohibitionspeed, sts.a14 as prohibitiondirection, ")
			.append("sts.a15 as prohibitionenergy, sts.a34 as energy, t.code as alarmcode, sts.a3 as a3,sts.a6_1 as a6_1,sts.a7_1 as a7_1,sts.a6_2 as a6_2,sts.a7_2 as a7_2,sts.a3a as a3a,sts.a3h as a3h,sts.a3c as a3c,sts.a3d as a3d,")
			.append("sts.a6a_1 as a6a_1,sts.a6h_1 as a6h_1,sts.a6c_1 as a6c_1,sts.a6d_1 as a6d_1,sts.a6f_1 as a6f_1,sts.a7a_1 as a7a_1,sts.a7h_1 as a7h_1,sts.a7c_1 as a7c_1,sts.a7d_1 as a7d_1,sts.a7f_1 as a7f_1,")
			.append("sts.a6a_2 as a6a_2,sts.a6h_2 as a6h_2,sts.a6c_2 as a6c_2,sts.a6d_2 as a6d_2,sts.a6f_2 as a6f_2,sts.a7a_2 as a7a_2,sts.a7h_2 as a7h_2,sts.a7c_2 as a7c_2,sts.a7d_2 as a7d_2,sts.a7f_2 as a7f_2,sts.a17a as a17a,idu.type, ")
			.append("idu.slinkaddress,t.severity,svg.id as svgid,svg.svg_name as svgname,svg1.id as linkedsvgid ,rm.svg_id1,rm.svg_max_latitude1 as refgrnt_svg_max_latitude1,")
			.append("rm.svg_max_longitude1 as refgrnt_svg_max_longitude1,rm.svg_min_latitude1 as refgrnt_svg_min_latitude1,rm.svg_min_longitude1 as refgrnt_svg_min_longitude1, rm.svg_id2,")
			.append("rm.svg_max_latitude2 as refgrnt_svg_max_latitude2,rm.svg_max_longitude2 as refgrnt_svg_max_longitude2,rm.svg_min_latitude2 as refgrnt_svg_min_latitude2,")
			.append("rm.svg_min_longitude2 as refgrnt_svg_min_longitude2, rm.svg_id3,rm.svg_max_latitude3 as refgrnt_svg_max_latitude3,rm.svg_max_longitude3 as refgrnt_svg_max_longitude3,rm.svg_min_latitude3 as refgrnt_svg_min_latitude3,")
			.append("rm.svg_min_longitude3 as refgrnt_svg_min_longitude3,case when t1.adapterid is null then idu.rc_flag else 0 end as rcflag,g.path_name as sitepath,c.name as companyname ,case when parent_id is not null then idu.parent_id else idu.id end parentidsort, idu.device_model from usp_getindoorunits_supplygroupname(:id) fn inner join indoorunits idu on fn.indoorunitid = idu.id ")
			.append(" left outer join ct_statusinfo sts on idu.oid= sts.facl_id left outer join ct_period_measure_data pmd on idu.oid= pmd.facl_id left join outdoorunits odu on idu.outdoorunit_id = odu.id left outer join groups g on g.uniqueid = idu.siteid ")
			.append("left outer join (select indoorunit_id,code,severity  from (select row_number() over (partition by indoorunit_id order  by severity, time desc) row_number, ")
			.append("indoorunit_id,code,severity  from notificationlog where lower(status) = 'new' and indoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) t on fn.indoorunitid = t.indoorunit_id ")
			.append("left join refrigerantmaster rm on (rm.svg_id1=idu.svg_id or rm.svg_id2=idu.svg_id or rm.svg_id3=idu.svg_id) and rm.siteid = idu.siteid left outer join svg_master svg on svg.id = idu.svg_id ")
			.append("left outer join svg_master svg1 on svg1.id = odu.svg_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order  by time desc) row_number, ")
			.append("adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on idu.adapters_id = t1.adapterid  ")
			.append("order by case when idu.parent_id is null then fn.indoorunitid else idu.parent_id end,idu.parent_id,fn.indoorunitid,fn.groupname, g.name) T order by parentidsort,pc,id");

	// Modifed by Ravi and shanf //
	private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_IDU = new StringBuilder(
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
			.append("rm.svg_min_longitude3 as refgrnt_svg_min_longitude3,case when t1.adapterid is null then idu.rc_flag else 0 end as rcflag,g.path_name as sitepath,c.name as companyname,case when parent_id is not null then idu.parent_id else idu.id end parentidsort, idu.device_model from indoorunits idu left outer join ct_statusinfo sts on idu.oid= sts.facl_id ")
			.append("left outer join ct_period_measure_data pmd on idu.oid= pmd.facl_id left join outdoorunits odu on idu.outdoorunit_id = odu.id left outer join groups g on g.uniqueid = idu.siteid left outer join groups g1 on g1.id = idu.group_id ")
			.append("left outer join (select indoorunit_id,code,severity  from (select row_number() over (partition by indoorunit_id order  by severity, time desc) row_number, ")
			.append("indoorunit_id,code,severity from notificationlog where lower(status) = 'new' and indoorunit_id is not null and severity is not null order by severity, time desc) temp where row_number = 1) t on idu.id = t.indoorunit_id ")
			.append("left join refrigerantmaster rm on (rm.svg_id1=idu.svg_id or rm.svg_id2=idu.svg_id or rm.svg_id3=idu.svg_id) and rm.siteid = idu.siteid left outer join svg_master svg on svg.id = idu.svg_id ")
			.append("left outer join svg_master svg1 on svg1.id = odu.svg_id left join companies c on c.id = g.company_id left outer join (select adapterid,code from (select row_number() over (partition by adapterid order  by time desc) row_number, ")
			.append("adapterid,code from notificationlog where lower(status) = 'new' and code = 'CW04' and adapterid is not null order by time desc) temp where row_number = 1) t1 on idu.adapters_id = t1.adapterid ")
			.append("where idu.id in (:id) order by case when idu.parent_id is null then idu.id else idu.parent_id end,idu.parent_id,idu.id,g1.name, g.name) T order by parentidsort,pc,id");

	private static final StringBuilder SQL_GET_ACCONFIG_DETAILS_ODU = new StringBuilder(
			"%s select * from (select odu.id as oduid, odu.centraladdress as centraladdress, odu.name as name, odu.type as type,odu.svg_max_latitude as svg_max_latitude,odu.svg_max_longitude as svg_max_longitude,case when t1.adapterid is null then 'Online' ")
			.append("else 'Offline' end as castatus,odu.svg_min_latitude as svg_min_latitude,odu.svg_min_longitude as svg_min_longitude,svg.svg_file_name as odusvgpath,case when odu.parentid is null then 'Main' else 'Sub' end pc")
			.append(",odu.parentid as parent_id,g.name as sitegroup,odu.slinkaddress,nl.severity,svg.id as svgid,svg.svg_name as svgname,nl.code as alarmcode,g.path_name as sitepath,c.name as companyname,")
			.append("pmd.c2,pmd.v23,pmd.g44,maint.ghp_engine_operation_hours as ghphrs,maint.ghp_time_after_oil_change as ghpoil,maint.pac_comp_operation_hours as pachrs,")
			.append("maint.vrf_comp_operation_hours_1 as vrfhrs1,maint.vrf_comp_operation_hours_2 as vrfhrs2,maint.vrf_comp_operation_hours_3 as vrfhrs3,setting.threshold_1 ,setting.threshold_2")
			.append(",setting.threshold_3 ,setting.threshold_4 ,setting.threshold_5 ,setting.threshold_6,case when odu.parentid is not null then odu.parentid else odu.id end parentidsort,odu.compressor1,")
			.append("odu.compressor2,odu.compressor3,odu.device_model  from outdoorunits odu %s left outer join groups g on g.uniqueid = odu.siteid ")
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
			" WITH RECURSIVE cteoutdoors AS (select distinct outdoorunit_id from usp_getindoorunits_supplygroupname(:id) f join indoorunits idu on f.indoorunitid=idu.id ")
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

	private static final StringBuffer SQL_GET_ODU_PARAMETERLIST = new StringBuffer(
			"select parameter_name , display_name from outdoorunitparameters where type ='%s' ");

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

		switch (acConfigRequest.getIdType()) {
		case BizConstants.ID_TYPE_GROUP:
			query = SQL_GET_ACCONFIG_DETAILS_GROUP.toString();
			parameter.put(DaoConstants.ID, CommonUtil
					.convertCollectionToString(acConfigRequest.getId()));
			break;
		case BizConstants.ID_TYPE_INDOOR:
			query = SQL_GET_ACCONFIG_DETAILS_IDU.toString();
			parameter.put(DaoConstants.ID, acConfigRequest.getId());
			break;

		}

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

					//add by shanf
					acConfigObj
					.setDeviceModel(rowData[84] == null ? BizConstants.HYPHEN
							: (String) rowData[84]);
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
					+ " AC Configuration Details for IDs: %s and idType: %s ",
					acConfigRequest.getId().toString(),
					acConfigRequest.getIdType()), sqlExp);
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
		//add by shanf
		scalarMapping.put(DaoConstants.DEVICE_MODEL, StandardBasicTypes.STRING); // 84

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
	public Set<ACConfigODUVO> getODUACConfigDetails(
			ACConfigRequest acConfigRequest) {

		logger.debug("Calling the AC Config details for outdoor units");

		Set<ACConfigODUVO> acODUList = new LinkedHashSet<ACConfigODUVO>();
		ACConfigODUVO acODUObj;

		LinkedHashMap<String, Type> scalarMapping = scalarMappingODU();

		Map<String, Object> parameter = new HashMap<String, Object>();
		String query = SQL_GET_ACCONFIG_DETAILS_ODU.toString();

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
					//add by shanf
					acODUObj.setDeviceModel(rowData[38] == null ? BizConstants.HYPHEN
							: (String) rowData[38]);
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
			logger.error(
					String.format(
							"An Exception occured while fetching"
									+ " AC Configuration ODU Details for IDs: %s and idType: %s ",
							acConfigRequest.getId().toString(),
							acConfigRequest.getIdType()), sqlExp);
		}
		return acODUList;
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
		//add by shanf
		scalarMapping.put(DaoConstants.DEVICE_MODEL,
				StandardBasicTypes.STRING); // 38

		return scalarMapping;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUList(Long[]
	 * id)
	 */
	@Override
	public List<ODUListVO> getODUList(Long[] id) throws HibernateException {

		List<BigInteger> iduList = new ArrayList<BigInteger>();
		// creating idulist
		for (Long iduid : id) {
			iduList.add(new BigInteger(iduid.toString()));
		}
		// creating scalar mapping
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		// creating parameter mapping
		Map<String, Object> parameter = new HashMap<String, Object>();

		String sqlQuery = String.format(SQL_GET_ODU_BY_IDU_ID.toString());
		scalarMapping.put(DaoConstants.OUTDOORUNIT_ID,
				StandardBasicTypes.BIG_INTEGER);
		scalarMapping.put(DaoConstants.ODU_NAME, StandardBasicTypes.STRING);
		// putting value in parameter map
		parameter.put(DaoConstants.INDOORUNIT_ID, iduList);
		// executing query
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);

		logger.debug("resultlist in getODUList method"
				+ (resultList != null ? resultList.toString() : resultList));

		ODUListVO oduVO = null;

		List<ODUListVO> oduList = null;

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowData = null;

			oduList = new ArrayList<ODUListVO>();

			while (itr.hasNext()) {
				oduVO = new ODUListVO();
				rowData = (Object[]) itr.next();
				oduVO.setOduID((BigInteger) rowData[0]);
				oduVO.setOduName((String) rowData[1]);

				oduList.add(oduVO);
			}
			return oduList;
		}
		return oduList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUDataForGroups(Long
	 * [] id)
	 */
	@Override
	public List<ODUListVO> getODUDataForGroups(Long[] id)
			throws HibernateException {
		// creating groupId list
		List<Long> groupId = Arrays.asList(id);

		String groupIdlist = CommonUtil.convertCollectionToString(groupId);
		// creating scalarmapping
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		// creating parameter map
		Map<String, Object> parameter = new HashMap<String, Object>();

		String sqlQuery = String.format(SQL_GET_ODU_BY_GROUP_ID.toString());
		// putting value in scalarmapping
		scalarMapping.put(DaoConstants.OUTDOORUNIT_ID,
				StandardBasicTypes.BIG_INTEGER);
		scalarMapping.put(DaoConstants.ODU_NAME, StandardBasicTypes.STRING);
		// putting value in parameter map
		parameter.put(DaoConstants.GROUPS_ID, groupIdlist);
		// executing query
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);
		logger.debug("resultList in getODUDataForGroups method"
				+ (resultList != null ? resultList.toString()
						: BizConstants.EMPTY_STRING));
		ODUListVO oduVO = null;
		List<ODUListVO> oduList = null;

		Object[] rowData = null;

		if (resultList != null) {

			if (resultList.size() > 0) {

				oduList = new ArrayList<>();
				// iterating resultList
				Iterator<?> itr = resultList.iterator();

				while (itr.hasNext()) {

					rowData = (Object[]) itr.next();

					oduVO = new ODUListVO();

					oduVO.setOduID((BigInteger) rowData[0]);
					oduVO.setOduName((String) rowData[1]);

					oduList.add(oduVO);
				}
				return oduList;
			}

		}
		return oduList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUParams(Long
	 * id)
	 */
	@Override
	public List<ODUParamVO> getODUParams(Long id, List<String> paramWithG1,
			List<String> paramsForGHPlist, List<String> paramsForVRFlist,
			String idType) throws HibernateException {

		String sqlQuery = null;
		String paramsForVRF = null;
		String paramsForG1 = null;
		String paramsForGHP = null;

		// For VRF param
		if (paramsForVRFlist != null && paramsForVRFlist.size() >= 1) {
			StringBuilder st = new StringBuilder();
			for (String vrfParams : paramsForVRFlist) {
				st.append("'").append(vrfParams).append("'").append(",");
			}

			int length = st.length();
			paramsForVRF = st.substring(0, length - 1).toString();
		} else { // for GHP
			paramsForG1 = CommonUtil.convertCollectionToString(paramWithG1);

			if (paramsForGHPlist != null && paramsForGHPlist.size() > 0) {
				StringBuilder ghpAll = new StringBuilder();
				for (String ghpParams : paramsForGHPlist) {
					ghpAll.append("'").append(ghpParams).append("'")
							.append(",");
				}

				int lengthofGHP = ghpAll.length();
				paramsForGHP = ghpAll.substring(1, lengthofGHP - 2).toString();
			}
		}
		// creating scalarmapping
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		Map<String, Object> parameter = new HashMap<String, Object>();

		scalarMapping.put(DaoConstants.ODUPARAM_OUTDOORUNIT_ID,
				StandardBasicTypes.BIG_INTEGER);
		scalarMapping.put(DaoConstants.MAP_TYPE, StandardBasicTypes.STRING);
		scalarMapping.put(DaoConstants.ODUPARAM_PROPERTY_ID,
				StandardBasicTypes.STRING);
		scalarMapping.put(DaoConstants.ODUPARAM_MEASURE_VALUE,
				StandardBasicTypes.STRING);
		scalarMapping.put(DaoConstants.ODUPARAM_DISPLAY_NAME,
				StandardBasicTypes.STRING);
		scalarMapping.put(DaoConstants.ODUPARAM_MODELNAME,
				StandardBasicTypes.STRING);
		scalarMapping.put(DaoConstants.ODUPARAM_DIMENSION,
				StandardBasicTypes.STRING);
		scalarMapping.put(DaoConstants.ODUPARAM_UTILIZATION_RATE,
				StandardBasicTypes.FLOAT);
		scalarMapping.put(DaoConstants.ODUPARAM_WORKING_HOUR,
				StandardBasicTypes.BIG_DECIMAL);

		if (paramsForVRF != null && idType.equalsIgnoreCase("VRF")
				&& paramsForVRF.length() > 0) {

			sqlQuery = String.format(SQL_GET_ODU_PARAMS_FOR_VRF.toString(),
					paramsForVRF);

			parameter.put(DaoConstants.ODU_ID, id);
			parameter.put(DaoConstants.ODU_PARAMS_IDTYPE, idType);
		} else {
			sqlQuery = String.format(SQL_GET_ODU_PARAMS_FOR_GHP.toString(),
					paramsForGHP, paramsForG1);

			parameter.put(DaoConstants.ODU_ID, id);
			parameter.put(DaoConstants.ODU_PARAMS_IDTYPE, idType);

		}

		// executing query
		List<?> resultList = sqlDao.executeSQLSelect(sqlQuery, scalarMapping,
				parameter);
		logger.debug("resultList in getODUParams method"
				+ (resultList != null ? resultList.toString() : resultList));

		ODUParamVO oduVO = null;

		List<ODUParamVO> oduData = null;

		Map<String, String> oduParams = null;

		Object[] rowData = null;

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();

			oduData = new ArrayList<>();

			oduParams = new HashMap<String, String>();
			oduVO = new ODUParamVO();
			while (itr.hasNext()) {
				rowData = (Object[]) itr.next();

				oduVO.setModelName((String) rowData[5]);
				oduVO.setDimension((String) rowData[6]);
				oduVO.setCurrentUtilization((Float) rowData[7] == null ? BizConstants.NUMBER_00
						: (((Float) rowData[7])).doubleValue());
				oduVO.setWorkingtime((BigDecimal) rowData[8] == null ? BizConstants.NUMBER_00
						: CommonUtil
								.getFormattedValueUpToTwoDecimal(((BigDecimal) rowData[8])
										.doubleValue()));
				oduVO.setType((String) rowData[1]);

				oduParams.put((String) rowData[4], (String) rowData[3]);

			}
			oduVO.setOduParam(oduParams);

			oduData.add(oduVO);
			return oduData;
		}
		return oduData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.ACConfigDAO#getODUParameterList(String
	 * idType)
	 */
	@Override
	public List<ODUParamsVO> getODUParameterList(String idType)
			throws HibernateException {

		String query = String.format(SQL_GET_ODU_PARAMETERLIST.toString(),
				idType);
		// executing query
		List<?> resultSet = sqlDao.executeSQLSelect(query);
		logger.debug("resultList in getODUParameterList method"
				+ (resultSet != null ? resultSet.toString() : resultSet));
		Object[] rowData = null;
		List<ODUParamsVO> oduParamsList = null;
		ODUParamsVO oduParams = null;

		if (resultSet != null && resultSet.size() > 0) {

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

}
