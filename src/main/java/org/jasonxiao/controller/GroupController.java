package org.jasonxiao.controller;

import org.jasonxiao.model.Group;
import org.jasonxiao.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        logger.info("Start to get all groups");
        return ResponseEntity.ok(groupService.getAll());
    }
}
