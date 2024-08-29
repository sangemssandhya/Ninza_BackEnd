package com.ninza.hrm.api.UsingUtilitiesempolyeetest;



import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.mysql.jdbc.Driver;
import com.ninza.hrm.ap.genericUtility.DataBaseUtility;
import com.ninza.hrm.ap.genericUtility.FileUtility;
import com.ninza.hrm.ap.genericUtility.JavaUtility;
import com.ninza.hrm.baseclass.BaseAPIclass;
import com.ninza.hrm.endpoint.IEndpoint;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ProjectTest extends BaseAPIclass{
	
	com.ninza.pojoclass.ProjectPojo pobj;
	
	
	
	
	
	
	@Test
	public void addSingleProjectWithCreateTest() throws Throwable
	{
		
		String BASEURL=Fiu.getDataFromPropertiesFile("BASEURI");
		String userName=Fiu.getDataFromPropertiesFile("DB_USERNAME");
		String password=Fiu.getDataFromPropertiesFile("DB_PASSWORD");
		
		String expmsg="Successfully Added";
		String projectName="ABC"+jiu.getRandomNumber();
		
		
		 pobj=new com.ninza.pojoclass.ProjectPojo(projectName,"Created","Prabhas", 0);
Response resp=	given()
	.spec(specReqObj)
	.body(pobj)
	.when()
	.post(IEndpoint.ADDPROJ);
	resp.then()
	.assertThat().statusCode(201)
	.assertThat().time(Matchers.lessThan(3000L))
	.assertThat().contentType(ContentType.JSON)
	.spec(specRespObj)
	.log().all();
	
	String actMsg=resp.jsonPath().get("msg");
	Assert.assertEquals(expmsg, actMsg);
	//verify the ProjectName DB layer
	
	
	boolean flag=false;
	Driver driverRef= new Driver();
	DriverManager.registerDriver(driverRef);
	//step 2: connect to database
	Connection conn= DriverManager.getConnection("jdbc:mysql://49.249.28.218:3333/ninza_hrm","root@%","root");
	System.out.println("====Done====");
	//step 3: create sql query
	Statement state=conn.createStatement();
	//step 4: execute select query and get result
	 ResultSet result=state.executeQuery("select * from project");
	while( result.next())
	{
		if(result.getString(4).equals(projectName)) 
		{
			flag=true;
			break;
		}
		
	}
	//step 5: close the connection
	 conn.close();
	 Assert.assertTrue("Project in DB is not Verified",flag);
	 
	 

	
	}
	@Test(dependsOnMethods = "addSingleProjectWithCreateTest")
	public void CreateDuplicateProjectTest()
	{
		given()
		.spec(specReqObj)
		.contentType(ContentType.JSON)
		.body(pobj)
		.when()
		.post("http://49.249.28.218:8091/addProject")
		.then()
		.assertThat().statusCode(409)
		.spec(specRespObj)
		.log().all();
	}
	
}
