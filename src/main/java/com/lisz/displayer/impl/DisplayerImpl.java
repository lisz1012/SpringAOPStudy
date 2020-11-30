package com.lisz.displayer.impl;

import com.lisz.displayer.Displayer;
import org.springframework.stereotype.Service;

@Service
public class DisplayerImpl implements Displayer {
	@Override
	public int display(int i) {
		System.out.println("i = " + i);
		return i;
	}

	public void print(){
		myPrint("aaa");
	}

	private void myPrint(String s) {
		System.out.println(s);
	}
}
