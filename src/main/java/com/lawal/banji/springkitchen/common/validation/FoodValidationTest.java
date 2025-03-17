package com.lawal.banji.springkitchen.common.validation;

import com.lawal.banji.springkitchen.food.model.Food;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodValidationTest implements TestRunner {

    private Food food;

    @Setter(AccessLevel.NONE)
    private ValidationTestResult result;


    @Override
    public ValidationTestResult runTests () {
        return null;
    }
}