package com.lawal.banji.springkitchen.food.service;

import com.lawal.banji.springkitchen.food.service.exception.FoodRepoEmptyOrNull;

public class FoodServiceLoggingMessage {

    public static final String SAVING_FOOD_MESSAGE = " Saving food: ";
    public static final String SUCCESSFULLY_SAVE_FOOD = " Successfully saved food: ";

    public static final String DELETING_FOOD_MESSAGE = " Deleting food: ";
    public static final String SUCCESSFULLY_DELETE_FOOD = " Successfully deleted food: ";

    public static final String FINDING_FOOD_BY_ID_MESSAGE = " Finding food by id: ";
    public static final String FOUND_FOOD_BY_ID_MESSAGE = " Found food with id: ";
    public static final String FOOD_NOT_FOUND_BY_ID_MESSAGE = " Did not found food by id: ";

    public static final String FINDING_FOOD_BY_NAME_MESSAGE = " Finding food by name: ";
    public static final String FOUND_FOOD_BY_NAME_MESSAGE = " Found food with name: ";
    public static final String FOOD_NOT_FOUND_BY_NAME_MESSAGE = " did not found food by name: ";

    public static final String UPDATING_FOOD_MESSAGE = " Updating food: ";
    public static final String SUCCESSFULLY_UPDATED_FOOD_MESSAGE = " Successfully updated food: ";

    public static final String VALIDATING_FOOD_MESSAGE = " validating food: ";
    public static final String SUCCESSFULLY_VALIDATED_FOOD = " Successfully validated food:";
    public static final String FOOD_IS_NOT_VALID_MESSAGE = " food is not valid";

    public static final String FETCHING_ALL_FOOD_MESSAGE = " Fetching all foods";
    public static final String FOUND_ALL_FOOD_MESSAGE = " Found all foods";

    public static final String NUMBER_OF_FOODS_MESSAGE = " number of foods: ";

    public static final String SEARCHING_FOR_MATCHES_BY_STRING_MESSAGE = " Searching for foods matching whose data contains: ";
    public static final String TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE = " Foods had data containing: ";

    public static final String FOUND_NO_FOOD_MESSAGE = " found no foods";
    public static final String FOUND_SOME_FOOD_MESSAGE = " found some foods";

    public static final String RANDOMLY_SELECTING_FOOD_MESSAGE = " Randomly selecting a food: ";
    public static final String SELECTED_RANDOM_FOOD_MESSAGE = " selected random food: ";
    public static final String FAILED_TO_GET_RANDOM_FOOD_FROM_NONEMPTY_REPO_MESSAGE = " None of the foods in the nonempty food repo were selected";
    public static final String NO_RANDOM_FOOD_AVAILABLE_MESSAGE = FoodRepoEmptyOrNull.MESSAGE + " No food available to pick randomly";
}