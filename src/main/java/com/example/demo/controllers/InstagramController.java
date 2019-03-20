package com.example.demo.controllers;

import com.example.demo.service.IInstagramService;
import org.apache.http.impl.client.BasicCookieStore;
import org.bytedeco.javacv.FrameFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "api/intagram")
public class InstagramController {

    @Autowired
    IInstagramService instragramService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void auth(HttpServletResponse res, @RequestBody Map<String, String> params) {
        BasicCookieStore basicClientCookie =
                instragramService.authService(params.get("username"), params.get("password"));

    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public String getUserName(@PathVariable String username) {
        try {
            return instragramService.getFollowers(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
