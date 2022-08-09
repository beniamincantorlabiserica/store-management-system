package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import util.logger.Logger;
import util.logger.LoggerType;
import view.View;
import view.ViewController;
import viewmodel.InventoryViewModelInterface;
import viewmodel.ItemTableViewModel;

public class InventoryViewController extends ViewController {
    @FXML
    public TableView<ItemTableViewModel> table;
    @FXML
    public TableColumn<ItemTableViewModel, String> item;
    @FXML
    public TableColumn<ItemTableViewModel, String> id;
    @FXML
    public TableColumn<ItemTableViewModel, String> quantity;
    @FXML
    public TableColumn<ItemTableViewModel, String> price;
    @FXML
    public TableColumn<ItemTableViewModel, String> recentlyChanged;
    @FXML
    public Button back;
    private InventoryViewModelInterface viewModel;

    /**
     * Called by view handler when view is created for the first time
     * <p>
     * Resets the values of the table rows
     * <p>
     * Sets row's values as Item(name,quantity,price)
     */
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getInventoryViewModel();
        reset();
        id.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        item.setCellValueFactory(cellData -> cellData.getValue().getItemProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().getQuantityProperty());
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        quantity.setOnEditCommit(itemIntegerCellEditEvent -> {
            viewModel.updateQuantity(
                    Integer.parseInt(String.valueOf(itemIntegerCellEditEvent.getRowValue().getIdProperty().getValue())),
                    Integer.parseInt(itemIntegerCellEditEvent.getNewValue()));
                    reset();
        });
        price.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
        price.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        price.setOnEditCommit(itemIntegerCellEditEvent -> {

            viewModel.changePrice(
                    Long.valueOf(String.valueOf(itemIntegerCellEditEvent.getRowValue().getIdProperty().getValue())),
                    Double.parseDouble(itemIntegerCellEditEvent.getNewValue()));
            reset();
        });
        recentlyChanged.setCellValueFactory(cellData -> cellData.getValue().getRecentlyChangedProperty());
    }

    /**
     * Returns to the Cashier UI if user is logged in as cashier or to the Dashboard UI otherwise
     */
    @FXML
    public void onBackButtonPressed() {
        getViewHandler().openView(viewModel.getUser().isCashier() ? View.CASH_REGISTER : View.DASHBOARD);
    }

    /**
     * Initializes a new ObservableList
     * <p>
     * Populates observable list
     * <p>
     * Sets items in the table
     */
    @Override
    public void reset() {
        Logger.getInstance().log(LoggerType.DEBUG, "InventoryViewController -> reset()");
        table.setItems(viewModel.getItems());
        table.setEditable(true);
        table.refresh();
    }
}
