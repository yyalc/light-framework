package com.zol.smartframework.bean;
/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * 封装表单参数
 */
public class FormParam {
    private String fieldName;
    
    private Object fieldValue;

	public FormParam(String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getFieldname() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
    
}
