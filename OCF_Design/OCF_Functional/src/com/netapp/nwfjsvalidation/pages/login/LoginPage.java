package com.netapp.nwfjsvalidation.pages.login;

import org.apache.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.xml.XmlTest;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;
import com.netapp.nwfjsvalidation.lib.utils.SeleniumUtils;
import com.netapp.nwfjsvalidation.pageelements.admin.AdminPageElements;
import com.netapp.nwfjsvalidation.pageobjects.admin.AdminPage;
import com.netapp.nwfjsvalidation.pages.testconstants.OCF_Constants;
import com.netapp.nwfjsvalidation.pages.testconstants.ConfigFileConstants;
import com.netapp.nwfjsvalidation.setup.Setup;
import com.netapp.nwfjsvalidation.testobjects.BrowserFunctions;


public class LoginPage {
	
	/** The driver. */
    private WebDriver driver;
    
	  /** The logger. */
    ExtLogger logger = ExtLogger.getLogger(LoginPage.class);

    /**
     * Instantiates a new login.
     *
     * @param driver the driver
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    
    /**
     * xpath for page elements
     */
	private final static String APPLICATION_TITLE_XPATH = "//*[@data-netapp-id='loginPage-title-text']";
	private final static String LOGIN_BUTTON_XPATH = "//*[@data-netapp-id='loginPage-signIn-button']";
	
	
    /** Application title. */
    @FindBy(xpath = APPLICATION_TITLE_XPATH)
    @CacheLookup
    private WebElement applicationTitle;
    
//    private final static String APPLICATION_TITLE_XPATH = "//h3";
	private final static String USERNAME_LABEL_XPATH = "//label[@for='username']";
	private final static String PASSWORD_LABEL_XPATH = "//label[@for='password']";
	
	private final static String USERNAME_TEXTBOX = "//*[@data-netapp-id='loginPage-username-input']";
	private final static String PASSWORD_TEXTBOX = "//*[@data-netapp-id='loginPage-password-input']";
	
//	private final static String USERNAME_TEXTBOX = "//input[@id='username']";
//	private final static String PASSWORD_TEXTBOX = "//input[@id='password']";
//	private final static String LOGIN_BUTTON_XPATH = "//button[@id='login-submit']";

	/** Application title. *//*
	@FindBy(xpath = APPLICATION_TITLE_XPATH)
	@CacheLookup
	private WebElement applicationTitle;*/

    /** The sign in button. */
    @FindBy(xpath = LOGIN_BUTTON_XPATH)
    @CacheLookup
    private WebElement signinBtn;
    
    
    @FindBy(xpath = USERNAME_LABEL_XPATH)
    @CacheLookup
    private WebElement usernameLabel;
    
    @FindBy(xpath = PASSWORD_LABEL_XPATH)
    @CacheLookup
    private WebElement passwordLabel;
    
    
    @FindBy(xpath = USERNAME_TEXTBOX)
    @CacheLookup
    private WebElement usernameTextfield;
    
    @FindBy(xpath = PASSWORD_TEXTBOX)
    @CacheLookup
    private WebElement passwordTextfield;
    
    
    public String getPageTitle(){
    	return driver.getTitle();
    }
    
    public String getApplicationTitle(){
    	return applicationTitle.getText();
    }
    
	public WebElement getUsernameLabel() {
		return usernameLabel;
	}

	public WebElement getPasswordLabel() {
		return passwordLabel;
	}

	public WebElement getUsernameTextfield() {
		return usernameTextfield;
	}

	public void setUsernameTextfield(String username) {
		usernameTextfield.sendKeys(username);
	}

	public WebElement getPasswordTextfield() {
		return passwordTextfield;
	}

	public void setPasswordTextfield(String password) {
		passwordTextfield.sendKeys(password);
	}
    
    
    public WebElement getSigninBtn() {
    	logger.info("signning button web element:: " + signinBtn);
        return signinBtn;
    }

    public AdminPageElements userLogin(){
    	return userLogin();
    }
   /* *//**
     * purpose: method for oncommand login.
     *
     * @param Config the config
     * @return navigate page or null
     * @throws Exception the exception
     *//*
    public AdminPage userLogin(String url, String username, String password) {
    	if(url == null) {
    		url = ConfigFileConstants.getDefaultURL();
    	}
    	if(username == null){
    		username = ConfigFileConstants.getSappUsername();
    	}
    	if(password == null){
    		password =  ConfigFileConstants.getSappPassword();
    	}
        return userLogin(url , username, password);
    }*/
    
    public AdminPage userLogin(XmlTest config) {
    	  return userLogin(ConfigFileConstants.getDefaultURL(config),
    			  ConfigFileConstants.getSappUsername(config),
    			 ConfigFileConstants.getSappPassword(config));
    }

    /**
     * purpose: method for oncommand login directly when user gave book marked URL.
     *
     * @param Config the config
     * @param url the url
     * @return navigate page or null
     * @throws Exception the exception
     */
    public AdminPage userLogin(XmlTest config, String url) {

        String Username = ConfigFileConstants.getSappUsername(config);
        String Password = ConfigFileConstants.getSappPassword(config);

        return this.userLogin(url, Username, Password);

    }
    
    /**
     * User login.
     *
     * @param url the url
     * @param userName the user name
     * @param pwd the pwd
     * @return the menu
     * @throws Exception the exception
     */
    public AdminPage userLogin(String url, String userName, String pwd) {
    	boolean isAppLaunched = this.launchApplication(url);
    	logger.testLog(Level.INFO, "Application successfully launched: " + isAppLaunched);
        if(isAppLaunched){
        	return signIn(userName, pwd);
        }
        return null;
    }
    
    /**
     * This method opens the login page Only. It does not log into the system.
     *
     * @param url String URL of the OCUM server
     *
     * @return <code>true</code> or <code>false</code> depending on whether the page was opened successfully or not
     * @throws InterruptedException
     */
	public boolean launchApplication(String url) {
		if (Setup.isDriverActive()) {
			try {

				driver.get(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				BrowserFunctions.maximizeBrowser();
			} catch (org.openqa.selenium.UnhandledAlertException e) {
				logger.info("found excepion " + e.getMessage());
				BrowserFunctions.waitForLoad();
				BrowserFunctions.maximizeBrowser();
			}

			logger.info("Waiting for Login Button in Login Page");
			SeleniumUtils.sleep(30);
			SeleniumUtils.waitForExistance(getSigninBtn(), 20);
			if (getApplicationTitle().contains(
					OCF_Constants.EXPECTED_APPLICATIN_TITLE)) {
				logger.testLog(Level.INFO,
						"Title From Page: " + driver.getTitle());
				logger.testLog(Level.INFO,
						"<SampleAppLogin>NetApp Sample App console Launched Successfully");
				return true;
			} else {
				logger.testLog(Level.ERROR,
						"Failed: To Launch NetApp Sampel App console");
				driver.quit();
				return false;
			}
		}
		return false;
	}
    
    
    /**
     * Signs into the application web UI after assuming that the page is open.
     *
     * @param userName String Username to login
     * @param pwd String Password for the username
     *
     * @return
     * @throws InterruptedException
     */
    public AdminPage signIn(String userName, String pwd) {
    	
    	getUsernameTextfield().sendKeys(userName);
    	getPasswordTextfield().sendKeys(pwd);
    	getSigninBtn().click();
    	
    	try {
			Thread.sleep(1000 * 15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	BrowserFunctions.waitForLoad();
    	
    	return PageFactory.initElements(driver, AdminPage.class);
    }
    
}
