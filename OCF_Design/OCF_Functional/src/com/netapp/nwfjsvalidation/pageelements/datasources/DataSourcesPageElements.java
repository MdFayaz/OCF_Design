package com.netapp.nwfjsvalidation.pageelements.datasources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataSourcesPageElements {

	WebDriver driver;
	
	public DataSourcesPageElements(WebDriver webDriver) {
		this.driver = webDriver;
	}
	
	private static final String MANAGE_DATA_SOURCE_PAGE_TITLE_XPATH = "//main/div/div[2]/div";
	
	@FindBy (xpath = MANAGE_DATA_SOURCE_PAGE_TITLE_XPATH)
	private WebElement dataSourcesPageTitle;

	public WebElement getDataSourcesPageTitle() {
		return dataSourcesPageTitle;
	}

	public void setDataSourcesPageTitle(WebElement dataSourcesPageTitle) {
		this.dataSourcesPageTitle = dataSourcesPageTitle;
	}
	
}
