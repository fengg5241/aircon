package com.panasonic.b2bacns.bizportal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.panasonic.b2bacns.bizportal.common.SessionInfo;

/**
 * This class contains common utility methods using across application
 * 
 * @author shobhit.singh
 * 
 */
@Component
public class CommonUtil {

	public static Cipher dcipher, ecipher;
	private static final Logger logger = Logger.getLogger(CommonUtil.class);

	@Resource(name = "properties")
	private Properties properties;

	public CommonUtil() {

	}

	/**
	 * Responsible for setting, initializing this object's encrypter and
	 * decrypter Chipher instances
	 * 
	 * @param passPhrase
	 */
	public CommonUtil(String passPhrase) {
		// 8-bytes Salt
		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
				(byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };

		// Iteration count
		int iterationCount = 19;

		try {

			// Generate a temporary key. In practice, you would save this key
			// Encrypting with DES Using a Pass Phrase
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
					iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("pbewithmd5anddes")
					.generateSecret(keySpec);

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameters to the cipthers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
					iterationCount);

			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (InvalidAlgorithmParameterException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (InvalidKeySpecException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (InvalidKeyException e) {
			logger.error("Exception:" + e.getMessage());
		}
	}

	/**
	 * Encrpt Password
	 * 
	 * @param str
	 * @return
	 */
	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string
			return Base64.encodeBase64String(enc);

		} catch (BadPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:" + e.getMessage());
		}
		return null;
	}

	/**
	 * Decrpt password To decrypt the encryted password
	 * 
	 * @param str
	 * @return
	 */
	public String decrypt(String str) {
		Cipher dcipher = null;
		try {
			byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
					(byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };
			int iterationCount = 19;
			try {
				String passPhrase = "";
				KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(),
						salt, iterationCount);
				SecretKey key = SecretKeyFactory
						.getInstance("PBEWithMD5AndDES")
						.generateSecret(keySpec);
				dcipher = Cipher.getInstance(key.getAlgorithm());
				// Prepare the parameters to the cipthers
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
						iterationCount);
				dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			}

