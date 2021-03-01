package qinfeng.zheng.selfbdrpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 16:37
 * @dec
 */
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("执行postProcessBeanDefinitionRegistry---》 MyBeanDefinitionRegistryPostProcessor");
		// 注册beanDefinition
/*		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(Haha.class);
		builder.addPropertyValue("name", "admin");
		AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
		registry.registerBeanDefinition("haha", beanDefinition);*/

		// 注册1个bdrpp
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(MyMyBeanDefinitionRegistryPostProcessor.class);
		AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
		registry.registerBeanDefinition("bdrpp", beanDefinition);


	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("执行postProcessBeanFactory");

	}
}
