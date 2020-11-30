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
@TestClass(description = "ServicePartsCaseBuild")
public class ServicePartsCaseBuild extends BaseTest {

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
		Recordset supplierdata = ExcelUtil.readSheetData("CaseBuild", testName, runOption);
		int count = supplierdata.getCount();
		while (supplierdata.next()) {
			dp.add(new Object[] { Integer.parseInt((supplierdata.getField(0).value())) });
		}
		dp.size();
		return dp.iterator();
	}


//Happypath
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void verifyConfNumber(Integer row) throws Exception {

		test = rep.startTest("verifyConfNumber - verifyConfNumber");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
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
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBuildRequestWithSmallUnitType.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status="+"true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		//no record found
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();

	}

	// Greater quantity
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void SupplierpartQtyMoreThanOrdeQty(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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
//Case build 
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBuildRequestWithSmallUnitType.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq,  data.get("vendorCodePath"), Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "10");
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
		if (JOGet.getString("confirmationNumber").equals("null")) {
			System.out.println("test passed");
		} else {
			Assert.fail();
		}
	}

	// 0 part order count in case build

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void ZeroPartOrderCount(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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

		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBuildRequestWithSmallUnitType.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "-1");
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
		if (JOGet.getString("confirmationNumber").equals("null")) {
			System.out.println("test passed");
		} else {
			Assert.fail();
		}
	}

	// one case number, unit one and 2 with part order same , makes greater quantity,
	// inserting 5 but total is 15.
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void greaterpartQtyScenario2(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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

		String GetReq = FileLoader.readJsonFilefromProject("Casebuildwith2Units.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[1].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[1].units[0].partQuantity", "10");
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
		if (!JOGet.getString("confirmationNumber").equals("null")) {
			Assert.fail();
		} else {
			System.out.println("test passed");
		}
	}

	// Duplicate record on same case should give error message

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void DuplicaterecordAtUnitLevel(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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

		// case build request

		String GetReq = FileLoader.readJsonFilefromProject("CaseBuildWith2Duplicateunits.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		String casenum = Integer.toString(TestUtil.genrateRandomNumber(8));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber", casenum);

		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[1].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[1].partQuantity", "10");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "10");
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

//	2different cases same part number and part quantity should give confirmation number
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void InsertRecordSce5(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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
		partReq = TestUtil.setValueintojson(partReq, data.get("orderQuantityPath"), "10");
		partReq = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "10");
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

		// Case build

		String GetReq = FileLoader.readJsonFilefromProject("Casebuildwith2Units.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[1].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[1].units[0].partQuantity", "5");
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
		if (JOGet.getString("confirmationNumber").equals("null")) {
			Assert.fail();
		} else {
			System.out.println("test passed");
		}
	}

	// Delivery Due date validation with multiple case numbers
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void DeliveryDueDateFulfillmentMavildationwithMultipleCases(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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

		// order table order 1
		String orderReq = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq = TestUtil.setValueintojson(orderReq, data.get("vendorCodePath"), Vendor);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("poNumberPath"), purchaseOrder);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));

		// order 2
		String purchaseorder2 = "PO" + TestUtil.genrateRandomString(6);
		int orderid2 = TestUtil.genrateRandomNumber(5);
		String orderReq2 = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq2 = TestUtil.setValueintojson(orderReq2, data.get("vendorCodePath"), Vendor);
		orderReq2 = TestUtil.setValueintojson(orderReq2, data.get("poNumberPath"), purchaseorder2);
		orderReq2 = TestUtil.setValueintojson(orderReq2, data.get("orderIdPath"), Integer.toString(orderid2));
		System.out.println(orderReq2);
		HttpPost HTTPPOstreqOrder2 = tu.prepareHTTPPostwithJsonString(orderReq2, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder2);
		JSONObject respOrder2 = rs.sendPostwithHeaders(HTTPPOstreqOrder2);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder2.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder2.toString());
		JSONObject JOOrder2 = new JSONObject(respOrder2.toString());
		System.out.println(JOOrder2.getString("id"));

		// order 3
		String purchaseorder3 = "PO" + TestUtil.genrateRandomString(6);
		int orderid3 = TestUtil.genrateRandomNumber(5);
		String orderReq3 = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq3 = TestUtil.setValueintojson(orderReq3, data.get("vendorCodePath"), Vendor);
		orderReq3 = TestUtil.setValueintojson(orderReq3, data.get("poNumberPath"), purchaseorder3);
		orderReq3 = TestUtil.setValueintojson(orderReq3, data.get("orderIdPath"), Integer.toString(orderid3));
		System.out.println(orderReq3);
		HttpPost HTTPPOstreqOrder3 = tu.prepareHTTPPostwithJsonString(orderReq3, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder3);
		JSONObject respOrder3 = rs.sendPostwithHeaders(HTTPPOstreqOrder3);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder3.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder3.toString());
		JSONObject JOOrder3 = new JSONObject(respOrder3.toString());
		System.out.println(JOOrder3.getString("id"));

