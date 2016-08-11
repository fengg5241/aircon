package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fanspeed_master database table.
 * 
 */
@Entity
@Table(name="fanspeed_master")
@NamedQuery(name="FanspeedMaster.findAll", query="SELECT f FROM FanspeedMaster f")
public class FanspeedMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=255)
	private String fanspeedname;

	//bi-directional many-to-one association to Indoorunitslog
	@OneToMany(mappedBy="fanspeedMaster")
	private List<Indoorunitslog> indoorunitslogs;

	//bi-directional many-to-one association to IndoorunitslogHistory
	@OneToMany(mappedBy="fanspeedMaster")
	private List<IndoorunitslogHistory> indoorunitslogHistories;

	public FanspeedMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFanspeedname() {
		return this.fanspeedname;
	}

	public void setFanspeedname(String fanspeedname) {
		this.fanspeedname = fanspeedname;
	}

	public List<Indoorunitslog> getIndoorunitslogs() {
		return this.indoorunitslogs;
	}

	public void setIndoorunitslogs(List<Indoorunitslog> indoorunitslogs) {
		this.indoorunitslogs = indoorunitslogs;
	}

	public Indoorunitslog addIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().add(indoorunitslog);
		indoorunitslog.setFanspeedMaster(this);

		return indoorunitslog;
	}

	public Indoorunitslog removeIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().remove(indoorunitslog);
		indoorunitslog.setFanspeedMaster(null);

		return indoorunitslog;
	}

	public List<IndoorunitslogHistory> getIndoorunitslogHistories() {
		return this.indoorunitslogHistories;
	}

	public void setIndoorunitslogHistories(List<IndoorunitslogHistory> indoorunitslogHistories) {
		this.indoorunitslogHistories = indoorunitslogHistories;
	}

	public IndoorunitslogHistory addIndoorunitslogHistory(IndoorunitslogHistory indoorunitslogHistory) {
		getIndoorunitslogHistories().add(indoorunitslogHistory);
		indoorunitslogHistory.setFanspeedMaster(this);

		return indoorunitslogHistory;
	}

	public IndoorunitslogHistory removeIndoorunitslogHistory(IndoorunitslogHistory indoorunitslogHistory) {
		getIndoorunitslogHistories().remove(indoorunitslogHistory);
		indoorunitslogHistory.setFanspeedMaster(null);

		return indoorunitslogHistory;
	}

}