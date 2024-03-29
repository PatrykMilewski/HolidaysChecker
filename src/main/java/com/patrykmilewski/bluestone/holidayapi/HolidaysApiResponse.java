package com.patrykmilewski.bluestone.holidayapi;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

/**
 * Class that represents JSON response from {@code holidayapi.com}
 *
 * It is used for mappings from and to {@code com.google.code.gson}
 * @see <a href="https://github.com/google/gson"></a>
 */
@Getter
public class HolidaysApiResponse {
    
    private int status;
    
    @SerializedName("holidays")
    private List<HolidaysNameAndData> holidaysNameAndData;
    
}
