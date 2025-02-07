package com.lawal.banji.springkitchen.food.controller;

import com.lawal.banji.springkitchen.food.controller.exception.*;
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
import org.springframework.validation.BindingResult;
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
            AppLogger.error(
                FoodController.class,
                FoodControllerSearchExceptionNullPathVariable.MESSAGE,
                new FoodControllerSearchExceptionNullPathVariable()
            );
            throw new FoodControllerSearchExceptionNullPathVariable();
        }

        AppLogger.debug(
            RecipeMealOrchestratorService.class,
            FoodControllerLoggingMessage.HTM_FIND_FOOD_REQUEST + " " + id);
        try {
            Food food = recipeMealOrchestratorService.findFoodById(id);
            addCommonFoodModelAttributes(model, food, FOOD_VIEW);
            AppLogger.info(FoodController.class, FoodControllerLoggingMessage.SENDING_FOOD_RESPONSE_BODY);
            return KITCHEN_VIEW;
        } catch (DataAccessException e) {
            AppLogger.error(
                RecipeMealOrchestratorService.class,
                FoodServiceSearchOperationFailed.MESSAGE,
                new FoodControllerDataAccessException("findFoodById")
            );
            throw e;
        }
    }

    @GetMapping({"/form", "/form/{id}"})
    public String showFoodForm(Model model, @PathVariable(required = false) Long id) {
        Food food = (id == null) ? recipeMealOrchestratorService.generateFood() : recipeMealOrchestratorService.findFoodById(id);
        addCommonFoodModelAttributes(model, food, FOOD_FORM_VIEW);
        return KITCHEN_VIEW;
    }

    @PostMapping({"", "/{id}"})
    public String saveFood (
        @PathVariable(required = false) Long id,
        @ModelAttribute("food") Food food,
        BindingResult bindingResult,
        Model model
    ) {

        if (bindingResult.hasErrors()) {
            AppLogger.error(
                FoodController.class,
                FoodControllerSaveOperationValidationError.MESSAGE,
                new FoodControllerSaveOperationValidationError()
            );
            addFoodFormAttributes(model, food);
            return KITCHEN_VIEW;
        }

        try {
            Food savedFood;
            String destinationView = FOOD_VIEW;

            if (id == null) {
                destinationView = FOOD_LIST_VIEW;
                AppLogger.debug(
                    FoodController.class,
                    FoodControllerLoggingMessage.PROCESSING_ADD_NEW_FOOD_REQUEST
                );
                savedFood = recipeMealOrchestratorService.saveFood(food);

                if (savedFood == null) {
                    AppLogger.error(
                        FoodController.class,
                        FoodControllerSaveOperationFailed.MESSAGE,
                        new FoodControllerSaveOperationFailed()
                    );
                    throw new FoodControllerSaveOperationFailed();
                }
            } else {
                destinationView = FOOD_VIEW;
                AppLogger.debug(
                    FoodController.class,
                    FoodControllerLoggingMessage.PROCESSING_UPDATE_FOOD_REQUEST
                );
                savedFood = recipeMealOrchestratorService.updateFood(id, food);

                if (savedFood == null) {
                    AppLogger.error(
                        FoodController.class,
                        FoodControllerUpdateOperationFailed.MESSAGE,
                        new FoodControllerUpdateOperationFailed()
                    );
                    throw new FoodControllerUpdateOperationFailed();
                }
            }
            addCommonFoodModelAttributes(model, savedFood, destinationView);
            return KITCHEN_VIEW;
        } catch (DataAccessException e) {
            throw new FoodControllerUpdateOperationFailed();
        }
    }

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
            AppLogger.info(
                FoodController.class,
                FoodControllerLoggingMessage.HTML_DELETE_FORM_REQUEST + id
            );
            addCommonFoodModelAttributes(model, food, DELETE_FOOD_VIEW);
            return KITCHEN_VIEW;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class,
                FoodServiceSearchOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteFood(Model model, @PathVariable Long id) {
        AppLogger.info(FoodController.class, FoodControllerLoggingMessage.PROCESSING_FOOD_DELETION_REQUEST);
        recipeMealOrchestratorService.deleteFood(id);

        model.addAttribute("foods", recipeMealOrchestratorService.findAllFoods());
        model.addAttribute("view", FOOD_LIST_VIEW);
        AppLogger.info(FoodController.class, FoodControllerLoggingMessage.SENDING_FOOD_DELETION_RESPONSE_BODY);
        return REDIRECT;
    }

    private void addCommonFoodModelAttributes (Model model, Food food, String view) {
        model.addAttribute("view", view);
        model.addAttribute("food", food);
        model.addAttribute(
            "pageHeading",
            switch (view) {
                case FOOD_VIEW -> FOOD_VIEW_PAGE_HEADING;
                case DELETE_FOOD_VIEW, FOOD_FORM_VIEW -> FORM_PAGE_HEADING;
                case FOOD_LIST_VIEW -> FOOD_LIST_PAGE_HEADING;
                default -> "Food Details";
            }
        );
    }
}