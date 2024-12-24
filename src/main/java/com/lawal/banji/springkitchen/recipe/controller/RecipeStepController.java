package com.lawal.banji.springkitchen.recipe.controller;

import com.lawal.banji.springkitchen.recipe.dto.RecipeDTO;
import com.lawal.banji.springkitchen.recipe.dto.StepDTO;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import com.lawal.banji.springkitchen.recipe.service.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/kitchen/recipe")
public final class RecipeStepController {

    private final RecipeStepService recipeStepService;

    @Autowired
    public RecipeStepController(RecipeStepService recipeStepService) {
        this.recipeStepService = recipeStepService;
    }

    @ModelAttribute
    public void setModelAttributes(Model model) { model.addAttribute("dashboard", "recipeDashboard"); }

    @GetMapping({""})
    public String getRecipes(Model model) {
        model.addAttribute("recipes", recipeStepService.findAllRecipes());
        model.addAttribute("view", "recipeListView");

        return "kitchen";
    }

    @GetMapping({"/search"})
    public String searchRecipes(Model model, @RequestParam(value = "string", required = false) String string) {
        model.addAttribute("recipes", recipeStepService.search(string));
        model.addAttribute("view", "recipeListView");
        model.addAttribute("pageHeading", "Search Results");
        return "kitchen";
    }

