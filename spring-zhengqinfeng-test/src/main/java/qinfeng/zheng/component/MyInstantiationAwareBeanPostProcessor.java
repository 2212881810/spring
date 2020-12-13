package qinfeng.zheng.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/11 23:49
 * @dec
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
//		System.err.println(bean.getClass());
//		if (bean instanceof User) {
//			User user = (User) bean;
//			user.setAge(100);
//		}
//		System.err.println("InstantiationAwareBeanPostProcessor beanName :" + beanName);
		return true;
	}
}
