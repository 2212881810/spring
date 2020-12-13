package qinfeng.zheng.component;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/6 22:37
 * @dec
 */
public class User/* implements BeanNameAware, EnvironmentAware */ {

	private Integer age;
	private String userName;




	private Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//	private Integer age;
//	private String userName;
//	private String beanName;
//	private Environment environment;
//	public void test() {
//		System.out.println("this is a test!");
//	}
//
//
//	public Integer getAge() {
//		return age;
//	}
//
//	public void setAge(Integer age) {
//		this.age = age;
//	}
//
//	@Override
//	public void setBeanName(String name) {
//		this.beanName = name;
//	}
//
//	@Override
//	public void setEnvironment(Environment environment) {
//		this.environment = environment;
//	}
//
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public String getBeanName() {
//		return beanName;
//	}
//
//	public Environment getEnvironment() {
//		return environment;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
}
