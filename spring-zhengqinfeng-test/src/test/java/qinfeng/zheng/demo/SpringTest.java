package qinfeng.zheng.demo;

import org.junit.Test;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.util.Map;
import java.util.Properties;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/13 11:06
 * @dec
 */
public class SpringTest {
	@Test
	public void test1() throws Exception {
		// 获取系统属性值
		Properties properties = System.getProperties();
		System.out.println(properties);

		// 创建一个标准的环境对象
		AbstractEnvironment environment = new StandardEnvironment();
		// 1. 获取系统环境变量
		Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
		System.out.println(systemEnvironment);

		// 2. 获取系统属性变量
		Map<String, Object> systemProperties = environment.getSystemProperties();
		System.out.println(systemProperties);
	}
}
