package com.patrykmilewski.bluestone.holidayapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "dev")
public class HolidaysApiTests {
    
    @Autowired
    private HolidaysApi holidaysApi;
    
    @Test
    public void HolidaysApiRequestsReturnsValidResponse() {
        // given
        LocalDate date = LocalDate.of(2018, 12, 25);
        
        // when
        HolidaysApiResponse resposne = holidaysApi.getHolidaysNames("PL", date);
        List<HolidaysNameAndData> holidaysList = resposne.getHolidaysNameAndData();
        
        // then
        assertEquals(1, resposne.getHolidaysNameAndData().size());
        assertEquals("Pierwszy dzień Bożego Narodzenia", holidaysList.get(0).getName());
    }
    
}
