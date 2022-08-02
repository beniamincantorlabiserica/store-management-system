package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Item;
import model.PaymentType;
import util.logger.Logger;
import util.logger.LoggerType;
import view.View;
import view.ViewController;
import viewmodel.CashRegisterViewModelInterface;
import viewmodel.ItemTableViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CashRegisterViewController extends ViewController {

    @FXML
    public TableView<ItemTableViewModel> table;
    @FXML
    public TableColumn<ItemTableViewModel, String> id;
    @FXML
    public TableColumn<ItemTableViewModel, String> item;
    @FXML
    public TableColumn<ItemTableViewModel, String> quantity;
    @FXML
    public TableColumn<ItemTableViewModel, String> price;
    @FXML
    public TableColumn<ItemTableViewModel, String> priceMark;
    @FXML
    public Button scanItemButton;
    @FXML
    public Label totalPrice;
    @FXML
    public TextField scanInput;
    private CashRegisterViewModelInterface viewModel; // contains a reference to the viewModel
    private ObservableList<ItemTableViewModel> currentItems;

    public CashRegisterViewController() { } // Empty constructor
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getCashRegisterViewModel(); // fetches the viewModel variable from the viewModel factory class
        totalPrice.setText("0");
        currentItems = FXCollections.observableArrayList();
        reset();
        id.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        item.setCellValueFactory(cellData -> cellData.getValue().getItemProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().getQuantityProperty());
        price.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
    }

    /**
     * THe function retrieves the id entered by the user,
     * sets the TextField to a null string.
     * It is trying to get the items from the view model,
     * and if there are no exeptions the function calls reset() and
     * calculateTotal() functions, if there is an exeption,
     * the function displays an alert
     */
    public void onScanPressed() {
        String id = scanInput.getText();
        scanInput.setText("");
        try {
            currentItems = viewModel.scanItem(id);
            ItemTableViewModel currentItem = currentItems.get(currentItems.size() - 1);
            currentItems.remove(currentItem);
            if (!addItemToCheckout(currentItem)) {
                currentItems.add(currentItem);
            }
            reset();
            calculateTotal();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("WRONG_BARCODE")) {
                Logger.getInstance().log(LoggerType.DEBUG, "Wrong Barcode");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Barcode");
                alert.setHeaderText("Please use a valid barcode.");
                alert.showAndWait();
            } else if (e.getMessage().equals("NO_MORE_ITEMS_IN_STOCK")) {
                Logger.getInstance().log(LoggerType.DEBUG, "No More Items In Stock");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No More Items In Stock");
                alert.setHeaderText("There are no more items in stock.");
                alert.showAndWait();
            }
        }
    }

    /**
     * The method displys the options of payment methods
     * to the cashier to choose and then displays a
     * confirmation pop up where the cashier can approve or
     * disapprove the transaction
     */
    public void onCheckoutPressed() {
        double total = viewModel.checkout();
        currentItems = FXCollections.observableArrayList();
        reset();
        totalPrice.setText("0");
        Logger.getInstance().log(LoggerType.DEBUG, "Total price from checkout: " + total);

        String paymentMethod = null;
        List<String> choices = new ArrayList<>();
        choices.add("Card"); // ->
        choices.add("Mobilepay"); // <- choices for the Choice Dialog
        choices.add("Cash");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Payment Method", choices); // ->
        dialog.setTitle("Payment method");
        dialog.setContentText("Choose a payment method"); // <- preferences for the Choice Dialog
        Optional<String> outcome = dialog.showAndWait(); // show the dialog and wait for an answer
        if (outcome.isPresent()) {
            paymentMethod = outcome.get();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Payment");
        alert.setHeaderText("Payment method: " + paymentMethod);
        alert.setContentText("Confirm transaction?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                switch (paymentMethod){
                    case "Card": viewModel.completePayment(PaymentType.CARD);
                    case "Mobilepay": viewModel.completePayment(PaymentType.MOBILEPAY);
                    case "Cash": viewModel.completePayment(PaymentType.CASH);
                    default: break;
                }
                Logger.getInstance().log(LoggerType.DEBUG, "Payment complete");
            } catch (Exception e) {
                Logger.getInstance().log(LoggerType.ERROR, e.getMessage());
            }
        } else {
            try {
                viewModel.cancelCheckout();
            } catch (Exception e){
                Logger.getInstance().log(LoggerType.ERROR, e.getMessage());
            }
        }
    }

    public boolean addItemToCheckout(ItemTableViewModel item) {
        for (ItemTableViewModel i : currentItems) {
            if (Objects.equals(item.getIdProperty(), i.getIdProperty())) {
                i.setQuantityProperty(i.getQuantityProperty().getValue() + 1);
                return true;
            }
        }
       return false;
    }

    /**
     * The function calculates the total price of the
     * items in the ArrayList and displays it in the totalPrice label
     */
    public void calculateTotal() {
        double sum = 0;
        for (ItemTableViewModel item : currentItems) {
            Logger.getInstance().log(LoggerType.DEBUG, "Item: " + item.getItemProperty());
            sum += Integer.parseInt(item.getPriceProperty().getValue())
                    * Integer.parseInt(item.getQuantityProperty().getValue());
        }
        totalPrice.setText(String.valueOf(sum));
        Logger.getInstance().log(LoggerType.DEBUG, "----------------------");
    }

    public void onInventoryButtonPressed() {
        getViewHandler().openView(View.INVENTORY);

    }

    public void onLogoutButtonPressed() {
        viewModel.logout();
        getViewHandler().openView(View.START);
    }

    @Override
    public void reset() {
        ObservableList<ItemTableViewModel> items = FXCollections.observableArrayList();
        items.clear();
        items.addAll(currentItems);
        table.setItems(items);
        table.setEditable(true);
        table.refresh();
    }
}
