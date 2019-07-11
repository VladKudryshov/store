package com.example.demo.service;

import com.example.demo.models.blog.Post;
import com.example.demo.models.blog.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBlogService {
    Post getPost(Integer id);

    List<Post> getAllShortPosts();

    Page<Story> getStory(Pageable pageable);

    Page<Post> getPostPage(Pageable pageable);

    void create(Post post);

    Post updatePost(Post post);

    void delete(Integer id);
}
