package qinfeng.zheng.supplier;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/5 21:08
 * @desc
 */
public class SpringTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("supplier.xml");
		User user = (User) context.getBean("user");
		System.out.println(user);

	}
}
