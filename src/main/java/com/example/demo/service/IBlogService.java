package com.example.demo.service;

import com.example.demo.models.blog.Post;
import com.example.demo.models.blog.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBlogService {
    Post getPost(Integer id);

    Page<Story> getStory(Pageable pageable);

    Page<Post> getPostPage(Pageable pageable);

    void create(Post post);

    Post updatePost(Post post);

    void delete(Integer id);
}
