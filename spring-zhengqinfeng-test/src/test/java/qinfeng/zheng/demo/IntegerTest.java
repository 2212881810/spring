package qinfeng.zheng.demo;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/11 22:16
 * @dec
 */
public class IntegerTest {
	public static void main(String[] args) {

		// 享元模式
		Integer a = Integer.valueOf(5);
		Integer b = 5; // 相当于是Integer.valueOf(5);
		System.out.println(a == b);


		int c = 10;
		long d = 10L;
		double e = 10d;
		System.out.println(c == d);  // true
		System.out.println(c == e);  // true
		System.out.println(d == e); // true


	}
}
