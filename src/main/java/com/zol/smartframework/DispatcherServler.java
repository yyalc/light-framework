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
import com.zol.smartframework.helper.RequestHelper;
import com.zol.smartframework.helper.ServletHelper;
import com.zol.smartframework.helper.UploadHelper;
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
		
		registerServlet(context);
	    //文件上传初始化
	    UploadHelper.init(context);
	}

	private void registerServlet(ServletContext context) {
		//注册处理jsp的servlet
		ServletRegistration jspServlet = context.getServletRegistration("jsp");
	    jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
	    //注册处理静态资源的默认servlet
	    ServletRegistration defaultServlet = context.getServletRegistration("default");
	    defaultServlet.addMapping("/favicon.ico");
	    defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
       ServletHelper.init(req, resp);
		try {
			//获取请求方法与请求路径
			String requestMethod=req.getMethod().toLowerCase();     
			String requestPath=req.getPathInfo();
			//获取Handler
			Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
			if(handler!=null){
				//获取controller类以及bean实例
				Class<?> controllerClass=handler.getControllerClass();
				Object controllerBean=BeanHelper.getBean(controllerClass);
				
				Param param;
				if(UploadHelper.isMultipart(req)){
					param=UploadHelper.createParam(req);
				}else{
					param=RequestHelper.createParam(req);
				}
			    Method actionMehod=handler.getActionMethod();
			    Object result;
			    //调用action方法
			    if(param.isEmpty()){
			    	result=ReflectionUtil.invokeMethod(controllerBean, actionMehod);
			    }else{
			    	result=ReflectionUtil.invokeMethod(controllerBean, actionMehod, param);
			    }
			    //处理action返回值
			    if(result instanceof View){
			    	handlerViewResult(req, resp, (View)result);
			    }else{
			        handlerDaraResult(resp, (Data)result);
			    }
			}
		} finally {
		   ServletHelper.destory();
		}
	}

	private void handlerDaraResult(HttpServletResponse resp, Data data)
			throws IOException {
		//返回json数据
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

	private void handlerViewResult(HttpServletRequest req,
			HttpServletResponse resp, View view) throws IOException,
			ServletException {
		//返回jsp页面
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
				req.getRequestDispatcher(ConfigHelper.getAppJspPath()+"/"+path).forward(req,resp);
			}
		}
	}

}
