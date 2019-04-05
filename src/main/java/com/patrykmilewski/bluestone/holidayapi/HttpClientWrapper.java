package com.patrykmilewski.bluestone.holidayapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
class HttpClientWrapper {

    private HttpClient httpClient;

    @Value("${holidayapi.acceptAllCerts}")
    private boolean acceptAllCerts;

    private ApiConfiguration apiConfiguration;

    @Autowired
    HttpClientWrapper(ApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
        httpClient = acceptAllCerts ? setupAcceptAllHttpClient() : HttpClients.createDefault();
    }
    
    @NotNull
    private HttpClient setupAcceptAllHttpClient() {
        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (certificate, authType) -> true).build();

        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException impossible) {
            throw new RuntimeException(impossible);
        }

        return HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }
    

    
    /**
     * Sends GET request to fixed endpoint from {@code holidayapi.com}
     *
     * @param request Keys and Values for GET request
     * @return {@code org.apache.http.HttpResponse} from request execution.
     * @throws IOException Exception may be thrown in case of a problem or the connection was aborted.
     */
    @NotNull
    HttpResponse sendGETRequest(@NotNull Form request) throws IOException {
        request.add("key", apiConfiguration.getHolidayApiKey());
        
        String getParams = HttpUtils.toGetParams(request);
        HttpGet httpRequest = new HttpGet(toGetRequestWithAddress(getParams));

        httpRequest.setHeader("content-type", "application/json");

        return httpClient.execute(httpRequest);
    }
    
    @NotNull
    HttpResponse sendPOSTRequest(@NotNull Form body, @NotNull String endpoint) {
        throw new RuntimeException("Operation not supported.");
    }
    
    @NotNull
    private String toGetRequestWithAddress(@NotNull String endpoint) {
        return apiConfiguration.getHolidayApiAddress() + "?" + endpoint;
    }

}
