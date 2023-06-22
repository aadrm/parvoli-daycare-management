package com.parvoli.person;

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
    protected LocalDate dob;

    @Transient
    private String fullName;

    @Transient
    private int age;

    protected Person() {
    }

    public Person(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        LocalDate currDate = LocalDate.now();
        return Period.between(dob, currDate).getYears();
    }
}
