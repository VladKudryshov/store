package com.example.demo.dao;

import com.example.demo.models.insta.InstaCookies;
import com.example.demo.models.orders.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface InstaDAO extends JpaRepository<InstaCookies, String> {
    InstaCookies findByUserId(String s);
}
