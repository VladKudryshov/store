package com.example.demo.instagramApi.models.response;

import com.example.demo.instagramApi.models.Comment;
import com.example.demo.instagramApi.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsResponse {
    private List<Comment> comments = Lists.newLinkedList();
    private Integer comment_count;
    private Boolean has_more_comments;
    private String next_max_id;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Boolean getHas_more_comments() {
        return has_more_comments;
    }

    public void setHas_more_comments(Boolean has_more_comments) {
        this.has_more_comments = has_more_comments;
    }

    public String getNext_max_id() {
        return next_max_id;
    }

    public void setNext_max_id(String next_max_id) {
        this.next_max_id = next_max_id;
    }
}
