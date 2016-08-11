package com.panasonic.b2bacns.bizportal.email.util;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class is used to send email in asynchronous way after setting messages
 * and from and to recipients.
 */

public class EmailUtil {

	private static Logger logger = Logger.getLogger(EmailUtil.class);

	private static final String PROP_TLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String PROP_SMTP_HOST = "mail.smtp.host";
	private static final String PROP_SMTP_USER = "mail.smtp.user";
	private static final String PROP_SMTP_PASSWORD = "mail.smtp.password";
	private static final String PROP_SMTP_PORT = "mail.smtp.port";
	private static final String PROP_USE_SMTP_AUTH = "mail.smtp.auth";
	private static final String CONTENT_TYPE_HTML = "text/html";
	private static final String PROP_TRANS_PROTOCOL = "mail.transport.protocol";
	private static final String SMTP = "smtp";

	private static Transport transport = null;

	private static javax.mail.Session mailsession = null;

	/**
	 * This API is used to send an email in asynchronous way. It constructs the
	 * EmailMessage object and post it to the MagEmailQueue for further
	 * processing.
	 * 
	 * @param fromAddress
	 * @param toAddress
	 * @param ccAddress
	 * @param subject
	 * @param message
	 * @return
	 * @return
	 */

	public static boolean sendMail(String fromAddress, String toAddress,
			String messagetype, String subject,
			Map<String, String> propertymap, File attachedFilename) {

		EmailMessage_new oEmailMessage = new EmailMessage_new();
		oEmailMessage.setFromAddress(fromAddress);
		oEmailMessage.setToadd(toAddress);
		oEmailMessage.setAttachedFilename(attachedFilename);
		oEmailMessage.setSubject(subject);
		oEmailMessage.setMessagetype(messagetype);
		oEmailMessage.setPropertyfileplaceholders(propertymap);
		return sendAsync(oEmailMessage);

	}

	// @Async
	public static boolean sendMail(String fromAddress, String password,
			String toAddress, String messagetype, String subject,
			Map<String, String> propertymap, File attachedFilename) {

		EmailMessage_new oEmailMessage = new EmailMessage_new();
		oEmailMessage.setFromAddress(fromAddress);
		oEmailMessage.setPassword(password);
		oEmailMessage.setToadd(toAddress);
		oEmailMessage.setAttachedFilename(attachedFilename);
		oEmailMessage.setSubject(subject);
		oEmailMessage.setMessagetype(messagetype);
		oEmailMessage.setPropertyfileplaceholders(propertymap);
		return sendAsync(oEmailMessage);

	}

	public static boolean sendMail(String fromAddress, String password,
			String[] toAddress, String messagetype, String subject,
			Map<String, String> propertymap, File attachedFilename) {

		EmailMessage_new oEmailMessage = new EmailMessage_new();
		oEmailMessage.setFromAddress(fromAddress);
		oEmailMessage.setPassword(password);
		oEmailMessage.setToAddress(toAddress);
		oEmailMessage.setAttachedFilename(attachedFilename);
		oEmailMessage.setSubject(subject);
		oEmailMessage.setMessagetype(messagetype);
		oEmailMessage.setPropertyfileplaceholders(propertymap);
		return sendAsync(oEmailMessage);

	}

	public static boolean connectTransport(String emailAccountID,
			String emailAccountPassord) {
		Properties mailProps = System.getProperties();

		logger.info("Creating Transport for mail account " + emailAccountID);

		boolean success = false;

		try {

			mailProps.put(PROP_USE_SMTP_AUTH,
					Utility.getProperties(PROP_USE_SMTP_AUTH));
			mailProps.put(PROP_TLS_ENABLE,
					Utility.getProperties(PROP_TLS_ENABLE));
			mailProps
					.put(PROP_SMTP_HOST, Utility.getProperties(PROP_SMTP_HOST));
			mailProps
					.put(PROP_SMTP_PORT, Utility.getProperties(PROP_SMTP_PORT));
			mailProps.put(PROP_SMTP_USER, emailAccountID);
			mailProps.put(PROP_TRANS_PROTOCOL, SMTP);

			if (emailAccountPassord != null) {
				mailProps.put(PROP_SMTP_PASSWORD, emailAccountPassord);
			}

			javax.mail.Session mailsession = javax.mail.Session
					.getDefaultInstance(mailProps, null);
			transport = mailsession.getTransport(SMTP);

			// transport.connect(emailAccountID,emailAccountPassord);
			transport.connect(mailProps.getProperty(PROP_SMTP_HOST),
					mailProps.getProperty(PROP_SMTP_USER),
					mailProps.getProperty(PROP_SMTP_PASSWORD));

			logger.info("Sending mail using transport " + transport.toString());

		} catch (Exception exp) {
			logger.error("Error occured while connection trasport", exp);
		}

		return success;
	}

