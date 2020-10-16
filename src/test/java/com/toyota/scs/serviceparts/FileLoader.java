package com.toyota.scs.serviceparts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.toyota.scs.serviceparts.util.TestUtil;

public class FileLoader extends BaseTest {

	public FileLoader(BaseTest test) {

	}

	public static String loadFileFromProjectAsString(String filePath) throws IOException {
		// logTrace("Entering FileLoader#loadFileFromProjectAsString");

		// logTrace("Attempting to load file from path [ " + filePath + " ]");
		BufferedReader resource;
		resource = openTextFileFromProject(filePath);

		// logTrace("Attempting to read file as String");
		String text;
		try {
			text = IOUtils.toString(resource);
		} finally {
			resource.close();
		}
		// logTrace("Exiting FileLoader#loadFileFromProjectAsString");
		return text;
	}

	public static BufferedReader openTextFileFromProject(String filePath) throws IOException {
		// logTrace("Entering FileLoader#openFileFromProject");
		filePath = filePath.replace("%20", " ");
		if (!filePath.startsWith("/")) {
			filePath = "/" + filePath;
		}
		if (!isReadableFile(filePath)) {
			// throw new AutolibException("File path of [ " + filePath + " ] was invalid or
			// file was unreadable");
		}

		// logTrace("Attempting to load file from path [ " + filePath + " ]");
		FileReader fileReader = new FileReader(getAbsolutePathForResource(filePath));
		// logTrace("Successfully loaded file into FileReader");

		// logTrace("Loading FileReader object into BufferedReader");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		// logTrace("Successfully loaded FileReader object into BufferedReader");

		// logTrace("File successfully loaded");
		// logTrace("Exiting FileLoader#openFileFromProject");
		return bufferedReader;
	}

	public static boolean isReadableFile(String filePath) {
		// logTrace("Entering FileLoader#isReadableFile");

		// logTrace("Validating file from path [ " + filePath + " ] is readable");
		boolean readable = false;
		File file = new File(filePath);
		if (!file.isDirectory() && file.exists() && file.canRead()) {
			readable = true;
		} else if (null != FileLoader.class.getResource(filePath)) {
			readable = true;
		}

		// logTrace("File was readable returning [ " + readable + " ]");
		// logTrace("Exiting FileLoader#isReadableFile");
		return readable;
	}

	public static String getAbsolutePathForResource(String filePath) {
		URL url = FileLoader.class.getResource(filePath);
		if (null == url) {
			return filePath;
		}

		File file = new File(url.getPath());

		return file.getPath();
	}

	public static String readJsonFilefromProject(String path)
			throws JsonIOException, JsonSyntaxException, IOException, JSONException {
		String filepath = String.format("%s/src/test/resources/Json/%s",
				new Object[] { System.getProperty("user.dir"), path });
		System.out.println(filepath);
		File f = new File(filepath);
		JSONObject JO;
		String jsontxt = null;
		if (f.exists()) {
			InputStream is = new FileInputStream(f);
			jsontxt = IOUtils.toString(is);

		} else {
			throw new IllegalArgumentException("File not Found");
		}
		return jsontxt;

	}

	public static String UpdateJsonFile(String jsontxt)
			throws JsonIOException, JsonSyntaxException, IOException, JSONException {
		JSONObject JO = null;
		JO = new JSONObject(jsontxt);
		JO.put("vendorCode", TestUtil.genrateRandomString(5));
		JO.put("tradingPartnerId", TestUtil.genrateRandomNumber(5));
		JO.put("vendorDesc", "Test" + TestUtil.genrateRandomString(5));
		JO.put("modifiedDate", TestUtil.formatDateForVendors());
		return JO.toString();
	}

	public static String readJsonFile(String path, String Url)
			throws JsonIOException, JsonSyntaxException, IOException, JSONException {
		String filepath = String.format("%s/src/test/resources/Json/%s",
				new Object[] { System.getProperty("user.dir"), path });
		File f = new File(filepath);
		JSONObject JO = null;
		String jsontxt = null;
		if (f.exists()) {
			InputStream is = new FileInputStream(f);
			jsontxt = IOUtils.toString(is);
			if (Url.contains("vendors")) {
				JO = new JSONObject(jsontxt);
			}
		} else {
			throw new IllegalArgumentException("File not Found");
		}
		return JO.toString();
	}
}
