package com.example.demo.service.impl;

import com.example.demo.dao.IBlogDAO;
import com.example.demo.models.blog.Post;
import com.example.demo.models.blog.Story;
import com.example.demo.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements IBlogService {

    @Autowired
    IBlogDAO blogDAO;

    @Override
    public Post getPost(Integer id) {
        return blogDAO.getPostById(id);
    }

    @Override
    public List<Post> getAllShortPosts() {
        return blogDAO.getAllShortPosts();
    }

    @Override
    public Page<Story> getStory(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> getPostPage(Pageable pageable) {
        return null;
    }

    @Override
    public void create(Post post) {

    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
