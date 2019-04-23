package com.example.demo.models.blog;

public class Story {

    private Integer id;
    private String title;
    private String content;

    public Story(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent().split("[.]",2)[0] + "...";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
