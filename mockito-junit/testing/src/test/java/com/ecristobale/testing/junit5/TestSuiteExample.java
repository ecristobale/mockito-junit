package com.ecristobale.testing.junit5;


import org.junit.platform.suite.api.*;

@Suite // add junit-platform-suite to pom.xml to use suite annotations
@SuiteDisplayName("JUnit 5 Test Suite Example")
//By default, it will only include test classes whose names either begin with Test or end with Test or Tests.
@SelectPackages("com.ecristobale.testing.junit5")
//@SelectPackages({"com.ecristobale.testing.junit5","com.ecristobale.testing.anotherpackage"}) // various packages with {}

//@SelectPackages("com.ecristobale.testing")
//@ExcludePackages("com.ecristobale.testing.junit5")
//@IncludePackages("com.ecristobale.testing.anotherpackage")

@IncludeTags("production")
//@ExcludeTags("development")
@SelectClasses( JUnitApiAdvancedExamples.class )
//@SelectClasses( {CalculatorTest.class, MoneyTransactionServiceTest.class} )
public class TestSuiteExample {
}
