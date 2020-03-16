package com.employeeapi.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC02_GET_Single_Employee_Record extends TestBase
{
	@BeforeClass
	void getEmployeeData() throws InterruptedException
	{
		logger.info("************* Started TC02_GET_Single_Employee_Record ***************");
		
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET,"/employees/"+empID);
		
		Thread.sleep(2000);
	}
	
	@Test
	void checkResponseBody()
	{
		logger.info("************* Checking Response Body ***************");
		String responseBody = response.getBody().asString();
		logger.info("Response Body ==>"+responseBody);
		Assert.assertTrue(responseBody!=null);
		Assert.assertEquals(responseBody.contains(empID), true);
	}
	
	@Test
	void checkStatusCode()
	{
		logger.info("************* Checking Status Code ***************");
		int statusCode = response.getStatusCode();
		logger.info("Status Code ==>"+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test
	void checkResponseTime()
	{
		logger.info("************* Checking Response Time ***************");
		long responseTine = response.getTime();
		logger.info("Response Time ==>"+responseTine);
		
		if(responseTine>7000)
			logger.warn("Response Time is greater than 7000");
		
		Assert.assertTrue(responseTine<7000);
	}
	
	@Test
	void checkStatusLine()
	{
		logger.info("************* Checking Status Line ***************");
		String stausLine = response.getStatusLine();
		logger.info("Status Line ==>"+stausLine);
		Assert.assertEquals(stausLine, "HTTP/1.1 200 OK");
	}

	@Test
	void checkContentType()
	{
		logger.info("************* Checking Content Type ***************");
		String contentType = response.getContentType();
		logger.info("Content Type ==>"+contentType);
		Assert.assertEquals(contentType, "text/html;charset=UTF-8");
	}
	
	@Test
	void checkContentLength()
	{
		logger.info("************* Checking Content Length ***************");
		String contentLength = response.header("Content-Length");
		logger.info("Content Length ==>"+contentLength);
		
		if(Integer.parseInt(contentLength)<1000)
			logger.warn("Content length is less than 1000");
		
		Assert.assertTrue(Integer.parseInt(contentLength)>1000);
	}
	
	@Test
	void checkServerType()
	{
		logger.info("************* Checking Server Type ***************");
		String serverType = response.header("server");
		logger.info("Server Type ==>"+serverType);
		Assert.assertEquals(serverType, "nginx/1.16.0");
	}
	
	@AfterClass
	void tearDown()
	{
		httpRequest=null;
		response=null;
		logger.info("************* Finishing TC02_GET_Single_Employee_Record ***************");
	}
}
