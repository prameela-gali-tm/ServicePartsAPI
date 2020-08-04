package com.toyota.scs.serviceparts.util;

import java.io.File;
import java.util.ArrayList;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.LogStatus;

import com.toyota.scs.serviceparts.BaseTest;

public class ExcelUtil extends BaseTest {

//	private static String TestEnvironment.Filepath = TestEnvironment.Filepath;

	public static String getColumnVal(String TableName, String testName, String Columntoread) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(TestEnvironment.Filepath);

		Recordset recordset = connection
				.executeQuery("select * from " + TableName + " where TestcaseName=" + "'" + testName + "'");
		test.log(LogStatus.INFO, "reading Authencation header from excel");

		while (recordset.next()) {
			System.out.println(recordset.getField(Columntoread));
			test.log(LogStatus.INFO, "Authencation header from excel" + recordset.getField(Columntoread));
		}

		connection.close();

		return recordset.getField(Columntoread);
	}

	public static ArrayList readRowData(String TableName, String testcaseName) throws FilloException {
		ArrayList ar = new ArrayList();
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(TestEnvironment.Filepath);
		Recordset recordset = connection
				.executeQuery("select * from " + TableName + " where TestcaseName=" + "'" + testcaseName + "'");
		// test.log(LogStatus.INFO, "reading Authencation header from excel");
		if (recordset.getCount() > 1) {

			System.out.println("More than one test found");
			test.log(LogStatus.INFO, "Duplicatetests");
		} else {
			while (recordset.next()) {

				for (int i = 0; i <= recordset.getFieldNames().size() - 1; i++) {
					// System.out.println(recordset.getField(i).value());
					ar.add(i, recordset.getField(i).value());

				}
			}
		}

		// test.log(LogStatus.INFO, "retrieved testdata from excel");
		connection.close();
		// System.out.println("Column count........"+ar.size());
		return ar;
	}
	
	
	
	public static ArrayList readRowDataWithTCID(String TableName, Integer TCID) throws FilloException {
		ArrayList ar = new ArrayList();
		Fillo fillo = new Fillo();
		System.out.println(TestEnvironment.Filepath);
		System.out.println(TestEnvironment.Filepath);
		Connection connection = fillo.getConnection(TestEnvironment.Filepath);
		Recordset recordset = connection
				.executeQuery("select * from " + TableName + " where TC_ID =" + "'" + TCID + "'");
		// test.log(LogStatus.INFO, "reading Authencation header from excel");
		if (recordset.getCount() > 1) {

			System.out.println("More than one test found");
			test.log(LogStatus.INFO, "Duplicatetests");
		} else {
			while (recordset.next()) {

				for (int i = 0; i <= recordset.getFieldNames().size() - 1; i++) {
					// System.out.println(recordset.getField(i).value());
					ar.add(i, recordset.getField(i).value());

				}
			}
		}

		// test.log(LogStatus.INFO, "retrieved testdata from excel");
		connection.close();
		// System.out.println("Column count........"+ar.size());
		return ar;
	}

	public static Recordset readSheetData(String sheetName, String Testname, String runFlag) throws FilloException {
		Fillo fillo = new Fillo();
		System.out.println(TestEnvironment.Filepath);
		Connection connection = fillo.getConnection(TestEnvironment.Filepath);
		
		Recordset recordset = connection.executeQuery("select * from " + sheetName + " where TestcaseName=" + "'" + Testname + "'"+" and "+"RunOption=" + "'" + runFlag + "'");

		if (recordset.getCount() == 0) {

			System.out.println("No test cases found");
			test.log(LogStatus.INFO, "No test cases found");
		} else {

			
		}
		return recordset;
	}

}
