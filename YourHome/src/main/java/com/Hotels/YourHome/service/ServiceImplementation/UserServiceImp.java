package com.Hotels.YourHome.service.ServiceImplementation;

import com.Hotels.YourHome.customException.BusinessException;
import com.Hotels.YourHome.dao.UserRepo;
import com.Hotels.YourHome.models.User;
import com.Hotels.YourHome.projections.UserView;
import com.Hotels.YourHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImp implements UserService, UserDetailsService {


    @Autowired
    UserRepo userRepo;

    @Override
    public void addUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        User u = userRepo.getUserById(id);
        try {
            userRepo.deleteUserById(id);
        }
        catch(Exception e) {
            throw new BusinessException(404, "Not found unable to delete");
        }
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {
        Timestamp t = Timestamp.from(Instant.now());
        try {
            User existing = userRepo.getUserById(id);
            if (user.getName() == null)
                user.setName(existing.getName());
            if (user.getAddress() == null)
                user.setAddress(existing.getAddress());
            if (user.getEmail() == null)
                user.setEmail(existing.getEmail());
            if (user.getPhoneNumber() == 0)
                user.setPhoneNumber(existing.getPhoneNumber());
            userRepo.UpdateAddress(id, user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), t);
        } catch (Exception e) {
            throw new BusinessException(404, "User not found in database");
        }
    }

    @Override
    public List<UserView> getUser() {
        try {
            return userRepo.FetchAllUsers();
        } catch (Exception e) {
            throw new BusinessException(404, "Error in getting User");
        }
    }

    @Override
    public Optional<UserView> getUserByName(String name) {

        Optional<UserView> user = userRepo.GetUserByName(name);
        user.orElseThrow(() -> new BusinessException(404,"User: "+name+"Not found"));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> us = userRepo.loadUserByName(name);
        us.orElseThrow(() -> new UsernameNotFoundException("User "+name.toUpperCase()+"Not found"));
        User user = us.get();
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRole().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
