package com.example.demo.service.impl;

import com.example.demo.dao.IBlogDAO;
import com.example.demo.models.blog.Post;
import com.example.demo.models.blog.Story;
import com.example.demo.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogService implements IBlogService {

    @Autowired
    IBlogDAO blogDAO;

    @Override
    public Post getPost(Integer id) {
        return blogDAO.findOne(id);
    }

    @Override
    public Page<Story> getStory(Pageable pageable) {
        Page<Post> posts = blogDAO.findAll(pageable);
        return posts.map(Story::new);
    }

    @Override
    public Page<Post> getPostPage(Pageable pageable) {
        return blogDAO.findAll(pageable);
    }

    @Override
    public void create(Post post) {
        blogDAO.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return blogDAO.save(post);
    }

    @Override
    public void delete(Integer id) {
        blogDAO.delete(id);
    }
}
