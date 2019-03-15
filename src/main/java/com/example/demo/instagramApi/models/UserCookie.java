package com.example.demo.instagramApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.Serializable;
import java.util.Date;

public class UserCookie implements Serializable {
    private String name;
    private String value;
    private String cookieDomain;
    private Date expiryDate;
    private String cookiePath;
    private Boolean isSecure;
    private Integer version;

    public UserCookie(BasicClientCookie cookie) {
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.cookieDomain = cookie.getDomain();
        this.cookiePath = cookie.getPath();
        this.expiryDate = cookie.getExpiryDate();
        this.isSecure = cookie.isSecure();
        this.version = cookie.getVersion();
    }

    public UserCookie() {
    }

    @JsonIgnore
    public Cookie getCookie() {
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setSecure(isSecure);
        cookie.setPath(cookiePath);
        cookie.setExpiryDate(expiryDate);
        cookie.setDomain(cookieDomain);
        return cookie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public Boolean getSecure() {
        return isSecure;
    }

    public void setSecure(Boolean secure) {
        isSecure = secure;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
