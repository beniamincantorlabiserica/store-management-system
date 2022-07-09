package model;

public interface DashboardModel {
    String getStoreStatus();
    String getCheckoutsToday();
    String getItemsToday();
    String getSalesToday();
    String getCheckoutsThisMonth();
    String getItemsThisMonth();
    String getSalesThisMonth();
    void setOpeningHours(String openingTime) throws RuntimeException;
    void setClosingHours(String closingTime) throws RuntimeException;
    String getClosingHours();
    String getOpeningHours();
    int getClosingHourInteger();
    int getOpeningHourInteger();
}
