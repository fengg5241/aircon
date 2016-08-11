/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.panasonic.b2bacns.bizportal.rc.AirconModeValidation;
import com.panasonic.b2bacns.bizportal.rc.AirconTemperatureLimit;
import com.panasonic.b2bacns.bizportal.rc.CurrentAirconMode;
import com.panasonic.b2bacns.bizportal.rc.FanSpeed;
import com.panasonic.b2bacns.bizportal.rc.RCOperation;
import com.panasonic.b2bacns.bizportal.rc.WinDirection;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.spf.b2bac.remotecontrol.RemoteControlException;
import com.panasonic.spf.b2bac.remotecontrol.api.asia.indoor.ACIndoorUnit;
import com.panasonic.spf.b2bac.remotecontrol.api.asia.indoor.parameter.AirFlowPosition;
import com.panasonic.spf.b2bac.remotecontrol.api.asia.indoor.parameter.AirFlowVolume;
import com.panasonic.spf.b2bac.remotecontrol.api.asia.indoor.parameter.EnergySavingSettingMode;
import com.panasonic.spf.b2bac.remotecontrol.api.asia.indoor.parameter.OperationMode;

/**
 * @author Narendra.Kumar
 * @author simanchal.patra
 */

public class RCOperationExecutor implements Runnable {

	private static final Logger LOGGER = Logger
			.getLogger(RCOperationExecutor.class);

	private static final String SETTABLE_ENERGRY_SAVING_MODE_VALIDATION_PROP = "B22";

	private static final String SETTABLE_FLAP_DIRECTION_VALIDATION_PROP = "B23";

	private static final String SETTABLE_FLAP_SWING_VALIDATION_PROP = "B24";

	private static final String IDU_POWER_STATUS_PROP = "A1";

	private static final String IDU_MODE_PROP_1 = "A2_1";

	private static final String IDU_MODE_PROP_2 = "A2_2";

	private String faclId;
	private String iduId;
	private Long userId;
	private Map<String, String> operationListMap = null;

	private PFStatusInfoService pfStatusInfoService;

	private RCOperationLogService rcOperationLogService;

	private int rcOpTimeOutMilliSec;
	
	//add by shanf
	private CountDownLatch signal;

	public RCOperationExecutor() {

	}

	public RCOperationExecutor(Map<String, String> operationListMap,
			String faclId, String iduId,
			PFStatusInfoService pfStatusInfoService,
			RCOperationLogService rcOperationLogService, Long userId,
			int rcOpTimeOutMilliSec) {

		this.operationListMap = operationListMap;
		this.faclId = faclId;
		this.iduId = iduId;
		this.pfStatusInfoService = pfStatusInfoService;
		this.rcOperationLogService = rcOperationLogService;
		this.userId = userId;
		this.rcOpTimeOutMilliSec = rcOpTimeOutMilliSec;
	}

	@Override
	public void run() {
		Boolean isSuccess = executeOperation();
		Set<Long> indoorUnits = new HashSet<Long>();
		indoorUnits.add(Long.valueOf(iduId));
		rcOperationLogService.logRCoperationsPerformedByUser(userId,
				Long.valueOf(iduId), operationListMap, isSuccess);
	}

