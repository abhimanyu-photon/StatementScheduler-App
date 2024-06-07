package com.jpmc.netbanking.scheduler.utils;


import io.micrometer.common.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private DateUtils() {}

    public static Date convertStringToDate(String date, String dateFormat) throws ParseException {
        return new SimpleDateFormat(dateFormat).parse(date);
    }

    public static LocalDate convertStringToLocalDate(String date, String dateFormat){
        if(StringUtils.isBlank(dateFormat)) {
            dateFormat = DEFAULT_DATE_FORMAT;
        }
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format");
        }
    }

    public static LocalDate convertStringToLocalDate(String date){
        return convertStringToLocalDate(date, DEFAULT_DATE_FORMAT);
    }
}
