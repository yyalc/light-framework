package com.zol.smartframework.bean;
/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 返回数据对象，一般为json,用于直接返回给浏览器
 */
public class Data {
	
	private Object model;

	public Data(Object model) {
		this.model = model;
	}

	public Object getModel() {
		return model;
	}
}
