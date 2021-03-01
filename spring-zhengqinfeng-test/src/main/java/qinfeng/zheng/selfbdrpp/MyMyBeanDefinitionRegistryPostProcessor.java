package qinfeng.zheng.selfbdrpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 16:49
 * @dec
 */
public class MyMyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
	public MyMyBeanDefinitionRegistryPostProcessor() {
		System.out.println("create MyMyBeanDefinitionRegistryPostProcessor....");
	}
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("执行postProcessBeanDefinitionRegistry----》MyMyBeanDefinitionRegistryPostProcessor ");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public int getOrder() {
		return 0;
	}
}
