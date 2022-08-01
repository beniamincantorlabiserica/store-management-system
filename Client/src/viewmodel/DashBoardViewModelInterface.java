package viewmodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public interface DashBoardViewModelInterface {
    /**
     * logs out the user
     */
    void logout();

    /**
     * performs viewModel reset()
     */
    void reset();

    /**
     * starts a thread that updates the time, date and day of week labels
     * with the correct values each second
     */
    void startUpdateThread();
     SimpleStringProperty getTimeProperty();

      SimpleStringProperty getDateProperty();

     SimpleStringProperty getDayOfWeekProperty();


     SimpleStringProperty getStoreStatusProperty();


     SimpleStringProperty getCheckoutsTodayProperty();


     SimpleStringProperty getItemsTodayProperty();


     SimpleStringProperty getSalesTodayProperty();

     SimpleStringProperty getCheckoutsThisMonthProperty();

     SimpleStringProperty getItemsThisMonthProperty();

     SimpleStringProperty getSalesThisMonthProperty();
     SimpleDoubleProperty getDayProgressBarProperty();

     SimpleDoubleProperty getMonthProgressBarProperty();
}
