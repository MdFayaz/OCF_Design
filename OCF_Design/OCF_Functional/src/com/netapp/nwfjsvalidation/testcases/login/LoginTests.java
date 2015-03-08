package com.netapp.nwfjsvalidation.testcases.login;

import static org.fest.assertions.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;
import com.netapp.nwfjsvalidation.pages.login.LoginPage;
import com.netapp.nwfjsvalidation.pages.testconstants.ConfigFileConstants;
import com.netapp.nwfjsvalidation.setup.Setup;

public class LoginTests {

	ExtLogger logger = ExtLogger.getLogger(LoginTests.class);
	private WebDriver driver = null;
	private LoginPage loginPage = null;
	private static String AUT_URL = null;  

	@BeforeClass(description = "Login Page Started")
	public void testSetup(XmlTest config) {
		logger.testCaseLog("Starting LoginTest");
		
		driver = Setup.getWebDriver(config);
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		AUT_URL = ConfigFileConstants.getDefaultURL(config);
		
		loginPage = PageFactory.initElements(driver, LoginPage.class);
	}

	/**
	 * Before Method
	 * 
	 * @param method
	 */
	@BeforeMethod(description = "print test case name")
	public void startTestCase(Method method) {
		logger.testCaseLog("Starting " + method.getName());
	}

	/**
	 * After Method.
	 *
	 */
	@AfterMethod(description = "Refresh the page")
	public void afterEveryTest(Method method) {
		logger.testCaseLog("Ending " + method.getName());

	}

	@AfterClass(description = "Login Page Finished")
	public void tearDown() {
		if (driver != null && !driver.toString().contains("null")) {
			driver.close();
			driver.quit();
		}
	}


	@Test(alwaysRun = true)
	public void testLogin() {
		loginPage.launchApplication(AUT_URL);
		
		assertThat(loginPage.getUsernameLabel().isDisplayed()).isTrue().as("Username Label displayed");
		assertThat(loginPage.getPasswordLabel().isDisplayed()).isTrue().as("Password Label displayed");
		
		assertThat(loginPage.getUsernameTextfield().isDisplayed()).isTrue().as("Username Textfield displayed");
		assertThat(loginPage.getPasswordTextfield().isDisplayed()).isTrue().as("Password Textfield displayed");
		
		assertThat(loginPage.getSigninBtn().isDisplayed()).isTrue().as("Login button displayed");
	}

}
