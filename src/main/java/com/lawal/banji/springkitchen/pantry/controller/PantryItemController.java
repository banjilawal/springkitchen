package com.lawal.banji.springkitchen.pantry.controller;

import com.lawal.banji.springkitchen.pantry.data.PantryItemDTO;
import com.lawal.banji.springkitchen.pantry.data.PantryItemGenerator;
import com.lawal.banji.springkitchen.pantry.model.PantryItem;
import com.lawal.banji.springkitchen.pantry.service.PantryItemService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/kitchen/pantry"})
public final class PantryItemController {

    private final PantryItemService pantryItemService;

    @Autowired
    public PantryItemController(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
    }

    @ModelAttribute
    public void setModelAttributes(Model model) { model.addAttribute("dashboard", "pantryDashboard"); }

    @GetMapping({""})
    public String getPantryItems(Model model) {
        model.addAttribute("pantryItems", pantryItemService.findAll());
        model.addAttribute("view", "inventoryView");

        return "kitchen";
    }

    @GetMapping("/search")
    public String searchPantryItems(Model model, @RequestParam(value = "string", required = false) String string) {
        model.addAttribute("pantryItems", pantryItemService.search(string));
        model.addAttribute("view", "inventoryView");
        model.addAttribute("pageHeading", "Search Results");
        return "kitchen";
    }

    @GetMapping("/{id}")
    public String getPantryItem(Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) { return "redirect:/kitchen/pantry";}

        model.addAttribute("pantryItem", pantryItem);
        model.addAttribute("view", "pantryItemView");
        return "kitchen";
    }

    @GetMapping("/new")
    public String newPantryItemForm(Model model) {
        model.addAttribute("pantryItemDTO", PantryItemGenerator.pantryItemDTO());
        model.addAttribute("view", "pantryItemFormView");
        model.addAttribute("pageHeading", "Pantry Item Form");
        return "kitchen";
    }

    @PostMapping("")
    public String createPantryItem(
        @Valid @ModelAttribute("pantryItemDTO") PantryItemDTO pantryItemDTO,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            if (pantryItemDTO == null) { return showErrors(model, "pantryItemDTO is null. Creation failed."); }
            model.addAttribute("view", "pantryItemFormView");
            model.addAttribute("pageHeading", "Pantry Item Form");
            return "kitchen";
        }
        pantryItemService.save(pantryItemDTO);
        model.addAttribute("pantryItems", pantryItemService.findAll());
        model.addAttribute("view", "inventoryView");
        return "redirect:/kitchen/pantry";
    }

    @GetMapping("/{id}/edit")
    public String editPantryItemForm(Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) { return showErrors(model, "The pantry item does not exist. Update failed."); }

        model.addAttribute("pantryItemDTO", pantryItem.toDTO());
        model.addAttribute("view", "pantryItemFormView");
        model.addAttribute("pageHeading", "Update Pantry Item");
        return "kitchen";
    }

    @PostMapping("/{id}")
    public String updatePantryItem(
        @PathVariable Long id,
        @ModelAttribute("pantryItemDTO") PantryItemDTO pantryItemDTO,
        BindingResult bindingResult,
        Model model
    ) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (bindingResult.hasErrors()) {
            if (pantryItem == null) { return showErrors(model, "The pantry item does not exist. Update failed."); }

            model.addAttribute("view", "pantryItemFormView");
            model.addAttribute("pageHeading", "Update Pantry Item");
            return "kitchen"; // Re-render the form with validation errors
        }
        model.addAttribute("pantryItem", pantryItemService.update(id, pantryItemDTO));
        model.addAttribute("view", "pantryView");
        return "redirect:/kitchen/pantry/"  + id;
    }

    @GetMapping("/{id}/delete")
    public String deletePantryItemForm(Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) { return itemNotFound(model); }
        model.addAttribute("pantryItem", pantryItem);
        model.addAttribute("view", "deletePantryItemView");
        return "kitchen";
    }

    @PostMapping("/{id}/delete")
    public String deletePantryItem(Model model, @PathVariable Long id) {
        pantryItemService.deleteById(id);

        model.addAttribute("pantryItems", pantryItemService.findAll());
        model.addAttribute("view", "inventoryView");
        return "kitchen";
    }

    @GetMapping("/notfound")
    public String itemNotFound(Model model) {
        model.addAttribute("view", "notFoundView");
        return "kitchen";
    }

    @GetMapping("/error")
    public String showErrors(Model model, String errorMessage) {
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("view", "errorView");
        return "kitchen";
    }
}
