package com.panasonic.b2bacns.bizportal.installation.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.installation.controller.CaInstallationApi;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.custmanagement.controller.CompanyStructure;
import com.panasonic.b2bacns.bizportal.util.Platform;
/**
 * This controller handles Ca Installation and Maintenance 
 * 
 * @author Srinivas
 * 
 */
@Controller
@RequestMapping(value = "/ca_data")
public class Ca_DataController {
	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(Ca_DataController.class);
	
	@Autowired
	private Ca_DataService fetch_all_ca;
	

	/**
	 * This method is used to display Ca Data page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewCa.htm", method = RequestMethod.GET)
	public ModelAndView ca_details(HttpServletRequest request,
		ModelMap model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();

		try {

			modelAndView.setViewName("ca_data/ca_data");

		} catch (Exception e) {
			logger.error("Error: while viewing schedule ", e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/postviewCa.htm", method = RequestMethod.GET)
	@ResponseBody
	public String save_ca_details(@RequestParam(value = "ca_mac") String ca_mac) {

		String jsonString = BizConstants.EMPTY_STRING;
		try {
			jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.Show_Ca_data(ca_mac));
			
			System.out.println("converting");
			System.out.println(jsonString);
			
		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;
	}
	
	@RequestMapping(value = "/listMac_Casearch.htm", method = RequestMethod.POST)
	@ResponseBody
	public String Show_Mac(@RequestParam(value = "cust_id") String cust_id) {

		
		String jsonString = BizConstants.EMPTY_STRING;
		try {
			
			List jsonResponse = fetch_all_ca.Fetch_mac_Address(cust_id);
			jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.Fetch_mac_Address(cust_id));


		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;
	}
	
	@RequestMapping(value = "/listMacaddress_cust.htm", method = RequestMethod.POST)
	@ResponseBody
	public String Show_Mac_cust(@RequestParam(value = "cust_id") String cust_id) {
		
		String jsonString = BizConstants.EMPTY_STRING;
		try {


			jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.Fetch_mac_Address_reg(cust_id));
					

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;
	}
	
	

	
	@RequestMapping(value = "/listsiteidcust.htm", method = RequestMethod.GET)
	@ResponseBody
	public String Show_siteidcust(@RequestParam(value = "cust_id") String cust_id) {

	
		
		String jsonString = BizConstants.EMPTY_STRING;
		try {

			
			List<Object> site_ids = CompanyStructure.get_site_ids(cust_id);
			jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.show_site_customers(site_ids));
			
			System.out.println(jsonString);

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;

	
	}
	
	@RequestMapping(value = "/listcamodel.htm", method = RequestMethod.GET)
	@ResponseBody
	public String Show_caModel() {

		
		String jsonString = BizConstants.EMPTY_STRING;
		try {

			jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.Fetch_caModel());
			
			System.out.println(jsonString);


		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;
	}
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@RequestMapping(value = "/Register_Ca.htm", method = RequestMethod.POST)
	@ResponseBody
	public String save_ca_reg(@RequestParam(value = "ca_mac_address_reg",required = true) String ca_mac_address_reg,@RequestParam(value = "sec_domain",required = true) String sec_domain,@RequestParam(value = "ca_model",required = true) String ca_model,@RequestParam(value = "customer_name",required = true) Long company_id,@RequestParam(value = "permission",required = true) String permission,HttpSession session) {

		String jsonString = BizConstants.EMPTY_STRING;
		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		List Facility_Id =null;
		Integer Inserted_Ca_id_value ;
		Integer Duplicate = 0;
		
		try {

			String User_id = Long.toString(sessionInfo.getUserId());
			
			List Inserted_Ca_id = fetch_all_ca.register_ca(ca_mac_address_reg,ca_model,company_id,User_id);


			if (Inserted_Ca_id != null && Inserted_Ca_id.size() > 0)
			{
				Object ca_id=Inserted_Ca_id.get(0);
				
				String ca_id_string="";			
				Integer ca_id_number=null;			
				Inserted_Ca_id_value =Integer.valueOf(String.valueOf(ca_id)) ;
				
				System.out.println(Inserted_Ca_id.get(0));
				String spf_company_id = Long.toString(company_id);
				String spf_ca_model = ca_model;
			    Facility_Id =  CaInstallationApi.regist(spf_company_id,ca_mac_address_reg,spf_ca_model,User_id,sec_domain,permission);			
			  
				if(Facility_Id.get(0) != null)
				{
					fetch_all_ca.Insert_Facility_id((String) Facility_Id.get(1),Inserted_Ca_id_value);
					return  CommonUtil.getJSONErrorMessage(BizConstants.CA_Registration_Success);
					//Duplicate = 1;
				}else{
					
					fetch_all_ca.delete_ca(Inserted_Ca_id_value);
					logger.error(Facility_Id.get(1));
					return  CommonUtil.getJSONErrorMessage((String) Facility_Id.get(1));
					
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: while viewing ca data ", e);
		
		}
		return jsonString; 

	}
	
	
	@RequestMapping(value = "/postCaassociate.htm", method = RequestMethod.POST)
	@ResponseBody
	public String save_ca_associate(@RequestParam(value = "cust_name", required = true) String cust_name,@RequestParam(value = "ca_mac_address", required = true) String ca_mac_address,@RequestParam(value = "site_id", required = true) String site_id,
			@RequestParam(value = "Timezone", required = true) Integer Timezone,@RequestParam(value = "Latitude", required = true) String Latitude,@RequestParam(value = "Longitude", required = true) String Longitude,
			@RequestParam(value = "installed_at", required = true) String installed_at,@RequestParam(value = "status", required = true) String status,@RequestParam(value = "model_name", required = true) String model_name,@RequestParam(value = "ca_name_associate", required = true) String ca_name,HttpSession session) 
	{
		
		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		String User_id = Long.toString(sessionInfo.getUserId());
		String jsonString = BizConstants.EMPTY_STRING;
		String Facility_Id =null;
		
		try {
			
			//System.out.println(installed_at.substring(0,installed_at.length()-5));
			System.out.println("installing");

			java.sql.Timestamp server_installed_at = java.sql.Timestamp.valueOf(installed_at) ;

			if(fetch_all_ca.check_ca_duplicate(site_id,cust_name,ca_name) == true)
			{
				
				return  CommonUtil.getJSONErrorMessage(BizConstants.CA_NAME_DUPLICATE);
			}
			else
			{
				fetch_all_ca.associate_ca_siteid(ca_mac_address,site_id,Timezone,Latitude,Longitude,server_installed_at,status,ca_name);
				Facility_Id =  CaInstallationApi.install(cust_name,ca_mac_address,model_name,site_id,server_installed_at,User_id);
			}
			//System.out.println(Facility_Id);

		} catch (Exception e) {
			
			e.printStackTrace();
		
			logger.error("Error: while viewing ca data ", e);
			
			return  CommonUtil.getJSONErrorMessage(BizConstants.CA_Association_FAILURE);
		}
		
		return  CommonUtil.getJSONErrorMessage(BizConstants.CA_Association_Success);

	}

	@RequestMapping(value = "/listcustomers_adapters.htm", method = RequestMethod.GET)
	@ResponseBody
	public String Show_Customers(HttpSession session) {

		String jsonString = BizConstants.EMPTY_STRING;
		List<?> resultList = new ArrayList<>();
		List<Object> Compared_Custs = new ArrayList<>();
		//String RoletypeId = BizConstants.EMPTY_STRING;
		int RoletypeId = 0;
		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
				
		Map<Integer, String> RoleTypeMap = sessionInfo.getRoleTypeMap();
		 Long User_id = sessionInfo.getUserId();

		try {
			
			List customer_ids =  CompanyStructure.get();
			RoletypeId = (int) fetch_all_ca.get_roleTypeId(User_id).get(0);
			

			if(RoletypeId == 2)
			{
				resultList = fetch_all_ca.show_cust_names_operator(User_id);
				if(resultList.size()!=0)
				{

					for (Object ti : resultList) {
						
						if(customer_ids.contains(ti.toString())==true)
						{
							Compared_Custs.add(ti);
						}
					}
										
					jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.show_cust_names(Compared_Custs));

				}

			}else{
				
				jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.show_cust_names(customer_ids));
			}


		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;
	}
	
	@RequestMapping(value = "/listtimezones.htm", method = RequestMethod.GET)
	@ResponseBody
	public String Show_Timzones() {

		String jsonString = BizConstants.EMPTY_STRING;
		try {

			jsonString = CommonUtil.convertFromEntityToJsonStr(fetch_all_ca.Show_Timzones());


		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;
	}
	
	
	@RequestMapping(value = "/getdomain.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getdomain() {

		String jsonString = "";
		try {

			/*Platform pf = new Platform();
			pf.getSECURITY_DOMAIN();*/
			
			Platform properties = new Platform();
			properties.getPropValues();
			jsonString = properties.getPropValues(); 


		} catch (Exception e) {
			logger.error("Error: while Fetching Security domain ", e);
		}

		return jsonString ;
	}
	
}
