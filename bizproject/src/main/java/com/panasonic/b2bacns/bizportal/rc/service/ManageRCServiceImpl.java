package com.panasonic.b2bacns.bizportal.rc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.concurrency.GlobalTaskPool;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Indoorunit;
import com.panasonic.b2bacns.bizportal.pf.common.Constants;
import com.panasonic.b2bacns.bizportal.rc.RCValidationParam;
import com.panasonic.b2bacns.bizportal.rc.ValidateRC;
import com.panasonic.b2bacns.bizportal.service.IndoorUnitsService;

/**
 * This class is an implementation of ManageRCService interface
 * 
 * @author simanchal.patra
 * @author fshan
 */
@Service
public class ManageRCServiceImpl implements ManageRCService {

	private static final Logger LOGGER = Logger
			.getLogger(ManageRCServiceImpl.class);
	//all change by shanf
	private static final StringBuilder SQL_GET_RC_VALIDATION_PARAM = new StringBuilder(
			"select idu.id as ID, sts.b16c as coolmodesettable, sts.b16d as drymodesettable, sts.b16h as heatmodesettable, sts.b16a as automodesettable, sts.b16f as fanmodesettable ,")
			.append("sts.b17h as highfansettable, sts.b17m as medfansettable, sts.b17l as lowfansettable, sts.b17at as autofansettable,")
			.append("sts.b21 as autooperationsettable,sts.b22 as energysavingsettable,sts.b23 as flapsettable,sts.b24 as swingsettable,")
			.append("sts.b20_h as fixedheatmode,sts.b20_c as fixedcoolmode,sts.b20_d as fixeddrymode,sts.b20_f as fixedfanmode,")
			.append("sts.b15c_u as templimituppercool,sts.B15c_l as templimitlowercool,sts.b15h_u as templimitupperheat,sts.B15h_l as templimitlowerheat,")
			.append("sts.B15d_u as templimitupperdry,sts.B15d_l as templimitlowerdry,sts.B15a_u as templimitupperauto,sts.B15a_l as templimitlowerauto,")
			.append("idu.parent_id as parentid, idu.centralcontroladdress ")
			.append("from indoorunits idu left outer join ct_statusinfo sts on idu.oid = sts.facl_id where idu.id in (:id)");

	@Autowired
	private RCOperationLogService rcOperationLogService;

	@Autowired
	private SQLDAO sqlDao;

	@Autowired
	private IndoorUnitsService indoorUnitsService;

	@Autowired
	private GlobalTaskPool globalTaskPool;

	@Autowired
	private PFStatusInfoService pfStatusInfoService;

	@Value("${rc.op.timeout.milisec}")
	private int rcOpTimeOutMilliSec;

	private void setTempRangeBasedOnMode(Map<Long, RCValidationParam> iduValidationMap, ValidateRC rcValidation) {
		Set<Integer> minTemp = null;

		Set<Integer> maxTemp = null;

		List<Integer> temperatureList = null;

		for (String mode : rcValidation.getMode_support_list()) {

			minTemp = new HashSet<Integer>();

			maxTemp = new HashSet<Integer>();

			temperatureList = new ArrayList<Integer>(2);

			switch (mode) {

			case "COOL":
				if (iduValidationMap != null && iduValidationMap.size() > 0) {
					for (Entry<Long, RCValidationParam> set : iduValidationMap.entrySet()) {
						RCValidationParam param = set.getValue();
						if (param.getTempLimitLowerCool() != null) {
							minTemp.add(param.getTempLimitLowerCool());
						}
						if (param.getTempLimitUpperCool() != null) {
							maxTemp.add(param.getTempLimitUpperCool());
						}
					}
					temperatureList.add(Collections.max(minTemp));
					temperatureList.add(Collections.min(maxTemp));
				}
				rcValidation.setTemp_range_cool(temperatureList);
				break;
			case "HEAT":
				if (iduValidationMap != null && iduValidationMap.size() > 0) {
					for (Entry<Long, RCValidationParam> set : iduValidationMap.entrySet()) {
						RCValidationParam param = set.getValue();
						if (param.getTempLimitLowerHeat() != null) {
							minTemp.add(param.getTempLimitLowerHeat());
						}
						if (param.getTempLimitUpperHeat() != null) {
							maxTemp.add(param.getTempLimitUpperHeat());
						}
					}
					temperatureList.add(Collections.max(minTemp));
					temperatureList.add(Collections.min(maxTemp));
				}
				rcValidation.setTemp_range_heat(temperatureList);
				break;
			case "DRY":
				if (iduValidationMap != null && iduValidationMap.size() > 0) {
					for (Entry<Long, RCValidationParam> set : iduValidationMap.entrySet()) {
						RCValidationParam param = set.getValue();
						if (param.getTempLimitLowerDry() != null) {
							minTemp.add(param.getTempLimitLowerDry());
						}
						if (param.getTempLimitUpperDry() != null) {
							maxTemp.add(param.getTempLimitUpperDry());
						}
					}
					temperatureList.add(Collections.max(minTemp));
					temperatureList.add(Collections.min(maxTemp));
				}
				rcValidation.setTemp_range_dry(temperatureList);
				break;
			case "AUTO":
				if (iduValidationMap != null && iduValidationMap.size() > 0) {
					for (Entry<Long, RCValidationParam> set : iduValidationMap.entrySet()) {
						RCValidationParam param = set.getValue();
						if (param.getTempLimitLowerAuto() != null) {
							minTemp.add(param.getTempLimitLowerAuto());
						}
						if (param.getTempLimitUpperAuto() != null) {
							maxTemp.add(param.getTempLimitUpperAuto());
						}
					}
					temperatureList.add(Collections.max(minTemp));
					temperatureList.add(Collections.min(maxTemp));
				}
				rcValidation.setTemp_range_auto(temperatureList);
				break;
			case "FAN":
				rcValidation.setTemp_range_fan(temperatureList);
				break;
			default:
				break;
			}
		}
	}

