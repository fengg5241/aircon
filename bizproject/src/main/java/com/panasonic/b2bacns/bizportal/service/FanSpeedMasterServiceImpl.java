package com.panasonic.b2bacns.bizportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.FanspeedMaster;

/**
 * Implementation of FanSpeedMasterService interface
 * 
 * @author shobhit.singh
 * 
 */
@Service
public class FanSpeedMasterServiceImpl implements FanSpeedMasterService {

	private static final String FANSPEEDNAME = "fanspeedname";

	private GenericDAO<FanspeedMaster> dao;

	@Autowired
	public void setDao(GenericDAO<FanspeedMaster> daoToSet) {
		dao = daoToSet;
		dao.setClazz(FanspeedMaster.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.FanSpeedMasterService#
	 * getFanspeedmasterbyId(long)
	 */
	@Override
	@Transactional
	public FanspeedMaster getFanspeedmasterbyId(long fanspeedID) {

		return dao.findByID(fanspeedID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.FanSpeedMasterService#
	 * getFanspeedmasterbyName(java.lang.String)
	 */
	@Override
	public FanspeedMaster getFanspeedmasterbyName(String fanspeedName) {

		Map<String, Object> propertiesMap = new HashMap<String, Object>();
		propertiesMap.put(FANSPEEDNAME, fanspeedName);

		List<FanspeedMaster> fanspeedMasters = dao
				.findAllByProperties(propertiesMap);

		FanspeedMaster fanspeedMaster = null;

		if (fanspeedMasters != null && fanspeedMasters.size() > 0) {
			fanspeedMaster = fanspeedMasters.get(0);
		}
		return fanspeedMaster;
	}

}
