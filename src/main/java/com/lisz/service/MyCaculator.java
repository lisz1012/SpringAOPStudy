package com.lisz.service;

import com.lisz.utils.LogUtil;

import java.lang.reflect.Method;

public class MyCaculator implements Calculator {
	public int add(int i, int j) throws NoSuchMethodException {
		Method method = MyCaculator.class.getMethod("add", int.class, int.class);
		LogUtil.start(method, i, j);
		int res = i + j;
		LogUtil.end(method, res);
		return res;
	}

	public int sub(int i, int j) throws NoSuchMethodException {
		Method method = MyCaculator.class.getMethod("sub", int.class, int.class);
		LogUtil.start(method, i, j);
		int res = i - j;
		LogUtil.end(method, res);
		return res;
	}

	public int mul(int i, int j) throws NoSuchMethodException {
		Method method = MyCaculator.class.getMethod("mul", int.class, int.class);
		LogUtil.start(method, i, j);
		int res = i * j;
		LogUtil.end(method, res);
		return res;
	}

	public int div(int i, int j) throws NoSuchMethodException {
		Method method = MyCaculator.class.getMethod("div", int.class, int.class);
		LogUtil.start(method, i, j);
		int res = i / j;
		LogUtil.end(method, res);
		return res;
	}
}
