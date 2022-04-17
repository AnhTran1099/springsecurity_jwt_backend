package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name); //tim kiem User co ton tai trong DB k?
    Boolean existsByUsername(String username); //username da co trong DB chua, khi tao du lieu
    Boolean existsByEmail(String email); //email da co trong DB chua
    User save(User user); //save du lieu vao DB
}
