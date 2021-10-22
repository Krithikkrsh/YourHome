package com.Hotels.YourHome.dao;

import com.Hotels.YourHome.models.Rooms;
import com.Hotels.YourHome.projections.RoomView;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.MappedTypes;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@MappedTypes(Rooms.class)
@Mapper
public interface RoomsRepo {

    @Select("select * from rooms where flag=false order by price")
    List<Rooms> getRooms();

    @Insert("insert into rooms (hotel_id,no_of_rooms,room_type,price,updated_at) values(#{hotelId},#{noOfRooms},#{roomType},#{price},#{updatedAt})")
    void insertRoom(Rooms room);

    @Select("select h.hotel_name,r.no_of_rooms,r.room_type,r.price from (hotels h join rooms r on r.hotel_id = h.hotel_id) where h.flag=false and r.flag = false and h.hotel_id=#{id} order by r.price")
    List<RoomView> getRoomByHotel(int id);

    @Update("update rooms set flag=true where room_id= #{id}")
    void deleteRoom(int id);

    @Update("update rooms set no_of_rooms=#{room.noOfRooms},price=#{room.price},room_type=#{room.roomType},updated_at=#{room.updatedAt} where room_id=#{id}")
    void updatePrice(Rooms room, int id);

    @Select("select r.no_of_rooms,r.room_type,r.price from rooms where room_id=#{id}")
    Optional<RoomView> getRoomsById(int id);
}
