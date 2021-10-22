package com.Hotels.YourHome.projections;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class HotelRequest {
    private String userName;
    private String Email;
    private String PhoneNumber;
    private Date checkIn;
    private Date checkOut;
    private Timestamp bookingTime;
    private String roomType;
    private String hotelName;
    private int hotelId;
    private int noOfRooms;
}
