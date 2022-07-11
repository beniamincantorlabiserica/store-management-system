package model;

public interface ServerDashboardModel {
    WorkingHours getWorkingHours();
    void setOpeningHours(String openingTime);
    void setClosingHours(String closingTime);
    String getCheckoutsToday();
    String getItemsToday();
    String getSalesToday();
    String getCheckoutsThisMonth();
    String getItemsThisMonth();
    String getSalesThisMonth();
}
