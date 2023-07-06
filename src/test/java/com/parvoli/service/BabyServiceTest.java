package com.parvoli.service;

import com.parvoli.exception.BadRequestException;
import com.parvoli.model.Baby;
import com.parvoli.repository.BabyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BabyServiceTest {

    @Mock
    private BabyRepository babyRepository;
    private CourseService courseService;
    private RoomService roomService;

    private BabyService underTest;
    private Clock clock;

    @BeforeEach
    void setUp() {
        underTest = new BabyService(babyRepository, courseService, roomService, clock);
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


    @Test
    void canFindBabyById() {
        // given
        Long babyId = 1L;
        Baby baby = new Baby();

        // when
        when(babyRepository.findById(babyId)).thenReturn(Optional.of(baby));
        Baby result = underTest.findBabyByIdElseNotFound(babyId) ;

        // then
        verify(babyRepository, times(1)).findById(babyId);
    }

    @Test
    void willThrowWhenBabyExists() {
        // given
        Baby baby = new Baby("Iggy", "Adrian", LocalDate.of(2023, Month.APRIL, 12));

        given(babyRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                baby.getFirstName(),
                baby.getLastName(),
                baby.getDateOfBirth()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.addBaby(baby))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(
                        "Baby: " + baby.getFirstName() + " born " + baby.getDateOfBirth() + " exists in the database"
                );
    }

    @Test
    @Disabled
    void findBabyCourse(){

    }

}