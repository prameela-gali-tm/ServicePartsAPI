package com.toyota.scs.serviceparts.util;

import com.toyota.scs.serviceparts.AutolibRuntimeException;

/**
 * This class holds the environment default values.
 */
public class TestEnvironment {

	public static String ENVIRONMENT = "NOT DEFINED";
	public static String APP_URL = "NOT DEFINED";
	public static String APP_SERVICE_URL = null;
	public static String Filepath = null;
	public static String DATABASE_CONNECTION_URL = null;

	public static void initializeEnvironent() {

		// For each environment, a function will be called to define the environment
		switch (Settings.getProperty("environment").trim().toUpperCase()) {

		case "DEV":
			setDevValues();
			break;

		case "QA":
			setQAValues();
			break;

		case "QAINT":
			setQAInternalValues();
			break;

		case "PROD":
			setProdValues();
			break;
		
		case "LOCAL":
			setLocalValues();
			break;

		default:
			throw new AutolibRuntimeException(String.format("No environment %s found at the Environment class. "
					+ "Please double check your config.properties and make sure it has a proper environment setup.",
					Settings.getProperty("environment")));
		}
	}

	public static void setDevValues() {
		ENVIRONMENT = "DEV";
		APP_SERVICE_URL = "DEV URL";
	}

	public static void setQAValues() {
		ENVIRONMENT = "QA";
		APP_SERVICE_URL = "https://api.qa.toyota.com/tmna/qa/Logistics/v/SupplierOrderInformation/skid";
		Filepath = "C:\\Users\\462205\\framework\\APIframework\\API\\src\\test\\resources\\testdata\\Scenarios1.xlsx";
	}

	public static void setQAInternalValues() {
		ENVIRONMENT = "QAINT";
		// APP_SERVICE_URL =
		// "https://ts.stage.portal.toyotasupplier.com/spbapi/rest/skid";
		APP_SERVICE_URL = "https://ts.stage.portal.toyotasupplier.com/spbapi/rest/";
		Filepath = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\Scenarios1.xlsx";
		System.out.println(Filepath);
		DATABASE_CONNECTION_URL = "jdbc:oracle:thin:@//DC2-UVLORL12Q-SCAN.tmm.na.corp.toyota.com:1590/LIOSS.tmm.na.corp.toyota.com";
	}

	public static void setProdValues() {
		ENVIRONMENT = "PROD";
		APP_SERVICE_URL = "PROD url";
	}
	
		
		
		public static void setLocalValues() {
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		ENVIRONMENT = "LOCAL";
		APP_SERVICE_URL = "http://localhost:8181/";
		Filepath = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\Scenarios1.xlsx";
		//DATABASE_CONNECTION_URL = "jdbc:oracle:thin:@//DC2-UVLORL12Q-SCAN.tmm.na.corp.toyota.com:1590/LIOSS.tmm.na.corp.toyota.com";
	}
}