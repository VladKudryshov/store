package com.example.demo.instagramApi.models.response;

public class ReportResponse {
    private String username;
    private String fullName;
    private String comment;
    private Boolean isLiked;
    private Boolean isFollowed;
    private Integer peopleInComment;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

    public Integer getPeopleInComment() {
        return peopleInComment;
    }

    public void setPeopleInComment(Integer peopleInComment) {
        this.peopleInComment = peopleInComment;
    }
}
