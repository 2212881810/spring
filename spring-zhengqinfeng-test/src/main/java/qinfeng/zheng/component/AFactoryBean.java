package qinfeng.zheng.component;

import org.springframework.beans.factory.FactoryBean;

/**
 * @Author ZhengQinfeng
 * @Date 2021/1/24 22:56
 * @dec
 */
public class AFactoryBean implements FactoryBean<A> {
	@Override
	public A getObject() throws Exception {
		return new A();
	}

	@Override
	public Class<?> getObjectType() {
		return A.class;
	}
}
