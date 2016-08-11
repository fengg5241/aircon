package com.panasonic.b2bacns.bizportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.WinddirectionMaster;

@Service
public class WindDirectionMasterServiceImpl implements
		WindDirectionMasterService {
	
	private static final String WINDDIRECTIONNAME = "winddirectionname";

	private GenericDAO<WinddirectionMaster> dao;

	
	@Autowired
	public void setDao(GenericDAO<WinddirectionMaster> daoToSet) {
		dao = daoToSet;
		dao.setClazz(WinddirectionMaster.class);
	}

	@Override
	@Transactional
	public WinddirectionMaster getWinddirectionmasterbyId(long windDirectionID) {

		return dao.findByID(windDirectionID);
	}

	@Override
	public WinddirectionMaster getWinddirectionmasterbyName(
			String windDirectionName) {
		
		Map<String, Object> propertiesMap = new HashMap<String, Object>();
		propertiesMap.put(WINDDIRECTIONNAME, windDirectionName);
		
		List<WinddirectionMaster> winddirectionMasters = dao.findAllByProperties(propertiesMap);
		
		WinddirectionMaster winddirectionMaster =null;
		
		if(winddirectionMasters!=null && winddirectionMasters.size()>0){
			winddirectionMaster = winddirectionMasters.get(0);
		}
		return winddirectionMaster;
	}

}
