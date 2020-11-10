package com.toyota.scs.serviceparts;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.codoid.products.exception.FilloException;
import com.relevantcodes.extentreports.ExtentTest;

import com.toyota.scs.serviceparts.Headers.HeaderType;
import com.toyota.scs.serviceparts.util.*;

public class Headers {

	public static String tname;
	public static String AuthorizationfromExcel;
	
	
	public static HashMap<String, String> AddHeaders() throws FilloException, InterruptedException {
		/**
		 * Authentication is basic, headers will be added based on the supplier
		 * read from excel sheet
		 * 
		 * @param type
		 *            service header type
		 * 
		 */

	//	String user = ExcelUtil.getColumnVal("SupplierInfo", tname, "Authorization");
		HashMap<String, String> headersmap = new HashMap<String, String>();
		headersmap.put("Content-Type", "application/json");
		headersmap.put("Authorization", AuthorizationfromExcel);
		return headersmap;

	}
	

	public static HashMap<String, String> AddHeadersinQAInternal() throws FilloException, InterruptedException {
		/**
		 * Authentication is basic, headers will be added based on the supplier
		 * read from excel sheet
		 * 
		 * @param type
		 *            service header type
		 * 
		 */

	//	String user = ExcelUtil.getColumnVal("SupplierInfo", tname, "Authorization");
		HashMap<String, String> headersmap = new HashMap<String, String>();
		headersmap.put("Content-Type", "application/json");
		headersmap.put("AppId", "vnandiko");
		return headersmap;

	}
	
	
	public static HashMap<String, String> AddHeaderAPPID() throws FilloException, InterruptedException {
	

	//	String user = ExcelUtil.getColumnVal("SupplierInfo", tname, "Authorization");
		HashMap<String, String> headersmap = new HashMap<String, String>();
		headersmap.put("AppID", "vnandiko");
		return headersmap;

	}
	public static HashMap<String, String> AddHeadersinQAInternalAdmin(String Sheetname) throws FilloException, InterruptedException {
		/**
		 * Authentication is basic, headers will be added based on the supplier
		 * read from excel sheet
		 * 
		 * @param type
		 *            service header type
		 * 
		 */

		//String user = ExcelUtil.getColumnVal(Sheetname, tname, "Authorization");
		
		HashMap<String, String> headersmap = new HashMap<String, String>();
		headersmap.put("Content-Type", "application/json");
		headersmap.put("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJVU0VSIjp7InVzZXJJZCI6InJrcmlzaG4xIiwibG9naXN0aWNzUG9pbnQiOltdLCJyb2xlcyI6eyJOQU1DIFBsYW5uZXIiOiJOQU1DIFBsYW5uZXIifSwic3VwcGxpZXJDb2RlcyI6e30sImFkbWluIjpmYWxzZSwicmFja0ZsYWciOmZhbHNlLCJmdWxsTmFtZSI6IlJhbWVzaCBLcmlzaG5hc2FteSIsImZpcnN0TmFtZSI6IlJhbWVzaCIsImxhc3ROYW1lIjoiS3Jpc2huYXNhbXkiLCJ1c2VyRW1haWwiOiJyYW1lc2gua0B0b3lvdGEuY29tIiwicGxhbnRDb2RlIjoiMjBUTUIiLCJzY3ZBZG1pbiI6ZmFsc2UsImNyb3NzRG9ja3MiOltdLCJhY3Rpb25zIjpbIkxQX0RBU0hCT0FSRCIsIlNVUFBMSUVSX0RBU0hCT0FSRCIsIlNFQVJDSCIsIlNISVBNRU5UX0xPQUQiLCJDT01QTElBTkNFX0RBU0hCT0FSRCIsIlNLSURfQlVJTEQiXSwibHBDb2RlcyI6W119fQ.nCj5pGP8ZVKT4YX9iVzk17GSGaBLbBHLxxVLIKFTCRE");
		return headersmap;

	}
	

	public static enum HeaderType {
		AUTH, BASIC_CONVO, JSON;
	}

	/**
	 * Automatically populates headers based on predefined options from
	 * RestService.HeaderType
	 *
	 * @param type
	 *            Uses options from RestService.HeaderType enum
	 */
	public static Header[] createHeader(HeaderType type) {
		Header[] headers = null;
		switch (type) {
		case AUTH:

			headers = new Header[] { new BasicHeader("Content-type", "application/x-www-form-urlencoded") };
			break;
		case BASIC_CONVO:

			headers = new Header[] { new BasicHeader("Content-type", "application/json;charset=utf-8"),
					new BasicHeader("Accept", "application/json"), new BasicHeader("username", "test.user"),
					new BasicHeader("Connection", "keep-alive"), };
			break;
		case JSON:

			headers = new Header[] { new BasicHeader("Content-type", "application/json")

			};
			break;
		default:
			break;
		}

		// Logging headers
		String allHeaders = "";
		for (Header header : headers) {
			allHeaders += "[" + header.getName() + ": " + header.getValue() + "] ";
		}

		return headers;
	}

}
