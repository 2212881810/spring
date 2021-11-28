package qinfeng.zheng.tx.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import qinfeng.zheng.tx.annotation.config.TransactionConfig;
import qinfeng.zheng.tx.annotation.service.BookService;

public class TransactionTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TransactionConfig.class);
        applicationContext.refresh();
        BookService bean = applicationContext.getBean(BookService.class);
        bean.checkout("zhangsan",1);
    }
}
