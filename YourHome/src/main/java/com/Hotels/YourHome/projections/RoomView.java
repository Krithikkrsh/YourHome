package com.Hotels.YourHome.projections;

import lombok.Data;

@Data
public class RoomView {
    private String hotelName;
    private Integer noOfRooms;
    private String roomType;
    private long price;
}
