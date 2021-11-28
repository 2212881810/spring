package qinfeng.zheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author ZhengQinfeng
 * @Date 2021/4/17 16:51
 * @dec
 */
@Controller
public class HelloController {

	@GetMapping("/hello")
	public String index() {
		return "hello";
	}
}
