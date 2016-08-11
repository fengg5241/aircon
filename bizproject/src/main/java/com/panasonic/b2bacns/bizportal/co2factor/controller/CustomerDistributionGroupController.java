package com.panasonic.b2bacns.bizportal.co2factor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.custmanagement.controller.CompanyStructure;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupIdu;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupIduData;
import com.panasonic.b2bacns.bizportal.topology.grouping.TopologyGroupSqlDao;
import com.panasonic.b2bacns.bizportal.topology.grouping.TopologyGroupingService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

@Controller
@RequestMapping(value = "/customer")
public class CustomerDistributionGroupController {

	private static final Logger logger = Logger
			.getLogger(CustomerDistributionGroupController.class);

	@Autowired
	private TopologyGroupSqlDao TopologyGroupSqlDao;

	@Autowired
	private TopologyGroupingService TopologyGroupingService;
	
	
	@Autowired
	private CustomerDistributionGroupService CustomerDistributionGroupService;

	@RequestMapping(value = "/addDistributionGroup.htm", method = RequestMethod.GET)
	@ResponseBody
	public String AddDistributionGroup(
			@RequestParam(value = "DG_GroupName") String groupName,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "cal") String calMode, HttpSession session) {

		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		String jsonString = BizConstants.EMPTY_STRING;
		try {

			String User_id = Long.toString(sessionInfo.getUserId());
			int CustomerID =Integer.valueOf(User_id);
			
			int	CustomerId=	CustomerDistributionGroupService.getCustomerIdCustomerDistribution(CustomerID);

			jsonString=	CustomerDistributionGroupService
					.create_new_DistributionGroup(groupName, CustomerId, type,
							calMode);

		} catch (Exception e) {

			// CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while Adding distribution group", e);
			e.printStackTrace();
			
			
			
		}

		return jsonString;

	}

	@RequestMapping(value = "/distributionGroup.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getDistributionGroupNames(
			 HttpSession session) {

		String jsonString = BizConstants.EMPTY_STRING;
		
		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		try {

			String User_id = Long.toString(sessionInfo.getUserId());
			int CustomerID =Integer.valueOf(User_id);
			
			int	CustomerId=	CustomerDistributionGroupService.getCustomerIdCustomerDistribution(CustomerID);
			

			
			jsonString = CommonUtil
					.convertFromEntityToJsonStr(CustomerDistributionGroupService
							.Distributin_group(CustomerId));

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@RequestMapping(value = "/getsitenames.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getsite_names(
			@RequestParam(value = "costomer_id") int CustomerID) {

		String jsonString = BizConstants.EMPTY_STRING;
		try {

			
			jsonString = CommonUtil
					.convertFromEntityToJsonStr(CustomerDistributionGroupService
							.getCustomerSiteIdDistributionGroup(CustomerID));

			System.out.println(jsonString);

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@RequestMapping(value = "/displayDistributiongroup1.htm", method = RequestMethod.POST)
	@ResponseBody
	public String showDistributionGroupPlsAndIdu1(
			@RequestParam(value = "Dgroupid") int ca_id) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getdDistributionData(ca_id));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@RequestMapping(value = "/DistributionGroupDropDown.htm", method = RequestMethod.POST)
	@ResponseBody
	public String showDistributionGroupDropDown(HttpSession session) {

		// List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;

		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		try {
			String User_id = Long.toString(sessionInfo.getUserId());
			int CustomerID =Integer.valueOf(User_id);
			
			int	CustomerId=	CustomerDistributionGroupService.getCustomerIdCustomerDistribution(CustomerID);
			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getdDistributionDropDownData(CustomerId));

			System.out.println(jsonString);

		} catch (Exception e) {
			CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@RequestMapping(value = "/ShowDistributionGroupPls.htm", method = RequestMethod.POST)
	@ResponseBody
	public String showDistributionGroupPLS(
			@RequestParam(value = "Ca_id[]") List ca_id) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getdDistributionDataPLs(ca_id));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@RequestMapping(value = "/ShowDistributionGroupIdu.htm", method = RequestMethod.POST)
	@ResponseBody
	public String showDistributionGroupIDu(
			@RequestParam(value = "Ca_id[]") List ca_id) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getdDistributionDataIdu(ca_id));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@SuppressWarnings({ "unchecked", "null" })
	@RequestMapping(value = "/updateDistributionGroupIduPls.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String updatedistributionGroupPLS(
			
			
			@RequestParam(value = "dgroupPlsIDu") String topology,
			HttpSession session) {

		String jsonString = BizConstants.EMPTY_STRING;
		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		try {
			String User_id = Long.toString(sessionInfo.getUserId());
			int CustomerID =Integer.valueOf(User_id);
			
			int	CustomerId=	CustomerDistributionGroupService.getCustomerIdCustomerDistribution(CustomerID);
			
			String customerId=String.valueOf(CustomerId);
			// List<?> AllDistIds =
			// TopologyGroupingService.getDistributionGroupId();

			DistributionGroupIdu acConfigRequestIDU = (DistributionGroupIdu) CommonUtil
					.convertFromJsonStrToEntity(DistributionGroupIdu.class,
							topology);
			List<DistributionGroupIduData> disGroupList = acConfigRequestIDU
					.getDgroupplsiduList();
			// System.out.println(disGroupList);
			// System.out.println(disGroupList.size());

			Map<Integer, List<String>> plsmap = new HashMap<Integer, List<String>>();

			Map<Integer, List<String>> idumap = new HashMap<Integer, List<String>>();

			List<Integer> uniqueDisGroupList = new ArrayList<Integer>();
			for (DistributionGroupIduData dg : disGroupList) {
				int dist_grp = dg.getDist_grp();
				if (!uniqueDisGroupList.contains(dist_grp)) {
					uniqueDisGroupList.add(dist_grp);
				}
				String plsFlag = dg.getDevice();

				if ("1".equals(plsFlag)) {
					if (!plsmap.containsKey(dist_grp)) {
						List<String> falicityIds = new ArrayList<String>();
						falicityIds.add(dg.getFacilityId());
						plsmap.put(dist_grp, falicityIds);
					} else {
						plsmap.get(dist_grp).add(dg.getFacilityId());
					}
				} else {
					if (!idumap.containsKey(dist_grp)) {
						List<String> falicityIds = new ArrayList<String>();
						falicityIds.add(dg.getFacilityId());
						idumap.put(dist_grp, falicityIds);
					} else {
						idumap.get(dist_grp).add(dg.getFacilityId());
					}
				}

			}
			for (Integer disGroupId : uniqueDisGroupList) {
				jsonString = TopologyGroupingService
						.updateDistributionGroupIduDataPlatForm(CustomerId,
								disGroupId + "", idumap.get(disGroupId),
								plsmap.get(disGroupId), customerId,disGroupList);
			}

			

		} catch (Exception e) {
			// CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while Updating distribution group Pls", e);
		}

		return jsonString;

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/DistributionGroupCaNames.htm", method = RequestMethod.GET)
	@ResponseBody
	public String ShowCaDistributionGroup(@RequestParam(value = "site_id[]") List site_id,HttpSession session) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;
		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		
		
		try {
			String User_id = Long.toString(sessionInfo.getUserId());
			int CustomerID =Integer.valueOf(User_id);
			
		int	CustomerId=	CustomerDistributionGroupService.getCustomerIdCustomerDistribution(CustomerID);
			
			
			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.CaAddressDistributionGroup(site_id, CustomerId));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;
	}
	
	
	@RequestMapping(value = "/deleteDistributionGroup.htm", method = RequestMethod.GET)
	@ResponseBody
	public String RemoveDistributionGroup(
			 @RequestParam(value = "d_groupid") String distgroupid,HttpSession session) {


		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		
		try {
                     
			String User_id = Long.toString(sessionInfo.getUserId());
			int CustomerID =Integer.valueOf(User_id);
			
		int	CustomerId=	CustomerDistributionGroupService.getCustomerIdCustomerDistribution(CustomerID);
		
		String CustomerIdRemoving =String.valueOf(CustomerId);
			
			String jsonString = CommonUtil.convertFromEntityToJsonStr(TopologyGroupSqlDao.delete_DistributionGroup_data(distgroupid, CustomerIdRemoving,User_id));



		} catch (Exception e) {
			 CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
			e.printStackTrace();
		}

		return  CommonUtil
				.getJSONErrorMessage(BizConstants.SUCCESS_REGISTRATION);

	}

	@RequestMapping(value = "/sessionInfo.htm", method = RequestMethod.GET)
	@ResponseBody
	public String GetSession(
			 HttpSession session) {
		String jsonString = BizConstants.EMPTY_STRING;

		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		
		try {
                     
			String Costomerid = Long.toString(sessionInfo.getUserId());
			
			 jsonString = CommonUtil.convertFromEntityToJsonStr(Costomerid);



		} catch (Exception e) {
			 CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
			e.printStackTrace();
		}

		return  jsonString;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
