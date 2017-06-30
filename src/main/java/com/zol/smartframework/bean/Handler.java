package com.zol.smartframework.bean;

import java.lang.reflect.Method;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 封装action信息
 */
public class Handler {
    
	/**
	 * controller类
	 */
	private Class<?> controllerClass;
	
	/**
	 * action方法
	 */
	private Method actionMethod;
	
	public Handler(Class<?> controllerClass, Method actionMethod) {
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}

	public Method getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(Method actionMethod) {
		this.actionMethod = actionMethod;
	}
	
}
