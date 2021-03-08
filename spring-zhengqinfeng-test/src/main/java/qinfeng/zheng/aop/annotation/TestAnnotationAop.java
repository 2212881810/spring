package qinfeng.zheng.aop.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import qinfeng.zheng.aop.annotation.config.SpringConfiguration;
import qinfeng.zheng.aop.annotation.service.MyCalculator;

public class TestAnnotationAop {

    public static void main(String[] args) throws NoSuchMethodException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(SpringConfiguration.class);
        ac.refresh();
        MyCalculator bean = ac.getBean(MyCalculator.class);
        System.out.println(bean.add(1, 1));
    }
}
