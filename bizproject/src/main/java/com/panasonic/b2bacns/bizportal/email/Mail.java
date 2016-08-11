package com.panasonic.b2bacns.bizportal.email;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * This class is used to send email in asynchronous way after setting messages
 * and from and to recipients.
 */
@Component
public class Mail {

	private static final Logger logger = Logger.getLogger(Mail.class);
	private Map<String, Transport> allTransports = new ConcurrentHashMap<String, Transport>();
	private Map<String, Session> allMailSessions = new ConcurrentHashMap<String, Session>();

	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource properties;

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
	@Async
	public void sendMail(String fromAddress, String toAddress,
			String messagetype, String subject,
			Map<String, String> propertymap, File attachedFilename,
			Boolean formatBody, Locale locale) {

		EmailMessage oEmailMessage = new EmailMessage();
		oEmailMessage.setFromAddress(fromAddress);
		oEmailMessage.setToadd(toAddress);
		oEmailMessage.setAttachedFilename(attachedFilename);
		oEmailMessage.setSubject(subject);
		oEmailMessage.setMessagetype(messagetype);
		oEmailMessage.setPropertyfileplaceholders(propertymap);
		sendAsync(oEmailMessage, formatBody, locale);

	}

	// @Async
	@Async
	public boolean sendMail(String fromAddress, String password,
			String toAddress, String messagetype, String subject,
			Map<String, String> propertymap, File attachedFilename,
			boolean formatBody, Locale locale) {

		EmailMessage oEmailMessage = new EmailMessage();
		oEmailMessage.setFromAddress(fromAddress);
		oEmailMessage.setPassword(password);
		oEmailMessage.setToadd(toAddress);
		oEmailMessage.setAttachedFilename(attachedFilename);
		oEmailMessage.setSubject(subject);
		oEmailMessage.setMessagetype(messagetype);
		oEmailMessage.setPropertyfileplaceholders(propertymap);
		return sendAsync(oEmailMessage, formatBody, locale);

	}

	@Async
	public boolean sendMail(String fromAddress, String password,
			String[] toAddress, String messagetype, String subject,
			Map<String, String> propertymap, File attachedFilename,
			boolean formatBody, Locale locale) {

		EmailMessage oEmailMessage = new EmailMessage();
		oEmailMessage.setFromAddress(fromAddress);
		oEmailMessage.setPassword(password);
		oEmailMessage.setToAddress(toAddress);
		oEmailMessage.setAttachedFilename(attachedFilename);
		oEmailMessage.setSubject(subject);
		oEmailMessage.setMessagetype(messagetype);
		oEmailMessage.setPropertyfileplaceholders(propertymap);
		return sendAsync(oEmailMessage, formatBody, locale);

	}

	private Transport getSMTPTransport(final Properties mailPropertis)
			throws NoSuchProviderException {

		Transport transport = null;

		if (mailPropertis != null
				&& (null != allTransports && allTransports
						.containsKey(mailPropertis
								.getProperty(BizConstants.PROP_SMTP_USER)))) {

			transport = allTransports.get(mailPropertis
					.getProperty(BizConstants.PROP_SMTP_USER));

		} else {
			transport = getSMTPSession(mailPropertis).getTransport(
					BizConstants.MAIL_PROPTOCOL);

			allTransports.put(
					mailPropertis.getProperty(BizConstants.PROP_SMTP_USER),
					transport);
		}

		return transport;
	}

	private Session getSMTPSession(final Properties mailPropertis) {

		Session mailSession = null;

		if (mailPropertis != null) {

			if (allMailSessions.containsKey(mailPropertis
					.getProperty(BizConstants.PROP_SMTP_USER))) {

				mailSession = allMailSessions.get(mailPropertis
						.getProperty(BizConstants.PROP_SMTP_USER));
			} else {

				mailSession = javax.mail.Session.getDefaultInstance(
						mailPropertis, new Authenticator() {
							@Override
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(
										mailPropertis
												.getProperty(BizConstants.PROP_SMTP_USER),
										BizConstants.PROP_SMTP_PASSWORD);
							}
						});

				allMailSessions.put(
						mailPropertis.getProperty(BizConstants.PROP_SMTP_USER),
						mailSession);
			}

		}

