package com.panasonic.b2bacns.bizportal.installation.controller;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.panasonic.spf.b2bac.facility.FacilityConstant.M2MPlatformCode;
import com.panasonic.spf.b2bac.facility.FacilityException;
import com.panasonic.spf.b2bac.facility.api.ControllerManager;
import com.panasonic.spf.b2bac.facility.api.parameter.InstallControllerParam;
import com.panasonic.spf.b2bac.facility.api.parameter.InstallControllerParam.CommunicationAuthentication;
import com.panasonic.spf.b2bac.facility.api.parameter.InstallControllerResult;
/**
*This Handles CA Installation and Maintenance Service PF functions
* 
* @author Srinivas
* 
*/
public class CaInstallationApi {
	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(Ca_DataController.class);

    /**
     * @return 
     * 
     */

    static List regist(String customer_name,String ca_mac_address_reg,String ca_model,String User_id,String sec_domain,String permission) {
    	
    		
    	//	List null_result = new Array(, owner)
    	//List<?> null_result = new ArrayList<>();
    	ArrayList<String> null_result = new ArrayList<String>();
    		
        try {
        	InstallControllerResult result = new InstallControllerResult();
            InstallControllerParam param = new InstallControllerParam();
            param.installMode = InstallControllerParam.InstallMode.REGIST;
            param.customerId = customer_name;
            param.physicalAddress = ca_mac_address_reg;
            param.modelId = ca_model;
            CommunicationAuthentication auth = new CommunicationAuthentication();
            auth.m2mPlatformCode = M2MPlatformCode.OpenDOF;
            auth.m2mAuthenticationInfo1 = sec_domain;
            auth.m2mAuthenticationInfo2 = customer_name;
            auth.m2mAuthenticationInfo3 = permission;
            param.communicationAuthentication = auth;
            String userId = User_id;
            ControllerManager controllerManager = new ControllerManager();
            
            result = controllerManager.installController(param, userId);
            if(result != null)
            {
            	null_result.add("correct");
            	null_result.add(result.controllerId);
            }
            else
            {
            	null_result.add(null);
            	null_result.add("Facility ID is not returned for CA");
            }

        } catch (FacilityException facExp) {
        	logger.error("Occured when registering CA, exception 1", facExp);
        	null_result.add(null);
        	null_result.add(facExp.getMessage());
        	System.out.println("Occured when registering CA, exception 1"+ facExp.getMessage());
        } catch (Exception exp) {
        	logger.error("Error: Occured when registering CA, exception 2", exp);
        	//System.out.println("Occured when registering CA, exception 2"+ exp);
        	//null_result = null;
        	null_result.add(null);
        	null_result.add(exp.getMessage());
        }
        
        

        return null_result;
        
    }

    
    static String install(String cust_name,String ca_mac_address,String model_name,String site_id,Timestamp installed_at,String User_id) {

    	
    	String null_result = "";
    	
        try {
        	InstallControllerResult result = new InstallControllerResult();
            InstallControllerParam param = new InstallControllerParam();
            param.installMode = InstallControllerParam.InstallMode.INSTALL;
            param.customerId = cust_name;
            param.physicalAddress = ca_mac_address;
            param.modelId = model_name;
            CommunicationAuthentication auth = new CommunicationAuthentication();
            auth.m2mPlatformCode = M2MPlatformCode.OpenDOF;
            auth.m2mAuthenticationInfo1 = "[6:b2bac.panasonic.com]";
            param.communicationAuthentication = auth;
            param.siteId = site_id;
            param.installDatetime = installed_at;
            String userId = User_id;
            ControllerManager controllerManager = new ControllerManager();
            result = controllerManager.installController(param, userId);
            if(result != null)
            {
            	
            	null_result = result.controllerId;
            }

        } catch (FacilityException facExp) {
        	
        	logger.error("Error: Occured when associating  CA exception 1", facExp);
        	null_result = null;

        } catch (Exception exp) {
        	logger.error("Error: Occured when associating CA exception 2", exp);
        	null_result = null;

        }
       // System.out.println(null_result);
        
        return null_result;
    }

}
