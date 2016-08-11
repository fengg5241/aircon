package com.panasonic.b2bacns.bizportal.acconfig.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.DetailsLogsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUListVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ODUParamsVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.RefrigerantSVG;

public interface ACConfigDAO {

	/**
	 * Provides the AC Configuration Details for a Group or an IDU.
	 * 
	 * @param acConfigRequest
	 *            ACConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public ACConfigIDUVO getACConfigDetails(ACConfigRequest acConfigRequest);

	/**
	 * Provides the AC Configuration Details for a Group or an ODU.
	 * 
	 * @param acConfigRequest
	 *            ACConfigRequest
	 * 
	 * @return AC configuration Details for the requested Entity
	 */
	public Set<ACConfigODUVO> getODUACConfigDetails(
			ACConfigRequest acConfigRequest);

	/**
	 * @param IndoorUnitsId
	 * @return
	 * @throws HibernateException
	 */
	public List<ODUListVO> getODUList(Long[] id) throws HibernateException;

	/**
	 * @param GroupsUnitsId
	 * @return
	 * @throws HibernateException
	 */
	public List<ODUListVO> getODUDataForGroups(Long[] id)
			throws HibernateException;

	/**
	 * @param OutDoorUnitsId
	 *            as id
	 * @param
	 * @return
	 * @throws HibernateException
	 */
	public List<ODUParamVO> getODUParams(Long id, List<String> paramWithG1,
			List<String> paramsForGHPlist, List<String> paramsForVRFlist,
			String idType) throws HibernateException;

	/**
	 * @param idType
	 * @return
	 * @throws HibernateException
	 */
	public List<ODUParamsVO> getODUParameterList(String idType)
			throws HibernateException;

	/**
	 * Set Remote Control Parameter in AC and Notification IDU Details.
	 * 
	 * @param rowData
	 * @return
	 */
	public DetailsLogsVO setRCParameter(Object[] rowData);

	/**
	 * Set Refrigerant SVG for all 3 parameter.
	 * 
	 * @param refrigerantSVGList
	 * @param rowData
	 */
	public void getRefrigerantSVG(Set<RefrigerantSVG> refrigerantSVGList,
			Object[] rowData);

}
