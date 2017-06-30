package com.zol.smartframework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 封装请求信息（请求方法，请求路径）
 */
public class Request {
	
	/**
	 * 请求方法
	 */
	private String requestMethod;
    
	/**
	 * 请求路径
	 */
	private String requestPath;

	public Request(String requestMethod, String requestPath) {
		this.requestMethod = requestMethod;
		this.requestPath = requestPath;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMthod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	
	
}
