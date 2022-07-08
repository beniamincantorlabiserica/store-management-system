package viewmodel;

import model.User;

public interface StartViewModelInterface {
    User login(String password);
    void reset();
}

