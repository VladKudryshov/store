package com.example.demo.instagramApi.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Optional;

public abstract class AbstractBaseRequest<T> {

    DefaultHttpClient client;

    static final String HOST_API = "https://i.instagram.com/api/v1";

    public void setup() {
        this.client = new DefaultHttpClient();
        this.client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        this.client.setCookieStore(new BasicCookieStore());
    }

    protected abstract String getEndpoint();

    protected abstract String getBody() throws Exception;

    public abstract T execute(BasicCookieStore userCookies) throws Exception;

    public abstract T parseResult(int resultCode, String content);

    public Optional<Cookie> getCSRFToken() {
        return client
                .getCookieStore()
                .getCookies()
                .stream()
                .filter(cookie -> cookie.getName().equalsIgnoreCase("csrftoken"))
                .findFirst();
    }

    public BasicCookieStore getCookieStore() {
        return (BasicCookieStore) client.getCookieStore();
    }

}
