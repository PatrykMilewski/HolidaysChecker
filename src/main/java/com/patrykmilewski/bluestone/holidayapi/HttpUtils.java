package com.patrykmilewski.bluestone.holidayapi;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;
import org.jetbrains.annotations.NotNull;

import java.util.List;

abstract class HttpUtils {
    
    /**
     * Mapping {@code org.apache.http.client.fluent.Form} into GET parameters in plain text.
     *
     * @param form List of Key and Value pairs, that will be used as GET Key and Values.
     * @return GET parameters as plain text chain.
     */
    @NotNull
    static String toGetParams(@NotNull Form form) {
        List<NameValuePair> formAsList = form.build();
        
        if (formAsList.size() == 0)
            return "";
            
        StringBuilder sb = new StringBuilder();
    
        formAsList.forEach(keyValues -> {
            sb.append(keyValues.getName()).append("=").append(keyValues.getValue()).append("&");
        });
        
        sb.deleteCharAt(sb.lastIndexOf("&"));
        
        return sb.toString();
    }
    
}
