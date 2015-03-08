package com.netapp.nwfjsvalidation.testobjects;

import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;
import com.netapp.nwfjsvalidation.lib.utils.SeleniumUtils;
import com.netapp.nwfjsvalidation.setup.Setup;
import com.netapp.nwfjsvalidation.testcases.login.LoginTests;

public class BrowserFunctions {

	
	ExtLogger logger = ExtLogger.getLogger(LoginTests.class);
	
//	String MAXIMIZE_BROWSER_WINDOW = "if (window.screen) " + 
//	        "{window.moveTo(0, 0);window.resizeTo(window.screen.availWidth," +
//	        		"window.screen.availHeight);};";
	
	
	/**
	 * Waits until the browser returns ready state
	 * 
	 * @param driver
	 */
	public static void waitForLoad() {
	    ExpectedCondition<Boolean> pageLoadCondition = new
	        ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	            }
	        };
	    WebDriverWait wait = new WebDriverWait(Setup.getDriver(), 30);
	    wait.until(pageLoadCondition);
	}
	
	
	public static void maximizeBrowser(){
		Setup.getDriver().manage().window().maximize();
	}
	
	/**
	 * Scroll to an web element to make it display on screen 
	 *  
	 * @param element
	 */
	public static void scrollToAnElement(WebElement element) {
		if (SeleniumUtils.isElementExists(element)) {
//			((Locatable) element).getCoordinates().inViewPort();
			Actions action = new Actions(Setup.getDriver());
			action.moveToElement(element).build().perform();
			((JavascriptExecutor)Setup.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
			SeleniumUtils.sleep(5);
		}
	}
	/**
	 * Scroll to the bottom of a page and sleep for 10 seconds
	 * 
	 */
	public static void scrollDown() {
		((JavascriptExecutor) Setup.getDriver())
				.executeScript("scroll(0, 250)");
		SeleniumUtils.sleep(10);
	}

	/**
	 * Scroll to the top of a page and sleep for 10 seconds
	 * 
	 */
	public static void scrollUp() {
		((JavascriptExecutor) Setup.getDriver())
				.executeScript("scroll(250, 0)");
		SeleniumUtils.sleep(10);
	}
	
	/**
	 * Get the handler of the new tab / new browser window page
	 */
	public static WebDriver getWindowHandler(){
		WebDriver driver = Setup.getDriver();
		Set<String> setOfWindows = driver.getWindowHandles();
		WebDriver newDriver = null;
		for (String windowTitle : setOfWindows) {
			newDriver = driver.switchTo().window(windowTitle);
		}
		return newDriver;
	}
	
	public static WebDriver switchToFrame(WebElement element){
		return Setup.getDriver().switchTo().frame(element);
	}
	
	public static WebDriver switchToDefaultContent(){
		return Setup.getDriver().switchTo().defaultContent();
	}
	
}
