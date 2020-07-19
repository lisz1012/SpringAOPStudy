package com.lisz.utils;

import java.util.Arrays;

public class LogUtil {
	public static void start(Object ... objects) {
		System.out.println("Method is about to execute, params are: " + Arrays.asList(objects));
	}

	public static void end(Object ... objects) {
		System.out.println("Method completed, result is: " + Arrays.asList(objects));
	}
}
