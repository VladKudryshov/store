package com.example.demo.service;

import com.example.demo.instagramApi.models.User;
import com.example.demo.instagramApi.models.response.MediaResponse;
import com.example.demo.instagramApi.models.response.ReportResponse;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.List;

public interface IInstagramService {

    BasicCookieStore authService(String userName, String password);

    User getUserByName(String username) throws Exception;

    MediaResponse getUserMedia(String userId);

    List<ReportResponse> getReport(String mediaId, String userId) throws Exception;

}
