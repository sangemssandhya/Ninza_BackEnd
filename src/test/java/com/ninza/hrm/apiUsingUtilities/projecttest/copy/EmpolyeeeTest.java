package com.ninza.hrm.apiUsingUtilities.projecttest.copy;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.mysql.jdbc.Driver;
import com.ninza.hrm.baseclass.BaseAPIclass;

import io.restassured.http.ContentType;


public class EmpolyeeeTest extends BaseAPIclass {
	@Test
	public void addEmpolyeeTest() throws SQLException
	{
		//create object of pojo class
		Random random=new Random();
		int ranNum=random.nextInt(3000);
		//API-1 Add a Project inside Server
		String projectName="Airtel"+ranNum;
		String Username="Amul"+ranNum;
		com.ninza.pojoclass.ProjectPojo pobj=new com.ninza.pojoclass.ProjectPojo(projectName,"Created","Prabhas", 0);
     given()
 	.spec(specReqObj)
	
	.body(pobj)
	.when()
	.post("http://49.249.28.218:8091/addProject")
	.then()
	.spec(specRespObj)
	
	.log().all();
	
	
		
		
		//API-2 ADD EMPOLYEE TO SAME PROJECT
		//public EmpolyeePOJO(String designation, String dob, String email, String empName, double experience,
				//String mobileNo, String project, String role, String username)
		com.ninza.pojoclass.EmpolyeePOJO empo=new com.ninza.pojoclass.EmpolyeePOJO("Architect", "24/07/2024", "rajesh123@gmail.com",Username, 20,
				"9876543210", projectName, "ROLE_EMPLOYEE", Username);
		given()
		.contentType(ContentType.JSON)
		.body(empo)
		.when()
		.post("http://49.249.28.218:8091/employees")
		.then()
		.assertThat().contentType(ContentType.JSON)
		.assertThat().statusCode(201)
		.and()
		.time(Matchers.lessThan(3000L))
		.log().all();
		
		
		//verify EMP name in DB
		boolean flag=false;
		Driver driverRef= new Driver();
		DriverManager.registerDriver(driverRef);
		//step 2: connect to database
		Connection conn= DriverManager.getConnection("jdbc:mysql://49.249.28.218:3333/ninza_hrm","root@%","root");
		System.out.println("====Done====");
		//step 3: create sql query
		Statement state=conn.createStatement();
		//step 4: execute select query and get result
		 ResultSet result=state.executeQuery("select * from employee");
		while( result.next())
		{
			if(result.getString(5).equals(Username)) 
			{
				flag=true;
				break;
			}
			
		}
		//step 5: close the connection
		 conn.close();
		 Assert.assertTrue("Employee in DB is not Verified",flag);
		 

	}
	@Test
	public void addEmpolyeewithoutEmailTest() throws SQLException
	{
		Random random=new Random();
		int ranNum=random.nextInt(3000);
		//API-1 Add a Project inside Server
		String projectName="Airtel"+ranNum;
		String Username="Amul"+ranNum;
		com.ninza.pojoclass.ProjectPojo pobj=new com.ninza.pojoclass.ProjectPojo(projectName,"Created","Prabhas", 0);
     given()
     .spec(specReqObj)
	.contentType(ContentType.JSON)
	.body(pobj)
	.when()
	.post("http://49.249.28.218:8091/addProject")
	.then()
	.spec(specRespObj)
	.log().all();
	
	
		
		
		//API-2 ADD EMPOLYEE TO SAME PROJECT
		//public EmpolyeePOJO(String designation, String dob, String email, String empName, double experience,
				//String mobileNo, String project, String role, String username)
		com.ninza.pojoclass.EmpolyeePOJO empo=new com.ninza.pojoclass.EmpolyeePOJO("Architect", "24/07/2024", "",Username, 20,
				"9876543210", projectName, "ROLE_EMPLOYEE", Username);
		given()
		.spec(specReqObj)
		
		.body(empo)
		.when()
		.post("http://49.249.28.218:8091/employees")
		.then()
		.assertThat().contentType(ContentType.JSON)
		.assertThat().statusCode(500)
		.spec(specRespObj)
		.log().all();
		
		
		
		 

	}

}
