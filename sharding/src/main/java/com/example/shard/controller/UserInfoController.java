package com.example.shard.controller;

import com.example.shard.model.UserInfo;
import com.example.shard.repo.UserRepository;
import com.example.shard.repo.UserRepositoryJpa;
import org.hibernate.engine.jdbc.env.spi.AnsiSqlKeywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
public class UserInfoController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRepositoryJpa repositoryJpa;

    @GetMapping("/userinfo")
    public List<UserInfo> getUserInfos() throws SQLException {
        return repository.selectAll();
//        return repositoryJpa.findAll(); с jpa repository не работает корректно
    }
    @GetMapping("/userinfo/{count}")
    public List<UserInfo> getUserInfos(@PathVariable Long count) throws SQLException {
        return Arrays.asList(repositoryJpa.findUserInfosByDays(count));
//        return repositoryJpa.findUserInfosByIdGreaterThan(count);
    }

    @GetMapping("/interval/{begin}/{end}")
    public List<UserInfo> searchInInterval(@PathVariable Long begin, @PathVariable Long end) throws SQLException {
        return repository.selectWhereDayGrateThan(begin, end);
    }

    @GetMapping("/userinfo/{name}/{days}")
    public Long addUserInfo(@PathVariable String name, @PathVariable Long days) throws SQLException {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setDays(days);
//        return repository.insert(userInfo);
        UserInfo userInfo1 = repositoryJpa.save(userInfo);
        return userInfo1.getId();
    }
}