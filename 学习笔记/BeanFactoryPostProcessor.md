# BeanFactoryPostProcessor

> BeanFactoryPostProcessor(简称bfpp)是spring框架中非常重要的一个接口，它还有一个比较重要的子接口BeanDefinitionRegistryPostProcessor(简称bfrpp), 所以说bfrpp也是bfpp。 bfpp主要用于修改BeanDefinition信息， bfrpp主要用于class类的扫描。

### 1. bfpp的调用入口在哪儿？

对spring稍微有点了解的人都知道org.springframework.context.support.AbstractApplicationContext#refresh方法，spring就是在这个方法内部的调用了bfpp接口。

![image-20200807225610849](C:\Users\zhengqinfeng\AppData\Roaming\Typora\typora-user-images\image-20200807225610849.png)

### 2.invokeBeanFactoryPostProcessors方法分析

核心方法：org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(beanFactory, beanFactoryPostProcessors)



#### 2.1 先来一点说明

1. 在调用该方法之前，spring内置的实现了bfpp接口的class已经被抽象成BeanDefinition(简称bd)存放在了beanDefinitionMap集合中
2. 没有被抽象成bd的class类，不会存放在beanDefinitionMap中，也不会在此被调用
3.  程序员自定义的bfpp（bfrpp）会在本方法内部完成扫描 ，class信息抽象成beanDefinition,然后存放在beanDefinitionMap中。 自定义的bfpp又可以分为两种，一种是通过@Component注解+实现bfpp接口实现 ， 另一种是通过ConfigurableApplicationContext#addBeanFactoryPostProcessor这个api完成。

#### 2.2 方法入参说明

```java
public static void invokeBeanFactoryPostProcessors(
      ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {}
```

第1个参数： beanFactory  默认就是 DefaultListableBeanFactory.class , 实现了BeanDefinitionRegistry接口

