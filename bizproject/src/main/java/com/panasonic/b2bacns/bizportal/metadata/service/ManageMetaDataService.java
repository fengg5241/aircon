package com.panasonic.b2bacns.bizportal.metadata.service;

import java.lang.reflect.InvocationTargetException;
import org.hibernate.HibernateException;
import com.panasonic.b2bacns.bizportal.metadata.vo.MetaIndoorUnitVO;
import com.panasonic.b2bacns.bizportal.metadata.vo.MetaOutdoorUnitVO;

/**
 * This interface contains all MetaData related methods
 * 
 * @author verma.ashish
 * 
 */
public interface ManageMetaDataService {
	
	/**
	 * Get indoorMetaData value for indoor Unit
	 * 
	 *  @param indoorUnitID
	 *            ID of the IndoorUnit
	 * @return
	 */
	public MetaIndoorUnitVO getIndoorMetaData(long id)
			throws IllegalAccessException, InvocationTargetException,
			HibernateException, Exception;

	/**
	 * Get outdoorMetaData value for outdoor Unit
	 * 
	 * @param outdoorUnitID
	 * 			ID of the OutDoorUnit
	 * @return
	 */
	public MetaOutdoorUnitVO getOutdoorMetaData(long id)
			throws IllegalAccessException, InvocationTargetException,
			HibernateException, Exception;

}
