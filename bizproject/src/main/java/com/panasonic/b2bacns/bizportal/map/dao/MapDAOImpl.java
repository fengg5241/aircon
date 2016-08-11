package com.panasonic.b2bacns.bizportal.map.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.map.vo.GroupVO;
import com.panasonic.b2bacns.bizportal.map.vo.IndoorOutdoorVO;
import com.panasonic.b2bacns.bizportal.map.vo.IndoorUnitVO;
import com.panasonic.b2bacns.bizportal.map.vo.OutdoorUnitVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.DaoConstants;

@Service
public class MapDAOImpl implements MapDAO {

	@Autowired
	private SQLDAO sqlDao;

	private static final String MAP_GROUPID = "group_id";
	private static final String MAP_IDU_SVGMAXLONGITUDE = "idu_svg_max_longitude";
	private static final String MAP_IDU_SVGMINLONGITUDE = "idu_svg_min_longitude";
	private static final String MAP_IDU_SVGMAXLATITUDE = "idu_svg_max_latitude";
	private static final String MAP_IDU_SVGMINLATITUDE = "idu_svg_min_latitude";
	private static final String MAP_IDU_CENTRAL_ADDRESS = "iducentraladdress";
	private static final String MAP_POWER_STATUS = "powerstatus";
	private static final String MAP_MODE_NAME = "modename";
	private static final String MAP_FANSPEED_NAME = "fanspeedname";
	private static final String MAP_WINDDIRECTION_NAME = "winddirectionname";
	private static final String MAP_ISECONAVI = "iseconavi";
	private static final String ROOM_TEMP = "roomtemp";
	private static final String SETPOINT_TEMP = "setpointtemp";
	private static final String MAP_ODU_SVGMAXLONGITUDE = "odu_svg_max_longitude";
	private static final String MAP_ODU_SVGMINLONGITUDE = "odu_svg_min_longitude";
	private static final String MAP_ODU_SVGMAXLATITUDE = "odu_svg_max_latitude";
	private static final String MAP_ODU_SVGMINLATITUDE = "odu_svg_min_latitude";
	private static final String INDOORUNIT_ID = "indoorunit_id";
	private static final String MAP_INDOORID = "indoorId";
	private static final String MAP_PATH = "path";
	private static final String MAP_LINKED_ODUID = "linkedoutdoorunit_id";
	private static final String MAP_COUNT_INDOOR = "countindoorunitid";
	private static final String MAP_COUNT_OUTDOOR = "countoutdoorunitid";
	private static final String MAP_IDU_MODEL_NAME = "idumodelname";
	private static final String MAP_ODU_MODEL_NAME = "odumodelname";
	private static final String MAP_ODU_CENTRAL_ADDRESS = "oducentraladdress";
	private static final String MAP_WORKING_TIME_1 = "workingtime1";
	private static final String MAP_WORKING_TIME_2 = "workingtime2";
	private static final String MAP_WORKING_TIME_3 = "workingtime3";
	private static final String MAP_UTILIZATION_RATE = "utilizationrate";
	private static final String MAP_ODU_GROUP_ID = "odugroupid";
	private static final String MAP_SERIAL_NUMBER = "serialnumber";
	private static final String MAP_WORKING_TIME_GHP = "ghpworkingtime1";
	private static final String MAP_TYPE = "type";