//		// part table
//		// order 1 today
		String partReq = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderQuantityPath"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderIdPath"), JOOrder.getString("id"));
		// partReq = TestUtil.setValueintojson(partReq, data.get("poNumberPath"),
		// purchaseOrder);
		partReq = TestUtil.setValueintojson(partReq, data.get("partNumberPath"), PartNumber);
		partReq = TestUtil.setValueintojson(partReq, data.get("deliveryDueDatePath"), getDateMinus(0));
		System.out.println(partReq);
		HttpPost HTTPPOstreqPart = tu.prepareHTTPPostwithJsonString(partReq, "fetchpart");
		System.out.println("Test uses file..." + partReq);
		JSONObject respPart = rs.sendPostwithHeaders(HTTPPOstreqPart);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart.toString());
		JSONObject JOPart = new JSONObject(respPart.toString());
//
//		// order 2 today minus 1 day
		String partReq2 = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("orderQuantityPath"), "10");
		partReq2 = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "10");

		partReq2 = TestUtil.setValueintojson(partReq2, data.get("orderIdPath"), JOOrder2.getString("id"));
		// partReq2 = TestUtil.setValueintojson(partReq2, data.get("poNumberPath"),
		// purchaseorder2);
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("partNumberPath"), PartNumber);
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("deliveryDueDatePath"), getDateMinus(1));
		System.out.println(partReq2);
		HttpPost HTTPPOstreqPart2 = tu.prepareHTTPPostwithJsonString(partReq2, "fetchpart");
		System.out.println("Test uses file..." + partReq2);
		JSONObject respPart2 = rs.sendPostwithHeaders(HTTPPOstreqPart2);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart2.toString());
		JSONObject JOPart2 = new JSONObject(respPart2.toString());
//
//		// order 3 today Minus 2 days
		String partReq3 = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq3 = TestUtil.setValueintojson(partReq3, data.get("orderQuantityPath"), "15");
		partReq3 = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "15");

		partReq3 = TestUtil.setValueintojson(partReq3, data.get("orderIdPath"), JOOrder3.getString("id"));
		// partReq3 = TestUtil.setValueintojson(partReq3, data.get("poNumberPath"),
		// purchaseorder3);
		partReq3 = TestUtil.setValueintojson(partReq3, data.get("partNumberPath"), PartNumber);
		partReq3 = TestUtil.setValueintojson(partReq3, data.get("deliveryDueDatePath"), getDateMinus(2));

		HttpPost HTTPPOstreqPart3 = tu.prepareHTTPPostwithJsonString(partReq3, "fetchpart");
		System.out.println("Test uses file..." + partReq3);
		JSONObject respPart3 = rs.sendPostwithHeaders(HTTPPOstreqPart3);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart3.toString());
		JSONObject JOPart3 = new JSONObject(respPart3.toString());
//
//		// Case build

		String GetReq = FileLoader.readJsonFilefromProject("CaseBuildWithmultiplecases.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[1].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[2].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "2");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[1].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[1].units[0].partQuantity", "20");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[2].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[2].units[0].partQuantity", "8");

		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