	private Set<String> getSupportedFanSpeedList(boolean highFanSettable, boolean medFanSettable,
			boolean lowFanSettable, boolean autoFanSettable) {
		Set<String> supportedFanSpeedList = new HashSet<String>();
		if (highFanSettable) {supportedFanSpeedList.add(Constants.HIGH);}
		if (medFanSettable) {supportedFanSpeedList.add(Constants.MEDIUM);}
		if (lowFanSettable) {supportedFanSpeedList.add(Constants.LOW);}
		if (autoFanSettable) {supportedFanSpeedList.add(Constants.AUTO);}
		return supportedFanSpeedList;
	}

	private Set<String> getSupportedFlapList(boolean flapSettable, boolean swingFanSettable, ValidateRC rcValidation) {
		Set<String> windDirctionList = new HashSet<String>();
		Set<String> temprryFlap = new HashSet<String>();

		if (swingFanSettable) {windDirctionList.add(Constants.SWING);}
		
		if (flapSettable) {
			windDirctionList.addAll(getDefaultSupportableFlap());
			rcValidation.setFlap_range_auto_heat(windDirctionList);
			temprryFlap = getDefaultSupportableFlap();
			temprryFlap.remove(Constants.F4);
			temprryFlap.remove(Constants.F5);
			if (swingFanSettable) {
				temprryFlap.add(Constants.SWING);
			}
			rcValidation.setFlap_range_auto_cool(temprryFlap);
			rcValidation.setFlap_range_auto(windDirctionList);
			rcValidation.setFlap_range_cool(temprryFlap);
			rcValidation.setFlap_range_dry(temprryFlap);

			rcValidation.setFlap_range_fan(windDirctionList);
			rcValidation.setFlap_range_heat(windDirctionList);
		}

		
		return windDirctionList;
	}

	private List<String> getSupportedModeList(boolean coolModeSettable, boolean dryModeSettable,
			boolean heatModeSettable, boolean autoModeSettable, boolean fanModeSettable) {
		List<String> supportedModeList = new ArrayList<String>();
		if (coolModeSettable) {supportedModeList.add(Constants.COOL);}
		if (dryModeSettable) {supportedModeList.add(Constants.DRY);}
		if (heatModeSettable) {supportedModeList.add(Constants.HEAT);}
		if (autoModeSettable) {supportedModeList.add(Constants.AUTO);}
		if (fanModeSettable) {supportedModeList.add(Constants.FAN);}
		return supportedModeList;
	}
	
