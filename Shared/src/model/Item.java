package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Item implements Serializable {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private LocalDateTime editDateTime;

    public Item(Long id, String name, Double price, Integer quantity, LocalDateTime lastEdit) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        setEditDateTime(LocalDateTime.now());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private void setEditDateTime(LocalDateTime editDateTime) {
        this.editDateTime = editDateTime;
    }
}