		return mailSession;
	}

	private String getMailBody(String mailContent,
			Map<String, String> propertyplaceholders) {

		String mailBody = mailContent;

		if (propertyplaceholders != null) {

			Set<Map.Entry<String, String>> setOfEntries = propertyplaceholders
					.entrySet();

			for (Map.Entry<String, String> entry : setOfEntries) {

				try {
					mailBody = mailBody.replaceAll("\\Q{" + entry.getKey()
							+ "}\\E",
							Matcher.quoteReplacement(entry.getValue()));
				} catch (Exception ex) {

					logger.error("Error occured while sending mail", ex);

				}
			}

		}

		return mailBody;
	}

	private String getMailBodyText(String mailContent,
			Map<String, String> propertyplaceholders) {

		String mailBody = "<html><body>Hello "
				+ propertyplaceholders.get("emailId")
				+ "! <br/><br/> Your account has been locked due to an excessive number of unsuccessful sign in attempts"
				+ " <br/><br/> Click the link below to unlock your account: <br/><br/>"
				+ "  <a href='" + propertyplaceholders.get("urlToken")
				+ "  '>  Unlock my account </a> <br/><br/>"
				+ "  <br/><br/>Regards<br/>B2BACNS Team</body></html>";
		return mailBody;
	}

	/**
	 * Set all the mail values with host and port of the server and sent it.
	 * 
	 * @param oEmailMessage
	 * @return
	 */
	public boolean sendAsync(EmailMessage oEmailMessage, boolean formatBody,
			Locale locale) {

		Transport transport = null;

		boolean success = false;

		Session mailsession = null;

		String mailBody = null;

		MimeMessage mimemessage = null;

		MimeBodyPart mimeBodyPart = null;

		Properties mailProps = new Properties();

		mailProps.put(BizConstants.PROP_USE_SMTP_AUTH,
				MailPropertiesUtil.get(BizConstants.PROP_USE_SMTP_AUTH));
		mailProps.put(BizConstants.PROP_TLS_ENABLE,
				MailPropertiesUtil.get(BizConstants.PROP_TLS_ENABLE));
		mailProps.put(BizConstants.PROP_SMTP_HOST,
				MailPropertiesUtil.get(BizConstants.PROP_SMTP_HOST));
		mailProps.put(BizConstants.PROP_SMTP_PORT,
				MailPropertiesUtil.get(BizConstants.PROP_SMTP_PORT));
		mailProps.put(BizConstants.PROP_SMTP_USER,
				oEmailMessage.getFromAddress());

		if (oEmailMessage.getPassword() != null) {
			mailProps.put(BizConstants.PROP_SMTP_PASSWORD,
					oEmailMessage.getPassword());
		}

		try {

			mailsession = getSMTPSession(mailProps);

			mimemessage = new MimeMessage(mailsession);

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
			if (formatBody) {

				mailBody = getMailBody(properties.getMessage(
						oEmailMessage.getMessagetype(), null, locale),
						oEmailMessage.getPropertyfileplaceholders());
			} else {
				mailBody = getMailBodyText(
						MailPropertiesUtil.get(oEmailMessage.getMessagetype()),
						oEmailMessage.getPropertyfileplaceholders());
			}

			mimeBodyPart = new MimeBodyPart();

			mimeBodyPart.setText(mailBody);

			mimeBodyPart.setContent(mailBody, BizConstants.CONTENT_TYPE_HTML);

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

			// Send message
			transport = getSMTPTransport(mailProps);

			transport.connect(
					mailProps.getProperty(BizConstants.PROP_SMTP_HOST),
					mailProps.getProperty(BizConstants.PROP_SMTP_USER),
					mailProps.getProperty(BizConstants.PROP_SMTP_PASSWORD));

			logger.debug("Sending mail to "
					+ mimemessage.getAllRecipients().length
					+ " User[s] using Transport : " + transport.toString());

			transport.sendMessage(mimemessage, mimemessage.getAllRecipients());

			success = true;

			logger.debug("Mail send complete using Transport : "
					+ transport.toString());

		} catch (RuntimeException exp) {
			exp.printStackTrace();
			success = false;

			logger.error("Error occured while sending mail for account - "
					+ oEmailMessage.getFromAddress(), exp);
		} catch (Exception exp) {
			exp.printStackTrace();
			success = false;

			logger.error("Error occured while sending mail for account - "
					+ oEmailMessage.getFromAddress(), exp);
		} finally {
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

		return success;
	}

	private static String getCommaSeparatedString(String[] usersList) {

		String comSepUsers = null;

		StringBuffer sb = new StringBuffer();

		if (usersList != null && usersList.length > 0) {

			for (int index = 0; index < usersList.length; index++) {

				if (index != usersList.length - 1) {
					sb.append(usersList[index]).append(",");
				} else {
					sb.append(usersList[index]);
				}
			}

			comSepUsers = sb.toString();
		} else {
			comSepUsers = "";
		}

		return comSepUsers;
	}

}
