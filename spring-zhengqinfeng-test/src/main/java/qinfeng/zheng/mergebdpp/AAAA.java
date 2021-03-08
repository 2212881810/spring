package qinfeng.zheng.mergebdpp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author ZhengQinfeng
 * @Date 2021/3/6 10:10
 * @dec
 */
@Component
public class AAAA {

	public AAAA() {
		System.out.println("create AAAA...");
	}

	@PostConstruct
	private  void init() {
		System.out.println("init ...");
	}

	@PreDestroy
	private void destroy() {
		System.out.println("destroy...");
	}
}
