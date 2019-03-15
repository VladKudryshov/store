package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.InstagramConstants;
import org.brunocvcunha.instagram4j.requests.InstagramDirectShareRequest;
import org.brunocvcunha.instagram4j.requests.InstagramFollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramLoginResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Instagram4j instagram = Instagram4j.builder().username("375255443163").password("vv12xm12").build();
        instagram.setup();
        try {
            InstagramLoginResult login = instagram.login();
            getFollowersByUserName(instagram);
            int a = 2;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void getFollowersByUserName(Instagram4j instagram) throws IOException, InterruptedException {
        HashSet<Object> objects = Sets.newHashSet();
        InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest("4karapuzika.by"));
        String page = StringUtils.EMPTY;
        while (true) {
            String s = getFollowers(instagram, userResult.getUser().getPk() ,page);

            ObjectMapper objectMapper = new ObjectMapper();
            PageUser pageUser = objectMapper.readValue(s, PageUser.class);
            objects.addAll((ArrayList)pageUser.getUsers());
            if (StringUtils.isBlank(pageUser.getNext_max_id())) {
                break;
            }
            page = pageUser.getNext_max_id();
            Thread.sleep(1000);
        }
        return;
    }

    private static String getFollowers(Instagram4j instagram,Long id, String nextPage) throws IOException {
        String uri = "https://i.instagram.com/api/v1/friendships/ID/following?".replace("ID", id.toString());
        if (StringUtils.isNotBlank(nextPage)) {
            uri += "max_id=" + nextPage;
        }
        HttpGet get1 = new HttpGet(uri);
        get1.addHeader("Connection", "close");
        get1.addHeader("Accept", "*/*");
        get1.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        get1.addHeader("Cookie2", "$Version=1");
        get1.addHeader("Accept-Language", "en-US");
        get1.addHeader("User-Agent", InstagramConstants.USER_AGENT);

        HttpResponse response = instagram.getClient().execute(get1);

        String content = EntityUtils.toString(response.getEntity());
        instagram.setLastResponse(response);

        get1.releaseConnection();

        return content;
    }
}
