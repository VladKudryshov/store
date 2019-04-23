package com.example.demo.dao;

import com.example.demo.models.blog.Post;
import com.example.demo.models.blog.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogDAO extends JpaRepository<Post, Integer> {

}
