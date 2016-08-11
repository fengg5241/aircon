/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * {"resultList": [{ "transactionId":1, "sites":["Block1", "Block2"],
 * "result":"Complete/Incomplete/No Data/In Progress",
 * "transactionTime":"02/12/2015 09:13", "startingDate":"01/01/2015",
 * "completionDate":"01/02/2015" }, {}] }
 * 
 * @author simanchal.patra
 *
 */
@JsonAutoDetect
public class RegisteredCutoffRequestDetails implements Serializable {

	private static final long serialVersionUID = 4494854574428743834L;

	private Long transactionId;

	private List<String> sites; // :["Block1", "Block2"],
	private String result; // ":"Complete/Incomplete/No Data/In Progress",
	private String transactionTime; // :"02/12/2015 09:13",
	private String startingDate; // ":"01/01/2015",
	private String completionDate; // ":"01/02/2015"

	private long appRegistaionID;

	/**
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the sites
	 */
	public List<String> getSites() {
		return sites;
	}

	/**
	 * @param sites
	 *            the sites to set
	 */
	public void setSites(List<String> sites) {
		this.sites = sites;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the transactionTime
	 */
	public String getTransactionTime() {
		return transactionTime;
	}

	/**
	 * @param transactionTime
	 *            the transactionTime to set
	 */
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	/**
	 * @return the startingDate
	 */
	public String getStartingDate() {
		return startingDate;
	}

	/**
	 * @param startingDate
	 *            the startingDate to set
	 */
	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}

	/**
	 * @return the completionDate
	 */
	public String getCompletionDate() {
		return completionDate;
	}

	/**
	 * @param completionDate
	 *            the completionDate to set
	 */
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	/**
	 * @return the appRegistaionID
	 */
	public long getAppRegistaionID() {
		return appRegistaionID;
	}

	/**
	 * @param appRegistaionID
	 *            the appRegistaionID to set
	 */
	public void setAppRegistaionID(long appRegistaionID) {
		this.appRegistaionID = appRegistaionID;
	}

}
