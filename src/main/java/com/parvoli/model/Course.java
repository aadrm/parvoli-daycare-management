package com.parvoli.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.parvoli.utils.converters.PeriodAttributeConverter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Convert(converter = PeriodAttributeConverter.class)
    @Column(nullable = false)
    private Period minAge;

    @Convert(converter = PeriodAttributeConverter.class)
    @Column(nullable = false)
    private Period maxAge;

    @Transient
    private LocalDate earliestAcceptedDateOfBirth;

    @Transient
    private LocalDate latestAcceptedDateOfBirth;

    @OneToMany(mappedBy = "course", cascade = CascadeType.DETACH)
    @JsonBackReference
    private Set<Room> rooms;

    public Course() {}

    public Course(Period minAge, Period maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public static Course createFromMonthsAndDays(int minMonths, int minDays, int maxMonths, int maxDays) {
        Period minAge = Period.of(0, minMonths, minDays);
        Period maxAge = Period.of(0, maxMonths, maxDays);
        return new Course(minAge, maxAge);
    }

    public Long getId() { return id; };


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Period getMinAge() {
        return minAge;
    }

    public Period getMaxAge() {
        return maxAge;
    }

    public Set<Room> getRooms() { return rooms; }

    public LocalDate getEarliestAcceptedDateOfBirth() {
        return LocalDate.now().minus(getMaxAge());
    }

    public LocalDate getLatestAcceptedDateOfBirth() {
        return LocalDate.now().minus(getMinAge());
    }
}
