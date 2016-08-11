/**
 * 
 */
package com.panasonic.b2bacns.bizportal.stats.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.stats.vo.EnergyConsumptionResponseVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsRequestVO;
import com.panasonic.b2bacns.bizportal.stats.vo.StatsResponseVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 * 
 */
@Component
public class StatsJsonBuilder {

	private static final String SERIES_EFFICIENCY = "Efficiency";
	private static final String SERIES_POWER = "Power Consumption";
	private static final String SERIES_SETTEMP = "Setting Temp";
	private static final String SERIES_RMTEMP = "Rm Temp";
	private static final String SERIES_OUTTEMP = "Outdoor Temp";
	private static final String SERIES_RATED = "Rated";
	private static final String SERIES_CURRENT = "Current";
	private static final String SERIES_OUTDOORTEMP = "Outdoor Temperature";
	private static final String SERIES_ROOMTEMP = "Room Temp";
	private static final String SERIES_DIFFTEMP = "Different Temp";
	private static final String SERIES_HIGH = "High";
	private static final String SERIES_MED = "Medium";
	private static final String SERIES_LOW = "Low";
	private static final String SERIES_BORDERWIDTH = "borderWidth";
	// Modifed By ravi as GHP not supported currently
	private static final String SERIES_WORKING_HOUR_COMP1 = "Working Hours Comp1";
	private static final String SERIES_WORKING_HOUR_COMP2 = "Working Hours Comp2";
	private static final String SERIES_WORKING_HOUR_COMP3 = "Working Hours Comp3";

	public void populateStatsResponseVo(Object[] rowData,
			StatsRequestVO requestVO, StatsResponseVO responseVO, int size,
			Map<Integer, TreeMap<Integer, Object>> processMapForChronological,
			Map<String, String> groupMap) {

		switch (requestVO.getType()) {
		case BizConstants.STATISTICS_ACCUMULATED:

			populateJsonForAccumulated(rowData, requestVO, responseVO, size,
					processMapForChronological, groupMap);

			break;

		case BizConstants.STATISTICS_CHRONOLOGICAL:

			populateJsonForChronological(rowData, requestVO, responseVO, size,
					processMapForChronological, groupMap);

			break;
		}

	}

	/**
	 * This method is used to populate StatsResponseVO from result set in order
	 * to create json structure for the all the charts type accumulated
	 * 
	 * @param rowData
	 * @param requestVO
	 * @param responseVO
	 * @param size
	 * @param processMapForChronological
	 * @param groupMap
	 */
	private void populateJsonForAccumulated(Object[] rowData,
			StatsRequestVO requestVO, StatsResponseVO responseVO, int size,
			Map<Integer, TreeMap<Integer, Object>> processMapForChronological,
			Map<String, String> groupMap) {

		switch (requestVO.getParameter()) {

		case BizConstants.POWER_CONSUMPTION:

			responseVO.getCategories().add(
					rowData[0] + BizConstants.EMPTY_STRING);

			if (responseVO.getSeries().size() == 0) {

				Map<String, Object> map = new HashMap<String, Object>();
				List<Double> list = new ArrayList<Double>();
				list.add(rowData[1] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
				if (StringUtils.equals(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {
					map.put(SERIES_BORDERWIDTH, 0);
				}
				map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_POWER);
				map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
				responseVO.getSeries().add(map);

			} else {

				if (responseVO.getSeries().get(0)
						.containsKey(BizConstants.KEY_JSON_RESPONSE_DATA)) {

					@SuppressWarnings("unchecked")
					List<Double> list = (ArrayList<Double>) responseVO
							.getSeries().get(0)
							.get(BizConstants.KEY_JSON_RESPONSE_DATA);

					list.add(rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[1]) : null);

					responseVO.getSeries().get(0)
							.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				}

			}

			break;

		case BizConstants.CAPACITY:

			responseVO.getCategories().add(
					rowData[0] + BizConstants.EMPTY_STRING);

			if (responseVO.getSeries().size() == 0) {

				Map<String, Object> map = new HashMap<String, Object>();
				List<Double> list = new ArrayList<Double>();

				list.add(rowData[1] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[1]) : null);

				if (StringUtils.equals(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {
					map.put(SERIES_BORDERWIDTH, 0);
				}
				map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_RATED);
				map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
				responseVO.getSeries().add(map);

				map = new HashMap<String, Object>();
				list = new ArrayList<Double>();

				list.add(rowData[2] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[2]) : null);

				if (StringUtils.equals(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {
					map.put(SERIES_BORDERWIDTH, 0);
				}

				map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_CURRENT);
				map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
				responseVO.getSeries().add(map);

				map = new HashMap<String, Object>();
				list = new ArrayList<Double>();

				list.add(rowData[3] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[3]) : null);
				if (StringUtils.equals(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {
					map.put(SERIES_BORDERWIDTH, 0);
				}
				map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_OUTDOORTEMP);
				map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
				responseVO.getSeries().add(map);

				responseVO.setTotal(rowData[2] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[2]) : null);

			} else {

				if (responseVO.getSeries().get(0)
						.containsKey(BizConstants.KEY_JSON_RESPONSE_DATA)) {

					@SuppressWarnings("unchecked")
					List<Double> list = (ArrayList<Double>) responseVO
							.getSeries().get(0)
							.get(BizConstants.KEY_JSON_RESPONSE_DATA);

					list.add(rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[1]) : null);

					responseVO.getSeries().get(0)
							.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					@SuppressWarnings("unchecked")
					List<Double> listSetTemp = (ArrayList<Double>) responseVO
							.getSeries().get(1)
							.get(BizConstants.KEY_JSON_RESPONSE_DATA);

					listSetTemp
							.add(rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: null);

					responseVO
							.getSeries()
							.get(1)
							.put(BizConstants.KEY_JSON_RESPONSE_DATA,
									listSetTemp);

					@SuppressWarnings("unchecked")
					List<Double> listRoomTemp = (ArrayList<Double>) responseVO
							.getSeries().get(2)
							.get(BizConstants.KEY_JSON_RESPONSE_DATA);

					listRoomTemp
							.add(rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[3])
									: null);

					responseVO
							.getSeries()
							.get(2)
							.put(BizConstants.KEY_JSON_RESPONSE_DATA,
									listRoomTemp);

					if (rowData[2] != null) {

						responseVO
								.setTotal((responseVO.getTotal() == null ? 0.0F
										: responseVO.getTotal())
										+ CommonUtil
												.getFormattedValueUpToTwoDecimal(rowData[2]));
					}

				}

			}

