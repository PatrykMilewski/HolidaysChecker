package com.patrykmilewski.bluestone.holidayapi;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Form;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

/**
 * API consumer for {@code holidayapi.com}
 */
@Service
public class HolidaysApi {

    private HttpClientWrapper holidayApiHttpClient;

    @Autowired
    HolidaysApi(HttpClientWrapper holidayApiHttpClient) {
        this.holidayApiHttpClient = holidayApiHttpClient;
    }
    
    /**
     * It is accepting country code based on ISO 3166-2 and LocalDate, where only YYYY-MM-DD matters.
     *
     * The result is mapped from JSON to HttpClientWrapper class with {@code com.google.code.gson}
     *
     * @param countryCode Country code based on ISO 3166-2.
     * @param localDate LocalDate that will be checked for holidays in given country.
     * @return Mapped response from JSON.
     */
    @NotNull
    @Cacheable("holidaysApiInfo")
    public HolidaysApiResponse getHolidaysNames(@NotNull String countryCode, @NotNull LocalDate localDate) {
        
        Form form = Form.form()
                .add("country", countryCode)
                .add("year", Integer.toString(localDate.getYear()))
                .add("month", Integer.toString(localDate.getMonthValue()))
                .add("day", Integer.toString(localDate.getDayOfMonth()));
        
        try {
            HttpResponse httpResponse = holidayApiHttpClient.sendGETRequest(form);
            
            return processHttpResponse(httpResponse);
            
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    
    }
    
    @NotNull
    private HolidaysApiResponse processHttpResponse(@NotNull HttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), "HTTP GET request failed.");
        }
    
        String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
        
        return new Gson().fromJson(jsonResponse, HolidaysApiResponse.class);
    }
    
}
