package com.lawal.banji.springkitchen.step.service;


import com.lawal.banji.springkitchen.step.service.exception.StepRepoEmptyOrNull;

public class StepServiceLoggingMessage {

    public static final String SAVING_STEP_MESSAGE = " Saving step: ";
    public static final String SUCCESSFULLY_SAVE_STEP = " Successfully saved step: ";
    public static final String SAVING_STEP_FAILED = " Failed to save step: ";

    public static final String DELETING_STEP_MESSAGE = " Deleting step: ";
    public static final String SUCCESSFULLY_DELETED_STEP = " Successfully deleted step: ";
    public static final String DELETING_STEP_FAILED = " Failed to delete step: ";

    public static final String FINDING_STEP_BY_ID_MESSAGE = " Finding step by id: ";
    public static final String FOUND_STEP_BY_ID_MESSAGE = " Found step with id: ";
    public static final String STEP_NOT_FOUND_BY_ID_MESSAGE = " Did not found step by id: ";

    public static final String FINDING_STEP_BY_DIRECTIONS_MESSAGE = " Finding step by directions: ";
    public static final String FOUND_STEP_BY_DIRECTIONS_MESSAGE = " Found step with directions: ";
    public static final String STEP_NOT_FOUND_BY_DIRECTIONS_MESSAGE = " did not found step by directions: ";

    public static final String FINDING_STEP_BY_DESCRIPTION_MESSAGE = " Finding step by description: ";
    public static final String FOUND_STEP_BY_DESCRIPTION_MESSAGE = " Found step with description: ";
    public static final String STEP_NOT_FOUND_BY_DESCRIPTION_MESSAGE = " did not found step by description: ";

    public static final String UPDATING_STEP_MESSAGE = " Updating step: ";
    public static final String SUCCESSFULLY_UPDATED_STEP_MESSAGE = " Successfully updated step: ";
    public static final String UPDATING_STEP_FAILED_MESSAGE = " Failed to update step: ";

    public static final String VALIDATING_STEP_MESSAGE = " validating step: ";
    public static final String SUCCESSFULLY_VALIDATED_STEP = " Successfully validated step:";
    public static final String STEP_IS_NOT_VALID_MESSAGE = " step is not valid";

    public static final String FETCHING_ALL_STEPS_MESSAGE = " Fetching all steps";
    public static final String FOUND_ALL_STEPS_MESSAGE = " Found all steps";

    public static final String NUMBER_OF_STEPS_MESSAGE = " number of steps: ";

    public static final String SEARCHING_FOR_MATCHES_BY_STRING_MESSAGE = " Searching for steps matching whose data contains: ";
    public static final String TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE = " Steps had data containing: ";

    public static final String FOUND_NO_STEP_MESSAGE = " found no steps";
    public static final String FOUND_SOME_STEP_MESSAGE = " found some steps";

    public static final String RANDOMLY_SELECTING_STEP_MESSAGE = " Randomly selecting a step: ";
    public static final String SELECTED_RANDOM_STEP_MESSAGE = " selected random step: ";
    public static final String FAILED_TO_GET_RANDOM_STEP_FROM_NONEMPTY_REPO_MESSAGE = " None of the steps in the nonempty step repo were selected";
    public static final String NO_RANDOM_STEP_AVAILABLE_MESSAGE = StepRepoEmptyOrNull.MESSAGE + " No step available to pick randomly";

    public static final String SELECTING_STEPS_BY_INGREDIENT_MESSAGE = " Selecting steps by ingredient: ";
    public static final String SELECTED_STEPS_BY_INGREDIENT_MESSAGE = " selected steps by ingredient: ";
    public static final String NO_STEPS_FOUND_BY_INGREDIENT_MESSAGE = " no steps found by ingredient: ";
    public static final String SELECTING_STEPS_BY_INGREDIENT_FAILED_MESSAGE = " Selecting steps by ingredient failed:";

    public static final String SELECTING_STEPS_BY_RECIPE_MESSAGE = " Selecting steps by recipe: ";
    public static final String SELECTED_STEPS_BY_RECIPE_MESSAGE = " selected steps by recipe: ";
    public static final String NO_STEPS_FOUND_BY_RECIPE_MESSAGE = " no steps found by recipe: ";
    public static final String SELECTING_STEPS_BY_RECIPE_FAILED_MESSAGE = " Selecting steps by recipe failed: ";

}