	public Boolean executeOperation() {

		ACIndoorUnit acu = null;
		Boolean isPowerON = Boolean.TRUE;
		String airconModeOperationValue = null;
		Boolean isSuccess = Boolean.TRUE;
		String operationValue = null;
		RCOperation rcOperation = null;

		if (operationListMap != null && !operationListMap.isEmpty()) {

			try {

				LOGGER.debug(String
						.format("Trying to open the connection for Aircon [ id : %s , facl Id: %s]",
								iduId, faclId));

				acu = new ACIndoorUnit(iduId, faclId, rcOpTimeOutMilliSec);
				acu.open();

				LOGGER.debug(String
						.format("Connection opened successfully for Aircon [ id : %s , facl Id: %s]",
								iduId, faclId));
				try {

					LOGGER.debug(String
							.format("Operations execution started for Aircon [ id : %s , facl Id: %s operations : %s]",
									iduId, faclId, operationListMap.toString()));

					if (operationListMap.containsKey(RCOperation.POWERSTATUS
							.name())) {

						operationValue = operationListMap
								.get(RCOperation.POWERSTATUS.name());

						if (StringUtils.equalsIgnoreCase(operationValue,
								BizConstants.ON)) {
							isPowerON = Boolean.TRUE;
						} else {
							isPowerON = Boolean.FALSE;
						}

						LOGGER.debug(String
								.format("Aircon [ id : %s , facl Id: %s Oeration : %s, value :%s] is  executing...",
										iduId, faclId, RCOperation.POWERSTATUS,
										operationValue));

						isSuccess = powerOperation(acu,
								RCOperation.POWERSTATUS, operationValue);

						LOGGER.debug(String
								.format("Aircon [ id : %s , facl Id: %s Oeration : %s, value :%s] exceution  finished",
										iduId, faclId, rcOperation,
										operationValue));

						if (operationListMap.size() == 1) {
							return Boolean.TRUE;
						}

					} else {
						if (!isOperationSupported(faclId, IDU_POWER_STATUS_PROP)) {
							isPowerON = Boolean.FALSE;
						} else {
							isPowerON = Boolean.TRUE;
						}
					}

					// if (isPowerON) {

					for (Entry<String, String> mapEntry : operationListMap
							.entrySet()) {

						rcOperation = RCOperation.valueOf(mapEntry.getKey());
						operationValue = mapEntry.getValue();

						LOGGER.debug(String
								.format("Aircon [ id : %s , facl Id: %s Oeration : %s, value :%s] is  executing...",
										iduId, faclId, rcOperation,
										operationValue));

						switch (rcOperation) {
						case POWERSTATUS:
							// Already executed before any other action
							// before this loop.
							break;
						case MODE:
							airconModeOperationValue = operationValue;
							isSuccess &= airconModeOperation(acu, rcOperation,
									operationValue);
							break;
						case TEMPERATURE:
							if (airconModeOperationValue == null) {
								if (operationListMap.get(RCOperation.MODE
										.name()) != null) {
									airconModeOperationValue = operationListMap
											.get(RCOperation.MODE.name());
								}
							}
							isSuccess &= temperatureOperation(acu, rcOperation,
									operationValue, airconModeOperationValue);
							break;
						case FANSPEED:
							isSuccess &= fanSpeedOperation(acu, rcOperation,
									operationValue);
							break;
						case WINDDIRECTION:
							isSuccess &= winDirectionOperation(acu,
									rcOperation, operationValue);
							break;
						case ENERGY_SAVING:
							isSuccess &= energySaving(acu, rcOperation,
									operationValue);
							break;
						case PROHIBITION_POWERSTATUS:
							isSuccess &= prohibitionPowerStatus(acu,
									rcOperation, operationValue);
							break;
						case PROHIBITON_MODE:
							isSuccess &= prohibitionMode(acu, rcOperation,
									operationValue);
							break;
						case PROHIBITION_FANSPEED:
							isSuccess &= prohibitionFanspeed(acu, rcOperation,
									operationValue);
							break;
						case PROHIBITION_WINDRIECTION:
							isSuccess &= prohibitionWindirection(acu,
									rcOperation, operationValue);
							break;
						case PROHIBITION_SET_TEMP:
							isSuccess &= prohibitionSetTemerature(acu,
									rcOperation, operationValue);
							break;
						case PROHIBITION_ENERGY_SAVING:
							isSuccess &= prohibitionEnergySaving(acu,
									rcOperation, operationValue);
							break;
						default:
							break;
						}
						LOGGER.debug(String
								.format("Aircon [ id : %s , facl Id: %s Oeration : %s, value :%s] exceution  finished",
										iduId, faclId, rcOperation,
										operationValue));
					}
					// } else {
					// LOGGER.debug(String
					// .format("Aircon [ id : %s , facl Id: %s] is off, No Other operation supported",
					// iduId, faclId));
					//
					// throw new UnsupportedOperationException(
					// String.format(
					// "Aircon [ id : %s , facl Id: %s] is off, No Other operation supported",
					// iduId, faclId));
					// }
				} catch (RemoteControlException e) {

					isSuccess = Boolean.FALSE;
					LOGGER.error(
							String.format(
									"Error occured while executing operation for Aircon [ id: %s, facl id : %s ]",
									iduId, faclId), e);
					throw e;
				} catch (Exception e) {

					isSuccess = Boolean.FALSE;
					LOGGER.info(
							String.format(
									"Error occured while executing operation for Aircon [ id: %s, facl id : %s ]",
									iduId, faclId), e);
					throw e;
				}

			} catch (RemoteControlException e) {

				isSuccess = Boolean.FALSE;
				//add by shanf, logger cannot work, check the real path when running in the AWS
				System.out.println(e.toString());
				LOGGER.error(
						String.format(
								"Error occured while opening the connection for Aircon [ id : %s , facl Id: %s]",
								iduId, faclId), e);

			} catch (Exception e) {

				isSuccess = Boolean.FALSE;
				LOGGER.error(
						String.format(
								"Error occured while opening operation for Aircon [ id: %s, facl id : %s ]",
								iduId, faclId), e);
			} finally {
				try {
					LOGGER.info(String
							.format("Trying to close the connection for Aircon [ id : %s , facl Id: %s]",
									iduId, faclId));

					acu.close();
					LOGGER.info(String
							.format("Connection closed successfully for Aircon [ id : %s , facl Id: %s]",
									iduId, faclId));

				} catch (Exception e) {

					isSuccess = Boolean.FALSE;
					LOGGER.error(
							String.format(
									"Error occured while closing the connection for Aircon [ id: %s, facl id : %s ]",
									iduId, faclId), e);
				}
			}

			LOGGER.info(String
					.format("Operations execution completed for Aircon [ id : %s , facl Id: %s operations : %s]",
							iduId, faclId, operationListMap.toString()));

		} else {

			isSuccess = Boolean.FALSE;
			LOGGER.info(String
					.format("No operation found to exceute for Aircon [ id: %s, facl id : %s ]",
							iduId, faclId));
		}
		return isSuccess;
	}