			break;

		case BizConstants.EFFICIENCY:

			if (StringUtils.equalsIgnoreCase(requestVO.getChartType(),
					BizConstants.API_CHART_EFFICIENCY_RANKING)) {

				responseVO.getCategories().add(rowData[0] + "-" + rowData[2]);

			} else {

				responseVO.getCategories().add(
						rowData[0] + BizConstants.EMPTY_STRING);
			}

			if (responseVO.getSeries().size() == 0) {

				Map<String, Object> map = new HashMap<String, Object>();
				List<Double> list = new ArrayList<Double>();

				if (!StringUtils.equals(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
						&& !StringUtils.equals(requestVO.getApiCallFor(),
								BizConstants.STATS_API_CALL_BY_GROUP)) {

					list.add(rowData[4] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[4]) : null);
					if (StringUtils.equals(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {
						map.put(SERIES_BORDERWIDTH, 0);
					}
					map.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							SERIES_EFFICIENCY);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

					map = new HashMap<String, Object>();
					list = new ArrayList<Double>();

					list.add(rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[1]) : null);

					map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_SETTEMP);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

					map = new HashMap<String, Object>();
					list = new ArrayList<Double>();

					list.add(rowData[2] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[2]) : null);

					map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_RMTEMP);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

					map = new HashMap<String, Object>();
					list = new ArrayList<Double>();

					list.add(rowData[3] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[3]) : null);

					map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_OUTTEMP);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

				} else {

					list.add(rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
					map.put(SERIES_BORDERWIDTH, 0);

					map.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							SERIES_EFFICIENCY);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

				}

			} else {

				if (responseVO.getSeries().get(0)
						.containsKey(BizConstants.KEY_JSON_RESPONSE_DATA)) {

					if (!StringUtils.equals(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
							&& !StringUtils.equals(requestVO.getApiCallFor(),
									BizConstants.STATS_API_CALL_BY_GROUP)) {

						@SuppressWarnings("unchecked")
						List<Double> list = (ArrayList<Double>) responseVO
								.getSeries().get(0)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						list.add(rowData[4] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[4])
								: null);

						responseVO.getSeries().get(0)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

						@SuppressWarnings("unchecked")
						List<Double> listSetTemp = (ArrayList<Double>) responseVO
								.getSeries().get(1)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						listSetTemp.add(rowData[1] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[1])
								: null);

						responseVO
								.getSeries()
								.get(1)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										listSetTemp);

						@SuppressWarnings("unchecked")
						List<Double> listRoomTemp = (ArrayList<Double>) responseVO
								.getSeries().get(2)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						listRoomTemp.add(rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[2])
								: null);

						responseVO
								.getSeries()
								.get(2)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										listRoomTemp);

						@SuppressWarnings("unchecked")
						List<Double> listOutTemp = (ArrayList<Double>) responseVO
								.getSeries().get(3)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						listOutTemp.add(rowData[3] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[3])
								: null);

						responseVO
								.getSeries()
								.get(3)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										listOutTemp);

					} else {

						@SuppressWarnings("unchecked")
						List<Double> list = (ArrayList<Double>) responseVO
								.getSeries().get(0)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						list.add(rowData[1] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[1])
								: null);

						responseVO.getSeries().get(0)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					}

				}

			}

			break;

		case BizConstants.DIFF_TEMPERATURE:

			responseVO.getCategories().add(
					rowData[0] + BizConstants.EMPTY_STRING);

			if (responseVO.getSeries().size() == 0) {

				Map<String, Object> map = new HashMap<String, Object>();
				List<Double> list = new ArrayList<Double>();
				list.add(rowData[1] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
				map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_ROOMTEMP);
				map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
				responseVO.getSeries().add(map);

				map = new HashMap<String, Object>();
				list = new ArrayList<Double>();
				list.add(rowData[2] != null ? CommonUtil
						.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
				map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_SETTEMP);
				map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
				responseVO.getSeries().add(map);

				map = new HashMap<String, Object>();
				list = new ArrayList<Double>();
				if (rowData[1] == null && rowData[2] == null) {

					list.add(null);

				} else {

					list.add((rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[1]) : 0)
							- (rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: 0));
				}
				map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_DIFFTEMP);
				map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
				responseVO.getSeries().add(map);

				responseVO.setTotalCapacityCool(null);
				responseVO.setTotalCapacityHeat(null);

				/*
				 * responseVO.setTotalCapacityHeat(rowData[3] != null ?
				 * CommonUtil .getFormattedValueUpToTwoDecimal(rowData[3]) :
				 * null); responseVO.setTotalCapacityCool(rowData[4] != null ?
				 * CommonUtil .getFormattedValueUpToTwoDecimal(rowData[4]) :
				 * null);
				 */

			} else {

				if (responseVO.getSeries().get(0)
						.containsKey(BizConstants.KEY_JSON_RESPONSE_DATA)) {

					@SuppressWarnings("unchecked")
					List<Double> list = (ArrayList<Double>) responseVO
							.getSeries().get(0)
							.get(BizConstants.KEY_JSON_RESPONSE_DATA);
					list.add(rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
					responseVO.getSeries().get(0)
							.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					@SuppressWarnings("unchecked")
					List<Double> listSetTemp = (ArrayList<Double>) responseVO
							.getSeries().get(1)
							.get(BizConstants.KEY_JSON_RESPONSE_DATA);
					listSetTemp
							.add(rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: null);
					responseVO
							.getSeries()
							.get(1)
							.put(BizConstants.KEY_JSON_RESPONSE_DATA,
									listSetTemp);

					@SuppressWarnings("unchecked")
					List<Double> listRoomTemp = (ArrayList<Double>) responseVO
							.getSeries().get(2)
							.get(BizConstants.KEY_JSON_RESPONSE_DATA);

					if (rowData[1] == null && rowData[2] == null) {

						listRoomTemp.add(null);

					} else {

						listRoomTemp
								.add((rowData[1] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[1])
										: 0)
										- (rowData[2] != null ? CommonUtil
												.getFormattedValueUpToTwoDecimal(rowData[2])
												: 0));
					}
					responseVO
							.getSeries()
							.get(2)
							.put(BizConstants.KEY_JSON_RESPONSE_DATA,
									listRoomTemp);

					/*
					 * if (rowData[3] != null) { responseVO
					 * .setTotalCapacityHeat((responseVO .getTotalCapacityHeat()
					 * != null ? responseVO .getTotalCapacityHeat() : 0) +
					 * CommonUtil .getFormattedValueUpToTwoDecimal(rowData[3]));
					 * }
					 * 
					 * if (rowData[4] != null) { responseVO
					 * .setTotalCapacityCool((responseVO .getTotalCapacityCool()
					 * != null ? responseVO .getTotalCapacityCool() : 0) +
					 * CommonUtil .getFormattedValueUpToTwoDecimal(rowData[4]));
					 * }
					 */

					responseVO.setTotalCapacityCool(null);
					responseVO.setTotalCapacityHeat(null);

				}
			}

			break;

		case BizConstants.WORKING_HOURS:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

				responseVO.getCategories().add(rowData[0] + "");

				if (responseVO.getSeries().size() == 0) {

					Map<String, Object> map = new HashMap<String, Object>();
					List<Double> list = new ArrayList<Double>();
					list.add(rowData[1] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[1]) : null);
					map.put(SERIES_BORDERWIDTH, 0);
					map.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							SERIES_WORKING_HOUR_COMP1);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

					map = new HashMap<String, Object>();
					list = new ArrayList<Double>();
					list.add(rowData[2] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[2]) : null);
					map.put(SERIES_BORDERWIDTH, 0);
					map.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							SERIES_WORKING_HOUR_COMP2);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

					map = new HashMap<String, Object>();
					list = new ArrayList<Double>();
					list.add(rowData[3] != null ? CommonUtil
							.getFormattedValueUpToTwoDecimal(rowData[3]) : null);
					map.put(SERIES_BORDERWIDTH, 0);
					map.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							SERIES_WORKING_HOUR_COMP3);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

				} else {

					if (responseVO.getSeries().get(0)
							.containsKey(BizConstants.KEY_JSON_RESPONSE_DATA)) {

						@SuppressWarnings("unchecked")
						List<Double> listComp1 = (ArrayList<Double>) responseVO
								.getSeries().get(0)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						listComp1.add(rowData[1] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[1])
								: null);

						@SuppressWarnings("unchecked")
						List<Double> listComp2 = (ArrayList<Double>) responseVO
								.getSeries().get(1)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						listComp2.add(rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[2])
								: null);

						@SuppressWarnings("unchecked")
						List<Double> listComp3 = (ArrayList<Double>) responseVO
								.getSeries().get(2)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						listComp3.add(rowData[3] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimal(rowData[3])
								: null);

					}

				}

			} else {

				if (!StringUtils.equalsIgnoreCase(
						requestVO.getParameterOption(),
						BizConstants.WORKHOUR_PARAM_OPTION_ON)) {

					responseVO
							.getCategories()
							.add(rowData[0]
									+ BizConstants.WORKHOUR_PARAM_LABEL_OFF_ACCUMULATED);
				}

				if (!StringUtils.equalsIgnoreCase(
						requestVO.getParameterOption(),
						BizConstants.WORKHOUR_PARAM_OPTION_OFF)) {

					responseVO
							.getCategories()
							.add(rowData[0]
									+ BizConstants.WORKHOUR_PARAM_LEBAL_ON_ACCUMULATED);

				}

				if (responseVO.getSeries().size() == 0) {

					Map<String, Object> map = new HashMap<String, Object>();
					List<Double> list = new ArrayList<Double>();
					if (!StringUtils.equalsIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_ON)) {

						list.add(rowData[2] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[2])
								: null);
					}
					if (!StringUtils.equalsIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_OFF)) {

						list.add(rowData[1] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[1])
								: null);

					}

					map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_HIGH);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

					map = new HashMap<String, Object>();
					list = new ArrayList<Double>();
					if (!StringUtils.equalsIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_ON)) {

						list.add(rowData[4] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[4])
								: null);
					}
					if (!StringUtils.equalsIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_OFF)) {

						list.add(rowData[3] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[3])
								: null);

					}

					map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_MED);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

					map = new HashMap<String, Object>();
					list = new ArrayList<Double>();
					if (!StringUtils.equalsIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_ON)) {

						list.add(rowData[6] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[6])
								: null);
					}
					if (!StringUtils.equalsIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_OFF)) {

						list.add(rowData[5] != null ? CommonUtil
								.getFormattedValueUpToTwoDecimalInMinutes(rowData[5])
								: null);
					}

					map.put(BizConstants.KEY_JSON_RESPONSE_NAME, SERIES_LOW);
					map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);
					responseVO.getSeries().add(map);

				} else {

					if (responseVO.getSeries().get(0)
							.containsKey(BizConstants.KEY_JSON_RESPONSE_DATA)) {

						@SuppressWarnings("unchecked")
						List<Double> listHigh = (ArrayList<Double>) responseVO
								.getSeries().get(0)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (!StringUtils.equalsIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_ON)) {

							listHigh.add(rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[2])
									: null);

						}

						if (!StringUtils.equalsIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_OFF)) {

							listHigh.add(rowData[1] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[1])
									: null);

						}

						responseVO
								.getSeries()
								.get(0)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										listHigh);

						@SuppressWarnings("unchecked")
						List<Double> listMed = (ArrayList<Double>) responseVO
								.getSeries().get(1)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (!StringUtils.equalsIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_ON)) {

							listMed.add(rowData[4] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[4])
									: null);

						}

						if (!StringUtils.equalsIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_OFF)) {

							listMed.add(rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[3])
									: null);

						}

						responseVO
								.getSeries()
								.get(1)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										listMed);

						@SuppressWarnings("unchecked")
						List<Double> listLow = (ArrayList<Double>) responseVO
								.getSeries().get(2)
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (!StringUtils.equalsIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_ON)) {

							listLow.add(rowData[6] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[6])
									: null);

						}

						if (!StringUtils.equalsIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_OFF)) {

							listLow.add(rowData[5] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimalInMinutes(rowData[5])
									: null);

						}

						responseVO
								.getSeries()
								.get(2)
								.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										listLow);

					}

				}
			}

			break;
		}

	}

	/**
	 * This method is used to populate StatsResponseVO from result set in order
	 * to create json structure for the all the charts type chronological
	 * 
	 * @param rowData
	 * @param requestVO
	 * @param responseVO
	 * @param size
	 * @param processMapForChronological
	 * @param groupMap
	 */
	private void populateJsonForChronological(Object[] rowData,
			StatsRequestVO requestVO, StatsResponseVO responseVO, int size,
			Map<Integer, TreeMap<Integer, Object>> processMapForChronological,
			Map<String, String> groupMap) {

		int index = 0;

		Boolean isNewGroup = null;

		Map<String, Object> mapIfNotExists = null;

		if (rowData[1] != null) {

			int outerIndex = 0;

			outer:

			for (Entry<Integer, TreeMap<Integer, Object>> entry : processMapForChronological
					.entrySet()) {

				index = outerIndex;

				for (Entry<Integer, Object> innerEntry : entry.getValue()
						.entrySet()) {

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_TODAY)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.PERIOD_THISWEEK)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.RANGE_WEEK)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.RANGE_DAY)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.PERIOD_24HOURS)) {

						String dateFromMap = null;

						if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
								BizConstants.PERIOD_THISWEEK)
								|| StringUtils.equalsIgnoreCase(
										requestVO.getPeriod(),
										BizConstants.RANGE_WEEK)) {

							dateFromMap = CommonUtil.getCalendarWithDateFormat(
									(long) innerEntry.getValue(),
									BizConstants.DATE_FORMAT_YYYYMMDD);

							if (String.valueOf((rowData[1])).contains(
									dateFromMap)) {

								break outer;
							}

						} else {

							dateFromMap = CommonUtil.getCalendarWithDateFormat(
									(long) innerEntry.getValue(),
									BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							Date date = CommonUtil.stringToDate(
									rowData[1].toString(),
									BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							String dateFromDatabase = CommonUtil
									.getCalendarWithDateFormatHourly(
											date.getTime(),
											BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							if (StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.PERIOD_24HOURS)) {

								dateFromMap = CommonUtil
										.getCalendarWithDateFormatHourly(
												(long) innerEntry.getValue(),
												BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

							}

							if (String.valueOf(dateFromMap).contains(
									dateFromDatabase)) {

								break outer;
							}

						}

					} else {

						if (!StringUtils
								.equalsIgnoreCase(requestVO.getPeriod(),
										BizConstants.RANGE_3YEAR)) {

							Integer yearIndex = null;

							if (StringUtils.equalsIgnoreCase(
									requestVO.getApiCallFor(),
									BizConstants.STATS_API_CALL_BY_AIRCON)) {

								yearIndex = rowData.length - 3;
							} else {

								yearIndex = rowData.length - 1;

							}

							BigDecimal periodNum = new BigDecimal(
									String.valueOf((rowData[1])));

							if (innerEntry.getKey() == periodNum.byteValue()
									&& entry.getKey()
											.equals(Integer.parseInt(String
													.valueOf((rowData[yearIndex]))))) {

								break outer;
							} /*
							 * else if (StringUtils.equalsIgnoreCase(
							 * requestVO.getPeriod(), BizConstants.RANGE_MONTH))
							 * { if (innerEntry.getKey() == periodNum
							 * .byteValue() && innerEntry.getKey() == 53 &&
							 * (entry.getKey() .equals(Integer.parseInt(String
							 * .valueOf((rowData[yearIndex]))))) || (Integer
							 * .parseInt(String.valueOf((Integer
							 * .parseInt(rowData[yearIndex] .toString())))) ==
							 * (Integer .parseInt(String.valueOf((Integer
							 * .parseInt(entry .getKey() .toString()) - 1))))))
							 * {
							 * 
							 * break outer;
							 * 
							 * }
							 * 
							 * }
							 */
						} else {

							if (innerEntry.getKey().equals(
									(Integer.parseInt(String
											.valueOf((rowData[1])))))) {

								break outer;
							}

						}

					}

					index++;
				}

				outerIndex = outerIndex + entry.getValue().size();
			}

		}

		switch (requestVO.getParameter()) {

		case BizConstants.POWER_CONSUMPTION:

			isNewGroup = true;

			mapIfNotExists = new HashMap<String, Object>();

			for (Map<String, Object> map : responseVO.getSeries()) {

				if (map.containsKey(BizConstants.KEY_JSON_RESPONSE_NAME)) {

					if (map.containsValue(rowData[0])) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[3] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[3]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					}

				}

			}

			if (isNewGroup) {

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0]);

				Double[] emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				List<Double> list = Arrays.asList(emptyArr);

				if (rowData[1] != null) {
					list.set(
							index,
							rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[3])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

			}

			break;

		case BizConstants.CAPACITY:

			isNewGroup = true;

			mapIfNotExists = new HashMap<String, Object>();

			for (Map<String, Object> map : responseVO.getSeries()) {

				if (map.containsKey(BizConstants.KEY_JSON_RESPONSE_NAME)) {

					if (map.containsValue(rowData[0] + BizConstants.HYPHEN
							+ SERIES_RATED)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[2] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[2]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					} else if (map.containsValue(rowData[0]
							+ BizConstants.HYPHEN + SERIES_CURRENT)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[3] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[3]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					} else if (map.containsValue(rowData[0]
							+ BizConstants.HYPHEN + SERIES_OUTDOORTEMP)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[4] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[4]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					}

				}

			}

			if (isNewGroup) {

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_RATED);

				Double[] emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				List<Double> list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

				mapIfNotExists = new HashMap<String, Object>();

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_CURRENT);

				emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				list = Arrays.asList(emptyArr);

				if (rowData[1] != null) {
					list.set(
							index,
							rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[3])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

				mapIfNotExists = new HashMap<String, Object>();

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_OUTDOORTEMP);

				emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							rowData[4] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[4])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

			}

			break;

		case BizConstants.EFFICIENCY:

			isNewGroup = true;

			mapIfNotExists = new HashMap<String, Object>();

			for (Map<String, Object> map : responseVO.getSeries()) {

				if (map.containsKey(BizConstants.KEY_JSON_RESPONSE_NAME)) {

					if (map.containsValue(rowData[0] + BizConstants.HYPHEN
							+ SERIES_EFFICIENCY)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (StringUtils
								.equals(requestVO.getApiCallFor(),
										BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
								|| StringUtils.equals(
										requestVO.getApiCallFor(),
										BizConstants.STATS_API_CALL_BY_GROUP)) {

							if (rowData[1] != null && rowData[2] != null) {
								list.set(
										index,
										CommonUtil
												.getFormattedValueUpToTwoDecimal(rowData[2]));
							}

						} else {

							if (rowData[1] != null && rowData[5] != null) {
								list.set(
										index,
										CommonUtil
												.getFormattedValueUpToTwoDecimal(rowData[5]));
							}
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

						if (StringUtils
								.equals(requestVO.getApiCallFor(),
										BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
								|| StringUtils.equals(
										requestVO.getApiCallFor(),
										BizConstants.STATS_API_CALL_BY_GROUP)) {
							break;
						}

					} else if (map.containsValue(rowData[0]
							+ BizConstants.HYPHEN + SERIES_SETTEMP)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[2] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[2]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					} else if (map.containsValue(rowData[0]
							+ BizConstants.HYPHEN + SERIES_RMTEMP)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[3] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[3]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					} else if (map.containsValue(rowData[0]
							+ BizConstants.HYPHEN + SERIES_OUTTEMP)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[4] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[4]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					}

				}

			}

			if (isNewGroup) {

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_EFFICIENCY);

				Double[] emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				List<Double> list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {

					if (StringUtils.equals(requestVO.getApiCallFor(),
							BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
							|| StringUtils.equals(requestVO.getApiCallFor(),
									BizConstants.STATS_API_CALL_BY_GROUP)) {

						list.set(
								index,
								rowData[2] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[2])
										: null);

					} else {
						list.set(
								index,
								rowData[5] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[5])
										: null);

					}
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

				if (StringUtils.equals(requestVO.getApiCallFor(),
						BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)
						|| StringUtils.equals(requestVO.getApiCallFor(),
								BizConstants.STATS_API_CALL_BY_GROUP)) {
					break;
				}

				mapIfNotExists = new HashMap<String, Object>();

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_SETTEMP);

				emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

				mapIfNotExists = new HashMap<String, Object>();

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_RMTEMP);

				emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[3])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

				mapIfNotExists = new HashMap<String, Object>();

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_OUTTEMP);

				emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							rowData[4] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[4])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

			}

			break;

		case BizConstants.DIFF_TEMPERATURE:

			isNewGroup = true;

			mapIfNotExists = new HashMap<String, Object>();

			for (Map<String, Object> map : responseVO.getSeries()) {

				if (map.containsKey(BizConstants.KEY_JSON_RESPONSE_NAME)) {

					if (map.containsValue(rowData[0] + BizConstants.HYPHEN
							+ SERIES_ROOMTEMP)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[2] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[2]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					} else if (map.containsValue(rowData[0]
							+ BizConstants.HYPHEN + SERIES_SETTEMP)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[3] != null) {
							list.set(
									index,
									CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[3]));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					} else if (map.containsValue(rowData[0]
							+ BizConstants.HYPHEN + SERIES_DIFFTEMP)) {

						isNewGroup = false;

						@SuppressWarnings("unchecked")
						List<Double> list = (List<Double>) map
								.get(BizConstants.KEY_JSON_RESPONSE_DATA);

						if (rowData[1] != null && rowData[2] != null
								&& rowData[3] != null) {

							list.set(
									index,
									(rowData[2] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[2])
											: 0)
											- (rowData[3] != null ? CommonUtil
													.getFormattedValueUpToTwoDecimal(rowData[3])
													: 0));
						}

						map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

					}

				}

			}

			if (isNewGroup) {

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_ROOMTEMP);

				Double[] emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				List<Double> list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

				mapIfNotExists = new HashMap<String, Object>();

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_SETTEMP);

				emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[3])
									: null);
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

				mapIfNotExists = new HashMap<String, Object>();

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_NAME,
						rowData[0] + BizConstants.HYPHEN + SERIES_DIFFTEMP);

				emptyArr = new Double[size];

				Arrays.fill(emptyArr, null);

				list = Arrays.asList(emptyArr);
				if (rowData[1] != null) {
					list.set(
							index,
							(rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: 0)
									- (rowData[3] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimal(rowData[3])

											: 0));
				}

				mapIfNotExists.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

				responseVO.getSeries().add(mapIfNotExists);

			}

			break;

		case BizConstants.WORKING_HOURS:

			if (StringUtils.equalsIgnoreCase(requestVO.getApiCallFor(),
					BizConstants.STATS_API_CALL_BY_REFRIGERANT_CIRCUIT)) {

				isNewGroup = true;

				mapIfNotExists = new HashMap<String, Object>();

				for (Map<String, Object> map : responseVO.getSeries()) {

					if (map.containsKey(BizConstants.KEY_JSON_RESPONSE_NAME)) {

						if (map.containsValue(rowData[0] + BizConstants.HYPHEN
								+ SERIES_WORKING_HOUR_COMP1)) {

							isNewGroup = false;

							@SuppressWarnings("unchecked")
							List<Double> list = (List<Double>) map
									.get(BizConstants.KEY_JSON_RESPONSE_DATA);

							if (rowData[1] != null && rowData[3] != null) {
								list.set(
										index,
										CommonUtil
												.getFormattedValueUpToTwoDecimal(rowData[3]));
							}

							map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

						}

						if (map.containsValue(rowData[0] + BizConstants.HYPHEN
								+ SERIES_WORKING_HOUR_COMP2)) {

							isNewGroup = false;

							@SuppressWarnings("unchecked")
							List<Double> list = (List<Double>) map
									.get(BizConstants.KEY_JSON_RESPONSE_DATA);

							if (rowData[1] != null && rowData[4] != null) {
								list.set(
										index,
										CommonUtil
												.getFormattedValueUpToTwoDecimal(rowData[4]));
							}

							map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

						}

						if (map.containsValue(rowData[0] + BizConstants.HYPHEN
								+ SERIES_WORKING_HOUR_COMP3)) {

							isNewGroup = false;

							@SuppressWarnings("unchecked")
							List<Double> list = (List<Double>) map
									.get(BizConstants.KEY_JSON_RESPONSE_DATA);

							if (rowData[1] != null && rowData[5] != null) {
								list.set(
										index,
										CommonUtil
												.getFormattedValueUpToTwoDecimal(rowData[5]));
							}

							map.put(BizConstants.KEY_JSON_RESPONSE_DATA, list);

						}
					}
				}

				if (isNewGroup) {

					Map<String, Object> mapIfNotExistsWH = new HashMap<String, Object>();

					mapIfNotExistsWH.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							rowData[0] + BizConstants.HYPHEN
									+ SERIES_WORKING_HOUR_COMP1);

					Double[] emptyArr = new Double[size];

					Arrays.fill(emptyArr, null);

					List<Double> list = Arrays.asList(emptyArr);
					if (rowData[1] != null) {
						list.set(
								index,
								rowData[3] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[3])
										: null);
					}

					mapIfNotExistsWH.put(BizConstants.KEY_JSON_RESPONSE_DATA,
							list);

					responseVO.getSeries().add(mapIfNotExistsWH);

					mapIfNotExistsWH = new HashMap<String, Object>();

					mapIfNotExistsWH.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							rowData[0] + BizConstants.HYPHEN
									+ SERIES_WORKING_HOUR_COMP2);

					emptyArr = new Double[size];

					Arrays.fill(emptyArr, null);

					list = Arrays.asList(emptyArr);
					if (rowData[1] != null) {
						list.set(
								index,
								rowData[4] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[4])
										: null);
					}

					mapIfNotExistsWH.put(BizConstants.KEY_JSON_RESPONSE_DATA,
							list);

					responseVO.getSeries().add(mapIfNotExistsWH);

					mapIfNotExistsWH = new HashMap<String, Object>();

					mapIfNotExistsWH.put(BizConstants.KEY_JSON_RESPONSE_NAME,
							rowData[0] + BizConstants.HYPHEN
									+ SERIES_WORKING_HOUR_COMP3);

					emptyArr = new Double[size];

					Arrays.fill(emptyArr, null);

					list = Arrays.asList(emptyArr);
					if (rowData[1] != null) {
						list.set(
								index,
								rowData[5] != null ? CommonUtil
										.getFormattedValueUpToTwoDecimal(rowData[5])
										: null);
					}

					mapIfNotExistsWH.put(BizConstants.KEY_JSON_RESPONSE_DATA,
							list);

					responseVO.getSeries().add(mapIfNotExistsWH);

				}

			} else {

				isNewGroup = true;

				mapIfNotExists = new HashMap<String, Object>();

				for (Map<String, Object> map : responseVO.getSeries()) {

					if (map.containsKey(BizConstants.KEY_JSON_RESPONSE_NAME)) {

						if (!StringUtils.endsWithIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_ON)
								|| StringUtils
										.endsWithIgnoreCase(
												requestVO.getParameterOption(),
												BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

							if (map.containsValue(rowData[0]
									+ BizConstants.HYPHEN
									+ BizConstants.WORKHOUR_PARAM_LABEL_OFF
									+ SERIES_HIGH)) {

								isNewGroup = false;

								@SuppressWarnings("unchecked")
								List<Double> list = (List<Double>) map
										.get(BizConstants.KEY_JSON_RESPONSE_DATA);

								if (rowData[1] != null && rowData[3] != null) {
									list.set(
											index,
											CommonUtil
													.getFormattedValueUpToTwoDecimalInMinutes(rowData[3]));
								}

								map.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										list);

							}
						}

						if (!StringUtils.endsWithIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_OFF)
								|| StringUtils
										.endsWithIgnoreCase(
												requestVO.getParameterOption(),
												BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

							if (map.containsValue(rowData[0]
									+ BizConstants.HYPHEN
									+ BizConstants.WORKHOUR_PARAM_LEBAL_ON
									+ SERIES_HIGH)) {

								isNewGroup = false;

								@SuppressWarnings("unchecked")
								List<Double> list = (List<Double>) map
										.get(BizConstants.KEY_JSON_RESPONSE_DATA);

								if (rowData[1] != null && rowData[2] != null) {
									list.set(
											index,
											CommonUtil
													.getFormattedValueUpToTwoDecimalInMinutes(rowData[2]));
								}

								map.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										list);

							}
						}

						if (!StringUtils.endsWithIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_ON)
								|| StringUtils
										.endsWithIgnoreCase(
												requestVO.getParameterOption(),
												BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

							if (map.containsValue(rowData[0]
									+ BizConstants.HYPHEN
									+ BizConstants.WORKHOUR_PARAM_LABEL_OFF
									+ SERIES_MED)) {

								isNewGroup = false;

								@SuppressWarnings("unchecked")
								List<Double> list = (List<Double>) map
										.get(BizConstants.KEY_JSON_RESPONSE_DATA);

								if (rowData[1] != null && rowData[5] != null) {
									list.set(
											index,
											CommonUtil
													.getFormattedValueUpToTwoDecimalInMinutes(rowData[5]));
								}

								map.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										list);

							}

						}

						if (!StringUtils.endsWithIgnoreCase(
								requestVO.getParameterOption(),
								BizConstants.WORKHOUR_PARAM_OPTION_OFF)
								|| StringUtils
										.endsWithIgnoreCase(
												requestVO.getParameterOption(),
												BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

							if (map.containsValue(rowData[0]
									+ BizConstants.HYPHEN
									+ BizConstants.WORKHOUR_PARAM_LEBAL_ON
									+ SERIES_MED)) {

								isNewGroup = false;

								@SuppressWarnings("unchecked")
								List<Double> list = (List<Double>) map
										.get(BizConstants.KEY_JSON_RESPONSE_DATA);

								if (rowData[1] != null && rowData[4] != null) {
									list.set(
											index,
											CommonUtil
													.getFormattedValueUpToTwoDecimalInMinutes(rowData[4]));
								}

								map.put(BizConstants.KEY_JSON_RESPONSE_DATA,
										list);

							}

							if (!StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_ON)
									|| StringUtils
											.endsWithIgnoreCase(
													requestVO
															.getParameterOption(),
													BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

								if (map.containsValue(rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LABEL_OFF
										+ SERIES_LOW)) {

									isNewGroup = false;

									@SuppressWarnings("unchecked")
									List<Double> list = (List<Double>) map
											.get(BizConstants.KEY_JSON_RESPONSE_DATA);

									if (rowData[1] != null
											&& rowData[7] != null) {
										list.set(
												index,
												CommonUtil
														.getFormattedValueUpToTwoDecimalInMinutes(rowData[7]));
									}

									map.put(BizConstants.KEY_JSON_RESPONSE_DATA,
											list);

								}
							}

							if (!StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_OFF)
									|| StringUtils
											.endsWithIgnoreCase(
													requestVO
															.getParameterOption(),
													BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

								if (map.containsValue(rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LEBAL_ON
										+ SERIES_LOW)) {

									isNewGroup = false;

									@SuppressWarnings("unchecked")
									List<Double> list = (List<Double>) map
											.get(BizConstants.KEY_JSON_RESPONSE_DATA);

									if (rowData[1] != null
											&& rowData[6] != null) {
										list.set(
												index,
												CommonUtil
														.getFormattedValueUpToTwoDecimalInMinutes(rowData[6]));
									}

									map.put(BizConstants.KEY_JSON_RESPONSE_DATA,
											list);

								}
							}
						}

					}

				}

				if (isNewGroup) {

					if (!StringUtils.endsWithIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_ON)
							|| StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

						Map<String, Object> mapIfNotExistsWH = new HashMap<String, Object>();

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_NAME, rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LABEL_OFF
										+ SERIES_HIGH);

						Double[] emptyArr = new Double[size];

						Arrays.fill(emptyArr, null);

						List<Double> list = Arrays.asList(emptyArr);
						if (rowData[1] != null) {
							list.set(
									index,
									rowData[3] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[3])
											: null);
						}

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_DATA, list);

						responseVO.getSeries().add(mapIfNotExistsWH);

					}
					if (!StringUtils.endsWithIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_OFF)
							|| StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

						Map<String, Object> mapIfNotExistsWH = new HashMap<String, Object>();

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_NAME, rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LEBAL_ON
										+ SERIES_HIGH);

						Double[] emptyArr = new Double[size];

						Arrays.fill(emptyArr, null);

						List<Double> list = Arrays.asList(emptyArr);
						if (rowData[1] != null) {
							list.set(
									index,
									rowData[2] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[2])
											: null);
						}

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_DATA, list);

						responseVO.getSeries().add(mapIfNotExistsWH);

					}

					if (!StringUtils.endsWithIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_ON)
							|| StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

						Map<String, Object> mapIfNotExistsWH = new HashMap<String, Object>();

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_NAME, rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LABEL_OFF
										+ SERIES_MED);

						Double[] emptyArr = new Double[size];

						Arrays.fill(emptyArr, null);

						List<Double> list = Arrays.asList(emptyArr);
						if (rowData[1] != null) {
							list.set(
									index,
									rowData[5] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[5])
											: null);
						}

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_DATA, list);

						responseVO.getSeries().add(mapIfNotExistsWH);

					}
					if (!StringUtils.endsWithIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_OFF)
							|| StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

						Map<String, Object> mapIfNotExistsWH = new HashMap<String, Object>();

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_NAME, rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LEBAL_ON
										+ SERIES_MED);

						Double[] emptyArr = new Double[size];

						Arrays.fill(emptyArr, null);

						List<Double> list = Arrays.asList(emptyArr);
						if (rowData[1] != null) {
							list.set(
									index,
									rowData[4] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[4])
											: null);
						}

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_DATA, list);

						responseVO.getSeries().add(mapIfNotExistsWH);
					}

					if (!StringUtils.endsWithIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_ON)
							|| StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

						Map<String, Object> mapIfNotExistsWH = new HashMap<String, Object>();

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_NAME, rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LABEL_OFF
										+ SERIES_LOW);

						Double[] emptyArr = new Double[size];

						Arrays.fill(emptyArr, null);

						List<Double> list = Arrays.asList(emptyArr);
						if (rowData[1] != null) {
							list.set(
									index,
									rowData[7] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[7])
											: null);
						}

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_DATA, list);

						responseVO.getSeries().add(mapIfNotExistsWH);

					}
					if (!StringUtils.endsWithIgnoreCase(
							requestVO.getParameterOption(),
							BizConstants.WORKHOUR_PARAM_OPTION_OFF)
							|| StringUtils.endsWithIgnoreCase(
									requestVO.getParameterOption(),
									BizConstants.WORKHOUR_PARAM_OPTION_ONOFF)) {

						Map<String, Object> mapIfNotExistsWH = new HashMap<String, Object>();

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_NAME, rowData[0]
										+ BizConstants.HYPHEN
										+ BizConstants.WORKHOUR_PARAM_LEBAL_ON
										+ SERIES_LOW);

						Double[] emptyArr = new Double[size];

						Arrays.fill(emptyArr, null);

						List<Double> list = Arrays.asList(emptyArr);
						if (rowData[1] != null) {
							list.set(
									index,
									rowData[6] != null ? CommonUtil
											.getFormattedValueUpToTwoDecimalInMinutes(rowData[6])
											: null);
						}

						mapIfNotExistsWH.put(
								BizConstants.KEY_JSON_RESPONSE_DATA, list);

						responseVO.getSeries().add(mapIfNotExistsWH);
					}

				}

			}

			break;
		}

	}

	/**
	 * This method is used to populate EnergyConsumptionResponseVO from result
	 * set in order to create json structure for the energy consumption chart
	 * dash board
	 * 
	 * @param rowData
	 * @param requestVO
	 * @param consumptionResponseVO
	 * @param processMapForChronological
	 */
	public void populateEnergyConsumptionResponseVO(Object[] rowData,
			StatsRequestVO requestVO,
			EnergyConsumptionResponseVO consumptionResponseVO,
			Map<Integer, TreeMap<Integer, Object>> processMapForChronological) {

		int index = 0;

		if (rowData[1] != null) {

			int outerIndex = 0;

			outer:

			for (Entry<Integer, TreeMap<Integer, Object>> entry : processMapForChronological
					.entrySet()) {

				index = outerIndex;

				for (Entry<Integer, Object> innerEntry : entry.getValue()
						.entrySet()) {

					if (StringUtils.equalsIgnoreCase(requestVO.getPeriod(),
							BizConstants.PERIOD_THISWEEK)
							|| StringUtils.equalsIgnoreCase(
									requestVO.getPeriod(),
									BizConstants.RANGE_WEEK)) {

						if (String.valueOf((rowData[1])).contains(
								CommonUtil.getCalendarWithDateFormat(
										(long) innerEntry.getValue(),
										BizConstants.DATE_FORMAT_YYYYMMDD))) {

							break outer;
						}

					} else {

						if (!StringUtils
								.equalsIgnoreCase(requestVO.getPeriod(),
										BizConstants.RANGE_3YEAR)) {

							Integer yearIndex = null;

							yearIndex = rowData.length - 1;

							BigDecimal periodNum = new BigDecimal(
									String.valueOf((rowData[1])));

							if (innerEntry.getKey() == periodNum.byteValue()
									&& entry.getKey()
											.equals(Integer.parseInt(String
													.valueOf((rowData[yearIndex]))))) {

								break outer;
							}
						} else {

							if (innerEntry.getKey().equals(
									(Integer.parseInt(String
											.valueOf((rowData[1])))))) {

								break outer;
							}

						}

					}

					index++;
				}

				outerIndex = outerIndex + entry.getValue().size();
			}

			consumptionResponseVO
					.getAverage_consumption()
					.set(index,
							rowData[2] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[2])
									: null);

			consumptionResponseVO
					.getTotal_consumption()
					.set(index,
							rowData[3] != null ? CommonUtil
									.getFormattedValueUpToTwoDecimal(rowData[3])
									: null);

		}

		// ***************************************************************************************

	}

}
