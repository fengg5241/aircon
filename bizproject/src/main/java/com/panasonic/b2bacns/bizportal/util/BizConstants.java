/**
 * 
 */
package com.panasonic.b2bacns.bizportal.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This Interface contains the Global Constants used throughout the application.
 * 
 * @author kumar.madhukar
 * 
 */
public interface BizConstants {

	// Name of the properties file used in the application
	String EMAIL_PROPERTIES_FILE = "email.properties";

	String CHARSET_ENCODE_FORMAT = "UTF-8";

	// Properties of Email
	String PROP_TLS_ENABLE = "mail.smtp.starttls.enable";
	String PROP_SMTP_HOST = "mail.smtp.host";
	String PROP_SMTP_USER = "mail.smtp.user";
	String PROP_SMTP_PASSWORD = "mail.smtp.password";
	String PROP_SMTP_PORT = "mail.smtp.port";
	String PROP_USE_SMTP_AUTH = "mail.smtp.auth";
	String CONTENT_TYPE_HTML = "text/html";
	String MAIL_PROPTOCOL = "smtp";
	String PROP_MAIL_ACCOUNT_ID = "mail.smtp.account.info";
	String PROP_MAIL_ACCOUNT_PASSWORD = "mail.smtp.password.account.info";
	// End of Email properties

	// Fail Attempt Type
	String FAIL_INCREMENT = "fail_increment";
	String FAIL_RESET = "fail_reset";

	// Failed Attempt messages
	String FOUR_SUCCESSIVE_FAILED_ATTEMPT = "Invalid email or password.";
	String FIFTH_SUCCESSIVE_FAILED_ATTEMPT = "You have one more attempt before your account will be locked.";
	String SIXTH_SUCCESSIVE_FAILED_ATTEMPT = "Your account is locked.";
	String FAILURE_MESSAGE = "Sign in failed.";
	String SUCCESSFULLY_SIGNED_IN = "Signed in successfully";
	String FAILED_SIGNED_IN = "Either user name or password is not correct";

	// User Active Flag
	Byte USER_STATUS_INACTIVE = (byte) 0;
	Byte USER_STATUS_ACTIVE = (byte) 1;

	// Active Flag
	Byte STATUS_INACTIVE = (byte) 0;
	Byte STATUS_ACTIVE = (byte) 1;

	// user confirmation error codes

	// int USER_ALREADY_CONFIRMED_ERROR = 101;
	// int EMAIL_NOT_EXIST_ERROR = 102;
	// int INVALID_TOKEN = 103;
	// int TOKEN_EXPIRED_ERROR = 104;
	// int PASSWORD_EXPIRED_ERROR = 105;
	//
	// // unlock user account error codes
	// int USER_ACCOUNT_ALREADY_UNLOCK = 106;
	// int HIBERNATE_EXCEPTION = 107;
	// int EXCEPTION = 108;
	//
	// int CHANGE_PASSWORD_TYPE_FORGET = 203;
	// int CHANGE_PASSWORD_TYPE_CHANGE = 204;
	//
	// int COMPANY_ALREADY_EXISTS_ERROR = 201;
	//
	// int INSUFFICIENT_PRIVILEDGES_ERROR = 301;
	//
	// //
	//
	String SUCCESS_REGISTRATION = "SUCCESS";
	String SUCCESS_REGISTRATION_DIST = "Distribution group added to devices successfully";
	String DUPLICATE_REGISTRATION = "DUPLICATE";
	String SELECT_MIN_IDU = "Please select atleast one IDU";
	String APPLICATION_DISTRIBUTION_ERROR = "Devices not added to application database";
	

	// int DIFFERENT_FROM_EXIST_PASSWORD_ERROR = 2;
	// int SHOULD_DIFFERENT_FROM_EXIST_PASSWORD_ERROR = 3;

	// Patterns
	String EMAIL_ID_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	String PASSWORD_PATTERN = "^.*(?=.{10,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";

	// Forget Password constants
	/* Modified by ravi as path is correct */
	String FORGET_PASSWORD_URL = "/login/changePassword.htm";
	String UNLOCK_TOKEN_URL = "/unlock/unlockUser.htm";
	int USER_PASSWORD_MAX_LENGTH = 16;
	int USER_PASSWORD_MIN_LENGTH = 8;

	// Unique IDs for different RC actions like change mode, temperature, fan
	// speed, flap position, device state
	long ATTRIBUTE_MODE = 1L;
	long ATTRIBUTE_TEMP = 2L;
	long ATTRIBUTE_FAN_SPEED = 3L;
	long ATTRIBUTE_VANE_POS = 4L;
	long ATTRIBUTE_STATE = 5L;

