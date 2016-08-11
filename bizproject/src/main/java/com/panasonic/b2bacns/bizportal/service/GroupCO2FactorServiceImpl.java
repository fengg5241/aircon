package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.persistence.Groupco2factor;

/**
 * 
 * @author Diksha.Rattan
 * 
 */
@Service
public class GroupCO2FactorServiceImpl implements GroupCO2FactorService {

	private GenericDAO<Groupco2factor> dao;

	@Autowired
	public void setDao(GenericDAO<Groupco2factor> daoToSet) {
		dao = daoToSet;
		dao.setClazz(Groupco2factor.class);
	}

	@Override
	public void saveOrUpdateGroupCO2Factor(List<Groupco2factor> groupCO2Factor) {
		dao.batchSaveOrUpdate(groupCO2Factor);
	}
}
