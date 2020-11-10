package com.toyota.scs.serviceparts.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.lang.Object;

import java.nio.*;
import java.nio.charset.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*; //input stream
import java.sql.*; //Blob
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.LogStatus;

import com.toyota.scs.serviceparts.BaseTest;

public class DBUtil extends BaseTest {

	static int rowCount;

	public static String fetchFVMPostRequestFromDB() throws ClassNotFoundException, IOException {
		String currMonth = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		String todaysDate = df.format(cal.getTime());
		System.out.println(todaysDate);

		// date from a month back
		LocalDate now = LocalDate.now();
		LocalDate thirty = now.minusDays(90);

		if ((String.valueOf(thirty.getMonthValue()).length() < 2)) {
			currMonth = "0" + String.valueOf(thirty.getMonthValue());
		}
		String backdated = currMonth + "/" + thirty.getDayOfMonth() + "/" + thirty.getYear();

		String req = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String sql = "SELECT * FROM SPB_API_LOG" + " WHERE request_date BETWEEN TO_DATE('" + backdated
					+ " 00:00','mm/dd/yyyy HH24:MI')AND TO_DATE('" + todaysDate + " 23:59','mm/dd/yyyy HH24:MI') and"
					+ " scs_type = 'skiddetails' and " + " log_type = 'SUCCESS' and request is not null and ROWNUM = 1";

			req = openConnection(sql);
		} catch (SQLException e) {
			System.out.println("SQL exception occured" + e);
		}
		return req;

	}

	public static void UpdateTransmissionDateForISDEEntry() throws ClassNotFoundException, IOException, ParseException {
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Calendar cal = Calendar.getInstance();
			String todaysDate = df.format(cal.getTime());  
		  System.out.println(todaysDate);
		 String[] dateandtimes= todaysDate.split(" ");
		 String[] times=dateandtimes[1].split(":");
		 int hour=(Integer.parseInt(times[0]))+1;
		String todaysdatetime=dateandtimes[0]+" "+hour+":"+times[1]+":"+times[2];
		 
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String sql = "update LCADM.spb_shipment_route SET transmission_date=TO_DATE('"+todaysDate +"', 'YYYY-MM-DD HH24:MI:SS') WHERE shipment_id='11270';"+"commit;";
System.out.println(sql);
			Blob blobreq = null;
			byte[] bytes = null;
			String postRequest = null;
			String connectionURL = TestEnvironment.DATABASE_CONNECTION_URL;
			java.sql.Connection con = DriverManager.getConnection(connectionURL, "lcapp", "lcStage1");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("SQL exception occured" + e);
		}

	}

	public static int getRowCount() throws ClassNotFoundException, IOException {
		String req = null;
		int count = 0;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String sql = "SELECT count(*) FROM SPB_API_LOG"
					+ " WHERE request_date BETWEEN TO_DATE('01/18/2020 00:00','mm/dd/yyyy HH24:MI')AND TO_DATE('03/18/2020 23:59','mm/dd/yyyy HH24:MI') and"
					+ "  supplier_code  is not null and  scs_type = 'skiddetails' and "
					+ " log_type = 'SUCCESS' and request is not null";

			String connectionURL = TestEnvironment.DATABASE_CONNECTION_URL;
			java.sql.Connection con = DriverManager.getConnection(connectionURL, "lcapp", "lcStage1");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Moving the cursor to the last row
				System.out.println("Table contains " + rs.getInt("count(*)") + " rows");
				rowCount = rs.getInt("count(*)");
			}
			;
		} catch (SQLException e) {
			System.out.println("SQL exception occured" + e);
		}
		return rowCount;

	}

	public static String fetchanotherFVMPostRequestFromDBbaseRow() throws ClassNotFoundException, IOException {
		String req = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String sql = "SELECT * FROM SPB_API_LOG"
					+ " WHERE request_date BETWEEN TO_DATE('02/18/2020 00:00','mm/dd/yyyy HH24:MI')AND TO_DATE('03/18/2020 23:59','mm/dd/yyyy HH24:MI') and"
					+ " scs_type = 'skiddetails' and " + " log_type = 'SUCCESS' and request is not null";

			req = openConnection(sql);

		} catch (SQLException e) {
			System.out.println("SQL exception occured" + e);
		}
		return req;

	}

	public static String openConnection(String sql) throws SQLException {
		Blob blobreq = null;
		byte[] bytes = null;
		String postRequest = null;
		String connectionURL = TestEnvironment.DATABASE_CONNECTION_URL;
		java.sql.Connection con = DriverManager.getConnection(connectionURL, "lcapp", "lcStage1");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int count = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			blobreq = rs.getBlob("REQUEST");
			InputStream in = blobreq.getBinaryStream();
			int len = (int) blobreq.length(); // read as long
			long pos = 1; // indexing starts from 1
			bytes = blobreq.getBytes(pos, len);
		}
		postRequest = decodeCharByteArray(bytes, "US-ASCII");
		System.out.println(postRequest.toString());
		con.close();
		rs.close();
		return postRequest;
	}

	public static String decodeCharByteArray(byte[] inputArray, String charSet) { // Ex
																					// charSet="US-ASCII"
		Charset theCharset = Charset.forName(charSet);
		CharsetDecoder decoder = theCharset.newDecoder();
		ByteBuffer theBytes = ByteBuffer.wrap(inputArray);
		CharBuffer inputArrayChars = null;
		try {
			inputArrayChars = decoder.decode(theBytes);
		} catch (CharacterCodingException e) {
			System.err.println("Error decoding");
		}
		return inputArrayChars.toString();
	}

	public static String fetchFVMPostRequestwithUnload() throws SQLException, ClassNotFoundException, JSONException {
		String currMonth = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		String todaysDate = df.format(cal.getTime());
		System.out.println(todaysDate);
		// date from a month back
		LocalDate now = LocalDate.now();
		LocalDate thirty = now.minusDays(90);

		if ((String.valueOf(thirty.getMonthValue()).length() < 2)) {
			currMonth = "0" + String.valueOf(thirty.getMonthValue());
		}
		String backdated = currMonth + "/" + thirty.getDayOfMonth() + "/" + thirty.getYear();
		Blob blobreq = null;
		byte[] bytes = null;
		String postRequest = null;
		String unload;
		String sql;
		String connectionURL = TestEnvironment.DATABASE_CONNECTION_URL;
		java.sql.Connection con = DriverManager.getConnection(connectionURL, "lcapp", "lcStage1");

		Class.forName("oracle.jdbc.OracleDriver");
		sql = "SELECT * FROM SPB_API_LOG" + " WHERE request_date BETWEEN TO_DATE('" + backdated
				+ " 00:00','mm/dd/yyyy HH24:MI')AND TO_DATE('" + todaysDate + " 23:59','mm/dd/yyyy HH24:MI') and"
				+ " scs_type = 'skiddetails' and " + " log_type = 'SUCCESS' and request is not null";

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int count = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			blobreq = rs.getBlob("REQUEST");
			InputStream in = blobreq.getBinaryStream();
			int len = (int) blobreq.length(); // read as long
			long pos = 1; // indexing starts from 1
			bytes = blobreq.getBytes(pos, len);
			postRequest = decodeCharByteArray(bytes, "US-ASCII");
			if (postRequest.contains("unload")) {
				unload = TestUtil.getValuebyJpath(new JSONObject(postRequest), "/unload");
				if (unload != null) {
					break;
				}
			}
		}

		System.out.println(postRequest.toString());

		return postRequest;
	}

	public static void verifyTransmissionPicked() throws SQLException, ClassNotFoundException {
		String sql;
		byte[] bytes = null;
		String postRequest = null;
		String connectionURL = TestEnvironment.DATABASE_CONNECTION_URL;
		java.sql.Connection con = DriverManager.getConnection(connectionURL, "lcapp", "lcStage1");

		Class.forName("oracle.jdbc.OracleDriver");
		sql =  " SELECT * FROM   ( select tliosjobrun.job_run_id from tliosjobrun where job_id='293' order by start_time desc"
+")WHERE ROWNUM <= 2";

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int count = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			System.out.println(rs.getString(count));
			String sqltoverifyBob=    "select * from tliosjoboutput where job_run_id='"+rs.getString(count)+"'";
			ResultSet rs2= con.createStatement().executeQuery(sqltoverifyBob);
			Blob blobreq = rs.getBlob("OUTPUT_OBJECT");
			InputStream in = blobreq.getBinaryStream();
			int len = (int) blobreq.length(); // read as long
			long pos = 1; // indexing starts from 1
			bytes = blobreq.getBytes(pos, len);
			postRequest = decodeCharByteArray(bytes, "US-ASCII");
			System.out.println(postRequest);
		}
		
	}
}