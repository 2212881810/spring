package qinfeng.zheng.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/6 18:06
 * @dec
 */
@Controller
public class AController {
	@Autowired
	private AService aService;

	public AService getaService() {
		System.out.println(this.aService);
		return aService;
	}
}
