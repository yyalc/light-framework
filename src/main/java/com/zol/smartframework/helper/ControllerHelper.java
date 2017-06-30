package com.zol.smartframework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zol.smartframework.annotation.Action;
import com.zol.smartframework.bean.Handler;
import com.zol.smartframework.bean.Request;
import com.zol.smartframework.util.ArrayUtil;
import com.zol.smartframework.util.CollectionUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 控制器助手类
 */
public final class ControllerHelper {
    
	/**
	 * 用于存放请求和处理器的映射关系
	 */
	private static final Map<Request, Handler> ACTION_MAP=new HashMap<Request, Handler>();
    /**
     * 遍历所有controller
     * 获取所有action方法，根据其构建request和handler，并建立对应关系
     */
	static{
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
	    if(CollectionUtil.isNotEmpty(controllerClassSet)){
	    	for(Class<?> controllerCls:controllerClassSet){
	    		//获取所有方法
	    		Method[] declaredMethods = controllerCls.getDeclaredMethods();
	    	    if(ArrayUtil.isNotEmpty(declaredMethods)){
	    	    	for(Method method:declaredMethods){
	    	    		//获取有action的方法
	    	    		if(method.isAnnotationPresent(Action.class)){
	    	    			Action annotation = method.getAnnotation(Action.class);
	    	    		    //获取映射规则
	    	    			String value = annotation.value();
	    	    			//验证规则
	    	    			if(value.matches("\\w+:/\\w*")){
	    	    				String[] array= value.split(":");
	    	    				if(ArrayUtil.isNotEmpty(array)&&array.length==2){
	    	    					String requestMethod=array[0];
	    	    					String requestPath=array[1];
	    	    					Request request=new Request(requestMethod, requestPath);
	    	    				    Handler handler=new Handler(controllerCls, method);
	    	    				    ACTION_MAP.put(request,handler);
	    	    				}
	    	    			}
	    	    		}
	    	    	}
	    	    }
	    	}
	    }
	}
	
	/**
	 * 获取handler
	 * @param requestMethod
	 * @param requestPath
	 * @return
	 */
	public static Handler getHandler(String requestMethod,String requestPath){
		Request request=new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
