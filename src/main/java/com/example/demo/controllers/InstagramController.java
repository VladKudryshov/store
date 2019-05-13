package com.example.demo.controllers;

import com.example.demo.instagramApi.models.User;
import com.example.demo.instagramApi.models.response.MediaResponse;
import com.example.demo.instagramApi.models.response.ReportResponse;
import com.example.demo.models.insta.InstaFiles;
import com.example.demo.service.IInstagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/intagram")
public class InstagramController {

    @Autowired
    IInstagramService instragramService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void auth(HttpServletResponse res, @RequestBody Map<String, String> params) {
        instragramService.authService(params.get("username"), params.get("password"));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public User getUser(@RequestParam() String username) {
        try {
            return instragramService.getUserByName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "media", method = RequestMethod.GET)
    public MediaResponse getUserMedia(@RequestParam String userId) {
        try {
            return instragramService.getUserMedia(userId);
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "report", method = RequestMethod.GET)
    public List<ReportResponse> getReport(@RequestParam String userId) {
        try {
            return instragramService.getReport("2032713312453778187_10212391882", userId);
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "reports", method = RequestMethod.GET)
    public List<InstaFiles> getReport() {
        try {
            return instragramService.getReports();
        } catch (Exception e) {
            return null;
        }
    }


}
