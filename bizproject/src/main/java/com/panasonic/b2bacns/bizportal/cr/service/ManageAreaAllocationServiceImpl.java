/**
 * 
 */
package com.panasonic.b2bacns.bizportal.cr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.cr.dao.service.AreaDAOservice;
import com.panasonic.b2bacns.bizportal.cr.dao.service.DistributionGroupDAOservice;
import com.panasonic.b2bacns.bizportal.cr.vo.AreadAllocationDetails;
import com.panasonic.b2bacns.bizportal.cr.vo.IDUAreaMapping;
import com.panasonic.b2bacns.bizportal.cr.vo.IDUDsitributionGroupDetail;
import com.panasonic.b2bacns.bizportal.dao.SQLDAO;
import com.panasonic.b2bacns.bizportal.exception.BusinessFailureException;
import com.panasonic.b2bacns.bizportal.persistence.Area;
import com.panasonic.b2bacns.bizportal.persistence.DistributionGroup;
import com.panasonic.b2bacns.bizportal.persistence.Indoorunit;
import com.panasonic.b2bacns.bizportal.service.IndoorUnitsService;

/**
 * @author simanchal.patra
 *
 */
@Service
public class ManageAreaAllocationServiceImpl implements
		ManageAreaAllocationService {

	private static final Logger logger = Logger
			.getLogger(ManageAreaAllocationServiceImpl.class);
	//change by shanf
	private static final String GET_IDU_AGEA_DG_LIST = "select idu.id iduId, idu.slinkaddress iduAddress,"
			+ " idu.name as iduname, dg.id as dgid, dg.group_name as dggroupname, a.id as areaid,"
			+ " a.name as areaname, dg.type as category from indoorunits  idu"
			+ " inner join distribution_group dg on idu.distribution_group_id  =  dg.id"
			+ " left outer join area a on idu.area_id = a.id"
			+ " Where idu.distribution_group_id = %s";

	@Autowired
	private DistributionGroupDAOservice distribtionGroupService;

	@Autowired
	private IndoorUnitsService iduService;

	@Autowired
	private AreaDAOservice areaDAOService;

	@Autowired
	private SQLDAO sqlDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.cr.service.ManageAreaAllocationService
	 * #getAllocatedAreas(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public AreadAllocationDetails getAllocatedAreas(Long siteID,
			Long distributionGroupID) {

		AreadAllocationDetails areadAllocationDetails = new AreadAllocationDetails();

		List<IDUDsitributionGroupDetail> dgDetailList = new ArrayList<>();

		List<com.panasonic.b2bacns.bizportal.cr.vo.Area> areaVOList = new ArrayList<>();

		Criteria areaCriteria = areaDAOService.createCriteria();
		areaCriteria.add(Restrictions.eq("distributionGroup.id",
				distributionGroupID));
		List<Area> areaList = areaCriteria.list();

		com.panasonic.b2bacns.bizportal.cr.vo.Area areaVO = null;

		for (Area area : areaList) {
			areaVO = new com.panasonic.b2bacns.bizportal.cr.vo.Area();
			areaVO.setAreaId(area.getId());
			areaVO.setAreaName(area.getName());
			areaVOList.add(areaVO);
		}

		// Criteria iduCriteria = iduService.getCriteria();
		// iduCriteria.add(Restrictions.sqlRestriction());
		// iduCriteria.list();

		LinkedHashMap<String, Type> scalarMapping = new LinkedHashMap<>();
		scalarMapping.put("iduId", StandardBasicTypes.LONG);
		scalarMapping.put("iduAddress", StandardBasicTypes.STRING);
		scalarMapping.put("iduname", StandardBasicTypes.STRING);
		scalarMapping.put("dgid", StandardBasicTypes.LONG);
		scalarMapping.put("dggroupname", StandardBasicTypes.STRING);
		scalarMapping.put("areaid", StandardBasicTypes.LONG);
		scalarMapping.put("areaname", StandardBasicTypes.STRING);
		scalarMapping.put("category", StandardBasicTypes.STRING);

		List<?> resultList = sqlDAO.executeSQLSelect(
				String.format(GET_IDU_AGEA_DG_LIST, distributionGroupID),
				scalarMapping);

		if (resultList != null && !resultList.isEmpty()) {
			Object[] row = null;

			Iterator<?> resultIterator = resultList.listIterator();

			IDUDsitributionGroupDetail dgDetail = null;

			while (resultIterator.hasNext()) {
				row = (Object[]) resultIterator.next();
				dgDetail = new IDUDsitributionGroupDetail();
				dgDetailList.add(dgDetail);

				dgDetail.setIduId((Long) row[0]);
				dgDetail.setIduAddress((String) row[1]);
				dgDetail.setDeviceName((String) row[2]);
				dgDetail.setDistributionGroupID((Long) row[3]);
				dgDetail.setDistribution((String) row[4]);
				dgDetail.setAreaID((Long) row[5]);
				dgDetail.setAreaName((String) row[6]);
				dgDetail.setCategory((String) row[7]);
			}

		}

		areadAllocationDetails.setAvailableAreas(areaVOList);
		areadAllocationDetails.setDistributionGroupDetails(dgDetailList);

		return areadAllocationDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.cr.service.ManageAreaAllocationService
	 * #createArea(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Transactional
	@Override
	public boolean createArea(Long siteID, Long distributionGroupID,
			String areaName, SessionInfo sesseionInfo)
			throws BusinessFailureException {

		boolean success = false;

		DistributionGroup dGroup = new DistributionGroup();
		dGroup.setId(distributionGroupID);
		Area newArea = new Area();
		newArea.setDistributionGroup(dGroup);
		newArea.setName(areaName);
		newArea.setCreationDate(new Date());
		newArea.setCreatedBy(sesseionInfo.getUserId());

		try {
			areaDAOService.create(newArea);
			success = true;
		} catch (Exception e) {
			logger.error(
					String.format(
							"Some error occured while Creating"
									+ " Area with Name - %s in Distribution group having ID - %d",
							areaName, distributionGroupID), e);
		}
		return success;
	}

	@Transactional
	@Override
	public List<com.panasonic.b2bacns.bizportal.cr.vo.Area> getAllAreas(
			Long dGroupID) {

		List<com.panasonic.b2bacns.bizportal.cr.vo.Area> areaVOList = new ArrayList<>();

		try {
			Criteria areaCriteria = areaDAOService.createCriteria();
			areaCriteria.add(Restrictions.eq("distributionGroup.id", dGroupID));
			@SuppressWarnings("unchecked")
			List<Area> areaList = areaCriteria.list();

			com.panasonic.b2bacns.bizportal.cr.vo.Area areaVO = null;

			for (Area area : areaList) {
				areaVO = new com.panasonic.b2bacns.bizportal.cr.vo.Area();
				areaVO.setAreaId(area.getId());
				areaVO.setAreaName(area.getName());
				areaVOList.add(areaVO);
			}

		} catch (Exception e) {
			logger.error(String.format("Some error occured while getting "
					+ " Areas in Distribution group having ID - %d", dGroupID),
					e);
		}

		return areaVOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.cr.service.ManageAreaAllocationService
	 * #removeArea(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Transactional
	@Override
	public boolean removeArea(Long siteID, Long distributionGroupID,
			String areaName) throws BusinessFailureException {

		boolean success = false;

		DistributionGroup dGroup = new DistributionGroup();
		dGroup.setId(distributionGroupID);
		Area area = null;

		try {

			Criteria areaCriteria = areaDAOService.createCriteria();
			areaCriteria.add(Restrictions.ilike("name", areaName));
			areaCriteria.add(Restrictions.eq("distributionGroup", dGroup));
			@SuppressWarnings("unchecked")
			List<Area> areaList = areaCriteria.list();

			if (areaList != null && !areaList.isEmpty()) {
				area = areaList.get(0);
			}
			if (area != null) {
				if (area.getIndoorunits() != null
						&& !area.getIndoorunits().isEmpty()) {
					List<Indoorunit> iduList = area.getIndoorunits();
					for (Indoorunit idu : iduList) {
						idu.setArea(null);
					}
					synchronized (iduService) {
						iduService.batchSaveOrUpdate(iduList);
					}
					area.setIndoorunits(null);
				} else {
					// throw new BusinessFailureException(
					// "area.associated.with.idus.cannot.delete");
				}
				synchronized (areaDAOService) {
					areaDAOService.delete(area);
				}
			} else {
				throw new BusinessFailureException("no.area.found");
			}
			success = true;
		} catch (Exception e) {
			logger.error(
					String.format(
							"Some error occured while deleting"
									+ " Area with Name - %s in Distribution group having ID - %d",
							areaName, distributionGroupID), e);
		}

		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void updateIDUAreaMapping(List<IDUAreaMapping> areaMappingList)
			throws BusinessFailureException {
		//add by shanf
		logger.error("updateIDUAreaMapping starting");
		Map<Long, IDUAreaMapping> iduMapp = new HashMap<>();
		List<Long> areaIdList = new ArrayList<>();

		for (IDUAreaMapping iduAreaMapping : areaMappingList) {
			iduMapp.put(iduAreaMapping.getIduId(), iduAreaMapping);
			if (iduAreaMapping.getAreaId() != null) {
				areaIdList.add(iduAreaMapping.getAreaId());
			}
		}

		Criteria iduCriteria = iduService.getCriteria();
		iduCriteria.add(Restrictions.in("id", iduMapp.keySet()));
		// iduCriteria.add(RootEntityResultTransformer.INSTANCE);
		List<Indoorunit> iduList = iduCriteria.list();

		List<Area> areaList = null;
		if (!areaIdList.isEmpty()) {
			Criteria areaCriteria = areaDAOService.createCriteria();
			areaCriteria.add(Restrictions.in("id", areaIdList));
			areaCriteria.list();
			areaList = areaCriteria.list();
		}
		List<Indoorunit> iduUpdatableList = new ArrayList<>();
		List<Area> areaUpdatableList = new ArrayList<>();

		Map<Long, Area> areaMap = new HashMap<>();

		if (areaList != null && !areaList.isEmpty()) {
			for (Area area : areaList) {
				areaMap.put(area.getId(), area);
			}
		} else {
			// fix issue #2388 (JP 3188) 
			// throw new BusinessFailureException("no.area.found.in.list");
		}

		Area oldAreaMapping = null;

		Area newAreaMapping = null;

		if ((iduList != null && !iduList.isEmpty())) {

			for (Indoorunit idu : iduList) {

				oldAreaMapping = idu.getArea();
				newAreaMapping = areaMap.get(iduMapp.get(idu.getId())
						.getAreaId());

				if (oldAreaMapping != null
						&& oldAreaMapping.getIndoorunits() != null) {
					oldAreaMapping.removeIndoorunit(idu);
					areaUpdatableList.add(oldAreaMapping);
				}

				if (newAreaMapping != null) {
					if (newAreaMapping.getIndoorunits() != null) {
						newAreaMapping.addIndoorunit(idu);
					} else {
						newAreaMapping
								.setIndoorunits(new ArrayList<Indoorunit>());
						newAreaMapping.addIndoorunit(idu);
					}
					areaUpdatableList.add(newAreaMapping);
				}
				// add by shanf
				if (newAreaMapping == null) {
					logger.error("newAreaMapping:"+null);
				}
				
				idu.setArea(newAreaMapping);
				iduUpdatableList.add(idu);
			}
		} else {
			throw new BusinessFailureException("no.idu.found.in.list");
		}

		if (!iduUpdatableList.isEmpty()) {
			iduService.batchSaveOrUpdate(iduList);
		}
		if (!areaUpdatableList.isEmpty()) {
			areaDAOService.batchSaveOrUpdate(areaUpdatableList);
		}
	}

	@Override
	@Transactional
	public boolean isAreaAssigned(Long areaId) throws BusinessFailureException {

		boolean iduAssigned = false;

		Area area = null;

		try {

			Criteria areaCriteria = areaDAOService.createCriteria();
			areaCriteria.add(Restrictions.idEq(areaId));
			@SuppressWarnings("unchecked")
			List<Area> areaList = areaCriteria.list();

			if (areaList != null && !areaList.isEmpty()) {
				area = areaList.get(0);
			}
			if (area != null) {

				if (area.getIndoorunits() != null
						&& !area.getIndoorunits().isEmpty()) {
					iduAssigned = true;
				} else {
					// throw new BusinessFailureException(
					// "area.associated.with.idus.cannot.delete");
				}
			} else {
				throw new BusinessFailureException("no.area.found");
			}
		} catch (Exception e) {
			logger.error(
					String.format(
							"Some error occured while checking "
									+ " Area with Name - %d is associated with any IndoorUnits",
							areaId), e);
		}

		return iduAssigned;

	}
}
