package com.zol.smartframework.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zol.smartframework.bean.FormParam;
import com.zol.smartframework.bean.Param;
import com.zol.smartframework.util.ArrayUtil;
import com.zol.smartframework.util.CodecUtil;
import com.zol.smartframework.util.StreamUtil;
import com.zol.smartframework.util.StringUtil;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * 请求助手类
 */
public final class RequestHelper {
	/**
	 * 创建请求对象
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static Param createParam(HttpServletRequest request) throws IOException{
		List<FormParam> formParamList=new ArrayList<>();
		formParamList.addAll(parseParameterNames(request));
		formParamList.addAll(parseInputStream(request));
	    return new Param(formParamList);
	}
    //获取请求头中的参数
	private static List<FormParam> parseParameterNames(
			HttpServletRequest request) {
		List<FormParam> formList=new ArrayList<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String fieldName=parameterNames.nextElement();
			String[] fieldValues = request.getParameterValues(fieldName);
			if(ArrayUtil.isNotEmpty(fieldValues)){
				Object fieldValue;
				if(fieldValues.length==1){
					fieldValue=fieldValues[0];
				}else{
					StringBuilder sb=new StringBuilder();
					for(int i=0;i<fieldValues.length;i++){
						sb.append(fieldValues[i]);
						if(i!=fieldValues.length-1){
							sb.append(StringUtil.SEPERATOR);
						}
					}
					fieldValue=sb.toString();
				}
				formList.add(new FormParam(fieldName, fieldValue));
			}
		}
		return formList;
	}
    //获取请求内body中的参数
	private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException{
		List<FormParam> formParamList=new ArrayList<>();
		String body=CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
		if(StringUtil.isNotEmpty(body)){
			String[] kvs=StringUtil.splitString(body, "&");
			if(ArrayUtil.isNotEmpty(kvs)){
				for(String kv:kvs){
					String[] item=StringUtil.splitString(kv,"=");
					if(ArrayUtil.isNotEmpty(item)&&item.length==2){
						String fieldName=item[0];
						String fieldValue=item[1];
						formParamList.add(new FormParam(fieldName, fieldValue));
					}
				}
			}
		}
		return formParamList;
	}
}
