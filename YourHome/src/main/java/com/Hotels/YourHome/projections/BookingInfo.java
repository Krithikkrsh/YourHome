package com.Hotels.YourHome.projections;

import lombok.Data;


import java.sql.Date;
import java.sql.Timestamp;

@Data
public class BookingInfo {
    private int userId;
    private int roomId;
    private int hotelId;
    private int noOfRooms;
    private Date checkIn;
    private Date checkOut;
    private Timestamp bookingTime;
    private double totalAmount;
}
