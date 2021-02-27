package qinfeng.zheng.tag;

public class TestJavaBean
{
    private String id;
    
    private String pointcut;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getPointcut()
    {
        return pointcut;
    }
    
    public void setPointcut(String pointcut)
    {
        this.pointcut = pointcut;
    }
    
    @Override
    public String toString()
    {
        return "TestJavaBean [id=" + id + ", pointcut=" + pointcut + "]";
    }
    
}
