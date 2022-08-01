package database;

public interface DAOManager {
    UserDAO getUserDAO();

    DashboardDAO getDashboardDAO();

    PreferenceDAO getPreferenceDAO();

    InventoryDAO getInventoryDAO();

    CheckoutDAO getCheckoutDAO();
}
