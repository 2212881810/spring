package qinfeng.zheng.component;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/6 22:37
 * @dec
 */
public class User implements BeanNameAware, EnvironmentAware {

	private String userName;
	private String beanName;
	private Environment environment;
	public void test() {
		System.out.println("this is a test!");
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}


	public String getUserName() {
		return userName;
	}

	public String getBeanName() {
		return beanName;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
