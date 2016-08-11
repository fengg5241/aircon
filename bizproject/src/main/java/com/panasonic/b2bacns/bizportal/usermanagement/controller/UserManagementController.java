package com.panasonic.b2bacns.bizportal.usermanagement.controller;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.common.Status;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Role;
import com.panasonic.b2bacns.bizportal.persistence.User;
import com.panasonic.b2bacns.bizportal.role.form.RoleFormBean;
import com.panasonic.b2bacns.bizportal.role.validator.RoleValidator;
import com.panasonic.b2bacns.bizportal.usermanagement.service.ManageUserManagementService;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.GroupCompanyVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.GroupUserVO;
import com.panasonic.b2bacns.bizportal.usermanagement.vo.UserDetailsVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.util.PasswordEncryptionDecryption;

@Controller
@RequestMapping(value = "/usermanagement")
public class UserManagementController {

	private static final Logger logger = Logger
			.getLogger(UserManagementController.class);

	@Autowired
	private ManageUserManagementService manageUserManagementService;

	@Autowired
	private RoleValidator roleValidator;

	// Added By Seshu
	 @Resource(name = "messageSource") 
	 private ReloadableResourceBundleMessageSource messageSource;
	 

	/*
	 * @Resource(name = "properties") private Properties bizProperties;
	 */

	@Value("${user.management.companyid}")
	private String companyIdFromProperty;

	@RequestMapping(value = "/viewAccount.htm", method = { RequestMethod.GET })
	public ModelAndView viewUsers(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView();

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {

			if (sessionInfo.getLastSelectedGroupID() != null) {
				// add last selected Group ID from session
				modelAndView.getModelMap().put(
						BizConstants.LAST_SELECTED_GROUP_ATTRIB_NAME,
						sessionInfo.getLastSelectedGroupID().toString());
			}

			modelAndView.setViewName("/user/user");

		} catch (Exception e) {
			logger.error("Error: while viewing dashboard ", e);
		}

		return modelAndView;

	}
	
	/**
	 * Returns the generated user id required at the time of registering a new
	 * user.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getGeneratedUserId.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getGeneratedUserId(HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;
		String companyId = request.getParameter("companyId");

		try {
			json = manageUserManagementService.generateUserId(companyId);

		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'CompaniesUsers' for UserId " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException jsonex) {
			logger.error("An Exception occured while converting data to json"
					+ jsonex.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in getGeneratedUserId", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * Returns all the site groups associated with the company
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getSiteGroupByCompanyId.htm", method = {
	 * RequestMethod.GET, RequestMethod.POST })
	 * 
	 * @ResponseBody public String getSiteGroupByCompanyId(HttpServletRequest
	 * request) {
	 * 
	 * String json = BizConstants.NO_RECORDS_FOUND; String companyId =
	 * request.getParameter("companyId");
	 * 
	 * try { if (companyId != null && !companyId.isEmpty()) { json =
	 * manageUserManagementService .getSiteGroupListByCompanyId(companyId); }
	 * 
	 * } catch (JsonProcessingException jsonExp) {
	 * 
	 * logger.error("An Exception occured while creating json" + "Exception :" +
	 * jsonExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (HibernateException sqlExp) {
	 * 
	 * logger.error("An Exception occured while fetching data from" +
	 * " 'Groups' for SiteId and Site Group Name " + "Exception :" +
	 * sqlExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (Exception e) {
	 * 
	 * logger.error("Error occurred in getSiteGroupByCompanyId", e); return
	 * CommonUtil .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); }
	 * return json; }
	 */

	/**
	 * Returns the name of site with which the group is associated
	 * 
	 * @param id
	 * @param idType
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getSiteName.htm", method = { RequestMethod.GET,
	 * RequestMethod.POST })
	 * 
	 * @ResponseBody public String getSiteName(@RequestParam("groupId") Long[]
	 * id,
	 * 
	 * @RequestParam String idType) {
	 * 
	 * String jsonString = BizConstants.NO_RECORDS_FOUND;
	 * 
	 * try { if (id.length > 0 && idType != null &&
	 * idType.equalsIgnoreCase(BizConstants.ID_TYPE_GROUP)) {
	 * 
	 * jsonString = manageUserManagementService.getSiteGroupNames(id); } } catch
	 * (JsonProcessingException jsonExp) {
	 * 
	 * logger.error("An Exception occured while creating json " + "Exception :"
	 * + jsonExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (HibernateException sqlExp) {
	 * 
	 * logger.error("An Exception occured while fetching data from" +
	 * " 'Groups' for SiteId and Site Group Name " + "Exception :" +
	 * sqlExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (Exception e) {
	 * 
	 * logger.error("Error occurred in getSiteGroupNames", e); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } return
	 * jsonString; }
	 */

