package com.example.CMS.mapper;

import com.example.CMS.model.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL //
            = "Select u.USER_ID, u.USER_NAME, u.PASSWORD From APP_USER u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long userId = rs.getLong("USER_ID");
        String userName = rs.getString("USER_NAME");
        String encryptedPassword = rs.getString("PASSWORD");

        return new AppUser(userId, userName, encryptedPassword);
    }

}