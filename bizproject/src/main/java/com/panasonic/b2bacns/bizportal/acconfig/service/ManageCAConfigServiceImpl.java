/**
 * 
 */
package com.panasonic.b2bacns.bizportal.acconfig.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panasonic.b2bacns.bizportal.acconfig.dao.CAConfigDAO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigIDUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigODUVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.CAConfigVO;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.common.reports.DataTableHeader;
import com.panasonic.b2bacns.common.reports.HeadingTextProperties;
import com.panasonic.b2bacns.common.reports.Logo;
import com.panasonic.b2bacns.common.reports.ReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportGenerator;
import com.panasonic.b2bacns.common.reports.csv.CSVReportMetadata;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportGenerator;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportMetadata;

/**
 * @author jwchan
 * 
 */
@Service
public class ManageCAConfigServiceImpl implements ManageCAConfigService {

	private static final Logger logger = Logger
			.getLogger(ManageCAConfigServiceImpl.class);

	@Autowired
	private CAConfigDAO caConfigDAO;

	@Resource(name = "properties")
	private Properties bizProperties;

	
	
	@Override
	public Long setMetaIDUByFacilityId(String facl_id)
			throws HibernateException {
		Long result = 0l;
		/*
		Metaindoorunit meta = caConfigDAO.getStatusInfoByFacl(facl_id);
		if(meta != null){
			Long id  = caConfigDAO.update_metaindoorunits(meta);
			result = id;
		}
		*/
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public List<CAConfigVO> getCAConfiguration(ACConfigRequest acConfigRequest) {

		List<CAConfigVO> caConfigDetails = null;
		try {
			caConfigDetails = caConfigDAO.getCAList(acConfigRequest);
			//logger.debug("Get CA Config CA Details request: "+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the CA Config CA Details : "
					+ e.getMessage());
			/*
			caConfigDetails = new ArrayList<CAConfigVO>();
			CAConfigVO caVO = new CAConfigVO();
			caVO.setAddress("address");
			caConfigDetails.add(caVO);
		   */
		}
		return caConfigDetails;

	}
//Set<ACConfigODUVO> acODUList = new LinkedHashSet<ACConfigODUVO>();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeS
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public Set<CAConfigVO> getCAConfigurationSet(ACConfigRequest acConfigRequest) {

		Set<CAConfigVO> caConfigDetails = null;
		try {
			caConfigDetails = caConfigDAO.getCASet(acConfigRequest);
			//logger.debug("Get CA Config CA Details request: "+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the CA Config CA Details : "
					+ e.getMessage());
			/*
			caConfigDetails = new ArrayList<CAConfigVO>();
			CAConfigVO caVO = new CAConfigVO();
			caVO.setAddress("address");
			caConfigDetails.add(caVO);
		   */
		}
		return caConfigDetails;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public List<CAConfigVO> getCAConfigurationByMac(ACConfigRequest acConfigRequest) {

		List<CAConfigVO> caConfigDetails = null;
		try {
			caConfigDetails = caConfigDAO.getCAListByMac(acConfigRequest);
			//logger.debug("Get CA Config CA Details request: "+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the CA Config CA Details : "
					+ e.getMessage());
			logger.error(e.getLocalizedMessage());
			logger.error(e);
			
			/*
			caConfigDetails = new ArrayList<CAConfigVO>();
			CAConfigVO caVO = new CAConfigVO();
			caVO.setAddress("address");
			caConfigDetails.add(caVO);
		   */
		}
		return caConfigDetails;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public Set<ACConfigODUVO> getODUConfigurationByMac(ACConfigRequest acConfigRequest) {

		Set<ACConfigODUVO> caConfigDetails = null;
		try {
			caConfigDetails = caConfigDAO.getODUListByMac(acConfigRequest);
			//logger.debug("Get CA Config CA Details request: "+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the CA Config CA Details : "
					+ e.getMessage());
			logger.error(e.getLocalizedMessage());
			logger.error(e);
			/*
			caConfigDetails = new ArrayList<CAConfigVO>();
			CAConfigVO caVO = new CAConfigVO();
			caVO.setAddress("address");
			caConfigDetails.add(caVO);
		   */
		}
		return caConfigDetails;

	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public Set<ACConfigODUVO> getODUConfigurationByMac2(ACConfigRequest acConfigRequest) {

		Set<ACConfigODUVO> caConfigDetails = null;
		try {
			caConfigDetails = caConfigDAO.getODUACConfigDetails(acConfigRequest);
			//logger.debug("Get CA Config CA Details request: "+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the CA Config CA Details ODU : "
					+ e.getMessage());
			logger.error(e.getLocalizedMessage());
			logger.error(e);
			
			/*
			caConfigDetails = new ArrayList<CAConfigVO>();
			CAConfigVO caVO = new CAConfigVO();
			caVO.setAddress("address");
			caConfigDetails.add(caVO);
		   */
		}
		return caConfigDetails;

	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public ACConfigIDUVO getACConfiguration(ACConfigRequest acConfigRequest) {

		ACConfigIDUVO acConfigDetails = null;
		try {
			acConfigDetails = caConfigDAO.getACConfigDetailsCA(acConfigRequest);
			logger.debug("Get AC Config IDU Details request: "
					+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the AC Config IDU Details by CA : "
					+ e.getMessage());
			logger.error(e.getLocalizedMessage());
			logger.error(e);
			
		}
		return acConfigDetails;

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #getODUACConfiguration
	 * (com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest)
	 */
	@Override
	public Set<ACConfigODUVO> getODUACConfiguration(
			ACConfigRequest acConfigRequest) {

		Set<ACConfigODUVO> acODUDetails = null;
		try {
			acODUDetails = caConfigDAO.getODUACConfigDetails(acConfigRequest);
			logger.debug("Get AC Config ODU Details request: "
					+ acConfigRequest);

		} catch (Exception e) {
			logger.error("Some error occured while fetching the AC Config ODU Details : "
					+ e.getMessage());
		}
		return acODUDetails;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateACDetailsODUCsvReport(java.util.Set)
	 */
	@Override
	public String generateACDetailsCsvReport(Set<CAConfigVO> indoorDataList,
			String addCustomer) throws Exception {

		// Generates a csv report
		ReportGenerator<CAConfigVO> fixture = new CSVReportGenerator<CAConfigVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		CSVReportMetadata metadata = new CSVReportMetadata(CAConfigVO.class,
				"Current CA Status", "Current-CA-Status-"
						+ new Date().getTime());

		metadata.setDataTableHeaderTextAlignment("CENTERE");

		metadata.setHeadingTextProperties(setACIDUHeaderText());

		// set headings for table columns
		List<DataTableHeader> tableHeadings = setACDetailsTableHeader(addCustomer);

		metadata.setDataTableHeading(tableHeadings);

		Collection<CAConfigVO> dataList =  new ArrayList<CAConfigVO>(indoorDataList);

		// Writes the csv metadata and dataList received in the csv
		return fixture.writeTabularReport(metadata, dataList);

	}

	private List<HeadingTextProperties> setACIDUHeaderText() {
		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_TITLE);
		headertext.setValue("Current CA Status");
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		headertext = new HeadingTextProperties();
		headertext.setName(BizConstants.REPORT_HEADER_GENERATED_AT);
		headertext.setValue(CommonUtil.dateToString(new Date(),
				BizConstants.DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD));
		headertext.setDisplayPosition("LEFT");
		headerTextList.add(headertext);

		return headerTextList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.panasonic.b2bacns.bizportal.acconfig.service.ManageACConfigService
	 * #generateACDetailsODUExcelReport(java.util.Set)
	 */
	@Override
	public String generateACDetailsExcelReport(Set<CAConfigVO> inputDataList,
			String addCustomer) throws Exception {

		// Generates an excel report
		ReportGenerator<CAConfigVO> fixture = new ExcelReportGenerator<CAConfigVO>();

		// It includes data for making the report like the report heading, table
		// heading etc.
		ExcelReportMetadata metadata = new ExcelReportMetadata(
				ACConfigVO.class, "Current CA Status", "Current-CA-Status-"
						+ new Date().getTime());

		Logo logo = new Logo();
		logo.setText("Panasonic Smart Cloud");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("Current CA Status");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		metadata.setHeadingTextProperties(setACIDUHeaderText());

		metadata.setDataTableHeading(setACDetailsTableHeader(addCustomer));

		Collection<CAConfigVO> dataList = new ArrayList<CAConfigVO>(inputDataList);

		// Writes the excel metadata and dataList received in the excel
		return fixture.writeTabularReport(metadata, dataList);

	}

	private File getLogoFile() throws IOException, URISyntaxException {
		// return resourceLoader.getResource("file:webapp/" + path).getFile();

		return new File(this.getClass().getClassLoader()
				.getResource("panasonic.png").toURI());
	}

	/**
	 * Set column names for AC Details download
	 * 
	 * @return
	 */
	private List<DataTableHeader> setACDetailsTableHeader(String addCustomer) {

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = new DataTableHeader();
/*
		if (StringUtils.equalsIgnoreCase(addCustomer, BizConstants.YES)) {

			heading = new DataTableHeader();
			heading.setColumnName("customerName");
			heading.setDisplayName(BizConstants.REPORT_COLUMN_CUSTOMER_NAME);
			heading.setSequence(0);
			heading.setAlignment("LEFT");
			tableHeadings.add(heading);
		}
*/
		heading = new DataTableHeader();
		heading.setColumnName("customerName");
		heading.setDisplayName("CustomerName");
		heading.setSequence(1);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("site_name");
		heading.setDisplayName("Site Name");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("address");
		heading.setDisplayName("CA ID");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);
		//add by shanf
		heading = new DataTableHeader();
		heading.setColumnName("deviceModel");
		heading.setDisplayName("Device Model");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);
		// Modified by shanf start
		heading = new DataTableHeader();
		heading.setColumnName("mov1pulse_id");
		heading.setDisplayName("Pulse Meter 1 ID");
		heading.setSequence(5);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov1pulse_type");
		heading.setDisplayName("Pulse Meter 1 Type");
		heading.setSequence(6);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov1pulse");
		heading.setDisplayName("Pulse Meter 1 Value");
		heading.setSequence(7);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov1pulse_factor");
		heading.setDisplayName("Pulse Meter 1 Factor");
		heading.setSequence(8);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov2pulse_id");
		heading.setDisplayName("Pulse Meter 2 ID");
		heading.setSequence(9);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov2pulse_type");
		heading.setDisplayName("Pulse Meter 2 Type");
		heading.setSequence(10);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov2pulse");
		heading.setDisplayName("Pulse Meter 2 Value");
		heading.setSequence(11);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov2pulse_factor");
		heading.setDisplayName("Pulse Meter 2 Factor");
		heading.setSequence(12);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov4pulse_id");
		heading.setDisplayName("Pulse Meter 3 ID");
		heading.setSequence(13);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov4pulse_type");
		heading.setDisplayName("Pulse Meter 3 Type");
		heading.setSequence(14);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov4pulse");
		heading.setDisplayName("Pulse Meter 3 Value");
		heading.setSequence(15);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("mov4pulse_factor");
		heading.setDisplayName("Pulse Meter 3 Factor");
		heading.setSequence(16);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("alarm_code");
		heading.setDisplayName("Alarm Code");
		heading.setSequence(17);
		heading.setAlignment("CENTERE");
		tableHeadings.add(heading);
		// Modified by shanf start
		return tableHeadings;
	}	
	
}
