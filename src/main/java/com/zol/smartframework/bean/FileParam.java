package com.zol.smartframework.bean;

import java.io.InputStream;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * 封装文件上传参数
 */
public class FileParam {
	//文件表单字段名
	private String filedName;
	//上传文件名
	private String fileName;
	//文件大小
	private long fileSize;
	//文件类型
	private String contentType;
	//上传文件的字节输入流
	private InputStream inputStream;

	public FileParam(String filedName, String fileName, long fileSize,
			String contentType, InputStream inputStream) {
		this.filedName = filedName;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.inputStream = inputStream;
	}

	public String getFiledName() {
		return filedName;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public String getContentType() {
		return contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
}
