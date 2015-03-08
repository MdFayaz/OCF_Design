package com.netapp.nwfjsvalidation.testcases.users;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class UserTests {

	
	private WebDriver driver;
	
	public UserTests(WebDriver webDriver){
		this.driver = webDriver;
	}
	
	@Test
	public void testUsers(){
		driver.close();
	}
	
}
