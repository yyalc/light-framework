package com.zol.smartframework.util;

import org.apache.commons.lang3.StringUtils;

/**  
 * 创建时间：2017年6月29日   
 * @author suzhihui  
 */
public class StringUtil {
	
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

}
