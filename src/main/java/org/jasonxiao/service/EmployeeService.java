package org.jasonxiao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jasonxiao.exception.HotelNotFoundException;
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
    Employee update(Long id, Employee employee) throws HotelNotFoundException, IOException;
    void delete(Long id);
}
