package qinfeng.zheng.componentscan;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 17:24
 * @dec
 */
public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext();
		ac.setConfigLocations("selfscan.xml");
		ac.refresh();

		Man man = ac.getBean(Man.class);
		System.out.println(man);

	}
}
