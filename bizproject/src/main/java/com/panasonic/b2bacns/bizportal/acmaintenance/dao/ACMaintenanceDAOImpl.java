package com.panasonic.b2bacns.bizportal.acmaintenance.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceSettingVO;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceUserVO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

@Service
public class ACMaintenanceDAOImpl implements ACMaintenanceDAO {

	private static final Logger logger = Logger
			.getLogger(ACMaintenanceDAOImpl.class);

	@Autowired
	private SQLDAO sqlDao;

	private static final String ID = "id";
	private static final String MAP_THRESHOLD = "threshold";
	private static final String MAP_NAME = "name";
	private static final String MAP_EMAIL = "email";
	private static final String MAP_SITEID = "siteid";
	private static final String MAP_VRFHRS1 = "vrfhrs1";
	private static final String MAP_VRFHRS2 = "vrfhrs2";
	private static final String MAP_VRFHRS3 = "vrfhrs3";
	private static final String MAP_PACHRS = "pachrs";
	private static final String MAP_GHPOIL = "ghpoil";
	private static final String MAP_GHPHRS = "ghphrs";
	private static final String MAP_THRESHOLD_1 = "threshold_1";
	private static final String MAP_THRESHOLD_2 = "threshold_2";
	private static final String MAP_THRESHOLD_3 = "threshold_3";
	private static final String MAP_THRESHOLD_4 = "threshold_4";
	private static final String MAP_THRESHOLD_5 = "threshold_5";
	private static final String MAP_THRESHOLD_6 = "threshold_6";
	private static final String MAP_COMPRESSOR1 = "compressor1";
	private static final String MAP_COMPRESSOR2 = "compressor2";
	private static final String MAP_COMPRESSOR3 = "compressor3";
	private static final String HRS = "hrs";
	private static final String HRS0 = "0hrs";
	private static final String MAP_TYPE = "type";

	private static final StringBuilder SQL_GET_MAINTENANCE_SETTING = new StringBuilder(
			"select ms.id,ms.threshold, mm.description as name from maintenance_setting ms join maintenance_type_master mm on ms.maintenance_type_id=mm.id where group_id=:siteid");
	private static final StringBuilder SQL_GET_MAINTENANCE_USER = new StringBuilder(
			"select id, user_mail_id as email from maintenance_user_list where company_id =:id");

	private static final StringBuilder SQL_GET_MAINT_STATUS_DATA = new StringBuilder(
			"select odu.id as id,odu.type as type,max(maint.ghp_engine_operation_hours) as ghphrs,")
			.append("max(maint.ghp_time_after_oil_change) as ghpoil,max(maint.pac_comp_operation_hours) as pachrs,")
			.append("max(maint.vrf_comp_operation_hours_1) as vrfhrs1,max(maint.vrf_comp_operation_hours_2) as vrfhrs2,")
			.append("max(maint.vrf_comp_operation_hours_3) as vrfhrs3,")
			.append("max(case when setting.maintenance_type_id in(1)then setting.threshold else null end) as threshold_1,")
			.append("max(case when setting.maintenance_type_id in(2)then setting.threshold else null end) as threshold_2,")
			.append("max(case when setting.maintenance_type_id in(3)then setting.threshold else null end) as threshold_3,")
			.append("max(case when setting.maintenance_type_id in(4)then setting.threshold else null end) as threshold_4,")
			.append("max(case when setting.maintenance_type_id in(5)then setting.threshold else null end) as threshold_5,")
			.append("max(case when setting.maintenance_type_id in(6)then setting.threshold else null end) as threshold_6,")
			.append("odu.compressor1,odu.compressor2,odu.compressor3 ")
			.append("from outdoorunits odu left outer join maintenance_status_data maint on odu.id=maint.outdoorunit_id ")
			.append("left outer join groups g on g.uniqueid =  odu.siteid left outer join maintenance_setting setting on g.id =  setting.group_id ")
			.append("where odu.id=:id group by odu.id,odu.type ");

