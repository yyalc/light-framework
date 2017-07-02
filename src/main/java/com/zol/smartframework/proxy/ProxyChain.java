package com.zol.smartframework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 *创建时间：2017年7月1日
 *@author suzhihui
 *代理链
 */
public class ProxyChain {
	
	private final Class<?> targertClass;
	
	private final Object targerObject;
	
	private final Method targerMethod;
	
	private final MethodProxy methodProxy;
	
	private final Object[] methodParams;
	
	private List<Proxy> proxyList=new ArrayList<Proxy>();
	
	private int proxyIndex=0;

	public ProxyChain(Class<?> targertClass, Object targerObject,
			Method targerMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		this.targertClass = targertClass;
		this.targerObject = targerObject;
		this.targerMethod = targerMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}

	public Class<?> getTargertClass() {
		return targertClass;
	}

	public Method getTargerMethod() {
		return targerMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}
	
	public Object doProxyChain() throws Throwable{
		Object result;
		if(proxyIndex<proxyList.size()){
			result=proxyList.get(proxyIndex++).doProxy(this);
		}else{
			result=methodProxy.invokeSuper(targerObject,methodParams);
		}
		return result;
	}

}
