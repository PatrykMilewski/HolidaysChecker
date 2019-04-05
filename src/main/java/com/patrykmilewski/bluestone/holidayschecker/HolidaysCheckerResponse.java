package com.patrykmilewski.bluestone.holidayschecker;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
class HolidaysCheckerResponse {
    
    @ApiModelProperty(value = "Date of the holidays in given format based on configuration.", required = true, example = "2018-12-24")
    private String date;
    
    @SerializedName("name1")
    @ApiModelProperty(value = "Name of the holidays in country1", required = true, example = "Pierwszy dzień Bożego Narodzenia")
    private String name1;
    
    @SerializedName("name2")
    @ApiModelProperty(value = "Name of the holidays in country2", required = true, example = "Christmas Day")
    private String name2;
    
}