//		System.out.println(JOGet.getString("confirmationNumber"));
//		System.out.println();
//		if (JOGet.getString("confirmationNumber").equals("null")) {
//			Assert.fail();
//		} else {
//			System.out.println("test passed");
//		}
	}

	// Delivery Due Date validation with Single case and multiple units case numbers
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void DeliveryDueDateFulfillmentMavildation(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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

		// order table order 1
		String orderReq = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq = TestUtil.setValueintojson(orderReq, data.get("vendorCodePath"), Vendor);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("poNumberPath"), purchaseOrder);
		orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));

		// order 2
		String purchaseorder2 = "PO" + TestUtil.genrateRandomString(6);
		int orderid2 = TestUtil.genrateRandomNumber(5);
		String orderReq2 = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq2 = TestUtil.setValueintojson(orderReq2, data.get("vendorCodePath"), Vendor);
		orderReq2 = TestUtil.setValueintojson(orderReq2, data.get("poNumberPath"), purchaseorder2);
		orderReq2 = TestUtil.setValueintojson(orderReq2, data.get("orderIdPath"), Integer.toString(orderid2));
		System.out.println(orderReq2);
		HttpPost HTTPPOstreqOrder2 = tu.prepareHTTPPostwithJsonString(orderReq2, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder2);
		JSONObject respOrder2 = rs.sendPostwithHeaders(HTTPPOstreqOrder2);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder2.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder2.toString());
		JSONObject JOOrder2 = new JSONObject(respOrder2.toString());
		System.out.println(JOOrder2.getString("id"));

		// order 3
		String purchaseorder3 = "PO" + TestUtil.genrateRandomString(6);
		int orderid3 = TestUtil.genrateRandomNumber(5);
		String orderReq3 = FileLoader.readJsonFilefromProject("orderpayload.json");
		orderReq3 = TestUtil.setValueintojson(orderReq3, data.get("vendorCodePath"), Vendor);
		orderReq3 = TestUtil.setValueintojson(orderReq3, data.get("poNumberPath"), purchaseorder3);
		orderReq3 = TestUtil.setValueintojson(orderReq3, data.get("orderIdPath"), Integer.toString(orderid3));
		System.out.println(orderReq3);
		HttpPost HTTPPOstreqOrder3 = tu.prepareHTTPPostwithJsonString(orderReq3, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder3);
		JSONObject respOrder3 = rs.sendPostwithHeaders(HTTPPOstreqOrder3);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder3.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder3.toString());
		JSONObject JOOrder3 = new JSONObject(respOrder3.toString());
		System.out.println(JOOrder3.getString("id"));

//		// part table
//		// order 1 today
		String partReq = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderQuantityPath"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "5");
		partReq = TestUtil.setValueintojson(partReq, data.get("orderIdPath"), JOOrder.getString("id"));
		// partReq = TestUtil.setValueintojson(partReq, data.get("poNumberPath"),
		// purchaseOrder);
		partReq = TestUtil.setValueintojson(partReq, data.get("partNumberPath"), PartNumber);
		partReq = TestUtil.setValueintojson(partReq, data.get("deliveryDueDatePath"), getDateMinus(0));
		System.out.println(partReq);
		HttpPost HTTPPOstreqPart = tu.prepareHTTPPostwithJsonString(partReq, "fetchpart");
		System.out.println("Test uses file..." + partReq);
		JSONObject respPart = rs.sendPostwithHeaders(HTTPPOstreqPart);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart.toString());
		JSONObject JOPart = new JSONObject(respPart.toString());
//
//		// order 2 today minus 1 day
		String partReq2 = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("orderQuantityPath"), "10");
		partReq2 = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "10");

		partReq2 = TestUtil.setValueintojson(partReq2, data.get("orderIdPath"), JOOrder2.getString("id"));
		// partReq2 = TestUtil.setValueintojson(partReq2, data.get("poNumberPath"),
		// purchaseorder2);
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("partNumberPath"), PartNumber);
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("deliveryDueDatePath"), getDateMinus(1));
		System.out.println(partReq2);
		HttpPost HTTPPOstreqPart2 = tu.prepareHTTPPostwithJsonString(partReq2, "fetchpart");
		System.out.println("Test uses file..." + partReq2);
		JSONObject respPart2 = rs.sendPostwithHeaders(HTTPPOstreqPart2);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart2.toString());
		JSONObject JOPart2 = new JSONObject(respPart2.toString());