	String ATTRIBUTE_VALUE_STATE_ON = "On";
	String ATTRIBUTE_VALUE_STATE_OFF = "Off";

	String OP_MODE_DRY = "/operationModeDry";
	String OP_MODE_FAN = "/operationModeFan";
	String OP_MODE_HEAT = "/operationModeHeat";
	String SET_TEMP = "/settingTemperature";
	String FAN_SPEED_AUTO = "/fanSpeedAuto";
	String FAN_SPEED_MIN = "/fanSpeedMin";
	String FAN_SPEED_2 = "/fanSpeed2";
	String FAN_SPEED_MAX = "/fanSpeedMax";
	String VANE_POS_HORZ_1 = "/vanePosHorz1";
	String VANE_POS_HORZ_2 = "/vanePosHorz2";
	String VANE_POS_HORZ_3 = "/vanePosHorz3";
	String VANE_POS_HORZ_4 = "/vanePosHorz4";
	String VANE_POS_HORZ_5 = "/vanePosHorz5";
	String VANE_POS_HORZ_SWING = "/vanePosHorzSwing";

	String REQ_MODE_AUTO = "Request AC operation mode : Auto - ";
	String REQ_MODE_COOL = "Request AC operation mode : Cooling - ";
	String REQ_MODE_HEAT = "Request AC operation mode : Heating - ";
	String REQ_MODE_DRY = "Request AC operation mode : Dry - ";
	String REQ_MODE_FAN = "Request AC operation mode : Fan - ";

	String REQ_FAN_AUTO = "Request AC Fan Speed : Auto - ";
	String REQ_FAN_MIN = "Request AC Fan Speed : Min - ";
	String REQ_FAN_MED = "Request AC Fan Speed : Medium - ";
	String REQ_FAN_MAX = "Request AC Fan Speed : Max - ";

	String REQ_TEMPERATURE = "Request AC Temperature set successfully";

	String REQ_VANE_POS = "Request AC Vane position - ";

	String REQ_AC_ON = "Request AC Turn On - ";
	String REQ_AC_OFF = "Request AC Turn Off - ";

	String TURN_ON = "/turnOn";
	String TURN_OFF = "/turnOff";

	String SHOW_CHART_BY_HOURS = "hours";
	String SHOW_CHART_BY_DAYS = "days";
	String SHOW_CHART_BY_WEEKS = "weeks";
	String SHOW_CHART_BY_MONTHS = "months";
	String SHOW_CHART_BY_YEARS = "years";

	String PERIOD_THISYEAR = "thisyear";
	String PERIOD_THISMONTH = "thismonth";
	String PERIOD_THISWEEK = "thisweek";
	String PERIOD_TODAY = "today";
	String PERIOD_24HOURS = "past24hours";
	String RANGE_YEAR = "year";
	String RANGE_MONTH = "month";
	String RANGE_WEEK = "week";
	String RANGE_DAY = "day";
	String RANGE_3YEAR = "3year";

	String PERIOD_RANGE = "range";

	//Modifed by seshu.
	String ROLE_TYPE_PANASONIC = "Panasonic";
	String ROLE_TYPE_CUSTOMER = "Customer";
	String ROLE_TYPE_INSTALLER = "Installer AC/CA";

	Long ROLE_TYPE_ID_PANASONIC = 1l;
	Long ROLE_TYPE_ID_CUSTOMER = 3l;
	Long ROLE_TYPE_ID_INSTALLER = 2l;

	Long PANASONIC_COMPANY_ID = 1l;
	Long SUPER_ADMIN_USER_ID = 1l;
	Long SUPER_ADMIN_ROLE_ID = 1l;

	String EMPTY_STRING = "";
	// Added By Ravi
	String EMPTY_SPACE = " ";
	String STRING_NUMBER_00 = "0.0";
	String COMMA_STRING = ",";
	String SINGLE_QUOTE_STRING = "'";
	String LEFT_BRACKET = "(";
	String RIGHT_BRACKET = ")";
	String OR_STRING = " or";
	String NULL_STRING = "null";
	String START_INDEX_STRING = "[";
	String CLOSE_INDEX_STRING = "]";
	String DASH_STRING = "-";
	String INDOOR = "Indoor";
	String OUTDOOR = "Outdoor";
	String RESULT_SUCCESS = "1";
	String RESULT_FAILURE = "0";
	String TIME_MINIMUM = "30";
	String TIME_MAXIMUM = "40";
	String GAS_METER_DATA = "Gas";
	String HEAT_METER_DATA = "Heat";
	Double NUMBER_00 = 0.0;
	String WORKING_HOURS_TREND_GRAPH = "workingHoursTrendGraph";
	String ROOM_TEMPERATURE_GRAPH = "roomTempGraph";
	String POWER_CONSUMPTION_GRAPH = "power_consumption";
	String CAPACITY_GRAPH = "capacityGraph";
	String EFFICIENCY_GRAPH = "efficiencyGraph";
	String GAS_HEAT_GRAPH = "gasHeatGraph";
	String STATS_ENERGY_CONSUMPTION_TODAY = "Today";

