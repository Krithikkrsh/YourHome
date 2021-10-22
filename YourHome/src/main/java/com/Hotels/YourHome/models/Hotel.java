package com.Hotels.YourHome.models;

import com.Hotels.YourHome.validators.Remove;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name = "Hotels")
@ToString(exclude = {"id","password","c_time","u_time"})
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id",unique = true,nullable = false)
    @JsonIgnore
    private @Getter @Setter int id;
    @Column(name = "hotel_name",nullable = false)
    private @Getter @Setter String name;
    @Column(name = "hotel_admin_name")
    private @Getter @Setter String admin;
    @Column(name = "hotel_mail",unique = true,nullable = false)
    private @Getter @Setter String  email;
    @Column(name = "phone_number",unique = true,nullable = false)
    private @Getter @Setter long number;
    @Column(name = "city")
    private @Getter @Setter String city;
    @Column(name = "area",nullable = false)
    private @Getter @Setter String area;
    @Column(name = "hotel_password")
    private @Getter String password;
    @Column(name="created_at")
    @JsonIgnore
    private @Getter @Setter String c_time;
    @JsonIgnore
    @Column(name="updated_at")
    private @Getter @Setter String u_time;
    @Column(name="image_path")
    private @Getter @Setter String imagePath;

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password,BCrypt.gensalt());
    }

}
