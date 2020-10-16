package com.toyota.scs.serviceparts.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codoid.products.exception.FilloException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.relevantcodes.extentreports.LogStatus;

import com.toyota.scs.serviceparts.*;

import net.minidev.json.parser.JSONParser;

public class TestUtil extends BaseTest {
	private static final Configuration configuration = Configuration.builder()
			.jsonProvider(new JacksonJsonNodeJsonProvider()).mappingProvider(new JacksonMappingProvider()).build();
	private static final String JSONObject = null;
	public String reqjson;
	String ResourceURL = null;

	public static String getValuebyJpath(JSONObject json, String jpath) throws NumberFormatException, JSONException {
		Object obj = json;

		if (!(jpath == null))

		{
			for (String s : jpath.split("/")) {
				if (!s.isEmpty())
					if (!(s.contains("[") || (s.contains("]")))) {
						obj = ((JSONObject) obj).get(s);
					} else if (s.contains("[") || (s.contains("]"))) {
						obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
								.get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
					}

			}
		} else {
			test.log(LogStatus.INFO, "Jpath is empty to get the key value form payload");
			test.log(LogStatus.FAIL, "supplier code doesn't match with the request");
		}
		return obj.toString();
	}

	public static String removeKeyFromjson(String jreq, String path)
			throws JsonIOException, JsonSyntaxException, IOException {
		String reqJson;
		ObjectNode updatedJson = null;
		if (path != null) {
			if ((jreq.charAt(0) == '[')) {
				reqJson = jreq.substring(1, (jreq.length() - 1));
			} else {
				reqJson = jreq;
			}

			// test.log(LogStatus.INFO, "removing the value at specified path" +
			// path);

			updatedJson = JsonPath.using(configuration).parse(reqJson).delete(path).json();

		} else {
			test.log(LogStatus.INFO, "Jpath is empty to remove the key value form payload");
			test.log(LogStatus.FAIL, "supplier code doesn't match with the request");
		}
		return updatedJson.toString();
	}

	public static String setValueintojson(String jreq, String path, String val)
			throws JsonIOException, JsonSyntaxException, IOException {
		String reqJson;
		ObjectNode updatedJson = null;
		if (path != null) {
			if ((jreq.charAt(0) == '[')) {
				reqJson = jreq.substring(1, (jreq.length() - 1));
			} else {
				reqJson = jreq;
			}
			test.log(LogStatus.INFO, "updating the value at specified path " + path);
			updatedJson = JsonPath.using(configuration).parse(reqJson).set(path, val).json();
		} else {
			test.log(LogStatus.INFO, "Jpath is empty to set the key value in payload " + path);
			test.log(LogStatus.FAIL, "supplier code doesn't match with the request");
		}
		return updatedJson.toString();
	}

	public static String setJSONObjectintojsonBody(String jreq, String path, String val)
			throws JsonIOException, JsonSyntaxException, IOException, net.minidev.json.parser.ParseException {
		String reqJson;
		JSONParser parser = new JSONParser();

		Object JsonValuetoset = parser.parse(val);
		System.out.println(JsonValuetoset);
		ObjectNode updatedJson = null;
		if (path != null) {
			if ((jreq.charAt(0) == '[')) {
				reqJson = jreq.substring(1, (jreq.length() - 1));
			} else {
				reqJson = jreq;
			}
			test.log(LogStatus.INFO, "updating the value at specified path " + path);
			updatedJson = JsonPath.using(configuration).parse(reqJson).set(path, JsonValuetoset).json();
		} else {
			test.log(LogStatus.INFO, "Jpath is empty to set the key value in payload " + path);
			test.log(LogStatus.FAIL, "supplier code doesn't match with the request");
		}
		return updatedJson.toString();
	}

	public static String getValuefromJson(String jreq, String path)

			throws JsonIOException, JsonSyntaxException, IOException {
		String reqJson;
		ObjectNode updatedJson = null;
		if (path != null) {
			if ((jreq.charAt(0) == '[')) {
				reqJson = jreq.substring(1, (jreq.length() - 1));
			} else {
				reqJson = jreq;
			}
			test.log(LogStatus.INFO, "getting the value from specified path");
			updatedJson = JsonPath.using(configuration).parse(reqJson).read(path);
		} else
			test.log(LogStatus.INFO, "Jpath is empty to set the key value in payload");

		return updatedJson.toString();
	}