	/**
	 * Returns logical groups associated with the site
	 * 
	 * @param siteId
	 * @param idType
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getLogicalGroups.htm", method = {
	 * RequestMethod.GET, RequestMethod.POST })
	 * 
	 * @ResponseBody public String getLogicalGroupName(@RequestParam("siteId")
	 * Long[] siteId,
	 * 
	 * @RequestParam String idType) {
	 * 
	 * String jsonString = BizConstants.NO_RECORDS_FOUND;
	 * 
	 * try { if (siteId.length > 0 && idType != null &&
	 * idType.equalsIgnoreCase(BizConstants.ID_TYPE_SITE)) {
	 * 
	 * jsonString = manageUserManagementService .getLogicalGroupName(siteId); }
	 * } catch (HibernateException sqlExp) {
	 * 
	 * logger.error("An Exception occured while fetching data from" +
	 * " 'Groups' for LogicalGroupId and Logical Group Name " + "Exception :" +
	 * sqlExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (JsonProcessingException jsonExp) {
	 * 
	 * logger.error("An Exception occured while creating json " + "Exception :"
	 * + jsonExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (Exception e) {
	 * 
	 * logger.error("Error occurred in getLogicalGroupName", e); return
	 * CommonUtil .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); }
	 * 
	 * return jsonString; }
	 */

	/**
	 * Returns control groups associated with the logical groups
	 * 
	 * @param logicalId
	 * @param idType
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getControlGroups.htm", method = {
	 * RequestMethod.GET, RequestMethod.POST })
	 * 
	 * @ResponseBody public String getControlGroupName(
	 * 
	 * @RequestParam("logicalId") Long[] logicalId,
	 * 
	 * @RequestParam String idType) {
	 * 
	 * String jsonString = BizConstants.NO_RECORDS_FOUND;
	 * 
	 * try {
	 * 
	 * if (logicalId.length > 0 && idType != null &&
	 * idType.equalsIgnoreCase(BizConstants.ID_TYPE_LOGICAL)) {
	 * 
	 * jsonString = manageUserManagementService .getControlGroupName(logicalId);
	 * }
	 * 
	 * } catch (JsonProcessingException jsonExp) {
	 * 
	 * logger.error(
	 * "JSON Processing Exception occured in getControlGroupName ", jsonExp);
	 * return CommonUtil .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
	 * 
	 * } catch (HibernateException sqlExp) {
	 * 
	 * logger.error("An Exception occured while fetching data from" +
	 * " 'Groups' for ControlGroupId and Control Group Name " + "Exception :" +
	 * sqlExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
	 * 
	 * } catch (Exception e) {
	 * 
	 * logger.error("Exception occured in getControlGroupName ", e); return
	 * CommonUtil .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
	 * 
	 * } return jsonString;
	 * 
	 * }
	 */

	/**
	 * This method handles request for getRoleList and takes userId returns
	 * Roles name and Roles Id from "Roles" table.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getRoleList.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getRoleList(HttpServletRequest request) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		String json = BizConstants.NO_RECORDS_FOUND;
		Long userId = sessionInfo.getUserId();

		try {
			if (userId != null && userId > 0) {
				json = manageUserManagementService.getRoleList(userId);
			}

		} catch (JsonProcessingException jsonExp) {

			logger.error("An Exception occured while creating json"
					+ "Exception :" + jsonExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'Groups' for role_id and role_name " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in getRoleList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * This method handles request for getRoleType and takes roleTypeid returns
	 * RolesType id and RolesType name from "RoleType" table.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getRoleType.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getRoleType(HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		Integer roleTypeid = sessionInfo.getRoleTypeMap().keySet() != null ? (Integer) sessionInfo
				.getRoleTypeMap().keySet().iterator().next()
				: null;

		try {
			if (roleTypeid != null) {
				json = manageUserManagementService.getRoleTypeName(roleTypeid);
			}
		} catch (JsonProcessingException jsonExp) {

			logger.error("An Exception occured while creating json"
					+ "Exception :" + jsonExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'Groups' for role_type_id and role_type_name "
					+ "Exception :" + sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in getRoleType", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * This method handles request for getFuncGrpList and takes role_type_id
	 * returns FunctionalGroup id and FunctionalGroup name from
	 * "FunctionalGroup" table.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getFuncGrpList.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getFuncGrpList(
			@RequestParam("role_type_id") Integer role_type_id) {

		String json = BizConstants.NO_RECORDS_FOUND;

		try {
			if (role_type_id != null && role_type_id > 0) {

				json = manageUserManagementService.getFuncGrpList(role_type_id);
			}

		} catch (JsonProcessingException jsonExp) {

			logger.error("An Exception occured while creating json"
					+ "Exception :" + jsonExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'Groups' for functional_id and functional_name "
					+ "Exception :" + sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in getFuncGrpList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * Get permissions assigned to the company from "Permissions" table
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getPermissionDetails.htm", method = {
	 * RequestMethod.GET, RequestMethod.POST })
	 * 
	 * @ResponseBody public String getPermissionDetails(HttpServletRequest
	 * request) {
	 * 
	 * SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
	 * 
	 * String json = BizConstants.NO_RECORDS_FOUND;
	 * 
	 * long companyId = sessionInfo.getCompanyId();
	 * 
	 * try { if (companyId > 0) { json = manageUserManagementService
	 * .getPermissionDetails(companyId); }
	 * 
	 * } catch (JsonProcessingException jsonExp) {
	 * 
	 * logger.error("An Exception occured while creating json" + "Exception :" +
	 * jsonExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (HibernateException sqlExp) {
	 * 
	 * logger.error("An Exception occured while fetching data from" +
	 * " 'Groups' for PermissionsId and PermissionsName " + "Exception :" +
	 * sqlExp.getMessage()); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } catch
	 * (Exception e) {
	 * 
	 * logger.error("Error occurred in getPermissionDetails", e); return
	 * CommonUtil .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); }
	 * return json; }
	 */

