package com.lawal.banji.springkitchen.food.controller;

import com.lawal.banji.springkitchen.food.controller.exception.FoodControllerDeleteExceptionNullPathVariable;
import com.lawal.banji.springkitchen.food.controller.exception.FoodControllerSearchExceptionNullPathVariable;
import com.lawal.banji.springkitchen.food.service.FoodServiceLoggingMessage;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceSearchExceptionTargetIdNull;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceSearchOperationFailed;
import com.lawal.banji.springkitchen.global.AppLogger;
import com.lawal.banji.springkitchen.orchestrator.RecipeMealOrchestratorService;
import com.lawal.banji.springkitchen.food.model.Food;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/kitchen/food")
public class FoodController{
    

    public static final String REDIRECT = "redirect:/kitchen/food";
    public static final String REDIRECT_FOOD_LIST = "redirect:/foods";
    public static final String REDIRECT_FOOD_DETAILS = "redirect:/food/{id}";

    public static final String DASHBOARD = "foodDashboard";
    public static final String KITCHEN_VIEW = "kitchen";
    public static final String FOOD_LIST_VIEW = "foodListView";
    public static final String FOOD_VIEW = "foodView";
    public static final String FOOD_FORM_VIEW = "foodFormView";
    public static final String FOOD_STEP_VIEW = "stepView";
    public static final String DELETE_FOOD_VIEW = "deleteFoodView";
    public static final String ERROR_VIEW = "errorView";


    public static final String SEARCH_PAGE_HEADING = "Search Results";
    public static final String FOOD_LIST_PAGE_HEADING = "Food List";
    public static final String FORM_PAGE_HEADING = "Food Form";
    public static final String FOOD_VIEW_PAGE_HEADING = "Food Details";

    private final RecipeMealOrchestratorService recipeMealOrchestratorService;

    @Autowired
    public FoodController(RecipeMealOrchestratorService recipeMealOrchestratorService) {
        this.recipeMealOrchestratorService = recipeMealOrchestratorService;
    }

    @ModelAttribute
    public void setModelAttributes(Model model) { model.addAttribute("dashboard", DASHBOARD); }

    @GetMapping({""})
    public String showAllFoods(Model model) {
        model.addAttribute("foods", recipeMealOrchestratorService.findAllFoods());
        model.addAttribute("dashboard", DASHBOARD);
        model.addAttribute("view", FOOD_LIST_VIEW);
        return KITCHEN_VIEW;
    }

    @GetMapping({"/search"})
    public String searchFoods(Model model, @RequestParam(value = "string", required = false) String string) {
        model.addAttribute("foods", recipeMealOrchestratorService.searchFoods(string));
        model.addAttribute("view", FOOD_LIST_VIEW);
        model.addAttribute("pageHeading", SEARCH_PAGE_HEADING);
        return KITCHEN_VIEW;
    }

