package com.Hotels.YourHome.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class Booking {

    private int userId;
    private int roomId;
    private int hotelId;
    private int noOfRooms;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date checkIn;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date checkOut;

}
