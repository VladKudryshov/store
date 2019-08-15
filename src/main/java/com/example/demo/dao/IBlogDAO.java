package com.example.demo.dao;

import com.example.demo.models.blog.Post;

import java.util.List;

public interface IBlogDAO {
    List<Post> getAllShortPosts();

    Post getPostById(Integer id);

    void removeById(Integer id);
}
