package com.example.customermanagement.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CustomCalendarDeserializer extends JsonDeserializer<Calendar> {

    @Override
    public Calendar deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String dateAsString = jsonparser.getText();
        try {
            Date date = CustomCalendarSerializer.FORMATTER.parse(dateAsString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}