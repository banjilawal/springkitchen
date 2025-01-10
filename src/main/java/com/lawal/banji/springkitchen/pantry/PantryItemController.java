package com.lawal.banji.springkitchen.pantry;

import com.lawal.banji.springkitchen.dataset.FoodNameDataset;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Controller
@RequestMapping({"/kitchen/food"})
public final class PantryItemController {

    private static final Logger logger = LoggerFactory.getLogger(PantryItemController.class);

    public static final String REDIRECT = "redirect:/kitchen/food";
    private static final String REDIRECT_FOOD_DETAILS = "redirect:/food/{id}";

    private static final String DASHBOARD = "foodDashboard";
    private static final String KITCHEN_VIEW = "kitchen";
    private static final String INVENTORY_VIEW = "inventoryView";
    private static final String FOOD_VIEW = "foodView";
    private static final String FOOD_FORM_VIEW = "foodFormView";
    private static final String DELETE_FOOD_VIEW = "deleteFoodView";
    private static final String ERROR_VIEW = "errorView";

    private static final String SEARCH_PAGE_HEADING = "Search Results";
    private static final String FORM_PAGE_HEADING = "PantryItem Form";

    private PantryItemService pantryItemService;

    @Autowired
    public PantryItemController(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
    }

    @ModelAttribute
    public void setModelAttributes(Model model) { model.addAttribute("dashboard", DASHBOARD); }

    @GetMapping({""})
    public String showAllFoods(Model model) {
        model.addAttribute("foods", pantryItemService.findAll());
        model.addAttribute("dashboard", DASHBOARD);
        model.addAttribute("view", INVENTORY_VIEW);
        return KITCHEN_VIEW;
    }

    @GetMapping("/search")
    public String searchFoods(Model model, @RequestParam(value = "string", required = false) String string) {
        model.addAttribute("foods", pantryItemService.search(string));
        model.addAttribute("view", INVENTORY_VIEW);
        model.addAttribute("pageHeading", SEARCH_PAGE_HEADING);
        return KITCHEN_VIEW;
    }

    @GetMapping("/{id}")
    public String showFood(Model model, @PathVariable(required = false) Long id) {
        if (id == null) { return handleError(model, FOOD_VIEW, "PantryItem ID is required."); }
        logger.info("Fetching pantryItem with ID: {}", id);

        PantryItem pantryItem = getFoodById(model, id, FOOD_VIEW);
        if (pantryItem == null) {
            logger.warn("PantryItem not found with id: {}", id);
            return REDIRECT;
        }
        addCommonAttributes(model, FOOD_VIEW, pantryItem);
        logger.info("Successfully displayed pantryItem with ID: {}", id);
        return KITCHEN_VIEW;
    }

    @GetMapping({"/form", "/form/{id}"})
    public String showFoodForm(Model model, @PathVariable(required = false) Long id) {
        PantryItem pantryItem = getOrCreateFood(model, id);
        if (pantryItem == null) {
            return "redirect:/error/handler?errorMessage=The%20requested%20item%20could%20not%20be%20found&viewName=itemNotFoundView";
        }
        addFoodFormAttributes(model, pantryItem);
        return KITCHEN_VIEW;
    }

