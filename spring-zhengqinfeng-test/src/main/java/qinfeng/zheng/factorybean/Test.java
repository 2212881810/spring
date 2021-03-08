package qinfeng.zheng.factorybean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 21:34
 * @dec
 */
public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("factoryBean.xml");


		UserFactoryBean userFactoryBean = (UserFactoryBean) context.getBean("&userFactoryBean");
		System.out.println(userFactoryBean);

		//竟然可以通过这个名字获取User对象
		User user = (User) context.getBean("userFactoryBean");
		System.out.println(user);

		user = context.getBean(User.class);
		System.out.println(user);
	}
}
