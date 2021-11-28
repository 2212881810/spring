package org.springframework.zheng;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/10 21:34
 * @dec
 */
public class TestProxy {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(A.class);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
				return methodProxy.invokeSuper(o, objects);
			}
		});


/*
死循环
		enhancer.setCallback(new InvocationHandler() {
			@Override
			public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
				return method.invoke(o, objects);
			}
		});*/
		A o = (A) enhancer.create();
		o.say();
	}
}
