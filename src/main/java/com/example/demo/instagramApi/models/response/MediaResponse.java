package com.example.demo.instagramApi.models.response;

import com.example.demo.instagramApi.models.Media;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaResponse {
    private List<Media> items = Lists.newLinkedList();
    private String next_max_id;

    public List<Media> getItems() {
        return items;
    }

    public void setItems(List<Media> items) {
        this.items = items;
    }

    public String getNext_max_id() {
        return next_max_id;
    }

    public void setNext_max_id(String next_max_id) {
        this.next_max_id = next_max_id;
    }
}
