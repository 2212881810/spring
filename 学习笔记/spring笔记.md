### spring bean的生命周期

1. 实例化spring容器
2. 扫描符合spring bean规则的class，放到一个集合中
3. 遍历这个集合中的class，将其封装成一个一个的beandefinition, 然后放到beanDefinitionMap中
4. 遍历beanDefinitionMap
5. 解析 
6. 验证是否需要实例化
7. 推断出构造方法
8. 通过这个构造方法反射实例化一个对象 
9. 合并bd
10. 提前暴露工厂 ----循环依赖
11. 注入属性---判断是否需要属性填充
12. 执行部分Aware
13. 执行部分Aware----生命周期回调函数 anno
14. 生命周期回调函数
15. aop 事件
16. put 到单例池
17. 销毁