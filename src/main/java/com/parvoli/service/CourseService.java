package com.parvoli.service;

import com.parvoli.exception.BadRequestException;
import com.parvoli.model.Course;
import com.parvoli.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> findCoursesWithRooms() {
       return courseRepository.findCoursesWithRooms();
    }
    public void addNewCourse(Course course) {
        LocalDate earliestAcceptedDateOfBirth = course.getEarliestAcceptedDateOfBirth();
        LocalDate latestAcceptedDateOfBirth = course.getLatestAcceptedDateOfBirth();
        if (earliestAcceptedDateOfBirth.isAfter(latestAcceptedDateOfBirth)) {
            throw new BadRequestException(
                    "The Course's minimum admitted age is larger than the maximum"
            );
        }
        courseRepository.save(course);
    }
}
