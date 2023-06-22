package com.parvoli.utils.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Period;

@Converter
public class PeriodAttributeConverter implements AttributeConverter<Period, String> {

    @Override
    public String convertToDatabaseColumn(Period period) {
        if (period == null) {
            return null;
        }
        return period.toString();
    }

    @Override
    public Period convertToEntityAttribute(String periodString) {
        if (periodString == null) {
            return null;
        }
        return Period.parse(periodString);
    }
}
