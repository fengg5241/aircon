package com.panasonic.b2bacns.bizportal.rc.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.rc.RCOperationLogRequest;
import com.panasonic.b2bacns.bizportal.rc.RCOperationLogVO;

/**
 * This interface declares methods to work with RcoperationLog object
 * 
 * @author shobhit.singh
 * 
 */
public interface RCOperationLogService {

	/**
	 * Returns list of RCOperationLogVO by date time range
	 * 
	 * @param fromDateTime
	 * @param toDateTime
	 * @return
	 * @throws Exception
	 */
	public List<RCOperationLogVO> getRcoperationLogsByDateTimeRange(
			RCOperationLogRequest logRequest, Long userId) throws Exception;

	/**
	 * Returns Integer for RC OP Logs Page Count
	 * 
	 * @param fromDateTime
	 * @param toDateTime
	 * @return
	 * @throws Exception
	 */
	public Integer getRcoperationLogsPageCount(
			RCOperationLogRequest logRequest, Long userId) throws Exception;

	/**
	 * Logs RC Operations for the provided IDUs and User
	 * 
	 * @param userID
	 * @param iduIDs
	 * @param rcOperationPerformedMap
	 * @param isSuccess
	 * @throws HibernateException
	 */
	public void logRCoperationsPerformedByUser(Long userID, Long iduIDs,
			Map<String, String> rcOperationPerformedMap, Boolean isSuccess)
			throws HibernateException;

	/**
	 * Generate Report for the RC operation log
	 * 
	 * @param logRequest
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public String generateReportRCOperationsLogByDateTimeRange(
			RCOperationLogRequest logRequest, String reportType,
			SessionInfo sessionInfo) throws Exception;

}
