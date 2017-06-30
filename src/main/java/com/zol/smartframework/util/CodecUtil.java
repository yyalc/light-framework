package com.zol.smartframework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 编码解码工具类
 */
public final class CodecUtil {
	private static final Logger LOGGER=LoggerFactory.getLogger(CodecUtil.class);
	
	/**
	 * 进行URL编码
	 * @param Url
	 * @return
	 */
	public static String encodeURL(String Url){
		String target;
		try {
			target=URLEncoder.encode(Url,"utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("encode url failure",e);
			throw new RuntimeException(e);
		}
		return target;
	}
	/**
	 * 进行URL解码
	 * @param Url
	 * @return
	 */
	public static String decodeURL(String Url){
		String target;
		try {
			target=URLDecoder.decode(Url,"utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("decode url failure",e);
			throw new RuntimeException(e);
		}
		return target;
	}
	

}
