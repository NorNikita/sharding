package com.example.shard.controller;

import com.example.shard.model.UserInfo;
import com.example.shard.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UserInfoController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/userinfo")
    public List<UserInfo> getUserInfos() throws SQLException {
        return repository.selectAll();
    }

    @GetMapping("/userinfo/{name}")
    public Long addUserInfo(@PathVariable String name) throws SQLException {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        return repository.insert(userInfo);
    }
}