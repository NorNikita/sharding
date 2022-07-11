package com.example.shard.repo;

import com.example.shard.model.UserInfo;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    Long insert(final UserInfo entity) throws SQLException;

    List<UserInfo> selectAll() throws SQLException;
}
