package com.Hotels.YourHome.service;

import com.Hotels.YourHome.models.User;
import com.Hotels.YourHome.projections.UserView;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    void delete(int id);
    void updateUser(int id,User user);
    List<UserView> getUser();
    Optional<UserView> getUserByName(String name);
}
