package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.StringTokenizer;

public class WorkingHours implements Serializable {
    private LocalTime openingTime;
    private LocalTime closingTime;

    public WorkingHours(String workingHours) {
        init(workingHours);
    }

    private void init(String workingHours) {
        StringTokenizer tokenizer = new StringTokenizer(workingHours, " ");
        openingTime = LocalTime.parse(tokenizer.nextToken());
        closingTime = LocalTime.parse(tokenizer.nextToken());
    }
}
