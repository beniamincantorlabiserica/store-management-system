package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Item implements Serializable {
    public Long id;
    public String name;
    public double price;
    public int quantity;
    public LocalDateTime editDateTime;

    public Item(Long id, String name, double price, int quantity, LocalDateTime lastEdit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.editDateTime = lastEdit;
    }

    public Item() {
    }

    public boolean isEditedRecently() {
        return editDateTime.plusHours(24).isAfter(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        setEditDateTime(LocalDateTime.now());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getEditDateTime() {
        return editDateTime;
    }

    private void setEditDateTime(LocalDateTime editDateTime) {
        this.editDateTime = editDateTime;
    }
}
