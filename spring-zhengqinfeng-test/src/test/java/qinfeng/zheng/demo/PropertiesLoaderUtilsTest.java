package qinfeng.zheng.demo;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author ZhengQinfeng
 * @Date 2020/12/13 17:09
 * @dec
 */
public class PropertiesLoaderUtilsTest {
	public static void main(String[] args) throws IOException {
		Properties properties = PropertiesLoaderUtils.loadAllProperties("db.properties");

		System.out.println(properties);
	}
}
