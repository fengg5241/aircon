package com.panasonic.b2bacns.bizportal.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.common.PermissionVO;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.email.Mail;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean;
import com.panasonic.b2bacns.bizportal.login.vo.ChangeVO;
import com.panasonic.b2bacns.bizportal.login.vo.LoginVO;
import com.panasonic.b2bacns.bizportal.persistence.FunctionalgroupPermission;
import com.panasonic.b2bacns.bizportal.persistence.Permission;
import com.panasonic.b2bacns.bizportal.persistence.Role;
import com.panasonic.b2bacns.bizportal.persistence.Rolefunctionalgrp;
import com.panasonic.b2bacns.bizportal.persistence.Session;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.useraudit.service.AuditManagementService;
import com.panasonic.b2bacns.bizportal.usermanagement.changePassword.ChangePasswordFormBean;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.PasswordEncryptionDecryption;

/**
 * This Service class implements {@link ManageLoginService} to provide all
 * functionalities related to Login and Logout activity.
 * 
 * @author simanchal.patra
 * @author kumar.madhukar
 */
@Service
public class ManageLoginServiceImpl implements ManageLoginService {

	private static final Logger logger = Logger
			.getLogger(ManageLoginServiceImpl.class);

	private static final String AUDIT_ACTION_LOGOUT = "User.logged.out.successfully";

	@Autowired
	private SessionService sessionService;

	@Autowired
	private AuditManagementService auditManagementService;

	@Autowired
	private UserService userService;

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource properties;

	@Resource(name = "mailProperties")
	private Properties propertiesMail;

	@Resource(name = "properties")
	private Properties bizProperties;

