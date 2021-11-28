package qinfeng.zheng.autowired;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/6 18:07
 * @dec
 */
public class TestSpring {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		context.setConfigLocations("autowired.xml");
		context.refresh();

		AController bean = context.getBean(AController.class);
		System.out.println(bean);
		bean = context.getBean(AController.class);
		System.out.println(bean);
		bean.getaService();
		bean.getaService();
		bean.getaService();

	}
}
