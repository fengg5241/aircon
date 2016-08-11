package com.panasonic.b2bacns.bizportal.email.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.security.spec.KeySpec;
import java.util.Collection;
import java.util.Properties;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.log4j.Logger;

public final class Utility {
	private static Logger m_Logger = Logger.getLogger(Utility.class);

	private static final String UNICODE_FORMAT = "UTF-8";

	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

	private static KeySpec ks;

	private static SecretKeyFactory skf;

	private static Cipher cipher;

	static byte[] arrayBytes;

	private static String myEncryptionKey;

	private static String myEncryptionScheme;

	static SecretKey key;

	public static final TimeZone utcTZ = TimeZone.getTimeZone(Constants.UTC);

	public static long toLocalTime(long time, TimeZone to) {
		return convertTime(time, utcTZ, to);
	}

	public static long toUTC(long time, TimeZone from) {
		return convertTime(time, from, utcTZ);
	}

	public static long convertTime(long time, TimeZone from, TimeZone to) {
		return time + getTimeZoneOffset(time, from, to);
	}

	private static long getTimeZoneOffset(long time, TimeZone from, TimeZone to) {
		int fromOffset = from.getOffset(time);
		int toOffset = to.getOffset(time);
		int diff = 0;

		if (fromOffset >= 0) {
			if (toOffset > 0) {
				toOffset = -1 * toOffset;
			} else {
				toOffset = Math.abs(toOffset);
			}
			diff = (fromOffset + toOffset) * -1;
		} else {
			if (toOffset <= 0) {
				toOffset = -1 * Math.abs(toOffset);
			}
			diff = (Math.abs(fromOffset) + toOffset);
		}
		return diff;
	}

	/**
	 * Get the value from property file.
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperties(String... key) {
		Properties props = null;
		InputStream is = null;
		String value = null;
		try {
			String filename;
			if (key.length == 2) {
				filename = key[1];
			} else {
				filename = Constants.PROPERTY_FILE_ETL;
			}

			if (props == null) {
				props = new Properties();
				is = Utility.class.getClassLoader().getResourceAsStream(
						filename);
				props.load(is);
				if (is == null) {
					System.out.println("Sorry, unable to find " + filename);
					return null;
				}
				value = props.getProperty(key[0]);
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {
				m_Logger.error(ex);
			}

		}
		return value;
	}

	/**
	 * Convert List to String with comma separated.
	 * 
	 * @param indoorUnit
	 * @return
	 */
	public static String convertCollectionToString(Collection<?> collection) {
		return collection.toString()
				.replace(Constants.STRING_LEFT_INDEX, Constants.EMPTY_STRING)
				.replace(Constants.STRING_RIGHT_INDEX, Constants.EMPTY_STRING)
				.replace(Constants.STRING_SPACE, Constants.EMPTY_STRING);
	}

	/**
	 * Convert Site of String into SiteVO List
	 * 
	 * @param indoorUnit
	 * @return
	 */
	/*
	 * public static List<SiteVO> convertSiteStringToSiteVO(String sites) {
	 * List<SiteVO> siteList = new ArrayList<SiteVO>(); SiteVO site = null;
	 * String[] siteListstr = sites.split(Constants.STRING_SEMICOLON); for (int
	 * i = 0; i < siteListstr.length; i++) { String[] siteStr =
	 * siteListstr[i].split(Constants.STRING_COMMA); if (siteStr.length == 4) {
	 * site = new SiteVO(); site.setId(Long.parseLong(siteStr[0]));
	 * site.setCategoryId(Integer.parseInt(siteStr[2]));
	 * site.setUniqueId(siteStr[1]); site.setTimezone(siteStr[3]);
	 * siteList.add(site); } } return siteList; }
	 * 
	 * public static String convertSiteVOToString(SiteVO site) { StringBuilder
	 * sb = new StringBuilder(Constants.EMPTY_STRING);
	 * sb.append(site.getId()).append(Constants.STRING_COMMA)
	 * .append(site.getUniqueId()).append(Constants.STRING_COMMA)
	 * .append(site.getCategoryId()).append(Constants.STRING_COMMA)
	 * .append(site.getTimezone()).append(Constants.STRING_SEMICOLON);
	 * 
	 * return sb.toString(); }
	 */

	/**
	 * Util Method to assign cryptography variables
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static void initiateCryptography() throws Exception {

		myEncryptionKey = Constants.ENCRYPTION_KEY;

		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;

		arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);

		ks = new DESedeKeySpec(arrayBytes);

		skf = SecretKeyFactory.getInstance(myEncryptionScheme);

		cipher = Cipher.getInstance(myEncryptionScheme);

		key = skf.generateSecret(ks);
	}

	/**
	 * This API is used to write the execution result (success/fail) in the file
	 * 
	 * @param result
	 *            If success then true otherwise false
	 * @return void
	 */
	public static void writeFile(boolean result) {

		Writer output = null;

		String text = Constants.STATUS_FAIL;

		if (result == true) {

			text = Constants.STATUS_SUCCESS;

		}

		File file = new File(Utility.getProperties(Constants.EMAIL_LCK_FILE));

		try {

			output = new BufferedWriter(new FileWriter(file));

			output.write(text);

			output.close();

			if (m_Logger.isDebugEnabled()) {

				m_Logger.debug("writeFile : Write " + text
						+ " in the t.lck file");

			}

		} catch (IOException io) {

			m_Logger.error(io.getMessage());

		} finally {

			try {
				output.close();
			} catch (IOException e) {
				m_Logger.error(e.getMessage());
			}
		}

	}
}
