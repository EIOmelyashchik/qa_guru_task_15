package restassured;

import model.Employee;
import model.Page;

public class RestAssuredSteps {

    public Page extractPageAfterGetRequest() {
        return Request.get("/api/users?page=2", 200)
                .as(Page.class);
    }

    public Employee extractEmployeeAfterPostRequest(Employee employee) {
        return Request.post(employee, "/api/users", 201)
                .as(Employee.class);
    }

    public Employee extractEmployeeAfterPutRequest(Employee employee) {
        return Request.put(employee, "/api/users", 200)
                .as(Employee.class);
    }

    public Employee extractEmployeeAfterPatchRequest(Employee employee) {
        return Request.patch(employee, "/api/users", 200)
                .as(Employee.class);
    }
}
