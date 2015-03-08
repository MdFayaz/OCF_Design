package com.netapp.nwfjsvalidation.testobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

import com.netapp.nwfjsvalidation.setup.Setup;

public class Alerts {

	/*private WebDriver driver;
	
	public Alerts(){
		this.driver = Setup.getDriver();
	}*/
	
	static Alert alertHandler ;

	/**
	 * Verify and handle the expected alert element message
	 * 
	 * @param doDiscard
	 */
	public static void verifyAndHandleAlert(boolean doDiscard, boolean logWarning) {
		if (isAlertPresent()) {
			if (doDiscard) {
				alertHandler.dismiss();
				//TODO:insert log message as, logInfo("Alert message was discarded ", true);
			} else {
				alertHandler.accept();
				//TODO:insert log message as, logInfo("Alert message was accepted ", true);
			}
		} 
		if(logWarning){
			//TODO:insert log message as, logWarning("No alert was present to handle");
		}
	} 
	
	/**
	 * Method to verify whether alert exists of not
	 */
	public static boolean isAlertPresent(){
		try {
			alertHandler = Setup.getDriver().switchTo().alert();
			return true;
		} catch (NoAlertPresentException nape) {
			return false;
		}
	}
	
	/**
	 * As a default handler for Alerts, it always discards
	 * 
	 */
	public static void verifyAndHandleAlert(){
		verifyAndHandleAlert(true, false);
	}
}
