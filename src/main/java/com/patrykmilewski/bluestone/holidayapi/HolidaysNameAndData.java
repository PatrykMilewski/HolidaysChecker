package com.patrykmilewski.bluestone.holidayapi;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Class that represents single holiday in given date from {@code holidayapi.com} JSON response.
 *
 * It is used for mappings from and to {@code com.google.code.gson}
 * @see <a href="https://github.com/google/gson"></a>
 */
@Getter
public class HolidaysNameAndData {
    
    private String name;
    
    private String date;
    
    private String observed;
    
    @SerializedName("public")
    private String publicField;
}
