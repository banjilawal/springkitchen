package com.lawal.banji.springkitchen.pantry.controller;

import com.lawal.banji.springkitchen.pantry.data.PantryItemDTO;
import com.lawal.banji.springkitchen.pantry.data.PantryItemGenerator;
import com.lawal.banji.springkitchen.pantry.model.PantryItem;
import com.lawal.banji.springkitchen.pantry.service.PantryItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/kitchen/pantry"})
public class PantryItemController {

    private PantryItemService pantryItemService;

    @Autowired
    public PantryItemController(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
    }

    public PantryItemService getPantryItemService() {
        return pantryItemService;
    }

    @Autowired
    public void setPantryItemService(PantryItemService pantryItemService) { this.pantryItemService = pantryItemService; }

    @ModelAttribute
    public void setModelAttributes(Model model) { model.addAttribute("dashboard", "pantryDashboard"); }

    @GetMapping({""})
    public String getPantryItems(Model model) {
        model.addAttribute("pantryItems", pantryItemService.findAll());
        model.addAttribute("view", "inventoryView");

        return "kitchen";
    }

    @GetMapping({"/search"})
    public String searchPantryItems(Model model, @RequestParam(value = "name", required = false) String name) {
        model.addAttribute("pantryItems", pantryItemService.search(name));
        model.addAttribute("view", "inventoryView");
        model.addAttribute("pageHeading", "Search Results");
        return "kitchen";
    }

    @GetMapping("/{id}")
    public String getPantryItemById (Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) { return "redirect:/kitchen/pantry";}

        model.addAttribute("pantryItem", pantryItem);
        model.addAttribute("view", "pantryItemView");
        return "kitchen";
    }

    @GetMapping("/save")
    public String showPantryItemForm(Model model, @RequestParam(value = "id", required = false) Long id) {
        PantryItemDTO pantryItemDTO;
        if (id != null) {
            PantryItem pantryItem = pantryItemService.findById(id);
            pantryItemDTO = new PantryItemDTO(id, pantryItem.getName(), pantryItem.getQuantityInStock(), pantryItem.getReorderLevel());
        } else {
            pantryItemDTO = PantryItemGenerator.pantryItemDTO(10L, 25L);
            while (pantryItemService.findByName(pantryItemDTO.getName()) != null) {
                pantryItemDTO = PantryItemGenerator.pantryItemDTO(10L, 25L);
            }
        }
        model.addAttribute("pantryItemDTO", pantryItemDTO);
        model.addAttribute("view", "pantryItemFormView");
        model.addAttribute("pageHeading", "Pantry Item Form");
        return "kitchen";
    }

    @PostMapping("/save")
    public String savePantryItem(Model model, @Valid @ModelAttribute("pantryItemDTO") PantryItemDTO pantryItemDTO) {

        if (pantryItemDTO.getId() != null) {
            pantryItemService.update(pantryItemDTO.getId(), pantryItemDTO);
            model.addAttribute("pantryItem", pantryItemDTO);
            model.addAttribute("view", "pantryItemView");
            return "redirect:/kitchen/pantry/" + pantryItemDTO.getId();
        } else {
            pantryItemService.save(new PantryItem(pantryItemDTO.getName(), pantryItemDTO.getQuantityInStock(), pantryItemDTO.getReorderLevel()));
            model.addAttribute("pantryItems", pantryItemService.findAll());
            model.addAttribute("view", "inventoryView");
            return "redirect:/kitchen/pantry";
        }
    }

    @GetMapping("/save/{id}")
    public String showUpdatePantryItemForm (Model model, @PathVariable Long id) { return showPantryItemForm(model, id); }

    @GetMapping("/delete/{id}")
    public String showDeletePantryItemForm (Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) { return "redirect:/pantry"; }
        model.addAttribute("pantryItem", pantryItem);

        String warningMessage = "Deleting this item will also delete all recipes that use it. "
            + pantryItem.getName() + " will be removed from all current and past grocery lists. "
            + "Click \"Yes I Want to Delete\" or \"Cancel\" to return to the item's page.";
        model.addAttribute("view", "deletePantryItemView");
        model.addAttribute("pageHeading", "Delete " + pantryItem.getName());
        model.addAttribute("warningMessage", warningMessage);
        return "kitchen";
    }

    @PostMapping("/delete/{id}")
    public String deletePantryItem (Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) {
            System.out.println("PantryItem is null");
            return "redirect:/kitchen/pantry";
        }
        pantryItemService.deleteById(id);
        model.addAttribute("pantryItems", pantryItemService.findAll());
        model.addAttribute("view", "inventoryView");
        return "redirect:/kitchen/pantry";
    }
}
