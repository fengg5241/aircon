package com.panasonic.b2bacns.bizportal.co2factor.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.co2factor.vo.CO2FactorRequestVO;
import com.panasonic.b2bacns.bizportal.co2factor.vo.CO2FactorVO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.Groupco2factor;
import com.panasonic.b2bacns.bizportal.service.GroupCO2FactorService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.DaoConstants;

/**
 * @author Narendra.Kumar
 * 
 */
@Service
public class CO2FactorServiceImpl implements CO2FactorService {
	private static final Logger logger = LogManager
			.getLogger(CO2FactorServiceImpl.class);
	private static final StringBuilder SQL_GET_CO2_FACTOR = new StringBuilder(
			"select * from usp_getsites(cast(:userId as Bigint),cast(:companyId as Bigint))");

	private static final StringBuilder SQL_GET_CO2_FACTOR_SITEID = new StringBuilder(
			"select groupid, co2factor,logdate from groupco2factor where groupid = :id");

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	@Autowired
	private GroupCO2FactorService groupCO2FactorService;

	@Autowired
	private SQLDAO sqldao;

	@Override
	public List<CO2FactorVO> getCO2Factor(Long companyId, Long userId) {
		List<CO2FactorVO> co2FactorVOs = getCO2FactorbyGroupAndUserId(
				companyId, userId);
		logger.info("CO2FactorServiceImpl getCO2Factor executed");
		return co2FactorVOs;
	}

	@Override
	public boolean saveCO2Factor(List<CO2FactorRequestVO> cO2FactorRequestVO) {
		boolean saveStatus = false;
		List<Groupco2factor> groupco2factorList = new ArrayList<Groupco2factor>();
		int saveCount = 0;
		for (CO2FactorRequestVO co2FactorRequest : cO2FactorRequestVO) {
			if (co2FactorRequest.getSiteIds() == null
					|| co2FactorRequest.getSiteIds() == 0
					|| co2FactorRequest.getCo2FactorValue() == null
					|| co2FactorRequest.getCo2FactorValue() == 0
					|| co2FactorRequest.getStartDate() == null
					|| co2FactorRequest.getStartDate().isEmpty()) {
				throw new GenericFailureException(
						CommonUtil
								.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR));
			}
			/*java.util.Date startDate = CommonUtil.stringToDate(
					co2FactorRequest.getStartDate(),
					BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS);

			if (startDate == null) {
				throw new GenericFailureException(
						CommonUtil
								.getJSONErrorMessage(BizConstants.DATE_FORMAT_ERROR));
			}*/
            
			//Modified by seshu
			Groupco2factor groupCO2persist = new Groupco2factor();
			Group gr = new Group();
			groupCO2persist.setCo2factor(co2FactorRequest.getCo2FactorValue());
			gr.setId(co2FactorRequest.getSiteIds());
			groupCO2persist.setGroup(gr);
			Calendar cal = Calendar.getInstance();
			groupCO2persist.setLogdate(new Timestamp(cal.getTimeInMillis()));
			groupco2factorList.add(groupCO2persist);
			saveCount++;
		}

		if (groupco2factorList != null) {

			if (groupco2factorList.size() > 0 && saveCount > 0) {

				groupCO2FactorService
						.saveOrUpdateGroupCO2Factor(groupco2factorList);
				saveStatus = true;
			}
		}
		return saveStatus;
	}

	public List<CO2FactorVO> getCO2FactorbyGroupAndUserId(Long companyId,
			Long userId) throws HibernateException {
		Map<String, Object> queryMap = new LinkedHashMap<String, Object>();
		queryMap.put(BizConstants.USER_ID, userId);
		queryMap.put(BizConstants.COMPANY_ID, companyId);
		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put("id", StandardBasicTypes.LONG); // 0
		scalarMapping.put("groupcategoryid", StandardBasicTypes.LONG); // 1
		scalarMapping.put("path", StandardBasicTypes.STRING); // 2
		scalarMapping.put("co2factor", StandardBasicTypes.DOUBLE); // 3
		scalarMapping.put("logdate", StandardBasicTypes.TIMESTAMP); // 4

		String sqlQuery = String.format(SQL_GET_CO2_FACTOR.toString());
		List<CO2FactorVO> co2FactorVOs = new ArrayList<CO2FactorVO>();
		List<?> result = sqldao.executeSQLSelect(sqlQuery, scalarMapping,
				queryMap);
		if (result != null && !result.isEmpty()) {
			Iterator<?> itr = result.iterator();
			Object[] rowData = null;
			while (itr.hasNext()) {
				CO2FactorVO co2FactorVO = new CO2FactorVO();
				rowData = (Object[]) itr.next();

				co2FactorVO.setSiteId((Long) rowData[0]);
				co2FactorVO.setCo2FactorValue(rowData[3] == null ? null
						: (Double) rowData[3]);
				co2FactorVO
						.setStartDate(rowData[4] == null ? BizConstants.HYPHEN
								: CommonUtil.dateToString(
										(Timestamp) rowData[4], DATE_FORMAT));
				co2FactorVOs.add(co2FactorVO);
			}

		}

		return co2FactorVOs;
	}

	@Override
	public List<CO2FactorVO> getCO2Factor(Long siteId) {
		logger.debug("Calling the notification details for indoor units");
		CO2FactorVO co2FactorVO;
		List<CO2FactorVO> co2FactorVOList = new ArrayList<CO2FactorVO>();

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();

		scalarMapping.put("groupid", StandardBasicTypes.LONG); // 0
		scalarMapping.put("co2factor", StandardBasicTypes.DOUBLE); // 1
		scalarMapping.put("logdate", StandardBasicTypes.TIMESTAMP); // 2

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(DaoConstants.ID, siteId);
		try {
			List<?> result = sqldao.executeSQLSelect(
					SQL_GET_CO2_FACTOR_SITEID.toString(), scalarMapping,
					parameter);
			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					co2FactorVO = new CO2FactorVO();

					rowData = (Object[]) itr.next();
					co2FactorVO.setSiteId(rowData[0] == null ? null
							: (Long) rowData[0]);
					co2FactorVO.setCo2FactorValue(rowData[1] == null ? null
							: (Double) rowData[1]);
					co2FactorVO
							.setStartDate(rowData[2] == null ? BizConstants.HYPHEN
									: CommonUtil
											.dateToString(
													(Timestamp) rowData[2],
													DATE_FORMAT));

					co2FactorVOList.add(co2FactorVO);
				}
			}
		} catch (HibernateException sqlExp) {
			logger.error("An Exception occured while fetching data from"
					+ " 'CO2Factor' for the following Exception :"
					+ sqlExp.getMessage());
		}
		return co2FactorVOList;
	}

}
