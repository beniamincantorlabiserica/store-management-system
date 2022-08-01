package networking;



import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUsersModel extends Remote {
    /**
     * @param password expects a valid password for either the store manager or the cashier
     * @return a User object containing the session information about the user logged in
     * @throws RuntimeException if a user was not found to match the password
     */
    User login(String password) throws RemoteException;

    /**
     * @param role     expects either "StoreManager" or "Cashier" as arguments to specify the role for which
     *                 the password should be changed
     * @param password expects the new password for the specified role
     */
    void updatePassword(String role, String password) throws RemoteException;

    /**
     * @return the master password as String
     * @throws RemoteException if any errors occur
     */
    String getMasterPassword() throws RemoteException;
}
