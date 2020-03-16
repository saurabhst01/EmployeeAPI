package com.employeeapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

public class TC05_DELETE_Employee_Record extends TestBase
{
	@BeforeClass
	void updateEmployee() throws InterruptedException
	{
		logger.info("************* Started TC05_DELETE_Employee_Record ***************");
		
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		
		response = httpRequest.request(Method.GET,"/employees");
		
		//First get the JsonPath object instance from the response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
		
		//Capture ID
		String empID = jsonPathEvaluator.getString("[0].id");
		response = httpRequest.request(Method.DELETE,"/delete/"+empID);
		
		Thread.sleep(3000);
	}
	
	@Test
	void checkResponseBody()
	{
		logger.info("************* Checking Response Body ***************");
		String responseBody = response.getBody().asString();
		logger.info("Response Body ==>"+responseBody);
		Assert.assertTrue(responseBody!=null);
		Assert.assertEquals(responseBody.contains("Successfully! deleted Records"),true);
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
	
	/*@Test
	void checkContentEncoding()
	{
		logger.info("************* Checking Content Encoding ***************");
		String contentEncoding = response.header("Content-Encoding");
		logger.info("Content Encoding ==>"+contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");
	}*/
	
	@AfterClass
	void tearDown()
	{
		httpRequest=null;
		response=null;
		logger.info("************* Finishing TC05_DELETE_Employee_Record ***************");
	}

}
