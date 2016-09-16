package org.jasonxiao.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasonxiao.exception.EmployeeNotFoundException;
import org.jasonxiao.model.Employee;
import org.jasonxiao.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
@Service
@CacheConfig(cacheNames = "employees")
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable(key = "'all.employees'")
    @Override
    @Transactional
    public List<Employee> getAll() {
        logger.info("Get all employees from repository");
        List<Employee> employees = employeeRepository.findAll();
        return employees == null ? Collections.emptyList() : employees;
    }

    @Override
    public Optional<Employee> get(Long id) {
        Assert.notNull(id, "Employee id cannot be null");
        logger.info("Get employee with id {} from repository", id);
        return Optional.ofNullable(employeeRepository.findOne(id));
    }

    @Override
    public Employee add(Employee employee) {
        Assert.notNull(employee, "Employee object cannot be null");
        Assert.isNull(employee.getId(), "Employee id field must be null");
        logger.info("Save employee to repository");
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee newEmployee) throws EmployeeNotFoundException, IOException {
        Assert.notNull(newEmployee, "Employee object cannot be null");
        Assert.notNull(newEmployee.getId(), "Employee id cannot be null");
        Long id = newEmployee.getId();
        if (!employeeRepository.exists(id)) {
            throw new EmployeeNotFoundException(id.toString());
        }
        Employee oldEmployee = employeeRepository.findOne(id);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        objectMapper.readerForUpdating(oldEmployee)
                .readValue(objectMapper.writeValueAsBytes(newEmployee));

        return employeeRepository.save(oldEmployee);
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "Employee id cannot be null");
        logger.info("Delete employee with id: {} from repository", id);
        employeeRepository.delete(id);
    }

    @Override
    public List<Employee> getByGroupId(Long groupId) {
        Assert.notNull(groupId, "Group id cannot be null");
        logger.info("Get employee by group id {} from repository", groupId);
        List<Employee> employees = employeeRepository.findByGroupId(groupId);
        return employees == null ? Collections.emptyList() : employees;
    }
}