	private static final StringBuilder SQL_GET_IU_OU_MAP = new StringBuilder(
			"select g.path, idu.group_id,coalesce(idu.id,0) as indoorunit_id, idu.outdoorunit_id as linkedoutdoorunit_id,fn_getpropertvalue(sts.A2,'settemp',sts.facl_id) as setpointtemp,sts.A2 as modename, ")
			.append("countindoorunitid,countoutdoorunitid,midu.modelname as idumodelname,modu.modelname as odumodelname,idu.centraladdress as iducentraladdress,")
			.append("odu.centraladdress as oducentraladdress,idu.svg_min_latitude as idu_svg_min_latitude,idu.svg_max_latitude as idu_svg_max_latitude, ")
			.append("idu.svg_min_longitude as idu_svg_min_longitude,idu.svg_max_longitude as idu_svg_max_longitude,odu.svg_min_latitude as odu_svg_min_latitude, ")
			.append("odu.svg_max_latitude as odu_svg_max_latitude,odu.svg_min_longitude as odu_svg_min_longitude,odu.svg_max_longitude as odu_svg_max_longitude, ")
			.append("sts1.V20 as workingtime1,sts1.V21 as workingtime2,sts1.V22 as workingtime3,sts1.B1 as utilizationrate,0 as odugroupid,sts.A1 as powerstatus,")
			.append("idu.serialnumber,fn_getpropertvalue(sts.A2,'fanspeed',sts.facl_id) as fanspeedname, fn_getpropertvalue(sts.A2,'winddirection',sts.facl_id) as winddirectionname,sts.A28 as iseconavi,")
			.append("sts.A4 as roomtemp,sts1.GS1 as ghpworkingtime1,odu.type %s left outer join metaindoorunits midu on idu.metaindoorunit_id = midu.id ")
			.append("left outer join outdoorunits odu on idu.outdoorunit_id = odu.id left outer join metaoutdoorunits modu on modu.id = odu.metaoutdoorunit_id ")
			.append("left outer join ct_statusinfo sts on idu.oid= sts.facl_id left outer join ct_statusinfo sts1 on odu.oid= sts1.facl_id ")
			.append(" left outer join ")
			.append("(select count(a.indoorunit_id) as countindoorunitid, a.indoorunit_id from notificationlog a  where lower(status)='new' group by a.indoorunit_id)  ast on   ast.indoorunit_id  = idu.id ")
			.append("left outer join ")
			.append(" (select count(a.outdoorunit_id) as countoutdoorunitid,a.outdoorunit_id from notificationlog a where lower(status)='new' group by a.outdoorunit_id)  ast1 on   ast1.outdoorunit_id  = idu.outdoorunit_id")
			.append(" left outer join groups g on g.id = idu.group_id %s");

