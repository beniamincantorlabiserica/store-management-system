package util.logger;

public enum LoggerType {
    DEBUG(LoggerType.COLOR_BLUE + "[DEBUG]", 4),
    ERROR(LoggerType.COLOR_RED + "[ERROR]", 3),
    WARNING(LoggerType.COLOR_YELLOW + "[WARNING]", 2),
    INFO("[INFO]", 1);

    public static final String COLOR_RESET = "\033[0m"; // DEFAULT
    private static final String COLOR_RED = "\033[0;31m"; // RED
    private static final String COLOR_YELLOW = "\033[0;33m"; // YELLOW
    private static final String COLOR_BLUE = "\033[0;34m"; // BLUE
    private final String label;
    private final int minLogLevel;

    LoggerType(String label, int minLogLevel) {
        this.label = label;
        this.minLogLevel = minLogLevel;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getMinLogLevel() {
        return minLogLevel;
    }
}
