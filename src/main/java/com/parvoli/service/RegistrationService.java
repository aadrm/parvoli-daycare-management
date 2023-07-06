package com.parvoli.service;

import com.parvoli.exception.BadRequestException;
import com.parvoli.model.Registration;
import com.parvoli.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Autowired RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public void addRegistration(Registration registration) {
        validateRegistration(registration);
        registrationRepository.save(registration);
    }

    private void validateRegistration(Registration registration) {
        checkForInvalidDateOrder(registration);
        checkForRegistrationConflicts(registration);
    }

    private void checkForInvalidDateOrder(Registration registration) {
        if (registration.getEndDate() != null && registration.getStartDate().isAfter(registration.getEndDate())) {
            throw new BadRequestException(
                    "Registration start date is after the de-registration date"
            );
        }
    }

    private void checkForRegistrationConflicts(Registration registration) {
        boolean registrationConflicts = registrationRepository.
                hasOverlappingRegistrations(
                        registration.getBaby(),
                        registration.getStartDate(),
                        registration.getEndDate()
                );
        if (registrationConflicts) {
            throw new BadRequestException("Registration conflicts");
        }
    }
}
