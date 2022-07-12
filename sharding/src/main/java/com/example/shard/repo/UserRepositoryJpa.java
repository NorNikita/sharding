package com.example.shard.repo;

import com.example.shard.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<UserInfo, Long> {
}
