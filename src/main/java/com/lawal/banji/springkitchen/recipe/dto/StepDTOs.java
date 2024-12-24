package com.lawal.banji.springkitchen.recipe.dto;

import java.util.HashSet;
import java.util.Set;

public class StepDTOs {
    private Set<StepDTO> stepDTOs;

    public StepDTOs() {
        this.stepDTOs = new HashSet<>();
    }

    public Set<StepDTO> getStepDTOs() {
        return stepDTOs;
    }

    public void setStepDTOs(Set<StepDTO> stepDTOs) {
        this.stepDTOs.clear();
        for (StepDTO stepDTO : stepDTOs) {
            this.stepDTOs.add(stepDTO);
        }
    }

    public void addStepDTO(StepDTO stepDTO) {
        if (stepDTO != null) this.stepDTOs.add(stepDTO);
    }

    public void removeStepDTO(StepDTO stepDTO) {
        if (stepDTO != null) this.stepDTOs.remove(stepDTO);
    }
}
