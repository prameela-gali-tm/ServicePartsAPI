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
@TestClass(description = "ServicePartsCaseBuild")
public class CaseBuildSerialNumbertests extends BaseTest {

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

	@DataProvider(name = "CaseBuildDataProvider")
	public Iterator<Object[]> testData(Method method) throws FilloException {
		// TestEnvironment.initializeEnvironent();
		testName = method.getName();
		Collection<Object[]> dp = new ArrayList<>();
		Recordset supplierdata = ExcelUtil.readSheetData("SerialNumber", testName, runOption);
		int count = supplierdata.getCount();
		while (supplierdata.next()) {
			dp.add(new Object[] { Integer.parseInt((supplierdata.getField(0).value())) });
		}
		dp.size();
		return dp.iterator();
	}

//serial number and Part count matches
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void verify2partcountwith2ValidSerialNumbers(Integer row) throws Exception {

		test = rep.startTest("verify2partcountwith2ValidSerialNumbers - verify2partcountwith2ValidSerialNumbers");
		currentRow.set(row);
		GetDataWithRowNum(row);

		InsertCaseBuildRcordsIntoTables();
		test.log(LogStatus.INFO, " verifyConfNumber test started");
		String vendorReq = FileLoader.readJsonFilefromProject("vendorpayload.json");

		// vendor table
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("IDCodePath"), Integer.toString(OrderID));
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("vendorCodePath"), Vendor);

		System.out.println(vendorReq);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString(vendorReq, "fetchvendor");
		System.out.println("Test uses file..." + vendorReq);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());

		// order table
		String orderReq = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq = TestUtil.setValueintojson(orderReq, data.get("vendorCodePath"), Vendor);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("poNumberPath"), purchaseOrder);
//		orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));
//part table
		String partReq = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderQuantityPath"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderIdPath"), JOOrder.getString("id"));
		partReq = TestUtil.setValueintojson(partReq, data.get("poNumberPath"), purchaseOrder);
		partReq = TestUtil.setValueintojson(partReq, data.get("partNumberPath"), PartNumber);
		System.out.println(partReq);
		HttpPost HTTPPOstreqPart = tu.prepareHTTPPostwithJsonString(partReq, "fetchpart");
		System.out.println("Test uses file..." + partReq);
		JSONObject respPart = rs.sendPostwithHeaders(HTTPPOstreqPart);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart.toString());
		JSONObject JOPart = new JSONObject(respPart.toString());
//Case Buld request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBildWithSerialNumber.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		String casenum = Integer.toString(TestUtil.genrateRandomNumber(8));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", casenum);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "2");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		if (JOGet.getString("confirmationNumber") == null) {
			Assert.assertEquals(true, false);
		} else {
			Assert.assertEquals(true, true);
		}

	}

//Serial number are more than part quantity
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void verifyGreaterSerialNumber(Integer row) throws Exception {

		test = rep.startTest("verify2partcountwith2ValidSerialNumbers - verify2partcountwith2ValidSerialNumbers");
		currentRow.set(row);
		GetDataWithRowNum(row);

		InsertCaseBuildRcordsIntoTables();
		test.log(LogStatus.INFO, " verifyConfNumber test started");
		String vendorReq = FileLoader.readJsonFilefromProject("vendorpayload.json");

		// vendor table
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("IDCodePath"), Integer.toString(OrderID));
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("vendorCodePath"), Vendor);

		System.out.println(vendorReq);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString(vendorReq, "fetchvendor");
		System.out.println("Test uses file..." + vendorReq);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());

		// order table
		String orderReq = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq = TestUtil.setValueintojson(orderReq, data.get("vendorCodePath"), Vendor);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("poNumberPath"), purchaseOrder);
//		orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));
//part table
		String partReq = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderQuantityPath"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderIdPath"), JOOrder.getString("id"));
		partReq = TestUtil.setValueintojson(partReq, data.get("poNumberPath"), purchaseOrder);
		partReq = TestUtil.setValueintojson(partReq, data.get("partNumberPath"), PartNumber);
		System.out.println(partReq);
		HttpPost HTTPPOstreqPart = tu.prepareHTTPPostwithJsonString(partReq, "fetchpart");
		System.out.println("Test uses file..." + partReq);
		JSONObject respPart = rs.sendPostwithHeaders(HTTPPOstreqPart);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart.toString());
		JSONObject JOPart = new JSONObject(respPart.toString());
//Case Buld request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/greaterSerialNumber.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		String casenum = Integer.toString(TestUtil.genrateRandomNumber(8));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", casenum);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "2");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		if (errMsg.contains("Service number count does not match with give part order qunatity")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}
	}