	String REPORT_TYPE_CSV = "csv";
	String REPORT_TYPE_EXCEL = "excel";
	String REPORT_TYPE_PDF = "PDF";
	String REPORT_ERROR = "report.document.error";
	String REPORT_HISTORY = "History";
	String REPORT_PARAMETER = "Parameter";

	String YES = "YES";
	String NO = "NO";

	String PAGE_MODE_ADD = "Add";
	String PAGE_MODE_EDIT = "Edit";
	String PERMISSION_USERS = "Users";
	String PERMISSION_NAME_COMPANY = "Companies";
	Integer PERMISSION_VAULE_READ = 4;
	Integer PERMISSION_VAULE_READWRITE = 7;
	String APPLICATION_CONTEXT_KEY = "applicationContext";
	String USER_LOG_RESEND_UNLOCK_INSTRUCTIONS = "Resend unlock Instructions Requested By User";
	String USER_LOG_ACCOUNT_UNLOCKED = "User Account Unlocked";
	String USER_LOG_RESEND_CONFIRMATION_INSTRUCTIONS = "Resend confirmation Instructions Requested By User";
	String USER_LOG_ACCOUNT_CONFIRMED_PASSWORD_CREATED = "User Account Confirmed , new password created";
	String USER_LOG_ACCOUNT_LOCKED = "User Account is locked due to six or more successive failed attempt for login";
	
	String CA_Registration_Success = "CA Registration Successfull";
	String CA_Registration_Duplicate = "CA Registration Failed, Adapter Mac Address Already exists";
	String CA_Association_Success = "CA Successfully Associated To Site";
	String CA_Association_FAILURE = "CA failed Associate To Site";
	
	String CA_NAME_DUPLICATE = "Failed: CA Name already exists..";
	String CA_Registration_Failed = "CA Registration Failed In SPF, deleted Inserted record in App DB";
	
	String Cust_Registration_Success = "Customer Registered Successfully,Site Registered";
	String Register_Site = "Site Registered Successfully";
	String Already_Registered_User = "Customer Was Already Created, Site Registered";
	String All_Sites_registered = "All Sites already Registered To customer";
	
	String HEAT = "HEAT";
	String COOL = "COOL";
	String FAN = "FAN";
	String DRY = "DRY";
	String AUTO = "AUTO";
	String MIX = "MIX";

	String HIGH = "high";
	String MEDIUM = "medium";
	String LOW = "low";
	String SWING = "swing";
	String F1 = "f1";
	String F2 = "f2";
	String F3 = "f3";
	String F4 = "f4";
	String F5 = "f5";

	// alarm status
	String ALARM_STATUS_NEW = "new";
	String ALARM_STATUS_ONHHOLD = "on-hold";
	String ALARM_STATUS_FIXED = "fixed";

	// unit status
	String UNIT_STATUS_ACTIVE = "active";
	String UNIT_STATUS_INACTIVE = "inactive";

	// types of IDs
	String ID_TYPE_COMPANY = "company";
	String ID_TYPE_GROUP = "group";
	String ID_TYPE_INDOOR = "indoorUnit";
	String ID_TYPE_CA = "caUnit";
	String ID_TYPE_OUTDOOR_VRF = "outdoorUnitVRF";
	String ID_TYPE_OUTDOOR_GHP = "outdoorUnitGHP";
	String ID_TYPE_OUTDOOR = "outdoorUnit";
	String ID_TYPE_FIRST_GROUP = "firstGroup";
	String ID_TYPE_AlARMTYPE = "alarmType";
	String ID_TYPE_SITE = "site";
	String ID_TYPE_LOGICAL = "logical";
	String ID_TYPE_REFRIGERANT_CIRCUIT = "refrigerantcircuit";

	// chart type for graphs
	String CHART_TYPE_OPTION_ON = "on";
	String CHART_TYPE_OPTION_OFF = "off";
	String CHART_TYPE_OPTION_HEAT = "heat";
	String CHART_TYPE_OPTION_COOL = "cool";

