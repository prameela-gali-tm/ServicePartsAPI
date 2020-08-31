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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

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

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void VerifyCaseBuildSuccessCode(Integer row) throws Exception {
		test = rep.startTest("VerifyCaseBuildSuccessCode-verify confirmation number is generated");
		currentRow.set(row);
		InsertCaseBuildRcordsIntoTables();
		System.out.println(Vendor);
		GetDataWithRowNum(row);
		test.log(LogStatus.INFO, " VerifyConfirmationnumberGenerated test started");
		String request = FileLoader.readJsonFilefromProject(Inputfile);
		String req1 = TestUtil.setValueintojson(request, "$.cases[0].units[0].partNumber", PartNumber);
		String req2 = TestUtil.setValueintojson(req1, "$.cases[0].units[0].poNumber", purchaseOrder);
		String req3 = TestUtil.setValueintojson(req2, "$.cases[0].units[0].poLineNumber", LineitemNum);
		String req4 = TestUtil.setValueintojson(req3, "$.vendorCode", Vendor);
		System.out.println(req4);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString("[" + req4 + "]");
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Test uses file..." + req4);
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("response..." + resp);
		JSONObject JO = new JSONObject(resp.toString());
		System.out.println(JO.getString("confirmationNumber"));

	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void VerifyRequiredFieldsatUnitLevel(Integer row) throws Exception {
		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
		String request = FileLoader.readJsonFilefromProject(Inputfile);

		String req1 = TestUtil.setValueintojson(request, data.get("partNumberPath"), data.get("partNumber"));
		String req2 = TestUtil.setValueintojson(req1, data.get("poNumberPath"), data.get("poNumber"));
		String req3 = TestUtil.setValueintojson(req2, data.get("poLineNumberPath"), data.get("poLineNumber"));
		String req4 = TestUtil.setValueintojson(req3, data.get("partQuantityPath"), data.get("partQuantity"));

		System.out.println(req4);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString("[" + req4 + "]");
		System.out.println("Test uses file..." + req4);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());
		String errorMessage = TestUtil.getValuebyJpath(resp, data.get("getRespvaluePath"));
		System.out.println(errorMessage);
//"Part Number is required field.","PO Number is required field.","PO Line Number is required field.

		if ((errorMessage.contains("Part Number is required field"))
				&& (errorMessage.contains("PO Number is required field"))
				&& (errorMessage.contains("PO Line Number is required field"))) {
			test.log(LogStatus.PASS, "response displays expected error message on Required fields " + errorMessage);
		} else {
			test.log(LogStatus.FAIL, "response doesn't show expected error message");
			Assert.fail("response doesn't show expected error message");
		}
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void VerifyCaseBuildPartQuantityError(Integer row) throws Exception {
		test = rep.startTest("VerifyCaseBuildPartQuantityError - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);
		test.log(LogStatus.INFO, " VerifyConfirmationnumberGenerated test started");
		String request = FileLoader.readJsonFilefromProject(Inputfile);

		request = TestUtil.setValueintojson(request, data.get("partNumberPath"), data.get("partNumber"));
		request = TestUtil.setValueintojson(request, data.get("poNumberPath"), data.get("poNumber"));
		request = TestUtil.setValueintojson(request, data.get("poLineNumberPath"), data.get("poLineNumber"));
		request = TestUtil.setValueintojson(request, data.get("partQuantityPath"), data.get("partQuantity"));

		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString("[" + request + "]");
		System.out.println("Test uses file..." + request);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());
		String errorMessage = TestUtil.getValuebyJpath(resp, data.get("getRespvaluePath"));
		System.out.println(errorMessage);

		if ((errorMessage.contains("Part Quantity is required field."))) {
			test.log(LogStatus.PASS, "Error Message displayed on part quantity" + errorMessage);
		} else {
			test.log(LogStatus.FAIL, "response doesn't show expected error message");
			Assert.fail("response doesn't show expected error message " + errorMessage);
		}
	}

	public static String UpdateJsonFile(String jsontxt)
			throws JsonIOException, JsonSyntaxException, IOException, JSONException {
		JSONArray ja = new JSONArray(jsontxt);
		JSONObject JO = null;
		for (int i = 0; i < ja.length(); i++) {
			JO = ja.getJSONObject(i);
			JO.put("vendorCode", Vendor);

//        "deliveryDueDate":"2020-04-01", 
			// "serialNumber":"231231F31", rand
			JO.put("partNumber", PartNumber);
			JO.put("poNumber", purchaseOrder);

//		JO.put("serialNumber", TestUtil.formatDateForVendors());
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
		DBUtil.insertRecordIntoVendors(VendorId, Vendor, vendorDescription);
		// Insert into part table
		DBUtil.insertRecordIntoPart(partId, OrderID, PartDesc, PartNumber, vendorPartNum, LineitemNum);
		// Insert into part table
		DBUtil.insertRecordIntoOrder(OrderID, OrderType, purchaseOrder, Vendor);

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

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void VerifyMoreDigits(Integer row) throws Exception {
		test = rep.startTest("VerifyMoreDigits - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
		String request = FileLoader.readJsonFilefromProject(Inputfile);

		String req1 = TestUtil.setValueintojson(request, data.get("partNumberPath"), data.get("partNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("poNumberPath"), data.get("poNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("poLineNumberPath"), data.get("poLineNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("partQuantityPath"), data.get("partQuantity"));

		req1 = TestUtil.setValueintojson(req1, data.get("homePositionPath"), data.get("homePosition"));
		// req1 = TestUtil.setValueintojson(req1, data.get("deliveryDueDatePath"),
		// data.get("deliveryDueDate"));
		req1 = TestUtil.setValueintojson(req1, data.get("serialNumberPath"), data.get("serialNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("subPartNumberPath"), data.get("subPartNumber"));

		System.out.println(req1);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString("[" + req1 + "]");
		System.out.println("Test uses file..." + req1);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());
		String errorMessage = TestUtil.getValuebyJpath(resp, data.get("getRespvaluePath"));
		System.out.println(errorMessage);
		System.out.println(errorMessage.contains("Part Number must be 20 digits or less"));
		System.out.println(errorMessage.contains("PO Number must be 8 digits"));
		System.out.println(errorMessage.contains("PO Line Number must be 5 digits or less"));
		System.out.println(errorMessage.contains("Home Position must be 1 digit"));
		System.out.println(errorMessage.contains("Serial Number must be 20 digits or less"));
		System.out.println(errorMessage.contains("Substitution Part Number must be 12 digits or less"));
		if ((errorMessage.contains("Part Number must be 20 digits or less"))
				&& (errorMessage.contains("PO Number must be 8 digits"))
				&& (errorMessage.contains("PO Line Number must be 5 digits or less"))
				&& (errorMessage.contains("Home Position must be 1 digit"))
				&& (errorMessage.contains("Serial Number must be 20 digits or less"))
				&& (errorMessage.contains("Substitution Part Number must be 12 digits or less"))) {
			test.log(LogStatus.PASS, "response displays expected error message on Required fields " + errorMessage);
		} else {
			test.log(LogStatus.FAIL, "response doesn't show expected error message");
			Assert.fail("response doesn't show expected error message");
		}
	}

	@Test(priority = 1, dataProvider = "CaseBuildDataProvider")
	public void VerifySpecialCharacters(Integer row) throws Exception {
		test = rep.startTest("VerifyRequiredFieldsatUnitLevel - verify po number is generated");
		currentRow.set(row);
		GetDataWithRowNum(row);
		test.log(LogStatus.INFO, " VerifyRequiredFieldsatUnitLevel test started");
		String request = FileLoader.readJsonFilefromProject(Inputfile);

		String req1 = TestUtil.setValueintojson(request, data.get("partNumberPath"), data.get("partNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("poNumberPath"), data.get("poNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("poLineNumberPath"), data.get("poLineNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("partQuantityPath"), data.get("partQuantity"));

		req1 = TestUtil.setValueintojson(req1, data.get("homePositionPath"), data.get("homePosition"));
		req1 = TestUtil.setValueintojson(req1, data.get("deliveryDueDatePath"), data.get("deliveryDueDate"));
		req1 = TestUtil.setValueintojson(req1, data.get("serialNumberPath"), data.get("serialNumber"));
		req1 = TestUtil.setValueintojson(req1, data.get("subPartNumberPath"), data.get("subPartNumber"));

		System.out.println(req1);
		HttpPost HTTPPOstreq = tu.prepareHTTPPostwithJsonString("[" + req1 + "]");
		System.out.println("Test uses file..." + req1);
		JSONObject resp = rs.sendPostwithHeaders(HTTPPOstreq);
		test.log(LogStatus.INFO, "sent post request with payload-----" + Inputfile);
		System.out.println("Reponse is----------");
		test.log(LogStatus.INFO, "received response-----" + resp.toString());
		JSONObject JO = new JSONObject(resp.toString());
		String errorMessage = TestUtil.getValuebyJpath(resp, data.get("getRespvaluePath"));
		System.out.println(errorMessage);
//"Part Number is required field.","PO Number is required field.","PO Line Number is required field.

//		if ((errorMessage.contains("Part Number is required field"))
//				&& (errorMessage.contains("PO Number is required field"))
//				&& (errorMessage.contains("PO Line Number is required field"))) {
//			test.log(LogStatus.PASS, "response displays expected error message on Required fields " + errorMessage);
//		} else {
//			test.log(LogStatus.FAIL, "response doesn't show expected error message");
//			Assert.fail();
//		}
	}

}
