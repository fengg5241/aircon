/**
 * 
 */
package com.panasonic.b2bacns.bizportal.rc.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.pf.common.PropValueAlgo;
import com.panasonic.b2bacns.bizportal.rc.RCOperation;

/**
 * @author Narendra.Kumar
 * @author simanchal.patra
 */
@Service
public class PFStatusInfoServiceImpl implements PFStatusInfoService {

	private static final Logger logger = Logger
			.getLogger(PFStatusInfoServiceImpl.class);

	private static final StringBuilder SQL_GET_PLATFORM_STATUS_INFO_BY_FACL_PROP_ID = new StringBuilder(
			"SELECT facl_id,property_id,measure_val FROM status_info where facl_id='%s'  and property_id ='%s' order by data_datetime desc");

	private static final StringBuilder SQL_GET_IDU_MODE = new StringBuilder(
			"SELECT facl_id,property_id,measure_val FROM status_info where facl_id='%s'  and property_id IN ('%s') order by data_datetime desc limit 1");

	private static final StringBuilder SQL_GET_IDU_INFO_FOR_TEMPRATURE = new StringBuilder(
			"Select * from (Select facl_id , max(CASE WHEN v.property_id in('%s')THEN v.measure_val ELSE NULL END) AS hightempreture, ")
			.append(" max(CASE WHEN v.property_id in('%s')THEN v.measure_val ELSE NULL END) AS lowtempreture from status_info v ")
			.append(" Where  property_id in ('%s','%s') and facl_id ='%s' Group by facl_id  )temp")
			.append(" where cast( %s as numeric)  between cast(lowtempreture as numeric) and cast(hightempreture as numeric)");

	@Autowired
	private SQLDAO sqldao;

	@Override
	@Transactional
	public Object getIDUInfo(String faclId, String propertyId)
			throws HibernateException {
		String SQL_QUERY = String.format(
				SQL_GET_PLATFORM_STATUS_INFO_BY_FACL_PROP_ID.toString(),
				faclId, propertyId);
		List<?> resultList = sqldao.executeSQLSelect(SQL_QUERY);
		logger.info(SQL_QUERY);
		Object measure_val = null;
		Object[] rowData = null;
		if (resultList != null && resultList.size() > 0) {
			Iterator<?> itr = resultList.iterator();

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();
				measure_val = (String) rowData[2];

			}
		}
		return measure_val;
	}

	@Override
	@Transactional
	public Object getIDUInfo(String faclId, String lowTempPropId,
			String highTempPropId, String tempToBeCheck)
			throws HibernateException {

		String SQL_QUERY = String.format(
				SQL_GET_IDU_INFO_FOR_TEMPRATURE.toString(), highTempPropId,
				lowTempPropId, highTempPropId, lowTempPropId, faclId,
				tempToBeCheck);
		List<?> resultList = sqldao.executeSQLSelect(SQL_QUERY);
		Object[] rowData = null;
		if (resultList != null && resultList.size() > 0) {
			Iterator<?> itr = resultList.iterator();

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();

			}
		}
		return rowData;
	}

	@Override
	@Transactional
	public Object getIDURCOPVals(String faclId, String operation,
			List<String> propertyIds) throws HibernateException {

		StringBuilder propIDs = new StringBuilder(propertyIds.toString());
		int loc = propIDs.indexOf("[");
		propIDs.replace(loc, loc + 1, "");

		loc = propIDs.indexOf("]");
		propIDs.replace(loc, loc + 1, "");

		String SQL_QUERY = String.format(SQL_GET_IDU_MODE.toString(), faclId,
				propIDs.toString());

		List<?> resultList = sqldao.executeSQLSelect(SQL_QUERY);
		logger.info(SQL_QUERY);
		Object measure_val = null;
		Object[] rowData = null;

		String x_1Val = null;
		String x_2Val = null;

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();

				if (((String) rowData[1]).equals("A2_1")) {
					x_1Val = (String) rowData[2];
				} else if (((String) rowData[1]).equals("A2_2")) {
					x_2Val = (String) rowData[2];
				} else {
					// should never happen
				}
			}

			switch (RCOperation.valueOf(operation)) {

			case MODE:
				measure_val = PropValueAlgo.computeMode(x_1Val, x_2Val);
				break;
			case FANSPEED:
				measure_val = PropValueAlgo.computeFanSpeed(x_1Val, x_2Val);
				break;
			case WINDDIRECTION:
				measure_val = PropValueAlgo.computeFlapPosition(x_1Val, x_2Val);
				break;
			default:
				break;
			}

		}
		return measure_val;
	}

}
