package apitest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Firsttest {

	//@Test
	public static void main(String args[]) {
		
		
		
		RestAssured.baseURI="https://maps.googleapis.com";

		Response Resp= given().
				param("query","restaurantsin Airoli"). 
				param("key","AIzaSyCVnEXESiM6tJJblutyn3hsh6ag3-6VPJo").
				when().
				get("/maps/api/place/textsearch/json").
				then().assertThat().statusCode(200).and().
				contentType(ContentType.JSON).and().log().all().
				body("status",equalTo("OK")).and().  
				body("results[0].name",equalTo("The Mint Leaf")). 
				body("results[0].id",equalTo("75afddfe57ad26e765213386026bf29227e6cd5f")).and(). 
				header("server","scaffolding on HTTPServer2").and().
				header("content-encoding","gzip").extract().
				response();

		//Above will give raw data that needs to be converted into String First

		String res=Resp.asString();
		System.out.println("JSON Response after converting into String is"+res);

		//Now need to convert String into JSON as only JSON Validations are supported

		JsonPath js=new JsonPath(res);

		int count=js.get("results.size()");
		System.out.println("Total Place Name count is"+count);

		for (int i=0;i<count;i++)
		{
			String PlaceName=js.get("results["+i+"].name");
			System.out.println("Restrutant Name in Airoli is\t"+PlaceName);
		}

		String Placeid=js.get("results[1].place_id");
		System.out.println("Place id is\t"+Placeid);
	}


}	
	