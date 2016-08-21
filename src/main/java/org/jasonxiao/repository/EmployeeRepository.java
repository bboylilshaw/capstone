package org.jasonxiao.repository;

import org.jasonxiao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason Xiao
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
