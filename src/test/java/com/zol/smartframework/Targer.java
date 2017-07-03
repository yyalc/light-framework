package com.zol.smartframework;

import java.lang.reflect.Method;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 */
public class Targer {
	
	public int invokeTarget(int i){
		System.out.println("********method params i:"+i);
		System.out.println("***********target method invoked");
		return 100;
	}
	
	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "e:/bk");
		Targer targer=(Targer) Enhancer.create(Targer.class, new MethodInterceptor(){

			@Override
			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {
				System.out.println("====proxy start");
				Object result=proxy.invokeSuper(obj, args);
				System.out.println("====proxy end");
				return result;
			}
			
		});
		targer.invokeTarget(12);
		System.out.println(targer);
	}

}
