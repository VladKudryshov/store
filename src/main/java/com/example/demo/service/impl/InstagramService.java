package com.example.demo.service.impl;

import com.example.demo.dao.InstaDAO;
import com.example.demo.dao.InstaFilesDAO;
import com.example.demo.instagramApi.models.Comment;
import com.example.demo.instagramApi.models.InstagramPayload;
import com.example.demo.instagramApi.models.User;
import com.example.demo.instagramApi.models.response.*;
import com.example.demo.instagramApi.requests.*;
import com.example.demo.models.insta.InstaCookies;
import com.example.demo.models.insta.InstaFiles;
import com.example.demo.service.IInstagramService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.FTPUtils;
import com.example.demo.utils.XLSUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InstagramService implements IInstagramService {

    @Autowired
    IUserService userService;

    @Autowired
    InstaDAO instaDAO;

    @Autowired
    InstaFilesDAO instaFilesDAO;


    @Override
    public BasicCookieStore authService(String username, String password) {

        try {
            CSRFTokenRequest csrfTokenRequest = new CSRFTokenRequest();
            csrfTokenRequest.setup();
            Optional<Cookie> execute = csrfTokenRequest.execute(new BasicCookieStore());

            execute.ifPresent(token -> {
                InstagramPayload payload = InstagramPayload.builder()
                        .username(username)
                        .password(password)
                        .token(token.getValue())
                        .build();

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setup();
                loginRequest.setPayload(payload);
                try {
                    loginRequest.execute(csrfTokenRequest.getCookieStore());

                    InstaCookies cookies = new InstaCookies();
                    cookies.setUserId(userService.getAuthenticatedUser().getId());
                    cookies.setCookies(loginRequest.getCookieStore());
                    instaDAO.save(cookies);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public User getUserByName(String username) throws Exception {
        String id = userService.getAuthenticatedUser().getId();
        InstaCookies byUserId = instaDAO.findByUserId(id);
        GetUserByUserNameRequest getUserByUserNameRequest = new GetUserByUserNameRequest(username);
        getUserByUserNameRequest.setup();
        return getUserByUserNameRequest.execute(byUserId.getCookies());
    }

    @Override
    public MediaResponse getUserMedia(String userId) {
        String id = userService.getAuthenticatedUser().getId();
        InstaCookies byUserId = instaDAO.findByUserId(id);
        GetMediaRequest mediaRequest = new GetMediaRequest(userId);
        mediaRequest.setup();
        try {
            return mediaRequest.execute(byUserId.getCookies());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ReportResponse> getReport(String mediaId, String userId) throws Exception {

        InstaFiles instaFiles = new InstaFiles();
        instaFiles.setFilename("File" + System.currentTimeMillis());
        instaFiles.setUserId(userService.getAuthenticatedUser().getId());
        instaFiles.setStatus("Pending");
        instaFiles.setDate(new Date());
        InstaFiles save = instaFilesDAO.save(instaFiles);

        Set<User> followers = Sets.newConcurrentHashSet();
        Set<User> likers = Sets.newConcurrentHashSet();
        Set<Comment> comments = Sets.newConcurrentHashSet();

        LikersResponse likersMedia = getLikersMedia(mediaId);
        likers.addAll(likersMedia.getUsers());

        CommentsResponse commetsMedia = getCommentsMedia(mediaId, StringUtils.EMPTY);
        String nextPage;
        System.out.println("Getted " + commetsMedia.getComments().size() + " comments");
        while (StringUtils.isNotBlank(nextPage = commetsMedia.getNext_max_id())) {
            commetsMedia = getCommentsMedia(mediaId, nextPage);
            comments.addAll(commetsMedia.getComments());
            System.out.println("Getted " + commetsMedia.getComments().size() + " comments");
            System.out.println(comments.size());

            Thread.sleep(1000);
        }
        FollowersResponse userFollowers = getUserFollowers(userId, StringUtils.EMPTY);
        System.out.println("Getted " + userFollowers.getUsers().size() + " followers");
        while (StringUtils.isNotBlank(nextPage = userFollowers.getNext_max_id())) {
            followers.addAll(userFollowers.getUsers());
            userFollowers = getUserFollowers(userId, nextPage);
            System.out.println("Getted " + userFollowers.getUsers().size() + " followers");
            System.out.println(followers.size());
            Thread.sleep(1000);
        }
        List<ReportResponse> reports = Lists.newLinkedList();
        comments.forEach(comment -> {
            Optional<User> follower = followers.stream().filter(f -> f.getPk().equals(comment.getUser_id())).findFirst();
            if (follower.isPresent()) {
                ReportResponse reportResponse = new ReportResponse();
                User user = follower.get();
                boolean isLiked = likers.contains(user);
                reportResponse.setComment(comment.getText());
                reportResponse.setFollowed(Boolean.TRUE);
                reportResponse.setUsername(user.getUsername());
                reportResponse.setFullName(user.getFull_name());
                reportResponse.setLiked(isLiked);
                reports.add(reportResponse);
            }
        });

        String pathname = "FileReport" + System.currentTimeMillis() + ".xlsx";
        try (SXSSFWorkbook resultWorkbook = new SXSSFWorkbook(null, 500)) {
            Sheet report = XLSUtils.createSheet(
                    resultWorkbook,
                    "report",
                    new String[]{"User Name", "Full Name", "Comment", "Liked", "Followed"});
            reports.forEach(item -> XLSUtils.writeRow(report, item.getRow()));
            resultWorkbook.write(new FileOutputStream(pathname));
        } catch (IOException e) {
            System.out.println("Can't save to file");
        }

        FTPUtils.share(new File(pathname));

        save.setStatus("Done");
        save.setUrl("84.201.155.169/reports/" + pathname);
        instaFilesDAO.save(save);

        return Lists.newArrayList();
    }

    @Override
    public List<InstaFiles> getReports() {
        return instaFilesDAO.findByUserIdOrderByDateDesc(userService.getAuthenticatedUser().getId());
    }

    private LikersResponse getLikersMedia(String mediaId) throws Exception {
        String id = userService.getAuthenticatedUser().getId();
        InstaCookies byUserId = instaDAO.findByUserId(id);

        GetMediaLikersRequest getMediaLikersRequest = new GetMediaLikersRequest(mediaId);
        getMediaLikersRequest.setup();
        return getMediaLikersRequest.execute(byUserId.getCookies());
    }

    private CommentsResponse getCommentsMedia(String mediaId, String nextPage) throws Exception {
        String id = userService.getAuthenticatedUser().getId();
        InstaCookies byUserId = instaDAO.findByUserId(id);
        GetMediaCommentsRequest request = new GetMediaCommentsRequest(mediaId, nextPage);
        request.setup();
        return request.execute(byUserId.getCookies());
    }

    private FollowersResponse getUserFollowers(String userId, String nextPage) throws Exception {
        String id = userService.getAuthenticatedUser().getId();
        InstaCookies byUserId = instaDAO.findByUserId(id);
        GetUserFollowersRequest request = new GetUserFollowersRequest(userId, nextPage);
        request.setup();
        return request.execute(byUserId.getCookies());
    }


}
