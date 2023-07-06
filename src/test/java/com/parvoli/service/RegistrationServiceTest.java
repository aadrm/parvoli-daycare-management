package com.parvoli.service;

import com.parvoli.exception.BadRequestException;
import com.parvoli.model.Registration;
import com.parvoli.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void addRegistration_ValidRegistration_Success() {
        // given
        Registration registration = new Registration();
        registration.setStartDate(LocalDate.of(2023, 1, 1));

        // when
        when(registrationRepository.hasOverlappingRegistrations(any(), any(), any())).thenReturn(false);

        registrationService.addRegistration(registration);

        // then
        verify(registrationRepository, times(1)).save(registration);
    }

    @Test
    public void addRegistration_OverlappingRegistrations_ThrowsBadRequestException() {
        // given
        Registration registration = new Registration();
        registration.setStartDate(LocalDate.of(2023, 1, 1));

        // when
        when(registrationRepository.hasOverlappingRegistrations(any(), any(), any())).thenReturn(true);

        // then
        assertThrows(BadRequestException.class, () -> registrationService.addRegistration(registration));
    }

    @Test
    public void addRegistration_InvalidDateOrder_ThrowsBadRequestException() {
        // given
        Registration registration = new Registration();
        registration.setStartDate(LocalDate.of(2023, 1, 2));
        registration.setEndDate(LocalDate.of(2023, 1, 1));

        // then
        assertThrows(BadRequestException.class, () -> registrationService.addRegistration(registration));
    }

}