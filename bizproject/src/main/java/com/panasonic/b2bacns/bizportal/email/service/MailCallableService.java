package com.panasonic.b2bacns.bizportal.email.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.panasonic.b2bacns.bizportal.email.util.Constants;
import com.panasonic.b2bacns.bizportal.email.util.EmailUtil;
import com.panasonic.b2bacns.bizportal.email.util.Utility;

public class MailCallableService implements Callable<Boolean> {

	/** Logger instance. **/
	private static Logger logger = Logger.getLogger(MailCallableService.class);

	private Map<String, String> emailMap = null;

	public MailCallableService(Map<String, String> emailMap) {
		this.emailMap = emailMap;
	}

	@Override
	public Boolean call() throws Exception {
		Boolean mailSentStatus = Boolean.FALSE;

		mailSentStatus = processEmail(emailMap);

		return mailSentStatus;
	}

	/**
	 * @return
	 */
	public boolean processEmail(Map<String, String> emailMap) {
		Boolean isMailSend = Boolean.FALSE;
		try {

			Utility.initiateCryptography();

			if (emailMap != null && !emailMap.isEmpty()) {

				logger.debug("Total Maintenance Notification Mail Retrieved : " + emailMap.size());

				EmailUtil.connectTransport(Utility.getProperties(Constants.PROP_MAIL_ACCOUNT_ID),
						Utility.getProperties(Constants.PROP_MAIL_ACCOUNT_PASSWORD));
				logger.warn("Confirmation Token Mail Connected with Transport.");
			} else {
				logger.warn("No Confirmation Token to publish ");
			}

			if (emailMap != null && emailMap.size() > 0) {

				if (logger.isDebugEnabled()) {

					logger.info("*********** Mail send BATCH START AT - " + new Date() + " ***********");
				}

				try {

					String emailIds = emailMap.get(Constants.EMAIL_IDS);
					String title = emailMap.get(Constants.TITLE);
					isMailSend = EmailUtil.sendMail(Utility.getProperties("mail.smtp.sender.address"),
							Utility.getProperties(Constants.PROP_MAIL_ACCOUNT_PASSWORD), emailIds,
							Constants.MAINT_MAIL_TEMPLATE, title, emailMap, null);

					if (logger.isDebugEnabled()) {
						if (isMailSend) {
							logger.debug("Mail sent successfully to address : " + emailMap.get(Constants.EMAIL_IDS)
									+ " Notification for - " + emailMap.get("title"));
						} else {
							logger.debug("Failed to send mail to address : " + emailMap.get(Constants.EMAIL_IDS)
									+ " Notification for - " + emailMap.get("title"));
						}

					}

				} catch (Exception exp) {

					logger.error("Sending mail to - " + emailMap.get(Constants.EMAIL_IDS) + " Failed.", exp);

				}
				if (logger.isDebugEnabled()) {

					logger.info("Notification  " + emailMap.get("title") + " COMPLETED SUCCESSFULLY.");

				}

				if (logger.isDebugEnabled()) {

					logger.info("*********** Mail send batch END at - " + new Date() + " ***********");

				}

			}

		} catch (SQLException sqlEXP) {

			logger.error("Sending mail to : Failed, Reason found as -", sqlEXP);

		} catch (Exception exp) {

			logger.error("Sending mail to : Failed, Reason found as -", exp);

		} finally {
			// EmailUtil.closeTrasport();
		}

		return isMailSend;
	}
}
