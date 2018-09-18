package com.xuyu.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.xuyu.user.IUserDao;
import com.xuyu.user.impl.IUserDaoImpl;

//ÿ�����ɶ�̬���������ʱ,ʵ����InvocationHandler�ӿڵĵ��ô��������� 
public class InvocationHandlerImpl implements InvocationHandler {

	// ҵ��ʵ��������������þ����ҵ�񷽷�
	private Object target;
	
	// ͨ�����캯������Ŀ�����
	public InvocationHandlerImpl(Object target) {
		this.target=target;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result=null;
		System.out.println("���ÿ�ʼ����");
		result=method.invoke(target, args);
		System.out.println("���ý�������");
		return result;
	}

	public static void main(String[] args) {
		// ���������
		IUserDao iUserDao=new IUserDaoImpl();
		//����������
		InvocationHandlerImpl invocationHandlerImpl=new InvocationHandlerImpl(iUserDao);
		//��ñ���������������
		ClassLoader classLoader = iUserDao.getClass().getClassLoader();
		//��ȡ��������Ľӿ�
		Class<?>[] interfaces = iUserDao.getClass().getInterfaces();
		//��ȡ������
		IUserDao newProxyInstance = (IUserDao) Proxy.newProxyInstance(classLoader, interfaces, invocationHandlerImpl);
		//������ִ��
		newProxyInstance.save();
	}
}
