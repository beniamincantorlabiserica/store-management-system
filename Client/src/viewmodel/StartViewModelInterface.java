package viewmodel;

import model.User;

import java.rmi.RemoteException;

public interface StartViewModelInterface {
    /**
     * @param password expects a valid password for either the store manager or the cashier
     * @return a User object containing the session information about the user logged in
     */
    User login(String password);

    /**
     * performs viewModel reset()
     */
    void reset();

    /**
     * @return true if the client could connect to the server, false otherwise
     */
    boolean isNetwork();

    /**
     * retries to connect to the server
     */
    void retryNetwork();

    /**
     * resets the session-specific details and logs out the user
     */
    void logout();

    /**
     * @return the opening time as "HH:mm"
     */
    String getOpeningHours();

    /**
     * @return the closing time as "HH:mm"
     */
    String getClosingHours();

    /**
     * @return the locked state of the store, true is locked, false is unlocked
     * @throws RemoteException if there is a fetch-from-database error
     */
    boolean getLockedState() throws RemoteException;

    /**
     * @param b expects a boolean to represent the new status of the store where
     *          true is locked, false is unlocked
     */
    void setLockedState(boolean b);

    /**
     * @param s expects a string containing the master password
     * @return true if the password is valid, false otherwise
     */
    boolean masterCheck(String s);

    /**
     * @return true if within working hours, false otherwise
     */
    boolean isOpen();
}

