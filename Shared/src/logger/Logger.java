package logger;

public class Logger {
    private static Logger logger;

    // 0 - logging off / 1 - info only / 2 - warnings and info / 3 - errors, warnings and info / 4 - errors, warnings, info and debug
    // use 0 for distribution and 3 for general debugging / development
    private int logLevel;

    private Logger() {
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

    public void log (String logLine) {
        log(LoggerType.INFO, logLine);
    }

    public void log (LoggerType loggerType, String logLine) {
        if(logLevel < loggerType.getMinLogLevel())
            return;
        print(loggerType + ": " + logLine + "." + LoggerType.COLOR_RESET);
    }

    private void print(String s) {
        System.out.println(s);
    }

}
