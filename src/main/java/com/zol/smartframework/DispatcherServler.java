package com.zol.smartframework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zol.smartframework.bean.Data;
import com.zol.smartframework.bean.Handler;
import com.zol.smartframework.bean.Param;
import com.zol.smartframework.bean.View;
import com.zol.smartframework.helper.BeanHelper;
import com.zol.smartframework.helper.ConfigHelper;
import com.zol.smartframework.helper.ControllerHelper;
import com.zol.smartframework.util.ArrayUtil;
import com.zol.smartframework.util.CodecUtil;
import com.zol.smartframework.util.JsonUtil;
import com.zol.smartframework.util.ReflectionUtil;
import com.zol.smartframework.util.StreamUtil;
import com.zol.smartframework.util.StringUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 请求转发器
 * 框架最核心的类，用于分发请求
 */
@WebServlet(urlPatterns="/*",loadOnStartup=0)
public class DispatcherServler extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		//初始化相关helper类
		HelperLoader.init();
		//获取servlercontext对象
		ServletContext context=getServletContext();
		//注册处理jsp的servlet
		ServletRegistration jspServlet = context.getServletRegistration("jsp");
	    jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
	    //注册处理静态资源的默认servlet
	    ServletRegistration defaultServlet = context.getServletRegistration("default");
	    defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        //获取请求方法与请求路径
        String requestMethod=req.getMethod();     
        String requestPath=req.getPathInfo();
        //获取Handler
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
	    if(handler!=null){
	    	//获取controller类以及bean实例
	    	Class<?> controllerClass=handler.getControllerClass();
	    	Object controllerBean=BeanHelper.getBean(controllerClass);
	    	//创建请求参数对象
	    	Map<String, Object> paramMap=new HashMap<String, Object>();
	    	Enumeration<String> parameterNames = req.getParameterNames();
	    	while(parameterNames.hasMoreElements()){
	    		String paramName=parameterNames.nextElement();
	    		String paramValue=req.getParameter(paramName);
	    		paramMap.put(paramName, paramValue);
	    	}
	    	//获取body中的参数
	    	String body=CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
	        if(StringUtil.isNotEmpty(body)){
	        	String[] contents = StringUtil.splitString(body, "&");
	        	if(ArrayUtil.isNotEmpty(contents)){
	        		for(String item:contents){
	        			String[] content = StringUtil.splitString(item, "=");
	        			if(ArrayUtil.isNotEmpty(content)&&content.length==2){
	        				String key=content[0];
	        				String value=content[1];
	        				paramMap.put(key, value);
	        			}
	        		}
	        	}
	        }
	        Param param=new Param(paramMap);
	        Method actionMehod=handler.getActionMethod();
	        //调用action方法
	        Object result=ReflectionUtil.invokeMethod(controllerBean, actionMehod, param);
	        //处理action返回值
	        if(result instanceof View){
	        	//返回jsp页面
	        	View view=(View) result;
	        	String path=view.getPath();
	        	if(StringUtil.isNotEmpty(path)){
	        		// /表示重定向
	        		if(path.startsWith("/")){
	        			resp.sendRedirect(req.getContextPath()+path);
	        		}else{
	        			Map<String, Object> model = view.getModel();
	        			for(Entry<String, Object> entry:model.entrySet()){
	        				req.setAttribute(entry.getKey(), entry.getValue());
	        			}
	        			req.getRequestDispatcher(ConfigHelper.getAppJspPath()+"/"+path).forward(req, resp);
	        		}
	        	}
	        }else{
	            //返回json数据
	        	Data data=(Data) result;
	        	Object model=data.getModel();
	        	if(model!=null){
	        		resp.setContentType("application/json");
	        		resp.setCharacterEncoding("utf-8");
	        		PrintWriter pw=resp.getWriter();
	        		String json=JsonUtil.toJson(model);
	        		pw.write(json);
	        		pw.flush();
	        		pw.close();
	        	}
	        	
	        }
	    }
	}

}