    @GetMapping("/{id}")
    public String showRecipe(Model model, @PathVariable Long id) {
        Recipe recipe = recipeStepService.findRecipeById(id);
        if (recipe == null) { return itemNotFound(model); }

        Set<Step> steps = recipeStepService.findStepsByRecipeId(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("steps", steps);
        model.addAttribute("view", "recipeView");
        return "kitchen";
    }

    @GetMapping("/new")
    public String showCreateRecipeForm(Model model) {
        Set<StepDTO> stepDTOs = new HashSet<>();
        model.addAttribute("recipeDTO", new RecipeDTO());
        model.addAttribute("stepDTOs", stepDTOs);
        model.addAttribute("view", "recipeFormView");
        model.addAttribute("pageHeading", "Recipe Item Form");
        return "kitchen";
    }

    @PostMapping()
    public String createRecipe (
        Model model, @ModelAttribute("recipeDTO") RecipeDTO recipeDTO, @ModelAttribute("stepDTOs") Set<StepDTO> stepDTOs) {
        Recipe recipe = recipeStepService.saveRecipe(recipeDTO);
        for (Step step : recipeStepService.saveAllSteps(stepDTOs)) { step.addRecipe(recipe); }
        model.addAttribute("recipes", recipeStepService.findAllRecipes());
        model.addAttribute("view", "recipeListView");
        return "redirect:/kitchen/recipe";
    }

    @GetMapping("/{id}/edit")
    public String showEditRecipeForm (Model model, @PathVariable Long id) {
        Recipe recipe = recipeStepService.findRecipeById(id);
        if (recipe == null) { return itemNotFound(model); }
        model.addAttribute("recipeDTO", recipe.toDTO());
        model.addAttribute("steps", recipeStepService.findStepsByRecipeId(id));
        model.addAttribute("view", "recipeFormView");
        model.addAttribute("pageHeading", "Update Recipe");
        return "kitchen";
    }

    @PostMapping("/{id}")
    public String updateRecipe (Model model, @PathVariable Long id, @ModelAttribute("recipeDTO") RecipeDTO recipeDTO) {
        if (recipeStepService.findRecipeById(id) == null) { return itemNotFound(model); }
        model.addAttribute("recipe", recipeStepService.updateRecipe(id, recipeDTO));
        model.addAttribute("steps", recipeStepService.findStepsByRecipeId(id));
        model.addAttribute("view", "recipeView");
        return "redirect:/kitchen/recipe";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteRecipeForm (Model model, @PathVariable Long id) {
        Recipe recipe = recipeStepService.findRecipeById(id);
        if (recipe == null) { return itemNotFound(model); }
        model.addAttribute("recipe", recipe);
        model.addAttribute("view", "deleteRecipeView");
        return "kitchen";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteRecipe(Model model, @PathVariable Long id) {
        if (recipeStepService.findRecipeById(id) == null) { return itemNotFound(model); }
        recipeStepService.deleteByRecipeId(id);

        model.addAttribute("recipes", recipeStepService.findAllRecipes());
        model.addAttribute("view", "recipeListView");
        return "redirect:/kitchen/recipe";
    }

    @GetMapping("/{recipeId}/step/{id}")
    public String showStep (Model model, @PathVariable Long id, @RequestParam(value = "recipeId") Long recipeId) {
        if (recipeStepService.findRecipeById(recipeId) == null) { return itemNotFound(model); }

        Step step = recipeStepService.findStepById(id);
        if (step == null) { return itemNotFound(model); }

        model.addAttribute("recipeId", recipeId);
        model.addAttribute("view", "stepView");
        return "kitchen";
    }

    @GetMapping("/{recipeId}/step")
    public String showCreateStepForm (Model model, @PathVariable Long recipeId) {
        if (recipeStepService.findRecipeById(recipeId) == null) { return itemNotFound(model); }
        model.addAttribute("stepDTO", new StepDTO());
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("view", "stepFormView");
        model.addAttribute("pageHeading", "Step Form");
        return "kitchen";
    }

    @PostMapping("/{recipeId}")
    public String createStep (Model model, @PathVariable Long recipeId, @ModelAttribute("stepDTO") StepDTO stepDTO) {
        Recipe recipe = recipeStepService.findRecipeById(recipeId);
        if (recipe == null) { return itemNotFound(model); }
        Step step = recipeStepService.saveStep(stepDTO);
        step.addRecipe(recipe);
        model.addAttribute("step", step);
        model.addAttribute("recipe", recipe);
        model.addAttribute("view", "recipeView");
        return "redirect:/kitchen/recipe/" + recipeId;
    }

    @GetMapping("/{recipeId}/step/{stepId}/edit")
    public String showEditStepForm (Model model, @PathVariable Long recipeId, @PathVariable Long stepId) {
        if (recipeStepService.findRecipeById(recipeId) == null) { return itemNotFound(model); }

        Step step = recipeStepService.findStepById(stepId);
        if (step == null) { return itemNotFound(model); }

        model.addAttribute("recipeId", recipeId);
        model.addAttribute("stepDTO", step.toDTO());
        model.addAttribute("view", "stepFormView");
        model.addAttribute("pageHeading", "Update Step");
        return "kitchen";
    }

    @PostMapping("{recipeId}/step/{id}")
    public String updateStep (
        Model model,
        @PathVariable Long id,
        @ModelAttribute("stepDTO") StepDTO stepDTO,
        @RequestParam(value = "recipeId") Long recipeId
    ) {
        Recipe recipe = recipeStepService.findRecipeById(recipeId);
        if (recipe == null) { return itemNotFound(model); }

        Step step = recipeStepService.findStepById(id);
        if (step == null) { return itemNotFound(model); }

        model.addAttribute("step", recipeStepService.updateStep(id, stepDTO));
        model.addAttribute("recipe", recipe);
        model.addAttribute("view", "recipeView");

        return "redirect:/kitchen/recipe/" + recipeId;
    }

    @GetMapping("/{recipeId}/step/{stepId}")
    public String showDeleteRecipeStepForm (Model model, @PathVariable Long recipeId, @PathVariable Long stepId) {
        Recipe recipe = recipeStepService.findRecipeById(recipeId);
        if (recipe == null) { return itemNotFound(model); }

        Step step = recipeStepService.findStepById(stepId);
        if (step == null) { return itemNotFound(model); }

        model.addAttribute("step", step);
        model.addAttribute("recipe", recipe);
        model.addAttribute("view", "deleteRecipeStepView");
        return "kitchen";
    }

    @DeleteMapping("/{recipeId}/step/{stepId}")
    public String deleteStepFromRecipe (Model model, @PathVariable Long recipeId, @PathVariable Long stepId) {
        Recipe recipe = recipeStepService.findRecipeById(recipeId);
        if (recipe == null) { return itemNotFound(model); }

        Step step = recipeStepService.findStepById(stepId);
        if (step == null) { return itemNotFound(model); }

        model.addAttribute("recipe", recipeStepService.updateRecipe(recipeId, recipe.toDTO()));
        model.addAttribute("steps", recipeStepService.removeStepFromRecipe(recipe, stepId));
        model.addAttribute("view", "recipeView");

        return "redirect:/kitchen/recipe/" + recipeId;
    }

    @GetMapping("/notfound")
    public String itemNotFound(Model model) {
        model.addAttribute("view", "notFoundView");
        return "kitchen";
    }

    @GetMapping("/error")
    public String showErrors(Model model) {
        model.addAttribute("view", "errorView");
        return "kitchen";
    }
}
