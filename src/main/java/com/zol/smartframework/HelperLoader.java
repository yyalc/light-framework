package com.zol.smartframework;

import com.zol.smartframework.helper.AopHelper;
import com.zol.smartframework.helper.BeanHelper;
import com.zol.smartframework.helper.ClassHelper;
import com.zol.smartframework.helper.ControllerHelper;
import com.zol.smartframework.helper.IocHelper;
import com.zol.smartframework.util.ClassUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 加载相应的helper类,使加载更加集中
 */
public final class HelperLoader {
	
	public static void init(){
		Class<?>[] classList={
			ClassHelper.class,
			BeanHelper.class,
			IocHelper.class,
			ControllerHelper.class,
			AopHelper.class
		};
		for(Class<?> cls:classList){
			ClassUtil.loadClass(cls.getName());
		}
	}

}
