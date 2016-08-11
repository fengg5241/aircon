/**
 * 
 */
package com.panasonic.b2bacns.bizportal.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.dashboard.vo.ODUParameterVO;
import com.panasonic.b2bacns.bizportal.persistence.Outdoorunitparameter;

/**
 * This interface is used to perform operations on Outdoorunitparameter
 * 
 * @author akansha
 *
 */
public interface OutdoorunitparamterService {

	/**
	 * This method is used to find the List of all Outdoorunitparameter
	 * 
	 * @return The List of {@link Outdoorunitparameter}
	 * @throws HibernateException
	 */
	public List<Outdoorunitparameter> getOutdoorunitparamters()
			throws HibernateException;

	/**
	 * This method is used to find the List of Outdoorunitparameter for the
	 * given properties
	 * 
	 * @param properties
	 *            Properties of Outdoorunitparameter
	 * @return The List of {@link Outdoorunitparameter}
	 * @throws HibernateException
	 */
	public List<Outdoorunitparameter> getOutdoorunitparamterByProperties(
			Map<String, Object> properties) throws HibernateException;

	/**
	 * This method is used to get all the parameters of ODU
	 * 
	 * @return The List of {@link ODUParameterVO}
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<ODUParameterVO> getParameters() throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * This method is used to get all the parameters of ODU based on its
	 * type(GHP or VRF)
	 * 
	 * @param type
	 *            The ODUType of the Entity
	 * @return The List of {@link ODUParameterVO}
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<ODUParameterVO> getParametersByType(String type)
			throws IllegalAccessException, InvocationTargetException;

	/**
	 * This method is used to get display names of parameter of ODU
	 * 
	 * @param parameterName
	 *            The list of parameter names of ODU
	 * @return The List of {@link String}
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<String> getDisplayNames(List<String> parameterName)
			throws IllegalAccessException, InvocationTargetException;

}