	String CHART_TYPE_OPTION_COP = "cop";
	String CHART_TYPE_OPTION_SEER = "seer";

	String UNIT_TYPE_OUTDOOR = "outdoor";
	String UNIT_TYPE_INDOOR = "indoor";

	String JSON_DATA = "json_data";

	String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	String DATE_FORMAT_YYYYMMDD_DOWNLOAD = "dd/MM/yyyy";
	String DATE_FORMAT_YYYYMMDD_HHMMSS_DOWNLOAD = "dd/MM/yyyy hh:mm:ss";
	//added by ravi
	String DATE_FORMAT_YYYYMMDD_24HHMMSS_DOWNLOAD = "dd/MM/yyyy HH:mm:ss";
	String DATE_FORMAT_YYYYMMDD_HHMM_DOWNLOAD = "dd/MM/yyyy HH:mm";
	String DATE_FORMAT_YYYYMMDD_DISTREPORT = "yyyy/MM/dd";
	String DATE_FORMAT_YYYYMMDD_DISTREPORT_HEADER = "yyyyMMdd";
	String DATE_FORMAT_YYYYMMDD_HHMM_DISTREPORT_HEADER = "yyyyMMddhhmm";
	String DATE_FORMAT_YYYYMMDD_HHMMSSS = "yyyy-MM-dd HH:mm:ss.S";

	// Request parameters
	String REQUEST_ID = "id";
	String REQUEST_ID_TYPE = "idType";
	String REQUEST_START_RANGE = "start_range";
	String REQUEST_END_RANGE = "end_range";

	String REQUEST_CHART_TYPE_OPTION = "chart_type_option";
	String REQUEST_OFFSET = "offset";
	String REQUEST_MIN_TEMP = "minTemp";
	String REQUEST_MAX_TEMP = "maxTemp";
	String REQUEST_STATUS = "status";

	String REQUEST_SELECTED_PARAM_LIST = "selectedParamList";

	// views for statistics
	String STATS_VIEW = "stats/visualization";
	String STATS_VIEW_WORK_HOUR_BREAKDOWN = "stats/working_hours_breakdown";
	String STATS_VIEW_WORK_HOUR_TREND = "stats/working_hours_trend";
	String STATS_VIEW_ROOM_TEMPERATURE = "stats/room_temperature";
	String STATS_VIEW_POWER_CONSUMPTION = "stats/power_consumption";
	String STATS_VIEW_CAPACITY = "stats/capacity";
	String STATS_VIEW_EFFICIENCY = "stats/efficiency";
	String STATS_VIEW_GAS_HEAT = "stats/gas_heat_data";

	// Compare statistics
	String COMPARE_STATS_INDOOR = "indoorUnit";
	String COMPARE_STATS_OUTDOOR = "outdoorUnit";
	String COMPARE_STATS_GROUP = "group";
	String COMPARE_EFFICIENCY = "efficiency";
	String COMPARE_CAPACITY = "capacity";
	String COMPARE_CO2 = "co2";
	String COMPARE_ENERGY_CONSUMPTION = "energyConsumption";
	String COMPARE_ROOM_TEMPERATURE = "roomTemperature";
	String COMPARE_SET_TEMPERATURE = "setTemperature";
	String COMPARE_WORKING_HOURS = "workingHours";
	String TABLE_POWER_CONSUMPTION_CAPACITY = "power_consumption_capacity";
	String TABLE_INDOORUNITSTATISTICS = "indoorunitstatistics";
	String REFRIGERANTSTATISTICS_TABLE = "refrigerantcircuit_statistics_";
	String REFRIGERANTSTATISTICS_MAIN_TABLE = "refrigerantcircuit_statistics";


	
	// view for schedule
	String SCHEDULE_VIEW = "schedule/schedule";
	
	String ON = "ON";
	String OFF = "OFF";

	int ECONAVI_ON = 1;
	int ECONAVI_OFF = 0;

	String NO_RECORDS_FOUND = "no.records.found";
	String NO_ON_DEVICE_FOUND = "no.on.device.found";
	String NO_ACTIVE_DEVICE_FOUND = "no.active.device.found";
	String REQUEST_FORMAT_ERROR = "request.format.error";
	String DATE_FORMAT_ERROR = "date.format.error";
	String APPLICATION_ERROR = "error.application";
	String PLATFORM_ERROR_DUPLICATE = "Distribution group already present, selected devices not added";
	String PLATFORM_ERROR_DUPLICATE2 = "If pulse meter is assigned to particular Distribution group and one indoor units must be added to that distribution group";
	
	
	String PLATFORM_ERROR = "Distribution group already created, selected devices not added";

	
	

