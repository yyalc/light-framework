package com.zol.smartframework.util;

import org.apache.commons.lang3.ArrayUtils;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 数组工具类
 */
public final class ArrayUtil {
	
	/**
	 * 判断数组为空
	 * @param arr
	 * @return
	 */
	public static boolean isEmpty(Object[] arr){
		return ArrayUtils.isEmpty(arr);
	}
	/**
	 * 判断数组非空
	 * @param arr
	 * @return
	 */
	public static boolean isNotEmpty(Object[] arr){
		return !isEmpty(arr);
	}
	
}
