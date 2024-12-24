package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.recipe.data.StepRepo;
import com.lawal.banji.springkitchen.recipe.dto.StepDTO;
import com.lawal.banji.springkitchen.recipe.dto.StepDTOs;
import com.lawal.banji.springkitchen.recipe.model.Step;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class StepService {

    @Resource
    private final StepRepo stepRepo;

    @Autowired
    public StepService(StepRepo stepRepo) {
        this.stepRepo = stepRepo;
    }

    public Long count () {
        return stepRepo.count();
    }

    public Step save(StepDTO stepDTO) {
        Step step = new Step();
        step.update(stepDTO);
        return stepRepo.save(step);
    }

    public Iterable<Step> saveAll(Set<StepDTO> stepDTOs) {
        Set<Step> steps = new HashSet<>();
        for (StepDTO stepDTO : stepDTOs) {
            steps.add(save(stepDTO));
        }
        return steps;
    }

    public Step update(Long targetId, StepDTO stepDTO) {
        Step step = findById(targetId);
        if (step == null || !step.getId().equals(stepDTO.getId())) return null;
        step.update(stepDTO);
        return stepRepo.save(step);
    }

    public Iterable<Step> findAll() {
        return stepRepo.findAll();
    }

    public Step findById(Long id) {
        return stepRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        stepRepo.deleteById(id);
    }

    public Set<Step> searchByDirections(String string) {
        Set<Step> matches = new HashSet<>();
        for (Step step : stepRepo.findAll()) {
            if (step.getDirections().toLowerCase().contains(string.toLowerCase()))
                matches.add(step);
        }
        return matches;
    }

    public Set<Step> search (String string) {
        return searchByDirections(string);
    }
}
