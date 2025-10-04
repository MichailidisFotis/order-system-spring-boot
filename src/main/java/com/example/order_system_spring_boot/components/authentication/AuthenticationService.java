package com.example.order_system_spring_boot.components.authentication;

import com.example.order_system_spring_boot.components.users.User;
import com.example.order_system_spring_boot.helpers.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class AuthenticationService implements AuthenticationRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public ResponseEntity<Object> register(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        String insertQuery = "insert into users(id, firstname, surname, email , password) values (?,?,?,?,?)";

        UUID randomUuid = UUID.randomUUID();

        user.setId(randomUuid.toString());

        jdbcTemplate.update(insertQuery, user.getId(), user.getFirstname(), user.getSurname(), user.getEmail(), user.getPassword());

        return ResponseHandler.responseBuilder("User Created!!", HttpStatus.CREATED, user, true);
    }


    @Override
    public ResponseEntity<Object> login(Login login, HttpServletRequest request) {

        HashMap<String, Object> response = new HashMap<>();


        List<User> users = new ArrayList<User>(
                jdbcTemplate.query("Select * from users where email = ?",
                        new PreparedStatementSetter() {
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, login.getEmail());
                            }
                        },
                        new BeanPropertyRowMapper<User>(User.class)
                ));

        if (users.isEmpty())
            return ResponseHandler.responseBuilder("User not Found", HttpStatus.NOT_FOUND, response, false);


        if (users.size() != 1)
            return ResponseHandler.responseBuilder("Error occurred", HttpStatus.BAD_REQUEST, response, false);


        User user = users.get(0);

        boolean passwordMatches = encoder.matches(login.getPassword(), user.getPassword());

        if (!passwordMatches)
            return ResponseHandler.responseBuilder("Wrong Credentials", HttpStatus.UNAUTHORIZED, response, false);

        request.getSession().setAttribute("user", user);

        return ResponseHandler.responseBuilder("User Logged in!!", HttpStatus.OK, user, true);
    }

    @Override
    public ResponseEntity<Object> logout(HttpServletRequest request) {

        request.getSession().invalidate();

        return ResponseHandler.responseBuilder("User Logged out!!", HttpStatus.OK, null, true);
    }
}