	String REQUEST_RESOURCE_NOT_AVAILABLE = "request.resource.not.available";
	String NO_GROUP_SELECTED = "no.group.selected";
	String FILE_NOT_FOUND = "file.not.found";
	String HYPHEN = "-";
	String FILE_LOGO_PATH = "file.logo.path";
	String RANGE_NOT_TADAY = "range.cannot.equalto.taday";
	String RANGE_ZERO_HOUR = "12";
	String RANGE_HOUR_AM = "am";
	String RANGE_HOUR_PM = "pm";
	String OUT_OF_DAY_RANGE = "out.of.day.range";
	String USERID_ALREADY_EXIST = "userid.already.exist";
	String ROLE_NAME_EXIST = "role_name.exist";
	String ROLE_ASSIGNED_TO_USER = "role.assignedto.other.user";
	String ROLE_NAME_EDIT_SUCCESSFULLY = "role.edited.successfully";

	// Notification & Ranking
	enum SEVERITY_TYPE {
		CRITICAL, NONCRITICAL
	};

	// String ACTION_NAME_POWERSTATUS = "PowerStatus";
	// String ACTION_NAME_TEMPRETURE = "Tempreture";
	// String ACTION_NAME_MODE = "Mode";
	// String ACTION_NAME_FANSPEED = "Fanspeed";
	// String ACTION_NAME_WINDDIRECTION = "Winddirection";
	//
	// String INDOORUNITSLOG_COLUMN_POWERSTATUS = "powerstatus";
	// String INDOORUNITSLOG_COLUMN_SETPOINTTEMPRETURE = "setpointtemperature";
	// String INDOORUNITSLOG_COLUMN_ACMODE = "acmode";
	// String INDOORUNITSLOG_COLUMN_FANSPEED = "fanspeed";
	// String INDOORUNITSLOG_COLUMN_FLAPMODE = "flapmode";
	//
	// String RC_PROHIBITION_COLUMN_ISON = "ison";
	// String RC_PROHIBITION_COLUMN_TEMP = "rcprohibitsettemp";
	// String RC_PROHIBITION_COLUMN_MODE = "rcprohibitmode";
	// String RC_PROHIBITION_COLUMN_FANSPEED = "rcprohibitfanspeed";
	// String RC_PROHIBITION_COLUMN_FLAPMODE = "rcprohibitvanepos";

	// outdoor types
	String ODU_TYPE_GHP = "GHP";
	String ODU_TYPE_VRF = "VRF";
	String ODU_TYPE_PAC = "PAC";

	String SESSION_INFO_OBJECT_NAME = "sessionInfo";
	String LAST_SELECTED_GROUP_ATTRIB_NAME = "lastSelectedGroup";

	String STATISTICS_ACCUMULATED = "accumulated";
	String STATISTICS_CHRONOLOGICAL = "chronological";
	String STATISTICS_REFRIGERANT = "refrigerantcircuit";
	String EFFICIENCY = "efficiency";
	String CAPACITY = "capacity";
	String POWER_CONSUMPTION = "power_consumption";
	String DIFF_TEMPERATURE = "difftemperature";
	String WORKING_HOURS = "workinghours";
	String POWER_CUNSUMPTION_TABLE = "power_consumption_capacity_";
	String POWER_CUNSUMPTION_MAIN_TABLE = "power_consumption_capacity";
	String INDOORUNITSTATISTICS_TABLE = "indoorunitstatistics_";
	String INDOORUNITSTATISTICS_MAIN_TABLE = "indoorunitstatistics";
	String OUTDOORUNITSTATISTICS_TABLE = "outdoorunitstatistics_";
	String OUTDOORUNITSTATISTICS_MAIN_TABLE = "outdoorunitstatistics";
	String YEARLY = "yearly";
	String MONTHLY = "monthly";
	String WEEKLY = "weekly";
	String DAILY = "daily";
	String EFFICIENCY_PARAM_OPTION_COP = "cop";
	String EFFICIENCY_PARAM_OPTION_EER = "seer";
	String CAPACITY_PARAM_OPTION_COOL = "cool";
	String CAPACITY_PARAM_OPTION_HEAT = "heat";
	String CAPACITY_HEATING = "heating";
	String CAPACITY_COOLING = "cooling";
	String WORKHOUR_PARAM_OPTION_ONOFF = "onoff";
	String WORKHOUR_PARAM_OPTION_ON = "on";
	String WORKHOUR_PARAM_OPTION_OFF = "off";

