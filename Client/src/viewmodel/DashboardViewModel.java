package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Model;
import util.DateTimeManager;

public class DashboardViewModel implements DashBoardViewModelInterface {
    private final Model model;

    private final Thread updateThread;

    private final SimpleStringProperty dateProperty;
    private final SimpleStringProperty timeProperty;
    private final SimpleStringProperty dayOfWeekProperty;
    private final SimpleStringProperty storeStatusProperty;
    private final SimpleStringProperty checkoutsTodayProperty;
    private final SimpleStringProperty itemsTodayProperty;
    private final SimpleStringProperty salesTodayProperty;
    private final SimpleStringProperty checkoutsThisMonthProperty;
    private final SimpleStringProperty itemsThisMonthProperty;
    private final SimpleStringProperty salesThisMonthProperty;
    private final SimpleDoubleProperty dayProgressBarProperty;
    private final SimpleDoubleProperty monthProgressBarProperty;

    private final DateTimeManager dateTimeManager;

    /**
     * constructor
     *
     * @param model          expects a reference to the model
     * @param viewModelState expects a reference to the viewModelState
     */
    // TODO viewModelState - use or remove
    public DashboardViewModel(Model model, ViewModelState viewModelState) {
        this.model = model;
        this.dateProperty = new SimpleStringProperty();
        this.timeProperty = new SimpleStringProperty();
        this.dayOfWeekProperty = new SimpleStringProperty();
        this.storeStatusProperty = new SimpleStringProperty();
        this.checkoutsThisMonthProperty = new SimpleStringProperty();
        this.checkoutsTodayProperty = new SimpleStringProperty();
        this.itemsThisMonthProperty = new SimpleStringProperty();
        this.itemsTodayProperty = new SimpleStringProperty();
        this.salesTodayProperty = new SimpleStringProperty();
        this.salesThisMonthProperty = new SimpleStringProperty();
        this.dayProgressBarProperty = new SimpleDoubleProperty();
        this.monthProgressBarProperty = new SimpleDoubleProperty();
        dateTimeManager = DateTimeManager.getInstance();
        updateThread = new Thread(() -> {
            while (true) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(this::softReset);
            }
        });
        updateThread.setDaemon(true);
    }

    /**
     * performs a soft reset in the dashboard, refreshing only the date, time and day of week properties with the real-time values
     */
    private void softReset() {
        this.dateProperty.set("DATE\n" + dateTimeManager.getDate());
        this.timeProperty.set("TIME\n" + dateTimeManager.getTime());
        this.dayOfWeekProperty.set("DAY OF WEEK\n" + dateTimeManager.getDayOfWeekName());
    }

    /**
     * delegates the logout request to the model
     * performs a reset
     */
    @Override
    public void logout() {
        model.logout();
        reset();
    }

    /**
     * refresh all the viewModel properties to the current values
     */
    @Override
    public void reset() {
        softReset();
        this.storeStatusProperty.set("STORE STATUS\n" + model.getStoreStatus());
        this.checkoutsThisMonthProperty.set("CHECKOUTS THIS MONTH\n" + model.getCheckoutsThisMonth());
        this.checkoutsTodayProperty.set("CHECKOUTS TODAY\n" + model.getCheckoutsToday());
        this.itemsThisMonthProperty.set("ITEMS THIS MONTH\n" + model.getItemsThisMonth());
        this.itemsTodayProperty.set("ITEMS TODAY\n" + model.getItemsToday());
        this.salesTodayProperty.set("SALES TODAY\n" + model.getSalesToday());
        this.salesThisMonthProperty.set("SALES THIS MONTH\n" + model.getSalesThisMonth());
        this.dayProgressBarProperty.set(model.getDayProgress());
        this.monthProgressBarProperty.set(model.getMonthProgress());
    }

    /**
     * starts the update thread that triggers the softUpdate() method each second
     */
    @Override
    public void startUpdateThread() {
        this.updateThread.start();
    }

    public SimpleStringProperty getDateProperty() {
        return dateProperty;
    }


    public SimpleStringProperty getTimeProperty() {
        return timeProperty;
    }


    public SimpleStringProperty getDayOfWeekProperty() {
        return dayOfWeekProperty;
    }


    public SimpleStringProperty getStoreStatusProperty() {
        return storeStatusProperty;
    }


    public SimpleStringProperty getCheckoutsTodayProperty() {
        return checkoutsTodayProperty;
    }


    public SimpleStringProperty getItemsTodayProperty() {
        return itemsTodayProperty;
    }


    public SimpleStringProperty getSalesTodayProperty() {
        return salesTodayProperty;
    }

    public SimpleStringProperty getCheckoutsThisMonthProperty() {
        return checkoutsThisMonthProperty;
    }

    public SimpleStringProperty getItemsThisMonthProperty() {
        return itemsThisMonthProperty;
    }

    public SimpleStringProperty getSalesThisMonthProperty() {
        return salesThisMonthProperty;
    }

    public SimpleDoubleProperty getDayProgressBarProperty() {
        return dayProgressBarProperty;
    }

    public SimpleDoubleProperty getMonthProgressBarProperty() {
        return monthProgressBarProperty;
    }
}
