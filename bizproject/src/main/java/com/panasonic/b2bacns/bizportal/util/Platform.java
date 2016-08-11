package com.panasonic.b2bacns.bizportal.util;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

import com.panasonic.b2bacns.bizportal.installation.controller.Ca_DataController;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class Platform {
	String result = "";
	InputStream inputStream;
	private static final Logger logger = Logger
			.getLogger(Ca_DataController.class);
 
	public String getPropValues(){
 
		try {
			Properties prop = new Properties();
			String propFileName = "platform.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			result = prop.getProperty("securitydomain");
		} catch (Exception e) {
			logger.error("Exception: " + e);
		}
		return result;
	}

}
