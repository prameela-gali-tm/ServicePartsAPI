package com.toyota.scs.serviceparts.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.toyota.scs.serviceparts.util.*;

public final class Settings {

    private static boolean initialized = false;

    public static Properties properties = new Properties();
    public static   String DataFilepath=TestEnvironment.Filepath;



    public static void loadSettings() {

	try {

	    InputStream in = new FileInputStream("src/main/resources/application.properties");
	    properties.load(in);
	

	    initialized = true;
	  

	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// Will set an environment provided from the command line. This will
	// override whatever has been set at the properties file.
	if (System.getProperties().containsKey("autolib.environment")) {
	    properties.setProperty("environment", System.getenv("autolib.environment").trim());
	   
	}

    }

    public static String getProperty(String name) {
	if (!initialized) {
	    loadSettings();
	}
	return properties.getProperty(name);
    }

}
