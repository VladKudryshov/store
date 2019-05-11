package com.example.demo.instagramApi.utils;

import java.util.UUID;

public class InstagramGenericUtil {

    public static String generateUUID(boolean dash) {
        String uuid = UUID.randomUUID().toString();

        if (dash) {
            return uuid;
        }

        return uuid.replaceAll("-", "");
    }
}
