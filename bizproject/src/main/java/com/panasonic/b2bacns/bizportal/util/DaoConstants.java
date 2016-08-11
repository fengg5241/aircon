package com.panasonic.b2bacns.bizportal.util;

import java.util.HashMap;
import java.util.Map;

public interface DaoConstants {

	String MAP_ODUID = "oduid";
	String MAP_GROUPID = "group_id";
	String MAP_SVGMAXLONGITUDE = "svg_max_longitude";
	String MAP_SVGMINLONGITUDE = "svg_min_longitude";
	String MAP_SVGMAXLATITUDE = "svg_max_latitude";
	String MAP_SVGMINLATITUDE = "svg_min_latitude";
	String MAP_REFGRNT_SVGMAXLONGITUDE1 = "refgrnt_svg_max_longitude1";
	String MAP_REFGRNT_SVGMINLONGITUDE1 = "refgrnt_svg_min_longitude1";
	String MAP_REFGRNT_SVGMAXLATITUDE1 = "refgrnt_svg_max_latitude1";
	String MAP_REFGRNT_SVGMINLATITUDE1 = "refgrnt_svg_min_latitude1";
	String MAP_REFGRNT_SVGMAXLONGITUDE2 = "refgrnt_svg_max_longitude2";
	String MAP_REFGRNT_SVGMINLONGITUDE2 = "refgrnt_svg_min_longitude2";
	String MAP_REFGRNT_SVGMAXLATITUDE2 = "refgrnt_svg_max_latitude2";
	String MAP_REFGRNT_SVGMINLATITUDE2 = "refgrnt_svg_min_latitude2";
	String MAP_REFGRNT_SVGMAXLONGITUDE3 = "refgrnt_svg_max_longitude3";
	String MAP_REFGRNT_SVGMINLONGITUDE3 = "refgrnt_svg_min_longitude3";
	String MAP_REFGRNT_SVGMAXLATITUDE3 = "refgrnt_svg_max_latitude3";
	String MAP_REFGRNT_SVGMINLATITUDE3 = "refgrnt_svg_min_latitude3";
	String MAP_SVG_ID1 = "svg_id1";
	String MAP_SVG_ID2 = "svg_id2";
	String MAP_SVG_ID3 = "svg_id3";

	String MAP_CENTRAL_ADDRESS = "centraladdress";
	String ID = "id";
	String MAP_CONTROL_GROUP = "controlgroup";
	String MAP_SITE_GROUP = "sitegroup";
	String MAP_PARENT_CHILD = "pc";
	String MAP_NAME = "name";
	String MAP_POWER_STATUS = "powerstatus";
	// String MAP_MODE_NAME = "modename";
	String MAP_ISECONAVI = "iseconavi";
	String MAP_PARENT_ID = "parent_id";
	String MAP_STATUS = "status";
	String MAP_IDU_SVG_PATH = "idusvgpath";
	String MAP_ODU_SVG_PATH = "odusvgpath";
	String MAP_CA_SVG_PATH = "casvgpath";
	String ROOM_TEMP = "roomtemp";

	String OUTDOORUNIT_ID = "outdoorunit_id";
	String ODU_NAME = "oduname";

	String INDOORUNIT_ID = "iduId";
	String GROUPS_ID = "groupIdlist";

	String ODUPARAM_OUTDOORUNIT_ID = "outdoorid";
	String ODUPARAM_PROPERTY_ID = "property_id";
	String ODUPARAM_MEASURE_VALUE = "measure_val";
	String ODUPARAM_DISPLAY_NAME = "display_name";
	String ODUPARAM_MODELNAME = "modelname";
	String ODUPARAM_DIMENSION = "dimension";
	String ODUPARAM_UTILIZATION_RATE = "utilizationrate";
	String ODUPARAM_WORKING_HOUR = "workinghour";

