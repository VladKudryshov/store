package com.example.demo.instagramApi.models;

public class Push {

    String device_type = "android_mqtt";
    Boolean is_main_push_channel = Boolean.TRUE;
    String phone_id;
    String[] device_token;
    String _csrftoken;
    String guid;
    String _uuid;
    String users;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public Boolean getIs_main_push_channel() {
        return is_main_push_channel;
    }

    public void setIs_main_push_channel(Boolean is_main_push_channel) {
        this.is_main_push_channel = is_main_push_channel;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }

    public String[] getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String[] device_token) {
        this.device_token = device_token;
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

    public String get_uuid() {
        return _uuid;
    }

    public void set_uuid(String _uuid) {
        this._uuid = _uuid;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
