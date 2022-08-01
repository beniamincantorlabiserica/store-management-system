package database;

public interface PreferenceDAO {
    boolean getLockedState();

    void setLockedState(boolean b) throws RuntimeException;
}
