package com.lawal.banji.springkitchen.common.validation;

import lombok.Data;

@Data
public class ValidationTestResult {

    private boolean outcome;
    private String message;

    public ValidationTestResult () {
        this.outcome = false;
        this.message = "validation not performed";
    }
}