	@Autowired
	private Mail mail;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.ManageLoginService#
	 * getUserActiveSessions(java.lang.String)
	 */
	@Override
	public List<String> getUserActiveSessions(String userLoginID) {

		List<String> sessions = new ArrayList<String>();

		logger.debug(String.format(
				"Trying to fetch all active sessions of user [%s] from"
						+ " database", userLoginID));
		try {
			List<Session> sessionList = sessionService
					.getActiveSessions(userLoginID);

			if (sessionList.size() > 0) {
				for (Session session : sessionList) {
					sessions.add(session.getUniquesessionid());
				}
			}
		} catch (Exception ex) {
			logger.error(
					"Error occurred while fetching the session ID of the user - "
							+ userLoginID, ex);
		}
		return sessions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.ManageLoginService#deleteUserSession
	 * (java.lang.String)
	 */
	@Override
	public boolean deleteUserSession(String userLoginID) {

		boolean success = false;

		try {
			logger.debug(String.format(
					"Deleteing the session ID for the user [%s] from DB",
					userLoginID));

			sessionService.deleteSessions(userLoginID);
			success = true;
			auditManagementService.storeUserActivity(userLoginID,
					AUDIT_ACTION_LOGOUT);

		} catch (Exception ex) {
			logger.error("Error occurred while deleting the session ID"
					+ " from the DB for the user - " + userLoginID, ex);
		}
		return success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.ManageLoginService#saveUserSession
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public boolean saveUserSession(String userLoginID, String sessionId)
			throws BusinessFailureException {
		boolean success = false;
		try {
			logger.info(String
					.format("Persisting the new Session ID [%s] for the user [%s] in to DB",
							sessionId, userLoginID));
			sessionService.saveSessions(userLoginID, sessionId);
			success = true;
		} catch (Exception ex) {
			logger.error(
					String.format(
							"Error occurred while Persisting the new SessionID [%s] for the user [%s] in to DB",
							sessionId, userLoginID), ex);
			throw new BusinessFailureException(
					String.format(
							"Error occurred while Persisting the new SessionID [%s] for the user [%s] in to DB",
							sessionId, userLoginID), ex);
		}
		return success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.ManageLoginService#authenticateUser
	 * (com.panasonic.b2bacns.bizportal.login.vo.LoginVO, java.util.Locale)
	 */
	@Override
	@Transactional
	public LoginVO authenticateUser(LoginVO loginVo, Locale locale)
			throws BusinessFailureException {

		try {

			logger.info(String.format(
					"Trying to authenticate user having Login ID [%s]",
					loginVo.getLoginId()));

			User user = userService.authenticateUser(loginVo);

			String encryptedPassword = PasswordEncryptionDecryption
					.getEncryptedPassword(loginVo.getPassword());

			if (user != null) {

				Long timeCurrent = CommonUtil
						.setUserTimeZone(Calendar.getInstance(),
								Calendar.getInstance().getTimeZone().getID())
						.getTime().getTime();

				if (user.isLocked()) {

					Long lockedTime = user.getLockedDate().getTime();

					Long unlockTime = Long.valueOf((String) bizProperties
							.get("user.lock.minutes"));

					if ((timeCurrent - lockedTime) > (unlockTime * 60L * 1000L)) {

						// the Lock time passed

						if (user.getEncryptedpassword().equals(
								encryptedPassword)) {
							// Authentication successful, reset account lock
							// Modified by sesha
							resetLockedUserAccount(BizConstants.FAIL_RESET, loginVo);
						} else {
							// award with again 10 more attempts.
							user.setLockedDate(null);
							user.setLocked(false);
							user.setFailedattempt(1);
							userService.updateUser(user);
							logger.debug(String
									.format("Failed to authenticate User having Login ID [%s]",
											loginVo.getLoginId()));
							// resetFailAttempt(BizConstants.FAIL_INCREMENT,
							// loginDTO);
							throw new BusinessFailureException(
									"failed.signed.in");
						}
					} else {
						// reset the user account lock time
						user.setLockedDate(new Date());
						userService.updateUser(user);
						throw new BusinessFailureException(
								"account.lock.for.one.hour");
					}
				} else {
					if (!user.getEncryptedpassword().equals(encryptedPassword)) {
						// Authentication failed

						logger.debug(String
								.format("Failed to authenticate User having Login ID [%s]",
										loginVo.getLoginId()));

						resetFailAttempt(BizConstants.FAIL_INCREMENT, loginVo);

						loginVo.setErrorMessage("failed.signed.in");
						auditManagementService.storeUserActivity(
								loginVo.getLoginId(), "failed.signed.in");

						throw new BusinessFailureException("failed.signed.in");
					}
				}
				//Added by jwchan. If the firstLogin user do not set the password yet, do not mark the firstLogin user had loggedin. This is not allowed for the user login before the generated password changes.
				if(user.getPasswordchangeDate() == null){
					loginVo.setLastLoginDate(null);
					
				}
				
				Long timePasswordChangedAt = user.getPasswordchangeDate() == null ? timeCurrent
						: user.getPasswordchangeDate().getTime();

				// if password has not been reset for the days
				// mentioned in biz.properties "password.expiry.days" or
				// more
				// the password will expire and the user will have
				// to generate a new Password
				Long expireyDays = Long.valueOf((String) bizProperties
						.get("password.expiry.days"));

				if ((timeCurrent - timePasswordChangedAt) > (expireyDays * 24L * 60L * 60L * 1000L)) {
					throw new BusinessFailureException(
							"password.expired.change.password");
				}

				populateUserRoleAndPermissions(loginVo, user);

				auditManagementService.storeUserActivity(loginVo.getLoginId(),
						"successfully.signed.in");

			} else {

				logger.debug(String.format(
						"Failed to Find User having Login ID [%s]",
						loginVo.getLoginId()));

				loginVo.setErrorMessage("failed.signed.in");
				auditManagementService.storeUserActivity(loginVo.getLoginId(),
						"failed.signed.in");

				throw new BusinessFailureException("failed.signed.in");
			}
		} catch (BusinessFailureException bfe) {

			throw bfe;

		} catch (RuntimeException ex) {

			logger.error(String.format("Error occurred while trying to "
					+ "authenticate User having Login ID [%s]",
					loginVo.getLoginId()));

			loginVo.setErrorMessage("failed.signed.in");

		} catch (Exception ex) {

			logger.error(String.format("Error occurred while trying to "
					+ "authenticate User having Login ID [%s]",
					loginVo.getLoginId()));

			loginVo.setErrorMessage("failed.signed.in");

		}
		return loginVo;
	}

	
	/*
	 * service change 
	 * 
	 */
	
	//Added by seshu.
	@Override
	@Transactional
	public LoginVO authenticateUsers(LoginVO changeVO, Locale locale)
			throws BusinessFailureException {

		try {

			logger.info(String.format(
					"Trying to authenticate user having Login ID [%s]",
					changeVO.getLoginId()));

			User user = userService.authenticateUser(changeVO);

			String encryptedPassword = PasswordEncryptionDecryption
					.getEncryptedPassword(changeVO.getPassword());

			if (user != null) {
				Long timeCurrent = CommonUtil
						.setUserTimeZone(Calendar.getInstance(),
								Calendar.getInstance().getTimeZone().getID())
						.getTime().getTime();
				
				Long timePasswordChangedAt = user.getPasswordchangeDate() == null ? timeCurrent
						: user.getPasswordchangeDate().getTime();
				
				Long expireyDays = Long.valueOf((String) bizProperties
						.get("password.expiry.days"));
				if (user.isLocked()) {
				
						throw new BusinessFailureException(
								"account.lock.for.one.hour");
					
				} else if(!user.getEncryptedpassword().equals(encryptedPassword)) {
					throw new BusinessFailureException("failed.signed.in");
				} else if((timeCurrent - timePasswordChangedAt) > (expireyDays * 24L * 60L * 60L * 1000L))	{
					throw new BusinessFailureException(
							"password.expired.change.password");
				}else {
					throw new BusinessFailureException(
							"change.password");
				}

			} else {

				logger.debug(String.format(
						"Failed to Find User having Login ID [%s]",
						changeVO.getLoginId()));

				changeVO.setErrorMessage("failed.signed.in");
				auditManagementService.storeUserActivity(changeVO.getLoginId(),
						"failed.signed.in");

				throw new BusinessFailureException("failed.signed.in");
			}
		} catch (BusinessFailureException bfe) {

			throw bfe;

		} catch (Exception ex) {

			logger.error(String.format("Error occurred while trying to "
					+ "authenticate User having Login ID [%s]",
					changeVO.getLoginId()));

			changeVO.setErrorMessage("failed.signed.in");

		}
		return changeVO;
	}
	
	@Transactional
	private void populateUserRoleAndPermissions(LoginVO loginVo, User user) {
		Role role = user.getRole();

		List<PermissionVO> permissionVOList = new ArrayList<PermissionVO>();

		//add by shanf
		List<String> permissionNameList = new ArrayList<String>();

		List<String> rolePermissions = new ArrayList<String>();

		List<Rolefunctionalgrp> functGrps = role.getRoleFunctGrp();

		Set<Long> permissionIds = new HashSet<Long>();

		for (Rolefunctionalgrp roleFunctGrp : functGrps) {
			List<FunctionalgroupPermission> functGroupPermissions = roleFunctGrp
					.getFunctGrp().getFunctionalgroupPermissions();

			for (FunctionalgroupPermission functGrpPermisson : functGroupPermissions) {

				Permission permission = functGrpPermisson.getPermission();
				if (!permissionIds.contains(permission.getId())) {
					PermissionVO permissionVO = new PermissionVO();
					permissionVO.setPermissionID(permission.getId());
					permissionVO.setPermissionName(permission.getName());
					permissionVO.setPermissionUrl(permission.getUrl());
					permissionVOList.add(permissionVO);
					//add by shanf
					permissionNameList.add(permission.getName());
					if (!rolePermissions.contains(permission.getName()))
						rolePermissions.add(permission.getName());
				}
				permissionIds.add(permission.getId());

			}
		}

		if (user.getLastLoginDate() == null) {
			loginVo.setFirstLogin(true);
		} else {
			loginVo.setFirstLogin(false);
		}

		loginVo.setUserRole(role.getName());
		loginVo.setUserRoleId(String.valueOf(role.getId()));
		//add by shanf
		loginVo.setPermissionNameList(permissionNameList);
		loginVo.setPermissionsList(permissionVOList);
		loginVo.setMenuList(rolePermissions);
		loginVo.setUserId(user.getId());
		loginVo.setSuccessMessage("successfully.signed.in");
		loginVo.setLastLoginDate(user.getLastLoginDate());
		loginVo.setRole(user.getRole());
		loginVo.setCompanyId(user.getCompanyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.ManageLoginService#resetFailAttempt
	 * (java.lang.String, com.panasonic.b2bacns.bizportal.login.vo.LoginVO,
	 * javax.servlet.http.HttpServletRequest, java.util.Locale)
	 */
	
	//Added by seshu
	@Transactional
	@Override
	public void resetLockedUserAccount(String attemptType, LoginVO loginDTO)
			throws BusinessFailureException {

		try {
			if (BizConstants.FAIL_RESET.equalsIgnoreCase(attemptType)) {
				logger.debug(String
						.format("User [%s] login is successful, failed attempt is set to zero.",
								loginDTO.getLoginId()));
				userService.resetLockedAccountAndLoginDate(
						loginDTO.getLoginId(), loginDTO.getLastLoginDate());
			}
		} catch (Exception ex) {
			logger.error(
					String.format("Error occurred while set/reset the"
							+ " failed attempt in DB, for user [%s]",
							loginDTO.getLoginId()), ex);

		}
	}

	@Transactional
	@Override
	public void resetFailAttempt(String attemptType, LoginVO loginDTO)
			throws BusinessFailureException {

		try {

			if (BizConstants.FAIL_INCREMENT.equalsIgnoreCase(attemptType)) {

				int failedAttempt = userService.getFailedAttempt(loginDTO
						.getLoginId());
				failedAttempt += 1;

				if (failedAttempt == (Integer.parseInt(bizProperties.get(
						"login.fail.attempt.count").toString()) - 1)) {

					loginDTO.setErrorMessage("nineth.successive.failed.attempt");
				} else if (failedAttempt >= Integer.parseInt(bizProperties.get(
						"login.fail.attempt.count").toString())) {

					loginDTO.setErrorMessage("tenth.successive.failed.attempt");

					logger.info(String.format(
							"User account [%s] is locking due to 10 or more successive"
									+ " failed attempt to login.",
							loginDTO.getLoginId()));

					userService.setFailedAttempt(loginDTO.getLoginId(),
							failedAttempt);
					userService.lockUserAccount(loginDTO.getLoginId());

					// store user activity if operation performed
					// successfully
					auditManagementService.storeUserActivity(
							loginDTO.getLoginId(),
							BizConstants.USER_LOG_ACCOUNT_LOCKED);

					throw new BusinessFailureException(
							"account.lock.for.one.hour");
				}

				userService.setFailedAttempt(loginDTO.getLoginId(),
						failedAttempt);
			} else if (BizConstants.FAIL_RESET.equalsIgnoreCase(attemptType)) {
				logger.debug(String
						.format("User [%s] login is successful, failed attempt is set to zero.",
								loginDTO.getLoginId()));
				userService.resetFailedAttemptAndLoginDate(
						loginDTO.getLoginId(), loginDTO.getLastLoginDate());
			}
		} catch (BusinessFailureException bfe) {

			throw bfe;

		} catch (Exception ex) {
			logger.error(
					String.format("Error occurred while set/reset the"
							+ " failed attempt in DB, for user [%s]",
							loginDTO.getLoginId()), ex);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.ManageLoginService#
	 * getFunctionalGroupAndRoleType
	 * (com.panasonic.b2bacns.bizportal.login.vo.LoginVO,
	 * com.panasonic.b2bacns.bizportal.common.SessionInfo)
	 */
	@Override
	@Transactional
	public void getFunctionalGroupAndRoleType(LoginVO loginVO,
			SessionInfo sessionInfo) {
		userService.getFunctionalGroupAndRoleType(loginVO, sessionInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.ManageLoginService#
	 * updateUserFirstLoginDetails
	 * (com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean)
	 */
	@Override
	public boolean updateUserFirstLoginDetails(
			FirstLoginFormBean firstLoginFormBean, Locale locale, String path)
			throws NoSuchAlgorithmException, IOException,
			BusinessFailureException {

		return userService.updateUserFirstLoginDetails(firstLoginFormBean,
				locale, path);
	}

	//Added by seshu.
	@Override
	public boolean changePassword(ChangePasswordFormBean changePasswordFormBean)
			throws NoSuchAlgorithmException, IOException, BusinessFailureException {
		User user = userService.authenticateUser(changePasswordFormBean);
		if (user.isLocked()) {
			throw new BusinessFailureException("account.lock.for.one.hour");
		}
		else{
			return userService.changePassword(changePasswordFormBean);
		}
	}
	// /**
	// * This method is used to lock the user account after six or more
	// successive
	// * failed attempt to login.
	// *
	// * @param loginDTO
	// * The User details
	// * @param request
	// * The HTTP request
	// * @param locale
	// * The Locale
	// */
	// private void lockUser(LoginVO loginDTO, HttpServletRequest request,
	// Locale locale) {
	// try {
	//
	// userService.lockUserAccount(loginDTO.getLoginId());
	//
	// StringBuilder baseURL = new StringBuilder(request.getRequestURL()
	// .toString()
	// .replace(request.getRequestURI(), request.getContextPath()));
	// String unlockTokenUrl = BizConstants.UNLOCK_TOKEN_URL;
	// baseURL.append(unlockTokenUrl);
	// // String encryptedToken = map.get("encryptedToken");
	// String finalUnlockLink = baseURL
	// + "?token="
	// + URLEncoder.encode(encryptedToken,
	// BizConstants.ENCODE_FORMAT) + "&&email="
	// + loginDTO.getEmail();
	//
	// sendMailForUnlockedUser(loginDTO.getEmail(), finalUnlockLink,
	// "User", locale);
	// logger.debug("Unlock token is sent to Email ID - ."
	// + loginDTO.getEmail());
	// } catch (Exception ex) {
	// logger.error(
	// "Error occurred while sending the unlock account mail to the user.",
	// ex);
	// }
	// }

	// /**
	// * This sends mail to the user with encrypted token and Email ID to unlock
	// * the account.
	// *
	// * @param userEmailID
	// * @param unlockUserLink
	// * @param roleName
	// * @param locale
	// */
	// private void sendMailForUnlockedUser(String userEmailID,
	// String unlockUserLink, String roleName, Locale locale) {
	// try {
	// logger.debug("Sending mail for Unlock user account for User having ID - "
	// + userEmailID);
	// HashMap<String, Object> propertiesMap = new HashMap<>();
	//
	// String userName = "";
	// propertiesMap.put("email", userEmailID);
	// List<User> Users = userService
	// .getUserListByProperties(propertiesMap);
	//
	// if (Users != null) {
	// for (User User : Users) {
	// if (User.getRole().getName().equals(roleName)) {
	// userName = CommonUtil.ucFirst(User.getFirstname());
	//
	// if (!"".equals(User.getLastname())) {
	// userName += " "
	// + CommonUtil.ucFirst(Users.get(0)
	// .getLastname());
	// }
	// }
	// }
	// Map<String, String> mailData = new HashMap<String, String>();
	// mailData.put("emailId", userEmailID);
	// mailData.put("urlToken", unlockUserLink);
	//
	// mail.sendMail(properties.getMessage("fromEmail", null, locale),
	// userEmailID, "unlockUser", properties.getMessage(
	// "unlock.user.subject", null, locale), mailData,
	// null, true, locale);
	//
	// logger.debug("Mail sent successfully to unlock account for User having ID - "
	// + userEmailID);
	//
	// } else {
	// throw new Exception("No user found with mail ID - "
	// + userEmailID + " to send mail to recover password.");
	// }
	// } catch (Exception exp) {
	// logger.error(
	// "Error occured while sending Mail for unlock account to user ID "
	// + userEmailID, exp);
	// }
	//
	// }
}
