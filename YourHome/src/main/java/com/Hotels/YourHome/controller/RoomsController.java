package com.Hotels.YourHome.controller;

import com.Hotels.YourHome.customException.ControllerException;
import com.Hotels.YourHome.models.Rooms;
import com.Hotels.YourHome.projections.RoomView;
import com.Hotels.YourHome.service.RoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoomsController {

    @Autowired
    RoomService roomS;

    @GetMapping("/hotels/rooms")
    @ApiOperation(value = "Get List of rooms")
    public ResponseEntity<List<Rooms>> rooms() {
        List<Rooms> roomsList = roomS.getRooms();
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Get all the rooms in database");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(roomsList);
    }

    @PostMapping(value = "/hotels/room")
    @ApiOperation(value = "Adding Rooms")
    public ResponseEntity<?> addRoom(@Valid @RequestBody Rooms room) {
        roomS.addRooms(room);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/hotels/room")
    @ApiOperation(value = "Getting rooms by id")
    public ResponseEntity<List<RoomView>> getRoomByHotel(@RequestParam("id") int id) {
        if (id == 0)
            throw new ControllerException(404, "Id is null");
        else {
            List<RoomView> rooms = roomS.getRoomByHotel(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("desc", "Get room by hotel id");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(rooms);
        }
    }

    @DeleteMapping("/hotels/rooms/{id}/delete")
    @ApiOperation(value = "Delete room by id")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") int id) {
        roomS.deleteRoom(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Delete rooms by id");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

    @PutMapping("/hotels/rooms/{id}/update")
    @ApiOperation(value = "Update Rooms price only by id")
    public ResponseEntity<Void> updateRoomPrice(@PathVariable("id") int id, @RequestBody Rooms rooms) {
        roomS.updatePrice(id, rooms);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Update room by id");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

    @GetMapping("/hotels/room/search")
    @ApiOperation(value = "Get room by rooms id")
    public ResponseEntity<Optional<RoomView>> getRoomById(@RequestParam("id") int id) {
        Optional<RoomView> room = roomS.getRoomById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("desc", "Get room by id");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(room);
    }
}
