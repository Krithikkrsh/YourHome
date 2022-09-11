package com.Hotels.YourHome.service.ServiceImplementation;

import com.Hotels.YourHome.customException.BusinessException;
import com.Hotels.YourHome.dao.UserRepo;
import com.Hotels.YourHome.models.User;
import com.Hotels.YourHome.projections.UserView;
import com.Hotels.YourHome.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;


@Service
@Slf4j
public class UserServiceImp implements UserService, UserDetailsService {


    @Autowired
    UserRepo userRepo;

    @Override
    public void addUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        User u = userRepo.getUserById(id);
        try {
            userRepo.deleteUserById(id);
            log.info("user- "+id+" deleted");
        } catch (Exception e) {
            throw new BusinessException(404, "Not found unable to delete");
        }
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {
        Timestamp t = Timestamp.from(Instant.now());
        try {
            User existing = userRepo.getUserById(id);
            user.setName(user.getName() == null ? existing.getName() : user.getName());
            user.setAddress(user.getAddress() == null ? existing.getAddress() : user.getAddress());
            user.setEmail(user.getEmail() == null ? existing.getEmail() : user.getEmail());
            user.setPhoneNumber(user.getPhoneNumber() == 0 ? existing.getPhoneNumber() : user.getPhoneNumber());
            userRepo.UpdateAddress(id, user.getName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), t);
            log.info("user- "+id+ "has been updated");
        } catch (Exception e) {
            throw new BusinessException(404, "User not found in database");
        }
    }

    @Override
    public List<UserView> getUser() {
        try {
            log.info("Getting all users");
            sleep(5);
            return userRepo.FetchAllUsers();
        } catch (Exception e) {
            throw new BusinessException(404, "Error in getting User");
        }
    }

    @Override
    @Cacheable(cacheNames = "User",key = "#name")
    public Optional<UserView> getUserByName(String name) {

        Optional<UserView> user = userRepo.GetUserByName(name);
        log.info("Getting the user- "+name);
        user.orElseThrow(() -> new BusinessException(404, "User: " + name + " not found"));
        return user;
    }

    @Override
    public void sleep(int seconds) {
        try{
            SECONDS.sleep(5);
        }
        catch (Exception e){
            log.error("Sleep method in error");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> us = userRepo.loadUserByName(name);
        us.orElseThrow(() -> new UsernameNotFoundException("User " + name.toUpperCase() + "Not found"));
        User user = us.get();
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRole().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }


}
