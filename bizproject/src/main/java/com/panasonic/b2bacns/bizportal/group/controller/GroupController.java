/**
 * 
 */
package com.panasonic.b2bacns.bizportal.group.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.group.service.ManageGroupService;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author akansha
 * @author Ravi
 * 
 */
@Controller
@RequestMapping(value = "/group")
public class GroupController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(GroupController.class);

	@Autowired
	private ManageGroupService groupService;

	/**
	 * To get list af all IDUs in group(s)
	 * 
	 * @param groupIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getIDUs.htm", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getIDUList(@RequestParam(value = "id") List<Long> groupIds,
			HttpServletRequest request) {

		String jsonString = BizConstants.EMPTY_STRING;

		try {

			if (groupIds != null && groupIds.size() > 0) {

				jsonString = CommonUtil.convertFromEntityToJsonStr(groupService
						.getIDUList(groupIds));
			} else {

				String customErrorMessage = CommonUtil
						.getJSONErrorMessage(BizConstants.NO_GROUP_SELECTED);

				GenericFailureException gfe = new GenericFailureException(
						customErrorMessage);

				logger.error("Error occured in getIDUList", gfe);
				return CommonUtil
						.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);

			}

		} catch (GenericFailureException gfe) {
			logger.error("Error occured in getIDUList", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (JsonProcessingException e) {
			logger.error("An exception occured in getIDUList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (Exception e) {
			logger.error("An exception occured in getIDUList", e);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		}
		return jsonString;

	}

	/**
	 * To get Group Hierarchy
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getGroupHierarchyTree.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getGroupHierarchyTree(HttpServletRequest request) {

		String getGroupHierarchyTreeJsonStr = BizConstants.EMPTY_STRING;

		try {

			HttpSession session = request.getSession(false);

			SessionInfo sessionInfo = CommonUtil.getSessionInfo(session);

			List<GroupsByCompany> groupVoMapCompanyWise = groupService
					.getParentGroupsByCompanyId(sessionInfo);

			if (groupVoMapCompanyWise != null
					&& !groupVoMapCompanyWise.isEmpty()
					&& groupVoMapCompanyWise.size() > 0) {

				getGroupHierarchyTreeJsonStr = CommonUtil
						.convertFromEntityToJsonStr(groupVoMapCompanyWise);
			} else {

				return CommonUtil
						.getJSONErrorMessage(BizConstants.NO_RECORDS_FOUND);
			}

		} catch (BusinessFailureException gfe) {
			logger.error("Error occured in getGroupHierarchyTree", gfe);
			return CommonUtil
					.getJSONErrorMessage(BizConstants.SOME_ERROR_OCCURRED);
		} catch (HibernateException | InvocationTargetException
				| IllegalAccessException | JsonProcessingException e) {
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

}
