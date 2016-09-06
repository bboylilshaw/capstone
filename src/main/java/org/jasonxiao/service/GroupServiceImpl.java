package org.jasonxiao.service;

import org.jasonxiao.exception.GroupNotFoundException;
import org.jasonxiao.model.Group;
import org.jasonxiao.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAll() {
        logger.info("Get all groups from repository");
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> get(Long id) {
        logger.info("Get group with id: {} from repository", id);
        Group group = groupRepository.findOne(id);
        return Optional.of(group);
    }

    @Override
    public Group add(Group group) {
        logger.info("Save group to repository");
        return groupRepository.save(group);
    }

    @Override
    public Group update(Long id, Group newGroup) throws GroupNotFoundException {
        Assert.notNull(newGroup, "Group object cannot be null");
        if (!groupRepository.exists(id)) {
            logger.error("Group not found with id: {}", id);
            throw new GroupNotFoundException(id.toString());
        }
        Group originalGroup = groupRepository.findOne(id);
        BeanUtils.copyProperties(newGroup, originalGroup, "employees");
        Group updatedGroup = groupRepository.save(originalGroup);
        logger.info("Updated group with id: {}", id);
        return updatedGroup;
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        groupRepository.delete(id);
    }
}