	String WORKHOUR_PARAM_LABEL_ONOFF = "ThermoStat OnOff ";
	String WORKHOUR_PARAM_LEBAL_ON = "ThermoStat On ";
	String WORKHOUR_PARAM_LABEL_OFF = "ThermoStat Off ";

	String WORKHOUR_PARAM_LEBAL_ON_ACCUMULATED = "-ThermoStat On";
	String WORKHOUR_PARAM_LABEL_OFF_ACCUMULATED = "-ThermoStat Off";

	String KEY_IDS = "ids";
	String KEY_YEAR = "year";
	String KEY_START_RANGE = "startRange";
	String KEY_END_RANGE = "endRange";
	String KEY_START_RESIDUAL_DATE = "startResidualDate";
	String KEY_END_RESIDUAL_DATE = "endResidualDate";
	String KEY_LABEL = "label";

	String KEY_SUPPLY_ID = "id";

	String KEY_USER_ID = "userId";
	String KEY_TODAY = "today";

	String KEY_YEAR1 = "year1";
	String KEY_START_RANGE1 = "startRange1";
	String KEY_END_RANGE1 = "endRange1";
	String KEY_START_RESIDUAL_DATE1 = "startResidualDate1";
	String KEY_END_RESIDUAL_DATE1 = "endResidualDate1";

	String KEY_YEAR2 = "year2";
	String KEY_START_RANGE2 = "startRange2";
	String KEY_END_RANGE2 = "endRange2";
	String KEY_START_RESIDUAL_DATE2 = "startResidualDate2";
	String KEY_END_RESIDUAL_DATE2 = "endResidualDate2";
	String YYYY_MM_DD = "yyyy-MM-dd";
	String DD_MM_YYYY = "dd-MM-yyyy";
	String YYYY_MM_DD_HH_MM_A = "yyyy-MM-dd hh:mm a";
	String CONCATE_TYPE_HYPHEN = "-";
	String CONCATE_TYPE_SLASH = "/";
	//Added By Ravi
	String CONCATE_TYPE_COLON = ":"; 
	String X_AXIS_MAP_KEY = "X_AXIS_MAP_KEY";
	String X_AXIS_MAP_NUMBER_KEY = "X_AXIS_MAP_NUMBER_KEY";
	String CONCATE_TYPE_SPACE = " ";
	String KEY_JSON_RESPONSE_DATA = "data";
	String KEY_JSON_RESPONSE_NAME = "name";

	String PARAMETER_SQL_HOURLY = "HOURLY";
	String PARAMETER_SQL_RANGE = "RANGE";

	String STATS_API_CALL_BY_GROUP = "byGroup";
	String STATS_API_CALL_BY_AIRCON = "byAircon";
	String STATS_API_CALL_BY_ENERY_CONSUMPTION = "byAirconEnergyConsumption";
	String STATS_API_CALL_BY_REFRIGERANT_CIRCUIT = "byRefrigerantCircuit";
	String STATS_API_CALL_FOR_DOWNLOAD = "forDownload";

	String CHRON_TYPE_YEAR = "year";
	String CHRON_TYPE_MONTH = "month";
	String CHRON_TYPE_WEEK = "week";
	String CHRON_TYPE_DAY = "logtime";

	String SET_NOTIFICATION_SETTING = "setNotificationSetting";
	String UPDATE_ADVANCE_NOTIFICATION_SETTING = "updateAdvanceNotificationSetting";
	String GET_ADVANCE_NOTIFICATION_SETTING = "getAdvanceNotificationSetting";
	String UPDATE_MASTER_NOTIFICATION_SETTING = "updateMasterNotificationSetting";
	String GET_MASTER_NOTIFICATION_SETTING = "getMasterNotificationSetting";
	String MASSIVE = "massive";
	String SAVE_CO2FACTOR = "saveCO2Factor";

	String[] PARAMSFORVRF = { "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8",
			"V9", "V10", "V11", "V12", "V13", "V14", "V15", "V16", "V17",
			"V18", "V19", "V20", "V21", "V22", "V23", "V24", "V25", "V26",
			"V27", "V28", "V29", "V30", "V31", "V32", "V33", "V34" };

