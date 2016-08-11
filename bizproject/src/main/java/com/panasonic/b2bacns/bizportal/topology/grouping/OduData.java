package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.math.BigDecimal;

public class OduData {

	/*
	 * 
	 * @author pramod
	 * 
	 */
	private String s_linkaddress;

	




	private int id;


	private String ConnectionNumber;
	
	//added by pramod
	private BigDecimal rated_Cool_Capacity;
	private BigDecimal rated_Heat_Capacity;
	private BigDecimal rated_Cool_Power;
	private BigDecimal rated_Heat_Power;
	
	




	

	
	

	
	public BigDecimal getRated_Cool_Capacity() {
		return rated_Cool_Capacity;
	}



	public void setRated_Cool_Capacity(BigDecimal rated_Cool_Capacity) {
		this.rated_Cool_Capacity = rated_Cool_Capacity;
	}



	public BigDecimal getRated_Heat_Capacity() {
		return rated_Heat_Capacity;
	}



	public void setRated_Heat_Capacity(BigDecimal rated_Heat_Capacity) {
		this.rated_Heat_Capacity = rated_Heat_Capacity;
	}



	public BigDecimal getRated_Cool_Power() {
		return rated_Cool_Power;
	}



	public void setRated_Cool_Power(BigDecimal rated_Cool_Power) {
		this.rated_Cool_Power = rated_Cool_Power;
	}



	public BigDecimal getRated_Heat_Power() {
		return rated_Heat_Power;
	}



	public void setRated_Heat_Power(BigDecimal rated_Heat_Power) {
		this.rated_Heat_Power = rated_Heat_Power;
	}



	private String category;

	public BigDecimal getRatedCoolEffi() {
		return RatedCoolEffi;
	}



	public void setRatedCoolEffi(BigDecimal ratedCoolEffi) {
		RatedCoolEffi = ratedCoolEffi;
	}



	public BigDecimal getRatedHeatEffi() {
		return RatedHeatEffi;
	}



	public void setRatedHeatEffi(BigDecimal ratedHeatEffi) {
		RatedHeatEffi = ratedHeatEffi;
	}



	public BigDecimal getRatedCapRef() {
		return RatedCapRef;
	}



	public void setRatedCapRef(BigDecimal ratedCapRef) {
		RatedCapRef = ratedCapRef;
	}




	public BigDecimal getAvgRatedEffi() {
		return avgRatedEffi;


	}



	public void setAvgRatedEffi(BigDecimal avgRatedEffi) {
		this.avgRatedEffi = avgRatedEffi;


	}



	public BigDecimal RatedCoolEffi;
	public BigDecimal RatedHeatEffi;
	public BigDecimal RatedCapRef;
	public BigDecimal avgRatedEffi;
	

	public String parent_id;


	





	public OduData(String s_linkaddress, int id, String connectionNumber,
			BigDecimal rated_Cool_Capacity, BigDecimal rated_Heat_Capacity,
			BigDecimal rated_Cool_Power, BigDecimal rated_Heat_Power,
			String category, BigDecimal ratedCoolEffi,
			BigDecimal ratedHeatEffi, BigDecimal ratedCapRef,
			BigDecimal avgRatedEffi, String parent_id, String facilityId,
			String model, String dname, String connectionType,
			String deviceName, String refrigCircuitGroupOduId,
			Integer refrigCircuitId, String s_link) {
		super();
		this.s_linkaddress = s_linkaddress;
		this.id = id;
		ConnectionNumber = connectionNumber;
		this.rated_Cool_Capacity = rated_Cool_Capacity;
		this.rated_Heat_Capacity = rated_Heat_Capacity;
		this.rated_Cool_Power = rated_Cool_Power;
		this.rated_Heat_Power = rated_Heat_Power;
		this.category = category;
		RatedCoolEffi = ratedCoolEffi;
		RatedHeatEffi = ratedHeatEffi;
		RatedCapRef = ratedCapRef;
		this.avgRatedEffi = avgRatedEffi;
		this.parent_id = parent_id;
		this.facilityId = facilityId;
		this.model = model;
		this.dname = dname;
		this.connectionType = connectionType;
		this.deviceName = deviceName;
		this.refrigCircuitGroupOduId = refrigCircuitGroupOduId;
		this.refrigCircuitId = refrigCircuitId;
		this.s_link = s_link;
	}



	public String getCategory() {
		return category;
	}



	public int getId() {
		return id;
	}










	public void setId(int id) {
		this.id = id;
	}



	public void setCategory(String category) {
		this.category = category;
	}







	public String getParent_id() {
		return parent_id;
	}



	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}



	public String getConnectionNumber() {
		return ConnectionNumber;
	}



	public void setConnectionNumber(String connectionNumber) {
		ConnectionNumber = connectionNumber;
	}



	private String facilityId;
	private String model;
	private String dname;

	private String connectionType;
	private String deviceName;

	private String refrigCircuitGroupOduId;
	private Integer refrigCircuitId;
	private String s_link;
	
	

	public String getS_linkaddress() {
		return s_linkaddress;
	}



	public void setS_linkaddress(String s_linkaddress) {
		this.s_linkaddress = s_linkaddress;
	}



	public String getFacilityId() {
		return facilityId;
	}



	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}



	public String getModel() {
		return model;
	}



	public void setModel(String model) {
		this.model = model;
	}



	public String getDname() {
		return dname;
	}



	public void setDname(String dname) {
		this.dname = dname;
	}



	public String getConnectionType() {
		return connectionType;
	}



	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}



	public String getDeviceName() {
		return deviceName;
	}



	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}



	public String getRefrigCircuitGroupOduId() {
		return refrigCircuitGroupOduId;
	}



	public void setRefrigCircuitGroupOduId(String refrigCircuitGroupOduId) {
		this.refrigCircuitGroupOduId = refrigCircuitGroupOduId;
	}



	public Integer getRefrigCircuitId() {
		return refrigCircuitId;
	}



	public void setRefrigCircuitId(Integer refrigCircuitId) {
		this.refrigCircuitId = refrigCircuitId;
	}



	public String getS_link() {
		return s_link;
	}



	public void setS_link(String s_link) {
		this.s_link = s_link;
	}



	public OduData() {
		super();
	}
}
