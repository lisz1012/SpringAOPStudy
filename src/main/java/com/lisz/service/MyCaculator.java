package com.lisz.service;

public class MyCaculator implements Calculator {
	public int add(int i, int j) {
		System.out.println("add is about to execute, param1 is " + i + " param 2 is " + j);
		int res = i + j;
		System.out.println("add completed, result is: " + res);
		return res;
	}

	public int sub(int i, int j) {
		System.out.println("sub is about to execute, param1 is " + i + " param 2 is " + j);
		int res = i - j;
		System.out.println("sub completed, result is: " + res);
		return res;
	}

	public int mul(int i, int j) {
		System.out.println("mul is about to execute, param1 is " + i + " param 2 is " + j);
		int res = i * j;
		System.out.println("mul completed, result is: " + res);
		return res;
	}

	public int div(int i, int j) {
		System.out.println("div is about to execute, param1 is " + i + " param 2 is " + j);
		int res = i / j;
		System.out.println("div completed, result is: " + res);
		return res;
	}
}
