package com.lawal.banji.springkitchen.food.controller;

import com.lawal.banji.springkitchen.food.service.exception.FoodRepoEmptyOrNull;

public class FoodControllerLoggingMessage {

    public static final String HTML_FOOD_CREATION_FORM_REQUEST = " Request for create a new food form: ";
    public static final String PROCESSING_ADD_NEW_FOOD_REQUEST = " Processing add new food form: ";
    public static final String SENDING_FOOD_ADDITION_RESPONSE_BODY = " Successfully the added new food ";

    public static final String SENDING_FOOD_FORM_VIEW = " Sending food form in response body ";
    public static final String HTML_FOOD_UPDATE_REQUEST_FORM = " Food update request form  update existing food form: ";
    public static final String PROCESSING_UPDATE_FOOD_REQUEST = " Processing food update request for using @PathVariable: ";
    public static final String SENDING_FOOD_NOT_FOUND_FOR_UPDATE_RESPONSE_BODY = " Food not found for update using @PathVariable ";
    public static final String SENDING_FOOD_UPDATE_RESPONSE_BODY = " Successfully update food with id ";

    public static final String HTML_DELETE_FORM_REQUEST = " Food deletion form request for using id @pathVariable";
    public static final String PROCESSING_FOOD_DELETION_REQUEST = " Processing food deletion request for food id ";
    public static final String SENDING_FOOD_DELETION_RESPONSE_BODY = " Sending Successful food deletion response body";

    public static final String HTM_FIND_FOOD_REQUEST = " Food search request with id @PathVariable: ";
    public static final String SENDING_FOOD_RESPONSE_BODY = " Sending food matching id  in response body ";
    public static final String SENDING_FOOD_NOT_FOUND_RESPONSE_BODY = " Sending food not found by id @pathVariable ";

    public static final String FINDING_FOOD_BY_NAME_MESSAGE = " Finding food by name: ";
    public static final String FOUND_FOOD_BY_NAME_MESSAGE = " Found food with name: ";
    public static final String FOOD_NOT_FOUND_BY_NAME_MESSAGE = " did not found food by name: ";

    public static final String HTML_FOOD_UPDATE_FORM_REQUEST = " Food update form request using id @pathVariable: ";
    public static final String SENDING_SUCCESSFUL_FOOD_UPDATE_RESPONSE_BODY = " Sending response body with food update: ";

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