package com.lawal.banji.springkitchen.pantry.model;

import com.lawal.banji.springkitchen.pantry.data.PantryItemDTO;

public interface FoodEntity {
    Long getId ();
    String getName ();
    Long getQuantityInStock ();
    Long getReorderLevel();

    void setId (Long id);
    void setName (String name);
    void setQuantityInStock (Long quantityInStock);
    void increaseQuantityInStock (Long amount);
    void decreaseQuantityInStock (Long amount);
    void setReorderLevel(Long reorderThreshold);

    PantryItemDTO toDTO();
    void update(PantryItemDTO pantryItemDTO);
}