	String[] PARAMSFORGHP = { "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8",
			"G9", "G10", "G11", "G12", "G13", "G14", "G15", "G16", "G17",
			"G18", "G19", "G20", "G21", "G22", "G23", "G24", "G25", "G26",
			"G27", "G28", "G29", "G30", "G31", "G32", "G33", "G34", "G35",
			"G36", "G37", "G38", "G39", "G40", "G41", "G42", "G43", "G44",
			"G45", "G46", "G47", "G48", "G49", "G50", "G51", "G52", "G53" };

	String RC_DEVICE_OPEN_EXCEPTION = "RC.DEVICE.OPEN.EXCEPTION";
	String OPERTAION_TYPE_AVAILABLE_EXCEPTION = "OPERTAION.TYPE.AVAILABLE.EXCEPTION";
	String GROUP_ID = "groupId";
	String IS_MASTER = "isMaster";
	String on = "on";
	String off = "off";
	int SELECT_BATCH_SIZE = 50;
	String SERIES_EFFICIENCY = "Efficiency";
	String COOL_RATING = "COOL_RATING";
	String HEAT_RATING = "HEAT_RATING";
	Double EFFICIENCY_TREE_COUNT = 0.002485434;

	String NOTIFICATION_MESSAGE_CUSTOMER = "NotificationMessageCustomer";
	String NOTIFICATION_MESSAGE_MAINTENANCE = "NotificationMessageMaintenance";
	String MAINTENANCE = "maintenance";
	String CUSTOMER = "customer";
	String ALL = "all";
	String HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";

	enum OutdoorMaintTypeRemaining {
		vrf_comp_1_operation_hours_remaining(1), vrf_comp_2_operation_hours_remaining(
				2), vrf_comp_3_operation_hours_remaining(3), pac_comp_operation_hours_remaining(
				4), ghp_engine_operating_time_remaining(5), ghp_oil_change_hours_remaining(
				6);
		private int value;

		private OutdoorMaintTypeRemaining(final int value) {
			this.value = value;
		}

		private static Map<Integer, OutdoorMaintTypeRemaining> map = new HashMap<Integer, OutdoorMaintTypeRemaining>();
		static {
			for (OutdoorMaintTypeRemaining outdoorMaintTypeEnum : OutdoorMaintTypeRemaining
					.values()) {
				map.put(outdoorMaintTypeEnum.value, outdoorMaintTypeEnum);
			}
		}

		public static OutdoorMaintTypeRemaining valueOf(int value) {
			OutdoorMaintTypeRemaining outdoorMaintType = map.get(value);
			return outdoorMaintType;
		}
	}

	enum OutdoorMaintType {
		vrf_comp_1_operation_hours(1), vrf_comp_2_operation_hours(2), vrf_comp_3_operation_hours(
				3), pac_comp_operation_hours(4), ghp_engine_operating_time(5), ghp_time_after_oil_change(
				6);
		public int value;

		private OutdoorMaintType(final int value) {
			this.value = value;
		}

	}

	/*
	 * Constants for action column of Role History table
     * @ Modifed by Seshu
	 */
	String ROLEHISTORY_ACTION_EDIT = "Edit Role";
	String ROLEHISTORY_ACTION_DELETE = "Delete Role";
	String ROLEHISTORY_ACTION_CREATE = "Create Role";
    String ROLEEDIT = "Role Edited succesfully";
	String USER_ID = "userId";
	String USER_IDS = "userIds";

	String COMPANY_ID = "companyId";
	char STRING_JOIN_WITH = ',';
	String GROUP_IDS1 = "groupIds1";
	String GROUP_LEVEL1 = "groupLevel1";
	String GROUP_IDS = "groupIds";
	String GROUP_LEVEL = "groupLevel";

	String TYPE_NAME = "typename";
	String NOTIFICATION_TYPE_ID = "notificationTypeId";
	String NOTIFICATION_SETTING_ID = "notificationSettingId";
	String NOTIFICATION_TYPE_IDS = "notificationTypeIds";
	String START_DATE = "startDate";
	String END_DATE = "endDate";
	String DEVICE_NOT_REACHABLE = "device.not.reachable";
	String A2 = "A2";
	String A1 = "A1";

	String ONE = "1";
	String ZERO = "0";
	String IDS = "ids";
	String FACL_ID = "faclId";
	String PROPERTY_ID = "propertyId";
	String HRS = "hrs";
	String HRS0 = "0hrs";
	String PERCENT = "%";
	String WH = "Wh";

	String REPORT_HEADER_TITLE = "Title";
	String REPORT_HEADER_GENERATED_AT = "Generated at";
	String REPORT_HEADER_DATE_RANGE = "Date range";
	String REPORT_HEADER_LEVEL_SELECTION = "Level selection";

