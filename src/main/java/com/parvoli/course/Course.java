package com.parvoli.course;

import com.parvoli.utils.converters.PeriodAttributeConverter;
import jakarta.persistence.*;

import java.time.Period;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = PeriodAttributeConverter.class)
    @Column(nullable = false)
    private Period minAge;

    @Convert(converter = PeriodAttributeConverter.class)
    @Column(nullable = false)
    private Period maxAge;

    protected Course() {}

    public Course(Period minAge, Period maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public static Course createFromMonthsAndDays(int minMonths, int minDays, int maxMonths, int maxDays) {
        Period minAge = Period.of(0, minMonths, minDays);
        Period maxAge = Period.of(0, maxMonths, maxDays);
        return new Course(minAge, maxAge);
    }

    public Period getMinAge() {
        return minAge;
    }

    public Period getMaxAge() {
        return maxAge;
    }
}
