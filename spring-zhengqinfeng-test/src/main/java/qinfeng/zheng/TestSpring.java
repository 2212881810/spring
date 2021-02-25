package qinfeng.zheng;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import qinfeng.zheng.extend.MyClassPathXmlApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/1/26 23:40
 * @dec
 */
public class TestSpring {


	/**
	 * org.springframework.context.support.AbstractApplicationContext#initPropertySources()扩展测试
	 * @throws Exception
	 */
	@Test
	public void testExtend01() throws Exception {
		AbstractApplicationContext ac =
				new MyClassPathXmlApplicationContext("applicationContent.xml");
	}
}
