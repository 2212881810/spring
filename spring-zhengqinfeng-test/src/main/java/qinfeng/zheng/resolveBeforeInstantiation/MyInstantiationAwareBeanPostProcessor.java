package qinfeng.zheng.resolveBeforeInstantiation;

import org.junit.Before;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;

import java.beans.PropertyDescriptor;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/4 22:37
 * @dec  自定义的bpp不会进入该方法的if条件内部 {@link AbstractAutowireCapableBeanFactory#resolveBeforeInstantiation(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition)}
 *
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		System.out.println("beanName:" + beanName + "----实例化之前的操作====>postProcessBeforeInstantiation");

		if (beanClass == BeforeInstantiation.class) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(beanClass);
			enhancer.setCallback(new MyMethodInterceptor());
			BeforeInstantiation beforeInstantiation = (BeforeInstantiation) enhancer.create();
			System.out.println("创建代理对象成功："+beforeInstantiation);
			return beforeInstantiation;
		}


		return null;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		System.out.println("beanName:" + beanName + "----实例化之后的操作====>postProcessAfterInstantiation");

		return true;
	}

	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		System.out.println("beanName:" + beanName + "----执行操作====>postProcessProperties");
		return null;
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanName:" + beanName + "----初始化之前的操作====>postProcessBeforeInitialization");

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("beanName:" + beanName + "----初始化之后的操作====>postProcessAfterInitialization");

		return bean;
	}
}
