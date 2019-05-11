package com.example.demo.instagramApi.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.brunocvcunha.instagram4j.InstagramConstants;

public abstract class AbstractGetRequest<T> extends AbstractBaseRequest<T> {

    @Override
    public T execute(BasicCookieStore userCookies) throws Exception {
        String uri = HOST_API + getEndpoint();

        HttpGet get = new HttpGet(uri);
        get.addHeader("Connection", "close");
        get.addHeader("Accept", "*/*");
        get.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        get.addHeader("Cookie2", "$Version=1");
        get.addHeader("Accept-Language", "en-US");
        get.addHeader("User-Agent", InstagramConstants.USER_AGENT);

        client.setCookieStore(userCookies);

        HttpResponse response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        String content = EntityUtils.toString(response.getEntity());

        get.releaseConnection();

        return parseResult(statusCode, content);
    }
}
