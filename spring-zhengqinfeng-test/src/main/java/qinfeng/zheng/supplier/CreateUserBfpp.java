package qinfeng.zheng.supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/5 21:00
 * @dec
 */
public class CreateUserBfpp implements BeanFactoryPostProcessor {


	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition user = beanFactory.getBeanDefinition("user");
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) user;
		beanDefinition.setInstanceSupplier(CreateUser::createUser);
		beanDefinition.setBeanClass(User.class);
	}
}
