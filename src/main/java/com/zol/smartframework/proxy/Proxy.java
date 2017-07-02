package com.zol.smartframework.proxy;

/**
 *创建时间：2017年7月1日
 *@author suzhihui
 *代理接口
 */
public interface Proxy {
	/**
	 * 执行链式代理
	 * @param proxyChain
	 * @return
	 * @throws Throwable
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;

}
