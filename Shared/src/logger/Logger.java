package logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Logger {

    private static Logger logger;
    private final ArrayList<String> logLines;

    // 0 - logging off / 1 - info only / 2 - warnings and info / 3 - errors, warnings and info / 4 - errors, warnings, info and debug
    // use 0 for distribution and 3 for general debugging / development
    private int logLevel;

    private Logger() {
        logLines = new ArrayList<>();
        this.logLevel = 4;
    }

    public static Logger getInstance() {
        if(logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        if(logLevel < 0 || logLevel > 3)
            throw new RuntimeException("LogLevel out of bounds.\nAllowed values: 0 -> 3 [NO LOGS / INFO ONLY / WARNINGS AND INFO / ERRORS, WARNINGS AND INFO]");
        this.logLevel = logLevel;
    }

    public String log (String logLine) {
        return log(LoggerType.INFO, logLine);
    }

    public String log (LoggerType loggerType, String logLine) {
        if(logLevel < loggerType.getMinLogLevel())
            return null;
        String unColoredLogLine = ": " + logLine + ".";
        print(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) + " " + loggerType + unColoredLogLine + LoggerType.COLOR_RESET);
        return unColoredLogLine;
    }

    private void print(String s) {
        logLines.add(s);
        System.out.println(s);
    }

    public String getLastLogLine() {
        return logLines.get(logLines.size()-1);
    }
}
