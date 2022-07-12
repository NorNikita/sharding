package com.example.shard.repo;

import com.example.shard.model.UserInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component
public final class UserRepositoryImpl implements UserRepository {

    @Autowired
    private DataSource dataSource;
    @PersistenceContext
    private EntityManager entityManager;

//    @Autowired
//    public UserRepositoryImpl(@Qualifier("master") DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Override
    public Long insert(final UserInfo entity) throws SQLException {
//        entityManager.persist(entity);
//        return null;
        String sql = "INSERT INTO userinfo_0 (name) VALUES (?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                }
            }
        }
        return entity.getId();
    }

    @Override
    public List<UserInfo> selectAll() throws SQLException {
        String sql = "SELECT * FROM userinfo_0";
        return getAddress(sql);
    }

    private List<UserInfo> getAddress(final String sql) throws SQLException {
        List<UserInfo> result = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                UserInfo info = new UserInfo();
                info.setId(resultSet.getLong(1));
                info.setName(resultSet.getString(2));
                result.add(info);
            }
        }
        return result;
    }
}

