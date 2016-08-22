package org.jasonxiao.repository;

import org.jasonxiao.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason Xiao
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
}
