package qinfeng.zheng;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import qinfeng.zheng.component.User;

import java.util.Properties;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/06 22:26
 * @dec
 */
public class App {
	public static void main(String[] args) {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContent.xml");
//		AbstractApplicationContext ac = new MyClassPathXmlApplicationContext("applicationContent.xml");
//		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-${username}.xml");

		DruidDataSource dataSource = ac.getBean(DruidDataSource.class);
		System.out.println(dataSource.getUsername());


	}
}
