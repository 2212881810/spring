package qinfeng.zheng.jdkProxy;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/12 21:59
 * @dec
 */
public class RealSubject implements Subject{
	@Override
	public void doSomething() {
		System.out.println("RealSubject doSomething...");
	}
}
