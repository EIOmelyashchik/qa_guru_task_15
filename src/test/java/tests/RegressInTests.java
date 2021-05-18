package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Employee;
import model.Page;
import model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import restassured.Request;
import restassured.RestAssuredSteps;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Verify 5 requests on 'https://reqres.in'")
public class RegressInTests {
    private final RestAssuredSteps restAssured = new RestAssuredSteps();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Rest assured")
    @DisplayName("Verify successful GET request")
    void checkUsersList() {
        Page page = restAssured.extractPageAfterGetRequest();
        List<User> userList = page.getUsers();

        Allure.step("Check that response contains: \"page\":2", () ->
                assertThat(page.getPage()).as("Page").isEqualTo(2));

        Allure.step("Check that response contains data about 6 users", () ->
                assertThat(userList.size()).as("Number of users").isEqualTo(6));

        userList.forEach(user -> {
            String email = user.getEmail();
            Allure.step(String.format("Check that email '%s' has a correct format", email), () ->
                    assertThat(email).as("E-mail")
                            .matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"));
        });
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Rest assured")
    @DisplayName("Verify successful POST request")
    void checkCreationNewUser() {
        Employee employee = new Employee("morpheus", "leader");

        Employee employeeRes = restAssured.extractEmployeeAfterPostRequest(employee);

        Allure.step("Check that response contains data about new user:\n" + employee, () ->
                assertThat(employeeRes).as("New user").isEqualTo(employee));
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Rest assured")
    @DisplayName("Verify successful PUT request")
    void checkEditingUserByPut() {
        Employee employee = new Employee("morpheus", "leader");

        Employee employeeRes = restAssured.extractEmployeeAfterPutRequest(employee);

        Allure.step("Check that response contains updated data about user:\n" + employee, () ->
                assertThat(employeeRes).as("Updated user").isEqualTo(employee));
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Rest assured")
    @DisplayName("Verify successful PATCH request")
    void checkEditingUserByPatch() {
        Employee employee = new Employee("morpheus", "leader");

        Employee employeeRes = restAssured.extractEmployeeAfterPatchRequest(employee);

        Allure.step("Check that response contains updated data about user:\n" + employee, () ->
                assertThat(employeeRes).as("Updated user").isEqualTo(employee));
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Rest assured")
    @DisplayName("Verify successful DELETE request")
    void checkDeletingUser() {
        Response response = Request.delete("/api/users/2", 204);
        Allure.step("Check that body response is empty", () ->
                response.then().body(Matchers.is("")));
    }

    @Test
    @Owner("omelyashchik")
    @Feature("Rest assured")
    @DisplayName("Verify unsuccessful POST request")
    void checkUnsuccessfulLogin() {
        Response response = Request.post(new User("peter@klaven"), "/api/login", 400);
        Allure.step("Check that response contains: \"error\":\"Missing password\"", () ->
                response.then().body("error", is("Missing password")));
    }
}
