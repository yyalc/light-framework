package com.zol.smartframework;
/**  
 * 创建时间：2017年6月29日   
 * @author suzhihui  
 * 相关配置项常亮
 */
public interface ConfigConstant {
	//配置文件
	String CONFIG_FILE="smart.properties";
	
	String JDBC_DRIVER="smart.framework.jdbc.driver";
	
	String JDBC_URL="smart.framework.jdbc.url";
    
	String JDBC_USERNAME="smart.framework.jdbc.username";
	
   	String JDBC_PASSWORED="smart.framework.jdbc.password";
   	//基础包名
   	String APP_BASE_PACKAGE="smart.framework.app.base_package";
   	//jsp的基础路径
   	String APP_JSP_PATH="smart.framework.app.jsp_path";
   	//静态资源文件的基础路径 js/css/img...
   	String APP_ASSETS_PATH="smart.framework.app.assets_path";
    //文件上传限制
	String APP_UPLOAD_LIMIT = "smart.framework.app.upload_limit";
}
