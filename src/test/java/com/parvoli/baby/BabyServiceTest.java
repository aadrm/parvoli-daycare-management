package com.parvoli.baby;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BabyServiceTest {

    @Mock
    private BabyRepository babyRepository;
    private BabyService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BabyService(babyRepository);
    }


    @Test
    void canFindAllBabies() {
        // when
        underTest.findAllBabies();
        // then
        verify(babyRepository).findAll();
    }

    @Test
    void canAddBaby() {
        // given
        Baby baby = new Baby("Iggy", "Adrian", LocalDate.of(2023, Month.APRIL, 12));
        // when
        underTest.addBaby(baby);
        // then
        verify(babyRepository).save(baby);
    }

}