/**
 * 
 */
package com.panasonic.b2bacns.bizportal.dashboard.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.ODUParameterInfo;
import com.panasonic.b2bacns.bizportal.dashboard.vo.ODUParameterVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.service.OutdoorunitparamterService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This class is an implementation of ManageParameterService interface
 * 
 * @author akansha
 *
 */
@Service
public class ManageParameterServiceImpl implements ManageParameterService {

	@Autowired
	private OutdoorunitparamterService outdoorunitparamterService;

	@Autowired
	private SQLDAO sqldao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dashboard.service.ManageParameterService
	 * #getODUParameters(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ODUParameterInfo> getODUParameters(Long id, String idType,
			String selectedParamList) throws IllegalAccessException,
			InvocationTargetException {

		List<String> parameterNameList = new LinkedList<String>();
		List<String> displayNameList = new LinkedList<String>();

		List<ODUParameterInfo> parameterInfoList = null;

		String oduType = BizConstants.EMPTY_STRING;

		switch (idType) {
		case BizConstants.ID_TYPE_OUTDOOR_GHP:

			oduType = BizConstants.ODU_TYPE_GHP;

			break;

		case BizConstants.ID_TYPE_OUTDOOR_VRF:

			oduType = BizConstants.ODU_TYPE_VRF;

			break;

		default:

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.REQUEST_RESOURCE_NOT_AVAILABLE);
			throw new GenericFailureException(customErrorMessage);
		}

		if (!selectedParamList.equals(BizConstants.EMPTY_STRING)) {

			selectedParamList = selectedParamList.replace(
					BizConstants.START_INDEX_STRING, BizConstants.EMPTY_STRING)
					.replace(BizConstants.CLOSE_INDEX_STRING,
							BizConstants.EMPTY_STRING);
		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.REQUEST_RESOURCE_NOT_AVAILABLE);
			throw new GenericFailureException(customErrorMessage);
		}

		if (!selectedParamList.equals(BizConstants.EMPTY_STRING)) {

			parameterNameList = CommonUtil
					.convertStringToList(selectedParamList);
			displayNameList = outdoorunitparamterService
					.getDisplayNames(parameterNameList);
		} else {

			List<ODUParameterVO> oduParameterList = outdoorunitparamterService
					.getParametersByType(oduType);
			if (oduParameterList != null && oduParameterList.size() > 0) {

				for (ODUParameterVO oduParameterVO : oduParameterList) {

					parameterNameList.add(oduParameterVO.getParameterName());
					displayNameList.add(oduParameterVO.getDisplayName());
				}
			} else {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

				throw new GenericFailureException(customErrorMessage);
			}

		}

		if (parameterNameList != null && parameterNameList.size() > 0) {

			parameterInfoList = getParameterData(id, parameterNameList, idType,
					displayNameList);
		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			throw new GenericFailureException(customErrorMessage);
		}

		return parameterInfoList;
	}

	/**
	 * 
	 * @param id
	 *            The ID of Entity
	 * @param parameterNameList
	 *            The List of parameter names
	 * @param idType
	 *            The idType of Entity
	 * @param displayNameList
	 *            The List of display names of parameters
	 * @return The List of {@link ODUParameterInfo}
	 */
	private List<ODUParameterInfo> getParameterData(Long id,
			List<String> parameterNameList, String idType,
			List<String> displayNameList) {

		String parameterNames = CommonUtil
				.convertCollectionToString(parameterNameList);

		Object[] parameterValues = sqldao.getParameters(id, parameterNames,
				idType);

		List<ODUParameterInfo> parameterInfoList = new ArrayList<ODUParameterInfo>();

		ODUParameterInfo parameterInfo = null;

		String creationDate = null;

		if (parameterValues != null && parameterValues.length > 0) {

			for (int i = 0; i < parameterNameList.size(); i++) {

				parameterInfo = new ODUParameterInfo();

				creationDate = null;

				parameterInfo.setParameterName(parameterNameList.get(i));
				parameterInfo
						.setValue(parameterValues[i] != null ? parameterValues[i]
								.toString() : BizConstants.EMPTY_STRING);

				if (parameterValues[(parameterValues.length) - 1] != null) {
					creationDate = CommonUtil
							.dateToString((Date) parameterValues[(parameterValues.length) - 1]);
				} else {
					creationDate = BizConstants.EMPTY_STRING;
				}
				parameterInfo.setCreationDate(creationDate);

				parameterInfo.setDisplayName(displayNameList.get(i));

				parameterInfoList.add(parameterInfo);
			}
		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);

			throw new GenericFailureException(customErrorMessage);
		}

		return parameterInfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dashboard.service.ManageParameterService
	 * #getODUParameterDetails(java.lang.Long, java.lang.String)
	 */
	@Override
	public void getODUParameterDetails(Long id, String idType) {

		String oduType = BizConstants.EMPTY_STRING;

		switch (idType) {
		case BizConstants.ID_TYPE_OUTDOOR_GHP:

			oduType = BizConstants.ODU_TYPE_GHP;

			break;

		case BizConstants.ID_TYPE_OUTDOOR_VRF:

			oduType = BizConstants.ODU_TYPE_VRF;

			break;

		default:

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.REQUEST_RESOURCE_NOT_AVAILABLE);
			throw new GenericFailureException(customErrorMessage);
		}

	}
}
