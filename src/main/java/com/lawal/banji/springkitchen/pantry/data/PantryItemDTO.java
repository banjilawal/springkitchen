package com.lawal.banji.springkitchen.pantry.data;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public final class PantryItemDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Length(min=3, max=50, message = "Name must be be at least 3 characters long")
    private String name;

    @NotNull(message = "Quantity in stock is required")
    @Min(value = 0, message = "Quantity in stock must be zero or greater")
    @Max(value = 200, message = "Personal accounts can have a maximum quantity in stock of 200 per item")
    private Long quantityInStock;

    @NotNull(message = "Reorder level is required")
    @Min(value = 0, message = "Reorder level must be zero or greater")
    @Max(value = 200, message = "Personal accounts can have a maximum reorder level of 200 per item")
    private Long reorderLevel;

    public PantryItemDTO() {}

    public PantryItemDTO(String name, Long quantityInStock, Long reorderLevel) {
        this(null, name, quantityInStock, reorderLevel);
    }

    public PantryItemDTO(Long id, String name, Long quantityInStock, Long reorderLevel) {
        this.id = id;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.reorderLevel = reorderLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() { return name;}

    public Long getQuantityInStock() {
        return quantityInStock;
    }

    public Long getReorderLevel() {
        return reorderLevel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantityInStock(Long quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setReorderLevel(Long reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    @Override
    public String toString() {
        return "PantryItemDTO[id:" + id + " name:" + name + " quantityInStock:" + quantityInStock + " reorderLevel" + reorderLevel + ']';
    }
}
