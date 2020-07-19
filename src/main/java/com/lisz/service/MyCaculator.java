package com.lisz.service;

import com.lisz.utils.LogUtil;

public class MyCaculator implements Calculator {
	public int add(int i, int j) {
		LogUtil.start(i, j);
		int res = i + j;
		LogUtil.end(res);
		return res;
	}

	public int sub(int i, int j) {
		LogUtil.start(i, j);
		int res = i - j;
		LogUtil.end(res);
		return res;
	}

	public int mul(int i, int j) {
		LogUtil.start(i, j);
		int res = i * j;
		LogUtil.end(res);
		return res;
	}

	public int div(int i, int j) {
		LogUtil.start(i, j);
		int res = i / j;
		LogUtil.end(res);
		return res;
	}
}
