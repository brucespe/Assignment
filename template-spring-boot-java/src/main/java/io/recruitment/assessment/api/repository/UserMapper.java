package io.recruitment.assessment.api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import io.recruitment.assessment.api.model.User;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException{
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setFirstname(rs.getString("Firstname"));
        user.setSurname(rs.getString("Surname"));
        user.setAccessLevel(rs.getString("AccessLevel"));
        return user;
    }
    
}
