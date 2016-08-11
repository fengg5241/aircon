package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the winddirection_master database table.
 * 
 */
@Entity
@Table(name="winddirection_master")
@NamedQuery(name="WinddirectionMaster.findAll", query="SELECT w FROM WinddirectionMaster w")
public class WinddirectionMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=255)
	private String winddirectionname;

	//bi-directional many-to-one association to Indoorunitslog
	@OneToMany(mappedBy="winddirectionMaster")
	private List<Indoorunitslog> indoorunitslogs;

	//bi-directional many-to-one association to IndoorunitslogHistory
	@OneToMany(mappedBy="winddirectionMaster")
	private List<IndoorunitslogHistory> indoorunitslogHistories;

	public WinddirectionMaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWinddirectionname() {
		return this.winddirectionname;
	}

	public void setWinddirectionname(String winddirectionname) {
		this.winddirectionname = winddirectionname;
	}

	public List<Indoorunitslog> getIndoorunitslogs() {
		return this.indoorunitslogs;
	}

	public void setIndoorunitslogs(List<Indoorunitslog> indoorunitslogs) {
		this.indoorunitslogs = indoorunitslogs;
	}

	public Indoorunitslog addIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().add(indoorunitslog);
		indoorunitslog.setWinddirectionMaster(this);

		return indoorunitslog;
	}

	public Indoorunitslog removeIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().remove(indoorunitslog);
		indoorunitslog.setWinddirectionMaster(null);

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
		indoorunitslogHistory.setWinddirectionMaster(this);

		return indoorunitslogHistory;
	}

	public IndoorunitslogHistory removeIndoorunitslogHistory(IndoorunitslogHistory indoorunitslogHistory) {
		getIndoorunitslogHistories().remove(indoorunitslogHistory);
		indoorunitslogHistory.setWinddirectionMaster(null);

		return indoorunitslogHistory;
	}

}