第2个参数： 一般情况下都是空，除非在调用spring容器的refresh(）方法之前调用api手动添加bfpp。【后面会举个例子】

#### 2.3 代码分析说明

1. 首先就是一个Set类型的集合，该集合存放的是已经执行完毕的bfpp名称

   ```java
   Set<String> processedBeans = new HashSet<>();  // 存放处理完毕的bfpp名称
   ```

   

2. 接下来就是一个if ....else....

   ```java
   if (beanFactory instanceof BeanDefinitionRegistry) {  // 因为beanFactory实现了BeanDefinitionRegistry接口，所以进入if代码块
       // 存放直接实现BeanFactoryPostProcessor接口实现类的集合, bfpp的作用是可以定制化的修改bd
       List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
   
       // 存放直接实现BeanDefinitionRegistryPostProcessor接口实现类的集合, bfrpp可以定制化修改bd
       List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();
       
       
       // 除非手动注入bfpp,否则这个for循环是没有意义的，因为beanFactoryPostProcessors一般为空
       for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
           if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
               BeanDefinitionRegistryPostProcessor registryProcessor = (BeanDefinitionRegistryPostProcessor) postProcessor;
               // 如果postProcessor实现的是bfrpp接口，那么先执行bfrpp接口方法，然后再把这个 postProcessor 放到registryProcessors集合中
               registryProcessor.postProcessBeanDefinitionRegistry(registry);
               registryProcessors.add(registryProcessor);
           } else { // 如果postProcessor 实现的是bfpp,就添加到regularPostProcessors集合
             
               /**
   		    * 为什么实现bfpp接口的实现类，在这里不先执行，而是缓存起来?
   			* 答： 因为此时spring内置的bfpp（bfrpp）都还没有执行，即是说扫描都还没有做，很明显时机不会！！！
   			*/
               regularPostProcessors.add(postProcessor);
           }
       }
   } else{
       invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
   }
   ```

   阅读上面for循环中代码可知，如果postProcessor类实现了 BeanDefinitionRegistryPostProcessor接口，就直接调用了该接口的postProcessBeanDefinitionRegistry方法。 而如果postProcessor类不是实现的BeanDefinitionRegistryPostProcessor接口，就暂时先存放在regularPostProcessors集合中。

3. 下面又声明了一个currentRegistryProcessors集合，用于存放当前即将执行BeanDefinitionRegistryPostProcessor实现类

   ```java	
   // 存储当前需要执行的 BeanDefinitionRegistryPostProcessor 实现类对象，每次执行完之后清除，防止重复执行
   List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();
   ```

4. 下面是重点

   ```java
   /**
    * 第1次： 获取spring内置的实现 BeanDefinitionRegistryPostProcessor 接口的类
    */
   // 找出实现了BeanDefinitionRegistryPostProcessor接口的class类类名，至于如何找出来的，本篇免谈
   String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
   
   // 循环遍历，挨个进行判断,处理
   for (String ppName : postProcessorNames) {
       // 如果bfrpp的实现类同时实现了PriorityOrdered接口，那么优先处理
       if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
           // getBean方法会 通过类型获取对应的实例，并将之放到currentRegistryProcessors集合，以便于后面调用执行
           currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
           processedBeans.add(ppName);  // 将该类添加到processedBeans集合中，表明这个类我已经处理了，后面不再处理
       }
   }
   
   sortPostProcessors(currentRegistryProcessors, beanFactory);  // 即然实现了PriorityOrdered接口，那么就先排个序
   registryProcessors.addAll(currentRegistryProcessors);  // 因为是bfrpp接口实现类，所以也添加到registryProcessors集合中
   
   // 这个就调用bfrpp接口方法，实现具体的逻辑
   invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
   
   
   currentRegistryProcessors.clear(); // 这一批执行完毕，清空集合，留着后面用。
   ```

5. 第4步执行完毕之后，又看见差不多的代码 

   ```java
   /**
    * 第2次： 获取 BeanDefinitionRegistryPostProcessor 接口实现
    */
   // 还是找出实现了bfrpp接口的class类类名
   postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
   
   for (String ppName : postProcessorNames) {
       //注意： 判断条件不一样了 【排除第1次已经处理过的bfrpp接口实现类; 同时，这个类实现了Ordered接口】
       // 因为PriorityOrdered extends Ordered，所以真实情况下自定义bfrpp(注解注入)且同时实现PriorityOrdered接口的类，也是在这个if条件下执行的
       if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
           currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
           processedBeans.add(ppName);
       }
   }
   // 后与第1次【第4步】一样的
   sortPostProcessors(currentRegistryProcessors, beanFactory);
   registryProcessors.addAll(currentRegistryProcessors);
   invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
   currentRegistryProcessors.clear();
   ```

6. 最后再搞一次

   ```java
   // Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
   // 执行其它实现了bfrpp接口的class类
   boolean reiterate = true;
   while (reiterate) {
       reiterate = false;
       // 还是找出实现了bfrpp接口的class类类名
       /**
    	* 第3次： 获取 BeanDefinitionRegistryPostProcessor 接口实现
    	*/
       postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
       for (String ppName : postProcessorNames) {
           // 注意： 过滤条件  【只要前面两步没有处理过的bfrpp接口实现类，本次都给处理了】
           if (!processedBeans.contains(ppName)) {
               currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
               processedBeans.add(ppName);
               reiterate = true;
           }
       }
       // 同前面一样
       sortPostProcessors(currentRegistryProcessors, beanFactory);
       registryProcessors.addAll(currentRegistryProcessors);
       invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
       currentRegistryProcessors.clear();
   }
   ```

7. 最后才是执行bfpp接口方法

   ```java
   // 因为bfrpp也是bfpp的子接口，所以实现了bfrpp接口的类，可能也实现了bfpp接口方法， 这里就是bfrpp接口实现类调用bfpp接口方法
   invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
   // bfpp接口实现类 执行bfpp接口方法
   invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
   ```

8. 前面几步都是从beanFactory中获取bfrpp接口实现类，仍然没有获取bfpp接口实现类，即便是【第7步】调用的bfpp接口方法，其调用对象要么是从入参beanFactoryPostProcessors集合中过滤出来的bfpp接口实现 ，要么就是bfrpp接口实现类同时也覆写bfrpp接口方法。

   下面的代码才是专门处理bfpp接口实现类的逻辑，代码套路与前面基本一致

   ```java
   System.out.println("=====================以下是处理BeanFactoryPostProcessor的实现类=========================");
   /**
    * 第4次： 获取bfpp接口实现类
   */
   String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
   
   
   // 存放实现了PriorityOrdered的bfpp实现类
   List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
   // 存放实现了Ordered接口的bfpp接口实现类类名
   List<String> orderedPostProcessorNames = new ArrayList<>();
   // 排除实现了PriorityOrdered和Ordered接口的bfpp接口实现类
   List<String> nonOrderedPostProcessorNames = new ArrayList<>();
   
   for (String ppName : postProcessorNames) {
       if (processedBeans.contains(ppName)) {
           // 跳过之前阶段已经处理过的类
       } else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
           // 实现了PriorityOrdered接口的bfpp添加到priorityOrderedPostProcessors集合中
           priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
       } else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
           // 实现了Ordered接口的bfpp添加到orderedPostProcessorNames集合中
           orderedPostProcessorNames.add(ppName);
       } else {
           // 其它的bfpp接口实现类添加到nonOrderedPostProcessorNames集合中
           nonOrderedPostProcessorNames.add(ppName);
       }
   }
   
   // First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
   // 首先处理实现了PriorityOrdered接口的bfpp实现类
   sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
   invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);
   
   // Next, invoke the BeanFactoryPostProcessors that implement Ordered.
   // 其次，处理实现了Ordered接口的实现类
   List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>();
   for (String postProcessorName : orderedPostProcessorNames) {
       orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
   }
   sortPostProcessors(orderedPostProcessors, beanFactory);
   invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);
   
   // Finally, invoke all other BeanFactoryPostProcessors.
   // 最后，处理其它bfpp接口实现类
   List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>();
   for (String postProcessorName : nonOrderedPostProcessorNames) {
       nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
   }
   invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);
   
   // Clear cached merged bean definitions since the post-processors might have
   // modified the original metadata, e.g. replacing placeholders in values...
   // 清理
   beanFactory.clearMetadataCache();
   ```

   以上是spring框架调用bfpp接口的源码分析，下面自定义几个bfpp(bfrpp)接口实现类来测试它们执行顺序

   

### 3. 示例,测试bfpp调用顺序

下面我将自定义3个bfrpp接口实现类，和3个bfpp的接口实现类。

```java
@Component
public class MyBfrpp01 implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("MyBfrpp01....实现了bfrpp, 本方法是bfrpp接口方法实现");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBfrpp01....实现了bfrpp, 本方法是bfrpp父接口=======> bfpp接口方法实现");

	}
}
```



```java
@Component
public class MyBfrpp02 implements BeanDefinitionRegistryPostProcessor , PriorityOrdered {

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp接口方法实现");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现");

	}
}
```



```java
@Component
public class MyBfrpp03 implements BeanDefinitionRegistryPostProcessor , Ordered {

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp接口方法实现");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现");

	}
}
```



```java
@Component
public class MyBfpp01 implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBfpp01....实现了brpp, 本方法是bfpp接口方法实现");
	}
}

```



```java
@Component
public class MyBfpp02 implements BeanFactoryPostProcessor , PriorityOrdered {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBfpp02....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现");
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
```



```java
@Component
public class MyBfpp03 implements BeanFactoryPostProcessor , PriorityOrdered {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBfpp03....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现");
	}

	@Override
	public int getOrder() {
		return 0;
	}
}

```



运行程序，打印结果如下：

```java
MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp接口方法实现
MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp接口方法实现
MyBfrpp01....实现了bfrpp, 本方法是bfrpp接口方法实现
MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现
MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现
MyBfrpp01....实现了bfrpp, 本方法是bfrpp父接口=======> bfpp接口方法实现
=====================以下是处理BeanFactoryPostProcessor的实现类=========================
MyBfpp02....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现
MyBfpp03....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现
MyBfpp01....实现了brpp, 本方法是bfpp接口方法实现
```



通过打印结果可知，bfpp(bfrpp)接口实现完全符合前面源码分析的结论。

**下面总结一下spring执行bfpp接口的顺序：**

1. 先执行实现PriorityOrdered接口的bfrpp
2. 再执行实现Ordered接口的bfrpp
3. 最后执行其它实现的bfrpp接口的类

- 如果一个class类在覆写bfrpp接口方法的同时，又覆写了bfpp接口方法，那么会先执行bfrpp接口方法再执行brpp接口方法

4. 执行实现PriorityOrdered接口的bfpp
5. 执行实现Ordered接口的bfpp
6. 执行其它的bfrpp接口实现类



### 4 . 通过api方法注册bfpp

```java
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(SpringConfig.class);
		ac.addBeanFactoryPostProcessor(new MyBfrpp02());  // 手动注册bfrpp
		ac.refresh();
	}
```



### 5.  ConfigurationClassPostProcessor

> 该类超级重要，它是bfrpp的一个实现类，同时还实现了PriorityOrdered接口，而且它也是spring的一个内置类，在new AnnotationConfigApplicationContext()时，spring就会将其抽象成beanDefinition,同时put到beanDefinitionMap集合中， 所以spring在调用invokeBeanFactoryPostProcessors方法时，首先就会执行org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry方法， 而且该类的getOrder方法返回的是 Integer.MAX_VALUE， 也就是说，该类会最先调用执行。

请看打印：

```java
ConfigurationClassPostProcessor.............. // 这是我在ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry方法添加的打印语句
MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp接口方法实现
MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp接口方法实现
MyBfrpp01....实现了bfrpp, 本方法是bfrpp接口方法实现
MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现
MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现
MyBfrpp01....实现了bfrpp, 本方法是bfrpp父接口=======> bfpp接口方法实现
=====================以下是处理BeanFactoryPostProcessor的实现类=========================
MyBfpp02....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现
MyBfpp03....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现
MyBfpp01....实现了brpp, 本方法是bfpp接口方法实现
```

但是，如果我们通过api的方式注入bfrpp，那么有可以我们注入的bfrpp先于ConfigurationClassPostProcessor类执行。

```java
//@Component   //去掉自动扫包注入spring
public class MyBfrpp02 implements BeanDefinitionRegistryPostProcessor , PriorityOrdered {}
```

```java
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
		ac.register(SpringConfig.class);
		ac.addBeanFactoryPostProcessor(new MyBfrpp02());  // 改为手动注册bfrpp
		ac.refresh();
	}
```

运行程序，打印如下：

```java
MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp接口方法实现  // 先于ConfigurationClassPostProcessor执行。。。。
ConfigurationClassPostProcessor...................
MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp接口方法实现
MyBfrpp01....实现了bfrpp, 本方法是bfrpp接口方法实现
MyBfrpp02....实现了bfrpp, 同时也实现了PriorityOrdered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现
MyBfrpp03....实现了bfrpp, 同时也实现了Ordered接口, 本方法是bfrpp父接口=======> bfpp接口方法实现
MyBfrpp01....实现了bfrpp, 本方法是bfrpp父接口=======> bfpp接口方法实现
=====================以下是处理BeanFactoryPostProcessor的实现类=========================
MyBfpp02....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现
MyBfpp03....实现了brpp, 同时也实现了PriorityOrdered接口,本方法是bfpp接口方法实现
MyBfpp01....实现了brpp, 本方法是bfpp接口方法实现
```

**结论** ： 如果是通注解的方式注入bfrpp类，那么这些bfrpp的调用会后于ConfigurationClassPostProcessor类的调用， 如果是通过api的方式注入brfpp类，那么其调用顺序就会先ConfigurationClassPostProcessor类。



**为会么要说ConfigurationClassPostProcessor类呢？**

因为spring就是在这个后置处理器中完成对java类的扫描，注册，至于具体如完成的，以后再说……



---



```
/**
 * 第1次： 获取spring内置的实现 BeanDefinitionRegistryPostProcessor 接口的类
 */
```