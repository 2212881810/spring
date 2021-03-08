package qinfeng.zheng.mergebdpp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/6 10:11
 * @dec
 */
public class TestSpring {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		context.setConfigLocations("mergebdpp.xml");
		context.refresh();

		AAAA bean = context.getBean(AAAA.class);

		context.registerShutdownHook();
	}
}
