package com.lawal.banji.springkitchen.recipe.controller;

import com.lawal.banji.springkitchen.orchestrator.RecipeMealOrchestratorService;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kitchen/recipe")
public class RecipeController {

    public static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    public static final String REDIRECT = "redirect:/kitchen/recipe";
    public static final String REDIRECT_RECIPE_LIST = "redirect:/recipes";
    public static final String REDIRECT_RECIPE_DETAILS = "redirect:/recipe/{id}";

    public static final String DASHBOARD = "recipeDashboard";
    public static final String KITCHEN_VIEW = "kitchen";
    public static final String RECIPE_LIST_VIEW = "recipeListView";
    public static final String RECIPE_VIEW = "recipeView";
    public static final String RECIPE_FORM_VIEW = "recipeFormView";
    public static final String RECIPE_STEP_VIEW = "stepView";
    public static final String DELETE_RECIPE_VIEW = "deleteRecipeView";
    public static final String ERROR_VIEW = "errorView";

    public static final String RECIPE_CANNOT_BE_CREATED = RecipeService.RECIPE_NOT_FOUND_BY_ID + ". Recipe cannot be created";

    public static final String SEARCH_PAGE_HEADING = "Search Results";
    public static final String RECIPE_LIST_PAGE_HEADING = "Recipe List";
    public static final String FORM_PAGE_HEADING = "Recipe Form";
    public static final String RECIPE_VIEW_PAGE_HEADING = "Recipe Details";

    private final RecipeMealOrchestratorService recipeMealOrchestratorService;

    @Autowired
    public RecipeController(RecipeMealOrchestratorService recipeMealOrchestratorService) {
        this.recipeMealOrchestratorService = recipeMealOrchestratorService;
    }

    @ModelAttribute
    public void setModelAttributes(Model model) { model.addAttribute("dashboard", DASHBOARD); }

    @GetMapping({""})
    public String showAllRecipes(Model model) {
        model.addAttribute("recipes", recipeMealOrchestratorService.findAllRecipes());
        model.addAttribute("dashboard", DASHBOARD);
        model.addAttribute("view", RECIPE_LIST_VIEW);
        return KITCHEN_VIEW;
    }

    @GetMapping({"/search"})
    public String searchRecipes(Model model, @RequestParam(value = "string", required = false) String string) {
        model.addAttribute("recipes", recipeMealOrchestratorService.search(string));
        model.addAttribute("view", RECIPE_LIST_VIEW);
        model.addAttribute("pageHeading", SEARCH_PAGE_HEADING);
        return KITCHEN_VIEW;
    }

    @GetMapping("/{id}")
    public String showRecipe(Model model, @PathVariable(required = false) Long id) {
        if (id == null) return handleError(model, RECIPE_VIEW, RecipeService.RECIPE_ID_CANNOT_BE_NULL);

        Recipe recipe = getRecipeById(model, id, RECIPE_VIEW);
        if (recipe == null) {
            logger.warn("Recipe not found with ID: {}", id);
            return handleError(model, RECIPE_VIEW, RecipeService.RECIPE_NOT_FOUND_BY_ID);
        }
        logger.info("Fetching recipe with ID: {}", id);

        addCommonRecipeAttributes(model, recipe, RECIPE_VIEW, RECIPE_VIEW_PAGE_HEADING);
        logger.info("Successfully displayed recipe with ID: {}", id);
        return KITCHEN_VIEW;
    }

    @GetMapping({"/form", "/form/{id}"})
    public String showRecipeForm(Model model, @PathVariable(required = false) Long id) {
        Recipe recipe = null;
        if (id == null) recipe = new Recipe();
        else recipe = getRecipeById(model, id, RECIPE_FORM_VIEW);
        addRecipeFormAttributes(model, recipe);

        return KITCHEN_VIEW;
    }


    @PostMapping({"", "/{id}"})
    public String saveRecipe(
        @PathVariable(required = false) Long id,
        @ModelAttribute("recipe") Recipe recipe,
        BindingResult bindingResult,
        Model model
    ) {
        String view = "";
        String pageHeading = "";
        if (!recipeMealOrchestratorService.isValidRecipe(recipe))
            throw new IllegalArgumentException("Recipe is not valid.");
        if (formHasValidationErrors(recipe, bindingResult, model) || bindingResult.hasErrors()) {
            logger.warn("Validation failed during form submission.");
            model.addAttribute("recipe", recipe);
            model.addAttribute("view", RECIPE_FORM_VIEW);
            return KITCHEN_VIEW; // Return to form on validation failure
        }
        if (id == null) {
            view = RECIPE_LIST_VIEW;
            pageHeading = RECIPE_LIST_PAGE_HEADING;
            logger.info("Successfully created a new recipe with title: {}", recipe.getTitle());
        } else {
            view = RECIPE_VIEW;
            pageHeading = RECIPE_VIEW_PAGE_HEADING;
            logger.info("Successfully updated recipe with ID: {}", id);
        }
        recipeMealOrchestratorService.saveRecipe(recipe);
        addCommonRecipeAttributes(model, recipe, view, pageHeading);
        return KITCHEN_VIEW;
    }

