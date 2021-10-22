package com.Hotels.YourHome.models;

import com.Hotels.YourHome.validators.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "user_management")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true)
    @JsonIgnore
    private int id;
    @NotNull(message = "Name required!")
    @Column(name = "user_name")
    private String name;
    @NotNull(message = "Email required!")
    @Column(name = "email", unique = true)
    @Email
    private String email;
    @NotNull(message = "Address required!")
    @Column(name = "address")
    private String address;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "id"))
    private Set<String> role;
    @Column(name = "phone_number")
    @PhoneNumber
    private long phoneNumber;
    @Column(name = "user_password")
    private String password;
    @UpdateTimestamp
    @JsonIgnore
    @Column(name = "updated_at")
    private Timestamp updatedAt;


}

