package com.panasonic.b2bacns.bizportal.topology.grouping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.custmanagement.controller.CompanyStructure;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.spf.b2bac.dataaggregation.DataAggregationException;
import com.panasonic.spf.b2bac.dataaggregation.api.DistributionGroupManager;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.GetDistributionGroupsParam;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.GetDistributionGroupsParam.TargetValidPeriod;
import com.panasonic.spf.b2bac.dataaggregation.api.parameter.GetDistributionGroupsResult;

/*
 * 
 * @author pramod
 * 
 */
@Controller
@RequestMapping(value = "/adapter")
public class TopologyGroupingController {

	private static final Logger logger = Logger
			.getLogger(TopologyGroupingController.class);

	@Autowired
	private TopologyGroupingService TopologyGroupingService;

	@Autowired
	private TopologyGroupSqlDao TopologyGroupSqlDao;
	

	@RequestMapping(value = "/cainstallation.htm", method = { RequestMethod.GET })
	public ModelAndView viewUsers(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView();
		
		// Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			if (sessionInfo.getLastSelectedGroupID() != null) {
				// add last selected Group ID from session
				modelAndView.getModelMap().put(
						BizConstants.LAST_SELECTED_GROUP_ATTRIB_NAME,
						sessionInfo.getLastSelectedGroupID().toString());
			}

