package com.zol.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * json工具类
 */
public final class JsonUtil {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(JsonUtil.class);
   
	/**
	 * 对象转json
	 * @param model
	 * @return
	 */
	public static  String toJson(Object object) {
		JSONObject jsonObject=new JSONObject();
		return jsonObject.toJSONString(object);
	}
	/**
	 * json转对象
	 * @param model
	 * @return
	 */
	public static <T> T toJson(String json,Class<T> type) {
		return JSONObject.parseObject(json, type);
	}

}
