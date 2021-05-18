package restassured;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class Request {

    @Step("Send GET request: endpoint - {endpoint}, expected status code - {statusCode}")
    public static Response get(String endpoint, int statusCode) {
        Response response = given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(statusCode)
                .log()
                .body()
                .extract()
                .response();
        attachResponse(response);
        return response;
    }

    @Step("Send POST request: body - {object}, endpoint - {endpoint}, expected status code - {statusCode}")
    public static Response post(Object object, String endpoint, int statusCode) {
        Response response = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(endpoint)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
        attachResponse(response);
        return response;
    }

    @Step("Send PUT request: body - {object}, endpoint - {endpoint}, expected status code - {statusCode}")
    public static Response put(Object object, String endpoint, int statusCode) {
        Response response = given()
                .contentType(JSON)
                .body(object)
                .when()
                .put(endpoint)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
        attachResponse(response);
        return response;
    }

    @Step("Send PATCH request: body - {object}, endpoint - {endpoint}, expected status code - {statusCode}")
    public static Response patch(Object object, String endpoint, int statusCode) {
        Response response = given()
                .contentType(JSON)
                .body(object)
                .when()
                .patch(endpoint)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
        attachResponse(response);
        return response;
    }

    @Step("Send DELETE request: endpoint - {endpoint}, expected status code - {statusCode}")
    public static Response delete(String endpoint, int statusCode) {
        Response response = given()
                .when()
                .delete(endpoint)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
        attachResponse(response);
        return response;
    }

    @Attachment(value = "response", type = "application/json", fileExtension = ".json")
    public static String attachResponse(Response response) {
        return response.asString();
    }
}
