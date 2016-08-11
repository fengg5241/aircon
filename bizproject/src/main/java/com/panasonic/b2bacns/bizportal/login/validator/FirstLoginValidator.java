/**
 * 
 */
package com.panasonic.b2bacns.bizportal.login.validator;

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
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.login.form.FirstLoginFormBean;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.PasswordEncryptionDecryption;

/**
 * @author akansha
 *
 */
@Component
public class FirstLoginValidator implements Validator {

	private static final Logger logger = Logger
			.getLogger(FirstLoginValidator.class);

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource messageSource;

	private static final String regexPassword = "((?=.*\\d)(?=.*[a-z]).{8,20})";

	private static final String EMAIL_ID_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	// Added by jia wei
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
	// end of adding by jia wei
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return FirstLoginFormBean.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		FirstLoginFormBean firstLoginFormBean = (FirstLoginFormBean) target;

		logger.info("login form validation takes place.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentLoginId",
				"required.currentLoginId");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newLoginId",
				"required.newLoginId");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword",
				"required.currentPassword");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword",
				"required.newPassword");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword");

		// Commited by Seshu as they are no longer needed in form display
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
				"required.email");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone",
				"required.telephone");*/

		if (StringUtils.equals(firstLoginFormBean.getCurrentPassword(),
				firstLoginFormBean.getNewPassword())) {

			// Modified by jia wei
			errors.rejectValue("newPassword", "newpasswordSameWithCurrentpassword","newpassword.same.with.currentpassword");
			
		}
		// Commited by Seshu as they are no longer needed in form display
		/*if (!Pattern.compile(EMAIL_ID_PATTERN).matcher(firstLoginFormBean.getEmail())
				.matches()) {

			errors.rejectValue("email", messageSource.getMessage(
					"email.invalid.addr", null, firstLoginFormBean.getLocale()));
		}*/
		validatePassword(firstLoginFormBean.getNewPassword(),
				firstLoginFormBean.getConfirmPassword(), errors,
				firstLoginFormBean.getLocale(), firstLoginFormBean.getNewLoginId());

		// Added By Jia Wei& modified by seshu
		if(firstLoginFormBean.getNewLoginId().length() < 4 || firstLoginFormBean.getNewLoginId().length() > 20){
			
			errors.rejectValue("newLoginId", "newuseridMinimumLength","newuserid.minimum.length");
			
		}
		//Modified by seshu.
		try {
			Map<String, Object> propertiesMap = new HashMap<String, Object>();
			Map<String, Object> propertiesList = new HashMap<String, Object>();
			
			propertiesMap.put(PROPERTY_LOGIN_ID,
					firstLoginFormBean.getCurrentLoginId());
			propertiesMap.put(PROPERTY_ENCRYPTEDPASSWORD,
					PasswordEncryptionDecryption
							.getEncryptedPassword(firstLoginFormBean
									.getCurrentPassword()));
			
			propertiesList.put(PROPERTY_LOGIN_ID,
					firstLoginFormBean.getNewLoginId());
			List<User> userList = dao.findAllByProperties(propertiesMap);

			List<User> alreadyExistUserList = getUserListByProperties(propertiesList);
								
			if (userList != null && userList.size() > 0) {
				
				if (alreadyExistUserList != null && !alreadyExistUserList.isEmpty()) {

					errors.rejectValue("newLoginId", "currentloginidNotMatch","newLoginId.not.match");
				}

			} else {
				errors.rejectValue("currentPassword", "currentpasswordNotMatch","currentpassword.not.match");
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// End of adding by jia wei
		
	}

	private List<User> getUserListByProperties(Map<String, Object> propertiesList) {
		return dao.findAllByProperties(propertiesList);
	}
	/**
	 * @param newPassword
	 * @param confirmPassword
	 */
	private void validatePassword(String newPassword, String confirmPassword,
			Errors errors, Locale locale, String loginId) {
        // start of adding by jia wei, support showing multiple errors, instead of only first one (ifelse)
		if (newPassword.length() < 8 || newPassword.length() > 20) {

			// Modifed and added By Jia Wei
			//errors.rejectValue("newPassword", messageSource.getMessage("newpassword.length.mismatch", null, locale));
			errors.rejectValue("newPassword", "newpasswordLengthMismatch", "newpassword.length.mismatch");
		}

		if (!Pattern.compile(regexPassword).matcher(newPassword).matches()) {
			// Modifed and added By Jia wei 
			/*
			errors.rejectValue("newPassword", "newpassword.should.contain.alphabet.and.digit", messageSource.getMessage(
					"newpassword.should.contain.alphabet.and.digit", null,
					locale));
			*/		
			errors.rejectValue("newPassword", "newpasswordShouldContainAlphabetAndDigit",
					"newpassword.should.contain.alphabet.and.digit");
			
		}

		if (!StringUtils.equals(newPassword, confirmPassword)) {

			// Modifed By Jia Wei
			errors.rejectValue("newPassword", "newpasswordMatchConfirmpassword",
					"newpassword.match.confirmpassword");
		}

		if (newPassword.toLowerCase().contains(loginId.toLowerCase())) {
			// Modified By Jia Wei / From Ravi
			errors.rejectValue("newPassword", "newpasswordContainsLoginid",
					"newpassword.contains.loginid");
		}
        // end of adding by jia wei
	}

}
