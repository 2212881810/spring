package qinfeng.zheng.selfBfpp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 11:42
 * @dec
 */
public class BfppTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext();
		ac.setConfigLocations("bfpp.xml");
		// 通过代码加入
//		ac.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
		ac.refresh();
	}
}
