package com.lisz.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 切入点表达式最精确的匹配方式："execution(public int com.lisz.service.MyCalculator.add(int, int))"
 * 一般使用的时候用的是通配符的方式：
 *      * 任意多的任意字符, public int com.lisz.service.MyCalculator.*(int, int)) 匹配任何符合条件的切入点（带切入点注解的）
 *        只能匹配一层package或目录、或者参数列表中一个参数的类型. *不能匹配访问修饰符，如果不确定访问修饰符是什么，可以直接省略不写：
 *        "execution(int com.lisz.service.MyCalculator.*(int, *))",返回值可以用*
 *      .. 1. 可以匹配多个参数、任意类型，参数列表里写(..), 表示不限制任何参数的类型和个数。
 *         2. 可以匹配多层路径
 * 最偷懒的方式：* *(..) 以*开头的时候是可以代替所有的各层路径 * com..*(..): com下的所有方法  * *.*(..)：所有方法
 * 使用通配符的时候不是越简洁越好，更多是选择符合要求或符合规则的匹配方式，此时就要求在定义标识符的时候，必须遵循企业或者项目的规范
 * 项目命名规范要遵循。各种匹配性能方面应该没什么大的差别
 *
 * 在使用表达式的时候还支持逻辑运算：&& || !
 *      execution(public int com.mashibing.serviceMyCalculator.*(..)&& execution(*.*(..)))
 *
 * advice的正常执行顺序: @Before -> @After -> @AfterReturning
 * advice的异常执行顺序: @Before -> @After -> @AfterThrowing
 * https://juejin.im/post/5e9fb976f265da47ef2f4137
 * 但是这里的测试顺序是：@Before -> @AfterReturning -> @After 这是个问题。
 */

@Aspect
@Component
public class LogUtil {

	@Before("execution(public int com.lisz.service.MyCalculator.*(int, *))")
	public static void start() { // 参数列表不要随便填写参数，会有报错
		System.out.println("is about to execute, params are: ");
	}

	@Before("execution(public int com.lisz.service.MyCalculator.*(..))") // ..匹配任意个数和类型的参数
	public static void start2() { // 参数列表不要随便填写参数，会有报错
		System.out.println("start2: is about to execute, params are: ");
	}

	@Before("execution(public int com..MyCalculator.*(..))") // ..匹配多层路径
	public static void start3() { // 参数列表不要随便填写参数，会有报错
		System.out.println("start3: is about to execute, params are: ");
	}

	@Before("execution(public int com.lisz.service.MyCalculator.*(..)) && execution(* *(..))") // ..匹配多层路径
	public static void start4() { // 参数列表不要随便填写参数，会有报错
		System.out.println("start4: is about to execute, params are: ");
	}

	@Before("execution(public int com.lisz.service.MyCalculator.*(..)) || execution(* *(..))") // ..匹配多层路径
	public static void start5() { // 参数列表不要随便填写参数，会有报错
		System.out.println("start5: is about to execute, params are: ");
	}

	@Before("!execution(public int com.lisz.service.MyCalculator.sub(int, int))")
	public static void start6() { // 参数列表不要随便填写参数，会有报错
		System.out.println("start6: is about to execute, params are: ");
	}

	@Before("execution(public int com.lisz.service.MyCalculator.add(int, int)))")
	public static void start7() { // 参数列表不要随便填写参数，会有报错
		System.out.println("start7: is about to execute, params are: ");
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