package com.example.shard.repo;

import com.example.shard.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositoryJpa extends JpaRepository<UserInfo, Long> {

    List<UserInfo> findUserInfosByIdGreaterThan(Long count);

    UserInfo findUserInfosByDays(Long days);
}