    @GetMapping("/{id}")
    public String findFoodById (Model model, @PathVariable(required = false) Long id) {
        if (id == null) {
            AppLogger.error(FoodController.class, FoodControllerSearchExceptionNullPathVariable.MESSAGE, new FoodControllerSearchExceptionNullPathVariable());
            throw new FoodControllerSearchExceptionNullPathVariable();
        }

        AppLogger.debug(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.FINDING_FOOD_BY_ID_MESSAGE + " " + id);
        try {
            Food food = recipeMealOrchestratorService.findFoodById(id);
            AppLogger.info(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.FOUND_FOOD_BY_ID_MESSAGE + food.toString());
            addCommonFoodAttributes(model, food, FOOD_VIEW);
            return KITCHEN_VIEW;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceSearchOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }
//
//    @GetMapping({"/form", "/form/{id}"})
//    public String showFoodForm(Model model, @PathVariable(required = false) Long id) {
//        Food food = null;
//        if (id == null) food = new Food();
//        else food = getFoodById(model, id, FOOD_FORM_VIEW);
//        addFoodFormAttributes(model, food);
//
//        return KITCHEN_VIEW;
//    }
//
//
//    @PostMapping({"", "/{id}"})
//    public String saveFood(
//        @PathVariable(required = false) Long id,
//        @ModelAttribute("food") Food food,
//        BindingResult bindingResult,
//        Model model
//    ) {
//        String view = "";
//        String pageHeading = "";
//        if (formHasValidationErrors(food, bindingResult, model) || bindingResult.hasErrors()) {
//            logger.warn("Validation failed during form submission.");
//            model.addAttribute("food", food);
//            model.addAttribute("view", FOOD_FORM_VIEW);
//            return KITCHEN_VIEW; // Return to form on validation failure
//        }
//        if (id == null) {
//            view = FOOD_LIST_VIEW;
//            pageHeading = FOOD_LIST_PAGE_HEADING;
//            logger.info("Successfully created a new food with title: {}", food.getTitle());
//        } else {
//            view = FOOD_VIEW;
//            pageHeading = FOOD_VIEW_PAGE_HEADING;
//            logger.info("Successfully updated food with ID: {}", id);
//        }
//        recipeMealOrchestratorService.saveFood(food);
//        addCommonFoodAttributes(model, food, view, pageHeading);
//        return KITCHEN_VIEW;
//    }
//
    @GetMapping("/{id}/delete")
    public String showDeleteFoodForm (Model model, @PathVariable Long id) {
        if (id == null) {
            AppLogger.error(
                FoodController.class,
                FoodControllerDeleteExceptionNullPathVariable.MESSAGE,
                new FoodControllerDeleteExceptionNullPathVariable()
            );
            throw new FoodControllerSearchExceptionNullPathVariable();
        }

        AppLogger.debug(FoodController.class,
            FoodServiceLoggingMessage.DELETING_FOOD_MESSAGE + " " + id);
        try {
            Food food = recipeMealOrchestratorService.findFoodById(id);
            AppLogger.info(
                FoodController.class,
                FoodServiceLoggingMessage.FOUND_FOOD_BY_ID_MESSAGE + food.toString()
            );
            AppLogger.info(FoodController.class, FoodControllerLoggingMessage.HTML_DELETE_FORM_REQUEST + id);
            addCommonFoodAttributes(model, food, DELETE_FOOD_VIEW);
            return KITCHEN_VIEW;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceSearchOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
//
//        model.addAttribute("food", food);
//        model.addAttribute("view", DELETE_FOOD_VIEW);
//        return KITCHEN_VIEW;
    }
//
    @PostMapping("/{id}/delete")
    public String deleteFood(Model model, @PathVariable Long id) {
        AppLogger.info(FoodController.class, FoodControllerLoggingMessage.PROCESSING_FOOD_DELETION_REQUEST);
        recipeMealOrchestratorService.deleteFood(id);
//        Food food = getFoodById(model, id, ERROR_VIEW);
//        foodService.deleteByFoodId(id);
//
//        model.addAttribute("foods", recipeMealOrchestratorService.findAllFoods());
        model.addAttribute("view", FOOD_LIST_VIEW);
        AppLogger.info(FoodController.class, FoodControllerLoggingMessage.SENDING_FOOD_DELETION_RESPONSE_BODY);
        return REDIRECT;
    }
//
//    @GetMapping("/{foodId}/step/{stepId}")
//    public String showStep (Model model, @PathVariable Long stepId, @PathVariable Long foodId) {
//        Food food = getFoodById(model, foodId, FOOD_STEP_VIEW);
//        if (food == null) {
//            return "redirect:/error/handler?errorMessage=The%20requested%20item%20could%20not%20be%20found&viewName=itemNotFoundView";
//        }
//        Step step = food.findStepById(stepId);
//        if (step == null) {
//            return "redirect:/error/handler?errorMessage=The%20requested%20item%20could%20not%20be%20found&viewName=itemNotFoundView";
//        }
//        model.addAttribute("foodId", foodId);
//        model.addAttribute("stepId", stepId);
//        model.addAttribute("food", food);
//        model.addAttribute("step", step);
//        model.addAttribute("view", FOOD_STEP_VIEW);
//        return KITCHEN_VIEW;
//    }

//    @GetMapping("/error/handler")
//    public String handleError(
//        Model model,
//        @RequestParam(required = false, defaultValue = "errorView") String viewName,
//        @RequestParam(required = false, defaultValue = "An error occurred") String errorMessage
//    ) {
//        if (errorMessage != null) { model.addAttribute("errorMessage", errorMessage); }
//        model.addAttribute("view", viewName); // Reuse the dynamic view
//        return KITCHEN_VIEW; // Standard entry point
//    }
//
//    private boolean formHasValidationErrors(Food food, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors() || food == null) {
//            handleError(model, "Food is null or has validation errors. Saving food failed.", FOOD_FORM_VIEW);
//            return true;
//        }
//        return false;
//    }

//    private Food getOrCreateFood(Model model, Long id) {
//        if (id == null) return generateNewFood();
//        return  getFoodById(model, id, ERROR_VIEW);
//    }
//
//    private Food getFoodById(Model model, Long id, String view) {
//        Food food = recipeMealOrchestratorService.findFoodById(id);
//        if (food == null) { handleError(model, view, FoodService.FOOD_NOT_FOUND_BY_ID); }
//        return food;
//    }

//    private Food generateNewFood() {
//        Food food = new Food(null, FoodTitleDataset.title(), FoodDescriptionDataset.description());
//        for (int i = 0; i < new Random().nextInt(2, 7); i++) {
//            food.addStep(foodOrchestratorService.getRandomStep());
//            System.out.println("Generated new step " + food.getSteps().get(i).toString());
//        }
//        System.out.println("Generated new food " + food.toString() + " with " + food.getStepsAsSet().size() + " steps.");
//        return food;
//    }

    private void addCommonFoodAttributes(Model model, Food food, String view) {
        model.addAttribute("view", view);
        model.addAttribute("food", food);
//        model.addAttribute("stepCount", food.getSteps().size());
//        model.addAttribute("steps", food.getStepsAsList());
    }

    private void addFoodFormAttributes(Model model, Food food) {
        model.addAttribute("stepList", food.getStepsAsList());
        model.addAttribute("stepCount", food.getSteps().size());
        model.addAttribute("food", food);
        model.addAttribute("view", FOOD_FORM_VIEW);
        model.addAttribute("pageHeading", FORM_PAGE_HEADING);
    }

}