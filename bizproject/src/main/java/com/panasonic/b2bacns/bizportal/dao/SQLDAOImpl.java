/**
 * 
 */
package com.panasonic.b2bacns.bizportal.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.swing.plaf.synth.SynthStyle;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.panasonic.b2bacns.bizportal.acconfig.dao.CAConfigDAO;
import com.panasonic.b2bacns.bizportal.acconfig.service.ManageCAConfigService;
import com.panasonic.b2bacns.bizportal.dashboard.vo.GroupLevelVO;
import com.panasonic.b2bacns.bizportal.exception.GenericFailureException;
import com.panasonic.b2bacns.bizportal.group.vo.IDUListVO;
import com.panasonic.b2bacns.bizportal.installation.controller.Ca_DataService;
import com.panasonic.b2bacns.bizportal.persistence.Adapter;
import com.panasonic.b2bacns.bizportal.persistence.Companiesuser;
import com.panasonic.b2bacns.bizportal.persistence.DistributionGroup;
import com.panasonic.b2bacns.bizportal.persistence.Group;
import com.panasonic.b2bacns.bizportal.persistence.Indoorunit;
import com.panasonic.b2bacns.bizportal.persistence.Metaindoorunit;
import com.panasonic.b2bacns.bizportal.persistence.Metaoutdoorunit;
import com.panasonic.b2bacns.bizportal.persistence.Outdoorunit;
import com.panasonic.b2bacns.bizportal.persistence.Pulsemeter;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupIduData;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupIduVo;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupPlsData;
import com.panasonic.b2bacns.bizportal.topology.grouping.DistributionGroupPlsVo;
import com.panasonic.b2bacns.bizportal.topology.grouping.IduData;
import com.panasonic.b2bacns.bizportal.topology.grouping.OduData;
import com.panasonic.b2bacns.bizportal.topology.grouping.Topology;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.bizportal.persistence.Refrigerantmaster;
import com.panasonic.b2bacns.bizportal.persistence.User;

/**
 * This class allows to add methods and allows to add SQL queries specific to
 * modules which requires execution SQL queries to achieve the functionality.
 * This provides Hibernate interfaces to execute those SQL queries.
 * 
 * @author akansha
 * 
 */
