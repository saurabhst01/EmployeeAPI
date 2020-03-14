package com.employeeapi.testcases;

import org.testng.Assert;
import org.testng.annotations.*;

import com.employeeapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;

public class TC01_GET_All_Employees extends TestBase
{
	@BeforeClass
	void getAllEmployees() throws InterruptedException
	{
		logger.info("************* Started TC01_GET_All_Employees ***************");
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET,"/employees");
		
		Thread.sleep(3000);
	}
	
	@Test
	void checkResponseBody()
	{
		logger.info("************* Checking Response Body ***************");
		String responseBody = response.getBody().asString();
		logger.info("Response Body ==>"+responseBody);
		Assert.assertTrue(responseBody!=null);
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
		Assert.assertEquals(contentType, "application/json;charset=utf-8");
	}
	
	@Test
	void checkServerType()
	{
		logger.info("************* Checking Server Type ***************");
		String serverType = response.header("server");
		logger.info("Server Type ==>"+serverType);
		Assert.assertEquals(serverType, "nginx/1.16.0");
	}
	
	@Test
	void checkContentEncoding()
	{
		logger.info("************* Checking Content Encoding ***************");
		String contentEncoding = response.header("Content-Encoding");
		logger.info("Content Encoding ==>"+contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");
	}
	
	@Test
	void checkContentLength()
	{
		logger.info("************* Checking Content Length ***************");
		String contentLength = response.header("Content-Length");
		logger.info("Content Length ==>"+contentLength);
		
		if(Integer.parseInt(contentLength)<100)
			logger.warn("Content length is less than 100");
		
		Assert.assertTrue(Integer.parseInt(contentLength)>100);
	}
	
	@Test
	void captureAllHeaders()
	{
		logger.info("************* Capturing All Headers ***************");
		Headers allHeaders = response.headers();
		for(Header header:allHeaders)
		{
			logger.info("Header: " + header.getName()+":    "+header.getValue());
		}
	}
	
	@Test
	void checkCookies()
	{
		logger.info("************* Checking Cookies ***************");
		String cookies = response.cookie("PHPSESSID");
		logger.info("Cookies ==>"+cookies);
	}
	
	@AfterClass
	void tearDown()
	{
		httpRequest=null;
		response=null;
		logger.info("************* Finishing TC01_GET_All_Employees ***************");
	}
}
