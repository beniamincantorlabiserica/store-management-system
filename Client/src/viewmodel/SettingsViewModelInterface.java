package viewmodel;

public interface SettingsViewModelInterface {

    void setOpeningHours(String s) throws RuntimeException;

    void setClosingHours(String s) throws RuntimeException;

    String getOpeningHours();

    String getClosingHours();

    boolean masterCheck(String s);

    void updatePassword(String role, String password);

    void setLockedState(boolean b);
}
