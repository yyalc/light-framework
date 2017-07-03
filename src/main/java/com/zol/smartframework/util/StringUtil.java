package com.zol.smartframework.util;

import org.apache.commons.lang3.StringUtils;

/**  
 * 创建时间：2017年6月29日   
 * @author suzhihui  
 */
public final class StringUtil {
	//字符串分隔符 分组符
	public static final String SEPERATOR = String.valueOf((char)29);

	//判断字符串是否为空
	public static boolean isEmpty(String str){
		if(null!=str){
			str=str.trim();
		}
		return StringUtils.isEmpty(str);
	}
	
	//判断字符串是否非空
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}

	/**
	 * 分割字符串
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String[] splitString(String str,String separator){
		return StringUtils.splitByWholeSeparator(str, separator);
	}
	
}
