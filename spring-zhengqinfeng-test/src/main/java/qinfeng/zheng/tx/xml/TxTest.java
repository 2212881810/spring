package qinfeng.zheng.tx.xml;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import qinfeng.zheng.tx.xml.service.BookService;

import java.sql.SQLException;

public class TxTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("tx.xml");
        BookService bookService = context.getBean("bookService", BookService.class);
        bookService.checkout("zhangsan",1);
    }
}
