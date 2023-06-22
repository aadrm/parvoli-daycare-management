package com.parvoli.baby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BabyRepository  extends JpaRepository<Baby, Long> {
//    @Query("" +
//            "SELECT CASE WHEN COUNT (b) > 0 THEN " +
//            "TRUE ELSE FALSE END " +
//            "FROM Baby b " +
//            "WHERE b.firstName = ?1
//    )
    boolean existsByFirstNameAndLastNameAndDob(String firstName, String lastName, LocalDate dob);
}
