package com.example.demo.instagramApi.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;
import org.brunocvcunha.instagram4j.InstagramConstants;
import org.brunocvcunha.instagram4j.util.InstagramHashUtil;

public abstract class AbstractPostRequest<T> extends AbstractBaseRequest<T> {

    @Override
    public T execute(BasicCookieStore userCookies) throws Exception {
        HttpPost post = new HttpPost(HOST_API + getEndpoint());
        post.addHeader("Connection", "close");
        post.addHeader("Accept", "*/*");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.addHeader("Cookie2", "$Version=1");
        post.addHeader("Accept-Language", "en-US");
        post.addHeader("User-Agent", InstagramConstants.USER_AGENT);

        String body = getBody();
        String payload = InstagramHashUtil.generateSignature(body);

        post.setEntity(new StringEntity(payload));

        client.setCookieStore(userCookies);


        HttpResponse response = client.execute(post);

        int statusCode = response.getStatusLine().getStatusCode();

        String content = EntityUtils.toString(response.getEntity());

        post.releaseConnection();

        return parseResult(statusCode, content);
    }
}
