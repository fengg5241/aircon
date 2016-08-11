package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cutoff_request database table.
 * 
 */
@Entity
@Table(name = "cutoff_request")
@NamedQuery(name = "CutoffRequest.findAll", query = "SELECT c FROM CutoffRequest c")
public class CutoffRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long createdby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fromdate;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date todate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Column(name = "platformtransaction_id")
	private Long platformtransactionId;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date completionDate;

	@Column(name = "distratio_report_filepath")
	private String distratioReportFilepath;

	@Column(name = "distdetail_report_filepath")
	private String distdetailReportFilepath;

	// bi-directional many-to-one association to CutoffRequestTransaction
	@OneToMany(mappedBy = "cutoffRequest")
	private List<CutoffRequestTransaction> cutoffRequestTransactions;

	// bi-directional many-to-one association to PowerdistributionDetailReport
	@OneToMany(mappedBy = "cutoffRequest")
	private List<DistributionDetailData> powerdistributionDetailReports;

	// bi-directional many-to-one association to PowerdistributionRatioReport
	@OneToMany(mappedBy = "cutoffRequest")
	private List<DistributionRatioData> powerdistributionRatioReports;

	public CutoffRequest() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(Long createdby) {
		this.createdby = createdby;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getFromdate() {
		return this.fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTodate() {
		return this.todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public List<CutoffRequestTransaction> getCutoffRequestTransactions() {
		return this.cutoffRequestTransactions;
	}

	public void setCutoffRequestTransactions(
			List<CutoffRequestTransaction> cutoffRequestTransactions) {
		this.cutoffRequestTransactions = cutoffRequestTransactions;
	}

	public CutoffRequestTransaction addCutoffRequestTransaction(
			CutoffRequestTransaction cutoffRequestTransaction) {
		getCutoffRequestTransactions().add(cutoffRequestTransaction);
		cutoffRequestTransaction.setCutoffRequest(this);

		return cutoffRequestTransaction;
	}

	public CutoffRequestTransaction removeCutoffRequestTransaction(
			CutoffRequestTransaction cutoffRequestTransaction) {
		getCutoffRequestTransactions().remove(cutoffRequestTransaction);
		cutoffRequestTransaction.setCutoffRequest(null);

		return cutoffRequestTransaction;
	}

	public Long getPlatformtransactionId() {
		return this.platformtransactionId;
	}

	public void setPlatformtransactionId(Long platformtransactionId) {
		this.platformtransactionId = platformtransactionId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the completionDate
	 */
	public Date getCompletionDate() {
		return completionDate;
	}

	/**
	 * @param completionDate
	 *            the completionDate to set
	 */
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public List<DistributionDetailData> getPowerdistributionDetailReports() {
		return this.powerdistributionDetailReports;
	}

	public void setPowerdistributionDetailReports(
			List<DistributionDetailData> powerdistributionDetailReports) {
		this.powerdistributionDetailReports = powerdistributionDetailReports;
	}

	public DistributionDetailData addPowerdistributionDetailReport(
			DistributionDetailData powerdistributionDetailReport) {
		getPowerdistributionDetailReports().add(powerdistributionDetailReport);
		powerdistributionDetailReport.setCutoffRequest(this);

		return powerdistributionDetailReport;
	}

	public DistributionDetailData removePowerdistributionDetailReport(
			DistributionDetailData powerdistributionDetailReport) {
		getPowerdistributionDetailReports().remove(
				powerdistributionDetailReport);
		powerdistributionDetailReport.setCutoffRequest(null);

		return powerdistributionDetailReport;
	}

	public List<DistributionRatioData> getPowerdistributionRatioReports() {
		return this.powerdistributionRatioReports;
	}

	public void setPowerdistributionRatioReports(
			List<DistributionRatioData> powerdistributionRatioReports) {
		this.powerdistributionRatioReports = powerdistributionRatioReports;
	}

	public DistributionRatioData addPowerdistributionRatioReport(
			DistributionRatioData powerdistributionRatioReport) {
		getPowerdistributionRatioReports().add(powerdistributionRatioReport);
		powerdistributionRatioReport.setCutoffRequest(this);

		return powerdistributionRatioReport;
	}

	public DistributionRatioData removePowerdistributionRatioReport(
			DistributionRatioData powerdistributionRatioReport) {
		getPowerdistributionRatioReports().remove(powerdistributionRatioReport);
		powerdistributionRatioReport.setCutoffRequest(null);

		return powerdistributionRatioReport;
	}

	/**
	 * @return the distratioReportFilepath
	 */
	public String getDistratioReportFilepath() {
		return distratioReportFilepath;
	}

	/**
	 * @param distratioReportFilepath
	 *            the distratioReportFilepath to set
	 */
	public void setDistratioReportFilepath(String distratioReportFilepath) {
		this.distratioReportFilepath = distratioReportFilepath;
	}

	/**
	 * @return the distdetailReportFilepath
	 */
	public String getDistdetailReportFilepath() {
		return distdetailReportFilepath;
	}

	/**
	 * @param distdetailReportFilepath
	 *            the distdetailReportFilepath to set
	 */
	public void setDistdetailReportFilepath(String distdetailReportFilepath) {
		this.distdetailReportFilepath = distdetailReportFilepath;
	}

}
