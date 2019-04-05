package com.patrykmilewski.bluestone.holidayschecker;

import com.patrykmilewski.bluestone.holidayapi.HolidaysApi;
import com.patrykmilewski.bluestone.holidayapi.HolidaysApiResponse;
import com.patrykmilewski.bluestone.holidayapi.HolidaysNameAndData;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
class HolidaysFinder {
    
    /**
     * Maximum amount of a days, that application will check through, while looking for common holiday.
     */
    @Value("${holidaysChecker.maxLookupInDays}")
    private int maxLookupInDays;
    
    /**
     *  Format of returned date, that will match formats accepted by DateTimeFormatter
     *  @see <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html"></a>
     */
    @Value("${holidaysChecker.dateFormat}")
    private String dateFormat;
    
    private HolidaysApi holidaysApi;
    
    @Autowired
    HolidaysFinder(HolidaysApi holidaysApi) {
        this.holidaysApi = holidaysApi;
    }
    
    @NotNull HolidaysCheckerResponse findFirstInCommon(@NotNull LocalDate startDate,
                                                       @NotNull String countryFirst,
                                                       @NotNull String countrySecond) {
        
        for (int i = 0; i < maxLookupInDays; i++) {
            HolidaysApiResponse responseFirstCountry = holidaysApi.getHolidaysNames(countryFirst, startDate);
            
            if (responseFirstCountry.getHolidaysNameAndData().size() != 0) {
                
                HolidaysApiResponse responseSecondCountry = holidaysApi.getHolidaysNames(countrySecond, startDate);
                
                return createResponse(responseFirstCountry, responseSecondCountry, startDate);
            }
    
            startDate = startDate.plusDays(1);
        }
        
        log.warn("Common holiday not found for countries {} and {}. Passed max lookup period in days ({}), " +
                "returning empty response. ", countryFirst, countrySecond, maxLookupInDays);
        
        return createEmptyResponse(countryFirst, countrySecond);
    }
    
    @NotNull
    private HolidaysCheckerResponse createEmptyResponse(String countryFirst, String countrySecond) {
        LocalDate unknownDate = LocalDate.ofEpochDay(0);
        
        return new HolidaysCheckerResponse(dateToSimpleString(unknownDate), countryFirst, countrySecond);
    }
    
    @NotNull
    private HolidaysCheckerResponse createResponse(HolidaysApiResponse responseFirstCountry,
                                                   HolidaysApiResponse responseSecondCountry, LocalDate startDate) {
        
        String date = dateToSimpleString(startDate);
        
        String firstCountryHolidaysName = convertHolidaysListToString(responseFirstCountry.getHolidaysNameAndData());
        String secondCountryHolidaysName = convertHolidaysListToString(responseSecondCountry.getHolidaysNameAndData());
        
        return new HolidaysCheckerResponse(date, firstCountryHolidaysName, secondCountryHolidaysName);
    }
    
    @NotNull
    private String convertHolidaysListToString(List<HolidaysNameAndData> holidaysNameAndData) {
        
        if (holidaysNameAndData.size() == 0)
            return "";
        
        if (holidaysNameAndData.size() == 1)
            return holidaysNameAndData.get(0).getName();
        
        StringBuilder sb = new StringBuilder();
        
        for (HolidaysNameAndData singleHolidaysData : holidaysNameAndData) {
            sb.append(singleHolidaysData.getName()).append(", ");
        }
        
        // remove last comma and space
        sb.delete(sb.length() - 2, sb.length());
        
        return sb.toString();
    }
    
    @NotNull
    private String dateToSimpleString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
