package com.lisz.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogUtil {
	public static void start(Method method, Object ... objects) {
		System.out.println(method.getName() + " is about to execute, params are: " + Arrays.asList(objects));
	}

	public static void end(Method method, Object ... objects) {
		System.out.println(method.getName() + " completed, result is: " + Arrays.asList(objects));
	}
}
