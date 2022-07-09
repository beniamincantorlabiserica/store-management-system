package viewmodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Model;

public class DashboardViewModel implements DashBoardViewModelInterface {

    private final Model model;

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
    private final SimpleIntegerProperty dayProgressBarProperty;
    private final SimpleIntegerProperty monthProgressBarProperty;

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
        this.dayProgressBarProperty = new SimpleIntegerProperty();
        this.monthProgressBarProperty = new SimpleIntegerProperty();
    }

    @Override
    public void logout() {
        model.logout();
        reset();
    }

    @Override
    public void reset() {
        this.dateProperty.set("");
        this.timeProperty.set("");
        this.dayOfWeekProperty.set("");
        this.storeStatusProperty.set("");
        this.checkoutsThisMonthProperty.set("");
        this.checkoutsTodayProperty.set("");
        this.itemsThisMonthProperty.set("");
        this.itemsTodayProperty.set("");
        this.salesTodayProperty.set("");
        this.salesThisMonthProperty.set("");
        this.dayProgressBarProperty.set(0);
        this.monthProgressBarProperty.set(0);
    }

    @Override
    public void setOpeningHours(String s) throws RuntimeException {
        model.setOpeningHours(s);
    }

    @Override
    public void setClosingHours(String s) throws RuntimeException {
        model.setClosingHours(s);
    }

    @Override
    public String getOpeningHours() {
        return model.getOpeningHours();
    }

    @Override
    public String getClosingHours() {
        return model.getClosingHours();
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

    public SimpleIntegerProperty getDayProgressBarProperty() {
        return dayProgressBarProperty;
    }

    public SimpleIntegerProperty getMonthProgressBarProperty() {
        return monthProgressBarProperty;
    }
}
