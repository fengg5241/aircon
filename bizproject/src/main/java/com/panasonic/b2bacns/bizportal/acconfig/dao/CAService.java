package com.panasonic.b2bacns.bizportal.acconfig.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.panasonic.b2bacns.bizportal.custmanagement.controller.CompanyStructure;
import com.panasonic.b2bacns.bizportal.topology.grouping.PulseMeterPf;
import com.panasonic.spf.b2bac.facility.FacilityException;
import com.panasonic.spf.b2bac.facility.api.FacilityManager;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureParam;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Property;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Contoller;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.Meter;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.IndoorUnit;
import com.panasonic.spf.b2bac.facility.api.parameter.GetFacilityStructureResult.OutdoorUnit;




public class CAService {
	
	public static List<String> getPulseMeterList(String siteid, String adapterName, String costomer_id) {
		
		List<PulseMeterPf> PLSList = null;
		PLSList = new ArrayList<PulseMeterPf>();
		List<String> PulseMtrList = new ArrayList<String>();
		

		try {

			List<String> customer_ids = CompanyStructure.get();
			for (String temp : customer_ids) {
				System.out.println(temp);
			}
			
			List<String> jsonResponse = CompanyStructure.get_site_ids("");
			
			for (String temp : jsonResponse) {
				System.out.println(temp);
			}
			
			
			String Site = siteid;
			
			GetFacilityStructureParam param = new GetFacilityStructureParam();
			param.customerId = costomer_id;
			param.siteId = Site;
			param.targetState = param.targetState.APPROVED_LATEST;
			param.controllerIds = Arrays.asList(adapterName);
			FacilityManager mgr = new FacilityManager();

			GetFacilityStructureResult result;
			result = mgr.getFacilityStructure(param);

			for (Contoller ctrl1 : result.controllers) {
				System.out.println("***** pls Controller ID : " + ctrl1.controllerId + " *****");

				for (int i = 0; i < ctrl1.meters.size(); i++) {
					Meter MTR = ctrl1.meters.get(i);
					
					PulseMeterPf Pmpf = new PulseMeterPf();

					Pmpf.setFacilityId(MTR.facilityId);
					Pmpf.setConnectionType(MTR.connectionType);
					Pmpf.setPort_number(MTR.portNumber);
					Pmpf.setType("PLS");
                    
					System.out.println("length");

					System.out.println(MTR.properties.size());
					if(MTR.properties.size() > 0){
						for (Property p : MTR.properties) {
							System.out.println(p.propertyId);

								 if(p.propertyId.toUpperCase().equals("M5")){
									 
									 Pmpf.setMulti_factor(p.value); 
									 PulseMtrList.add(p.value);
								 }
								 else{
									 Pmpf.setMulti_factor("2");
									 PulseMtrList.add("2");
								 }
								 if(p.propertyId.toUpperCase().equals("M6")){
									 Pmpf.setMetertype(p.value);
									 PulseMtrList.add(p.value);
								 }
								 else{
									 Pmpf.setMetertype("2");
									 PulseMtrList.add("2");
								 }
						
						}
						
					}else{
						Pmpf.setMulti_factor("2");
						 Pmpf.setMetertype("2");
						 PulseMtrList.add("0");
						
					}
					
					PLSList.add(Pmpf);

				}
			}

		} catch (FacilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return PulseMtrList;
	}
}
