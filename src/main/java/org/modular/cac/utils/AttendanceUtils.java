package org.modular.cac.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

@UtilityClass
public class AttendanceUtils {

    public static LocalDateTime getDateFrom(Month month, Integer day){
        int year = LocalDate.now().getYear();
        var date = LocalDate.of(year,month,day);

        return date.atStartOfDay();
    }

    public static Month parseMonth(String month){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "MX"));
            TemporalAccessor temporal = formatter.parse(month.toLowerCase());
            return  Month.from(temporal);
        }catch (Exception e){
            return null;
        }

    }

}
