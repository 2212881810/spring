package qinfeng.zheng.proxy;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/10 21:07
 * @dec
 */
public class TestProxy {
	public static void main(String[] args) {
		//动态代理创建的class文件存储到本地
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D:\\learn\\sourceCode\\spring-framework\\");
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(A.class);
		enhancer.setCallback(new AMethodInterceptor());



		A a = (A) enhancer.create();
		a.a();

	}
}
