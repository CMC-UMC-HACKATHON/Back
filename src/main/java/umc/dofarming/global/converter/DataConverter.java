package umc.dofarming.global.converter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataConverter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static String convertToTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

}
