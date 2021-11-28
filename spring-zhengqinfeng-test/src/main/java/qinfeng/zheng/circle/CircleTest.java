package qinfeng.zheng.circle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import qinfeng.zheng.bean.A;
import qinfeng.zheng.tx.xml.service.BookService;

import java.sql.SQLException;

public class CircleTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("circle.xml");
		A a = context.getBean(A.class);
		System.out.println(a.getB());
	}
}
