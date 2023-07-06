package com.parvoli.repository;

import com.parvoli.model.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BabyRepository  extends JpaRepository<Baby, Long> {

    boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);

    Baby findByFirstName(String firstName);

    List<Baby> getBabiesByDateOfBirthAfterAndDateOfBirthBefore(LocalDate after, LocalDate before);
}
