package com.toyota.scs.serviceparts.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.google.gson.JsonIOException;
import com.relevantcodes.extentreports.LogStatus;
import com.toyota.scs.serviceparts.BaseTest;
import com.toyota.scs.serviceparts.Headers;
import com.toyota.scs.serviceparts.RestService;
import com.toyota.scs.serviceparts.reports.ExtentManager;
import com.toyota.scs.serviceparts.util.ExcelUtil;
import com.toyota.scs.serviceparts.util.TestClass;
import com.toyota.scs.serviceparts.util.TestEnvironment;
import com.toyota.scs.serviceparts.util.TestNGListener;
import com.toyota.scs.serviceparts.util.TestUtil;

@Listeners(TestNGListener.class)
@TestClass(description = "ServicePartsVendorTests")
public class ServicePartsVendorTests extends BaseTest {

	RestService rs = new RestService();
	public String reqjson;

	String Jsonfile;
	String Inputfile;
	Method method;
	public String testName;
	public ArrayList testdata;
	public String respjpathtoGet;
	public String reqJpathtoGet;
	public String reqFirstJpathtoSet;
	public String ReqjpathtoRemove;
	public String secondReqJpathtoSet;
	public String secondReqJpathtoRemove;
	public String reqSecondJpathtoSet;
	public String valuetosetOnfirstJpath;
	public String fpath;
	public String runOption = "Y";
	TestClass classAnnotation;
	Test testAnnotation;
	ITestResult result;
	ThreadLocal<Integer> currentRow = new ThreadLocal<>();
	TestUtil tu = new TestUtil();

	@BeforeClass
	public void getClassname() {
		rep.setTestRunnerOutput(rep.getClass().getName());
		rep.addSystemInfo("TestClass One", this.getClass().getName());
	}

	@DataProvider(name = "VendorsDataProvider")
	public Iterator<Object[]> testData(Method method) throws FilloException {
		// TestEnvironment.initializeEnvironent();
		testName = method.getName();
		Collection<Object[]> dp = new ArrayList<>();
		Recordset supplierdata = ExcelUtil.readSheetData("Vendors", testName, runOption);
		int count = supplierdata.getCount();
		while (supplierdata.next()) {
			dp.add(new Object[] { Integer.parseInt((supplierdata.getField(0).value())) });

		}

		dp.size();
		return dp.iterator();

	}

	@Test(priority = 1, dataProvider = "VendorsDataProvider")
	public void VerifyVendorsSuccessCode(Integer row)
			throws ParseException, IOException, JsonIOException, FilloException, InterruptedException, JSONException {
		test = rep.startTest("VerifyVendorsSuccessCode-verify confirmation number is generated");

		currentRow.set(row);
		GetDataWithRowNum(row);
		test.log(LogStatus.INFO, " VerifyConfirmationnumberGenerated test started");
		HttpPost req = tu.prepareHTTPPost(Inputfile);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Test uses file..." + Inputfile);
		JSONObject resp = rs.sendPostwithHeaders(req);
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		System.out.println("response..." + resp);
		// pipeline in jenkins

	}

	@Test(priority = 1, dataProvider = "VendorsDataProvider")
	public void VerifyVendorsFailCode(Integer row)
			throws ParseException, IOException, JsonIOException, FilloException, InterruptedException, JSONException {
		test = rep.startTest("VerifyVendorsSuccessCode-verify confirmation number is generated");

		currentRow.set(row);
		GetDataWithRowNum(row);
		test.log(LogStatus.INFO, " VerifyConfirmationnumberGenerated test started");
		HttpPost req = tu.prepareForFailureHTTPPost(Inputfile);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Test uses file..." + Inputfile);
		JSONObject resp = rs.sendPostwithHeaders(req);
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		System.out.println("response..." + resp);
		// pipeline in jenkins

	}

	public void GetDataWithRowNum(Integer RowNum) throws FilloException {

		System.out.println(testName + "...started");
		System.out.println(TestEnvironment.Filepath);
		testdata = (ArrayList) ExcelUtil.readRowDataWithTCID("Vendors", RowNum);

		int i = 2;
		runOption = (String) testdata.get(i++);
		Headers.AuthorizationfromExcel = (String) testdata.get(i++);
		Jsonfile = (String) testdata.get(i++);
		Inputfile = Jsonfile + ".json";
		respjpathtoGet = (String) testdata.get(i++);
		reqJpathtoGet = (String) testdata.get(i++);
		reqFirstJpathtoSet = (String) testdata.get(i++);
		reqSecondJpathtoSet = (String) testdata.get(i++);
		ReqjpathtoRemove = (String) testdata.get(i++);
		secondReqJpathtoRemove = (String) testdata.get(i++);
		valuetosetOnfirstJpath = (String) testdata.get(i++);
	}

	public String prepareRequestArraytoJString(String JReqString)
			throws JsonIOException, IOException, FilloException, InterruptedException {
		String JString = null;

		if (JReqString.charAt(0) == '[') {

			JString = JReqString.substring(1, (JReqString.length() - 1));
		} else {
			JString = JReqString;
		}
		return JString;
	}

	public String prepareRequestJStringtoArray(String originalRequest, String UpdatedReq) {

		String PreparedReq = null;
		if (originalRequest.charAt(0) == '[') {
			PreparedReq = "[" + UpdatedReq + "]";
		} else {
			PreparedReq = UpdatedReq;
		}

		return PreparedReq;

	}
	
	@Test(priority = 1, dataProvider = "VendorsDataProvider")
	public void VerifyVendorCodeLength(Integer row) {
		
	}

	
	@Test(priority = 1, dataProvider = "VendorsDataProvider")
	public void VerifyModifiedDateFormat(Integer row) {
		
	}
}
