package com.example.demo.instagramApi.requests;

import com.example.demo.instagramApi.models.response.FollowersResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class GetUserFollowersRequest extends AbstractGetRequest<FollowersResponse> {

    private String userId;
    private String nextPage;

    public GetUserFollowersRequest(String userId) {
        this.userId = userId;
    }

    public GetUserFollowersRequest(String userId, String nextPage) {
        this.userId = userId;
        this.nextPage = nextPage;
    }

    @Override
    protected String getEndpoint() {
        if (StringUtils.isNotBlank(nextPage)) {
            URLCodec urlCodec = new URLCodec();
            try {
                return "/friendships/" + userId + "/followers?max_id=" + urlCodec.encode(nextPage);
            } catch (EncoderException ig) {
                throw new RuntimeException("Can't get next page");
            }
        }
        return "/friendships/" + userId + "/followers";
    }

    @Override
    protected String getBody() throws Exception {
        return null;
    }

    @Override
    public FollowersResponse parseResult(int resultCode, String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, FollowersResponse.class);
    }
}
