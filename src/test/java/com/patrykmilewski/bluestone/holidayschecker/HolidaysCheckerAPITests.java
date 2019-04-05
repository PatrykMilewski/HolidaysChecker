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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "dev")
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class HolidaysCheckerAPITests {

    @Autowired
    private MockMvc mvc;
    
    @Test
    public void HolidaysCheckerFindsChristmas() throws Exception {
        
        mvc.perform(
                get("/holidays/check")
                        .param("date", "2018-12-23")
                        .param("country1", "PL")
                        .param("country2", "US")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.date").value("2018-12-25"))
                .andExpect(jsonPath("$.name1").value("Pierwszy dzień Bożego Narodzenia"))
                .andExpect(jsonPath("$.name2").value("Christmas Day"));
    }
    
    @Test
    public void HolidaysCheckerFindsMultipleHolidays() throws Exception {
        
        mvc.perform(
                get("/holidays/check")
                        .param("date", "2016-12-24")
                        .param("country1", "PL")
                        .param("country2", "US")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.date").value("2016-12-25"))
                .andExpect(jsonPath("$.name1").value("Pierwszy dzień Bożego Narodzenia"))
                .andExpect(jsonPath("$.name2").value("Christmas Day, First Day of Hanukkah"));
        
        // "First Day of Hanukkah" is Jewish holiday
    }
    
}
