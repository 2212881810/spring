package qinfeng.zheng.selfAware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/27 2:15
 * @dec
 */
public class MyAware implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("myAware before....");
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("myAware after....");
		return null;
	}
}