	String ODU_ID = "id";
	String ODU_PARAMS_IDTYPE = "idType";
	String MAP_PROHIBITION_STATUS = "prohibitionstatus";
	String MAP_PROHIBITION_MODE = "prohibitionmode";
	String MAP_PROHIBITION_TEMP = "prohibitiontemp";
	String MAP_PROHIBITION_SPEED = "prohibitionspeed";
	String MAP_PROHIBITION_DIRECTION = "prohibitiondirection";
	String MAP_PROHIBITION_ENERGY = "prohibitionenergy";
	String MAP_ENERGY = "energy";
	String MAP_ALARM_CODE = "alarmcode";
	String MAP_A2_1 = "a2_1";
	String MAP_A2_2 = "a2_2";
	String MAP_A3 = "a3";
	String MAP_A3A = "a3a";
	String MAP_A3H = "a3h";
	String MAP_A3C = "a3c";
	String MAP_A3D = "a3d";
	String MAP_A6_1 = "a6_1";
	String MAP_A6A_1 = "a6a_1";
	String MAP_A6H_1 = "a6h_1";
	String MAP_A6C_1 = "a6c_1";
	String MAP_A6D_1 = "a6d_1";
	String MAP_A6F_1 = "a6f_1";
	String MAP_A6_2 = "a6_2";
	String MAP_A6A_2 = "a6a_2";
	String MAP_A6H_2 = "a6h_2";
	String MAP_A6C_2 = "a6c_2";
	String MAP_A6D_2 = "a6d_2";
	String MAP_A6F_2 = "a6f_2";
	String MAP_A7_1 = "a7_1";
	String MAP_A7A_1 = "a7a_1";
	String MAP_A7H_1 = "a7h_1";
	String MAP_A7C_1 = "a7c_1";
	String MAP_A7D_1 = "a7d_1";
	String MAP_A7F_1 = "a7f_1";
	String MAP_A7_2 = "a7_2";
	String MAP_A7A_2 = "a7a_2";
	String MAP_A7H_2 = "a7h_2";
	String MAP_A7C_2 = "a7c_2";
	String MAP_A7D_2 = "a7d_2";
	String MAP_A7F_2 = "a7f_2";
	String MAP_TYPE = "type";
	String MAP_SLINKADD = "slinkaddress";

	String MAP_C2 = "c2";
	String MAP_G44 = "g44";
	String MAP_SVGID = "svgid";
	String MAP_SEVERITY = "severity";
	String MAP_SVGNAME = "svgname";
	String MAP_LINKEDSVGID = "linkedsvgid";
	String MAP_RCFLAG = "rcflag";
	String MAP_VRFHRS1 = "vrfhrs1";
	String MAP_VRFHRS2 = "vrfhrs2";
	String MAP_VRFHRS3 = "vrfhrs3";
	String MAP_PACHRS = "pachrs";
	String MAP_GHPOIL = "ghpoil";
	String MAP_GHPHRS = "ghphrs";
	String CURRENT_TIME = "currenttime";
	String MAP_ALARM_TYPE = "alarm_type";
	String MAP_CUSTOMER_DESC = "customer_description";
	String MAP_MAINT_DESC = "maintenance_description";
	String MAP_COUNTER_MEASURE_CUSTOMER = "countermeasurecustomer";
	String MAP_COUNTER_MEASURE_MAINT_2WAY = "countermeasure2way";
	String MAP_COUNTER_MEASURE_MAINT_3WAY = "countermeasure3way";
	String MAP_SYSTEM = "system";
	String MAP_FIXEDTIME = "fixed_time";
	String MAP_SITEPATH = "sitepath";
	String MAP_COMPANYNAME = "companyname";
	String MAP_CASTATUS = "castatus";
	String MAP_A17A = "a17a";
	String MAP_CA_ID = "ca_id";

	String MAP_THRESHOLD_1 = "threshold_1";
	String MAP_THRESHOLD_2 = "threshold_2";
	String MAP_THRESHOLD_3 = "threshold_3";
	String MAP_THRESHOLD_4 = "threshold_4";
	String MAP_THRESHOLD_5 = "threshold_5";
	String MAP_THRESHOLD_6 = "threshold_6";
	String MAP_V23 = "v23";
	String MAP_COMPRESSOR1 = "compressor1";
	String MAP_COMPRESSOR2 = "compressor2";
	String MAP_COMPRESSOR3 = "compressor3";
	
