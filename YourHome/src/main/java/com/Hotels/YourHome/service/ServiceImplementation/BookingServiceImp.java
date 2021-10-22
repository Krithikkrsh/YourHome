package com.Hotels.YourHome.service.ServiceImplementation;

import com.Hotels.YourHome.dao.BookingRepo;
import com.Hotels.YourHome.models.Booking;
import com.Hotels.YourHome.projections.BookingInfo;
import com.Hotels.YourHome.projections.HotelRequest;
import com.Hotels.YourHome.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    BookingRepo bookrepo;


    @Override
    public void addBooking(Booking booking) {
        int totalAmount = bookrepo.getRoomsPrice(booking.getRoomId())*booking.getNoOfRooms();
        bookrepo.addBooking(booking,totalAmount);
    }

    @Override
    public List<BookingInfo> showView(int id) {
        return bookrepo.showUserBooking(id);
    }

    @Override
    public List<HotelRequest> showOrders() {
        return bookrepo.order();
    }

    @Override
    public void deleteBooking(int id) {
        bookrepo.deleteBooking(id);
    }
}
