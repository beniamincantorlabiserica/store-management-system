package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import model.Item;

public class ItemTableViewModel {
    private final SimpleStringProperty itemProperty;
    private final SimpleStringProperty idProperty;
    private final SimpleStringProperty quantityProperty;
    private final SimpleStringProperty priceProperty;
    private final SimpleStringProperty recentlyChangedProperty;

    public ItemTableViewModel(Item item) {
        itemProperty = new SimpleStringProperty(item.getName());
        idProperty = new SimpleStringProperty(String.valueOf(item.getId()));
        quantityProperty = new SimpleStringProperty(String.valueOf(item.getQuantity()));
        priceProperty = new SimpleStringProperty(String.valueOf(item.getPrice()));
        recentlyChangedProperty = new SimpleStringProperty(getEditedRecentlyMarker(item));

    }

    private String getEditedRecentlyMarker(Item item) {
        if (item.isEditedRecently()) {
            return "!";
        }
        return "No";
    }

    public SimpleStringProperty getItemProperty() {
        return itemProperty;
    }

    public SimpleStringProperty getIdProperty() {
        return idProperty;
    }

    public SimpleStringProperty getQuantityProperty() {
        return quantityProperty;
    }

    public SimpleStringProperty getPriceProperty() {
        return priceProperty;
    }

    public void setQuantityProperty(String s) {
        quantityProperty.setValue(s);
    }

    public SimpleStringProperty getRecentlyChangedProperty() {
        return recentlyChangedProperty;
    }
}
