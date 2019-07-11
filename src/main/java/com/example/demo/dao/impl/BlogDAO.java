package com.example.demo.dao.impl;

import com.example.demo.dao.IBlogDAO;
import com.example.demo.models.Statistic;
import com.example.demo.models.blog.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogDAO implements IBlogDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Post> getAllShortPosts() {
        return namedParameterJdbcTemplate
                .getJdbcOperations()
                .query("SELECT p.*, s.viewers, comments" +
                        " from post p" +
                        " INNER JOIN matching_statistic ms ON p.id = ms.obj_id AND ms.type = 'POST'" +
                        " LEFT JOIN statistic s ON s.id = ms.matching_id", ((resultSet, i) -> {
                    Post post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setTitle(resultSet.getString("title"));
                    post.setContent(resultSet.getString("short_content"));
                    post.setTag(resultSet.getString("tag"));
                    post.setDate(resultSet.getDate("date"));
                    post.setAuthor(resultSet.getString("author"));

                    Statistic statistic = new Statistic();
                    statistic.setViewers(resultSet.getInt("viewers"));
                    statistic.setComments(resultSet.getInt("comments"));

                    post.setStatistic(statistic);
                    return post;
                }));
    }
}
