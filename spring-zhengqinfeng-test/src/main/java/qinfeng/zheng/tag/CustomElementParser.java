package qinfeng.zheng.tag;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.Assert;
import org.w3c.dom.Element;


/**
 * BeanDefinitionParser 自定义标签的解析
 */
public class CustomElementParser extends AbstractSingleBeanDefinitionParser
{
    private static final String ID_ATTRIBUTE = "id";
    
    private static final String POINITCUT_ATTRIBUTE = "pointcut";
    
    @Override
    protected Class<?> getBeanClass(Element element)
    {
        return TestJavaBean.class;
    }
    
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder)
    {
        String id = element.getAttribute(ID_ATTRIBUTE);
        String poinitcut = null;
        Assert.notNull(id);
        builder.addPropertyValue(ID_ATTRIBUTE, id);
        
        if (element.hasAttribute(POINITCUT_ATTRIBUTE))
        {
            poinitcut = element.getAttribute(POINITCUT_ATTRIBUTE);
            builder.addPropertyValue(POINITCUT_ATTRIBUTE, poinitcut);
        }
    }
}
