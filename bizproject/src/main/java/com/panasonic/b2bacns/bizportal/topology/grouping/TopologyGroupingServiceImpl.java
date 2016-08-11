package com.panasonic.b2bacns.bizportal.topology.grouping;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.spf.b2bac.dataaggregation.DataAggregationConstant.DistributionCalcCode;
import com.panasonic.spf.b2bac.dataaggregation.DataAggregationConstant.DistributionGroupMeterType;
import com.panasonic.spf.b2bac.dataaggregation.DataAggregationException;
import com.panasonic.spf.b2bac.dataaggregation.api.DistributionGroupManager;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.CreateDistributionGroupParam;
import com.panasonic.spf.b2bac.facility.FacilityException;
import com.panasonic.spf.b2bac.facility.api.FacilityManager;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureParam;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureParam.TargetState;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Contoller;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.IndoorUnit;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Meter;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.OutdoorUnit;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Property;

@Service(value = "TopologyGroupingService")
public class TopologyGroupingServiceImpl implements TopologyGroupingService {

	@Autowired
	private SQLDAO sqlDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getCostomer(List cust_ids) {
		// TODO Auto-generated method stub
		return sqlDao.Costomer(cust_ids);
	}

	@Override
	public List getSite(List siteid) {
		// TODO Auto-generated method stub
		return sqlDao.getSiteIdTopolgy(siteid);
	}

	@Override
	public List CaAddress(int site_id, int cust_id) {
		// TODO Auto-generated method stub
		return sqlDao.CaAddress(site_id, cust_id);
	}

