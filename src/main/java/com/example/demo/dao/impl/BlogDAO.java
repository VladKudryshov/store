package com.example.demo.dao.impl;

import com.example.demo.dao.IBlogDAO;
import com.example.demo.models.Statistic;
import com.example.demo.models.blog.Post;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BlogDAO implements IBlogDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Post> getAllShortPosts() {
        return namedParameterJdbcTemplate
                .getJdbcOperations()
                .query("SELECT p.id, p.title, p.short_content as content, p.tag, p.date, p.author, s.viewers, comments" +
                        " from post p" +
                        " INNER JOIN matching_statistic ms ON p.id = ms.obj_id AND ms.type = 'POST'" +
                        " LEFT JOIN statistic s ON s.id = ms.matching_id", ((resultSet, i) -> transformPost(resultSet)));
    }

    @Override
    public Post getPostById(Integer id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", id);
        return namedParameterJdbcTemplate
                .queryForObject("SELECT p.id, p.title, p.content, p.tag, p.date, p.author, s.viewers, comments" +
                        " from post p" +
                        " INNER JOIN matching_statistic ms ON p.id = ms.obj_id AND ms.type = 'POST'" +
                        " LEFT JOIN statistic s ON s.id = ms.matching_id" +
                        " WHERE p.id = :id", params, (resultSet, i) -> transformPost(resultSet));
    }

    @Override
    public void removeById(Integer id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", id);
        namedParameterJdbcTemplate
                .update("DELETE FROM post p" +
                        " WHERE p.id = :id", params);
    }

    private Post transformPost(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getInt("id"));
        post.setTitle(resultSet.getString("title"));
        post.setContent(resultSet.getString("content"));
        post.setTag(resultSet.getString("tag"));
        post.setDate(resultSet.getDate("date"));
        post.setAuthor(resultSet.getString("author"));

        Statistic statistic = new Statistic();
        statistic.setViewers(resultSet.getInt("viewers"));
        statistic.setComments(resultSet.getInt("comments"));

        post.setStatistic(statistic);
        return post;
    }
}
