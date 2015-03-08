package com.netapp.nwfjsvalidation.testcases.datasources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;
import com.netapp.nwfjsvalidation.pageconstants.admin.AdminPageLevelConstants;
import com.netapp.nwfjsvalidation.pageobjects.datasources.DataSourcesPage;
import com.netapp.nwfjsvalidation.pages.login.LoginPage;
import com.netapp.nwfjsvalidation.setup.Setup;

public class DataSourcesTests {


	ExtLogger logger = ExtLogger.getLogger(DataSourcesTests.class);
	
	private WebDriver driver = null;
	private DataSourcesPage dataSources = null;
	private LoginPage loginPage = null;
	
	
	@BeforeClass(description = "DataSources Page Test Started")
	public void testSetup(XmlTest config) {
		logger.testCaseLog("Starting DataSources Page Test");
		
		driver = Setup.getWebDriver(config);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		dataSources = (DataSourcesPage) loginPage.userLogin(config).navigateTo(AdminPageLevelConstants.DATA_SOURCES);
		driver.manage().deleteAllCookies();
	}
	
	@AfterClass(description = "DataSources Page Test Execution Finished")
	public void tearDown() {
		if (driver != null && !driver.toString().contains("null")) {
			driver.close();
			driver.quit();
		}
	}
	
	@Test
	private void validate_direct_navigation_to_datasource_page(){
		System.out.println(dataSources.getPageTitle());
	}
}
