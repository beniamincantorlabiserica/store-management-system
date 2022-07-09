package model;

public interface DashboardModel {
    String getStoreStatus();
    int getCheckoutsToday();
    int getItemsToday();
    int getSalesToday();
    int getCheckoutsThisMonth();
    int getItemsThisMonth();
    int getSalesThisMonth();
}
