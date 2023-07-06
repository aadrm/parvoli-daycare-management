package com.parvoli.controller;

import com.parvoli.service.CourseOccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController()
@RequestMapping("api/vi/course-occupancy")
public class CourseOccupancyController {

    public final CourseOccupancyService courseOccupancyService;

    @Autowired
    public CourseOccupancyController(CourseOccupancyService courseOccupancyService) {
        this.courseOccupancyService = courseOccupancyService;
    }

    @GetMapping
    public Map<String, Object> getOccupancyData(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        if (startDate == null) {
           startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = LocalDate.now().plusMonths(1);
        }

        return courseOccupancyService.sortBabiesInTimeRange(startDate, endDate);
    }
}
