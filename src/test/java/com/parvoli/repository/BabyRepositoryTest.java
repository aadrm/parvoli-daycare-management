package com.parvoli.repository;


import com.parvoli.model.Baby;
import com.parvoli.repository.BabyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BabyRepositoryTest {

    @Autowired
    private BabyRepository underTest;

    @Test
    void babyExists() {
        // given
        String firstName = "Iggy";
        String lastName = "Adrian";
        LocalDate dob = LocalDate.of(2023, Month.APRIL, 12);
        Baby baby = new Baby(
                firstName,
                lastName,
                dob
        );
        underTest.save(baby);

        // when
        boolean actual = underTest.existsByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dob);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void babyNotExists() {
        // given
        String firstName = "Iggy";
        String lastName = "Adrian";
        LocalDate dob = LocalDate.of(2023, Month.APRIL, 12);
        Baby baby = new Baby(
                firstName,
                lastName,
                dob
        );
        underTest.save(baby);

        // when
        String anotherFirstName = "Antonio";
        boolean actual = underTest.existsByFirstNameAndLastNameAndDateOfBirth(anotherFirstName, lastName, dob);

        // then
        assertThat(actual).isFalse();
    }
}