/**
 * @author UmaMaheswararao
 */

package com.Listeners;

import static com.base.BasePage.extent;
import static com.base.BasePage.tlExtentTest;
import static com.base.BasePage.tlExtentTestNode;
import static com.base.BasePage.tldriver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class TestListeners implements ITestListener {
	
	public void onStart(ITestContext context) {
		System.out.println("I am in onStart method => "+context.getName());
	}

	public void onFinish(ITestContext context) {
		System.out.println("I am in onFinish method => " +context.getName());
		extent.flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println("I am in onTestStart method => "+result.getMethod().getMethodName() + " Start");
		String className = result.getTestClass().getName();
		String[] array = className.split("[.]");
		className = array[array.length - 1];
		String testName = result.getMethod().getMethodName();
		ExtentTest test = extent.createTest(testName).assignCategory(className);
		tlExtentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("I am in onTestSuccess method => "+result.getMethod().getMethodName() + " Succeed");
		tlExtentTestNode.get().pass("Test case has been executed successfully....!");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("I am in onTestFailure method => "+result.getMethod().getMethodName() + " Failed");
		String failureReason = result.getThrowable().toString();
		failureReason = failureReason.replace("java.lang.AssertionError:", "")
				.replace("expected [true] but found [false]", "").trim();
		String base64String = ((TakesScreenshot) tldriver.get()).getScreenshotAs(OutputType.BASE64);
		try {
			tlExtentTestNode.get().fail(failureReason,
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64String).build());
		} catch (Exception e) {
		}
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("I am in onTestSkipped method => "+result.getMethod().getMethodName() + " Skipped");
		tlExtentTestNode.get().skip("Test case has been skipped...!");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test failed but it is in defined success ratio " +result.getMethod().getMethodName());
	}


	

}
