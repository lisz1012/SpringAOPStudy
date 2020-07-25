package com.lisz.service.impl;

import com.lisz.service.Calculator;
import org.springframework.stereotype.Service;

@Service
public class MyCalculator2 implements Calculator {
	public int add(int i, int j) throws NoSuchMethodException {
		int res = i + j;
		return res;
	}

	public int sub(int i, int j) throws NoSuchMethodException {
		int res = i - j;
		return res;
	}

	public int mul(int i, int j) throws NoSuchMethodException {
		int res = i * j;
		return res;
	}

	public int div(int i, int j) throws NoSuchMethodException {
		int res = i / j;
		return res;
	}

	@Override
	public int show(int i) {
		System.out.println("i = " + i);
		i = 9/0;
		return i;
	}
}
