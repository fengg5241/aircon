/**
 * 
 */
package com.panasonic.b2bacns.bizportal.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dashboard.vo.ODUParameterVO;
import com.panasonic.b2bacns.bizportal.persistence.Outdoorunitparameter;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * This class is an implementation of OutdoorunitparamterService interface
 * 
 * @author akansha
 *
 */
@Service
public class OutdoorunitparamterServiceImpl implements
		OutdoorunitparamterService {

	private GenericDAO<Outdoorunitparameter> dao;

	private List<ODUParameterVO> cachedOutdoorUnitParameter = new ArrayList<ODUParameterVO>();

	private Map<String, List<ODUParameterVO>> cachedParametersByTypeMap = new HashMap<String, List<ODUParameterVO>>();

	private Map<String, String> cachedParametersByNameMap = new HashMap<String, String>();

	@Autowired
	public void setDao(GenericDAO<Outdoorunitparameter> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Outdoorunitparameter.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.OutdoorunitparamterService#
	 * getOutdoorunitparamters()
	 */
	@Override
	public List<Outdoorunitparameter> getOutdoorunitparamters()
			throws HibernateException {
		return dao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.OutdoorunitparamterService#
	 * getOutdoorunitparamterByProperties(java.util.Map)
	 */
	@Override
	public List<Outdoorunitparameter> getOutdoorunitparamterByProperties(
			Map<String, Object> properties) throws HibernateException {
		return dao.findAllByProperties(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.OutdoorunitparamterService#
	 * getParameters()
	 */
	@Override
	public List<ODUParameterVO> getParameters() throws IllegalAccessException,
			InvocationTargetException {
		if (cachedOutdoorUnitParameter.isEmpty()
				|| cachedOutdoorUnitParameter.size() == 0) {

			List<Outdoorunitparameter> oduParameterList = dao.findAll();

			List<ODUParameterVO> vrfParamsList = new ArrayList<>();

			List<ODUParameterVO> ghpParamsList = new ArrayList<>();

			ODUParameterVO parameterVO = null;

			for (Outdoorunitparameter oduParm : oduParameterList) {

				parameterVO = new ODUParameterVO();

				BeanUtils.copyProperties(parameterVO, oduParm);

				cachedOutdoorUnitParameter.add(parameterVO);

				cachedParametersByNameMap.put(parameterVO.getParameterName(),
						parameterVO.getDisplayName());

				if (null != oduParm
						&& oduParm.getType().equals(BizConstants.ODU_TYPE_GHP)) {
					ghpParamsList.add(parameterVO);
				} else if (null != oduParm
						&& oduParm.getType().equals(BizConstants.ODU_TYPE_VRF)) {
					vrfParamsList.add(parameterVO);
				}

			}

			cachedParametersByTypeMap.put(BizConstants.ODU_TYPE_GHP,
					ghpParamsList);
			cachedParametersByTypeMap.put(BizConstants.ODU_TYPE_VRF,
					vrfParamsList);
		}

		return cachedOutdoorUnitParameter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.OutdoorunitparamterService#
	 * getParametersByType(java.lang.String)
	 */
	@Override
	public List<ODUParameterVO> getParametersByType(String oduType)
			throws IllegalAccessException, InvocationTargetException {

		if (cachedOutdoorUnitParameter.isEmpty()
				|| cachedOutdoorUnitParameter.size() == 0) {
			getParameters();
		}

		return cachedParametersByTypeMap.get(oduType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.OutdoorunitparamterService#
	 * getDisplayNames(java.util.List)
	 */
	@Override
	public List<String> getDisplayNames(List<String> parameterNameList)
			throws IllegalAccessException, InvocationTargetException {

		if (cachedOutdoorUnitParameter.isEmpty()
				|| cachedOutdoorUnitParameter.size() == 0) {
			getParameters();
		}

		List<String> displayNameList = new ArrayList<String>();

		for (String parameterName : parameterNameList) {
			displayNameList.add(cachedParametersByNameMap.get(parameterName));
		}

		return displayNameList;
	}

}
