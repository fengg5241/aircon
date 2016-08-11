package com.panasonic.b2bacns.bizportal.login.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.panasonic.b2bacns.bizportal.login.form.LoginFormBean;

/**
 * This class is used to validate the login/logout form activity
 * 
 * @author kumar.madhukar
 *
 */
@Component
public class LoginValidator implements Validator {

	private static final Logger logger = Logger.getLogger(LoginValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {

		return LoginFormBean.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		logger.debug("login form validation takes place.");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
		// "required.email");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId",
				"required.loginId");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password");
	}

	//Added by seshu.
	public void validates(Object target, Errors errors) {

		logger.debug("login form validation takes place.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId",
				"required.loginId");
		
	}
}