	private static final StringBuilder SQL_UPDATE_MAINT_STATUS_VRF1 = new StringBuilder(
			"update maintenance_status_data maint set vrf_comp_operation_hours_1 = threshold from maintenance_setting ms left join groups g on ms.group_id=g.id ")
			.append("left join outdoorunits odu on odu.siteid=g.uniqueid where odu.id=%d and ms.maintenance_type_id=1 and maint.outdoorunit_id =%d and odu.compressor1 = true");
	private static final StringBuilder SQL_UPDATE_MAINT_STATUS_VRF2 = new StringBuilder(
			"update maintenance_status_data maint set vrf_comp_operation_hours_2 = threshold from maintenance_setting ms left join groups g on ms.group_id=g.id ")
			.append("left join outdoorunits odu on odu.siteid=g.uniqueid where odu.id=%d and ms.maintenance_type_id=2 and maint.outdoorunit_id =%d and odu.compressor2 = true");

	private static final StringBuilder SQL_UPDATE_MAINT_STATUS_VRF3 = new StringBuilder(
			"update maintenance_status_data maint set vrf_comp_operation_hours_3 = threshold from maintenance_setting ms left join groups g on ms.group_id=g.id ")
			.append("left join outdoorunits odu on odu.siteid=g.uniqueid where odu.id=%d and ms.maintenance_type_id=3 and maint.outdoorunit_id =%d and odu.compressor3 = true");

	private static final StringBuilder SQL_UPDATE_MAINT_STATUS_PAC = new StringBuilder(
			"update maintenance_status_data maint set pac_comp_operation_hours = threshold from maintenance_setting ms left join groups g on ms.group_id=g.id ")
			.append("left join outdoorunits odu on odu.siteid=g.uniqueid where odu.id=%d and ms.maintenance_type_id=4 and maint.outdoorunit_id =%d");

	private static final StringBuilder SQL_UPDATE_MAINT_STATUS_GHPOIL = new StringBuilder(
			"update maintenance_status_data maint set ghp_time_after_oil_change = threshold from maintenance_setting ms left join groups g on ms.group_id=g.id ")
			.append("left join outdoorunits odu on odu.siteid=g.uniqueid where odu.id=%d and ms.maintenance_type_id=6 and maint.outdoorunit_id =%d");

	private static final StringBuilder SQL_UPDATE_MAINT_STATUS_GHPHRS = new StringBuilder(
			"update maintenance_status_data maint set ghp_engine_operation_hours = threshold from maintenance_setting ms left join groups g on ms.group_id=g.id ")
			.append("left join outdoorunits odu on odu.siteid=g.uniqueid where odu.id=%d and ms.maintenance_type_id=5 and maint.outdoorunit_id =%d");