//lesser serial number
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void verifyLesserSerialNumber(Integer row) throws Exception {

		test = rep.startTest("verify2partcountwith2ValidSerialNumbers - verify2partcountwith2ValidSerialNumbers");
		currentRow.set(row);
		GetDataWithRowNum(row);

		InsertCaseBuildRcordsIntoTables();
		test.log(LogStatus.INFO, " verifyConfNumber test started");
		String vendorReq = FileLoader.readJsonFilefromProject("vendorpayload.json");

		// vendor table
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("IDCodePath"), Integer.toString(OrderID));
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("vendorCodePath"), Vendor);

		System.out.println(vendorReq);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString(vendorReq, "fetchvendor");
		System.out.println("Test uses file..." + vendorReq);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());

		// order table
		String orderReq = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq = TestUtil.setValueintojson(orderReq, data.get("vendorCodePath"), Vendor);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("poNumberPath"), purchaseOrder);
//		orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));
//part table
		String partReq = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderQuantityPath"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderIdPath"), JOOrder.getString("id"));
		partReq = TestUtil.setValueintojson(partReq, data.get("poNumberPath"), purchaseOrder);
		partReq = TestUtil.setValueintojson(partReq, data.get("partNumberPath"), PartNumber);
		System.out.println(partReq);
		HttpPost HTTPPOstreqPart = tu.prepareHTTPPostwithJsonString(partReq, "fetchpart");
		System.out.println("Test uses file..." + partReq);
		JSONObject respPart = rs.sendPostwithHeaders(HTTPPOstreqPart);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart.toString());
		JSONObject JOPart = new JSONObject(respPart.toString());
//Case Buld request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBildWithSerialNumber.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		String casenum = Integer.toString(TestUtil.genrateRandomNumber(8));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", casenum);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "3");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		if (errMsg.contains("Service number count does not match with give part order qunatity")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}
	}
	
	// duplicate serial number error

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void verifyDuplicateSerialNumber(Integer row) throws Exception {

		test = rep.startTest("verifyDuplicateSerialNumber - verifyDuplicateSerialNumber");
		currentRow.set(row);
		GetDataWithRowNum(row);

		InsertCaseBuildRcordsIntoTables();
		test.log(LogStatus.INFO, " verifyConfNumber test started");
		String vendorReq = FileLoader.readJsonFilefromProject("vendorpayload.json");

		// vendor table
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("IDCodePath"), Integer.toString(OrderID));
		vendorReq = TestUtil.setValueintojson(vendorReq, data.get("vendorCodePath"), Vendor);

		System.out.println(vendorReq);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString(vendorReq, "fetchvendor");
		System.out.println("Test uses file..." + vendorReq);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());

		// order table
		String orderReq = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq = TestUtil.setValueintojson(orderReq, data.get("vendorCodePath"), Vendor);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("poNumberPath"), purchaseOrder);
//		orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));
//part table
		String partReq = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderQuantityPath"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderIdPath"), JOOrder.getString("id"));
		partReq = TestUtil.setValueintojson(partReq, data.get("poNumberPath"), purchaseOrder);
		partReq = TestUtil.setValueintojson(partReq, data.get("partNumberPath"), PartNumber);
		System.out.println(partReq);
		HttpPost HTTPPOstreqPart = tu.prepareHTTPPostwithJsonString(partReq, "fetchpart");
		System.out.println("Test uses file..." + partReq);
		JSONObject respPart = rs.sendPostwithHeaders(HTTPPOstreqPart);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart.toString());
		JSONObject JOPart = new JSONObject(respPart.toString());
//Case Buld request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/DuplicateSeialNumber.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		String casenum = Integer.toString(TestUtil.genrateRandomNumber(8));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", casenum);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "2");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		if (errMsg.contains("Duplicate serial number present in given payload")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}
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

	public String getDateMinus(int days) {
		String currMonth = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String todaysDate = df.format(cal.getTime());
		System.out.println(todaysDate);

		LocalDate now = LocalDate.now();
		LocalDate backdated = now.minusDays(days);

		if ((String.valueOf(backdated.getMonthValue()).length() < 2)) {
			currMonth = "0" + String.valueOf(backdated.getMonthValue());
		} else {
			currMonth = Integer.toString(backdated.getMonthValue());
		}
		String backdated1 = backdated.getYear() + "-" + currMonth + "-" + backdated.getDayOfMonth();
		return backdated1;
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
		String sheetName = "SerialNumber";
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