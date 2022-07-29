package model;

public interface DashboardModel {
    /**
     * @return "OPEN" if the store is within working hours, "CLOSED" otherwise
     */
    String getStoreStatus();

    /**
     * @return the number of checkouts having the date set to the current day
     */
    String getCheckoutsToday();

    /**
     * @return the number of items * quantity from the checkouts having the date set to the current day
     */
    String getItemsToday();

    /**
     * @return the income given all the items * quantity from the checkouts having the date set to the current day
     */
    String getSalesToday();

    /**
     * @return the number of checkouts having the date within 30 days in the past from the current day
     */
    String getCheckoutsThisMonth();

    /**
     * @return the number of items * quantity from the checkouts having the date within 30 days in the past from the current day
     */
    String getItemsThisMonth();

    /**
     * @return the income given all the items * quantity checkouts having the date within 30 days in the past from the current day
     */
    String getSalesThisMonth();

    /**
     * @return the closing time as "HH:mm"
     */
    String getClosingHours();

    /**
     * @return the opening time as "HH:mm"
     */
    String getOpeningHours();

    /**
     * @return the closing hour as int
     */
    int getClosingHourInteger();

    /**
     * @return the opening hour as int
     */
    int getOpeningHourInteger();
}
