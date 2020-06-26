package com.mirth.connect.userutil;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class RestBuilder {

    public String get(String url) throws IOException {
        HttpGet httpMethod = new HttpGet(url);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpMethod);
        return IOUtils.toString(response.getEntity().getContent());
    }
    public String post(String url, String body) throws IOException {
        HttpPost httpMethod = new HttpPost(url);
        httpMethod.addHeader("content-type", "application/json");
        StringEntity userEntity = new StringEntity(body);
        httpMethod.setEntity(userEntity);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpMethod);
        return IOUtils.toString(response.getEntity().getContent());
    }

}

