package qinfeng.zheng.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/3 20:58
 * @dec
 */
public class UserFactoryBean implements FactoryBean<User> {
	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public User getObject() throws Exception {
		return new User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
