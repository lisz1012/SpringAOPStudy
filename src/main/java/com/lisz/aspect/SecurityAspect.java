package com.lisz.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class SecurityAspect {
	@Pointcut("execution(public int com.lisz.service.MyCalculator.*(int, *))")
	public void myPointcut() {}

	@Before("myPointcut()")
	public void before() {
		System.out.println("Security Aspect starts");
	}

	@After("myPointcut()")
	public void after() {
		System.out.println("Security Aspect ends");
	}
}
