package com.example.shard.controller;

import com.example.shard.model.entity.UserInfo;
import com.example.shard.repo.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class UserInfoController {

    @Autowired
    private UserRepositoryJpa repositoryJpa;

    @GetMapping("/userinfo")
    public List<UserInfo> getUserInfos() throws SQLException {
        return repositoryJpa.findAll();
    }

    @GetMapping("/interval")
    public List<UserInfo> searchByTimestamp(@RequestParam("begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin,
                                            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws SQLException {
        return repositoryJpa.findUserInfosByTimestampAfterAndTimestampBefore(begin, end);
    }

    @GetMapping("/userinfo/{name}/{days}")
    public Long addUserInfo(@PathVariable String name, @PathVariable Long days) throws SQLException {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setDays(days);
        userInfo.setTimestamp(LocalDateTime.now());
        userInfo.setUuid(UUID.randomUUID().toString());
        UserInfo userInfo1 = repositoryJpa.save(userInfo);
        return userInfo1.getId();
    }
}