	private Map<Long, RCValidationParam> getValidationParam(List<Long> unitIDs) {
		Map<Long, RCValidationParam> iduValidationMap = new HashMap<Long, RCValidationParam>();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", unitIDs);

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put("ID", StandardBasicTypes.LONG);				//0
		scalarMapping.put("coolmodesettable", StandardBasicTypes.INTEGER);//1
		scalarMapping.put("drymodesettable", StandardBasicTypes.INTEGER);//2
		scalarMapping.put("heatmodesettable", StandardBasicTypes.INTEGER);//3
		scalarMapping.put("automodesettable", StandardBasicTypes.INTEGER);//4
		scalarMapping.put("fanmodesettable", StandardBasicTypes.INTEGER);//5
		scalarMapping.put("highfansettable", StandardBasicTypes.INTEGER);//6
		scalarMapping.put("medfansettable", StandardBasicTypes.INTEGER);//7
		scalarMapping.put("lowfansettable", StandardBasicTypes.INTEGER);//8
		scalarMapping.put("autofansettable", StandardBasicTypes.INTEGER);//9
		scalarMapping.put("autooperationsettable", StandardBasicTypes.INTEGER);//10
		scalarMapping.put("energysavingsettable", StandardBasicTypes.INTEGER);//11
		scalarMapping.put("flapsettable", StandardBasicTypes.INTEGER);//12
		scalarMapping.put("swingsettable", StandardBasicTypes.INTEGER);//13
		scalarMapping.put("fixedheatmode", StandardBasicTypes.INTEGER);//14
		scalarMapping.put("fixedcoolmode", StandardBasicTypes.INTEGER);//15
		scalarMapping.put("fixeddrymode", StandardBasicTypes.INTEGER);//16
		scalarMapping.put("fixedfanmode", StandardBasicTypes.INTEGER);//17
		
		scalarMapping.put("templimituppercool", StandardBasicTypes.INTEGER);//18
		scalarMapping.put("templimitlowercool", StandardBasicTypes.INTEGER);//19
		scalarMapping.put("templimitupperheat", StandardBasicTypes.INTEGER);//20
		scalarMapping.put("templimitlowerheat", StandardBasicTypes.INTEGER);//21
		scalarMapping.put("templimitupperdry", StandardBasicTypes.INTEGER);//22
		scalarMapping.put("templimitlowerdry", StandardBasicTypes.INTEGER);//23
		scalarMapping.put("templimitupperauto", StandardBasicTypes.INTEGER);//24
		scalarMapping.put("templimitlowerauto", StandardBasicTypes.INTEGER);//25
		
		scalarMapping.put("parentid", StandardBasicTypes.LONG);//26
		scalarMapping.put("centralcontroladdress", StandardBasicTypes.STRING);//27
		
		List<?> resultList = sqlDao.executeSQLSelect(
				SQL_GET_RC_VALIDATION_PARAM.toString(), scalarMapping,
				parameters);
		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			RCValidationParam param = null;
			Object[] rowData = null;

			while (itr.hasNext()) {
				param = new RCValidationParam();
				rowData = (Object[]) itr.next();
				
				param.setId((Long) rowData[0]);
				//add by shanf:check null value, if have null value, no need to show rc dialog
				// Modified by Ravi to skip Energy Settable mode
				for (int i = 1; i < 26; i++) {
					if (rowData[i] == null && i != 11) {
						return null;
					}
					
				}
				
				param.setCoolModeSettable(convertIntegerToBoolean((Integer) rowData[1]));
				param.setDryModeSettable(convertIntegerToBoolean((Integer) rowData[2]));
				param.setHeatModeSettable(convertIntegerToBoolean((Integer) rowData[3]));
				param.setAutoModeSettable(convertIntegerToBoolean((Integer) rowData[4]));
				param.setFanModeSettable(convertIntegerToBoolean((Integer) rowData[5]));
				param.setHighFanSettable(convertIntegerToBoolean((Integer) rowData[6]));
				param.setMedFanSettable(convertIntegerToBoolean((Integer) rowData[7]));
				param.setLowFanSettable(convertIntegerToBoolean((Integer) rowData[8]));
				param.setAutoFanSettable(convertIntegerToBoolean((Integer) rowData[9]));
				param.setAutoOperationSettable(convertIntegerToBoolean((Integer) rowData[10]));
				// Modified by ravi to handle null as we skip this from validation
				param.setEnergySavingSettable(convertIntegerToBoolean(rowData[11] == null ? 0 : (Integer) rowData[11]));
				param.setFlapSettable(convertIntegerToBoolean((Integer) rowData[12]));
				param.setSwingFanSettable(convertIntegerToBoolean((Integer) rowData[13]));
				param.setFixedHeatMode(convertIntegerToBoolean((Integer) rowData[14]));
				param.setFixedCoolMode(convertIntegerToBoolean((Integer) rowData[15]));
				param.setFixedDryMode(convertIntegerToBoolean((Integer) rowData[16]));
				param.setFixedFanMode(convertIntegerToBoolean((Integer) rowData[17]));
				
				param.setTempLimitUpperCool(rowData[18] == null ? null
						: (Integer) rowData[18]);
				param.setTempLimitLowerCool(rowData[19] == null ? null
						: (Integer) rowData[19]);
				param.setTempLimitUpperHeat(rowData[20] == null ? null
						: (Integer) rowData[20]);
				param.setTempLimitLowerHeat(rowData[21] == null ? null
						: (Integer) rowData[21]);
				param.setTempLimitUpperDry(rowData[22] == null ? null
						: (Integer) rowData[22]);
				param.setTempLimitLowerDry(rowData[23] == null ? null
						: (Integer) rowData[23]);
				param.setTempLimitUpperAuto(rowData[24] == null ? null
						: (Integer) rowData[24]);
				param.setTempLimitLowerAuto(rowData[25] == null ? null
						: (Integer) rowData[25]);
				param.setParentId(rowData[26] == null ? 0
						: (Long) rowData[26]);
				param.setCentralControlAddress(rowData[27] == null ? ""
						: (String) rowData[27]);
				iduValidationMap.put(param.getId(), param);
			}
		}
		return iduValidationMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dashboard.service.ManageRCService#
	 * getControlRC(java.util.List)
	 */
	@Override
	public ValidateRC getRCValidation(List<Long> unitIDs) {

		Map<Long,RCValidationParam> iduValidationMap = getValidationParam(unitIDs);
		
		//add by shanf
		if (iduValidationMap == null) {
			ValidateRC invalidRC = new ValidateRC();
			invalidRC.setValidStatus(false);
			return invalidRC;
		}
		// Don't initialize to false, otherwise the result will always be false
		// even though all are true
		boolean defaultStatus = unitIDs.size() > 0;
		boolean coolModeSettable = defaultStatus;
		boolean dryModeSettable = defaultStatus;
		boolean heatModeSettable = defaultStatus;
		boolean autoModeSettable = defaultStatus;
		boolean fanModeSettable = defaultStatus;
		boolean highFanSettable = defaultStatus;
		boolean medFanSettable = defaultStatus;
		boolean lowFanSettable = defaultStatus;
		boolean autoFanSettable = defaultStatus;
		boolean energySavingSettable = defaultStatus;
		boolean flapSettable = defaultStatus;
		boolean swingFanSettable = defaultStatus;
		boolean prohibitionMaster = defaultStatus;
		
		for (Entry<Long, RCValidationParam> set : iduValidationMap.entrySet()) {
			RCValidationParam param = set.getValue();
			if (param != null) {
				//If (isFixedCoolMode == 0), cool button is disabled in Mode
				coolModeSettable = coolModeSettable && param.isCoolModeSettable() && param.isFixedCoolMode();
				dryModeSettable = dryModeSettable && param.isDryModeSettable() && param.isFixedDryMode();
				heatModeSettable = heatModeSettable && param.isHeatModeSettable() && param.isFixedHeatMode();
				autoModeSettable = autoModeSettable && param.isAutoModeSettable() && param.isAutoOperationSettable();
				fanModeSettable = fanModeSettable && param.isFanModeSettable() && param.isFixedFanMode();
				
				highFanSettable = highFanSettable && param.isHighFanSettable();
				medFanSettable = medFanSettable && param.isMedFanSettable();
				lowFanSettable = lowFanSettable && param.isLowFanSettable();
				autoFanSettable = autoFanSettable && param.isAutoFanSettable();
				
				energySavingSettable = energySavingSettable && param.isEnergySavingSettable();
				flapSettable = flapSettable && param.isFlapSettable();
				swingFanSettable = swingFanSettable && param.isSwingFanSettable();
				//only check parent
				if (param.getParentId() == 0) {
					String ccAddress = param.getCentralControlAddress();
					boolean validCCAddress = true;
					if (ccAddress == null || "0".equals(ccAddress) || "".equals(ccAddress)
							|| "null".equals(ccAddress) || "99".equals(ccAddress)) {
						validCCAddress = false;
					}
					prohibitionMaster = prohibitionMaster && validCCAddress;
				}
			}
			
		}
		
		ValidateRC rcValidation = new ValidateRC();
		List<String> supportedModeList = getSupportedModeList(coolModeSettable, dryModeSettable, heatModeSettable,
				autoModeSettable, fanModeSettable);
		rcValidation.setMode_support_list(supportedModeList);
		
		Set<String> windDirctionList = getSupportedFlapList(flapSettable, swingFanSettable, rcValidation);
		rcValidation.setWindDirection_list(windDirctionList);

		Set<String> supportedFanSpeedList = getSupportedFanSpeedList(highFanSettable, medFanSettable, lowFanSettable,
				autoFanSettable);
		rcValidation.setFan_speed_list(supportedFanSpeedList);
		
		setTempRangeBasedOnMode(iduValidationMap, rcValidation);
		
		rcValidation.setProhibition_MASTER(convertBooleanToInt(prohibitionMaster));
		
		rcValidation.setPowerStatus_support(1);
		rcValidation.setTemperature_support(1);
		rcValidation.setMode_support(1);
		rcValidation.setFanSpeed_support(1);
		rcValidation.setWindDirection_support(convertBooleanToInt(flapSettable));
		rcValidation.setEnergySaving_support(convertBooleanToInt(energySavingSettable));
		
		return rcValidation;
	}

