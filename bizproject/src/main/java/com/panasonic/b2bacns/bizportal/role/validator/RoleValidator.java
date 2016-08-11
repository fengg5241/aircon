package com.panasonic.b2bacns.bizportal.role.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import com.panasonic.b2bacns.bizportal.role.form.RoleFormBean;

@Component
public class RoleValidator implements Validator{

	
	private static final Logger logger = Logger
			.getLogger(RoleFormBean.class);
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RoleFormBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	  
		RoleFormBean  roleFormBean = (RoleFormBean) target;
		
		logger.info("login form validation takes place.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role_name", "required.role_name");
		
		
	}

}
