package com.lisz.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class LogUtil {

	@Before("execution(public int com.lisz.service.MyCalculator.add(int, int))")
	public static void start() { // 参数列表不要随便填写参数，会有报错
		System.out.println("is about to execute, params are: ");
	}

	@AfterReturning("execution(public int com.lisz.service.MyCalculator.add(int, int))") // 正常返回值之后执行
	public static void end() {
		System.out.println("completed, result is: ");
	}

	@AfterThrowing("execution(public int com.lisz.service.MyCalculator.add(int, int))")
	public static void logException() {
		System.out.println("方法抛出异常");
	}

	@After("execution(public int com.lisz.service.MyCalculator.add(int, int))") // 无论如何方法执行完了都会执行，相当于finally
	public static void logFinally() {
		System.out.println("方法执行结束");
	}
}