	private static Set<String> getDefaultSupportableFlap() {
		Set<String> supportedFlap = new HashSet<String>();
		supportedFlap.add(Constants.F1);
		supportedFlap.add(Constants.F2);
		supportedFlap.add(Constants.F3);
		supportedFlap.add(Constants.F4);
		supportedFlap.add(Constants.F5);
		return supportedFlap;
	}

	private int convertBooleanToInt(Boolean val) {
		if (val) {
			return 1;
		} else {
			return 0;
		}
	}

	private boolean convertIntegerToBoolean(Integer val) {
		boolean bool = false;
		if (val != null) {
			if (val == 1) {
				bool = true;
			} else if (val == 0) {
				bool = false;
			}
		}
		return bool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.dashboard.service.ManageRCService#
	 * setControlRC(java.util.List, java.util.Map)
	 */
	@Override
	public boolean setControlRC(Long userID, Set<Long> indoorUnits,
			Map<String, String> operationListMap)
			throws GenericFailureException {
		boolean actionStatus = false;

		try {

			actionStatus = executeIDURCOperation(indoorUnits, operationListMap,
					userID);

		} catch (Exception ex) {
			LOGGER.error(
					"Error ocurred while exectuing RC operation by User ID "
							+ userID, ex);
			actionStatus = false;
		}

		return actionStatus;
	}

	private Boolean executeIDURCOperation(Set<Long> iduIdList,
			Map<String, String> operationListMap, Long userID) {

		Boolean isSuccess = Boolean.TRUE;
		List<Long> iduList = new ArrayList<Long>();
		iduList.addAll(iduIdList);
		List<Indoorunit> indoorunitList = indoorUnitsService
				.getIndoorUnitsBatchByIds(iduList);

		if (operationListMap != null && !operationListMap.isEmpty()
				&& iduIdList != null && !iduIdList.isEmpty()) {
//			long start = System.currentTimeMillis();
			//add by shanf
//			CountDownLatch signal = new CountDownLatch(indoorunitList.size());
			for (Indoorunit indoorunit : indoorunitList) {

				String faclId = indoorunit.getOid();

				try {

					RCOperationExecutor rcOperation = new RCOperationExecutor(
							operationListMap, faclId, String.valueOf(indoorunit
									.getId()), this.pfStatusInfoService,
							this.rcOperationLogService, userID, rcOpTimeOutMilliSec);

					//add by shanf
//					rcOperation.setSignal(signal);
//					RCOperationExecutorTest rcOperation = new RCOperationExecutorTest(
//							operationListMap, faclId, String.valueOf(indoorunit
//									.getId()), this.pfStatusInfoService,
//							this.rcOperationLogService, userID);


					globalTaskPool.executeTask(rcOperation);

				} catch (Exception e) {
					isSuccess = Boolean.FALSE;
					LOGGER.info(
							String.format(
									"Error occured while executing operation for Aircon [ id: %s, facl id : %s ]",
									String.valueOf(indoorunit.getId()), faclId),
							e);

				}

			}			
		}
		return isSuccess;
	}

}
