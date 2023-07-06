package com.parvoli.repository;

import com.parvoli.model.Baby;
import com.parvoli.model.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RegistrationRepositoryTest {

    @Autowired
    private RegistrationRepository underTest;

    @Autowired
    private BabyRepository babyRepository;
    private final String babyWithDefinedRegistrationName = "DefinedEnd";
    private final String babyWithOpenEndRegistrationName = "OpenEnd";
    private Baby babyWithDefinedRegistration;
    private Baby babyWithOpenEndRegistration;

    // Creates a Baby with two Registrations.
    // One registration has a register and deregister date.
    // The other registration has only a register date at a later time than the deregister date
    // of the first registration.
    // The Baby object can be used to test different conflicts between new registrations
    // and the existing ones.
    @BeforeEach
    void setUp() {
        babyWithDefinedRegistration = new Baby(
                babyWithDefinedRegistrationName,
                "Defined",
                LocalDate.of(2023, 1, 1)
        );
        babyRepository.save(babyWithDefinedRegistration);
        babyWithOpenEndRegistration = new Baby(
                babyWithOpenEndRegistrationName,
                "End",
                LocalDate.of(2023, 1, 2)
        );
        babyRepository.save(babyWithOpenEndRegistration);

        LocalDate existingRegisterDate = LocalDate.of(2023, 1, 10);
        LocalDate existingDeregisterDate = LocalDate.of(2023, 1, 20);

        Registration existingRegistration = new Registration();
        existingRegistration.setBaby(babyWithDefinedRegistration);
        existingRegistration.setStartDate(existingRegisterDate);
        existingRegistration.setEndDate(existingDeregisterDate);
        underTest.save(existingRegistration);

        existingRegisterDate = LocalDate.of(2023, 1, 25);
        existingRegistration = new Registration();
        existingRegistration.setBaby(babyWithOpenEndRegistration);
        existingRegistration.setStartDate(existingRegisterDate);
        existingRegistration.setEndDate(null);
        underTest.save(existingRegistration);
    }

    @ParameterizedTest
    @CsvSource({
            //
            babyWithDefinedRegistrationName + ", 2023-01-20, 2023-01-25, true",
            babyWithDefinedRegistrationName + ", 2023-01-05, 2023-01-10, true",
            babyWithDefinedRegistrationName + ", 2023-01-15, 2023-01-15, true",
            babyWithDefinedRegistrationName + ", 2023-01-01, , true",
            babyWithDefinedRegistrationName + ", 2023-01-15, , true",
            babyWithOpenEndRegistrationName + ", 2023-01-26, 2023-01-26, true",
            babyWithDefinedRegistrationName + ", 2023-01-21, 2023-01-30, false",
            babyWithDefinedRegistrationName + ", 2023-01-08, 2023-01-09, false",
            babyWithOpenEndRegistrationName + ", 2023-01-10, 2023-01-24, false",
    })
    void hasOverlappingRegistrations_ShouldReturnExpected(
            String babyName,
            LocalDate startDate,
            LocalDate endDate,
            boolean expected) {
        // given
        Baby baby = babyRepository.findByFirstName(babyName);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(baby, startDate, endDate);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void dateRangeLowerBoundOverlapsWithExistingRegistration() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 20);
        LocalDate endDate = LocalDate.of(2023, 1, 25);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, endDate);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void dateRangeUpperBoundOverlapsWithExistingRegistration() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, endDate);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void dateRangeIsWithinOverlappingExistingRegistration() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 15);
        LocalDate endDate = LocalDate.of(2023, 1, 16);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, endDate);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void dateRangeWithoutDefinedEndAndStartDateBeforeExistingStartDateOverlap() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 1);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, null);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void dateRangeWithoutDefinedEndAndStartDateBeforeExistingEndDateOverlap() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 15);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, null);

        // then
        assertThat(actual).isTrue();
    }


    @Test
    void dateRangeOverlapsIfAfterOpenEndExistingRegistration() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 24);
        LocalDate endDate = LocalDate.of(2023, 1, 25);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithOpenEndRegistration, startDate, endDate);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void dateRangeHasNoOverlapIfBeforeExistingRegistration() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 8);
        LocalDate endDate = LocalDate.of(2023, 1, 9);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, endDate);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void dateRangeHasNoOverlapIfAfterExistingRegistration() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 21);
        LocalDate endDate = LocalDate.of(2023, 1, 22);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, endDate);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void dateRangeWithOpenEndHasNoOverlapIfStartDateIsAfterExistingEndDate() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 21);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithDefinedRegistration, startDate, null);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void dateRangeHasNoOverlapIfEndDateIsBeforeStartDateOfExisting() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 21);
        LocalDate endDate = LocalDate.of(2023, 1, 24);

        // when
        boolean actual = underTest.hasOverlappingRegistrations(babyWithOpenEndRegistration, startDate, endDate);

        // then
        assertThat(actual).isFalse();
    }
}
