package com.lisz.proxy;

import com.lisz.service.Calculator;
import com.lisz.utils.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类com.sun.proxy.$Proxy9是在运行过程中才生成的，没有提前写代码定义，每次运行的时候的代理类$Proxy...是不一样的
 * 在Calculator的代理生成类中。请来了代理的总代理Proxy来帮忙
 * 只要实现了同样的接口就可以不改代码，代理另外的实现类
 * 必须要有接口，否则不能使用这种JDK的动态代理，但是在生产环境中不能保证每个类都实现了某个/些接口
 * 所以有第二种方式是cglib，有没有接口都无所谓
 * Spring中有两种动态代理的方式1. JDK 2. cglib
 * Spring甚至可以帮我们把以下的逻辑都省略，不用写前后打印日志的逻辑代码
 */
public class CalculatorProxy {
	public static Object getProxy(final Object object) {
		Class<?> infces[] = object.getClass().getInterfaces();
		return Proxy.newProxyInstance(Calculator.class.getClassLoader(), infces, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object retValue = null;
				try {
					LogUtil.start(method, args);
					retValue = method.invoke(object, args);
					LogUtil.end(method, retValue);
				} catch (Exception e) {
					LogUtil.logException(method, e);
					e.printStackTrace();
				} finally {
					LogUtil.logFinally(method);
				}
				return retValue;
			}
		});
	}
}
