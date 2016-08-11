package com.panasonic.b2bacns.bizportal.custmanagement.controller;
import java.util.List;

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
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.installation.controller.Ca_DataController;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
/**
 * This controller handles Customer Registration
 * 
 * @author Srinivas
 * 
 */
@Controller
@RequestMapping(value = "/cust_data")
public class Cust_DataController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(Ca_DataController.class);
	
	@RequestMapping(value = "/view_cust_data.htm", method = RequestMethod.GET)
	public ModelAndView ca_details(HttpServletRequest request,
		ModelMap model, HttpSession session) {
		
		ModelAndView modelAndView = new ModelAndView();

		try {
//System.out.println("something");
			modelAndView.setViewName("cust_data/cust_data");

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		System.out.println(e);
		
		}
		return modelAndView;
	}
	@Autowired
	private Cust_DataService cust_info;
	
	@Autowired
	private GenericDAO<Company> dao;

	@RequestMapping(value = "/listsiteid.htm", method = RequestMethod.GET)
	@ResponseBody
	public String Show_siteid(@RequestParam(value = "customer_name") String cust_id) {

		int customerId=Integer.valueOf(cust_id);
		String jsonString = BizConstants.EMPTY_STRING;
		try {
			
			@SuppressWarnings("rawtypes")
			List jsonResponse = CompanyStructure.get_site_ids(cust_id);
			if(jsonResponse.size()==0){
				
				jsonString = CommonUtil.convertFromEntityToJsonStr(cust_info.Fetch_AllSiteNames(customerId));
			}else{
				
				jsonString = CommonUtil.convertFromEntityToJsonStr(cust_info.Fetch_AllSites(jsonResponse,customerId));
			}


		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;
	}
	
	
	/*@RequestMapping(value = "/postcreateCust.htm", method = RequestMethod.POST)
	@ResponseBody
	public String save_cust_details(@RequestParam(value = "cust_name") String cust_name,@RequestParam(value = "creationdate") Timestamp creationdate,
			@RequestParam(value = "country") String country,@RequestParam(value = "address") String address,@RequestParam(value = "postal_code") String postal_code,
			@RequestParam(value = "status") Integer status,HttpSession session) {

		List jsonResponse = new ArrayList<>();
		String jsonString = BizConstants.EMPTY_STRING;
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		
		try {

			Long Inserted_Cust_id = cust_info.create_new_cust(cust_name,creationdate,country,address,postal_code,status);
			String User_id = Long.toString(sessionInfo.getUserId());
			CompanyStructure.create_cust(Inserted_Cust_id,User_id);

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return  CommonUtil.getJSONErrorMessage(BizConstants.Cust_Registration_Success);
		
	}*/
	
	
	@RequestMapping(value = "/postcustreg.htm", method = RequestMethod.POST)
	@ResponseBody
	public String save_cust_details(@RequestParam(value = "customer_name") String customer_name,@RequestParam(value = "json_request")  String json_request,HttpSession session) {

		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		String Error_Message = null;

		try {
			//System.out.println(json_request);
			
			CustRequest site_ids = (CustRequest) CommonUtil
					.convertFromJsonStrToEntity(CustRequest.class,
							json_request);
			
			//cust_info.register_cust_siteid(site_ids.getSite_id(),customer_name,sessionInfo.getUserId());
			String User_id = Long.toString(sessionInfo.getUserId());
			
			
			long cust_id = Long.parseLong(customer_name);
			 
			//System.out.println(CompanyStructure.create_cust(cust_id,User_id));
			Error_Message = CompanyStructure.create_cust(cust_id,User_id);
			
			for(int i=0;i<site_ids.getSite_id().size();i++)
			{				

				String site_id = String.valueOf(site_ids.getSite_id().get(i));
				//System.out.println(site_id);
				CompanyStructure.regist(customer_name,site_id,User_id);

			}
			

		} catch (Exception e) {
			e.printStackTrace();
			
		}

		return  CommonUtil.getJSONErrorMessage(Error_Message);

	}
	
	
	
	@RequestMapping(value = "/listcustomers.htm", method = RequestMethod.GET)
	@ResponseBody
	public String Show_Customers() {


		
		String jsonString = BizConstants.EMPTY_STRING;
		try {
			
			List jsonResponse = cust_info.show_cust();
			jsonString = CommonUtil.convertFromEntityToJsonStr(cust_info.show_cust());
			//System.out.println(jsonResponse);

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString ;
	}
	
}
