package viewmodel;

import javafx.beans.property.*;
import model.Item;

public class ItemTableViewModel {
    private SimpleStringProperty itemProperty;
    private SimpleStringProperty idProperty;
    private SimpleStringProperty quantityProperty;
    private SimpleStringProperty priceProperty;

    public ItemTableViewModel(Item item) {
        itemProperty = new SimpleStringProperty(item.getName());
        idProperty = new SimpleStringProperty(String.valueOf(item.getId()));
        quantityProperty = new SimpleStringProperty(String.valueOf(item.getQuantity()));
        priceProperty = new SimpleStringProperty(String.valueOf(item.getPrice()));

    }
    public SimpleStringProperty getItemProperty() {
        return itemProperty;
    }
    public SimpleStringProperty getIdProperty() {return idProperty;}
    public SimpleStringProperty getQuantityProperty() {
        return quantityProperty;
    }
    public SimpleStringProperty getPriceProperty() {
        return priceProperty;
    }
    public void setQuantityProperty(String s){
        quantityProperty.setValue(s);
    }
}
