package com.parvoli.baby;

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
}