	private static final StringBuilder SQL_UPDATE_NOTIFICATIONLOG = new StringBuilder(
			"update notificationlog set status = '%s',fixed_time = '%s', updatedate = '%s',updatedby = '%s' where 	lower(status) = '%s' and lower(code) = '%s' and outdoorunit_id = %d");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.dao.ACMaintenanceDAO#
	 * getMaintenanceSetting(java.lang.Long)
	 */
	@Override
	public List<ACMaintenanceSettingVO> getMaintenanceSetting(Long siteID) {

		logger.debug("Calling the AC Maintenance Setting");

		List<ACMaintenanceSettingVO> acMaintenanceVOList = new ArrayList<ACMaintenanceSettingVO>();
		ACMaintenanceSettingVO acMaintenanceObj;

		String query = SQL_GET_MAINTENANCE_SETTING.toString();

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(ID, StandardBasicTypes.LONG); // 0
		scalarMapping.put(MAP_NAME, StandardBasicTypes.STRING); // 1
		scalarMapping.put(MAP_THRESHOLD, StandardBasicTypes.LONG); // 2

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(MAP_SITEID, siteID);

		try {
			List<?> result = sqlDao.executeSQLSelect(query, scalarMapping,
					parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					acMaintenanceObj = new ACMaintenanceSettingVO();

					rowData = (Object[]) itr.next();

					acMaintenanceObj.setId((Long) rowData[0]);
					acMaintenanceObj
							.setName(rowData[1] == null ? BizConstants.HYPHEN
									: (String) rowData[1]);
					acMaintenanceObj.setValue(rowData[2] == null ? null
							: (Long) rowData[2]);
					acMaintenanceVOList.add(acMaintenanceObj);
				}

			}

		} catch (Exception sqlExp) {
			logger.error(
					String.format("An Exception occured while fetching"
							+ " AC Maintenance Setting Details for Site ID %d",
							siteID), sqlExp);
		}
		return acMaintenanceVOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.dao.ACMaintenanceDAO#
	 * getCurrentRemainingMaintenanceTime(java.lang.Long)
	 */
	@Override
	public List<ACMaintenanceSettingVO> getCurrentRemainingMaintenanceTime(
			Long outdoorID) {
		logger.debug("Calling the AC Maintenance Status Data");

		List<ACMaintenanceSettingVO> acMaintenanceVOList = new ArrayList<ACMaintenanceSettingVO>();
		ACMaintenanceSettingVO acMaintenanceObj;

		String query = SQL_GET_MAINT_STATUS_DATA.toString();

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(ID, StandardBasicTypes.LONG); // 0 NOT IN USE
		scalarMapping.put(MAP_TYPE, StandardBasicTypes.STRING); // 1
		scalarMapping.put(MAP_VRFHRS1, StandardBasicTypes.LONG); // 2
		scalarMapping.put(MAP_VRFHRS2, StandardBasicTypes.LONG); // 3
		scalarMapping.put(MAP_VRFHRS3, StandardBasicTypes.LONG); // 4
		scalarMapping.put(MAP_PACHRS, StandardBasicTypes.LONG); // 5
		scalarMapping.put(MAP_GHPOIL, StandardBasicTypes.LONG); // 6
		scalarMapping.put(MAP_GHPHRS, StandardBasicTypes.LONG); // 7
		scalarMapping.put(MAP_THRESHOLD_1, StandardBasicTypes.LONG); // 8
		scalarMapping.put(MAP_THRESHOLD_2, StandardBasicTypes.LONG); // 9
		scalarMapping.put(MAP_THRESHOLD_3, StandardBasicTypes.LONG); // 10
		scalarMapping.put(MAP_THRESHOLD_4, StandardBasicTypes.LONG); // 11
		scalarMapping.put(MAP_THRESHOLD_5, StandardBasicTypes.LONG); // 12
		scalarMapping.put(MAP_THRESHOLD_6, StandardBasicTypes.LONG); // 13
		scalarMapping.put(MAP_COMPRESSOR1, StandardBasicTypes.BOOLEAN); // 14
		scalarMapping.put(MAP_COMPRESSOR2, StandardBasicTypes.BOOLEAN); // 15
		scalarMapping.put(MAP_COMPRESSOR3, StandardBasicTypes.BOOLEAN); // 16

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(ID, outdoorID);

		try {
			List<?> result = sqlDao.executeSQLSelect(query, scalarMapping,
					parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					rowData = (Object[]) itr.next();

					// Long outdoorId = (Long) rowData[0];

					if (rowData[1] != null) {
						switch (((String) rowData[1]).toUpperCase()) {
						case BizConstants.ODU_TYPE_VRF:

							// Comp 1
							acMaintenanceObj = new ACMaintenanceSettingVO();
							acMaintenanceObj.setId(1l);
							acMaintenanceObj
									.setName(BizConstants.OutdoorMaintTypeRemaining
											.valueOf(1).toString());
							if (rowData[14] != null && (Boolean) rowData[14]) {
								acMaintenanceObj
										.setValue(rowData[2] == null ? rowData[8] == null ? BizConstants.HYPHEN
												: ((Long) rowData[8])
														.toString().concat(HRS)
												: ((Long) rowData[2]) < 0 ? HRS0
														: ((Long) rowData[2])
																.toString()
																.concat(HRS));
								acMaintenanceVOList.add(acMaintenanceObj);
							} else {
								acMaintenanceObj.setValue(BizConstants.HYPHEN);
							}

							// Comp 2
							acMaintenanceObj = new ACMaintenanceSettingVO();
							acMaintenanceObj.setId(2l);
							acMaintenanceObj
									.setName(BizConstants.OutdoorMaintTypeRemaining
											.valueOf(2).toString());
							if (rowData[15] != null && (Boolean) rowData[15]) {
								acMaintenanceObj
										.setValue(rowData[3] == null ? rowData[9] == null ? BizConstants.HYPHEN
												: ((Long) rowData[9])
														.toString().concat(HRS)
												: ((Long) rowData[3]) < 0 ? HRS0
														: ((Long) rowData[3])
																.toString()
																.concat(HRS));

								acMaintenanceVOList.add(acMaintenanceObj);
							} else {
								acMaintenanceObj.setValue(BizConstants.HYPHEN);
							}

							// Comp 3
							acMaintenanceObj = new ACMaintenanceSettingVO();
							acMaintenanceObj.setId(3l);
							acMaintenanceObj
									.setName(BizConstants.OutdoorMaintTypeRemaining
											.valueOf(3).toString());
							if (rowData[14] != null && (Boolean) rowData[14]) {
								acMaintenanceObj
										.setValue(rowData[4] == null ? rowData[10] == null ? BizConstants.HYPHEN
												: ((Long) rowData[10])
														.toString().concat(HRS)
												: ((Long) rowData[4]) < 0 ? HRS0
														: ((Long) rowData[4])
																.toString()
																.concat(HRS));
								acMaintenanceVOList.add(acMaintenanceObj);
							} else {
								acMaintenanceObj.setValue(BizConstants.HYPHEN);
							}
							break;
						case BizConstants.ODU_TYPE_PAC:
							// PAC Comp
							acMaintenanceObj = new ACMaintenanceSettingVO();
							acMaintenanceObj.setId(4l);
							acMaintenanceObj
									.setName(BizConstants.OutdoorMaintTypeRemaining
											.valueOf(4).toString());
							acMaintenanceObj
									.setValue(rowData[5] == null ? rowData[11] == null ? BizConstants.HYPHEN
											: ((Long) rowData[11]).toString()
													.concat(HRS)
											: ((Long) rowData[5]) < 0 ? HRS0
													: ((Long) rowData[5])
															.toString().concat(
																	HRS));
							acMaintenanceVOList.add(acMaintenanceObj);

							break;
						case BizConstants.ODU_TYPE_GHP:
							// GHP Oil
							acMaintenanceObj = new ACMaintenanceSettingVO();
							acMaintenanceObj.setId(6l);
							acMaintenanceObj
									.setName(BizConstants.OutdoorMaintTypeRemaining
											.valueOf(6).toString());
							acMaintenanceObj
									.setValue(rowData[6] == null ? rowData[13] == null ? BizConstants.HYPHEN
											: ((Long) rowData[13]).toString()
													.concat(HRS)
											: ((Long) rowData[6]) < 0 ? HRS0
													: ((Long) rowData[6])
															.toString().concat(
																	HRS));
							acMaintenanceVOList.add(acMaintenanceObj);
							// GHP Engine
							acMaintenanceObj = new ACMaintenanceSettingVO();
							acMaintenanceObj.setId(5l);
							acMaintenanceObj
									.setName(BizConstants.OutdoorMaintTypeRemaining
											.valueOf(5).toString());
							acMaintenanceObj
									.setValue(rowData[7] == null ? rowData[12] == null ? BizConstants.HYPHEN
											: ((Long) rowData[12]).toString()
													.concat(HRS)
											: ((Long) rowData[7]) < 0 ? HRS0
													: ((Long) rowData[7])
															.toString().concat(
																	HRS));
							acMaintenanceVOList.add(acMaintenanceObj);
							break;

						}
					}
				}

			}

		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while fetching"
					+ " AC Maintenance Status Data for Outdoor ID %d",
					outdoorID), sqlExp);
		}
		return acMaintenanceVOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.dao.ACMaintenanceDAO#
	 * getMaintenanceSettingMailList(java.lang.Long)
	 */
	@Override
	public List<ACMaintenanceUserVO> getMaintenanceSettingMailList(
			Long maintenanceTypeID) {
		logger.debug("Calling the AC Maintenance User Details");

		List<ACMaintenanceUserVO> acMaintenanceUserList = new ArrayList<ACMaintenanceUserVO>();
		ACMaintenanceUserVO acMaintenanceUserObj;

		String query = SQL_GET_MAINTENANCE_USER.toString();

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(ID, StandardBasicTypes.LONG); // 0
		scalarMapping.put(MAP_EMAIL, StandardBasicTypes.STRING); // 1

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(ID, maintenanceTypeID);

		try {
			List<?> result = sqlDao.executeSQLSelect(query, scalarMapping,
					parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					acMaintenanceUserObj = new ACMaintenanceUserVO();

					rowData = (Object[]) itr.next();

					acMaintenanceUserObj.setId((Long) rowData[0]);
					acMaintenanceUserObj
							.setEmail(rowData[1] == null ? BizConstants.HYPHEN
									: (String) rowData[1]);
					acMaintenanceUserList.add(acMaintenanceUserObj);
				}

			}

		} catch (Exception sqlExp) {
			logger.error(
					String.format(
							"An Exception occured while fetching"
									+ " AC Maintenance Setting User Details for Setting type ID %d",
							maintenanceTypeID), sqlExp);
		}
		return acMaintenanceUserList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.dao.ACMaintenanceDAO#
	 * resetThresholdAlert(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean resetThresholdAlert(Long oduID, Long maintID, String timezone) {

		logger.debug("Reset AC Threshold Alert");

		String preAlarmType = BizConstants.EMPTY_STRING;

		String query = BizConstants.EMPTY_STRING;
		switch (maintID.intValue()) {
		case 1:
			query = SQL_UPDATE_MAINT_STATUS_VRF1.toString();
			preAlarmType = BizConstants.PREALARM_PRE01;
			break;
		case 2:
			query = SQL_UPDATE_MAINT_STATUS_VRF2.toString();
			preAlarmType = BizConstants.PREALARM_PRE01;
			break;
		case 3:
			query = SQL_UPDATE_MAINT_STATUS_VRF3.toString();
			preAlarmType = BizConstants.PREALARM_PRE01;
			break;
		case 4:
			query = SQL_UPDATE_MAINT_STATUS_PAC.toString();
			preAlarmType = BizConstants.PREALARM_PRE01;
			break;
		case 5:
			query = SQL_UPDATE_MAINT_STATUS_GHPHRS.toString();
			preAlarmType = BizConstants.PREALARM_PRE02;
			break;
		case 6:
			query = SQL_UPDATE_MAINT_STATUS_GHPOIL.toString();
			preAlarmType = BizConstants.PREALARM_PRE03;
			break;

		}

		String finalQuery = String.format(query, oduID, oduID);
		try {
			int updatedRows = sqlDao.executeSQLUpdateQuery(finalQuery);

			if (updatedRows > 0) {
				updateNotificationLog(oduID, preAlarmType, timezone);
				return true;
			}
		} catch (Exception sqlExp) {
			logger.error(
					String.format(
							"An Exception occured while fetching"
									+ " AC Maintenance Reset the AC Threshold Alert for Outdoor ID %d and Main Type Id %d",
							oduID, maintID), sqlExp);
		}
		return false;
	}

	private void updateNotificationLog(Long oduID, String preAlarmType,
			String timezone) {

		Calendar date = GregorianCalendar.getInstance(TimeZone.getDefault());
		long uTime = CommonUtil.toUTC(date.getTimeInMillis(),
				date.getTimeZone());
		long siteTime = CommonUtil.toLocalTime(uTime,
				TimeZone.getTimeZone(timezone));
		Calendar siteCal = Calendar.getInstance();
		siteCal.setTimeInMillis(siteTime);

		// dateFormat.format(siteCal.getTime());

		String fixedTime = CommonUtil.dateToString(siteCal.getTime(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSSS);
		// "2016-02-09 19:14:38.106";

		String query = String.format(SQL_UPDATE_NOTIFICATIONLOG.toString(),
				BizConstants.STATUS_FIXED, fixedTime, CommonUtil.dateToString(
						new Date(), BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSSS),
				BizConstants.UPDATED_BY_APPLICATION, BizConstants.STATUS_NEW,
				preAlarmType, oduID);

		try {
			int updatedRows = sqlDao.executeSQLUpdateQuery(query);

			if (updatedRows > 0) {
				logger.debug("Notificationlog updated for status = "
						+ BizConstants.STATUS_NEW + ", code = " + preAlarmType
						+ ", outdoorunit id = " + oduID);
			}
		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while updating"
					+ " Notificationlog for Outdoor ID %d", oduID), sqlExp);
		}

	}
}
