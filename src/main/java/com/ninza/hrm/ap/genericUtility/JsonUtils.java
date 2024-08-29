package com.ninza.hrm.ap.genericUtility;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class JsonUtils {
	/**
	 * get the Jsondata from Based on json complex xpath
	 * @param resp
	 * @param jsonXpath
	 * @return
	 */
	public String getDataOnJsonPath(Response resp,String jsonXpath)
	{
		List<Object>list=JsonPath.read(resp.asString(), jsonXpath);
		return list.get(0).toString();
	}
	/**
	 * get the xmldata from based on xml complex path
	 * @param resp
	 * @param xmlpath
	 * @return
	 */
	
	public String getDataOnXpathPath(Response resp,String xmlpath)
	{
		return resp.xmlPath().get(xmlpath);
	}
	/**
	 * verify the data in jsonbody based jsonpath
	 * @param resp
	 * @param jsonXpath
	 * @param expectedData
	 * @return
	 */
	
	public boolean VerifyDataOnJsonPath(Response resp,String jsonXpath,String expectedData)
	{
		List<String>list=JsonPath.read(resp.asString(),jsonXpath);
		boolean flag=false;
		for(String str:list)
		{
			if(str.equals(expectedData))
			{
				System.out.println(expectedData+"is available ==Pass");
				flag=true;
			}
		}
		if(flag==false)
		{
			System.out.println(expectedData+"is not available ==fail");
		}
		return flag;
	}
	
	public String getAccessToken()
	{
		Response resp= given()
				.formParam("client_id", "ninza-cilent")
				.formParam("client_secret","")
				.formParam("grant_type","client_credentials")
				.when()
				.post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");
		resp.then()
		.log().all();
		String token=resp.jsonPath().get("access_token");
		return token;
	}
}