	private static final String IDU_MAP_1 = " from  indoorunits idu";
	private static final String IDU_MAP_2 = " where idu.id in (:indoorId)";
	private static final String GROUP_MAP = " from usp_getindoorunits_supplygroupname(:group_id) t join indoorunits idu on t.indoorunitid = idu.id";
	private static final String STRING_0 = "0";
	private static final String STRING_1 = "1";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.map.dao.MapDAO#getUnitsForGroup(java.
	 * lang.String)
	 */
	@Override
	public GroupVO getUnitsForGroup(String groupId) throws HibernateException {

		String finalQuery = String.format(SQL_GET_IU_OU_MAP.toString(),
				GROUP_MAP, BizConstants.EMPTY_STRING);

		List<IndoorUnitVO> indoorUnitVOList = new ArrayList<IndoorUnitVO>();

		List<OutdoorUnitVO> outdoorUnitVOList = new ArrayList<OutdoorUnitVO>();

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(MAP_GROUPID, groupId);

		// execute query.
		List<?> result = sqlDao.executeSQLSelect(finalQuery, scalarMapping(),
				parameter);

		if (result != null && !result.isEmpty()) {

			getMapIndoorOutdoor(indoorUnitVOList, outdoorUnitVOList, result);

			GroupVO groupVo = new GroupVO();
			if (indoorUnitVOList.size() > 0) {
				groupVo.setIduList(indoorUnitVOList);
			}
			if (outdoorUnitVOList.size() > 0) {
				groupVo.setOduList(outdoorUnitVOList);
			}

			return groupVo;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.map.dao.MapDAO#getIduMap(java.lang.String
	 * )
	 */
	@Override
	public IndoorOutdoorVO getIduMap(List<Long> iduId)
			throws HibernateException {

		String finalQuery = String.format(SQL_GET_IU_OU_MAP.toString(),
				IDU_MAP_1, IDU_MAP_2);
		List<IndoorUnitVO> indoorUnitVOList = new ArrayList<IndoorUnitVO>();

		List<OutdoorUnitVO> outdoorUnitVOList = new ArrayList<OutdoorUnitVO>();

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(MAP_INDOORID, iduId);

		// execute query.
		List<?> result = sqlDao.executeSQLSelect(finalQuery, scalarMapping(),
				parameter);

		if (result != null && !result.isEmpty()) {

			getMapIndoorOutdoor(indoorUnitVOList, outdoorUnitVOList, result);

			IndoorOutdoorVO indoorOutdoorVO = new IndoorOutdoorVO();
			if (indoorUnitVOList.size() > 0) {
				indoorOutdoorVO.setIduList(indoorUnitVOList);
			}
			if (outdoorUnitVOList.size() > 0) {
				indoorOutdoorVO.setOduList(outdoorUnitVOList);
			}
			return indoorOutdoorVO;

		}
		return null;

	}

	private LinkedHashMap<String, Type> scalarMapping() {
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put(MAP_PATH, StandardBasicTypes.STRING);
		scalarMapping.put(MAP_GROUPID, StandardBasicTypes.LONG);
		scalarMapping.put(INDOORUNIT_ID, StandardBasicTypes.LONG);
		scalarMapping.put(MAP_LINKED_ODUID, StandardBasicTypes.LONG);
		scalarMapping.put(SETPOINT_TEMP, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_MODE_NAME, StandardBasicTypes.INTEGER);
		scalarMapping.put(MAP_COUNT_INDOOR, StandardBasicTypes.LONG);
		scalarMapping.put(MAP_COUNT_OUTDOOR, StandardBasicTypes.LONG);
		scalarMapping.put(MAP_IDU_MODEL_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(MAP_ODU_MODEL_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(MAP_IDU_CENTRAL_ADDRESS, StandardBasicTypes.STRING);
		scalarMapping.put(MAP_ODU_CENTRAL_ADDRESS, StandardBasicTypes.STRING);
		scalarMapping.put(MAP_IDU_SVGMINLATITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_IDU_SVGMAXLATITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_IDU_SVGMINLONGITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_IDU_SVGMAXLONGITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_ODU_SVGMINLATITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_ODU_SVGMAXLATITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_ODU_SVGMINLONGITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_ODU_SVGMAXLONGITUDE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_WORKING_TIME_1, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_WORKING_TIME_2, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_WORKING_TIME_3, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_UTILIZATION_RATE, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_ODU_GROUP_ID, StandardBasicTypes.INTEGER);
		scalarMapping.put(MAP_POWER_STATUS, StandardBasicTypes.STRING);
		scalarMapping.put(MAP_SERIAL_NUMBER, StandardBasicTypes.STRING);
		scalarMapping.put(MAP_FANSPEED_NAME, StandardBasicTypes.INTEGER);
		scalarMapping.put(MAP_WINDDIRECTION_NAME, StandardBasicTypes.INTEGER);
		scalarMapping.put(MAP_ISECONAVI, StandardBasicTypes.STRING);
		scalarMapping.put(ROOM_TEMP, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_WORKING_TIME_GHP, StandardBasicTypes.DOUBLE);
		scalarMapping.put(MAP_TYPE, StandardBasicTypes.STRING);
		return scalarMapping;
	}

	/**
	 * @param indoorUnitVOList
	 * @param outdoorUnitVOList
	 * @param result
	 */
	private void getMapIndoorOutdoor(List<IndoorUnitVO> indoorUnitVOList,
			List<OutdoorUnitVO> outdoorUnitVOList, List<?> result) {
		Set<Long> outdoorUnits = new HashSet<Long>();
		Object[] rowData = null;
		Iterator<?> itr = result.iterator();

		while (itr.hasNext()) {

			IndoorUnitVO indoorUnitVO = new IndoorUnitVO();
			OutdoorUnitVO outdoorUnitVO = new OutdoorUnitVO();

			rowData = (Object[]) itr.next();

			long iduId = (Long) rowData[2];

			if (iduId > 0) {
				indoorUnitVO.setGroupId(rowData[1] != null ? (Long) rowData[1]
						: null);
				indoorUnitVO.setId((Long) rowData[2]);
				indoorUnitVO.setTemprature(rowData[4] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(((Double) rowData[4]))
						: null);
				indoorUnitVO.setMode(rowData[5] == null ? BizConstants.HYPHEN
						: DaoConstants.Mode.valueOf((Integer) rowData[5])
								.toString());
				indoorUnitVO
						.setAlarmCount(rowData[6] != null ? ((Long) rowData[6])
								.toString() : BizConstants.HYPHEN);
				indoorUnitVO.setModelName((String) rowData[8]);
				indoorUnitVO.setAddress((String) rowData[10]);
				indoorUnitVO.setOduLabel((String) rowData[11]);
				indoorUnitVO
						.setSvgMinLatitude(rowData[12] != null ? (Double) rowData[12]
								: null);
				indoorUnitVO
						.setSvgMaxLatitude(rowData[13] != null ? (Double) rowData[13]
								: null);
				indoorUnitVO
						.setSvgMinLongitude(rowData[14] != null ? (Double) rowData[14]
								: null);
				indoorUnitVO
						.setSvgMaxLongitude(rowData[15] != null ? (Double) rowData[15]
								: null);
				indoorUnitVO
						.setLinkODUid(rowData[3] != null ? (Long) rowData[3]
								: null);

				if (rowData[25] != null
						&& StringUtils.isNotBlank(rowData[25].toString())) {
					if (StringUtils.equalsIgnoreCase(rowData[25].toString(),
							STRING_1))
						indoorUnitVO.setState(BizConstants.ON);
					else if (StringUtils.equalsIgnoreCase(
							rowData[25].toString(), STRING_0))
						indoorUnitVO.setState(BizConstants.OFF);
				}

				if (rowData[29] != null
						&& StringUtils.isNotBlank(rowData[29].toString())) {
					if (StringUtils.equalsIgnoreCase(rowData[29].toString(),
							STRING_1))
						indoorUnitVO.setEconaviMode(BizConstants.ECONAVI_ON);
					else if (StringUtils.equalsIgnoreCase(
							rowData[29].toString(), STRING_0))
						indoorUnitVO.setEconaviMode(BizConstants.ECONAVI_OFF);
				}
				if (rowData[30] != null) {
					indoorUnitVO
							.setRoomTemprature(CommonUtil
									.getFormattedValueUpToTwoDecimal((Double) rowData[30]));

				}

				indoorUnitVOList.add(indoorUnitVO);

			}

			long outdoorId = (Long) rowData[3];

			boolean isOduUnitExistsInList = outdoorUnits.add(outdoorId);

			if (isOduUnitExistsInList) {

				outdoorUnitVO.setId(outdoorId);
				outdoorUnitVO
						.setAlarmCount(rowData[7] != null ? ((Long) rowData[7])
								.toString() : STRING_0);
				outdoorUnitVO.setAddress((String) rowData[11]);
				outdoorUnitVO
						.setSvgMinLatitude(rowData[16] != null ? (Double) rowData[16]
								: null);
				outdoorUnitVO
						.setSvgMaxLatitude(rowData[17] != null ? (Double) rowData[17]
								: null);
				outdoorUnitVO
						.setSvgMinLongitude(rowData[18] != null ? (Double) rowData[18]
								: null);
				outdoorUnitVO
						.setSvgMaxLongitude(rowData[19] != null ? (Double) rowData[19]
								: null);

				outdoorUnitVO.setModelName((String) rowData[9]);

				if (BizConstants.ODU_TYPE_VRF
						.equalsIgnoreCase((String) rowData[32])) {
					outdoorUnitVO
							.setWorkingTime1(rowData[20] != null ? (Double) rowData[20]
									: null);
					outdoorUnitVO
							.setWorkingTime2(rowData[21] != null ? (Double) rowData[21]
									: null);
					outdoorUnitVO
							.setWorkingTime3(rowData[22] != null ? (Double) rowData[22]
									: null);
				} else {
					outdoorUnitVO
							.setWorkingTime1(rowData[31] != null ? (Double) rowData[31]
									: null);

				}
				if (rowData[23] != null) {
					outdoorUnitVO
							.setUtilizationRatio(CommonUtil
									.getFormattedValueUpToTwoDecimal((Double) rowData[23]));

				}

				outdoorUnitVO.setGroupId(((Integer) rowData[24]).longValue());

				outdoorUnitVOList.add(outdoorUnitVO);

			}

		}
	}
}
