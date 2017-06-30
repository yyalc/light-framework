package com.zol.smartframework.bean;

import java.util.Map;

import com.zol.smartframework.util.CastUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 请求参数对象
 */
public class Param {
   
	private Map<String,Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	/**
	 * 根据参数名获取long类型参数值
	 * @param name
	 * @return
	 */
	public long getLong(String name){
		return CastUtil.castLong(paramMap.get(name));
	}

	/**
	 * 获取所有字段信息
	 * @return
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}
	
}
