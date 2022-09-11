package com.Hotels.YourHome.service.ServiceImplementation;

import com.Hotels.YourHome.customException.BusinessException;
import com.Hotels.YourHome.dao.RoomsRepo;
import com.Hotels.YourHome.models.Rooms;
import com.Hotels.YourHome.projections.RoomView;
import com.Hotels.YourHome.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImp implements RoomService {

    @Autowired
    RoomsRepo roomRepo;

    @Override
    public List<Rooms> getRooms() {
        try {
            List<Rooms> rooms = roomRepo.getRooms();
            return rooms;
        } catch (Exception e) {
            throw new BusinessException(404, "No rooms found!");
        }
    }

    @Override
    public void addRooms(Rooms rooms) {
        try {
            Timestamp t = Timestamp.from(Instant.now());
            rooms.setUpdatedAt(t);
            roomRepo.insertRoom(rooms);
        } catch (Exception e) {
            throw new BusinessException(400, "Error in adding User!");
        }
    }

    @Override
    public List<RoomView> getRoomByHotel(int id) {
        try {
            List<RoomView> view = roomRepo.getRoomByHotel(id);
            return view;
        } catch (Exception e) {
            throw new BusinessException(404, "No room found for this hotel");
        }
    }

    @Override
    public void deleteRoom(int id) {

        Optional<RoomView> room = roomRepo.getRoomsById(id);

        if (room.isPresent())
            roomRepo.deleteRoom(id);
        else
            throw new BusinessException(404, "No rooms found!");
    }

    @Override
    public void updatePrice(int id, Rooms room) {
        roomRepo.updatePrice(room, id);
    }

    @Override
    public Optional<RoomView> getRoomById(int id) {
        Optional<RoomView> room = roomRepo.getRoomsById(id);
        room.orElseThrow(() -> new BusinessException(404,"Room not found"));
        return roomRepo.getRoomsById(id);
    }
}