	/**
	 * Set all the mail values with host and port of the server and sent it.
	 * 
	 * @param oEmailMessage
	 * @return
	 */
	public static boolean sendAsync(EmailMessage_new oEmailMessage) {
		boolean success = false;

		try {

			String mailBody = Utility.getProperties(
					oEmailMessage.getMessagetype(),
					Constants.PROPERTY_EMAIL_TEMPLT);

			if (oEmailMessage.getPropertyfileplaceholders() != null) {

				Map<String, String> propertyplaceholders = oEmailMessage
						.getPropertyfileplaceholders();

				Set<Map.Entry<String, String>> setOfEntries = propertyplaceholders
						.entrySet();

				for (Map.Entry<String, String> entry : setOfEntries) {
					String value = Constants.EMPTY_STRING;
					if (entry.getValue() != null
							&& StringUtils.isNotBlank(entry.getValue())
							&& !StringUtils.equalsIgnoreCase(entry.getValue(),
									Constants.STRING_NULL)) {
						value = entry.getValue();
					}
					mailBody = mailBody.replaceAll(
							Constants.PATTERN_1 + entry.getKey()
									+ Constants.PATTERN_2, value);
				}

			}

			MimeMessage mimemessage = new MimeMessage(mailsession);

			mimemessage.setFrom(new InternetAddress(oEmailMessage
					.getFromAddress()));

			if (oEmailMessage.getToadd() != null) {

				mimemessage.setRecipients(javax.mail.Message.RecipientType.TO,
						InternetAddress.parse(oEmailMessage.getToadd()));

			} else if (oEmailMessage.getToAddress() != null) {

				mimemessage.setRecipients(javax.mail.Message.RecipientType.TO,
						InternetAddress
								.parse(getCommaSeparatedString(oEmailMessage
										.getToAddress())));
			}

			mimemessage.setSubject(oEmailMessage.getSubject());

			MimeBodyPart mimeBodyPart = new MimeBodyPart();

			mimeBodyPart.setText(mailBody);

			mimeBodyPart.setContent(mailBody, CONTENT_TYPE_HTML);

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(mimeBodyPart);

			if (oEmailMessage.getAttachedFilename() != null) {

				mimeBodyPart = new MimeBodyPart();

				FileDataSource fds = new FileDataSource(oEmailMessage
						.getAttachedFilename().getAbsolutePath());

				mimeBodyPart.setDataHandler(new DataHandler(fds));

				mimeBodyPart.setFileName(fds.getName());

				multipart.addBodyPart(mimeBodyPart);
			}

			mimemessage.setContent(multipart);

			mimemessage.saveChanges();

			transport.sendMessage(mimemessage, mimemessage.getAllRecipients());

			success = true;

			logger.info("Mail send complete using Transport : "
					+ transport.toString());

		} catch (RuntimeException exp) {

			success = false;

			logger.error("Error occured while sending mail from account - "
					+ oEmailMessage.getFromAddress(), exp);
		} catch (Exception exp) {

			success = false;

			logger.error("Error occured while sending mail from account - "
					+ oEmailMessage.getFromAddress(), exp);
		} finally {

		}

		return success;
	}

	public static void closeTrasport() {

		logger.info("closing Transport for " + transport.toString());

		if (transport != null) {
			try {
				transport.close();
			} catch (MessagingException exp) {

				logger.error(
						"Error occured while closing connection to Transport "
								+ transport.toString(), exp);
			}
		}
	}

	private static String getCommaSeparatedString(String[] usersList) {

		String comSepUsers = null;

		StringBuffer sb = new StringBuffer();

		if (usersList != null && usersList.length > 0) {

			for (int index = 0; index < usersList.length; index++) {

				if (index != usersList.length - 1) {
					sb.append(usersList[index]).append(Constants.STRING_COMMA);
				} else {
					sb.append(usersList[index]);
				}
			}

			comSepUsers = sb.toString();
		} else {
			comSepUsers = Constants.EMPTY_STRING;
		}

		return comSepUsers;
	}
}
