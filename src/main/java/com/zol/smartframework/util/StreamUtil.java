package com.zol.smartframework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 流操作工具类
 */
public final class StreamUtil {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(StreamUtil.class);
	
	/**
	 * 	从输入流中获取字符串
	 * @param is
	 * @return
	 */
	public static String getString(InputStream is){
		StringBuilder sb=new StringBuilder();
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String line;
			while((line=br.readLine())!=null){
				sb.append(line);
			}
		} catch (Exception e) {
			LOGGER.error("get String failure",e);
			throw new RuntimeException(e);
		}
		return sb.toString();
	}
	/**
	 * 将输入流复制到输出流
	 * @param is
	 * @param os
	 */
	public static void copyStream(InputStream is,OutputStream os){
		try {
			int length;
			byte[] buffer=new byte[4*1024];
			while((length=is.read(buffer,0,buffer.length))!=-1){
				os.write(buffer);
			}
			os.flush();
		} catch (Exception e) {
			LOGGER.error("copy stream error",e);
			throw new RuntimeException(e);
		}finally{
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				LOGGER.error("close stream error",e);
			}
		}
		
	}
     
}
