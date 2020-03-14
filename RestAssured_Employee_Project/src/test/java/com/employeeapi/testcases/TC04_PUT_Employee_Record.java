package com.employeeapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;
import com.employeeapi.utilities.RestUtils;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

public class TC04_PUT_Employee_Record extends TestBase
{
	String empName = RestUtils.empName();
	String empSal = RestUtils.empSal();
	String empAge = RestUtils.empAge();
	
	@BeforeClass
	void updateEmployee() throws InterruptedException
	{
		logger.info("************* Started TC04_PUT_Employee_Record ***************");
		
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		
		//JSONObject is a class that represents a simple JSON. We can add Key-Value pairs using PUT method
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("salary", empSal);
		requestParams.put("age", empAge);
		
		//Adding header stating that request body is JSON
		//httpRequest.header("Content-Type","application-json");
		RestAssured.given().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig()
			.encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
			.contentType("multipart/form-data; boundary=--MyBoundary").queryParams(requestParams).put("/update"+empID);
		
		//Add JSON to the body of the request
		httpRequest.body(requestParams.toJSONString());
		
		response = httpRequest.request(Method.PUT,"/update"+empID);
		
		Thread.sleep(5000);
	}

	@Test
	void checkResponseBody()
	{
		logger.info("************* Checking Response Body ***************");
		String responseBody = response.getBody().asString();
		logger.info("Response Body ==>"+responseBody);
		Assert.assertTrue(responseBody!=null);
		Assert.assertEquals(responseBody.contains(empName), true);
		Assert.assertEquals(responseBody.contains(empSal), true);
		Assert.assertEquals(responseBody.contains(empAge), true);
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
	
	@AfterClass
	void tearDown()
	{
		httpRequest=null;
		response=null;
		logger.info("************* Finishing TC04_PUT_Employee_Record ***************");
	}
}
