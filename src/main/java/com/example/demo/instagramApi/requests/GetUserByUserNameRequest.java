package com.example.demo.instagramApi.requests;

import com.example.demo.instagramApi.models.Media;
import com.example.demo.instagramApi.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Objects;

public class GetUserByUserNameRequest extends AbstractGetRequest<User> {

    private String username;

    public GetUserByUserNameRequest(String username) {
        this.username = username;
    }

    @Override
    protected String getEndpoint() {
        return "/users/" + username + "/usernameinfo";
    }

    @Override
    protected String getBody() throws Exception {
        return null;
    }

    @Override
    public User parseResult(int resultCode, String content) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(content, JsonObject.class);


        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
           user = objectMapper.readValue(jsonObject.get("user").toString(), User.class);
        } catch (IOException e) {
            System.out.println("Can't parse");
        }
        return user;
    }
}