    @PostMapping({"", "/{id}"})
    public String saveFood(
        @PathVariable(required = false) Long id,
        @Valid @ModelAttribute("food") PantryItem pantryItem,
        BindingResult bindingResult,
        Model model
    ) {
        PantryItem target = null;
        String view = "";
        String logMessage = "Updating {} pantry item";
        if (hasValidationErrors(pantryItem, bindingResult, model)) { return FOOD_FORM_VIEW; }
        if (id == null || pantryItem.isNew()) {
            target = new PantryItem();
            view =
            logMessage = "Creating new pantry item named {}";
        }
        else {
            target = getFoodById(model, id, FOOD_FORM_VIEW);
            if (target == null) return REDIRECT;
            view = FOOD_VIEW;
            logMessage = "Updating {} pantry item";
        }
        target.getUpdate(pantryItem);
        logger.info(logMessage, target.getName());
        return saveFoodAndRedirect(model, target, view, REDIRECT);
////        if (bindingResult.hasErrors()) {
////            if (pantryItem == null) { return showErrors(model, "pantryItem is null. Creation failed."); }
////            model.addAttribute("view", FOOD_FORM_VIEW);
////            model.addAttribute("pageHeading", "Pantry Item Form");
////            return KITCHEN_VIEW;
////        }
//        target.getUpdate(pantryItem);
//        foodService.save(pantryItem);
//        model.addAttribute("foods", foodService.findAll());
//        model.addAttribute("view", INVENTORY_VIEW);
//        return REDIRECT;
    }

//    @GetMapping("/{id}/edit")
//    public String editFoodForm(Model model, @PathVariable Long id) {
//        PantryItem food = foodService.findById(id);
//        if (food == null) { return showErrors(model, "The pantry item does not exist. Update failed."); }
//
//        model.addAttribute("food", food);
//        model.addAttribute("view", FOOD_FORM_VIEW);
//        model.addAttribute("pageHeading", "Update Pantry Item");
//        return KITCHEN_VIEW;
//    }
//
//    @PostMapping("/{id}")
//    public String updateFood(
//        @PathVariable Long id,
//        @ModelAttribute("food") PantryItem food,
//        BindingResult bindingResult,
//        Model model
//    ) {
////        FoodDTO food = foodService.findFoodById(id);
//        if (bindingResult.hasErrors()) {
//            if (food == null) { return showErrors(model, "The pantry item does not exist. Update failed."); }
//
//            model.addAttribute("view", FOOD_FORM_VIEW);
//            model.addAttribute("pageHeading", "Update Pantry Item");
//            return KITCHEN_VIEW; // Re-render the form with validation errors
//        }
//        model.addAttribute("food", foodService.save(food));
//        model.addAttribute("view", "pantryView");
//        return "redirect:/kitchen/food/"  + id;
//    }

    @GetMapping("/{id}/delete")
    public String showDeleteFoodForm(Model model, @PathVariable Long id) {
        String view = DELETE_FOOD_VIEW;
        PantryItem pantryItem = getFoodById(model, id, view);
        model.addAttribute("food", pantryItem);
        model.addAttribute("view", view);

        return KITCHEN_VIEW;
    }

    @PostMapping("/{id}/delete")
    public String deleteFood(Model model, @PathVariable Long id) {
        PantryItem pantryItem = getFoodById(model, id, DELETE_FOOD_VIEW);
        if (pantryItem != null) pantryItemService.deleteById(id);

        model.addAttribute("foods", pantryItemService.findAll());
        model.addAttribute("view", INVENTORY_VIEW);
        return KITCHEN_VIEW;
    }

    @GetMapping("/error/handler")
    public String handleError(
        Model model,
        @RequestParam(required = false) String errorMessage,
        @RequestParam(required = false, defaultValue = ERROR_VIEW) String viewName
    ) {
        if (errorMessage != null) { model.addAttribute("errorMessage", errorMessage); }
        model.addAttribute("view", viewName); // Reuse the dynamic view
        return KITCHEN_VIEW; // Standard entry point
    }

    private void addFoodFormAttributes(Model model, PantryItem pantryItem) {
        model.addAttribute("food", pantryItem);
        model.addAttribute("view", FOOD_FORM_VIEW);
        model.addAttribute("pageHeading", FORM_PAGE_HEADING);
    }

    private boolean hasValidationErrors(PantryItem pantryItem, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || pantryItem == null) {
            handleError(model, "PantryItem is null or has validation errors. Saving pantryItem failed.", FOOD_FORM_VIEW);
            return true;
        }
        return false;
    }

    private PantryItem getOrCreateFood(Model model, Long id) {
        PantryItem pantryItem = null;
        if (id != null) { pantryItem = getFoodById(model, id, ERROR_VIEW); }
        else { pantryItem = pantryItemCreator(); }
        return pantryItem;
    }

    private PantryItem getFoodById(Model model, Long id, String view) {
        PantryItem PantryItem = pantryItemService.findById(id);
        if (PantryItem == null) {
            handleError(model, view, "PantryItem not found.");
        }
        return PantryItem;
    }

    private void addCommonAttributes(Model model, String view, PantryItem pantryItem) {
        model.addAttribute("view", view);
        model.addAttribute("food", pantryItem);
    }

    private String saveFoodAndRedirect(Model model, PantryItem pantryItem, String view, String redirect) {
        saveAndSetupModel(pantryItem, model, view);
        model.addAttribute("foods", pantryItemService.findAll()); // Only include in cases where needed
        return redirect;
    }

    private void saveAndSetupModel(PantryItem pantryItem, Model model, String view) {
        model.addAttribute("food", pantryItemService.save(pantryItem));
        model.addAttribute("view", view);
    }

    private PantryItem pantryItemCreator() {
        return FoodNameDataset.pantryItem(new HashSet<>(pantryItemService.findAll()), 1, 30);
    }
}
