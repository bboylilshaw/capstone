package org.jasonxiao.controller;

import org.jasonxiao.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Jason Xiao
 */
@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GroupService groupService;

    @Test
    public void getAllGroups() throws Exception {
        when(this.groupService.getAll()).thenReturn(Collections.emptyList());
//        given(this.groupService.getAll()).willReturn(Collections.emptyList());
        this.mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getGroup() throws Exception {

    }

    @Test
    public void addGroup() throws Exception {

    }

    @Test
    public void updateGroup() throws Exception {

    }

    @Test
    public void deleteGroup() throws Exception {

    }

    @Test
    public void getGroupEmployees() throws Exception {

    }

}