			catch (InvalidAlgorithmParameterException e) {
				logger.error("Exception:" + e.getMessage());
			} catch (InvalidKeySpecException e) {
				logger.error("Exception:" + e.getMessage());
			} catch (NoSuchPaddingException e) {
				logger.error("Exception:" + e.getMessage());
			} catch (NoSuchAlgorithmException e) {
				logger.error("Exception:" + e.getMessage());
			} catch (InvalidKeyException e) {
				logger.error("Exception:" + e.getMessage());
			}
			// Decode base64 to get bytes
			byte[] dec = Base64.decodeBase64(str);
			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);
			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (BadPaddingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:" + e.getMessage());
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage());
		}
		return null;
	}

	/**
	 * generateResetToken: generate random unique number for reset.token.
	 * 
	 * @return {@link String}
	 */
	public static String generateResetToken() {
		return UUID.randomUUID().toString();
	}

	/**
	 * generateVerificationToken: generate random unique number for verification
	 * token.
	 * 
	 * @return {@link String}
	 */
	public static String generateVerificationToken() {
		return UUID.randomUUID().toString();
	}

	// Method to convert '/' to '#' to pass in PathVariable
	public static String encodeParameter(String inputStr) {

		String convertedStr = "";

		if (inputStr != null && inputStr.indexOf('/') > 0) {
			convertedStr = inputStr.replace('/', '#');
		} else {
			convertedStr = inputStr;
		}
		return convertedStr;
	}

	public static String getEncryptedPassword(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] result = getSalt(password).getBytes(
				BizConstants.CHARSET_ENCODE_FORMAT);
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		for (int i = 0; i < 1000; i++) {
			md.update(result);
			result = md.digest();
		}
		return bytesToHex(result);
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes)
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(
					1));
		return result.toString();
	}

	public static String getSalt(String password) {
		final byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8,
				(byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };
		StringBuffer result = new StringBuffer();
		for (byte byt : salt)
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(
					1));
		return result.toString().concat(password);
	}

	/**
	 * Util Method to convert first character in Upper Case
	 * 
	 * @param String
	 * @return String
	 */
	public static String ucFirst(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	// /**
	// * Generate Unique ID based on below mentioned parameters :
	// *
	// * @param type
	// * - OR or SR etc.
	// * @param Store
	// * Id
	// * @param Database
	// * Primary key
	// * @return unique id
	// */
	// public static String getNextId(String type, String storeId, long
	// primaryKey) {
	// int randomNumber = (int) (Math.random() * 9999);
	// String ranNum = null;
	// String strId = null;
	// try {
	// strId = String.valueOf(storeId);
	// ranNum = String.valueOf(randomNumber);
	// if (strId.length() < 2) {
	// strId = "0" + strId;
	// }
	// if (ranNum.length() < 4)
	// ranNum = "0" + ranNum;
	// } catch (Exception e) {
	// logger.info("Unique Id Generator has Error:" + e.getMessage());
	// }
	//
	// return type + "-" + strId + "-" + primaryKey + ranNum;
	//
	// }

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Long getDifferenceInDays(String fromDate, String toDate) {

		Date d1 = null;
		Date d2 = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		try {
			d1 = format.parse(fromDate + " 00:00:00");
			d2 = format.parse(toDate + " 23:59:59");

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffDays = diff / (24 * 60 * 60 * 1000);

			return diffDays;
		} catch (Exception ex) {
			logger.info("Exception occur while getting difference in days:"
					+ ex.getMessage());
		}

		return null;
	}

	public static boolean isAfterToday(String userSession, String toDate) {

		boolean afterToday = false;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar providedTimeCal = Calendar.getInstance(TimeZone
				.getTimeZone(userSession));

		Calendar currentTimeCal = Calendar.getInstance(TimeZone
				.getTimeZone(userSession));

		try {

			providedTimeCal.setTime(format.parse(toDate));
			if (providedTimeCal.getTimeInMillis() > currentTimeCal
					.getTimeInMillis()) {
				afterToday = true;
			}
		} catch (Exception ex) {
			logger.error(String.format("Exception occurred while calculating"
					+ "  provided time [%s] is after system Today", toDate), ex);
		}

		return afterToday;
	}

	/**
	 * set Time DifferenceBetween two AjaxCalls
	 * 
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	public static String setTimeDifferenceBetweenAjaxCalls(HttpSession session)
			throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String privious_execution_time = (String) session
				.getAttribute("privious_execution_time_call_2");
		String current_execution_time = dateFormat.format(date);
		Date privious = null;
		Date current = null;

		long diffSeconds = 0;
		String result = "0";

		if (privious_execution_time != null) {

			privious = dateFormat.parse(privious_execution_time);
			current = dateFormat.parse(current_execution_time);
			// in milliseconds
			long diff = current.getTime() - privious.getTime();
			diffSeconds = diff / 1000;

		}

		if ((diffSeconds >= 30 && diffSeconds <= 40)
				|| privious_execution_time == null) {
			session.setAttribute("privious_execution_time_call_2",
					current_execution_time);
			result = BizConstants.RESULT_SUCCESS;
		} else {

			result = BizConstants.RESULT_FAILURE;

		}

		return result;

	}

	/**
	 * convert entity to json String using jackson api
	 * 
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String convertFromEntityToJsonStr(Object obj)
			throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(obj);

	}

	/**
	 * convert json String to object using jackson api.
	 * 
	 * @param clazz
	 * @param jsonStr
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object convertFromJsonStrToEntity(Class<?> clazz,
			String jsonStr) throws JsonParseException, JsonMappingException,
			IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		Object obj = mapper.readValue(jsonStr, clazz);

		return obj;

	}

	/**
	 * Returns the session info object for the request
	 * 
	 * @param request
	 * @return
	 */
	public static SessionInfo getSessionInfo(HttpServletRequest request) {
		return getSessionInfo(request.getSession(false));
	}

	/**
	 * 
	 * @param request
	 * @param sessionInfo
	 */
	public static synchronized void setSessionInfo(HttpServletRequest request,
			final SessionInfo sessionInfo) {

		setSessionInfo(request.getSession(false), sessionInfo);
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	public static SessionInfo getSessionInfo(HttpSession session) {

		@SuppressWarnings("unchecked")
		AtomicReference<SessionInfo> sessionInfoHolder = (session
				.getAttribute(BizConstants.SESSION_INFO_OBJECT_NAME) != null) ? (AtomicReference<SessionInfo>) (session
				.getAttribute(BizConstants.SESSION_INFO_OBJECT_NAME)) : null;

		return (sessionInfoHolder != null ? sessionInfoHolder.get() : null);
	}

	/**
	 * 
	 * @param session
	 * @param sessionInfo
	 */
	public static synchronized void setSessionInfo(HttpSession session,
			final SessionInfo sessionInfo) {

		@SuppressWarnings("unchecked")
		AtomicReference<SessionInfo> sessionInfoHolder = (AtomicReference<SessionInfo>) session
				.getAttribute(BizConstants.SESSION_INFO_OBJECT_NAME);

		if (sessionInfoHolder == null
				|| (sessionInfoHolder != null && sessionInfoHolder.get() == null)) {
			sessionInfoHolder = new AtomicReference<SessionInfo>(sessionInfo);
		} else {
			sessionInfoHolder.set(sessionInfo);
		}

		session.setAttribute(BizConstants.SESSION_INFO_OBJECT_NAME,
				sessionInfoHolder);
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, HttpSession> getContextCachedSessionMap(
			HttpServletRequest request) {

		HashMap<String, HttpSession> value = null;

		Object contextObj = request.getServletContext().getAttribute(
				BizConstants.CACHED_SESSION_MAP_NAME);

		if (contextObj != null) {
			value = ((AtomicReference<HashMap<String, HttpSession>>) contextObj)
					.get();
		}

		return value;
	}

	@SuppressWarnings("unchecked")
	public static synchronized void setContextCachedSessionMap(
			HttpServletRequest request,
			final HashMap<String, HttpSession> cachedSessionMap) {

		AtomicReference<HashMap<String, HttpSession>> cachedSessionMapHolder = null;

		Object contextObj = request.getServletContext().getAttribute(
				BizConstants.CACHED_SESSION_MAP_NAME);

		if (contextObj != null) {
			cachedSessionMapHolder = (AtomicReference<HashMap<String, HttpSession>>) contextObj;
		}

		if (cachedSessionMapHolder == null
				|| (cachedSessionMapHolder != null && cachedSessionMapHolder
						.get() == null)) {
			cachedSessionMapHolder = new AtomicReference<HashMap<String, HttpSession>>(
					cachedSessionMap);
		} else {
			cachedSessionMapHolder.set(cachedSessionMap);
		}

		request.getServletContext().setAttribute(
				BizConstants.CACHED_SESSION_MAP_NAME, cachedSessionMapHolder);
	}

	/**
	 * Returns list of error field names
	 * 
	 * @param bindingResult
	 * @return
	 */
	public static List<String> getErrorFields(BindingResult bindingResult) {
		List<String> errorFieldNameList = new ArrayList<>();
		List<ObjectError> list = bindingResult.getAllErrors();
		for (ObjectError objectError : list) {
			FieldError fieldError = (FieldError) objectError;
			String fieldName = fieldError.getField();
			errorFieldNameList.add(fieldName);
		}
		return errorFieldNameList;
	}

	/**
	 * Convert string date to date object with specified date format.
	 * 
	 * @param stringDate
	 * @param dateFormat
	 * @return
	 */
	public static Date stringToDate(String stringDate, String dateFormat) {
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = formatter.parse(stringDate);
		} catch (Exception ex) {

			logger.error("There is a problem to convert from string to Date - "
					+ ex);
		}
		return date;
	}

	/**
	 * Convert Data object to string date with specified date format.
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateToString(Date date, String dateFormat) {
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		String dateString = formatter.format(date);
		return dateString;

	}

	/**
	 * Convert Timestamp object to string date with specified date format.
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String dateToString(Timestamp timeStamp, String dateFormat) {
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		String stringDate = null;
		stringDate = formatter.format(new Date(timeStamp.getTime()));
		return stringDate;
	}

	/**
	 * convert map data in percentage
	 * 
	 * @param map
	 * @param totalUnits
	 * @return
	 */
	public static Map<String, Long> getMapDataInPercentage(
			Map<String, Long> map, long totalUnits) {

		HashMap<String, Long> hashMap = new HashMap<>();

		double percentValue = 0;

		for (Entry<String, Long> entry : map.entrySet()) {
			percentValue = (double) (entry.getValue() * 100) / totalUnits;
			hashMap.put(entry.getKey(), Math.round(percentValue));
		}
		return hashMap;
	}

	/**
	 * Return map with max values
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Long> getMapWithMaxValue(Map<String, Long> map) {

		Map<String, Long> newMap = new HashMap<>();

		long maxValueInMap = Collections.max(map.values());

		for (Entry<String, Long> entry : map.entrySet()) {
			if (entry.getValue() == maxValueInMap) {
				newMap.put(entry.getKey(), entry.getValue());
			}
		}

		return newMap;
	}

	/**
	 * Format and returns value up to two decimal
	 * 
	 * @param value
	 * @return
	 */
	public static Double getFormattedValueUpToTwoDecimal(Double value) {
		DecimalFormat df = new DecimalFormat("###.##");
		return Double.parseDouble(df.format(value));
	}

	/**
	 * Format and returns value up to two decimal
	 * 
	 * @param value
	 * @return
	 */
	public static Double getFormattedValueUpToTwoDecimal(Object value) {

		BigDecimal bigDecimalNumber = new BigDecimal(value.toString());
		bigDecimalNumber = bigDecimalNumber.setScale(2, RoundingMode.HALF_UP);

		return bigDecimalNumber.doubleValue();
	}

	/**
	 * converts from Timestamp date to string in format "yyyy-MM-dd"
	 * 
	 * @param timeStamp
	 * @return
	 */
	public static String dateToString(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = null;
		try {
			stringDate = formatter.format(date);
		} catch (Exception ex) {
			System.out
					.println("===There is a problem to convert from string to Date===");
		}
		return stringDate;
	}

	/**
	 * Convert List to String with commna seperated.
	 * 
	 * @param indoorUnit
	 * @return
	 */
	public static String convertCollectionToString(Collection<?> indoorUnit) {
		return indoorUnit
				.toString()
				.replace(BizConstants.START_INDEX_STRING,
						BizConstants.EMPTY_STRING)
				.replace(BizConstants.CLOSE_INDEX_STRING,
						BizConstants.EMPTY_STRING);
	}

	/**
	 * Return JSON for error message
	 * 
	 * @param errorKey
	 * @return
	 */
	public static String getJSONErrorMessage(String errorKey) {
		String customErrorMessage = "{\"errorMessage\":" + "\"" + errorKey
				+ "\"" + "}";
		return customErrorMessage;
	}

	/**
	 * Converts a comma separated string to a List of String type
	 * 
	 * @param listString
	 *            The comma separated string
	 * @return The List<String>
	 */
	public static List<String> convertStringToList(String listString) {

		return Arrays.asList(listString.split(BizConstants.COMMA_STRING));

	}

	/**
	 * 
	 * @param date
	 * @param dateDiff
	 * @return
	 */
	public static Calendar getCalenderDate(Calendar date, int dateDiff) {

		Calendar calAdd = date;

		calAdd.add(GregorianCalendar.DATE, dateDiff);

		return calAdd;

	}

	public static Object convertFromJsonStrToEntityList(Class<?> clazz,
			String jsonStr) throws JsonParseException, JsonMappingException,
			IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		Object jsonList = mapper.readValue(jsonStr, TypeFactory
				.defaultInstance().constructCollectionType(List.class, clazz));
		return jsonList;

	}

	public static GregorianCalendar getCalendarWeekFromMonday(int year,
			int weekNumber) {

		GregorianCalendar weekFromMonday = new GregorianCalendar(Locale.GERMANY);
		weekFromMonday.clear();
		weekFromMonday.set(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
		weekFromMonday.set(GregorianCalendar.WEEK_OF_YEAR, weekNumber);
		weekFromMonday.set(GregorianCalendar.YEAR, year);
		return weekFromMonday;

	}

	public static GregorianCalendar getCalendarWeekLastDay(int year,
			int weekNumber) {

		GregorianCalendar weekFromMonday = new GregorianCalendar(Locale.GERMANY);
		weekFromMonday.clear();
		weekFromMonday.set(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
		weekFromMonday.set(GregorianCalendar.WEEK_OF_YEAR, weekNumber + 1);
		weekFromMonday.set(GregorianCalendar.YEAR, year);
		weekFromMonday.add(GregorianCalendar.DATE, -1);
		return weekFromMonday;

	}

	public static Map<String, String> getDateFromPeriod(String period) {

		Map<String, String> dateMap = new HashMap<String, String>();

		Calendar currentCal = Calendar.getInstance();

		Calendar cal = (Calendar) currentCal.clone();

		switch (period) {
		case BizConstants.PERIOD_THISYEAR:

			cal.set(Calendar.DAY_OF_YEAR, 1);

			break;

		case BizConstants.PERIOD_THISMONTH:

			cal.set(Calendar.DAY_OF_MONTH, 1);

			break;

		case BizConstants.PERIOD_THISWEEK:

			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

			break;

		case BizConstants.PERIOD_TODAY:

			break;

		}

		dateMap.put(BizConstants.KEY_START_RANGE, dateToString(cal.getTime()));

		dateMap.put(BizConstants.KEY_END_RANGE,
				dateToString(currentCal.getTime()));

		return dateMap;
	}

	public static int getWeekOfMonthFromWeekOfYear(int weekOfYear) {

		int weekOfMonth = 0;

		Calendar cal = Calendar.getInstance();

		cal.clear();

		cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);

		weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);

		return weekOfMonth;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> mapSortedByValues(
			Map<K, V> map) {

		List<Entry<K, V>> sortedMapList = new ArrayList<Entry<K, V>>(
				map.entrySet());

		Collections.sort(sortedMapList, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});
		Map<K, V> sortedMap = null;
		if (sortedMapList != null && !sortedMapList.isEmpty()) {
			sortedMap = new LinkedHashMap<K, V>();
			for (Entry<K, V> entry : sortedMapList) {
				sortedMap.put(entry.getKey(), entry.getValue());
			}
		}
		return sortedMap;
	}

	public static String getCalendarWithDateFormat(long milliseconds,
			String dateformat) {

		Calendar cal = Calendar.getInstance();

		cal.setTimeInMillis(milliseconds);

		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);

		String formatted = format1.format(cal.getTime());

		return formatted;

	}

	public static GregorianCalendar convertStringToCalendarWithDateFormat(
			String date, String dateformat) {

		GregorianCalendar cal = new GregorianCalendar(Locale.GERMANY);

		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return cal;

	}

	public static Calendar setUserTimeZone(Calendar cal, String userTimeZone) {

		Instant dt = cal.getTime().toInstant();

		LocalDateTime zoneDatetime = LocalDateTime.ofInstant(dt,
				ZoneId.of(userTimeZone));

		Calendar cal1 = new GregorianCalendar(Locale.GERMANY);

		cal1.clear();

		cal1.set(zoneDatetime.getYear(),
				zoneDatetime.getMonth().getValue() - 1,
				zoneDatetime.getDayOfMonth(), zoneDatetime.getHour(),
				zoneDatetime.getMinute(), zoneDatetime.getSecond());

		return cal1;
	}

	public static String getCalendarWithDateFormatHourly(long milliseconds,
			String dateformat) {

		GregorianCalendar cal = new GregorianCalendar(Locale.GERMANY);

		cal.setTimeInMillis(milliseconds);

		cal.set(Calendar.MINUTE, 0);

		cal.set(Calendar.SECOND, 0);

		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);

		String formatted = format1.format(cal.getTime());

		return formatted;

	}

	public static Calendar getCalendarWithDateFormatWithoutTime(Calendar cal) {

		Calendar cal1 = (Calendar) cal.clone();

		cal1.set(Calendar.HOUR_OF_DAY, 0);

		cal1.set(Calendar.MINUTE, 0);

		cal1.set(Calendar.SECOND, 0);

		cal1.getTime();

		return cal1;

	}

	public static Calendar getCalendarFromMilliseconds(long milliseconds) {

		Calendar cal = Calendar.getInstance();

		cal.setTimeInMillis(milliseconds);

		return cal;

	}

	public static Calendar dateToCalendar(Date date) {
		Calendar gc = Calendar.getInstance(Locale.GERMANY);

		gc.setFirstDayOfWeek(Calendar.MONDAY);
		gc.setTime(date);
		return gc;
	}

	public static void writeDownloadableFile(HttpServletResponse response,
			File file) {

		String fileExtenstion = file.getName().split("\\.")[1];

		if (fileExtenstion.equals(BizConstants.REPORT_TYPE_CSV)) {
			response.setContentType("text/csv");
		} else if (fileExtenstion.equals(BizConstants.REPORT_TYPE_EXCEL)) {
			response.setContentType("application/vnd.ms-excel");
		}

		response.setHeader("Content-disposition", "attachment; filename="
				+ file.getName());

		response.setContentLength((int) file.length());

		FileInputStream inputFile;
		try {
			inputFile = new FileInputStream(file);

			if (inputFile != null) {

				FileCopyUtils.copy(inputFile, response.getOutputStream());
				response.getOutputStream().flush();
			}
		} catch (IOException e) {
			logger.error("Some error occured in writing downloadable file", e);
		} finally {
			try {
				response.getOutputStream().flush();
			} catch (IOException e) {
				logger.error(
						"IO error occured while writing downloadable file", e);
			} catch (Exception e2) {
				logger.error(
						"Some error occured while writing downloadable file",
						e2);
			}
			try {
				response.getOutputStream().close();
			} catch (Exception e3) {
				logger.error(
						"Some error occured while clsoing the stream of downloadable file",
						e3);
			}
		}

	}

	/**
	 * Format and returns value up to two decimal in minutes
	 * 
	 * @param value
	 * @return
	 */
	public static Double getFormattedValueUpToTwoDecimalInMinutes(Object value) {

		BigDecimal bigDecimalNumber = new BigDecimal(value.toString());

		bigDecimalNumber = bigDecimalNumber.divide(new BigDecimal(60), 2,
				RoundingMode.HALF_UP);

		return bigDecimalNumber.doubleValue();

	}

	public static long toLocalTime(long time, TimeZone to) {
		return convertTime(time, TimeZone.getTimeZone("UTC"), to);
	}

	public static long toUTC(long time, TimeZone from) {
		return convertTime(time, from, TimeZone.getTimeZone("UTC"));
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
	 * main method
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {

		/*
		 * // setUserTimeZone(Calendar.getInstance(), "America/Virgin"); // //
		 * Object wtfw = 85858.269673734; //
		 * System.out.println(getFormattedValueUpToTwoDecimal(wtfw));
		 * 
		 * System.out.println(getCalendarWeekLastDay(2015, 45).getTime());
		 * 
		 * Calendar cal = Calendar.getInstance(Locale.GERMANY);
		 * cal.set(Calendar.DATE, 1); cal.set(Calendar.MONTH, Calendar.OCTOBER);
		 */

		// TimeZone tz = TimeZone.getDefault();

		String timeZoneId1[] = TimeZone.getAvailableIDs();

		for (String timeZone : timeZoneId1) {

			final SimpleDateFormat f = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss z");
			f.setTimeZone(TimeZone.getTimeZone(timeZone));
			System.out.println(timeZone + "  End Time :  "
					+ f.format(new Date()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(f.parse(f.format(new Date())));
			cal.add(Calendar.HOUR, -24);
			System.out.println(timeZone + "  Start Time :  "
					+ f.format(cal.getTime()));

		}

	}

}
