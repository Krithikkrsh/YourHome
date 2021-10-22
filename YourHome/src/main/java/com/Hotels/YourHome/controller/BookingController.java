package com.Hotels.YourHome.controller;

import com.Hotels.YourHome.models.Booking;
import com.Hotels.YourHome.projections.BookingInfo;
import com.Hotels.YourHome.projections.HotelRequest;
import com.Hotels.YourHome.service.BookingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingService bookS;

    @PostMapping("/hotel-booking/booking")
    @ApiOperation(value = "Add Booking")
    public ResponseEntity<Void> addBooking(@RequestBody Booking booking)
    {
        bookS.addBooking(booking);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc","Create booking");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

    @GetMapping("/hotel-booking/{id}")
    @ApiOperation(value = "Show Booking by id")
    public ResponseEntity<List<BookingInfo>>showvalues(@RequestParam("id") int id)
    {
        List<BookingInfo> bookingList = bookS.showView(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc","Get booking deatils using user's id");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bookingList);
    }

    @GetMapping("/hotel-booking/order-preview")
    @ApiOperation(value = "Show orders for respective hotel")
    public ResponseEntity<List<HotelRequest>> showOrders()
    {
        List<HotelRequest> hotelRequestList=  bookS.showOrders();
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc","Showing hotel orders");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(hotelRequestList);
    }

    @DeleteMapping("/hotel-booking/{id}/delete")
    @ApiOperation(value = "Delete Booking by id")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") int id)
    {
        bookS.deleteBooking(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc","delete booking by id");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

}
