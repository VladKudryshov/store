package com.example.demo;

import java.util.List;

public class PageUser {
    private String sections;
    private Object users;
    private Boolean big_list;
    private String next_max_id;
    private Integer page_size;
    private String status;

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public Object getUsers() {
        return users;
    }

    public void setUsers(Object users) {
        this.users = users;
    }

    public Boolean getBig_list() {
        return big_list;
    }

    public void setBig_list(Boolean big_list) {
        this.big_list = big_list;
    }

    public String getNext_max_id() {
        return next_max_id;
    }

    public void setNext_max_id(String next_max_id) {
        this.next_max_id = next_max_id;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
