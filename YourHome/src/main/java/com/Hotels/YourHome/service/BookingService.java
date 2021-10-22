package com.Hotels.YourHome.service;

import com.Hotels.YourHome.models.Booking;
import com.Hotels.YourHome.projections.BookingInfo;
import com.Hotels.YourHome.projections.HotelRequest;

import java.util.List;

public interface BookingService {
    public void addBooking(Booking booking);
    public List<BookingInfo> showView(int id);
    public List<HotelRequest> showOrders();
    public void deleteBooking(int id);
}
