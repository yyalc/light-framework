package com.zol.smartframework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zol.smartframework.annotation.Aspect;
import com.zol.smartframework.annotation.Service;
import com.zol.smartframework.proxy.AspectProxy;
import com.zol.smartframework.proxy.Proxy;
import com.zol.smartframework.proxy.ProxyManager;
import com.zol.smartframework.proxy.TransactionProxy;

/**
 *创建时间：2017年7月1日
 *@author suzhihui
 *方法拦截助手类
 */
public final class AopHelper {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AopHelper.class);
	
	static{
		try {
			Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
			Map<Class<?>,List<Proxy>> targetMap=creatTargetMap(proxyMap);
			for(Entry<Class<?>,List<Proxy>> targetEntry:targetMap.entrySet()){
				Class<?> targetClass=targetEntry.getKey();
				List<Proxy> proxyList=targetEntry.getValue();
				Object proxy=ProxyManager.cteatProxy(targetClass, proxyList);
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			LOGGER.error("aop failure");
		}
		
	}
	
	private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
		Map<Class<?>, Set<Class<?>>> proxyMap=new HashMap<Class<?>, Set<Class<?>>>();
		addAspectProxy(proxyMap);
		addTRansactionProxy(proxyMap);
		return proxyMap;
	}
	
	private static void addTRansactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap)
			throws Exception {
		Set<Class<?>> proxyClassSet=ClassHelper.getClassSetByAnnotation(Service.class);
		proxyMap.put(TransactionProxy.class, proxyClassSet);
	}

	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap)
			throws Exception {
		Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);
		for(Class<?> cls:proxyClassSet){
			if(cls.isAnnotationPresent(Aspect.class)){
				Aspect aspect=cls.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet=creatTargetClassSet(aspect);
				proxyMap.put(cls, targetClassSet);
			}
		}
	}

	private static Set<Class<?>> creatTargetClassSet(Aspect aspect) throws Exception {
		Set<Class<?>> targetClassSet=new HashSet<Class<?>>();
		Class<? extends Annotation> annotation=aspect.value();
		if(null!=annotation&&!annotation.equals(Aspect.class)){
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}
	
	private static Map<Class<?>,List<Proxy>> creatTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
		Map<Class<?>,List<Proxy>> targetMap=new HashMap<Class<?>, List<Proxy>>();
		for(Entry<Class<?>, Set<Class<?>>> proxyEntry:proxyMap.entrySet()){
			Class<?> proxyClass=proxyEntry.getKey();
			Set<Class<?>> targetClassSet=proxyEntry.getValue();
			for(Class<?> cls:targetClassSet){
				Proxy proxy=(Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(cls)){
					targetMap.get(cls).add(proxy);
				}else{
					List<Proxy> proxyList=new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(cls, proxyList);
				}
			}
		}
		return targetMap;
	}

}
