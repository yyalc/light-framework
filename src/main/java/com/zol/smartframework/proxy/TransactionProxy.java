package com.zol.smartframework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zol.smartframework.annotation.Transaction;
import com.zol.smartframework.helper.DatabaseHelper;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * 事务代理
 */
public class TransactionProxy implements Proxy {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(TransactionProxy.class);

	//用于保证每个线程只开启一次事务
	private static final ThreadLocal<Boolean> FLAG_HOLDER=new ThreadLocal<Boolean>(){
       protected Boolean initialValue() {
    	   return false;
       };		
	};
	
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		Method targerMethod=proxyChain.getTargerMethod();
		boolean flag=FLAG_HOLDER.get();
		if(!flag&&targerMethod.isAnnotationPresent(Transaction.class)){
			FLAG_HOLDER.set(true);
			try {
				DatabaseHelper.beginTransaction();
				LOGGER.info("begin transaction");
				result=proxyChain.doProxyChain();
				DatabaseHelper.commitTransaction();
				LOGGER.info("commit transaction");
			} catch (Exception e) {
				DatabaseHelper.rollbackTransaction();
				LOGGER.info("rollback transaction");
				throw e;
			}finally{
				FLAG_HOLDER.remove();
			}
		}else{
			result=proxyChain.doProxyChain();
		}
		return result;
	}

}
