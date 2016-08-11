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
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.cr.vo.PowerRatio;
import com.panasonic.b2bacns.bizportal.dao.GenericDAO;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.persistence.DistributionRatioData;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;

/**
 * @author simanchal.patra
 *
 */
@Service
public class DistributionRatioDAOServiceImpl implements
		DistributionRatioDAOservice {

	/** Logger instance. **/
	private static final Logger logger = Logger
			.getLogger(DistributionRatioDAOServiceImpl.class);

	private GenericDAO<DistributionRatioData> dao;

	@Autowired
	public void setDAO(GenericDAO<DistributionRatioData> daoToSet) {
		logger.debug("daoToSet " + daoToSet);
		dao = daoToSet;
		dao.setClazz(DistributionRatioData.class);
	}

	@Autowired
	private SQLDAO sqldao;

	private static final StringBuilder SQL_GET_DIST_RATIO_DATA = new StringBuilder(
			"select g.name as site, dg.group_name as distributiongroup, idu.id as indoorunitid, idu.slinkaddress as iduid,")
			.append(" a.name as area, a.id as areaid, idu.name as iduname, pdr.powerdistribution_ratio,")
			.append(" (CASE WHEN idu.id IS NOT NULL THEN pdr.powerusage_kwh  ELSE NULL END) powerusage_kwh,")
			.append(" pdr.cutoffstart_actual_time,  pdr.cutoffend_actual_time,")
			.append(" (select * from usp_allparentofsite(cast (g.id as integer))) as sitegroup,")
			.append(" pdr.pulsemeter_id as pulsemeterid,  pm.meter_name as pulsemetername,")
			.append(" (CASE WHEN pdr.pulsemeter_id  IS NOT NULL THEN pdr.powerusage_kwh  ELSE NULL END) pulsermeterPowerUsage, dg.id as distribution_group_id")
			.append(" from distribution_ratio_data pdr")
			.append(" left outer join  indoorunits idu on idu.id = pdr.indoorunit_id")
			.append(" Left outer join cutoff_request cr on cr.id = pdr.cutoffreq_id")
			.append(" Left outer join pulse_meter  pm on pm.id = pdr.pulsemeter_id")
			.append(" Left outer join distribution_group dg on dg.id = (CASE WHEN pdr.pulsemeter_id  IS NOT NULL THEN pm.distribution_group_id ELSE idu.distribution_group_id END)")
			.append(" Left outer join area a on a.id = idu.area_id")
			.append(" Left Outer join groups g on idu.siteid = g.uniqueid")
			.append(" Left outer join distribution_detail_data pddd on pddd.cutoffreq_id = pdr.cutoffreq_id")
			.append(" and  pddd.indoorunit_id = pdr.indoorunit_id")
			.append(" where cr.platformtransaction_id = %d")
			.append(" order by distribution_group_id, areaid, indoorunitid");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#create
	 * (java.lang.Object)
	 */
	@Transactional
	@Override
	public Object create(DistributionRatioData entity) {

		return dao.create(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * saveOrUpdate(java.lang.Object)
	 */
	@Transactional
	@Override
	public void saveOrUpdate(DistributionRatioData entity) {
		dao.saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#findAll
	 * ()
	 */
	@Transactional
	@Override
	public List<DistributionRatioData> findAll() {

		return dao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#update
	 * (java.lang.Object)
	 */
	@Transactional
	@Override
	public void update(DistributionRatioData entity) {
		dao.update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#delete
	 * (java.lang.Object)
	 */
	@Transactional
	@Override
	public void delete(DistributionRatioData entity) {
		dao.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * deleteById(long)
	 */
	@Transactional
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
	@Transactional
	@Override
	public DistributionRatioData findByID(long id) {

		return dao.findByID(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.lang.String, java.lang.Object)
	 */
	@Transactional
	@Override
	public List<DistributionRatioData> findAllByProperty(String propertyName,
			Object value) {

		return dao.findAllByProperty(propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findAllByProperty(java.util.HashMap)
	 */
	@Transactional
	@Override
	public List<DistributionRatioData> findAllByProperty(
			HashMap<String, Object> properties) {

		return dao.findAllByProperty(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.panasonic.b2bacns.bizportal.stats.dao.service.GenericDAOService#
	 * findBySortCriteria(java.util.Map, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public List<DistributionRatioData> findBySortCriteria(
			Map<String, Object> criteriaMap, String orderBy,
			String orderByPropertyName) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void merge(DistributionRatioData entity) throws HibernateException {
		dao.merge(entity);
	}

	@Override
	public Criteria createCriteria() {

		return dao.createCriteria();
	}

	@Override
	public void batchSaveOrUpdate(List<DistributionRatioData> entities)
			throws HibernateException {
		dao.batchSaveOrUpdate(entities);
	}

	@Override
	public void batchDelete(List<DistributionRatioData> entities)
			throws HibernateException {
		dao.batchDelete(entities);
	}

	@Override
	@Transactional
	public List<PowerRatio> getPowerRatioData(Long transactionId) {

		String query = String.format(SQL_GET_DIST_RATIO_DATA.toString(),
				transactionId);

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<>();

		scalarMapping.put("site", StandardBasicTypes.STRING);
		scalarMapping.put("distributiongroup", StandardBasicTypes.STRING);
		scalarMapping.put("indoorunitid", StandardBasicTypes.LONG);
		scalarMapping.put("iduid", StandardBasicTypes.STRING);
		scalarMapping.put("area", StandardBasicTypes.STRING);
		scalarMapping.put("areaid", StandardBasicTypes.LONG);
		scalarMapping.put("iduname", StandardBasicTypes.STRING);
		scalarMapping.put("powerdistribution_ratio",
				StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("powerusage_kwh", StandardBasicTypes.BIG_DECIMAL);
		scalarMapping.put("cutoffstart_actual_time", StandardBasicTypes.STRING);
		scalarMapping.put("cutoffend_actual_time", StandardBasicTypes.STRING);
		scalarMapping.put("sitegroup", StandardBasicTypes.STRING);
		scalarMapping.put("pulsemeterid", StandardBasicTypes.LONG);
		scalarMapping.put("pulsemetername", StandardBasicTypes.STRING);
		scalarMapping.put("pulsermeterPowerUsage",
				StandardBasicTypes.BIG_DECIMAL);
		// scalarMapping.put("pmdisplay_id", StandardBasicTypes.STRING);
		scalarMapping.put("distribution_group_id", StandardBasicTypes.LONG);

		List<?> resultList = null;

		List<PowerRatio> powerRatioDataList = null;

		try {
			resultList = sqldao.executeSQLSelect(query, scalarMapping);
		} catch (Exception e) {
			logger.error("Error occured while fetching data"
					+ " for Power Ratio Report for Platform Transaction ID - "
					+ transactionId, e);
		}

		try {
			if (resultList != null && resultList.size() > 0) {
				powerRatioDataList = generatePowerRatioReportData(resultList);
			}
		} catch (Exception e) {
			logger.error("Error occured while preparing report data"
					+ " for Power Ratio Report for Platform Transaction ID - "
					+ transactionId, e);

		}
		return powerRatioDataList;
	}

	private List<PowerRatio> generatePowerRatioReportData(List<?> resultList)
			throws Exception {

		List<PowerRatio> powerRatioDataList = new ArrayList<>();

		PowerRatio iduRecord = null;
		PowerRatio areaRecord = null;
		PowerRatio pulseMeterRecord = null;
		PowerRatio totalDistGroupRecord = null;
		Long dGroup = null;
		List<PowerRatio> IduNoAreaList = null;

		Map<Long, List<PowerRatio>> dgroupAndIduNoAreaList = new HashMap<Long, List<PowerRatio>>();

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

				dGroup = (Long) rowData[15];

				if (dGroup == null) {
					throw new UnsupportedOperationException(
							"Distribution group should not be null for Cutoff Distibution reports");
				}

				if (oldDistGroupID == null) {
					oldDistGroupID = dGroup;
				}

				if (oldDistGroupID != dGroup) {
					// Current Distribution group is different than previous one
					// Insert IDUs without Area and Pulse Meter

					if (!dgroupAndIduNoAreaList.isEmpty()) {
						// There are IDUs without Area
						for (PowerRatio iduWOArea : dgroupAndIduNoAreaList
								.get(oldDistGroupID)) {
							// insert IDUs
							powerRatioDataList.add(iduWOArea);
						}
						dgroupAndIduNoAreaList.remove(oldDistGroupID);

						totalDistGroupRecord
								.setSite(getSiteNameCommaSeparated(dgSites));

						setDGSiteGroups(totalDistGroupRecord, dgSitegroups);

						powerRatioDataList.add(totalDistGroupRecord);

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

					if (!dgSitegroups.contains((String) rowData[11])) {
						dgSitegroups.add((String) rowData[11]);
					}

					// Sum values of all IDUs for total value of
					// this Distribution-group
					totalDistGroupRecord
							.setPowerUsage(((totalDistGroupRecord
									.getPowerUsage() != null ? convertToBigDecimal(totalDistGroupRecord
									.getPowerUsage()) : BigDecimal.ZERO)
									.add(convertToBigDecimal(iduRecord
											.getPowerUsage()))).toPlainString());
					totalDistGroupRecord
							.setDistributionRatio(((totalDistGroupRecord
									.getDistributionRatio() != null ? convertToBigDecimal(totalDistGroupRecord
									.getDistributionRatio()) : BigDecimal.ZERO)
									.add(convertToBigDecimal(iduRecord
											.getDistributionRatio())))
									.toPlainString());

				}

				if (rowData[5] != null && iduRecord != null) {
					// Area present

					if (oldAreaId.equals((Long) rowData[5])) {
						// Area ID is same as Previous Area ID

						// insert IDU
						powerRatioDataList.add(iduRecord);

						if (areaRecord == null) {
							areaRecord = getNewAreaRecord(
									iduRecord.getDistributionGroup(),
									String.valueOf(rowData[4]));
							areaSites = new ArrayList<>();
						}

						if (!areaSites.contains(iduRecord.getSite())) {
							areaSites.add(iduRecord.getSite());
						}

						// sum the ratio and usage of IDUs belong to this Area
						areaRecord
								.setPowerUsage(((areaRecord.getPowerUsage() != null ? convertToBigDecimal(areaRecord
										.getPowerUsage()) : BigDecimal.ZERO)
										.add(convertToBigDecimal(iduRecord
												.getPowerUsage())))
										.toPlainString());
						areaRecord
								.setDistributionRatio(((areaRecord
										.getDistributionRatio() != null ? convertToBigDecimal(areaRecord
										.getDistributionRatio())
										: BigDecimal.ZERO)
										.add(convertToBigDecimal(iduRecord
												.getDistributionRatio())))
										.toPlainString());

					} else {
						// Area ID is Different than Previous Area ID
						if (areaRecord != null) {

							areaRecord
									.setSite(getSiteNameCommaSeparated(areaSites));

							// So, Insert record for the previous Area
							powerRatioDataList.add(areaRecord);

							// As inserted, reset
							areaRecord = null;
							areaSites = new ArrayList<>();
						}

						if (iduRecord != null) {
							powerRatioDataList.add(iduRecord);
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
						areaRecord
								.setPowerUsage(((areaRecord.getPowerUsage() != null ? convertToBigDecimal(areaRecord
										.getPowerUsage()) : BigDecimal.ZERO)
										.add(convertToBigDecimal(iduRecord
												.getPowerUsage())))
										.toPlainString());
						areaRecord
								.setDistributionRatio(((areaRecord
										.getDistributionRatio() != null ? convertToBigDecimal(areaRecord
										.getDistributionRatio())
										: BigDecimal.ZERO)
										.add(convertToBigDecimal(iduRecord
												.getDistributionRatio())))
										.toPlainString());

					}

					// As it is inserted, reset
					iduRecord = null;
				} else {
					// Area is null

					if (areaRecord != null) {

						areaRecord.setSite(getSiteNameCommaSeparated(areaSites)
								.toString());

						// So, Insert record for the previous Area
						powerRatioDataList.add(areaRecord);

						// As inserted, reset
						areaRecord = null;
					}

					// check if it is pulse meter or IDU having no area assigned
					if (rowData[12] != null) {
						// This record is pulse meter

						if (!dgroupAndIduNoAreaList.isEmpty()) {
							for (PowerRatio iduWOArea : dgroupAndIduNoAreaList
									.get(dGroup)) {
								powerRatioDataList.add(iduWOArea);
							}
							dgroupAndIduNoAreaList.remove(dGroup);
						}

						// Create & Insert new pulse meter
						pulseMeterRecord = getNewPulseMeterRecord(rowData);

						pulseMeterRecord
								.setSite(getSiteNameCommaSeparated(dgSites));

						powerRatioDataList.add(pulseMeterRecord);

						totalDistGroupRecord
								.setSite(getSiteNameCommaSeparated(dgSites));

						setDGSiteGroups(totalDistGroupRecord, dgSitegroups);

						powerRatioDataList.add(totalDistGroupRecord);

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
								IduNoAreaList = new ArrayList<PowerRatio>();
								IduNoAreaList.add(iduRecord);
								dgroupAndIduNoAreaList.put(dGroup,
										IduNoAreaList);
							}
						} else {
							// should, never happen
							throw new Exception("How this could be possibele");
						}
					}

				}
			}

			// For the last distribution group without pulse meter
			if (!dgroupAndIduNoAreaList.isEmpty()) {
				// There are IDUs without Area
				for (PowerRatio iduWOArea : dgroupAndIduNoAreaList.get(dGroup)) {
					// insert IDUs
					powerRatioDataList.add(iduWOArea);
				}
				dgroupAndIduNoAreaList.remove(dGroup);

				totalDistGroupRecord
						.setSite(getSiteNameCommaSeparated(dgSites));

				setDGSiteGroups(totalDistGroupRecord, dgSitegroups);

				powerRatioDataList.add(totalDistGroupRecord);

				// Reset for next distribution group total
				totalDistGroupRecord = null;

				// reset oldDistGroupID
				oldDistGroupID = null;
			}

		} else {
			logger.debug("No Power Ratio data retrived to create report");
		}

		return powerRatioDataList;
	}

	/**
	 * @param totalDistGroupRecord
	 * @param distGroupPowerRatio
	 * @param distGroupPowerUsage
	 * @param rowData
	 * @return
	 */
	private PowerRatio getNewDistributionGroupTotalRecord(Object[] rowData) {

		PowerRatio totalDistGroupRecord = new PowerRatio();

		totalDistGroupRecord.setSite(null);
		totalDistGroupRecord.setDistributionGroup(rowData[1] != null ? String
				.valueOf(rowData[1]) : BizConstants.HYPHEN);
		//change by shanf
		totalDistGroupRecord.setIduId("Total:");
		totalDistGroupRecord.setPulseMeterId(BizConstants.HYPHEN);
		totalDistGroupRecord.setArea(BizConstants.HYPHEN);
		totalDistGroupRecord.setIduName(BizConstants.HYPHEN);
		totalDistGroupRecord.setDistributionRatio(null);
		totalDistGroupRecord.setPowerUsage(null);
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
	private PowerRatio getNewPulseMeterRecord(Object[] rowData)
			throws Exception {

		PowerRatio pulseMeterRecord = new PowerRatio();

		pulseMeterRecord.setSite(BizConstants.HYPHEN);
		pulseMeterRecord.setDistributionGroup(rowData[1] != null ? String
				.valueOf(rowData[1]) : BizConstants.HYPHEN);
		pulseMeterRecord.setIduId(BizConstants.HYPHEN);
		pulseMeterRecord.setArea(BizConstants.HYPHEN);
		pulseMeterRecord.setPulseMeterId(rowData[12] != null ? String
				.valueOf(rowData[12]) : BizConstants.HYPHEN);
		pulseMeterRecord.setIduName(rowData[13] != null ? String
				.valueOf(rowData[13]) : BizConstants.HYPHEN);
		pulseMeterRecord.setDistributionRatio(BizConstants.HYPHEN);
		pulseMeterRecord
				.setPowerUsage(rowData[14] != null ? convertToBigDecimal(
						String.valueOf(rowData[14])).toPlainString()
						: BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel1(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel2(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel3(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel4(BizConstants.HYPHEN);
		pulseMeterRecord.setSiteGroupLevel5(BizConstants.HYPHEN);

		pulseMeterRecord
				.setCutOffStartDateTime(rowData[9] != null ? (CommonUtil.dateToString(
						CommonUtil.stringToDate(String.valueOf(rowData[9]),
								BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
						: BizConstants.HYPHEN);
		pulseMeterRecord.setCutOffEndDateTime(rowData[10] != null ? (CommonUtil
				.dateToString(CommonUtil.stringToDate(
						String.valueOf(rowData[10]),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
				: BizConstants.HYPHEN);
		return pulseMeterRecord;
	}

	/**
	 * @param iduRecord
	 * @param sqlData
	 * @return
	 * @throws Exception
	 */
	private PowerRatio getNewIDURecord(Object[] sqlData) throws Exception {

		PowerRatio iduRecord = new PowerRatio();

		iduRecord.setSite(sqlData[0] != null ? String.valueOf(sqlData[0])
				: BizConstants.HYPHEN);
		iduRecord.setDistributionGroup(sqlData[1] != null ? String
				.valueOf(sqlData[1]) : BizConstants.HYPHEN);
		//change by shanf
		iduRecord.setIduId(sqlData[3] != null ? "=\""+String.valueOf(sqlData[3])+"\""
				: BizConstants.HYPHEN);
		iduRecord.setPulseMeterId(BizConstants.HYPHEN);
		iduRecord.setArea(BizConstants.HYPHEN);
		iduRecord.setIduName(sqlData[6] != null ? String.valueOf(sqlData[6])
				: BizConstants.HYPHEN);
		iduRecord
				.setDistributionRatio(sqlData[7] != null ? convertToBigDecimal(
						String.valueOf(sqlData[7])).toPlainString()
						: BizConstants.HYPHEN);

		iduRecord.setPowerUsage(sqlData[8] != null ? convertToBigDecimal(
				String.valueOf(sqlData[8])).toPlainString()
				: BizConstants.HYPHEN);
		iduRecord.setCutOffStartDateTime(sqlData[9] != null ? (CommonUtil
				.dateToString(CommonUtil.stringToDate(
						String.valueOf(sqlData[9]),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
				: BizConstants.HYPHEN);
		iduRecord.setCutOffEndDateTime(sqlData[10] != null ? (CommonUtil
				.dateToString(CommonUtil.stringToDate(
						String.valueOf(sqlData[10]),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS),
						BizConstants.DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD))
				: BizConstants.HYPHEN);

		String[] siteGroupLevels = Arrays.copyOf(StringUtils.split(
				String.valueOf(sqlData[11]), BizConstants.COMMA_STRING), 5);

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
	private PowerRatio getNewAreaRecord(String dstributionGroupName,
			String areaName) {

		PowerRatio areaRecord = new PowerRatio();
		areaRecord.setSite(null);
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

	/**
	 * @param areaSites
	 * @return
	 */
	private String getSiteNameCommaSeparated(List<String> sites) {
		if (sites == null) {
			throw new UnsupportedOperationException(
					"No Site/SiteGroup defined for distribution group");
		}
		return sites.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}

	private void setDGSiteGroups(PowerRatio totalDistGroupRecord,
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

	private BigDecimal convertToBigDecimal(String bigDecimalStr)
			throws Exception {

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		String pattern = "###.##";

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
}
