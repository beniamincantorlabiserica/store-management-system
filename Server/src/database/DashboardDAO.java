package database;

import model.WorkingHours;

public interface DashboardDAO {
    WorkingHours getWorkingHours();

    String getCheckoutsToday();

    String getItemsToday();

    String getSalesToday();

    String getCheckoutsThisMonth();

    String getItemsThisMonth();

    String getSalesThisMonth();

    void setOpeningTime(String openingTime);

    void setClosingTime(String closingTime);
}
