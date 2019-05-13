package com.example.demo.instagramApi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {
    private Long pk;
    private String id;
    private Integer comment_count;
    private Integer carousel_media_count;
    private Integer like_count;
    private Caption caption;
    private List<CarouselMedia> carousel_media;
    private ImageVersions image_versions2;
    private User user;

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Integer getCarousel_media_count() {
        return carousel_media_count;
    }

    public void setCarousel_media_count(Integer carousel_media_count) {
        this.carousel_media_count = carousel_media_count;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Caption getCaption() {
        return caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public List<CarouselMedia> getCarousel_media() {
        return carousel_media;
    }

    public void setCarousel_media(List<CarouselMedia> carousel_media) {
        this.carousel_media = carousel_media;
    }

    public ImageVersions getImage_versions2() {
        return image_versions2;
    }

    public void setImage_versions2(ImageVersions image_versions2) {
        this.image_versions2 = image_versions2;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
