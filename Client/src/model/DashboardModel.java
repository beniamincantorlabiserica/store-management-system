package model;

import java.time.LocalTime;

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
    LocalTime getClosingHours();

    /**
     * @return the opening time as "HH:mm"
     */
    LocalTime getOpeningHours();

    /**
     * @return the closing hour as int
     */
    int getClosingHourInteger();

    /**
     * @return the opening hour as int
     */
    int getOpeningHourInteger();

    /**
     * Formula
     * x = now() - openingHour() // hours passed
     * t = closingHour() - openingHour()
     * 1 ------- t
     * y  ------- x
     * y = x / t
     * y = ( now() - getOpeningHour() ) / ( closingHour() - openingHour() )
     *
     * @return the current working day progress (from opening hour to closing hour is from 0 to 100) as a number on a scale from 0 to 1, where 0 is
     * the first working hour and 1 is the last OR 0 if the store is outside working hours
     */
    double getDayProgress();

    /**
     * Formula
     * x = LocalDateTime.now().getDayOfMonth()
     * t = LocalDateTime.now().getCurrentMonthTotalDays()
     * 1 ------- t
     * y ------- x
     * y = LocalDateTime.now().getDayOfMonth() / LocalDateTime.now().getCurrentMonthTotalDays()
     *
     * @return the current month progress (from first to last day is from 0 to 100) as a number on a scale from 0 to 1, where 0 is the first day of the month
     * and 1 is the last day
     */
    double getMonthProgress();
}
