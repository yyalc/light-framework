package com.zol.smartframework.helper;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zol.smartframework.ConfigConstant;
import com.zol.smartframework.util.PropsUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 */
public final class ConfigHelper {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ConfigHelper.class);
	
	private static final Properties CONFIG_PROPERTIES=PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
	/**
	 * 获取JDBC驱动
	 * @return
	 */
	public static String getJdbcDriver(){
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_DRIVER);
	}
	/**
	 * 获取JDBC url
	 * @return
	 */
	public static String getJdbcUrl(){
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_URL);
	}
	/**
	 * 获取JDBC用户名
	 * @return
	 */
	public static String getJdbcUsername(){
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_USERNAME);
	}
	/**
	 * 获取JDBC密码
	 * @return
	 */
	public static String getJdbcPassword(){
		return PropsUtil.getString(CONFIG_PROPERTIES, ConfigConstant.JDBC_PASSWORED);
	}
	/**
	 * 获取应用基础包名
	 * @return
	 */
	public static String getAppBasePackage(){
		return PropsUtil.getString(CONFIG_PROPERTIES,ConfigConstant.APP_BASE_PACKAGE);
	}
	/**
	 * 获取应用静态资源
	 * @return
	 */
	public static String getAppAssetPath(){
		return PropsUtil.getString(CONFIG_PROPERTIES,ConfigConstant.APP_ASSETS_PATH,"/asset/");
	}
	/**
	 * 获取应用jsp路径
	 * @return
	 */
	public static String getAppJspPath(){
		return PropsUtil.getString(CONFIG_PROPERTIES,ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
	}
	/**
	 * 获取应用文件上传限制
	 * @return
	 */
	public static int getAppUploadLimit(){
		return PropsUtil.getInt(CONFIG_PROPERTIES,ConfigConstant.APP_UPLOAD_LIMIT,10);
	}
	/**
	 * 根据属性名获取string类型的属性值
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		return PropsUtil.getString(CONFIG_PROPERTIES, key);
	}
	/**
	 * 根据属性名获取int类型的属性值
	 * @param key
	 * @return
	 */
	public static int getInt(String key){
		return PropsUtil.getInt(CONFIG_PROPERTIES, key);
	}
	/**
	 * 根据属性名获取boolean类型的属性值
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		return PropsUtil.getBoolean(CONFIG_PROPERTIES, key);
	}
}
