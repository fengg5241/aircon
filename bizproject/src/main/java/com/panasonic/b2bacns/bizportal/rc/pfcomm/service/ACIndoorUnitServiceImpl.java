package com.panasonic.b2bacns.bizportal.rc.pfcomm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.concurrency.GlobalTaskPool;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.rc.ControlRCVO;
import com.panasonic.b2bacns.bizportal.service.IndoorUnitsService;

/**
 * An implementation of ACIndoorUnitService
 * 
 * @author shobhit.singh
 * 
 */
@Service
public class ACIndoorUnitServiceImpl implements ACIndoorUnitService {

	@Autowired
	private IndoorUnitsService indoorUnitsService;

	@Autowired
	private GlobalTaskPool globalTaskPool;

	@Override
	public ControlRCVO getControlRCVO(List<ACConfigVO> acConfigList)
			throws GenericFailureException {
		return new IDUCurrentStatus().getControlRCVO(acConfigList);
	}

}
