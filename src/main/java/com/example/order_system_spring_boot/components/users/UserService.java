package com.example.order_system_spring_boot.components.users;


import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.UUID;

@Repository
public class UserService implements UserRepository {


    @Autowired
    JdbcTemplate jdbcTemplate;


//    @Override
//    public Object createUser(User user) throws SqlScriptException {
//
//        String insertQuery = "insert into users(id, firstname, surname, email , password) values (?,?,?,?,?)";
//
//        UUID randomUuid = UUID.randomUUID();
//
//        user.setId(randomUuid.toString());
//
//        jdbcTemplate.update(insertQuery,user.getId(),user.getFirstname(),user.getSurname(),user.getEmail() , user.getPassword());
//
//        return  user;
//    }

    @Override
    public List<User> getAll() {

        List<User> users = jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<User>(User.class));


        return users;
    }
}
