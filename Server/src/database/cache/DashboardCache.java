package database.cache;

import database.DashboardDAO;
import database.manager.DashboardDatabaseManager;
import model.WorkingHours;
import util.logger.Logger;

public class DashboardCache implements DashboardDAO {
    private final DashboardDatabaseManager dashboardDatabaseManager;

    private WorkingHours workingHours;
    private boolean isWorkingHoursCacheValid;

    public DashboardCache(DashboardDatabaseManager dashboardDatabaseManager) {
        this.dashboardDatabaseManager = dashboardDatabaseManager;
        initialLoad();
    }

    private void initialLoad() {
        Logger.getInstance().log("Building caches..");
        updateWorkingHoursCache();
        Logger.getInstance().log("Caches built");
    }

    @Override
    public WorkingHours getWorkingHours() {
        if (!isWorkingHoursCacheValid) {
            updateWorkingHoursCache();
        }
        return workingHours;
    }

    private void updateWorkingHoursCache() {
        this.workingHours = dashboardDatabaseManager.getWorkingHours();
        isWorkingHoursCacheValid = true;
    }

    @Override
    public String getCheckoutsToday() {
        return dashboardDatabaseManager.getCheckoutsToday();
    }

    @Override
    public String getItemsToday() {
        return dashboardDatabaseManager.getItemsToday();
    }

    @Override
    public String getSalesToday() {
        return dashboardDatabaseManager.getSalesToday();
    }

    @Override
    public String getCheckoutsThisMonth() {
        return dashboardDatabaseManager.getCheckoutsThisMonth();
    }

    @Override
    public String getItemsThisMonth() {
        return dashboardDatabaseManager.getItemsThisMonth();
    }

    @Override
    public String getSalesThisMonth() {
        return dashboardDatabaseManager.getSalesThisMonth();
    }

    @Override
    public void setOpeningTime(String openingTime) {
        dashboardDatabaseManager.setOpeningTime(openingTime);
        isWorkingHoursCacheValid = false;
    }

    @Override
    public void setClosingTime(String closingTime) {
        dashboardDatabaseManager.setClosingTime(closingTime);
        isWorkingHoursCacheValid = false;
    }
}
