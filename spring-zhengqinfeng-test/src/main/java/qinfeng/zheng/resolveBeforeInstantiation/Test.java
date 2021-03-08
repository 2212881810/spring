package qinfeng.zheng.resolveBeforeInstantiation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/4 22:47
 * @dec
 */
public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beforeInstantiation.xml");
		BeforeInstantiation bean = context.getBean(BeforeInstantiation.class);
		bean.doSomeThing();
	}
}
