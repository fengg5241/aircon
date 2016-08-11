package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.MaintenanceSetting;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MaintenanceSettingServiceImpl implements MaintenanceSettingService {

	private GenericDAO<MaintenanceSetting> maintenanceSettingDAO;

	@Autowired
	public void setDao(GenericDAO<MaintenanceSetting> daoToSet) {
		maintenanceSettingDAO = daoToSet;
		maintenanceSettingDAO.setClazz(MaintenanceSetting.class);
	}

	@Override
	@Transactional
	public boolean setMaintenanceSetting(
			List<MaintenanceSetting> acMaintenanceList) {
		maintenanceSettingDAO.batchSaveOrUpdate(acMaintenanceList);
		return true;
	}

}
