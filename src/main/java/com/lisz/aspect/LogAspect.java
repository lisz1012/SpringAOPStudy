package com.lisz.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
 * 但是这里的测试顺序其实是：@Before -> @AfterReturning -> @After。
 *
 * 获得被代理方法的参数、方法名等信息的时候，必须使用JoinPoint对象为第一个参数传入代理方法。从中可以get到signature和args等
 *
 * @AfterReturning 注解内添加returning参数可以获取其返回值，并在advisor这里加一个参数，接着那个返回值，
 *                 这个返回值的变量名必须跟注解参数中的returning = 的相一致，类型必须是返回值的类型或其父类（接口）
 * @AfterThrowing 注解内添加 throwing 参数可以获取其异常，并在advisor这里加一个参数，接着那个异常，
 *                 这个异常的变量名必须跟注解参数中的throwing = 的相一致，异常的类型要跟抛出的对上才能执行
 *
 * 通知方法在定义的时候对于访问修饰符和返回值类型都没有明确的要求，但是要注意参数不能随便添加
 * 如果有多个匹配的execution表达式相同，能否抽出来？可以:
 *      1. 写一个无返回值的空方法 2. 在其上写@Pointcut 3. 把织入点表达式作为唯一的字符串参数写进去 4. 在用到这个织入点表达式的注解（如@Before）
 *      后面的参数中写方法的调用，如：@Before(value = "myPointcut()")。此处的空方法之气到一个声明的作用，@Pointcut总得有个地方放吧
 *
 * 环绕通知在执行的时候是优先于普通通知的。
 * 如果是正常结束，则顺序是：@Around前置通知 -> @Before -> 原方法调用 -> @AfterReturning -> @After -> @Around后置通知 -> @Around finally通知 -> @Around环绕返回前通知
 * 如果是异常结束，则顺序是：@Around前置通知 -> @Before -> 原方法调用 -> @AfterReturning -> @After -> @Around异常通知 -> @Around finally通知 -> @Around环绕返回前通知
 * 环绕通知写起来就跟用JDK的动态代理是差不多的了，如果需要修改返回值，则必须用@Around, 这种方式操作起来会控制得更细致
 * 用@Order(x)注解来标识不同的Aspect之间的相对顺序，x数字越大@Before越先执行、@After越后执行；数字越小则反之： 大内小外，底层就是一个类似于filter的责任链，类似递归，数字小的去
 * 调用数字大的。两个@Order 参数里面的x相等，则按照字母顺序来
 */

@Aspect
@Component
@Order(2)
public class LogAspect {

	@Pointcut("execution(public int com.lisz.service.MyCalculator.*(int, *))")
	public void myPointcut(){}

	@Pointcut("execution(public int com.lisz.service.MyCalculator.mul(int, *))")
	public void mulPointcut(){}

	@Pointcut("execution(public int com.lisz.service.impl.MyCalculator2.show(int))")
	public void myPointcut2(){}

	@Pointcut("execution(public int com.lisz.displayer.impl.DisplayerImpl.display(int))")
	public void displayPointcut() {}

	@Before(value = "myPointcut()")
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

	// 通知方法在定义的时候对于访问修饰符和返回值类型都没有明确的要求
	@Before("myPointcut()")
	private int start8(JoinPoint joinPoint) { // 添加JoinPoint类型的参数，获得被代理对象的被代理方法的参数列表
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
		return 100;
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

	// 环绕通知要写三段
	@Around("myPointcut()")
	public Object around(ProceedingJoinPoint pjp){
		Signature signature = pjp.getSignature();
		Object[] args = pjp.getArgs();
		System.out.println("signature: " + signature);
		System.out.println("Args: " + args);

		Object retVal = 1; // 也可以把上面这个args写进参数中
		System.out.println("环绕通知： " + signature.getName() + " 开始执行");
		try {
			// 通过反射的方式调用目标的方法，相当于执行method.invoke()同时可以自己修改返回的结果
			retVal = pjp.proceed();
			retVal = 100;
		} catch (Throwable throwable) {
			//throwable.printStackTrace();
			System.out.println("环绕异常通知： " + signature.getName() + " 出现异常");
		} finally {
			System.out.println("环绕返回通知： " + signature.getName() + " 方法返回结果是： " + retVal);
		}

		System.out.println("mul ends");
		return retVal;
	}

	// 测试执行顺序

	@Before("myPointcut2()")
	public void before2(JoinPoint joinPoint) {
		int i = (int)joinPoint.getArgs()[0];
		String methodName = joinPoint.getSignature().getName();
		System.out.println(methodName + " @Before with arg: " + i);
	}

	@After(value = "myPointcut2()")
	public void after2(JoinPoint joinPoint) {
		int i = (int)joinPoint.getArgs()[0];
		String methodName = joinPoint.getSignature().getName();
		System.out.println(methodName + " @After with arg: " + i);
	}

	@AfterReturning(value = "myPointcut2()", returning = "retVal")
	public void afterReturning2(JoinPoint joinPoint, int retVal) {
		int i = (int)joinPoint.getArgs()[0];
		String methodName = joinPoint.getSignature().getName();
		System.out.println(methodName + " @AfterReturning with arg: " + i + " and return value: " + retVal);
	}

	@AfterThrowing(value = "myPointcut2()", throwing = "e")
	public void afterThrowing2(JoinPoint joinPoint, Exception e) {
		int i = (int)joinPoint.getArgs()[0];
		String methodName = joinPoint.getSignature().getName();
		System.out.println(methodName + " @AfterReturning with arg: " + i + " and threw an exception: " + e.getMessage());
	}

	@Around(value = "myPointcut2()")
	public int around2(ProceedingJoinPoint pjp) {
		int i = (int)pjp.getArgs()[0];
		String methodName = pjp.getSignature().getName();
		System.out.println("@Around starts");
		int res = 0;
		try {
			res = (int)pjp.proceed();
			System.out.println("@Around ends");
		} catch (Throwable throwable) {
			System.out.println("@Around catches an exception");
		} finally {
			System.out.println("@Around finally");
		}
		System.out.println(methodName + " @Around ends with arg: " + i + " and result: " + res);
		// 这里发个坏，修改一下返回值试试，哈哈哈
		//res = 1000;
		return res;
	}

	@Around("displayPointcut()")
	public int around3(ProceedingJoinPoint pjp) {
		try {
			pjp.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
		}
		return 10000;
	}
}