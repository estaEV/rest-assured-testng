package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeSuite;
import utils.ConfigFactory;
import utils.EmployeeAlreadyCreatedException;
import utils.GlobalVar;
import utils.NonExistingEmployeeException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static utils.ConfigFactory.getManualRequestSpec;
import static utils.ConfigFactory.getManualResponseSpec;

public class StepDefinitionRestAssured implements GlobalVar {
    Set<Integer> createdEmployeesIds = new HashSet<Integer>();

    //    @Test(description = "POST a new user")
    @Given("A post request with the required values is made")
    public void postNewUser(DataTable data) {
        List<List<String>> dataList = data.asLists(String.class);
        StringBuilder body = new StringBuilder();
                    body.append("{\"name\":\"")
                        .append(dataList.get(0).get(0))
                        .append("\",\"salary\":\"")
                        .append(dataList.get(0).get(1))
                        .append("\",\"age\":\"")
                        .append(dataList.get(0).get(2))
                        .append("\"}");

        try {
            int currentUserId = RestAssured
                    .given()
                    .spec(getManualRequestSpec())
//                    .body("{\"name\":Eugene,\"salary\":\"someRand\",\"age\":\"26\"}")
                    .body(body.toString())
                    .when()
                    .post(GlobalVar.POST_URL)
                    .then()
                    .spec(getManualResponseSpec())
                    .body("status", equalTo("success"))
                    .body("data.id", notNullValue())
                    .body("message", equalTo("Successfully! Record has been added."))
                    .log().body()
                    .extract()
                    .path("data.id");

            if (createdEmployeesIds.contains(currentUserId))
                throw new EmployeeAlreadyCreatedException("That string is coming from utils.EmployeeAlreadyCreatedException \"throw new\" inside the try block.");
            else {
                createdEmployeesIds.add(currentUserId);
            }

        } catch (EmployeeAlreadyCreatedException e) {
            System.out.println("Here we have a sout from the utils.EmployeeAlreadyCreatedException catch block. BTW the retrieved ID already exists in out custom set.");
        }
    }

    @Given("Delete request for ID {int} has been made")
    //    @Test(description = "DELETE a new user")
    public void deleteUser(int arg0) {
        RestAssured
                .given()
                .spec(getManualRequestSpec())
                .when()
                .delete(GlobalVar.DELETE_URL + arg0)
                .then()
                .spec(getManualResponseSpec())
                .body("status", equalTo("success"))
                .body("data", equalTo(String.valueOf(arg0)))
                .body("message", equalTo("Successfully! Record has been deleted"))
                .log().body();
    }

    @Given("Get employee with ID {int} request is made")
//    @Test(description = "GET a specific user")
    public void getASpecificUser(int arg0) {
        try {
            ValidatableResponse response = RestAssured
                    .given()
                    .spec(getManualRequestSpec())
                    .when()
                    .get(GlobalVar.GET_URL + String.valueOf(arg0))
                    .then()
                    .spec(getManualResponseSpec())
                    .body("status", equalTo("success"))
                    .body("data.id", equalTo(arg0))
                    .body("message", equalTo("Successfully! Record has been fetched."))
                    .log().body();

            if (response.extract().statusCode() >= 300) {
                throw new NonExistingEmployeeException("That string is coming from utils.NonExistingEmployeeException \"throw new\" inside the try block.");
            }
        } catch (NonExistingEmployeeException e) {
            System.out.println("Here we have a sout from the utils.NonExistingEmployeeException catch block. BTW the retrieved ID already exists in out custom set.");
        }
    }

    @Given("Get all employees request is made")
    //    @Test(description = "GET all users")
    public void getAllUsers() {
        ValidatableResponse response = RestAssured
                .given()
                .spec(getManualRequestSpec())
                .when()
                .get(GlobalVar.GET_ALL_URL)
                .then()
                .spec(getManualResponseSpec())
                .body("status", equalTo("success"))
                .body("message", equalTo("Successfully! All records has been fetched."))
                .body("data.employee_name", hasItems("Doris Wilder", "Bradley Greer", "Tatyana Fitzpatrick"))
                .log().body();
    }


}
