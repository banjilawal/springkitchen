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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/kitchen/pantry", "/kitchen_page/pantry"})
public class PantryItemController {

    private String dashboard;
    private PantryItemService pantryItemService;

    @Autowired
    public PantryItemController(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
        this.dashboard = "pantryDashboard";

        for (PantryItem pantryItem: PantryItemGenerator.pantryItems(15, 10L,25L)) {
            PantryItem item = pantryItemService.save(pantryItem);
        }
        for (PantryItem pantryItem: pantryItemService.findAll()) {
            System.out.println(pantryItem.toString());
        }
    }

    public String getDashboard() {
        return dashboard;
    }

    public PantryItemService getPantryItemService() {
        return pantryItemService;
    }

    @Autowired
    public void setPantryItemService(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
    }


    @GetMapping({"/search"})
    public String searchPantryItems(
        Model model,
        @RequestParam(value = "name", required = false) String name
    ) {
        Iterable<PantryItem> pantryItems = pantryItemService.search(name);
        model.addAttribute("pantryItems", pantryItems);
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("view", "pantrySearchResultsView");
        model.addAttribute("pageHeading", "Search Results");
        return "kitchen_page";
    }
    
    @GetMapping({"", "/inventory"})
    public String getPantryItems(Model model) {
        model.addAttribute("pantryItems", pantryItemService.findAll());
        model.addAttribute("message", pantryItemService.toString());
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("view", "inventoryView");
        model.addAttribute("pageHeading", "Pantry Inventory");

        return "kitchen_page";
    }

    @GetMapping("/{id}")
    public String getPantryItemById (Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) { return "redirect:/kitchen/pantry/inventory";}

        model.addAttribute("pantryItem", pantryItem);
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("view", "pantryItemView");
        model.addAttribute("id", pantryItem.getId());
        model.addAttribute("name", pantryItem.getName());
        model.addAttribute("quantityInStock", pantryItem.getQuantityInStock());
        model.addAttribute("reorderLevel", pantryItem.getReorderLevel());
        model.addAttribute("pageHeading", pantryItem.getName());
        return "kitchen_page";
    }

    @GetMapping("/save")
    public String showPantryItemForm(Model model, @RequestParam(value = "id", required = false) Long id) {
        PantryItemDTO pantryItemDTO;
        if (id != null) {
            PantryItem pantryItem = pantryItemService.findById(id);
            pantryItemDTO = new PantryItemDTO(id, pantryItem.getName(), pantryItem.getQuantityInStock(), pantryItem.getReorderLevel());
//            if (pantryItem == null) { return "redirect:/kitchen/pantry/inventory";}
//
//            model.addAttribute("pantryItem", pantryItem);
        } else {
            pantryItemDTO = PantryItemGenerator.pantryItemDTO(10L, 25L);
            while (pantryItemService.findByName(pantryItemDTO.getName()) != null) {
                pantryItemDTO = PantryItemGenerator.pantryItemDTO(10L, 25L);
            }
        }
        model.addAttribute("pantryItemDTO", pantryItemDTO);
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("view", "pantryItemFormView");
        model.addAttribute("pageHeading", "Pantry Item Form");
        return "kitchen_page";
    }

    @PostMapping("/save")
    public String savePantryItem(
        @Valid @ModelAttribute("pantryItemDTO") PantryItemDTO pantryItemDTO,
//        BindingResult bindingResult,
        Model model
    ) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("error", "Invalid form data. Please reenter");
//            model.addAttribute("dashboard", dashboard);
//            model.addAttribute("view", "pantryItemFormView");
//            model.addAttribute("pageHeading", "New Pantry Entry");
//            return "kitchen_page";
//        }
        String view = "inventory";
        if (pantryItemDTO.getId() != null) {
//            view = "pantryItemView";
            getPantryItemService().update(pantryItemDTO.getId(), pantryItemDTO);
        } else {
            pantryItemService.save(
                new PantryItem(
                    pantryItemDTO.getName(),
                    pantryItemDTO.getQuantityInStock(),
                    pantryItemDTO.getReorderLevel()
                )
            );
        }

        model.addAttribute("dashboard", dashboard);
        model.addAttribute("pantryItems", pantryItemService.findAll());
        model.addAttribute("view", view);

        return "kitchen_page";
//        return "redirect:/kitchen/pantry/inventory";
    }

    @GetMapping("/save/{id}")
    public String showUpdatePantryItemForm (Model model, @PathVariable Long id) {
        return showPantryItemForm(model, id);
//        PantryItem pantryItem = pantryItemService.findById(id);
//        if (pantryItem == null) { return "redirect:/pantry/inventory"; }
//        model.addAttribute("food", pantryItem);
//        model.addAttribute("dashboard", dashboard);
//        model.addAttribute("view", "pantryItemFormView");
//        model.addAttribute("pageHeading", "Edit " + pantryItem.getName());
//
//        return "kitchen_page";
    }

//    @PostMapping("/{id}/update")
//    public String updatePantryItem (
//        Model model,
//        @ModelAttribute ("pantryItem") PantryItem pantryItem,
//        Errors errors
//    ) {
//        if (pantryItem == null || getPantryItemService().findById(pantryItem.getId()) == null || errors.hasErrors()) {
//            System.out.println("PantryItem is null");
//            return "redirect:/kitchen/pantry/inventory";
//        }
//        pantryItemService.save(pantryItem);
//        model.addAttribute("dashboard", dashboard);
//        model.addAttribute("view", "inventory");
//        model.addAttribute("pantryItems", pantryItemService.findAll());
//        return "kitchen_page";
//    }

    @GetMapping("/{id}/delete")
    public String showDeletePantryItemForm (Model model, @PathVariable Long id) {
        PantryItem pantryItem = pantryItemService.findById(id);
        if (pantryItem == null) { return "redirect:/pantry/inventory"; }
        model.addAttribute("food", pantryItem);

        model.addAttribute("dashboard", dashboard);
        model.addAttribute("view", "foodDeleteView");
        model.addAttribute("pageHeading", "Delete " + pantryItem.getName());
        model.addAttribute("warningMessage", "Confirm you want to delete " + pantryItem.getName());
        return "kitchen_page";
    }

    @PostMapping("/{id}/delete")
    public String deletePantryItem (Model model, @ModelAttribute ("pantryItem") PantryItem pantryItem, Errors errors) {
        if (pantryItem == null || getPantryItemService().findById(pantryItem.getId()) == null || errors.hasErrors()) {
            System.out.println("PantryItem is null");
            return "redirect:/kitchen/pantry/inventory";
        }
        pantryItemService.deleteById(pantryItem.getId());
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("view", "inventory");
        model.addAttribute("pantryItems", pantryItemService.findAll());
        return "redirect:/kitchen/pantry/inventory";
    }

//    @GetMapping
//    public String redirectToPantry() {
//        return "forward:/kitchen/pantry/inventory";
//    }
}
