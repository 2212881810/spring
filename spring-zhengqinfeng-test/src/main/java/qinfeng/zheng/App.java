package qinfeng.zheng;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import qinfeng.zheng.component.User;
import qinfeng.zheng.tag.TestJavaBean;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/06 22:26
 * @dec
 */
public class App {
	public static void main(String[] args) throws Exception {




		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext();
		ac.setAllowBeanDefinitionOverriding(false);
		ac.setAllowCircularReferences(false);
		ac.setConfigLocations("applicationContent.xml");
		ac.refresh();




//		A bean = ac.getBean(A.class);
//		System.out.println(bean.getB());

//		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-${username}.xml");

//		DruidDataSource dataSource = ac.getBean(DruidDataSource.class);
//		System.out.println(dataSource.getUsername());


//		A a = (A) ac.getBean("aFactory");  // 会默认调用getObject方法
//		System.out.println(a.getClass());
//
//		AFactoryBean aFactoryBean = (AFactoryBean) ac.getBean("&aFactory");
//		System.out.println(aFactoryBean.getClass());

	}
}
