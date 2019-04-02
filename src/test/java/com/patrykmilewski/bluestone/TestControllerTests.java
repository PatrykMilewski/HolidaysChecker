package com.patrykmilewski.bluestone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "production")
public class TestControllerTests {
    
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void DefaultProfileLoads() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/test/hello")).andReturn().getResponse();
        
        assertEquals(200, response.getStatus());
        assertEquals("Hello world from production profile!", response.getContentAsString());
    }
    
}
