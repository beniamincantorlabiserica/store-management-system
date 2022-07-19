package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import logger.Logger;
import model.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

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

    private void softReset() {
        this.dateProperty.set("DATE\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.timeProperty.set("TIME\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        this.dayOfWeekProperty.set("DAY OF WEEK\n" + LocalDateTime.now().getDayOfWeek().name());
    }

    @Override
    public void logout() {
        model.logout();
        reset();
    }

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
        this.dayProgressBarProperty.set(getDayProgress());
        this.monthProgressBarProperty.set(getMonthProgress());
    }

    private double getMonthProgress() {
        // x = LocalDateTime.now().getDayOfMonth()
        // t = LocalDateTime.now().getCurrentMonthTotalDays()
        // 1 ------- t
        //  y  ------- x
        // y = LocalDateTime.now().getDayOfMonth() / LocalDateTime.now().getCurrentMonthTotalDays()
        return (double) LocalDateTime.now().getDayOfMonth() / (double) LocalDateTime.now().getMonth().length(Year.isLeap(LocalDateTime.now().getYear()));
    }

    private double getDayProgress() {
        // x = now() - openingHour() // hours passed
        // t = closingHour() - openingHour()
        // 1 ------- t
        // y  ------- x
        // y = x / t
        // y = ( now() - getOpeningHour() ) / ( closingHour() - openingHour() )
        if (LocalTime.now().getHour() <= model.getOpeningHourInteger() || LocalTime.now().getHour() > model.getClosingHourInteger()) {
            return 0;
        }
        double hoursPassed = LocalTime.now().getHour() - model.getOpeningHourInteger();
        double totalHours = model.getClosingHourInteger() - model.getOpeningHourInteger();
        return hoursPassed / totalHours;
    }

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
