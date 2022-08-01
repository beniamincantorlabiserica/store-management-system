package model;

public interface ServerDashboardModel {
    /**
     * @return the current working hours (open/close schedule) as a WorkingHours object
     */
    WorkingHours getWorkingHours();

    /**
     * @param openingTime expects a String with the format of "HH:mm" to represent the new
     *                    opening time for the store
     */
    void setOpeningHours(String openingTime);

    /**
     * @param closingTime expects a String with the format of "HH:mm" to represent the new
     *                    closing time for the store
     */
    void setClosingHours(String closingTime);

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
}
