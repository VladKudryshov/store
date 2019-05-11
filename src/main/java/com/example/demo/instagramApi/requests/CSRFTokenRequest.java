package com.example.demo.instagramApi.requests;

import com.example.demo.instagramApi.utils.InstagramGenericUtil;
import org.apache.http.cookie.Cookie;

import java.util.Optional;

public class CSRFTokenRequest extends AbstractGetRequest<Optional<Cookie>> {
    @Override
    protected String getEndpoint() {
        return "/si/fetch_headers/?challenge_type=signup&guid=" + InstagramGenericUtil.generateUUID(false);
    }

    @Override
    protected String getBody() {
        return null;
    }

    @Override
    public Optional<Cookie> parseResult(int resultCode, String content) {
        return getCSRFToken();
    }
}
