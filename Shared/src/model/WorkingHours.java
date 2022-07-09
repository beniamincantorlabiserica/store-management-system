package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.StringTokenizer;

public class WorkingHours implements Serializable {
    private LocalTime openingTime;
    private LocalTime closingTime;

    public WorkingHours(String workingHours) {
        init(workingHours);
    }

    public WorkingHours() {
        this("09:00 17:00");
    }

    private void init(String workingHours) {
        StringTokenizer tokenizer = new StringTokenizer(workingHours, " ");
        openingTime = LocalTime.parse(tokenizer.nextToken());
        closingTime = LocalTime.parse(tokenizer.nextToken());
    }

    public String getSQLReadyWorkingHours() {
        return openingTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " " + closingTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getOpeningTime() {
        return openingTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getClosingTime() {
        return closingTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setOpeningTime(String openingTime) {
        LocalTime temporaryTime;
        try {
            temporaryTime = LocalTime.parse(openingTime);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("TIME_PARSE_FAILED");
        }
        if(temporaryTime.isAfter(closingTime)) {
            throw new RuntimeException("TIME_INCORRECT");
        }
        this.openingTime = temporaryTime;
    }

    public void setClosingTime(String closingTime) {
        LocalTime temporaryTime;
        try {
            temporaryTime = LocalTime.parse(closingTime);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("TIME_PARSE_FAILED");
        }
        if(temporaryTime.isBefore(openingTime)) {
            throw new RuntimeException("TIME_INCORRECT");
        }
        this.closingTime = temporaryTime;
    }
}
