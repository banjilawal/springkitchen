package com.lawal.banji.springkitchen.common;

import java.time.LocalDateTime;

public enum Constant {

    INSTANCE;

    public static final long TIME_INTERVAL = 6;
    public static final int DAYS_PER_WEEK = 7;
    public static final int NUMBER_OF_FOODS = 30;


    public static final int NUMBER_OR_RECIPES = NUMBER_OF_FOODS / 2;
    public static final int MIN_FOOD_OCCURRENCES_PER_RECIPE = 1;
    public static final int MAX_FOOD_OCCURRENCES_PER_RECIPE = 3;

    public static final int HOURS_BETWEEN_MEALS = 8;
    private static final int NUMBER_OF_MEALS_PER_DAY = 3;
    public static final int NUMBER_OF_WEEKS_SCHEDULED = 1;
    public static final int MAX_RECIPE__OCCURRENCES_PER_DAY = 3;

    public static final int MIN_RECIPE_STEPS = 3;
    public static final int MAX_RECIPE_STEPS = 12;


    public static final LocalDateTime DATE__TIME_FLOOR = LocalDateTime.now().minusWeeks(TIME_INTERVAL);
    public static final LocalDateTime DATE__TIME_CEILING = LocalDateTime.now().plusWeeks(TIME_INTERVAL);

}