package org.springframework.zheng;

import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

/**
 * @Author ZhengQinfeng
 * @Date 2021/4/27 20:38
 * @dec
 */
public class TestPlaceholder {
	public static void main(String[] args) {
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("(",")");


		String text = "foo=$(foo),bar=$(bar)";
		Properties props = new Properties();
		props.setProperty("foo", "bar");


		String s = helper.replacePlaceholders(text, props);
		System.out.println(s);

	}
}
