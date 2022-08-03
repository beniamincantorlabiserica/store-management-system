package viewmodel;

public interface SettingsViewModelInterface {
    /**
     * @return the opening time as "HH:mm"
     */
    String getOpeningHours();

    /**
     * @param s expects a String with the format of "HH:mm" to represent the new
     *          opening time for the store
     * @throws RuntimeException if the format of the String is invalid or if the desired
     *                          opening hours are after the closing hours
     */
    void setOpeningHours(String s) throws RuntimeException;

    /**
     * @return the closing time as "HH:mm"
     */
    String getClosingHours();

    /**
     * @param s expects a String with the format of "HH:mm" to represent the new
     *          closing time for the store
     * @throws RuntimeException if the format of the String is invalid or if the desired
     *                          closing hours are before the opening hours
     */
    void setClosingHours(String s) throws RuntimeException;

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

    void validateNewPassword(String s);

    /**
     * @param b expects a boolean to represent the new status of the store where
     *          true is locked, false is unlocked
     */
    void setLockedState(boolean b);
}
