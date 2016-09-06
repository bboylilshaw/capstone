package org.jasonxiao.repository;

import org.jasonxiao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Jason Xiao
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QueryDslPredicateExecutor<Employee> {

    @Override
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.group")
    List<Employee> findAll();

    @Query("SELECT e FROM Employee e WHERE e.group.id = :groupId")
    List<Employee> findByGroupId(@Param("groupId") Long groupId);
}
