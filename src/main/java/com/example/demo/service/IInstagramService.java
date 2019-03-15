package com.example.demo.service;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.IOException;

public interface IInstagramService {

    BasicCookieStore authService(String userName, String password);

    String getFollowers(String username) throws IOException;

}
