/**
 * 
 */
package com.panasonic.b2bacns.bizportal.acconfig.service;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamVO;

/**
 * @author amitesh.arya
 * 
 */
public interface ManageACConfigService {

	/**
	 * Provides the AC Configuration Details for a Group or an IDU.
	 * 
	 * @param acConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public ACConfigIDUVO getACConfiguration(ACConfigRequest acConfigRequest);

	/**
	 * Provides the AC Configuration Details for a Group or an ODU .
	 * 
	 * @param acConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public Set<ACConfigODUVO> getODUACConfiguration(
			ACConfigRequest acConfigRequest);

	/**
	 * Generate excel report for AC Details IDU.
	 * 
	 * @param inputDataList
	 * @param addCustomer
	 * @return
	 * @throws Exception
	 */
	public String generateACDetailsExcelReport(Set<ACConfigVO> inputDataList,
			String addCustomer) throws Exception;

	/**
	 * Generate csv report for AC Details IDU.
	 * 
	 * @param inputDataList
	 * @param addCustomer
	 * @return
	 * @throws Exception
	 */
	public String generateACDetailsCsvReport(Set<ACConfigVO> inputDataList,
			String addCustomer) throws Exception;

	/**
	 * Generate excel report for AC Details Outdoor
	 * 
	 * @param outdoorDataList
	 * @param addCustomer
	 * @return
	 * @throws Exception
	 */
	public String generateACDetailsODUExcelReport(
			Set<ACConfigODUVO> outdoorDataList, String addCustomer)
			throws Exception;

	/**
	 * Generate csv report for AC Details Outdoor
	 * 
	 * @param outdoorDataList
	 * @param addCustomer
	 * @return
	 * @throws Exception
	 */
	public String generateACDetailsODUCsvReport(
			Set<ACConfigODUVO> outdoorDataList, String addCustomer)
			throws Exception;

	/**
	 * This method provides the OutDoorData for IndoorUnitIds
	 * 
	 * @param IndoorUnitsID
	 *            ID of the IndoorUnits
	 * 
	 * @return OutDoorUnits Data for requested IndoorUnits
	 */
	public String getODUList(Long[] id) throws HibernateException,
			JsonProcessingException, IllegalAccessException, Exception;

	/**
	 * This method provides the OutDoorData for GroupsUnitIDs
	 * 
	 * @param GroupID
	 *            ID of the Groups
	 * 
	 * @return OutDoorUnits Data for requested GroupIds
	 */
	public String getGroupODUList(Long[] id) throws HibernateException,
			JsonProcessingException, IllegalAccessException, Exception;

	/**
	 * This method provides the OutDoorParam Data for OutDoorUnitIDs
	 * 
	 * @param OutDoorID
	 *            ID of the OutDoor
	 * 
	 * @return OutDoorparams Data
	 */
	public List<ODUParamVO> getODUParams(Long id, List<String> paramWithG1,
			List<String> paramsForGHPlist, List<String> paramsForVRFlist,
			String idType) throws HibernateException;

	/**
	 * This method provides the ODU parameters Data for idType
	 * 
	 * @param ODUParameterType
	 *            idType of the Parameters
	 * 
	 * @return OutDoorparameters Data
	 */
	public String getODUParameterList(String idType) throws HibernateException,
			JsonProcessingException, IllegalAccessException, Exception;

	/**
	 * Generate excel report for ODU params.
	 * 
	 * @param oduParamList
	 * @return
	 * @throws Exception
	 */
	public String generateODUParamsExcelReport(List<ODUParamVO> oduParamList)
			throws Exception;

	/**
	 * Generate csv report for ODU params.
	 * 
	 * @param oduParamList
	 * @return
	 * @throws Exception
	 */
	public String generateODUParamsCsvReport(List<ODUParamVO> oduParamList)
			throws Exception;

}
