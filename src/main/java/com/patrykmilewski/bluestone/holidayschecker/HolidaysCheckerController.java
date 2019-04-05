package com.patrykmilewski.bluestone.holidayschecker;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Slf4j
@RestController
class HolidaysCheckerController implements HolidaysCheckerControllerDocs {
    
    private HolidaysFinder holidaysFinder;
    
    @Value("${holidayapi.inFuture.warning}")
    private boolean checkingInFutureWarning;
    
    @Autowired
    HolidaysCheckerController(HolidaysFinder holidaysFinder) {
        this.holidaysFinder = holidaysFinder;
    }
    
    @NotNull
    @Cacheable("holidaysCheck")
    public String checkHolidays(String date, String country1, String country2) {
    
        if (Strings.isNullOrEmpty(date) || Strings.isNullOrEmpty(country1) || Strings.isNullOrEmpty(country2)) {
            throw new IllegalArgumentException("Bad request. One of required arguments is null or empty.");
        }
        
        LocalDate localDate = LocalDate.parse(date);
        
        if (checkingInFutureWarning && LocalDate.now().compareTo(localDate) < 0) {
            log.warn("Checking holidays date in the future. External API requires premium API key for this.");
        }
        
        HolidaysCheckerResponse response = holidaysFinder.findFirstInCommon(localDate, country1, country2);
    
        return new Gson().toJson(response);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
    
}
