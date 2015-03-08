package com.netapp.nwfjsvalidation.pageelements.admin;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;

public class AdminPageElements {


	/** The driver. */
	WebDriver driver;
	
	  /** The logger. */
    ExtLogger logger = ExtLogger.getLogger(AdminPageElements.class);
    
    public AdminPageElements(WebDriver webDriver) {
    	this.driver = webDriver;
    }
    
    private final static String MANAGE_NAVIGATION_MENU_ITEMS_XPATH = "//div[@ng-show='showManagementHeaderButton']";
    private final static String SETUP_NAVIGATION_MENU_ITEMS_XPATH = "//div[@ng-show='setUpSpecificStatesHeaderButton']";
    private final static String HELP_MENU_ITEM_XPATH = "//div[@ng-show='setUpSpecificStatesHeaderButton']";
    
    private final static String USERS_XPATH = "";
    private final static String DATA_SOURCES_CSS = "button[data-netapp-id='Data Sources']";
    private final static String EMAIL_XPATH = "";
    private final static String NTP_SERVERS_XPATH = "";
    private final static String NETWORK_XPATH = "";

    
    
    @FindBys(@FindBy(xpath = MANAGE_NAVIGATION_MENU_ITEMS_XPATH))
    @CacheLookup
    private List<WebElement> manageSectionMenuItemNames;
    
    @FindBys(@FindBy(xpath = SETUP_NAVIGATION_MENU_ITEMS_XPATH))
    @CacheLookup
    private List<WebElement> setupSectionMenuItemNames;
    
    @FindBys(@FindBy(xpath = HELP_MENU_ITEM_XPATH))
    @CacheLookup
    private WebElement help;
    
    @FindBy(xpath = USERS_XPATH)
	@CacheLookup
	protected WebElement userLink;
	
	@FindBy(css = DATA_SOURCES_CSS)
	@CacheLookup
	protected WebElement dataSourcesLink;
	
	@FindBy(xpath = EMAIL_XPATH)
	@CacheLookup
	protected WebElement emailLink;
	
	@FindBy(xpath = NTP_SERVERS_XPATH)
	@CacheLookup
	protected WebElement ntpServersLink;
	
	@FindBy(xpath = NETWORK_XPATH)
	@CacheLookup
	protected WebElement networkLink;
    
    
    public WebElement getUserLink() {
		return userLink;
	}


	public void setUserLink(WebElement userLink) {
		this.userLink = userLink;
	}


	public WebElement getDataSourcesLink() {
		return dataSourcesLink;
	}


	public void setDataSourcesLink(WebElement dataSourcesLink) {
		this.dataSourcesLink = dataSourcesLink;
	}


	public WebElement getEmailLink() {
		return emailLink;
	}


	public void setEmailLink(WebElement emailLink) {
		this.emailLink = emailLink;
	}


	public WebElement getNtpServersLink() {
		return ntpServersLink;
	}


	public void setNtpServersLink(WebElement ntpServersLink) {
		this.ntpServersLink = ntpServersLink;
	}


	public WebElement getNetworkLink() {
		return networkLink;
	}


	public void setNetworkLink(WebElement networkLink) {
		this.networkLink = networkLink;
	}


	public List<WebElement> getManageSectionMenuItemNames() {
		return manageSectionMenuItemNames;
	}


	public void setManageSectionMenuItemNames(
			List<WebElement> manageSectionMenuItemNames) {
		this.manageSectionMenuItemNames = manageSectionMenuItemNames;
	}


	public List<WebElement> getSetupSectionMenuItemNames() {
		return setupSectionMenuItemNames;
	}


	public void setSetupSectionMenuItemNames(
			List<WebElement> setupSectionMenuItemNames) {
		this.setupSectionMenuItemNames = setupSectionMenuItemNames;
	}


	public WebElement getHelp() {
		return help;
	}


	public void setHelp(WebElement help) {
		this.help = help;
	}

    
   /* @DataProvider(name = "ExpectedMenuNames")
    public static Object[][] expectedMenuItemNames(){
    	return new Object[][] { new Object[] {
    				"Schedule", 
    				"Connectors", 
    				"Jobs", 
    				"Annotations",
    				"Email Notification",
    				"System Information",
    				"Build from history",
    				"Reset DWH",
    				"Backup/Restore",
    				"Troubleshooting",
    				"User Management"}};
    	
    }*/
    
	
	
}