	public String setupURL() {
		if ((TestNGListener.className).equalsIgnoreCase("ServicePartsVendorTests")) {
			ResourceURL = TestEnvironment.APP_SERVICE_URL + "vendors";
		}
		if ((TestNGListener.className).equalsIgnoreCase("ServicePartsCaseBuild")) {
			ResourceURL = TestEnvironment.APP_SERVICE_URL + "casebuild?status=true";
		}
		return ResourceURL;
	}

	public String setupURL(String endpoint) {

		ResourceURL = TestEnvironment.APP_SERVICE_URL + endpoint;

		System.out.println(ResourceURL);

		return ResourceURL;
	}

	public HttpPost prepareHTTPPostwithJsonString(String Json, String endpoint)
			throws JsonIOException, IOException, FilloException, InterruptedException {
		String url = setupURL(endpoint);
		HttpPost postrequest = new HttpPost(url);
		HashMap<String, String> header = new HashMap<String, String>();
		if (TestEnvironment.ENVIRONMENT.equals("QAINT")) {
			header = Headers.AddHeadersinQAInternal();
		} else {
			header = Headers.AddHeaders();
		}
		reqjson = Json;
		postrequest.setEntity(new StringEntity(reqjson));// setting Payload-
		for (Map.Entry<String, String> entry : header.entrySet())// Setting
																	// Headers
		{
			postrequest.addHeader(entry.getKey(), entry.getValue());

		}
		return postrequest;
	}
	public HttpGet prepareHTTGetwithJsonString(String Json, String endpoint)
			throws JsonIOException, IOException, FilloException, InterruptedException {
		String url = setupURL(endpoint);
		HttpGet getrequest = new HttpGet(url);
		HashMap<String, String> header = new HashMap<String, String>();
		if (TestEnvironment.ENVIRONMENT.equals("QAINT")) {
			header = Headers.AddHeadersinQAInternal();
		} else {
			header = Headers.AddHeaders();
		}
		reqjson = Json;
		((HttpResponse) getrequest).setEntity(new StringEntity(reqjson));// setting Payload-
		for (Map.Entry<String, String> entry : header.entrySet())// Setting
																	// Headers
		{
			getrequest.addHeader(entry.getKey(), entry.getValue());

		}
		return getrequest;
	}

	public HttpPost prepareHTTPPostRequestforISDEToken(String Json)
			throws JsonIOException, IOException, FilloException, InterruptedException {
		String url = "https://lck0aelg2m.execute-api.us-east-1.amazonaws.com/test/api/JWT";
		HttpPost postrequest = new HttpPost(url);

		reqjson = Json;
		postrequest.setEntity(new StringEntity(reqjson));// setting Payload-

		return postrequest;
	}

