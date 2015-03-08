package com.netapp.nwfjsvalidation.lib.utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.netapp.nwfjsvalidation.pageconstants.admin.AdminPageLevelConstants;
import com.netapp.nwfjsvalidation.pageelements.admin.AdminPageElements;
import com.netapp.nwfjsvalidation.testobjects.BrowserFunctions;

public class NavigateTo {

	protected WebDriver driver;
	private static ExtLogger logger = ExtLogger.getLogger(NavigateTo.class);

	private final static String ADMIN_LINK = "//a[@ui-sref='shell.admin']";
	protected final static String DASHBOARD_LINK = "//a[@ui-sref='shell.dashboard']";
	protected final static String HELP_LINK = "//a[@ui-sref='sapp-admin-help-link']";

	@FindBy(xpath = ADMIN_LINK)
	@CacheLookup
	protected WebElement adminLink;

	@FindBy(xpath = DASHBOARD_LINK)
	@CacheLookup
	protected WebElement dashboardLink;

	@FindBy(xpath = HELP_LINK)
	@CacheLookup
	protected WebElement helpLink;

	public NavigateTo(WebDriver webDriver) {
		this.driver = webDriver;
	}

	/**
	 * Here page element is that current page (may be with in a frame) and web
	 * is entire document page
	 */
	HashMap<String, WebElement> pageElements = new HashMap<String, WebElement>();
	HashMap<String, WebElement> webElements = new HashMap<String, WebElement>();

	private final static String FRAME = "_frame";

	/**
	 * This method make available to put all the web elements initiated
	 * 
	 */
	public HashMap<String, WebElement> getWebElements() {
		webElements.put("Admin", adminLink);
		webElements.put(AdminPageLevelConstants.DASHBOARD, dashboardLink);

		return webElements;
	}

	/**
	 * Navigate to the given menu path
	 * 
	 * @param menuPath
	 * @return 
	 */
	public NavigateTo navigateTo(String menuPath) {
		return navigateTo(menuPath, getWebElements(), getObjectClass());
	}

	
	/**
	 * Navigate to a given view, by getting particular web element. It is a
	 * recursive method that calls on the returned class after initial click,
	 * which return class should be a derived class of this class
	 * 
	 * @param menu
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NavigateTo navigateTo(String menu,
			Map<String, WebElement> webElements, Map<String, Class> objectMap) {
		BrowserFunctions.waitForLoad();
		String menuItem = "";
		NavigateTo pageObject = null;
		if (menu != null && !menu.isEmpty() && menu.indexOf("->") != -1) {
			menuItem = menu.substring(0, menu.indexOf("->"));
		} else {
			menuItem = menu;
		}
		System.out.println("Menu::: " + menuItem);
		for(String key : webElements.keySet()) {
			System.out.println("WebElements::: " + webElements.get(key));
		}
		
		if (webElements.containsKey(menuItem)) {
			WebElement elementToClick = webElements.get(menuItem);
			logger.testStepLog("In NavigateTo:: " + menuItem + " :: " + elementToClick);
			click(elementToClick);
			logger.testStepLog("Navigated to '" + menuItem + "' ");
		}

		if (webElements.containsKey(menuItem + FRAME)) {
			click(webElements.get(menuItem + FRAME));
		}
		//TODO: only uncomment if not outer else block
		// if(objectMap.get(menuItem) != null) {
		pageObject = (NavigateTo) PageFactory.initElements(driver,
				objectMap.get(menuItem));
		if (menu.indexOf("->") != -1) {
			pageObject.navigateTo(menu.substring(menu.indexOf("->") + 2),
					pageObject.getWebElements(), pageObject.getObjectClass());
		} else {
			pageObject.initWebElements();

		}
		
		SeleniumUtils.sleep(5);
		return pageObject;
	}

	/**
	 * Perform click operation on a given web element
	 * 
	 * @param element
	 * @return
	 */
	private WebElement click(WebElement element) {
		SeleniumUtils.conditionalWait(element, 10);
		element.click();
		return element;
	}

	/**
	 * An Elements that need to be access from data pool after initialized needs
	 * to be put into this map. Expected element as a key and the corresponding
	 * web element as a value.
	 * 
	 */
	private void initWebElements() {
		// pageElements.put("Admin", admin); this is for example
	}

	@SuppressWarnings("rawtypes")
	HashMap<String, Class> objectMap = new HashMap<String, Class>();

	/**
	 * This method make available of all page classes that can access from dash
	 * board menu
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public HashMap<String, Class> getObjectClass() {
		objectMap.put("Admin", AdminPageElements.class);

		return objectMap;
	}
}
