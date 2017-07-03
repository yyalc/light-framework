package com.zol.smartframework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zol.smartframework.util.CastUtil;
import com.zol.smartframework.util.CollectionUtil;
import com.zol.smartframework.util.StringUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 请求参数对象
 */
public class Param {
   
	private List<FormParam> formParamList;
	
	private List<FileParam> fileParamList;

	public Param(List<FormParam> formParamList) {
		super();
		this.formParamList = formParamList;
	}

	public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
		super();
		this.formParamList = formParamList;
		this.fileParamList = fileParamList;
	}
	
	/**
	 * 获取请求参数映射
	 * @return
	 */
	public Map<String, Object> getFieldMap(){
		Map<String, Object> fieldMap=new HashMap<String, Object>();
		if(CollectionUtil.isNotEmpty(formParamList)){
			for(FormParam formParam:formParamList){
				String fieldName=formParam.getFieldname();
				Object fieldValue=formParam.getFieldValue();
				if(fieldMap.containsKey(fieldName)){
					fieldValue=fieldMap.get(fieldName)+StringUtil.SEPERATOR+fieldValue;
				}
				fieldMap.put(formParam.getFieldname(), formParam.getFieldValue());
			}
		}
		return fieldMap;
	}
	
	/**
	 * 获取上传文件映射
	 * @return
	 */
	public Map<String,List<FileParam>> getFileMap(){
		Map<String, List<FileParam>> fileMap=new HashMap<>();
		if(CollectionUtil.isNotEmpty(fileParamList)){
			for(FileParam fileParam:fileParamList){
				String filedName=fileParam.getFiledName();
				List<FileParam> fileParamList;
				if(fileMap.containsKey(filedName)){
					fileParamList=fileMap.get(filedName);
				}else{
					fileParamList=new ArrayList<>();
				}
				fileParamList.add(fileParam);
				fileMap.put(filedName, fileParamList);
			}
		}
		return fileMap;
	}
	
	/**
	 * 获取所有上传文件
	 * @param fileName
	 * @return
	 */
	public List<FileParam> getFileList(String fileName){
		return getFileMap().get(fileName);
	}
	
	/**
	 * 获取唯一上传文件
	 * @param fileName
	 * @return
	 */
	public FileParam getFile(String fileName){
		List<FileParam> fileParamList=getFileList(fileName);
		if(CollectionUtil.isNotEmpty(fileParamList)){
			return fileParamList.get(0);
		}
		return null;
		
	}

	/**
	 * 根据参数名获取string类型参数值
	 * @param name
	 * @return
	 */
	public String getString(String name){
		return CastUtil.castString(getFieldMap().get(name));
	}
	/**
	 * 根据参数名获取double类型参数值
	 * @param name
	 * @return
	 */
	public double getDouble(String name){
		return CastUtil.castDouble(getFieldMap().get(name));
	}
	/**
	 * 根据参数名获取long类型参数值
	 * @param name
	 * @return
	 */
	public long getLong(String name){
		return CastUtil.castLong(getFieldMap().get(name));
	}
	/**
	 * 根据参数名获取int类型参数值
	 * @param name
	 * @return
	 */
	public int getInt(String name){
		return CastUtil.castInt(getFieldMap().get(name));
	}
	/**
	 * 根据参数名获取boolean类型参数值
	 * @param name
	 * @return
	 */
	public boolean getBoolean(String name){
		return CastUtil.castBoolean(getFieldMap().get(name));
	}
	
	/**
	 * 判断参数是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return CollectionUtil.isEmpty(formParamList)&&CollectionUtil.isEmpty(fileParamList);
	}
	
}
