package org.jasonxiao.service;

import org.jasonxiao.exception.EmployeeNotFoundException;
import org.jasonxiao.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
public interface EmployeeService {
    List<Employee> getAll();
    Optional<Employee> getById(Long id);
    Employee add(Employee employee);
    Employee update(Long id, Employee employee) throws EmployeeNotFoundException, IOException;
    void delete(Long id);
}
