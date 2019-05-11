package com.example.demo.instagramApi.requests;

import com.example.demo.instagramApi.models.response.MediaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

public class GetMediaRequest extends AbstractGetRequest<MediaResponse> {

    private String userId;

    public GetMediaRequest(String userId) {
        this.userId = userId;
    }

    @Override
    protected String getEndpoint() {
        return "/feed/user/" + userId;
    }

    @Override
    protected String getBody() throws Exception {
        return null;
    }

    @Override
    public MediaResponse parseResult(int resultCode, String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content,MediaResponse.class);
        } catch (IOException e) {
            return null;
        }
    }
}
