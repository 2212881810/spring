package qinfeng.zheng.selfbdrpp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 16:42
 * @dec
 */
public class Test {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext();
		ac.setAllowBeanDefinitionOverriding(false);
		ac.setAllowCircularReferences(false);
		ac.setConfigLocations("selfbdrpp.xml");

		ac.refresh();
	}
}
