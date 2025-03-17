package com.lawal.banji.springkitchen.recipe.service;


import com.lawal.banji.springkitchen.recipe.service.exception.RecipeRepoEmptyOrNull;

public class RecipeServiceLoggingMessage {

    public static final String SAVING_RECIPE_MESSAGE = " Saving recipe: ";
    public static final String SUCCESSFULLY_SAVE_RECIPE = " Successfully saved recipe: ";
    public static final String SAVING_RECIPE_FAILED = " Failed to save recipe: ";

    public static final String DELETING_RECIPE_MESSAGE = " Deleting recipe: ";
    public static final String SUCCESSFULLY_DELETE_RECIPE = " Successfully deleted recipe: ";

    public static final String FINDING_RECIPE_BY_ID_MESSAGE = " Finding recipe by id: ";
    public static final String FOUND_RECIPE_BY_ID_MESSAGE = " Found recipe with id: ";
    public static final String RECIPE_NOT_FOUND_BY_ID_MESSAGE = " Did not found recipe by id: ";

    public static final String FINDING_RECIPE_BY_TITLE_MESSAGE = " Finding recipe by title: ";
    public static final String FOUND_RECIPE_BY_TITLE_MESSAGE = " Found recipe with title: ";
    public static final String RECIPE_NOT_FOUND_BY_TITLE_MESSAGE = " did not found recipe by title: ";

    public static final String FINDING_RECIPE_BY_DESCRIPTION_MESSAGE = " Finding recipe by description: ";
    public static final String FOUND_RECIPE_BY_DESCRIPTION_MESSAGE = " Found recipe with description: ";
    public static final String RECIPE_NOT_FOUND_BY_DESCRIPTION_MESSAGE = " did not found recipe by description: ";

    public static final String UPDATING_RECIPE_MESSAGE = " Updating recipe: ";
    public static final String SUCCESSFULLY_UPDATED_RECIPE_MESSAGE = " Successfully updated recipe: ";

    public static final String VALIDATING_RECIPE_MESSAGE = " validating recipe: ";
    public static final String SUCCESSFULLY_VALIDATED_RECIPE = " Successfully validated recipe:";
    public static final String RECIPE_IS_NOT_VALID_MESSAGE = " recipe is not valid";

    public static final String FETCHING_ALL_RECIPES_MESSAGE = " Fetching all recipes";
    public static final String FOUND_ALL_RECIPES_MESSAGE = " Found all recipes";

    public static final String NUMBER_OF_RECIPES_MESSAGE = " number of recipes: ";

    public static final String SEARCHING_FOR_MATCHES_BY_STRING_MESSAGE = " Searching for recipes matching whose data contains: ";
    public static final String TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE = " Recipes had data containing: ";

    public static final String FOUND_NO_RECIPE_MESSAGE = " found no recipes";
    public static final String FOUND_SOME_RECIPE_MESSAGE = " found some recipes";

    public static final String RANDOMLY_SELECTING_RECIPE_MESSAGE = " Randomly selecting a recipe: ";
    public static final String SELECTED_RANDOM_RECIPE_MESSAGE = " selected random recipe: ";
    public static final String FAILED_TO_GET_RANDOM_RECIPE_FROM_NONEMPTY_REPO_MESSAGE = " None of the recipes in the nonempty recipe repo were selected";
    public static final String NO_RANDOM_RECIPE_AVAILABLE_MESSAGE = RecipeRepoEmptyOrNull.MESSAGE + " No recipe available to pick randomly";
}