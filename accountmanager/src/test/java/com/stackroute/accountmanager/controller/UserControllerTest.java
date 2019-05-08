package com.stackroute.accountmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.accountmanager.domain.User;
import com.stackroute.accountmanager.service.SecurityTokenGenerator;
import com.stackroute.accountmanager.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private SecurityTokenGenerator securityTokenGenerator;

    private User user;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User("John","John123");
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }


//    @Test
//    public void testSaveUserSuccess() throws Exception{
//        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(user);
//        mockMvc.perform(post("/api/v1/accountmanager/save")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonToString(user)))
//                .andExpect(status().isCreated())
//                .andDo(print());
//
//        Mockito.verify(userService, Mockito.times(1)).saveUser(Mockito.any());
//
//    }

    @Test
    public void testUserLoginSuccess() throws Exception{

        Mockito.when(userService.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
        mockMvc.perform(post("/api/v1/accountmanager/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(user)))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(userService, Mockito.times(1)).findByUsernameAndPassword(user.getUsername(), user.getPassword());

    }


    private static String jsonToString(final Object obj) throws JsonProcessingException {
        String result;
        try{
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "Json Processing error";
        }
        return result;
    }

}