    @GetMapping("/{id}/delete")
    public String showDeleteRecipeForm (Model model, @PathVariable Long id) {
        Recipe recipe = getRecipeById(model, id, DELETE_RECIPE_VIEW);

        model.addAttribute("recipe", recipe);
        model.addAttribute("view", DELETE_RECIPE_VIEW);
        return KITCHEN_VIEW;
    }

    @PostMapping("/{id}/delete")
    public String deleteRecipe(Model model, @PathVariable Long id) {
        Recipe recipe = getRecipeById(model, id, ERROR_VIEW);
//        recipeService.deleteByRecipeId(id);

        model.addAttribute("recipes", recipeMealOrchestratorService.findAllRecipes());
        model.addAttribute("view", RECIPE_LIST_VIEW);
        return REDIRECT;
    }

    @GetMapping("/{recipeId}/step/{stepId}")
    public String showStep (Model model, @PathVariable Long stepId, @PathVariable Long recipeId) {
        Recipe recipe = getRecipeById(model, recipeId, RECIPE_STEP_VIEW);
        if (recipe == null) {
            return "redirect:/error/handler?errorMessage=The%20requested%20item%20could%20not%20be%20found&viewName=itemNotFoundView";
        }
        Step step = recipe.findStepById(stepId);
        if (step == null) {
            return "redirect:/error/handler?errorMessage=The%20requested%20item%20could%20not%20be%20found&viewName=itemNotFoundView";
        }
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("stepId", stepId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("step", step);
        model.addAttribute("view", RECIPE_STEP_VIEW);
        return KITCHEN_VIEW;
    }

    @GetMapping("/error/handler")
    public String handleError(
        Model model,
        @RequestParam(required = false, defaultValue = "errorView") String viewName,
        @RequestParam(required = false, defaultValue = "An error occurred") String errorMessage
    ) {
        if (errorMessage != null) { model.addAttribute("errorMessage", errorMessage); }
        model.addAttribute("view", viewName); // Reuse the dynamic view
        return KITCHEN_VIEW; // Standard entry point
    }

    private boolean formHasValidationErrors(Recipe recipe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || recipe == null) {
            handleError(model, "Recipe is null or has validation errors. Saving recipe failed.", RECIPE_FORM_VIEW);
            return true;
        }
        return false;
    }

//    private Recipe getOrCreateRecipe(Model model, Long id) {
//        if (id == null) return generateNewRecipe();
//        return  getRecipeById(model, id, ERROR_VIEW);
//    }

    private Recipe getRecipeById(Model model, Long id, String view) {
        Recipe recipe = recipeMealOrchestratorService.findRecipeById(id);
        if (recipe == null) { handleError(model, view, RecipeService.RECIPE_NOT_FOUND_BY_ID); }
        return recipe;
    }

//    private Recipe generateNewRecipe() {
//        Recipe recipe = new Recipe(null, RecipeTitleDataset.title(), RecipeDescriptionDataset.description());
//        for (int i = 0; i < new Random().nextInt(2, 7); i++) {
//            recipe.addStep(recipeOrchestratorService.getRandomStep());
//            System.out.println("Generated new step " + recipe.getSteps().get(i).toString());
//        }
//        System.out.println("Generated new recipe " + recipe.toString() + " with " + recipe.getStepsAsSet().size() + " steps.");
//        return recipe;
//    }

    private void addCommonRecipeAttributes(Model model, Recipe recipe, String view, String pageHeading) {
        model.addAttribute("view", view);
        model.addAttribute("recipe", recipe);
        model.addAttribute("stepCount", recipe.getSteps().size());
        model.addAttribute("steps", recipe.getStepsAsList());
        model.addAttribute("pageHeading", pageHeading);
    }

    private void addRecipeFormAttributes(Model model, Recipe recipe) {
        model.addAttribute("stepList", recipe.getStepsAsList());
        model.addAttribute("stepCount", recipe.getSteps().size());
        model.addAttribute("recipe", recipe);
        model.addAttribute("view", RECIPE_FORM_VIEW);
        model.addAttribute("pageHeading", FORM_PAGE_HEADING);
    }

}
