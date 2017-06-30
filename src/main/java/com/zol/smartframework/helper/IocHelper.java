package com.zol.smartframework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.zol.smartframework.annotation.Inject;
import com.zol.smartframework.util.ArrayUtil;
import com.zol.smartframework.util.CollectionUtil;
import com.zol.smartframework.util.ReflectionUtil;

/**  
 * 创建时间：2017年6月30日   
 * @author suzhihui  
 * 依赖注入助手类
 */
public final class IocHelper {
	
    static{
    	//获取所有bean类和bean实例之间的映射关系
    	Map<Class<?>, Object> beanMap=BeanHelper.getBeanMap();
    	if(CollectionUtil.isNotEmpty(beanMap)){
    		for(Entry<Class<?>, Object> entry:beanMap.entrySet()){
    			Class<?> beanClass=entry.getKey();
    			Object beanInatance=entry.getValue();
    			//获取bean的所有成员变量
    			Field[] declaredFields = beanClass.getDeclaredFields();
    			if(ArrayUtil.isNotEmpty(declaredFields)){
    				for(Field field:declaredFields){
    					if(field.isAnnotationPresent(Inject.class)){
    						Class<?> beanFielsClass = field.getType();
    						Object beanFieldInstance=beanMap.get(beanFielsClass);
    						if(beanFieldInstance!=null){
    							//反射设置值
    							ReflectionUtil.setField(beanInatance, field, beanFieldInstance);
    						}
    						field.getClass();
    					}
    				}
    			}
    		}
    	}
    }
}
