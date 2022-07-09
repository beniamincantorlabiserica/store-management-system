package viewmodel;

public interface DashBoardViewModelInterface {
    void logout();
    void reset();
    void setOpeningHours(String s);
    void setClosingHours(String s);
    String getOpeningHours();
    String getClosingHours();
}
