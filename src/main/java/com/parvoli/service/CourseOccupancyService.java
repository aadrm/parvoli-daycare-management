package com.parvoli.service;

import com.parvoli.model.Baby;
import com.parvoli.model.Course;
import com.parvoli.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CourseOccupancyService {

    private final BabyService babyService;
    private final CourseService courseService;

    @Autowired
    public CourseOccupancyService(BabyService babyService, CourseService courseService, RoomService roomService) {
        this.babyService = babyService;
        this.courseService = courseService;
    }

    protected int courseOccupancyOnDate(Course course, List<Baby> babies, LocalDate onDate) {
        LocalDate earliestAcceptedDateOfBirth = onDate.minus(course.getMaxAge());
        LocalDate latestAcceptedDateOfBirth = onDate.minus(course.getMinAge());
        // Filter babies within the accepted age range
        long numOfBabies = babies.stream()
                .filter(baby -> baby.getDateOfBirth().isAfter(earliestAcceptedDateOfBirth)
                        && baby.getDateOfBirth().isBefore(latestAcceptedDateOfBirth))
                .count();
        return (int) numOfBabies;
    }

    private Map<Course, Integer> prepareCourceCapacityMap(List<Course> courses) {
        Map<Course, Integer> courseCapacityMap = new HashMap<>();
        for (Course course : courses) {
            int courseCapacity = 0;
            Set<Room> rooms = course.getRooms();

            if (rooms != null) {
                courseCapacity = rooms.stream()
                        .mapToInt(Room::getCapacity)
                        .sum();

            }
            courseCapacityMap.put(course, courseCapacity);
        }
        return courseCapacityMap;
    }

    private Map<LocalDate, Object> prepareCourceOccupancyInDateRangeMap(
            List<Course> courses,
            List<Baby> babies,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Map<LocalDate, Object> courseOccupancyMap = new LinkedHashMap<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            Map<Course, Integer> OccupancyByCourseAndDate = new HashMap<>();
            for (Course course : courses) {
                int courseOccupancy = courseOccupancyOnDate(course, babies, date);
                OccupancyByCourseAndDate.put(course, courseOccupancy);
            }
            courseOccupancyMap.put(date, OccupancyByCourseAndDate);
        }
        return courseOccupancyMap;
    }


    public Map<String, Object> sortBabiesInTimeRange(LocalDate startDate, LocalDate endDate) {
        List<Course> courses = courseService.findAllCourses();
        List<Baby> babies = babyService.findAllBabies();

        Map<String, Object> data = new HashMap<>();
        data.put("courseCapacityMap", prepareCourceCapacityMap(courses));
        data.put("courseOccupancyMap", prepareCourceOccupancyInDateRangeMap(courses, babies, startDate, endDate));

        return data;
    }

}
