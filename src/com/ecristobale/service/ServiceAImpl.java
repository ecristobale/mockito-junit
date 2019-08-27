package com.ecristobale.service;

import java.util.List;

public class ServiceAImpl implements ServiceA{

	@Override
	public int add(int a, int b) {
		if(a == 5) {
			return a + b + 1;
		}
		return a + b;
	}

	@Override
	public int divide(int a, int b) {
		if(b == 0) {
			throw new ArithmeticException("/ by zero");
		}
		return a/b;
	}

	@Override
	public boolean manageList(List<String> names) {
		if(names == null) {
			throw new RuntimeException("There was some error with the list");
		} else if(names.size() > 0) {
			// do some business logic...
			return true;
		} else {
			// the list is not completed due to some business logic
			return false;
		}
	}

}
