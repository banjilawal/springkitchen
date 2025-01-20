package com.lawal.banji.springkitchen.pantry;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Entity
@Table(name = "pantryItems")
public  class PantryItem {

    /* String constants */
    public static final String PANTRY_ITEM_UPDATE_SOURCE_CANNOT_BE_NULL = "PantryItem update Source cannot be null";
    public static final String INVALID_UPDATE_SOURCE_ID = "PantryItem update source id is not valid. Update failed";

    private static final String METHOD_DOES_NOT_ACCEPT_NULL_ID_PARAMETERS = "PantryItem method does not allow null id parameters";
    public static final String PANTRY_ITEM_NAME_CANNOT_BE_NULL_OR_BLANK = "PantryItem name cannot be null or blank";

    public static final String AMOUNT_CANNOT_BE_NULL_OR_LESS_THAN_ZERO = "Amount cannot be null or less than 0";
    public static final String REORDER_LEVEL_MUST_NOT_BE_NULL_OR_LESS_THAN_ZERO = "Reorder level must not be null or less than 0";
    public static final String QUANTITY_IN_STOCK_MUST_NOT_BE_NULL_OR_LESS_THAN_ZERO = "Quantity cannot be null or less than 0";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    @Size(min=3, max=50, message = "Name must be be at least 3 characters long")
    @Length(min=3, max=50, message = "Name must be be at least 3 characters long")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Reorder level is required")
    @Min(value = 0, message = "Reorder level must be zero or greater")
    @Max(value = 200, message = "Personal accounts can have a maximum reorder level of 200 per item")
    private Long reorderLevel;

    @Column(nullable = false)
    @NotNull(message = "Quantity in stock is required")
    @Min(value = 0, message = "Quantity in stock must be zero or greater")
    @Max(value = 200, message = "Personal accounts can have a maximum quantity in stock of 200 per item")
    private Long quantityInStock;


    /* Constructors */
    public PantryItem() {}

    public PantryItem(Long id, String name, long reorderLevel, long quantityInStock) {
        setId(id);
        setName(name);
        setReorderLevel(reorderLevel);
        setQuantityInStock(quantityInStock);
    }


    /* Builders */
    public static Builder builder () { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private Long reorderLevel;
        private Long quantityInStock;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder reorderLevel(Long reorderLevel) {
            this.reorderLevel = reorderLevel;
            return this;
        }

        public Builder quantityInStock(Long quantityInStock) {
            this.quantityInStock = quantityInStock;
            return this;
        }

        public PantryItem build() { return new PantryItem(id, name, reorderLevel, quantityInStock); }
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getQuantityInStock() {
        return quantityInStock;
    }

    public Long getReorderLevel() {
        return reorderLevel;
    }

    /* Setters */
    public void setId(Long id) { this.id = id; }

    public void setName (String name) {
         this.name = name.trim();
    }

    public void setReorderLevel(Long reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    /*
        SetQuantityInStock method should not be used. It's only here because Spring and the JPA work better with all
        setters enabled. I might make setQuantityInStock protected instead of public.
        SetQuantityInStock is not a safe method. Use increaseQuantityInStock or decreaseQuantityInStock
        instead.
    * */
    public void setQuantityInStock(Long quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void increaseQuantityInStock(Long amount) {
        this.quantityInStock += amount;
    }

    public void decreaseQuantityInStock(Long amount) {
        this.quantityInStock -= amount;
    }

    /* Update methods */
    public void getUpdate(PantryItem source) {
        if (source == this) return;

        setName(source.getName());
        setReorderLevel(source.getReorderLevel());
        setQuantityInStock(source.getQuantityInStock());
    }

    /* Equals and hash methods */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (object instanceof PantryItem pantryItem) {
            boolean idEqual = id != null && pantryItem.getId() != null && id.equals(pantryItem.getId());
            boolean nameEqual = name != null && pantryItem.getName() != null && name.equalsIgnoreCase(pantryItem.getName());
            return idEqual && nameEqual;
        }
        return false;
    }

    @Override
    public int hashCode() { return id != null ? Objects.hash(id) : Objects.hash(name); }

    /* String methods */
    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "[id:" + id
            + " name:" + name
            + " quantityInStock:" + quantityInStock
            + " reorderLevel:" + reorderLevel + ']';
    }
}
