package com.zol.smartframework.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 创建时间：2017年6月29日   
 * @author suzhihui  
 * 类操作工具类
 */
public class ClassUtil {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ClassUtil.class);
	
	//获取类加载器
	public static ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
	
	//加载类
	public static Class<?> loadClass(String classname,boolean isInitialized){
		Class<?> cls;
		try {
			cls=Class.forName(classname,isInitialized,getClassLoader());
		} catch (ClassNotFoundException e) {
			LOGGER.error("load class failure",e);
			throw new RuntimeException(e);
		}
		return cls;
	}
    
	//获取指定包名下的所有类
	public static Class<Set<?>> getClassSet(String packagename){
		Set<Class<?>> classSet=new HashSet<>();
		try {
			Enumeration<URL> urls=getClassLoader().getResources(packagename.replace(".", "/"));
		    while(urls.hasMoreElements()){
		    	URL url=urls.nextElement();
		    	String protocol=url.getProtocol();
		    	if("file".equals(protocol)){
		    		String packagePath=url.getPath().replaceAll("%20", "");
		    		addClass(classSet, packagePath, packagename);
		    	}else if("jar".equals(protocol)){
		    		
		    	}
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static void addClass(Set<Class<?>> set,String packagePath,String packagename){
		
	}
	
	private static void doAddClass(Set<Class<?>> set,String classname){
		
	}
	
	public static void main(String[] args) {
		System.out.println("aabbccbbdd".replace("bb", "22"));
	}
}
