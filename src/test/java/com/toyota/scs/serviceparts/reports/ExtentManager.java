package com.toyota.scs.serviceparts.reports;

import java.io.File;
import java.util.Date;

import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import com.toyota.scs.serviceparts.util.*;

public class ExtentManager {
	static ExtentReports extent;
	public static ExtentTest test;

	public static ExtentReports startReport() {
		Date d= new Date();
	//	String FileName=d.toString().replace(":", "_").replace(" ", "_")+".html";
		if (extent == null) {
			extent = new ExtentReports(System.getProperty("user.dir") + "\\test-output\\"+"extent.html", true,
					DisplayOrder.NEWEST_FIRST);

			extent.loadConfig(new File(System.getProperty("user.dir") + "\\ReportConfig.xml"));
			
		}
		return extent;


	}


}
