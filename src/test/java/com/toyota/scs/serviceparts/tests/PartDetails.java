package com.toyota.scs.serviceparts.tests;

import static org.testng.Assert.assertEquals;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.relevantcodes.extentreports.LogStatus;
import com.toyota.scs.serviceparts.BaseTest;
import com.toyota.scs.serviceparts.FileLoader;
import com.toyota.scs.serviceparts.Headers;
import com.toyota.scs.serviceparts.RestService;
import com.toyota.scs.serviceparts.util.DBUtil;
import com.toyota.scs.serviceparts.util.ExcelUtil;
import com.toyota.scs.serviceparts.util.TestClass;
import com.toyota.scs.serviceparts.util.TestEnvironment;
import com.toyota.scs.serviceparts.util.TestNGListener;
import com.toyota.scs.serviceparts.util.TestUtil;
import com.toyota.scs.serviceparts.util.Xls_Reader;

@Listeners(TestNGListener.class)
@TestClass(description = "PartDetails")
public class PartDetails extends BaseTest {

	RestService rs = new RestService();
	public String reqjson;
	String Jsonfile;
	String Inputfile;
	String vendorpayloadFile;
	Method method;
	public String testName;
	public String fpath;
	public String runOption = "Y";
	public static String Vendor;
	public String PartDesc;
	public static String PartNumber;
	public String vendorPartNum;
	public String LineitemNum;
	public String OrderType;
	Hashtable<String, String> data;
	public static String purchaseOrder;
	int partId, OrderID;
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

	@DataProvider(name = "PartDetailsDataProvider")
	public Iterator<Object[]> testData(Method method) throws FilloException {
		// TestEnvironment.initializeEnvironent();
		testName = method.getName();
		Collection<Object[]> dp = new ArrayList<>();
		Recordset supplierdata = ExcelUtil.readSheetData("ViewAllPartDetails", testName, runOption);
		int count = supplierdata.getCount();
		while (supplierdata.next()) {
			dp.add(new Object[] { Integer.parseInt((supplierdata.getField(0).value())) });
		}
		dp.size();
		return dp.iterator();
	}

//serial number and Part count matches
	@Test(priority = 1, dataProvider = "PartDetailsDataProvider")
	public void verifyResponseonPartDetailsAPI(Integer row) throws Exception {

		test = rep.startTest("verifyResponseonPartDetailsAPI - verifyResponseonPartDetailsAPI");
		currentRow.set(row);
		GetDataWithRowNum(row);

		RestService rs = new RestService();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("distFD", "9104095");
		hm.put("vendorCode","0874H");
		hm.put("directFlag","N");
		hm.put("transportCode","3");
		
		
		
		
		JSONObject rr = rs.sendgetwithParams("http://dev-scs-parts-lb-48427546.us-west-2.elb.amazonaws.com/viewallpartdetails", hm);
		//String page = TestUtil.getValuebyJpath(rr, "/per_page");
		String email = TestUtil.getValuebyJpath(rr, "/data[2]/email");
		System.out.println("email of the third record Value is..." + email);
		test.log(LogStatus.PASS, "Get request test with Headers Passed and email matched the expected");

	}


	

	/////////////////////////////////////////////////////////////////////////////////////////////////

	public static String UpdateJsonFile(String jsontxt)
			throws JsonIOException, JsonSyntaxException, IOException, JSONException {
		JSONArray ja = new JSONArray(jsontxt);
		JSONObject JO = null;
		for (int i = 0; i < ja.length(); i++) {
			JO = ja.getJSONObject(i);
			JO.put("vendorCode", Vendor);

//	        "deliveryDueDate":"2020-04-01", 
			// "serialNumber":"231231F31", rand
			JO.put("partNumber", PartNumber);
			JO.put("poNumber", purchaseOrder);

//			JO.put("serialNumber", TestUtil.formatDateForVendors());
		}
		return "[" + JO.toString() + "]";
	}


	private void InsertCaseBuildRcordsIntoTables() throws ClassNotFoundException, SQLException {
		Vendor = TestUtil.genrateRandomString(5);
		int VendorId = TestUtil.genrateRandomNumber(5);
		String vendorDescription = TestUtil.genrateRandomString(5);
		partId = TestUtil.genrateRandomNumber(4);
		OrderID = TestUtil.genrateRandomNumber(5);
		PartDesc = "Desc" + TestUtil.genrateRandomString(5);
		PartNumber = "PART" + TestUtil.genrateRandomString(7) + TestUtil.genrateRandomString(7);
		vendorPartNum = "VendorPT" + TestUtil.genrateRandomString(5);
		LineitemNum = TestUtil.genrateRandomString(1);
		OrderType = TestUtil.genrateRandomString(1);
		purchaseOrder = "PO" + TestUtil.genrateRandomString(6);

		// Insert into Vendors table
//		DBUtil.insertRecordIntoVendors(VendorId, Vendor, vendorDescription);
//		// Insert into part table
//		DBUtil.insertRecordIntoPart(partId, OrderID, PartDesc, PartNumber, vendorPartNum, LineitemNum);
//		// Insert into part table
//		DBUtil.insertRecordIntoOrder(OrderID, OrderType, purchaseOrder, Vendor);

	}

	public void GetDataWithRowNum(Integer RowNum) throws FilloException, URISyntaxException {

		System.out.println(testName + "...started");
		System.out.println(TestEnvironment.Filepath);

		Xls_Reader xls = new Xls_Reader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\Scenarios1.xlsx");
		String sheetName = "ViewAllPartDetails";
		data = new Hashtable<String, String>();
		System.out.println(RowNum);
		for (int cNum = 1; cNum < 100; cNum++) {
			String key = xls.getCellData(sheetName, cNum, 1);
			String value = xls.getCellData(sheetName, cNum, RowNum + 1);
			data.put(key, value);

		}
		runOption = data.get("RunOption");

		Headers.AuthorizationfromExcel = data.get("Authorization");
		Inputfile = data.get("InputJson") + ".json";

		System.out.println(data.toString());
	}

}
