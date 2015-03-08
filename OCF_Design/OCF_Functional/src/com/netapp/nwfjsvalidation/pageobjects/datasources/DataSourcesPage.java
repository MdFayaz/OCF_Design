package com.netapp.nwfjsvalidation.pageobjects.datasources;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.netapp.nwfjsvalidation.lib.utils.SeleniumUtils;
import com.netapp.nwfjsvalidation.pageconstants.admin.AdminPageLevelConstants;
import com.netapp.nwfjsvalidation.pageelements.datasources.DataSourcesPageElements;
import com.netapp.nwfjsvalidation.pageobjects.admin.AdminPage;

public class DataSourcesPage extends AdminPage {

	@SuppressWarnings("rawtypes")
	static HashMap<String, Class> objectMap = new HashMap<String, Class>();
	static HashMap<String, WebElement> webElements = new HashMap<String, WebElement>();
	
	WebDriver driver;
	DataSourcesPageElements dsPageElements = null;
	
	public DataSourcesPage(WebDriver webDriver){
		super(webDriver);
		this.driver = webDriver;
		dsPageElements = PageFactory.initElements(this.driver, DataSourcesPageElements.class);
	}
	
	  public AdminPage navigateTo(String menuPath){
			return (AdminPage)navigateTo(menuPath, getWebElements(), getObjectClass());
		}
	    /**
		 * This method make available of all page classes that wish to navigate 
		 * i.e, give destination page class not the current page class
		 * 
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public HashMap<String, Class> getObjectClass(){
			objectMap.put(AdminPageLevelConstants.ADD_CLUSTER_DIALOG, AddClusterDialog.class);
			
		/*	objectMap.put(AdminPageLevelConstants.ADMIN, AdminPage.class);
			objectMap.put(AdminPageLevelConstants.DASHBOARD, DashboardPage.class);
			
			objectMap.put(AdminPageLevelConstants.USERS, UsersPage.class);
			objectMap.put(AdminPageLevelConstants.DATA_SOURCES, DataSourcesPage.class);*/
			
			return objectMap;
		}
		/**
		 * This method should contains both current page link and the next all navigatable
		 * classes links
		 * 
		 */
		public HashMap<String, WebElement> getWebElements(){
			/*webElements.put(AdminPageLevelConstants.ADMIN, adminLink);d                                           
			webElements.put(AdminPageLevelConstants.DASHBOARD, dashboardLink);
			webElements.put(AdminPageLevelConstants.HELP, helpLink);
			
			webElements.put(AdminPageLevelConstants.USERS, adminPageElements.getUserLink());
			webElements.put(AdminPageLevelConstants.EMAIL, adminPageElements.getEmailLink());
			webElements.put(AdminPageLevelConstants.NTP_SERVERS, adminPageElements.getNtpServersLink());
			webElements.put(AdminPageLevelConstants.NETWORK, adminPageElements.getNetworkLink());*/
			
			webElements.put(AdminPageLevelConstants.DATA_SOURCES, adminPageElements.getDataSourcesLink());
			webElements.put(AdminPageLevelConstants.ADD_CLUSTER_DIALOG, adminPageElements.getNetworkLink());
			
			return webElements;
		}
		
		public String getPageTitle() {
			String pageTitle = "";
			if(SeleniumUtils.isElementExists(dsPageElements.getDataSourcesPageTitle())) {
				pageTitle = dsPageElements.getDataSourcesPageTitle().getText().trim();
			}
			return pageTitle;
		}
		
}
