package model;

public interface ServerUsersModel {
    /**
     * @param password expects a valid password for either the store manager or the cashier
     * @return a User object containing the session information about the user logged in
     */
    User login(String password);

    /**
     * @param role     expects either "StoreManager" or "Cashier" as arguments to specify the role for which
     *                 the password should be changed
     * @param password expects the new password for the specified role
     */
    void updatePassword(String password, String role);

    /**
     * @return the master password as String
     */
    String getMasterPassword();
}
