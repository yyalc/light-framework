package com.zol.smartframework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zol.smartframework.util.RefeletionUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * bean助手类
 */
public class BeanHelper {
	/**
	 * bean映射（用于存放bean类与bean实例的映射关系）
	 */
	private static final Map<Class<?>, Object> BEAN_MAP=new HashMap<>();
	
	static{
		Set<Class<?>> beanClasseSet=ClassHelper.getBeanClassSet();
		for(Class<?> cls:beanClasseSet){
			Object object=RefeletionUtil.newInstance(cls);
			BEAN_MAP.put(cls, object);
		}
	}
	/**
	 * 获取bean映射
	 * @return
	 */
	public static Map<Class<?>, Object> getBeanMap(){
		return BEAN_MAP;
	}
	/**
	 * 获取bean实例
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls){
		if(!BEAN_MAP.containsKey(cls)){
			throw new RuntimeException("can not get bean by class :"+cls);
		}
		return (T) BEAN_MAP.get(cls);
	}

}
