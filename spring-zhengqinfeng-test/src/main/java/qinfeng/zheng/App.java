package qinfeng.zheng;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import qinfeng.zheng.component.User;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/06 22:26
 * @dec
 */
public class App {
	public static void main(String[] args) {
//		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
//		UserComponent userComponent = ac.getBean(UserComponent.class);
//		userComponent.test();
//		System.out.println("编译成功，开心");
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContent.xml");
		User user = ac.getBean(User.class);
		System.out.println(user);

	}
}
