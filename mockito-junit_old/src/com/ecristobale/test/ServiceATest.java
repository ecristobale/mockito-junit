package com.ecristobale.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ecristobale.service.ServiceA;
import com.ecristobale.service.ServiceAImpl;

public class ServiceATest {

	@Test
	public void testAdd() {
		int a = 2;
		int b = 3;
		ServiceA serviceA = new ServiceAImpl();
		Assert.assertEquals(5, serviceA.add(a, b));
	}

	@Test
	public void testAdd2() {
		int a = 5;
		int b = 3;
		ServiceA serviceA = new ServiceAImpl();
		Assert.assertEquals(9, serviceA.add(a, b));
	}
	
	@Test
	public void testDivideOK() {
		int a = 4;
		int b = 2;
		ServiceA serviceA = new ServiceAImpl();
		Assert.assertEquals(2, serviceA.divide(a, b));
	}
	
	@Test(expected = ArithmeticException.class)
	public void testDivideException() {
		int a = 4;
		int b = 0;
		ServiceA serviceA = new ServiceAImpl();
		serviceA.divide(a, b);
	}
	
	@Test()
	public void testManageListTrue() {
		ServiceA serviceA = new ServiceAImpl();
		List<String> names = Arrays.asList("name1");
		Assert.assertEquals(true, serviceA.manageList(names));
	}
	
	@Test()
	public void testManageListFalse() {
		ServiceA serviceA = new ServiceAImpl();
		List<String> names = new ArrayList<String>();
		Assert.assertEquals(false, serviceA.manageList(names));
	}
	
	@Test(expected = RuntimeException.class)
	public void testManageListException() {
		ServiceA serviceA = new ServiceAImpl();
		serviceA.manageList(null);
	}

}
