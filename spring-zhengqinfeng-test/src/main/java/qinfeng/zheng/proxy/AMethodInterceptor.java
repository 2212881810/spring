package qinfeng.zheng.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/13 11:16
 * @dec
 */
public class AMethodInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("begin..");
		return methodProxy.invokeSuper(o, objects);
	}
}
