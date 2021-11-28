package qinfeng.zheng.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/12 21:59
 * @dec
 */
public class JDKDynamicProxy implements InvocationHandler {
	Object target;

	public JDKDynamicProxy(Object target) {
		this.target = target;
	}

	public <T> T getProxy() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before...");
		return method.invoke(target, args);
	}
}
