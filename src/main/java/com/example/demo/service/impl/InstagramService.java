package com.example.demo.service.impl;

import com.example.demo.dao.InstaDAO;
import com.example.demo.instagramApi.models.InstagramPayload;
import com.example.demo.instagramApi.requests.CSRFTokenRequest;
import com.example.demo.instagramApi.requests.LoginRequest;
import com.example.demo.models.insta.InstaCookies;
import com.example.demo.service.IInstagramService;
import com.example.demo.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.brunocvcunha.instagram4j.InstagramConstants;
import org.brunocvcunha.instagram4j.requests.payload.InstagramLoginPayload;
import org.brunocvcunha.instagram4j.util.InstagramGenericUtil;
import org.brunocvcunha.instagram4j.util.InstagramHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstagramService implements IInstagramService {

    @Autowired
    IUserService userService;

    @Autowired
    InstaDAO instaDAO;

    private DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

    private static String generateUuid(boolean dash) {
        String uuid = UUID.randomUUID().toString();

        if (dash) {
            return uuid;
        }

        return uuid.replaceAll("-", "");
    }

    @Override
    public BasicCookieStore authService(String username, String password) {

        try {
            CSRFTokenRequest csrfTokenRequest = new CSRFTokenRequest();
            csrfTokenRequest.setup();
            Optional<Cookie> execute = csrfTokenRequest.execute(new BasicCookieStore());

            execute.ifPresent(token->{
                InstagramPayload payload = InstagramPayload.builder()
                        .username(username)
                        .password(password)
                        .token(token.getValue())
                        .build();

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setup();
                loginRequest.setPayload(payload);
                try {
                    loginRequest.execute(csrfTokenRequest.getCookieStore());
                    int a = 2;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            int a = 2;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void postExecute(DefaultHttpClient defaultHttpClient, InstagramLoginPayload payload) {
        try {
            HttpPost post = new HttpPost(InstagramConstants.API_URL + "accounts/login/");
            post.addHeader("Connection", "close");
            post.addHeader("Accept", "*/*");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            post.addHeader("Cookie2", "$Version=1");
            post.addHeader("Accept-Language", "en-US");
            post.addHeader("User-Agent", InstagramConstants.USER_AGENT);
            ObjectMapper mapper = new ObjectMapper();

            String payloadJson = mapper.writeValueAsString(payload);
            post.setEntity(new StringEntity(generateSignature(payloadJson)));

            HttpResponse response = defaultHttpClient.execute(post);

            String content = EntityUtils.toString(response.getEntity());

            InstaCookies cookies = new InstaCookies();
            cookies.setUserId(userService.getAuthenticatedUser().getId());
            BasicCookieStore cookieStore = (BasicCookieStore) defaultHttpClient.getCookieStore();
            cookies.setCookies(cookieStore);
            instaDAO.save(cookies);

            getString("kudr9tov");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFollowers(String username) throws IOException {

        return null;
    }

    private String getString(String username) throws IOException, ClassNotFoundException {

        String id = userService.getAuthenticatedUser().getId();
        InstaCookies byUserId = instaDAO.findByUserId(id);

        // Read objects

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        defaultHttpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        defaultHttpClient.setCookieStore(byUserId.getCookies());

        try {
            String uri = "https://i.instagram.com/api/v1/users/ID/usernameinfo".replace("ID", username);

            HttpGet get = new HttpGet(uri);
            get.addHeader("Connection", "close");
            get.addHeader("Accept", "*/*");
            get.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            get.addHeader("Cookie2", "$Version=1");
            get.addHeader("Accept-Language", "en-US");
            get.addHeader("User-Agent", InstagramConstants.USER_AGENT);

            HttpResponse response = defaultHttpClient.execute(get);

            String content = EntityUtils.toString(response.getEntity());

            get.releaseConnection();

            return content;

        } catch (IOException e) {
            return StringUtils.EMPTY;
        }
    }


    private String generateSignature(String payload) throws UnsupportedEncodingException {
        String parsedData = URLEncoder.encode(payload, "UTF-8");

        String signedBody = generateHash(InstagramConstants.API_KEY, payload);

        return "ig_sig_key_version=" + InstagramConstants.API_KEY_VERSION + "&signed_body=" + signedBody + '.'
                + parsedData;

    }

    private String generateHash(String key, String string) {
        SecretKeySpec object = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init((Key) object);
            byte[] byteArray = mac.doFinal(string.getBytes("UTF-8"));
            return new String(new Hex().encode(byteArray), "ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
