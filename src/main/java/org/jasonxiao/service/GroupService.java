package org.jasonxiao.service;

import org.jasonxiao.exception.GroupNotFoundException;
import org.jasonxiao.model.Group;

import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
public interface GroupService {
    List<Group> getAll();
    Optional<Group> get(Long id);
    Group add(Group group);
    Group update(Group newGroup) throws GroupNotFoundException;
    void delete(Long id);
}
