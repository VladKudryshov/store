package com.example.demo.instagramApi.models;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class InstagramPayload {
    private String username;
    private String phone_id;
    private String _csrftoken;
    private String guid;
    private String device_id;
    private String password;
    private int login_attempt_account = 0;

    public static InstagramPayload builder(){
        return new InstagramPayload();
    }

    public InstagramPayload username(String username){
        this.username = username;
        return this;
    }

    public InstagramPayload password(String password){
        this.password = password;
        return this;
    }

    public InstagramPayload token(String token){
        this._csrftoken = token;
        return this;
    }

    public InstagramPayload build(){
        String seed = DigestUtils.md5Hex(username + password);
        String volatileSeed = "12345";
        this.device_id =  "android-" + DigestUtils.md5Hex(seed + volatileSeed).substring(0, 16);
        this.phone_id = UUID.randomUUID().toString();
        this.guid = UUID.randomUUID().toString();
        return this;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }

    public String get_csrftoken() {
        return _csrftoken;
    }

    public void set_csrftoken(String _csrftoken) {
        this._csrftoken = _csrftoken;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLogin_attempt_account() {
        return login_attempt_account;
    }

    public void setLogin_attempt_account(int login_attempt_account) {
        this.login_attempt_account = login_attempt_account;
    }
}
