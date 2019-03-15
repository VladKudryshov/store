package com.example.demo.instagramApi.requests;

import com.example.demo.instagramApi.models.InstagramPayload;
import com.example.demo.instagramApi.models.SelfUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LoginRequest extends AbstractPostRequest<SelfUser> {

    private InstagramPayload payload;

    @Override
    protected String getEndpoint() {
        return "/accounts/login/";
    }

    @Override
    protected String getBody() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(payload);
    }

    public void setPayload(InstagramPayload payload) {
        this.payload = payload;
    }

    @Override
    public SelfUser parseResult(int resultCode, String content) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
        JsonObject user = jsonObject.getAsJsonObject("logged_in_user");

        SelfUser selfUser = new SelfUser();
        selfUser.setId(user.get("pk").getAsLong());
        return null;
    }
}
