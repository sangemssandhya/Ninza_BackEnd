package com.ninza.hrm.baseclass;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Basic;
import com.ninza.hrm.ap.genericUtility.DataBaseUtility;
import com.ninza.hrm.ap.genericUtility.FileUtility;
import com.ninza.hrm.ap.genericUtility.JavaUtility;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPIclass {
	public JavaUtility jiu=new JavaUtility();
	public FileUtility Fiu=new FileUtility();
	public DataBaseUtility dbu=new DataBaseUtility();
	public static RequestSpecification specReqObj;
	public static ResponseSpecification specRespObj;
	@BeforeSuite
	public void configBS() throws Throwable
	{
		dbu.getDbconnection();
		System.out.println("=======connect to DB============");
		RequestSpecBuilder builder=new RequestSpecBuilder();
		builder.setContentType(ContentType.JSON);
		builder.setBaseUri(Fiu.getDataFromPropertiesFile("BASEURI"));
		specReqObj=builder.build();
		
		ResponseSpecBuilder resBuilder=new ResponseSpecBuilder();
		resBuilder.expectContentType(ContentType.JSON);
		specRespObj=resBuilder.build();
	}
	
	@AfterSuite
	public void configAS() throws SQLException
	{
		dbu.closeDbconnection();
		System.out.println("======Disconnect DB===========");
	}
}
