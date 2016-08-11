package com.panasonic.b2bacns.bizportal.rc.pfcomm.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.rc.ControlRCVO;

/**
 * This interface contains method runs as a thread and having logic to create
 * final VO equivalent to required JSON output from RC GET API
 * 
 * @author shobhit.singh
 * 
 */
public interface ACIndoorUnitService {

	/**
	 * This method runs as a thread and having logic to create final VO
	 * equivalent to required JSON output from RC GET API
	 * 
	 * @param iduIDs
	 * @return
	 * @throws GenericFailureException
	 */
	public ControlRCVO getControlRCVO(List<ACConfigVO> acConfigList)
			throws GenericFailureException;
}
