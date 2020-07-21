package com.lisz.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 切入点表达式最精确的匹配方式："execution(public int com.lisz.service.MyCalculator.add(int, int))"
 * 一般使用的时候用的是通配符的方式：
 *      * 任意多的任意字符, public int com.lisz.service.MyCalculator.*(int, int)) 匹配任何符合条件的切入点（带切入点注解的）
 *      .
 */

@Aspect
@Component
public class LogUtil {

	@Before("execution(public int com.lisz.service.MyCalculator.*(int, int))")
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
