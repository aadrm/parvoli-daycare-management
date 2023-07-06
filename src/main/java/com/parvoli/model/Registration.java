package com.parvoli.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Registration {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne
    @JsonBackReference
    private Baby baby;

    public Registration() {
    }

    public Registration(Long id, LocalDate registerDate, LocalDate deregisterDate, Baby baby) {
        this.id = id;
        this.startDate = registerDate;
        this.endDate = deregisterDate;
        this.baby = baby;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate registerDate) {
        this.startDate = registerDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate deregisterDate) {
        this.endDate = deregisterDate;
    }

    public Baby getBaby() {
        return baby;
    }

    public void setBaby(Baby baby) {
        this.baby = baby;
    }
}
