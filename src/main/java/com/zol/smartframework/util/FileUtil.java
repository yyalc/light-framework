package com.zol.smartframework.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * 文件操作工具类
 */
public final class FileUtil {
   
	private static final Logger LOGGER=LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 获取真实文件名（自动去掉文件路径）
	 * @param fileName
	 * @return
	 */
	public static String getRealFileName(String fileName){
		return FilenameUtils.getName(fileName);
	}
	
	/**
	 * 创建文件
	 */
	public static File createFile(String filePath){
		File file = null;
		try {
			file=new File(filePath);
			File parentFile=file.getParentFile();
			if(!parentFile.exists()){
				FileUtils.forceMkdir(parentFile);
			}
		} catch (Exception e) {
			LOGGER.error("create file error",e);
			throw new RuntimeException(e);
		}
		return file;
	}
}
