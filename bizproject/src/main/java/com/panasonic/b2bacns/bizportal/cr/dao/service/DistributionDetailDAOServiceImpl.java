/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.dao.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.cr.vo.PowerDetail;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.persistence.DistributionDetailData;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author simanchal.patra
 *
 */
@Service
public class DistributionDetailDAOServiceImpl implements
		DistributionDetailDAOservice {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(DistributionDetailDAOServiceImpl.class);

	private GenericDAO<DistributionDetailData> dao;

	@Autowired
	public void setDAO(GenericDAO<DistributionDetailData> daoToSet) {
		logger.debug("daoToSet " + daoToSet);
		dao = daoToSet;
		dao.setClazz(DistributionDetailData.class);
	}

	@Autowired
	private SQLDAO sqldao;

	private static final StringBuilder SQL_GET_DIST_DETAIL_DATA = new StringBuilder(
			"select g.name as site,dg.group_name as distributiongroup, idu.id as indoorunitid, idu.slinkaddress as iduid,  a.name as area,")
			.append(" a.id as areaid, idu.name as iduname, pm.id as pulsemeterid, meter_name as pulsemetername, pddr.ratedcapacity_kw, pddr.workinghours_tstat_onhigh_fan,")
			.append(" pddr.workinghours_tstat_on_med_fan , pddr.workinghours_tstat_on_low_fan ,")
			.append(" pddr.workinghours_tstat_off_high_fan , pddr.workinghours_tstat_off_med_fan ,")
			.append(" pddr.workinghours_tstat_off_low_fan ,  pddr.powerusage_kwh, pddr.cutoffstart_actual_time, pddr.cutoffend_actual_time,")
			.append(" (CASE WHEN g.id IS NOT NULL THEN (select * from usp_allparentofsite(cast (g.id as integer))) ELSE NULL END) sitegroup,")
			.append(" pddr.pulsemeter_power_usage as pulsermeterPowerUsage, adp.name || '-' ||pm.port_number as pmdisplay_id, dg.id  from distribution_detail_data pddr")
			.append(" left outer join  indoorunits idu on idu.id = pddr.indoorunit_id")
			.append(" Left outer join cutoff_request cr on cr.id = pddr.cutoffreq_id")
			.append(" Left outer join pulse_meter pm on pddr.pulsemeter_id = pm.id")
			.append(" Left outer join distribution_group dg on dg.id = (CASE WHEN pddr.pulsemeter_id  IS NOT NULL THEN pm.distribution_group_id ELSE idu.distribution_group_id END)")
			.append(" Left outer join area a on a.id = idu.area_id")
			.append(" Left Outer join groups g on idu.siteid = g.uniqueid")
			.append(" Left outer join distribution_ratio_data drd on pddr.cutoffreq_id = drd.cutoffreq_id")
			.append(" and  drd.indoorunit_id = pddr.indoorunit_id")
			.append(" Left outer join adapters adp on adp.id =  pm.adapters_id ")
			.append(" where cr.platformtransaction_id  = %d")
			.append(" order by dg.id, areaid, indoorunitid");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#create
	 * (java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public Object create(DistributionDetailData entity) {

		return dao.create(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * saveOrUpdate(java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void saveOrUpdate(DistributionDetailData entity) {
		dao.saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#findAll
	 * ()
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<DistributionDetailData> findAll() {

		return dao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#update
	 * (java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void update(DistributionDetailData entity) {
		dao.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#delete
	 * (java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void delete(DistributionDetailData entity) {
		dao.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * deleteById(long)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void deleteById(long entityId) {
		dao.deleteById(entityId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#findByID
	 * (long)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public DistributionDetailData findByID(long id) {

		return dao.findByID(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.lang.String, java.lang.Object)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<DistributionDetailData> findAllByProperty(String propertyName,
			Object value) {

		return dao.findAllByProperty(propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.util.HashMap)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<DistributionDetailData> findAllByProperty(
			HashMap<String, Object> properties) {

		return dao.findAllByProperty(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findBySortCriteria(java.util.Map, java.lang.String, java.lang.String)
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public List<DistributionDetailData> findBySortCriteria(
			Map<String, Object> criteriaMap, String orderBy,
			String orderByPropertyName) {

		throw new UnsupportedOperationException();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void merge(DistributionDetailData entity) throws HibernateException {
		dao.merge(entity);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public Criteria createCriteria() {

		return dao.createCriteria();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void batchSaveOrUpdate(List<DistributionDetailData> entities)
			throws HibernateException {
		dao.batchSaveOrUpdate(entities);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void batchDelete(List<DistributionDetailData> entities)
			throws HibernateException {
		dao.batchDelete(entities);
	}

	@Override
	public List<PowerDetail> getPowerDetailData(Long transactionId) {

		String query = String.format(SQL_GET_DIST_DETAIL_DATA.toString(),
				transactionId);

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<>();

		scalarMapping.put("site", StandardBasicTypes.STRING);
		scalarMapping.put("distributiongroup", StandardBasicTypes.STRING);
		scalarMapping.put("indoorunitid", StandardBasicTypes.LONG);
		scalarMapping.put("iduid", StandardBasicTypes.STRING);
		scalarMapping.put("area", StandardBasicTypes.STRING);
		scalarMapping.put("areaid", StandardBasicTypes.LONG);
		scalarMapping.put("iduname", StandardBasicTypes.STRING);
		scalarMapping.put("pulsemeterid", StandardBasicTypes.LONG);
		scalarMapping.put("pulsemetername", StandardBasicTypes.STRING);
		scalarMapping.put("ratedcapacity_kw", StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("workinghours_tstat_onhigh_fan",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("workinghours_tstat_on_med_fan",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("workinghours_tstat_on_low_fan",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("workinghours_tstat_off_high_fan",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("workinghours_tstat_off_med_fan",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("workinghours_tstat_off_low_fan",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("powerusage_kwh", StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("cutoffstart_actual_time", StandardBasicTypes.STRING);
		scalarMapping.put("cutoffend_actual_time", StandardBasicTypes.STRING);
		scalarMapping.put("sitegroup", StandardBasicTypes.STRING);
		scalarMapping.put("pulsermeterPowerUsage",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("pmdisplay_id", StandardBasicTypes.STRING);
		scalarMapping.put("id", StandardBasicTypes.LONG);

		List<?> resultList = null;
		try {
			resultList = sqldao.executeSQLSelect(query, scalarMapping);
		} catch (Exception e) {
			logger.error("Error occured while retriving Power Detail report"
					+ " Data for PF transaction ID - " + transactionId, e);
		}

		List<PowerDetail> powerDetailData = null;

		try {

			if (resultList != null && resultList.size() > 0) {
				powerDetailData = generatePowerDistDetailsReportData(resultList);
			}
		} catch (Exception e) {
			logger.error("Error occured while generation Power Detail report"
					+ " Data for PF transaction ID - " + transactionId, e);
		}

		return powerDetailData;
	}

	private List<PowerDetail> generatePowerDistDetailsReportData(
			List<?> resultList) throws Exception {

		List<PowerDetail> powerDetailDataList = new ArrayList<>();

		PowerDetail iduRecord = null;
		PowerDetail areaRecord = null;
		PowerDetail pulseMeterRecord = null;
		PowerDetail totalDistGroupRecord = null;
		Long dGroup = null;
		List<PowerDetail> IduNoAreaList = null;

		Map<Long, List<PowerDetail>> dgroupAndIduNoAreaList = new HashMap<Long, List<PowerDetail>>();

		if (!resultList.isEmpty()) {

			Iterator<?> itr = resultList.iterator();
			Object[] rowData = null;
			Long oldAreaId = null;
			Long oldDistGroupID = null;

			List<String> areaSites = null;
			List<String> dgSites = null;
			List<String> dgSitegroups = null;

			while (itr.hasNext()) {

				rowData = (Object[]) itr.next();

				dGroup = (Long) rowData[22];

				if (dGroup == null) {
					throw new UnsupportedOperationException(
							"To generate Cutoff Distibution reports"
									+ " Distribution-Group should not be null, IDU Id "
									+ (Long) rowData[2]);
				}

				if (oldDistGroupID == null) {
					oldDistGroupID = dGroup;
				}

				if (oldDistGroupID != dGroup) {
					// Current Distribution group is different than previous one
					// Insert IDUs without Area and Pulse Meter

					if (!dgroupAndIduNoAreaList.isEmpty()) {

						// There are IDUs without Area
						for (PowerDetail iduWOArea : dgroupAndIduNoAreaList
								.get(oldDistGroupID)) {
							// insert IDUs
							powerDetailDataList.add(iduWOArea);
						}
						dgroupAndIduNoAreaList.remove(oldDistGroupID);

						totalDistGroupRecord
								.setSite(getSiteNameCommaSeparated(dgSites));

						setDGSiteGroups(totalDistGroupRecord, dgSitegroups);

						powerDetailDataList.add(totalDistGroupRecord);

						// Reset for next distribution group total
						totalDistGroupRecord = null;
					}
					// reset oldDistGroupID
					oldDistGroupID = dGroup;
				}

				if (rowData[2] != null) {
					// This record is IDU

					if (oldAreaId == null && rowData[5] != null) {
						oldAreaId = (Long) rowData[5];
					}

					iduRecord = getNewIDURecord(rowData);

					// Create & Insert Total of this Distribution group
					if (totalDistGroupRecord == null) {
						totalDistGroupRecord = getNewDistributionGroupTotalRecord(rowData);
						dgSites = new ArrayList<>();
						dgSitegroups = new ArrayList<>();

					}

					if (!dgSites.contains(iduRecord.getSite())) {
						dgSites.add(iduRecord.getSite());
					}

					if (!dgSitegroups.contains((String) rowData[19])) {
						dgSitegroups.add((String) rowData[19]);
					}

					// Sum values of all IDUs for total value of
					// this Distribution-group
					totalDistGroupRecord.setRatedCapacity(calculateParamValue(
							totalDistGroupRecord.getRatedCapacity(),
							iduRecord.getRatedCapacity()));
					totalDistGroupRecord
							.setPowerUsageIndexes(calculateParamValue(
									totalDistGroupRecord.getPowerUsageIndexes(),
									iduRecord.getPowerUsageIndexes()));
					totalDistGroupRecord.setTsOnHighFan(calculateParamValue(
							totalDistGroupRecord.getTsOnHighFan(),
							iduRecord.getTsOnHighFan()));
					totalDistGroupRecord.setTsOnMedFan(calculateParamValue(
							totalDistGroupRecord.getTsOnMedFan(),
							iduRecord.getTsOnMedFan()));
					totalDistGroupRecord.setTsOnLowFan(calculateParamValue(
							totalDistGroupRecord.getTsOnLowFan(),
							iduRecord.getTsOnLowFan()));
					totalDistGroupRecord.setTsOffHighFan(calculateParamValue(
							totalDistGroupRecord.getTsOffHighFan(),
							iduRecord.getTsOffHighFan()));
					totalDistGroupRecord.setTsOffMedFan(calculateParamValue(
							totalDistGroupRecord.getTsOffMedFan(),
							iduRecord.getTsOffMedFan()));
					totalDistGroupRecord.setTsOffLowFan(calculateParamValue(
							totalDistGroupRecord.getTsOffLowFan(),
							iduRecord.getTsOffLowFan()));
				}

				if (rowData[5] != null && iduRecord != null) {
					// Area present

					if (oldAreaId.equals((Long) rowData[5])) {
						// Area ID is same as Previous Area ID

						// insert IDU
						powerDetailDataList.add(iduRecord);

						if (areaRecord == null) {
							areaRecord = getNewAreaRecord(
									iduRecord.getDistributionGroup(),
									String.valueOf(rowData[4]));
							areaSites = new ArrayList<>();
						}

						if (areaSites != null
								&& !areaSites.contains(iduRecord.getSite())) {
							areaSites.add(iduRecord.getSite());
						}

						// sum the ratio and usage of IDUs belong to this Area
						areaRecord.setRatedCapacity(calculateParamValue(
								areaRecord.getRatedCapacity(),
								iduRecord.getRatedCapacity()));
						areaRecord.setPowerUsageIndexes(calculateParamValue(
								areaRecord.getPowerUsageIndexes(),
								iduRecord.getPowerUsageIndexes()));
						areaRecord.setTsOnHighFan(calculateParamValue(
								areaRecord.getTsOnHighFan(),
								iduRecord.getTsOnHighFan()));
						areaRecord.setTsOnMedFan(calculateParamValue(
								areaRecord.getTsOnMedFan(),
								iduRecord.getTsOnMedFan()));
						areaRecord.setTsOnLowFan(calculateParamValue(
								areaRecord.getTsOnLowFan(),
								iduRecord.getTsOnLowFan()));
						areaRecord.setTsOffHighFan(calculateParamValue(
								areaRecord.getTsOffHighFan(),
								iduRecord.getTsOffHighFan()));
						areaRecord.setTsOffMedFan(calculateParamValue(
								areaRecord.getTsOffMedFan(),
								iduRecord.getTsOffMedFan()));
						areaRecord.setTsOffLowFan(calculateParamValue(
								areaRecord.getTsOffLowFan(),
								iduRecord.getTsOffLowFan()));

					} else {
						// Area ID is Different than Previous Area ID
						if (areaRecord != null) {

							areaRecord
									.setSite(getSiteNameCommaSeparated(areaSites));

							// So, Insert record for the previous Area
							powerDetailDataList.add(areaRecord);

							// As inserted, reset
							areaRecord = null;
							areaSites = new ArrayList<>();
						}

						if (iduRecord != null) {
							powerDetailDataList.add(iduRecord);
						}

						// update Area ID
						oldAreaId = (Long) rowData[5];

						// Create Area record for this IDU
						areaRecord = getNewAreaRecord(
								iduRecord.getDistributionGroup(),
								String.valueOf(rowData[4]));

						if (!areaSites.contains(iduRecord.getSite())) {
							areaSites.add(iduRecord.getSite());
						}

						// sum the ratio and usage of IDUs belong to this
						// Area
						areaRecord.setRatedCapacity(calculateParamValue(
								areaRecord.getRatedCapacity(),
								iduRecord.getRatedCapacity()));
						areaRecord.setPowerUsageIndexes(calculateParamValue(
								areaRecord.getPowerUsageIndexes(),
								iduRecord.getPowerUsageIndexes()));
						areaRecord.setTsOnHighFan(calculateParamValue(
								areaRecord.getTsOnHighFan(),
								iduRecord.getTsOnHighFan()));
						areaRecord.setTsOnMedFan(calculateParamValue(
								areaRecord.getTsOnMedFan(),
								iduRecord.getTsOnMedFan()));
						areaRecord.setTsOnLowFan(calculateParamValue(
								areaRecord.getTsOnLowFan(),
								iduRecord.getTsOnLowFan()));
						areaRecord.setTsOffHighFan(calculateParamValue(
								areaRecord.getTsOffHighFan(),
								iduRecord.getTsOffHighFan()));
						areaRecord.setTsOffMedFan(calculateParamValue(
								areaRecord.getTsOffMedFan(),
								iduRecord.getTsOffMedFan()));
						areaRecord.setTsOffLowFan(calculateParamValue(
								areaRecord.getTsOffLowFan(),
								iduRecord.getTsOffLowFan()));
					}

					// As it is inserted, reset
					iduRecord = null;
				} else {
					// Area is null

					if (areaRecord != null) {

						areaRecord.setSite(getSiteNameCommaSeparated(areaSites)
								.toString());

						// So, Insert record for the previous Area
						powerDetailDataList.add(areaRecord);

						// As inserted, reset
						areaRecord = null;
					}

					// check if it is pulse meter or IDU having no area assigned
					if (rowData[7] != null) {
						// This record is pulse meter

						if (!dgroupAndIduNoAreaList.isEmpty()) {
							for (PowerDetail iduWOArea : dgroupAndIduNoAreaList
									.get(dGroup)) {
								powerDetailDataList.add(iduWOArea);
							}
							dgroupAndIduNoAreaList.remove(dGroup);
						}

						// Create & Insert new pulse meter
						pulseMeterRecord = getNewPulseMeterRecord(rowData);

						pulseMeterRecord
								.setSite(getSiteNameCommaSeparated(dgSites));

						powerDetailDataList.add(pulseMeterRecord);

						if (totalDistGroupRecord != null) {

							totalDistGroupRecord
									.setSite(getSiteNameCommaSeparated(dgSites));

							setDGSiteGroups(totalDistGroupRecord, dgSitegroups);
						}

						powerDetailDataList.add(totalDistGroupRecord);

						// Reset for next Pulsemeter
						pulseMeterRecord = null;

						// Reset for next distribution group total
						totalDistGroupRecord = null;

					} else if (rowData[2] != null) {
						// This is an IDU not assigned to any Area, i.e. Area
						// for this IDU is Null

						if (iduRecord != null) {
							if (dgroupAndIduNoAreaList.containsKey(dGroup)) {
								dgroupAndIduNoAreaList.get(dGroup).add(
										iduRecord);
							} else {
								IduNoAreaList = new ArrayList<PowerDetail>();
								IduNoAreaList.add(iduRecord);
								dgroupAndIduNoAreaList.put(dGroup,
										IduNoAreaList);
							}
						} else {
							// should, never happen
							throw new Exception(
									"Unknown type in Distribution Detail report");
						}
					}

				}
			}

			// For the last distribution group without pulse meter
			if (!dgroupAndIduNoAreaList.isEmpty()) {
				// Create & Insert Total of this Distribution group
				if (totalDistGroupRecord == null) {
					totalDistGroupRecord = getNewDistributionGroupTotalRecord(rowData);
					dgSites = new ArrayList<>();
					dgSitegroups = new ArrayList<>();

				}
				// There are IDUs without Area
				for (PowerDetail iduWOArea : dgroupAndIduNoAreaList.get(dGroup)) {
					// insert IDUs
					powerDetailDataList.add(iduWOArea);
				}
				dgroupAndIduNoAreaList.remove(dGroup);

				totalDistGroupRecord
						.setSite(getSiteNameCommaSeparated(dgSites));

				setDGSiteGroups(totalDistGroupRecord, dgSitegroups);

				powerDetailDataList.add(totalDistGroupRecord);

				// Reset for next distribution group total
				totalDistGroupRecord = null;

				// reset oldDistGroupID
				oldDistGroupID = null;
			}

		} else {
			logger.debug("No Power Detail data retrived to create report");
		}

		return powerDetailDataList;
	}

	private void setDGSiteGroups(PowerDetail totalDistGroupRecord,
			List<String> dgSitegroups) {

		if (dgSitegroups.size() > 0) {

			String[] siteGroupLevels = Arrays.copyOf(StringUtils.split(
					String.valueOf(dgSitegroups.get(0)),
					BizConstants.COMMA_STRING), 5);

			totalDistGroupRecord
					.setSiteGroupLevel1(siteGroupLevels[0] != null ? siteGroupLevels[0]
							: BizConstants.HYPHEN);
			totalDistGroupRecord
					.setSiteGroupLevel2(siteGroupLevels[1] != null ? siteGroupLevels[1]
							: BizConstants.HYPHEN);
			totalDistGroupRecord
					.setSiteGroupLevel3(siteGroupLevels[2] != null ? siteGroupLevels[2]
							: BizConstants.HYPHEN);
			totalDistGroupRecord
					.setSiteGroupLevel4(siteGroupLevels[3] != null ? siteGroupLevels[3]
							: BizConstants.HYPHEN);
			totalDistGroupRecord
					.setSiteGroupLevel5(siteGroupLevels[4] != null ? siteGroupLevels[4]
							: BizConstants.HYPHEN);
		}

	}

	/**
	 * @param areaSites
	 * @return
	 */
	private String getSiteNameCommaSeparated(List<String> sites) {
		String siteNameCommaSeparated = null;
		if (sites != null && sites.size() > 0) {
			siteNameCommaSeparated = sites.toString().replaceAll("\\[", "")
					.replaceAll("\\]", "");
		}
		return siteNameCommaSeparated;
	}

	/**
	 * @param iduRecord
	 * @param areaRecord
	 * @return
	 * @throws Exception
	 */
	private String calculateParamValue(String areaRecordVal, String iduRecordVal)
			throws Exception {

		return ((areaRecordVal != null ? convertToBigDecimal(areaRecordVal)
				: BigDecimal.ZERO).add(convertToBigDecimal(iduRecordVal)))
				.toPlainString();
	}

	/**
	 * @param totalDistGroupRecord
	 * @param distGroupPowerDetail
	 * @param distGroupPowerUsage
	 * @param rowData
	 * @return
	 */
	private PowerDetail getNewDistributionGroupTotalRecord(Object[] rowData) {

		PowerDetail totalDistGroupRecord = new PowerDetail();

		totalDistGroupRecord.setSite(BizConstants.HYPHEN);
		totalDistGroupRecord.setDistributionGroup(rowData[1] != null ? String
				.valueOf(rowData[1]) : BizConstants.HYPHEN);
		//change by shanf
		totalDistGroupRecord.setIduId("Total:");
		totalDistGroupRecord.setArea(BizConstants.HYPHEN);
		totalDistGroupRecord.setIduName(BizConstants.HYPHEN);
		totalDistGroupRecord.setPulseMeterId(BizConstants.HYPHEN);
		totalDistGroupRecord.setSiteGroupLevel1(BizConstants.HYPHEN);
		totalDistGroupRecord.setSiteGroupLevel2(BizConstants.HYPHEN);
		totalDistGroupRecord.setSiteGroupLevel3(BizConstants.HYPHEN);
		totalDistGroupRecord.setSiteGroupLevel4(BizConstants.HYPHEN);
		totalDistGroupRecord.setSiteGroupLevel5(BizConstants.HYPHEN);

		totalDistGroupRecord.setCutOffStartDateTime(BizConstants.HYPHEN);
		totalDistGroupRecord.setCutOffEndDateTime(BizConstants.HYPHEN);
		return totalDistGroupRecord;
	}

	/**
	 * @param pulseMeterRecord
	 * @param rowData
	 * @return
	 * @throws Exception
	 */
	private PowerDetail getNewPulseMeterRecord(Object[] rowData)
			throws Exception {

		PowerDetail pulseMeterRecord = new PowerDetail();

		pulseMeterRecord.setSite(BizConstants.HYPHEN);
		pulseMeterRecord.setDistributionGroup(rowData[1] != null ? String
				.valueOf(rowData[1]) : BizConstants.HYPHEN);
		pulseMeterRecord.setIduId(BizConstants.HYPHEN);
		pulseMeterRecord.setArea(BizConstants.HYPHEN);
		pulseMeterRecord.setPulseMeterId(rowData[7] != null ? String
				.valueOf(rowData[7]) : BizConstants.HYPHEN);
		pulseMeterRecord.setIduName(rowData[8] != null ? String
				.valueOf(rowData[8]) : BizConstants.HYPHEN);
		pulseMeterRecord.setPowerUsageIndexes(convertToBigDecimal(
				String.valueOf(rowData[20])).toPlainString());
		pulseMeterRecord.setSiteGroupLevel1(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel2(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel3(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel4(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel5(BizConstants.HYPHEN);

		pulseMeterRecord
				.setCutOffStartDateTime(rowData[17] != null ? (CommonUtil.dateToString(
						CommonUtil.stringToDate(String.valueOf(rowData[17]),
								BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
						: BizConstants.HYPHEN);
		pulseMeterRecord.setCutOffEndDateTime(rowData[18] != null ? (CommonUtil
				.dateToString(CommonUtil.stringToDate(
						String.valueOf(rowData[18]),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
				: BizConstants.HYPHEN);

		pulseMeterRecord.setRatedCapacity(BizConstants.HYPHEN);
		pulseMeterRecord.setTsOnHighFan(BizConstants.HYPHEN);
		pulseMeterRecord.setTsOnMedFan(BizConstants.HYPHEN);
		pulseMeterRecord.setTsOnLowFan(BizConstants.HYPHEN);
		pulseMeterRecord.setTsOffHighFan(BizConstants.HYPHEN);
		pulseMeterRecord.setTsOffMedFan(BizConstants.HYPHEN);
		pulseMeterRecord.setTsOffLowFan(BizConstants.HYPHEN);

		return pulseMeterRecord;
	}

	/**
	 * @param iduRecord
	 * @param sqlData
	 * @return
	 * @throws Exception
	 */
	private PowerDetail getNewIDURecord(Object[] sqlData) throws Exception {

		PowerDetail iduRecord = new PowerDetail();

		iduRecord.setSite(sqlData[0] != null ? String.valueOf(sqlData[0])
				: BizConstants.HYPHEN);
		iduRecord.setDistributionGroup(sqlData[1] != null ? String
				.valueOf(sqlData[1]) : BizConstants.HYPHEN);
		//add by shanf
		iduRecord.setIduId(sqlData[3] != null ? "=\""+String.valueOf(sqlData[3])+"\""
				: BizConstants.HYPHEN);
		iduRecord.setPulseMeterId(BizConstants.HYPHEN);
		iduRecord.setArea(BizConstants.HYPHEN);
		iduRecord.setIduName(sqlData[6] != null ? String.valueOf(sqlData[6])
				: BizConstants.HYPHEN);
		iduRecord
				.setPowerUsageIndexes(sqlData[16] != null ? convertToBigDecimal(
						String.valueOf(sqlData[16])).toPlainString()
						: BizConstants.HYPHEN);
		iduRecord.setRatedCapacity(sqlData[9] != null ? convertToBigDecimal(
				String.valueOf(sqlData[9])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setTsOnHighFan(sqlData[10] != null ? convertToBigDecimal(
				String.valueOf(sqlData[10])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setTsOnMedFan(sqlData[11] != null ? convertToBigDecimal(
				String.valueOf(sqlData[11])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setTsOnLowFan(sqlData[12] != null ? convertToBigDecimal(
				String.valueOf(sqlData[12])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setTsOffHighFan(sqlData[13] != null ? convertToBigDecimal(
				String.valueOf(sqlData[13])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setTsOffMedFan(sqlData[14] != null ? convertToBigDecimal(
				String.valueOf(sqlData[14])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setTsOffLowFan(sqlData[15] != null ? convertToBigDecimal(
				String.valueOf(sqlData[15])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setCutOffStartDateTime(sqlData[17] != null ? (CommonUtil
				.dateToString(CommonUtil.stringToDate(
						String.valueOf(sqlData[17]),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
				: BizConstants.HYPHEN);
		iduRecord.setCutOffEndDateTime(sqlData[18] != null ? (CommonUtil
				.dateToString(CommonUtil.stringToDate(
						String.valueOf(sqlData[18]),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
				: BizConstants.HYPHEN);

		String[] siteGroupLevels = Arrays.copyOf(StringUtils.split(
				String.valueOf(sqlData[19]), BizConstants.COMMA_STRING), 5);

		iduRecord
				.setSiteGroupLevel1(siteGroupLevels[0] != null ? siteGroupLevels[0]
						: BizConstants.HYPHEN);
		iduRecord
				.setSiteGroupLevel2(siteGroupLevels[1] != null ? siteGroupLevels[1]
						: BizConstants.HYPHEN);
		iduRecord
				.setSiteGroupLevel3(siteGroupLevels[2] != null ? siteGroupLevels[2]
						: BizConstants.HYPHEN);
		iduRecord
				.setSiteGroupLevel4(siteGroupLevels[3] != null ? siteGroupLevels[3]
						: BizConstants.HYPHEN);
		iduRecord
				.setSiteGroupLevel5(siteGroupLevels[4] != null ? siteGroupLevels[4]
						: BizConstants.HYPHEN);

		return iduRecord;
	}

	/**
	 * @param dstributionGroupName
	 * @param areaName
	 * @return
	 */
	private PowerDetail getNewAreaRecord(String dstributionGroupName,
			String areaName) {

		PowerDetail areaRecord = new PowerDetail();
		areaRecord.setSite(BizConstants.HYPHEN);
		areaRecord.setDistributionGroup(dstributionGroupName);
		//change by shanf
		areaRecord.setIduId("Total:");
		areaRecord.setPulseMeterId(BizConstants.HYPHEN);
		areaRecord.setArea(areaName != null ? areaName : BizConstants.HYPHEN);
		areaRecord.setIduName(BizConstants.HYPHEN);
		areaRecord.setSiteGroupLevel1(BizConstants.HYPHEN);
		areaRecord.setSiteGroupLevel2(BizConstants.HYPHEN);
		areaRecord.setSiteGroupLevel3(BizConstants.HYPHEN);
		areaRecord.setSiteGroupLevel4(BizConstants.HYPHEN);
		areaRecord.setSiteGroupLevel5(BizConstants.HYPHEN);
		areaRecord.setCutOffStartDateTime(BizConstants.HYPHEN);
		areaRecord.setCutOffEndDateTime(BizConstants.HYPHEN);
		return areaRecord;
	}

	private static BigDecimal convertToBigDecimal(String bigDecimalStr)
			throws Exception {

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		String pattern = "###.#";

		BigDecimal val = BigDecimal.ZERO;

		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		if (bigDecimalStr != null && !bigDecimalStr.equals(BizConstants.HYPHEN)) {
			try {
				val = (BigDecimal) decimalFormat.parse(bigDecimalStr);
			} catch (ParseException e) {
				throw new Exception(
						String.format(
								"Error occured while converting String %s to BigDecimal",
								bigDecimalStr));
			}
		}

		return val;
	}

	public static void main(String[] args) {
		DistributionDetailDAOServiceImpl o = new DistributionDetailDAOServiceImpl();
		System.out.println(o.getSiteNameCommaSeparated(new ArrayList()));
	}
}
