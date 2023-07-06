package com.parvoli.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Entity
@Component
public class Baby extends Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy="baby", cascade = CascadeType.REMOVE)
    private Set<Registration> registrations;

    @ManyToOne
    private Room room;

    public Baby() {
    }

    public Baby(String firstName, String lastName, LocalDate dateOfBirth) {
        super(firstName, lastName, dateOfBirth);
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getAgeInTotalDays() {
        LocalDate currDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(dateOfBirth, currDate);
    }
    public Period getAgeAsPeriod() {
        LocalDate currDate = LocalDate.now();
        return Period.between(dateOfBirth, currDate);
    }
}

