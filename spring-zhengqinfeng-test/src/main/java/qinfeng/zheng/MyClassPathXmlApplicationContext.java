package qinfeng.zheng;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/13 15:40
 * @dec
 */
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

	public MyClassPathXmlApplicationContext(String... configLocations) {
		super(configLocations);
	}

	@Override
	protected void initPropertySources() {
		// 设置了一个必须属性os, 如果spring在启动时，发现没有环境变量os,就会抛错
		getEnvironment().setRequiredProperties("os");
	}

	@Override
	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		super.setAllowCircularReferences(false);
		super.setAllowBeanDefinitionOverriding(false);
//		super.customizeBeanFactory(beanFactory);
	}
}