	/**
	 * This method handles add new role request. It takes Company_Id from
	 * session, Role_name, Role_Type_id, Functional_Id parameter from request
	 * and return status true/false
	 * 
	 * @param roleFormBean
	 * @param result
	 * @param request
	 * @param redirectAttributes
	 * @param locale
	 * @return
	 */

	@RequestMapping(value = "/addNewRole.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String addNewRole(

	@ModelAttribute("roleFormBean") RoleFormBean roleFormBean,
			final BindingResult result, HttpServletRequest request,
			final RedirectAttributes redirectAttributes, Locale locale) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
		String json = BizConstants.EMPTY_STRING;

		Map<String, String> jsonMap = null;

		Long companyId = sessionInfo.getCompanyId();

		Long userId = sessionInfo.getUserId();

		Role role = null;

		Map<Integer, String> roleTypeMap = (Map<Integer, String>) sessionInfo
				.getRoleTypeMap();

		Integer roleTypeId = roleTypeMap.keySet().iterator().next();

		roleFormBean.setLocale(locale);

		roleValidator.validate(roleFormBean, result);

		if (!result.hasErrors()) {

			role = populateRole(roleFormBean);

			Integer roleTypeIdReq = role.getRoletype_id();

			if (roleTypeIdReq != null) {

				if (roleTypeIdReq >= roleTypeId) {

					role.setCompany_id(companyId != null ? companyId.intValue()
							: null);
					role.setCreatedby(userId != null ? userId.toString() : null);

					Date date = new Date();
					role.setCreationdate(new Timestamp(date.getTime()));

					try {
						if (companyId != null && roleTypeIdReq != null) {

							if (companyId > 0 && roleTypeIdReq > 0) {

								boolean status = manageUserManagementService
										.addNewRole(role,
												roleFormBean.getFunctional_id());

								if (!status) {
									jsonMap = new HashMap<String, String>();
									jsonMap.put("errormessage",
											BizConstants.ROLE_NAME_EXIST);

									return CommonUtil
											.convertFromEntityToJsonStr(jsonMap);
								} else {
									jsonMap = new HashMap<String, String>();
									jsonMap.put("success", "true");

									return CommonUtil
											.convertFromEntityToJsonStr(jsonMap);
								}

							}
						}
					} catch (HibernateException sqlExp) {
						logger.error("An Exception occured while adding new user "
								+ " 'role' table "
								+ "Exception :"
								+ sqlExp.getMessage());
						return CommonUtil
								.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
					} catch (BusinessFailureException bfe) {
						try {
							jsonMap = new HashMap<String, String>();

							jsonMap.put("errormessage", bfe.getMessage());
							return CommonUtil
									.convertFromEntityToJsonStr(jsonMap);
						} catch (JsonProcessingException jpExp) {
							logger.error(
									"An Exception occured while Creating json for addNewRole",
									jpExp);
							return CommonUtil
									.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
						}
					} catch (Exception e) {

						logger.error("Error occurred in addNewRole", e);
						return CommonUtil
								.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
					}

				}
			}
		} else {
			try {
				return CommonUtil
						.convertFromEntityToJsonStr(BizConstants.EMPTY_REQUEST);
			} catch (JsonProcessingException jpExp) {
				logger.error("Error occurred while creating json "
						+ jpExp.getMessage());
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
		}
		return json;
	}

	/**
	 * This method is used to populate DTO from form bean.
	 * 
	 * @param roleFormBean
	 * @return Role
	 */
	private Role populateRole(RoleFormBean roleFormBean) {
		Role role = new Role();
		role.setName(roleFormBean.getRole_name());
		role.setRoletype_id(Integer.parseInt(roleFormBean.getRoletype_id()));

		return role;
	}

	/**
	 * This method handles Edit role request. It takes Company_Id, Role_name,
	 * Role_Type_id, Functional_Id parameter from request and return status
	 * true/false
	 * 
	 * @param roleFormBean
	 * @param result
	 * @param request
	 * @param locale
	 * @param redirectAttributes
	 * @return
	 */

	@RequestMapping(value = "/editRole.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String editRole(

	@ModelAttribute("roleFormBean") RoleFormBean roleFormBean,
			final BindingResult result, HttpServletRequest request,
			Locale locale, final RedirectAttributes redirectAttributes) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		Long companyId = sessionInfo.getCompanyId();

		Long userId = sessionInfo.getUserId();

		String json = BizConstants.EMPTY_STRING;
		Map<String, String> jsonMap = null;

		Role role = null;

		roleFormBean.setLocale(locale);

		roleValidator.validate(roleFormBean, result);

