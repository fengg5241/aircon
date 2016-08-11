package com.panasonic.b2bacns.bizportal.reports;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigRequest;
import com.panasonic.b2bacns.bizportal.acconfig.vo.ACConfigVO;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;
import com.panasonic.b2bacns.bizportal.util.BizConstants;
import com.panasonic.b2bacns.bizportal.util.CommonUtil;
import com.panasonic.b2bacns.common.reports.DataTableHeader;
import com.panasonic.b2bacns.common.reports.HeadingTextProperties;
import com.panasonic.b2bacns.common.reports.Logo;
import com.panasonic.b2bacns.common.reports.ReportGenerator;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportGenerator;
import com.panasonic.b2bacns.common.reports.xlsx.ExcelReportMetadata;

/**
 * The class <code>ExcelReportGeneratorTest</code> contains tests for the class
 * {@link <code>ExcelReportGenerator</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 21/9/15 5:27 PM
 *
 * @author simanchal.patra
 *
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF")
@ContextConfiguration(locations = { "classpath:test-applicationcontext.xml" })
@Transactional(propagation = Propagation.NESTED)
@Ignore
public class ExcelReportGeneratorTest extends TestCase {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ResourceLoader resourceLoader;

	protected MockMvc mockMvc;

	@Override
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testMock() throws Exception {
		assertNotNull("Mock MVC cannot be initialized", mockMvc);
	}

	private MockHttpServletRequestBuilder get(String uri) {
		return MockMvcRequestBuilders.get(uri);
	}

	private StatusResultMatchers status() {
		return MockMvcResultMatchers.status();
	}

	private MockHttpSession getSession() {
		MockHttpSession session = new MockHttpSession();
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserRole("1");
		sessionInfo.setUserRoleId("1");
		sessionInfo.setUserId(24l);
		sessionInfo.setLastSelectedGroupID(7l);
		session.setAttribute("sessionInfo", sessionInfo);
		return session;
	}

	private List<ACConfigVO> testViewACConfiguration() throws Exception {

		Locale locale = Locale.ENGLISH;
		List<Long> groupIds = new ArrayList<Long>();
		groupIds.add(9l);
		groupIds.add(20l);
		ACConfigRequest request = new ACConfigRequest();
		request.setId(groupIds);
		request.setIdType(BizConstants.ID_TYPE_GROUP);

		String acconfigDetails;

		acconfigDetails = mockMvc
				.perform(
						get("/acconfig/getACDetails.htm")
								.header("referer", "/acconfig/*.htm")
								.accept("application/json")
								.session(getSession())
								.locale(locale)
								.param("json_request",
										CommonUtil
												.convertFromEntityToJsonStr(request)))
				.andExpect(status().is(200)).andReturn().getResponse()
				.getContentAsString();

		System.out.println("testViewACConfiguration -- acconfigDetails -- "
				+ acconfigDetails);

		@SuppressWarnings("unchecked")
		List<ACConfigVO> dataList = (List<ACConfigVO>) CommonUtil
				.convertFromJsonStrToEntityList(ACConfigVO.class,
						acconfigDetails);

		return dataList;

	}

	private File getLogoFile() throws IOException, URISyntaxException {
		// return resourceLoader.getResource("file:webapp/" + path).getFile();

		File logoFile = new File("./panasonic.png");

		URL logoFileURL = this.getClass().getClassLoader()
				.getResource("panasonic.png");

		if (logoFileURL != null) {
			logoFile = new File(logoFileURL.toURI());
		}

		return logoFile;
	}

	/**
	 * Run the void writeTabularReport(ReportMetadata, Collection<T>) method
	 * test
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWriteTabularReport() throws Exception {

		List<ACConfigVO> inputDataList = testViewACConfiguration();

		ReportGenerator<ACConfigVO> fixture = new ExcelReportGenerator<ACConfigVO>();

		ExcelReportMetadata metadata = new ExcelReportMetadata(
				ACConfigVO.class, "AC Configuration Details", "test-report-"
						+ new Date().getTime());

		Logo logo = new Logo();
		logo.setImage(getLogoFile());
		logo.setImageRelativePosition("RIGHT");

		logo.setText("Panasonic");
		logo.setTextFontSize((short) 16);
		logo.setTextFont("Callibri");
		logo.setTextRelativePosition("LEFT");

		metadata.setLogo(logo);
		metadata.setDataFontSize((short) 12);
		metadata.setSheetName("AC Config Details");
		metadata.setDataTableHeaderFontSize((short) 14);
		metadata.setDataTableHeaderTextAlignment("CENTERE");
		metadata.setReportNameFontSize((short) 14);
		metadata.setDisplayGirdLines(true);

		List<HeadingTextProperties> headerTextList = new ArrayList<HeadingTextProperties>();

		HeadingTextProperties headertext1 = new HeadingTextProperties();
		headertext1.setName("Report Generation Date");
		headertext1.setValue(new Date().toString());
		headertext1.setDisplayPosition("LEFT");

		headerTextList.add(headertext1);

		metadata.setHeadingTextProperties(headerTextList);

		List<DataTableHeader> tableHeadings = new ArrayList<DataTableHeader>();

		DataTableHeader heading = new DataTableHeader();

		heading = new DataTableHeader();
		heading.setColumnName("mode");
		heading.setDisplayName("Mode");
		heading.setSequence(4);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("linkODUSVG");
		heading.setDisplayName("Link ODU SVG");
		heading.setSequence(2);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("temperature");
		heading.setDisplayName("Temperature");
		heading.setSequence(3);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("unitAddress");
		heading.setDisplayName("Unit Address");
		heading.setSequence(0);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		heading = new DataTableHeader();
		heading.setColumnName("power");
		heading.setDisplayName("Power");
		heading.setSequence(1);
		heading.setAlignment("LEFT");
		tableHeadings.add(heading);

		metadata.setDataTableHeading(tableHeadings);

		Collection<ACConfigVO> dataList = inputDataList;
		System.out.println(fixture.writeTabularReport(metadata, dataList));
		assertTrue(true);
	}

}

/*
 * $CPS$ This comment was generated by CodePro. Do not edit it. patternId =
 * com.instantiations.assist.eclipse.pattern.testCasePattern strategyId =
 * com.instantiations.assist.eclipse.pattern.testCasePattern.junitTestCase
 * additionalTestNames = assertTrue = false callTestMethod = true createMain =
 * false createSetUp = false createTearDown = false createTestFixture = false
 * createTestStubs = false methods =
 * writeTabularReport(QReportMetadata;!QCollection<QT;>;) package =
 * com.panasonic.b2bacns.bizportal.reports package.sourceFolder =
 * bizportal/src/test/java superclassType = junit.framework.TestCase testCase =
 * ExcelReportGeneratorTest testClassType =
 * com.panasonic.b2bacns.bizportal.reports.ExcelReportGenerator
 */
