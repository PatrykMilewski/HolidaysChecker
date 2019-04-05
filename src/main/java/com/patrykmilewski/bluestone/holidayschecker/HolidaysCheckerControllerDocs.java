package com.patrykmilewski.bluestone.holidayschecker;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holidays")
@Api(tags = "HolidaysCheckerController")
public interface HolidaysCheckerControllerDocs {
    
    @ApiOperation(value = "Returns names of holidays in common for given countries in the same date.", response = HolidaysCheckerResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
            @ApiResponse(code = 400, message = "Bad request, invalid arguments")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "From date where searching starts", required = true, paramType = "query"),
            @ApiImplicitParam(name = "country1", value = "First country code", required = true, paramType = "query"),
            @ApiImplicitParam(name = "country2", value = "Second country code", required = true, paramType = "query")
    })
    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = "application/json")
    String checkHolidays(String date, String country1, String country2);
    
}
