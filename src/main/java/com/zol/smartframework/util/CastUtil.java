package com.zol.smartframework.util;

/**
 * 创建时间：2017年6月30日
 * 
 * @author suzhihui 类型转换工具类
 */
public final class CastUtil {

	// 转换为String类型,默认为""
	public static String castString(Object object) {
		return castString(object, "");
	}

	// 转换为String类型，提供默认值
	public static String castString(Object object, String defaultValue) {
		return object != null ? object.toString() : defaultValue;
	}

	// 转换为Double类型,默认为0
	public static double castDouble(Object object) {
		return castDouble(object, 0);
	}

	// 转换为Double类型，提供默认值
	public static double castDouble(Object object, double defaultValue) {
		double doubleValue = defaultValue;
		String strValue = castString(object);
		if (StringUtil.isNotEmpty(strValue)) {
			try {
				doubleValue = Double.parseDouble(strValue);
			} catch (NumberFormatException e) {
				doubleValue = defaultValue;
			}
		}
		return doubleValue;
	}

	// 转换为long类型,默认为0
	public static long castLong(Object object) {
		return castLong(object, 0);
	}

	// 转换为long类型，提供默认值
	public static long castLong(Object object, long defaultValue) {
		long longValue = defaultValue;
		String strValue = castString(object);
		if (StringUtil.isNotEmpty(strValue)) {
			try {
				longValue = Long.parseLong(strValue);
			} catch (NumberFormatException e) {
				longValue = defaultValue;
			}
		}
		return longValue;
	}
	
	// 转换为int类型,默认为0
	public static int castInt(Object object) {
		return castInt(object, 0);
	}
	
	// 转换为int类型，提供默认值
	public static int castInt(Object object, int defaultValue) {
		int intValue = defaultValue;
		String strValue = castString(object);
		if (StringUtil.isNotEmpty(strValue)) {
			try {
				intValue = Integer.parseInt(strValue);
			} catch (NumberFormatException e) {
				intValue = defaultValue;
			}
		}
		return intValue;
	}
	
	// 转换为boolean类型,默认为false
	public static boolean castBoolean(Object object) {
		return castBoolean(object, false);
	}
	
	// 转换为boolean类型，提供默认值
	public static boolean castBoolean(Object object, boolean defaultValue) {
		boolean booleanValue = defaultValue;
		if(object!=null){
			booleanValue=Boolean.parseBoolean(castString(object));
		}
		return booleanValue;
	}
	
}
