package com.example.demo.models.insta;

import org.apache.http.impl.client.BasicCookieStore;

import javax.persistence.*;

@Entity
@Table(name = "insta_cookies")
public class InstaCookies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private BasicCookieStore cookies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BasicCookieStore getCookies() {
        return cookies;
    }

    public void setCookies(BasicCookieStore cookies) {
        this.cookies = cookies;
    }
}
