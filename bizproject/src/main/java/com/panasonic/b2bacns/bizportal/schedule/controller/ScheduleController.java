package com.panasonic.b2bacns.bizportal.schedule.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * This controller handles request for schedule data
 * 
 * @author Ravi
 * 
 */
@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(ScheduleController.class);


	/**
	 * This method is used to display Schedule page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewSchedule.htm", method = RequestMethod.GET)
	public ModelAndView getVisualization(HttpServletRequest request,
			ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();

		try {
			modelAndView.setViewName(BizConstants.SCHEDULE_VIEW);;
		} catch (Exception e) {
			logger.error("Error: while viewing schedule ", e);
		}

		return modelAndView;
	}

}
