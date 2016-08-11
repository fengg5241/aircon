package com.panasonic.b2bacns.bizportal.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean;
import com.panasonic.b2bacns.bizportal.login.vo.ChangeVO;
import com.panasonic.b2bacns.bizportal.login.vo.LoginVO;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.usermanagement.changePassword.ChangePasswordFormBean;

/**
 * This service Interface is intended to provide all functionalities related to
 * {@link User}
 * 
 * @author simanchal.patra
 * @author kumar.madhukar
 */
public interface UserService {

	/**
	 * This persists the provided User in DB.
	 * 
	 * @param user
	 *            The User
	 * @return ID of the newly created User
	 */
	public long createUser(User user) throws HibernateException;

	/**
	 * This updates the provided user details in DB.
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user) throws HibernateException;

	/**
	 * This provides all the users in DB.
	 * 
	 * @return List of {@link User}
	 */
	public List<User> getUserList() throws HibernateException;

	/**
	 * This provides the list of users which matches the provided criteria.
	 * 
	 * @param criteria
	 *            The criteria to filter out
	 * 
	 * @return List of {@link User}
	 */
	public List<User> getUserListByProperties(Map<String, Object> criteria)
			throws HibernateException;

	/**
	 * This marks as deletes the provided user in DB.
	 * 
	 * @param userId
	 *            ID of the USer
	 * @return boolean, True if success, false otherwise.
	 */
	public boolean deleteUser(long userId) throws HibernateException;

	/**
	 * This provides all the details of the User whose ID is provided.
	 * 
	 * @param userId
	 *            ID of the User
	 * @return The {@link User}
	 */
	public User findUserById(long userId) throws HibernateException;

	/**
	 * This provides the failed login attempt count for the given registered
	 * Login ID of the User.
	 * 
	 * @param userLoginID
	 *            Registered Login ID of the User
	 * 
	 * @return an integer, count of the failed login attempt.
	 */
	public int getFailedAttempt(String userLoginID);

	/**
	 * This updates the failed login attempt count for the given registered
	 * Login ID of the User.
	 * 
	 * @param userLoginID
	 *            Registered Login ID of the User
	 * @param failedAttempt
	 *            Count of the failed login attempt
	 */
	public void setFailedAttempt(String userLoginID, int failedAttempt);

	/**
	 * This authenticates the user on the basis of Email ID, Password and Unlock
	 * Token (if any) provided by the User.
	 * 
	 * @param loginDTO
	 *            User details to authenticate
	 * 
	 * @return The User {@link User}, if authentication successful.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public User authenticateUser(LoginVO loginDTO)
			throws NoSuchAlgorithmException;

	/**
	 * This provides details of the user from DB for who is related to the given
	 * tokenId.
	 * 
	 * @param tokenID
	 *            The Token ID
	 * 
	 * @return List of the User {@link User}.
	 */
	public List<User> getUserByTokenID(String tokenID);

	/**
	 * This provides details of the user from DB for who is registered with the
	 * given Email ID.
	 * 
	 * @param emailID
	 *            The Email ID
	 * 
	 * @return List of the User {@link User}.
	 */
	public User getUser(String emailID);

	/**
	 * This locks the User account for the given Login ID
	 * 
	 * @param userLoginID
	 *            Registered Login ID of the User
	 */
	public void lockUserAccount(String userLoginID);

	/**
	 * Save last visited groups by user
	 * 
	 * @param userId
	 * @param lastVisitedGroups
	 */
	public void saveLastVisitedGroups(Long userId, String lastVisitedGroups);

	/**
	 * Get last visited groups by user
	 * 
	 * @param userId
	 * 
	 * @return lastVisitedGroups
	 */
	public String getLastVisitedGroups(Long userId);

	/**
	 * This updates the successful login attempt count and login date for the
	 * given registered Login ID of the User.
	 * 
	 * @param userLoginID
	 *            Registered Login ID of the User
	 * @param lastLoginDate
	 *            Current time when the user is successful logged in
	 */
	public void resetFailedAttemptAndLoginDate(String userLoginID,
			Date lastLoginDate);

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
	 * This method is used to change user's password
	 * 
	 * @param changePasswordFormBean
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public boolean changePassword(ChangePasswordFormBean changePasswordFormBean)
			throws NoSuchAlgorithmException, IOException;
    //Added by seshu.
	public User authenticateUser(ChangePasswordFormBean changePasswordFormBean);

	//Added by seshu.
	public void resetLockedAccountAndLoginDate(String loginId, Date lastLoginDate);

}
