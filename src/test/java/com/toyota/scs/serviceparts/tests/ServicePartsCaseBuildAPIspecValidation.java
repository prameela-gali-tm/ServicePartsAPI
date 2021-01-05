package com.toyota.scs.serviceparts.tests;

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
@TestClass(description = "ServicePartsCaseBuildAPIspecValidation")
public class ServicePartsCaseBuildAPIspecValidation extends BaseTest {

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
	public static String purchaseOrder;
	int partId, OrderID;
	TestClass classAnnotation;
	Test testAnnotation;
	ITestResult result;
	ThreadLocal<Integer> currentRow = new ThreadLocal<>();
	TestUtil tu = new TestUtil();

	public String OrderType;
	Hashtable<String, String> data;

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
		Recordset supplierdata = ExcelUtil.readSheetData("APISpec", testName, runOption);
		int count = supplierdata.getCount();
		while (supplierdata.next()) {
			dp.add(new Object[] { Integer.parseInt((supplierdata.getField(0).value())) });
		}
		dp.size();
		return dp.iterator();
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidHomePosition(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		//GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].poLineHomePosition", "7887xn@,xn");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals( respGet.toString(), "");
		if( !respGet.toString().contains("Home Position must be 1 digit.")) {
			Assert.fail("Home Position must be 1 digit. Messeage not visible.");
		}
		
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateDeliveryDueDateInvalid(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].poLineDeliveryDueDate", "7887xn@,xn");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidPONumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		//GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].poNumber", "7887xn@,xn");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidPOLine(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].poLineNumber", "7887xn@,xn");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidPONumberPOLine(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBuildRequestWithDuplicatePO.json");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidSerialNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].serialNumberDetails[0].serialNumber",
				"7887xn@,xn");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateDuplicateSerialNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].serialNumberDetails[0].serialNumber",
				"231231F37");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		// TODO
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidUnauthorizedVendor(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", "rewqrt543");

		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidDistFD(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidDistFD - test started");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].distFD", "7887xn@,xn79837uwqhewqejqweqiuy");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateMissingDistFD(Integer row) throws Exception {
		test = rep.startTest("ValidateMissingDistFD - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].distFD", "");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateDealerNumberInvalid(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].dealerNumber", "1234565432345676543");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateRFIDInvalid(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].transportationCode", "7887");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateRFIDDuplicate(Integer row) throws Exception {
		test = rep.startTest("ValidateRFIDDuplicate - ValidateRFIDDuplicate");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].rfidDetails[0].rfid", "T11234123456");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].rfidDetails[0].rfid", "T11234123456");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateRFIDTypeInvalid(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].rfidDetails[0].rfid", "T11234123456");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidPartNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", "Part753159025852678687");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		if (errMsg.contains("Part Number must be 20 digits or less")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateMissingPartNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", "");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		if (errMsg.contains("Part Number is required field")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateDuplicatePartNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildDetailedUnitObjects.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", "1234567890987654321");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", "1234567890987654321");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		if (errMsg.contains("Duplicate record exist under the same case number")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidPartQuantity(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidPartQuantity - ValidateInvalidPartQuantity");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "ddggg5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateMissingPartQuantity(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidPartQuantity - ValidateInvalidPartQuantity");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");

	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidTransportationCode(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].transportationCode", "7887");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateRequiredTransportationCode(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].transportationCode", "");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidDirectShipFlag(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);

		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].directShipFlag", "D");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateMissingDirectShipFlag(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
//Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);

		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].directShipFlag", "");

		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateDirectShipFlagDealerMissing(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
//Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);

		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].directShipFlag", "");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateMissingVendorCode(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
//Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", "");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidCaseNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidCaseNumber - ValidateInvalidCaseNumber");
		currentRow.set(row);
		GetDataWithRowNum(row);
//Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", "79798799979879");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		if (errMsg.contains("Case Number must be 8 digits")) {
			test.log(LogStatus.INFO, "Test Passed " + "Error Message " + errMsg);
		} else {
			System.out.println("test failed");
		}Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidCaseNumberTwice60(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
//Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", "hdhrt");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", "75315955");

		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateMissingCaseNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
//Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", "hdhrt");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", "");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateDuplicateCaseNumber(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);
//Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", "hdhrt");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", "75315955");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ValidateInvalidVendorCode(Integer row) throws Exception {
		test = rep.startTest("ValidateInvalidVendorCode - ValidateInvalidVendorCode");
		currentRow.set(row);
		GetDataWithRowNum(row);

		// Case Build request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/caseBuildwithDetailedUnitObject.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", "hdhrtuuhjh");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		Assert.assertEquals(JOGet.getString("confirmationNumber"), "null");
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
		String sheetName = "APISpec";
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
