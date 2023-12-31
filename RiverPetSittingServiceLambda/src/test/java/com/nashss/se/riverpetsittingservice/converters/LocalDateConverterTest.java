package com.nashss.se.riverpetsittingservice.converters;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateConverterTest {
    LocalDateConverter converter = new LocalDateConverter();
    @Test
    void convert_withProperLocalDate_returnsProperString() {
        //Given
        LocalDate aDate = LocalDate.now();
        //When
        String converted = converter.convert(aDate);
        String expected = LocalDate.now().toString();
        assertEquals(converted, expected);

    }

    @Test
    void unconvert_withProperString_returnProperLocalDate() {
        //Given
        String date = "2023-09-01";
        //When
        LocalDate result = converter.unconvert(date);
        LocalDate expected = LocalDate.of(2023, 9, 1);

        assertEquals(expected, result);
    }
}