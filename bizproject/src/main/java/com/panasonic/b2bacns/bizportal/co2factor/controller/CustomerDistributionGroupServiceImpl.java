package com.panasonic.b2bacns.bizportal.co2factor.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAOImpl;
import com.panasonic.b2bacns.bizportal.persistence.Company;
import com.panasonic.b2bacns.bizportal.persistence.DistributionGroup;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author Pramod
 *
 */

@Service(value = "CustomerDistributionGroupService")
public class CustomerDistributionGroupServiceImpl implements CustomerDistributionGroupService{

	
	
	private static final Logger logger = Logger.getLogger(CustomerDistributionGroupServiceImpl.class);
	
	private static final StringBuilder DISPLAY_DISTRIBUTION = new StringBuilder(
			"select id,group_name from distribution_group where customer_id='%d'");
	
	private GenericDAO<DistributionGroup> dao;
	@Autowired
	public void setDao(GenericDAO<DistributionGroup> daoToSet) {
		dao = daoToSet;
		dao.setClazz(DistributionGroup.class);
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	private GenericDAO<DistributionGroup> dao1;
	@Autowired
	public void setDao1(GenericDAO<DistributionGroup> daoToSet) {
		dao1 = daoToSet;
		dao1.setClazz(DistributionGroup.class);
	}

	
	protected final Session getCurrentSession() {

		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException hbExp) {
			logger.error(
					"Error occured while obtaining current Hibernate Session"
							+ " to execute Query", hbExp);
		}
		return null;
	}
	
	@Override
	public List<?> executeSQLSelect(String sqlQuery) {
		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);

		logger.debug("SQL query to be executed is : " + query);

		try {
			return query.list();
		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query without Scalar"
							+ " mapping - %s ", query.getQueryString()), hbExp);
		}

		return null;
	}
	
	
	@SuppressWarnings("unused")
	@Override
	public String create_new_DistributionGroup(String DistributionName,
			int CostomerId, String type, String calMode) {
		List<?> resultList = null;
		List<?> resultList2 = null;
		Integer duplictaeName=null;	
			
		
		
		String sql = "SELECT  group_name FROM distribution_group where customer_id='"+CostomerId+"'";
		resultList2= dao.executeSQLQuery(sql);
		String groupname="";
		for(int i = 0; i < resultList2.size(); i++){
			Object  l3 =  resultList2.get(i);
			
			String existingname =String.valueOf(l3);	
			if(DistributionName.equalsIgnoreCase(existingname)){			
				duplictaeName=1;
			}else{
					
			}	
		}	
		
		
		try {
			if (duplictaeName==null) {
				DistributionGroup dst = new DistributionGroup();
				Company c = new Company();
				
				
				Object  l =  CostomerId;
				int i = Integer.valueOf(String.valueOf(l));				
				Long l3 = new Long(i);
				c.setId(l3);			
				dst.setGroupName(DistributionName);
				dst.setCompany(c);;				
				dst.setGroupName(DistributionName);
				
				dst.setType(type);
				dst.setTypeMeasurment(calMode);
				dao1.create(dst);	
				dao1.sessionFlush();
								
			} else {
				return CommonUtil.getJSONErrorMessage(BizConstants.DUPLICATE_REGISTRATION);
			}									
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CommonUtil.getJSONErrorMessage(BizConstants.SUCCESS_REGISTRATION);
	}



	@Override
	public List Distributin_group(int CostomerorId) {
		List<?> resultList = null;
		List<Object[]> DistributionGroupS = new ArrayList<>();
		try {
			
								
			String SQL_QUERY = DISPLAY_DISTRIBUTION.toString();
			SQL_QUERY = String.format(SQL_QUERY, CostomerorId);
			resultList = executeSQLSelect(SQL_QUERY);
			if (resultList != null && resultList.size() > 0) {
				DistributionGroupS
						.addAll((Collection<? extends Object[]>) resultList);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DistributionGroupS;
	}


	@Override
	public List getCustomerSiteIdDistributionGroup(int CostomerorId) {
		List<?> resultList = null;
		
		String  sql1= "SELECT DISTINCT company_id FROM companiesusers where user_id='"+CostomerorId+"'";
		resultList=   dao.executeSQLQuery(sql1);
		Object  l =  resultList.get(0);
		int Customer_ID = Integer.valueOf(String.valueOf(l));	
		String  sql2= 	"select id,name from groups where id ='"+Customer_ID+"'";
		
		
		resultList = executeSQLSelect(sql2);
		return resultList;
	}


	@Override
	public int getCustomerIdCustomerDistribution(int CostomerorId) {
		// TODO Auto-generated method stub
		List<?> resultList = null;
		String  sql1= "SELECT DISTINCT company_id FROM companiesusers where user_id='"+CostomerorId+"'";
		resultList=   dao.executeSQLQuery(sql1);
		Object  l =  resultList.get(0);
		int Customer_ID = Integer.valueOf(String.valueOf(l));	
		
		
		return Customer_ID;
	}



	

	
	
	
	
}
