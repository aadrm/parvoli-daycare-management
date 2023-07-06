package com.parvoli.controller;

import com.parvoli.model.Room;
import com.parvoli.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public void registerNewRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    @GetMapping
    public List<Room> getRooms() {
        return roomService.findAllRooms();
    }
}