	String DEVICE_MODEL = "device_model";

	enum PowerStatus {
		OFF(0), ON(1);
		private int value;

		private PowerStatus(final int value) {
			this.value = value;
		}

		private static Map<Integer, PowerStatus> map = new HashMap<Integer, PowerStatus>();
		static {
			for (PowerStatus powerEnum : PowerStatus.values()) {
				map.put(powerEnum.value, powerEnum);
			}
		}

		public static PowerStatus valueOf(int value) {
			PowerStatus powerStatus = map.get(value);
			if (powerStatus == null)
				return PowerStatus.OFF;
			return powerStatus;
		}
	}

	enum FanSpeed {
		Inactive(0), Stop(1), Auto(2), HH(3), H(4), L(5), LL(6), Unset(-1);
		private int value;

		private FanSpeed(final int value) {
			this.value = value;
		}

		private static Map<Integer, FanSpeed> map = new HashMap<Integer, FanSpeed>();
		static {
			for (FanSpeed fanSpeedEnum : FanSpeed.values()) {
				map.put(fanSpeedEnum.value, fanSpeedEnum);
			}
		}

		public static FanSpeed valueOf(int value) {
			FanSpeed fanSpeed = map.get(value);
			if (fanSpeed == null)
				return FanSpeed.Unset;
			return fanSpeed;
		}
	}

	enum FlapMode {
		Undecided(0), Swing(1), F1(2), F2(3), F3(4), F4(5), F5(6), Stop(7);
		private int value;

		private FlapMode(final int value) {
			this.value = value;
		}

		private static Map<Integer, FlapMode> map = new HashMap<Integer, FlapMode>();
		static {
			for (FlapMode flapModeEnum : FlapMode.values()) {
				map.put(flapModeEnum.value, flapModeEnum);
			}
		}

		public static FlapMode valueOf(int value) {
			FlapMode flapMode = map.get(value);
			if (flapMode == null)
				return FlapMode.Undecided;
			return flapMode;
		}
	}

	enum EcoNavi {
		False(0), True(1);
		private int value;

		private EcoNavi(final int value) {
			this.value = value;
		}

		private static Map<Integer, EcoNavi> map = new HashMap<Integer, EcoNavi>();
		static {
			for (EcoNavi ecoNaviEnum : EcoNavi.values()) {
				map.put(ecoNaviEnum.value, ecoNaviEnum);
			}
		}

		public static EcoNavi valueOf(int value) {
			EcoNavi ecoNavi = map.get(value);
			if (ecoNavi == null)
				return EcoNavi.False;
			return ecoNavi;
		}
	}

	enum Prohibited {
		notProhibited(0), prohibited(1);
		private int value;

		private Prohibited(final int value) {
			this.value = value;
		}

		private static Map<Integer, Prohibited> map = new HashMap<Integer, Prohibited>();
		static {
			for (Prohibited prohibitedEnum : Prohibited.values()) {
				map.put(prohibitedEnum.value, prohibitedEnum);
			}
		}

		public static Prohibited valueOf(int value) {
			Prohibited prohibited = map.get(value);
			if (prohibited == null)
				return Prohibited.notProhibited;
			return prohibited;
		}
	}

	enum Mode {
		Undecided(0), Heating(1), Cooling(2), Fan(3), Dry(4), Auto_Heating(5), Auto_Cooling(
				6), Auto_Undecided(7);
		private int value;

		private Mode(final int value) {
			this.value = value;
		}

		private static Map<Integer, Mode> map = new HashMap<Integer, Mode>();
		static {
			for (Mode modeEnum : Mode.values()) {
				map.put(modeEnum.value, modeEnum);
			}
		}

		public static Mode valueOf(int value) {
			Mode mode = map.get(value);
			if (mode == null)
				return Mode.Undecided;
			return mode;
		}
	}

}
