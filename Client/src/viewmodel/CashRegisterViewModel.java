package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import model.Item;
import model.Model;
import model.PaymentType;
import util.logger.Logger;
import util.logger.LoggerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CashRegisterViewModel implements CashRegisterViewModelInterface{

    private final Model model;
    public SimpleStringProperty totalPriceLabelProperty;
    public SimpleStringProperty scanInputProperty;
    ObservableList<ItemTableViewModel> listToReturn;

    public CashRegisterViewModel(Model model) {
        this.model = model;
        this.listToReturn = FXCollections.observableArrayList();
        this.totalPriceLabelProperty = new SimpleStringProperty();
        this.scanInputProperty = new SimpleStringProperty();
    }
    /**
     * The function calculates the total price of the
     * items in the ArrayList and displays it in the totalPrice label
     */
    public void calculateTotal() {
        double sum = 0;
        for (ItemTableViewModel item : listToReturn) {
            Logger.getInstance().log(LoggerType.DEBUG, "Item: " + item.getItemProperty());
            System.out.println(Double.parseDouble(item.getPriceProperty().getValue()));
            sum += Double.parseDouble(item.getPriceProperty().getValue())
                    * Double.parseDouble(item.getQuantityProperty().getValue());
        }
        totalPriceLabelProperty.set(String.valueOf(sum));
        Logger.getInstance().log(LoggerType.DEBUG, "----------------------");
    }
    public void onScanPressed() {
        String id = scanInputProperty.getValue();
        scanInputProperty.setValue("");
        try {
            listToReturn = scanItem(id);
            ItemTableViewModel currentItem = listToReturn.get(listToReturn.size() - 1);
            listToReturn.remove(currentItem);
            if (!addItemToCheckout(currentItem)) {
                listToReturn.add(currentItem);
            }
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
    public void reset(){
     listToReturn.clear();
    }
    public void onCheckoutPressed() {
        double total = checkout();
        listToReturn = FXCollections.observableArrayList();
//        reset();
        totalPriceLabelProperty.setValue("0");
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
                    case "Card": completePayment(PaymentType.CARD);
                    case "Mobilepay": completePayment(PaymentType.MOBILEPAY);
                    case "Cash": completePayment(PaymentType.CASH);
                    default: break;
                }
                Logger.getInstance().log(LoggerType.DEBUG, "Payment complete");
            } catch (Exception e) {
                Logger.getInstance().log(LoggerType.ERROR, e.getMessage());
            }
        } else {
            try {
                cancelCheckout();
            } catch (Exception e){
                Logger.getInstance().log(LoggerType.ERROR, e.getMessage());
            }
        }
    }


    public boolean addItemToCheckout(ItemTableViewModel item) {
        for (ItemTableViewModel i : listToReturn) {
            if (Objects.equals(item.getIdProperty(), i.getIdProperty())) {
                i.setQuantityProperty(i.getQuantityProperty().getValue() + 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public ObservableList<ItemTableViewModel> scanItem(String barCode) throws RuntimeException {
        listToReturn.clear();
        for(Item item : model.scanItem(barCode)){
            listToReturn.add(new ItemTableViewModel(item));
        }

        return listToReturn;
    }
    @Override
    public ObservableList<ItemTableViewModel> getCurrentItems(){
        return listToReturn;
    }

    @Override
    public double checkout() throws RuntimeException {
        return model.checkout();
    }

    @Override
    public void logout() throws RuntimeException {
        model.logout();
    }

    @Override
    public void completePayment(PaymentType paymentType) throws RuntimeException {
        model.completePayment(paymentType);
    }

    @Override
    public void cancelCheckout() throws RuntimeException {
        model.cancelCheckout();
    }
    @Override
    public void setTotalPriceLabelProperty(String s) {
        this.totalPriceLabelProperty.setValue(s);
    }

    @Override
    public SimpleStringProperty getTotalPriceLabelProperty() {
        return totalPriceLabelProperty;
    }
    @Override
    public SimpleStringProperty getScanInputProperty() {
        return scanInputProperty;
    }
}
