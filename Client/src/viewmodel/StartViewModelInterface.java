package viewmodel;

import model.User;

public interface StartViewModelInterface {
    User login(String password);
    void reset();
    boolean isNetwork();
    void retryNetwork();
    void logout();
    String getOpeningHours();
    String getClosingHours();
}

