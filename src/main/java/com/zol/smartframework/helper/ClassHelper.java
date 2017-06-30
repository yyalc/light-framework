package com.zol.smartframework.helper;

import java.util.HashSet;
import java.util.Set;

import com.zol.smartframework.annotation.Controller;
import com.zol.smartframework.annotation.Service;
import com.zol.smartframework.util.ClassUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 类操作助手
 */
public final class ClassHelper {
	
	private static final Set<Class<?>> CLASS_SET;
	
    static{
    	String basePackage=ConfigHelper.getAppBasePackage();
    	CLASS_SET=ClassUtil.getClassSet(basePackage);
    }
    
    //获取应用包名下的所有类
    public static Set<Class<?>> getClasseSet(){
    	return CLASS_SET;
    }
    
    //获取应用包名下的所有service类
    public static Set<Class<?>> getServiceClassSet(){
    	Set<Class<?>> classSet=new HashSet<>();
    	for(Class<?> cls:CLASS_SET){
    		if(cls.isAnnotationPresent(Service.class)){
    			classSet.add(cls);
    		}
    	}
    	return classSet;
    }
    
    //获取应用包名下的所有controller类
    public static Set<Class<?>> getControllerClassSet(){
    	Set<Class<?>> classSet=new HashSet<>();
    	for(Class<?> cls:CLASS_SET){
    		if(cls.isAnnotationPresent(Controller.class)){
    			classSet.add(cls);
    		}
    	}
    	return classSet;
    }
    
    //获取应用包名下的所有bean类(controller,service)
    public static Set<Class<?>> getBeanClassSet(){
    	Set<Class<?>> classSet=new HashSet<>();
    	classSet.addAll(getServiceClassSet());
    	classSet.addAll(getControllerClassSet());
    	return classSet;
    }
}
