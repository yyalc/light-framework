package com.zol.smartframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建时间：2017年6月30日
 * 
 * @author suzhihui 反射工具类
 */
public class ReflectionUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReflectionUtil.class);
   /**
    *创建实例
    * @param cls
    * @return
    */
	public static Object newInstance(Class<?> cls) {
		Object instanc = null;
		try {
			instanc = cls.newInstance();
		} catch (Exception e) {
			LOGGER.error("new instance failure", e);
			throw new RuntimeException(e);
		}
		return instanc;
	}
	
	/**
	 * 调用方法
	 * @param obj 目标实例
	 * @param method 目标方法
	 * @param args  参数
	 * @return
	 */
	public static Object invokeMethod(Object obj,Method method,Object...args){
        Object result=null;
		try {
			result=method.invoke(obj, args);
		} catch (Exception e) {
			LOGGER.error("invoke method failure",e);
			throw new RuntimeException(e);
		}		
		return result;
	}
	/**
	 * 设置成员变量的值
	 * @param obj 目标实例
	 * @param field 目标字段
	 * @param value 目标值
	 */
	public static void setField(Object obj,Field field,Object value){
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			LOGGER.error("set field failure",e);
			throw new RuntimeException(e);
		}
	}
}
