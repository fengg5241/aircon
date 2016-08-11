package com.panasonic.b2bacns.bizportal.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.email.dao.Email;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean;
import com.panasonic.b2bacns.bizportal.login.vo.ChangeVO;
import com.panasonic.b2bacns.bizportal.login.vo.LoginVO;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.usermanagement.changePassword.ChangePasswordFormBean;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.PasswordEncryptionDecryption;

/**
 * This is the implementation for the User service.
 * 
 * @author kumar.madhukar
 * 
 */
@Service
public class UserServiceImpl implements UserService {

	private static final String PROPERTY_ID = "id";

	// private static final String PROPERTY_EMAIL = "email";

	private static final String PROPERTY_ENCRYPTEDPASSWORD = "encryptedpassword";

	// private static final String PROPERTY_UNLOCK_TOKEN = "unlocktoken";

	// private static final String PROPERTY_IS_VERIFIED = "isverified";

	private static final String PROPERTY_RESET_PASSWORD_TOKEN = "resetpasswordtoken";

	private static final String PROPERTY_LOGIN_ID = "loginid";

	private static final String PROPERTY_IS_VALID = "isValid";

	// private static final String PROPERTY_IS_LOCKED = "isLocked";

	private GenericDAO<User> dao;

	@Autowired
	private SQLDAO sqlDao;

	@Autowired
	public void setDao(GenericDAO<User> daoToSet) {
		dao = daoToSet;
		dao.setClazz(User.class);
	}

