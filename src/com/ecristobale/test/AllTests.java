package com.ecristobale.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ServiceATest.class, ServiceBTest.class })
public class AllTests {

}
