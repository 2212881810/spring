package qinfeng.zheng.jdkProxy;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/12 22:03
 * @dec
 */
public class TestJdkProxy {
	public static void main(String[] args) {
		// 保存生成的代理类的字节码文件
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

		// jdk动态代理测试
		Subject subject = new JDKDynamicProxy(new RealSubject()).getProxy();
		subject.doSomething();
	}
}
