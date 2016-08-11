package com.panasonic.b2bacns.bizportal.acconfig.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.panasonic.b2bacns.bizportal.acconfig.dao.CAService;
import com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService;
import com.panasonic.b2bacns.bizportal.acconfig.service.ManageCAConfigService;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.CAConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamVO;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * This class contains controllers for all devices (CA/IDU/ODU)
 * @author RSI
 * @author jwchan 
 * 
 */

@Controller
@RequestMapping(value = "/acconfig")
public class ACConfigController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(ACConfigController.class);

	@Autowired
	private ManageACConfigService acConfigService;
    
	@Autowired
	private ManageCAConfigService caConfigService;
    
	@Autowired
	private SQLDAO sqlDao;

	
	/*@RequestMapping(value = "/getMetaIDU.htm", method = { RequestMethod.GET })
	@ResponseBody
	public Long test2(HttpServletRequest request) {

		String json = null;

			String acconfig_request = request.getParameter("json_request");
			ACConfigIDUVO acConfigList = null;
			ACConfigRequest acConfigRequest = null;
			try {
				acConfigRequest = (ACConfigRequest) CommonUtil
						.convertFromJsonStrToEntity(ACConfigRequest.class,
								acconfig_request);
                
                logger.debug("Get AC Details request: " + acConfigRequest);
                
                			//name = "[2:{" + name + "}]"; //[2:{B0000000000F02000008}]
	

                
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
			
			    long rs = 0l;
                if(acConfigRequest != null){
                    rs = caConfigService.setMetaIDUByFacilityId(acConfigRequest.getIdType());
                }
                return rs;

            }
	}*/
	
	@RequestMapping(value = "/testPF_PLS.htm", method = { RequestMethod.GET })
	@ResponseBody
	public String testPLS(HttpServletRequest request) {

		String json = null;
		String result = null;
			String acconfig_request = request.getParameter("json_request");
			ACConfigIDUVO acConfigList = null;
			ACConfigRequest acConfigRequest = null;
			try {
				acConfigRequest = (ACConfigRequest) CommonUtil
						.convertFromJsonStrToEntity(ACConfigRequest.class,
								acconfig_request);
                
                logger.debug("Get AC Details request: " + acConfigRequest);
                
                			//name = "[2:{" + name + "}]"; //[2:{B0000000000F02000008}]
                
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException je) {
				// TODO Auto-generated catch block
				je.printStackTrace();
			} catch (IOException ie) {
				// TODO Auto-generated catch block
				ie.printStackTrace();
			} finally{
			
			    List<String> rs = new ArrayList<String>();
                if(acConfigRequest != null){
                	//sitename, adaptername, customerid
                    rs = CAService.getPulseMeterList(acConfigRequest.getIdType(), acConfigRequest.getFileType(), acConfigRequest.getAddCustName());
                }else{}
                
                
                try {
					 result = CommonUtil.convertFromEntityToJsonStr(rs);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return result;
            }
	}
	
	
	/**
	 * Provides the CA Configuration Details for all the CAs for requested
	 * groups.
	 * 
	 * @param request
	 * @return CA configuration Details in JSON format.
	 */
	@RequestMapping(value = "/downloadCADetails2.htm", method = { RequestMethod.GET,
			RequestMethod.POST  })
	@ResponseBody
	public String downloadCADetails(HttpServletRequest request,HttpServletResponse response) {
		String json = null;
		try {
			String acconfig_request = request.getParameter("json_request");
			Set<CAConfigVO> caConfigList = null;
			
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get CA Details request: " + acConfigRequest);
			
			if (!StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
					BizConstants.REPORT_TYPE_CSV)
					&& !StringUtils.equalsIgnoreCase(
							acConfigRequest.getFileType(),
							BizConstants.REPORT_TYPE_EXCEL)) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
				throw new GenericFailureException(customErrorMessage);

			}
			//List<CAConfigVO> result = caConfigService.getCAConfigurationSet();
		    //caConfigList = new HashSet<CAConfigVO>(result);
			caConfigList =  caConfigService.getCAConfigurationSet(acConfigRequest);
			logger.debug("acConfigList-->" + caConfigList);

			String filePath = null;
			
			if (StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
					BizConstants.REPORT_TYPE_EXCEL)) {
				// generate excel file for given list at a specific position
				filePath = caConfigService.generateACDetailsExcelReport(
						caConfigList, acConfigRequest.getAddCustName());
			} else {
				// generate csv file for given list at a specific position
				filePath = caConfigService.generateACDetailsCsvReport(
						caConfigList, acConfigRequest.getAddCustName());
			}
			
			logger.debug("ACDetailsReport is saved at --->" + filePath);

			// check if file is successfully generated
			if (filePath != null && StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}
			
			
			
			
		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in downloadCADetails ",
					e);
		} catch (GenericFailureException gfe) {

			logger.error("Error occured in downloadCADetails", gfe);
			json = gfe.getCustomErrorMsg();

		} catch (Exception e) {
			json = CommonUtil.getJSONErrorMessage("error.application");
			logger.error("Exception occured in downloadCADetails ", e);
		}
		return json;
	}
    
	/*
		String json = null;
		try {
			String jsonRequest = request.getParameter("json_request");

			logger.debug("Request for downloadACDetails --->" + jsonRequest);

			// converting json received from request to a vo
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							jsonRequest);
			if (!StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
					BizConstants.REPORT_TYPE_CSV)
					&& !StringUtils.equalsIgnoreCase(
							acConfigRequest.getFileType(),
							BizConstants.REPORT_TYPE_EXCEL)) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
				throw new GenericFailureException(customErrorMessage);

			}
			// creating list of AC Details
			Set<ACConfigVO> acConfigList = acConfigService.getACConfiguration(
					acConfigRequest).getIduList();

			logger.debug("acConfigList-->" + acConfigList);

			String filePath = null;

			if (StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
					BizConstants.REPORT_TYPE_EXCEL)) {
				// generate excel file for given list at a specific position
				filePath = acConfigService.generateACDetailsExcelReport(
						acConfigList, acConfigRequest.getAddCustName());
			} else {
				// generate csv file for given list at a specific position
				filePath = acConfigService.generateACDetailsCsvReport(
						acConfigList, acConfigRequest.getAddCustName());
			}

			logger.debug("ACDetailsReport is saved at --->" + filePath);

			// check if file is successfully generated
			if (filePath != null && StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}

		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in downloadACDetails ",
					e);
		} catch (GenericFailureException gfe) {

			logger.error("Error occured in downloadACDetails", gfe);
			json = gfe.getCustomErrorMsg();

		} catch (Exception e) {
			json = CommonUtil.getJSONErrorMessage("error.application");
			logger.error("Exception occured in downloadACDetails ", e);
		}
		return json;


	 */
	
	
	
	/**
	 * Provides the CA Configuration Details for all the CAs for requested
	 * groups.
	 * 
	 * @param request
	 * @return CA configuration Details in JSON format.
	 */
	@RequestMapping(value = "/getCADetails2.htm", method = { RequestMethod.GET,
			RequestMethod.POST  })
	@ResponseBody
	public String viewCAConfiguration(HttpServletRequest request) {

		String json = null;
		try {
			String acconfig_request = request.getParameter("json_request");
			List<CAConfigVO> caConfigList = null;
			
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC Details request: " + acConfigRequest);
			/*
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC Details request: " + acConfigRequest);
*/
			/*
			if (acConfigRequest.getId() != null
					&& !acConfigRequest.getId().isEmpty()
					&& acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()) {
				acConfigList = acConfigService
						.getACConfiguration(acConfigRequest);
				if (acConfigList != null && !acConfigList.isEmpty()) {
					json = CommonUtil.convertFromEntityToJsonStr(acConfigList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}
			*/
			
			caConfigList = caConfigService.getCAConfiguration(acConfigRequest);
			if (caConfigList != null && !caConfigList.isEmpty()) {
				json = CommonUtil.convertFromEntityToJsonStr(caConfigList);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in viewCAConfiguration ",
					e);
		} catch (Exception e) {
			json = CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Exception occured in viewCAConfiguration ", e);
		}

		return json;
	}
    
	/**
	 * Provides the CA Configuration Details for all the CAs for requested
	 * groups.
	 * 
	 * @param request
	 * @return CA configuration Details in JSON format.
	 */
	@RequestMapping(value = "/getCADetails3.htm", method = { RequestMethod.GET,
			RequestMethod.POST  })
	@ResponseBody
	public String viewCAConfiguration3(HttpServletRequest request) {

		String json = null;
		try {
			String acconfig_request = request.getParameter("json_request");
			List<CAConfigVO> caConfigList = null;
			
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC Details request: " + acConfigRequest);

			/*
			if (acConfigRequest.getId() != null
					&& !acConfigRequest.getId().isEmpty()
					&& acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()) {
				acConfigList = acConfigService
						.getACConfiguration(acConfigRequest);
				if (acConfigList != null && !acConfigList.isEmpty()) {
					json = CommonUtil.convertFromEntityToJsonStr(acConfigList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}
			*/
			
			caConfigList = caConfigService.getCAConfigurationByMac(acConfigRequest);
			if (caConfigList != null && !caConfigList.isEmpty()) {
				json = CommonUtil.convertFromEntityToJsonStr(caConfigList);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in viewCAConfiguration ",
					e);
		} catch (Exception e) {
			json = CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Exception occured in viewCAConfiguration ", e);
		}

		return json;
	}

	/**
	 * Provides the CA Configuration Details for all the CAs for requested
	 * groups.
	 * 
	 * @param request
	 * @return CA configuration Details in JSON format.
	 */
	@RequestMapping(value = "/getODUDetails3.htm", method = { RequestMethod.GET,
			RequestMethod.POST  })
	@ResponseBody
	public String viewCAConfiguration4(HttpServletRequest request) {

		String json = null;
		try {
			String acconfig_request = request.getParameter("json_request");
			Set<ACConfigODUVO> caConfigList = null;
			
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC Details request: " + acConfigRequest);

			/*
			if (acConfigRequest.getId() != null
					&& !acConfigRequest.getId().isEmpty()
					&& acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()) {
				acConfigList = acConfigService
						.getACConfiguration(acConfigRequest);
				if (acConfigList != null && !acConfigList.isEmpty()) {
					json = CommonUtil.convertFromEntityToJsonStr(acConfigList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}
			*/
			
			caConfigList = caConfigService.getODUConfigurationByMac(acConfigRequest);
			if (caConfigList != null && !caConfigList.isEmpty()) {
				json = CommonUtil.convertFromEntityToJsonStr(caConfigList);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in viewCAConfiguration ",
					e);
		} catch (Exception e) {
			json = CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Exception occured in viewCAConfiguration ", e);
		}

		return json;
	}

	/**
	 * Provides the CA Configuration Details for all the CAs for requested
	 * groups.
	 * 
	 * @param request
	 * @return CA configuration Details in JSON format.
	 */
	@RequestMapping(value = "/getODUDetails4.htm", method = { RequestMethod.GET,
			RequestMethod.POST  })
	@ResponseBody
	public String viewODUConfiguration4(HttpServletRequest request) {

		String json = null;
		try {
			String acconfig_request = request.getParameter("json_request");
			Set<ACConfigODUVO> caConfigList = null;
			
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC Details request: " + acConfigRequest);

			/*
			if (acConfigRequest.getId() != null
					&& !acConfigRequest.getId().isEmpty()
					&& acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()) {
				acConfigList = acConfigService
						.getACConfiguration(acConfigRequest);
				if (acConfigList != null && !acConfigList.isEmpty()) {
					json = CommonUtil.convertFromEntityToJsonStr(acConfigList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}
			*/
			
			caConfigList = caConfigService.getODUConfigurationByMac2(acConfigRequest);
			if (caConfigList != null && !caConfigList.isEmpty()) {
				json = CommonUtil.convertFromEntityToJsonStr(caConfigList);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in viewCAConfiguration ",
					e);
		} catch (Exception e) {
			json = CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Exception occured in viewCAConfiguration ", e);
		}

		return json;
	}
    	
	
	
	/**
	 * Provides the AC Configuration Details for all the IDUs for requested
	 * groups or IDUs ids.
	 * 
	 * @param request
	 * @return AC configuration Details in JSON format.
	 */
	@RequestMapping(value = "/getACDetails.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String viewACConfiguration(HttpServletRequest request) {

		String json_response = BizConstants.EMPTY_STRING;
		try {
			String acconfig_request = request.getParameter("json_request");
			ACConfigIDUVO acConfigList = null;
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC Details request: " + acConfigRequest);

			if (acConfigRequest.getId() != null
					&& !acConfigRequest.getId().isEmpty()
					&& acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()) {
				acConfigList = acConfigService
						.getACConfiguration(acConfigRequest);
				if (acConfigList != null && acConfigList.getIduList() != null
						&& !acConfigList.getIduList().isEmpty()) {
					json_response = CommonUtil
							.convertFromEntityToJsonStr(acConfigList);
				} else {
					json_response = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in viewACConfiguration", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in viewACConfiguration", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

	/**
	 * Provides the AC Configuration Details for all the IDUs for requested
	 * groups and IDUs ids.
	 * 
	 * @param request
	 * @return AC configuration Details in JSON format.
	 */
	@RequestMapping(value = "/getACDetailsCA.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String viewACConfigurationCA(HttpServletRequest request) {

		String json = null;
		try {
			String acconfig_request = request.getParameter("json_request");
			ACConfigIDUVO acConfigList = null;
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC Details request: " + acConfigRequest);

			if (acConfigRequest.getId() != null
					&& !acConfigRequest.getId().isEmpty()
					&& acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()) {
				acConfigList = caConfigService
						.getACConfiguration(acConfigRequest);
				if (acConfigList != null && acConfigList.getIduList() != null
						&& !acConfigList.getIduList().isEmpty()) {
					json = CommonUtil.convertFromEntityToJsonStr(acConfigList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in viewACConfiguration ",
					e);
		} catch (Exception e) {
			json = CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Exception occured in viewACConfiguration ", e);
		}

		return json;
	}
	
	
	
	@RequestMapping(value = "/getACDetailsODU0.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String viewACDetailsODU2(HttpServletRequest request) {

		String json = null;
		try {
			String acconfig_request = request.getParameter("json_request");
			Set<ACConfigODUVO> acODUConfigList = null;
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC ODU Details request: " + acConfigRequest);

			if (acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()
			) {
				acODUConfigList = caConfigService
						.getODUACConfiguration(acConfigRequest);
				if (acODUConfigList != null && !acODUConfigList.isEmpty()) {
					json = CommonUtil
							.convertFromEntityToJsonStr(acODUConfigList);
				} else {
					json = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error(
					"JSON Processing Exception occured in viewACDetailsODU ", e);
		} catch (Exception e) {
			json = CommonUtil
					.getJSONErrorMessage(BizConstants.APPLICATION_ERROR);
			logger.error("Exception occured in viewACDetailsODU ", e);
		}

		return json;
	}

	/**
	 * Download AC Details in excel or csv format
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadACDetails.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String downloadACDetails(HttpServletRequest request,
			HttpServletResponse response) {

		String json_response = BizConstants.EMPTY_STRING;
		try {
			String jsonRequest = request.getParameter("json_request");

			logger.debug("Request for downloadACDetails --->" + jsonRequest);

			// converting json received from request to a vo
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							jsonRequest);
			if (!StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
					BizConstants.REPORT_TYPE_CSV)
					&& !StringUtils.equalsIgnoreCase(
							acConfigRequest.getFileType(),
							BizConstants.REPORT_TYPE_EXCEL)) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
				throw new GenericFailureException(customErrorMessage);

			}
			// creating list of AC Details
			Set<ACConfigVO> acConfigList = acConfigService.getACConfiguration(
					acConfigRequest).getIduList();

			logger.debug("acConfigList-->" + acConfigList);

			String filePath = null;

			if (acConfigList != null && !acConfigList.isEmpty()) {

				if (StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
						BizConstants.REPORT_TYPE_EXCEL)) {
					// generate excel file for given list at a specific position
					filePath = acConfigService.generateACDetailsExcelReport(
							acConfigList, acConfigRequest.getAddCustName());
				} else {
					// generate csv file for given list at a specific position
					filePath = acConfigService.generateACDetailsCsvReport(
							acConfigList, acConfigRequest.getAddCustName());
				}
			}

			logger.debug("ACDetailsReport is saved at --->" + filePath);

			// check if file is successfully generated
			if (filePath != null && StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in downloadACDetails", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in downloadACDetails", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in downloadACDetails", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json_response;
	}
	/**
	 * This method is used to view dash board along with left menu group
	 * selection details.
	 * 
	 * @param groupForm
	 * @param result
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws JsonProcessingException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/viewAcConfig.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView viewAcConfigration(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/acconfig/acconfiguration");

		return modelAndView;

	}
	
	/**
	 * Provides the AC Configuration Details for all the ODUs for requested
	 * groups or ODU Ids.
	 * 
	 * @param request
	 * @return AC configuration Details in JSON format.
	 */
	@RequestMapping(value = "/getACDetailsODU.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String viewACDetailsODU(HttpServletRequest request) {

		String json_response = BizConstants.EMPTY_STRING;

		try {
			String acconfig_request = request.getParameter("json_request");
			Set<ACConfigODUVO> acODUConfigList = null;
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							acconfig_request);
			logger.debug("Get AC ODU Details request: " + acConfigRequest);

			if (acConfigRequest.getId() != null
					&& !acConfigRequest.getId().isEmpty()
					&& acConfigRequest.getIdType() != null
					&& !acConfigRequest.getIdType().isEmpty()
					&& (acConfigRequest.getIdType().equals(
							BizConstants.ID_TYPE_GROUP) || acConfigRequest
							.getIdType().equals(BizConstants.ID_TYPE_OUTDOOR))) {
				acODUConfigList = acConfigService
						.getODUACConfiguration(acConfigRequest);
				if (acODUConfigList != null && !acODUConfigList.isEmpty()) {
					json_response = CommonUtil
							.convertFromEntityToJsonStr(acODUConfigList);
				} else {
					json_response = CommonUtil
							.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
				}
			} else {
				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in getACDetailsODU", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in getACDetailsODU", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return json_response;
	}

	/**
	 * Download AC ODU Details in excel or csv format.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadACDetailsODU.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String downloadACDetailsODU(HttpServletRequest request,
			HttpServletResponse response) {

		String json_response = null;
		try {
			String jsonRequest = request.getParameter("json_request");

			logger.debug("Request for downloadACDetails --->" + jsonRequest);

			// converting json received from request to a vo
			ACConfigRequest acConfigRequest = (ACConfigRequest) CommonUtil
					.convertFromJsonStrToEntity(ACConfigRequest.class,
							jsonRequest);
			if (!StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
					BizConstants.REPORT_TYPE_CSV)
					&& !StringUtils.equalsIgnoreCase(
							acConfigRequest.getFileType(),
							BizConstants.REPORT_TYPE_EXCEL)) {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.REQUEST_FORMAT_ERROR);
				throw new GenericFailureException(customErrorMessage);

			}
			// creating list of AC Details
			Set<ACConfigODUVO> acODUConfigList = acConfigService
					.getODUACConfiguration(acConfigRequest);

			logger.debug("acConfigODUList-->" + acODUConfigList);
			
			//add by shanf
			for (ACConfigODUVO acConfigODUVO : acODUConfigList) {
				String slinkAddress = acConfigODUVO.getSLinkAddress();
				if (slinkAddress != null && !"".equals(slinkAddress)) {
					acConfigODUVO.setSLinkAddress( "=\""+slinkAddress+"\"");
				}
			}
			String filePath = null;

			if (acODUConfigList != null && !acODUConfigList.isEmpty()) {

				if (StringUtils.equalsIgnoreCase(acConfigRequest.getFileType(),
						BizConstants.REPORT_TYPE_EXCEL)) {
					// generate excel file for given list at a specific position
					filePath = acConfigService.generateACDetailsODUExcelReport(
							acODUConfigList, acConfigRequest.getAddCustName());
				} else {
					// generate csv file for given list at a specific position
					filePath = acConfigService.generateACDetailsODUCsvReport(
							acODUConfigList, acConfigRequest.getAddCustName());
				}
			}

			logger.debug("ACDetailsODUReport is saved at --->" + filePath);

			// check if file is successfully generated
			if (filePath != null && StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				json_response = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in downloadACDetailsODU", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (GenericFailureException gfe) {
			logger.error("Error occured in downloadACDetailsODU", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in downloadACDetailsODU", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json_response;
	}

	/**
	 * This Method Provides data for given IndoorUnits or GroupsUnits in json
	 * format
	 * 
	 * @param indoorUnitsIds
	 *            ID of IndoorUnits
	 * 
	 * @param groupUnitsID
	 *            ID of Groups
	 * 
	 * @return ODU Data for given IndoorUnitsID or GroupsUnitsID
	 */
	@RequestMapping(value = "/getODUList.htm", method = RequestMethod.GET)
	@ResponseBody
	//add [] by shanf
	public String getODUList(@RequestParam("id[]") Long[] id,
			@RequestParam String idType) {

		String jsonString = BizConstants.NO_RECORDS_FOUND;

		try {
			if (id.length > 0 && idType != null
					&& idType.equalsIgnoreCase(BizConstants.ID_TYPE_INDOOR)) {

				jsonString = acConfigService.getODUList(id);

				return jsonString;

			} else if (id.length > 0 && idType != null
					&& idType.equalsIgnoreCase(BizConstants.ID_TYPE_GROUP)) {
				jsonString = acConfigService.getGroupODUList(id);

				return jsonString;
			}
		} catch (GenericFailureException e) {
			logger.error("Error occured in getODUList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException | IllegalAccessException
				| HibernateException e) {
			logger.error("Error occured in getODUList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in getODUList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return jsonString;
	}

	/**
	 * This method provides data for provided OutDoorUnitID in json format
	 * 
	 * @param OutDoorUnitID
	 * 
	 * @return ODU params Data for given OutDoorUnitID
	 */
	@RequestMapping(value = "/getODUParams.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getODUParams(@RequestParam("id") Long id,
			@RequestParam String idType, @RequestParam String[] params) {

		String jsonString = BizConstants.NO_RECORDS_FOUND;
		// creating list of ODU params having G1
		List<String> paramWithG1 = new ArrayList<String>();
		List<ODUParamVO> oduParamList = null;
		// creating list of ODU params having VRF values
		List<String> paramsForVRFlist = new ArrayList<String>();
		// creating list of ODU params having GHP values
		List<String> paramsForGHPlist = new ArrayList<String>();
		// if params are null and idtype is VRF then taking params from Biz
		// constant
		if (idType.equalsIgnoreCase("VRF")
				&& (params == null || params.length == 0)) {
			params = BizConstants.PARAMSFORVRF;
		} else if (idType.equalsIgnoreCase("GHP")
				&& (params == null || params.length == 0)) {
			// if params are null and idtype is GHP then taking params from
			// Bizconstant
			params = BizConstants.PARAMSFORGHP;
		}
		// creating params list for various condition
		for (String param : params) {
			if (param.equalsIgnoreCase("G1"))
				paramWithG1.add(param);
			else if (param.startsWith("G"))
				paramsForGHPlist.add(param);
			else if (param.startsWith("V"))
				paramsForVRFlist.add(param);
		}

		try {
			oduParamList = acConfigService.getODUParams(id, paramWithG1,
					paramsForGHPlist, paramsForVRFlist, idType);

			if (oduParamList != null && !oduParamList.isEmpty()) {
				jsonString = CommonUtil
						.convertFromEntityToJsonStr(oduParamList);
			} else {
				jsonString = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}
		} catch (GenericFailureException e) {
			logger.error("Error occured in getODUParams", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (HibernateException e) {
			logger.error("Error occured in getODUParams", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in getODUParams", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return jsonString;
	}

	/**
	 * This method provides data for provided idType in json format
	 * 
	 * @param idType
	 * 
	 * @return ODU params Data for idType
	 */
	@RequestMapping(value = "/getODUParameterList.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getODUParameterList(@RequestParam("idType") String idType) {

		String ODUParameterJson = BizConstants.NO_RECORDS_FOUND;

		try {
			ODUParameterJson = acConfigService.getODUParameterList(idType);

		} catch (GenericFailureException e) {
			logger.error("Error occured in getODUParameterList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JSONException | HibernateException e) {
			logger.error("Error occured in getODUParameterList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in getODUParameterList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return ODUParameterJson;

	}

	/**
	 * Download ODU Params in excel and csv format
	 * 
	 * @param id
	 * @param idType
	 * @param params
	 * @param fileType
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadODUParams.htm", method = { RequestMethod.GET })
	public String downloadODUParams(@RequestParam("id") Long id,
			@RequestParam String idType, @RequestParam String[] params,
			@RequestParam String fileType, HttpServletResponse response) {

		logger.debug("Request for downloadODUParams ---> id:" + id
				+ ", idType:" + idType + ",params" + params.toString());

		String json = BizConstants.EMPTY_STRING;
		try {
			// creating list of ODU params having G1
			List<String> paramWithG1 = new ArrayList<String>();
			// declaring null list of ODU params
			List<ODUParamVO> oduParamList = null;
			// creating list of ODU params having VRF values
			List<String> paramsForVRFlist = new ArrayList<String>();
			// creating list of ODU params having GHP values
			List<String> paramsForGHPlist = new ArrayList<String>();

			// if params are null and idtype is VRF then taking params from Biz
			// constant
			if (idType.equalsIgnoreCase("VRF")
					&& (params == null || params.length == 0)) {
				params = BizConstants.PARAMSFORVRF;
			} else if (idType.equalsIgnoreCase("GHP")
					&& (params == null || params.length == 0)) {
				// if params are null and idtype is GHP then taking params from
				// Bizconstant
				params = BizConstants.PARAMSFORGHP;
			}
			// creating params list for various condition
			for (String param : params) {
				if (param.equalsIgnoreCase("G1"))
					paramWithG1.add(param);
				else if (param.startsWith("G"))
					paramsForGHPlist.add(param);
				else if (param.startsWith("V"))
					paramsForVRFlist.add(param);
			}

			// creating list of ODU params
			oduParamList = acConfigService.getODUParams(id, paramWithG1,
					paramsForGHPlist, paramsForVRFlist, idType);

			logger.debug("oduParamList-->" + oduParamList);

			String filePath = null;

			if (StringUtils.equalsIgnoreCase(fileType,
					BizConstants.REPORT_TYPE_EXCEL)) {
				// generate excel file for given list at a specific position
				filePath = acConfigService
						.generateODUParamsExcelReport(oduParamList);
			} else {
				// generate csv file for given list at a specific position
				filePath = acConfigService
						.generateODUParamsCsvReport(oduParamList);
			}

			logger.debug("ODUParamsReport is saved at --->" + filePath);

			// check if file is successfully generated
			if (filePath != null && StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in downloadODUParams", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("Error occured in downloadODUParams", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}
}
