package qinfeng.zheng.anno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author ZhengQinfeng
 * @Date 2021/2/28 21:34
 * @dec
 */
@ComponentScan("qinfeng.zheng.anno")
@Configuration
public class Config {


	@Bean
	public XXXXUser xxxxUser() {
		return new XXXXUser();
	}

	@Bean
	public YYYYUser yyyyUser() {
		XXXXUser xxxxUser = xxxxUser();
		System.out.println("xxxxUser:"+ xxxxUser);
		return new YYYYUser();
	}

}
