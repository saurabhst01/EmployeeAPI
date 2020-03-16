package com.employeeapi.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners extends TestListenerAdapter
{
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	public void onStart(ITestContext testContext)
	{
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/reports/EmployeeAPI_Report.html");
		
		htmlReporter.config().setDocumentTitle("Automation Report"); //Title of the Report
		htmlReporter.config().setReportName("Employee REST API Test Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Project Name", "Employee Project");
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "Saurabh");
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getName()); //Create new entry in the Report
		
		test.log(Status.PASS, "Test Case passed is "+result.getName());
	}
	
	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getName()); //Create new entry in the Report
		
		test.log(Status.FAIL, "TEST CASE FAILED IS " +result.getName());
		test.log(Status.FAIL, "TEST CASE FAILED IS " +result.getThrowable()); //To add error/exception in extent report
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getName());
		
		test.log(Status.SKIP, "Test Case skipped is "+result.getName());
	}

	public void onFinish(ITestContext testContext)
	{
		extent.flush();
	}
}
