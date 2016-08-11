package com.panasonic.b2bacns.bizportal.rc.pfcomm.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.rc.ControlRCVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * 
 * This class runs as a thread and having logic to create final VO equivalent to
 * required JSON output from RC GET API
 * 
 * @author shobhit.singh
 * 
 */
public class IDUCurrentStatus {

	private static final String IS_ALL_ON = "isAllOn";
	private static final String IS_ALL_OFF = "isAllOff";
	private static final String ON_OFF = "On-Off";

	/**
	 * Required Maps and Sets to create final VO equivalent to required JSON
	 * output from RC GET API
	 */
	private Map<String, Boolean> powerStatusMap = new HashMap<>();
	private Set<String> modeSet = new HashSet<>();
	private Set<String> fanSpeedSet = new HashSet<>();
	private Set<String> windDirectionSet = new HashSet<>();
	private Set<Boolean> powerProhibitionSet = new HashSet<>();
	private Set<Boolean> tempProhibitionSet = new HashSet<>();
	private Set<Boolean> modeProhibitionSet = new HashSet<>();
	private Set<Boolean> fanSpeedProhibitionSet = new HashSet<>();
	private Set<Boolean> windDirectionProhibitionSet = new HashSet<>();
	private Set<String> energySavingSet = new HashSet<>();

	// key-> Temperature
	// Value-> Counts of on devices having particular temperature
	private Map<String, Long> temperatureMapInCount = new HashMap<>();

	public void accumulateAll(List<ACConfigVO> currentIDUStatusList) {

		for (ACConfigVO idu : currentIDUStatusList) {

			if (BizConstants.ON.equalsIgnoreCase(idu.getPower())) {
				powerStatusMap.put(IS_ALL_ON, true);
			} else {
				powerStatusMap.put(IS_ALL_OFF, false);
			}

			temperatureMapInCount = getMapDataInCount(temperatureMapInCount,
					idu.getTemperature());

			modeSet.add(idu.getMode());

			fanSpeedSet.add(idu.getFanSpeed());

			windDirectionSet.add(idu.getFlapMode());

			energySavingSet.add(idu.getEnergy_saving());

			powerProhibitionSet.add(Boolean.valueOf(idu.getProhibitRCPower()));
			tempProhibitionSet.add(Boolean.valueOf(idu.getProhibitRCTemp()));
			modeProhibitionSet.add(Boolean.valueOf(idu.getProhibitRCMode()));
			fanSpeedProhibitionSet.add(Boolean.valueOf(idu.getFanSpeed()));
			windDirectionProhibitionSet.add(Boolean.valueOf(idu
					.getProhibitRCFlapMode()));
		}
	}

