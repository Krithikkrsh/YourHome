package com.Hotels.YourHome.dao;

import com.Hotels.YourHome.models.Booking;
import com.Hotels.YourHome.projections.BookingInfo;
import com.Hotels.YourHome.projections.HotelRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;


@MappedTypes(Booking.class)
@Mapper
public interface BookingRepo {

    @Insert("insert into booking(user_id,hotel_id,no_of_rooms,room_id,check_in,check_out,total_amount)" +
            " values(#{b.userId},#{b.hotelId},#{b.noOfRooms},#{b.roomId},#{b.checkIn},#{b.checkOut},#{amount})")
    void addBooking(Booking b,int amount);

    @Select("select * from booking where user_id=#{id} and flag=false order by booking_time")
    List<BookingInfo> showUserBooking(int id);

    @Select("select * from hotel_request order by booking_time")
    List<HotelRequest> order();

    @Select("select price from rooms where room_id=#{id}")
    int getRoomsPrice(int id);

    @Delete("update booking set flag=true where id=#{id}")
    void deleteBooking(int id);
}
