package com.lisz.service;

import org.springframework.stereotype.Service;

@Service
public class MyCalculator implements Calculator {
	public int add(int i, int j) throws NoSuchMethodException {
		int res = i + j;
		System.out.println(i + " + " + j + " = " + res);
		return res;
	}

	public int sub(int i, int j) throws NoSuchMethodException {
		int res = i - j;
		return res;
	}

	public int mul(int i, int j) throws NoSuchMethodException {
		int res = i * j;
		System.out.println(i + " * " + j + " = " + res);
		return res;
	}

	public int div(int i, int j) throws NoSuchMethodException {
		int res = i / j;
		return res;
	}

	public int show(int i) {
		System.out.println("i = " + i);
		return i;
	}
}
