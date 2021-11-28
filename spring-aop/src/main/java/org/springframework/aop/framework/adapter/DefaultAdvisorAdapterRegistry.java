/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * Default implementation of the {@link AdvisorAdapterRegistry} interface.
 * Supports {@link org.aopalliance.intercept.MethodInterceptor},
 * {@link org.springframework.aop.MethodBeforeAdvice},
 * {@link org.springframework.aop.AfterReturningAdvice},
 * {@link org.springframework.aop.ThrowsAdvice}.
 *
 * @author Rod Johnson
 * @author Rob Harrop
 * @author Juergen Hoeller
 */
@SuppressWarnings("serial")
public class DefaultAdvisorAdapterRegistry implements AdvisorAdapterRegistry, Serializable {


	/**
	 * 持有一个AdvisorAdapter的List,这个list中的adapter是与实现spring aop的advisor增强功能相对应的
	 */
	private final List<AdvisorAdapter> adapters = new ArrayList<>(3);


	/**
	 * Create a new DefaultAdvisorAdapterRegistry, registering well-known adapters.
	 * <p>
	 * 把已有的advice实现的adapter加进来
	 */
	public DefaultAdvisorAdapterRegistry() {
		/*
		 *  提供了3个适配器，方便于扩展， MethodBeforeAdviceAdapter：方法前 ；
		 * AfterReturningAdviceAdapter： 方法正常执行后，返回
		 *
		 *ThrowsAdviceAdapter： 方法执行之后，抛异常
		 */
		registerAdvisorAdapter(new MethodBeforeAdviceAdapter());  //适配MethodBeforeAdvice  ---------》 AspectJMethodBeforeAdvice
		registerAdvisorAdapter(new AfterReturningAdviceAdapter());  // 适配AfterReturningAdvice  --------> AspectJAfterReturningAdvice
		registerAdvisorAdapter(new ThrowsAdviceAdapter());
	}


	@Override
	public Advisor wrap(Object adviceObject) throws UnknownAdviceTypeException {
		if (adviceObject instanceof Advisor) {
			return (Advisor) adviceObject;
		}
		if (!(adviceObject instanceof Advice)) {
			throw new UnknownAdviceTypeException(adviceObject);
		}
		Advice advice = (Advice) adviceObject;
		if (advice instanceof MethodInterceptor) {
			// So well-known it doesn't even need an adapter.
			return new DefaultPointcutAdvisor(advice);
		}
		for (AdvisorAdapter adapter : this.adapters) {
			// Check that it is supported.
			if (adapter.supportsAdvice(advice)) {
				return new DefaultPointcutAdvisor(advice);
			}
		}
		throw new UnknownAdviceTypeException(advice);
	}

	@Override
	public MethodInterceptor[] getInterceptors(Advisor advisor) throws UnknownAdviceTypeException {
		List<MethodInterceptor> interceptors = new ArrayList<>(3);
		// 从advisor中获取advice
		Advice advice = advisor.getAdvice();

		// 1.  如果这个advice实现了MethodInterceptor接口，直接加入到interceptors集合中，用于后面构建拦截器链
		if (advice instanceof MethodInterceptor) {
			// 有3个advice 实现了MethodInterceptor接口; AspectJAfterAdvice,AspectJAfterThrowingAdvice,AspectJAroundAdvice
			interceptors.add((MethodInterceptor) advice);
		}


		// 2. 如果这个advice 满足适配器功能，也加入到interceptors集合中去。
		for (AdvisorAdapter adapter : this.adapters) {  // 循环遍历adapter

			if (adapter.supportsAdvice(advice)) { //看当前的advice是否适配本次循环中的adapter
				// 如果当前advice可以适配本次循环的adapter，那么用个适配器
				/*
					适配器模式很重要，在此以advice为例记录一下spring是如何实现的：

					首先，通过类图可知， AfterReturningAdvice 和 MethodBeforeAdvice 这两类advice是没有实现MethodInterceptor接口，
					但是这两类advice又不可能不处理吧，那该怎么办呢？ spring就为这两类advice提供了各自的适配器。

					AfterReturningAdvice -----------》 AfterReturningAdviceInterceptor
					MethodBeforeAdvice   -----------》 MethodBeforeAdviceInterceptor

					而AfterReturningAdviceInterceptor，MethodBeforeAdviceInterceptor 这两个适配器却都是实现了MethodInterceptor接口，

					这样，对于interceptors集合而言，在遍历所有的advice时，
					如果是AspectJAfterAdvice,AspectJAfterThrowingAdvice,AspectJAroundAdvice这3种advice时，直接将它们的实例对象添加到集合中去;
					而如果是AfterReturningAdvice,MethodBeforeAdvice 这两种advice时，需要通地AfterReturningAdviceInterceptor，MethodBeforeAdviceInterceptor
					将它们包装一下，然后再将包装对象实例添加到interceptors集合中去。

					然后在调用advice的具体方法时，如果AspectJAfterAdvice,AspectJAfterThrowingAdvice,AspectJAroundAdvice这3种advice，直接通过实例对象去调;
					如果是AfterReturningAdvice,MethodBeforeAdvice 这两种advice时，就通过包装类.advice.方法;

					总之：适配器就是将真实需要调用的对象再包装一层~

				 */
				interceptors.add(adapter.getInterceptor(advisor));
			}
		}


		if (interceptors.isEmpty()) {
			throw new UnknownAdviceTypeException(advisor.getAdvice());
		}


		return interceptors.toArray(new MethodInterceptor[0]);
	}

	@Override
	public void registerAdvisorAdapter(AdvisorAdapter adapter) {
		this.adapters.add(adapter);
	}

}
