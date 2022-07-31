package model;

public interface UserModel {
    /**
     * @param password expects a valid password for either the store manager or the cashier
     * @return a User object containing the session information about the user logged in
     * @throws RuntimeException if a user was not found to match the password
     */
    User login(String password) throws RuntimeException;

    /**
     * resets the session-specific details and logs out the user
     */
    void logout();

    /**
     * @return true if there is a user logged in, false otherwise
     */
    boolean isLoggedIn();

    /**
     * @param s expects a string containing the master password
     * @return true if the password is valid, false otherwise
     */
    boolean masterCheck(String s);

    /**
     * @param role     expects either "StoreManager" or "Cashier" as arguments to specify the role for which
     *                 the password should be changed
     * @param password expects the new password for the specified role
     */
    void updatePassword(String role, String password);

    /**
     * @return the current logged-in User, null otherwise
     */
    User getUser();
}
