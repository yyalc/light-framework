package com.zol.smartframework.helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zol.smartframework.bean.FileParam;
import com.zol.smartframework.bean.FormParam;
import com.zol.smartframework.bean.Param;
import com.zol.smartframework.util.CollectionUtil;
import com.zol.smartframework.util.FileUtil;
import com.zol.smartframework.util.StreamUtil;
import com.zol.smartframework.util.StringUtil;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * 文件上传助手类
 */
public class UploadHelper {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UploadHelper.class);
	
	private static ServletFileUpload servletFileUpload;
	
	/**
	 * 初始化
	 * @param servletContext
	 */
	public static void init(ServletContext servletContext){
		//servlet缓存目录，服务器实现，因服务器不同而不同
		File repository=(File) servletContext.getAttribute("javax.servlet.context.tmpdir");
	    servletFileUpload=new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
	    int appUploadLimit = ConfigHelper.getAppUploadLimit();
	    if(appUploadLimit!=0){
	    	servletFileUpload.setFileSizeMax(appUploadLimit*1024*1024);
	    }
	}
	/**
	 * 判断请求是否为mulitpart类型
	 * @param request
	 * @return
	 */
	public static boolean isMultipart(HttpServletRequest request){
		return ServletFileUpload.isMultipartContent(request);
	}
	/**
	 * 创建请求对象
	 * @param request
	 * @return
	 */
	public static Param createParam(HttpServletRequest request)throws IOException{
		List<FormParam> formParamList=new ArrayList<>();
		List<FileParam> fileParamList=new ArrayList<>();
		try {
			Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
		    if(CollectionUtil.isNotEmpty(fileItemListMap)){
		    	for(Entry<String, List<FileItem>> fileItemListEntry:fileItemListMap.entrySet()){
		    		String fieldName=fileItemListEntry.getKey();
		    		List<FileItem> fileItemList=fileItemListEntry.getValue();
		    		if(CollectionUtil.isNotEmpty(fileItemList)){
		    			for(FileItem fileItem:fileItemList){
		    				//判断是否为普通表单对象
		    				if(fileItem.isFormField()){
		    					String fieldValue=fileItem.getString("UTF-8");
		    					formParamList.add(new FormParam(fieldName, fieldValue));
		    				}else{
		    					String fileName=FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),"UTF-8"));
		    					if(StringUtil.isNotEmpty(fileName)){
		    						long fileSize=fileItem.getSize();
		    						String contentType=fileItem.getContentType();
		    						InputStream is=fileItem.getInputStream();
		    						fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, is));
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		} catch (FileUploadException e) {
			LOGGER.error("create param error",e);
            throw new RuntimeException(e);
		}
		return new Param(formParamList, fileParamList);
	}
	/**
	 * 上传文件
	 * @param basePath
	 * @param fileParam
	 */
	public static void uploadFile(String basePath,FileParam fileParam){
		try {
			if(null!=fileParam){
				String filePath=basePath+fileParam.getFileName();
				FileUtil.createFile(filePath);
				InputStream is=fileParam.getInputStream();
				OutputStream os=new BufferedOutputStream(new FileOutputStream(filePath));
				StreamUtil.copyStream(is, os);
			}
		} catch (Exception e) {
			LOGGER.error("upload file error",e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 批量上传文件
	 * @param basePath
	 * @param fileParamList
	 */
	public static void  uploadFile(String basePath,List<FileParam> fileParamList){
		try {
			if(CollectionUtil.isNotEmpty(fileParamList)){
				for(FileParam fileParam:fileParamList){
					uploadFile(basePath, fileParam);
				}
			}
		} catch (Exception e) {
			LOGGER.error("upload file list error",e);
			throw new RuntimeException(e);
		}
	}

}
