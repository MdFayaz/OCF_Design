package com.netapp.nwfjsvalidation.setup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.xml.XmlTest;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;

public class Setup {

	private static ExtLogger logger = ExtLogger.getLogger(Setup.class);
	
	public static String browserName;
	public static String browserVersion;
	public static boolean writeBrowserStringToFile;
	public static String browserVersionString = " ";

	private static String remoteServerIP, remoteServerPort;

	private static int height, width, driverTimeout;

	private static boolean isIEBrowser, isChromeBrowser, gridEnabled;

	public static void setRemoteServerIP(String serverIP) {
		remoteServerIP = serverIP;
	}

	public static String getRemoteServerIP() {
		return remoteServerIP;
	}

	public static void setRemoteServerPort(String serverPort) {
		remoteServerPort = serverPort;
	}

	public static String getRemoteServerPort() {
		return remoteServerPort;
	}

	public static void setBrowserHeight(int browserHeight) {
		height = browserHeight;
	}

	public static int getBrowserHeight() {
		return height;
	}

	public static void setBrowserWidth(int browserWidth) {
		width = browserWidth;
	}

	public static int getBrowserWidth() {
		return width;
	}

	public static void setDriverTimeout(int timeout) {
		driverTimeout = timeout;
	}

	public static int getDriverDefaultTimeout() {
		return driverTimeout;
	}

	private static boolean isGridEnabled() {
		return gridEnabled;
	}

	public static boolean isIEBrowser() {
		return isIEBrowser;
	}
	
	public static boolean isChromeBrowser() {
		return isChromeBrowser;
	}
	
	public static void setBrowserName(String name){
		browserName = name;
	}
	
	public static String getBrowserName(){
		return browserName;
	}
	
	public static void setBrowserVersion(String version){
		browserVersion = version;
	}
	
	public static String getBrowserVersion(){
		return browserVersion;
	}
	
