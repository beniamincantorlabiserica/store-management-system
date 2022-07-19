package model;

public interface DashboardModel {
    String getStoreStatus();
    String getCheckoutsToday();
    String getItemsToday();
    String getSalesToday();
    String getCheckoutsThisMonth();
    String getItemsThisMonth();
    String getSalesThisMonth();
    String getClosingHours();
    String getOpeningHours();
    int getClosingHourInteger();
    int getOpeningHourInteger();
}
