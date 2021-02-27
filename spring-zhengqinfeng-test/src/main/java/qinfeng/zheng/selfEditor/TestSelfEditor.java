package qinfeng.zheng.selfEditor;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/27 21:06
 * @dec
 */
public class TestSelfEditor {
	public static void main(String[] args) {

//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("selfEditor.xml");



		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext();
		ac.setAllowBeanDefinitionOverriding(false);
		ac.setAllowCircularReferences(false);
		ac.setConfigLocations("selfEditor.xml");
		ac.refresh();
		// 不能通过这种方式来注册propertyEditor ,因为spring在解析xml配置时会加载Customer,
		// 时就会用address属性,但是此时AddressPropertyEditorRegistrar这个还没有回载进去,所以没法解析,报错吧~
//		ac.getBeanFactory().addPropertyEditorRegistrar(new AddressPropertyEditorRegistrar());


		Customer customer = ac.getBean(Customer.class);
		System.out.println(customer);


	}
}
