package com.example.demo.dao;

import com.example.demo.models.insta.InstaFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstaFilesDAO extends JpaRepository<InstaFiles, String> {
    List<InstaFiles> findByUserIdOrderByIdDesc(String s);
}
