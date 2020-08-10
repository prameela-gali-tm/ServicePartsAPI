package com.toyota.scs.serviceparts.util;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import com.toyota.scs.serviceparts.BaseTest;
import com.toyota.scs.serviceparts.util.*;

public class TestNGListener  extends BaseTest implements IInvokedMethodListener, ITestListener,  ISuiteListener{

	public static String description, className;
	Test testAnnotation;
	TestClass classAnnotation;
	ITestResult result;

	public void onTestStart(ITestResult result) {
		classAnnotation = result.getMethod().getTestClass().getRealClass().getAnnotation(TestClass.class);
		className = classAnnotation.description();
		System.out.println("Test class .." + classAnnotation.description() + "..Started");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		System.out.println("test Failed" + result.getTestName());
		BaseTest.test.log(LogStatus.FAIL, result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("test skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ISuite suite) {
	
		 
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

}
