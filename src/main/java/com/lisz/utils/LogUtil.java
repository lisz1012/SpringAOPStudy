package com.lisz.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogUtil {
	public static void start(Method method, Object ... objects) {
		System.out.println(method.getName() + " is about to execute, params are: " + (objects == null ? "[]" : Arrays.asList(objects)));
	}

	public static void end(Method method, Object ... objects) {
		System.out.println(method.getName() + " completed, result is: " + (objects == null ? "[]" : Arrays.asList(objects)));
	}

	public static void logException(Method method, Exception e) {
		System.out.println(method.getName() + "方法抛出异常： " + e.getMessage());
	}

	public static void logFinally(Method method) {
		System.out.println(method.getName() + " 方法执行结束");
	}
}
