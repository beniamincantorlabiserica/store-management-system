package client.viewmodel;

import java.rmi.RemoteException;

public interface StartViewModelInterface {
    boolean login(String password);

    void reset();
}

