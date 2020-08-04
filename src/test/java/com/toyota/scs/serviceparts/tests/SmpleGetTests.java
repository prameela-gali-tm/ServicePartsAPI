package com.toyota.scs.serviceparts.tests;

import java.io.IOException;
import java.util.HashMap;
import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.toyota.scs.serviceparts.*;
import com.toyota.scs.serviceparts.reports.ExtentManager;
import com.toyota.scs.serviceparts.util.TestUtil;
import com.toyota.scs.serviceparts.util.TestNGListener;

import com.toyota.scs.serviceparts.util.TestClass;

@Listeners(TestNGListener.class)
@TestClass(description = "GetRequestDemo")
public class SmpleGetTests extends BaseTest{
	

	@Test(priority= 1)
	public void simplegetTest() throws ParseException, IOException, JSONException {
		test = rep.startTest("GetTestWithoutHeaders and Validate Perpage");
		test.log(LogStatus.INFO, "Get request test started");
		RestService rs = new RestService();
		 JSONObject rr= rs.sendGetRequest("https://api.qa.toyota.com/tmna/qa/Logistics/v/SupplierOrderInformation/skid");
		//JSONObject rr = rs.sendGetRequest("http://reqres.in/api/users");
		//String page = TestUtil.getValuebyJpath(rr, "/per_page");
		//String email = TestUtil.getValuebyJpath(rr, "/data[2]/email");
		System.out.println("Per_page Value is..." + rr.toString());
		test.log(LogStatus.PASS, "Get request test Passed and perpage count matched");
	}

	@Test(enabled= false)
	public void getTestWithHeaders() throws ParseException, IOException, NumberFormatException, JSONException {
		test = rep.startTest("Get Request TestWithHeaders");
		test.log(LogStatus.INFO, "Get request WithHeaders test started");
		RestService rs = new RestService();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("Content-Type", "application/json");
		JSONObject rr = rs.sendGetRequestWithHeaders("http://reqres.in/api/users", hm);
		//String page = TestUtil.getValuebyJpath(rr, "/per_page");
		String email = TestUtil.getValuebyJpath(rr, "/data[2]/email");
		System.out.println("email of the third record Value is..." + email);
		test.log(LogStatus.PASS, "Get request test with Headers Passed and email matched the expected");
	}
	
	@AfterMethod
	public void Quit() {
		rep.endTest(test);
		rep.flush();
	}
}
