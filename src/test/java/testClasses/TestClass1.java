package testClasses;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.io.File;

import org.testng.annotations.Test;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.restassured.module.jsv.JsonSchemaValidator;

public class TestClass1 {
	

	@Test
	public void getMethodSample() {
		baseURI = "https://reqres.in/";
		given()
			.get("api/users")
		.then()
			.assertThat()
				.statusCode(200)
				.body("total", equalTo(12))
				.body("total_pages", equalTo(2))
				.body("data[0].first_name", equalTo("George"))
				.body("data[1].first_name", equalTo("Janet"))
				.body("data[2].first_name", equalTo("Emma"))
		.log().all();
	}
	
	@Test
	public void advanceAssertionsExamples() {
		
		given()
			.get("api/users")
		.then()
			.assertThat()
				.statusCode(200)
				.body("total", equalTo(12))
				.body("total_pages", equalTo(2))
				.body("data.first_name", hasItems("George", "Janet", "Emma"))
		.log().all();
	}
	
	@Test
	public void jsonSchemaValidatorExample() {
		baseURI = "https://reqres.in/";
		final String jsonScemaFilePath="src/test/resources/schema/schema.json";
		
		File file = new File(jsonScemaFilePath);
		given()
			.get("api/users")
		.then()
			.assertThat()
				.statusCode(200)
				.body(JsonSchemaValidator.matchesJsonSchema(file))
		.log().all();
	}
}
