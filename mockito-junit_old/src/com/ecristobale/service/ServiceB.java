package com.ecristobale.service;

public interface ServiceB {

	public abstract ServiceA getServiceA();
	
	public abstract void setServiceA(ServiceA serviceA);
	
	public abstract int multiply(int a, int b);
	
	public abstract int addAndMultiply(int add1, int add2, int multiply);
}
