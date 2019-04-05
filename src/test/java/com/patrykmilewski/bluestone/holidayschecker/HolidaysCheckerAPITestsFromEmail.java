package com.patrykmilewski.bluestone.holidayschecker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "dev")
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class HolidaysCheckerAPITestsFromEmail {
    
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void CheckForHolidaysWithExampleFromEmail() throws Exception {
        
        mvc.perform(
                get("/holidays/check")
                        .param("date", "2016-03-27")
                        .param("country1", "PL")
                        .param("country2", "NO")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.date").value("2016-03-27"))
                .andExpect(jsonPath("$.name1").value("Niedziela Wielkanocna"))
                .andExpect(jsonPath("$.name2").value("Påske"));      // will fail due to holidayapi.com
                                                                                            // not having this data
    }
    
}
