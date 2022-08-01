package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {
    private static DateTimeManager instance;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private DateTimeManager() {
    }

    public static DateTimeManager getInstance() {
        if (instance == null) {
            instance = new DateTimeManager();
        }
        return instance;
    }

    public String getTime() {
        return getCurrentTime().format(TIME_FORMATTER);
    }

    public String getDate() {
        return getCurrentTime().format(DATE_FORMATTER);
    }

    public String getDayOfWeekName() {
        return getCurrentTime().getDayOfWeek().name();
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

}
