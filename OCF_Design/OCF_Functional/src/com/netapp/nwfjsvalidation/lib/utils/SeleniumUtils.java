package com.netapp.nwfjsvalidation.lib.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.netapp.nwfjsvalidation.setup.Setup;
import com.netapp.nwfjsvalidation.testcases.login.LoginTests;
import com.netapp.nwfjsvalidation.testobjects.BrowserFunctions;

public class SeleniumUtils{

	static ExtLogger logger = ExtLogger.getLogger(LoginTests.class);
	
	  private static final int DEFAULTTIMEOUT = 20;
//	    private static final String LOADING = "//div[contains(text(),'Loading')]";
//	    private static final int POLLING_INTERVAL = 1000;
//	    private static final Long WAIT_FOR_DISABLE_TIMEOUT = new Long(60000);
//	    private static final Long TIMEOUT = new Long(300000);
	
	/**
	 * Sleep for the given number of seconds
	 * @param sec
	 */
	public static void sleep(long sec) {
		try {
			if (Setup.isDriverActive()) {
				Setup.getDriver().manage().timeouts()
						.implicitlyWait(sec, TimeUnit.SECONDS);
			} else {
				System.out.println("Driver not active or not initialized");
			}
		} catch (SessionNotFoundException snfe) {
			logger.testLog(Level.ERROR, "Web Driver " + Setup.getDriver()
					+ " session not found. Possibly, you may be "
					+ "calling sleep() after closing web driver object.");
		}
		/*
		 * try { Thread.sleep(sec * 1000); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
	}
	
	/**
	 * Method to verify whether desired web element is exists or not by handling
	 * exceptions
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isElementExists(WebElement element) {
		boolean result = false;
		
		try {
			if (element != null) {
				result = element.isDisplayed();
			}
		} catch (NoSuchElementException noElementExecption) {
			//Element may not find, hence can return false
		} catch (StaleElementReferenceException staleElementException) {
			//Element may not find, hence can return false
		}
		return result;
	}
	
	/**
	 * Returns properties list of an element
	 */
	public static Object getElementProperties(WebElement element){
		Object object = ((JavascriptExecutor)Setup.getDriver()).executeScript("var item{}; " +
				"for (index = 0; index < argument[0].attributes.length(); ++index) {" +
				"items[arguments[0]].attributes[index].name = arguments[0].attributes[index].value};" +
				"return items;", element);
		return object;
	}
	
	/**
	 * Method to clear and fill value in text field 
	 * 
	 * @param element
	 * @param value
	 * @param key
	 */
	public static void setText(WebElement element, String text){
		setText(element, text, "");
	}
	
	/**
	 * overloaded setText(element, text) method 
	 * @param element
	 * @param text
	 * @param key
	 */
	public static void setText(WebElement element, String text, String key){
		if(text != null/* && isElementExists(element)*/){
			if (element.isEnabled()) {
				try {
					element.click();
					element.clear();
					element.sendKeys(text);
				} catch (InvalidElementStateException iese) {
					//TODO:insert log message as,
//							.logTestResult("WebElement with properties "
//									+ element + " was not enabled", element
//									.isEnabled());
					//TODO:insert log message as, logInfo(INFO, true, true);
				}
			} else {
				//TODO:insert log message as, logTestResult("Element '"+ key
//						+"' was not enabled", false);
			}
		}
	}
	public static final String NOT_AVAILABLE = "Not Available";
	public static final String CLASS = "class"; 
	public static final String ACTIVE = "active";	
	
	/**
	 * Return the available default text value for the parameterized web element
	 * 
	 * @param element
	 * @return
	 */
	public static String getText(WebElement element) {
		String text = NOT_AVAILABLE;
		if (isElementExists(element)) {
			try {
				text = element.getAttribute("value");
			} catch (InvalidElementStateException iese) {
				//TODO:insert log message as, logTestResult("WebElement with properties "
//						+ element + " was not enabled", element.isEnabled());
				//TODO:insert log message as, logInfo(INFO, true, true);
			}
		}
		return text;
	}
	
	/**
	 * Returns the enabled / disabled state of an element
	 * 
	 * @param element
	 * @return
	 */
	public static String getElementState(WebElement element) {
		if (isElementExists(element)) {
			return element.isEnabled() ? "enabled" : "disabled";
		}
		return NOT_AVAILABLE;
	}
	

	/**
	 * Return s the selected / active tab and logs an error if the tab doesn't
	 * exists
	 * 
	 * @param tabName
	 * @return
	 */
	public static String getSelectedTab(Map<String, WebElement> tabbedWebElements, String tabName) {
		if (tabbedWebElements.containsKey(tabName)) {
			Iterator<Entry<String, WebElement>> iterator = tabbedWebElements
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, WebElement> entry = iterator.next();
				WebElement activeElement = tabbedWebElements
						.get(entry.getKey());
				
				 conditionalWait(activeElement, 10);
				
				if (activeElement.getAttribute(CLASS).contains(ACTIVE)) {
					return activeElement.getText();
				}
			}
		} else {
			//TODO: insert log message as, logError("tab '" + tabName + "' not exists");
		}
		return NOT_AVAILABLE;
	}
	
