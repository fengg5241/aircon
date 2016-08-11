package com.panasonic.b2bacns.bizportal.email.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.email.service.MailCallableService;
import com.panasonic.b2bacns.bizportal.email.util.Constants;

@Component
public class Email {

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource properties;

	/** Logger instance. **/
	private static Logger logger = Logger.getLogger(Email.class);

	public void mailSender(String emailId, String token, Locale locale,
			String url) throws IOException {

		try {

			logger.info("Mail Template preparation start for sending mail : ");

			StringBuilder appendedUrl = new StringBuilder(
					(url == null || url.isEmpty()) ? "../firstLogin.htm" : url);

			appendedUrl.append("?emailtoken=").append(token)
					.append("&emailAddr=").append(emailId);

			if (StringUtils.isNotBlank(emailId)) {
				Map<String, String> letterMap = new LinkedHashMap<String, String>();
				// letterMap.put(Constants.TOKEN, token);
				letterMap.put(Constants.URL, appendedUrl.toString());
				letterMap.putAll(prepareLetterMap(letterMap, emailId));

				MailCallableService mailCallableService = new MailCallableService(
						letterMap);
				try {
					/*
					 * globalTaskPool.getGlobalTaskPool().submit(
					 * mailCallableService);
					 */
					mailCallableService.processEmail(letterMap);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				logger.info("Email id is empty. Email task is completed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Map<String, String> prepareLetterMap(Map<String, String> letterMap,
			String emailIds) throws SQLException {

		letterMap.put(Constants.TITLE, "Email Activation");
		letterMap.put(Constants.EMAIL_IDS, emailIds);
		return letterMap;
	}

}
