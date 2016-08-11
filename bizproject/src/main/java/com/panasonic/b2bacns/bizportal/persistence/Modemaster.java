package com.panasonic.b2bacns.bizportal.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the modemaster database table.
 * 
 */
@Entity
@Table(name="modemaster")
@NamedQuery(name="Modemaster.findAll", query="SELECT m FROM Modemaster m")
public class Modemaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=25)
	private String modename;

	//bi-directional many-to-one association to Indoorunitslog
	@OneToMany(mappedBy="modemaster")
	private List<Indoorunitslog> indoorunitslogs;

	//bi-directional many-to-one association to IndoorunitslogHistory
	@OneToMany(mappedBy="modemaster")
	private List<IndoorunitslogHistory> indoorunitslogHistories;

	public Modemaster() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModename() {
		return this.modename;
	}

	public void setModename(String modename) {
		this.modename = modename;
	}

	public List<Indoorunitslog> getIndoorunitslogs() {
		return this.indoorunitslogs;
	}

	public void setIndoorunitslogs(List<Indoorunitslog> indoorunitslogs) {
		this.indoorunitslogs = indoorunitslogs;
	}

	public Indoorunitslog addIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().add(indoorunitslog);
		indoorunitslog.setModemaster(this);

		return indoorunitslog;
	}

	public Indoorunitslog removeIndoorunitslog(Indoorunitslog indoorunitslog) {
		getIndoorunitslogs().remove(indoorunitslog);
		indoorunitslog.setModemaster(null);

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
		indoorunitslogHistory.setModemaster(this);

		return indoorunitslogHistory;
	}

	public IndoorunitslogHistory removeIndoorunitslogHistory(IndoorunitslogHistory indoorunitslogHistory) {
		getIndoorunitslogHistories().remove(indoorunitslogHistory);
		indoorunitslogHistory.setModemaster(null);

		return indoorunitslogHistory;
	}

}