			modelAndView.setViewName("/cainstallation/cainstallation");

		} catch (Exception e) {
			logger.error("Error: while viewing dashboard ", e);
		}

		return modelAndView;

	}

	@RequestMapping(value = "/CostomerNames.htm", method = RequestMethod.GET)
	@ResponseBody
	public String get_costomer() {

		String jsonString = BizConstants.EMPTY_STRING;
		try {

			List<?> customer_ids = CompanyStructure.get();

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getCostomer(customer_ids));

		} catch (Exception e) {
			logger.error("Error: while viewing cust data ", e);
		}

		return jsonString;
	}

	@RequestMapping(value = "/getsitenames.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getsite_names(
			@RequestParam(value = "costomer_id") String my_id) {

		String jsonString = BizConstants.EMPTY_STRING;
		try {

			List<?> site_ids = CompanyStructure.get_site_ids(my_id);
			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getSite(site_ids));

			System.out.println(jsonString);

		} catch (Exception e) {
			logger.error("Error: while viewing site data ", e);
		}

		return jsonString;

	}
	
	@RequestMapping(value = "/getDistributionGroupSiteNames.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getsite_namesDistributionGroup(@RequestParam(value = "site_id") int site_id,
			@RequestParam(value = "cust_id") int cust_id) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;
		try {
		jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.CaAddress(site_id, cust_id));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}
		return jsonString;
	}
	
	
	
	

	@RequestMapping(value = "/distributionGroup.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getDistributionGroupNames(
			@RequestParam(value = "costomer_id") int costomer_id) {

		String jsonString = BizConstants.EMPTY_STRING;
		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.Distributin_group(costomer_id));

		} catch (Exception e) {
			logger.error("Error: while fetching distribution group  data ", e);
		}

		return jsonString;

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/CaNames.htm", method = RequestMethod.GET)
	@ResponseBody
	public String Show_Mac(@RequestParam(value = "site_id") int site_id,
			@RequestParam(value = "cust_id") int cust_id) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;
		try {
			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.CaAddress(site_id, cust_id));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/DistributionGroupCaNames.htm", method = RequestMethod.GET)
	@ResponseBody
	public String ShowCaDistributionGroup(@RequestParam(value = "site_id[]") List site_id,
			@RequestParam(value = "cust_id") int cust_id) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;
		try {
			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.CaAddressDistributionGroup(site_id, cust_id));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;
	}
	
	
	
	@RequestMapping(value = "/RetrieveTopolgy.htm", method = RequestMethod.POST)
	@ResponseBody
	public String RetrieveTopolgy(
			@RequestParam(value = "adapter_id") String adapter,
			@RequestParam(value = "site_id") String site_id,
			@RequestParam(value = "costomer_id") String costomer_id,
			@RequestParam(value = "cloud_adapter_id") String cloud_adapter_id,
			@RequestParam(value = "check_existing") int check_existing) {

		String jsonAll = BizConstants.EMPTY_STRING;

		try {
			List jsonResponse = new ArrayList<>();
			List jsonIdu = new ArrayList<>();
			List jsonOdu = new ArrayList<>();
			List jsonPls = new ArrayList<>();
			List json_fetch_all = new ArrayList<>();

			if (check_existing == 1) {
				// System.out.println("app");
				json_fetch_all = TopologyGroupingService.RetrieveAllTopolgy(
						site_id, cloud_adapter_id, costomer_id);
				jsonAll = CommonUtil.convertFromEntityToJsonStr(json_fetch_all);

			} else {

				jsonIdu = TopologyGroupingService.RetrieveTopolgyIDU(site_id,
						adapter, costomer_id);
				jsonOdu = TopologyGroupingService.RetrieveTopolgyODU(site_id,
						adapter, costomer_id);
				jsonPls = TopologyGroupingService.RetrieveTopolgyPlS(site_id,
						adapter, costomer_id);

				jsonResponse.add(jsonIdu);
				jsonResponse.add(jsonOdu);
				jsonResponse.add(jsonPls);
				jsonAll = CommonUtil.convertFromEntityToJsonStr(jsonResponse);

			}

		} catch (Exception e) {
			logger.error("Error: while retrive topology data ", e);
			e.printStackTrace();
		}

		return jsonAll;

		

	}

	@RequestMapping(value = "/RetrieveToplogyODU.htm", method = RequestMethod.POST)
	@ResponseBody
	public String RetrieveTopolgyODU(
			@RequestParam(value = "adapter_id") String adapter,
			@RequestParam(value = "site_id") String site_id,
			@RequestParam(value = "costomer_id") String costomer_id) {

		List jsonResponse = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.RetrieveTopolgyODU(site_id, adapter, costomer_id));

			System.out.println(jsonResponse);

		} catch (Exception e) {
			logger.error("Error: while inserting ODU topology data ", e);
		}

		return jsonString;

	}

	@RequestMapping(value = "/RetrieveToplogyPLS.htm", method = RequestMethod.POST)
	@ResponseBody
	public String RetrieveTopolgyPLS(
			@RequestParam(value = "adapter_id") String adapter,
			@RequestParam(value = "site_id") String site_id,
			@RequestParam(value = "costomer_id") String costomer_id) {

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.RetrieveTopolgyPlS(site_id, adapter, costomer_id));

		} catch (Exception e) {
			logger.error("Error: while inserting PLS topology data ", e);
			e.printStackTrace();
		}

		return jsonString;

	}

	@RequestMapping(value = "/InsertRetrieveToplogy.htm", method = RequestMethod.POST)
	@ResponseBody
	public String InsertRetrieveToplogy(
			@RequestParam(value = "topologyOdu") String topologyOdu,
			@RequestParam(value = "topologyIdu") String topologyIdu,
			@RequestParam(value = "topology_pls") String topology_pls,
			@RequestParam(value = "adapter_id") int adapter_id,
			@RequestParam(value = "site_id") String site_id) {

		String jsonString = "{\"status\" : \"false\"}";
		Boolean output = false;

		try {

			OduTopology acConfigRequestODU = (OduTopology) CommonUtil
					.convertFromJsonStrToEntity(OduTopology.class, topologyOdu);
			List<OduData> list_1 = acConfigRequestODU.getTopologyList();

			IduTopology acConfigRequestIDU = (IduTopology) CommonUtil
					.convertFromJsonStrToEntity(IduTopology.class, topologyIdu);
			List<IduData> list_2 = acConfigRequestIDU.getTopologyList();

			PlsData acConfigRequest = (PlsData) CommonUtil
					.convertFromJsonStrToEntity(PlsData.class, topology_pls);
			List<Topology> list_3 = acConfigRequest.getTopologyList();

			output = TopologyGroupingService.InsertRetrieveToplogy(list_1,
					list_2, list_3, adapter_id, site_id);

		} catch (Exception e) {
			logger.error("Error: while inserting topology data ", e);
			e.printStackTrace();
		}

		if (output) {
			jsonString = "{\"status\" : \"true\"}";
		}

		return jsonString;

	}

	@RequestMapping(value = "/UpdateRetrieveToplogy.htm", method = RequestMethod.POST)
	@ResponseBody
	public String InsertRetrieveToplogy(
			@RequestParam(value = "topologyOdu") String topologyOdu,
			@RequestParam(value = "topologyIdu") String topologyIdu,
			@RequestParam(value = "topology_pls") String topology_pls) {

		String jsonString = "{\"status\" : \"false\"}";
		Boolean output = false;

		try {

			OduTopology acConfigRequestODU = (OduTopology) CommonUtil
					.convertFromJsonStrToEntity(OduTopology.class, topologyOdu);
			List<OduData> list_1 = acConfigRequestODU.getTopologyList();

			IduTopology acConfigRequestIDU = (IduTopology) CommonUtil
					.convertFromJsonStrToEntity(IduTopology.class, topologyIdu);
			List<IduData> list_2 = acConfigRequestIDU.getTopologyList();

			PlsData acConfigRequest = (PlsData) CommonUtil
					.convertFromJsonStrToEntity(PlsData.class, topology_pls);
			List<Topology> list_3 = acConfigRequest.getTopologyList();

			output = TopologyGroupingService.UpdateRetrieveToplogy(list_1,
					list_2, list_3);

		} catch (Exception e) {
			logger.error("Error: while updating topology data ", e);
			e.printStackTrace();
		}

		if (output) {
			jsonString = "{\"status\" : \"true\"}";
		}

		return jsonString;

	}



	@RequestMapping(value = "/UpdateRetrieveToplogyIdu.htm", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateTopolgyPLS(
			@RequestParam(value = "topologyIdu") String topology,
			@RequestParam(value = "adapter_id") int adapter_id,
			@RequestParam(value = "site_id") String site_id) {

		String jsonString = BizConstants.EMPTY_STRING;

		try {
			IduTopology acConfigRequestIDU = (IduTopology) CommonUtil
					.convertFromJsonStrToEntity(IduTopology.class, topology);
			List<IduData> list_1 = acConfigRequestIDU.getTopologyList();
			TopologyGroupingService.UpdateRetrieveTopolgyIdu(list_1,
					adapter_id, site_id);
		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
			e.printStackTrace();
		}
		return jsonString;
	}

	@RequestMapping(value = "/UpdateRetrieveToplogyOdu.htm", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateTopolgyODU(
			@RequestParam(value = "topologyOdu") String topology,
			@RequestParam(value = "adapter_id") int adapter_id,
			@RequestParam(value = "site_id") String site_id) {

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			OduTopology acConfigRequestODU = (OduTopology) CommonUtil
					.convertFromJsonStrToEntity(OduTopology.class, topology);
			List<OduData> list_1 = acConfigRequestODU.getTopologyList();

			TopologyGroupingService.UpdateRetrieveTopolgyOdu(list_1,
					adapter_id, site_id);

		} catch (Exception e) {
			logger.error("Error: while viewing ca data ", e);
			e.printStackTrace();
		}

		return jsonString;

	}

	@RequestMapping(value = "/distributionGroup.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDistributionGroup(
			@RequestParam(value = "Customerid") int Customerid) {

		String jsonString = BizConstants.EMPTY_STRING;
		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.Distributin_group(Customerid));

		} catch (Exception e) {
			logger.error("", e);
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
	public String showDistributionGroupDropDown(
			@RequestParam(value = "Cust_id") int cust_id) {

		

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getdDistributionDropDownData(cust_id));

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
			e.printStackTrace();
		}

		return jsonString;

	}

	@RequestMapping(value = "/ShowDistributionGroupIdu.htm", method = RequestMethod.POST)
	@ResponseBody
	public String showDistributionGroupIDu(
			@RequestParam(value = "Ca_id[]") List ca_id) {

		List jsonResponse = new ArrayList<>();
		
		List jsonAdaptors = new ArrayList<>();

		String jsonString = BizConstants.EMPTY_STRING;
		String adapterid = BizConstants.EMPTY_STRING;
		

		try {
			
			 jsonString =  CommonUtil.convertCollectionToString(ca_id);
			
			 
			 System.out.println(jsonString);
			 
			 
			 jsonAdaptors.add(jsonString);
			
			 jsonString = CommonUtil
					.convertFromEntityToJsonStr(TopologyGroupingService
							.getdDistributionDataIdu(jsonAdaptors));

				System.out.println(jsonResponse);
		} catch (Exception e) {
			CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@RequestMapping(value = "/updateDistributionGroupIdu.htm", method = RequestMethod.POST)
	@ResponseBody
	public String updatedistributionGroup(
			@RequestParam(value = "Customerid") int customerid,
			@RequestParam(value = "dgroup") String topology, HttpSession session) {

		String jsonString = BizConstants.EMPTY_STRING;
		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		try {
			String User_id = Long.toString(sessionInfo.getUserId());
			DistributionGroupIdu acConfigRequestIDU = (DistributionGroupIdu) CommonUtil
					.convertFromJsonStrToEntity(DistributionGroupIdu.class,
							topology);
		
		} catch (Exception e) {
			CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			e.printStackTrace();
			logger.error("Error: while viewing ca data ", e);
		}

		return jsonString;

	}

	@SuppressWarnings({ "unchecked", "null" })
	@RequestMapping(value = "/updateDistributionGroupIduPls.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String updatedistributionGroupPLS(HttpServletRequest request,
			//@RequestParam(value = "Customerid") int customerid,@RequestParam(value = "dgroupPlsIDu") String topology,
			HttpSession session) {

		String jsonString = BizConstants.EMPTY_STRING;
		// Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		try {
			String acconfig_request = request.getParameter("json_request");
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			String topology = acConfigRequest.getIdType();
			int customerid = Integer.parseInt(acConfigRequest.getFileType());
			int duplicate = 0;
			String User_id = Long.toString(sessionInfo.getUserId());
			System.out.println(customerid);
			System.out.println(topology);
			DistributionGroupIdu acConfigRequestIDU = (DistributionGroupIdu) CommonUtil
					.convertFromJsonStrToEntity(DistributionGroupIdu.class,
							topology);
			List<DistributionGroupIduData> disGroupList = acConfigRequestIDU
					.getDgroupplsiduList();
			

			Map<Integer, List<String>> plsmap = new HashMap<Integer, List<String>>();

			Map<Integer, List<String>> idumap = new HashMap<Integer, List<String>>();

			List<Integer> uniqueDisGroupList = new ArrayList<Integer>();
			
			  System.out.println("inside nandha's loop");
			  
			for (DistributionGroupIduData dg : disGroupList) {
					int dist_grp = dg.getDist_grp();

					//Added by Nandha to check existing distribution group
			        try {
			            GetDistributionGroupsParam param = new GetDistributionGroupsParam();

			            param.targetValidPeriod = TargetValidPeriod.ALL;

			            param.customerId = Integer.toString(customerid);

			            DistributionGroupManager distributionGroupManager = new DistributionGroupManager();
			            GetDistributionGroupsResult result = distributionGroupManager.getDistributionGroups(param);
			            System.out.println("inside nandha's for loop");
			            for (com.panasonic.spf.b2bac.dataaggregation.api.parameter.GetDistributionGroupsResult.DistributionGroup grp : result.distributionGroups) {
			            	System.out.println(grp.distributionGroupId);
			            	System.out.println(dist_grp);
			                if ( Integer.parseInt(grp.distributionGroupId) == dist_grp)
			                {
			                	
			               
								System.out.println("Distribution group alreay existsdddddddddddddd"+grp);
								//return CommonUtil.getJSONErrorMessage(BizConstants.PLATFORM_ERROR_DUPLICATE);
								duplicate = 1;
			                }
			                
			            }
			            System.out.println("outside nandha's loop");

			        } catch (DataAggregationException daExp) {
						System.out.println("Get Distribution group Failed");
						return CommonUtil.getJSONErrorMessage(BizConstants.PLATFORM_ERROR);
			        } catch (Exception exp) {
			            System.out.println("Get Distribution group Failed");
						return CommonUtil.getJSONErrorMessage(BizConstants.PLATFORM_ERROR);
			        } 

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
			
			
			if(duplicate==0){
				for (Integer disGroupId : uniqueDisGroupList) {
					
					if(!idumap.containsKey(disGroupId) && !plsmap.containsKey(disGroupId)){

						duplicate = 1;
						
					}else if(idumap.containsKey(disGroupId) && !plsmap.containsKey(disGroupId)){
						System.out.println("Flow 1");

					}else if(!idumap.containsKey(disGroupId) && plsmap.get(disGroupId).size() > 0 ){
						duplicate = 1;
						
					}else if(idumap.get(disGroupId).size() > 0 && plsmap.get(disGroupId).size() > 0 ){
					

					}else{
						duplicate = 1;
					}
					
					
				}
				
				if(duplicate==0){
					for (Integer disGroupId : uniqueDisGroupList) {
			
							jsonString = TopologyGroupingService.updateDistributionGroupIduDataPlatForm(customerid,
											disGroupId + "", idumap.get(disGroupId),
											plsmap.get(disGroupId), User_id, disGroupList);
					}
				}else{
					return CommonUtil.getJSONErrorMessage(BizConstants.PLATFORM_ERROR_DUPLICATE2);
				}
				
			}else{
				
				return CommonUtil.getJSONErrorMessage(BizConstants.PLATFORM_ERROR_DUPLICATE);
			}
			

		} catch (Exception e) {
			// CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while Updating distribution group Pls", e);
			e.printStackTrace();
		}

		return jsonString;

	}

	@RequestMapping(value = "/addDistributionGroup.htm", method = RequestMethod.GET)
	@ResponseBody
	public String AddDistributionGroup(
			@RequestParam(value = "CustomerName") int Costomerid,
			@RequestParam(value = "DG_GroupName") String groupName,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "cal") String calMode) {
		String jsonString = BizConstants.EMPTY_STRING;
		try {

			jsonString = TopologyGroupSqlDao.create_new_DistributionGroup(
					groupName, Costomerid, type, calMode);

		} catch (Exception e) {

			CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while Adding distribution group", e);
			e.printStackTrace();
		}

		return jsonString;

	}

	@RequestMapping(value = "/deleteDistributionGroup.htm", method = RequestMethod.POST)
	@ResponseBody
	public String RemoveDistributionGroup(
			@RequestParam(value = "d_groupid") String distgroupid,
			@RequestParam(value = "costomerid") String costomerid,
			HttpSession session) {

		//Modified by ravi
		SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);
		String jsonString ="";
		try {

			String User_id = Long.toString(sessionInfo.getUserId());

			 jsonString = TopologyGroupSqlDao.delete_DistributionGroup_data(distgroupid,costomerid, User_id);

		} catch (Exception e) {
			jsonString=CommonUtil.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Error: while viewing ca data ", e);
			e.printStackTrace();
		}

		return jsonString;

	}

}
