package com.example.demo.controllers;

import com.example.demo.models.blog.Post;
import com.example.demo.models.blog.Story;
import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;
import com.example.demo.service.IBlogService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "api/blog")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Post> getPostPage() {
        return blogService.getAllShortPosts();
    }

    @RequestMapping(value = "stories", method = RequestMethod.GET)
    public Page<Story> getStories() {
        return blogService.getStory(new PageRequest(0, 3));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable Integer id) {
       return blogService.getPost(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createPost(@RequestBody Post post) {
        blogService.create(post);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Post updatePost(@RequestBody Post post) {
        return blogService.updatePost(post);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable Integer id) {
        blogService.delete(id);
    }

}
