package timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StoreTimeStamp {
    public static String getTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
