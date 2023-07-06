package com.parvoli.repository;

import com.parvoli.model.Baby;
import com.parvoli.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("" +
            "SELECT COUNT (r) > 0 FROM Registration r " +
            "WHERE r.baby = :baby " +
            "AND (" +
                "(r.startDate <= :startDate AND r.endDate >= :startDate) " +
                "OR (r.startDate <= :endDate And r.endDate >= :endDate) " +
                "OR (r.startDate >= :startDate AND :endDate IS NULL) " +
                "OR ((r.startDate <= :endDate) AND (r.endDate IS NULL))" +
            ")")
    boolean hasOverlappingRegistrations(Baby baby, LocalDate startDate, LocalDate endDate);
}
