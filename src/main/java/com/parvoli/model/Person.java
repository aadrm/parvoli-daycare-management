package com.parvoli.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@MappedSuperclass
public abstract class Person {

    @Column(nullable = false)
    protected String firstName;

    @Column(nullable = false)
    protected String lastName;

    @Column(nullable = false)
    protected LocalDate dateOfBirth;

    @Transient
    private String fullName;

    @Transient
    private int age;

    public Person() {
    }

    public Person(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        LocalDate currDate = LocalDate.now();
        return Period.between(dateOfBirth, currDate).getYears();
    }
}
