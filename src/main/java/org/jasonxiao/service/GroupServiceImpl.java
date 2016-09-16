package org.jasonxiao.service;

import org.jasonxiao.exception.GroupNotFoundException;
import org.jasonxiao.model.Group;
import org.jasonxiao.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
@Service
@CacheConfig(cacheNames = "groups")
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Cacheable(key = "'all.groups'")
    @Override
    @Transactional
    public List<Group> getAll() {
        logger.info("Get all groups from repository");
        return groupRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Group> get(Long id) {
        Assert.notNull(id, "Group id cannot be null");
        logger.info("Get group with id: {} from repository", id);
        Group group = groupRepository.findOne(id);
        return Optional.of(group);
    }

    @Override
    @Transactional
    public Group add(Group group) {
        Assert.notNull(group, "Group object cannot be null");
        Assert.isNull(group.getId(), "Group id must be null");
        logger.info("Save group to repository");
        return groupRepository.save(group);
    }

    @Override
    public Group update(Group newGroup) throws GroupNotFoundException {
        Assert.notNull(newGroup, "Group object cannot be null");
        Assert.notNull(newGroup.getId(), "Group id cannot be null");
        Long id = newGroup.getId();
        if (!groupRepository.exists(id)) {
            logger.error("Cannot find group with id: {}", id);
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
        Assert.notNull(id, "Group id cannot be null");
        groupRepository.delete(id);
    }
}
