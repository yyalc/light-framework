package com.zol.smartframework.util;


import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 集合工具类
 */
public final class CollectionUtil {
	
	/**
	 * 判断集合是否为空
	 * @param coll
	 * @return
	 */
	public static boolean isEmpty(Collection<?> coll){
		return CollectionUtils.isEmpty(coll);
	}
	/**
	 * 判断集合是否非空
	 * @param coll
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> coll){
		return !isEmpty(coll);
	}
	/**
	 * 判断map是否为空
	 * @param coll
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map){
		return MapUtils.isEmpty(map);
	}
	/**
	 * 判断map是否非空
	 * @param coll
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map){
		return !isEmpty(map);
	}

}
