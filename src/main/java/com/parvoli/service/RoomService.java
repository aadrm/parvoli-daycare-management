package com.parvoli.service;

import com.parvoli.model.Course;
import com.parvoli.exception.BadRequestException;
import com.parvoli.exception.ResourceNotFoundException;
import com.parvoli.model.Room;
import com.parvoli.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> findRoomsByCourse(Course course) {
        return roomRepository.findByCourse(course);
    }

    public Room findRoomByIdElseNotFound(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existing Room with id: " + id
                ));
    }

    public void addRoom(Room room) {
        boolean roomExists = roomRepository.existsByCourseAndGroupLetter(room.getCourse(), room.getGroupLetter());

        if (roomExists) {
            throw new BadRequestException(
                    "The Room '" + room.getCourse().getName() + "' already exists."
            );
        } else {
            roomRepository.save(room);
        }
    }
}
