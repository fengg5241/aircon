package com.panasonic.b2bacns.bizportal.email;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * 
 * This class is used for setting the JMS Message Object
 */
public class EmailMessage implements Serializable {

	private static final long serialVersionUID = -2435969400434520280L;
	private String fromAddress = null;
	private String password = null;
	private String[] toAddress = null;
	private String[] ccAdddress = null;
	private String[] bccAddress = null;
	private String subject = null;
	private String mailMessage = null;
	private File attachedFilename = null;
	private File attachedPdfFile = null;
	private boolean sendAsMultipart;
	private Map<String, String> propertyfileplaceholders = null;
	private String messagetype = null;
	private String toadd = null;

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String[] getCcAdddress() {
		return ccAdddress;
	}

	public void setCcAdddress(String[] ccAdddress) {
		this.ccAdddress = ccAdddress;
	}

	public String[] getBccAddress() {
		return bccAddress;
	}

	public void setBccAddress(String[] bccAddress) {
		this.bccAddress = bccAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}

	public File getAttachedFilename() {
		return attachedFilename;
	}

	public void setAttachedFilename(File attachedFilename) {
		this.attachedFilename = attachedFilename;
	}

	public File getAttachedPdfFile() {
		return attachedPdfFile;
	}

	public void setAttachedPdfFile(File attachedPdfFile) {
		this.attachedPdfFile = attachedPdfFile;
	}

	public boolean isSendAsMultipart() {
		return sendAsMultipart;
	}

	public void setSendAsMultipart(boolean sendAsMultipart) {
		this.sendAsMultipart = sendAsMultipart;
	}

	public Map<String, String> getPropertyfileplaceholders() {
		return propertyfileplaceholders;
	}

	public void setPropertyfileplaceholders(
			Map<String, String> propertyfileplaceholders) {
		this.propertyfileplaceholders = propertyfileplaceholders;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public String getToadd() {
		return toadd;
	}

	public void setToadd(String toadd) {
		this.toadd = toadd;
	}
}