@Repository
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class SQLDAOImpl implements SQLDAO {

	/** Logger instance. **/
	private static final Logger logger = Logger.getLogger(SQLDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ManageCAConfigService caConfigService;

	@Autowired
	private CAConfigDAO caConfigDAO;

	@SuppressWarnings("rawtypes")
	// @Autowired
	private GenericDaoImpl dao;

	// @PersistenceContext(unitName = "Outdoorunit")
	// private EntityManager em;

	/* added by Ravi */
	private static final String PATH_NAME = "path_name";
	private static final String COMPANY_NAME = "companyname";

	private static final String INDOORUNITS = "indoorunits";
	private static final String OUTDOORUNITS = "outdoorunits";
	private static final String INDOORUNIT_ID = "indoorunit_id";
	private static final String OUTDOORUNIT_ID = "outdoorunit_id";
	private static final String INDOORUNITID = "indoorunitid";
	private static final String INDOORUNITSLOG = "indoorunitslog";
	private static final String OUTDOORUNITSLOG = "outdoorunitslog";

	private static final String IDULOG_SETPOINTTEMPERATURE = "setpointtemperature";
	private static final String FANSPEED_MASTER_FANSPEED = "fanspeed";
	private static final String MODEMASTER_MODENAME = "acmode";
	private static final String WINDDIRECTION_MASTER_NAME = "flapmode";

	private static final String ID = "id";
	private static final String SITE_ID = "siteid";
	private static final String GROUP_ID = "group_id";
	private static final String MAP_NAME = "name";

	private static final StringBuilder SQL_GET_IDU_ODU_INFO_POSTGRESSQL = new StringBuilder(
			"Select count(distinct(%s)) as unitcount, count(distinct(log.%s)) as  inactivecount, ")
			.append("count(distinct(nl.%s)) as alarmcount from usp_getindoorunits_supplygroupname('%s') fn ")
			.append("inner join indoorunits idu on idu.id = fn.indoorunitid ")
			.append("Left Outer Join %s log on idu.%s  = log.%s and log.status =  '%s' ")
			.append("Left Outer Join  notificationlog nl on idu.%s  = nl.%s and nl.status =  '%s'");

	private static final StringBuilder SQL_GET_INACTIVE_IU_OU = new StringBuilder(
			"select id from %s where status='%s' and id in (%s)");

	public static final StringBuilder SQL_GET_RC_CONTROL_STATUS_IDU = new StringBuilder(
			"SELECT indoorunitslog.ison, indoorunitslog.currenttemp, modemaster.modename acmode, fanspeed_master.fanspeedname fanspeed, ")
			.append("winddirection_master.winddirectionname flapmode ")
			.append("FROM indoorunitslog, modemaster, fanspeed_master, winddirection_master ")
			.append("where indoorunit_id = %d and ")
			.append("indoorunitslog.currentmode=modemaster.id and indoorunitslog.fanspeed=fanspeed_master.id and ")
			.append("indoorunitslog.flapmode=winddirection_master.id ");

	public static final StringBuilder SQL_GET_PARAMETER = new StringBuilder(
			"SELECT %s,creationdate FROM %s WHERE outdoorunit_id=%s");

	private static final String GHP_PARAMETER_STATISTICS = "ghpparameter_statistics";
	private static final String VRF_PARAMETER_STATISTICS = "vrfparameter_statistics";

	private static final StringBuilder SQL_PART1_SET_CONTROL_STATUS_IDUS_POSTGRESSQL = new StringBuilder(
			"update indoorunitslog SET %s ");

	private static final StringBuilder SQL_PART2_SET_CONTROL_STATUS_IDUS_POSTGRESSQL = new StringBuilder(
			" from (select case when parent_id is not null then parent_id else id end id  from indoorunits where id in %s) ")
			.append("indoorunits where indoorunitslog.indoorunit_id=indoorunits.id and indoorunitslog.status = 'active' ");

	private static final StringBuilder SQL_DEVICE_POWERSTATUS_CHECK = new StringBuilder(
			" and indoorunitslog.powerstatus = 1");

	/* modified by Ravi */
	private static final StringBuilder SQL_GET_IDULIST_POSTGRESSQL = new StringBuilder(
			"select distinct idu.id, idu.name, g.path_name, c.name as companyname ")
			.append("from usp_getindoorunits_supplygroupname('%s') ")
			.append("fn inner join indoorunits idu ")
			.append("on fn.indoorunitid = idu.id ")
			.append("Left join groups g on  fn.groupid = g.id ")
			.append("left join companiesusers cu on cu.group_id = g.id ")
			.append("left join companies c on c.id = cu.company_id order by id");

	private static final StringBuffer SQL_GET_GROUP_LEVEL = new StringBuffer(
			"select distinct  gl.id,gl.type_level_name as name from groups g ")
			.append("left outer join group_level gl on gl.id >= g.group_level_id ")
			.append("left outer join companiesusers cu on cu.group_id =  g.id ")
			.append("where cu.user_id =  :userID and cu.company_id = :companyID  order by gl.id ");

	private static final String USER_ID = "userID";
	private static final String COMPANY_ID = "companyID";

	private static final StringBuilder SQ_ADD_CUST_DATA = new StringBuilder(
			"INSERT INTO companies (createdby,creationdate,deploymentid,imagepath,name,updatedate,updatedby,isdel)VALUES ('TEST','%s','2','','%s','2015-10-22 00:00:00','TEST',TRUE)");

	private static final StringBuilder SQ_ADD_CA_DATA = new StringBuilder(
			"INSERT INTO adapters (mac_address,company_id,model_id,createdby,creationdate)VALUES ('%s',%d,'%s','%s',CURRENT_TIMESTAMP)");

	private static final StringBuilder SQ_DELETE_ADAPTER = new StringBuilder(
			"DELETE FROM adapters where id='%d'");

	private static final StringBuilder SQ_ADD_Facility = new StringBuilder(
			"Update adapters set oid='%s' where id='%s'");

	private static final StringBuilder SQ_ASSOCIATE_CA_DATA = new StringBuilder(
			"Update adapters set siteid=:site_id,timezone=:Timezone,latitude=:Latitude,longitude=:Longitude,enabled_date=:installed_at,state=:status,name=:ca_name where mac_address=:ca_mac_address");
	

	private static final StringBuilder SQ_GET_DUP_CA = new StringBuilder(
			"select id from adapters where siteid='%s' and company_id='%s' and name='%s'");

	/*private static final StringBuilder SQ_GET_CA_DATA = new StringBuilder(
			"select a.address,a.latitude,a.longitude,a.enabled_date,a.state,a.model_id,a.name as adapter_name,g.name as group_site_name,a.fw_version,t.timezone from adapters a ")
			.append("join groups g on g.uniqueid = a.siteid")
			.append("join timezonemaster t on t.id = a.timezone where a.mac_address=:ca_mac");*/
	
	private static final StringBuilder SQ_GET_CA_DATA = new StringBuilder(
			"select a.address,a.latitude,a.longitude,a.enabled_date,a.state,a.model_id,a.name as adapter_name,g.name as group_site_name,a.fw_version,t.timezone from adapters a "
			+ "join groups g on g.uniqueid = a.siteid join timezonemaster t on t.id = a.timezone where a.mac_address=:ca_mac");

	private static final StringBuilder SQ_FETCH_CA_MAC = new StringBuilder(
			"select id,mac_address,model_id from adapters where siteid is NOT NULL AND company_id= :cust_id AND mac_address IS NOT NULL");

	/*08007B92002D
	 * private static final StringBuilder SQ_FETCH_CA_MAC_REG = new
	 * StringBuilder(
	 * "select id,mac_address,model_id from adapters where siteid is NULL AND company_id='%s' AND mac_address IS NOT NULL"
	 * );
	 */

	private static final StringBuilder SQ_FETCH_CA_MAC_REG = new StringBuilder(
			"select id,mac_address,model_id from adapters where siteid is NULL AND company_id= :cust_id AND mac_address IS NOT NULL");

	private static final StringBuilder SQ_GET_CUST_DATA = new StringBuilder(
			"select id,name from companies");

	private static final StringBuilder SQ_FETCH_SITE_CUSTS = new StringBuilder(
			"select id,name from groups where id in(%s)");
	
	private static final StringBuilder SQ_FETCH_ROLE_ID = new StringBuilder(
			"select roles_id from users where id='%d'");
	
	private static final StringBuilder SQ_FETCH_ROLETYPE_ID = new StringBuilder(
			"select roletype_id from roles where id='%d'");
	
	

	private static final StringBuilder SQ_FETCH_SITEID = new StringBuilder(
			"select id, name from groups where groupcategoryid=2 and id not in  (SELECT group_id FROM companiesusers)");

	/*
	 * private static final StringBuilder SQ_FETCH_SITEID = new StringBuilder(
	 * "select id, name from groups where groupcategoryid=2 and id not in  (SELECT group_id FROM companiesusers WHERE  company_id = %s  )"
	 * );
	 */

	private static final StringBuilder SQ_FETCH_SITEID_CUST = new StringBuilder(
			"select g.id,g.name from companiesusers cu ")
			.append("inner join groups g on g.id = cu.group_id where cu.company_id=%s");

	private static final StringBuilder SQ_FETCH_CA_MODEL = new StringBuilder(
			"select model_id from model_mst where model_ctg_cd = 'CA'");

	private static final StringBuilder SQ_FETCH_TIME_ZONE = new StringBuilder(
			"select id,timezone from timezonemaster");

	private static final StringBuilder SQ_CHECK_MAC = new StringBuilder(
			"select id,mac_address from adapters where mac_address='%s'");

	private static final StringBuilder SQ_FETCH_CA_CUST = new StringBuilder(
			"select DISTINCT ad.company_id,cm.name from adapters ad ")
			.append("inner join companies cm on cm.id = ad.company_id where ad.mac_address IS NOT NULL");

	private static final StringBuilder SQ_REG_CUST_SITE = new StringBuilder(
			"insert into companiesusers(company_id,user_id,group_id)VALUES ('%s','%s',%s)");

	// topology saving and distribution group quries(pramod)

	private static final StringBuilder COSTOMER_DATA = new StringBuilder(
			"select id,name from companies");

	private static final StringBuilder SITE_DATA = new StringBuilder(
			"select id,name from groups where company_id= %d and groupcategoryid=2");

	private static final StringBuilder SQ_GET_CA_DATA_TOPOLOGY = new StringBuilder(
			"select oid,name,id from adapters where siteid='%d' and company_id='%d'");

	private static final StringBuilder SQ_GET_CA_DATA_DISTRIBUTION_GROUP = new StringBuilder(
			"select oid,name,id from adapters where siteid in('%s') and company_id='%d'");
	private static final StringBuilder SQ_GET_CA_DATA_DISTRIBUTION_GROUP_TWO_SITES = new StringBuilder(
			"select oid,name,id from adapters where siteid in(%s) and company_id='%d'");

	private static final StringBuilder SQ_GET_CA_DATA_TOPOLOGY_ID = new StringBuilder(
			"select id from adapters where oid='%s'");

	private static final StringBuilder DISTRIBUTION_GROUP_IDU_SITE1 = new StringBuilder(
			"select name,apm.slink,apm.adapterName,apm.facilty_id,apm.devicename  from groups as g,  (select pm.id as meterId,pm.slinkaddress as slink,a.siteid as siteId,a.oid as facilty_id, a.name as adapterName,pm.name as devicename  from    (select * from  indoorunits where id in(%s)) as pm,   adapters a   where a.id= pm.adapters_id ) as apm where g.uniqueid = apm.siteId ");

	private static final StringBuilder DISTRIBUTION_GROUP_PLS_SITE = new StringBuilder(
			"select name,apm.portnumber,apm.adapterName,apm.metername  from groups as g,(select pm.id as meterId,pm.port_number as portnumber ,a.siteid as siteId, a.name as adapterName,pm.meter_name as metername from (select * from  pulse_meter where id in(%s)) as pm,  adapters a   where a.id= pm.adapters_id ) as apm where g.uniqueid = apm.siteId ");

	private static final StringBuilder UPDATE_RETRIEVE_TOPOLGY_PLS = new StringBuilder(
			"update pulse_meter set meter_name='%s' where oid='%s'");
	private static final StringBuilder SQl_FETCH_DISTRIBUTION_GROUP_DATA_VRF = new StringBuilder(
			"select id,group_name from distribution_group where type='VRF' and customer_id=%d");
	private static final StringBuilder SQl_FETCH_DISTRIBUTION_GROUP_DATA_GHP = new StringBuilder(
			"select id,group_name from distribution_group where type='GHP' and customer_id=%d");

	private static final StringBuilder SQl_FETCH_DISTRIBUTION_GROUP_DATA = new StringBuilder(
			"select id,group_name,type from distribution_group");

	private static final StringBuilder UPDATE_RETRIEVE_TOPOLGY_IDU = new StringBuilder(
			"update indoorunits set name='%s' where oid='%s'");

	private static final StringBuilder UPDATE_RETRIEVE_TOPOLGY_ODU = new StringBuilder(
			"update outdoorunits set device_name='%s' where oid='%s'");

	private static final StringBuilder DISTRIBUTION_GROUP = new StringBuilder(
			"select id,group_name from distribution_group where customer_id=%d");

	private static final StringBuilder DISTRIBUTION_GROUP_DATA1 = new StringBuilder(
			"select id from pulse_meter where  pulse_meter.distribution_group_id=%d");

	private static final StringBuilder DISTRIBUTION_GROUP_DATA2 = new StringBuilder(
			"select id from indoorunits where  distribution_group_id=%d");

	private static final StringBuilder SQl_FETCH_DISTRIBUTION_GROUP_PLS = new StringBuilder(
			"select p.id,p.oid,p.distribution_group_id,p.port_number,p.meter_name,p.meter_type from pulse_meter p where  p.adapters_id in(%s)");

	private static final StringBuilder SQl_FETCH_DISTRIBUTION_GROUP_IDU = new StringBuilder(
			"select i.id,i.slinkaddress,i.name,i.type,i.distribution_group_id,i.oid from indoorunits i where  i.adapters_id in(%s)");

	private final StringBuilder SQl_FETCH_DISTRIBUTION_GROUP_IDU_CREATED = new StringBuilder(
			"select i.id,i.slinkaddress,i.name,i.type,i.oid,d.id dGroupId,d.group_name from indoorunits i,distribution_group d where i.adapters_id in(%s) and i.distribution_group_id=d.id");

	private final StringBuilder SQl_FETCH_DISTRIBUTION_GROUP_PLS_CREATED = new StringBuilder(
			"select p.id,p.oid,p.distribution_group_id,p.port_number,p.meter_name,p.meter_type,d.id dGroupId,d.group_name from pulse_meter p ,distribution_group d where  p.adapters_id in(%s) and p.distribution_group_id=d.id");

	private static final StringBuilder UPDATE_DISTRIBUTION_GROUP_IDU = new StringBuilder(
			"update indoorunits  set  distribution_group_id = '%d' where  id='%d'");

	private static final StringBuilder DISTRIBUTION_GROUP_TYPE = new StringBuilder(
			"select type_measurment   from distribution_group where  id='%d'");

	private static final StringBuilder FACILTYID_INDOORUNIT_DISTRIBUTION_GROUP = new StringBuilder(
			"select oid from indoorunits where id ='%d'");

	private static final StringBuilder UPDATE_DISTRIBUTION_GROUP_PLS = new StringBuilder(
			"update pulse_meter  set  distribution_group_id = '%d' where  port_number='%d'");

	private static final StringBuilder FACILTYID_PULSE_METER_DISTRIBUTION_GROUP = new StringBuilder(
			"select oid from pulse_meter where id ='%d'");
	private static final StringBuilder DISTRIBUTION_GROUP_ID = new StringBuilder(
			"select id from distribution_group");

	private static final StringBuilder DISTRIBUTION_GROUP_ID_CAL_CODE = new StringBuilder(
			"select type_measurment from distribution_group where id='%d'");

	private static final StringBuilder DELETE_DISTRIBUTION_GROUP = new StringBuilder(
			"delete from   distribution_group where id = (%d)");

	private static final StringBuilder INSERT_DISTRIBUTION_GROUP = new StringBuilder(
			"insert into distribution_group(group_name,customer_id,type,type_measurment) values('%s','%s','%s','%s')");

	private static final StringBuilder DISPLAY_DISTRIBUTION = new StringBuilder(
			"select id,group_name from distribution_group where customer_id='%d'");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeSQLSelect(java.lang
	 * .String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeSQLSelect(java.lang
	 * .String, java.util.Map)
	 */
	@Override
	public List<?> executeSQLSelect(String sqlQuery,
			Map<String, Object> parameter) {

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);
		for (Map.Entry<String, Object> entry : parameter.entrySet()) {

			if (entry.getValue() instanceof ArrayList) {
				query.setParameterList(entry.getKey(),
						(List<?>) entry.getValue());

			} else {

				query.setParameter(entry.getKey(), entry.getValue());
			}

		}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeSQLUpdateQuery(java
	 * .lang.String)
	 */
	@Override
	public int executeSQLUpdateQuery(String sqlQuery) throws HibernateException {

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);

		logger.debug("SQL query to be executed is : " + query);

		int noOfRecordsUpdatedOrDeleted;

		try {
			noOfRecordsUpdatedOrDeleted = query.executeUpdate();
		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query without Scalar"
							+ " mapping - %s ", query.getQueryString()), hbExp);
			throw hbExp;
		}
		return noOfRecordsUpdatedOrDeleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeSQLUpdateQuery(java
	 * .lang.String, java.util.Map)
	 */
	@Override
	public int executeSQLUpdateQuery(String sqlQuery,
			Map<String, Object> parameter) throws HibernateException {

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);

		for (Map.Entry<String, Object> entry : parameter.entrySet()) {
			if (entry.getValue() instanceof ArrayList) {
				query.setParameterList(entry.getKey(),
						(ArrayList<?>) entry.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}

		}

		logger.debug("SQL query to be executed is : " + query);

		int noOfRecordsUpdatedOrDeleted;

		try {
			noOfRecordsUpdatedOrDeleted = query.executeUpdate();
		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query without Scalar"
							+ " mapping - %s ", query.getQueryString()), hbExp);
			throw hbExp;
		}
		return noOfRecordsUpdatedOrDeleted;
	}

	/*
	 * Auther Srinivas
	 */

	@Override
	public List<?> executeInsertSQLSelect(String sqlQuery) {

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);
		SQLQuery query1 = getCurrentSession()
				.createSQLQuery("select lastval()");

		logger.debug("SQL query to be executed is : " + query);

		List<?> resultList = null;
		try {

			int some_data = query.executeUpdate();

			// System.out.println(query1.list());
			resultList = query1.list();
			// return some_data;

		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query without Scalar"
							+ " mapping - %s ", query.getQueryString()), hbExp);
		}

		return resultList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeSQLSelect(java.lang
	 * .String, java.util.LinkedHashMap)
	 */
	@Override
	public List<?> executeSQLSelect(String sqlQuery,
			LinkedHashMap<String, Type> scalarMapping) {

		List<?> resultList = null;

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);

		Iterator<Entry<String, Type>> itr = scalarMapping.entrySet().iterator();
		Entry<String, Type> columnName = null;
		while (itr.hasNext()) {
			columnName = itr.next();

			query.addScalar(columnName.getKey(), columnName.getValue());

		}

		logger.debug("SQL query to be executed with Scalar mapping is : "
				+ query);

		try {
			resultList = query.list();
		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query - %s ",
					query.getQueryString()), hbExp);
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeSQLSelect(java.lang
	 * .String, java.util.LinkedHashMap, java.util.Map)
	 */
	@Override
	public List<?> executeSQLSelect(String sqlQuery,
			LinkedHashMap<String, Type> scalarMapping,
			Map<String, Object> parameter) {

		List<?> resultList = null;

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);

		Iterator<Entry<String, Type>> itr = scalarMapping.entrySet().iterator();
		Entry<String, Type> columnName = null;
		while (itr.hasNext()) {
			columnName = itr.next();

			query.addScalar(columnName.getKey(), columnName.getValue());

		}

		for (Map.Entry<String, Object> entry : parameter.entrySet()) {
			if (entry.getValue() instanceof ArrayList) {
				query.setParameterList(entry.getKey(),
						(ArrayList<?>) entry.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}

		}

		logger.debug("SQL query to be executed with Scalar mapping is : "
				+ query);

		try {
			resultList = query.list();
		} catch (HibernateException hbExp) {

			logger.error(String.format(
					"Error occured while executing query - %s ",
					query.getQueryString()), hbExp);
		}

		return resultList;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#getParameters(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Object[] getParameters(Long id, String parameterNames, String idType) {

		String SQL_QUERY = BizConstants.EMPTY_STRING;

		if (idType.equals(BizConstants.ID_TYPE_OUTDOOR_GHP)) {

			SQL_QUERY = String.format(SQL_GET_PARAMETER.toString(),
					parameterNames, GHP_PARAMETER_STATISTICS, id);
		} else if (idType.equals(BizConstants.ID_TYPE_OUTDOOR_VRF)) {

			SQL_QUERY = String.format(SQL_GET_PARAMETER.toString(),
					parameterNames, VRF_PARAMETER_STATISTICS, id);
		}

		List<?> resultList = executeSQLSelect(SQL_QUERY);

		Object[] parameterValues = null;

		if (resultList != null && !resultList.isEmpty()) {

			parameterValues = (Object[]) resultList.get(0);
		}

		return parameterValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#getIDUList(java.util.List)
	 */
	@Override
	public List<IDUListVO> getIDUList(List<Long> groupIds) {

		StringBuffer selectedGroupIds = new StringBuffer();
		String SQL_QUERY = BizConstants.EMPTY_STRING;

		List<?> resultList = null;
		List<IDUListVO> IDUList = null;

		if (groupIds != null && groupIds.size() > 0) {

			for (Long id : groupIds) {
				selectedGroupIds.append(id).append(BizConstants.COMMA_STRING);
			}
		} else {

			String customErrorMessage = CommonUtil
					.getJSONErrorMessage(BizConstants.NO_GROUP_SELECTED);

			throw new GenericFailureException(customErrorMessage);
		}

		String selectedGroupIdString = selectedGroupIds.toString().substring(0,
				selectedGroupIds.length() - 1);

		if (!selectedGroupIdString.equals(BizConstants.EMPTY_STRING)) {

			SQL_QUERY = String.format(SQL_GET_IDULIST_POSTGRESSQL.toString(),
					selectedGroupIdString);
		}

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		/* Modified by Ravi */
		scalarMapping.put(ID, StandardBasicTypes.LONG);
		scalarMapping.put(MAP_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(PATH_NAME, StandardBasicTypes.STRING);
		scalarMapping.put(COMPANY_NAME, StandardBasicTypes.STRING);

		resultList = executeSQLSelect(SQL_QUERY, scalarMapping);

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowdata = null;

			IDUList = new ArrayList<IDUListVO>();

			while (itr.hasNext()) {

				rowdata = (Object[]) itr.next();

				IDUListVO iduListVO = new IDUListVO();

				/* Modified by Ravi */
				iduListVO.setId((Long) rowdata[0]);
				iduListVO.setName((String) rowdata[1]);
				iduListVO.setPathName((String) rowdata[2]);
				iduListVO.setCompanyName((String) rowdata[3]);

				IDUList.add(iduListVO);
			}
		}

		return IDUList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#getGroupLevelDetails(java.
	 * lang.Long, java.lang.Long)
	 */
	public List<GroupLevelVO> getGroupLevelDetails(Long userId, Long companyId) {
		String query = SQL_GET_GROUP_LEVEL.toString();

		List<GroupLevelVO> groupLevelVOList = new ArrayList<GroupLevelVO>();
		GroupLevelVO groupLevelObj;

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<String, Type>();
		scalarMapping.put(ID, StandardBasicTypes.LONG);
		scalarMapping.put(MAP_NAME, StandardBasicTypes.STRING);

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(USER_ID, userId);
		parameter.put(COMPANY_ID, companyId);

		try {
			List<?> result = executeSQLSelect(query, scalarMapping, parameter);

			if (!result.isEmpty()) {

				Iterator<?> itr = result.iterator();
				Object[] rowData = null;

				while (itr.hasNext()) {

					groupLevelObj = new GroupLevelVO();

					rowData = (Object[]) itr.next();

					groupLevelObj.setId((Long) rowData[0]);
					groupLevelObj
							.setName(rowData[1] == null ? BizConstants.HYPHEN
									: (String) rowData[1]);
					groupLevelVOList.add(groupLevelObj);
				}
			}
		} catch (Exception sqlExp) {
			logger.error(String.format("An Exception occured while fetching"
					+ " Group Level Details for User ID %d", userId), sqlExp);
		}
		return groupLevelVOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeHQLQuery(java.lang.
	 * String)
	 */
	@Override
	public List<?> executeHQLQuery(String hqlQuery) throws HibernateException {

		List<?> resultList = null;

		Query query = getCurrentSession().createQuery(hqlQuery);

		try {
			resultList = query.list();
		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query - %s ",
					query.getQueryString()), hbExp);
		}

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.dao.SQLDAO#executeSQLUpdateQuery(java
	 * .lang.String, java.util.Map, java.util.LinkedHashMap)
	 */
	@Override
	public int executeSQLUpdateQuery(String sqlQuery,
			Map<String, Object> parameter,
			LinkedHashMap<String, Type> scalarMapping) {

		SQLQuery query = getCurrentSession().createSQLQuery(sqlQuery);

		for (Map.Entry<String, Object> entry : parameter.entrySet()) {

			if (entry.getValue() instanceof ArrayList) {
				query.setParameterList(entry.getKey(),
						(ArrayList<?>) entry.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

		for (Entry<String, Type> entry : scalarMapping.entrySet()) {
			query.addScalar(entry.getKey(), entry.getValue());
		}

		logger.debug("SQL query to be executed is : " + query);

		int affectRowCount = 0;

		try {
			affectRowCount = query.executeUpdate();
		} catch (HibernateException hbExp) {
			logger.error(String.format(
					"Error occured while executing query without Scalar"
							+ " mapping - %s ", query.getQueryString()), hbExp);
			throw hbExp;
		}
		return affectRowCount;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List Costomer(List cust_ids) {
		List<?> resultList = null;
		List<Long[]> custs = null;
		custs = (List<Long[]>) cust_ids;
		String cust_list = CommonUtil.convertCollectionToString(custs);

		String SQL_QUERY = SQ_FETCH_CUST_NAMES.toString();
		SQL_QUERY = String.format(SQL_QUERY, cust_list);
		resultList = executeSQLSelect(SQL_QUERY);

		return resultList;
	}

	public List getSiteIdTopolgy(List siteId) {
		List<?> resultList = null;
		List<Long[]> sites = null;
		sites = (List<Long[]>) siteId;
		String site_id = CommonUtil.convertCollectionToString(sites);
		System.out.println(site_id);
		String SQL_QUERY = SQ_FETCH_SITE_CUSTS.toString();
		SQL_QUERY = String.format(SQL_QUERY, site_id);
		resultList = executeSQLSelect(SQL_QUERY);
		return resultList;
	}

	@Override
	public List CaAddress(int siteid, int cust_id) {
		System.out.println(siteid + cust_id);
		List<?> resultList = null;
		// delete by shanf
		String SQL_QUERY = SQ_GET_CA_DATA_TOPOLOGY.toString();
		SQL_QUERY = String.format(SQL_QUERY, siteid, cust_id);
		resultList = executeSQLSelect(SQL_QUERY);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List CaAddressDistributionGroup(List site_ids, int cust_id) {
		// TODO Auto-generated method stub

		List<?> resultList = null;
		// delete by shanf
		if (site_ids.size() == 1) {
			List<Long[]> sites = (List<Long[]>) site_ids;
			String site_id = CommonUtil.convertCollectionToString(site_ids);
			String SQL_QUERY = SQ_GET_CA_DATA_DISTRIBUTION_GROUP.toString();
			SQL_QUERY = String.format(SQL_QUERY, site_id, cust_id);
			resultList = executeSQLSelect(SQL_QUERY);

		} else if (site_ids.size() > 1 && site_ids.size() != 0) {
			List<Long[]> sites = (List<Long[]>) site_ids;
			String site_id = CommonUtil.convertCollectionToString(sites);
			String MSite_ids = site_id.replaceAll(", ", "','");
			String Finalsiteid = "'" + MSite_ids + "'";
			String SQL_QUERY = SQ_GET_CA_DATA_DISTRIBUTION_GROUP_TWO_SITES
					.toString();
			SQL_QUERY = String.format(SQL_QUERY, Finalsiteid, cust_id);
			resultList = executeSQLSelect(SQL_QUERY);
		}
		return resultList;
	}

	/*
	 * @Override public List saveRetrieveToplogyIdu(List<IndoorUnitPlatForm>
	 * idutoplogy,String adapterid) { // TODO Auto-generated method stub // TODO
	 * Auto-generated method stub List<?> resultList1 = null; List<?> resultList
	 * = null; List<Long []> adapteridApp =null;
	 * 
	 * if(idutoplogy != null && idutoplogy.size() > 0 ){ Indoorunit idu = new
	 * Indoorunit(); for(IndoorUnitPlatForm IduData:idutoplogy ){ String
	 * setCentralControlAddress=IduData.getCentralControlAddress(); String
	 * setS_link=IduData.getSlink(); String
	 * connectuionAddress=IduData.getConnectionIduAddress(); String
	 * setType=IduData.getConnectionType(); String
	 * setConnectionNumber=IduData.getConnectionNumber(); String
	 * setMainIduAddress=IduData.getMainIduAddress(); String
	 * setRefrigCircuitNo=IduData.getRefrigCircuitId();
	 * 
	 * String SQL_QUERY = SQ_GET_CA_DATA_TOPOLOGY_ID.toString(); SQL_QUERY =
	 * String.format(SQL_QUERY,adapterid); //adapteridApp = (List<Long[]>)
	 * executeSQLSelect(SQL_QUERY); String adapteridIdu =
	 * CommonUtil.convertCollectionToString(adapteridApp);
	 * 
	 * 
	 * String DeviceModel =IduData.getDeviceModel(); String
	 * fecility_id=IduData.getFacilityId();
	 * 
	 * 
	 * String SQL_QUERY1 = RETRIEVE_TOPOLGY_IDU.toString();
	 * 
	 * 
	 * 
	 * SQL_QUERY1 =
	 * String.format(SQL_QUERY1,adapteridIdu,setCentralControlAddress
	 * ,setConnectionNumber
	 * ,DeviceModel,fecility_id,setMainIduAddress,setS_link,setType
	 * ,setRefrigCircuitNo,connectuionAddress); resultList1 =
	 * executeSQLSelect(SQL_QUERY1);
	 * 
	 * 
	 * 
	 * 
	 * } } return null; }
	 */
	/*
	 * @Override public List saveRetrieveToplogyOdu(List<OutdoorUintPf>
	 * odutoplogy,String adapterName) { // TODO Auto-generated method stub
	 * List<?> resultList1 = null; List<Long []> adapteridApp =null;
	 * if(odutoplogy != null && odutoplogy.size() > 0 ){
	 * 
	 * for(OutdoorUintPf OduData:odutoplogy ){
	 * 
	 * 
	 * 
	 * String setType=OduData.getConnectionType(); String
	 * setConnectionNumber=OduData.getConnectionNumber();
	 * 
	 * String setRefrigCircuitNo=OduData.getRefrigCircuitId(); String
	 * setRefrigCircuitGroupNo=OduData.getRefrigCircuitGroupOduId();
	 * 
	 * String DeviceModel= OduData.getModel(); String
	 * fecility_id=OduData.getFacilityId(); String
	 * setS_link=OduData.getS_link(); String SQL_QUERY =
	 * SQ_GET_CA_DATA_TOPOLOGY_ID.toString(); SQL_QUERY =
	 * String.format(SQL_QUERY,adapterName); adapteridApp = (List<Long[]>)
	 * executeSQLSelect(SQL_QUERY); String adapteridPls =
	 * CommonUtil.convertCollectionToString(adapteridApp); String SQL_QUERY1 =
	 * RETRIEVE_TOPOLGY_ODU.toString();
	 * 
	 * 
	 * SQL_QUERY1 =
	 * String.format(SQL_QUERY1,adapteridPls,setRefrigCircuitGroupNo
	 * ,setRefrigCircuitNo
	 * ,DeviceModel,fecility_id,setS_link,setType,setConnectionNumber);
	 * resultList1 = executeSQLSelect(SQL_QUERY1); } } return null; }
	 */

	/*
	 * @Override public List saveRetrieveToplogyPls(List<PulseMeterPf>
	 * plstoplogy,String adapterName) { // TODO Auto-generated method stub
	 * 
	 * List<?> resultList1 = null; List<Long []> adapteridApp =null;
	 * if(plstoplogy != null && plstoplogy.size() > 0 ){
	 * 
	 * for(PulseMeterPf PlsData:plstoplogy ){
	 * 
	 * 
	 * 
	 * String setType=PlsData.getConnectionType(); String
	 * FacilityId=PlsData.getFacilityId();
	 * 
	 * String portNumber=PlsData.getPort_number(); String
	 * Model=PlsData.getModel(); String SQL_QUERY =
	 * SQ_GET_CA_DATA_TOPOLOGY_ID.toString(); SQL_QUERY =
	 * String.format(SQL_QUERY,adapterName); adapteridApp = (List<Long[]>)
	 * executeSQLSelect(SQL_QUERY); String adapteridPls =
	 * CommonUtil.convertCollectionToString(adapteridApp);
	 * 
	 * String SQL_QUERY1 = RETRIEVE_TOPOLGY_PLS.toString();
	 * 
	 * 
	 * SQL_QUERY1 =
	 * String.format(SQL_QUERY1,adapteridPls,setType,FacilityId,portNumber
	 * ,Model); resultList1 = executeSQLSelect(SQL_QUERY1); } }
	 * 
	 * return null; }
	 */

	/*
	 * @Override public List UpdateRetrieveToplogyOdu(List<OduData> ODU,int
	 * adapter_id,String site_id) { List<?> resultList1 = null; if(ODU != null
	 * && ODU.size() > 0 ){
	 * 
	 * for(OduData OduData:ODU ){ String fid=OduData.getFacilityId(); String
	 * setType=OduData.getConnectionType(); String
	 * setConnectionNumber=OduData.getConnectionNumber();
	 * 
	 * String setRefrigCircuitNo=OduData.getRefrigCircuitId(); String
	 * setRefrigCircuitGroupNo=OduData.getRefrigCircuitGroupOduId();
	 * 
	 * String DeviceModel= OduData.getModel(); String
	 * fecility_id=OduData.getFacilityId(); String
	 * setS_link=OduData.getS_link(); String DeviceName=
	 * OduData.getDeviceName(); String Category= OduData.getCategory();
	 * 
	 * String SQL_QUERY1 = RETRIEVE_TOPOLGY_ODU.toString();
	 * 
	 * SQL_QUERY1 = String.format(SQL_QUERY1,adapter_id,setRefrigCircuitGroupNo,
	 * setRefrigCircuitNo
	 * ,DeviceModel,fecility_id,setS_link,Category,setConnectionNumber
	 * ,DeviceName,setType); System.out.println(SQL_QUERY1); //resultList1 =
	 * executeSQLSelect(SQL_QUERY1);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * } return null;
	 * 
	 * }
	 * 
	 * 
	 * @SuppressWarnings("unused")
	 * 
	 * @Override public List UpdateRetrieveToplogyIdu(List<IduData> IDU,int
	 * adapter_id,String site_id) { List<?> resultList1 = null; if(IDU != null
	 * && IDU.size() > 0){
	 * 
	 * for (IduData IduData : IDU) { String fid=IduData.getFacilityId(); String
	 * dname=IduData.getDeviceName(); String
	 * setCentralControlAddress=IduData.getCentralControlAddress(); String
	 * setS_link=IduData.getS_link(); String
	 * connectuionAddress=IduData.getConnectionNumber(); String
	 * setType=IduData.getConnectionType(); String
	 * setConnectionNumber=IduData.getConnectionNumber(); String
	 * setMainIduAddress=IduData.getMainIduAddress(); String
	 * setRefrigCircuitNo=IduData.getRefrigCircuitId(); String
	 * dmodal=IduData.getModel();
	 * 
	 * //
	 * "insert into indoorunits (adapters_id,name,centralcontroladdress,connectionnumber,device_model,oid,mainiduaddress,s_link,connectionType,refrigcircuitno,Connectionaddress) values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"
	 * );
	 * 
	 * String SQL_QUERY1 = RETRIEVE_TOPOLGY_IDU.toString();
	 * 
	 * 
	 * 
	 * SQL_QUERY1 =
	 * String.format(SQL_QUERY1,adapter_id,dname,setCentralControlAddress
	 * ,setConnectionNumber
	 * ,dmodal,fid,setMainIduAddress,setS_link,setType,setRefrigCircuitNo
	 * ,connectuionAddress,site_id); System.out.println(SQL_QUERY1);
	 * //resultList1 = executeSQLSelect(SQL_QUERY1);
	 * 
	 * 
	 * }
	 * 
	 * } return null; }
	 */

	@Override
	public List DistributionGroup(int CostomerId) {
		List<?> resultList = null;
		List<Object[]> DistributionGroupS = new ArrayList<>();
		try {
			String SQL_QUERY = DISPLAY_DISTRIBUTION.toString();
			SQL_QUERY = String.format(SQL_QUERY, CostomerId);
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
	public List DisplayDistrbutionGroup(int DistributionGroupid) {
		@SuppressWarnings("unused")
		List<?> resultList2 = null;

		List<?> resultList4 = null;

		List<Long[]> idu = null;
		List<Long[]> pls = null;

		String SQL_QUERY1 = DISTRIBUTION_GROUP_DATA1.toString();
		String SQL_QUERY2 = DISTRIBUTION_GROUP_DATA2.toString();

		SQL_QUERY1 = String.format(SQL_QUERY1, DistributionGroupid);
		SQL_QUERY2 = String.format(SQL_QUERY2, DistributionGroupid);
		pls = (List<Long[]>) executeSQLSelect(SQL_QUERY1);
		idu = (List<Long[]>) executeSQLSelect(SQL_QUERY2);

		@SuppressWarnings("rawtypes")
		List<DistributionGroupPlsVo> PLSList = new ArrayList<DistributionGroupPlsVo>();
		List<DistributionGroupIduVo> IDUList = new ArrayList<DistributionGroupIduVo>();
		;
		List site_Data4 = new ArrayList<>();

		try {

			if (idu != null && idu.size() > 0) {

				String SQL_QUERY3 = DISTRIBUTION_GROUP_IDU_SITE1.toString();
				String idulist = CommonUtil.convertCollectionToString(idu);
				SQL_QUERY3 = String.format(SQL_QUERY3, idulist);
				resultList4 = executeSQLSelect(SQL_QUERY3);

				if (resultList4 != null && resultList4.size() > 0) {

					Iterator<?> itr1 = resultList4.iterator();
					Object[] rowdata1 = null;

					IDUList = new ArrayList<DistributionGroupIduVo>();

					while (itr1.hasNext()) {

						rowdata1 = (Object[]) itr1.next();

						DistributionGroupIduVo iduListVO = new DistributionGroupIduVo();

						iduListVO.setCaName((String) rowdata1[2]);
						iduListVO.setS_linkaddress((String) rowdata1[1]);
						iduListVO.setSiteName((String) rowdata1[0]);
						iduListVO.setDeviceName((String) rowdata1[4]);

						IDUList.add(iduListVO);
					}
				}
			}

			if (pls != null && pls.size() > 0) {
				String SQL_QUERY3 = DISTRIBUTION_GROUP_PLS_SITE.toString();
				String plslist = CommonUtil.convertCollectionToString(pls);
				SQL_QUERY3 = String.format(SQL_QUERY3, plslist);
				resultList2 = executeSQLSelect(SQL_QUERY3);

				if (resultList2 != null && resultList2.size() > 0) {

					Iterator<?> itr1 = resultList2.iterator();
					Object[] rowdata2 = null;

					PLSList = new ArrayList<DistributionGroupPlsVo>();

					while (itr1.hasNext()) {

						rowdata2 = (Object[]) itr1.next();

						DistributionGroupPlsVo PlsListVO = new DistributionGroupPlsVo();

						PlsListVO.setSiteName((String) rowdata2[0]);
						PlsListVO.setPortnumber((Integer) rowdata2[1]);
						PlsListVO.setCaName((String) rowdata2[2]);
						PlsListVO.setMeterName((String) rowdata2[3]);

						PLSList.add(PlsListVO);
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		site_Data4.add(PLSList);
		site_Data4.add(IDUList);

		return site_Data4;
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, List<Object>> listDistributionGroupDropDownData(
			int cust_id) {
		List<?> resultList1 = null;
		List<?> resultList2 = null;
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		@SuppressWarnings("unused")
		List<Object> resultListVRF2 = new ArrayList<>();
		List<Object> resultListGHP2 = new ArrayList<>();
		String SQL_QUERY1 = SQl_FETCH_DISTRIBUTION_GROUP_DATA_VRF.toString();
		SQL_QUERY1 = String.format(SQL_QUERY1, cust_id);
		resultList1 = executeSQLSelect(SQL_QUERY1);
		Iterator itr1 = resultList1.iterator();
		Object[] rowData = null;
		try {
			while (itr1.hasNext()) {
				rowData = (Object[]) itr1.next();
				resultListVRF2.add(rowData);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String SQL_QUERY2 = SQl_FETCH_DISTRIBUTION_GROUP_DATA_GHP.toString();
		SQL_QUERY2 = String.format(SQL_QUERY2, cust_id);
		resultList2 = executeSQLSelect(SQL_QUERY2);
		Iterator itr2 = resultList2.iterator();
		Object[] rowData1 = null;
		try {
			while (itr2.hasNext()) {
				rowData1 = (Object[]) itr2.next();
				resultListGHP2.add(rowData1);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("VRF", resultListVRF2);
		map.put("GHP", resultListGHP2);

		return map;
	}

	@SuppressWarnings("unused")
	@Override
	public List getDistributionGroupPls(List adapterid) {

		List<?> resultList1 = null;
		List<?> resultList3 = null;

		List<List<?>> resultList4 = new ArrayList<>();
		List<Long[]> adaptors = new ArrayList<Long[]>();
		;
		adaptors.addAll(adapterid);
		String adaptorsID = CommonUtil.convertCollectionToString(adaptors);
		@SuppressWarnings("unused")
		List<Object> resultList2 = new ArrayList<>();
		String SQL_QUERY1 = SQl_FETCH_DISTRIBUTION_GROUP_PLS.toString();
		SQL_QUERY1 = String.format(SQL_QUERY1, adaptorsID);
		resultList1 = executeSQLSelect(SQL_QUERY1);
		String SQL_QUERY_EXISTING_DG_PLS = SQl_FETCH_DISTRIBUTION_GROUP_PLS_CREATED
				.toString();
		SQL_QUERY_EXISTING_DG_PLS = String.format(SQL_QUERY_EXISTING_DG_PLS,
				adaptorsID);
		System.out.println(SQL_QUERY_EXISTING_DG_PLS);
		resultList3 = executeSQLSelect(SQL_QUERY_EXISTING_DG_PLS);
		Iterator itr1 = resultList1.iterator();
		Object[] rowData = null;
		try {
			while (itr1.hasNext()) {
				rowData = (Object[]) itr1.next();
				if (rowData[2] == null) {
					resultList2.add(rowData);

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resultList4.add(resultList2);
		resultList4.add(resultList3);

		return resultList4;
	}

	@SuppressWarnings("unused")
	@Override
	public List getDistributionGroupIdu(List adapterid) {
		List<?> resultList1 = new ArrayList<>();

		List<?> resultList5 = new ArrayList<>();

		List<Object> resultList3 = new ArrayList<>();
		List<Long[]> adaptors = new ArrayList<Long[]>();
		List<List<?>> resultList4 = new ArrayList<>();
		String SQL_QUERY1 = SQl_FETCH_DISTRIBUTION_GROUP_IDU.toString();
		adaptors.addAll(adapterid);
		String adaptorsID = CommonUtil.convertCollectionToString(adaptors);
		SQL_QUERY1 = String.format(SQL_QUERY1, adaptorsID);
		resultList1 = executeSQLSelect(SQL_QUERY1);

		try {
			String SQL_QUERY_EXISTING_DG = SQl_FETCH_DISTRIBUTION_GROUP_IDU_CREATED
					.toString();
			SQL_QUERY_EXISTING_DG = String.format(SQL_QUERY_EXISTING_DG,
					adaptorsID);

			System.out.println(SQL_QUERY_EXISTING_DG);
			resultList5 = executeSQLSelect(SQL_QUERY_EXISTING_DG);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Iterator itr1 = resultList1.iterator();
		Object[] rowData = null;
		try {
			while (itr1.hasNext()) {
				rowData = (Object[]) itr1.next();

				if (rowData[4] == null) {
					resultList3.add(rowData);
					System.out.println(rowData[4]);
				}

				// resultList2.add(rowData);
				System.out.println(rowData[4]);

			}
			resultList4.add(resultList3);
			resultList4.add(resultList5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList4;
	}

	@Override
	public Map<Integer, String> UpdateDistributionGroupIdu(
			List<DistributionGroupIduData> idu) {
		// TODO Auto-generated method stub
		String id;
		int groupid;
		@SuppressWarnings("unused")
		List<?> resultList1 = null;
		// delete by shanf
		Map<Integer, String> GroupByFid = new HashMap<Integer, String>();

		// delete by shanf
		// String Status="";

		if (idu != null && idu.size() > 0) {
			for (DistributionGroupIduData IduData : idu) {
				id = IduData.getId();
				groupid = IduData.getDist_grp();

				String SQL_QUERY_IDU_PF = FACILTYID_INDOORUNIT_DISTRIBUTION_GROUP
						.toString();
				SQL_QUERY_IDU_PF = String.format(SQL_QUERY_IDU_PF, id);
				List<?> s = executeSQLSelect(SQL_QUERY_IDU_PF);
				String Fid = (String) s.get(0);
				GroupByFid.put(groupid, Fid);
				/*
				 * String SQL_QUERY1 = UPDATE_DISTRIBUTION_GROUP_IDU.toString();
				 */

				/*
				 * SQL_QUERY1 = String.format(SQL_QUERY1,groupid,id);
				 * 
				 * resultList1 = executeSQLSelect(SQL_QUERY1); //
				 * GroupByType.put(groupid, l); String iduIdlist =
				 * CommonUtil.convertCollectionToString(l1); String
				 * SQL_QUERY_IDU_PF =
				 * FACILTYID_INDOORUNIT_DISTRIBUTION_GROUP.toString();
				 * SQL_QUERY_IDU_PF = String.format(SQL_QUERY_IDU_PF,iduIdlist);
				 * resultList2 = executeSQLSelect(SQL_QUERY_IDU_PF);
				 */
			}

			String SQL_QUERY12 = DISTRIBUTION_GROUP_TYPE.toString();
			SQL_QUERY12 = String.format(SQL_QUERY12);
			resultList1 = executeSQLSelect(SQL_QUERY12);

			// calculationType
			// Status=(String) resultList1.get(0);

		}
		return GroupByFid;

	}

	@Override
	public List UpdateDistributionGroupPls() {
		@SuppressWarnings("unused")
		List<?> resultList3 = new ArrayList<>();
		// delete by shanf
		try {
			String SQL_QUERY_DISTRIBUTION = DISTRIBUTION_GROUP_ID.toString();
			SQL_QUERY_DISTRIBUTION = String.format(SQL_QUERY_DISTRIBUTION);
			resultList3 = executeSQLSelect(SQL_QUERY_DISTRIBUTION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultList3;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String delete_DistributionGroup_data(Long DistributionGroupId,
			int costomer) {
		// delete by shanf
		// DistributionGroup dst = new DistributionGroup();

		/*
		 * try { DeleteDistributionGroupParam param = new
		 * DeleteDistributionGroupParam();
		 * 
		 * param.customerId = costomer; param.distributionGroupId =
		 * DistributionGroupId; String userId = "USER001";
		 * DistributionGroupManager distributionGroupManager = new
		 * DistributionGroupManager();
		 * distributionGroupManager.deleteDistributionGroup(param, userId);
		 * 
		 * } catch (DataAggregationException daExp) {
		 * 
		 * } catch (Exception exp) {
		 * 
		 * }
		 */

		return "success";
	}

	/**
	 *
	 * 
	 * @author Srinivas
	 * 
	 */
	public List create_new_cust(String cust_name, String creationdate) {

		List<?> resultList = null;
		List<Object> customers = new ArrayList<>();
		String SQL_QUERY = SQ_ADD_CUST_DATA.toString();
		SQL_QUERY = String.format(SQL_QUERY, creationdate, cust_name);

		resultList = executeSQLSelect(SQL_QUERY);

		System.out.println(resultList);

		/*
		 * if (resultList != null && resultList.size() > 0) {
		 * 
		 * Iterator<?> itr = resultList.iterator(); Object[] rowData = null;
		 * while (itr.hasNext()) { rowData = (Object[]) itr.next();
		 * 
		 * if (rowData[0] != null) {
		 * 
		 * customers.add(rowData);
		 * 
		 * } } }
		 */
		return customers;
	}

	public List Insert_caRegister(String ca_mac_address_reg, String ca_model,
			Long company_id, String User_id) {
		List<?> resultList = null;
		List<?> resultList1 = null;

		String SQL_QUERY = SQ_CHECK_MAC.toString();

		SQL_QUERY = String.format(SQL_QUERY, ca_mac_address_reg);
		resultList = executeSQLSelect(SQL_QUERY);

		System.out.println(resultList.size());

		if (resultList.size() == 0) {
			String SQL_QUERY1 = SQ_ADD_CA_DATA.toString();
			SQL_QUERY1 = String.format(SQL_QUERY1, ca_mac_address_reg,
					company_id, ca_model, User_id);

			resultList = executeInsertSQLSelect(SQL_QUERY1);

		}

		return resultList;

	}

	public List delete_ca(Integer Inserted_Ca_id_value) {
		List<?> resultList = null;
		String SQL_QUERY = SQ_DELETE_ADAPTER.toString();
		SQL_QUERY = String.format(SQL_QUERY, Inserted_Ca_id_value);
		resultList = executeInsertSQLSelect(SQL_QUERY);
		return resultList;

	}

	public int Insert_Adapter_facility(String Facility_Id,
			Object Inserted_Ca_id_value) {
		int resultList;
		String SQL_QUERY = SQ_ADD_Facility.toString();
		SQL_QUERY = String.format(SQL_QUERY, Facility_Id, Inserted_Ca_id_value);
		resultList = executeSQLUpdateQuery(SQL_QUERY);
		return resultList;

	}

	public int Insert_caAssociate(String ca_mac_address, String site_id,
			Integer Timezone, String Latitude, String Longitude,
			Timestamp installed_at, String status, String ca_name) throws ParseException {
		int resultList;
		// delete by shanf List<Object> customers = new ArrayList<>();
		/*String SQL_QUERY = SQ_ASSOCIATE_CA_DATA.toString();
		SQL_QUERY = String.format(SQL_QUERY, site_id, Timezone, Latitude,
				Longitude, installed_at, status, ca_name, ca_mac_address);
		resultList = executeSQLUpdateQuery(SQL_QUERY);*/

		Query query = getCurrentSession().createSQLQuery(SQ_ASSOCIATE_CA_DATA.toString())
				.setParameter("site_id", site_id)
				.setParameter("Timezone", Timezone)
				.setParameter("Latitude", Double.parseDouble(Latitude))
				.setParameter("Longitude", Double.parseDouble(Longitude))
				.setParameter("installed_at", installed_at)
				.setParameter("status", status)
				.setParameter("ca_name", ca_name)
				.setParameter("ca_mac_address", ca_mac_address);
		resultList = query.executeUpdate();
		
		System.out.println("executing");
		return resultList;
		
	}

	public boolean check_ca_duplicate(String site_id, String cust_name,
			String ca_name) {
		List<?> resultList = new ArrayList<>();
		String SQL_QUERY = SQ_GET_DUP_CA.toString();
		SQL_QUERY = String.format(SQL_QUERY, site_id, cust_name, ca_name);
		resultList = executeSQLSelect(SQL_QUERY);

		if (resultList.isEmpty()) {

			System.out.println("no duplicate");

			return false;
		}

		return true;

	}

	public List show_cust() {

		List<?> resultList = null;
		List<Object> cloud_adapters = new ArrayList<>();
		String SQL_QUERY = SQ_GET_CUST_DATA.toString();
		SQL_QUERY = String.format(SQL_QUERY);
		resultList = executeSQLSelect(SQL_QUERY);

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowData = null;
			while (itr.hasNext()) {
				rowData = (Object[]) itr.next();

				if (rowData[0] != null) {

					cloud_adapters.add(rowData);

				}
			}
		}
		return cloud_adapters;
	}

	public List Show_Ca_data(String ca_mac) {

		
		List<?> resultList = new ArrayList<>();
		Query query = getCurrentSession().createSQLQuery(SQ_GET_CA_DATA.toString())
				.setParameter("ca_mac", ca_mac);
				resultList = query.list();
				return resultList;
				
	}

	public List Fetch_mac_Address(String cust_id) {
		
		List<?> resultList = new ArrayList<>();
		Query query = getCurrentSession().createSQLQuery(SQ_FETCH_CA_MAC.toString())
				.setParameter("cust_id", new BigInteger(cust_id));
				resultList = query.list();
				return resultList;
				
		/*List<?> resultList = null;
		List<Object> mac_address = new ArrayList<>();

		String SQL_QUERY = SQ_FETCH_CA_MAC.toString();

		SQL_QUERY = String.format(SQL_QUERY, cust_id);
		resultList = executeSQLSelect(SQL_QUERY);

		System.out.println(resultList);

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();

			Object[] rowData = null;

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();
				// delete by shanf
				if (rowData[0] != null) {

					mac_address.add(rowData);

				}
			}
		}

		return mac_address;*/
	}

	public List Fetch_mac_Address_reg(String cust_id) {

		List<?> resultList = new ArrayList<>();
		Query query = getCurrentSession().createSQLQuery(SQ_FETCH_CA_MAC_REG.toString())
				.setParameter("cust_id", new BigInteger(cust_id));
				resultList = query.list();
				return resultList;
				
	}

	public List Fetch_siteid(String cust_id) {
		List<?> resultList = null;
		String SQL_QUERY = SQ_FETCH_SITEID.toString();

		SQL_QUERY = String.format(SQL_QUERY, cust_id);
		resultList = executeSQLSelect(SQL_QUERY);

		return resultList;
	}

	public List Fetch_AllSites(List site_ids, int CustomerID) {
		// delete by shanf
		List<Long[]> sites = (List<Long[]>) site_ids;

		String site_list = CommonUtil.convertCollectionToString(sites);
		String SQL_QUERY = SQ_FETCH_SITE_NAMES.toString();
		SQL_QUERY = String.format(SQL_QUERY, site_list, CustomerID);
		List<?> resultList = executeSQLSelect(SQL_QUERY);

		System.out.println(resultList);

		return resultList;

	}

	public List Fetch_AllSiteNames(int CustomerID) {
		// delete by shanf
		String SQL_QUERY = SQ_FETCH_ALL_SITE_NAMES.toString();
		SQL_QUERY = String.format(SQL_QUERY, CustomerID);
		List<?> resultList = executeSQLSelect(SQL_QUERY);
		return resultList;
	}

	public List Fetch_siteidcust(String cust_id) {

		List<?> resultList = null;
		// delete by shanf
		String SQL_QUERY = SQ_FETCH_SITEID_CUST.toString();
		SQL_QUERY = String.format(SQL_QUERY, cust_id);
		resultList = executeSQLSelect(SQL_QUERY);
		return resultList;
	}

	public List Fetch_caModel() {

		List<?> resultList = null;
		List<Object> ca_model = new ArrayList<>();
		String SQL_QUERY = SQ_FETCH_CA_MODEL.toString();
		SQL_QUERY = String.format(SQL_QUERY);
		System.out.println(SQL_QUERY);
		resultList = executeSQLSelect(SQL_QUERY);
		System.out.println(resultList);
		return resultList;

	}

	public List show_cust_adapters() {

		List<?> resultList = null;
		List<Object> ca_cust = new ArrayList<>();

		String SQL_QUERY = SQ_FETCH_CA_CUST.toString();

		SQL_QUERY = String.format(SQL_QUERY);
		resultList = executeSQLSelect(SQL_QUERY);

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowData = null;
			while (itr.hasNext()) {
				rowData = (Object[]) itr.next();

				if (rowData[0] != null) {

					ca_cust.add(rowData);

				}

			}
		}

		return ca_cust;

	}

	private static final StringBuilder SQ_FETCH_CUST_NAMES = new StringBuilder(
			"select id,name from companies where id in(%s)");
	
	
	private static final StringBuilder SQ_FETCH_CUST_IDS = new StringBuilder(
			"select company_id from companiesusers where user_id='%d'");

	private static final StringBuilder SQ_FETCH_SITE_NAMES = new StringBuilder(
			"select id,name from groups where groupcategoryid=2 and id not in (%s) and company_id=%d");

	private static final StringBuilder SQ_FETCH_ALL_SITE_NAMES = new StringBuilder(
			"select id,name from groups where groupcategoryid=2 and company_id=%d");

	// "select id, name from groups where groupcategoryid=2 and id not in  (SELECT group_id FROM companiesusers)");

	public List show_cust_names(List cust_ids) {

		List<?> resultList = null;
		List<Long[]> custs = null;
		custs = (List<Long[]>) cust_ids;
		String cust_list = CommonUtil.convertCollectionToString(custs);
		String SQL_QUERY = SQ_FETCH_CUST_NAMES.toString();
		SQL_QUERY = String.format(SQL_QUERY, cust_list);
		resultList = executeSQLSelect(SQL_QUERY);
		return resultList;

	}
	
	public List show_cust_names_operator(Long User_id) {

		List<?> resultList = new ArrayList<>();
		String SQL_QUERY = SQ_FETCH_CUST_IDS.toString();
		SQL_QUERY = String.format(SQL_QUERY, User_id);
		resultList = executeSQLSelect(SQL_QUERY);
		return resultList;

	}

	public List get_roleTypeId(Long User_id){

		int rolTypeId = 0;
		int roleId = 0;
		User users = new User();	
		List<?> resultList = new ArrayList<>();
		List<?> resultList1 = new ArrayList<>();
		
		String SQL_QUERY = SQ_FETCH_ROLE_ID.toString();
		SQL_QUERY = String.format(SQL_QUERY, User_id);
		resultList = executeSQLSelect(SQL_QUERY);
		
		String SQL_QUERY1 = SQ_FETCH_ROLETYPE_ID.toString();
		SQL_QUERY1 = String.format(SQL_QUERY1, resultList.get(0));
		resultList1 = executeSQLSelect(SQL_QUERY1);
		rolTypeId = (Integer) resultList1.get(0);
		return resultList1;
	}
	
	

	public List show_site_customers(List site_ids) {
		// System.out.println("inside sql");
		// System.out.println(site_ids);
		// delete by shanf
		List<Long[]> sites = (List<Long[]>) site_ids;
		String site_id = CommonUtil.convertCollectionToString(sites);
		// System.out.println(site_id);
		String SQL_QUERY = SQ_FETCH_SITE_CUSTS.toString();
		SQL_QUERY = String.format(SQL_QUERY, site_id);
		// System.out.println(SQL_QUERY);
		List<?> resultList = executeSQLSelect(SQL_QUERY);
		return resultList;
	}

	public List Show_Timzones() {

		List<?> resultList = null;
		List<Object> time_zone = new ArrayList<>();
		String SQL_QUERY = SQ_FETCH_TIME_ZONE.toString();

		SQL_QUERY = String.format(SQL_QUERY);
		resultList = executeSQLSelect(SQL_QUERY);

		if (resultList != null && resultList.size() > 0) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowData = null;
			while (itr.hasNext()) {
				rowData = (Object[]) itr.next();

				if (rowData[0] != null) {

					time_zone.add(rowData);

				}

			}
		}

		return time_zone;

	}

	@Override
	public boolean register_dao_cust_siteid(List<Integer> site_id,
			String customer_name, Long userId) {

		Group group = new Group();
		List<?> resultList = null;
		// delete by shanf
		if (site_id != null && site_id.size() > 0) {
			Iterator<?> itr = site_id.iterator();
			Object[] rowData = null;

			try {
				while (itr.hasNext()) {

					String SQL_QUERY = SQ_REG_CUST_SITE.toString();
					SQL_QUERY = String.format(SQL_QUERY, customer_name, userId,
							itr.next());
					resultList = executeSQLSelect(SQL_QUERY);

				}

				return true;
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return false;

	}

	@Override
	public List UpdateDistributionGroupPlsAndIdUMerging(
			List<DistributionGroupPlsData> pls, Map<Integer, String> GroupByFid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCalCode(int DistributionGroupId) {
		// TODO Auto-generated method stub
		List<?> resultList3 = null;
		String SQL_QUERY_DISTRIBUTION = DISTRIBUTION_GROUP_ID_CAL_CODE
				.toString();
		SQL_QUERY_DISTRIBUTION = String.format(SQL_QUERY_DISTRIBUTION,
				DistributionGroupId);
		resultList3 = executeSQLSelect(SQL_QUERY_DISTRIBUTION);

		String s = (String) resultList3.get(0);

		return s;
	}

	private static final StringBuilder SQL_FETCH_IDU_RETRIVE_TOPOLOGY = new StringBuilder(
			"select slinkaddress,parent_id,device_model,name,oid,id from indoorunits where adapters_id= :cloud_adapter_id");

	private static final StringBuilder SQL_FETCH_ODU_RETRIVE_TOPOLOGY = new StringBuilder(
			"select slinkaddress,device_model,name,oid,id from outdoorunits where adapters_id= :cloud_adapter_id");

	private static final StringBuilder SQL_FETCH_PLS_RETRIVE_TOPOLOGY = new StringBuilder(
			"select device_model,meter_name,port_number,oid,id from pulse_meter where adapters_id= :cloud_adapter_id");

	@Override
	public List RetrieveAllTopolgy(String site_id, String cloud_adapter_id,
			String costomer_id) {

		/*List<List> resultList = new ArrayList<>();
		// delete bu shanf
		String SQL_QUERY1 = SQL_FETCH_IDU_RETRIVE_TOPOLOGY.toString();
		SQL_QUERY1 = String.format(SQL_QUERY1, cloud_adapter_id);

		String SQL_QUERY2 = SQL_FETCH_ODU_RETRIVE_TOPOLOGY.toString();
		SQL_QUERY2 = String.format(SQL_QUERY2, cloud_adapter_id);

		String SQL_QUERY3 = SQL_FETCH_PLS_RETRIVE_TOPOLOGY.toString();
		SQL_QUERY3 = String.format(SQL_QUERY3, cloud_adapter_id);

		List<?> resultList1 = executeSQLSelect(SQL_QUERY1);
		List<?> resultList2 = executeSQLSelect(SQL_QUERY2);
		List<?> resultList3 = executeSQLSelect(SQL_QUERY3);*/
		
		List<List> resultList = new ArrayList<>();
		List<List> resultList1 = new ArrayList<>();
		List<List> resultList2 = new ArrayList<>();
		List<List> resultList3 = new ArrayList<>();
		
		
		Query query1 = getCurrentSession().createSQLQuery(SQL_FETCH_IDU_RETRIVE_TOPOLOGY.toString())
				.setParameter("cloud_adapter_id", new BigInteger(cloud_adapter_id));
		resultList1 = query1.list();
		
		Query query2 = getCurrentSession().createSQLQuery(SQL_FETCH_ODU_RETRIVE_TOPOLOGY.toString())
				.setParameter("cloud_adapter_id", new BigInteger(cloud_adapter_id));
		resultList2 = query2.list();
		
		Query query3 = getCurrentSession().createSQLQuery(SQL_FETCH_PLS_RETRIVE_TOPOLOGY.toString())
				.setParameter("cloud_adapter_id", new BigInteger(cloud_adapter_id));
		resultList3 = query3.list();

		resultList.add(resultList1);
		resultList.add(resultList2);
		resultList.add(resultList3);
		System.out.println(resultList);

		return resultList;

	}

	private static final StringBuilder INSERT_TOPOLGY_ODU = new StringBuilder(
			"insert into outdoorunits (adapters_id,refrigCircuitGroupOduId,refrigCircuitNo,device_model,oid,slinkaddress,type,connectionnumber,name,connectiontype,siteid) values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')");

	private static final StringBuilder UPDATE_TOPOLGY_ODU_PARENT = new StringBuilder(
			"update outdoorunits set parentid=(select ID from outdoorunits where oid=:Parent_id) where oid=:facility_id");


	private static final StringBuilder UPDATE_TOPOLGY_IDU_PARENT = new StringBuilder(
			"update indoorunits set parent_id=(select ID from indoorunits where connectionaddress=:Parent_id and mainIduAddress='null' and refrigcircuitno=:refcircuitid and adapters_id = :adapter_id and connectionnumber = :connection_number) where mainIduAddress=:Parent_id  and oid=:facility_id");

	
	
	private static final StringBuilder UPDATE_TOPOLGY_IDU_ODU_PARENT = new StringBuilder(
			"update indoorunits set outdoorunit_id=(select ID from outdoorunits where oid=:out_id),type=(select type from outdoorunits where oid=:out_id) where oid=:facility_id");

	
	
	private static final StringBuilder INSERT_TOPOLGY_IDU = new StringBuilder(
			"insert into indoorunits (adapters_id,name,centraladdress,connectionnumber,device_model,oid,mainiduaddress,slinkaddress,connectionType,refrigcircuitno,Connectionaddress,siteid) values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')");
	private static final StringBuilder INSERT_TOPOLGY_PLS = new StringBuilder(
			"insert into pulse_meter (adapters_id,connection_type,meter_name,oid,port_number,device_model,meter_type,multi_factor) values ('%s','%s','%s','%s','%s','%s','%s','%s')");

	private static final StringBuilder UPDATE_TOPOLGY_ODU_ONLY = new StringBuilder(
			"update outdoorunits set name='%s'where id='%d'");

	private static final StringBuilder UPDATE_TOPOLGY_IDU_ONLY = new StringBuilder(
			"update indoorunits set name='%s'where id='%d'");

	private static final StringBuilder UPDATE_TOPOLGY_PLS_ONLY = new StringBuilder(
			"update pulse_meter set meter_name='%s'where id='%d'");

	private static final StringBuilder SELECT_TOPOLGY_COM = new StringBuilder(
			"select company_id, siteid from adapters where id =:adapter_id");

	private static final StringBuilder UPDATE_METAREFRIGERANT = new StringBuilder(
			"update metarefrigerant set rated_efficiency_cooling = :RefCoolingEfficiencyFinal, rated_efficiency_heating= :RefHeatingEfficiencyFinal,"
			+ "rated_capacity=:Sum_Total_Ref_Capacity,averatedefficiency=:avg_effi__Final where refid=:refId");


	
	
	
	/*
	 * private static final StringBuilder UPDATE_REFRIGERANT_MASTER = new
	 * StringBuilder(
	 * "update refrigerantmaster set linenumber=(select connectionnumber from outdoorunits where refrigcircuitgroupoduid='0' and refrigcircuitno='%d' and adapters_id = '%d')  where refrigerantid='%d';"
	 * );
	 */

	private static final StringBuilder INSERT_REFRIGERANT_MASTER = new StringBuilder(
			"insert into refrigerantmaster (refrigerantid,adapterid,siteid,linenumber) values (:refrigerant_id,:adapter_id,:site_id,:connection_number)");
	
	
	private static final StringBuilder INSERT_METAREFRIGERANT = new StringBuilder(
			"insert into metarefrigerant (refid,rated_efficiency_cooling,rated_efficiency_heating,rated_capacity,AveRatedEfficiency) values (:refID,:RatecoolEffi,:ratHeatEffi,:rateCapRef,:avg_ratEffi)");


	private static final StringBuilder SELECT_REFRIGERANT_MASTER = new StringBuilder(
			"select refid from refrigerantmaster where refrigerantid = :refrigerant_id and adapterid=:adapter_id and linenumber=:connection_number");


	private static final StringBuilder SAVE_ODU_REFID = new StringBuilder(
			"update outdoorunits set refrigerantid='%d'where oid='%s'");

	private static final StringBuilder GET_REF_ID = new StringBuilder(
			"select refid from  refrigerantmaster where adapterid=:adapter_id and linenumber=:connection_number and refrigerantid=:refrigerantid");
			

	private static final StringBuilder UPDATE_ODU_REF_ID = new StringBuilder(
			"update outdoorunits set refid=:refID where adapters_id=:adapter_id and connectionnumber=:con_number and refrigerantid=:ref_cir_num");
	

	private static final StringBuilder GET_REF_ID_META_REF = new StringBuilder(
			"select refid from  metarefrigerant where refid=:refID");

	private static final StringBuilder GET_REF_ID_ODU_CA = new StringBuilder(
			"select DISTINCT refid from outdoorunits where adapters_id=:adapter_id");

	

	private static final StringBuilder GET_REF_ID_ODU_CA_META_ID = new StringBuilder(
			"select metaoutdoorunit_id from outdoorunits where refid = :refId");
	

	/*
	 * private static final StringBuilder GET_ODU_RATED_PARAMS = new StringBuilder(
			"select rated_cooling_capacity,rated_heating_capacity,rated_cooling_power,rated_heating_power from metaoutdoorunits where id in :MetaODu");
	*/
	
	
	private static final StringBuilder GET_ODU_RATED_PARAMS = new StringBuilder(
			"select rated_cooling_capacity,rated_heating_capacity,rated_cooling_power,rated_heating_power from metaoutdoorunits where id in :MetaODu");
	
	
	

	
	private static final StringBuilder GET_ALL_RATED_PARAMS = new StringBuilder(
			"select round((SUM(round((rated_cooling_capacity/rated_cooling_power) - 0.005, 2))/:total_OD)-0.005,2) as final_cool_effi,round((SUM(round((rated_heating_capacity/rated_heating_power) - 0.005, 2))/:total_OD)-0.005,2) as final_heat_effi,round((SUM(rated_heating_capacity)+SUM(rated_cooling_capacity))-0.005,2) as final_rated_cap,round(((round((SUM(round((rated_cooling_capacity/rated_cooling_power) - 0.005, 2))/:total_OD)-0.005,2)+round((SUM(round((rated_heating_capacity/rated_heating_power) - 0.005, 2))/:total_OD)-0.005,2))/2) - 0.005, 2) as final_avg_effi from metaoutdoorunits where id in :MetaODu");
	
	
	@Override
	public Boolean UpdateRetrieveToplogy(List<OduData> ODU, List<IduData> IDU,
			List<Topology> PLS) {
		int ODUresult = 0, IDUResult = 0, PLSResult = 0;
		boolean flag = false;
		/*System.out.println("indies dao impl");
		flag = Delete_Topology.Delete_Topology(3);*/
		//TopologyDeleteSqlDao.Delete_Topology(BigInteger.valueOf(3));

		
		

		if (ODU != null && ODU.size() > 0) {

			for (OduData OduData : ODU) {
				int foid = OduData.getId();
				String DeviceName = OduData.getDeviceName();

				String SQL_QUERY1 = UPDATE_TOPOLGY_ODU_ONLY.toString();
				SQL_QUERY1 = String.format(SQL_QUERY1, DeviceName, foid);
				System.out.println(SQL_QUERY1);
				ODUresult = executeSQLUpdateQuery(SQL_QUERY1);
			}

		}

		if (IDU != null && IDU.size() > 0) {

			for (IduData IduData : IDU) {
				int fiid = IduData.getId();
				String dname = IduData.getDeviceName();

				String SQL_QUERY1 = UPDATE_TOPOLGY_IDU_ONLY.toString();
				SQL_QUERY1 = String.format(SQL_QUERY1, dname, fiid);
				System.out.println(SQL_QUERY1);
				IDUResult = executeSQLUpdateQuery(SQL_QUERY1);
			}

		}

		if (PLS != null && PLS.size() > 0) {

			for (Topology PlsData : PLS) {

				int fpid = PlsData.getId();
				String meter_name = PlsData.getDeviceName();
				String SQL_QUERY1 = UPDATE_TOPOLGY_PLS_ONLY.toString();
				SQL_QUERY1 = String.format(SQL_QUERY1, meter_name, fpid);
				PLSResult = executeSQLUpdateQuery(SQL_QUERY1);
			}
		}

		if (ODUresult > 0 || IDUResult > 0 || PLSResult > 0) {
			flag = true;
		}

		return flag;
	}

	/* Modifed by Ravi */
	@SuppressWarnings("unused")
	@Override
	public boolean InsertRetrieveToplogy(List<OduData> ODU, List<IduData> IDU,
			List<Topology> PLS, int adapter_id, String site_id) {
		List<?> resultList1 = new ArrayList<>();
		List<?> resultList = new ArrayList<>();
		List<?> resultList2 = new ArrayList<>();
		List<?> resultList3 = new ArrayList<>();
		List<?> resultList4 = new ArrayList<>();
		List<?> resultList5 = new ArrayList<>();
		List<?> resultList6 = new ArrayList<>();
		List<?> resultList7 = new ArrayList<>();
		List<?> resultList8 = new ArrayList<>();

		Session session = null;
		Transaction tx = null;
		Long rs = null;

		Long MetaId = null;
		Boolean output = true;

		try {
			session = getCurrentSession();
			tx = session.beginTransaction();
			// tx.setTimeout(10);

			// added by pramod

			if (ODU != null && ODU.size() > 0) {
				try {
					for (OduData OduData : ODU) {

						Metaoutdoorunit M_ODU = new Metaoutdoorunit();
						Outdoorunit odu = new Outdoorunit();
						Refrigerantmaster refm = new Refrigerantmaster();
						Adapter ca = new Adapter();
						ca.setId((long) adapter_id);

						Group group = new Group();
						group.setUniqueid(site_id);

						M_ODU.setRated_cooling_capacity(OduData
								.getRated_Cool_Capacity());
						M_ODU.setRated_heating_capacity(OduData
								.getRated_Heat_Capacity());
						M_ODU.setRated_cooling_power(OduData
								.getRated_Cool_Power());
						M_ODU.setRated_heating_power(OduData
								.getRated_Heat_Power());

						session.save(M_ODU);
						MetaId = (Long) session.save(M_ODU);
						M_ODU.setId(MetaId);
						odu.setMetaoutdoorunit(M_ODU);

						odu.setAdapter(ca);
						odu.setRefrigcircuitgroupoduid(OduData
								.getRefrigCircuitGroupOduId());
						odu.setRefrigcircuitno(OduData.getRefrigCircuitId());
						odu.setDevice_model(OduData.getModel());
						odu.setOid(OduData.getFacilityId());
						odu.setSlinkaddress(OduData.getS_link());
						odu.setType(OduData.getCategory());
						odu.setConnectionnumber(OduData.getConnectionNumber());
						odu.setName(OduData.getDeviceName());
						odu.setRefrigerantid(OduData.getRefrigCircuitId());
						odu.setConnectiontype(Long.parseLong(
						OduData.getConnectionType(), 10));
						odu.setGroup(group);
						session.save(odu);

						/*String SQL_QUERY = SELECT_REFRIGERANT_MASTER.toString();
						SQL_QUERY = String.format(SQL_QUERY,OduData.getRefrigCircuitId(), adapter_id,OduData.getConnectionNumber());
						resultList = executeSQLSelect(SQL_QUERY);*/
						
						
						
						Query query = session.createSQLQuery(SELECT_REFRIGERANT_MASTER.toString())
								.setParameter("refrigerant_id", OduData.getRefrigCircuitId())
								.setParameter("adapter_id", adapter_id)
								.setParameter("connection_number", OduData.getConnectionNumber());
								resultList = query.list();
								
								

						if (resultList.isEmpty()) {
							/*String SQL_QUERY1 = INSERT_REFRIGERANT_MASTER
									.toString();
							SQL_QUERY1 = String.format(SQL_QUERY1,
									OduData.getRefrigCircuitId(), adapter_id,
									site_id, OduData.getConnectionNumber());
							resultList = executeSQLSelect(SQL_QUERY1);*/
							
							
							
							Query query1 = session.createSQLQuery(INSERT_REFRIGERANT_MASTER.toString())
									.setParameter("refrigerant_id", OduData.getRefrigCircuitId())
									.setParameter("adapter_id", adapter_id)
									.setParameter("site_id", site_id)
									.setParameter("connection_number", OduData.getConnectionNumber());
									query1.executeUpdate();
									
									
							//logger.info(resultList);

						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (IDU != null && IDU.size() > 0) {
				for (IduData IduData : IDU) {

					Indoorunit idu = new Indoorunit();

					Adapter ca = new Adapter();
					ca.setId((long) adapter_id);

					Group group = new Group();
					group.setUniqueid(site_id);

					Metaindoorunit midu = new Metaindoorunit();

					idu.setAdapter(ca);
					idu.setName(IduData.getDeviceName());
					idu.setCentralControlAddress(IduData
							.getCentralControlAddress());
					idu.setConnectionnumber(IduData.getConnectionNumber());
					idu.setDevice_model(IduData.getModel());
					idu.setOid(IduData.getFacilityId());
					idu.setMainiduaddress(IduData.getMainIduAddress());
					idu.setSlinkaddress(IduData.getS_link());
					idu.setConnectiontype(IduData.getConnectionType());
					idu.setRefrigcircuitno(IduData.getRefrigCircuitId());
					idu.setConnectionaddress(IduData.getConnectionIduAddress());
					idu.setSite(group);

					BigInteger company_id = null;
					if (adapter_id > 0) {
						/*String SQL_QUERY_COM = SELECT_TOPOLGY_COM.toString();
						SQL_QUERY_COM = String
								.format(SQL_QUERY_COM, adapter_id);
						System.out.println(SQL_QUERY_COM);
						resultList1 = executeSQLSelect(SQL_QUERY_COM);*/
						
						Query query1 = session.createSQLQuery(SELECT_TOPOLGY_COM.toString()).setParameter("adapter_id", adapter_id);
						resultList1 = query1.list();

						
						Object[] rowData = null;
						try {
							if (!resultList1.isEmpty()
									&& resultList1.size() > 0) {
								logger.debug("resultList in getStatusInfoByFacl method"
										+ resultList1.toString());
								Iterator<?> itr = resultList1.iterator();

								while (itr.hasNext()) {
									rowData = (Object[]) itr.next();
									company_id = (BigInteger) rowData[0];
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					String idu_odu_parent = null;

					if (ODU != null && ODU.size() > 0) {
						for (OduData OduData : ODU) {
							if (IduData.getOdu_facility_id().equalsIgnoreCase(
									OduData.getFacilityId())) {
								idu_odu_parent = OduData.getFacilityId();
							}
						}
					}
					Metaindoorunit meta = null;
					try {
						meta = caConfigDAO.getStatusInfoByFacl(
								IduData.getFacilityId(), idu_odu_parent,
								adapter_id, site_id, company_id.toString());
						meta.setCreationdate(new Date());
						meta.setUpdatedate(new Date());
						meta.setUpdatedby("Topology");

						rs = (Long) session.save(meta);
						midu.setId(rs);

						idu.setMetaindoorunit(midu);

						session.save(idu);
					}

					catch (PersistenceException e) {
						logger.info("this is PersistenceException exception throw");
						rs = null;
					} catch (HibernateException ex) {
						logger.error(
								"Hibernate Exception: Status info Property ID not found",
								ex);

						rs = null;
					} catch (RuntimeException ex) {
						logger.error(
								"RunTime Exception: Status info Property ID not found",
								ex);
						rs = null;
					} catch (Exception ex) {
						logger.error(
								"Exception: Status info Property ID not found",
								ex);
						rs = null;
					} catch (Error e) {
						logger.error(
								"Error: Status info Property ID not found", e);
						rs = null;
					}

					if (rs == null) {

						tx.rollback();
						session.close();
						output = false;

						Class<?> thisClass = null;
						// StringBuilder sb = new StringBuilder();
						String s = "";
						try {
							thisClass = Class
									.forName(meta.getClass().getName());

							Field[] aClassFields = thisClass
									.getDeclaredFields();
							// sb.append(this.getClass().getSimpleName() +
							// " [ ");
							for (Field f : aClassFields) {
								// String fName = f.getName();
								// sb.append("(" + f.getType() + ") " + fName +
								// " = " + f.get(this) + ", ");

								if (!java.lang.reflect.Modifier.isFinal(f
										.getModifiers())) {

									if (!f.getName().equals("logo")) {
										if (!f.getName().equals("modelname")) {
											if (!f.getName().equals("id")) {
												if (!f.getName().equals(
														"serialVersionUID")) {
													if (!f.getName().equals(
															"indoorunits")) {

														Method m = null;
														try {

															String fn = f
																	.getName();
															m = thisClass
																	.getMethod(
																			"get"
																					+ Character
																							.toUpperCase(fn
																									.charAt(0))
																					+ fn.substring(1),
																			null);

															try {
																Object result = (Object) m
																		.invoke(meta,
																				null); // field
																						// value
																if (result == null) {

																	logger.debug("The field is not found in Platform DB: "
																			+ f.getName());

																}
															} catch (IllegalAccessException iae) {
																iae.printStackTrace();
															} catch (InvocationTargetException ite) {
																ite.printStackTrace();
															}

														} catch (NoSuchMethodException nsme) {
															nsme.printStackTrace();
														}

													}
												}
											}
										}
									}

								}
							}
							// sb.append("]");
						} catch (Exception e) {
							e.printStackTrace();
						}

						break;
					}
				}

			}

			if (PLS != null && PLS.size() > 0 && rs != null) {

				for (Topology PlsData : PLS) {

					Pulsemeter pm = new Pulsemeter();

					Adapter ca = new Adapter();
					ca.setId((long) adapter_id);

					pm.setAdapter(ca);
					pm.setConnection_type(PlsData.getConnectionType());
					pm.setMeter_name(PlsData.getDeviceName());
					pm.setOid(PlsData.getFacilityId());
					pm.setPort_number(Integer.parseInt(PlsData.getPort_number()));
					pm.setDevice_model(PlsData.getModel());
					pm.setMeter_type(PlsData.getMetertype());
					pm.setMulti_factor(PlsData.getMulti_factor());

					session.save(pm);
				}
			}
			/* Modifed by Srinivas */
			if (rs != null) {
				session.flush();
				session.clear();
				tx.commit();
				List refCirNumber = new ArrayList();
				// int OutDoor_Number = 0;

				// After commit doing update of ODU
				for (OduData OduData : ODU) {
					String Parent_id = OduData.getParent_id();
					logger.debug("ODU parent ID" + Parent_id);
					String facility_id = OduData.getFacilityId();

					/*String SQL_QUERY_REF_ID = GET_REF_ID.toString();
					SQL_QUERY_REF_ID = String.format(SQL_QUERY_REF_ID,
							adapter_id, OduData.getConnectionNumber(),
							OduData.getRefrigCircuitId());
					resultList3 = executeSQLSelect(SQL_QUERY_REF_ID);*/
					
					
					Query query1 = session.createSQLQuery(GET_REF_ID.toString()).setParameter("adapter_id", adapter_id)
							.setParameter("refrigerantid", OduData.getRefrigCircuitId()).setParameter("connection_number", OduData.getConnectionNumber());
					resultList3 = query1.list();
					

					Object refId = resultList3.get(0);
					int refID = Integer.valueOf(String.valueOf(refId));

					/*String SQL_QUERY = UPDATE_ODU_REF_ID.toString();
					SQL_QUERY = String.format(SQL_QUERY, refID, adapter_id,
							OduData.getConnectionNumber(),
							OduData.getRefrigCircuitId());
					resultList2 = executeSQLSelect(SQL_QUERY);*/
					
					Query query2 = session.createSQLQuery(UPDATE_ODU_REF_ID.toString()).setParameter("refID", refID).setParameter("adapter_id", adapter_id)
							.setParameter("con_number", OduData.getConnectionNumber())
							.setParameter("ref_cir_num", OduData.getRefrigCircuitId());
					query2.executeUpdate();
					

					/*String SQL_QUERY_REF_ID_META_REF = GET_REF_ID_META_REF
							.toString();
					SQL_QUERY_REF_ID_META_REF = String.format(
							SQL_QUERY_REF_ID_META_REF, refID);
					resultList4 = executeSQLSelect(SQL_QUERY_REF_ID_META_REF);*/
					
					
					Query query3 = session.createSQLQuery(GET_REF_ID_META_REF.toString()).setParameter("refID", refID);
					resultList4 = query3.list();
					

					if (resultList4.size() == 0) {
						/*String SQL_QUERY1 = INSERT_METAREFRIGERANT.toString();
						SQL_QUERY1 = String.format(SQL_QUERY1, refID,
								OduData.getRatedCoolEffi(),
								OduData.getRatedHeatEffi(),
								OduData.getRatedCapRef(),
								OduData.getAvgRatedEffi());
						resultList = executeInsertSQLSelect(SQL_QUERY1);*/
						Query query5 = session.createSQLQuery(INSERT_METAREFRIGERANT.toString()).setParameter("refID", refID)
								.setParameter("RatecoolEffi", OduData.getRatedCoolEffi())
								.setParameter("ratHeatEffi", OduData.getRatedHeatEffi())
								.setParameter("rateCapRef", OduData.getRatedCapRef())
								.setParameter("avg_ratEffi", OduData.getAvgRatedEffi());
						query5.executeUpdate();
						
					}
					
					if (Parent_id != null) {
						/*String SQL_QUERY2 = UPDATE_TOPOLGY_ODU_PARENT
								.toString();
						SQL_QUERY2 = String.format(SQL_QUERY2, Parent_id,
								facility_id);

						System.out.println(SQL_QUERY2);
						// resultList1 = executeSQLSelect(SQL_QUERY2);
						executeSQLUpdateQuery(SQL_QUERY2);*/
						
						
						Query query6 = session.createSQLQuery(UPDATE_TOPOLGY_ODU_PARENT.toString()).setParameter("Parent_id", Parent_id)
								.setParameter("facility_id", facility_id);
						query6.executeUpdate();
						
					}

					/*
					 * if(!refCirNumber.contains(OduData.getRefrigCircuitId()))
					 * { refCirNumber.add(OduData.getRefrigCircuitId()); }
					 */

				}
				// added by pramod
				/*String SQL_QUERY_REF_ID_ODU_CA = GET_REF_ID_ODU_CA.toString();
				SQL_QUERY_REF_ID_ODU_CA = String.format(
						SQL_QUERY_REF_ID_ODU_CA, adapter_id);
				resultList5 = executeSQLSelect(SQL_QUERY_REF_ID_ODU_CA);*/
				
				
				Query query6 = session.createSQLQuery(GET_REF_ID_ODU_CA.toString()).setParameter("adapter_id", adapter_id);
				resultList5 = query6.list();
				

				for (int i = 0; i < resultList5.size(); i++) {
					Object RefId = resultList5.get(i);
					Integer refId = Integer.valueOf(String.valueOf(RefId));

					/*String SQL_QUERY_GET_REF_ID_ODU_CA_META_ID = GET_REF_ID_ODU_CA_META_ID
							.toString();
					SQL_QUERY_GET_REF_ID_ODU_CA_META_ID = String.format(
							SQL_QUERY_GET_REF_ID_ODU_CA_META_ID, refId);
					resultList6 = executeSQLSelect(SQL_QUERY_GET_REF_ID_ODU_CA_META_ID);*/
					
					Query query7 = session.createSQLQuery(GET_REF_ID_ODU_CA_META_ID.toString()).setParameter("refId", refId);
					resultList6 = query7.list();
					
					
					Query query8 = session.createSQLQuery(GET_ODU_RATED_PARAMS.toString()).setParameterList("MetaODu", resultList6);
					resultList7 = query8.list();
					int NumOdus=resultList7.size();
					
					
					Query query9 = session.createSQLQuery(GET_ALL_RATED_PARAMS.toString()).setParameter("total_OD", NumOdus).setParameterList("MetaODu", resultList6);
					resultList8 = query9.list();
					
					System.out.println(resultList8);
					System.out.println(resultList8.get(0));
					System.out.println(resultList8.iterator());
					
					Iterator<?> itr1 = resultList8.iterator();
					Object[] rowdata1 = null;
					
					while (itr1.hasNext()) {
						

						rowdata1 = (Object[]) itr1.next();

						System.out.println(rowdata1[0]);
						
						Query query10 = session.createSQLQuery(UPDATE_METAREFRIGERANT.toString())
								.setParameter("RefCoolingEfficiencyFinal", rowdata1[0])
								.setParameter("RefHeatingEfficiencyFinal", rowdata1[1])
								.setParameter("Sum_Total_Ref_Capacity", rowdata1[2])
								.setParameter("avg_effi__Final", rowdata1[3])
								.setParameter("refId", refId);
						query10.executeUpdate();
						
						
						
					}
					
				
					
					
					//String MetaODu = CommonUtil.convertCollectionToString(resultList6);

					/*String SQL_QUERY_META_IDS = GET_ODU_RATED_PARAMS.toString();
					SQL_QUERY_META_IDS = String.format(SQL_QUERY_META_IDS,MetaODu);
					resultList7 = executeSQLSelect(SQL_QUERY_META_IDS);*/

					
					
					/*Query query8 = session.createSQLQuery(GET_ODU_RATED_PARAMS.toString()).setParameterList("MetaODu", resultList6);
					resultList7 = query8.list();
					int NumOdus=resultList7.size();
					
					BigDecimal Sum_Rcooling_Capacity = new BigDecimal(0);
					BigDecimal Sum_Rcooling_Power = new BigDecimal(0);
					BigDecimal Sum_RHeating_Capacity = new BigDecimal(0);
					BigDecimal Sum_RHeating_Power = new BigDecimal(0);
					
					BigDecimal Sum_Total_Ref_Capacity= new BigDecimal(0);
					BigDecimal Sum_Total_Ref_Avg_Efficiency= new BigDecimal(0);
					BigDecimal avg_effi_constant = new BigDecimal(2.0);
					
					BigDecimal avg_effi__Final = new BigDecimal(2.0);
					
					
					
					int NumOdus=resultList7.size();
					BigDecimal bd2 = new BigDecimal(NumOdus);
					
					for (int j = 0; j < resultList7.size(); j++) {

						Iterator<?> itr1 = resultList7.iterator();
						Object[] rowdata1 = null;

						

						while (itr1.hasNext()) {
							

								rowdata1 = (Object[]) itr1.next();

								BigDecimal R_Cooling = (BigDecimal) rowdata1[0];
								BigDecimal R_Cooling_Power = (BigDecimal) rowdata1[2];

								Sum_Rcooling_Capacity=Sum_Rcooling_Capacity.add(R_Cooling);
								Sum_Rcooling_Power = Sum_Rcooling_Power.add(R_Cooling_Power);
								
								BigDecimal R_Heat = (BigDecimal) rowdata1[1];
								BigDecimal R_Heat_Power = (BigDecimal) rowdata1[3];

								
								Sum_RHeating_Capacity=Sum_RHeating_Capacity.add(R_Heat);
								Sum_RHeating_Power = Sum_RHeating_Power.add(R_Heat_Power);
								
						}
					}
					
					
					Sum_Total_Ref_Capacity=Sum_Rcooling_Capacity.add(Sum_RHeating_Capacity);
					
					System.out.println("Sum_Total_Ref_Capacity "+Sum_Total_Ref_Capacity);
					
				
					
					BigDecimal RefCoolingEfficiency=(Sum_Rcooling_Capacity.divide(Sum_Rcooling_Power,2, RoundingMode.FLOOR));
					
					BigDecimal RefHeatingEfficiency=(Sum_RHeating_Capacity.divide(Sum_RHeating_Power,2, RoundingMode.FLOOR));
					
							
					BigDecimal RefCoolingEfficiencyFinal=RefCoolingEfficiency.divide(bd2,2, RoundingMode.FLOOR);
					
					BigDecimal RefHeatingEfficiencyFinal=RefHeatingEfficiency.divide(bd2,2, RoundingMode.FLOOR);
					
					
					Sum_Total_Ref_Avg_Efficiency=	RefCoolingEfficiencyFinal.add(RefHeatingEfficiencyFinal);
					avg_effi__Final=Sum_Total_Ref_Avg_Efficiency.divide(avg_effi_constant,2, RoundingMode.FLOOR);
					
					System.out.println("RefCoolingEfficiencyFinal  "+RefCoolingEfficiencyFinal);
					System.out.println("RefHeatingEfficiencyFinal  "+RefHeatingEfficiencyFinal);
					
		
					
					
					Query query9 = session.createSQLQuery(UPDATE_METAREFRIGERANT.toString())
							.setParameter("RefCoolingEfficiencyFinal", RefCoolingEfficiencyFinal)
							.setParameter("RefHeatingEfficiencyFinal", RefHeatingEfficiencyFinal)
							.setParameter("Sum_Total_Ref_Capacity", Sum_Total_Ref_Capacity)
							.setParameter("avg_effi__Final", avg_effi__Final)
							.setParameter("refId", refId);
					query9.executeUpdate();
					
					*/
					
					

				}

				/*
				 * for (int j = 0; j < refCirNumber.size(); j++) {
				 * 
				 * String SQL_QUERY2 = UPDATE_METAREFRIGERANT.toString();
				 * SQL_QUERY2 =
				 * String.format(SQL_QUERY2,refCirNumber.get(j),refCirNumber
				 * .get(j),refCirNumber.get(j));
				 * executeSQLUpdateQuery(SQL_QUERY2);
				 * 
				 * 
				 * String SQL_QUERY3 = UPDATE_REFRIGERANT_MASTER.toString();
				 * SQL_QUERY3 =
				 * String.format(SQL_QUERY3,refCirNumber.get(j),adapter_id
				 * ,refCirNumber.get(j)); System.out.println(SQL_QUERY3);
				 * executeSQLUpdateQuery(SQL_QUERY3);
				 * 
				 * 
				 * }
				 */

				// After commit doing update of IDU
				for (IduData IduData : IDU) {
					String Parent_id = IduData.getParent_id();
					System.out.println(Parent_id);
					String facility_id = IduData.getFacilityId();
					String refcircuitid = IduData.getRefrigCircuitId();
					String connection_number = IduData.getConnectionNumber();

					if (Parent_id != null) {
						System.out.println(Parent_id);
						/*String SQL_QUERY2 = UPDATE_TOPOLGY_IDU_PARENT
								.toString();

						SQL_QUERY2 = String.format(SQL_QUERY2, Parent_id,
								refcircuitid, adapter_id, Parent_id,
								facility_id);
								executeSQLUpdateQuery(SQL_QUERY2);
						System.out.println(SQL_QUERY2);*/
						
						
						Query query9 = session.createSQLQuery(UPDATE_TOPOLGY_IDU_PARENT.toString())
								.setParameter("Parent_id", Parent_id)
								.setParameter("refcircuitid", refcircuitid)
								.setParameter("adapter_id", adapter_id)
								.setParameter("facility_id", facility_id)
								.setParameter("connection_number", connection_number);
						query9.executeUpdate();
						

						// "update indoorunits set parent_id=(select ID from indoorunits where connectionaddress='%s' and mainIduAddress='addr001' and refcicuitid='%s') where mainIduAddress='%s' and oid='%s'");

						// resultList1 = executeSQLSelect(SQL_QUERY2);
						

					}

					String out_id = IduData.getOdu_facility_id();
					System.out.println(out_id);
					if (out_id != null || !out_id.equalsIgnoreCase("")) {
						/*String SQL_QUERY3 = UPDATE_TOPOLGY_IDU_ODU_PARENT
								.toString();
						SQL_QUERY3 = String.format(SQL_QUERY3, out_id, out_id,
								facility_id);
						// System.out.println(SQL_QUERY3);

						// resultList1 = executeSQLSelect(SQL_QUERY3);
						executeSQLUpdateQuery(SQL_QUERY3);*/
						
						
						Query query9 = session.createSQLQuery(UPDATE_TOPOLGY_IDU_ODU_PARENT.toString())
								.setParameter("out_id", out_id)
								.setParameter("facility_id", facility_id);
						query9.executeUpdate();
						
					}
				}

			}
		} catch (RuntimeException e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (RuntimeException rbe) {
				logger.error(
						"Couldnâ€™t roll back transaction, already roll backed",
						rbe);
			}
			throw e;
		} finally {
			try {
				if (session != null) {
					session.close();
				}
			} catch (HibernateException e) {
				logger.error("Couldnâ€™t close session, already closed", e);
			}
		}

		return output;
	}

	@Override
	public List UpdateRetrieveToplogyIdu(List<IduData> PLS, int adapter_id,
			String site_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List UpdateRetrieveToplogyOdu(List<OduData> ODU, int adapter_id,
			String site_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * private static final StringBuilder UPDATE_RETRIEVE_TOPOLGY_IDU = new
	 * StringBuilder( "Update indoorunits set name='%s' where id='%s'"); private
	 * static final StringBuilder UPDATE_RETRIEVE_TOPOLGY_ODU = new
	 * StringBuilder( "Update outdoorunits set name='%s' where id='%s'");
	 * private static final StringBuilder UPDATE_RETRIEVE_TOPOLGY_PLS = new
	 * StringBuilder( "Update pulse_meter set meter_name='%s' where id='%s'");
	 * 
	 * 
	 * @Override public List
	 * Update_Retrieve_Toplogy_Odu_Idu_Pls(List<Integer>Idu_Ids,List<String>
	 * Idu_Dnames,List<Integer> Odu_Ids,List<String> Odu_Dnames,List<Integer>
	 * Pls_Ids,List<String> Pls_Dnames) {
	 * 
	 * List<List> resultList = new ArrayList<>(); List<?> resultList1 = new
	 * ArrayList<>(); List<?> resultList2 = new ArrayList<>(); List<?>
	 * resultList3 = new ArrayList<>();
	 * 
	 * 
	 * if (Idu_Ids != null && Idu_Ids.size() > 0 && Idu_Dnames != null &&
	 * Idu_Dnames.size()>0) { Iterator<?> iduids = Idu_Ids.iterator();
	 * Iterator<?> itr = Idu_Ids.iterator(); Object[] rowData = null;
	 * 
	 * try{ while (itr.hasNext()) {
	 * 
	 * String SQL_QUERY = SQ_REG_CUST_SITE.toString(); SQL_QUERY =
	 * String.format(SQL_QUERY,customer_name,userId,itr.next()); resultList =
	 * executeSQLSelect(SQL_QUERY); }
	 * 
	 * return true; } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } }
	 * 
	 * 
	 * 
	 * return resultList1;
	 * 
	 * 
	 * }
	 */

	/*
	 * @Override public boolean register_dao_cust_siteid(List<Integer> site_id,
	 * String customer_name, Long userId) {
	 * 
	 * Group group =new Group(); Companiesuser companiesuser = new
	 * Companiesuser();
	 * 
	 * 
	 * List<?> resultList = null; List<Object> cloud_adapters = new
	 * ArrayList<>();
	 * 
	 * 
	 * 
	 * if (site_id != null && site_id.size() > 0) { Iterator<?> itr =
	 * site_id.iterator(); Object[] rowData = null;
	 * 
	 * try{ while (itr.hasNext()) {
	 * 
	 * String SQL_QUERY = SQ_REG_CUST_SITE.toString(); SQL_QUERY =
	 * String.format(SQL_QUERY,customer_name,userId,itr.next()); resultList =
	 * executeSQLSelect(SQL_QUERY);
	 * 
	 * }
	 * 
	 * return true; } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } }
	 * 
	 * return false;
	 * 
	 * }
	 */

	@Override
	public String create_new_DistributionGroup(String DistributionName,
			int CostomerId, String type, String calMode) {
		List<?> resultList1 = null;

		String SQL_QUERY = INSERT_DISTRIBUTION_GROUP.toString();
		SQL_QUERY = String.format(SQL_QUERY, DistributionName, CostomerId,
				type, calMode);
		int id = executeSQLUpdateQuery(SQL_QUERY);
		return "success" + id;
	}

	public void updatePlusmeterTableByDisGroup(String id, String facilityId,
			int dist_grp) {
		String updateSql = "update pulse_meter set distribution_group_id = "
				+ dist_grp + "  where oid = '" + facilityId + "' and id=" + id;
		int executeSQLUpdateQuery = executeSQLUpdateQuery(updateSql);
		logger.info(executeSQLUpdateQuery);
	}

	public void updateIduTableByDisGroup(String id, String facilityId,
			int dist_grp) {
		String updateSql = "update indoorunits set distribution_group_id = "
				+ dist_grp + "  where oid = '" + facilityId + "' and id=" + id;
		int executeSQLUpdateQuery = executeSQLUpdateQuery(updateSql);
		logger.info(executeSQLUpdateQuery);
	}

}
