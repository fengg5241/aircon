package com.panasonic.b2bacns.bizportal.email;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.panasonic.b2bacns.bizportal.util.BizConstants;

/**
 * It is an utility class which loads the Mail property file.
 * 
 */
public class MailPropertiesUtil {

	private static final Logger logger = Logger
			.getLogger(MailPropertiesUtil.class);
	private static Properties prop = System.getProperties();
	private static boolean loded = false;

	private static void load() {
		InputStream resourceAsStream = MailPropertiesUtil.class
				.getClassLoader().getResourceAsStream(
						BizConstants.EMAIL_PROPERTIES_FILE);
		try {
			if (resourceAsStream != null) {
				prop.load(resourceAsStream);
				loded = true;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			logger.error(
					"Exception occured while loading email properties file.",
					exp);
		} finally {
			try {
				if (resourceAsStream != null) {
					resourceAsStream.close();
				}
			} catch (Exception exp) {
				logger.error("Exception occured while closing"
						+ " email properties file.", exp);
			}
		}
	}

	/**
	 * Get the property value based on the given key.
	 * 
	 * @param propertyName
	 * @return string
	 */
	public static String get(String propertyName) {
		if (!loded) {
			load();
		}
		String propertyValue = null;
		try {
			propertyValue = prop.getProperty(propertyName);

		} catch (Exception exp) {
			logger.error(
					"Error occured while reading property from Mail properties file",
					exp);
		}
		return propertyValue;

	}
}
