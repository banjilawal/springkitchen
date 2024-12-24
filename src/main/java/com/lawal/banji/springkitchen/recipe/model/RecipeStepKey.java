//package com.lawal.banji.springkitchen.recipe.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//@Embeddable
//public final class RecipeStepKey implements Serializable {
//
//    @Column(name = "recipe_id")
//    private Long recipeId;
//
//    @Column(name = "step_id")
//    private Long stepId;
//
//    public RecipeStepKey() {}
//
//    public RecipeStepKey(Recipe recipe, Step step){
//        this(recipe.getId(), step.getId());
//    }
//
//    public RecipeStepKey(Long recipeId, Long stepId){
//        this.recipeId = recipeId;
//        this.stepId = stepId;
//    }
//
//    public Long getRecipeId() {
//        return recipeId;
//    }
//
//    public Long getStepId() {
//        return stepId;
//    }
//
//
//    public void setRecipeId(Long recipeId) {
//        this.recipeId = recipeId;
//    }
//
//    public void setStepId(Long stepDetailId) {
//        this.stepId = stepDetailId;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        if (object == this) return true;
//        if (object == null) return false;
//        if (object instanceof RecipeStepKey key)
//            return recipeId.equals(key.getRecipeId())
//                && stepId.equals(key.getStepId());
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(recipeId, stepId);
//    }
//
//    @Override
//    public String toString() {
//        return "RecipeStepKey[ recipeId:" + recipeId + " stepDetailId:" + stepId + " ]";
//    }
//}
