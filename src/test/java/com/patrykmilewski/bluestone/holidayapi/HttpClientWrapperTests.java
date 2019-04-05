package com.patrykmilewski.bluestone.holidayapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "dev")
public class HttpClientWrapperTests {

    @Autowired
    HttpClientWrapper httpClientWrapper;
    
    @Test
    public void HttpClientWrapperRequestReturns200() throws IOException {
        Form request = Form.form()
                .add("country", "PL")
                .add("year", "2018")
                .add("month", "12")
                .add("day", "25");
        
        HttpResponse response = httpClientWrapper.sendGETRequest(request);
        
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }
    
}
