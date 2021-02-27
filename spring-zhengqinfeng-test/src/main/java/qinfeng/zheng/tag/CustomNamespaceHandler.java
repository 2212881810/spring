package qinfeng.zheng.tag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 自定义标签解码入口
 */
public class CustomNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        System.out.println("Init CustomNamespaceHandler");
        
        registerBeanDefinitionParser("test", new CustomElementParser());
    }
}
