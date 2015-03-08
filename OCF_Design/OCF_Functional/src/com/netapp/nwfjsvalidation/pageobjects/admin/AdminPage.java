package com.netapp.nwfjsvalidation.pageobjects.admin;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.netapp.nwfjsvalidation.lib.utils.NavigateTo;
import com.netapp.nwfjsvalidation.pageconstants.admin.AdminPageLevelConstants;
import com.netapp.nwfjsvalidation.pageelements.admin.AdminPageElements;
import com.netapp.nwfjsvalidation.pageobjects.dashboard.DashboardPage;
import com.netapp.nwfjsvalidation.pageobjects.datasources.DataSourcesPage;
import com.netapp.nwfjsvalidation.pageobjects.users.UsersPage;


public class AdminPage extends NavigateTo {

	@SuppressWarnings("rawtypes")
	static HashMap<String, Class> objectMap = new HashMap<String, Class>();
	static HashMap<String, WebElement> webElements = new HashMap<String, WebElement>();
	protected AdminPageElements adminPageElements;
	
	WebDriver driver;
	
	public AdminPage(WebDriver webDriver){
		super(webDriver);
		this.driver = webDriver;
		adminPageElements = PageFactory.initElements(this.driver, AdminPageElements.class);
	}
	
	

	public int getMenuItemSize(){
		return adminPageElements.getManageSectionMenuItemNames().size();
	}
    
    public String[] getManageMenuItemNames() {
    	String[] menuNames = new String[getMenuItemSize()];
    	for(int index = 0; index < getMenuItemSize(); index++){
    		menuNames[index] = adminPageElements.getManageSectionMenuItemNames().get(index).getText();
    	}
    	return menuNames;
    }
    
    public String[] getSetupMenuItemNames(){
    	String[] menuNames = new String[getMenuItemSize()];
    	for(int index = 0; index < getMenuItemSize(); index++){
    		menuNames[index] = adminPageElements.getSetupSectionMenuItemNames().get(index).getText();
    	}
    	return menuNames;
    }
    
    public AdminPage navigateTo(String menuPath){
		return (AdminPage) navigateTo(menuPath, getWebElements(), getObjectClass());
	}
    /**
	 * This method make available of all page classes that wish to navigate 
	 * i.e, give destination page class not the current page class
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, Class> getObjectClass(){
		objectMap.put(AdminPageLevelConstants.ADMIN, AdminPage.class);
		objectMap.put(AdminPageLevelConstants.DASHBOARD, DashboardPage.class);
		
		objectMap.put(AdminPageLevelConstants.USERS, UsersPage.class);
		objectMap.put(AdminPageLevelConstants.DATA_SOURCES, DataSourcesPage.class);
		
		return objectMap;
	}
	/**
	 * This method should contains both current page link and the next all navigatable
	 * classes links
	 * 
	 */
	public HashMap<String, WebElement> getWebElements(){
		webElements.put(AdminPageLevelConstants.ADMIN, adminLink);
		webElements.put(AdminPageLevelConstants.DASHBOARD, dashboardLink);
		webElements.put(AdminPageLevelConstants.HELP, helpLink);
		
		webElements.put(AdminPageLevelConstants.USERS, adminPageElements.getUserLink());
		webElements.put(AdminPageLevelConstants.DATA_SOURCES, adminPageElements.getDataSourcesLink());
		webElements.put(AdminPageLevelConstants.EMAIL, adminPageElements.getEmailLink());
		webElements.put(AdminPageLevelConstants.NTP_SERVERS, adminPageElements.getNtpServersLink());
		webElements.put(AdminPageLevelConstants.NETWORK, adminPageElements.getNetworkLink());
		
		return webElements;
	}
    
}
