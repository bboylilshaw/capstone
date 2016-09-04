package org.jasonxiao.controller;

import org.jasonxiao.exception.GroupNotFoundException;
import org.jasonxiao.model.Employee;
import org.jasonxiao.model.Group;
import org.jasonxiao.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author Jason Xiao
 */
@RestController
public class GroupController extends ApiBaseController {
    
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> getAllGroups() {
        logger.info("Start to get all groups");
        return ResponseEntity.ok(groupService.getAll());
    }

    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
    public ResponseEntity getGroup(@PathVariable Long id) {
        logger.info("Start to get group with id: {}", id);
        Optional<Group> group = groupService.get(id);
        if (!group.isPresent()) {
            logger.warn("Cannot find any group with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(group.get());
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ResponseEntity addGroup(@RequestBody Group group) {
        Assert.notNull(group);
        Assert.isNull(group.getId());
        logger.info("Start to add a new group");
        Group savedGroup = groupService.add(group);
        return ResponseEntity
                .created(URI.create("/api/group/" + savedGroup.getId()))
                .body(savedGroup);
    }

    @RequestMapping(value = "/group/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateGroup(@PathVariable Long id,
                                      @RequestBody Group newGroup) throws GroupNotFoundException {
        logger.info("Start to update group with id: {}", id);
        Group updatedGroup = groupService.update(id, newGroup);
        return ResponseEntity.accepted().body(updatedGroup);
    }

    @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        logger.info("Start to delete group with id: {}", id);
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/group/{id}/employees", method = RequestMethod.GET)
    public ResponseEntity getGroupEmployees(@PathVariable Long id) {
        logger.info("Start to get employees in group id: {}", id);
        Optional<Group> group = groupService.get(id);
        if (group.isPresent()) {
            List<Employee> employees = group.get().getEmployees();
            return ResponseEntity.ok(employees);
        }
        return ResponseEntity.notFound().build();
    }

}
