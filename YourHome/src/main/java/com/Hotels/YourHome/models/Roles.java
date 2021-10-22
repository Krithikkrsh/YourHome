package com.Hotels.YourHome.models;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Roles {
    private String role;
}
