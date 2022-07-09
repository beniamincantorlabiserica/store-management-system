package model;

public interface DashboardModel {
    String getStoreStatus();
    int getCheckoutsToday();
    int getItemsToday();
    int getSalesToday();
    int getCheckoutsThisMonth();
    int getItemsThisMonth();
    int getSalesThisMonth();
    void setOpeningHours(String openingTime) throws RuntimeException;
    void setClosingHours(String closingTime) throws RuntimeException;
    String getClosingHours();
    String getOpeningHours();
}