		if (!result.hasErrors()) {

			role = populateRole(roleFormBean);

			role.setCompany_id(companyId != null ? companyId.intValue() : null);
			role.setCreatedby(userId != null ? userId.toString() : null);

			Date date = new Date();
			role.setCreationdate(new Timestamp(date.getTime()));

			Long roleId = 0l;

			if (roleFormBean.getRoleId() != null) {
				roleId = Long.parseLong(roleFormBean.getRoleId());
			} else {

				roleId = 0L;
			}

			role.setId(roleId);

			try {
				if (companyId > 0 && roleId > 0) {

					boolean status = manageUserManagementService.editRole(role,
							roleFormBean.getFunctional_id(), roleId);
					if (!status) {

						jsonMap = new HashMap<String, String>();
						jsonMap.put("errormessage",
								BizConstants.ROLE_NAME_EXIST);

						return CommonUtil.convertFromEntityToJsonStr(jsonMap);
					} else {

						jsonMap = new HashMap<String, String>();
						jsonMap.put("success", "true");
						return CommonUtil.convertFromEntityToJsonStr(jsonMap);

					}

				}
			} catch (HibernateException sqlExp) {

				logger.error("An Exception occured while Editing Role data from"
						+ " 'role' for roleId "
						+ "Exception :"
						+ sqlExp.getMessage());

				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

			} catch (BusinessFailureException bfe) {

				try {
					jsonMap = new HashMap<String, String>();
					jsonMap.put("errormessage", bfe.getMessage());
					logger.error("Exception in Edit Role" + bfe.getMessage());
					return CommonUtil.convertFromEntityToJsonStr(jsonMap);
				} catch (JsonProcessingException jpExp) {

					logger.error("Exception in Edit Role" + jpExp);
                    //added by seshu
					return CommonUtil
							.getJSONErrorMessage(jpExp.getMessage());
				}

			} catch (Exception e) {

				logger.error("Error occurred in editRole", e);

				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
		} else {
			try {
				return CommonUtil
						.convertFromEntityToJsonStr(BizConstants.EMPTY_REQUEST);
			} catch (JsonProcessingException jpExp) {
				logger.error("Error occurred while creating json "
						+ jpExp.getMessage());
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}

		}

		return json;
	}

	/**
	 * This method handles Delete role request. It takes Company_Id from
	 * session, Role_ID parameter from request and return status true/false
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/deleteRole.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String deleteRole(HttpServletRequest request) {

		boolean status = false;

		String roleId = request.getParameter("roleId");

		Long role_Id = 0L;
		try {
			if (roleId != null && !roleId.isEmpty())
				role_Id = Long.parseLong(roleId);
		} catch (NumberFormatException e) {
			role_Id = 0L;
		}

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		try {
			status = manageUserManagementService.deleteRole(
					sessionInfo.getUserId(), role_Id);

		} catch (HibernateException sqlExp) {

			logger.error("An Exception deleting role" + " 'role :' "
					+ role_Id.longValue() + " Exception : "
					+ sqlExp.getMessage());

			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (BusinessFailureException bef) {
            //Modified by seshu.
			logger.error("An Exception deleting role" + " 'role :' "
					+ role_Id.longValue() + " Exception : " + bef.getMessage());
			return CommonUtil
					.getJSONErrorMessage(bef.getMessage());
		} catch (Exception e) {

			logger.error("Error occurred in deleteRole", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		try {

			return CommonUtil.convertFromEntityToJsonStr(new Status(status));

		} catch (JsonProcessingException e) {

			logger.error("Error occurred in deleteRole", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
	}

	/**
	 * Returns complete role history of the given user associated with the given
	 * company
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewRoleLog.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String viewRoleLog(HttpServletRequest request) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		String json = BizConstants.NO_RECORDS_FOUND;
		String companyId = request.getParameter("companyId");

		Long userId = sessionInfo.getUserId();

		try {
			if (companyId != null && !companyId.isEmpty() && userId > 0) {
				json = manageUserManagementService.viewRoleLog(companyId,
						userId);

			} else {

				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (JsonProcessingException jsonExp) {

			logger.error("An Exception occured while creating json"
					+ "Exception :" + jsonExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'CompaniesUsers' for UserId " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in viewRoleLog", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * This method handles simple user list request. It takes Company_Id from
	 * request and if it is not available then from session and returns loginId
	 * list
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getUserIdList.htm", method = {
	 * RequestMethod.GET, RequestMethod.POST })
	 * 
	 * @ResponseBody public String getUserIdList(HttpServletRequest request) {
	 * 
	 * String json = BizConstants.NO_RECORDS_FOUND;
	 * 
	 * String companyId = request.getParameter("companyId");
	 * 
	 * if (StringUtils.isBlank(companyId)) { SessionInfo sessionInfo =
	 * CommonUtil.getSessionInfo(request);
	 * 
	 * companyId = sessionInfo.getCompanyId().toString(); }
	 * 
	 * try { json = manageUserManagementService.getUserIdList(companyId);
	 * 
	 * } catch (HibernateException sqlExp) {
	 * 
	 * logger.error("An Exception occured while fetching data from" +
	 * " 'CompaniesUsers' for UserId " + "Exception :" + sqlExp.getMessage());
	 * return CommonUtil .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
	 * } catch (Exception e) {
	 * 
	 * logger.error("Error occurred in getUserList", e); return CommonUtil
	 * .getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED); } return json; }
	 */

