package qinfeng.zheng.anno;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 21:34
 * @dec
 */
public class Test {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
	}
}