	@Override
	public List CaAddressDistributionGroup(List site_id, int cust_id) {
		// TODO Auto-generated method stub
		return sqlDao.CaAddressDistributionGroup(site_id, cust_id);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public List<IndoorUnitPlatForm> RetrieveTopolgyIDU(String siteid, String adapterName, String costomerId) {

		List<IndoorUnitPlatForm> IDUList;
		IDUList = new ArrayList<IndoorUnitPlatForm>();
		try {
//System.out.println(adapterName);
			String Site = siteid;
			String Costermerid = costomerId;
			GetFacilityStructureParam param = new GetFacilityStructureParam();
			param.customerId = Costermerid;
			param.siteId = Site;
			param.targetState = TargetState.APPROVED_LATEST;

			//List<String> dfi = new ArrayList<String>();
			// dfi.add(AdapterFid);
			// dfi.add(AdapterFid1);
			// param.controllerIds=dfi;
			/* param.controllerIds=dfi; */
			param.controllerIds = Arrays.asList(adapterName);
			/*
			 * param.controllerIds
			 * param.controllerIds.add("[2:{B0000000000F02000000}]");
			 */
			FacilityManager mgr = new FacilityManager();
			GetFacilityStructureResult result = mgr.getFacilityStructure(param);
			String s2 = null;
			String s3 = null;
			String s5 = null;

			for (Contoller ctrl1 : result.controllers) {
				
				System.out.println("***** Controller ID : " + ctrl1.controllerId + " *****");
				System.out.println("Model ID        : " + ctrl1.modelId);
				System.out.println("Service Status  : " + ctrl1.serviceStatus);
				// System.out.println("Property Count : " +
				// ctrl.properties.size());
				System.out.println(ctrl1.indoorUnits.size());

				for (int i = 0; i < ctrl1.indoorUnits.size(); i++) {
					IndoorUnitPlatForm idupf;
					idupf = new IndoorUnitPlatForm();
					IndoorUnit IDU = ctrl1.indoorUnits.get(i);
					idupf.setConnectionIduAddress(IDU.connectionIduAddress);
					idupf.setFacilityId(IDU.facilityId);	

					String myName = IDU.facilityId;

					// int fid_length = fid.length();
					// String fid_short = fid.substring(1, (fid_length-1));

					//System.out.println(myName);
					idupf.setFacilityId(myName);
					idupf.setConnectionType(IDU.connectionType);
					idupf.setConnectionNumber(IDU.connectionNumber);
					idupf.setRefrigCircuitId(IDU.refrigCircuitNo);
					idupf.setMainIduAddress(IDU.mainIduAddress);
					idupf.setMainIduFlag(IDU.mainIduFlag);
					idupf.setCentralControlAddress(IDU.centralControlAddress);
					
					System.out.println("MainIduAddress "+idupf.getMainIduAddress());
					System.out.println("ConnectionIduAddress "+idupf.getConnectionIduAddress());
					System.out.println("ConnectionNumber "+idupf.getConnectionNumber());
					System.out.println("RefrigCircuitId "+idupf.getRefrigCircuitId());
					System.out.println("CentralControlAddress "+idupf.getCentralControlAddress());
					System.out.println("mainIduFlag "+idupf.getMainIduFlag());
					System.out.println("FacilityId "+idupf.getFacilityId());
					System.out.println("FacilityId "+idupf.getConnectionType());
					//System.out.println("Line number "+idupf.getConnectionType());
					
					//System.out.println("refrigCircuitNo "+idupf.getre);
					
					idupf.setType("IDU");

					for (Property p : IDU.properties) {
						/*
						 * System.out.println("propertyId  : " + p.propertyId);
						 * System.out.println("value: " + p.value);
						 */
						if (p.propertyId.toUpperCase().equals("A30"))
							idupf.setDeviceModel(p.value);
						
						//if (p.propertyId.toUpperCase().equals("C5"))
							//System.out.println("C5 is "+ p.value);
						
						if (p.propertyId.toUpperCase().equals("T11")){
							//System.out.println("T11 is "+ p.value);
							idupf.setCentralAddress(p.value);
							
						}

						

					}
					IDUList.add(idupf);
					idupf.setSlink(idupf.getConnectionNumber() + "-" + idupf.getRefrigCircuitId() + "-"
							+ idupf.getConnectionIduAddress());
					System.out.println("Slink "+idupf.getConnectionNumber() + "-" + idupf.getRefrigCircuitId() + "-"
							+ idupf.getConnectionIduAddress());
					System.out.println("*******************************");
				}
				// this.RetrieveTopolgyIDUStoreData(IDUList,adapterName);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return IDUList;

	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<IndoorUnitPlatForm> RetrieveTopolgyIDUStoreData(List<IndoorUnitPlatForm> idutoplogy, String adapter) {
		// TODO Auto-generated method
		// stubsqlDao.saveRetrieveToplogyIdu(idutoplogy,adapter)
		return null;
	}*/

	@Override
	public List<OutdoorUintPf> RetrieveTopolgyODU(String siteid, String adapterName, String costomer_id) {



		List<OutdoorUintPf> ODUList = null;
		ODUList = new ArrayList<OutdoorUintPf>();

		try {
			
			String Site = siteid;
			String Costermerid = "1";
			//Integer Ratcap1 = 0;

			BigDecimal  rated_cool_cap = new BigDecimal(0.0);
			BigDecimal  rated_heat_cap = new BigDecimal(0.0);
			BigDecimal  rated_Heat_power = new BigDecimal(0.0);
			BigDecimal  rated_Cool_power = new BigDecimal(0.0);
			BigDecimal  rated_cool_effi = new BigDecimal(0.0);
			BigDecimal  rated_heat_effi = new BigDecimal(0.0);
			BigDecimal  sumRateCoolEffi = new BigDecimal(0.0);
			BigDecimal  sumRateHeatEffi = new BigDecimal(0.0);
			BigDecimal  ratedCoolEffi  = new BigDecimal(0.0);
			BigDecimal  ratedHeatEffi  = new BigDecimal(0.0);
			BigDecimal  avgRatedEffi = new BigDecimal(0.0);
			BigDecimal  zero = new BigDecimal(0.0);
			List refCirNumber = new ArrayList();
			
			int outdoor_number = 0;
			
			GetFacilityStructureParam param = new GetFacilityStructureParam();
			param.customerId = costomer_id;
			param.siteId = Site;
			param.targetState = TargetState.APPROVED_LATEST;
			param.controllerIds = Arrays.asList(adapterName);

			FacilityManager mgr = new FacilityManager();

			GetFacilityStructureResult result = mgr.getFacilityStructure(param);
			for (Contoller ctrl1 : result.controllers) {
				System.out.println("***** Controller ID : " + ctrl1.controllerId + " *****");

				System.out.println("Model ID        : " + ctrl1.modelId);
				System.out.println("Service Status  : " + ctrl1.serviceStatus);
				// System.out.println("Property Count : " +
				// ctrl.properties.size());
				
				outdoor_number= ctrl1.outdoorUnits.size();

				for (int i = 0; i < ctrl1.outdoorUnits.size(); i++) {
					OutdoorUnit odu = ctrl1.outdoorUnits.get(i);
					OutdoorUintPf odf1 = new OutdoorUintPf();
					odf1.setFacilityId(odu.facilityId);

					odf1.setConnectionType(odu.connectionType);

					System.out.println("odu connection number"+odu.connectionNumber);
					
					odf1.setConnectionNumber(odu.connectionNumber);

					odf1.setRefrigCircuitGroupOduId(odu.refrigCircuitGroupOduId);

					odf1.setRefrigCircuitId(Integer.valueOf(odu.refrigCircuitNo));

					

					odf1.setType("ODU");
					for (Property p : odu.properties) {
						/*
						 * System.out.println("propertyId  : " + p.propertyId);
						 * 
						 * System.out.println("value: " + p.value);
						 */

						if (p.propertyId.toUpperCase().equals("A30"))
							odf1.setModel(p.value);
						if (p.propertyId.toUpperCase().equals("B14"))
							odf1.setCategory(p.value);
						if (p.propertyId.toUpperCase().equals("C5")){
							odf1.setWay(Boolean.getBoolean(p.value));
						}
						if (p.propertyId.toUpperCase().equals("A29")) 
							rated_cool_cap = new BigDecimal(p.value);
						if (p.propertyId.toUpperCase().equals("A36")) 
							rated_heat_cap = new BigDecimal(p.value);
						if (p.propertyId.toUpperCase().equals("A37")) 
							rated_Cool_power = new BigDecimal(p.value);
						if (p.propertyId.toUpperCase().equals("A38")) 
							rated_Heat_power = new BigDecimal(p.value);

					}
					
					
					System.out.println("rated_cool_cap "+rated_cool_cap);
					System.out.println("rated_heat_cap "+rated_heat_cap);
					System.out.println("rated_Cool_power "+rated_Cool_power);
					System.out.println("rated_Heat_power "+rated_Heat_power);
					
					if(rated_Heat_power != zero && rated_Cool_power != zero)
					{

						//BigDecimal bd3 = bd1.divide(bd2, RoundingMode.HALF_UP);
						
						//odf1.setRatedCoolEffi(rated_cool_cap.divide(rated_heat_power,2, RoundingMode.FLOOR));
						
						System.out.println(rated_cool_cap.divide(rated_Cool_power,2, RoundingMode.FLOOR));
						System.out.println(rated_heat_cap.divide(rated_Heat_power,2, RoundingMode.FLOOR));
						
						
						//added by pramod
						odf1.setRated_Cool_Capacity(rated_cool_cap);						
						odf1.setRated_Heat_Capacity(rated_heat_cap);					
						odf1.setRated_Cool_Power(rated_Cool_power);					
						odf1.setRated_Heat_Power(rated_Heat_power);
						
						
						
						
						
						odf1.setRatedCoolEffi(rated_cool_cap.divide(rated_Cool_power,2, RoundingMode.FLOOR));
						odf1.setRatedHeatEffi(rated_heat_cap.divide(rated_Heat_power,2, RoundingMode.FLOOR));
						
						System.out.println("rate heat capppppppppppp"+rated_cool_cap);
						System.out.println("rate cool capppppppppppp"+rated_heat_cap);
						System.out.println("rate capaci capppppppppppp"+rated_cool_cap.add(rated_heat_cap));

						odf1.setRatedCapRef(rated_cool_cap.add(rated_heat_cap));

						
					}

					
					if(!refCirNumber.contains(Integer.valueOf(odu.refrigCircuitNo)))
					{
						refCirNumber.add(Integer.valueOf(odu.refrigCircuitNo));
					}
					odf1.setavgRatedEffi(avgRatedEffi);
					odf1.setS_link(odf1.getConnectionNumber() + "-" + odf1.getRefrigCircuitId() + "-"
							+ odf1.getRefrigCircuitGroupOduId());
					ODUList.add(odf1);
				}

			}

			// this.RetrieveTopolgyODUStoreData(ODUList,adapterName);

		} catch (FacilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return ODUList;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<OutdoorUintPf> RetrieveTopolgyODUStoreData(List<OutdoorUintPf> odutoplogy, String adapterName) {
		// TODO Auto-generated method stub
		return sqlDao.saveRetrieveToplogyOdu(odutoplogy, adapterName);
	}*/

	@Override
	public List<PulseMeterPf> RetrieveTopolgyPlS(String siteid, String adapterName, String costomer_id) {
		
		// TODO Auto-generated method stub
		List<PulseMeterPf> PLSList = null;
		PLSList = new ArrayList<PulseMeterPf>();

		try {

			String Site = siteid;
			String Costermerid = "1";
			GetFacilityStructureParam param = new GetFacilityStructureParam();
			param.customerId = costomer_id;
			param.siteId = Site;
			param.targetState = TargetState.APPROVED_LATEST;
			param.controllerIds = Arrays.asList(adapterName);
			FacilityManager mgr = new FacilityManager();

			GetFacilityStructureResult result;

			result = mgr.getFacilityStructure(param);

			for (Contoller ctrl1 : result.controllers) {
				System.out.println("***** pls Controller ID : " + ctrl1.controllerId + " *****");

				System.out.println("pls Model ID        : " + ctrl1.modelId);
				System.out.println("pls Service Status  : " + ctrl1.serviceStatus);
				// System.out.println("Property Count : " +
				// ctrl.properties.size());
				System.out.println(ctrl1.meters.size());

				for (int i = 0; i < ctrl1.meters.size(); i++) {
					Meter MTR = ctrl1.meters.get(i);
					PulseMeterPf Pmpf = new PulseMeterPf();

					Pmpf.setFacilityId(MTR.facilityId);

					Pmpf.setConnectionType(MTR.connectionType);

					Pmpf.setPort_number(MTR.portNumber);

					Pmpf.setType("PLS");
					System.out.println("length");

					System.out.println("PLS size:" + MTR.properties.size());
					
					Pmpf.setMulti_factor("1.00");
					Pmpf.setMetertype("1");
					
					if(MTR.properties.size() > 0){
						for (Property p : MTR.properties) {
							System.out.println("PLS some1");
							System.out.println("PLS property:" + p.propertyId);
							System.out.println("PLS value:" + p.value);
							
							
								 if(p.propertyId.toUpperCase().equals("M5")){
									 Pmpf.setMulti_factor(p.value); 
								 }

								 if(p.propertyId.toUpperCase().equals("M6")){
									 Pmpf.setMetertype(p.value); 
								 }
								
						
							
							// this.RetrieveTopolgyPLSStoreData(PLSList,adapterName);

						}
						
					}

					else{
						Pmpf.setMulti_factor("1.00");
						 Pmpf.setMetertype("1");
						
					}
					
					PLSList.add(Pmpf);

				}
			}

		} catch (FacilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("iam done");
		return PLSList;
	}
/*
	@SuppressWarnings("unchecked")
	@Override
	public List<PulseMeterPf> RetrieveTopolgyPLSStoreData(List<PulseMeterPf> plstoplogy, String adapterName) {
		// TODO Auto-generated method stub
		return sqlDao.saveRetrieveToplogyPls(plstoplogy, adapterName);
	}
*//*
	@Override
	public List UpdateRetrieveTopolgyPls(List<Topology> topologys, int adapter_id,String site_id) {
		// TODO Auto-generated method stub
		return sqlDao.UpdateRetrieveToplogyPls(topologys, adapter_id,site_id);
	}*/

	@Override
	public List UpdateRetrieveTopolgyIdu(List<IduData> topologysIDU, int adapter_id,String site_id) {
		// TODO Auto-generated method stub
		return sqlDao.UpdateRetrieveToplogyIdu(topologysIDU, adapter_id,site_id);
	}

	@Override
	public Boolean InsertRetrieveToplogy(List<OduData> list_1 ,List<IduData> list_2 ,List<Topology> list_3 ,int adapter_id,String site_id)
	{
		return sqlDao.InsertRetrieveToplogy(list_1,list_2,list_3,adapter_id,site_id);

	}
	
	
	@Override
	public Boolean UpdateRetrieveToplogy(List<OduData> list_1 ,List<IduData> list_2 ,List<Topology> list_3)
	{
		return sqlDao.UpdateRetrieveToplogy(list_1,list_2,list_3);

	}
	
	@Override
	public List UpdateRetrieveTopolgyOdu(List<OduData> Odu, int adapter_id,String site_id) {
		// TODO Auto-generated method stub
		return sqlDao.UpdateRetrieveToplogyOdu(Odu, adapter_id,site_id);
	}

	@Override
	public List Distributin_group(int CostomerorId) {
		// TODO Auto-generated method stub
		return sqlDao.DistributionGroup(CostomerorId);
	}

	@Override
	public List getdDistributionData(int groupId) {
		// TODO Auto-generated method stub
		return sqlDao.DisplayDistrbutionGroup(groupId);
	}

	@Override
	public Map<String, List<Object>> getdDistributionDropDownData(int cust_id) {
		// TODO Auto-generated method stub
		return sqlDao.listDistributionGroupDropDownData(cust_id);
	}

	@Override
	public List getdDistributionDataPLs(List siteid) {
		// TODO Auto-generated method stub
		return sqlDao.getDistributionGroupPls(siteid);
	}

	@Override
	public List getdDistributionDataIdu(List siteid) {
		// TODO Auto-generated method stub
		return sqlDao.getDistributionGroupIdu(siteid);
	}

	@SuppressWarnings("unused")
	@Override
	public List updateDistributionGroupData(List<DistributionGroupIduData> idu, int customerid, String userid) {

		

		List iduids = new ArrayList<>();
		if (idu != null && idu.size() > 0) {
			for (DistributionGroupIduData distributionGroupData : idu) {
				// iduids.add(distributionGroupData.getIdu_id());
			}
		}
		Map<Integer, String> groupid = new HashMap<Integer, String>();
		groupid = sqlDao.UpdateDistributionGroupIdu(idu);
		// this.updateDistributionGroupIduDataPlatForm(customerid,feclityId_groupID,userid);

		return null;

	}


	@Override
	public List updateDistributionGroupPlsData(List<DistributionGroupPlsData> pls, List<DistributionGroupIduData> Idu,
			int customerid, String userid) {

		/*
		 * if ( pls != null && pls.size() > 0) { for (DistributionGroupPlsData
		 * distributionGroupData : pls) {
		 * plsids.add(distributionGroupData.getPdu_id()); } }
		 */

		// List feclityId_groupID= sqlDao.UpdateDistributionGroupPls();
		// this.updateDistributionGroupPlsuDataPlatForm(customerid,feclityId_groupID,userid);
		return sqlDao.UpdateDistributionGroupPls();
	}

	@Override
	public List updateDistributionGroupPlsuDataPlatForm(int customerid, List fid, String userid) {
		Iterator<?> itr = fid.iterator();

		HashMap<Object, Object> fecilityIdGrourpId = new HashMap<Object, Object>();
		Object[] rowData = null;
		//CreateDistributionGroupParam param = new CreateDistributionGroupParam();
		// param.customerId=customerid;
		/*
		 * String pattern = "yyyy-MM-dd HH:mm:ss";
		 * 
		 * @SuppressWarnings("deprecation") Date simpleDateFormat = new
		 * Date(pattern); param.validPeriodStartDate=simpleDateFormat;
		 */

		while (itr.hasNext()) {
			rowData = (Object[]) itr.next();
			fecilityIdGrourpId.put(rowData[0], rowData[1]);
		}

		return null;
	}

	@Override
	public String Add_Distributin_group_Data(String DistributionName, int DistributionGroupId, String type,
			String calMode) {
		// TODO Auto-generated method stub
		return sqlDao.create_new_DistributionGroup(DistributionName, DistributionGroupId, type, calMode);
	}

	@Override
	public String Delete_Distributin_group_Data(Long DistributionGroupId, int costomer) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return sqlDao.delete_DistributionGroup_data(DistributionGroupId, costomer);
	}

	@Override
	public List getDistributionGroupId() {
		// TODO Auto-generated method stub
		return sqlDao.UpdateDistributionGroupPls();
	}
	
	@Override
	public List RetrieveAllTopolgy(String site_id,String cloud_adapter_id,String costomer_id)
	{
		return sqlDao.RetrieveAllTopolgy(site_id,cloud_adapter_id,costomer_id);
	}



	@Override
	public String updateTableRelatedToDisGroup(
			List<DistributionGroupIduData> disGroupList) {
		
		try {
			for (DistributionGroupIduData dg : disGroupList) {
				String plsFlag = dg.getDevice();
				if ("1".equals(plsFlag)) {
					sqlDao.updatePlusmeterTableByDisGroup(dg.getId(),dg.getFacilityId(),dg.getDist_grp());
				}else {
					sqlDao.updateIduTableByDisGroup(dg.getId(),dg.getFacilityId(),dg.getDist_grp());
				}
			}		
		}catch (Exception e) {
			
			e.printStackTrace();
			return CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_DISTRIBUTION_ERROR);
			
		}
		
		return CommonUtil.getJSONErrorMessage(BizConstants.SUCCESS_REGISTRATION_DIST);

			
	}

	@Override
	public String updateDistributionGroupIduDataPlatForm(int customerid, String DistributiongroupId, List<String> Idusfid,
			List<String> Plsfid,String userId,List<DistributionGroupIduData> disGroupList) {
		// TODO Auto-generated method stub
				/*
				 * HashMap <Object,Object> fecilityIdGrourpId= new
				 * HashMap<Object,Object>();
				 * 
				 * Iterator<?> itr = fid.iterator(); Object[] rowData = null;
				 */

				if (Idusfid !=null && Idusfid.size() != 0) {
					//String registeredsiteId = "2";
					
					
					String registeredCustomerId = Integer.toString(customerid);
					//String user = "1";

					CreateDistributionGroupParam param = new CreateDistributionGroupParam();
					DistributionGroupManager distributionGroupManager = new DistributionGroupManager();

					param.customerId = registeredCustomerId;
					param.distributionGroupId = DistributiongroupId;
					int DbGroupidQuery = Integer.parseInt(DistributiongroupId);
					String calcoede = sqlDao.getCalCode(DbGroupidQuery);
					if (calcoede.equals("Thermo ON")) {
						param.distributionCalcCode = DistributionCalcCode.THERMO_ON;

					} else {
						param.distributionCalcCode = DistributionCalcCode.WORKING_TIME;
					}

					
System.out.println("hi");
System.out.println(DistributionCalcCode.THERMO_ON);
System.out.println(DistributionCalcCode.WORKING_TIME);

System.out.println("enddddddddddddddddddddddddddddddddddddddddddd");

					try {
						param.validPeriodStartDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2015-01-01 00:00:00");
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					com.panasonic.spf.b2bac.dataaggregation.api.parameter.Meter meter2 = new com.panasonic.spf.b2bac.dataaggregation.api.parameter.Meter();

					param.meters = new ArrayList<com.panasonic.spf.b2bac.dataaggregation.api.parameter.Meter>();
					if (Plsfid != null) {
						for (int i = 0; i < Plsfid.size(); i++) {
							meter2.meterId = Plsfid.get(i);
							meter2.meterType = DistributionGroupMeterType.POWER;
							param.meters.add(meter2);
						}

					}
					
					param.indoorUnitIds = new ArrayList<String>();
					if (Idusfid != null) {
						for (int i = 0; i < Idusfid.size(); i++) {

							System.out.println(Idusfid.get(i));

							String IduFid = Idusfid.get(i);

							param.indoorUnitIds.add(IduFid);

						}
					}
										
				try {
					
					System.out.println(param.distributionCalcCode);
						distributionGroupManager.createDistributionGroup(param, userId);						
						
					} 
					catch (DataAggregationException daExp) {
						
						return CommonUtil.getJSONErrorMessage(BizConstants.PLATFORM_ERROR_DUPLICATE);
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("problme is here in plataform exception");
						return CommonUtil.getJSONErrorMessage(BizConstants.PLATFORM_ERROR);
						
						
						
					}
								
						
				} else {
					
					return CommonUtil.getJSONErrorMessage(BizConstants.SELECT_MIN_IDU);
					/*
					 * return CommonUtil
					 * .getJSONErrorMessage(BizConstants.SUCCESS_REGISTRATION_DIST);
					 */
				}
				
				return this.updateTableRelatedToDisGroup(disGroupList);
				
	}


}
