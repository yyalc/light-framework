package com.zol.smartframework.helper;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * servlet 助手类
 */
public final class ServletHelper {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ServletHelper.class);
    //每个线程独自拥有一份servlet_helper
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER=new ThreadLocal<>();
    
	private HttpServletRequest request;
	
	private HttpServletResponse response;

	public ServletHelper(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	/**
	 * 初始化
	 * @param request
	 * @param response
	 */
	public static void init(HttpServletRequest request,HttpServletResponse response){
		SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
	}
	/**
	 * 销毁
	 */
	public static void destory	(){
		SERVLET_HELPER_HOLDER.remove();
	}
	/**
	 * 获取request对象
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return SERVLET_HELPER_HOLDER.get().request;
	}
	/**
	 * 获取response对象
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		return SERVLET_HELPER_HOLDER.get().response;
	}
	/**
	 * 获取session对象
	 * @return
	 */
	public static HttpSession getSession(){
		return getRequest().getSession();
	}
	/**
	 * 获取ServletContext对象
	 * @return
	 */
	public static ServletContext getServletContext(){
		return getRequest().getServletContext();
	}
	/**
	 * 将属性放入request中	
	 * @param key
	 * @param value
	 */
	public static void setRequestAttribute(String key,Object value){
		getRequest().setAttribute(key, value);
	}
	/**
	 * 从request中获取属性
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRequestAttribute(String key){
		return (T) getRequest().getAttribute(key);
	}
	/**
	 * 从request中移除属性
	 * @param key
	 */
	public static void removeRequestAttribute(String key){
		getRequest().removeAttribute(key);
	}
	/**
	 * 发送重定向响应
	 * @param location
	 */
	public static void sendRedirect(String location){
		try {
			getResponse().sendRedirect(location);
		} catch (IOException e) {
			LOGGER.error("send redirect error",e);
		}
	}
	/**
	 * 将属性放入session中	
	 * @param key
	 * @param value
	 */
	public static void sendSessionAttribute(String key,Object value){
		getSession().setAttribute(key, value);
	}
	/**
	 * 从session中获取属性
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(String key){
		return (T) getSession().getAttribute(key);
	}
	/**
	 * 从session移除属性
	 * @param key
	 */
	public static void removeSessionAttribute(String key){
		getSession().removeAttribute(key);
	}
	/**
	 * 使session失效
	 */
	public static void invalidSession(){
		getSession().invalidate();
	}
}
