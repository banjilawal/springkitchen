//package com.lawal.banji.springkitchen.recipe.model;
//
//import com.lawal.banji.springkitchen.pantry.model.PantryItem;
//import jakarta.persistence.*;
//
//
//@Entity
//public final class Ingredient {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Long id;
//
//    @Column(nullable = false)
//    private Double amount;
//
//    @OneToOne
//    @JoinColumn(name = "pantry_item_id")
//    private PantryItem pantryItem;
//
//    public Ingredient() {
//    }
//
//    public Ingredient(Double amount, PantryItem pantryItem) {
//        this.amount = amount;
//        this.pantryItem = pantryItem;
//    }
//
//    public Ingredient(Long id, Double amount, PantryItem pantryItem) {
//        this.id = id;
//        this.amount = amount;
//        this.pantryItem = pantryItem;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public Double getAmount() {
//        return amount;
//    }
//
//    public PantryItem getPantryItem() {
//        return pantryItem;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//
//    public void setPantryItem(PantryItem pantryItem) {
//        this.pantryItem = pantryItem;
//    }
//
//    @Override
//    public String toString () {
//        return pantryItem.getName() + " " + amount + " amount";
//    }
//}
