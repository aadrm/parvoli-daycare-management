package com.parvoli.service;

import com.parvoli.model.Baby;
import com.parvoli.model.Course;
import com.parvoli.model.Room;
import com.parvoli.repository.BabyRepository;
import com.parvoli.repository.CourseRepository;
import com.parvoli.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
class CourseOccupancyServiceTest {

    @Autowired
    private BabyRepository babyRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CourseOccupancyService underTest;

    @BeforeEach
    void setUp() {
        Baby baby = new Baby(
                "Test",
                "Subject",
                LocalDate.now().minusDays(56)
        );
        babyRepository.save(baby);
        baby = new Baby(
                "Test",
                "Subject",
                LocalDate.now().minusDays(35)
        );
        babyRepository.save(baby);
        baby = new Baby(
                "Test",
                "Subject",
                LocalDate.now().minusDays(70)
        );
        babyRepository.save(baby);



        Course course = Course.createFromMonthsAndDays(1, 0, 2, 0);
        course.setName("newborns");
        courseRepository.save(course);
        Room room = new Room();
        room.setGroupLetter('a');
        room.setCapacity(3);
        room.setCourse(course);
        roomRepository.save(room);

        course = Course.createFromMonthsAndDays(2, 0, 3, 0);
        course.setName("toddler");

        courseRepository.save(course);
    }

    @Test
    void playground() {
        System.out.println("-----test----");
        underTest.sortBabiesInTimeRange(LocalDate.now(), LocalDate.now().plusYears(1));
    }


}