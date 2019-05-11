package com.example.demo.instagramApi.requests;

import com.example.demo.instagramApi.models.response.LikersResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GetMediaLikersRequest extends AbstractGetRequest<LikersResponse> {

    private String mediaId;

    public GetMediaLikersRequest(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    protected String getEndpoint() {
        return "/media/" + mediaId + "/likers";
    }

    @Override
    protected String getBody() throws Exception {
        return null;
    }

    @Override
    public LikersResponse parseResult(int resultCode, String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, LikersResponse.class);
        } catch (IOException e) {
            return null;
        }
    }
}
