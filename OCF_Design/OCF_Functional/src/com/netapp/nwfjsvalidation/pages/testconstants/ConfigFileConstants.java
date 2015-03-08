package com.netapp.nwfjsvalidation.pages.testconstants;

import org.testng.xml.XmlTest;

public class ConfigFileConstants {

	private static String SAPP_URL = "sapp_url";
	private static String SAPP_USERNAME = "sapp_username";
	private static String SAPP_PASSWORD = "sapp_password";

//	private static String url;
//	private static String username;
//	private static String password;

	/*public static void setDefaultUrl(XmlTest config) {
		url = config.getParameter(SAPP_URL).toLowerCase();
	}

	public static String getDefaultURL() {
		return url;
	}

	public static void setSappUsername(XmlTest config) {
		username = config.getParameter(SAPP_USERNAME).toLowerCase();
	}

	
	public static String getSappUsername() {
		return username;
	}

	public static void setSappPassword(XmlTest config) {
		password = config.getParameter(SAPP_PASSWORD).toLowerCase();
	}

	
	public static String getSappPassword() {
		return password;
	}
	*/
	public static String getSappPassword(XmlTest config) {
		return config.getParameter(SAPP_PASSWORD).toLowerCase();
	}


	public static String getDefaultURL(XmlTest config) {
		return config.getParameter(SAPP_URL).toLowerCase();
	}
	
	public static String getSappUsername(XmlTest config) {
		return config.getParameter(SAPP_USERNAME).toLowerCase();
	}

}
