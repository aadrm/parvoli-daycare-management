package com.parvoli.baby;

import com.parvoli.person.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Entity
public class Baby extends Person {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private int ageInMonths;

    @Transient
    private int ageInDays;

    @Transient
    private int ageInRemainingDaysAfterMonths;

    protected Baby() {
    }

    public Baby(String firstName, String lastName, LocalDate dob) {
        super(firstName, lastName, dob);
    }

    public int getAgeInDays() {
        LocalDate currDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(dob, currDate);
    }

    public int getAgeInMonths() {
        LocalDate currDate = LocalDate.now();
        return (int) ChronoUnit.MONTHS.between(dob, currDate);
    }

    public int getAgeInRemainingDaysAfterMonths() {
        LocalDate currDate = LocalDate.now();
        return Period.between(dob, currDate).getDays();
    }
}
