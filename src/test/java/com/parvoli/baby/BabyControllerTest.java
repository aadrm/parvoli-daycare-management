package com.parvoli.baby;

import com.parvoli.controller.BabyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BabyControllerTest {

    @Autowired
    static BabyController underTest;
}