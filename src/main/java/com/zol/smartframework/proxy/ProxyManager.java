package com.zol.smartframework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *创建时间：2017年7月1日
 *@author suzhihui
 *代理管理器
 */
public final class ProxyManager {
	
	@SuppressWarnings("unchecked")
	public static <T> T cteatProxy(final Class<T> targertClass,final List<Proxy> proxieList){
		return (T) Enhancer.create(targertClass, new MethodInterceptor(){

			@Override
			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {
				return new ProxyChain(targertClass, obj, method, proxy, args, proxieList).doProxyChain();
			}});
	}

}