//
//		// order 3 today Minus 2 days
		String partReq3 = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq3 = TestUtil.setValueintojson(partReq3, data.get("orderQuantityPath"), "15");
		partReq3 = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "15");

		partReq3 = TestUtil.setValueintojson(partReq3, data.get("orderIdPath"), JOOrder3.getString("id"));
		// partReq3 = TestUtil.setValueintojson(partReq3, data.get("poNumberPath"),
		// purchaseorder3);
		partReq3 = TestUtil.setValueintojson(partReq3, data.get("partNumberPath"), PartNumber);
		partReq3 = TestUtil.setValueintojson(partReq3, data.get("deliveryDueDatePath"), getDateMinus(2));

		HttpPost HTTPPOstreqPart3 = tu.prepareHTTPPostwithJsonString(partReq3, "fetchpart");
		System.out.println("Test uses file..." + partReq3);
		JSONObject respPart3 = rs.sendPostwithHeaders(HTTPPOstreqPart3);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart3.toString());
		JSONObject JOPart3 = new JSONObject(respPart3.toString());
//
//		// Case build

		String GetReq = FileLoader.readJsonFilefromProject("caseBuildwith3Parts.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "2");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[1].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[1].partQuantity", "20");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[2].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[2].partQuantity", "8");

		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/messages[0]/message");
		
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void verifyallPurchaseOrderCountFulfillment(Integer row) throws Exception {

		test = rep.startTest("verifyallPurchaseOrderCountFulfillment - verifyallPurchaseOrderCountFulfillment");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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
//			orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));
		// part table
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
		
		// Case Buld request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBuildRequestWithSmallUnitType.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		String errMsg = TestUtil.getValuebyJpath(JOGet, "/responseCaseBuildDetails/cases[0]/units[0]/partPOLineStatus");
		if (errMsg.contains("FULL FILLED")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}

		String partQtyRemaining = TestUtil.getValuebyJpath(JOGet, "/responseCaseBuildDetails/cases[0]/units[0]/partPOLineQuantityRemaining");
		if (partQtyRemaining.contains("0")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}
	}

	
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void verifyPartialPurchaseOrderStatus(Integer row) throws Exception {

		test = rep.startTest("verifyPartialPurchaseOrderStatus - verifyPartialPurchaseOrderStatus");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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
//			orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));
		// part table
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
		
		// Case Buld request
		String GetReq = FileLoader.readJsonFilefromProject("casebuild/CaseBuildRequestWithSmallUnitType.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "4");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
			String errMsg = TestUtil.getValuebyJpath(JOGet, "/responseCaseBuildDetails/cases[0]/units[0]/partPOLineStatus");
		if (errMsg.contains("PARTIAL")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}

		String partQtyRemaining = TestUtil.getValuebyJpath(JOGet, "/responseCaseBuildDetails/cases[0]/units[0]/partPOLineQuantityRemaining");
		if (partQtyRemaining.contains("1")) {
			System.out.println("test passed");
		} else {
			System.out.println("test failed");
		}

	}
	
	//CaseBuild request with 2 different type units detailed and short units. This should fail because case should contain similar units
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void HappyPathWithMixeUnitTypesUnderSameCase(Integer row) throws Exception {

		test = rep.startTest("HappyPathWithMixeUnitTypesUnderSameCase - HappyPathWithMixeUnitTypesUnderSameCase");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
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
		

		//second record in part Table
		String partReq2 = FileLoader.readJsonFilefromProject("partpayload.json");
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("orderQuantityPath"), "10");
		partReq2 = TestUtil.setValueintojson(partReq, data.get("outstandingQuantity"), "10");

		partReq2 = TestUtil.setValueintojson(partReq2, data.get("orderIdPath"), JOOrder.getString("id"));

		String partnumber2="PART" + TestUtil.genrateRandomString(7) + TestUtil.genrateRandomString(7);
		partReq2 = TestUtil.setValueintojson(partReq2, data.get("partNumberPath"),partnumber2);
		partReq2 = TestUtil.setValueintojson(partReq2, "$.deliveryDueDate", getDateMinus(1));
		System.out.println(partReq2);
		HttpPost HTTPPOstreqPart2 = tu.prepareHTTPPostwithJsonString(partReq2, "fetchpart");
		System.out.println("Test uses file..." + partReq2);
		JSONObject respPart2 = rs.sendPostwithHeaders(HTTPPOstreqPart2);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + respPart2.toString());
		JSONObject JOPart2 = new JSONObject(respPart2.toString());
		
		//Case Buld request
		String GetReq = FileLoader.readJsonFilefromProject("CaseBuildRequestWithMixedUnitTypes.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",
				Integer.toString(TestUtil.genrateRandomNumber(8)));
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "5");
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[1].partNumber", partnumber2);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[1].partQuantity", "8");
		System.out.println(GetReq);
		HttpPost HTTPPOstreqGet = tu.prepareHTTPPostwithJsonString("[" + GetReq + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet = rs.sendPostwithHeaders(HTTPPOstreqGet);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet = new JSONObject(respGet.toString());
		

	}

	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//                           Case build Re-processing
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// Reprocessing
	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void CaseBuildReprocessing(Integer row) throws Exception {

		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);

		generateRandomDataForTableRecords();
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
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
//			orderReq = TestUtil.setValueintojson(orderReq, data.get("orderIdPath"), Integer.toString(OrderID));
		System.out.println(orderReq);
		HttpPost HTTPPOstreqOrder = tu.prepareHTTPPostwithJsonString(orderReq, "fetchorder");
		System.out.println("Test uses file..." + HTTPPOstreqOrder);
		JSONObject respOrder = rs.sendPostwithHeaders(HTTPPOstreqOrder);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respOrder.toString());
		test.log(LogStatus.INFO, "received response-----" + respOrder.toString());
		JSONObject JOOrder = new JSONObject(respOrder.toString());
		System.out.println(JOOrder.getString("id"));
		// part table
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
		
		String casenum=Integer.toString(TestUtil.genrateRandomNumber(8));
			
		// Case build send first time
	
		String GetReq = FileLoader.readJsonFilefromProject("CaseBuildRequestWithSmallUnitType.json");
		GetReq = TestUtil.setValueintojson(GetReq, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].caseNumber",casenum);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq = TestUtil.setValueintojson(GetReq, "$.cases[0].units[0].partQuantity", "4");
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
		if (!JOGet.getString("confirmationNumber").equals("null")) {
			System.out.println("test passed");
		} else {
			Assert.fail();
		}
		// Case build send Second time time
		String GetReq1 = FileLoader.readJsonFilefromProject("CaseBuildRequestWithSmallUnitType.json");
		GetReq1 = TestUtil.setValueintojson(GetReq1, "$.vendorCode", Vendor);
		GetReq = TestUtil.setValueintojson(GetReq1, "$.cases[0].caseNumber",casenum);
		GetReq1 = TestUtil.setValueintojson(GetReq1, "$.cases[0].units[0].partNumber", PartNumber);
		GetReq1 = TestUtil.setValueintojson(GetReq1, "$.cases[0].units[0].partQuantity", "3");
		System.out.println(GetReq1);
		HttpPost HTTPPOstreqGet1 = tu.prepareHTTPPostwithJsonString("[" + GetReq1 + "]", "casebuildAPI?status=true");
		System.out.println("Test uses file..." + GetReq);
		JSONObject respGet1 = rs.sendPostwithHeaders(HTTPPOstreqGet1);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------" + respGet1.toString());
		test.log(LogStatus.INFO, "received response-----" + respGet.toString());
		JSONObject JOGet1 = new JSONObject(respGet1.toString());
		System.out.println(JOGet.getString("confirmationNumber"));
		System.out.println();
		if (!JOGet.getString("confirmationNumber").equals("null")) {
			System.out.println("test passed");
		} else {
			Assert.fail();
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

	private void generateRandomDataForTableRecords() throws ClassNotFoundException, SQLException {
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
		String sheetName = "CaseBuild";
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
