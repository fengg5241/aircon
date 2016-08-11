package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.MaintenanceUserList;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MaintenanceUserListServiceImpl implements
		MaintenanceUserListService {

	private GenericDAO<MaintenanceUserList> maintenanceUserDAO;

	@Autowired
	public void setDao(GenericDAO<MaintenanceUserList> daoToSet) {
		maintenanceUserDAO = daoToSet;
		maintenanceUserDAO.setClazz(MaintenanceUserList.class);
	}

	@Override
	public boolean setMaintenanceUserList(
			List<MaintenanceUserList> acMaintenanceList) {
		maintenanceUserDAO.batchSaveOrUpdate(acMaintenanceList);
		return true;
	}

	@Override
	public boolean deleteMaintenanceUserList(
			List<MaintenanceUserList> acMaintenanceList) {
		maintenanceUserDAO.batchDelete(acMaintenanceList);
		return true;
	}

}
