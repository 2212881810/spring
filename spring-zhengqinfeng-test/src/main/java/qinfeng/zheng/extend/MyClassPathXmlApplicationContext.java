package qinfeng.zheng.extend;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/13 15:40
 * @dec extend01
 */
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

	public MyClassPathXmlApplicationContext(String... configLocations) {
		super(configLocations);
	}

	@Override
	protected void initPropertySources() {
		// os属性必须进行验证，spring容器在启动过程中发现这个属性为空，就会抛错
		getEnvironment().setRequiredProperties("os");


		// 在该方法中可以对环境属性或者是系统属性进行替换等操作
		Map<String, Object> map = getEnvironment().getSystemProperties();
		String o = (String) map.get("java.vm.info");
		System.out.println("java.vm.info:" + o);
		//修改默认的java.vm.info的属性值
		map.put("java.vm.info", "this is a test vm info");

		map = getEnvironment().getSystemProperties();
		o = (String) map.get("java.vm.info");
		System.out.println("java.vm.info-new###:" + o);

	}


	@Override
	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		// 关掉循环依赖
		super.setAllowCircularReferences(true);
		super.setAllowBeanDefinitionOverriding(true);
		super.customizeBeanFactory(beanFactory);
	}

	private class MyPropertySource extends PropertySource {
		public MyPropertySource(String name, Object value) {
			super(name, value);
		}

		@Override
		public Object getProperty(String name) {
			return "123";
		}
	}


}


