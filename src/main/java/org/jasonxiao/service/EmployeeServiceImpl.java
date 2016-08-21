package org.jasonxiao.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasonxiao.exception.HotelNotFoundException;
import org.jasonxiao.model.Employee;
import org.jasonxiao.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll() {
        logger.info("Get all employees from repository");
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getById(Long id) {
        logger.info("Get employee with id {} from repository", id);
        return Optional.ofNullable(employeeRepository.findOne(id));
    }

    @Override
    public Employee add(Employee employee) {
        Assert.notNull(employee, "Employee Object cannot be null");
        logger.info("Save employee to repository");
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id, Employee updatedEmployee) throws HotelNotFoundException, IOException {
        Assert.notNull(updatedEmployee, "Employee object cannot be null");
        if (!employeeRepository.exists(id)) {
            throw new HotelNotFoundException();
        }
        Employee oldEmployee = employeeRepository.findOne(id);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.readerForUpdating(oldEmployee).readValue(objectMapper.writeValueAsBytes(updatedEmployee));
        return employeeRepository.save(oldEmployee);
    }
}
