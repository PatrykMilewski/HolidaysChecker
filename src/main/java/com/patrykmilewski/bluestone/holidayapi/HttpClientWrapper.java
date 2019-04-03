package com.patrykmilewski.bluestone.holidayapi;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

abstract class HttpClient {
    
    org.apache.http.client.HttpClient httpClient;
    
    public HolidayHttpClientSecured(boolean acceptAllCerts) {
        httpClient = acceptAllCerts ? setupAcceptAllHttpClient() : HttpClients.createDefault();
    }
    
    private org.apache.http.client.HttpClient setupAcceptAllHttpClient() {
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
    
    public HttpResponse sendPOSTRequest(Form body, String endpoint) throws IOException {
        JSONObject jsonObject = toJsonObject(body);
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        
        HttpPost httpRequest = new HttpPost(endpoint);
        httpRequest.setEntity(stringEntity);
        
        return httpClient.execute(httpRequest);
    }
    
    public HttpResponse sendGETRequest(Form body, String endpoint) {
        throw new RuntimeException("Operation not supported.");
    }
    
    JSONObject toJsonObject(Form body) {
        List<NameValuePair> keyValuesList = body.build();
        JSONObject convertedJson = new JSONObject();
        keyValuesList.forEach(pair -> convertedJson.put(pair.getName(), pair.getValue()));
        return convertedJson;
    }
    
    Header[] toHeadersArray(Form form) {
        List<NameValuePair> list = form.build();
        return list.stream()
                .map(nameValuePair -> new BasicHeader(nameValuePair.getName(), nameValuePair.getValue()))
                .toArray(Header[]::new);
    }
    
}
