package com.ecristobale.test;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.ecristobale.service.ServiceA;
import com.ecristobale.service.ServiceAImpl;
import com.ecristobale.service.ServiceB;
import com.ecristobale.service.ServiceBImpl;

public class ServiceBTest {

	@Test
	public void testMultiply() {
		int a = 5;
		int b = 3;
		ServiceB serviceB = new ServiceBImpl();
		assertEquals(15, serviceB.multiply(a, b));
	}
	
	@Test
	public void testAddAndMultiply() {
		ServiceA serviceA = new ServiceAImpl();
		ServiceB serviceB = new ServiceBImpl();
		
		serviceB.setServiceA(serviceA);
		
		assertEquals(10, serviceB.addAndMultiply(2, 3, 2));
	}
	
	@Test
	public void testAddAndMultiplyMockito() {
		// we doesn´t need to know that if a equals to 5 then the add is different
		// we just want to test serviceB, so we just configure a default return for 
		// serviceA method add with Mockito
		ServiceA serviceA = mock(ServiceA.class);
		when(serviceA.add(5, 3)).thenReturn(8);
		
		ServiceB serviceB = new ServiceBImpl();
		
		serviceB.setServiceA(serviceA);
		
		assertEquals(16, serviceB.addAndMultiply(5, 3, 2));
	}

}
