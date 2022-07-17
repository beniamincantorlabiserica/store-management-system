package viewmodel;

import model.User;

import java.rmi.RemoteException;

public interface StartViewModelInterface {
    User login(String password);
    void reset();
    boolean isNetwork();
    void retryNetwork();
    void logout();
    String getOpeningHours();
    String getClosingHours();
    boolean getLockedState() throws RemoteException;
    boolean masterCheck(String s);
    void setLockedState(boolean b);
}

