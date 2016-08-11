package com.panasonic.b2bacns.bizportal.custmanagement.controller;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.panasonic.b2bacns.bizportal.installation.controller.Ca_DataController;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.spf.b2bac.facility.FacilityException;
import com.panasonic.spf.b2bac.facility.api.CustomerManager;
import com.panasonic.spf.b2bac.facility.api.SiteManager;
import com.panasonic.spf.b2bac.facility.api.parameter.CreateCustomerParam;
import com.panasonic.spf.b2bac.facility.api.parameter.CreateSiteParam;
import com.panasonic.spf.b2bac.facility.api.parameter.DeleteSiteParam;
import com.panasonic.spf.b2bac.facility.api.parameter.GetCustomersResult;
import com.panasonic.spf.b2bac.facility.api.parameter.GetCustomersResult.Customer;
import com.panasonic.spf.b2bac.facility.api.parameter.GetSitesParam;
import com.panasonic.spf.b2bac.facility.api.parameter.GetSitesResult;
/**
 * This class handles all service PF Lib Functions
 * 
 * @author Srinivas
 * 
 */
public class CompanyStructure {
	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(Ca_DataController.class);
	
	
	static String create_cust(Long Inserted_Cust_id,String User_id) {
		String cust_status = BizConstants.Cust_Registration_Success;
		
        try {
            CreateCustomerParam param = new CreateCustomerParam();
            param.customerId = Long.toString(Inserted_Cust_id);
            String userId = User_id;
            CustomerManager customerManager = new CustomerManager();
            customerManager.createCustomer(param, userId);

        } catch (FacilityException facExp) {
      
         
        	cust_status = BizConstants.Already_Registered_User;
        	logger.error("Error: while viewing dashboard while registering customer exception 1", facExp);
        } catch (Exception exp) {

        	logger.error("Error: Service Platform Exception while registering customer exception 2", exp);
        	
        	//specified measurement manage master dosen't exist.
        }
        
        return cust_status;
    }

	
    static void regist(String customer_name,String site_id,String User_id) {
        try {
            CreateSiteParam param = new CreateSiteParam();

            param.customerId = customer_name;

            param.siteId = site_id;

            String userId = User_id;

            SiteManager siteManager = new SiteManager();
            siteManager.createSite(param, userId);

        } catch (FacilityException facExp) {
        	logger.error("Error: Service Platform Exception while registering site exception 1", facExp);
        } catch (Exception exp) {
        	logger.error("Error: Service Platform Exception while registering site exception 2", exp);
        }
    }

    /**
     * 
     */
    private static void delete() {
        try {
            DeleteSiteParam param = new DeleteSiteParam();

            param.customerId = "CSTMER001";

            param.siteId = "SITE001";

            String userId = "USER001";

            SiteManager siteManager = new SiteManager();
            siteManager.deleteSite(param, userId);

        } catch (FacilityException facExp) {
        } catch (Exception exp) {
        }
    }

public static List get() {
	

		List<Object> customer_ids = new ArrayList<>();
	
    try {
    	
        CustomerManager customerManager = new CustomerManager();
        GetCustomersResult result = customerManager.getCustomers();
        
        for(Customer customer : result.customers){
    		customer_ids.add(customer.customerId);
    	}

        return customer_ids;
    } catch (FacilityException facExp) {
    	logger.error("Error: While getting sites exception 1", facExp);
    	
    } catch (Exception exp) {
    	logger.error("Error: While getting sites exception 2", exp);
    }
     
	return null;
}




public static List get_site_ids(String cust_id){
	
	List<Object> site_ids = new ArrayList<>();

	try {


		/****************************************************
		 *  Test for SiteManager Get
		 ***************************************************/

		GetSitesParam getSitesParam = new GetSitesParam();
		//GetSitesResult getSiteResult = new GetSitesResult();

		getSitesParam.customerId = cust_id;
		SiteManager sitemanager = new SiteManager();
		GetSitesResult getSiteResult = sitemanager.getSites(getSitesParam);
		
		//getSiteResult = SiteManager.getSites(getSitesParam);

		String registeredSite = "";
		Boolean isRegisteredSiteExist = Boolean.FALSE;

		// check to verify inserted regsitered site is there in db
		for(String s : getSiteResult.siteIds){
			if (s.equals(registeredSite))
				isRegisteredSiteExist = Boolean.TRUE;
			site_ids.add(s);
		}
		
		 return site_ids;

		
	} catch (FacilityException facExp) {
		logger.error("Error: While getting all site ids exception 1", facExp);
	} catch (Exception exp) {
		logger.error("Error: While getting all site ids exception 2", exp);
	}	
	
	return site_ids;
}













}
