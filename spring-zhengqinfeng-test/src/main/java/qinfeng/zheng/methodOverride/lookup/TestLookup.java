package qinfeng.zheng.methodOverride.lookup;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/3 23:10
 * @dec spring中默认的对象都是单例的，spring会在一级缓存中保存该对象，方便获取
 * 如果是原型对象 ，会创建 一个新对象
 * 那么如果想在一个单例bean下引用一个原型bean ,该怎么办？
 * 在此时就需要命名用lookup-method标签
 */
public class TestLookup {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("lookup.xml");

		FruitPlate fruitPlate1 = (FruitPlate) context.getBean("fruitPlate1");
		fruitPlate1.getFruit();

//
//		FruitPlate fruitPlate2 = (FruitPlate) context.getBean("fruitPlate2");
//		System.out.println(fruitPlate2.getFruit());
	}
}
