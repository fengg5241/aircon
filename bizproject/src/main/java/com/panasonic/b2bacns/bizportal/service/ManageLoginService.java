package com.panasonic.b2bacns.bizportal.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean;
import com.panasonic.b2bacns.bizportal.login.vo.ChangeVO;
import com.panasonic.b2bacns.bizportal.login.vo.LoginVO;
import com.panasonic.b2bacns.bizportal.usermanagement.changePassword.ChangePasswordFormBean;

/**
 * This Interface is intended to provide all functionalities related to User
 * Login and Logout activities
 * 
 * @author simanchal.patra
 * @author kumar.madhukar
 *
 */
public interface ManageLoginService {

	/**
	 * This provides all the Active Session's ID present in database for the
	 * given User.
	 * 
	 * @param userLoginID
	 *            Registered Login ID of the user
	 * 
	 * @return List of ID of sessions
	 */
	public List<String> getUserActiveSessions(String userLoginID);

	/**
	 * This removes the all the active sessions from DB of the user whose Login
	 * ID is provided.
	 * 
	 * @param userLoginID
	 *            Registered Login ID of the user
	 * 
	 * @return True, if success
	 */
	public boolean deleteUserSession(String userLoginID);

	/**
	 * This saves the active session ID in DB of the user whose Login ID is
	 * provided.
	 * 
	 * @param userLoginID
	 *            Registered Login ID of the user
	 * @param sessionId
	 *            ID of the Active session of the same User
	 * 
	 * @return True, if success
	 * @throws BusinessFailureException
	 */
	public boolean saveUserSession(String userLoginID, String sessionId)
			throws BusinessFailureException;

	/**
	 * This authenticates the User with the provided Email ID and password. It
	 * provides the User related details like Role, Permission etc., if the
	 * authentication is successful. Else the corresponding error message.
	 * 
	 * @param loginVO
	 *            Login details provided by the User
	 * @param locale
	 *            Locale chosen by the same User
	 * 
	 * @return The LoginVO containing the User details or Error message
	 */
	public LoginVO authenticateUser(LoginVO loginVO, Locale locale)
			throws BusinessFailureException;

	/**
	 * This method is used to reset the failed attempt value in the User table.
	 * 
	 * @param attemptType
	 * @throws BusinessFailureException
	 */
	public void resetFailAttempt(String attemptType, LoginVO loginDTO)
			throws BusinessFailureException;

	/**
	 * This method is used to get functional groups and role type.
	 * 
	 * @param loginVO
	 * @param sessionInfo
	 */
	public void getFunctionalGroupAndRoleType(LoginVO loginVO,
			SessionInfo sessionInfo);

	/**
	 * This method is used to update user details at the time of first login.
	 * 
	 * @param firstLoginFormBean
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws BusinessFailureException
	 */
	public boolean updateUserFirstLoginDetails(
			FirstLoginFormBean firstLoginFormBean, Locale locale, String path)
			throws NoSuchAlgorithmException, IOException,
			BusinessFailureException;

	/**
	 * This method is used to change password of the user.
	 * 
	 * @param changePasswordFormBean
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws BusinessFailureException 
	 */
	 //Added by seshu
	public boolean changePassword(ChangePasswordFormBean changePasswordFormBean)
			throws NoSuchAlgorithmException, IOException, BusinessFailureException;
    //Added by seshu
	public LoginVO authenticateUsers(LoginVO changeVO, Locale locale)
			throws BusinessFailureException;

	//Added by seshu
	public	void resetLockedUserAccount(String attemptType, LoginVO loginDTO) throws BusinessFailureException;
	

}
