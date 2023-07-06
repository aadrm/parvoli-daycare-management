package com.parvoli.repository;

import com.parvoli.model.Course;
import com.parvoli.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByCourseAndGroupLetter(Course course, char groupLetter);
    List<Room> findByCourse(Course course);
}
