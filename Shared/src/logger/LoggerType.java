package logger;

public enum LoggerType {
    WARNING("[WARNING]"),
    ERROR("[ERROR]"),
    INFO("[INFO]");

    private String label;

    LoggerType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
