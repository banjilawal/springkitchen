package com.lawal.banji.springkitchen.pantry.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Entity
public final class PantryItem implements FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)

    private Long quantityInStock;

    @Column(nullable = false)
    private Long reorderLevel;

    public PantryItem() {}

    public PantryItem(Builder builder) {
//        this.id = builder.id;
        this.name = builder.name;
        this.quantityInStock = builder.quantityInStock;
        this.reorderLevel = builder.reorderLevel;
    }

    public PantryItem(String name, Long quantityInStock, Long reorderLevel) {
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.reorderLevel = reorderLevel;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getQuantityInStock() {
        return quantityInStock;
    }

    @Override
    public Long getReorderLevel() {
        return reorderLevel;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setQuantityInStock(Long quantityInStock) {
        assert quantityInStock >= 0 : "Quantity cannot be less than 0";
        this.quantityInStock = quantityInStock;
    }

    @Override
    public void increaseQuantityInStock(Long amount) {
        assert amount >= 0 : "Amount cannot be less than 0";
        this.quantityInStock += amount;
    }

    @Override
    public void decreaseQuantityInStock(Long amount) {
        assert amount >= 0 && amount <= quantityInStock: "Amount cannot be less than 0";
        this.quantityInStock -= amount;
    }

    @Override
    public void setReorderLevel(Long reorderThreshold) {
        this.reorderLevel = reorderThreshold;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (object instanceof PantryItem pantryItem)
            return id.equals(pantryItem.getId()) && name.equalsIgnoreCase(pantryItem.getName());
        return false;
    }

    @Override
    public String toString() {
        return "PantryItem[id:" + id + " name:" + name + " quantityInStock:" + quantityInStock + " reorderThreshold:" + reorderLevel + ']';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private Long quantityInStock;
        private Long reorderLevel;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder quantityInStock(Long quantityInStock) {
            this.quantityInStock = quantityInStock;
            return this;
        }

        public Builder reorderLevel(Long reorderLevel) {
            this.reorderLevel = reorderLevel;
            return this;
        }

        public PantryItem build() {
            return new PantryItem(this);
        }
    }
}
