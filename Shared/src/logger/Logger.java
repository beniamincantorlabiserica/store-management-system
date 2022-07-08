package logger;

public class Logger {
    private static Logger logger;

    // -1 - logging off / 0 - info only / 1 - errors only / 2 - errors and warnings / 3 - errors, warnings and info
    // use 0 for distribution and 3 for general debugging / development
    private short logLevel;

    private Logger() {
        this.logLevel = 3;
    }

    public static Logger getInstance() {
        if(logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public short getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(short logLevel) {
        if(logLevel < -1 || logLevel > 3)
            throw new RuntimeException("LogLevel out of bounds.\nAllowed values: -1 -> 3 [NO LOGS / INFO ONLY / ERRORS ONLY / ERRORS AND WARNINGS / ERRORS WARNINGS AND INFO]");
        this.logLevel = logLevel;
    }

    public void log (String logLine) {
        log(LoggerType.INFO, logLine);
    }

    public void log (LoggerType loggerType, String logLine) {
        print(loggerType + ": " + logLine + ".");
    }

    private void print(String s) {
        System.out.println(s);
    }

}
