package com.zol.smartframework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 创建时间：2017年6月29日   
 * @author suzhihui  
 * 属性文件工具类
 */
public final class PropsUtil {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(PropsUtil.class);
   
	//加载属性文件
	public static Properties loadProps(String filename){
		Properties props=null;
		InputStream is=null;
		if(StringUtil.isEmpty(filename)){
			LOGGER.error("load props failure params is empty");
			return null;
		}
		try {
			is=ClassUtil.getClassLoader().getResourceAsStream(filename);
			if(is==null){
				throw new FileNotFoundException(filename+" file is not found");
			}
			props=new Properties();
			props.load(is);
		} catch (Exception e) {
			LOGGER.error("load props file failure",e);
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error("load props close input stream failure",e);
				}
			}
		}
		return props;
	}
	//获取string类型的属性值，默认为空
	public static String getString(Properties props,String key){
		return getString(props, key, "");
	}
	//获取string类型的属性值
	public static String getString(Properties props,String key,String defaultvalue){
		 String value=defaultvalue;
		 if(props.containsKey(key)){
			 value=props.getProperty(key);
		 }
		 return value;
	}
	//获取int类型的属性值，默认为0
	public static int getInt(Properties props,String key){
		return getInt(props, key, 0);
	}
	//获取int类型的属性值
	public static int getInt(Properties props,String key,int defaultvalue){
		int value=defaultvalue;
		if(props.containsKey(key)){
			value=CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}
	//获取boolean类型的属性值，默认为false
	public static boolean getBoolean(Properties props,String key){
		return getBoolean(props, key, false);
	}
	//获取boolean类型的属性值
	public static boolean getBoolean(Properties props,String key,boolean defaultvalue){
		boolean value=defaultvalue;
		if(props.containsKey(key)){
			value=CastUtil.castBoolean(props.getProperty(key));
		}
		return value;
	}
	
	
}
