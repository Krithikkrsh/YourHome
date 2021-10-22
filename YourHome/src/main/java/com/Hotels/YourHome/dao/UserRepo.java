package com.Hotels.YourHome.dao;

import com.Hotels.YourHome.models.User;
import com.Hotels.YourHome.projections.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Modifying
    @Query(value = "Update user_management set flag=true where user_id= ?1",nativeQuery = true)
    void deleteUserById(int id);

    @Query(value = "select user_name as Name,email as Email,phone_number as PhoneNumber,Address from user_management where flag = false order by user_name",nativeQuery = true)
    List<UserView> FetchAllUsers();

    @Query(value = "Select user_name as Name,email as Email,phone_number as PhoneNumber,Address from user_management where user_name=?1 and flag=false",nativeQuery = true)
    Optional<UserView> GetUserByName(String name);

    @Modifying
    @Query(value = "update user_management set user_name=?2,email=?3,address=?4,phone_number=?5,updated_at=?6 where user_id= ?1",nativeQuery = true)
    void UpdateAddress(int id, String name, String email, String address, long phoneNumber, Timestamp time);

    @Query(value = "select * from user_management where user_id=?1 and flag=false",nativeQuery = true)
    User getUserById(int id);

    @Query(value = "select user_password from user_management where lower(user_name)=lower(?1)",nativeQuery = true)
    String Password(String name);

    @Query(value = "select * from user_management where user_name = ?1",nativeQuery = true)
    Optional<User>  loadUserByName(String name);
}
