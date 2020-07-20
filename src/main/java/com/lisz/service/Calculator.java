package com.lisz.service;

import org.springframework.stereotype.Service;

@Service
public interface Calculator {
	int add(int i, int j) throws NoSuchMethodException;
	int sub(int i, int j) throws NoSuchMethodException;
	int mul(int i, int j) throws NoSuchMethodException;
	int div(int i, int j) throws NoSuchMethodException;

}