	/**
	 * Method to click / select a button type object
	 * 
	 * @param element
	 * @param value
	 */
	public static void clickTab(WebElement element, String value) {
		boolean activeElement = element.getAttribute(CLASS)
				.contains(ACTIVE);
		if (isElementExists(element) && !activeElement) {
			element.click();
			//TODO: insert log message, logTestResult("Selected " + value, true);
		} else {
			//TODO: insert log message, logInfo("INFO: An element with properties " + element
//					+ " is already active.");
		}
	}
	
	/**
	 * Select the check box, if it is not already enabled
	 * 
	 * @param element
	 * @param value
	 * @param key
	 */
	public static void setCheckBoxState(WebElement element, String value, String key) {
		if (value != null && isElementExists(element)) {
			if (element.isEnabled()) {
				if (element.isSelected() != Boolean.valueOf(value)) {
					element.click();
					//TODO: insert log message, logTestResult("Element '" + key
//							+ "' was selected", true);
				}
			} else {
				//TODO: insert log message, logError("Element '" + key + "' was not enabled");
			}
		}
	}
	
	/**
	 * return the check box state whether it is checked / unchecked, not
	 * considering state (enable / disable ) of the field here
	 * 
	 * @param element
	 * @return
	 */
	public static boolean getCheckBoxState(WebElement element){
		boolean state = false;
		if (isElementExists(element)) {
			state = element.isSelected();
		}
		return state;
	}
	
	
	/**
	 * Asks driver to wait for the presence of an element until given
	 * seconds
	 * 
	 */
	public static WebElement conditionalWait(WebElement waitForElement, long timeInSeconds){
		BrowserFunctions.waitForLoad();
		try {
			WebDriverWait driverWait = new WebDriverWait(Setup.getDriver(),	timeInSeconds);
			driverWait.until(ExpectedConditions.visibilityOf(waitForElement));
		} catch (NoSuchElementException nsee) {
		}
		return waitForElement;
	}
	
	/***********************************************************************************/
	/*******************      Waiting for element methods       ************************/
	/***********************************************************************************/

	
	public static void waitForExistance(WebElement element){
		waitForExistance(element, 20);
	}
	
	public static void waitForExistance(WebElement element, int timeoutInSeconds){
		 if (!element.isDisplayed()) {
			 Setup.getDriver().navigate().refresh();
	        }
	        /**
	         * ended
	         */
	      /* (new WebDriverWait(Setup.getDriver(), timeoutInSeconds)).
	        ignoring(StaleElementReferenceException.class).
	        until(new ExpectedCondition<WebElement>() {
	            @Override
	            public WebElement apply(WebDriver d) {
	                return element;
	            }
	        });
	       */
	       (new WebDriverWait(Setup.getDriver(), DEFAULTTIMEOUT)).
	        ignoring(StaleElementReferenceException.class).
	        until(new ExpectedCondition<WebElement>() {
	            @Override
	            public WebElement apply(WebDriver d) {
	                return element;
	            }
	        });

	}
	
	
	
}
