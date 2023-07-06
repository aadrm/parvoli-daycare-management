package com.parvoli.service;

import com.parvoli.model.Course;
import com.parvoli.exception.BadRequestException;
import com.parvoli.exception.ResourceNotFoundException;
import com.parvoli.model.Baby;
import com.parvoli.model.Room;
import com.parvoli.repository.BabyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class BabyService {

    private final BabyRepository babyRepository;
    private final CourseService courseService;
    private final RoomService roomService;
    private final Clock clock;

    @Autowired
    public BabyService(BabyRepository babyRepository, CourseService courseService, RoomService roomService, Clock clock) {
        this.babyRepository = babyRepository;
        this.courseService = courseService;
        this.roomService = roomService;
        this.clock = clock;
    }

    public Period getBabyAgeAsPeriod(Baby baby, Clock clock) {
        LocalDate currDate = LocalDate.now(clock);
        return Period.between(baby.getDateOfBirth(), currDate);
    }

    public List<Baby> findAllBabies() {
        return babyRepository.findAll();
    }

    public Baby findBabyByIdElseNotFound(Long id) {
        return babyRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existing Baby with id: " + id
                ));
    }

    public void addBaby(Baby baby) {
        boolean babyExists = babyRepository
                .existsByFirstNameAndLastNameAndDateOfBirth(
                        baby.getFirstName(),
                        baby.getLastName(),
                        baby.getDateOfBirth()
                );

        if (babyExists) {
            throw new BadRequestException(
                    "Baby: " + baby.getFirstName() + " born " + baby.getDateOfBirth() + " exists in the database"
            );
        } else {
            babyRepository.save(baby);
        }

    }

    public void addBabyByIdToRoomById(Long babyId, Long roomId) {
        Baby baby = findBabyByIdElseNotFound(babyId);
        Room room = roomService.findRoomByIdElseNotFound(roomId);
        baby.setRoom(room);
        babyRepository.save(baby);
    }

    public List<Course> findCorrespondingCourses(Baby baby) {
        List<Course> courses = courseService.findAllCourses();
        courses.removeIf(course -> !babyFitsCourse(baby, course));
        return courses;
    }

    public List<Baby> getBabiesBornBetween(LocalDate after, LocalDate before) {
        return babyRepository.getBabiesByDateOfBirthAfterAndDateOfBirthBefore(after, before);
    }

    private Boolean babyFitsCourse(Baby baby, Course course) {
        LocalDate earliestDateOfBirth = course.getEarliestAcceptedDateOfBirth();
        LocalDate latestDateOfBirth = course.getLatestAcceptedDateOfBirth();
        LocalDate babyDateOfBirth = baby.getDateOfBirth();
        return (babyDateOfBirth.isAfter(earliestDateOfBirth) && babyDateOfBirth.isBefore(latestDateOfBirth));
    }

    private Boolean isBabyInCorrectRoom(Baby baby, Room room) {
        return babyFitsCourse(baby, room.getCourse());
    }

}