	// set up HTTPGet with headers
	public JSONObject sendGetRequestWithHeaders(HashMap<String, String> header)
			throws ParseException, IOException, JSONException {
		String url = setupURL();
		HttpGet getrequest = new HttpGet(url);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			getrequest.addHeader(entry.getKey(), entry.getValue());
		}
		JSONObject response = getRequest(getrequest);
		return response;
	}

	// Get Request without headers
	private JSONObject getRequest(HttpUriRequest request) throws ParseException, IOException, JSONException {

		// RestResponse response = null;
		CloseableHttpClient HttpClient = HttpClients.createDefault();

		CloseableHttpResponse httpResponse = HttpClient.execute(request);

		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject respjsonObj = new JSONObject(responseString);
		// JSONObject respjsonObj = new JSONObject(responseString.substring(1,
		// (responseString.length() - 1)));

		System.out.println("Status for get request code is------------" + httpResponse.getStatusLine().getStatusCode());
		System.out.println("---------------------");
		System.out.println("ResponseBody---------------------" + respjsonObj);

		return respjsonObj;
	}

	// set up HTTPGet with Parameters
	public JSONObject sendGetwithPathParams(HashMap<String, String> header) throws ParseException, IOException,
			URISyntaxException, FilloException, InterruptedException, JSONException {

		String url = setupURL();
		// url = url + "?route=KYSL&run=13&reload=2020-03-06T12:00";

		URIBuilder uriBuilder = new URIBuilder(url);

		for (Map.Entry<String, String> entry : header.entrySet()) {
			uriBuilder.addParameter(entry.getKey(), entry.getValue());

		}

		URI uri = uriBuilder.build();
		// String uri = uriBuilder.build().toString();
		HttpGet getrequest = new HttpGet(uri);
		getrequest.addHeader("appID", "vnandiko");

		System.out.println("GET URL---------------------" + uri);
		CloseableHttpClient HttpClient = HttpClients.createDefault();

		CloseableHttpResponse httpResponse = HttpClient.execute(getrequest);

		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		JSONObject respjsonObj = new JSONObject(responseString);
		// JSONObject respjsonObj = new JSONObject(responseString.substring(1,
		// (responseString.length() - 1)));

		System.out.println("Status for get request code is------------" + httpResponse.getStatusLine().getStatusCode());

		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			test.log(LogStatus.PASS, "status code is 200 as expected");
		} else {
			test.log(LogStatus.FAIL, "status code is not 200 as expected");
		}
		System.out.println("---------------------");
		System.out.println("ResponseBody---------------------" + respjsonObj);

		return respjsonObj;

	}

	public HttpPost prepareHTTPPost(String Reqfilepath) throws JsonIOException, IOException, FilloException,
			InterruptedException, JsonSyntaxException, JSONException {
		TestEnvironment.initializeEnvironent();
		String url = setupURL();
		HttpPost postrequest = new HttpPost(url);
		HashMap<String, String> header = new HashMap<String, String>();
		if (TestEnvironment.ENVIRONMENT.equals("QAINT")) {
			header = Headers.AddHeadersinQAInternal();
		} else {
			header = Headers.AddHeaders();
		}

		reqjson = FileLoader.UpdateJsonFile(reqjson);
		postrequest.setEntity(new StringEntity(reqjson));// setting Payload-
		for (Map.Entry<String, String> entry : header.entrySet())// Setting Headers
		{
			postrequest.addHeader(entry.getKey(), entry.getValue());
		}
		return postrequest;
	}

	public HttpPost prepareForFailureHTTPPost(String Reqfilepath) throws JsonIOException, IOException, FilloException,
			InterruptedException, JsonSyntaxException, JSONException {
		TestEnvironment.initializeEnvironent();
		String url = setupURL();
		HttpPost postrequest = new HttpPost(url);
		HashMap<String, String> header = new HashMap<String, String>();
		if (TestEnvironment.ENVIRONMENT.equals("QAINT")) {

			header = Headers.AddHeadersinQAInternal();
		} else {
			header = Headers.AddHeaders();
		}
		reqjson = FileLoader.readJsonFilefromProject(Reqfilepath);
		postrequest.setEntity(new StringEntity(reqjson));// setting Payload-
		for (Map.Entry<String, String> entry : header.entrySet())// Setting
																	// Headers
		{
			postrequest.addHeader(entry.getKey(), entry.getValue());
		}
		return postrequest;
	}

	private static ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
	}

	public static <T> T convertJsonToJava(String jsonString, Class<T> cls) throws JsonMappingException, IOException {

		T result = null;
		try {
			result = mapper.readValue(jsonString, cls);
		} catch (JsonParseException e) {
			System.out.println("Exception occured while converting JSON into Java Objects" + e.getMessage());

		}
		return result;
	}

	public static int genrateRandomNumber(int length) {

		int min = 0;
		int max = 0;
		if (length == 2) {
			min = 10;
			max = 99;
		}
		if (length == 2) {
			min = 10;
			max = 99;
		}

		if (length == 3) {
			min = 100;
			max = 999;
		}

		if (length == 4) {
			min = 1000;
			max = 99999;
		}
		if (length == 5) {
			min = 10000;
			max = 999999;
		}
		if (length == 8) {
			min = 10000000;
			max = 99999999;
		}
		int b = (int) (Math.random() * (max - min + 1) + min);
		return b;
	}

	public static String genrateRandomString(int targetStringLength) {
		int leftLimit = 48; // numeral '0'

		int rightLimit = 122; // letter 'z'

		// int targetStringLength = 5;

		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)

				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))

				.limit(targetStringLength)

				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)

				.toString();

		return generatedString;

	}

	public static String formatDateForVendors() {
		String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

		String dateString = simpleDateFormat.format(new Date());
		System.out.println(dateString);

		return dateString;
	}

}