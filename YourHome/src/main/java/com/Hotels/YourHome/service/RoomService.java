package com.Hotels.YourHome.service;

import com.Hotels.YourHome.models.Rooms;
import com.Hotels.YourHome.projections.RoomView;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Rooms> getRooms();
    void addRooms(Rooms rooms);
    List<RoomView> getRoomByHotel(int id);
    void deleteRoom(int id);
    void updatePrice(int id,Rooms room);
    Optional<RoomView> getRoomById(int id);
}