	/**
	 * This method handles Full user list request. It takes Company_Id from
	 * request and if it is not available then from session and returns Full
	 * User list
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserIdListFull.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getUserIdListFull(HttpServletRequest request) {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		String json = BizConstants.NO_RECORDS_FOUND;

		Long userId = sessionInfo.getUserId();

		try {

			if (userId != null && userId > 0) {
				json = manageUserManagementService.getUserIdListFull(userId);

			} else {
				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'Users' for UserId " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException e) {

			logger.error("Error occurred in getUserList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in getUserList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * To get Group Hierarchy. if customer, can view only those groups with
	 * which it is associated
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserGroup.htm ", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getGroupHierarchyTree(HttpServletRequest request) {

		String getGroupHierarchyTreeJsonStr = BizConstants.EMPTY_STRING;

		try {

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

			String type = request.getParameter("type");

			boolean updateUser = false;

			if (type != null && type.equalsIgnoreCase(BizConstants.update))
				updateUser = true;

			List<GroupCompanyVO> groupVoCompanyWise;

			groupVoCompanyWise = manageUserManagementService
					.getGroupsHierarchyByCompanyId(sessionInfo,
							updateUser /*
										 * parameter for update
										 */, 0L);

			getGroupHierarchyTreeJsonStr = CommonUtil
					.convertFromEntityToJsonStr(groupVoCompanyWise == null
							|| groupVoCompanyWise.isEmpty() ? BizConstants.json_string
							: groupVoCompanyWise);

		} catch (JsonProcessingException e) {
			logger.error("Error occured in getGroupHierarchyTree", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception exp) {
			logger.error("Error occured in getGroupHierarchyTree", exp);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getGroupHierarchyTreeJsonStr;

	}

	/**
	 * Returns the UserDetails and the groups associated with the company id
	 * with which the user is associated if its a customer
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserDetail.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getUserDetails(HttpServletRequest request) {

		String getUserHierarchyTreeJsonStr = BizConstants.EMPTY_STRING;

		try {

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

			String type = request.getParameter("type");

			String userId = request.getParameter("userId");

			boolean updateUser = false;

			if (StringUtils.isNumeric(userId)
					&& (StringUtils.isNotBlank(type) && type
							.equalsIgnoreCase(BizConstants.update))) {
				/*
				 * parameter for update
				 */
				updateUser = true;

				List<GroupCompanyVO> groupVoCompanyWise = null;

				UserDetailsVO userDetailsVO = manageUserManagementService
						.retrieveUserDetails(Long.parseLong(userId));

				if (userDetailsVO.getCompId() != 1) {

					groupVoCompanyWise = manageUserManagementService
							.getGroupsHierarchyByCompanyId(sessionInfo,
									updateUser, Long.parseLong(userId));
				}

				userDetailsVO.setGroup_strucutre(groupVoCompanyWise);

				getUserHierarchyTreeJsonStr = CommonUtil
						.convertFromEntityToJsonStr(userDetailsVO);

			}

		} catch (JsonProcessingException e) {
			logger.error("Error occured in getUserDetails", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception exp) {
			logger.error("Error occured in getUserDetails", exp);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getUserHierarchyTreeJsonStr;

	}

	/**
	 * This method is used to register a new user.
	 * 
	 * @param request
	 * @param group_ids
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userRegistration.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String userRegistration(HttpServletRequest request,
			@RequestParam(value = "group_id") Set<Long> group_ids,
			HttpServletResponse response) {

		String filePath = BizConstants.userRegistration;

		String companyId = request.getParameter("companyId");

		String loginId = request.getParameter("loginId");

		String role_Id = request.getParameter("role_Id");

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		Long companyIdSession = sessionInfo.getCompanyId();

		if (StringUtils.isBlank(role_Id)) {
			return CommonUtil.getJSONErrorMessage("role.required");
		}

		if (StringUtils.isBlank(loginId)) {
			return CommonUtil.getJSONErrorMessage("loginid.required");
		}

		if (StringUtils.isBlank(companyId)) {
			companyId = String.valueOf(companyIdSession);
		}

		try {

			filePath = manageUserManagementService.userRegistration(
					sessionInfo, loginId, companyId, group_ids, role_Id);

			if (StringUtils.isNotBlank(filePath)) {
				File file = new File(filePath);
				CommonUtil.writeDownloadableFile(response, file);
			} else {
				filePath = CommonUtil
						.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
			}

		} catch (BusinessFailureException bfexp) {

			logger.error("Error occurred in userRegistration", bfexp);
			//added by seshu
			return CommonUtil
					.getJSONErrorMessage(bfexp.getMessage());
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while Inserting data into"
					+ " 'Users' for UserId " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException e) {

			logger.error("Error occurred in userRegistration", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (NoSuchAlgorithmException e) {

			logger.error("Error occurred in userRegistration", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in userRegistration", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}

		return filePath;
	}

	/**
	 * This method is used to update the records of an existing user
	 * 
	 * @param request
	 * @param newGroupIds
	 * @param prev_group_ids
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value = "/updateUser.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String updateUser(HttpServletRequest request,
			@RequestParam(value = "group_id") Set<Long> newGroupIds,
			@RequestParam(value = "old_group_id") Set<Long> prev_group_ids,
			@RequestParam(value = "userInfoUpdated") boolean userInfo) {

		String json = BizConstants.updateUser;

		String companyId = request.getParameter("companyId");

		String user_id = request.getParameter("userId");

		String role_Id = request.getParameter("role_Id");

		String accountState = request.getParameter("account_state");

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
		//added by seshu
		String companyIdSession = sessionInfo.getCompanyId().toString();

		if (StringUtils.isBlank(user_id)) {
			return CommonUtil.getJSONErrorMessage("userid.required");
		}
        //Modified by seshu.
		if (StringUtils.isBlank(companyId)) {
			companyId = companyIdSession;
		}
		
		//Modified by seshu.
		/*if (companyId.equals(String.valueOf(BizConstants.PANASONIC_COMPANY_ID))) {
			newGroupIds = null;
		}*/

		try {
			json = manageUserManagementService.updateUser(user_id, role_Id,
					accountState, newGroupIds, sessionInfo, companyId,
					prev_group_ids, userInfo);

		} catch (BusinessFailureException e) {
			logger.error("Error occurred in userRegistration", e);
			//added by seshu
			return CommonUtil
					.getJSONErrorMessage(e.getMessage());
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while Inserting data into"
					+ " 'Users' for UserId " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException e) {

			logger.error("Error occurred in userRegistration", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in userRegistration", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * This method is used to activate users email id.
	 * 
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/activateUserEmailId.htm", method = {
	 * RequestMethod.POST, RequestMethod.GET })
	 * 
	 * @ResponseBody public ModelAndView activateUserEmailID(HttpServletRequest
	 * request, HttpServletResponse response, final RedirectAttributes
	 * redirectAttributes, Locale locale) {
	 * 
	 * ModelAndView modelAndView = new ModelAndView(
	 * "redirect:/login/login.htm"); try {
	 * 
	 * ServletContext context = request.getServletContext();
	 * 
	 * String emailId_userID = request.getParameter("emailAddr");
	 * 
	 * String emailToken = request.getParameter("emailtoken");
	 * 
	 * Object emailIdList = context.getAttribute("emailalreadyverified");
	 * 
	 * @SuppressWarnings("unchecked") Set<String> emailalreadyverified =
	 * emailIdList == null ? new HashSet<String>() : (Set<String>) emailIdList;
	 * 
	 * // code refactor for coverity issue for Dereference before null // check
	 * if (!emailalreadyverified.contains(emailId_userID)) { if
	 * (StringUtils.isNotBlank(emailId_userID) &&
	 * StringUtils.isNotBlank(emailToken)) { String update =
	 * manageUserManagementService.activateEmail( emailId_userID, emailToken);
	 * redirectAttributes .addFlashAttribute("errorMessage", update);
	 * emailalreadyverified.add(emailId_userID); } else {
	 * redirectAttributes.addFlashAttribute("errorMessage",
	 * "Validation Failed !"); } } else {
	 * redirectAttributes.addFlashAttribute("errorMessage",
	 * "Email Id Already Verified"); } // Modified by Sim, didn't find any code
	 * referencing this attribute. // So commenting below code. //
	 * context.setAttribute("emailalreadyverified", // emailalreadyverified);
	 * 
	 * } catch (Exception exp) {
	 * logger.error("Error occured in getGroupHierarchyTree", exp); } return
	 * modelAndView; }
	 */

	/**
	 * This method is used to view all logs of the user
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewLog.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getviewLog(HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;

		String userid = request.getParameter("user_id");
		if (userid != null && !userid.isEmpty()) {

			try {
				json = manageUserManagementService.getviewLog(Long
						.valueOf(userid));

			} catch (HibernateException sqlExp) {

				logger.error("An Exception occured while fetching data from"
						+ " 'useraudit' for UserId " + "Exception :"
						+ sqlExp.getMessage());
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (JsonProcessingException e) {

				logger.error("Error occurred in getviewLog", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			} catch (Exception e) {

				logger.error("Error occurred in getviewLog", e);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
			}
		}
		return json;
	}

	/**
	 * This method is used to reset user Password
	 * 
	 * @return
	 */
	@RequestMapping(value = "/resetUser.htm", method = RequestMethod.GET)
	@ResponseBody
	public String resetUser(HttpServletRequest request,
			HttpServletResponse response) {

		String loginId = request.getParameter("loginId");
		String companyIdSession = null;
		String companyIdDB = null;
		Long userIdSession = 0l;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);
		companyIdSession = sessionInfo.getCompanyId().toString();
		userIdSession = sessionInfo.getUserId();

		String companyIdProperty = companyIdFromProperty;
		String filePath = BizConstants.NO_RECORDS_FOUND;

		Long userId = 0l;
		String userName = null;

		try {
			if (loginId != null) {

				companyIdDB = manageUserManagementService
						.getCompanyIdByLoginId(loginId);

				if ((companyIdSession.equals(companyIdProperty))
						|| (companyIdSession.equals(companyIdDB))) {

					String newPassword = null;
					String encryptPassword = null;

					newPassword = manageUserManagementService
							.generatePassword();

					if (newPassword != null && !newPassword.isEmpty()) {

						User user = null;
						encryptPassword = PasswordEncryptionDecryption
								.getEncryptedPassword(newPassword);

						user = manageUserManagementService.resetPassword(
								loginId, encryptPassword, userIdSession);

						if (user != null) {

							userId = user.getId();
							userName = user.getLoginid();
							/*
							 * userFullName =
							 * userName.append(user.getFirstname()
							 * ).append(user.getLastname()).toString();
							 */

							filePath = manageUserManagementService
									.downloadResetUserDetails(userName,
											newPassword);

							if (filePath != null
									&& StringUtils.isNotBlank(filePath)) {

								File file = new File(filePath);
								CommonUtil
										.writeDownloadableFile(response, file);
							} else {
								filePath = CommonUtil
										.getJSONErrorMessage(BizConstants.FILE_NOT_FOUND);
							}
						}
					}
					if (userId > 0) {

						manageUserManagementService.addUserMangementHistory(
								userIdSession, userId);
					}
					return filePath;
				}
			}
		} catch (HibernateException hbexp) {
			logger.error("Error occured in resetUser", hbexp);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception exp) {
			logger.error("Error occured in resetUser", exp);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return filePath;

	}

	/**
	 * This method handles request for getCompanyList and returns Company name
	 * and Company Id from "companies" table.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getcompanylist.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getCompanyList() {

		String json = BizConstants.NO_RECORDS_FOUND;

		try {

			json = manageUserManagementService.getCompanyList();

		} catch (JsonProcessingException jsonExp) {

			logger.error("An Exception occured while creating json"
					+ "Exception :" + jsonExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'companies' " + "Exception :" + sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in getCompanyList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * This method returns the list of users associated with the given company
	 * id and created by the logged in user
	 * 
	 * @param request
	 * @return
	 */
	//Modified by seshu
	@RequestMapping(value = "/getUserlistundercompany.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getUserListUnderCompany(HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		String companyId = request.getParameter("companyids");

		Long userId = sessionInfo.getUserId();
		String loginId = sessionInfo.getLoginId();

		try {
			if (companyId != null && !companyId.isEmpty() && userId > 0) {

				json = manageUserManagementService.getUserListUnderCompany(
						Long.valueOf(companyId), userId, loginId);

			} else {

				json = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'CompaniesUsers' for UserId " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException e) {

			logger.error("Error occurred in getUserList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {

			logger.error("Error occurred in getUserList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	/**
	 * This method returns all the groups associated with all the given company
	 * in a hierarchical manner.
	 * 
	 * @param companyIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getmultiplecompanyUserGroup.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getMultipleCompanyUserGroup(
			@RequestParam("companyids") List<Long> companyIds,
			HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;

		String companyId = request.getParameter("companyids");
		String type = request.getParameter("type");

		try {

			if (companyId != null) {

				boolean updateUser = false;

				if (type != null && type.equalsIgnoreCase(BizConstants.update))
					updateUser = true;

				List<GroupCompanyVO> groupVoCompanyWise = manageUserManagementService
						.getMultipleCompanyUserGroup(companyIds, updateUser);

				json = CommonUtil
						.convertFromEntityToJsonStr(groupVoCompanyWise == null
								|| groupVoCompanyWise.isEmpty() ? BizConstants.json_string
								: groupVoCompanyWise);
			}

		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'CompaniesUsers' for UserId " + "Exception :"
					+ sqlExp.getMessage());
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException e) {

			logger.error("Error occurred in getUserList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;

	}

	//Added by seshu.
	@RequestMapping(value = "/getfullrolelist.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getFullRoleList(HttpServletRequest request) {
		
		String json = BizConstants.NO_RECORDS_FOUND;

		try {

			json = manageUserManagementService.getTotalRoleList();

		} catch (JsonProcessingException jsonExp) {

			logger.error("An Exception occured while creating json"
					+ "Exception :" + jsonExp.getMessage());
		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'companies' " + "Exception :" + sqlExp.getMessage());
		} catch (Exception e) {

			logger.error("Error occurred in getCompanyList", e);
		}
		return json;
	}
	
	
	//Added by seshu(to get all list)
	@RequestMapping(value = "/getAllUserIdListFull.htm", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getAllUserIdListFull(HttpServletRequest request) {

		String json = BizConstants.NO_RECORDS_FOUND;

		String companyId = request.getParameter("companyId");

		if (companyId.isEmpty() || companyId == null) {
			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

			companyId = sessionInfo.getCompanyId().toString();
		}

		try {
			json = manageUserManagementService.getAllUserIdListFull(companyId);

		} catch (HibernateException sqlExp) {

			logger.error("An Exception occured while fetching data from"
					+ " 'Users' for UserId " + "Exception :"
					+ sqlExp.getMessage());
		} catch (Exception e) {

			logger.error("Error occurred in getUserList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return json;
	}

	//Added by seshu.(to keep backup)
	@RequestMapping(value = "/edditRole.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String edditRole(
			@ModelAttribute("roleFormBean") RoleFormBean roleFormBean,
			final BindingResult result, HttpServletRequest request,
			Locale locale, final RedirectAttributes redirectAttributes) throws BusinessFailureException {

		SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

		Long companyId = sessionInfo.getCompanyId();

		String json=BizConstants.EMPTY_STRING;
		Long userId = sessionInfo.getUserId();

		Role role = null;

		roleFormBean.setLocale(locale);

		roleValidator.validate(roleFormBean, result);

		if (!result.hasErrors()) {

			role = populateRole(roleFormBean);

			role.setCompany_id(companyId.intValue());
			role.setCreatedby(userId.toString());

			/*
			 * String userTimeZone = sessionInfo.getUserTimeZone(); Calendar cal
			 * = Calendar.getInstance(); Instant dt_ins = cal.toInstant();
			 * LocalDateTime zoneDateTime = LocalDateTime.ofInstant(dt_ins,
			 * ZoneId.of("Asia/Kolkata"));
			 * 
			 * role.setCreationdate(Timestamp.valueOf(zoneDateTime));
			 */
			Date date = new Date();
			role.setCreationdate(new Timestamp(date.getTime()));

			Long roleId = 0l;

			if (roleFormBean.getRoleId() != null) {
				roleId = Long.parseLong(roleFormBean.getRoleId());
			} else {

				roleId = 0L;
			}

			role.setId(roleId);

			try {
				if (companyId > 0 && roleId > 0) {

					boolean status = manageUserManagementService.edditRole(role,
							roleFormBean.getFunctional_id(), roleId);
					if (!status) {
						result.rejectValue("role_name", messageSource
								.getMessage("role_name.exist", null,
										roleFormBean.getLocale()));
						redirectAttributes.addFlashAttribute("editRoleResult",
								result);
					}else {
						return CommonUtil
								.convertFromEntityToJsonStr(BizConstants.ROLEEDIT);
					}

				}
			} catch (HibernateException sqlExp) {

				logger.error("An Exception occured while Editing Role data from"
						+ " 'role' for roleId "
						+ "Exception :"
						+ sqlExp.getMessage());
				
			} catch (Exception e) {

				logger.error("Error occurred in editRole", e);
			}
		}

		return json;
	}
	
	//Added by seshu.
	@RequestMapping(value = "/getUserAssignedDetail.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getUserAssignedDetails(HttpServletRequest request) {

		String getUserHierarchyTreeJsonStr = BizConstants.EMPTY_STRING;

		try {

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

			String type = request.getParameter("type");

			String userId = request.getParameter("userId");

			boolean updateUser = false;

			if (StringUtils.isNumeric(userId)
					&& (StringUtils.isNotBlank(type) && type
							.equalsIgnoreCase(BizConstants.update))) {

				updateUser = true;

				List<GroupCompanyVO> groupVoCompanyWise = new ArrayList<>();

				groupVoCompanyWise = manageUserManagementService
						.getGroupsHierarchyByCompanyId(sessionInfo,
								updateUser /*
											 * parameter for update
											 */, Long.parseLong(userId));

				UserDetailsVO userDetailsVO = manageUserManagementService
						.retrieveUserDetails(Long.parseLong(userId));

				userDetailsVO.setGroup_strucutre(groupVoCompanyWise);

				getUserHierarchyTreeJsonStr = CommonUtil
						.convertFromEntityToJsonStr(userDetailsVO);

			}

		} catch (Exception exp) {
			logger.error("Error occured in getUserDetails", exp);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return getUserHierarchyTreeJsonStr;

	}
	
	//Added by seshu.(
	@RequestMapping(value = "/getCustomerDetail.htm", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getCustomerUserDetails(HttpServletRequest request) {

		String getUserHierarchyTreeJsonStr = BizConstants.EMPTY_STRING;

		try {

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(request);

			String type = request.getParameter("type");

			String userId = request.getParameter("userId");
			//Added by seshu.
			String companyID = request.getParameter("companyId");

			boolean updateUser = false;

			if (StringUtils.isNumeric(userId)
					&& (StringUtils.isNotBlank(type) && type
							.equalsIgnoreCase(BizConstants.update))) {

				updateUser = true;

				List<GroupCompanyVO> groupVoCompanyWise = new ArrayList<>();
				//Modified by seshu.
				groupVoCompanyWise = manageUserManagementService
						.getCustomerGroupsHierarchyByCompanyId(sessionInfo,
								updateUser /*
											 * parameter for update
											 */, Long.parseLong(userId), Long.parseLong(companyID));

				UserDetailsVO userDetailsVO = manageUserManagementService
						.retrieveUserDetails(Long.parseLong(userId));

				userDetailsVO.setGroupVoCompanyWise(groupVoCompanyWise);

				getUserHierarchyTreeJsonStr = CommonUtil
						.convertFromEntityToJsonStr(userDetailsVO);

			}

		} catch (Exception exp) {
			logger.error("Error occured in getUserDetails", exp);
		}
		return getUserHierarchyTreeJsonStr;

	}

}