	/**
	 * This method is having logic to create final VO equivalent to required
	 * JSON output from RC GET API
	 * 
	 * @return
	 */
	public ControlRCVO getControlRCVO(List<ACConfigVO> currentIDUStatusList) {

		accumulateAll(currentIDUStatusList);

		ControlRCVO controlRCVO = new ControlRCVO();

		// Status - On/OFF
		if ((powerStatusMap.containsKey(IS_ALL_ON))
				&& (!powerStatusMap.containsKey(IS_ALL_OFF))) {
			controlRCVO.setPowerStatus(BizConstants.ATTRIBUTE_VALUE_STATE_ON);
		} else if ((!powerStatusMap.containsKey(IS_ALL_ON))
				&& (powerStatusMap.containsKey(IS_ALL_OFF))) {
			controlRCVO.setPowerStatus(BizConstants.ATTRIBUTE_VALUE_STATE_OFF);
		} else if ((powerStatusMap.containsKey(IS_ALL_ON))
				&& (powerStatusMap.containsKey(IS_ALL_OFF))) {
			controlRCVO.setPowerStatus(ON_OFF);
		}

		Map<String, Long> mapWithMaxValue = CommonUtil
				.getMapWithMaxValue(temperatureMapInCount);

		Set<String> temps = mapWithMaxValue.keySet();

		Double sum = 0.0;
		int validTempCount = 0;
		for (String currentTemperature : temps) {
			if (!currentTemperature.equals(BizConstants.HYPHEN)) {
				sum = sum + Double.parseDouble(currentTemperature);
				validTempCount++;
			}
		}
		if (validTempCount > 0) {
			double averageCurrentTemperature = sum / validTempCount;
			controlRCVO
					.setTemperature(CommonUtil
							.getFormattedValueUpToTwoDecimal(averageCurrentTemperature));
		} else {
			controlRCVO
					.setTemperature(CommonUtil
							.getFormattedValueUpToTwoDecimal(0));
		}

		// Modes, Fan speed, Wind direction
		int noOfModes = modeSet.size();
		if (noOfModes > 1) {
			controlRCVO.setMode(BizConstants.MIX);
		} else {
			String arr[] = modeSet.toArray(new String[modeSet.size()]);
			controlRCVO.setMode(arr[0]);
		}

		int noOfFanSpeeds = fanSpeedSet.size();
		if (noOfFanSpeeds > 1) {
			controlRCVO.setFanSpeed(BizConstants.MIX);
		} else {
			String arr[] = fanSpeedSet.toArray(new String[fanSpeedSet.size()]);
			controlRCVO.setFanSpeed(arr[0]);
		}

		int noOfWindDirections = windDirectionSet.size();
		if (noOfWindDirections > 1) {
			controlRCVO.setWindDirection(BizConstants.MIX);
		} else {
			String arr[] = windDirectionSet.toArray(new String[windDirectionSet
					.size()]);
			controlRCVO.setWindDirection(arr[0]);
		}

		int powerProhibitionSetSize = powerProhibitionSet.size();
		if (powerProhibitionSetSize > 1) {
			controlRCVO.setPowerProhibition(BizConstants.MIX);
		} else {
			Boolean arr[] = powerProhibitionSet
					.toArray(new Boolean[powerProhibitionSet.size()]);
			if (arr[0]) {
				controlRCVO.setPowerProhibition(arr[0].toString());
			}
		}

		int tempProhibitionSetSize = tempProhibitionSet.size();
		if (tempProhibitionSetSize > 1) {
			controlRCVO.setTempProhibition(BizConstants.MIX);
		} else {
			Boolean arr[] = tempProhibitionSet
					.toArray(new Boolean[tempProhibitionSet.size()]);
			if (arr[0]) {
				controlRCVO.setTempProhibition(arr[0].toString());
			}
		}

		int modeProhibitionSetSize = modeProhibitionSet.size();
		if (modeProhibitionSetSize > 1) {
			controlRCVO.setModeProhibition(BizConstants.MIX);
		} else {
			Boolean arr[] = modeProhibitionSet
					.toArray(new Boolean[modeProhibitionSet.size()]);
			if (arr[0]) {
				controlRCVO.setModeProhibition(arr[0].toString());
			}
		}

		int fanSpeedProhibitionSetSize = fanSpeedProhibitionSet.size();
		if (fanSpeedProhibitionSetSize > 1) {
			controlRCVO.setFanSpeedProhibition(BizConstants.MIX);
		} else {
			Boolean arr[] = fanSpeedProhibitionSet
					.toArray(new Boolean[fanSpeedProhibitionSet.size()]);
			if (arr[0]) {
				controlRCVO.setFanSpeedProhibition(arr[0].toString());
			}
		}

		int windDirectionProhibitionSetSize = windDirectionProhibitionSet
				.size();
		if (windDirectionProhibitionSetSize > 1) {
			controlRCVO.setWindDirectionProhibition(BizConstants.MIX);
		} else {
			Boolean arr[] = windDirectionProhibitionSet
					.toArray(new Boolean[windDirectionProhibitionSet.size()]);
			if (arr[0]) {
				controlRCVO.setWindDirectionProhibition(arr[0].toString());
			}
		}

		int energySavingSetSize = energySavingSet.size();
		if (energySavingSetSize > 1) {
			controlRCVO.setEnergySaving(BizConstants.MIX);
		} else {
			String arr[] = energySavingSet.toArray(new String[energySavingSet
					.size()]);
			controlRCVO.setEnergySaving(arr[0]);
		}

		return controlRCVO;
	}

	/**
	 * Helper method used by methods getVisualizationSummary and
	 * getControlStatus for updating map with key and value as number of
	 * occurrences of that key. For example- as per Map<"17", 4> -- 4 devices
	 * has 17 degree C temperature
	 * 
	 * @param map
	 * @param mapKey
	 * @return
	 */
	private Map<String, Long> getMapDataInCount(Map<String, Long> map,
			Object mapKey) {

		if (map != null && mapKey != null) {
			Long unitCount = 1l;
			if (map.get(mapKey.toString()) != null) {

				Long existingCount = map.get(mapKey.toString());
				unitCount = existingCount + 1;
			}
			map.put(mapKey.toString(), unitCount);
		}

		return map;
	}
}
