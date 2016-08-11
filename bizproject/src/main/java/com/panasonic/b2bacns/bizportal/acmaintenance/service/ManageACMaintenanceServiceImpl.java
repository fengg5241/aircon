/**
 * 
 */
package com.panasonic.b2bacns.bizportal.acmaintenance.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.acmaintenance.dao.ACMaintenanceDAO;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceRequest;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceSettingVO;
import com.panasonic.b2bacns.bizportal.acmaintenance.vo.ACMaintenanceUserVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.MaintenanceSetting;
import com.panasonic.b2bacns.bizportal.persistence.MaintenanceTypeMaster;
import com.panasonic.b2bacns.bizportal.persistence.MaintenanceUserList;
import com.panasonic.b2bacns.bizportal.service.MaintenanceSettingService;
import com.panasonic.b2bacns.bizportal.service.MaintenanceUserListService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * @author amitesh.arya
 * 
 */
@Service
public class ManageACMaintenanceServiceImpl implements
		ManageACMaintenanceService {

	private static final Logger logger = Logger
			.getLogger(ManageACMaintenanceServiceImpl.class);

	@Autowired
	private ACMaintenanceDAO acMaintenanceDAO;

	@Autowired
	private MaintenanceUserListService maintenanceUserListService;

	@Autowired
	private MaintenanceSettingService maintenanceSettingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.service.
	 * ManageACMaintenanceService
	 * #getMaintenanceSetting(com.panasonic.b2bacns.bizportal
	 * .acmaintenance.vo.ACMaintenanceRequest)
	 */
	@Override
	public List<ACMaintenanceSettingVO> getMaintenanceSetting(
			ACMaintenanceRequest acMaintenanceRequest) {

		List<ACMaintenanceSettingVO> acMaintenanceDetails = null;
		try {
			acMaintenanceDetails = acMaintenanceDAO
					.getMaintenanceSetting(acMaintenanceRequest.getSiteID());
			logger.debug("Get AC Maintenance Setting request: "
					+ acMaintenanceRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the AC Maintenance Setting Details : "
					+ e.getMessage());
		}
		return acMaintenanceDetails;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.service.
	 * ManageACMaintenanceService
	 * #setMaintenanceSetting(com.panasonic.b2bacns.bizportal
	 * .acmaintenance.vo.ACMaintenanceRequest)
	 */
	@Override
	@Transactional
	public boolean setMaintenanceSetting(
			ACMaintenanceRequest acMaintenanceRequest) {
		List<MaintenanceSetting> acMaintenanceList = new ArrayList<MaintenanceSetting>();
		MaintenanceSetting maintenanceSettingObj;
		logger.debug("Set AC Maintenance Setting request: "
				+ acMaintenanceRequest);
		try {
			for (ACMaintenanceSettingVO acMaintenanceSettingVO : acMaintenanceRequest
					.getMaintenanceTypeList()) {
				if (acMaintenanceSettingVO.getId() == null
						|| acMaintenanceSettingVO.getValue() == null)
					continue;
				maintenanceSettingObj = new MaintenanceSetting();
				Long settingId = acMaintenanceSettingVO.getId();
				//Added by seshu.
				if (settingId != 0) {
					maintenanceSettingObj.setId(acMaintenanceSettingVO.getId());
				}
				maintenanceSettingObj.setThreshold(new Long(
						(int) acMaintenanceSettingVO.getValue()));
				Group group = new Group();
				group.setId(acMaintenanceRequest.getSiteID());
				MaintenanceTypeMaster typeMaster = new MaintenanceTypeMaster();
				typeMaster.setId(new Long(BizConstants.OutdoorMaintType
						.valueOf(acMaintenanceSettingVO.getName()).value));
				maintenanceSettingObj.setGroup(group);
				maintenanceSettingObj.setMaintenanceTypeMaster(typeMaster);
				acMaintenanceList.add(maintenanceSettingObj);
			}
			if (!acMaintenanceList.isEmpty()) {
				logger.debug(String
						.format("Setting Maintenance Request. Total records:%d, set records in DB: %d",
								acMaintenanceRequest.getMaintenanceTypeList()
										.size(), acMaintenanceList.size()));
				return maintenanceSettingService
						.setMaintenanceSetting(acMaintenanceList);
			}
		} catch (IllegalArgumentException e) {
			throw new GenericFailureException(
					"Name is not valid in maintenanceTypeList");
		}
		logger.debug("No Record(s) found to update.");
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.service.
	 * ManageACMaintenanceService
	 * #getMaintenanceSettingMailList(com.panasonic.b2bacns
	 * .bizportal.acmaintenance.vo.ACMaintenanceRequest)
	 */
	@Override
	public List<ACMaintenanceUserVO> getMaintenanceSettingMailList(
			ACMaintenanceRequest acMaintenanceRequest) {
		List<ACMaintenanceUserVO> acMaintenanceUserDetails = null;
		try {
			acMaintenanceUserDetails = acMaintenanceDAO
					.getMaintenanceSettingMailList(acMaintenanceRequest
							.getCompanyID());
			logger.debug("Get AC Maintenance Setting request: "
					+ acMaintenanceRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the AC Maintenance Setting Details : "
					+ e.getMessage());
		}
		return acMaintenanceUserDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.service.
	 * ManageACMaintenanceService
	 * #setMaintenanceSettingMailList(com.panasonic.b2bacns
	 * .bizportal.acmaintenance.vo.ACMaintenanceRequest)
	 */
	@Override
	@Transactional
	public boolean setMaintenanceSettingMailList(
			ACMaintenanceRequest acMaintenanceRequest) {
		List<MaintenanceUserList> acMaintenanceUserList = new ArrayList<MaintenanceUserList>();
		List<MaintenanceUserList> acDeleteUserList = new ArrayList<MaintenanceUserList>();
		MaintenanceUserList maintenanceSettingUserObj;
		boolean status = false;
		logger.debug("Set AC Maintenance User Email request: "
				+ acMaintenanceRequest);
		try {
			if (acMaintenanceRequest.getAddUserList() != null) {
				for (String email : acMaintenanceRequest.getAddUserList()) {
					Company company = new Company();
					company.setId(acMaintenanceRequest.getCompanyID());

					maintenanceSettingUserObj = new MaintenanceUserList();
					maintenanceSettingUserObj.setCompany(company);
					maintenanceSettingUserObj.setUserMailId(email);
					maintenanceSettingUserObj.setIssend(true);
					acMaintenanceUserList.add(maintenanceSettingUserObj);
				}
				if (!acMaintenanceUserList.isEmpty()) {
					logger.debug(String
							.format("User Email Request. Total records:%d, set records in DB: %d",
									acMaintenanceRequest.getAddUserList()
											.size(), acMaintenanceUserList
											.size()));
					status = maintenanceUserListService
							.setMaintenanceUserList(acMaintenanceUserList);
				}
			}
			if (acMaintenanceRequest.getDeleteUserList() != null) {
				for (Long emailID : acMaintenanceRequest.getDeleteUserList()) {
					Company company = new Company();
					company.setId(acMaintenanceRequest.getCompanyID());

					maintenanceSettingUserObj = new MaintenanceUserList();
					maintenanceSettingUserObj.setId(emailID);
					maintenanceSettingUserObj.setCompany(company);
					acDeleteUserList.add(maintenanceSettingUserObj);
				}
				if (!acDeleteUserList.isEmpty()) {
					logger.debug(String
							.format("User Email for Delete Request. Total records:%d, set records in DB: %d",
									acMaintenanceRequest.getDeleteUserList()
											.size(), acDeleteUserList.size()));
					status = maintenanceUserListService
							.deleteMaintenanceUserList(acDeleteUserList);
				}
			}
		} catch (Exception e) {
			logger.error("An Exception occured while Adding or deleting "
					+ " AC Maintenance Setting User Details records", e);
			return false;
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.service.
	 * ManageACMaintenanceService
	 * #getCurrentRemainingMaintenanceTime(com.panasonic
	 * .b2bacns.bizportal.acmaintenance.vo.ACMaintenanceRequest)
	 */
	@Override
	public List<ACMaintenanceSettingVO> getCurrentRemainingMaintenanceTime(
			ACMaintenanceRequest acMaintenanceRequest) {

		List<ACMaintenanceSettingVO> acMaintenanceDetails = null;
		try {
			acMaintenanceDetails = acMaintenanceDAO
					.getCurrentRemainingMaintenanceTime(acMaintenanceRequest
							.getOduID());
			logger.debug("Get AC Maintenance Setting request: "
					+ acMaintenanceRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the AC Maintenance Status Data : "
					+ e.getMessage());
		}
		return acMaintenanceDetails;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.acmaintenance.service.
	 * ManageACMaintenanceService
	 * #resetThresholdAlert(com.panasonic.b2bacns.bizportal
	 * .acmaintenance.vo.ACMaintenanceRequest)
	 */
	@Override
	@Transactional
	public boolean resetThresholdAlert(
			ACMaintenanceRequest acMaintenanceRequest, String timeZone) {
		logger.debug("Reset AC Threshold Alert request: "
				+ acMaintenanceRequest);
		return acMaintenanceDAO.resetThresholdAlert(
				acMaintenanceRequest.getOduID(),
				acMaintenanceRequest.getMaintenanceID(), timeZone);
	}
}
