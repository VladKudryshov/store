package com.example.demo.instagramApi.requests;

import com.example.demo.instagramApi.models.response.CommentsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class GetMediaCommentsRequest extends AbstractGetRequest<CommentsResponse> {

    private String mediaId;
    private String nextPage;

    public GetMediaCommentsRequest(String mediaId) {
        this.mediaId = mediaId;
    }

    public GetMediaCommentsRequest(String mediaId, String nextPage) {
        this.mediaId = mediaId;
        this.nextPage = nextPage;
    }

    @Override
    protected String getEndpoint() {
        if (StringUtils.isNotBlank(nextPage)) {
            URLCodec urlCodec = new URLCodec();
            try {
                return "/media/" + mediaId + "/comments?max_id=" + urlCodec.encode(nextPage);
            } catch (EncoderException ig) {
                throw new RuntimeException("Can't get next page");
            }
        }
        return "/media/" + mediaId + "/comments";
    }

    @Override
    protected String getBody() throws Exception {
        return null;
    }

    @Override
    public CommentsResponse parseResult(int resultCode, String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, CommentsResponse.class);
    }
}
