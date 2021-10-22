package com.Hotels.YourHome.models;

import com.Hotels.YourHome.validators.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Getter
@Setter
@ToString(exclude = "updatedAt")
public class Rooms {

    private int roomId;
    private int hotelId;
    private int noOfRooms;
    @Type
    private String roomType;
    private double price;
    @UpdateTimestamp
    private Timestamp updatedAt;

}
