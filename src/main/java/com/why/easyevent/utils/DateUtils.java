package com.why.easyevent.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/11 10 17
 * @Version: v1.0
 */
public class DateUtils {
    public static LocalDate coverStringToLocalDate(String isoDataString) {
        DateTimeFormatter struct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(isoDataString, struct);
        return parse;
    }

    public static String coverLocalDateToString(LocalDate date) {
        DateTimeFormatter struct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = struct.format(date);
        return format;
    }

}
