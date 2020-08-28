package com.toyota.scs.serviceparts.util;

import java.util.Hashtable;

import org.testng.SkipException;

public class DataUtil {

	public static Object[] getData(Xls_Reader xls, String supplier) {
		String sheetName = "NAMC Detail";
		// reads data for only testCaseName
		int testStartRowNum = 1;
		while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(supplier)) {
			testStartRowNum++;
			if (testStartRowNum > 48461) {
				return null;
			}

		}
		System.out.println("Test starts from row - " + testStartRowNum);

		// calculate total cols
		int cols = 0;
		while (!xls.getCellData(sheetName, cols, testStartRowNum).equals("")) {
			cols++;
		}
		int rows = 0;
		while (!xls.getCellData(sheetName, 0, rows).equals(supplier)) {
			rows++;
		}

		System.out.println("Total cols are  - " + cols);

		Hashtable<String, String> table = null;
		Object[] data = new Object[rows];

		for (int dataRow = 0; dataRow < rows ; testStartRowNum++) {

			table = new Hashtable<String, String>();

			for (int cNum = 0; cNum < cols; cNum++) {
				String key = xls.getCellData(sheetName, cNum, 1);
				String value = xls.getCellData(sheetName, cNum, testStartRowNum);
				table.put(key, value);
				// 0,0 0,1 0,2
				// 1,0 1,1

			}

			data[dataRow] = table;
			dataRow++;
		}
		return data;
	}

}
