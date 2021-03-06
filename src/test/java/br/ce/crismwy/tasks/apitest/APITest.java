package br.ce.crismwy.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
			;
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2030-12-30\" }").contentType(ContentType.JSON)
			//.log().all()
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			//.log().all()
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }").contentType(ContentType.JSON)
			//.log().all()
		.when()
			.post("/todo")
		.then()
			//.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
			//.log().all()
			;
	}
	
	@Test
	public void deveRemoverTarefaComSucesso () {
		Integer id = RestAssured.given()
			.body("{ \"task\": \"Teste para Remo??o\", \"dueDate\": \"2030-12-30\" }").contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			//.log().all()
			.statusCode(201)
			.extract().path("id")
		;
		//System.out.println(id);
		
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204)
		;
	}
}