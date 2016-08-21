package org.jasonxiao.controller;

import org.jasonxiao.exception.HotelNotFoundException;
import org.jasonxiao.model.Employee;
import org.jasonxiao.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author Jason Xiao
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        logger.info("Start to get all employees");
        return ResponseEntity.ok(employeeService.getAll());
    }

    @RequestMapping("/employee/{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Long id) {
        logger.info("Start to get employee with id: {}", id);
        if (!employeeService.getById(id).isPresent()) {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employeeService.getById(id).get());
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        logger.info("Start to add a new employee");
        return new ResponseEntity<>(employeeService.add(employee), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,
                                                   @RequestBody Employee hotel) throws IOException, HotelNotFoundException {
        logger.info("Start to update employee with id: {}", id);
        return ResponseEntity.ok(employeeService.update(id, hotel));
    }
}