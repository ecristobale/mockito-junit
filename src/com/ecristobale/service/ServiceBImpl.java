package com.ecristobale.service;

public class ServiceBImpl implements ServiceB {

	private ServiceA serviceA;
	
	@Override
	public ServiceA getServiceA() {
		return serviceA;
	}

	@Override
	public void setServiceA(ServiceA serviceA) {
		this.serviceA = serviceA;

	}

	@Override
	public int multiply(int a, int b) {
		return a * b;
	}

	@Override
	public int addAndMultiply(int add1, int add2, int multiply) {
		return this.serviceA.add(add1, add2) * multiply;
	}

}