	@Autowired
	private Email emailDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.UserService#getUserList()
	 */
	@Override
	@Transactional
	public List<User> getUserList() {
		return dao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#getUserListByProperties
	 * (java.util.Map)
	 */
	@Override
	@Transactional
	public List<User> getUserListByProperties(Map<String, Object> properties) {
		return dao.findAllByProperties(properties);
	}

	/**
	 * Creates an empty criteria for that Entity.
	 * 
	 * @return The criteria.
	 */
	@Transactional
	private Criteria findByCriteria(String alias) {
		return dao.findByCriteria(alias);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.UserService#deleteUser(long)
	 */
	@Override
	@Transactional
	public boolean deleteUser(long userId) {

		dao.delete(findUserById(userId));
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#findUserById(long)
	 */
	@Override
	@Transactional
	public User findUserById(long userId) {
		return dao.findByID(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#createUser(com.panasonic
	 * .b2bacns.bizportal.persistence.User)
	 */
	@Override
	@Transactional
	public long createUser(User user) {
		return dao.create(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#updateUser(com.panasonic
	 * .b2bacns.bizportal.persistence.User)
	 */
	@Override
	@Transactional
	public boolean updateUser(User user) throws HibernateException {

		dao.update(user);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#getFailedAttempt(
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public int getFailedAttempt(String userLoginID) {

		int failedAttempt = 0;
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_LOGIN_ID, userLoginID);
		List<User> users = getUserListByProperties(properties);
		if (users.size() > 0)
			failedAttempt = users.get(0).getFailedattempt();
		return failedAttempt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#setFailedAttempt(
	 * java.lang.String, int)
	 */
	@Override
	@Transactional
	public void setFailedAttempt(String userLoginID, int failedAttempt) {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_LOGIN_ID, userLoginID);
		List<User> users = getUserListByProperties(properties);
		if (users.size() > 0) {
			User user = users.get(0);
			user.setFailedattempt(failedAttempt);
			dao.update(user);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#authenticateUser(
	 * com.panasonic.b2bacns.bizportal.login.vo.LoginVO)
	 */
	@Transactional
	@Override
	public User authenticateUser(LoginVO loginDTO)
			throws NoSuchAlgorithmException {

		User user = null;

		Criteria criteria = findByCriteria("user");

		criteria.add(Restrictions.and(
				Property.forName(PROPERTY_LOGIN_ID).eq(loginDTO.getLoginId()),
				// Property.forName(PROPERTY_ENCRYPTEDPASSWORD).eq(
				// PasswordEncryptionDecryption
				// .getEncryptedPassword(loginDTO.getPassword())),
				Property.forName(PROPERTY_IS_VALID).eq(true)));

		List<?> list = criteria.list();

		if (list != null && list.size() == 1) {
			if (list.get(0) instanceof User) {
				user = (User) list.get(0);
			}
		}
		return user;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#getUserByTokenID(
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public List<User> getUserByTokenID(String tokenID) {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_RESET_PASSWORD_TOKEN, tokenID);
		List<User> users = getUserListByProperties(properties);
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#isUserExist(java.
	 * lang.String )
	 */
	@Override
	@Transactional
	public User getUser(String loginID) {
		User user = null;
		HashMap<String, Object> criteria = new HashMap<String, Object>();
		criteria.put(PROPERTY_LOGIN_ID, loginID);
		List<User> userList = getUserListByProperties(criteria);
		if (userList != null && userList.size() == 1) {
			user = userList.get(0);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#lockUserAccount(java
	 * .lang.String)
	 */
	@Override
	@Transactional
	public void lockUserAccount(String userLoginID) {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_LOGIN_ID, userLoginID);
		List<User> userList = getUserListByProperties(properties);
		if (userList.size() == 1) {
			User user = userList.get(0);
			user.setLocked(true);
			user.setLockedDate(new Date());
			dao.update(user);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#saveLastVisitedGroups
	 * (java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	public void saveLastVisitedGroups(Long userId, String lastVisitedGroups) {
		Map<String, Object> propertiesMap = new HashMap<String, Object>();

		propertiesMap.put(PROPERTY_ID, userId);
		List<User> userList = getUserListByProperties(propertiesMap);

		if (userList != null && userList.size() > 0) {
			User user = userList.get(0);
			user.setLastVisitedGroups(lastVisitedGroups);
			dao.update(user);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.service.UserService#getLastVisitedGroups
	 * (java.lang.Long)
	 */
	@Override
	public String getLastVisitedGroups(Long userId) {

		String lastVisitedGroups = BizConstants.EMPTY_STRING;

		Map<String, Object> propertiesMap = new HashMap<String, Object>();

		propertiesMap.put(PROPERTY_ID, userId);
		List<User> userList = getUserListByProperties(propertiesMap);

		if (userList != null && userList.size() > 0) {
			User user = userList.get(0);
			lastVisitedGroups = user.getLastVisitedGroups();
		}

		return lastVisitedGroups;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.UserService#
	 * resetFailedAttemptAndLoginDate(java.lang.String, java.util.Date)
	 */
	@Override
	@Transactional
	public void resetFailedAttemptAndLoginDate(String userLoginID,
			Date lastLoginDate) {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_LOGIN_ID, userLoginID);
		List<User> users = getUserListByProperties(properties);
		if (users.size() > 0) {
			User user = users.get(0);
			user.setFailedattempt(0);
			user.setLastLoginDate(lastLoginDate);
			dao.update(user);
		}

	}

	@Override
	@Transactional
	public void getFunctionalGroupAndRoleType(LoginVO loginVO,
			SessionInfo sessionInfo) {

		// String query =
		// "select fn.functional_groupid as groupid, fn.functional_groupname as groupname, "
		// +
		// "fn.roletypeid as roletypeid, rt.roletypename as roletypename from functionalgroup fn "
		// +
		// "inner join roletype rt on fn.roletypeid=rt.id where roletypeid = :id";

		String query = "select fn.functional_groupid as groupid, fn.functional_groupname as groupname, "
				+ "fn.roletypeid as roletypeid, roletypename as roletypename from functionalgroup fn "
				+ "inner join roletype on fn.roletypeid=roletype.id where roletypeid= %d";

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", loginVO.getRole().getRoletype_id());

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put("groupid", StandardBasicTypes.INTEGER);
		scalarMapping.put("groupname", StandardBasicTypes.STRING);
		scalarMapping.put("roletypeid", StandardBasicTypes.INTEGER);
		scalarMapping.put("roletypename", StandardBasicTypes.STRING);

		Map<Integer, String> roleTypeMap = new TreeMap<Integer, String>();

		Map<Integer, String> functionalGroupMap = new TreeMap<Integer, String>();

		List<?> result = sqlDao.executeSQLSelect(
				String.format(query, loginVO.getRole().getRoletype_id()),
				scalarMapping);

		Object[] columns = null;

		if (result != null && result.size() > 0) {

			for (Object row : result) {
				columns = (Object[]) row;

				functionalGroupMap.put((Integer) columns[0],
						(String) columns[1]);
				roleTypeMap.put((Integer) columns[2], (String) columns[3]);

			}

		}

		sessionInfo.setFunctionalGroupMap(functionalGroupMap);
		sessionInfo.setRoleTypeMap(roleTypeMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.service.UserService#
	 * updateUserFirstLoginDetails
	 * (com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean)
	 */
	@Override
	@Transactional
	public boolean updateUserFirstLoginDetails(
			FirstLoginFormBean firstLoginFormBean, Locale locale, String path)
			throws NoSuchAlgorithmException, IOException,
			BusinessFailureException {

		Map<String, Object> propertiesMap = new HashMap<String, Object>();

		propertiesMap.put(PROPERTY_LOGIN_ID,
				firstLoginFormBean.getCurrentLoginId());
		propertiesMap.put(PROPERTY_ENCRYPTEDPASSWORD,
				PasswordEncryptionDecryption
						.getEncryptedPassword(firstLoginFormBean
								.getCurrentPassword()));
		List<User> userList = getUserListByProperties(propertiesMap);

		if (userList != null && userList.size() > 0) {

			propertiesMap.clear();

			propertiesMap.put(PROPERTY_LOGIN_ID,
					firstLoginFormBean.getNewLoginId());

			List<User> alreadyExistUserList = getUserListByProperties(propertiesMap);

			if (alreadyExistUserList != null && !alreadyExistUserList.isEmpty()) {

				throw new BusinessFailureException(
						BizConstants.USERID_ALREADY_EXIST);
			}

			User user = userList.get(0);
			user.setLoginid(firstLoginFormBean.getNewLoginId());
			user.setEncryptedpassword(PasswordEncryptionDecryption
					.getEncryptedPassword(firstLoginFormBean.getNewPassword()));
			user.setPasswordchangeDate(CommonUtil.setUserTimeZone(
					Calendar.getInstance(),
					Calendar.getInstance().getTimeZone().getID()).getTime());
			//Commited by seshu as no longer required in form display
			/*user.setTelephone(firstLoginFormBean.getTelephone());
			user.setEmail(firstLoginFormBean.getEmail());*/
			user.setLastLoginDate(CommonUtil.setUserTimeZone(
					Calendar.getInstance(),
					Calendar.getInstance().getTimeZone().getID()).getTime());
			// UUID token = UUID.randomUUID();
			// user.setConfirmationtoken(token.toString());

			dao.update(user);
			// emailDao.mailSender(user.getEmail(), token.toString(), locale,
			// path);

			return true;
		} else {
			return false;
		}
	}

	/*
	 * Service to change password
	 * 
	 * @param : changePasswordFormBean
	 */
	@Override
	@Transactional
	public boolean changePassword(ChangePasswordFormBean changePasswordFormBean)
			throws NoSuchAlgorithmException, IOException {
		Map<String, Object> propertiesMap = new HashMap<String, Object>();

		propertiesMap.put(PROPERTY_LOGIN_ID,
				changePasswordFormBean.getLoginId());
		propertiesMap.put(PROPERTY_ENCRYPTEDPASSWORD,
				PasswordEncryptionDecryption
						.getEncryptedPassword(changePasswordFormBean
								.getCurrentPassword()));
		List<User> userList = dao.findAllByProperties(propertiesMap);

		if (userList != null && userList.size() > 0) {
			User user = userList.get(0);
			user.setEncryptedpassword(PasswordEncryptionDecryption
					.getEncryptedPassword(changePasswordFormBean
							.getNewPassword()));
			user.setPasswordchangeDate(CommonUtil.setUserTimeZone(
					Calendar.getInstance(),
					Calendar.getInstance().getTimeZone().getID()).getTime());

			dao.update(user);

			return true;
		} else {
			return false;
		}
	}
   //Added by seshu.
	@Override
	public User authenticateUser(ChangePasswordFormBean changePasswordFormBean) {
		User user = null;

		Criteria criteria = findByCriteria("user");

		criteria.add(Restrictions.and(
				Property.forName(PROPERTY_LOGIN_ID).eq(changePasswordFormBean.getLoginId()),				
				Property.forName(PROPERTY_IS_VALID).eq(true)));

		List<?> list = criteria.list();

		if (list != null && list.size() == 1) {
			if (list.get(0) instanceof User) {
				user = (User) list.get(0);
			}
		}
		return user;
	}

	//Added by seshu.
	@Override
	public void resetLockedAccountAndLoginDate(String userLoginID, Date lastLoginDate) {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put(PROPERTY_LOGIN_ID, userLoginID);
		List<User> users = getUserListByProperties(properties);
		if (users.size() > 0) {
			User user = users.get(0);
			user.setFailedattempt(0);
			user.setLocked(false);
			user.setLockedDate(null);
			user.setLastLoginDate(CommonUtil.setUserTimeZone(
					Calendar.getInstance(),
					Calendar.getInstance().getTimeZone().getID()).getTime());
			dao.update(user);
		}
		
	}
}
