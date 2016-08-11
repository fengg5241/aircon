package com.panasonic.b2bacns.bizportal.topology.grouping;
import java.math.BigDecimal;

public class OutdoorUintPf {
private String facilityId;
	
	private String connectionType;
	private String connectionNumber;
	private String refrigCircuitGroupOduId;
	private Integer refrigCircuitId;
	private BigDecimal RatedCoolEffi;
	private BigDecimal RatedHeatEffi;
	private BigDecimal RatedCapRef;
	private BigDecimal avgRatedEffi;
	private int adapterid;
	private String Model;
	private String S_link;
	public BigDecimal getRated_Cool_Capacity() {
		return Rated_Cool_Capacity;
	}
	public void setRated_Cool_Capacity(BigDecimal rated_Cool_Capacity) {
		Rated_Cool_Capacity = rated_Cool_Capacity;
	}
	public BigDecimal getRated_Heat_Capacity() {
		return Rated_Heat_Capacity;
	}
	public void setRated_Heat_Capacity(BigDecimal rated_Heat_Capacity) {
		Rated_Heat_Capacity = rated_Heat_Capacity;
	}
	public BigDecimal getRated_Cool_Power() {
		return Rated_Cool_Power;
	}
	public void setRated_Cool_Power(BigDecimal rated_Cool_Power) {
		Rated_Cool_Power = rated_Cool_Power;
	}
	public BigDecimal getRated_Heat_Power() {
		return Rated_Heat_Power;
	}
	public void setRated_Heat_Power(BigDecimal rated_Heat_Power) {
		Rated_Heat_Power = rated_Heat_Power;
	}
	private String Type;
	private String Dname;
	private String Ratcap1;
	private String Ratcap2;

	
	//added by pramod
	private BigDecimal Rated_Cool_Capacity;
	private BigDecimal Rated_Heat_Capacity;
	private BigDecimal Rated_Cool_Power;
	private BigDecimal Rated_Heat_Power;
	
	
	
	private boolean way;
	
	public boolean isWay() {
		return way;
	}
	public void setWay(boolean way) {
		this.way = way;
	}
	public String getRatcap1() {
		return Ratcap1;
	}
	public void setRatcap1(String ratcap1) {
		Ratcap1 = ratcap1;
	}
	public String getRatcap2() {
		return Ratcap2;
	}
	public void setRatcap2(String ratcap2) {
		Ratcap2 = ratcap2;
	}

	public String getRatedCapacity() {
		return RatedCapacity;
	}
	public void setRatedCapacity(String ratedCapacity) {
		RatedCapacity = ratedCapacity;
	}
	private String Category;
	private String RatedCapacity;
	

	
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getDname() {
		return Dname;
	}
	public void setDname(String dname) {
		Dname = dname;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getS_link() {
		return S_link;
	}
	public void setS_link(String s_link) {
		S_link = s_link;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	
	public int getAdapterid() {
		return adapterid;
	}
	public void setAdapterid(int adapterid) {
		this.adapterid = adapterid;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String setFacilityId(String facilityId) {
		return this.facilityId = facilityId;
	}
	
	public String getConnectionType() {
		return connectionType;
	}
	public String setConnectionType(String connectionType) {
		return this.connectionType = connectionType;
	}
	public String getConnectionNumber() {
		return connectionNumber;
	}
	public String setConnectionNumber(String connectionNumber) {
		return this.connectionNumber = connectionNumber;
	}
	public String getRefrigCircuitGroupOduId() {
		return refrigCircuitGroupOduId;
	}
	public String setRefrigCircuitGroupOduId(String refrigCircuitGroupOduId) {
		return this.refrigCircuitGroupOduId = refrigCircuitGroupOduId;
	}
	public Integer getRefrigCircuitId() {
		return refrigCircuitId;
	}
	public Integer setRefrigCircuitId(Integer integer) {
		return this.refrigCircuitId = integer;
	}

	
	public BigDecimal getRatedCapRef() {
		return RatedCapRef;
	}
	public BigDecimal setRatedCapRef(BigDecimal RatedCapRef) {
		return this.RatedCapRef = RatedCapRef;
	}
	
	
	public BigDecimal getRatedCoolEffi() {
		return RatedCoolEffi;
	}
	public BigDecimal setRatedCoolEffi(BigDecimal bigDecimal) {
		return this.RatedCoolEffi = bigDecimal;
	}
	public BigDecimal getRatedHeatEffi() {
		return RatedHeatEffi;
	}
	public BigDecimal setRatedHeatEffi(BigDecimal RatedHeatEffi) {
		return this.RatedHeatEffi = RatedHeatEffi;
	}

	public BigDecimal getavgRatedEffi() {
		return avgRatedEffi;
	}
	public BigDecimal setavgRatedEffi(BigDecimal avgRatedEffi) {
		return this.avgRatedEffi = avgRatedEffi;


	}
}
