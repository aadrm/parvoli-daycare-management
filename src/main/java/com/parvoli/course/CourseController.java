package com.parvoli.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    public final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) { this.courseService = courseService;}

    @GetMapping
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @PostMapping
    public void createCourse(@RequestBody CourseRequestDto requestDto) {
        int minMonths = requestDto.getMinMonths();
        int minDays = requestDto.getMinDays();
        int maxMonths = requestDto.getMaxMonths();
        int maxDays = requestDto.getMaxDays();

        Course course = Course.createFromMonthsAndDays(minMonths, minDays, maxMonths, maxDays);
        courseService.addNewCourse(course);
    }
}
