package com.panasonic.b2bacns.bizportal.metadata.service;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.metadata.vo.MetaIndoorUnitVO;
import com.panasonic.b2bacns.bizportal.metadata.vo.MetaOutdoorUnitVO;
import com.panasonic.b2bacns.bizportal.persistence.Indoorunit;
import com.panasonic.b2bacns.bizportal.persistence.Metaindoorunit;
import com.panasonic.b2bacns.bizportal.persistence.Metaoutdoorunit;
import com.panasonic.b2bacns.bizportal.persistence.Outdoorunit;
import com.panasonic.b2bacns.bizportal.service.IndoorUnitsService;
import com.panasonic.b2bacns.bizportal.service.OutdoorUnitsService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * This class is an implementation of ManageMetaDataService interface
 * 
 * @author verma.ashish
 * 
 */
@Service
public class ManageMetaDataServiceImpl implements ManageMetaDataService {

	@Autowired
	private IndoorUnitsService indoorUnitsService;

	@Autowired
	private OutdoorUnitsService outdoorUnitsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.metadata.service.ManageMetaDataService#
	 * getOutdoorMetaData(java.lang.Long)
	 */
	@Override
	public MetaOutdoorUnitVO getOutdoorMetaData(long id)
			throws IllegalAccessException, InvocationTargetException,
			HibernateException {

		MetaOutdoorUnitVO metaOutdoorUnitVO = new MetaOutdoorUnitVO();

		Outdoorunit outdoorUnit = outdoorUnitsService.getOutdoorUnitById(id);

		if (outdoorUnit != null) {
			Metaoutdoorunit metaOutDoorUnit = outdoorUnit.getMetaoutdoorunit();
			if (metaOutDoorUnit == null) {
				throw new GenericFailureException(BizConstants.NO_RECORDS_FOUND);
			}
			BeanUtils.copyProperties(metaOutdoorUnitVO, metaOutDoorUnit);
			return metaOutdoorUnitVO;
		} else {
			throw new GenericFailureException(BizConstants.NO_RECORDS_FOUND);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.metadata.service.ManageMetaDataService#
	 * getIndoorMetaData(java.lang.Long)
	 */

	@Override
	public MetaIndoorUnitVO getIndoorMetaData(long id)
			throws IllegalAccessException, InvocationTargetException,
			HibernateException, Exception {

		MetaIndoorUnitVO metaIndoorUnitVO = new MetaIndoorUnitVO();

		Indoorunit indoorUnit = indoorUnitsService.getIndoorunitById(id);

		if (indoorUnit != null) {
			Metaindoorunit metaIndoorunit = indoorUnit.getMetaindoorunit();
			if (metaIndoorunit==null) {
				throw new GenericFailureException(BizConstants.NO_RECORDS_FOUND);
			}
			BeanUtils.copyProperties(metaIndoorUnitVO, metaIndoorunit);
			return metaIndoorUnitVO;
		} else {
			throw new GenericFailureException(BizConstants.NO_RECORDS_FOUND);
		}
	}

}
