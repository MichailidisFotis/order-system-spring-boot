package com.example.order_system_spring_boot.components.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository {
//    Object createUser(User user);
    List<User> getAll();
}
