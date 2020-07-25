package com.lisz.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
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
 *
 * 获得被代理方法的参数、方法名等信息的时候，必须使用JoinPoint对象为第一个参数传入代理方法。从中可以get到signature和args等
 *
 * @AfterReturning 注解内添加returning参数可以获取其返回值，并在advisor这里加一个参数，接着那个返回值，
 *                 这个返回值的变量名必须跟注解参数中的returning = 的相一致，类型必须是返回值的父类（接口）
 * @AfterThrowing 注解内添加 throwing 参数可以获取其异常，并在advisor这里加一个参数，接着那个异常，
 *                 这个异常的变量名必须跟注解参数中的throwing = 的相一致，异常的类型要跟抛出的对上才能执行
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

	@Before("execution(public int com.lisz.service.MyCalculator.*(int, *))")
	public static void start8(JoinPoint joinPoint) { // 添加JoinPoint类型的参数，获得被代理对象的被代理方法的参数列表
		Object agrs[] = joinPoint.getArgs();
		System.out.println("start8 is about to execute, params are: ");
		Arrays.asList(agrs).forEach(System.out::println);
		Signature signature = joinPoint.getSignature();
		System.out.println("Signature: " + signature);
		System.out.println("Method name: " + signature.getName());
		System.out.println("Modifiers: " + signature.getModifiers());
		System.out.println("Declared type name: " + signature.getDeclaringTypeName());
		System.out.println("Kind: " + joinPoint.getKind());

		System.out.println(joinPoint.getTarget()); // 打印被代理对象
		System.out.println(joinPoint.getThis()); // 打印被代理对象
	}

	// 正常返回值之后执行. @AfterReturning注解内添加returning参数可以获取其返回值
	@AfterReturning(pointcut = "execution(public int com.lisz.service.MyCalculator.add(int, int))", returning = "retVal")
	public static void end(JoinPoint joinPoint, int retVal) { // 这个变量名必须跟上面的returning = "retVal"一致，类型必须是返回值的父类（接口）
		Signature signature = joinPoint.getSignature();
		System.out.println(signature.getName() + " completed, result is: " + retVal);
	}

	// 程序出现异常之后执行. @AfterThrowing注解内添加 throwing 参数可以获取其异常
	@AfterThrowing(pointcut = "execution(public int com.lisz.service.MyCalculator.div(int, int))", throwing = "e")
	public static void logException(JoinPoint joinPoint, ArithmeticException e) { //这里可以指定要对哪种异常进行处理，类型对不上则不会执行
		Signature signature = joinPoint.getSignature();
		System.out.println(signature.getName() + " 方法抛出异常 " + e.getMessage());
	}

	@After("execution(public int com.lisz.service.MyCalculator.add(int, int))") // 无论如何方法执行完了都会执行，相当于finally
	public static void logFinally() {
		System.out.println("方法执行结束");
	}
}