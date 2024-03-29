package pt.ua.deti.ies.ex2;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    // build create Employee REST API
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // build get employee by id REST API
    // http://localhost:8080/api/employees/1
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employee, employee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // Build Get All Employees REST API
    // http://localhost:8080/api/employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(name = "email", required = false) String email) {
        List<Employee> employees;
        if (email != null) {
            Employee employee = employeeService.getEmployeeByEmail(email);
            return new ResponseEntity<>(employee != null ? List.of(employee) : List.of(),
                                        employee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        }

        employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Build Update Employee REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/employees/1
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody Employee employee) {
        employee.setId(employeeId);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, updatedEmployee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // Build Delete Employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee successfully deleted!", HttpStatus.OK);
    }
}