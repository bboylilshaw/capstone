package org.jasonxiao.service;

import org.jasonxiao.model.Group;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Jason Xiao
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
@ComponentScan
public class GroupServiceImplTest {

    @Autowired
    GroupService groupService;

    @Test
    public void getAll() throws Exception {
        List<Group> result = groupService.getAll();
        Assert.assertEquals(10, result.size());
    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}