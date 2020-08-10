package com.toyota.scs.serviceparts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;

import com.toyota.scs.serviceparts.ResponseCodes;
import com.toyota.scs.serviceparts.Headers.HeaderType;

public class RestService {
	private int timeout = 120;
	private List<BasicHeader> customHeaders = null;
	private boolean acceptAllSSL = false;

	// set up HTTPGet without headers
	public JSONObject sendGetRequest(String url) throws ParseException, IOException, JSONException {
		// sendGetRequest(url, type, null);
		HttpGet getrequest = new HttpGet(url);
		JSONObject response = getRequest(getrequest);
		return response;
	}

	// set up HTTPGet with headers
	public JSONObject sendGetRequestWithHeaders(String url, HashMap<String, String> header)
			throws ParseException, IOException, JSONException {

		HttpGet getrequest = new HttpGet(url);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			getrequest.addHeader(entry.getKey(), entry.getValue());
		}
		JSONObject response = getRequest(getrequest);
		return response;
	}

	public JSONObject sendPostwithHeaders(HttpPost postrequest) throws ClientProtocolException, IOException, JSONException {

		JSONObject response = postRequest(postrequest);
		return response;
	}



	
	// Post Request with Headers
	public JSONObject postRequest(HttpUriRequest postrequest) throws ClientProtocolException, IOException, JSONException {
		RestResponse response = null;
		JSONObject respjsonObj;
		String respJson;
		CloseableHttpClient HttpClient = HttpClients.createDefault();
		System.out.println(postrequest.getFirstHeader("Authorization"));

		CloseableHttpResponse httpResponse = HttpClient.execute(postrequest);
		System.out.println("Statucode for post request.." + httpResponse.getStatusLine().getStatusCode());
		System.out.println(httpResponse);
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		if ((responseString.charAt(0) == '[')) {
			respJson = responseString.substring(1, (responseString.length() - 1));
		} else {
			respJson = responseString;
		}
		respjsonObj = new JSONObject(respJson);
		return respjsonObj;
	}

	// Post Request with Headers
	public String postRequestreturnsJstring(HttpUriRequest postrequest) throws ClientProtocolException, IOException {
		RestResponse response = null;
		String respJson;
		CloseableHttpClient HttpClient = HttpClients.createDefault();
		System.out.println(postrequest.getFirstHeader("Authorization"));

		CloseableHttpResponse httpResponse = HttpClient.execute(postrequest);
		System.out.println("Statucode for post request.." + httpResponse.getStatusLine().getStatusCode());
		System.out.println(httpResponse);
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		return responseString;
	}

	// Get Request without headers
	private JSONObject getRequest(HttpUriRequest request) throws ParseException, IOException, JSONException {

		// RestResponse response = null;
		CloseableHttpClient HttpClient = HttpClients.createDefault();

		CloseableHttpResponse httpResponse = HttpClient.execute(request);

		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		// JSONObject respjsonObj = new JSONObject(responseString);
		JSONObject respjsonObj = new JSONObject(responseString.substring(1, (responseString.length() - 1)));

		System.out.println("Status for get request code is------------" + httpResponse.getStatusLine().getStatusCode());
		System.out.println("---------------------");
		System.out.println("ResponseBody---------------------" + respjsonObj);

		return respjsonObj;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return this.timeout;
	}

	public void setAcceptAllSSL(boolean acceptAllSSL) {
		this.acceptAllSSL = acceptAllSSL;
	}

	public boolean getAcceptAllSSL() {
		return this.acceptAllSSL;
	}

	public void addCustomHeaders(String header, String value) {
		if (customHeaders == null) {
			customHeaders = new ArrayList<>();
		} else if ((header).isEmpty() || (value).isEmpty()) {
			throw new RestException(
					"Header name and value cannot be null: Header: [ " + header + " ] Value: [ " + value + " ]");
		}

		customHeaders.add(new BasicHeader(header, value));
	}

	/**
	 * Sends a GET request to a URL
	 *
	 * @param URL
	 *            for the service you are testing
	 * @return response in string format
	 * @throws IOException
	 * @throws ParseException
	 */

	/**
	 * Sends a GET request
	 *
	 * @param url
	 *            for the service you are testing
	 * @return response in string format
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public RestResponse sendGetRequest(String url, HeaderType type, List<NameValuePair> params)
			throws ParseException, IOException {

		CloseableHttpClient sslHttpClient = null;
		HttpGet request = new HttpGet(url);

		if (params != null) {
			request = new HttpGet(createQueryParamUrl(url, params));
		}

		request.setHeaders(createHeaders(type));

		RestResponse response = sendRequest(request);

		System.out.println(response);

		return response;
	}

	public RestResponse sendPostRequest(String url, HeaderType type, List<NameValuePair> params, String json)
			throws ParseException, IOException {

		HttpPost httppost = new HttpPost(url);

		if (params != null) {
			httppost = new HttpPost(createQueryParamUrl(url, params));
		}

		if (json != null) {

			httppost.setEntity(createJsonEntity(json));
		}

		httppost.setHeaders(createHeaders(type));

		RestResponse response = sendRequest(httppost);

		return response;
	}

	public RestResponse sendPostRequestWithSoapRequest(String url, String requestAsString)
			throws ParseException, IOException {
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new InputStreamEntity(new StringBufferInputStream(requestAsString)));
		httppost.setHeader("Content-type", "text/xml; charset=UTF-8");
		httppost.setHeader("SOAPAction", "");
		RestResponse response = sendRequest(httppost);
		return response;
	}

	public RestResponse sendPutRequest(String url, HeaderType type, List<NameValuePair> params, String json)
			throws ParseException, IOException {

		HttpPut httpPut = new HttpPut(url);

		if (params != null) {
			httpPut = new HttpPut(createQueryParamUrl(url, params));
		}

		if (json != null) {

			httpPut.setEntity(createJsonEntity(json));
		}

		httpPut.setHeaders(createHeaders(type));

		RestResponse response = sendRequest(httpPut);

		return response;
	}

	public RestResponse sendPutRequest(String url, HeaderType type, String json) throws ParseException, IOException {
		return sendPutRequest(url, type, null, json);
	}

	public RestResponse sendDeleteRequest(String url, HeaderType type, List<NameValuePair> params)
			throws ParseException, IOException {

		HttpDelete httpDelete = new HttpDelete(url);

		if (params != null) {
			httpDelete = new HttpDelete(createQueryParamUrl(url, params));
		}

		httpDelete.setHeaders(createHeaders(type));

		RestResponse response = sendRequest(httpDelete);

		return response;
	}

	private Header[] createHeaders(HeaderType type) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sends a delete request. Depends on the service if a response is returned.
	 * If no response is returned, will return null *
	 *
	 * @param url
	 *            for the service
	 * @return response in string format or null
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public RestResponse sendDeleteRequest(String url, HeaderType type) throws ParseException, IOException {
		return sendDeleteRequest(url, type, null);
	}

	public RestResponse sendDeleteRequest(String url) throws ParseException, IOException {
		return sendDeleteRequest(url, null, null);
	}

	public Header[] sendOptionsRequest(String url) throws ParseException, IOException {
		HttpOptions httpOptions = new HttpOptions(url);
		return sendRequest(httpOptions).getHeaders();
	}

	public static String getJsonFromObject(Object request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
		} catch (JsonProcessingException e) {
			throw new RestException("Failed to convert object to json", e);
		}
	}

	private RestResponse sendRequest(HttpUriRequest request) throws ParseException, IOException {

		RestResponse response = null;
		CloseableHttpClient HttpClient = HttpClients.createDefault();

		CloseableHttpResponse httpResponse = HttpClient.execute(request);

		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

		System.out.println("Status code is------------" + httpResponse.getStatusLine().getStatusCode());
		System.out.println("---------------------");
		System.out.println("---------------------");
		System.out.println("---------------------");
		System.out.println("ResponseBody---------------------" + responseString);

		// response=getresponse;

		return response;
	}

	private String createQueryParamUrl(String url, List<NameValuePair> params) {
		String allParams = "";
		for (NameValuePair param : params) {
			allParams += "[" + param.getName() + ": " + param.getValue() + "] ";
		}

		url = url + "?" + URLEncodedUtils.format(params, "utf-8");

		return url;
	}

	private ByteArrayEntity createJsonEntity(String json) {
		ByteArrayEntity entity = null;
		try {
			entity = new ByteArrayEntity(json.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// This literally cannot be reached, but has to be checked anyway
			throw new RestException(e.getMessage(), e);
		}

		return entity;
	}

}