	String REPORT_COLUMN_DATA_START_DATE_TIME = "Data Start Date and Time";
	String REPORT_COLUMN_DATA_DURATION = "Data Duration (hours)";
	String REPORT_COLUMN_DATA_DATE_TIME = "data-date-time";
	String REPORT_COLUMN_CUSTOMER_NAME = "Customer Name";
	String REPORT_COLUMN_ENTITY_NAME = "Group/Site/Circuit/Unit Name";
	String REPORT_COLUMN_POWER_CONSUMPTION = "power-consumption (kWh)";
	//Start of Modification by Ravi
	String REPORT_COLUMN_RATED_CAPACITY = "rated-capacity (kW)";
	String REPORT_COLUMN_CURRENT_CAPACITY = "current-capacity (kW)";
	//End of Modification by Ravi
	String REPORT_COLUMN_AVERAGE_OUTDOOR_TEMP = "average-outdoor-temperature ("
			+ "\u00b0" + "C)";
	String REPORT_COLUMN_EFFICIENCY = "efficiency (%)";
	String REPORT_COLUMN_ROOM_TEMP = "room-temp (" + "\u00b0" + "C)";
	String REPORT_COLUMN_SET_TEMP = "set-temp (" + "\u00b0" + "C)";
	String REPORT_COLUMN_DIFF_TEMP = "diff-temp (" + "\u00b0" + "C)";
	//Modified By Ravi
	String REPORT_COLUMN_COMPRESSOR1 = "compressor 1 (hours)";
	//commented by ravi AS GHP not supported by April release
	//String REPORT_COLUMN_COMPRESSOR4 = "engine (hours)";
	String REPORT_COLUMN_COMPRESSOR2 = "compressor 2 (hours)";
	String REPORT_COLUMN_COMPRESSOR3 = "compressor 3 (hours)";
	String REPORT_COLUMN_THERMO_OFF_LOW = "thermo-off-fan-low (hours)";
	String REPORT_COLUMN_THERMO_OFF_MEDIUM = "thermo-off-fan-medium (hours)";
	String REPORT_COLUMN_THERMO_OFF_HIGH = "thermo-off-fan-high (hours)";
	String REPORT_COLUMN_THERMO_ON_LOW = "thermo-on-fan-low (hours)";
	String REPORT_COLUMN_THERMO_ON_MEDIUM = "thermo-on-fan-medium (hours)";
	String REPORT_COLUMN_THERMO_ON_HIGH = "thermo-on-fan-high (hours)";

	// Lock account
	Integer STATUS_UNLOCK = 0;
	Integer STATUS_LOCK = 1;

	// Valid user
	Integer STATUS_VALID = 1;
	Integer STATUS_INVALID = 0;

	// default password length

	int passwordLength = 15;

	// USer Id format

	String userId = "0000000";

	//Modified by seshu.
	String USER_MANAGEMENT_HISTORY_PASSWORD_RESET = "Passwordreset";

	String zero = "0";

	// Password generation string.

	String passwordString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	String update = "update";

	String json_string = "No Record(s) Found !";

	String userRegistration = "User Registration Failed Due to Some Validation Error";

	String updateUser = "User Updation Failed Due to Some Validation Error";

	// Excel For NoticationOverview
	String REPORT_HEADER_CATEGORY_LEVEL = "Category level";
	String REPORT_HEADER_ALARM_CODE = "Alarm Code";

	String API_CHART_EFFICIENCY_RANKING = "efficiencyranking";
	//Modified by seshu.
	String USER_GROUP_NOT_AVAILABLE = "Sorry. You have no privilege to see such information. ";

	String EMPTY_REQUEST = "empty.request";

	String VALID = "Valid";
	String INVALID = "Invalid";

	String PARENT = "parent";
	String CHILD = "child";
	String RCLOG_PAGINATION = "rclog.pagination.rowcount";

	String ROLE_ADDED = "role.added.successfully";

	String STATUS_NEW = "new";
	String STATUS_FIXED = "Fixed";

	String PREALARM_PRE01 = "pre01";
	String PREALARM_PRE02 = "pre02";
	String PREALARM_PRE03 = "pre03";

	String UPDATED_BY_APPLICATION = "Application";

	String SOME_ERROR_OCCURRED = "some.error.occurred";

	String ACCESS_FORBIDDEN = "permission.forbidden";

	String CACHED_SESSION_MAP_NAME = "cachedSessionMap";
	
	//Added by Ravi
	String FIRST_LOGIN_SUCCESS = "firstLogin.success";
}
