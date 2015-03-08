package com.netapp.nwfjsvalidation.testcases.admin;

import static org.fest.assertions.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;
import com.netapp.nwfjsvalidation.pageobjects.admin.AdminPage;
import com.netapp.nwfjsvalidation.pages.login.LoginPage;
import com.netapp.nwfjsvalidation.setup.Setup;

public class AdminTests {

	ExtLogger logger = ExtLogger.getLogger(AdminTests.class);
	
	private WebDriver driver = null;
	private AdminPage adminPage = null;
	private LoginPage loginPage = null;
	
	@BeforeClass(description = "Admin Page Test Started")
	public void testSetup(XmlTest config) {
		logger.testCaseLog("Starting AdminPage Test");
		
		driver = Setup.getWebDriver(config);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		adminPage = loginPage.userLogin(config);
		adminPage.navigateTo("Data Sources");
		
		driver.manage().deleteAllCookies();
	}
	
	@AfterClass(alwaysRun = true, description = "Admin Page Test Execution Finished")
	public void tearDown() {
		if (driver != null && !driver.toString().contains("null")) {
			driver.close();
			driver.quit();
		}
	}
	
	@Test(alwaysRun = true)
	public void testAdminPage() {
		
		String[] actualMenuNames = adminPage.getManageMenuItemNames();
		assertThat(actualMenuNames).isEqualTo(AdminTestData.expectedManageSectionButtons);
		
		actualMenuNames = adminPage.getSetupMenuItemNames();
		assertThat(actualMenuNames).isEqualTo(AdminTestData.expectedSetupSectionButtons);
		
	}
}