package viewmodel;

public interface DashBoardViewModelInterface {
    void logout();
    void reset();
    void setOpeningHours(String s);
    void setClosingHours(String s);
    String getOpeningHours();
    String getClosingHours();
    void startUpdateThread();
    boolean masterCheck(String s);
    void updatePassword(String role, String password);
}
