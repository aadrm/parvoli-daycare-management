package com.parvoli.controller;

import com.parvoli.model.Baby;
import com.parvoli.service.BabyService;
import com.parvoli.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/babies")
public class BabyController {

    private final BabyService babyService;

    @Autowired
    public BabyController(BabyService babyService) {
        this.babyService = babyService;
    }

    @GetMapping
    public List<Baby> getBabies() {
        return babyService.findAllBabies();
    }

    @PostMapping
    public void registerNewBaby(@RequestBody Baby baby) {
        babyService.addBaby(baby);
    }

    @GetMapping("/{babyId}/possible-courses")
    public List<Course> returnPossibleCourses(@PathVariable Long babyId) {
        Baby baby = babyService.findBabyByIdElseNotFound(babyId);
        return babyService.findCorrespondingCourses(baby);
    }

    @PutMapping("/{babyId}/assign-room/{roomId}")
    public void addBabyToRoom(@PathVariable Long babyId, @PathVariable Long roomId) {
        babyService.addBabyByIdToRoomById(babyId, roomId);
    }
}
