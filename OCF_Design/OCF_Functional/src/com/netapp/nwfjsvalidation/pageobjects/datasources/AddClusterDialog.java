package com.netapp.nwfjsvalidation.pageobjects.datasources;

import org.openqa.selenium.WebDriver;

public class AddClusterDialog extends DataSourcesPage {

	WebDriver driver;
	
	public AddClusterDialog(WebDriver webDriver){
		super(webDriver);
		this.driver = webDriver;
	}
}
