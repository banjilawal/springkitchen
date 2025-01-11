package com.lawal.banji.springkitchen;

import com.lawal.banji.springkitchen.recipe.model.Step;

import java.util.Set;

public class GlobalValidator {

    public static void validateId(Long id) {
        if (id == null) {
            throw new NullIdException();
        }
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new NullNameException();
        }
    }

    public static void validateStepSet(Set<Step> steps) {
        if (steps == null) {
            throw new NullStepSetException();
        }
    }
}