	private Boolean energySaving(ACIndoorUnit ac, RCOperation rcOperation,
			String operationValue) {

		Boolean isSuccess = false;
		Boolean isOerationSupported = Boolean.FALSE;
		try {
			isOerationSupported = isOperationSupported(faclId,
					SETTABLE_ENERGRY_SAVING_MODE_VALIDATION_PROP);

			if (isOerationSupported) {
				switch (operationValue) {
				case BizConstants.ONE:
					ac.setEnergySavingSettingMode(EnergySavingSettingMode.IN_POWER_SAVING_OPERATION);
					break;
				case BizConstants.ZERO:
					ac.setEnergySavingSettingMode(EnergySavingSettingMode.NOT_IN_POWER_SAVING_OPERATION);
					break;

				default:
					break;

				}
				isSuccess = true;
			} else {

				isSuccess = Boolean.FALSE;
				String errorMsg = String
						.format("Operation is not supported for Aircon [ id: %s, facl id : %s, Operation :%s ,value :%s, Property Id : %s]",
								iduId, faclId, rcOperation, operationValue,
								SETTABLE_ENERGRY_SAVING_MODE_VALIDATION_PROP);
				LOGGER.info(errorMsg);
			}
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s, value :%s, Property Id : %s]",
							iduId, faclId, rcOperation, operationValue,
							SETTABLE_ENERGRY_SAVING_MODE_VALIDATION_PROP), e);
			isSuccess = Boolean.FALSE;
		}
		return isSuccess;
	}

	public Boolean powerOperation(ACIndoorUnit ac, RCOperation rcOperation,
			String operationValue) throws RemoteControlException {

		Boolean isSuccess = false;

		try {
			switch (operationValue) {
			case BizConstants.ON:
				ac.setPower(Boolean.TRUE);
				break;
			case BizConstants.OFF:
				ac.setPower(Boolean.FALSE);
				break;
			default:
				break;
			}
			isSuccess = true;
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value:%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;

			// throw e;
		}
		return isSuccess;
	}

	public Boolean airconModeOperation(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue)
			throws RemoteControlException {

		AirconModeValidation airconMode = AirconModeValidation
				.getPropertyID(operationValue);

		Boolean isSuccess = false;

		if (airconMode != null) {
			String propertyId = airconMode.getPropertyID();
			try {

				if (isOperationSupported(faclId, propertyId)) {
					switch (airconMode) {
					case COOL:
						ac.setOperationMode(OperationMode.COOLING);
						break;
					case DRY:
						ac.setOperationMode(OperationMode.DRY);
						break;
					case HEAT:
						ac.setOperationMode(OperationMode.HEATING);
						break;
					case AUTO:
						ac.setOperationMode(OperationMode.AUTO);
						break;
					case FAN:
						ac.setOperationMode(OperationMode.FAN);
						break;

					default:
						break;
					}
					isSuccess = true;
				} else {

					isSuccess = Boolean.FALSE;
					String errorMsg = String
							.format("Operation is not supported for Aircon [ id: %s, facl id : %s, Operation :%s ,value :%s, Property Id : %s]",
									iduId, faclId, rcOperation, operationValue,
									propertyId);
					LOGGER.info(errorMsg);
					// throw new UnsupportedOperationException(errorMsg);

				}
			} catch (RemoteControlException e) {

				LOGGER.info(
						String.format(
								"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s, value :%s, Property Id : %s]",
								iduId, faclId, rcOperation, operationValue,
								propertyId), e);
				isSuccess = Boolean.FALSE;

				// throw e;
			}
		} else {

			isSuccess = Boolean.FALSE;
			String errorMsg = String
					.format("Operation is not supported for Aircon [ id: %s, facl id : %s, Operation :%s ,value :%s]",
							iduId, faclId, rcOperation, operationValue);
			LOGGER.info(errorMsg);
			// throw new UnsupportedOperationException(errorMsg);

		}
		return isSuccess;
	}

	public Boolean temperatureOperation(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue,
			String airconModeOperationValue) throws RemoteControlException {

		Boolean isSuccess = false;
		try {
			Float tempVal = Float.parseFloat(operationValue);

			if (varifyTempratureRange(iduId, faclId, tempVal,
					airconModeOperationValue)) {
				ac.setTargetTemperature(Float.parseFloat(operationValue));
				isSuccess = true;
			} else {

				isSuccess = Boolean.FALSE;
				LOGGER.info(String
						.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s, value :%s]",
								iduId, faclId, rcOperation, operationValue));

			}
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s, value :%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;
			// throw e;
		}
		return isSuccess;
	}

	public Boolean fanSpeedOperation(ACIndoorUnit ac, RCOperation rcOperation,
			String operationValue) throws RemoteControlException {

		Boolean isSuccess = false;
		FanSpeed fanSpeed = FanSpeed.getPropertyID(operationValue);

		if (fanSpeed != null) {

			String propertyId = fanSpeed.getPropertyID();

			try {
				if (isOperationSupported(faclId, propertyId)) {
					switch (fanSpeed) {
					case HIGH:
						ac.setAirFlowVolume(AirFlowVolume.HH);
						break;
					case LOW:
						ac.setAirFlowVolume(AirFlowVolume.L);
						break;
					case MEDIUM:
						ac.setAirFlowVolume(AirFlowVolume.H);
						break;
						//add by JiaWei
					case AUTO:
						ac.setAirFlowVolume(AirFlowVolume.AUTO);
						break;
					default:
						isSuccess = Boolean.FALSE;
						String errorMsg = String
								.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s, value :%s, Property Id : %s]",
										iduId, faclId, rcOperation,
										operationValue, propertyId);
						LOGGER.info(errorMsg);
						throw new UnsupportedOperationException(errorMsg);
					}
					isSuccess = true;
				} else {
					isSuccess = Boolean.FALSE;
					String errorMsg = String
							.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
									iduId, faclId, rcOperation, operationValue,
									propertyId);
					LOGGER.info(errorMsg);
					// throw new UnsupportedOperationException(errorMsg);

				}

			} catch (RemoteControlException e) {

				LOGGER.info(
						String.format(
								"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
								iduId, faclId, rcOperation, operationValue,
								propertyId), e);
				isSuccess = Boolean.FALSE;
				// throw e;
			}
		} else {
			isSuccess = Boolean.FALSE;
			String errorMsg = String
					.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
							iduId, faclId, rcOperation, operationValue);
			LOGGER.info(errorMsg);
			// throw new UnsupportedOperationException(errorMsg);

		}

		return isSuccess;
	}

	public Boolean winDirectionOperation(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue)
			throws RemoteControlException {

		Boolean isSuccess = false;

		WinDirection winDirection = WinDirection.getPropertyID(operationValue);

		if (winDirection != null) {
			try {
				switch (winDirection) {
				case F1:
					if (isOperationSupported(faclId,
							SETTABLE_FLAP_DIRECTION_VALIDATION_PROP)) {
						ac.setAirFlowPosition(AirFlowPosition.F1);
					} else {
						isSuccess = Boolean.FALSE;
						String errorMsg = String
								.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
										iduId, faclId, rcOperation,
										operationValue,
										SETTABLE_FLAP_DIRECTION_VALIDATION_PROP);
						LOGGER.info(errorMsg);
					}
					break;
				case F2:
					if (isOperationSupported(faclId,
							SETTABLE_FLAP_DIRECTION_VALIDATION_PROP)) {
						ac.setAirFlowPosition(AirFlowPosition.F2);
					} else {
						isSuccess = Boolean.FALSE;
						String errorMsg = String
								.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
										iduId, faclId, rcOperation,
										operationValue,
										SETTABLE_FLAP_DIRECTION_VALIDATION_PROP);
						LOGGER.info(errorMsg);
					}
					break;
				case F3:
					if (isOperationSupported(faclId,
							SETTABLE_FLAP_DIRECTION_VALIDATION_PROP)) {
						ac.setAirFlowPosition(AirFlowPosition.F3);
					} else {
						isSuccess = Boolean.FALSE;
						String errorMsg = String
								.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
										iduId, faclId, rcOperation,
										operationValue,
										SETTABLE_FLAP_DIRECTION_VALIDATION_PROP);
						LOGGER.info(errorMsg);
					}
					break;
				case F4:
					if (isOperationSupported(faclId,
							SETTABLE_FLAP_DIRECTION_VALIDATION_PROP)) {
						ac.setAirFlowPosition(AirFlowPosition.F4);
					} else {
						isSuccess = Boolean.FALSE;
						String errorMsg = String
								.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
										iduId, faclId, rcOperation,
										operationValue,
										SETTABLE_FLAP_DIRECTION_VALIDATION_PROP);
						LOGGER.info(errorMsg);
					}
					break;
				case F5:
					if (isOperationSupported(faclId,
							SETTABLE_FLAP_DIRECTION_VALIDATION_PROP)) {
						ac.setAirFlowPosition(AirFlowPosition.F5);
					} else {
						isSuccess = Boolean.FALSE;
						String errorMsg = String
								.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
										iduId, faclId, rcOperation,
										operationValue,
										SETTABLE_FLAP_DIRECTION_VALIDATION_PROP);
						LOGGER.info(errorMsg);
					}
					break;
				case SWING:
					if (isOperationSupported(faclId,
							SETTABLE_FLAP_SWING_VALIDATION_PROP)) {
						ac.setAirFlowPosition(AirFlowPosition.SWING);
					} else {
						isSuccess = Boolean.FALSE;
						String errorMsg = String
								.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s, Property Id : %s]",
										iduId, faclId, rcOperation,
										operationValue,
										SETTABLE_FLAP_DIRECTION_VALIDATION_PROP);
						LOGGER.info(errorMsg);
					}
					break;
				case STOP:
					ac.setAirFlowPosition(AirFlowPosition.STOP);
					break;
				default:
					break;
				}
				isSuccess = true;
			} catch (RemoteControlException e) {
				LOGGER.info(
						String.format(
								"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
								iduId, faclId, rcOperation, operationValue), e);
				isSuccess = Boolean.FALSE;
				// throw e;
			}
		}

		return isSuccess;
	}

	public Boolean prohibitionEnergySaving(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue)
			throws RemoteControlException {
		Boolean isSuccess = false;
		try {

			switch (operationValue) {
			case BizConstants.ONE:
				ac.setEcoModeControlEnable(Boolean.TRUE);
				break;
			case BizConstants.ZERO:
				ac.setEcoModeControlEnable(Boolean.FALSE);
				break;

			default:
				break;
			}
			isSuccess = true;
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;
			// throw e;
		}
		return isSuccess;
	}

	public Boolean prohibitionPowerStatus(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue)
			throws RemoteControlException {
		Boolean isSuccess = true;
		try {

			switch (operationValue) {
			case BizConstants.ONE:
				ac.setPowerControlEnable(Boolean.TRUE);
				break;
			case BizConstants.ZERO:
				ac.setPowerControlEnable(Boolean.FALSE);
				break;

			default:
				break;
			}
			isSuccess = true;
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;
			// throw e;
		}
		return isSuccess;
	}

	public Boolean prohibitionMode(ACIndoorUnit ac, RCOperation rcOperation,
			String operationValue) throws RemoteControlException {
		Boolean isSuccess = false;
		try {

			switch (operationValue) {
			case BizConstants.ONE:
				ac.setOperationModeControlEnable(Boolean.TRUE);
				break;
			case BizConstants.ZERO:
				ac.setOperationModeControlEnable(Boolean.FALSE);
				break;

			default:
				break;
			}
			isSuccess = true;
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;
			// throw e;
		}
		return isSuccess;
	}

	public Boolean prohibitionFanspeed(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue)
			throws RemoteControlException {
		Boolean isSuccess = false;
		try {

			switch (operationValue) {
			case BizConstants.ONE:
				ac.setAirFlowVolumeControlEnable(Boolean.TRUE);
				break;
			case BizConstants.ZERO:
				ac.setAirFlowVolumeControlEnable(Boolean.FALSE);
				break;

			default:
				break;
			}
			isSuccess = true;
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;
			// throw e;
		}
		return isSuccess;
	}

	public Boolean prohibitionWindirection(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue)
			throws RemoteControlException {
		Boolean isSuccess = false;
		try {

			switch (operationValue) {
			case BizConstants.ONE:
				ac.setAirFlowDirectionControlEnable(Boolean.TRUE);
				break;
			case BizConstants.ZERO:
				ac.setAirFlowDirectionControlEnable(Boolean.FALSE);
				break;

			default:
				break;
			}
			isSuccess = true;
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;
			// throw e;
		}
		return isSuccess;
	}

	public Boolean prohibitionSetTemerature(ACIndoorUnit ac,
			RCOperation rcOperation, String operationValue)
			throws RemoteControlException {
		Boolean isSuccess = false;
		try {

			switch (operationValue) {
			case BizConstants.ONE:
				ac.setTemperatureControlEnable(Boolean.TRUE);
				break;
			case BizConstants.ZERO:
				ac.setTemperatureControlEnable(Boolean.FALSE);
				break;

			default:
				break;
			}
			isSuccess = true;
		} catch (RemoteControlException e) {

			LOGGER.info(
					String.format(
							"Error occured while executing operation for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
							iduId, faclId, rcOperation, operationValue), e);
			isSuccess = Boolean.FALSE;
			// throw e;
		}
		return isSuccess;
	}

	public Boolean varifyTempratureRange(String iduId, String faclId,
			Float tempVal, String airconModeOperationVal) {

		Boolean isTempratureLiesBetweenRange = Boolean.FALSE;
		try {
			String lowPropertyId = BizConstants.EMPTY_STRING;
			String highPropertyId = BizConstants.EMPTY_STRING;

			AirconModeValidation airconModeValidation = null;

			CurrentAirconMode currentAirconMode = null;
			Boolean airconOperationSupported = Boolean.FALSE;

			if (airconModeOperationVal != null
					&& StringUtils.isNotBlank(airconModeOperationVal)) {

				airconModeValidation = AirconModeValidation
						.getPropertyID(airconModeOperationVal);

				String propertyId = airconModeValidation == null ? BizConstants.EMPTY_STRING
						: airconModeValidation.getPropertyID();

				airconOperationSupported = isOperationSupported(faclId,
						propertyId);

			} else {
				List<String> propList = new ArrayList<>();
				propList.add(IDU_MODE_PROP_1);
				propList.add(IDU_MODE_PROP_2);

				String currentAirconModeMeasureVal = String
						.valueOf(pfStatusInfoService.getIDURCOPVals(faclId,
								RCOperation.MODE.name(), propList));

				Integer currentAirconModeMeasureIntVal = Integer
						.parseInt(currentAirconModeMeasureVal);

				currentAirconMode = CurrentAirconMode
						.getPropertyID(currentAirconModeMeasureIntVal);

				if (currentAirconMode != null) {
					if (currentAirconMode.equals(CurrentAirconMode.UNDECIDED)) {
						airconOperationSupported = false;
					} else {
						airconOperationSupported = true;
					}
				} else {
					airconOperationSupported = false;
				}

			}
			if (airconOperationSupported) {

				if (airconModeValidation == AirconModeValidation.HEAT
						|| currentAirconMode == CurrentAirconMode.HEATING) {

					lowPropertyId = AirconTemperatureLimit.HOT_LOW
							.getPropertyID();
					highPropertyId = AirconTemperatureLimit.HOT_HI
							.getPropertyID();
				} else if (airconModeValidation == AirconModeValidation.COOL
						|| currentAirconMode == CurrentAirconMode.COOLING) {

					lowPropertyId = AirconTemperatureLimit.COOL_LOW
							.getPropertyID();
					highPropertyId = AirconTemperatureLimit.COOL_HI
							.getPropertyID();
				} else if (airconModeValidation == AirconModeValidation.DRY
						|| currentAirconMode == CurrentAirconMode.DRY) {
					lowPropertyId = AirconTemperatureLimit.DRY_LOW
							.getPropertyID();
					highPropertyId = AirconTemperatureLimit.DRY_HI
							.getPropertyID();
				} else if (airconModeValidation == AirconModeValidation.AUTO) {
					lowPropertyId = AirconTemperatureLimit.AUTO_LOW
							.getPropertyID();
					highPropertyId = AirconTemperatureLimit.AUTO_HI
							.getPropertyID();
				} else {
					String errorMsg = String
							.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
									iduId, faclId, RCOperation.TEMPERATURE,
									tempVal);
					LOGGER.info(errorMsg);
					// throw new UnsupportedOperationException(errorMsg);
				}

				String msg = String
						.format("Aircon detail for Temperature operation- [ id: %s, facl id : %s Operation :%s value :%s, Low Property Id: %s, HighPropertyId :%s]",
								iduId, faclId, RCOperation.TEMPERATURE,
								tempVal, lowPropertyId, highPropertyId);
				LOGGER.info(msg);

				Object rowData = pfStatusInfoService.getIDUInfo(faclId,
						lowPropertyId, highPropertyId, String.valueOf(tempVal));

				if (rowData != null) {
					isTempratureLiesBetweenRange = Boolean.TRUE;
				} else {
					String errorMsg = String
							.format("Input Temperature range is not between low and high temp reange for Aircon [ id: %s, facl id : %s Operation :%s value :%s",
									iduId, faclId, RCOperation.TEMPERATURE,
									tempVal);
					LOGGER.info(errorMsg);
				}

			} else {
				String errorMsg = String
						.format("Operation is not supported for Aircon [ id: %s, facl id : %s Operation :%s value :%s]",
								iduId, faclId, RCOperation.TEMPERATURE, tempVal);
				LOGGER.info(errorMsg);
				// throw new UnsupportedOperationException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.info(
					String.format(
							"Error occured while opening the connection for Aircon [ id : %s , facl Id: %s]",
							iduId, faclId), e);
			throw e;
		}
		return isTempratureLiesBetweenRange;
	}

	public Boolean isOperationSupported(String faclId, String propertyId) {
		Boolean isOerationSupported = Boolean.FALSE;
		Object measureValue = pfStatusInfoService
				.getIDUInfo(faclId, propertyId);
		if (measureValue != null
				&& StringUtils.isNotBlank(measureValue.toString())) {
			if (StringUtils.equalsIgnoreCase(measureValue.toString(),
					BizConstants.ONE))
				isOerationSupported = Boolean.TRUE;
		}
		return isOerationSupported;

	}
	//add by shanf
	/**
	 * @return the signal
	 */
	public CountDownLatch getSignal() {
		return signal;
	}

	/**
	 * @param signal the signal to set
	 */
	public void setSignal(CountDownLatch signal) {
		this.signal = signal;
	}
}
