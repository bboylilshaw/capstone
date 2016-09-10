package org.jasonxiao.controller;

import org.jasonxiao.exception.EmployeeNotFoundException;
import org.jasonxiao.model.Employee;
import org.jasonxiao.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
@RestController
public class EmployeeController extends ApiBaseController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam(value = "groupId", required = false) Long groupId) {
        List<Employee> employees;
        if (groupId != null) {
            logger.info("Start to get employees in group with id {}", groupId);
            employees = employeeService.getByGroupId(groupId);
        } else {
            logger.info("Start to get all employees");
            employees = employeeService.getAll();
        }

        return ResponseEntity.ok(employees);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployee(@PathVariable("id") Long id) {
        logger.info("Start to get employee with id: {}", id);
        Optional<Employee> employee = employeeService.get(id);
        if (!employee.isPresent()) {
            logger.warn("Cannot find any employee with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee.get());
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        Assert.notNull(employee);
        Assert.isNull(employee.getId());
        logger.info("Start to add a new employee");
        Employee savedEmployee = employeeService.add(employee);
        return ResponseEntity
                .created(URI.create("/api/employee/" + savedEmployee.getId()))
                .body(savedEmployee);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateEmployee(@PathVariable("id") Long id,
                                         @RequestBody Employee newEmployee) throws IOException, EmployeeNotFoundException {
        logger.info("Start to update employee with id: {}", id);
        newEmployee.setId(id);
        Employee updatedEmployee = employeeService.update(newEmployee);
        return ResponseEntity.accepted().body(updatedEmployee);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployee(@PathVariable("id") Long id) {
        logger.info("Start to delete employee with id: {}", id);
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
