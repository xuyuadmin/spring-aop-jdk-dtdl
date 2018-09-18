package com.xuyu.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.xuyu.user.IUserDao;
import com.xuyu.user.impl.IUserDaoImpl;

//每次生成动态代理类对象时,实现了InvocationHandler接口的调用处理器对象 
public class InvocationHandlerImpl implements InvocationHandler {

	// 业务实现类对象，用来调用具体的业务方法
	private Object target;
	
	// 通过构造函数传入目标对象
	public InvocationHandlerImpl(Object target) {
		this.target=target;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result=null;
		System.out.println("调用开始处理");
		result=method.invoke(target, args);
		System.out.println("调用结束处理");
		return result;
	}

	public static void main(String[] args) {
		// 被代理对象
		IUserDao iUserDao=new IUserDaoImpl();
		//传入代理对象
		InvocationHandlerImpl invocationHandlerImpl=new InvocationHandlerImpl(iUserDao);
		//获得被代理类的类加载器
		ClassLoader classLoader = iUserDao.getClass().getClassLoader();
		//获取被代理类的接口
		Class<?>[] interfaces = iUserDao.getClass().getInterfaces();
		//获取代理类
		IUserDao newProxyInstance = (IUserDao) Proxy.newProxyInstance(classLoader, interfaces, invocationHandlerImpl);
		//代理类执行
		newProxyInstance.save();
	}
}
