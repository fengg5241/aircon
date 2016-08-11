package com.panasonic.b2bacns.bizportal.usermanagement.changePassword;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.util.PasswordEncryptionDecryption;

@Component
public class ChangePasswordValidator implements Validator {

	private static final Logger logger = Logger
			.getLogger(ChangePasswordFormBean.class);

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource messageSource;

	private static final String regexPassword = "((?=.*\\d)(?=.*[a-z]).{8,20})";

	// Added By Jia Wei
	private static final String PROPERTY_LOGIN_ID = "loginid";

	private static final String PROPERTY_ENCRYPTEDPASSWORD = "encryptedpassword";

	private GenericDAO<User> dao;

	@Autowired
	private SQLDAO sqlDao;

	@Autowired
	public void setDao(GenericDAO<User> daoToSet) {
		dao = daoToSet;
		dao.setClazz(User.class);
	}

	// End of adding by jia wei

	@Override
	public boolean supports(Class<?> clazz) {

		return ChangePasswordFormBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ChangePasswordFormBean changePasswordFormBean = (ChangePasswordFormBean) target;

		logger.info("Change password validation takes place.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId",
				"required.loginId");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword",
				"required.currentPassword");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword",
				"required.newPassword");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword");

		if (StringUtils.equals(changePasswordFormBean.getCurrentPassword(),
				changePasswordFormBean.getNewPassword())) {

			// Modified By Jia Wei
			// errors.rejectValue("newPassword",
			// "currentpassword.not.match.newpassword","currentpassword.not.match.newpassword");
			errors.rejectValue("newPassword",
					"newpasswordSameWithCurrentpassword",
					"newpassword.same.with.currentpassword");
		}

		validatePassword(changePasswordFormBean.getNewPassword(),
				changePasswordFormBean.getConfirmPassword(), errors,
				changePasswordFormBean.getLocale(),
				changePasswordFormBean.getLoginId());

		// Added By Jia Wei
		try {
			Map<String, Object> propertiesMap = new HashMap<String, Object>();

			propertiesMap.put(PROPERTY_LOGIN_ID,
					changePasswordFormBean.getLoginId());
			propertiesMap.put(PROPERTY_ENCRYPTEDPASSWORD,
					PasswordEncryptionDecryption
							.getEncryptedPassword(changePasswordFormBean
									.getCurrentPassword()));
			List<User> userList = dao.findAllByProperties(propertiesMap);

			if (userList != null && userList.size() > 0) {

			} else {
				errors.rejectValue("currentPassword",
						"currentpasswordNotMatch", "currentpassword.not.match");

			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// End of adding by Jia Wei

	}

	/**
	 * @param newPassword
	 * @param confirmPassword
	 * @param loginId
	 */
	private void validatePassword(String newPassword, String confirmPassword,
			Errors errors, Locale locale, String loginId) {
        // start of adding by jia wei, support showing multiple errors, instead of only first one (ifelse)
		if (newPassword.length() < 8 || newPassword.length() > 20) {
			// Modified by Jia Wei
			errors.rejectValue("newPassword", "newpasswordLengthMismatch",
					"newpassword.length.mismatch");
		}

		if (!Pattern.compile(regexPassword).matcher(newPassword).matches()) {
			// Modified By Jia Wei
			errors.rejectValue("newPassword",
					"newpasswordShouldContainAlphabetAndDigit",
					"newpassword.should.contain.alphabet.and.digit");
		}

		if (!StringUtils.equals(newPassword, confirmPassword)) {
			// Modified By Jia Wei
			errors.rejectValue("newPassword",
					"newpasswordMatchConfirmpassword",
					"newpassword.match.confirmpassword");
		}

		if (newPassword.toLowerCase().contains(loginId.toLowerCase())) {
			// Modified By Ravi
			errors.rejectValue("newPassword", "newpasswordContainsLoginid",
					"newpassword.contains.loginid");
		} 
        //end of adding by jiawei
	}

}