	private static DesiredCapabilities init(XmlTest config) {

		// System.getenv("grid_client");
		setRemoteServerIP(System.getenv("grid_client"));

		// System.getenv("grid_port");
		setRemoteServerPort(System.getenv("grid_port"));

		String driverTimeoutParam = System.getenv("driver_timeout");

		setBrowserHeight(Integer.parseInt(config
				.getParameter("browser_window_height")));

		setBrowserWidth(Integer.parseInt(config
				.getParameter("browser_window_width")));

		gridEnabled = Boolean.valueOf(config.getParameter("grid_enabled"));

		/* if no environmental variables */
		if (null == driverTimeoutParam) {
			driverTimeoutParam = config.getParameter("driver_timeout");
			logger.info("driver timeout  Params are not passed as environmental variables. Setting driver timeout parameter from the Config XML ");
		}
		if (driverTimeoutParam != null) {
			setDriverTimeout(Integer.parseInt(driverTimeoutParam));
		}

		logger.info("Setting default driver timeout to "
				+ getDriverDefaultTimeout());

		if (getRemoteServerIP() == null) {
			logger.info("Grid client ip Params are not passed as environmental variables. Setting grid_client parameter from the Config XML ");
			setRemoteServerIP(config.getParameter("grid_client"));
		}

		if (getRemoteServerPort() == null) {
			logger.info("Grid port Params are not passed as environmental variables. Setting grid_port parameter from the Config XML ");
			setRemoteServerPort(config.getParameter("grid_port"));
		}
		
		return getBrowserType(config);
	}
	
	
	private static DesiredCapabilities getBrowserType(XmlTest config) {
		setBrowserName(System.getenv("client_browser"));
		setBrowserVersion(System.getenv("client_browserversion"));
		
		DesiredCapabilities capability;

		if (getBrowserName() == null) {
			logger.info("Browser Params are not passed as environmental variables. Setting browser name parama from the Config XML ");
			setBrowserName(config.getParameter("client_browser").toLowerCase());
		}

		if (getBrowserVersion() == null) {
			logger.info("Browser version Params are not passed as environmental variables. Setting version param from the Config XML ");
			setBrowserVersion(config.getParameter("client_browserversion"));
		}
		
		logger.info("Client browser name:" + getBrowserName());
		logger.info("Client browser version:" + getBrowserVersion());

		/*
		 * getting the browser name and browser versions as environmental
		 * variables
		 */
		logger.info("Client browser version system property is :"
				+ System.getProperty("browser_version"));
		if (!writeBrowserStringToFile
				&& !(browserVersionString.equalsIgnoreCase(System
						.getProperty("browser_version")))) {
			String fileName = "";

			try {
				String userDir = System.getProperty("user.dir");

				logger.info("Current working directory is " + userDir);
				fileName = userDir + "//browser.txt";
				browserVersionString = getBrowserName() + "::"
						+ getBrowserVersion().split("[A-Z|a-z|_|-]+")[1];
				logger.info("browser name and browser version string is"
						+ browserVersionString);
				System.setProperty("browser_version", browserVersionString);
				// writeBrowserStringToFile = Common.writeToFile(fileName,
				// browserVersionString);
			} catch (Exception e) {
				logger.info("Error while writing  the browser file " + fileName);

			}

		}
		if (getBrowserName().contains("firefox")
				|| getBrowserName().equalsIgnoreCase("firefox")) {
			// Firefox Driver

			logger.info("Configuring settings for Firefox");
			capability = DesiredCapabilities.firefox();
			capability.setPlatform(Platform.ANY);

		} else if (getBrowserName().contains("internet explorer")
				|| getBrowserName().equalsIgnoreCase("internet explorer")) {

			// IE Driver
			isIEBrowser = true;
			logger.info("Configuring settings for IE");
			capability = DesiredCapabilities.internetExplorer();
			capability
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			// capability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,
			// true);

		} else if (getBrowserName().contains("chrome")
				|| getBrowserName().equalsIgnoreCase("chrome")) {

			logger.info("Configuring settings for Chrome");
			// Google Chrome Driver
			isChromeBrowser = true;
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-web-security");
			options.addArguments("--start-maximized");

			// For use with RemoteWebDriver:
			capability = DesiredCapabilities.chrome();
			capability.setCapability(ChromeOptions.CAPABILITY, options);

		} else if (getBrowserName().contains("opera")
				|| getBrowserName().equalsIgnoreCase("opera")) {

			// Opera Driver

			capability = DesiredCapabilities.opera();
			capability.setJavascriptEnabled(true);

		} else {
			capability = DesiredCapabilities.htmlUnit();
		}

		return capability;
	}

	static RemoteWebDriver driver;

	/**
	 * This method gives the remote web driver, based on the config XML file
	 * parameters
	 * 
	 * @param config
	 * @return
	 */
	public static RemoteWebDriver getWebDriver(XmlTest config) {
		DesiredCapabilities capability = init(config);

		// URL of the remote device to run the test scripts

		String url = "http://" + getRemoteServerIP() + ":" + getRemoteServerPort() + "/wd/hub";
		URL clientURL = null;
		try {
			clientURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (isGridEnabled()) {
			capability.setVersion(getBrowserVersion());
		}
		driver = new RemoteWebDriver(clientURL, capability);
		
		// Code to Maximize the browser
		if (!isIEBrowser()) {
			logger.info("Maximizing the browser");
			driver.manage().window().setPosition(new Point(0, 0));
			Dimension dim = new Dimension(getBrowserHeight(), getBrowserWidth());
			driver.manage().window().setSize(dim);
		}
		// Allow timeout to be set by a config parameter - used by Performance
		// Team
		driver.manage().timeouts()
				.implicitlyWait(driverTimeout, TimeUnit.SECONDS);
		
		logger.testCaseLog("Initialization of driver:: " + driver);
		
		return driver;
	}
	
	
	public static RemoteWebDriver getDriver(){
		return driver;
	}
	
	public static boolean isDriverActive() {
		return (driver != null && !driver.toString().contains("null"));
	}
	
	
}
